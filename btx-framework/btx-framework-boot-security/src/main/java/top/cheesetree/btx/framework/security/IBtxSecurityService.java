package top.cheesetree.btx.framework.security;

import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.framework.security.model.SecurityFuncDTO;
import top.cheesetree.btx.framework.security.model.SecurityMenuDTO;
import top.cheesetree.btx.framework.security.model.SecurityUserDTO;

import java.util.List;

/**
 * @Author: van
 * @Date: 2022/1/12 15:10
 * @Description: TODO
 */
public interface IBtxSecurityService {
    List<SecurityMenuDTO> getMenu(String userid, String authlevel);

    List<SecurityFuncDTO> getFunc(String userid);

    CommJSON<SecurityUserDTO> login(String loginid,
                                    String loginpwd);

    String gethdImgurl(String url, String userid);

    CommJSON changePwd(String userid, String loginpwd,
                       String newpwd);
}
