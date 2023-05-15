package top.cheesetree.btx.framework.cache.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.UUID;

/**
 * @author van
 * @date 2022/2/18 14:49
 * @description TODO
 */
@Getter
@Setter
public class BtxRedisSerializer {
    private RedisSerializer<?> keySerializer;

    private RedisSerializer<?> valueSerializer;

    private RedisSerializer<?> hashKeySerializer;

    private RedisSerializer<?> hashValueSerializer = valueSerializer;

    private RedisSerializer<String> stringSerializer = RedisSerializer.string();

    private String serializerTag;

    public BtxRedisSerializer(String serializerTag) {
        this.serializerTag = serializerTag;
    }

    public BtxRedisSerializer() {
        this(UUID.randomUUID().toString());
    }

    @Override
    public int hashCode() {
        return this.serializerTag.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.serializerTag.equals(((BtxRedisSerializer) o).getSerializerTag());
    }
}
