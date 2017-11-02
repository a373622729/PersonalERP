package com.qf.domain;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by ios on 17/10/24.
 * 对应商品类型表
 */
public class ProductClassification {

    private Integer id;
    @NotNull
    private Integer parentId;
    @Length(min = 2, max = 20)
    private String name;

    @NotNull
    protected Integer level;

    @NotNull Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
