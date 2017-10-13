package com.qf.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ios on 17/10/12.
 */
@Controller
@RequestMapping("/")
public class HellordWordController {

    @RequestMapping(value = "/helloWord", method = RequestMethod.GET)
    public String helloWord(Model model) {
        model.addAttribute("usera", "qf122");
        return "helloWord";
    }
}
