package top.cheesetree.btx.framework.security.controller;

import top.cheesetree.btx.framework.security.model.SecurityUserDTO;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/8/27 11:34
 * @Version: 1.0
 * @Description:
 */
public interface SecurityController {
    String getUserId();

    SecurityUserDTO getUser();
}