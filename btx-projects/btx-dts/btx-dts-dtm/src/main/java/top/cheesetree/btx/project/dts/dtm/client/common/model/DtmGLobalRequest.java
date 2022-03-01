package top.cheesetree.btx.project.dts.dtm.client.common.model;

import lombok.Builder;
import lombok.Data;
import top.cheesetree.btx.framework.core.model.ValueObject;

/**
 * @author van
 * @date 2022/3/1 20:01
 * @description TODO
 */
@Data
@Builder
public class DtmGLobalRequest implements ValueObject {
    private String gid;
    private String trans_type;
}
