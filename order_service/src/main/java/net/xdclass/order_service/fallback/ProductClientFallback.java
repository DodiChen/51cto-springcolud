package net.xdclass.order_service.fallback;

import net.xdclass.order_service.service.ProductClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 针对商品服务做降级处理
 */
@Service
public class ProductClientFallback implements ProductClient {

    @Override
    public String findById(int id) {
        // 可以做日志，也可以返回兜底数据
        System.out.println("feign 调用product-service findById异常");
        return null;
    }
}
