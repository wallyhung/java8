package org.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.statistic.HiveJDBC;
import org.statistic.MysqlJDBC;
import org.util.Constant;
import org.util.TimeUtil;

import com.mysql.jdbc.StringUtils;

public class NewPushStatistic extends StatisticServiceImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(NewPushStatistic.class);
	public static HiveJDBC hiveJDBC = HiveJDBC.getInstance();
	public MysqlJDBC mysqlJDBC = MysqlJDBC.getInstance();
	
	@Override
	public List<String> getSlotNameFromHive(String day) throws SQLException {
		
		//获取连接
		Connection conn = hiveJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		sqlBuilder.append("select distinct(slot_name) from ");
		sqlBuilder.append(Constant.TABLE_NEW_PUSH_USER_SEVEN);
		sqlBuilder.append(" where day = '");
		sqlBuilder.append(day);
		sqlBuilder.append("'");
		ResultSet resultSet = statement.executeQuery(sqlBuilder.toString());
		
		List<String> keys = new ArrayList<String>();
		while (resultSet.next()) {
			keys.add(resultSet.getString(1));
         }
		return keys;
	}
	
	@Override
	public List<String> getPnameBySlotName(String day, String slotName)
			throws SQLException {
		return null;
	}
	
	
	@Override
	public List<String> getNewUsers(String day, String slotName, int type) throws SQLException {
		//获取连接
		Connection conn = hiveJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		
		sqlBuilder.append("select t.imei from ");
		sqlBuilder.append(Constant.TABLE_NEW_PUSH_USER_SEVEN);
		sqlBuilder.append(" t where t.day ");
		
		if(type == Constant.NEW_USER_SEVEN)
		{ 
			sqlBuilder.append("= '").append(day).append("'");
			if(!StringUtils.isNullOrEmpty(slotName)) sqlBuilder.append(" and t.slot_name = '").append(slotName).append("'");
		}
		if(type == Constant.NEW_USER_THIRTY)
		{
			sqlBuilder.append(" between '");
			sqlBuilder.append(TimeUtil.getThirthDaysBefore(TimeUtil.getDayFromDayStringNon(day)));
			sqlBuilder.append("' and '");
			sqlBuilder.append(TimeUtil.getDayNextDayString(day));
			
			if(!StringUtils.isNullOrEmpty(slotName))
			{
				sqlBuilder.append("' and t.slot_name = '");
				sqlBuilder.append(slotName);
			}
			sqlBuilder.append("' group by t.imei having min(from_unixtime(cast(substr(t.timestamp,0,10) as bigint),'yyyyMMdd')) = '");
			sqlBuilder.append(day);
			sqlBuilder.append("'");
		}
		
	    logger.info("sql:{}",sqlBuilder.toString());
		ResultSet resultSet = statement.executeQuery(sqlBuilder.toString());
		List<String> imeis = new ArrayList<String>();
		while (resultSet.next()) {
			imeis.add(resultSet.getString(1));
         }
		return imeis;
	}
	
	/**
	 * 只做30天的新增数据
	 */
	@Override
	public void getNewUserIntoHive(String day, String slotName, String devid, int type) throws SQLException {
		
		//获取连接
		Connection conn = hiveJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		sqlBuilder.append("INSERT INTO TABLE ").append(Constant.TABLE_USER).append(" PARTITION(day=").append(day).append(")");
		sqlBuilder.append(" select '").append(slotName).append("',t.imei,").append(Constant.FLAG_NEW_PUSH).append(",'").append(devid).append("' from ");
		sqlBuilder.append(Constant.TABLE_NEW_PUSH_USER_SEVEN);
		sqlBuilder.append(" t where t.day between '");
		sqlBuilder.append(TimeUtil.getThirthDaysBefore(TimeUtil.getDayFromDayStringNon(day)));
		sqlBuilder.append("' and '");
		sqlBuilder.append(TimeUtil.getDayNextDayString(day));
		sqlBuilder.append("' and t.slot_name = '");
		sqlBuilder.append(slotName);
		sqlBuilder.append("' group by t.imei having min(t.day) = '");
		sqlBuilder.append(day);
		sqlBuilder.append("'");
//		logger.info("sql:{}",sqlBuilder.toString());
		statement.execute(sqlBuilder.toString());
	}
	
	@Override
	public List<String> getRemainUser(String day, String slotName)
			throws SQLException {
		return null;
	}
	
	
	public List<String> getNewPlatformRemainUser(String day, String slotName) throws SQLException {
		//获取连接
		Connection conn = hiveJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		
		sqlBuilder.append("select distinct(t.clientMessage.imei) from ");
		sqlBuilder.append(Constant.TABLE_NEW_PUSH);
		sqlBuilder.append(" t where t.day = '");
		sqlBuilder.append(day);
		sqlBuilder.append("' and t.rtype = 1");
		if(!StringUtils.isNullOrEmpty(slotName)) sqlBuilder.append(" and  t.clientMessage.slot_name = '").append(slotName).append("'");
		
//		logger.info("sql:{}",sqlBuilder.toString());
		ResultSet resultSet = statement.executeQuery(sqlBuilder.toString());
		List<String> imeis = new ArrayList<String>();
		while (resultSet.next()) {
			imeis.add(resultSet.getString(1));
         }
		return imeis;
	}
	
	public List<String> getNewPlatformRemainUserByDevid(String day, String devid) throws SQLException {
		//获取连接
		Connection conn = hiveJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		
		sqlBuilder.append("select distinct(t.clientMessage.imei) from ");
		sqlBuilder.append(Constant.TABLE_NEW_PUSH);
		sqlBuilder.append(" t where t.day = '");
		sqlBuilder.append(day);
		sqlBuilder.append("' and t.rtype = 1");
		if(!StringUtils.isNullOrEmpty(devid)) sqlBuilder.append(" and  t.app.devid = ").append(devid);
		
//		logger.info("sql:{}",sqlBuilder.toString());
		ResultSet resultSet = statement.executeQuery(sqlBuilder.toString());
		List<String> imeis = new ArrayList<String>();
		while (resultSet.next()) {
			imeis.add(resultSet.getString(1));
         }
		return imeis;
	}
	
	/**
	 * 新平台数据
	 * @param day
	 * @param slotName
	 * @param devid
	 * @throws SQLException
	 */
	public void getNewPlatformNewUserIntoHive(String day,String slotName,String devid) throws SQLException
	{
		//获取连接
		Connection conn = hiveJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		sqlBuilder.append("INSERT INTO TABLE ").append(Constant.TABLE_USER).append(" PARTITION(day=").append(day).append(")");
		sqlBuilder.append(" select '").append(slotName).append("',t.clientMessage.imei,").append(Constant.FLAG_NEW_PUSH).append(",'").append(devid).append("' from ");
		sqlBuilder.append(Constant.TABLE_NEW_PUSH);
		sqlBuilder.append(" t where t.day between '");
		sqlBuilder.append(TimeUtil.getThirthDaysBefore(TimeUtil.getDayFromDayStringNon(day)));
		sqlBuilder.append("' and '");
		sqlBuilder.append(TimeUtil.getDayNextDayString(day));
		sqlBuilder.append("' and t.rtype = 1 and t.clientMessage.slot_name = '");
		sqlBuilder.append(slotName);
		sqlBuilder.append("' group by t.clientMessage.imei having min(from_unixtime(cast(substr(t.clientMessage.timestamp,0,10) as bigint),'yyyyMMdd')) = '");
		sqlBuilder.append(day);
		sqlBuilder.append("'");
		logger.info("sql:{}",sqlBuilder.toString());
		statement.execute(sqlBuilder.toString());
	}
	
	public static void main(String[] args) throws SQLException {
		NewPushStatistic statistic = new NewPushStatistic();
//		statistic.getNewPlatformNewUserIntoHive("20141130", "b4d63095cb664ed299584f1817b69ce9", "6");
		
		System.out.println(statistic.getDistinctFieldsFromHive("20141215", null, Constant.TABLE_INSTALL, "slot_name"));
		
	}
	
	
	

}
