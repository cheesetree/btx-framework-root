/*
 * MIT License
 *
 * Copyright (c) 2021 yedf
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package top.cheesetree.btx.project.dts.dtm.client.client;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import top.cheesetree.btx.project.dts.dtm.client.common.config.BtxDtmProperties;
import top.cheesetree.btx.project.dts.dtm.client.common.model.DtmConsumer;
import top.cheesetree.btx.project.dts.dtm.client.common.model.DtmServerInfo;
import top.cheesetree.btx.project.dts.dtm.client.tcc.Tcc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author lixiaoshuang
 */
@Component
@Slf4j
public class DtmClient {
    private BtxDtmProperties btxDtmProperties;

    public DtmClient(BtxDtmProperties btxDtmProperties) {
        this.btxDtmProperties = btxDtmProperties;
    }

    /**
     * 生成全局事务id
     *
     * @return
     * @throws Exception
     */
    public String genGid() throws Exception {
        DtmServerInfo dtmServerInfo = new DtmServerInfo(btxDtmProperties.getTccUrl());
        JSONObject jsonObject = null;
        try {
            ResponseEntity<String> res = getRestTemplate().getForEntity(dtmServerInfo.newGid(), String.class);
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

    /**
     * tcc事务
     *
     * @param gid
     * @param function
     * @return
     * @throws Exception
     */
    public void tccGlobalTransaction(String gid, DtmConsumer<Tcc> function) throws Exception {
        Tcc tcc = new Tcc(btxDtmProperties, gid);
        tcc.tccGlobalTransaction(function);
    }

    public RestTemplate getRestTemplate() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        fastConverter.setSupportedMediaTypes(supportedMediaTypes);

        //创建配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        SimpleClientHttpRequestFactory hrf = new SimpleClientHttpRequestFactory();
        hrf.setConnectTimeout(1000);
        hrf.setReadTimeout(btxDtmProperties.getTccTimeOut());
        RestTemplate rt =
                new RestTemplateBuilder().additionalMessageConverters(fastConverter).build();
        rt.setRequestFactory(hrf);
        return rt;
    }
}
