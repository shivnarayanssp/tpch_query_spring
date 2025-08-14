package dev.shiv4u.tpch_query.models;

// model/Nation.java

import lombok.Data;

@Data
public class Nation {
    private Integer nationKey;
    private String name;
    private Integer regionKey;
    private String comment;
}

