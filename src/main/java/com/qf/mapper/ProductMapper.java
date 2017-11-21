package com.qf.mapper;

import com.qf.domain.Product;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Set;

/**
 * Created by ios on 17/11/3.
 */
@Mapper
public interface ProductMapper {

    @Select("select count(*) from product where number = #{number}")
    Integer findCountByProductNumber(@Param("number") String number);

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

    @Insert("insert into product (number, name, color, size, unit_price, pieces_per_box, description, type_id, user_id, image_path) " +
            "values (#{number}, #{name}, #{color}, #{size}, #{unitPrice},#{piecesPerBox},#{description}, #{typeId}, #{userId},#{imagePath})")
    Integer insertProduct(Product product);

    @Delete("delete from PRODUCT WHERE id = #{productId}")
    Integer deleteProductById(@Param("productId") Integer productId);

    @UpdateProvider(type = ProductSqlBuilder.class, method = "buildUpdateProduct")
    Integer updateProduct(Product product);

    class ProductSqlBuilder {
        public String buildUpdateProduct(final Product product) {
            return new SQL(){{
                UPDATE("product");
                if (product.getColor() != null ) {
                    SET("color = #{color}");
                }
                if (product.getDescription() != null) {
                    SET("description = #{description}");
                }
                if (product.getImagePath() != null) {
                    SET("image_path = #{imagePath}");
                }
                if (product.getName() != null) {
                    SET("name = #{name}");
                }
                if (product.getPiecesPerBox() != null) {
                    SET("pieces_per_box = #{piecesPerBox}");
                }
                if (product.getSize() != null) {
                    SET("size = #{size}");
                }
                if (product.getUnitPrice() != null) {
                    SET("unit_price = #{unitPrice}");
                }
                if (product.getTypeId() != null) {
                    SET("type_id = #{typeId}");
                }
                WHERE(" id = #{id}");
            }}.toString();
        }
    }
}
