package top.cheesetree.btx.project.dts.dtm.client.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.cheesetree.btx.project.dts.dtm.client.client.DtmClient;

/**
 * @author van
 * @date 2022/3/1 11:37
 * @description TODO
 */
@EnableConfigurationProperties({BtxDtmProperties.class})
@Configuration
@MapperScan({"top.cheesetree.btx.project.dts.dtm.client.**.mapper"})
public class BtxDtmConfigure {
    @Autowired
    BtxDtmProperties btxDtmProperties;

    @Bean
    public DtmClient dtmClient() {
        return new DtmClient(btxDtmProperties);
    }
}
