package com.qf.mapper;

import com.qf.domain.ProductStockInRecordVO;
import com.qf.domain.StockInRecord;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ios on 17/11/30.
 */
public interface StockInRecordMapper {

    @Insert("INSERT INTO STOCK_IN_RECORD (stock_id, count, unit_price) VALUES (#{stockId}, #{count}, #{unitPrice})")
    Integer insertStockInRecord(StockInRecord stockInRecord);

    @Select("SELECT psv.number, sir.createAt, sir.count, sir.unit_price, psv.position, psv.pieces_per_box, psv.image_path FROM product_stock_view psv, STOCK_IN_RECORD sir " +
            "WHERE psv.stock_id = sir.stock_id AND (sir.createAt BETWEEN #{start} AND #{end}) " +
            "ORDER BY sir.createAt desc")
    @Results({
            @Result(column = "number", property = "number"),
            @Result(column = "createAt", property = "createAt"),
            @Result(column = "count", property = "count"),
            @Result(column = "unit_price", property = "unitPrice"),
            @Result(column = "position", property = "position"),
            @Result(column = "pieces_per_box", property = "piecesPerBox"),
            @Result(column = "image_path", property = "imagePath")
    })
    List<ProductStockInRecordVO> getStockInRecordBetween(@Param("start") Timestamp start, @Param("end") Timestamp end);
}
