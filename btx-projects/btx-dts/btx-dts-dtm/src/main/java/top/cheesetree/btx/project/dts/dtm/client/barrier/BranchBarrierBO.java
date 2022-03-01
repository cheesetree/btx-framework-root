package top.cheesetree.btx.project.dts.dtm.client.barrier;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author van
 * @date 2022/3/1 11:20
 * @description TODO
 */
@Data
@TableName("barrier")
public class BranchBarrierBO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 事务类型
     */
    private String transType;

    /**
     * 全局事务id
     */
    private String gid;

    /**
     * 分支id
     */
    private String branchId;

    /**
     * 操作
     */
    private String op;

    /**
     * 屏障id
     */
    private String barrierId;

    private String reason;

    private Date createTime;

    private Date updateTime;
}
