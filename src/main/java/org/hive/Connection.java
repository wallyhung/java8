package org.hive;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;


public class Connection {
	
	private static Connection connection = null;
	private static final Properties properties = new Properties();
	private static MongoClient client = null;
	
	static {
    	try {
    		properties.load(Connection.class.getResourceAsStream("/mongo.properties"));
    		init();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public static synchronized Connection getInstance()
	{
		if(connection == null) connection = new Connection();
		return connection;
	}
	
	
	private static void init()
	{
		ServerAddress sa = null;
		try {
			sa = new ServerAddress(properties.getProperty("server"),Integer.parseInt(properties.getProperty("port")));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(Integer.parseInt(properties.getProperty("connectionsPerHost"))).build();
		client = new MongoClient(sa, options);
	}
	
	public DB getDB()
	{
		DB db = client.getDB(properties.getProperty("mongo.database"));
		return db;
	}
	
	public DB getDB(String db)
	{
		return client.getDB(db);
	}
	
	public DBCollection getCollection()
	{
		DB db = getDB();
		return db.getCollection("log");
	}
	
	
	public DBCollection getCollection(String coll)
	{
		DB db = getDB();
		return db.getCollection(coll);
	}
	
	public DBCollection getCollection(String db,String coll)
	{
		return getDB(db).getCollection(coll);
	}
	
	
	public DBCollection createCollection(String name,DBObject options)
	{
		DB db = getDB();
		return db.createCollection(name, options);
	}
	

}
