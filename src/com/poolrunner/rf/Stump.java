package com.poolrunner.rf;

import java.io.Serializable;

/**
 * Decision stump (one-level regressor).
 */
public final class Stump implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int featureIndex;
    private final double threshold;
    private final double leftValue;
    private final double rightValue;

    public Stump(int featureIndex, double threshold, double leftValue, double rightValue) {
        this.featureIndex = featureIndex;
        this.threshold = threshold;
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    public double predict(double[] features) {
        double v = features[featureIndex];
        return v <= threshold ? leftValue : rightValue;
    }

    // getters for inspection / debugging
    public int getFeatureIndex() { return featureIndex; }
    public double getThreshold() { return threshold; }
    public double getLeftValue() { return leftValue; }
    public double getRightValue() { return rightValue; }
}
