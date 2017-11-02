package com.qf.mapper;

import com.qf.domain.ProductClassVO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * Created by ios on 17/10/25.
 */
@Mapper
public interface ProductClassMapper {

    /**
     * 找到该等级下所有的子等级, 支持多级
     */
    @Select("select * from product_classification where  user_id = #{userId} and level = #{level} ")
    @Results({
            @Result(id=true, column="id", property = "id"),
            @Result(column = "parent_id", property = "parentId"),
            @Result(column = "name", property = "name"),
            @Result(column = "level", property = "level"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "id", property = "productClassChildrenList",
                many = @Many(
                        select = "com.qf.mapper.ProductClassMapper.findChildrenClassByParentId",
                        fetchType = FetchType.LAZY
                )
            )
    })
    List<ProductClassVO> findClassByLevel(@Param("level") Integer level, @Param("userId") Integer userId);

    /**
     * 根据父等级的id找到所有子等级
     */
    @Select("select * from product_classification where parent_id = #{parentId} and id != 0")
    @Results({
            @Result(id=true, column = "id", property = "id"),
            @Result(column = "parent_id", property = "parentId"),
            @Result(column = "name", property = "name"),
            @Result(column = "level", property = "level"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "id", property = "productClassChildrenList",
                    many = @Many(
                            select = "com.qf.mapper.ProductClassMapper.findChildrenClassByParentId",
                            fetchType = FetchType.LAZY
                    )
            )
    })
    List<ProductClassVO> findChildrenClassByParentId(Integer parentId);

    @Select("select IFNULL(max(level),0) from product_classification")
    Integer findDeepthLevel();

    @Select("select level from product_classification where id = #{id}")
    Integer findLevel(Integer id);

    @Insert("insert into product_classification (parent_id, name, user_id, level) values(#{pid}, #{name}, #{userId}, #{level})")
    int insertProductClass(@Param("pid") Integer pid, @Param("name") String name, @Param("userId") Integer userId, @Param("level") Integer level);

    ProductClassVO findAllClass();
}
