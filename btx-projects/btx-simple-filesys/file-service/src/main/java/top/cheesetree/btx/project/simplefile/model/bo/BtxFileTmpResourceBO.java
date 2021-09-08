package top.cheesetree.btx.project.simplefile.model.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.cheesetree.btx.framework.core.model.ValueObject;

import java.util.Date;

/**
 * 临时文件记录表
 *
 * @TableName tb_btx_file_tmp_resource
 */
@TableName(value = "tb_btx_file_tmp_resource")
@Data
public class BtxFileTmpResourceBO implements ValueObject {
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
     * 存储截止时间
     */
    private Date endTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}