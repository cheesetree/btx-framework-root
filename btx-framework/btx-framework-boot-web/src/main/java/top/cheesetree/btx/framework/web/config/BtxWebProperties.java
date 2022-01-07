package top.cheesetree.btx.framework.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2022/1/6 16:01
 * @Version: 1.0
 * @Description:
 */
@ConfigurationProperties("btx.web")
@Data
public class BtxWebProperties {
    private String dateformat = "yyyy-MM-dd HH:mm:ss";
    private boolean writedateusedateformat = true;
}
