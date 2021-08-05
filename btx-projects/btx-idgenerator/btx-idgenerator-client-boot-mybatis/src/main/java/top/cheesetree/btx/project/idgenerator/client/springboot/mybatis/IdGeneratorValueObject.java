package top.cheesetree.btx.project.idgenerator.client.springboot.mybatis;

import lombok.Data;
import top.cheesetree.btx.framework.core.model.ValueObject;
import top.cheesetree.btx.project.idgenerator.core.common.IdGeneratorConst;
import top.cheesetree.btx.project.idgenerator.core.common.IdGeneratorEnum;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/11/18 09:01
 * @Version: 1.0
 * @Description:
 */
@Data
public class IdGeneratorValueObject implements ValueObject {
    private IdGeneratorEnum.IdGeneratorTypeEnum idGeneratorType = IdGeneratorEnum.IdGeneratorTypeEnum.SNOWFLAKE;
    private String bizType = IdGeneratorConst.DEF_BIZTYPE;
}
