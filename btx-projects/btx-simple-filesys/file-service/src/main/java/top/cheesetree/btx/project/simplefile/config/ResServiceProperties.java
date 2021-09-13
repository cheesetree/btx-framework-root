package top.cheesetree.btx.project.simplefile.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/12/22 14:54
 * @Version: 1.0
 * @Description:
 */
@ConfigurationProperties(value = "btx.file")
@Data
public class ResServiceProperties {
    private String gatewayUrl;
    private String shareUrl;
    private boolean storageLimit;

}
