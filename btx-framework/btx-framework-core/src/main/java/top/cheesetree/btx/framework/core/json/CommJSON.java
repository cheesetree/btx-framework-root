package top.cheesetree.btx.framework.core.json;

import lombok.Getter;
import lombok.Setter;
import top.cheesetree.btx.framework.core.constants.BtxMessage;
import top.cheesetree.btx.framework.core.model.ValueObject;

import java.io.Serializable;

@Getter
@Setter
public class CommJSON<T extends Serializable> implements ValueObject {
    private Integer ret;
    private String subcode;
    private String msg;
    private T result;

    public CommJSON() {
        this.ret = BtxMessage.SYSTEM_ERROR.getCode();
        this.msg = BtxMessage.SYSTEM_ERROR.getMessage();
    }

    public CommJSON(T result) {
        this.result = result;
        this.ret = BtxMessage.SUCCESS.getCode();
    }

    public CommJSON(Integer ret, String msg) {
        this.ret = ret;
        this.msg = msg;
    }

    public CommJSON(String subcode, String msg) {
        this.ret = BtxMessage.SYSTEM_ERROR.getCode();
        this.subcode = subcode;
        this.msg = msg;
        this.result = result;
    }

    public CommJSON(String subcode, T result) {
        this.subcode = subcode;
        this.result = result;
    }

    public CommJSON(Integer ret, String subcode, String msg, T result) {
        this.ret = ret;
        this.subcode = subcode;
        this.msg = msg;
        this.result = result;
    }

    public boolean checkSuc() {
        return this.ret == BtxMessage.SUCCESS.getCode();
    }


}
