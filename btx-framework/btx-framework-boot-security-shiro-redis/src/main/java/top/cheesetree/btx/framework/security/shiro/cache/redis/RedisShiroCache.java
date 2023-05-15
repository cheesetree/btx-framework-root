package top.cheesetree.btx.framework.security.shiro.cache.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.nio.charset.Charset;
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

    private static DefaultRedisScript<Object> getdelLua;

    static {
        getdelLua = new DefaultRedisScript<>();
        getdelLua.setResultType(Object.class);
        getdelLua.setScriptText("local current = redis.call('get', KEYS[1]);\n" +
                "if (current) then\n" +
                "    redis.call('del', KEYS[1]);\n" +
                "end\n" +
                "return current;");
    }


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
        redisTemplate.opsForValue().set(rk, v, expire, TimeUnit.SECONDS);
        return null;
    }

    @Override
    public V remove(K k) throws CacheException {
        String key = getRealKey(k.toString());
        Object r = this.redisTemplate.execute(getdelLua, new ArrayList<String>() {{
            add(key);
        }});
        return r == null ? null : (V) r;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete((Collection<String>) keys());
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        Set<K> keys = (Set<K>) redisTemplate.execute(new RedisCallback<Set<K>>() {
            @Override
            public Set<K> doInRedis(RedisConnection connection) {
                Set<K> keys = new HashSet<K>();
                //可以找到对应名字的所有key
                Cursor<byte[]> scan = connection.scan(ScanOptions.scanOptions().match(prefix + "*").build());
                while (scan.hasNext()) {
                    String key = new String(scan.next(), Charset.defaultCharset());
                    keys.add((K) key);
                }
                return keys;
            }
        });
        return !keys.isEmpty() ? Collections.unmodifiableSet(keys) : (Set<K>) Collections.emptySet();
    }

    @Override
    public Collection<V> values() {
        List values = redisTemplate.opsForValue().multiGet((Collection<String>) keys());
        return (Collection) (!values.isEmpty() ? Collections.unmodifiableCollection(values) : Collections.emptyList());
    }

    private String getRealKey(String key) {
        return prefix + ":" + key;
    }
}
