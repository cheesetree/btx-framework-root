package top.cheesetree.btx.framework.security;

import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.framework.security.constants.BtxSecurityEnum;
import top.cheesetree.btx.framework.security.model.SecurityAuthUserDTO;
import top.cheesetree.btx.framework.security.model.SecurityUserDTO;

/**
 * @Author: van
 * @Date: 2022/1/13 09:21
 * @Description: TODO
 */
public interface IBtxSecurityOperation {

    CommJSON<SecurityAuthUserDTO> login(BtxSecurityEnum.AuthType authtype, String... args);

    String getUserId();

    SecurityUserDTO getUserInfo();
}
