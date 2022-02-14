package top.cheesetree.btx.framework.security.shrio.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

/**
 * @Author: van
 * @Date: 2022/1/13 09:00
 * @Description: TODO
 */
@Component
public class BtxSecurityOAuthTokenAuthorizingRealm extends AuthorizingRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && this.getAuthenticationTokenClass().isAssignableFrom(OAuthToken.class);    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
