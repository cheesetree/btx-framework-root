package top.cheesetree.btx.framework.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: van
 * @Date: 2021/8/27 13:58
 * @Description: TODO
 */
@ConfigurationProperties(prefix = "btx.security")
@Getter
@Setter
public class BtxSecurityProperties {
    private String[] contextInterceptorExcludePathPatterns = new String[1];
    private String errorPath = "";
    private String loginPath = "";
    private String noAuthPath = "";
    private String expirePath = noAuthPath;
}
