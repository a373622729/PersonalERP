package com.qf.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.qf.domain.ProductClassVO;
import com.qf.domain.User;
import com.qf.service.ProductClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by ios on 17/10/24.
 */
@Controller
@RequestMapping("/products")
@SessionAttributes("user")
public class ProductController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    ThreadLocal<User> authContext = new ThreadLocal<>();

    @ModelAttribute
    public void initUser(HttpSession session) {
        Object obj = session.getAttribute("user");
        if(obj instanceof User) {
            User user = (User) obj;
            authContext.set(user);
        }
    }

    @Autowired
    private ProductClassService productClassService;

    @RequestMapping( value = "", method = RequestMethod.GET)
    public String products(Model model) {

        return "products/products";
    }

    @RequestMapping(value = "/class", method = RequestMethod.GET)
    @ResponseBody
    public String getProductClass(){
        List<ProductClassVO> productClassVOs = productClassService.findAllProductClass(authContext.get().getId());
        ProductClassVO first = new ProductClassVO();
        first.setName("所有");
        first.setId(0);
        productClassVOs.add(0, first);
        String jsonString = JSONArray.toJSONString(productClassVOs);
        jsonString = jsonString.replaceAll("name", "text")
                .replaceAll("productClassChildrenList", "nodes")
                .replace(",\"nodes\":[]", "");
        return jsonString;
    }

    @RequestMapping(value = "/class/{pid}", method = RequestMethod.POST)
    @ResponseBody
    public String addProductClass(@PathVariable("pid") Integer pid, String name) {
        boolean success =  productClassService.addProductClass(pid, name, authContext.get().getId());
        return "{\"success\" : \""+ success +"\"}";
    }
}
