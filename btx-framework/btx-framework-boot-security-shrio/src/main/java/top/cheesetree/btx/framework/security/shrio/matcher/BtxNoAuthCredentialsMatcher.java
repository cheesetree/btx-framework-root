package top.cheesetree.btx.framework.security.shrio.matcher;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @author van
 * @date 2022/2/11 22:14
 * @description 不验证适配器，用以自定义认证过程
 */
public class BtxNoAuthCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        return true;
    }
}
