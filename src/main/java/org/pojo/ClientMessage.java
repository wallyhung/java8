package org.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 客户端请求广告信息实体
 *
 * @author Xull
 * @version 1.0
 * @email wanxkl@gmail.com
 * @created 2014-2-24 下午8:04
 * @description
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientMessage extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -6433996491857988068L;

    //开发者应用key，由运营后台产生
    private String              slot_name;
    //是否为测试模式(true:是，false:否，开发者配置.客户端携带，网关根据此标识下发不同类型广告)
    private boolean             test_mode;
    //客户端设备ID
    private String              imei;
    //手机型号
    private String              model;
    //手机品牌
    private String              manuf;
    //开发者应用包名
    private String              pname;
    //开发者应用名称
    private String              app_name;
    //网络类型（移动：mobile 联通：unicom 电信：telcom WIFI：wi）
    private String              net;
    //地区语言:zh-CN,en-GB,en-US
    private String              hl;
    //屏幕宽度
    private int                 u_w;
    //屏幕高度
    private int                 u_h;
    //地理位置信息：属性为 空("")或者为NULL都不序列化
    private Location            location;
    //平台(android,ios)
    private String              platfrom;
    //Dex版本
    private int                 dex_version;
    //Shell版本
    private int                 shell_version;
    //标识：push:推送 spot_inside:内弹插屏 spot_outside:外弹插屏
    private String              format;
    //电话号码
    private String              phone_number;
    //年龄
    private int                 cust_age;
    //性别(0,女 1,男 2,未知)
    private int                 cust_gender;
    //关键字(搜索广告关键字)
    private List<String>        kw;
    //扩展参数
    private Map<String, String> extras;
    //渠道ID
    private int                 channel_id;
    //国际移动用户识别码(存储在手机SIM卡中)
    private String              imsi;
    //SIM卡唯一识别号码
    private String              iccid;
    //客户端MAC地址
    private String              mac;
    //客户端请求序号(从1开始,成功展示后+1)
    private int                 rn;
    //当前请求时间戳(客户端生成)
    private long                rt;
    //客户端最后一次成功请求时间
    private long                lt;
    //host配置版本号(修改地址后,客户端可以利用此版本号动态更新)
    private int                 host_version;
    //过滤包名版本(运营后台生成,由网关下发到客户端)
    private int                 filter_version;
    //网络状态(0:无网,1:有网)
    private int                 net_status;
    // 系统版本号
    private String              os_version;
    //客户端唯一标示符
    private String              cuid;
    //请求广告触发条件(0:启动 1:网络 2:锁屏)
    private int trigger;
    //客户端应用列表(用户手机安装的应用)
    private List<String>        app_list;
    //客户端效果数列表(产生效果的广告ID列表)
    private List<Integer>       c_result;
    //客户端版本名称
    private String              vname;
    //客户端版本号
    private int                 vcode;
    //客户端签名信息
    private String              signs;

    public ClientMessage() {
    }

    public ClientMessage(String slot_name, String imei, String ip, String phone_number, String ua) {
        this.slot_name = slot_name;
        this.imei = imei;
        super.setIp(ip);
        super.setUa(ua);
        this.phone_number = phone_number;
    }

    public ClientMessage(String slot_name, boolean test_mode, String imei, String model, String manuf, String pname, String app_name, String net, int u_w, int u_h, Location location, String platfrom, int dex_version, int shell_version, String format, String ua, String phone_number, int cust_age, int cust_gender, List<String> kw, Map<String, String> extras, int channel_id, String imsi, String iccid, String mac, List<String> app_list, String hl, int filter_version, int host_version, long rt, int net_status, String os_version, int rn) {
        this.slot_name = slot_name;
        this.test_mode = test_mode;
        this.imei = imei;
        this.model = model;
        this.manuf = manuf;
        this.pname = pname;
        this.app_name = app_name;
        this.net = net;
        this.u_w = u_w;
        this.u_h = u_h;
        this.location = location;
        this.platfrom = platfrom;
        this.dex_version = dex_version;
        this.shell_version = shell_version;
        this.format = format;
        super.setUa(ua);
        this.phone_number = phone_number;
        this.cust_age = cust_age;
        this.cust_gender = cust_gender;
        this.kw = kw;
        this.extras = extras;
        this.channel_id = channel_id;
        this.imsi = imsi;
        this.iccid = iccid;
        this.mac = mac;
        this.app_list = app_list;
        this.hl = hl;
        this.filter_version = filter_version;
        this.host_version = host_version;
        this.rt = rt;
        this.net_status = net_status;
        this.os_version = os_version;
        this.rn = rn;
    }

    public String getSlot_name() {
        return slot_name;
    }

    public void setSlot_name(String slot_name) {
        this.slot_name = slot_name;
    }

    public boolean isTest_mode() {
        return test_mode;
    }

    public void setTest_mode(boolean test_mode) {
        this.test_mode = test_mode;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManuf() {
        return manuf;
    }

    public void setManuf(String manuf) {
        this.manuf = manuf;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public int getU_w() {
        return u_w;
    }

    public void setU_w(int u_w) {
        this.u_w = u_w;
    }

    public int getU_h() {
        return u_h;
    }

    public void setU_h(int u_h) {
        this.u_h = u_h;
    }

    public Location getLocation() {
        if (this.location == null) location = new Location();
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPlatfrom() {
        return platfrom;
    }

    public void setPlatfrom(String platfrom) {
        this.platfrom = platfrom;
    }

    public int getDex_version() {
        return dex_version;
    }

    public void setDex_version(int dex_version) {
        this.dex_version = dex_version;
    }

    public int getShell_version() {
        return shell_version;
    }

    public void setShell_version(int shell_version) {
        this.shell_version = shell_version;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getCust_age() {
        return cust_age;
    }

    public void setCust_age(int cust_age) {
        this.cust_age = cust_age;
    }

    public int getCust_gender() {
        return cust_gender;
    }

    public void setCust_gender(int cust_gender) {
        this.cust_gender = cust_gender;
    }

    public List<String> getKw() {
        return kw;
    }

    public void setKw(List<String> kw) {
        this.kw = kw;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public List<String> getApp_list() {
        return app_list;
    }

    public void setApp_list(List<String> app_list) {
        this.app_list = app_list;
    }

    public String getHl() {
        return hl;
    }

    public void setHl(String hl) {
        this.hl = hl;
    }

    public int getFilter_version() {
        return filter_version;
    }

    public void setFilter_version(int filter_version) {
        this.filter_version = filter_version;
    }

    public int getHost_version() {
        return host_version;
    }

    public void setHost_version(int host_version) {
        this.host_version = host_version;
    }

    public long getRt() {
        return rt;
    }

    public void setRt(long rt) {
        this.rt = rt;
    }

    public int getNet_status() {
        return net_status;
    }

    public void setNet_status(int net_status) {
        this.net_status = net_status;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public int getRn() {
        return rn;
    }

    public void setRn(int rn) {
        this.rn = rn;
    }

    //mysql库保存kw
    public static String getChangedKw(List<String> kwList) {
        StringBuilder res = new StringBuilder("");
        if (kwList != null && kwList.size() > 0) {
            for (String k : kwList) {
                res.append(k + ",");
            }
            res.deleteCharAt(res.length() - 1);
        }
        return res.toString();
    }

    public static String getChangedAppList(List<String> appList) {
        StringBuilder res = new StringBuilder("");
        if (appList != null && appList.size() > 0) {
            for (String a : appList) {
                res.append(a + ",");
            }
            res.deleteCharAt(res.length() - 1);
        }
        return res.toString();
    }

    public List<Integer> getC_result() {
        return c_result;
    }

    public void setC_result(List<Integer> c_result) {
        this.c_result = c_result;
    }

    public long getLt() {
        return lt;
    }

    public void setLt(long lt) {
        this.lt = lt;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
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

    public int getTrigger() {
        return trigger;
    }

    public void setTrigger(int trigger) {
        this.trigger = trigger;
    }
}
