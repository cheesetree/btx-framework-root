package top.cheesetree.btx.framework.security.shiro.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.cheesetree.btx.framework.security.shiro.constants.BtxSecurityShiroEnum;

/**
 * @Author: van
 * @Date: 2022/1/13 09:03
 * @Description: TODO
 */
@ConfigurationProperties("btx.security.shiro.cache")
@Getter
@Setter
public class BtxShiroCacheProperties {
    private boolean enabled = true;
    private BtxSecurityShiroEnum.CACHE_TYPE cacheType = BtxSecurityShiroEnum.CACHE_TYPE.INNER;
}
