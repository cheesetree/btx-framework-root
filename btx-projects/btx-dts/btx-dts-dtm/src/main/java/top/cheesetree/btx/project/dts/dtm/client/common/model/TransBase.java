package top.cheesetree.btx.project.dts.dtm.client.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.cheesetree.btx.project.dts.dtm.client.common.enums.TransTypeEnum;

@Data
@NoArgsConstructor
public class TransBase {

    /**
     * 全局事务id
     */
    private String gid;

    /**
     * 事务类型
     */
    private TransTypeEnum transTypeEnum;


    private boolean waitResult;

    public TransBase(TransTypeEnum transTypeEnum, String gid, boolean waitResult) {
        this.gid = gid;
        this.transTypeEnum = transTypeEnum;
        this.waitResult = waitResult;
    }
}