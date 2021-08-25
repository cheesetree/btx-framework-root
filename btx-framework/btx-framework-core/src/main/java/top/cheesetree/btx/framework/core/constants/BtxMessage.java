package top.cheesetree.btx.framework.core.constants;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/8/25 09:43
 * @Version: 1.0
 * @Description:
 */
@Data
public class BtxMessage implements Serializable {
    public static final BtxMessage SYSTEM_ERROR = new BtxMessage(-1, "系统错误");
    public static final BtxMessage SUCCESS = new BtxMessage(0, "");

    private int code;
    private String message;

    public BtxMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
