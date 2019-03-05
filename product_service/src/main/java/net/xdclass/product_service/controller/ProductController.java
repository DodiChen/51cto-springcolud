package net.xdclass.product_service.controller;

import net.xdclass.product_service.domain.Product;
import net.xdclass.product_service.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chendong
 * @date 2019/3/3 21:58
 */
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Value("${server.port}")
    private String port;

    @Autowired
    public ProductService productService;


    /**
     * 获取所有商品列表
     * @return
     */
    @RequestMapping("/list")
    public Object list(){
        return productService.listProduct();
    }

    /**
     * 根据id获取商品详情
     * @param id
     * @return
     */
    @RequestMapping("/find")
    public Object findById(int id){
        Product product = productService.findById(id);
        Product result = new Product();
        BeanUtils.copyProperties(product, result);
        result.setName(result.getName() + " data from port=" + port);
        return result;
    }
}
