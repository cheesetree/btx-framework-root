package top.cheesetree.btx.project.simplefile.model.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.cheesetree.btx.framework.core.model.ValueObject;

/**
 * 文件分享记录表
 *
 * @TableName tb_btx_file_sahre
 */
@TableName(value = "tb_btx_file_sahre")
@Data
public class BtxFileSahreBO implements ValueObject {
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}