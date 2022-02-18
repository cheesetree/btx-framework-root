package top.cheesetree.btx.framework.cache.redis;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Author: van
 * @Date: 2020/9/27 13:57
 * @Description: TODO
 */

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
        return generateRedisTemplate(keyClz, valueClz, needprefix, new BtxRedisSerializer());
    }

    public <TKey, TValue> RedisTemplate<TKey, TValue> generateRedisTemplate(Class<TKey> keyClz,
                                                                            Class<TValue> valueClz,
                                                                            BtxRedisSerializer btxRedisSerializer) {
        return generateRedisTemplate(keyClz, valueClz, btxRedisCacheProperties.getUseKeyPrefix(), btxRedisSerializer);
    }

    public <TKey, TValue> RedisTemplate<TKey, TValue> generateRedisTemplate(Class<TKey> keyClz,
                                                                            Class<TValue> valueClz,
                                                                            boolean needprefix,
                                                                            BtxRedisSerializer btxRedisSerializer) {
        KeyValueMapKey redisTemplateMapKey = new KeyValueMapKey(keyClz, valueClz, needprefix, btxRedisSerializer);
        RedisTemplate<TKey, TValue> result = (RedisTemplate<TKey, TValue>) redisTemplateMap.get(redisTemplateMapKey);
        if (result == null) {
            result = (RedisTemplate<TKey, TValue>) redisTemplateMap.get(redisTemplateMapKey);
            if (result == null) {
                result = new RedisTemplate<>();
                redisTemplateMap.put(redisTemplateMapKey, result);
                result.setConnectionFactory(redisConnectionFactory);

                RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
                redisTemplate.setConnectionFactory(redisConnectionFactory);

                if (btxRedisSerializer.getKeySerializer() == null) {
                    if (String.class.isAssignableFrom(keyClz)) {
                        if (needprefix) {
                            btxRedisSerializer.setKeySerializer(new BtxKeyStringRedisSerializer(btxRedisCacheProperties.getKeyPrefix()));
                        } else {
                            btxRedisSerializer.setKeySerializer(result.getStringSerializer());
                        }
                    } else {
                        btxRedisSerializer.setKeySerializer(new GenericFastJsonRedisSerializer());
                    }
                }

                if (btxRedisSerializer.getHashKeySerializer() == null) {
                    if (String.class.isAssignableFrom(valueClz)) {
                        btxRedisSerializer.setHashKeySerializer(result.getStringSerializer());
                    } else {
                        btxRedisSerializer.setHashKeySerializer(new GenericFastJsonRedisSerializer());
                    }
                }

                if (btxRedisSerializer.getValueSerializer() == null) {
                    if (String.class.isAssignableFrom(valueClz)) {
                        btxRedisSerializer.setValueSerializer(result.getStringSerializer());
                    } else {
                        btxRedisSerializer.setValueSerializer(new GenericFastJsonRedisSerializer());
                    }
                }

                result.setValueSerializer(btxRedisSerializer.getValueSerializer());
                result.setKeySerializer(btxRedisSerializer.getKeySerializer());
                result.setHashValueSerializer(btxRedisSerializer.getHashValueSerializer());
                result.setHashKeySerializer(btxRedisSerializer.getHashKeySerializer());
                result.setStringSerializer(btxRedisSerializer.getStringSerializer());
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
        private boolean needprefix;
        private BtxRedisSerializer btxRedisSerializer;

        public KeyValueMapKey(Class<?> keyClass, Class<?> valueClass, boolean needprefix,
                              BtxRedisSerializer btxRedisSerializer) {
            super();
            this.keyClass = keyClass;
            this.valueClass = valueClass;
            this.needprefix = needprefix;
            this.btxRedisSerializer = btxRedisSerializer;
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
            return keyClass.equals(that.keyClass) && valueClass.equals(that.valueClass) && needprefix == that.needprefix && btxRedisSerializer.equals(that.btxRedisSerializer);
        }

        @Override
        public int hashCode() {
            return Objects.hash(keyClass, valueClass, needprefix);
        }
    }

}
