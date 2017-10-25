package com.qf.mapper;

import com.qf.domain.ProductClassVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by ios on 17/10/25.
 */
@Mapper
public interface ProductClassMapper {

    ProductClassVO findAllClass();
}
