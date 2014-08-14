package com.framework.spi.entity;

/**
 *
 * @author nelson
 */
public class CodeEntity {
    private long createTime = 0;
    private String code = "";
    private int count = 0;
    private long timeoutTime = 0;

    /**
     * @return the createTime
     */
    public long getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
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
