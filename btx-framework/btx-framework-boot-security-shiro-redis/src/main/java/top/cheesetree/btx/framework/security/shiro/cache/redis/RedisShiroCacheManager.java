package top.cheesetree.btx.framework.security.shiro.cache.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import top.cheesetree.btx.framework.cache.redis.RedisTemplateFactoryImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author van
 * @date 2022/2/15 17:25
 * @description TODO
 */
public class RedisShiroCacheManager implements CacheManager{
    private RedisTemplateFactoryImpl redisTemplateFactory;
    public int expire = 1800;

    public RedisShiroCacheManager(RedisTemplateFactoryImpl redisTemplateFactory, int expire) {
        this.redisTemplateFactory = redisTemplateFactory;
        this.expire = expire;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        Type type = this.getClass().getGenericSuperclass();
        Class z = Object.class;
        if (type instanceof ParameterizedType) {
            z = (Class<V>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }

        return new RedisShiroCache<>(redisTemplateFactory.generateRedisTemplate(String.class,
                z), name
                , expire);
    }
}
