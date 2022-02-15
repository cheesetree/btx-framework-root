package top.cheesetree.btx.framework.security.shiro.realm.custom;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.framework.security.IBtxSecurityPermissionService;
import top.cheesetree.btx.framework.security.IBtxSecurityUserService;
import top.cheesetree.btx.framework.security.model.SecurityFuncDTO;
import top.cheesetree.btx.framework.security.model.SecurityUserDTO;
import top.cheesetree.btx.framework.security.shiro.matcher.BtxNoAuthCredentialsMatcher;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: van
 * @Date: 2022/1/12 15:15
 * @Description: TODO
 */
public class BtxSecurityAuthorizingRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    IBtxSecurityPermissionService btxSecurityPermissionService;

    @Autowired
    @Lazy
    private IBtxSecurityUserService btxSecurityUserService;

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
        btxSecurityPermissionService.getFunc(((SecurityUserDTO) principalCollection.getPrimaryPrincipal()).getUid()).forEach((SecurityFuncDTO f) -> {
            stringPermissions.add(f.getFuncCode());
        });
        info.setStringPermissions(stringPermissions);

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        CommJSON<SecurityUserDTO> ret = btxSecurityUserService.login(token.getUsername(),
                token.getCredentials().toString());
        if (ret.checkSuc()) {
            SecurityUserDTO u = ret.getResult();
            return new SimpleAuthenticationInfo(u, u.getUid(), u.getName());
        } else {
            throw new AccountException(ret.getMsg());
        }
    }

}
