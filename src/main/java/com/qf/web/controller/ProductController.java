package com.qf.web.controller;

import com.qf.domain.Product;
import com.qf.domain.User;
import com.qf.service.ProductClassService;
import com.qf.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ios on 17/11/6.
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
    private ProductService productService;
    @Autowired
    private ProductClassService productClassService;

    @RequestMapping( value = "", method = RequestMethod.GET)
    public String products(Model model) {
        return "products/products";
    }

    @RequestMapping(value = "/class/{classId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List> getProducts(@PathVariable Integer classId) {
        String childrenIds = productClassService.findChildrenNodeIds(classId).substring(2);
        List<Product> products = productService.findProductsByClassIdAndClassChildrenId(childrenIds);
        Map<String, List> map = new HashMap<>(1);
        map.put("aaData", products);
        return map;
    }
}
