package com.poolrunner.rf;

import java.util.*;

/**
 * Converts DataPoint list to FeatureVector list.
 * Keeps the ordering intact.
 */
public final class FeatureEngineer {

    private FeatureEngineer() {}

    public static List<FeatureVector> toFeatureVectors(List<DataPoint> points) {
        List<FeatureVector> out = new ArrayList<>(points.size());
        for (DataPoint p : points) {
            double[] features = new double[] { p.rt, p.st, (double) p.pt };
            out.add(new FeatureVector(features, p.p99));
        }
        return out;
    }
}
