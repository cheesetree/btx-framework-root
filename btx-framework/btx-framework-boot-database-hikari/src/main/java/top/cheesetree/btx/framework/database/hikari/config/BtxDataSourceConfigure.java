package top.cheesetree.btx.framework.database.hikari.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: van
 * @Date: 2020/10/23 10:55
 * @Description: TODO
 */
@ConfigurationProperties(prefix = "btx.database")
@Getter
@Setter
public class BtxDataSourceConfigure {
    private String appId;
    private String secretKey;
}
