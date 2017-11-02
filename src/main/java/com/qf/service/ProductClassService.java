package com.qf.service;

import com.qf.domain.ProductClassVO;
import com.qf.mapper.ProductClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by ios on 17/10/25.
 */
@Service
public class ProductClassService {

    @Autowired
    private ProductClassMapper productClassMapper;

    public List<ProductClassVO> findAllProductClass(Integer userId) {
        Integer depthLevel = productClassMapper.findDeepthLevel();
        if ( depthLevel == 0) {
            return new ArrayList<>();
        } else {
            return productClassMapper.findClassByLevel(1, userId);
        }
    }

    public boolean addProductClass(Integer pid, String name, Integer userId) {
        int level = pid == 0 ? 0 : productClassMapper.findLevel(pid);
        int count =  productClassMapper.insertProductClass( pid, name, userId, level + 1);
        return count == 1;
    }

}
