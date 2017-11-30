package com.qf.domain;

import java.sql.Timestamp;

/**
 * Created by ios on 17/11/30.
 * 用来展示入库记录信息
 */
public class ProductStockInRecordVO {
    private String number;  //商品编号
    private Timestamp createAt; //入库时间
    private Float unitPrice; //入库时单价
    private Integer count; //入库数量
    private String position; //存放位置
    private Integer piecesPerBox; //每箱多少片
    private String imagePath; //图片名

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getPiecesPerBox() {
        return piecesPerBox;
    }

    public void setPiecesPerBox(Integer piecesPerBox) {
        this.piecesPerBox = piecesPerBox;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
