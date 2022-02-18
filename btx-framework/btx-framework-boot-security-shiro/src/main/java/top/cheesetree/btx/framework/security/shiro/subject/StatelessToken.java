package top.cheesetree.btx.framework.security.shiro.subject;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.util.UUID;

/**
 * @author van
 * @date 2022/2/18 09:56
 * @description TODO
 */
@Getter
@Setter
public class StatelessToken extends UsernamePasswordToken {
    private String token = UUID.randomUUID().toString();

    public StatelessToken() {
        super();
    }

    public StatelessToken(String token) {
        super();
        this.token = token;
    }

    public StatelessToken(String username, char[] password) {
        super(username, password);
    }

    public StatelessToken(String username, String password) {
        super(username, password);
    }

    public StatelessToken(String username, char[] password, String host) {
        super(username, password, host);
    }

    public StatelessToken(String username, String password, String host) {
        super(username, password, host);
    }

    public StatelessToken(String username, char[] password, boolean rememberMe) {
        super(username, password, rememberMe);
    }

    public StatelessToken(String username, String password, boolean rememberMe) {
        super(username, password, rememberMe);
    }

    public StatelessToken(String username, char[] password, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
    }

    public StatelessToken(String username, String password, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
    }
}
