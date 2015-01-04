package org.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class HiveJDBC {
	
	 public static final String driverName = "org.apache.hive.jdbc.HiveDriver";
	    /**
	     * 获取连接 
	     */
	    public Connection getConnection(String url,String userName,String password){
	        try {
	            Class.forName(driverName);
	            Connection conn = DriverManager.getConnection(url, userName, password);
	            return conn;
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	     
	    public static void main(String args[]){
	        Connection con = new HiveJDBC().getConnection(
	            "jdbc:hive2://10.0.3.230:10000/default", "root", "111111");
	        try {
	            Statement stmt = con.createStatement();
	            String sql = "show tables";
	             
	            ResultSet res = stmt.executeQuery(sql);
	            while(res.next()) {
	                sql = "select * from " + res.getString(1);
	                System.out.println("tables:" + res.getString(1));
	                 
	                ResultSet resTable = stmt.executeQuery(sql);
	                while(resTable.next()){
	                    System.out.println(resTable.getString(2));
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
}
