package top.cheesetree.btx.framework.core.json;

import lombok.Getter;
import lombok.Setter;
import top.cheesetree.btx.framework.core.constants.BtxConsts;

import java.io.Serializable;


/**
 * JsonResult
 *
 * @param <T>
 * @author He@77.nG
 */
@Getter
@Setter
@Deprecated
public class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = -3136052538356961560L;

    public static Integer OK = 200;
    public static Integer FAILURE = 300;
    public static Integer ERROR = 400;
    public static Integer UNLOGIN = 401;
    public static Integer NOAUTHORITY = 403;
    public static Integer BADREQUEST = 500;

    private Integer statusCode;

    private String errorCode;

    private String message;

    private T result;

    public JsonResult() {
        this.statusCode = OK;
        this.message = BtxConsts.EMPTY;
    }

    public JsonResult(T result) {
        this.result = result;
        this.statusCode = OK;
        this.message = BtxConsts.EMPTY;
    }

    public JsonResult(String errMsg) {
        this.statusCode = FAILURE;
        this.message = errMsg;
    }

    public JsonResult(Integer statucCode, String errMsg) {
        this.statusCode = statucCode;
        this.message = errMsg;
    }

    public JsonResult(Integer statucCode, String errorCode, String errMsg) {
        this.statusCode = statucCode;
        this.errorCode = errorCode;
        this.message = errMsg;
    }

    public JsonResult(Integer statucCode, String errMsg, T result) {
        this.result = result;
        this.statusCode = statucCode;
        this.message = errMsg;
    }

    public JsonResult(Integer statucCode, String errorCode, String errMsg, T result) {
        this.result = result;
        this.statusCode = statucCode;
        this.errorCode = errorCode;
        this.message = errMsg;
    }
}
