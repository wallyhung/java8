package org.statistic;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MysqlJDBC 
{
	private final static Logger logger = LoggerFactory.getLogger(MysqlJDBC.class);
	private static final Properties properties = new Properties();
	private static MysqlJDBC jdbc = null;
	
	public Connection getConnection()
	{
		Connection connection = null;
		InputStream in = null;
		try
		{
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties");
			properties.load(in);
			Class.forName(properties.getProperty("driver"));
			connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("password"));
		}catch(Exception e)
		{
			logger.error("database config error:{}",e.getMessage());
		}finally{
			try {
				if(in != null){
					in.close();
				}
			} catch (IOException e) 
			{
				logger.error("ioexception in close fail:{}",e.getMessage());
			}
		}
		return connection;
	}
	
	
	/**
	 * 单例获取实例
	 * @return
	 */
	public static synchronized MysqlJDBC getInstance()
	{
		if(jdbc == null) jdbc = new MysqlJDBC();
		return jdbc;
	}
	
	
	
	//关闭连接
	public void close(Connection connection){
		try {
			if(connection != null){
				connection.close();
			}
		} catch (SQLException e) {
			logger.error("inputstream in close fail:{}",e.getMessage());
		}
	}
	//关闭连接
	public void CloseAll(ResultSet rs, Statement st,Connection connection){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		if(st != null){
			try {
				st.close();
			} catch (SQLException e) {
			}
		}
		if(connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
			}
		}
	}
}
