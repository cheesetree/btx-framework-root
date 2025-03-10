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
@ConfigurationProperties("btx.security.shiro.csrf")
@Getter
@Setter
public class BtxShiroCsrfProperties {
    private Boolean enabled = false;
    private List<String> domains = new ArrayList<>();

}
