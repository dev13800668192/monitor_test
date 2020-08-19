package com.example.monitor_test.service.imp;

import com.example.monitor_test.common.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author by
 * @version 1.0
 * @date 2020/7/29 9:40
 */
@Service
public class TestData {

    @Autowired
    HttpClientUtil httpClientUtil;

    @Scheduled(cron = "0/5 * * * * ?")
    public void test() {
        Long timestamp = System.currentTimeMillis();
        String url = "http://localhost:8080/api/monitor/client/pushData";
        String jsonParam = "{" +
                "        \"hostname\":\"DESKTOP-VJKTLSC\",\n" +
                "        \"ip\":\"10.0.2.254\",\n" +
                "        \"cpu\":\"72.5\",\n" +
                "        \"memory\":\"56\",\n" +
                "        \"gpu\":\"87\",\n" +
                "        \"fps\":\"60\",\n" +
                "        \"hardDisk\":\"42.25\",\n" +
                "        \"io\":\"52.15\",\n" +
                "        \"updateTime\":"+timestamp+"\n" +
                "    }" ;
        String s = httpClientUtil.doPostJson(url, jsonParam, null);
        System.out.println(jsonParam);
        System.out.println(s);
    }
}
