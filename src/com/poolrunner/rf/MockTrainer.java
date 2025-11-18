package com.poolrunner.rf;

import java.io.*;
import java.util.*;

/**
 * Concrete lightweight trainer implementing Trainer.
 * Builds one stump per feature using median threshold.
 */
public final class MockTrainer implements Trainer, Serializable {
    private static final long serialVersionUID = 1L;

    private final List<Stump> ensemble = new ArrayList<>();

    public MockTrainer() {}

    @Override
    public void train(List<FeatureVector> rows) {
        ensemble.clear();
        if (rows == null || rows.isEmpty()) return;

        int featureCount = rows.get(0).features.length;
        for (int fi = 0; fi < featureCount; fi++) {
            // extract column
            double[] col = new double[rows.size()];
            for (int i = 0; i < rows.size(); i++) col[i] = rows.get(i).features[fi];

            // median threshold
            double[] sorted = Arrays.copyOf(col, col.length);
            Arrays.sort(sorted);
            double theta = sorted[sorted.length / 2];

            // partition and compute means
            double leftSum = 0, rightSum = 0;
            int leftCount = 0, rightCount = 0;
            for (int i = 0; i < rows.size(); i++) {
                double val = col[i];
                double lbl = rows.get(i).label;
                if (val <= theta) { leftSum += lbl; leftCount++; }
                else { rightSum += lbl; rightCount++; }
            }
            double yL = leftCount == 0 ? averageLabel(rows) : leftSum / leftCount;
            double yR = rightCount == 0 ? averageLabel(rows) : rightSum / rightCount;

            ensemble.add(new Stump(fi, theta, yL, yR));
        }
    }

    private double averageLabel(List<FeatureVector> rows) {
        double s = 0;
        for (FeatureVector v : rows) s += v.label;
        return s / rows.size();
    }

    @Override
    public double[] predictPerTree(double[] features) {
        double[] out = new double[ensemble.size()];
        for (int i = 0; i < ensemble.size(); i++) out[i] = ensemble.get(i).predict(features);
        return out;
    }

    @Override
    public void save(OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(this);
            oos.flush();
        }
    }

    @Override
    public void load(InputStream is) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            MockTrainer loaded = (MockTrainer) ois.readObject();
            this.ensemble.clear();
            this.ensemble.addAll(loaded.ensemble);
        }
    }

    // For inspection by controller or tests
    public List<Stump> getEnsemble() { return Collections.unmodifiableList(ensemble); }
}
