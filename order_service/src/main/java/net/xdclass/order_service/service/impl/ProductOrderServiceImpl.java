package net.xdclass.order_service.service.impl;

import net.xdclass.order_service.domain.ProductOrder;
import net.xdclass.order_service.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author chendong
 * @date 2019/3/5 22:33
 */
@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public ProductOrder save(int userId, int productId) {

        // 获取商品详情
        Map<String, Object> productMap = restTemplate.getForObject(
                "http://product-service/api/v1/product/find?id=" + productId,
                Map.class
        );


        ProductOrder productOrder = new ProductOrder();
        productOrder.setCreateTime(new Date());
        productOrder.setUserId(userId);
        productOrder.setTradeNo(UUID.randomUUID().toString());
        productOrder.setProductName(productMap.get("name").toString());
        productOrder.setPrice(Integer.parseInt(productMap.get("price").toString()));
        return productOrder;
    }

    @Override
    public ProductOrder save2(int userId, int productId) {

        // 获取商品详情

        ServiceInstance instance = loadBalancerClient.choose("product-service");
        String url = String.format("http://%s:%s/api/v1/product/find?id="+productId, instance.getHost(), instance.getPort());
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> productMap = restTemplate.getForObject(
                url,
                Map.class
        );


        ProductOrder productOrder = new ProductOrder();
        productOrder.setCreateTime(new Date());
        productOrder.setUserId(userId);
        productOrder.setTradeNo(UUID.randomUUID().toString());
        productOrder.setProductName(productMap.get("name").toString());
        productOrder.setPrice(Integer.parseInt(productMap.get("price").toString()));
        return productOrder;
    }
}
