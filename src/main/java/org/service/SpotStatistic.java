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
 * 插屏30天新增统计
 * @author wally
 *
 */
public class SpotStatistic extends StatisticServiceImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(SpotStatistic.class);
	public MysqlJDBC mysqlJDBC = MysqlJDBC.getInstance();
	public static HiveJDBC hiveJDBC = HiveJDBC.getInstance();
	
	
	
	@Override
	public List<String> getSlotNameFromHive(String day) throws SQLException {
		//获取连接
		Connection conn = hiveJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		sqlBuilder.append("select distinct(slot_name) from ");
		sqlBuilder.append(Constant.TABLE_SPOT_USER_SEVEN);
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
	
	
	/**
	 * 根据key和日期查询某个应用的新增用户
	 * @param day
	 * @param slotName
	 * @return 新增用户的结果
	 * @throws SQLException
	 */
	@Override
	public List<String> getNewUsers(String day, String slotName, int type) throws SQLException {
		//获取连接
		Connection conn = hiveJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		
		sqlBuilder.append("select t.imei from ");
		sqlBuilder.append(Constant.TABLE_SPOT_USER_SEVEN);
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
		
//	    logger.info("sql:{}",sqlBuilder.toString());
		ResultSet resultSet = statement.executeQuery(sqlBuilder.toString());
		List<String> imeis = new ArrayList<String>();
		while (resultSet.next()) {
			imeis.add(resultSet.getString(1));
         }
		return imeis;
	}
	
	
	/**
	 * 根据key和day查询新增，并将结果导入到hive中
	 */
	@Override
	public void getNewUserIntoHive(String day, String slotName, String devid, int type) throws SQLException {
		//获取连接
		Connection conn = hiveJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		sqlBuilder.append("INSERT INTO TABLE ").append(Constant.TABLE_USER).append(" PARTITION(day=").append(day).append(")");
		sqlBuilder.append(" select '").append(slotName).append("',t.imei,").append(Constant.FLAG_SPOT).append(",'").append(devid).append("' from ");
		sqlBuilder.append(Constant.TABLE_SPOT_USER_SEVEN);
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
	
	public static void main(String[] args) throws SQLException {
		SpotStatistic spotStatistic = new SpotStatistic();
		Map<String, String> map = spotStatistic.getDevInfo();
		for(Map.Entry<String, String> entry : map.entrySet())
		{
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		
		List<String> slotNames = spotStatistic.getSlotNameFromHive("20141111");
		System.out.println("今天总共应用个数：" + slotNames.size() + "--------");
		
		for (String string : slotNames) {
			System.out.println(string);
		}
		
		for (String string : slotNames) {
			System.out.println(string);
			spotStatistic.getNewUserIntoHive("20141111", string, map.get(string), Constant.NEW_USER_THIRTY);
		}
	}

}
