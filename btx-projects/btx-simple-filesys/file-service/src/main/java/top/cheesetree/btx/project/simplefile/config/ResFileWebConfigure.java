package top.cheesetree.btx.project.simplefile.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.cheesetree.btx.project.simplefile.Interceptor.ApiAuthInterceptor;

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
    public ApiAuthInterceptor apiAuthInterceptor() {
        return new ApiAuthInterceptor();
    }

    @Override
    @ConditionalOnProperty(value = "btx.file.token.enabled", havingValue = "true")
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiAuthInterceptor()).addPathPatterns("/file/**");
    }
}
