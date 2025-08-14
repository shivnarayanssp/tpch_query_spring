package dev.shiv4u.tpch_query.models;

// model/LineitemId.java (composite key)

import lombok.Data;

import java.io.Serializable;

@Data
public class LineItemId implements Serializable {
    private Integer lOrderkey;
    private Integer lLinenumber;
}

