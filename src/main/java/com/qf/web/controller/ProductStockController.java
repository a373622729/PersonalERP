package com.qf.web.controller;

import com.qf.domain.ProductStockVO;
import com.qf.service.ProductClassService;
import com.qf.service.ProductStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ios on 17/11/9.
 * 从 product_stock_view 视图中获取到商品和库存的关联信息
 */
@Controller
@RequestMapping("productStocks")
public class ProductStockController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductStockService productStockService;
    @Autowired
    private ProductClassService productClassService;

    @RequestMapping(value = "/class/{classId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List> getProductStocks(@PathVariable("classId") Integer productClassId) {
        String            childrenIds = productClassService.findChildrenNodeIds(productClassId).substring(2);
        List<ProductStockVO>     products    = productStockService.findProductStocksByClassIdAndClassChildrenId(childrenIds);
        Map<String, List> map         = new HashMap<>(1);
        map.put("aaData", products);
        return map;
    }
}
