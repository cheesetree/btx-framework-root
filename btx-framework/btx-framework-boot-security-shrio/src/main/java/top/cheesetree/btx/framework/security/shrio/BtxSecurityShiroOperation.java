package top.cheesetree.btx.framework.security.shrio;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.framework.security.IBtxSecurityOperation;
import top.cheesetree.btx.framework.security.constants.BtxSecurityMessage;
import top.cheesetree.btx.framework.security.model.SecurityUserDTO;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2022/1/13 09:29
 * @Version: 1.0
 * @Description:
 */
@Slf4j
public abstract class BtxSecurityShiroOperation implements IBtxSecurityOperation {
    @Override
    public CommJSON<SecurityUserDTO> login(String loginid, String loginpwd) {
        CommJSON<SecurityUserDTO> ret = new CommJSON<>();
        UsernamePasswordToken t = new UsernamePasswordToken(loginid, loginpwd);
        try {
            SecurityUtils.getSubject().login(t);
            ret = new CommJSON<>((SecurityUserDTO) SecurityUtils.getSubject().getPrincipal());
        } catch (AuthenticationException e) {
            log.warn("login error:{}", e);
            ret = new CommJSON<>(BtxSecurityMessage.SECURIT_LOGIN_ERROR.getCode(), e.getMessage());
        }

        return ret;
    }

    @Override
    public String getUserId() {
        return ((SecurityUserDTO) SecurityUtils.getSubject().getPrincipal()).getUid();
    }

    @Override
    public SecurityUserDTO getUserInfo() {
        return (SecurityUserDTO) SecurityUtils.getSubject().getPrincipal();
    }
}
