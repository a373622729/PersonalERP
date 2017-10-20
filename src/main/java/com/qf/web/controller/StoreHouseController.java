package com.qf.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ios on 17/10/13.
 */
@Controller
@RequestMapping("/storeHouse")
public class StoreHouseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String storeHouse() {
        return "storeHouse/storeHouse";
    }
}
