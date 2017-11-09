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


    public boolean updateProductClass(Integer id, String name, Integer userId) {
        int count = productClassMapper.updateProductClass(id, name, userId);
        return count == 1;
    }

    //根据id删除他以及其子类的id
    public boolean deleteProductClass(Integer id) {
        String ids = productClassMapper.findChildrenNodeIds(id);

        int count = productClassMapper.deleteProductClass(ids.substring(2));
        return count > 0;
    }

    //删除这些id对应的条目
    public boolean deleteProductClass(String ids) {
        int count = productClassMapper.deleteProductClass(ids);
        return count > 0;
    }

    //找出这个id下所有子类的id
    //返回 '$,id,...'
    public String findChildrenNodeIds(Integer id) {
        String ids = productClassMapper.findChildrenNodeIds(id);
        return ids;
    }
}
