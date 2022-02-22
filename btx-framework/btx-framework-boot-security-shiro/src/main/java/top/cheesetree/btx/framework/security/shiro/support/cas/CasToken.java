package top.cheesetree.btx.framework.security.shiro.support.cas;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * @author van
 * @date 2022/2/21 09:19
 * @description TODO
 */
@Getter
@Setter
public class CasToken implements RememberMeAuthenticationToken {
    private String ticket;
    private String userId;
    private boolean isRememberMe = false;

    public CasToken(String ticket, String userId) {
        this.ticket = ticket;
        this.userId = userId;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public Object getCredentials() {
        return ticket;
    }
}
