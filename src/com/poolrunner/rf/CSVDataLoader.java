package com.poolrunner.rf;

import java.io.*;
import java.util.*;

/**
 * Loads CSV files of the form:
 * rt,st,pt,p99
 * (header optional)
 */
public final class CSVDataLoader {

    /**
     * Load DataPoint list from CSV path.
     * Expect numeric values; trims whitespace.
     */
    public static List<DataPoint> loadFromCsv(String path) throws IOException {
        List<DataPoint> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            // If first line contains non-numeric tokens, assume header and skip
            if (line != null && line.matches(".*[a-zA-Z].*")) {
                line = br.readLine();
            }
            while (line != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length < 4) {
                        throw new IOException("Bad CSV format, expected 4 columns per row");
                    }
                    double rt = Double.parseDouble(parts[0].trim());
                    double st = Double.parseDouble(parts[1].trim());
                    int pt = (int) Math.round(Double.parseDouble(parts[2].trim()));
                    double p99 = Double.parseDouble(parts[3].trim());
                    rows.add(new DataPoint(rt, st, pt, p99));
                }
                line = br.readLine();
            }
        }
        return rows;
    }
}
