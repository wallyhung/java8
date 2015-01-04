package org.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 客户端报告实体
 * @author Xull
 * @version 1.0
 * @email wanxkl@gmail.com
 * @created 2014-3-1 pm 3:00
 * @description
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdFeedback extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 4634344886569514721L;
    private String slot_name;
    private String imei;
    private int    adid;
    private int    appid;
    /** (1:CPA 2:CPC) * */
    private int    type;
    /** 补推广告标示 * */
    private int    reissue;
    /** 当前广告组中第几个广告 * */
    private int    frame;
    /** 来源(1:push 2:suspend 3:spot_inside 4:spot_outside) * */
    private int    s_type;

    private String cuid;
    private long rt;

    //客户端版本名称
    private String vname;
    //客户端版本号
    private int    vcode;
    //客户端签名信息
    private String signs;
    //开发者应用包名
    private String pname;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public int getVcode() {
        return vcode;
    }

    public void setVcode(int vcode) {
        this.vcode = vcode;
    }

    public String getSigns() {
        return signs;
    }

    public void setSigns(String signs) {
        this.signs = signs;
    }

    public AdFeedback() {
    }


    public String getSlot_name() {
        return slot_name;
    }

    public void setSlot_name(String slot_name) {
        this.slot_name = slot_name;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public int getAdid() {
        return adid;
    }

    public void setAdid(int adid) {
        this.adid = adid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getReissue() {
        return reissue;
    }

    public void setReissue(int reissue) {
        this.reissue = reissue;
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public int getS_type() {
        return s_type;
    }

    public void setS_type(int s_type) {
        this.s_type = s_type;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public long getRt() {
        return rt;
    }

    public void setRt(long rt) {
        this.rt = rt;
    }
}
