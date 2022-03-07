package top.cheesetree.btx.project.dts.dtm.client.client;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import top.cheesetree.btx.project.dts.dtm.client.common.config.BtxDtmProperties;
import top.cheesetree.btx.project.dts.dtm.client.common.model.DtmConsumer;
import top.cheesetree.btx.project.dts.dtm.client.common.model.DtmServerInfo;
import top.cheesetree.btx.project.dts.dtm.client.tcc.Tcc;

import java.util.Objects;

/**
 * @author van
 * @date 2022/3/1 19:57
 * @description TODO
 */
@Component
@Slf4j
public class DtmClient {
    @Autowired
    BtxDtmProperties btxDtmProperties;
    @Autowired
    RestTemplate dtmRestTemplate;

    public String genGid() throws Exception {
        DtmServerInfo dtmServerInfo = new DtmServerInfo(btxDtmProperties.getTccUrl());
        JSONObject jsonObject = null;
        try {
            ResponseEntity<String> res = dtmRestTemplate.getForEntity(dtmServerInfo.newGid(), String.class);
            if (HttpStatus.OK.equals(res.getStatusCode())) {
                jsonObject = JSONObject.parseObject(res.getBody());
            } else {
                log.error("REQ ERROR:URL[{}] RES[{}]", dtmServerInfo.newGid(), res);
            }
        } catch (RestClientException e) {
            throw new Exception("Can’t get gid, please check the dtm server.");
        }

        if (Objects.isNull(jsonObject)) {
            throw new Exception("Can’t get gid, please check the dtm server.");
        }
        Object code = jsonObject.get("code");
        if (null != code && (int) code > 0) {
            Object message = jsonObject.get("message");
            throw new Exception(message.toString());
        }
        return jsonObject.get("gid").toString();
    }

    public void tccGlobalTransaction(String gid, DtmConsumer<Tcc> function) throws Exception {
        Tcc tcc = new Tcc(btxDtmProperties.getTccUrl(), gid);
        tcc.tccGlobalTransaction(function);
    }

}
