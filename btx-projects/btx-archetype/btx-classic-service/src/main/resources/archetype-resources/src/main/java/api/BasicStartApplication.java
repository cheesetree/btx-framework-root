#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\' )
package ${package}.web;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/11/10 16:28
 * @Version: 1.0
 * @Description:
 */
@Component
public class BasicStartApplication extends SpringBootServletInitializer implements ErrorPageRegistrar {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类

        return builder.sources(BasicServiceApplication.class);
    }


    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage[] errorPages = new ErrorPage[1];
        errorPages[0] = new ErrorPage(HttpStatus.NOT_FOUND, "/pages/index.html");

        registry.addErrorPages(errorPages);
    }
}
