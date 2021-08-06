package top.cheesetree.btx.framework.core.json;

import lombok.Getter;
import lombok.Setter;
import top.cheesetree.btx.framework.core.model.ValueObject;

@Getter
@Setter
public class CommJSON<T> implements ValueObject {

    public static Integer SUCCESS = 0;
    public static Integer ERROR = -1;

    private Integer ret;
    private String subcode;
    private String msg;
    private T result;

    public CommJSON() {
        this.ret = ERROR;
    }

    public CommJSON(T result) {
        this.result = result;
        this.ret = SUCCESS;
    }

    public CommJSON(Integer ret, String msg) {
        this.ret = ret;
        this.msg = msg;
    }

    public boolean checkSuc() {
        return this.ret == SUCCESS;
    }

}
