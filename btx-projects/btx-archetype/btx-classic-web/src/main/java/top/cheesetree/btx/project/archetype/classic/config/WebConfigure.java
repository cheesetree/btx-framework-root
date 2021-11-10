package top.cheesetree.btx.project.archetype.classic.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import top.cheesetree.btx.framework.security.basic.config.BtxWebConfigure;


@Configuration
public class WebConfigure extends BtxWebConfigure {

    @Bean
    @ConditionalOnProperty(name = "btx.dev", havingValue = "true")
    public FilterRegistrationBean corsFilter() {

        CorsConfiguration config = new CorsConfiguration();
        //开放哪些ip、端口、域名的访问权限，星号表示开放所有域
        config.addAllowedOrigin("*");
        //是否允许发送Cookie信息
        config.setAllowCredentials(true);
        //开放哪些Http方法，允许跨域访问
        config.addAllowedMethod("*");
        //允许HTTP请求中的携带哪些Header信息
        config.addAllowedHeader("*");
        config.setMaxAge(1800L);

        //添加映射路径，“/**”表示对所有的路径实行全局跨域访问权限的设置
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(configSource));
        bean.setOrder(0);
        return bean;
    }

}