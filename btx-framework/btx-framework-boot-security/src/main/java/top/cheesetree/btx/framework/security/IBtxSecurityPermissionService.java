package top.cheesetree.btx.framework.security;

import top.cheesetree.btx.framework.security.model.SecurityFuncDTO;
import top.cheesetree.btx.framework.security.model.SecurityMenuDTO;

import java.util.List;

/**
 * @Author: van
 * @Date: 2022/1/12 15:10
 * @Description: TODO
 */
public interface IBtxSecurityPermissionService {
    List<SecurityMenuDTO> getMenu(String userid, String authlevel);

    List<SecurityFuncDTO> getFunc(String userid);
}
