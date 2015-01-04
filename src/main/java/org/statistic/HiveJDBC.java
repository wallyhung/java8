package org.statistic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HiveJDBC {

//	public static final String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";
	public static final String driverName = "org.apache.hive.jdbc.HiveDriver";
	public static final Logger logger = LoggerFactory.getLogger(HiveJDBC.class);
	public static HiveJDBC jdbc = null;
	
	public Connection getConnection(String url, String userName, String password) 
	{
		try 
		{
			Class.forName(driverName);
			Connection conn = DriverManager.getConnection(url, userName,password);
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("hive获取不到连接...");
			logger.error("具体错误：{}",e);
		}
		return null;
	}
	
	
	/**
	 * 获取固定的连接
	 * @return
	 */
	public Connection getConnection() 
	{
		try 
		{
			Class.forName(driverName);
			Connection conn = DriverManager.getConnection("jdbc:hive2://10.0.3.230:10000/default", "hadoop", "111111");
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("获取不到连接...");
		}
		return null;
	}
	
	
	/**
	 * 单例获取实例
	 * @return
	 */
	public static synchronized HiveJDBC getInstance()
	{
		if(jdbc == null) jdbc = new HiveJDBC();
		return jdbc;
	}
	
	

	public static void main(String args[]) {
		Connection con = new HiveJDBC().getConnection(
				"jdbc:hive2://10.0.3.230:10000/default", "root", "111111");
		try {
			Statement stmt = con.createStatement();
			String sql = "show tables";

			ResultSet res = stmt.executeQuery(sql);
			while (res.next()) {
				sql = "select * from " + res.getString(1) + " limit 1";
				System.out.println("tables:" + res.getString(1));

				ResultSet resTable = stmt.executeQuery(sql);
				while (resTable.next()) {
					System.out.println(resTable.getString(2));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
