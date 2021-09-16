package top.cheesetree.btx.project.simplefile.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
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

    @Bean
    public FilterRegistrationBean corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        //开放哪些ip、端口、域名的访问权限，星号表示开放所有域
        config.addAllowedOrigin("*");
        //开放哪些Http方法，允许跨域访问
        config.addAllowedMethod("POST");
        //允许HTTP请求中的携带哪些Header信息
        config.addAllowedHeader("*");
        config.setMaxAge(1800L);

        //添加映射路径，“/**”表示对所有的路径实行全局跨域访问权限的设置
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/file/*/upload/**", config);

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(configSource));
        bean.setOrder(0);
        return bean;
    }
}
