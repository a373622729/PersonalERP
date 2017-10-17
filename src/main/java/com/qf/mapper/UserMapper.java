package com.qf.mapper;

import com.qf.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by ios on 17/10/17.
 */
@Mapper
public interface UserMapper {
    @Select("select * from users where name = #{name}")
    User findByName(@Param("name") String name);
}
