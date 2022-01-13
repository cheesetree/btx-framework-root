package top.cheesetree.btx.framework.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/8/27 13:58
 * @Version: 1.0
 * @Description:
 */
@ConfigurationProperties(prefix = "btx.security")
@Getter
@Setter
public class BtxSecurityProperties {
    private String[] contextInterceptorExcludePathPatterns;
    private String errorPath;
    private String loginPath;
    private String noAuthPath;
}
