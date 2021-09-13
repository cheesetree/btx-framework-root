package top.cheesetree.btx.project.simplefile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.cheesetree.btx.project.simplefile.model.bo.BtxFileTagBO;

import java.util.List;

/**
 *
 */
public interface BtxFileTagService extends IService<BtxFileTagBO> {
    boolean updateTags(Long fileid, List<BtxFileTagBO> tags);
}
