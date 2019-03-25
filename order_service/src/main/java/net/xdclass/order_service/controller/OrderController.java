package net.xdclass.order_service.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import net.xdclass.order_service.service.ProductOrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author chendong
 * @date 2019/3/5 22:31
 */
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/save")
    @HystrixCommand(fallbackMethod = "saveOrderFail")
    public Object save(@RequestParam("user_id") int userId, @RequestParam("product_id") int productId, HttpServletRequest request){

        String token = request.getHeader("token");
        System.out.println("token=" + token);
        String cookie = request.getHeader("Cookie");
        System.out.println("cookie=" + cookie);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("code", "0");
        Object obj = productOrderService.save3(userId,productId);
        data.put("data", obj);
        return data;
    }

    private Object saveOrderFail(int userId, int productId, HttpServletRequest request){

        String saveOrderKey = "save-order";
        String sendValue = (String) redisTemplate.opsForValue().get(saveOrderKey);
        String ip = request.getRemoteUser();
        // 避免阻塞
        new Thread(() -> {
            if(StringUtils.isEmpty(sendValue)){
                System.out.println("紧急短信，用户下单失败，请立即查找原因, ip地址是——" + ip);
                // TODO 短信请求，调用短信服务

                redisTemplate.opsForValue().set(saveOrderKey, "save-order-fail", 20, TimeUnit.MINUTES);
            }else{
                System.out.println("已经发送过短信， 20s内不重复发送");
            }
        }).start();

        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("code", "-1");
        msg.put("msg", "抢购人数太多，您被挤出，请稍后重试");
        return msg;
    }
}
