package top.cheesetree.btx.framework.security.shrio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.cheesetree.btx.framework.security.constants.BtxSecurityEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: van
 * @Date: 2022/1/13 09:03
 * @Description: TODO
 */
@ConfigurationProperties("btx.security.shiro")
@Data
public class BtxSecurityShiroProperties {
    private List<BtxSecurityEnum.AuthType> authTypes = new ArrayList<>();
}
