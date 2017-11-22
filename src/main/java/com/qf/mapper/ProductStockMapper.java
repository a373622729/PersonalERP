package com.qf.mapper;

import com.qf.domain.ProductStockVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by ios on 17/11/9.
 */
@Mapper
public interface ProductStockMapper {

    @Select("select stock_id, product_id, number, name, color, size, unit_price, description, position, pieces_count, pieces_per_box, type_id, image_path, create_at, update_at  from product_stock_view where type_id in (${classIds})")
    @Results({
            @Result(column = "stock_id", property = "stock.id"),
            @Result(column = "product_id", property = "product.id"),
            @Result(column = "product_id", property = "stock.productId"),
            @Result(column = "number", property = "product.number"),
            @Result(column = "name", property = "product.name"),
            @Result(column = "color", property = "product.color"),
            @Result(column = "size", property = "product.size"),
            @Result(column = "unit_price", property = "product.unitPrice"),
            @Result(column = "description", property = "product.description"),
            @Result(column = "position", property = "stock.position"),
            @Result(column = "pieces_count", property = "stock.countOfPieces"),
            @Result(column = "pieces_per_box", property = "product.piecesPerBox"),
            @Result(column = "type_id", property = "product.typeId"),
            @Result(column = "image_path", property = "product.imagePath"),
            @Result(column = "create_at", property = "product.createAt"),
            @Result(column = "update_at", property = "product.updateAt")
    })
    List<ProductStockVO> findProductStocksByClassIdAndClassChildrenId(@Param("classIds") String classIds);
}
