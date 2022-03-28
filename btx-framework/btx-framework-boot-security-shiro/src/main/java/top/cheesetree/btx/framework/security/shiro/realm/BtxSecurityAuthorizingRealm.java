package top.cheesetree.btx.framework.security.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;
import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.framework.security.IBtxSecurityPermissionService;
import top.cheesetree.btx.framework.security.IBtxSecurityUserService;
import top.cheesetree.btx.framework.security.constants.BtxSecurityMessage;
import top.cheesetree.btx.framework.security.model.SecurityFuncDTO;
import top.cheesetree.btx.framework.security.model.SecurityMenuDTO;
import top.cheesetree.btx.framework.security.shiro.matcher.BtxNoAuthCredentialsMatcher;
import top.cheesetree.btx.framework.security.shiro.model.AuthTokenInfo;
import top.cheesetree.btx.framework.security.shiro.model.BtxShiroSecurityAuthUserDTO;
import top.cheesetree.btx.framework.security.shiro.model.BtxShiroSecurityUserDTO;
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
    IBtxSecurityPermissionService<? extends SecurityMenuDTO, ? extends SecurityFuncDTO> btxSecurityPermissionService;

    @Autowired
    @Lazy
    private IBtxSecurityUserService<BtxShiroSecurityUserDTO> btxSecurityUserService;

    public BtxSecurityAuthorizingRealm(BtxNoAuthCredentialsMatcher btxNoAuthCredentialsMatcher) {
        super(btxNoAuthCredentialsMatcher);
        this.setAuthenticationTokenClass(StatelessToken.class);
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && token.getClass().isAssignableFrom(StatelessToken.class);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> stringPermissions = new HashSet<>();
        btxSecurityPermissionService.getFunc(((BtxShiroSecurityAuthUserDTO) principalCollection.getPrimaryPrincipal()).getUser().getUid()).forEach((SecurityFuncDTO f) -> {
            stringPermissions.add(f.getFuncCode());
        });
        info.setStringPermissions(stringPermissions);

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        StatelessToken token = (StatelessToken) authenticationToken;

        if (StringUtils.hasLength(token.getUsername()) && token.getCredentials() != null) {
            CommJSON<BtxShiroSecurityUserDTO> ret = btxSecurityUserService.login(token.getUsername(),
                    token.getCredentials().toString());
            if (ret.checkSuc()) {
                BtxShiroSecurityAuthUserDTO u = new BtxShiroSecurityAuthUserDTO();
                u.setUser(ret.getResult());
                AuthTokenInfo t = new AuthTokenInfo();
                t.setAccessToken(token.getToken());
                u.setAuthinfo(t);
                return new SimpleAuthenticationInfo(new SimplePrincipalCollection(u, "user"),
                        t.getAccessToken());
            } else {
                throw new AccountException(ret.getMsg());
            }
        } else {
            throw new AccountException(BtxSecurityMessage.SECURIT_UNLOGIN_ERROR.getMessage());
        }

    }

    @Override
    protected Object getAuthenticationCacheKey(AuthenticationToken token) {
        return token != null ? ((StatelessToken) token).getToken() : null;
    }
}
