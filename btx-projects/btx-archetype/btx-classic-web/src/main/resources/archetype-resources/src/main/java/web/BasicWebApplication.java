#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' )
        package ${package}.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/9/15 13:57
 * @Version: 1.0
 * @Description:
 */
@SpringBootApplication
@ComponentScans({
        @ComponentScan(value = {"top.cheesetree.btx", "cn.jx"})
})
@EnableTransactionManagement
@EnableScheduling
public class BasicWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasicWebApplication.class, args);
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return (factory -> {
            ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/pages/index.html");
            factory.addErrorPages(errorPage404);
        });

    }


}
