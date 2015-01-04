package org.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;

/**
 * 开发者应用列表
 *
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 202014/8/18 17:19
 * @description
 */
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class App implements Serializable {
    private static final long serialVersionUID = -2317399576811849705L;

    /**
     * 应用ID(自增长的id)
     */
    private int    appid;
    /**
     * 开发者应用的下载地址(URI)相对路径
     */
    private String uri;
    /**
     * 开发者应用状态(1:审核通过 2:审核未通过 3:已下线 4:未上传 5:待审核)
     */
    private int    appStatus;
    /**
     * 应用类别(所属分类)
     */
    private String category;
    /**
     * 应用的图标
     */
    private String icon;
    /**
     * 备注名称(应用别名)
     */
    private String noteName;
    /**
     * 应用介绍
     */
    private String info;
    /**
     * 修改时间
     */
    private Date   modifyTime;
    /**
     * 应用名称
     */
    private String name;
    /**
     * 应用包名
     */
    private String packageName;
    /**
     * 应用上传时间
     */
    private Date   createTime;

    /**
     * 推送状态(0:开启 1:关闭)
     */
    private int     pushStatus;
    /**
     * 是否已嵌入sdk
     */
    private int     sdkInclude;
    /**
     * app大小
     */
    private String  size;
    /**
     * 发布ID（开发者Key）
     *
     */
    private String  slotName;
    /**
     * 应用版本
     */
    private String  version;
    /**
     * App推送结算比例，由后台运营扣量表中控制
     */
    private Integer balanceScale;
    /**
     * 生效时间，指设置扣量的生效时间
     */
    private Date    effectDate;
    /**
     * App插屏结算比例，由后台运营扣量表中控制
     */
    private Integer cpBalanceScale;
    /**
     * 插屏生效时间，指设置扣量的生效时间
     */
    private Date    cpEffectDate;
    /**
     * 维护时间，指设置扣量的时间
     */
    private Date    repairDate;
    /**
     * 扣量是否生效的状态 1：有效 2:无效
     */
    private Integer effectStatus;

    //悬窗推荐位
    /**
     * 自有 默认选中 ，0：选中 1：未选中
     */
    private Integer floatWindow1;
    /**
     * 桌面 默认选中 ，0：选中 1：未选中
     */
    private Integer floatWindow2;
    /**
     * 第三方  默认选中 ，0：选中 1：未选中
     */
    private Integer floatWindow3;
    /**
     * 补推开关 默认开启 ，0：开启 1：关闭
     */
    private int pushSwitch = 0;
    /**
     * 推送频率：1:一天四条,2:一天两条3:一天一条4:二天一条对应前台（frequency）
     */
    private int frequency = 1;
    /**
     * 内弹插屏状态默认关闭 ，0：开启 1：关闭
     */
    private int insideShot;

    /**
     * 外弹插屏状态默认关闭 ，0：开启 1：关闭
     */
    private int outerShot;
    /**
     * 外弹条数限制
     */
    private int outsideLimit;
    /**
     * 广告是否强制下载  0：是 1：否
     */
    private int forceDownload;
    /**
     * 广告是否必须显示常规图片  0：是 1：否
     */
    private int isCommonImage;
    /**
     * 广告出现次数限制(内弹条数限制)
     */
    private int adCount;
    /**
     * 是否进行补弹  0：是 1：否
     */
    private int isPatchShot;
    /**
     * 开发者账号
     */
    private String devEmail = "";
    /**
     * 开发者名称
     */
    private String devName  = "";
    /**
     * 开发者id
     */
    private int devid;

    /**
     * 媒介维护人账号
     */
    private String adminAccount = "";
    /**
     * 媒介维护人姓名
     */
    private String adminName    = "";
    /**
     * 发布ID一对多或一对一，状态值参考message.properties
     */
    private int    oneToMany;
    /**
     * 应用屏蔽地区
     */
    private String blockArea;

    /** 应用屏蔽广告**/
    private String blockAds;

    public String getBlockAds() {
        return blockAds;
    }

    public void setBlockAds(String blockAds) {
        this.blockAds = blockAds;
    }

    public Integer getCpBalanceScale() {
        return cpBalanceScale;
    }

    public void setCpBalanceScale(Integer cpBalanceScale) {
        this.cpBalanceScale = cpBalanceScale;
    }

    public Date getCpEffectDate() {
        return cpEffectDate;
    }

    public void setCpEffectDate(Date cpEffectDate) {
        this.cpEffectDate = cpEffectDate;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public Integer getFloatWindow1() {
        return floatWindow1;
    }

    public void setFloatWindow1(Integer floatWindow1) {
        this.floatWindow1 = floatWindow1;
    }

    public Integer getFloatWindow2() {
        return floatWindow2;
    }

    public void setFloatWindow2(Integer floatWindow2) {
        this.floatWindow2 = floatWindow2;
    }

    public Integer getFloatWindow3() {
        return floatWindow3;
    }

    public void setFloatWindow3(Integer floatWindow3) {
        this.floatWindow3 = floatWindow3;
    }

    public int getPushSwitch() {
        return pushSwitch;
    }

    public void setPushSwitch(int pushSwitch) {
        this.pushSwitch = pushSwitch;
    }

    public Date getEffectScale() {
        return effectDate;
    }

    public Integer getBalanceScale() {
        return balanceScale;
    }

    public void setBalanceScale(Integer balanceScale) {
        this.balanceScale = balanceScale;
    }

    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    public Integer getEffectStatus() {
        return effectStatus;
    }

    public void setEffectStatus(Integer effectStatus) {
        this.effectStatus = effectStatus;
    }

    public void setEffectScale(Date effectScale) {
        this.effectDate = effectScale;
    }

    public Date getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(Date repairDate) {
        this.repairDate = repairDate;
    }

    public App() {
    }

    public int getAppid() {
        return this.appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public int getAppStatus() {
        return this.appStatus;
    }

    public void setAppStatus(int appStatus) {
        this.appStatus = appStatus;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getPushStatus() {
        return this.pushStatus;
    }

    public void setPushStatus(int pushStatus) {
        this.pushStatus = pushStatus;
    }

    public int getSdkInclude() {
        return this.sdkInclude;
    }

    public void setSdkInclude(int sdkInclude) {
        this.sdkInclude = sdkInclude;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSlotName() {
        return this.slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getInsideShot() {
        return insideShot;
    }

    public void setInsideShot(int insideShot) {
        this.insideShot = insideShot;
    }

    public int getOuterShot() {
        return outerShot;
    }

    public void setOuterShot(int outerShot) {
        this.outerShot = outerShot;
    }

    public int getForceDownload() {
        return forceDownload;
    }

    public void setForceDownload(int forceDownload) {
        this.forceDownload = forceDownload;
    }

    public int getIsCommonImage() {
        return isCommonImage;
    }

    public void setIsCommonImage(int isCommonImage) {
        this.isCommonImage = isCommonImage;
    }

    public int getAdCount() {
        return adCount;
    }

    public void setAdCount(int adCount) {
        this.adCount = adCount;
    }

    public int getIsPatchShot() {
        return isPatchShot;
    }

    public void setIsPatchShot(int isPatchShot) {
        this.isPatchShot = isPatchShot;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public int getDevid() {
        return devid;
    }

    public void setDevid(int devid) {
        this.devid = devid;
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getDevEmail() {
        return devEmail;
    }

    public void setDevEmail(String devEmail) {
        this.devEmail = devEmail;
    }

    public int getOneToMany() {
        return oneToMany;
    }

    public void setOneToMany(int oneToMany) {
        this.oneToMany = oneToMany;
    }

    public String getBlockArea() {
        return blockArea;
    }

    public void setBlockArea(String blockArea) {
        this.blockArea = blockArea;
    }

    public int getOutsideLimit() {
        return outsideLimit;
    }

    public void setOutsideLimit(int outsideLimit) {
        this.outsideLimit = outsideLimit;
    }
}