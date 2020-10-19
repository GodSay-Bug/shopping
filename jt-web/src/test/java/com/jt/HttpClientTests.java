package com.jt;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @Author WL
 * @Date 2020-10-19 10:32
 * @Version 1.0
 * <p>
 * 测试HttpClient
 */
public class HttpClientTests {


    /**
     * 要求：在java代码内部，获取百度页面
     * 步骤：
     * 确定地址：https://www.baidu.com
     * 创建httpclient对象
     * 创建请求类型
     * 发送http请求，并取得响应的结果，之后判断状态码是否是200
     * 如果请求正确，对获取的数据再加工
     */
    @Test
    public void testGet() throws IOException {

        String url = "https://www.baidu.com";

        // import org.apache.http.impl.client.HttpClients;
        HttpClient httpClient = HttpClients.createDefault();
        // 请求方式
        HttpGet httpGet = new HttpGet(url);


        HttpResponse execute = httpClient.execute(httpGet);
        //  响应是否成功，响应码是否为200
        int statusCode = execute.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            // 获取响应对象
            HttpEntity entity = execute.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            System.out.println(result);
        }
    }


}
