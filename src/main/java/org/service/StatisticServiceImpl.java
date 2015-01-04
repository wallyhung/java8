
package org.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.statistic.HiveJDBC;
import org.statistic.MysqlJDBC;

import com.mysql.jdbc.StringUtils;

public abstract class StatisticServiceImpl  implements StatisticService{
	
	
	private static final Logger logger = LoggerFactory.getLogger(StatisticServiceImpl.class);
	public  static HiveJDBC hiveJDBC = HiveJDBC.getInstance();
	public MysqlJDBC mysqlJDBC = MysqlJDBC.getInstance();
	
	
	@Override
	public Map<String, String> getDevInfo() throws SQLException {
		
		Map<String, String> map = new HashMap<String, String>();
		//获取连接
		Connection conn = mysqlJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		sqlBuilder.append("SELECT t.`slot_name`,t.`dev_id` FROM adpushadmin.`app` t");
		
		ResultSet resultSet = statement.executeQuery(sqlBuilder.toString());
		while (resultSet.next()) {
			map.put(resultSet.getString(1), resultSet.getString(2));
         }
		//关闭连接
		mysqlJDBC.CloseAll(resultSet, statement, conn);
		return map;
	}
	
	
	@Override
	public List<String> getSlotNameFromMysql(String devid) throws SQLException {
		
		List<String> keys = new ArrayList<String>();
		//获取连接
		Connection conn = mysqlJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		sqlBuilder.append("SELECT t.`slot_name` FROM adpushadmin.`app` t WHERE t.`dev_id` = '");
		sqlBuilder.append(devid).append("'");
		
		ResultSet resultSet = statement.executeQuery(sqlBuilder.toString());
		while (resultSet.next()) {
			keys.add(resultSet.getString(1));
         }
		//关闭连接
		mysqlJDBC.CloseAll(resultSet, statement, conn);
		return keys;
	}
	
	@Override
	public List<String> getNewUsers(String day, String devid, int flag,	String tbName) throws SQLException {
		//获取连接
		Connection conn = hiveJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		sqlBuilder.append("select distinct(t.imei) from ").append(tbName).append(" t where t.day = '").append(day).append("'");
		if(!StringUtils.isNullOrEmpty(devid)) sqlBuilder.append(" and t.devid = '").append(devid).append("'");
		if(flag != 0)
		{
			sqlBuilder.append(" and t.flag = ").append(flag).append(" ");
		}
//		logger.info("sql:{}",sqlBuilder.toString());
		ResultSet resultSet = statement.executeQuery(sqlBuilder.toString());
		List<String> list = new ArrayList<String>();
		while (resultSet.next()) {
			list.add(resultSet.getString(1));
         }
		return list;
	}
	
	@Override
	public int getNewUsersCount(String day, String devid, int flag, String tbName) throws SQLException {
		//获取连接
		Connection conn = hiveJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		sqlBuilder.append("select count(distinct(t.imei)) from ").append(tbName).append(" t where t.day = '").append(day).append("'");
		if(!StringUtils.isNullOrEmpty(devid)) sqlBuilder.append(" and t.devid = '").append(devid).append("'");
		if(flag != 0) sqlBuilder.append(" and t.flag = ").append(flag).append(" ");
		ResultSet resultSet = statement.executeQuery(sqlBuilder.toString());
		
		int res = 0;
		while (resultSet.next()) {
			res = resultSet.getInt(1);
         }
		return res;
	}
	
	@Override
	public List<String> getDistinctFieldsFromHive(String day, String slotName,String table,String field) throws SQLException {
		//获取连接
		Connection conn = hiveJDBC.getConnection();
		Statement statement = conn.createStatement();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.setLength(0);
		sqlBuilder.append("select distinct(t.").append(field).append(") from ").append(table).append(" t where t.day = '").append(day).append("'");
		if(!StringUtils.isNullOrEmpty(slotName)) sqlBuilder.append(" and t.slot_name = '").append(slotName).append("'");
		
		ResultSet resultSet = statement.executeQuery(sqlBuilder.toString());
		List<String> list = new ArrayList<String>();
		while (resultSet.next()) {
			list.add(resultSet.getString(1));
         }
		return list;
	}

}
