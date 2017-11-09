package com.qf.service;

import com.qf.domain.Product;
import com.qf.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ios on 17/11/3.
 */
@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public int findCountByTypeId(Integer typeId) {
        return productMapper.findCountByTypeId(typeId);
    }

    public int findCountByTypeIds(String typeIds) {
        return productMapper.findCountByTypeIds(typeIds);
    }

    public List<Product> findProductsByClassId(Integer classId, Integer userId) {
        if (classId == 0) {
            return productMapper.findAllProductsByUser(userId);
        } else {
            return productMapper.findProductsByClassId(classId);
        }
    }

    /**
     * 找出typeIds匹配的所有product
     * @param typeIds
     * @return
     */
    public List<Product> findProductsByClassIdAndClassChildrenId(String typeIds) {
        return productMapper.findProductsByClassIdAndClassChildrenId(typeIds);
    }
}
