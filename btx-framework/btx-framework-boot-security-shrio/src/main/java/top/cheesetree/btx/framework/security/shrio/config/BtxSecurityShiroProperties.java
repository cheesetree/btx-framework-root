package top.cheesetree.btx.framework.security.shrio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.cheesetree.btx.framework.security.shrio.constants.ShiroEnum;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2022/1/13 09:03
 * @Version: 1.0
 * @Description:
 */
@ConfigurationProperties("btx.security.shiro")
@Data
public class BtxSecurityShiroProperties {
    private ShiroEnum.AuthType authType = ShiroEnum.AuthType.PASSWORD;
}
