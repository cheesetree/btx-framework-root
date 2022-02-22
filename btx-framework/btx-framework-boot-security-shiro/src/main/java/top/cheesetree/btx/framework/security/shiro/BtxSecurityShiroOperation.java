package top.cheesetree.btx.framework.security.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.framework.security.IBtxSecurityOperation;
import top.cheesetree.btx.framework.security.constants.BtxSecurityMessage;
import top.cheesetree.btx.framework.security.model.SecurityAuthUserDTO;
import top.cheesetree.btx.framework.security.model.SecurityUserDTO;
import top.cheesetree.btx.framework.security.shiro.config.BtxShiroProperties;
import top.cheesetree.btx.framework.security.shiro.subject.StatelessToken;
import top.cheesetree.btx.framework.security.shiro.support.cas.CasToken;

/**
 * @Author: van
 * @Date: 2022/1/13 09:29
 * @Description: TODO
 */
@Slf4j
@Component
public class BtxSecurityShiroOperation implements IBtxSecurityOperation {
    @Autowired
    BtxShiroProperties btxShiroProperties;

    @Override
    public CommJSON<SecurityAuthUserDTO> login(String... args) {
        CommJSON<SecurityAuthUserDTO> ret;

        AuthenticationToken t = null;

        switch (btxShiroProperties.getAuthType()) {
            case JWT:
                break;
            case TOKEN:
            case CAS:
                if (args.length > 0) {
                    t = new CasToken(null, args[0]);
                } else {

                }
                break;
            case SESSION:
            default:
                if (args.length > 1) {
                    t = new StatelessToken(args[0], args[1]);
                } else {

                }
                break;
        }

        try {
            SecurityUtils.getSubject().login(t);

            ret = new CommJSON<>((SecurityAuthUserDTO) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal());
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
