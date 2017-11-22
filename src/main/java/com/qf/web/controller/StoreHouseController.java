package com.qf.web.controller;


import com.qf.domain.Stock;
import com.qf.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ios on 17/10/13.
 */
@Controller
@RequestMapping("/storeHouse")
public class StoreHouseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StockService stockService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String storeHouse() {
        return "storeHouse/storeHouse";
    }


    @RequestMapping(value = "/stocks/{stockId}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Boolean> updateStock(@PathVariable("stockId") Integer stockId, Stock stock) {
        Map<String, Boolean> result = new HashMap<>(1);
        boolean success = stockService.increaseStockCount(stock);
        result.put("success", success);
        return result;
    }
}
