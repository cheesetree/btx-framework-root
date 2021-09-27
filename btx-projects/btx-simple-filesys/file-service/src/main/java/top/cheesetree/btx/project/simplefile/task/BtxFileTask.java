package top.cheesetree.btx.project.simplefile.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.cheesetree.btx.project.simplefile.model.bo.BtxFileTmpResourceBO;
import top.cheesetree.btx.project.simplefile.service.BtxFileTmpResourceService;
import top.cheesetree.btx.simplefile.file.client.IFileClient;

import javax.annotation.Resource;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/9/10 17:56
 * @Version: 1.0
 * @Description:
 */
@Component
public class BtxFileTask {
    @Resource
    BtxFileTmpResourceService btxFileTmpResourceService;
    @Resource
    private IFileClient fileClient;

    public void doFileSign() {
        btxFileTmpResourceService.lambdaQuery().eq(BtxFileTmpResourceBO::getFileHash, null).list().forEach((BtxFileTmpResourceBO f) -> {

        });

    }

    @Scheduled(fixedDelay = 600000)
    public void doCleatTmpFiles() {
        btxFileTmpResourceService.lambdaQuery().le(BtxFileTmpResourceBO::getEndTime, System.currentTimeMillis()).list().forEach((BtxFileTmpResourceBO f) ->
        {
            btxFileTmpResourceService.deleteById(f.getLsh());
        });
    }
}
