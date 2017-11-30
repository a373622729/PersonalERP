package com.qf.service;

import com.qf.domain.Product;
import com.qf.domain.ProductStockVO;
import com.qf.domain.Stock;
import com.qf.domain.StockInRecord;
import com.qf.mapper.ProductStockMapper;
import com.qf.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ios on 17/11/9.
 */
@Service
public class ProductStockService {

    @Autowired
    private ProductStockMapper productStockMapper;
    @Autowired
    private StockInRecordService stockInRecordService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StockService stockService;

    public List<ProductStockVO> findProductStocksByClassIdAndClassChildrenId(String classIds) {
        return productStockMapper.findProductStocksByClassIdAndClassChildrenId(classIds);
    }

    /**
     * 添加商品和库存信息,最后增加入库记录
     * @param product
     * @param stock
     * @return
     */
    @Transactional
    public boolean insertProductAndStock(Product product, Stock stock) {
        product.setNumber(product.getNumber().trim().toUpperCase());
        boolean success = productService.insertProduct(product);
        boolean success1 = stockService.insertStock(stock);
        //增加入库记录
        StockInRecord stockInRecord = new StockInRecord();
        stockInRecord.setCount(stock.getCountOfPieces());
        stockInRecord.setStockId(stock.getId());
        stockInRecord.setUnitPrice(product.getUnitPrice());
        boolean success2 = stockInRecordService.insertStockInRecord(stockInRecord);
        return success && success1 && success2;
    }

    @Transactional
    public boolean updateProductAndStock(Product product, Stock stock) {
        boolean success = productService.updateProduct(product);
        boolean success1 = stockService.updateStock(stock);
        return success && success1;
    }

    public boolean deleteProductStocksByProductId(Integer productId) {
        boolean success = productService.deleteProductById(productId);
        //stock在数据库中定义了当外键produce_id不存在时自动删除
        return success;
    }

    //入库,在这里需要利用移动加权平均法来计算商品的平均价格
    @Transactional
    public boolean newStockIn(ProductStockVO productStockVO, Float oldProductUnitPrice, Integer oldStockCount) {
        Stock stock = productStockVO.getStock();
        Product product = productStockVO.getProduct();

        StockInRecord stockInRecord = new StockInRecord();
        stockInRecord.setStockId(stock.getId());
        stockInRecord.setCount(stock.getCountOfPieces());
        stockInRecord.setUnitPrice(product.getUnitPrice());
        boolean success = stockService.increaseStockCount(stock, stockInRecord);
        //计算新的单价
        Float oldMoney = oldProductUnitPrice * oldStockCount;
        Float newMoney = stock.getCountOfPieces() * product.getUnitPrice();
        Integer newCount = oldStockCount + stock.getCountOfPieces();
        Float newUnitPrice = (oldMoney + newMoney) / newCount;
        product.setUnitPrice(newUnitPrice);
        boolean success1 = productService.updateProduct(product);
        return success && success1;
    }
}
