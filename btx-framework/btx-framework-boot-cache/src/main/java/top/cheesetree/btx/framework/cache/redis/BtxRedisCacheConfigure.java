package top.cheesetree.btx.framework.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/9/27 14:07
 * @Version: 1.0
 * @Description:
 */

@ConditionalOnProperty(name = "spring.redis.host")
@EnableCaching(proxyTargetClass = true)
@Configuration
@EnableConfigurationProperties({BtxRedisCacheProperties.class, CacheProperties.class})
@Import(DefaultListableBeanFactory.class)
public class BtxRedisCacheConfigure extends CachingConfigurerSupport {
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private CacheProperties cacheProperties;

    @Autowired
    private BtxRedisCacheProperties btxRedisCacheProperties;

    @Override
    @Bean
    public CacheManager cacheManager() {
//        BtxRedisCacheProperties defaultBtxCacheConfig = btxRedisCacheProperties;
//
//        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
//
//        // 针对不同cacheName，设置不同的过期时间
//        Map<String, RedisCacheConfiguration> initialCacheConfiguration =
//                new HashMap<String, RedisCacheConfiguration>(btxRedisCacheProperties.getCaches().size());
//        for (String cacheName : btxRedisCacheProperties.getCaches().keySet()) {
//
//            BtxRedisConfigProperties btxCacheConfig = btxRedisCacheProperties.getCaches().get(cacheName);
//
//            btxCacheConfig.setDefaultValues(defaultBtxCacheConfig);
//
//            RedisCacheConfiguration cacheConfig =
//                    BtxRedisCacheManager.createRedisCacheConfiguration(wssipCacheConfig);
//
//            initialCacheConfiguration.put(cacheName, cacheConfig);
//        }
//
//        BtxRedisCacheManager redisCacheManager =
//                new BtxRedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
//                        defaultCacheConfig, initialCacheConfiguration);
//        return redisCacheManager;
        return null;
    }


}
