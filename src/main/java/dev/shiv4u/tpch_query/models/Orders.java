package dev.shiv4u.tpch_query.models;

// model/Orders.java

import lombok.Data;

@Data
public class Orders {
    private Integer orderKey;
    private Integer custKey;
    private String orderStatus;
    private Double totalPrice;
    private java.sql.Date orderDate; // date type
    private String orderPriority;
    private String clerk;
    private Integer shipPriority;
    private String comment;
}

