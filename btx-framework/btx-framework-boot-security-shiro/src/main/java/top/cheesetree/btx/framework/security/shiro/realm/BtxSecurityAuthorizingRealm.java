package top.cheesetree.btx.framework.security.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.framework.security.IBtxSecurityPermissionService;
import top.cheesetree.btx.framework.security.IBtxSecurityUserService;
import top.cheesetree.btx.framework.security.model.SecurityAuthUserDTO;
import top.cheesetree.btx.framework.security.model.SecurityFuncDTO;
import top.cheesetree.btx.framework.security.model.SecurityUserDTO;
import top.cheesetree.btx.framework.security.shiro.matcher.BtxNoAuthCredentialsMatcher;
import top.cheesetree.btx.framework.security.shiro.model.AuthTokenInfo;
import top.cheesetree.btx.framework.security.shiro.subject.StatelessToken;

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
        return token != null && this.getAuthenticationTokenClass().isAssignableFrom(StatelessToken.class);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> stringPermissions = new HashSet<>();
        btxSecurityPermissionService.getFunc(((SecurityAuthUserDTO) principalCollection.getPrimaryPrincipal()).getUser().getUid()).forEach((SecurityFuncDTO f) -> {
            stringPermissions.add(f.getFuncCode());
        });
        info.setStringPermissions(stringPermissions);

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        StatelessToken token = (StatelessToken) authenticationToken;

        CommJSON<SecurityUserDTO> ret = btxSecurityUserService.login(token.getUsername(),
                token.getCredentials().toString());
        if (ret.checkSuc()) {
            SecurityAuthUserDTO u = new SecurityAuthUserDTO();
            u.setUser(ret.getResult());
            AuthTokenInfo t = new AuthTokenInfo();
            t.setAccessToken(token.getToken());
            u.setAuthinfo(t);
            return new SimpleAuthenticationInfo(new SimplePrincipalCollection(u, "user"),
                    t.getAccessToken());
        } else {
            throw new AccountException(ret.getMsg());
        }
    }

    @Override
    protected Object getAuthenticationCacheKey(AuthenticationToken token) {
        return token != null ? ((StatelessToken) token).getToken() : null;
    }
}
