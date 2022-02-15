package top.cheesetree.btx.framework.security.shiro.realm.mobile;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author van
 * @date 2022/2/10 14:20
 * @description TODO
 */
public class MobileToken implements AuthenticationToken {
    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
