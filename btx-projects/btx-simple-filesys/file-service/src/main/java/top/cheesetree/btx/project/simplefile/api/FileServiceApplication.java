package top.cheesetree.btx.project.simplefile.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import top.cheesetree.btx.project.simplefile.config.ResServiceProperties;

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
        @ComponentScan(value = {"top.cheesetree.btx"})
})
@EnableTransactionManagement
@EnableConfigurationProperties({ResServiceProperties.class})
public class FileServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileServiceApplication.class, args);
    }


}
