package top.cheesetree.btx.framework.web.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import top.cheesetree.btx.framework.web.http.HttpsClientRequestFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: van
 * @Date: 2021/8/27 10:05
 * @Description: TODO
 */
@Slf4j
public class HttpUtil {
    private final static int DEF_TIMEOUT = 10000;
    private final static int DEF_CON_TIMEOUT = 1000;

    public static String httpGet(String url, boolean isHttps) {
        return httpGet(url, null, DEF_TIMEOUT, isHttps);
    }

    public static String httpGet(String url, int to, boolean isHttps) {
        return httpGet(url, null, to, isHttps);
    }

    public static String httpGet(String url, HashMap<String, String> headers, boolean isHttps) {
        return httpGet(url, headers, DEF_TIMEOUT, isHttps);
    }


    public static String httpPostJson(String url, String pa, boolean isHttps) {
        return httpPostJson(url, pa, DEF_TIMEOUT, isHttps);
    }

    public static String httpPostJson(String url, HashMap<String, String> headers, String pa, boolean isHttps) {
        return httpPost(url, pa, headers, DEF_TIMEOUT, isHttps);
    }

    public static String httpPostJson(String url, String pa, int to, boolean isHttps) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return httpPost(url, pa, headers, to, isHttps);
    }

    public static String httpAjaxPost(String url, HashMap<String, String> pa, boolean isHttps) {
        return httpAjaxPost(url, pa, DEF_TIMEOUT, isHttps);
    }

    public static String httpAjaxPost(String url, HashMap<String, String> pa, int to, boolean isHttps) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

        if (pa != null) {
            Iterator<Map.Entry<String, String>> iter = pa.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                param.add(entry.getKey(), entry.getValue());
            }
        }

        return httpPost(url, param, headers, to, isHttps);
    }

    /**
     * get方法
     */
    public static String httpGet(String url, HashMap<String, String> headers, int to, boolean isHttps) {
        String ret = "";
        RestTemplate restTemplate = geRestTemplate(isHttps, to);
        HttpHeaders header = new HttpHeaders();

        if (headers != null) {
            Iterator<Map.Entry<String, String>> iter = headers.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                header.add(entry.getKey(), entry.getValue());
            }
        }

        HttpEntity<HashMap<String, String>> httpEntity = new HttpEntity<HashMap<String, String>>(header);
        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        if (HttpStatus.OK.equals(res.getStatusCode())) {
            ret = res.getBody();
        } else {
            log.error("REQ ERROR:URL[{}] RES[{}]", url, res);
        }

        return ret;
    }

    public static <T> String httpPost(String url, T params, HashMap<String, String> headers, int to, boolean isHttps) {
        String ret = "";
        RestTemplate restTemplate = geRestTemplate(isHttps, to);
        HttpHeaders header = new HttpHeaders();

        if (headers != null) {
            Iterator<Map.Entry<String, String>> iter = headers.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                header.add(entry.getKey(), entry.getValue());
            }
        }

        if (!headers.containsKey(HttpHeaders.CONTENT_TYPE)) {
            header.put(HttpHeaders.CONTENT_TYPE, Arrays.asList(MediaType.APPLICATION_JSON_VALUE));
            header.put(HttpHeaders.ACCEPT, Arrays.asList(MediaType.APPLICATION_JSON_VALUE));
        }

        HttpEntity<T> httpEntity = new HttpEntity<T>(params, header);
        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        if (HttpStatus.OK.equals(res.getStatusCode())) {
            ret = res.getBody();
        } else {
            log.error("REQ ERROR:URL[{}] RES[{}][{}]", url, res, res.getBody());
        }

        return ret;
    }

    public static RestTemplate geRestTemplate(boolean isHttps, int timeout) {
        RestTemplate restTemplate;
        if (isHttps) {
            HttpsClientRequestFactory hcr = new HttpsClientRequestFactory();
            hcr.setConnectTimeout(DEF_CON_TIMEOUT);
            hcr.setReadTimeout(timeout);
            restTemplate = new RestTemplate(hcr);
        } else {
            SimpleClientHttpRequestFactory hrf = new SimpleClientHttpRequestFactory();
            hrf.setConnectTimeout(DEF_CON_TIMEOUT);
            hrf.setReadTimeout(timeout);
            restTemplate = new RestTemplate(hrf);
        }

        return restTemplate;
    }

}