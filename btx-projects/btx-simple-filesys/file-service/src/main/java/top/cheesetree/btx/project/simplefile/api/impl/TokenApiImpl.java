package top.cheesetree.btx.project.simplefile.api.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.cheesetre.btx.project.simplefile.api.TokenApi;
import top.cheesetre.btx.project.simplefile.model.dto.TokenInfoDTO;
import top.cheesetree.btx.framework.cache.redis.RedisTemplateFactoryImpl;
import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.project.simplefile.comm.FileConsts;
import top.cheesetree.btx.project.simplefile.comm.FileErrorMessage;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/11/20 15:11
 * @Version: 1.0
 * @Description:
 */
@RestController
@Slf4j
public class TokenApiImpl implements TokenApi {
    @Autowired
    RedisTemplateFactoryImpl redisTemplateFactory;

    @Override
    public CommJSON<TokenInfoDTO> getToken(@RequestBody JSONObject jo) {
        CommJSON<TokenInfoDTO> ret;

        String appid = jo.getString("appid");
        String key = jo.getString("appsecret");

        RedisTemplate rt = redisTemplateFactory.generateRedisTemplate(String.class);
        if (rt.opsForHash().get(FileConsts.REDIS_SYS_KEY, appid).equals(key)) {
            Object otk = rt.opsForHash().get(FileConsts.REDIS_TOKEN_KEY, appid);
            if (otk != null) {
                rt.expire(otk.toString(), 10, TimeUnit.SECONDS);
            }
            String tk = UUID.randomUUID().toString();
            int expire = 3600;
            TokenInfoDTO t = new TokenInfoDTO();
            t.setAccesstoken(tk);
            t.setExpiresin(expire);

            rt.opsForValue().set(tk, "true", expire, TimeUnit.SECONDS);
            rt.opsForHash().put(FileConsts.REDIS_TOKEN_KEY, appid, tk);
            ret = new CommJSON(t);
        } else {
            ret = new CommJSON(FileErrorMessage.SYS_UNAUTH);
        }

        return ret;
    }
}
