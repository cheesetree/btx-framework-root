package top.cheesetree.btx.project.idgenerator.core.factory;

import top.cheesetree.btx.project.idgenerator.core.common.IdGeneratorEnum;
import top.cheesetree.btx.project.idgenerator.core.generator.IdGenerator;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/11/13 09:56
 * @Version: 1.0
 * @Description:
 */
public interface IdGeneratorFactory {
    IdGenerator getIdGenerator(String bizType, IdGeneratorEnum.IdGeneratorTypeEnum idGeneratorType);
}
