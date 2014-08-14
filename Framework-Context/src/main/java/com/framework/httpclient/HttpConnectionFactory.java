package com.framework.httpclient;

import com.framework.enumeration.HttpConnectionEnum;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public final class HttpConnectionFactory {

    private static HttpConnectionFactory httpConnectionFactory;
    private static final Map<HttpConnectionEnum, HttpConnection> httpConnectionMap = new HashMap<HttpConnectionEnum, HttpConnection>(2, 1);

    static {
        httpConnectionMap.put(HttpConnectionEnum.MULTION, new MultionHttpConnection());
        httpConnectionMap.put(HttpConnectionEnum.THREAD_SAFE_SINGTON, new ThreadSafeSingtonHttpConnection());
    }

    public static synchronized HttpConnectionFactory getInstance() {
        if (httpConnectionFactory == null) {
            httpConnectionFactory = new HttpConnectionFactory();
        }
        return httpConnectionFactory;
    }

    public HttpConnection getHttpClient(HttpConnectionEnum httpClientEnum) {
        return httpConnectionMap.get(httpClientEnum);
    }
}
