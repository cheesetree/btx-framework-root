package top.cheesetree.btx.project.simplefile.api.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import top.cheesetre.btx.project.simplefile.api.TokenApi;
import top.cheesetre.btx.project.simplefile.model.dto.TokenInfoDTO;
import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.project.simplefile.comm.FileConsts;
import top.cheesetree.btx.project.simplefile.comm.FileErrorMessage;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/11/20 15:11
 * @Version: 1.0
 * @Description:
 */
@Service
@ResponseBody
@Slf4j
public class TokenApiImpl implements TokenApi {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public CommJSON<TokenInfoDTO> getToken(@RequestBody JSONObject jo) {
        CommJSON<TokenInfoDTO> ret;

        String appid = jo.getString("appid");
        String key = jo.getString("appsecret");
        if (redisTemplate.opsForHash().get(FileConsts.REDIS_SYS_KEY, appid).equals(key)) {
            String tk = UUID.randomUUID().toString();
            int expire = 3600;
            TokenInfoDTO t = new TokenInfoDTO();
            t.setAccesstoken(tk);
            t.setExpiresin(expire);

            redisTemplate.opsForValue().set(tk, "true", expire);
            ret = new CommJSON(t);
        } else {
            ret = new CommJSON(FileErrorMessage.SYS_UNAUTH);
        }

        return ret;
    }
}
