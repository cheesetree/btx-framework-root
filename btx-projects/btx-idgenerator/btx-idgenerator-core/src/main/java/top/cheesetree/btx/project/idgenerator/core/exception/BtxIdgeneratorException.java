package top.cheesetree.btx.project.idgenerator.core.exception;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/11/12 15:24
 * @Version: 1.0
 * @Description:
 */
public class BtxIdgeneratorException extends RuntimeException {
    public BtxIdgeneratorException() {
        super();
    }

    public BtxIdgeneratorException(String msg) {
        super(msg);
    }

    public BtxIdgeneratorException(Throwable cause) {
        super(cause);
    }

    public BtxIdgeneratorException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BtxIdgeneratorException(String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
    }
}
