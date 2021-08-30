package top.cheesetree.btx.framework.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/8/27 10:00
 * @Version: 1.0
 * @Description:
 */
@Configuration
public class BtxWebMvcConfiguration implements WebMvcConfigurer {
    @Bean
    @ConditionalOnClass(name = {"org.springframework.cloud.openfeign.FeignClient"})
    public WebMvcRegistrations webMvcRegistrations() {
        return new WebMvcRegistrations() {
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return new RequestMappingHandlerMapping() {
                    @Override
                    protected boolean isHandler(Class<?> beanType) {
                        // return super.isHandler(beanType) &&
                        //								!AnnotatedElementUtils.hasAnnotation(beanType,
                        //										org.springframework.cloud.openfeign.FeignClient.class)
                        return super.isHandler(beanType);
                    }
                };
            }

        };
    }
}
