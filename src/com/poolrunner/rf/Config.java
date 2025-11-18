package com.poolrunner.rf;

/**
 * Configuration holder for RF module.
 */
public final class Config {
    private Config() {}

    public static final int EXPECTED_WINDOW_SAMPLES = 600;
    public static final int MIN_POOL = 1;
    public static final int MAX_POOL = 2000;
    public static final double QUANTILE = 0.99; // reserved, module predicts P99 then maps to pool
    public static final int DEFAULT_SEARCH_STEP = 1; // step when searching candidate pool sizes
}
