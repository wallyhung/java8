package org.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.statistic.HiveJDBC;
import org.statistic.MysqlJDBC;
import org.util.Constant;
import org.util.TimeUtil;

import com.mysql.jdbc.StringUtils;


/**
 * 推送平台统计30天的用户
 * @author wally
 */
public class PushStatistic extends StatisticServiceImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(PushStatistic.class);
	public static HiveJDBC hiveJDBC = HiveJDBC.getInstance();
	public MysqlJDBC mysqlJDBC = MysqlJDBC.getInstance();
	
	
	/**
	 * 获取昨天推送的所有应用key
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<String> getSlotNameFromHive(String day) throws SQLException
	{
		//获取连接
		Connection conn = hiveJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		sqlBuilder.append("select distinct(slot_name) from ");
		sqlBuilder.append(Constant.TABLE_PUSH);
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
	public List<String> getPnameBySlotName(String day,String slotName) throws SQLException
	{
		//获取连接
		Connection conn = hiveJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		
		sqlBuilder.append("select distinct(t.pname) from ");
		sqlBuilder.append(Constant.TABLE_PUSH);
		sqlBuilder.append(" t where t.day = '");
		sqlBuilder.append(day);
		sqlBuilder.append("' and t.slot_name = '");
		sqlBuilder.append(slotName);
		sqlBuilder.append("'");
		
		ResultSet resultSet = statement.executeQuery(sqlBuilder.toString());
		List<String> pnames = new ArrayList<String>();
		while (resultSet.next()) {
			pnames.add(resultSet.getString(1));
         }
		return pnames;
	}
	
	
	@Override
	public List<String> getNewUsers(String day, String slotName, int type) throws SQLException {
		
		//获取连接
		Connection conn = hiveJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		
		sqlBuilder.append("select t.imei from ");
		sqlBuilder.append(Constant.TABLE_PUSH);
		sqlBuilder.append(" t where t.day between '");
		
		if(type == Constant.NEW_USER_SEVEN)  sqlBuilder.append(TimeUtil.getSevenDaysBeforeString(TimeUtil.getDayFromDayStringNon(day)));
		if(type == Constant.NEW_USER_THIRTY)  sqlBuilder.append(TimeUtil.getThirthDaysBefore(TimeUtil.getDayFromDayStringNon(day)));
		
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
		
//		logger.info("sql:{}",sqlBuilder.toString());
		ResultSet resultSet = statement.executeQuery(sqlBuilder.toString());
		
		List<String> imeis = new ArrayList<String>();
		while (resultSet.next()) {
			imeis.add(resultSet.getString(1));
         }
		return imeis;
	}
	
	
	@Override
	public void getNewUserIntoHive(String day, String slotName, String devid, int type) throws SQLException {
		
		String tb = Constant.TABLE_USER;
		String start = TimeUtil.getThirthDaysBefore(TimeUtil.getDayFromDayStringNon(day));
		if(Constant.NEW_USER_SEVEN == type)
		{
			tb = Constant.TABLE_USER_SEVEN;
			start = TimeUtil.getSevenDaysBeforeString(TimeUtil.getDayFromDayStringNon(day));
		}
		
		Connection conn = hiveJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		sqlBuilder.append("INSERT INTO TABLE ").append(tb).append(" PARTITION(day=").append(day).append(")");
		sqlBuilder.append(" select '").append(slotName).append("',t.imei,").append(Constant.FLAG_PUSH).append(",'").append(devid).append("' from ");
		sqlBuilder.append(Constant.TABLE_PUSH);
		sqlBuilder.append(" t where t.day between '");
		sqlBuilder.append(start);
		sqlBuilder.append("' and '");
		sqlBuilder.append(TimeUtil.getDayNextDayString(day));
		
		sqlBuilder.append("' and t.slot_name = '");
		sqlBuilder.append(slotName);
		sqlBuilder.append("' group by t.imei having min(from_unixtime(cast(substr(t.timestamp,0,10) as bigint),'yyyyMMdd')) = '");
		sqlBuilder.append(day);
		sqlBuilder.append("'");
//		logger.info("sql:{}",sqlBuilder.toString());
		statement.execute(sqlBuilder.toString());
	}
	
	
	
	@Override
	public List<String> getRemainUser(String day, String slotName) throws SQLException {
		//获取连接
		Connection conn = hiveJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		
		sqlBuilder.append("select distinct(t.imei) from ");
		sqlBuilder.append(Constant.TABLE_PUSH);
		sqlBuilder.append(" t where t.day = '");
		sqlBuilder.append(day);
		sqlBuilder.append("'");
		if(!StringUtils.isNullOrEmpty(slotName)) sqlBuilder.append(" and t.slot_name = '").append(slotName).append("'");
		
//		logger.info("sql:{}",sqlBuilder.toString());
		ResultSet resultSet = statement.executeQuery(sqlBuilder.toString());
		List<String> imeis = new ArrayList<String>();
		while (resultSet.next()) {
			imeis.add(resultSet.getString(1));
         }
		return imeis;
	}
	
	
	/**
	 * 创建新增用户表
	 * @param name
	 * @throws SQLException
	 */
	public void createTable() throws SQLException
	{
		//获取连接
		Connection conn = hiveJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		sqlBuilder.append("create table if not exists ");
		sqlBuilder.append(Constant.TABLE_USER);
		sqlBuilder.append("(");
		sqlBuilder.append("slot_name STRING,");
		sqlBuilder.append("imei  STRING,");
		sqlBuilder.append("flag INT,");
		sqlBuilder.append("devid STRING)");
		sqlBuilder.append("PARTITIONED BY(day STRING)");
		sqlBuilder.append("ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'");
		sqlBuilder.append("STORED AS TEXTFILE");
		statement.execute(sqlBuilder.toString());
	}
	
	
	public static void main(String[] args) throws SQLException {
		PushStatistic statistic = new PushStatistic();
		List<String> slotNames = statistic.getSlotNameFromHive("20141111");
		System.out.println("今天总共应用个数：" + slotNames.size() + "--------");
		
		for (String string : slotNames) {
			System.out.println(string);
		}
		
		Map<String, String> map = statistic.getDevInfo();
		for (String string : slotNames) {
			System.out.println(string);
			statistic.getNewUserIntoHive("20141111", string, map.get(string), Constant.NEW_USER_THIRTY);
		}
		
		
		
		System.out.println(statistic.getNewUsersCount("20141117", "6", Constant.FLAG_PUSH, Constant.TABLE_USER));
		
		System.out.println(statistic.getSlotNameFromMysql("39"));
		
		System.out.println(statistic.getNewUsers("20141111", "39", Constant.FLAG_PUSH, Constant.TABLE_USER_SEVEN));
		
//		createTable();
	}
	

}
