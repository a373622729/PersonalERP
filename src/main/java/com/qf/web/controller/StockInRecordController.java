package com.qf.web.controller;


import com.qf.domain.ProductStockInRecordVO;
import com.qf.service.StockInRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ios on 17/10/13.
 * 入库记录
 */
@Controller
@RequestMapping("/stockInRecord")
public class StockInRecordController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StockInRecordService stockInRecordService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String storeHouse() {
        return "stockInRecord/stockInRecord";
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List> getStockInRecord(String s, String e) {
        Timestamp start, end;
        if (e == null) {
            end = Timestamp.valueOf(LocalDateTime.now());
        } else {
            end = Timestamp.valueOf(e + " 23:59:59");
        }
        if (s == null) {
            start = Timestamp.valueOf(end.toLocalDateTime().minusMonths(1));
        } else {
            start = Timestamp.valueOf(s + " 00:00:00");
        }

        List<ProductStockInRecordVO> list = stockInRecordService.getStockInRecordBetween(start, end);
        Map<String, List>            map  = new HashMap<>(1);
        map.put("aaData", list);
        return map;
    }
}
