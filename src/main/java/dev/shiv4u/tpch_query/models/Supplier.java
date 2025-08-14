package dev.shiv4u.tpch_query.models;

// model/Supplier.java

import lombok.Data;

@Data
public class Supplier {
    private Integer suppKey;
    private String name;
    private String address;
    private Integer nationKey;
    private String phone;
    private Double acctBal;
    private String comment;
}

