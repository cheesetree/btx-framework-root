package top.cheesetree.btx.framework.security.shiro.support.cas;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author van
 * @date 2022/2/21 10:18
 * @description TODO
 */
public class CasAuthenticationException extends AuthenticationException {
    public CasAuthenticationException() {
    }

    public CasAuthenticationException(String message) {
        super(message);
    }

    public CasAuthenticationException(Throwable cause) {
        super(cause);
    }

    public CasAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
