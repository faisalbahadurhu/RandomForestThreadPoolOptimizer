package com.poolrunner.rf;

/**
 * Feature vector for trainer input.
 */
public final class FeatureVector {
    public final double[] features; // {rt, st, pt}
    public final double label;      // p99

    public FeatureVector(double[] features, double label) {
        this.features = features;
        this.label = label;
    }
}
