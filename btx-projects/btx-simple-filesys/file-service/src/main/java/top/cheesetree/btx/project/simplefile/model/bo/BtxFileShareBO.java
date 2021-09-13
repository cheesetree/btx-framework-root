package top.cheesetree.btx.project.simplefile.model.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.cheesetree.btx.framework.core.model.ValueObject;

import java.util.Date;

/**
 * 文件分享记录表
 *
 * @TableName tb_btx_file_share
 */
@TableName(value = "tb_btx_file_share")
@Data
public class BtxFileShareBO implements ValueObject {
    /**
     * 流水号
     */
    @TableId(type = IdType.ID_WORKER)
    private Long lsh;

    /**
     * 来源系统 ID
     */
    private Long srcSysId;

    /**
     * 目标系统 ID
     */
    private Long dstSysId;

    /**
     * 文件 ID
     */
    private Long fileId;

    /**
     * 是否临时
     */
    private String isTmp;

    /**
     * 授权开始时间
     */
    private Date startTime;

    /**
     * 授权结束时间
     */
    private Date endTime;

    /**
     * 授权类型
     */
    private Integer authType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}