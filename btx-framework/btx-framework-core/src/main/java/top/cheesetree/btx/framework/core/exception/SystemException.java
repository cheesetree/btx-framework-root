package top.cheesetree.btx.framework.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: van
 * @Date: 2021/8/12 14:08
 * @Description: TODO
 */
@Getter
@Setter
public class SystemException extends BtxException {

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, String errcode) {
        super(message, errcode);
    }

    public SystemException(String message, Throwable cause, String errcode) {
        super(message, cause, errcode);
    }

    public SystemException(Throwable cause, String errcode) {
        super(cause, errcode);
    }
}
