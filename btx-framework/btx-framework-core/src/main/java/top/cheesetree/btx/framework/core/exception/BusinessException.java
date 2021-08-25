package top.cheesetree.btx.framework.core.exception;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/8/10 15:38
 * @Version: 1.0
 * @Description:
 */
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
