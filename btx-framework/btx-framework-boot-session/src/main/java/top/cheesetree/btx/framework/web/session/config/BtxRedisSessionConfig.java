package top.cheesetree.btx.framework.web.session.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

/**
 * @Author: van
 * @Date: 2021/4/2 15:01
 * @Description: TODO
 */
@Configuration
public class BtxRedisSessionConfig extends RedisHttpSessionConfiguration {
    public BtxRedisSessionConfig() {
        super();
        BtxSessionConfigProperties c = new BtxSessionConfigProperties();
        super.setRedisNamespace(c.getNamespace());
        super.setMaxInactiveIntervalInSeconds((int) c.getTimeout().getSeconds());
    }
}
