package dev.shiv4u.tpch_query.models;

// model/Lineitem.java

import lombok.Data;

@Data
public class LineItem {
    private Integer lOrderkey;
    private Integer lLinenumber;
    private Integer lPartkey;
    private Integer lSuppkey;
    private Double lQuantity;
    private Double lExtendedprice;
    private Double lDiscount;
    private Double lTax;
    private String lReturnflag;
    private String lLinestatus;
    private java.sql.Date lShipdate;
    private java.sql.Date lCommitdate;
    private java.sql.Date lReceiptdate;
    private String lShipinstruct;
    private String lShipmode;
    private String lComment;
}

