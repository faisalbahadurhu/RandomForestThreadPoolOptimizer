package com.poolrunner.rf;

import java.io.*;

/**
 * Utility to save/load Trainer to disk.
 */
public final class ModelIO {

    private ModelIO() {}

    public static void saveTrainer(Trainer trainer, File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            trainer.save(fos);
        }
    }

    public static Trainer loadTrainer(File file) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(file)) {
            // We know MockTrainer is the class used; load it
            MockTrainer mt = new MockTrainer();
            mt.load(fis);
            return mt;
        }
    }
}
