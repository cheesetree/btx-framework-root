package top.cheesetree.btx.project.idgenerator.client.factory.impl;


import top.cheesetree.btx.project.idgenerator.client.generator.impl.ButterflyIdGenerator;
import top.cheesetree.btx.project.idgenerator.client.generator.impl.SegmentIdGenerator;
import top.cheesetree.btx.project.idgenerator.client.generator.impl.SnowflakeIdGenerator;
import top.cheesetree.btx.project.idgenerator.core.common.IdGeneratorEnum;
import top.cheesetree.btx.project.idgenerator.core.factory.AbstractIdGeneratorFactory;
import top.cheesetree.btx.project.idgenerator.core.generator.IdGenerator;

import javax.annotation.Resource;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/11/13 10:17
 * @Version: 1.0
 * @Description:
 */
public class IdGeneratorFactoryClient extends AbstractIdGeneratorFactory {
    @Resource
    ButterflyIdGenerator butterflyIdGenerator;
    @Resource
    SegmentIdGenerator segmentIdGenerator;
    @Resource
    SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    protected IdGenerator createIdGenerator(IdGeneratorEnum.IdGeneratorTypeEnum idGeneratorType) {
        IdGenerator ret = null;

        switch (idGeneratorType) {
            case BUTTERFLY:
                ret = butterflyIdGenerator;
                break;
            case SEGMENT:
                ret = segmentIdGenerator;
                break;
            case SNOWFLAKE:
            default:
                ret = snowflakeIdGenerator;
                break;
        }
        return ret;
    }
}
