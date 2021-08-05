package top.cheesetree.btx.project.idgenerator.client.springboot.mybatis;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import top.cheesetree.btx.project.idgenerator.client.springboot.config.IdGeneratorClientConfigure;
import top.cheesetree.btx.project.idgenerator.core.common.IdGeneratorConst;
import top.cheesetree.btx.project.idgenerator.core.common.IdGeneratorEnum;
import top.cheesetree.btx.project.idgenerator.core.factory.IdGeneratorFactory;

import javax.annotation.Resource;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/11/17 17:01
 * @Version: 1.0
 * @Description:
 */
@Component
@ConditionalOnProperty(prefix = "btx.idgenerator.mybatis", value = "enabled", matchIfMissing =
        true)
public class BtxMybatisIdGenerator implements IdentifierGenerator {
    @Resource
    private IdGeneratorFactory idGeneratorFactory;

    @Resource
    private IdGeneratorClientConfigure idGeneratorClientConfigure;

    @Override
    public Number nextId(Object entity) {
        IdGeneratorEnum.IdGeneratorTypeEnum type = idGeneratorClientConfigure.getIdGeneratorType();
        String biztype = IdGeneratorConst.DEF_BIZTYPE;

        if (entity instanceof IdGeneratorValueObject) {
            IdGeneratorValueObject o = (IdGeneratorValueObject) entity;
            type = o.getIdGeneratorType();
            biztype = o.getBizType();
        }

        return idGeneratorFactory.getIdGenerator(biztype, type).getId();
    }
}
