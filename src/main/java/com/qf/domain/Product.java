package com.qf.domain;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by ios on 17/10/24.
 * 对应商品信息表
 */
public class Product {

    private Integer id;
    @Length(max = 10)
    private String number;
    @Length(max = 20)
    private String name;
    @Length(max = 10)
    private String color;
    @Length(max = 10)
    private String size;
    @NotNull
    private Float unitPrice;
    @NotNull
    private Integer piecesPerBox;
    @NotNull
    private Integer typeId;

    private Integer stockBox;

    private Integer stockPiece;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getPiecesPerBox() {
        return piecesPerBox;
    }

    public void setPiecesPerBox(Integer piecesPerBox) {
        this.piecesPerBox = piecesPerBox;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getStockBox() {
        return stockBox;
    }

    public void setStockBox(Integer stockBox) {
        this.stockBox = stockBox;
    }

    public Integer getStockPiece() {
        return stockPiece;
    }

    public void setStockPiece(Integer stockPiece) {
        this.stockPiece = stockPiece;
    }
}
