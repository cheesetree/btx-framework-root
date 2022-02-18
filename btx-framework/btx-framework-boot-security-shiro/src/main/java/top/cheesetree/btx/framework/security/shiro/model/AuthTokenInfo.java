package top.cheesetree.btx.framework.security.shiro.model;

import lombok.Getter;
import lombok.Setter;
import top.cheesetree.btx.framework.core.model.ValueObject;

/**
 * @author van
 * @date 2022/2/18 08:58
 * @description TODO
 */
@Getter
@Setter
public class AuthTokenInfo implements ValueObject {
    private String accessToken;
    private String refreshToken;
}
