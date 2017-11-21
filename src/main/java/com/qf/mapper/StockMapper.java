package com.qf.mapper;

import com.qf.domain.Stock;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

/**
 * Created by ios on 17/11/15.
 */
@Mapper
public interface StockMapper {

    @Insert("INSERT INTO STOCK (product_id, position, pieces_count) VALUES (#{productId}, #{position}, #{countOfPieces})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "productId", before = true, resultType = int.class)
    Integer insertStock(Stock stock);


    @UpdateProvider(type = StockSqlBuilder.class, method = "buildUpdateStock")
    Integer updateStock(Stock stock);

    class StockSqlBuilder {
        public String buildUpdateStock(final Stock stock) {
            return new SQL(){{
                UPDATE("stock");
                if (stock.getCountOfPieces() != null && stock.getCountOfPieces() >= 0 ) {
                    SET("pieces_count = #{countOfPieces}");
                }
                if (stock.getPosition() != null) {
                    SET("position = #{position}");
                }
                if (stock.getProductId() != null) {
                    SET("product_id = #{productId}");
                }
                WHERE("id = #{id}");
            }}.toString();
        }
    }
}
