package com.qf.service;

import com.qf.domain.ProductStockVO;
import com.qf.mapper.ProductStockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ios on 17/11/9.
 */
@Service
public class ProductStockService {

    @Autowired
    private ProductStockMapper productStockMapper;

    public List<ProductStockVO> findProductStocksByClassIdAndClassChildrenId(String classIds) {
        return productStockMapper.findProductStocksByClassIdAndClassChildrenId(classIds);
    }
}
