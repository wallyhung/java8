package org.util;

public class Constant {
	
	/*********************************************hadoop处理30天新增***************************************************/
	
	public static final int FLAG_PUSH = 1;
	public static final int FLAG_SPOT = 2;
	public static final int FLAG_NEW_PUSH = 3;
	public static final int FLAG_SPOT_INSIDE = 4;
	public static final int FLAG_SPOT_OUTSIDE = 5;
	
	 /*********************************************客户端Format值***************************************************/

    //推送
    public static final String FORMAT_PUSH          = "push";
    //内弹
    public static final String FORMAT_SPOT_INSIDE   = "spot_inside";
    //外弹
    public static final String FORMAT_SPOT_OUTSIDE  = "spot_outside";
    //悬窗
    public static final String FORMAT_SUSPEND       = "suspend";

    /********************************************请求广告返回结果*************************************************/

    public static final String REQ_RESULT_TIME   = "validTime"; //时间不合法
    public static final String REQ_RESULT_STATUS = "sendStatus";//开发者应用状态不合法(关闭)
    public static final String REQ_RESULT_LIMIT  = "sendLimit"; //已达上线
    public static final String REQ_RESULT_AREA   = "vaildArea"; //地区屏蔽
    public static final String REQ_RESULT_NOAD   = "noad";      //无广告
    public static final String REQ_RESULT_OK     = "ok";        //成功请求广告

    /**
     * **********************************************日志类型****************************************************
     */
    public static final int RTYPE_REQUEST         = 1; //请求
    public static final int RTYPE_PUSH            = 2; //推送
    public static final int RTYPE_VIEW            = 3; //展示
    public static final int RTYPE_CLICK           = 4; //点击
    public static final int RTYPE_DOWNLOAD        = 5; //下载成功
    public static final int RTYPE_INSTALL         = 6; //安装
    public static final int RTYPE_START           = 7; //启动
    public static final int RTYPE_ALL             = 8; //所有请求(包含无效请求)
    public static final int RTYPE_INFO            = 9; //设备信息搜集
    public static final int RTYPE_BEFORE_DOWNLOAD = 10;//点击下载
    
    /**新增用户类型***/
    public static final int NEW_USER_SEVEN = 1;
    public static final int NEW_USER_THIRTY = 2;
    
    
    /*********************************hive中存储的表名***********************************/

    /**
     * 推送基础数据表
     */
    public static final String TABLE_PUSH = "request";
    /**
     * 新推送基础数据表
     */
	public static final String TABLE_NEW_PUSH = "biglog";
	/**
	 * mysql中导入  插屏7天新增用户表	
	 */
	public static final String TABLE_SPOT_USER_SEVEN = "spot_seven_user";
	/**
	 * mysql中导入  新推送7天新增用户表
	 */
	public static final String TABLE_NEW_PUSH_USER_SEVEN = "push_seven_user";
	/**
	 * 整个平台30天新增用户
	 */
	public static final String TABLE_USER = "new_user";
	/**
	 * 整个平台7天新增用户
	 */
	public static final String TABLE_USER_SEVEN = "new_user_seven";
	
	
	public static final String TABLE_INSTALL = "install";
	
    
	
}
