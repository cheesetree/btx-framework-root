package top.cheesetree.btx.framework.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: van
 * @Date: 2021/8/10 15:13
 * @Description: TODO
 */
@Getter
@Setter
public class BtxException extends RuntimeException {
    private String errcode;

    public BtxException() {
        super();
    }

    public BtxException(Throwable cause) {
        super(cause);
    }

    public BtxException(String message, String errcode) {
        super(message);
        this.errcode = errcode;
    }

    public BtxException(String message, Throwable cause, String errcode) {
        super(message, cause);
        this.errcode = errcode;
    }

    public BtxException(Throwable cause, String errcode) {
        super(cause);
        this.errcode = errcode;
    }

    public BtxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                        String errcode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errcode = errcode;
    }

}
