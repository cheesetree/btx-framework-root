package top.cheesetree.btx.project.simplefile.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.cheesetree.btx.project.simplefile.ApiAuthInterceptor;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/12/22 17:05
 * @Version: 1.0
 * @Description:
 */
@Configuration
public class ResFileWebConfigure implements WebMvcConfigurer {

    @Bean
    public ApiAuthInterceptor ApiAuthInterceptor() {
        return new ApiAuthInterceptor();
    }

    @Override
    @ConditionalOnProperty(value = "btx.res.token.enabled")
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ApiAuthInterceptor()).addPathPatterns("/file/**");
    }
}
