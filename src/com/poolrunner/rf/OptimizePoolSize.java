package com.poolrunner.rf;

/**
 * Convert predicted P99 (latency) to optimal pool size.
 * This implementation does a simple candidate search in [min,max] using the trainer
 * to simulate predicted P99 for each candidate by substituting p=q into features.
 *
 * This is intentionally simple for reproducibility; you can replace with a more
 * sophisticated policy later.
 */
public final class OptimizePoolSize {

    private OptimizePoolSize() {}

    public static int findOptimalPool(MockTrainer trainer, double currentRt, double currentSt, int currentPool) {
        // conservative strategy: search from currentPool up to MAX_POOL (to prefer larger pool)
        int best = currentPool;
        double targetP99 = Double.POSITIVE_INFINITY;
        for (int q = currentPool; q <= Config.MAX_POOL; q += Config.DEFAULT_SEARCH_STEP) {
            double[] features = new double[] { currentRt, currentSt, (double) q };
            double[] perTree = trainer.predictPerTree(features);
            double predictedP99 = QuantilePredictor.aggregateMean(perTree);
            // choose the q with minimum predicted P99; tie-breaker choose smaller q
            if (predictedP99 < targetP99) {
                targetP99 = predictedP99;
                best = q;
            }
            // small optimization: if predictedP99 is already near zero or very small, stop
            if (predictedP99 <= 0.0) break;
        }
        return Math.max(Config.MIN_POOL, Math.min(Config.MAX_POOL, best));
    }
}
