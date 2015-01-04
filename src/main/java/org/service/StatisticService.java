package org.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface StatisticService {
	
	/**
	 * @from hive distinct获取
	 * 根据日期获取应用key
	 * @param day
	 * @return
	 * @throws SQLException
	 */
	public List<String> getSlotNameFromHive(String day) throws SQLException;
	
	/**
	 * mysql中获取应用名，开发者账号id map
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getDevInfo() throws SQLException;
	
	/**
	 * @from mysql库中查询
	 * 根据账号id获取应用key
	 * @param day
	 * @param devid
	 * @return
	 * @throws SQLException
	 */
	public List<String> getSlotNameFromMysql(String devid) throws SQLException;
	
	
	/**
	 * 根据应用名，日期获取包名   只针对推送应用
	 * @param day
	 * @param slotName
	 * @return
	 * @throws SQLException
	 */
	public List<String> getPnameBySlotName(String day,String slotName) throws SQLException;
	
	/**
	 * 用hive获取新增用户
	 * @param day       日期
	 * @param slotName  应用名 （参数可为空，查询是整个平台） 
	 * @param type      新增用户类型（1.7天新增，2.30天新增）
	 * @return
	 * @throws SQLException
	 */
	public List<String> getNewUsers(String day,String slotName,int type) throws SQLException;
	
	
	/**
	 * 根据日期，开发者账号获取新增用户
	 * @param day        日期
	 * @param devid      开发者账号
	 * @param flag       平台（1.推送，2.插屏，3.新推送）
	 * @param tbName     表名（new_user, new_user_seven, push_seven_user,spot_seven_user）
	 * @return
	 * @throws SQLException
	 */
	public List<String> getNewUsers(String day,String devid,int flag,String tbName) throws SQLException;
	
	
	/**
	 * 根据日期，开发者账号获取新增用户数
	 * @param day        日期
	 * @param devid      开发者账号
	 * @param flag       平台（1.推送，2.插屏，3.新推送）
	 * @param tbName     表名（new_user, new_user_seven, push_seven_user,spot_seven_user）
	 * @return
	 * @throws SQLException
	 */
	public int getNewUsersCount(String day,String devid,int flag,String tbName) throws SQLException;
	
	
	/**
	 * 获取新增用户并保存新增用户表中
	 * @param day         日期
	 * @param slotName    应用名   (参数不能为空)
	 * @param devid       开发者账号id
	 * @param type        新增用户类型（1.7天新增，2.30天新增）
	 * @throws SQLException
	 */
	public void getNewUserIntoHive(String day,String slotName,String devid,int type) throws SQLException;
	
	/**
	 * 根据应用名获取留存用户
	 * @param day         日期
	 * @param slotName    应用名 （应用名若为空，获取整个平台的留存）
	 * @return
	 * @throws SQLException
	 */
	public List<String> getRemainUser(String day, String slotName) throws SQLException;
	
	
	
	public List<String> getDistinctFieldsFromHive(String day,String slotName,String table,String field) throws SQLException;

}
