package top.cheesetree.btx.framework.security.shiro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import top.cheesetree.btx.framework.security.IBtxSecurityOperation;
import top.cheesetree.btx.framework.security.controller.SecurityController;
import top.cheesetree.btx.framework.security.model.SecurityUserDTO;
import top.cheesetree.btx.framework.security.shiro.model.AuthTokenInfo;

/**
 * @author van
 * @date 2022/4/6 15:24
 * @description TODO
 */
public class BtxSpringSecurityController implements SecurityController {
    @Autowired
    IBtxSecurityOperation btxSecurityShiroOperation;

    @Override
    public String getUserId() {
        return btxSecurityShiroOperation.getUserId();
    }

    @Override
    public <T extends SecurityUserDTO> T getUser() {
        return btxSecurityShiroOperation.getUserInfo();
    }

    @Override
    public AuthTokenInfo getAuthInfo() {
        return btxSecurityShiroOperation.getAuthInfo();
    }


}
