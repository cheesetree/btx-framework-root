package top.cheesetree.btx.project.simplefile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.cheesetree.btx.project.simplefile.model.bo.BtxFileArchiveResourceBO;

/**
 *
 */
public interface BtxFileArchiveResourceService extends IService<BtxFileArchiveResourceBO> {
    int moveToHis(long lsh);
}