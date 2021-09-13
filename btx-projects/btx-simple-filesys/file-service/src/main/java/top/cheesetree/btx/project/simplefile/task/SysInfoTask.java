package top.cheesetree.btx.project.simplefile.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.cheesetree.btx.framework.cache.redis.RedisTemplateFactoryImpl;
import top.cheesetree.btx.project.simplefile.comm.FileConsts;
import top.cheesetree.btx.project.simplefile.model.bo.BtxFileSystemBO;
import top.cheesetree.btx.project.simplefile.service.BtxFileSystemService;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/9/8 14:40
 * @Version: 1.0
 * @Description:
 */
@Component
public class SysInfoTask {
    @Autowired
    BtxFileSystemService btxFileSystemService;

    @Autowired
    RedisTemplateFactoryImpl redisTemplateFactory;

    @Scheduled(fixedDelay = 60000)
    public void doSysInfo() {
        btxFileSystemService.list().forEach((BtxFileSystemBO c) -> {
            redisTemplateFactory.generateRedisTemplate(String.class).opsForHash().put(FileConsts.REDIS_SYS_KEY,
                    c.getSysId(), c.getSecretKey());
        });
    }

    @Scheduled(fixedDelay = 600000)
    public void clearSysInfo() {
        redisTemplateFactory.generateRedisTemplate(String.class).opsForHash().keys(FileConsts.REDIS_SYS_KEY).forEach(c -> {
            if (btxFileSystemService.lambdaQuery().eq(BtxFileSystemBO::getSysId, c.toString()) == null) {
                redisTemplateFactory.generateRedisTemplate(String.class).opsForHash().delete(FileConsts.REDIS_SYS_KEY
                        , c.toString());
            }
        });
    }
}
