package top.cheesetree.btx.project.simplefile.model.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.cheesetree.btx.framework.core.model.ValueObject;

import java.util.Date;

/**
 * 归档文件历史记录表
 *
 * @TableName tb_btx_file_archive_resource_his
 */
@TableName(value = "tb_btx_file_archive_resource_his")
@Data
public class BtxFileArchiveResourceHisBO implements ValueObject {
    /**
     * 流水号
     */
    @TableId(type = IdType.ID_WORKER)
    private Long lsh;

    /**
     * 所属系统ID
     */
    private String sysId;

    /**
     * 原始名称
     */
    private String fileOriName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件所占空间
     */
    private Long fileStorage;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件元数据
     */
    private String fileStorageInfo;

    /**
     * 文件指纹
     */
    private String fileHash;

    /**
     * 是否加密
     */
    private String isCrypto;

    /**
     * 是否公开
     */
    private String isPub;

    /**
     * 移入历史表时间
     */
    private Date hisTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}