package top.cheesetree.btx.project.idgenerator.client.springboot;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.cheesetree.btx.project.idgenerator.client.factory.impl.IdGeneratorFactoryClient;
import top.cheesetree.btx.project.idgenerator.client.generator.impl.ButterflyIdGenerator;
import top.cheesetree.btx.project.idgenerator.client.generator.impl.SegmentIdGenerator;
import top.cheesetree.btx.project.idgenerator.client.generator.impl.SnowflakeIdGenerator;
import top.cheesetree.btx.project.idgenerator.client.springboot.config.IdGeneratorClientConfigure;
import top.cheesetree.btx.project.idgenerator.client.springboot.config.SnowflakeConfigure;
import top.cheesetree.btx.project.idgenerator.core.factory.IdGeneratorFactory;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/11/20 08:47
 * @Version: 1.0
 * @Description:
 */
@EnableConfigurationProperties({SnowflakeConfigure.class, IdGeneratorClientConfigure.class})
@Component
public class IdGeneratorClient {
    @Bean
    public IdGeneratorFactory idGeneratorFactory() {
        return new IdGeneratorFactoryClient();
    }

    @Bean
    public ButterflyIdGenerator butterflyIdGenerator() {
        return new ButterflyIdGenerator();
    }

    @Bean
    public SegmentIdGenerator segmentIdGenerator() {
        return new SegmentIdGenerator();
    }

    @Bean
    public SnowflakeIdGenerator snowflakeIdGenerator() {
        return new SnowflakeIdGenerator();
    }

}
