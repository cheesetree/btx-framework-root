package top.cheesetree.btx.project.simplefile.model.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.cheesetree.btx.framework.core.model.ValueObject;

/**
 * 系统日志
 *
 * @TableName tb_btx_file_log
 */
@TableName(value = "tb_btx_file_log")
@Data
public class BtxFileLogBO implements ValueObject {
    /**
     * 流水号
     */
    @TableId(type = IdType.ID_WORKER)
    private Long lsh;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 事件所属系统
     */
    private String eventSysid;

    /**
     * 事件入参
     */
    private String eventReq;

    /**
     * 事件结果
     */
    private String eventResult;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}