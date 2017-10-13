package com.qf.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ios on 17/10/13.
 */
@Controller
@RequestMapping("/storeHouse")
public class StoreHouseController {

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String storeHouse() {
        return "storeHouse/storeHouse";
    }
}
