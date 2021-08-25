package top.cheesetree.btx.framework.core.exception;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/8/12 14:08
 * @Version: 1.0
 * @Description:
 */
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
