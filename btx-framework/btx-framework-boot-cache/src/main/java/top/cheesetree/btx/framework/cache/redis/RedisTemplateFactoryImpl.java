package top.cheesetree.btx.framework.cache.redis;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RedisTemplateFactoryImpl {
    private static final Map<KeyValueMapKey, RedisTemplate<?, ?>> redisTemplateMap = new ConcurrentHashMap<>();

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private BtxRedisCacheProperties btxRedisCacheProperties;

    public <TValue> RedisTemplate<String, TValue> generateRedisTemplate(Class<TValue> clz) {
        return generateRedisTemplate(String.class, clz);
    }

    public <TKey, TValue> RedisTemplate<TKey, TValue> generateRedisTemplate(Class<TKey> keyClz,
                                                                            Class<TValue> valueClz) {
        return generateRedisTemplate(keyClz, valueClz, true);
    }

    public <TKey, TValue> RedisTemplate<TKey, TValue> generateRedisTemplate(Class<TKey> keyClz,
                                                                            Class<TValue> valueClz,
                                                                            boolean needprefix) {
        KeyValueMapKey redisTemplateMapKey = new KeyValueMapKey(keyClz, valueClz);
        RedisTemplate<TKey, TValue> result = (RedisTemplate<TKey, TValue>) redisTemplateMap.get(redisTemplateMapKey);
        if (result == null) {
            result = (RedisTemplate<TKey, TValue>) redisTemplateMap.get(redisTemplateMapKey);
            if (result == null) {
                result = new RedisTemplate<>();
                redisTemplateMap.put(redisTemplateMapKey, result);
                result.setConnectionFactory(redisConnectionFactory);

                RedisSerializer keySerializer;
                RedisSerializer valueSerializer;
                RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
                redisTemplate.setConnectionFactory(redisConnectionFactory);

                if (String.class.isAssignableFrom(keyClz)) {
                    if (needprefix) {
                        keySerializer = new BtxKeyStringRedisSerializer(btxRedisCacheProperties.getKeyPrefix());
                    } else {
                        keySerializer = result.getKeySerializer();
                    }

                } else {
                    keySerializer = new GenericFastJsonRedisSerializer();
                }

                if (String.class.isAssignableFrom(valueClz)) {
                    valueSerializer = result.getStringSerializer();
                } else {
                    valueSerializer = new GenericFastJsonRedisSerializer();
                }

                result.setValueSerializer(valueSerializer);
                result.setKeySerializer(keySerializer);
                result.setHashValueSerializer(valueSerializer);
                result.setHashKeySerializer(keySerializer);
                result.afterPropertiesSet();
            }
        }

        return result;
    }

    @Getter
    @Setter
    public static class KeyValueMapKey implements Serializable {
        private Class<?> keyClass;
        private Class<?> valueClass;

        public KeyValueMapKey(Class<?> keyClass, Class<?> valueClass) {
            super();
            this.keyClass = keyClass;
            this.valueClass = valueClass;
        }
    }

}
