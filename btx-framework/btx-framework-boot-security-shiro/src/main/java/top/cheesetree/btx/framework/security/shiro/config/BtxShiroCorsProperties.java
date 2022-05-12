package top.cheesetree.btx.framework.security.shiro.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author van
 * @date 2022/4/28 09:20
 * @description TODO
 */
@ConfigurationProperties("btx.security.shiro.cors")
@Getter
@Setter
public class BtxShiroCorsProperties {
    private Boolean enabled = false;
    private List<String> origins = new ArrayList<>();
    private String[] allowHeaders = new String[0];
    private String[] methods = new String[0];
    private String[] exposedHeaders = new String[0];
    String allowCredentials = "true";
    long maxAge = -1;

}
