package top.cheesetree.btx.framework.security.model;

import lombok.Getter;
import lombok.Setter;
import top.cheesetree.btx.framework.core.model.ValueObject;

/**
 * @author van
 * @date 2022/2/18 08:34
 * @description TODO
 */
@Getter
@Setter
public class SecurityAuthUserDTO<T> implements ValueObject {
    private SecurityUserDTO user;
    private T authinfo;
}
