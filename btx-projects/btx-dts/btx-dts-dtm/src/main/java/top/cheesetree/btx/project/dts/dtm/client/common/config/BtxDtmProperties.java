package top.cheesetree.btx.project.dts.dtm.client.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author van
 * @date 2022/3/1 11:37
 * @description TODO
 */
@ConfigurationProperties("btx.dts.dtm")
@Data
public class BtxDtmProperties {

    private String tccUrl;

    private int tccTimeOut = 10000;
}
