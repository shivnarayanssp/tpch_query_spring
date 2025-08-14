package dev.shiv4u.tpch_query.models;

// model/Region.java

import lombok.Data;

@Data
public class Region {
    private Integer regionKey;
    private String name;
    private String comment;
}

