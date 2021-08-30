package top.cheesetree.btx.framework.security.model;

import lombok.Getter;
import lombok.Setter;

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
public class SecurityMenuDTO {
    private String menuId;
    private String menuName;
    private String menuUrl;
    private String menuDesc;
    private String menuLevel;
    private String authLevel;
    private String target;
    private String note;
    private String menuIcon;
    private String pMenuId;
    private String param1;
    private String param2;
}
