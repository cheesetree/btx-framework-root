package top.cheesetree.btx.framework.security;

import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.framework.security.model.SecurityUserDTO;

/**
 * @Author: van
 * @Date: 2022/1/12 15:10
 * @Description: TODO
 */
public interface IBtxSecurityUserService {
    CommJSON<SecurityUserDTO> login(String loginid,
                                    String loginpwd);

    CommJSON logout();

    CommJSON<String> gethdImgurl(String url, String userid);

    CommJSON changePwd(String userid, String loginpwd,
                       String newpwd);
}
