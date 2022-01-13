package top.cheesetree.btx.framework.security.shrio.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.framework.security.IBtxSecurityService;
import top.cheesetree.btx.framework.security.model.SecurityFuncDTO;
import top.cheesetree.btx.framework.security.model.SecurityUserDTO;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2022/1/12 15:15
 * @Version: 1.0
 * @Description:
 */
@Component
public class BtxSecurityAuthorizingRealm extends AuthorizingRealm {
    @Autowired
    IBtxSecurityService btxSecurityService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> stringPermissions = new HashSet<>();
        btxSecurityService.getFunc((String) SecurityUtils.getSubject().getPrincipal()).forEach((SecurityFuncDTO f) -> {
            stringPermissions.add(f.getFuncCode());
        });
        info.setStringPermissions(stringPermissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        CommJSON<SecurityUserDTO> ret = btxSecurityService.login(token.getUsername(),
                token.getCredentials().toString());
        if (ret.checkSuc()) {
            SecurityUserDTO u = ret.getResult();
            return new SimpleAuthenticationInfo(u.getUid(), u, u.getName());
        } else {
            throw new AccountException(ret.getMsg());
        }
    }
}
