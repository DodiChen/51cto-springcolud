package net.xdclass.apigateway.filter;

import com.netflix.discovery.util.StringUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chendong
 * @date 2019/3/25 20:52
 */
@Component
public class LoginFilter extends ZuulFilter {

    /**
     * 过滤器类型，前置过滤器
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 过滤器的顺序，越小，越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 4;
    }

    /**
     * 过滤器是否生效
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        System.out.println(request.getRequestURI());
        System.out.println(request.getRequestURL());

        if("/apigateway/api/v1/order/save".equalsIgnoreCase(request.getRequestURI())){
            return true;
        }
        return false;
    }

    /**
     * 业务逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String token = request.getHeader("token");
        if(StringUtils.isBlank(token)){
            token = request.getParameter("token");
        }
        // 登陆校验逻辑，自定义
        if(StringUtils.isBlank(token)){
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            requestContext.setResponseBody(token);
        }
        return null;
    }
}
