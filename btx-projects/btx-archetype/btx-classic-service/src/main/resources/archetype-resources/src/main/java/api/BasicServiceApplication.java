#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
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
        @ComponentScan(value = {"top.cheesetree.btx","cn.jx"})
})
@EnableTransactionManagement
@EnableScheduling
public class BasicServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasicServiceApplication.class, args);
    }


}
