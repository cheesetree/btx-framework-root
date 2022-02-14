package top.cheesetree.btx.framework.security.shrio.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.framework.security.IBtxSecurityService;
import top.cheesetree.btx.framework.security.model.SecurityFuncDTO;
import top.cheesetree.btx.framework.security.model.SecurityUserDTO;
import top.cheesetree.btx.framework.security.shrio.matcher.BtxNoAuthCredentialsMatcher;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: van
 * @Date: 2022/1/12 15:15
 * @Description: TODO
 */
@Component
public class BtxSecurityAuthorizingRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    IBtxSecurityService btxSecurityService;

    public BtxSecurityAuthorizingRealm(BtxNoAuthCredentialsMatcher btxNoAuthCredentialsMatcher) {
        super(btxNoAuthCredentialsMatcher);
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && this.getAuthenticationTokenClass().isAssignableFrom(UsernamePasswordToken.class);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> stringPermissions = new HashSet<>();
        btxSecurityService.getFunc(((SecurityUserDTO) principalCollection.getPrimaryPrincipal()).getUid()).forEach((SecurityFuncDTO f) -> {
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
            return new SimpleAuthenticationInfo(u, u.getUid(), u.getName());
        } else {
            throw new AccountException(ret.getMsg());
        }
    }

}
