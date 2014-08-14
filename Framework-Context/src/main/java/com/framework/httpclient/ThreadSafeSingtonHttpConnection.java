package com.framework.httpclient;

import com.frameworkLog.factory.LogFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public class ThreadSafeSingtonHttpConnection implements HttpConnection {

    private final Logger logger = LogFactory.getInstance().getLogger(ThreadSafeSingtonHttpConnection.class);
    private DefaultHttpClient httpClient;

    public ThreadSafeSingtonHttpConnection() {
        int maxTotal = 300;
        int maxPerRout = 200;
        int connecttimeOut = 10000;
        int readTimeOut = 5000;
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager();
        cm.setMaxTotal(maxTotal);
        cm.setDefaultMaxPerRoute(maxPerRout);
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, connecttimeOut);
        HttpConnectionParams.setSoTimeout(params, readTimeOut);
        httpClient = new DefaultHttpClient(cm, params);
    }

    @Override
    public HttpResponseEntity executePost(String url, Map<String, String> parameters, CookieStore cookie) {
        if (cookie != null) {
            httpClient.setCookieStore(cookie);
        }
        HttpResponseEntity httpResponseEntity = null;
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        StringEntity entity;
        try {
            entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
            post.setEntity(entity);
            HttpResponse response = httpClient.execute(post);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity);
            CookieStore cookieStore = httpClient.getCookieStore();
            httpResponseEntity = new HttpResponseEntity(result, cookieStore);
        } catch (Exception ex) {
            logger.error("executePost 出现异常", ex);
        } finally {
            post.abort();
        }
        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity executePost(String url, CookieStore cookie) {
        if (cookie != null) {
            httpClient.setCookieStore(cookie);
        }
        HttpResponseEntity httpResponseEntity = null;
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        try {
            HttpResponse response = httpClient.execute(post);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity);
            CookieStore cookieStore = httpClient.getCookieStore();
            httpResponseEntity = new HttpResponseEntity(result, cookieStore);
        } catch (Exception ex) {
            logger.error("executePost 出现异常", ex);
        } finally {
            post.abort();
        }
        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity executeGet(String url, CookieStore cookie) {
        if (cookie != null) {
            httpClient.setCookieStore(cookie);
        }
        HttpResponseEntity httpResponseEntity = null;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity);
            CookieStore cookieStore = httpClient.getCookieStore();
            httpResponseEntity = new HttpResponseEntity(result, cookieStore);
        } catch (Exception ex) {
            logger.error("executeGet 出现异常", ex);
        } finally {
            httpGet.abort();
        }
        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity executeGet(String url, CookieStore cookie, Map<String, String> headers) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        if (cookie != null) {
            httpClient.setCookieStore(cookie);
        }
        HttpResponseEntity httpResponseEntity = null;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity);
            CookieStore cookieStore = httpClient.getCookieStore();
            httpResponseEntity = new HttpResponseEntity(result, cookieStore);
        } catch (Exception ex) {
            logger.error("executeGet 出现异常", ex);
        } finally {
            httpGet.abort();
            httpClient = null;
        }
        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity executePost(String url, Map<String, String> parameters, CookieStore cookie, Map<String, String> headers) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        if (cookie != null) {
            httpClient.setCookieStore(cookie);
        }
        HttpResponseEntity httpResponseEntity = null;
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            post.addHeader(entry.getKey(), entry.getValue());
        }
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        StringEntity entity;
        try {
            entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
            post.setEntity(entity);
            HttpResponse response = httpClient.execute(post);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity);
            CookieStore cookieStore = httpClient.getCookieStore();
            httpResponseEntity = new HttpResponseEntity(result, cookieStore);
        } catch (Exception ex) {
            logger.error("executePost 出现异常", ex);
        } finally {
            post.abort();
            httpClient = null;
        }
        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity executePost(String url, String jsonData, CookieStore cookie) {
        if (cookie != null) {
            httpClient.setCookieStore(cookie);
        }
        HttpResponseEntity httpResponseEntity = null;
        HttpPost post = new HttpPost(url);
        try {
            StringEntity myEntity = new StringEntity(jsonData, "UTF-8");
            post.setHeader("Connection", "close");
            post.addHeader("Content-Type", "text/json;charset=utf-8");
            post.setEntity(myEntity);
            HttpResponse response = httpClient.execute(post);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity);
            CookieStore cookieStore = httpClient.getCookieStore();
            httpResponseEntity = new HttpResponseEntity(result, cookieStore);
        } catch (Exception ex) {
            logger.error("executePost 出现异常", ex);
        } finally {
            post.abort();
        }
        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity executePostFile(String url, File file, String fileName, Map<String, String> parameters, CookieStore cookie) {
        if (cookie != null) {
            httpClient.setCookieStore(cookie);
        }
        HttpResponseEntity httpResponseEntity = null;
        HttpPost post = new HttpPost(url);
        try {
            MultipartEntity mulentity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            post.setHeader("Connection", "close");
            post.addHeader("Content-Type", "application/ostream;charset=utf-8");
//            FileBody filebody = new FileBody(file);        
            FileBody filebody = new FileBody(file, fileName);
            //  mulentity.addPart("fileName",filebody);  
            FormBodyPart fromBody = new FormBodyPart("media", filebody);
            mulentity.addPart(fromBody);
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                mulentity.addPart(entry.getKey(), new StringBody(entry.getValue()));
            }
            post.setEntity(mulentity);
            HttpResponse response = httpClient.execute(post);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity);
            CookieStore cookieStore = httpClient.getCookieStore();
            httpResponseEntity = new HttpResponseEntity(result, cookieStore);
        } catch (Exception ex) {
            logger.error("executePost 出现异常", ex);
        } finally {
            post.abort();
            httpClient = null;
        }
        return httpResponseEntity;
    }
}
