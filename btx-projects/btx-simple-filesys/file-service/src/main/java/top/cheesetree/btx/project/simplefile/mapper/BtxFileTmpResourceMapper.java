package top.cheesetree.btx.project.simplefile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.cheesetree.btx.project.simplefile.model.bo.BtxFileTmpResourceBO;

/**
 * @Entity top.cheesetree.btx.project.simplefile.model.bo.BtxFileTmpResourceBO
 */
public interface BtxFileTmpResourceMapper extends BaseMapper<BtxFileTmpResourceBO> {
    void moveToArchive(long lsh, long fileid, String sysid);
}




