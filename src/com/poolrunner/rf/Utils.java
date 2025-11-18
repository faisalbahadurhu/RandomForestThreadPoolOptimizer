package com.poolrunner.rf;

/** small helpers */
public final class Utils {
    private Utils() {}
    public static int safeInt(double d) { return (int)Math.max(Integer.MIN_VALUE, Math.min(Integer.MAX_VALUE, Math.round(d))); }
}
