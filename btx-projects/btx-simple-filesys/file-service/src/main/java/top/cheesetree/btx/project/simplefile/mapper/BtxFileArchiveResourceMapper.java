package top.cheesetree.btx.project.simplefile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.cheesetree.btx.project.simplefile.model.bo.BtxFileArchiveResourceBO;

/**
 * @Entity top.cheesetree.btx.project.simplefile.model.bo.BtxFileArchiveResourceBO
 */
public interface BtxFileArchiveResourceMapper extends BaseMapper<BtxFileArchiveResourceBO> {
    void moveToHis(long lsh, long fileid, String sysid);
}




