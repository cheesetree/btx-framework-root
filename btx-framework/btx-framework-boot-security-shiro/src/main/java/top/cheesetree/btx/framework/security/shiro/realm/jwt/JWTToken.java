package top.cheesetree.btx.framework.security.shiro.realm.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author van
 * @date 2022/2/10 14:17
 * @description TODO
 */
public class JWTToken implements AuthenticationToken {
    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
