package top.cheesetree.btx.framework.security;

import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.framework.security.model.SecurityAuthUserDTO;
import top.cheesetree.btx.framework.security.model.SecurityUserDTO;

/**
 * @Author: van
 * @Date: 2022/1/13 09:21
 * @Description: TODO
 */
public interface IBtxSecurityOperation {

    CommJSON<? extends SecurityAuthUserDTO> login(String... args);

    CommJSON logout();

    String getUserId();

    <T extends SecurityUserDTO> T getUserInfo();

}
