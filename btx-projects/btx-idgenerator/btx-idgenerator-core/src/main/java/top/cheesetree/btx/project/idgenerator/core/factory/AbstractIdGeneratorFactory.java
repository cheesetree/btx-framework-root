package top.cheesetree.btx.project.idgenerator.core.factory;

import top.cheesetree.btx.project.idgenerator.core.common.IdGeneratorEnum;
import top.cheesetree.btx.project.idgenerator.core.generator.IdGenerator;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/11/13 09:58
 * @Version: 1.0
 * @Description:
 */
public abstract class AbstractIdGeneratorFactory implements IdGeneratorFactory {
    private static ConcurrentHashMap<String, IdGenerator> generators = new ConcurrentHashMap<>();

    @Override
    public IdGenerator getIdGenerator(String bizType, IdGeneratorEnum.IdGeneratorTypeEnum idGeneratorType) {
        if (generators.containsKey(bizType)) {
            return generators.get(bizType);
        }
        synchronized (this) {
            if (generators.containsKey(bizType)) {
                return generators.get(bizType);
            }
            IdGenerator idGenerator = createIdGenerator(idGeneratorType);
            generators.put(bizType, idGenerator);
            return idGenerator;
        }
    }

    /**
     * 创建id生成器
     *
     * @return
     */
    protected abstract IdGenerator createIdGenerator(IdGeneratorEnum.IdGeneratorTypeEnum idGeneratorType);
}
