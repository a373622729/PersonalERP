package com.qf.service;

import com.qf.domain.Product;
import com.qf.domain.ProductStockVO;
import com.qf.domain.Stock;
import com.qf.mapper.ProductStockMapper;
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
    private ProductService productService;
    @Autowired
    private StockService stockService;

    public List<ProductStockVO> findProductStocksByClassIdAndClassChildrenId(String classIds) {
        return productStockMapper.findProductStocksByClassIdAndClassChildrenId(classIds);
    }

    @Transactional
    public boolean insertProductAndStock(Product product, Stock stock) {
        product.setNumber(product.getNumber().trim().toUpperCase());
        boolean success = productService.insertProduct(product);
        boolean success1 = stockService.insertStock(stock);
        return success && success1;
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
}
