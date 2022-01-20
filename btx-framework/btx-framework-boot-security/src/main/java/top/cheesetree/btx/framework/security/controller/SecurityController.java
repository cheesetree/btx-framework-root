package top.cheesetree.btx.framework.security.controller;

import top.cheesetree.btx.framework.security.model.SecurityUserDTO;

/**
 * @Author: van
 * @Date: 2021/8/27 11:34
 * @Description: TODO
 */
public interface SecurityController {
    String getUserId();

    SecurityUserDTO getUser();
}
