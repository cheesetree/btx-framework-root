package top.cheesetree.btx.framework.database.hikari.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/10/23 10:55
 * @Version: 1.0
 * @Description:
 */
@ConfigurationProperties(prefix = "btx.database")
@Getter
@Setter
public class BtxDataSourceConfigure {
    private String appId;
    private String secretKey;
}
