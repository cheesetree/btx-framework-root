package top.cheesetree.btx.framework.cache.redis;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/9/27 13:57
 * @Version: 1.0
 * @Description:
 */
@Getter
@Setter
public class BtxRedisConfigProperties {
    private Duration timeToLive;

    /**
     * 缓存Key前缀
     */
    private String keyPrefix;

    /**
     * 是否使用KeyPrefix.
     */
    private Boolean useKeyPrefix = true;

    /**
     * 缓存NULL值
     */
    private Boolean cacheNullValues;

    /**
     * evict操作支持, Pattern批量删除
     */
    private Boolean evictAllowPattern;
}
