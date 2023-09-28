package com.lee.senlouapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.lee.senlouapiclientsdk.model.User;

import java.util.HashMap;
import java.util.Map;

import static com.lee.senlouapiclientsdk.utils.SignUtils.genSign;

/**
 * 测试调用第三方接口的客户端
 */
public class SenlouApiClient {
    public static final String GATEWAY_HOST = "http://localhost:8090";
    private String accessKey;
    private String secretKey;

    public SenlouApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name){
        //Hutool调用第三方api
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        String result = HttpUtil.get(GATEWAY_HOST + "/api/name/get", paramMap);
        System.out.println(result);
        return result;
    }

    public String getNameByPost( String name){
        //Hutool调用第三方api
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        String result = HttpUtil.post(GATEWAY_HOST + "/api/name/post", paramMap);
        System.out.println(result);
        return result;
    }

    private Map<String,String> getHeaderMap(String body){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
//        hashMap.put("secretKey",secretKey);//密码不能直接放到请求头中
        hashMap.put("nonce", RandomUtil.randomNumbers(4));//生成随机数
        hashMap.put("body",body);//请求体
        hashMap.put("timestamp",String.valueOf(System.currentTimeMillis() / 1000));//当前时间戳，防重放
        hashMap.put("sign",genSign(body,secretKey ));//加密生成的签名
        return hashMap;
    }

    public String getUsernameByPost(User user){
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/name/user")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return result;
    }
}
