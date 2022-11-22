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

    public static final BtxSecurityMessage SECURIT_UNLOGIN_ERROR = new BtxSecurityMessage(20002, "访问未登录");

    public static final BtxSecurityMessage SECURIT_UNAUTH_ERROR = new BtxSecurityMessage(20003, "访问未授权");

    public static final BtxSecurityMessage SECURIT_CAS_TICKET_ERROR = new BtxSecurityMessage(20004, "TICKET校验失败");

}
