package top.cheesetree.btx.project.simplefile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.cheesetree.btx.project.simplefile.model.bo.BtxFileTmpResourceBO;

/**
 * @Entity top.cheesetree.btx.project.simplefile.model.bo.BtxFileTmpResourceBO
 */
public interface BtxFileTmpResourceMapper extends BaseMapper<BtxFileTmpResourceBO> {
    void moveToArchive(@Param("lsh") long lsh, @Param("fileid") long fileid, @Param("sysid") String sysid);
}




