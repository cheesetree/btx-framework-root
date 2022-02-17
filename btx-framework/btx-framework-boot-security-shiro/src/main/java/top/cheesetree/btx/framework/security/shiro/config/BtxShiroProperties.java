package top.cheesetree.btx.framework.security.shiro.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: van
 * @Date: 2022/1/13 09:03
 * @Description: TODO
 */
@ConfigurationProperties("btx.security.shiro")
@Getter
@Setter
public class BtxShiroProperties {
    private String authenticationCacheName = "authenticationCache";
    private String authorizationCacheName = "authorizationCache";
    private int sessionTimeOut = 3600;
}
