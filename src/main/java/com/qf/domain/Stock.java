package com.qf.domain;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by ios on 17/11/6.
 * 库存表
 * 该表对应商品在库房的存放位置,剩余数量等等
 */
public class Stock {
    @NotNull
    private Integer id;
    @NotNull
    private Integer productId;
    @Length(max = 50)
    private String position;
    
    private Integer countOfPieces;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getCountOfPieces() {
        return countOfPieces;
    }

    public void setCountOfPieces(Integer countOfPieces) {
        this.countOfPieces = countOfPieces;
    }
}
