package top.cheesetree.btx.framework.security.shrio;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Component;
import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.framework.security.IBtxSecurityOperation;
import top.cheesetree.btx.framework.security.constants.BtxSecurityEnum;
import top.cheesetree.btx.framework.security.constants.BtxSecurityMessage;
import top.cheesetree.btx.framework.security.model.SecurityUserDTO;
import top.cheesetree.btx.framework.security.shrio.realm.JWTToken;
import top.cheesetree.btx.framework.security.shrio.realm.MobileToken;

/**
 * @Author: van
 * @Date: 2022/1/13 09:29
 * @Description: TODO
 */
@Slf4j
@Component
public class BtxSecurityShiroOperation implements IBtxSecurityOperation {

    @Override
    public CommJSON<SecurityUserDTO> login(BtxSecurityEnum.AuthType authtype, String... args) {
        CommJSON<SecurityUserDTO> ret;

        AuthenticationToken t = null;

        switch (authtype) {
            case TOKEN:
                break;
            case JWT:
                t = new JWTToken();
                break;
            case MOBILE:
                if (args.length > 1) {
                    t = new MobileToken();
                } else {

                }
                break;
            case PASSWORD:
            default:
                if (args.length > 1) {
                    t = new UsernamePasswordToken(args[0], args[1]);
                } else {

                }
                break;
        }

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
