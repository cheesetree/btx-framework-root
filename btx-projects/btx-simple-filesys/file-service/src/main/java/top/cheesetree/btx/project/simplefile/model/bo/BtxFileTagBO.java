package top.cheesetree.btx.project.simplefile.model.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.cheesetree.btx.framework.core.model.ValueObject;

/**
 * 文件标签关联
 *
 * @TableName tb_btx_file_tag
 */
@TableName(value = "tb_btx_file_tag")
@Data
public class BtxFileTagBO implements ValueObject {
    /**
     * 流水号
     */
    @TableId(type = IdType.ID_WORKER)
    private Long lsh;

    /**
     * 文件 ID
     */
    private Long fileId;

    /**
     * 文件 TAG
     */
    private String tag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}