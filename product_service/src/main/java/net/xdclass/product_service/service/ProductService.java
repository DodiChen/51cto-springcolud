package net.xdclass.product_service.service;

import net.xdclass.product_service.domain.Product;

import java.util.List;

/**
 * @author chendong
 * @date 2019/3/3 22:01
 */
public interface ProductService {
    List<Product> listProduct();

    Product findById(int id);
}
