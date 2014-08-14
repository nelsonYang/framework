package com.framework.spi.entity;

/**
 *
 * @author nelson
 */
public class SessionEntity {
    private String sessionId = "";
    private String session  = "";
    private String token = "";
    private long touchTime = 0;
    private long timeoutTime = 0;

    /**
     * @return the session
     */
    public String getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(String session) {
        this.session = session;
    }

    /**
     * @return the createTime
     */
    public long getTouchTime() {
        return touchTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setTouchTime(long touchTime) {
        this.touchTime = touchTime;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return the timeoutTime
     */
    public long getTimeoutTime() {
        return timeoutTime;
    }

    /**
     * @param timeoutTime the timeoutTime to set
     */
    public void setTimeoutTime(long timeoutTime) {
        this.timeoutTime = timeoutTime;
    }
}
