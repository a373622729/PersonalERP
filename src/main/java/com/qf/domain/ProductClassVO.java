package com.qf.domain;

import java.util.List;

/**
 * Created by ios on 17/10/24.
 */
public class ProductClassVO extends ProductClassification{

    private List<ProductClassVO> productClassChildrenList;  //子级类型

    public List<ProductClassVO> getProductClassChildrenList() {
        return productClassChildrenList;
    }

    public void setProductClassChildrenList(List<ProductClassVO> productClassChildrenList) {
        this.productClassChildrenList = productClassChildrenList;
    }
}
