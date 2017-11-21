package com.qf.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.qf.domain.ProductClassVO;
import com.qf.domain.User;
import com.qf.service.ProductClassService;
import com.qf.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by ios on 17/10/24.
 */
@Controller
@RequestMapping("/products")
@SessionAttributes("user")
public class ProductClassController {

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
    @Autowired
    private ProductService productService;



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
        if (name == null || name.isEmpty()) return "{\"success\" : \"false\"}";
        boolean success =  productClassService.addProductClass(pid, name, authContext.get().getId());
        return "{\"success\" : \""+ success +"\"}";
    }

    @RequestMapping(value = "/class/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String updateProductClass(@PathVariable Integer id, String name) {
        if (name == null || name.isEmpty()) return "{\"success\" : \"false\"}";
        boolean success = productClassService.updateProductClass(id, name, authContext.get().getId());
        return "{\"success\" : \""+ success +"\"}";
    }

    @RequestMapping(value = "/class/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteProductClass(@PathVariable Integer id) {
        String childrenIds = productClassService.findChildrenNodeIds(id).substring(2);
        int productCountThisId = productService.findCountByTypeIds(childrenIds);
        if (productCountThisId > 0) {
            return "{\"success\" : \"false\"}";
        }
        boolean success = productClassService.deleteProductClass(childrenIds);
        return "{\"success\" : \""+ success +"\"}";
    }
}
