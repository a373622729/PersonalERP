package com.qf.web.controller;

import com.qf.domain.Product;
import com.qf.domain.ProductStockVO;
import com.qf.domain.Stock;
import com.qf.domain.User;
import com.qf.service.ProductClassService;
import com.qf.service.ProductService;
import com.qf.service.ProductStockService;
import com.qf.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ios on 17/11/9.
 * 从 product_stock_view 视图中获取到商品和库存的关联信息
 */
@Controller
@RequestMapping("productStocks")
@SessionAttributes("user")
public class ProductStockController {

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
    private ProductStockService productStockService;
    @Autowired
    private ProductClassService productClassService;
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/class/{classId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List> getProductStocks(@PathVariable("classId") Integer productClassId) {
        String            childrenIds = productClassService.findChildrenNodeIds(productClassId).substring(2);
        List<ProductStockVO>     products    = productStockService.findProductStocksByClassIdAndClassChildrenId(childrenIds);
        Map<String, List> map         = new HashMap<>(1);
        map.put("aaData", products);
        return map;
    }

    /**
     * 增加新的商品信息
     * @param productClassId 属于哪类
     * @param productStock   product 和 stock
     * @param productImage 上传的图片
     * @return 成功与否
     */
    @RequestMapping(value = "/class/{classId}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Boolean> addProductStocks(@PathVariable("classId") Integer productClassId, ProductStockVO productStock, MultipartFile productImage) {
        Map<String, Boolean> result = new HashMap<>();
        Product product = productStock.getProduct();
        String imageName = null;
        try {
            imageName = saveImage(productImage, product.getNumber());
        } catch (Exception e) {
           result.put("saveImage", false);
        }
        if (imageName != null) {
            product.setImagePath(imageName);
        }
        product.setTypeId(productClassId);
        product.setUserId(authContext.get().getId());
        Stock stock = productStock.getStock();

        boolean success = productStockService.insertProductAndStock(product, stock);

        result.put("insertProductStock", success);
        return result;
    }

    private String saveImage(MultipartFile productImage, String newImageName) throws Exception {
        if (productImage == null) return null;
        newImageName = newImageName.trim().toUpperCase();
        String fileName = productImage.getOriginalFilename();
        newImageName += fileName.substring(fileName.lastIndexOf("."));
        String productFilePath = "src/main/resources/static/productImages/";
        FileUtil.uploadFile(productImage.getBytes(), productFilePath, newImageName);
        return newImageName;
    }

    /**
     * 更新商品和库存信息
     * @param productId 需要更新的商品id
     * @param productStock 需要更新的商品和库存信息
     * @param productImage 更改的图片
     * @return 是否成功
     */
    @RequestMapping(value = "/product/{productId}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Boolean> updateProductStocks(@PathVariable("productId") Integer productId, ProductStockVO productStock, MultipartFile productImage) {
        Map<String, Boolean> result = new HashMap<>();
        Product product = productStock.getProduct();
        String imageName = null;
        try {
            imageName = saveImage(productImage, product.getNumber());
        } catch (Exception e) {
            result.put("saveImage", false);
        }
        if (imageName != null) {
            product.setImagePath(imageName);
        }
//        product.setTypeId(productClassId);
//        product.setUserId(authContext.get().getId());
        Stock stock = productStock.getStock();

        boolean success = productStockService.updateProductAndStock(product, stock);

        result.put("updateProductStock", success);
        return result;
    }

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Boolean> deleteProductStocks(@PathVariable("productId") Integer productId) {
        Map<String, Boolean> result = new HashMap<>(1);
        boolean success = productStockService.deleteProductStocksByProductId(productId);
        logger.info("delete product id =" + productId + " success.");
        result.put("success", success);
        return result;
    }
}
