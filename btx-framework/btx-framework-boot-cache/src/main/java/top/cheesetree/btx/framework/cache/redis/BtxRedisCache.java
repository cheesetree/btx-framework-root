package top.cheesetree.btx.framework.cache.redis;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/8/25 14:56
 * @Version: 1.0
 * @Description:
 */
public class BtxRedisCache extends RedisCache {

    protected BtxRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig) {
        super(name, cacheWriter, cacheConfig);
    }
}
