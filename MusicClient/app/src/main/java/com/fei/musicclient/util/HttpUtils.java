package com.fei.musicclient.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.List;

/**
 * Created by Fei on 02/06/2015.
 */
public class HttpUtils {

    public static final int HTTP_GET_REQUEST = 0;
    public static final int HTTP_POST_REQUEST = 1;

    public static HttpEntity httpRequest(int method,String uri, List<NameValuePair> pairs) throws IOException {

        HttpClient client = new DefaultHttpClient();
        HttpResponse response = null;

        switch (method) {
            case HTTP_GET_REQUEST:
                HttpGet get = new HttpGet(uri);
                response = client.execute(get);
                break;
            case HTTP_POST_REQUEST:
                HttpPost post = new HttpPost(uri);
                if (pairs!=null) {
                    HttpEntity entity = new UrlEncodedFormEntity(pairs);
                    post.setEntity(entity);
                    post.setHeader("Content-Type", "application/x-www-form-urlencoded");
                    response = client.execute(post);
                }
                break;
        }

        HttpEntity responseEntity = response.getEntity();

        return responseEntity;

    }
}
