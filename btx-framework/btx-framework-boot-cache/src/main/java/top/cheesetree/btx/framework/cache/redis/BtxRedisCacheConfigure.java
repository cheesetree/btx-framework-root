package top.cheesetree.btx.framework.cache.redis;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import top.cheesetree.btx.framework.cache.annotation.BtxCacheable;

import java.util.HashMap;
import java.util.Map;

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
public class BtxRedisCacheConfigure extends CachingConfigurerSupport implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {
    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private CacheProperties cacheProperties;
    @Autowired
    private BtxRedisConfigProperties btxRedisConfigProperties;
    @Autowired
    private BtxRedisCacheProperties btxRedisCacheProperties;
    private ApplicationContext applicationContext;

    @Override
    @Bean
    public CacheManager cacheManager() {
        BtxRedisConfigProperties defaultWssipCacheConfig = btxRedisConfigProperties;

        defaultWssipCacheConfig.setDefaultValues(cacheProperties.getRedis());

        RedisCacheConfiguration defaultCacheConfig =
                BtxRedisCacheManager.createRedisCacheConfiguration(defaultWssipCacheConfig);

        // 针对不同cacheName，设置不同的过期时间
        Map<String, RedisCacheConfiguration> initialCacheConfiguration =
                new HashMap<>(btxRedisCacheProperties.getCaches().size());
        for (String cacheName : btxRedisCacheProperties.getCaches().keySet()) {

            BtxRedisConfigProperties wssipCacheConfig = btxRedisCacheProperties.getCaches().get(cacheName);

            wssipCacheConfig.setDefaultValues(defaultWssipCacheConfig);

            RedisCacheConfiguration cacheConfig =
                    BtxRedisCacheManager.createRedisCacheConfiguration(wssipCacheConfig);

            initialCacheConfiguration.put(cacheName, cacheConfig);
        }

        return new BtxRedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                defaultCacheConfig, initialCacheConfiguration);
    }

    @Bean(name = "redisTemplateFactory")
    public RedisTemplateFactoryImpl redisTemplateFactory() {
        return new RedisTemplateFactoryImpl();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Map<String, Object> beanWhithAnnotation = applicationContext.getBeansWithAnnotation(BtxCacheable.class);
        beanWhithAnnotation.forEach((k, v) -> {
            BtxCacheable an = v.getClass().getAnnotation(BtxCacheable.class);

            BtxRedisConfigProperties c = new BtxRedisConfigProperties();
            c.setKeyPrefix(an.key());

            BtxRedisCacheManager.configMap.put(an.key(), c);
        });
    }
}
