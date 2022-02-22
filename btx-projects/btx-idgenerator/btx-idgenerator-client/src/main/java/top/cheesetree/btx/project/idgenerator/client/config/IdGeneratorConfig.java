package top.cheesetree.btx.project.idgenerator.client.config;

import lombok.Data;
import top.cheesetree.btx.project.idgenerator.core.common.IdGeneratorEnum;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/11/20 08:46
 * @Version: 1.0
 * @Description:
 */
@Data
public class IdGeneratorConfig {
    private IdGeneratorEnum.IdGeneratorTypeEnum idGeneratorType = IdGeneratorEnum.IdGeneratorTypeEnum.SNOWFLAKE;
}
