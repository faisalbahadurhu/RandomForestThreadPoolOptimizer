package com.poolrunner.rf;

/**
 * Single sampled row collected every second.
 */
public final class DataPoint {
    public final double rt;   // request rate (req/sec)
    public final double st;   // avg service time (sec or ms consistent)
    public final int pt;      // pool size at that second
    public final double p99;  // observed P99 latency (ms or same units)

    public DataPoint(double rt, double st, int pt, double p99) {
        this.rt = rt;
        this.st = st;
        this.pt = pt;
        this.p99 = p99;
    }
}
