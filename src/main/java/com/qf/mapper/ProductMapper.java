package com.qf.mapper;

import com.qf.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by ios on 17/11/3.
 */
@Mapper
public interface ProductMapper {

    @Select("select count(*) from product where type_id = #{typeId}")
    Integer findCountByTypeId(@Param("typeId") Integer typeId);

    @Select("select count(*) from product where type_id in (${typeIds})")
    Integer findCountByTypeIds(@Param("typeIds") String typeIds);


    @Select("select id, number, name, color, size, unit_price, pieces_per_box, description, type_id from product where type_id in (${typeIds})")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "number", property = "number"),
            @Result(column = "name", property = "name"),
            @Result(column = "color", property = "color"),
            @Result(column = "size", property = "size"),
            @Result(column = "unit_price", property = "unitPrice"),
            @Result(column = "pieces_per_box", property = "piecesPerBox"),
            @Result(column = "description", property = "description"),
            @Result(column = "type_id", property = "typeId"),

    })
    List<Product> findProductsByClassIdAndClassChildrenId(@Param("typeIds") String typeIds);

    @Select("select id, number, name, color, size, unit_price, pieces_per_box, description, type_id from product where type_id = #{typeId}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "number", property = "number"),
            @Result(column = "name", property = "name"),
            @Result(column = "color", property = "color"),
            @Result(column = "size", property = "size"),
            @Result(column = "unit_price", property = "unitPrice"),
            @Result(column = "pieces_per_box", property = "piecesPerBox"),
            @Result(column = "description", property = "description"),
            @Result(column = "type_id", property = "typeId"),

    })
    List<Product> findProductsByClassId(@Param("typeId") Integer typeId);

    @Select("select id, number, name, color, size, unit_price, pieces_per_box, description, type_id from product where user_Id = #{userId}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "number", property = "number"),
            @Result(column = "name", property = "name"),
            @Result(column = "color", property = "color"),
            @Result(column = "size", property = "size"),
            @Result(column = "unit_price", property = "unitPrice"),
            @Result(column = "pieces_per_box", property = "piecesPerBox"),
            @Result(column = "description", property = "description"),
            @Result(column = "type_id", property = "typeId"),
    })
    List<Product> findAllProductsByUser(@Param("userId") Integer userId);
}
