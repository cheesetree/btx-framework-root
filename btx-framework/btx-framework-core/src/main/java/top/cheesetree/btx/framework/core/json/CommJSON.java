package top.cheesetree.btx.framework.core.json;

import lombok.Getter;
import lombok.Setter;
import top.cheesetree.btx.framework.core.constants.BtxMessage;
import top.cheesetree.btx.framework.core.model.ValueObject;

/**
 * @Author: van
 * @Date: 2021/8/12 14:08
 * @Description: TODO
 */

@Getter
@Setter
public class CommJSON<T> implements ValueObject {
    private Integer ret;
    private String subcode;
    private String msg;
    private T result;

    public CommJSON() {
        this(BtxMessage.SYSTEM_ERROR);
    }

    public CommJSON(BtxMessage mesg) {
        this(mesg.getCode(), "", mesg.getMessage(), null);
    }

    public CommJSON(T result) {
        this(BtxMessage.SUCCESS.getCode(), "", "", result);
    }

    public CommJSON(Integer ret, String msg) {
        this(ret, "", msg, null);
    }

    public CommJSON(Integer ret, String subcode, String msg) {
        this(ret, subcode, msg, null);
    }

    public CommJSON(String subcode, T result) {
        this(BtxMessage.SUCCESS.getCode(), subcode, "", result);
    }

    public CommJSON(Integer ret, String subcode, String msg, T result) {
        this.ret = ret;
        this.subcode = subcode;
        this.msg = msg;
        this.result = result;
    }

    public boolean checkSuc() {
        return this.ret.equals(BtxMessage.SUCCESS.getCode());
    }


}
