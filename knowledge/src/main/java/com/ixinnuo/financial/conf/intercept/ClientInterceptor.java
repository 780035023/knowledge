package com.ixinnuo.financial.conf.intercept;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ixinnuo.financial.framework.SessionKey;

/**
 * 权限拦截器
 * 
 * @author aisino
 */
@Component
public class ClientInterceptor implements HandlerInterceptor {

    /**
     * 无需权限，放行的资源
     */
    private static List<String> list = new ArrayList<String>();

    static {
        //末尾带"/",则只包含下级所有资源；不带"/",包含本级和所有下级
        
        list.add("/");//放行所有
        // 测试
        list.add("/test/redirect");
        //swagger api
        list.add("/swagger-resources");
        // 首页
        list.add("/index/home");
        // 登录
        list.add("/uumsLogin/callback");
        //查询所有产品列表
        list.add("/product/showAllProduct");
        //根据条件查询产品列表
        list.add("/product/showProductByCondition");
        //根据Id查询对应产品
        list.add("/product/showProductById");
    }

    private static final Logger logger = LoggerFactory.getLogger(ClientInterceptor.class);

    private ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
        long beginTime = System.currentTimeMillis();
        startTimeThreadLocal.set(beginTime);
        String servletPath = arg0.getServletPath();
        logger.info(">>>>>>>>>>start在请求{}处理之前进行拦截", servletPath);
        // 无需权限，放行
        for (String redirect : list) {
            if (servletPath.startsWith(redirect)) {
                return true;
            }
        }
        // 需要权限，验证
        Object obj = arg0.getSession().getAttribute(SessionKey.LOGIN_INFO.name());
        if (null == obj) {
            logger.info(">>>>>>>>>>process资源{}需要权限，重定向首页去登录", servletPath);
            arg1.sendRedirect("/html/index.html");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub
        long endTime = System.currentTimeMillis();
        long beginTime = startTimeThreadLocal.get();
        long consumeTime = endTime - beginTime;
        logger.info(">>>>>>>>>>end状态:跳转至{}成功! 耗时  {} ms", arg0.getRequestURI(), consumeTime);
    }

}
