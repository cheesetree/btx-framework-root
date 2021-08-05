package top.cheesetree.btx.framework.cache.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/9/27 13:51
 * @Version: 1.0
 * @Description:
 */
@ConfigurationProperties(prefix = "btx.cache.redis")
@Setter
@Getter
public class BtxRedisCacheProperties extends BtxRedisConfigProperties {
    private Duration missedHistoryExpire;

    /**
     * 缓存个性化配置
     */
    private final Map<String, BtxRedisConfigProperties> caches = new ConcurrentHashMap<>();

}
