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
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RedisTemplateFactoryImpl {
    private static Map<KeyValueMapKey, RedisTemplate<?, ?>> redisTemplateMap = new ConcurrentHashMap<>();

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private BtxRedisCacheProperties btxRedisCacheProperties;

    public <TValue> RedisTemplate<String, TValue> generateRedisTemplate(Class<TValue> clz) {
        return generateRedisTemplate(String.class, clz);
    }

    public <TKey, TValue> RedisTemplate<TKey, TValue> generateRedisTemplate(Class<TKey> keyClz,
                                                                            Class<TValue> valueClz) {
        return generateRedisTemplate(keyClz, valueClz, btxRedisCacheProperties.getUseKeyPrefix());
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
                RedisSerializer hashKeySerializer;
                RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
                redisTemplate.setConnectionFactory(redisConnectionFactory);

                if (String.class.isAssignableFrom(keyClz)) {
                    if (needprefix) {
                        keySerializer = new BtxKeyStringRedisSerializer(btxRedisCacheProperties.getKeyPrefix());
                        hashKeySerializer = result.getStringSerializer();
                    } else {
                        keySerializer = result.getStringSerializer();
                        hashKeySerializer = keySerializer;
                    }

                } else {
                    keySerializer = new GenericFastJsonRedisSerializer();
                    hashKeySerializer = keySerializer;
                }

                if (String.class.isAssignableFrom(valueClz)) {
                    valueSerializer = result.getStringSerializer();
                } else {
                    valueSerializer = new GenericFastJsonRedisSerializer();
                }

                result.setValueSerializer(valueSerializer);
                result.setKeySerializer(keySerializer);
                result.setHashValueSerializer(valueSerializer);
                result.setHashKeySerializer(hashKeySerializer);
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

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            KeyValueMapKey that = (KeyValueMapKey) o;
            return keyClass.equals(that.keyClass) && valueClass.equals(that.valueClass);
        }

        @Override
        public int hashCode() {
            return Objects.hash(keyClass, valueClass);
        }
    }

}
