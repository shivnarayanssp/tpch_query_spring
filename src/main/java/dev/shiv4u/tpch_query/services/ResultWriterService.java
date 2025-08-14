package dev.shiv4u.tpch_query.services;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Map;

@Service
public class ResultWriterService {

    private static final DecimalFormat REVENUE_FORMAT = new DecimalFormat("#0.00");

    public void writeResults(String filePath, Map<String, Double> results) throws IOException {
        Path path = Paths.get(filePath);

        // Ensure parent directory exists
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            results.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) // Highest revenue first
                    .forEach(entry -> {
                        try {
                            bw.write(entry.getKey() + "|" + REVENUE_FORMAT.format(entry.getValue()));
                            bw.newLine();
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        }
    }
}
