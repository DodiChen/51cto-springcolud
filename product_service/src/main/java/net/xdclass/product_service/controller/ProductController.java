package net.xdclass.product_service.controller;

import net.xdclass.product_service.domain.Product;
import net.xdclass.product_service.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author chendong
 * @date 2019/3/3 21:58
 */
@RestController
@RefreshScope
@RequestMapping("/api/v1/product")
public class ProductController {

    @Value("${server.port}")
    private String port;

    @Value("${env}")
    private String env;

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
        /*try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        Product product = productService.findById(id);
        Product result = new Product();
        BeanUtils.copyProperties(product, result);
        result.setName(result.getName() + " data from port=" + port + " env = " + env);
        return result;
    }
}
