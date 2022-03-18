package top.cheesetree.btx.framework.security.shiro.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.cheesetree.btx.framework.security.constants.BtxSecurityEnum;
import top.cheesetree.btx.framework.security.shiro.constants.BtxSecurityShiroConst;

/**
 * @Author: van
 * @Date: 2022/1/13 09:03
 * @Description: TODO
 */
@ConfigurationProperties("btx.security.shiro")
@Getter
@Setter
public class BtxShiroProperties {
    private int sessionTimeOut = 1800;
    private BtxSecurityEnum.AuthType authType = BtxSecurityEnum.AuthType.SESSION;
    private String tokenKey = BtxSecurityShiroConst.AUTHORIZATION_KEY;
    private boolean autoPermission = false;
}
