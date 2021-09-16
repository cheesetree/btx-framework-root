package top.cheesetree.btx.project.simplefile.Interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.cheesetree.btx.framework.cache.redis.RedisTemplateFactoryImpl;
import top.cheesetree.btx.framework.core.json.CommJSON;
import top.cheesetree.btx.project.simplefile.comm.FileConsts;
import top.cheesetree.btx.project.simplefile.comm.FileErrorMessage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/12/22 16:48
 * @Version: 1.0
 * @Description:
 */
@Slf4j
public class ApiAuthInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private RedisTemplateFactoryImpl redisTemplateFactory;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        boolean ret = false;

        String tk = request.getHeader("Authorization");
        if (StringUtils.isNoneBlank(tk)) {
            ret = redisTemplateFactory.generateRedisTemplate(String.class).hasKey(tk);
        }

        if (!ret) {
            tk = FileConsts.REDIS_FILE_TMP_KEY + tk;
            //一次性 key
            if (redisTemplateFactory.generateRedisTemplate(String.class).hasKey(tk)) {
                redisTemplateFactory.generateRedisTemplate(String.class).delete(tk);
                ret = true;
            } else {
                CommJSON<String> r = new CommJSON(FileErrorMessage.TOKEN_UNEXIST);
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(JSON.toJSONBytes(r, SerializerFeature.WriteMapNullValue));
            }
        }

        return ret;
    }
}
