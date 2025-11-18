package com.poolrunner.rf;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        // Example: csv path provided as arg
        String csvPath = args.length > 0 ? args[0] : "window_600.csv";
        File csv = new File(csvPath);
        if (!csv.exists()) {
            System.err.println("CSV not found: " + csv.getAbsolutePath());
            return;
        }

        // Build trainer and module
        MockTrainer trainer = new MockTrainer();
        RandomForestP99Module module = new RandomForestP99Module(trainer);

        // Train from CSV
        module.trainFromCsv(csv);

        // Example current sample (could be latest sample)
        double currentRt = 420.0;
        double currentSt = 0.135; // seconds
        int currentPool = 120;

        int predictedPool = module.predictOptimalPool(currentRt, currentSt, currentPool);
        System.out.println("Predicted optimal pool: " + predictedPool);
    }
}
