package com.poolrunner.rf;

/**
 * Aggregates per-tree outputs. For this prototype we use simple mean.
 * Class left as separate in case you replace aggregator with percentile estimator.
 */
public final class QuantilePredictor {
    private QuantilePredictor() {}

    public static double aggregateMean(double[] perTree) {
        double s = 0;
        for (double v : perTree) s += v;
        return perTree.length == 0 ? 0.0 : s / perTree.length;
    }
}
