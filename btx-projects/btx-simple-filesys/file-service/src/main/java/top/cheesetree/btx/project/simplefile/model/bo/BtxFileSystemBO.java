package top.cheesetree.btx.project.simplefile.model.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.cheesetree.btx.framework.core.model.ValueObject;

/**
 * 系统表
 *
 * @TableName tb_btx_file_system
 */
@TableName(value = "tb_btx_file_system")
@Data
public class BtxFileSystemBO implements ValueObject {
    /**
     * 流水号
     */
    @TableId(type = IdType.ID_WORKER)
    private Long lsh;

    /**
     *
     */
    private String sysId;

    /**
     *
     */
    private String sysName;

    /**
     *
     */
    private String secretKey;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}