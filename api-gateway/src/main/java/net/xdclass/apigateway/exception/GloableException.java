package net.xdclass.apigateway.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chendong
 * @date 2019/3/25 21:29
 */

@ControllerAdvice
public class GloableException {
    @ResponseBody
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Map errorHandler(Exception ex) {
        Map map = new HashMap();
        map.put("code", 100);
        map.put("msg", ex.getMessage());
        return map;
    }

}
