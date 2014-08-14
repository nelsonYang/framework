package com.framework.httpclient;

import java.io.File;
import java.util.Map;
import org.apache.http.client.CookieStore;

/**
 *
 * @author Administrator
 */
public interface HttpConnection {

    public HttpResponseEntity executePost(String url, Map<String, String> parameters, CookieStore cookie);

    public HttpResponseEntity executePost(String url, CookieStore cookie);

    public HttpResponseEntity executeGet(String url, CookieStore cookie);

    public HttpResponseEntity executeGet(String url, CookieStore cookie, Map<String, String> headers);

    public HttpResponseEntity executePost(String url, Map<String, String> parameters, CookieStore cookie, Map<String, String> headers);
    
    public HttpResponseEntity executePost(String url,String jsonData, CookieStore cookie);
    
    public HttpResponseEntity executePostFile(String url,File file,String fileName,Map<String,String> parameters, CookieStore cookie);
}
