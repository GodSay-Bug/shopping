package com.jt.service;

import com.jt.pojo.Item;
import com.jt.util.ObjectMapperUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author WL
 * @Date 2020-10-19 16:12
 * @Version 1.0
 * 远程调用
 */
@Service
public class HttpClientServiceImpl implements HttpClientService {

    // TODO
    @Override
    public List<Item> getItems() {

        String url = "http://manage.jt.com/getItems";

        HttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);

        List<Item> itemList = new ArrayList<>();
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(httpResponse.getEntity());
                if (!StringUtils.isEmpty(result)) {
                    itemList = ObjectMapperUtil.toObject(result, itemList.getClass());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemList;
    }
}
