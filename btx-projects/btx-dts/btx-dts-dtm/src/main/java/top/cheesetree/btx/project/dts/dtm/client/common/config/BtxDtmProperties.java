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

    private int connectTimeOut = 2;

    private int readTimeOut = 10;

    private int writeTimeOut = readTimeOut;

    private int maxPoolIdle = 5;

    private int maxPoolLiveTime = 5;
}
