package top.cheesetree.btx.project.idgenerator.client.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import top.cheesetree.btx.project.idgenerator.client.config.SnowflakeConfig;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/11/17 16:04
 * @Version: 1.0
 * @Description:
 */
@ConfigurationProperties("btx.idgenerator.snowflake")
public class SnowflakeConfigure extends SnowflakeConfig {
}
