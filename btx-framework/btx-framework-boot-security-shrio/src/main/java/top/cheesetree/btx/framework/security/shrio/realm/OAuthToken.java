package top.cheesetree.btx.framework.security.shrio.realm;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author van
 * @date 2022/2/11 16:28
 * @description TODO
 */
public class OAuthToken implements AuthenticationToken {
    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
