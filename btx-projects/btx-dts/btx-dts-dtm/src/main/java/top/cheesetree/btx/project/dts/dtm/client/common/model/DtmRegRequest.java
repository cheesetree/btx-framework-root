package top.cheesetree.btx.project.dts.dtm.client.common.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;
import top.cheesetree.btx.framework.core.model.ValueObject;

/**
 * @author van
 * @date 2022/3/1 19:57
 * @description TODO
 */
@Builder
@Data
public class DtmRegRequest implements ValueObject {
    private String gid;
    private String trans_type;
    private String branch_id;
    private String status;
    private String data;
    @JSONField(name = "try")
    private String tryd;
    private String confirm;
    private String cancel;
}
