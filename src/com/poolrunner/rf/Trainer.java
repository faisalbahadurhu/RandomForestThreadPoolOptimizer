package com.poolrunner.rf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Simple trainer interface (pluggable).
 */
public interface Trainer {
    /**
     * Train the model on provided rows.
     */
    void train(List<FeatureVector> rows);

    /**
     * Per-tree predictions for given feature array.
     * Returns array of per-tree outputs.
     */
    double[] predictPerTree(double[] features);

    /**
     * Persist trainer state to output stream.
     */
    void save(OutputStream os) throws IOException;

    /**
     * Load trainer state from input stream.
     */
    void load(InputStream is) throws IOException, ClassNotFoundException;
}
