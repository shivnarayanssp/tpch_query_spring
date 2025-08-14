package dev.shiv4u.tpch_query.models;

// model/Customer.java

import lombok.Data;

@Data
public class Customer {
    private Integer custKey;
    private String name;
    private String address;
    private Integer nationKey;
    private String phone;
    private Double acctBal;
    private String mktSegment;
    private String comment;
}

