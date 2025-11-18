package com.poolrunner.rf;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Top-level orchestrator that follows the algorithm:
 *  - load CSV / accept DataPoint list
 *  - feature engineer
 *  - train MockTrainer
 *  - predict for a new sample
 *  - map predicted P99 to p*
 */
public final class RandomForestP99Module {

    private final Trainer trainer;

    public RandomForestP99Module(Trainer trainer) {
        this.trainer = trainer;
    }

    /**
     * Train on a 600-sample window.
     */
    public void trainOn(List<DataPoint> window) {
        if (window == null || window.size() == 0) throw new IllegalArgumentException("Empty training window");
        List<FeatureVector> fv = FeatureEngineer.toFeatureVectors(window);
        trainer.train(fv);
    }

    /**
     * Predict optimal pool size for a given current sample.
     */
    public int predictOptimalPool(double currentRt, double currentSt, int currentPool) {
        // per-tree outputs for current sample (note: trainer implemented as MockTrainer)
        double[] perTree = trainer.predictPerTree(new double[] { currentRt, currentSt, (double) currentPool });
        // predicted P99 (ensemble aggregate)
        double predictedP99 = QuantilePredictor.aggregateMean(perTree);

        // compute optimal pool size via optimizer (uses trainer to simulate candidate pools)
        if (!(trainer instanceof MockTrainer)) {
            // fallback: simple heuristic if trainer not MockTrainer
            return currentPool;
        }
        MockTrainer mt = (MockTrainer) trainer;
        return OptimizePoolSize.findOptimalPool(mt, currentRt, currentSt, currentPool);
    }

    // convenience: load-and-train from CSV file
    public void trainFromCsv(File csvFile) throws IOException {
        List<DataPoint> rows = CSVDataLoader.loadFromCsv(csvFile.getAbsolutePath());
        if (rows.size() < Config.EXPECTED_WINDOW_SAMPLES) {
            System.err.println("Warning: training window smaller than expected: " + rows.size());
        }
        trainOn(rows);
    }
}
