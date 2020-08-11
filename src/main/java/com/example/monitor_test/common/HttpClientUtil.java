package com.example.monitor_test.common;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author by
 * @version 1.0
 * @date 2020/5/13 14:31
 */
@Service
public class HttpClientUtil {
    /**
     * 带参数且设置请求头的get请求
     * @param url
     * @param headerParam
     * @param param
     * @return
     */
    public String doGet(String url,Map<String,String> headerParam,Map<String,String> param){
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try{
            //创建url
            URIBuilder builder =new URIBuilder(url);
            if (param != null){
                for (String key:param.keySet()){
                    builder.addParameter(key,param.get(key).toString());
                }
            }
            URI uri = builder.build();
            //创建http Get请求
            HttpGet httpGet = new HttpGet(uri);
            //设置请求头
            if (headerParam != null){
                for (String key:headerParam.keySet()){
                    httpGet.addHeader(key,headerParam.get(key).toString());
                }
            }
            //执行请求
            response = httpClient.execute(httpGet);
            //判断返回状态是否为200
            if (response.getStatusLine().getStatusCode()==200){
                resultString = EntityUtils.toString(response.getEntity(),"UTF-8");

            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if (response!=null){
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 不带参数的get请求
     * @param url
     * @return
     */
    public  String doGet(String url){
        return doGet(url,null,null);
    }

    /**
     * 不带参数，带请求头的get请求
     * @param url
     * @param headerParam
     * @return
     */
    public String doGet(String url,Map<String,String> headerParam){
        return doGet(url, headerParam,null);
    }

  /**
     * 带参数，带请求头的post请求
     * @param url
     * @param param
     * @return
     */
    public  String doPost(String url,Map<String,String> headerParam,Map<String,String> param){
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try{
            //创建Http Post请求
            HttpPost httpPost = new HttpPost(url);

            //创建参数列表
            if (param!=null){
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key:param.keySet()){
                    paramList.add(new BasicNameValuePair(key,param.get(key)));
                }
                //模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }

            //设置请求头
            if (headerParam != null){
                for (String key:headerParam.keySet()){
                    httpPost.addHeader(key,headerParam.get(key).toString());
                }
            }

            //执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 不带参数的post请求
     * @param url
     * @return
     */
    public  String doPost(String url){
        return doPost(url,null,null);
    }


    /**
     * 传送json类型的post请求
     * @param url
     * @param json
     * @return
     */
    public  String doPostJson(String url,String json,Map<String,String> headerParam){
        //创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try{
            //创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //设置请求头
            if (headerParam != null){
                for (String key:headerParam.keySet()){
                    httpPost.addHeader(key,headerParam.get(key).toString());
                }
            }
            //创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            //执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(),"utf-8");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;

    }
}
