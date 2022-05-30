package top.cheesetree.btx.framework.security.shiro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.framework.security.IBtxSecurityOperation;
import top.cheesetree.btx.framework.security.constants.BtxSecurityEnum;
import top.cheesetree.btx.framework.security.constants.BtxSecurityMessage;
import top.cheesetree.btx.framework.security.model.SecurityUserDTO;
import top.cheesetree.btx.framework.security.shiro.config.BtxShiroProperties;
import top.cheesetree.btx.framework.security.shiro.model.BtxShiroSecurityAuthUserDTO;
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
    public CommJSON<BtxShiroSecurityAuthUserDTO> login(String... args) {
        CommJSON<BtxShiroSecurityAuthUserDTO> ret;

        AuthenticationToken t = null;
        BtxSecurityEnum.AuthType authtype = btxShiroProperties.getAuthType();

        if (args.length > 2 && StringUtils.hasLength(args[2])) {
            authtype = BtxSecurityEnum.AuthType.valueOf(args[2]);
        }

        switch (authtype) {
            case JWT:
                break;
            case CAS:
                if (args.length > 0) {
                    t = new CasToken(null, args[0]);
                } else {

                }
                break;
            case EXT_TOKEN:
                if (args.length > 0) {
                    t = new StatelessToken(args[0]);
                } else {

                }
                break;
            case TOKEN:
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

            ret = new CommJSON<>((BtxShiroSecurityAuthUserDTO) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal());
        } catch (AuthenticationException e) {
            log.warn("login error:{}", e);
            String errmsg = e.getMessage();
            if (JSONValidator.from(errmsg).validate()) {
                ret = JSON.parseObject(errmsg, CommJSON.class);
            } else {
                ret = new CommJSON<>(BtxSecurityMessage.SECURIT_LOGIN_ERROR.getCode(), e.getMessage());
            }
        }

        return ret;
    }

    @Override
    public CommJSON logout() {
        SecurityUtils.getSubject().logout();

        return new CommJSON("");
    }

    @Override
    public String getUserId() {
        return getUserInfo().getUid();
    }

    @Override
    public <T extends SecurityUserDTO> T getUserInfo() {
        return (T) (((BtxShiroSecurityAuthUserDTO) SecurityUtils.getSubject().getPrincipal()).getUser());
    }

}
