package org.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
/**
 * 广告表，用于广告管理，广告池
 * 运营后台提供
 */
@JsonIgnoreProperties(ignoreUnknown = true) //不存在的字段不转换
//@JsonInclude(JsonInclude.Include.NON_NULL) //为null的字段不转换
public class Ad implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 广告ID
     * 广告服务网关下发广告的唯一ID，统计计费的唯一标识
     * 从1开始顺序往上加，每添加一条记录+1
     */
    private int adid;
    //广告形式 1：推送 2：插屏 3：推送加插屏
    private int adStyle = 1;

    /************************************以下是推送信息****************************************************/
    /**
     * 推送广告分组，用于广告池分组
     */
    private int    pushAdGroup    = 4;
    /**
     * 推送投放量(旧:amount)日投放上限
     */
    private int    pushAmount     = 1;
    /**
     * 广告url，外链或应用墙的app下载地址，是web服务器的uri，相对路径
     */
    private String appUrl         = "";
    /**
     * 用户是否可以主动删除推送消息，默认不可以(旧cancelWay) 0:不可以 1：可以
     */
    private byte   pushCancelWay  = 0;
    /**
     * 渠道
     */
    private String channel        = "自有";
    /**
     * 推送建立时间(旧：createTime)
     */
    private Date   pushCreateTime = new Date();
    /**
     * 推送投放开始时间(旧:startTime)
     */
    private Date   pushStartTime  = new Date();
    /**
     * 推送投放结束时间(旧:endTime)
     */
    private Date   pushEndTime    = new Date();
    /**
     * 推送通知栏图标uri,用户可替换(icon)
     */
    private String pushIcon       = "";
    /**
     * 推送介绍(intro)
     */
    private String pushIntro      = "";
    /**
     * 推送权值,广告池调整顺序用
     */
    private int    pushNo         = 1;
    /**
     * 推送广告单价(price)
     */
    private float  pushPrice      = 1;
    /**
     * 推送状态 1:下线2:暂停3：投放中
     */
    private int    pushStatus     = 2;
    /**
     * 推送标题，推送通知栏中的标题(title)
     */
    private String pushTitle      = "";
    /**
     * 广告类型1：CPA(打开应用介绍) 2：CPC(打开外链页面)
     */
    private int type;

    /***********************************以下是墙池专有信息***************************************************/
    /**
     * 标识是否是墙池:0：否,1：是
     */
    private int codetype   = 0;
    /**
     * 墙池权值
     */
    private int wallweight = 1;
    /**
     * 墙上运行状态(1下线，2暂停，3投放中)
     */
    private int wallstatus = 2;

    /**********************************以下是悬窗池专有信息*************************************************************/
    /**
     * 标识是否是悬窗:0：否,1：是
     */
    private int isfloatwindow     = 0;
    /**
     * 悬窗池权值
     */
    private int floatwindowweight = 1;
    /**
     * 悬窗运行状态(1下线，2暂停，3投放中)
     */
    private int floatwindowstatus = 2;

    /***************************************以下是原adapp中的内容(广告)**************************************************/
    /**
     * APP的下载地址
     */
    private String apk = "";
    /**
     * APP作者
     */
    private String author;
    /**
     * 原APK解析出应用图标
     */
    private String sourceIcon  = "";
    /**
     * CPA广告包名
     */
    private String packageName       = "";
    /**
     *app介绍(intro)
     */
    private String appIntro    = "";
    /**
     * 应用名称(name)
     */
    private String appName     = "";
    /**
     *截图1
     */
    private String screenshot1 = "";
    /**
     *截图2
     */
    private String screenshot2 = "";
    /**
     *截图3
     */
    private String screenshot3;
    /**
     *截图4
     */
    private String screenshot4;
    /**
     *应用大小
     */
    private String size    = "";
    /**
     *宣传口号
     */
    private String slogan  = "";
    /**
     *广告摘要
     */
    private String summary = "";
    /**
     *版本
     */
    private String version = "";

    /***************************************以下是插屏中的内容**********************************************/
    /**
     * 插屏广告单价
     */
    private float  spotPrice     = 1;
    /**
     * 内弹推广图默认显示：1：常规图 2：吸引图
     */
    private int    defaultPhoto  = 1;
    /**
     * 常规图1
     */
    private String commonPhoto1  = "";
    /**
     * 常规图2
     */
    private String commonPhoto2  = "";
    /**
     * 吸引图1
     */
    private String attractPhoto1 = "";
    /**
     * 吸引图2
     */
    private String attractPhoto2 = "";

    /**
     * 外弹图1
     */
    private String outerPhoto1    = "";
    /**
     * 外弹图2
     */
    private String outerPhoto2    = "";
    /**
     * 插屏广告投放量
     */
    private int    spotAmount     = 1;
    /**
     * 结算认定标准
     */
    private String settleStand    = "安装";
    /**
     * 结算周期
     */
    private String settleCycle    = "周";
    /**
     * 认证率
     */
    private float  certiRate      = 0;
    /**
     * 插屏广告创建时间
     */
    private Date   spotCreateTime = new Date();
    /**
     * 插屏投放开始时间
     */
    private Date   spotStartTime  = new Date();
    /**
     * 投放结束时间
     */
    private Date   spotEndTime    = new Date();
    /**
     * 是否强测，默认否，0:否1：是
     */
    private int    forceTest      = 0;
    /**
     * 支付比例(默认为100%)
     */
    private int    payRatio       = 100;
    /**
     * 插屏权值
     */
    private int    soptWeight     = 1;

    /**
     * 状态，1：下线  2:暂停 3：投放中
     */
    private int  spotStatus  = 2;
    /**
     * 是否强制下载：1是 2否
     */
    private int  enforceDown = 1;
    /**
     * 广告分类  AppCategory ID
     */
    private int  cid         = 1;
    /**
     * 维护时间
     */
    private Date updateTime  = new Date();

    public int getAdid() {
        return adid;
    }

    public void setAdid(int adid) {
        this.adid = adid;
    }

    public int getAdStyle() {
        return adStyle;
    }

    public void setAdStyle(int adStyle) {
        this.adStyle = adStyle;
    }

    public int getPushAdGroup() {
        return pushAdGroup;
    }

    public void setPushAdGroup(int pushAdGroup) {
        this.pushAdGroup = pushAdGroup;
    }

    public int getPushAmount() {
        return pushAmount;
    }

    public void setPushAmount(int pushAmount) {
        this.pushAmount = pushAmount;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public byte getPushCancelWay() {
        return pushCancelWay;
    }

    public void setPushCancelWay(byte pushCancelWay) {
        this.pushCancelWay = pushCancelWay;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Date getPushCreateTime() {
        return pushCreateTime;
    }

    public void setPushCreateTime(Date pushCreateTime) {
        this.pushCreateTime = pushCreateTime;
    }

    public Date getPushStartTime() {
        return pushStartTime;
    }

    public void setPushStartTime(Date pushStartTime) {
        this.pushStartTime = pushStartTime;
    }

    public Date getPushEndTime() {
        return pushEndTime;
    }

    public void setPushEndTime(Date pushEndTime) {
        this.pushEndTime = pushEndTime;
    }

    public String getPushIcon() {
        return pushIcon;
    }

    public void setPushIcon(String pushIcon) {
        this.pushIcon = pushIcon;
    }

    public String getPushIntro() {
        return pushIntro;
    }

    public void setPushIntro(String pushIntro) {
        this.pushIntro = pushIntro;
    }

    public int getPushNo() {
        return pushNo;
    }

    public void setPushNo(int pushNo) {
        this.pushNo = pushNo;
    }

    public float getPushPrice() {
        return pushPrice;
    }

    public void setPushPrice(float pushPrice) {
        this.pushPrice = pushPrice;
    }

    public int getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(int pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getPushTitle() {
        return pushTitle;
    }

    public void setPushTitle(String pushTitle) {
        this.pushTitle = pushTitle;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCodetype() {
        return codetype;
    }

    public void setCodetype(int codetype) {
        this.codetype = codetype;
    }

    public int getWallweight() {
        return wallweight;
    }

    public void setWallweight(int wallweight) {
        this.wallweight = wallweight;
    }

    public int getWallstatus() {
        return wallstatus;
    }

    public void setWallstatus(int wallstatus) {
        this.wallstatus = wallstatus;
    }

    public int getIsfloatwindow() {
        return isfloatwindow;
    }

    public void setIsfloatwindow(int isfloatwindow) {
        this.isfloatwindow = isfloatwindow;
    }

    public int getFloatwindowweight() {
        return floatwindowweight;
    }

    public void setFloatwindowweight(int floatwindowweight) {
        this.floatwindowweight = floatwindowweight;
    }

    public int getFloatwindowstatus() {
        return floatwindowstatus;
    }

    public void setFloatwindowstatus(int floatwindowstatus) {
        this.floatwindowstatus = floatwindowstatus;
    }

    public String getApk() {
        return apk;
    }

    public void setApk(String apk) {
        this.apk = apk;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSourceIcon() {
        return sourceIcon;
    }

    public void setSourceIcon(String sourceIcon) {
        this.sourceIcon = sourceIcon;
    }

    public String getAppIntro() {
        return appIntro;
    }

    public void setAppIntro(String appIntro) {
        this.appIntro = appIntro;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getScreenshot1() {
        return screenshot1;
    }

    public void setScreenshot1(String screenshot1) {
        this.screenshot1 = screenshot1;
    }

    public String getScreenshot2() {
        return screenshot2;
    }

    public void setScreenshot2(String screenshot2) {
        this.screenshot2 = screenshot2;
    }

    public String getScreenshot3() {
        return screenshot3;
    }

    public void setScreenshot3(String screenshot3) {
        this.screenshot3 = screenshot3;
    }

    public String getScreenshot4() {
        return screenshot4;
    }

    public void setScreenshot4(String screenshot4) {
        this.screenshot4 = screenshot4;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public float getSpotPrice() {
        return spotPrice;
    }

    public void setSpotPrice(float spotPrice) {
        this.spotPrice = spotPrice;
    }

    public int getDefaultPhoto() {
        return defaultPhoto;
    }

    public void setDefaultPhoto(int defaultPhoto) {
        this.defaultPhoto = defaultPhoto;
    }

    public String getCommonPhoto1() {
        return commonPhoto1;
    }

    public void setCommonPhoto1(String commonPhoto1) {
        this.commonPhoto1 = commonPhoto1;
    }

    public String getCommonPhoto2() {
        return commonPhoto2;
    }

    public void setCommonPhoto2(String commonPhoto2) {
        this.commonPhoto2 = commonPhoto2;
    }

    public String getAttractPhoto1() {
        return attractPhoto1;
    }

    public void setAttractPhoto1(String attractPhoto1) {
        this.attractPhoto1 = attractPhoto1;
    }

    public String getAttractPhoto2() {
        return attractPhoto2;
    }

    public void setAttractPhoto2(String attractPhoto2) {
        this.attractPhoto2 = attractPhoto2;
    }

    public String getOuterPhoto1() {
        return outerPhoto1;
    }

    public void setOuterPhoto1(String outerPhoto1) {
        this.outerPhoto1 = outerPhoto1;
    }

    public String getOuterPhoto2() {
        return outerPhoto2;
    }

    public void setOuterPhoto2(String outerPhoto2) {
        this.outerPhoto2 = outerPhoto2;
    }

    public int getSpotAmount() {
        return spotAmount;
    }

    public void setSpotAmount(int spotAmount) {
        this.spotAmount = spotAmount;
    }

    public String getSettleStand() {
        return settleStand;
    }

    public void setSettleStand(String settleStand) {
        this.settleStand = settleStand;
    }

    public String getSettleCycle() {
        return settleCycle;
    }

    public void setSettleCycle(String settleCycle) {
        this.settleCycle = settleCycle;
    }

    public float getCertiRate() {
        return certiRate;
    }

    public void setCertiRate(float certiRate) {
        this.certiRate = certiRate;
    }

    public Date getSpotCreateTime() {
        return spotCreateTime;
    }

    public void setSpotCreateTime(Date spotCreateTime) {
        this.spotCreateTime = spotCreateTime;
    }

    public Date getSpotStartTime() {
        return spotStartTime;
    }

    public void setSpotStartTime(Date spotStartTime) {
        this.spotStartTime = spotStartTime;
    }

    public Date getSpotEndTime() {
        return spotEndTime;
    }

    public void setSpotEndTime(Date spotEndTime) {
        this.spotEndTime = spotEndTime;
    }

    public int getForceTest() {
        return forceTest;
    }

    public void setForceTest(int forceTest) {
        this.forceTest = forceTest;
    }

    public int getPayRatio() {
        return payRatio;
    }

    public void setPayRatio(int payRatio) {
        this.payRatio = payRatio;
    }

    public int getSoptWeight() {
        return soptWeight;
    }

    public void setSoptWeight(int soptWeight) {
        this.soptWeight = soptWeight;
    }

    public int getSpotStatus() {
        return spotStatus;
    }

    public void setSpotStatus(int spotStatus) {
        this.spotStatus = spotStatus;
    }

    public int getEnforceDown() {
        return enforceDown;
    }

    public void setEnforceDown(int enforceDown) {
        this.enforceDown = enforceDown;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}