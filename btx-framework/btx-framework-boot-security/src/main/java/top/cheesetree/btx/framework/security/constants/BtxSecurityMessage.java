package top.cheesetree.btx.framework.security.constants;

import top.cheesetree.btx.framework.core.constants.BtxMessage;

/**
 * @Author: van
 * @Date: 2022/1/13 10:28
 * @Description: TODO
 */
public class BtxSecurityMessage extends BtxMessage {
    public BtxSecurityMessage(Integer code, String message) {
        super(code, message);
    }

    public static final BtxSecurityMessage SECURIT_LOGIN_ERROR = new BtxSecurityMessage(20001, "登录失败");

}
