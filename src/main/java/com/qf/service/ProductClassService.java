package com.qf.service;

import com.qf.mapper.ProductClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ios on 17/10/25.
 */
@Service
public class ProductClassService {

    @Autowired
    private ProductClassMapper productClassMapper;



}
