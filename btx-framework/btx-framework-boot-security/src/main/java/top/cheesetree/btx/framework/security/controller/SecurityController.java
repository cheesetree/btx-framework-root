package top.cheesetree.btx.framework.security.controller;

import top.cheesetree.btx.framework.core.model.ValueObject;
import top.cheesetree.btx.framework.security.model.SecurityUserDTO;

/**
 * @Author: van
 * @Date: 2021/8/27 11:34
 * @Description: TODO
 */
public interface SecurityController {
    String getUserId();

    <T extends SecurityUserDTO> T getUser();

    <T extends ValueObject> T getAuthInfo();
}
