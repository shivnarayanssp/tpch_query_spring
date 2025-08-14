package dev.shiv4u.tpch_query.services;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TpchDataGeneratorService {

    /**
     * Generate TPCH data using dbgen command
     *
     * @param dbgenPath   path to dbgen executable
     * @param tablePath   path to store generated .tbl files
     * @param scaleFactor scale factor (e.g., "0.01")
     */
    public void generateTpchData(String dbgenPath, String tablePath, String scaleFactor) throws IOException, InterruptedException {
        // Example command: dbgen -s 0.01 -f -T c,o,l,s,n,r -v -C -D -b ./dists/
        ProcessBuilder pb = new ProcessBuilder(
                dbgenPath,
                "-s", scaleFactor,
                "-f",         // force overwrite
                "-T", "c,o,l,s,n,r",  // tables: customer, orders, lineitem, supplier, nation, region
                "-v"
        );
        pb.directory(new java.io.File(tablePath));
        pb.inheritIO();  // redirect output to console
        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("TPCH data generation failed with exit code " + exitCode);
        }
    }
}
