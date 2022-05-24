package top.cheesetree.btx.project.nacos.config;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @author van
 * @date 2022/5/23 10:08
 * @description TODO
 */
@ConditionalOnProperty(value = "nacos.config.bootstrap.enabled", havingValue = "true")
@NacosPropertySource(dataId = "${nacos.config.data-id}", autoRefreshed = true)
@Configuration
public class NacosConfigure {
}
