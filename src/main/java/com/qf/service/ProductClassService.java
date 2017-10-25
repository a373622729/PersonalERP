package com.qf.service;

import com.qf.domain.ProductClassVO;
import com.qf.mapper.ProductClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ios on 17/10/25.
 */
@Service
public class ProductClassService {

    @Autowired
    private ProductClassMapper productClassMapper;

    public List<ProductClassVO> findAllProductClass(Integer userId) {
        int depthLevel = productClassMapper.findDeepthLevel();
        if (depthLevel == 0) {
            return null;
        } else {
            return productClassMapper.findClassByLevel(1, userId);
        }
    }

}
