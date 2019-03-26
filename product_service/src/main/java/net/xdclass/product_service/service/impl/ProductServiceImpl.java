package net.xdclass.product_service.service.impl;

import net.xdclass.product_service.domain.Product;
import net.xdclass.product_service.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author chendong
 * @date 2019/3/3 22:02
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static final Map<Integer, Product> daoMap = new HashMap<Integer, Product>();

    private Logger logger = LoggerFactory.getLogger(getClass());

    static{
        Product p1 = new Product(1, "iphonex", 9999, 10);
        Product p2 = new Product(2, "冰箱", 45325, 10);
        Product p3 = new Product(3, "洗衣机", 5, 10);
        Product p4 = new Product(4, "电话", 952315999, 10);
        Product p5 = new Product(5, "汽车", 542, 10);
        Product p6 = new Product(6, "椅子", 95499, 10);
        Product p7 = new Product(7, "java编程思想", 54, 10);

        daoMap.put(1, p1);
        daoMap.put(2, p2);
        daoMap.put(3, p3);
        daoMap.put(4, p4);
        daoMap.put(5, p5);
        daoMap.put(6, p6);
        daoMap.put(7, p7);
    }

    @Override
    public List<Product> listProduct() {
        Collection<Product> collection = daoMap.values();
        List<Product> list = new ArrayList<>(collection);
        return list;
    }

    @Override
    public Product findById(int id) {
        logger.info("service find by id");
        return daoMap.get(id);
    }
}
