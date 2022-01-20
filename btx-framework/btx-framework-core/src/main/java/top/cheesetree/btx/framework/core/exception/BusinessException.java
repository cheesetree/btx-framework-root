package top.cheesetree.btx.framework.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: van
 * @Date: 2021/8/10 15:38
 * @Description: TODO
 */
@Getter
@Setter
public class BusinessException extends BtxException {
    public BusinessException() {
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, String errcode) {
        super(message, errcode);
    }

    public BusinessException(String message, Throwable cause, String errcode) {
        super(message, cause, errcode);
    }

    public BusinessException(Throwable cause, String errcode) {
        super(cause, errcode);
    }
}
