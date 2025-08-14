package dev.shiv4u.tpch_query.dtos;

import lombok.Data;

@Data
public class Query5RequestDTO {
    private String regionName;   // e.g., "ASIA"
    private String startDate;    // "1994-01-01"
    private String endDate;      // "1995-01-01"
    private int numThreads;
    private String tablePath;    // folder for .tbl files
    private String resultPath;   // output file path
    private String dbgenPath;    // path to TPCH dbgen binary
    private String scaleFactor;  // e.g., "0.01"
}
