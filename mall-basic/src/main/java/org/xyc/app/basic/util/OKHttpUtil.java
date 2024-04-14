package org.xyc.app.basic.util;

import com.alibaba.fastjson2.JSON;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * HTTP请求工具包
 * @author xuyachang
 * @date 2024/2/26
 */
public class OKHttpUtil {

    /**
     * 饿汉式单例，保证内部使用时已经被初始化
     */
    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .retryOnConnectionFailure(false)
            .connectionPool(new ConnectionPool(200, 5, TimeUnit.MINUTES))
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .build();

    private OKHttpUtil(){}

    public static OkHttpClient getClient(){
        return client;
    }

    /**
     * get请求
     * @param url
     * @param param
     * @return
     */
    public static String get(String url,Map<String,String> param){
        HttpUrl builderUrl = HttpUrl.parse(url);
        if(Objects.isNull(builderUrl)){
            throw new NullPointerException("url不合法");
        }
        HttpUrl.Builder urlBuilder = builderUrl.newBuilder();
        if(Objects.nonNull(param)){
            param.forEach(urlBuilder::addQueryParameter);
        }
        HttpUrl httpUrl = urlBuilder.build();
        //构建请求
        Request request = new Request.Builder()
                .get()
                .url(httpUrl)
                .build();

        //执行请求
        return executeRequest(request);
    }

    /**
     * post发送json
     * @param url
     * @param param
     * @return
     */
    public static String postJson(String url,Object param){
        return postJson(url,JSON.toJSONString(param));
    }

    /**
     * post发送json
     * @param url
     * @param json
     * @return
     */
    public static String postJson(String url,String json){
        RequestBody body = RequestBody.create(json,MediaType.parse("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();

        return executeRequest(request);
    }

    /**
     * post发送form表单
     * @param url
     * @param param
     * @return
     */
    public static String postFrom(String url,Map<String,String> param){
        FormBody.Builder formBuilder = new FormBody.Builder();
        param.forEach(formBuilder::add);
        RequestBody formBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        //执行请求
        return executeRequest(request);
    }

    public static String postFile(String url,String fileUrl){
        File file = new File(fileUrl);
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(file,MediaType.parse("text/x-markdown; charset=utf-8")))
                .build();

        return executeRequest(request);
    }

    private static String executeRequest(Request request){
        //执行请求
        try (Response response = client.newCall(request).execute()){
            //返回响应
            return handleResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private static String handleResponse(Response response) throws IOException {
        if(response.isSuccessful() && 200 == response.code()){
            return response.body().string();
        }else{
            return response.message();
        }
    }
}
