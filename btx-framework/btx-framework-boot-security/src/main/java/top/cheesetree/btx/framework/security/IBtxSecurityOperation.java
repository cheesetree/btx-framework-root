package top.cheesetree.btx.framework.security;

import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.framework.security.model.SecurityUserDTO;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2022/1/13 09:21
 * @Version: 1.0
 * @Description:
 */
public interface IBtxSecurityOperation {
    CommJSON<SecurityUserDTO> login(String loginid,
                                    String loginpwd);

    String getUserId();

    SecurityUserDTO getUserInfo();
}
