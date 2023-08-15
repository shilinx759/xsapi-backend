package com.xsapi.xsapiinterface.client;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.xsapi.xsapiinterface.model.User;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

import static com.xsapi.xsapiinterface.util.SignUtil.getSign;

/**
 * @author slx
 */
public class XsApiClient {

    private String accessKey;

    private String secretKey;

    public XsApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result= HttpUtil.get("http://localhost:8123/api/name/", paramMap);
        return result;
    }
    public String getNameByPost(@RequestParam String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result= HttpUtil.post("http://localhost:8123/api/name/", paramMap);
        return result;
    }

    public Map<String, String> getHeader(String body) {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", accessKey);
        //密钥一定不能直接发送
        hashMap.put("body", body);
        hashMap.put("sign", getSign(body,secretKey));
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
        return hashMap;
    }

    public String getUsernameByPost( User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post("http://localhost:8123/api/name/user")
                .body(json)
                .addHeaders(getHeader(json))
                .execute();
        System.out.println("httpResponse body= " + httpResponse.body());
        System.out.println("httpResponse status= " + httpResponse.getStatus());
        return httpResponse.body();
    }
}
