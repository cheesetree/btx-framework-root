package top.cheesetree.btx.project.simplefile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.cheesetree.btx.project.simplefile.model.bo.BtxFileTmpResourceBO;

/**
 *
 */
public interface BtxFileTmpResourceService extends IService<BtxFileTmpResourceBO> {
    void moveToArchive(long lsh, long fileid, String sysid);
}
