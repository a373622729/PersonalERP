package com.qf.web.controller;

import com.qf.domain.ProductClassVO;
import com.qf.domain.User;
import com.qf.service.ProductClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

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
        List<ProductClassVO> productClassVOs = productClassService.findAllProductClass(authContext.get().getId());
        model.addAttribute("productClassVOs", productClassVOs);
        return "products/products";
    }
}
