package net.xdclass.order_service.service;

import net.xdclass.order_service.domain.ProductOrder;

/**
 * @author chendong
 * @date 2019/3/5 22:32
 */
public interface ProductOrderService {

    /**
     * ribbon下单接口
     * @param userId
     * @param productId
     * @return
     */
    ProductOrder save(int userId, int productId);

    /**
     * ribbon下单接口
     * @param userId
     * @param productId
     * @return
     */
    ProductOrder save2(int userId, int productId);

    /**
     * feign下单接口
     * @param userId
     * @param productId
     * @return
     */
    ProductOrder save3(int userId, int productId);

}
