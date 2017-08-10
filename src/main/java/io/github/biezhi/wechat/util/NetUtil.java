package io.github.biezhi.wechat.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.github.biezhi.wechat.Utils;
import okhttp3.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by haoxiongshan on 2017/8/10.
 */
public class NetUtil {

    private static final Logger log = LoggerFactory.getLogger(NetUtil.class);

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static int connTimeout = 10;
    private static int writeTimeout = 20;
    private static int readTimeout = 10;

    private String cookie = "";

    static{
        Properties prop =  System.getProperties();
        if(prop != null && !prop.isEmpty()){
            String strConnTimeout = prop.getProperty("http.conn-time-out");
            if(StringUtils.isNotEmpty(strConnTimeout)){
                connTimeout = Integer.parseInt(strConnTimeout);
            }
            String strReadTimeout = prop.getProperty("http.read-time-out");
            if(StringUtils.isNotEmpty(strReadTimeout)){
                readTimeout = Integer.parseInt(strReadTimeout);
            }
            String strWriteTimeout = prop.getProperty("http.write-time-out");
            if(StringUtils.isNotEmpty(strWriteTimeout)){
                writeTimeout = Integer.parseInt(strWriteTimeout);
            }
        }
    }

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(connTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .build();

    private String doGet(String url, Map<String, Object>... params) {
        return doGet(url, null, params);
    }

    private String doGet(String url, String cookie, Map<String, Object>... params) {
        if (null != params && params.length > 0) {
            Map<String, Object> param = params[0];
            Set<String> keys = param.keySet();
            StringBuilder sbuf = new StringBuilder(url);
            if (url.contains("=")) {
                sbuf.append("&");
            } else {
                sbuf.append("?");
            }
            for (String key : keys) {
                sbuf.append(key).append('=').append(param.get(key)).append('&');
            }
            url = sbuf.substring(0, sbuf.length() - 1);
        }
        try {
            Request.Builder requestBuilder = new Request.Builder().url(url);

            if (null != cookie) {
                requestBuilder.addHeader("Cookie", cookie);
            }

            Request request = requestBuilder.build();

            log.debug("[*] 请求 => {}\n", request);

            Response response = client.newCall(request).execute();
            String body = response.body().string();

            log.debug("[*] 响应 => {}", body);
            return body;
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    private JsonElement doPost(String url, Object object) {
        String bodyJson = null;
        RequestBody requestBody = RequestBody.create(JSON, "");
        if (null != object) {
            bodyJson = Utils.toJson(object);
            requestBody = RequestBody.create(JSON, bodyJson);
        }

        Request.Builder requestBuilder = new Request.Builder().url(url).post(requestBody);
        if (null != cookie) {
            requestBuilder.addHeader("Cookie", cookie);
        }

        Request request = requestBuilder.build();

        log.debug("[*] 请求 => {}\n", request);
        try {
            Response response = client.newCall(request).execute();
            String body = response.body().string();
            if (null != body && body.length() <= 300) {
                log.debug("[*] 响应 => {}", body);
            }
            return new JsonParser().parse(body);
        } catch (Exception e) {
            log.error("", e);
            return new JsonParser().parse("{}");
        }
    }

}
