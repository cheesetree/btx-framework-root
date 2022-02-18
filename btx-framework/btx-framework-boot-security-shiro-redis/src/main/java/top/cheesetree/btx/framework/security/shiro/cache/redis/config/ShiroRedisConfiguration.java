package top.cheesetree.btx.framework.security.shiro.cache.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.cheesetree.btx.framework.cache.redis.RedisTemplateFactoryImpl;
import top.cheesetree.btx.framework.security.shiro.cache.redis.RedisShiroCacheManager;
import top.cheesetree.btx.framework.security.shiro.config.BtxShiroCacheProperties;
import top.cheesetree.btx.framework.security.shiro.config.BtxShiroProperties;

/**
 * @Author: van
 * @Date: 2022/1/12 13:25
 * @Description: TODO
 */
@Configuration
@Slf4j
public class ShiroRedisConfiguration {
    @Autowired
    BtxShiroProperties btxShiroProperties;
    @Autowired
    BtxShiroCacheProperties btxShiroCacheProperties;

    @Bean
    @ConditionalOnProperty(value = "btx.security.shiro.cache.cache-type", havingValue = "REDIS")
    public CacheManager shiroCacheManager() {
        return new RedisShiroCacheManager(redisTemplateFactory(), btxShiroCacheProperties.getCacheExpire() == null ?
                btxShiroProperties.getSessionTimeOut() : btxShiroCacheProperties.getCacheExpire());
    }

    @Bean
    @ConditionalOnMissingBean({RedisTemplateFactoryImpl.class})
    public RedisTemplateFactoryImpl redisTemplateFactory() {
        return new RedisTemplateFactoryImpl();
    }

}
