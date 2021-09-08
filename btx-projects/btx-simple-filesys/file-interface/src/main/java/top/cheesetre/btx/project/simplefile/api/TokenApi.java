package top.cheesetre.btx.project.simplefile.api;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.cheesetre.btx.project.simplefile.model.dto.TokenInfoDTO;
import top.cheesetree.btx.framework.core.json.CommJSON;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/11/20 14:59
 * @Version: 1.0
 * @Description:
 */
@RequestMapping("/token")
public interface TokenApi {

    @PostMapping("/get")
    CommJSON<TokenInfoDTO> getToken(JSONObject jo);
}
