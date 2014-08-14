package com.framework.httpclient;

import org.apache.http.client.CookieStore;

/**
 *
 * @author Administrator
 */
public final class HttpResponseEntity {
    private String result;
    private CookieStore cookie;
    public HttpResponseEntity( String result,CookieStore cookie){
        this.result = result;
        this.cookie = cookie;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * @return the cookie
     */
    public CookieStore getCookie() {
        return cookie;
    }

    /**
     * @param cookie the cookie to set
     */
    public void setCookie(CookieStore cookie) {
        this.cookie = cookie;
    }
}
