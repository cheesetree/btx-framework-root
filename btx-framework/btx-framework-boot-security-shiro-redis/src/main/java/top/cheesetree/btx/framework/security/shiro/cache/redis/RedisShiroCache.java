package top.cheesetree.btx.framework.security.shiro.cache.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author van
 * @date 2022/2/15 17:22
 * @description TODO
 */
public class RedisShiroCache<K, V> implements Cache<K, V> {
    private RedisTemplate<String, V> redisTemplate;
    private String prefix;
    private int expire;

    public RedisShiroCache(RedisTemplate<String, V> redisTemplate, String prefix, int expire) {
        this.redisTemplate = redisTemplate;
        this.prefix = prefix;
        this.expire = expire;
    }

    @Override
    public V get(K k) throws CacheException {
        V v = redisTemplate.opsForValue().get(getRealKey(k.toString()));
        return v;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        String rk = getRealKey(k.toString());
        redisTemplate.opsForValue().set(rk, v);
        return redisTemplate.opsForValue().getAndExpire(rk, expire, TimeUnit.SECONDS);
    }

    @Override
    public V remove(K k) throws CacheException {
        return redisTemplate.opsForValue().getAndDelete(getRealKey(k.toString()));
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(redisTemplate.keys(prefix + ":*"));
    }

    @Override
    public int size() {
        return redisTemplate.opsForValue().size(prefix + ":*").intValue();
    }

    @Override
    public Set<K> keys() {
        return (Set<K>) redisTemplate.keys(prefix + ":*");
    }

    @Override
    public Collection<V> values() {
        Set<String> keys = null;

        List<V> values = new ArrayList<V>(keys.size());
        for (String key : keys) {
            V value = value = redisTemplate.opsForValue().get(key);
            if (value != null) {
                values.add(value);
            }
        }
        return Collections.unmodifiableList(values);
    }

    private String getRealKey(String key) {
        return prefix + ":" + key;
    }
}
