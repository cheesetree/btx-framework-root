package top.cheesetree.btx.framework.security.model;

import lombok.Getter;
import lombok.Setter;
import top.cheesetree.btx.framework.core.model.ValueObject;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/8/27 13:48
 * @Version: 1.0
 * @Description:
 */
@Getter
@Setter
public class SecurityFuncDTO implements ValueObject {
    private String funcCode;
    private String funcName;
    private String funcDesc;
    private String note;
    private String actionLink;


}
