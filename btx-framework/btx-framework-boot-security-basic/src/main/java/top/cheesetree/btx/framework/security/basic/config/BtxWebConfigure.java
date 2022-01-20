package top.cheesetree.btx.framework.security.basic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.cheesetree.btx.framework.security.basic.interceptor.BtxLoginInterceptor;
import top.cheesetree.btx.framework.security.config.BtxSecurityProperties;

/**
 * @Author: van
 * @Date: 2021/8/27 16:31
 * @Description: TODO
 */
@Configuration
public class BtxWebConfigure implements WebMvcConfigurer {
    @Autowired
    private BtxLoginInterceptor loginInterceptor;
    @Autowired
    BtxSecurityProperties btxMVCProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration ir = registry.addInterceptor(loginInterceptor);
        ir.addPathPatterns("/**");
        ir.excludePathPatterns(btxMVCProperties.getContextInterceptorExcludePathPatterns());
    }
}
