package top.cheesetree.btx.project.idgenerator.client.springboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.cheesetree.btx.project.idgenerator.client.config.IdGeneratorConfig;
import top.cheesetree.btx.project.idgenerator.core.common.IdGeneratorEnum;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/11/17 16:04
 * @Version: 1.0
 * @Description:
 */
@ConfigurationProperties("btx.idgenerator")
@Data
public class IdGeneratorClientConfigure extends IdGeneratorConfig {
    private IdGeneratorEnum.IdGeneratorTypeEnum idGeneratorType = IdGeneratorEnum.IdGeneratorTypeEnum.SNOWFLAKE;

}
