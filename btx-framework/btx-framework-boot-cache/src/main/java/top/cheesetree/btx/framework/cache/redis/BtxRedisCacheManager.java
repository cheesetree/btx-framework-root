package top.cheesetree.btx.framework.cache.redis;

import top.cheesetree.btx.framework.cache.BtxCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.util.Map;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/9/27 13:37
 * @Version: 1.0
 * @Description:
 */
@Slf4j
public class BtxRedisCacheManager extends RedisCacheManager implements BtxCacheManager {
    private final RedisCacheWriter cacheWriter;

    public BtxRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
                                Map<String, RedisCacheConfiguration> initialCacheConfigurations,
                                boolean allowInFlightCacheCreation) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
        this.cacheWriter = cacheWriter;
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        return super.createRedisCache(name, cacheConfig);
    }

}
