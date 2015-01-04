package org.pojo;

/**
 * 实体基类
 *
 * @author Xull
 * @version 1.0
 * @email wanxkl@gmail.com
 * @created 2014-2-27
 * @description
 */
public class BaseEntity {
    private String ip; //客户端IP地址
    private String ua; //客户端USER-AGENT
    private long   timestamp;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
