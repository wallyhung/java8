package org.statistic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hive.Connection;
import org.service.NewPushStatistic;
import org.service.PushStatistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.CompareUtil;
import org.util.Constant;
import org.util.FileUtil;
import org.util.TimeUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceOutput;


/**
 * 查询包名
 * @author wally
 *
 */

public class PnameCompare {
		private static final Logger logger = LoggerFactory.getLogger(PnameCompare.class);
		private static Connection conn = Connection.getInstance();
		
		public static Map<String, Integer> parseToMap(String path)
		{
			Map<String, Integer> map = new HashMap<String, Integer>();
			//进入读文件阶段
			InputStreamReader in = null;
			int idx = 0;
			try 
			{
				in = new InputStreamReader(new FileInputStream(new File("d:/compare/day/" + path + ".txt")), "UTF-8");
				BufferedReader br = new BufferedReader(in);
				String currentJsonStr= null;
				String res = null;
				try {
					//按行读取
					while((currentJsonStr = br.readLine()) != null){
						idx++;
						if(!currentJsonStr.trim().equals("")){
							res = currentJsonStr.trim();
							if(res.endsWith(",")) res = res.substring(0, res.length()-1);
							map.put(res, idx);
						}
					}
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
				finally{
					if (br != null) {
		                try {
		                    br.close();
		                } catch (IOException e1) {
		                	logger.error("关闭读取文件的缓冲流出错：{}。",e1.getMessage());
		                }
		            }
					if (in != null) {
		                try {
		                    in.close();
		                } catch (IOException e2) {
		                	logger.error("关闭读取文件的缓冲流出错：{}。",e2.getMessage());
		                }
		            }
				}
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			} catch (UnsupportedEncodingException e3) {
				logger.error(e3.getMessage());
			} 
			return map;
		}
		
		public static Map<String, Integer> parseToMap(String path,Map<String, Integer> map)
		{
			//进入读文件阶段
			InputStreamReader in = null;
			int idx = 0;
			try 
			{
				in = new InputStreamReader(new FileInputStream(new File("d:/compare/day/" + path + ".txt")), "UTF-8");
				BufferedReader br = new BufferedReader(in);
				String currentJsonStr= null;
				String res = null;
				try {
					//按行读取
					while((currentJsonStr = br.readLine()) != null){
						idx++;
						if(!currentJsonStr.trim().equals("")){
							res = currentJsonStr.trim();
							if(res.endsWith(",")) res = res.substring(0, res.length()-1);
							map.put(res, idx);
						}
					}
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
				finally{
					if (br != null) {
		                try {
		                    br.close();
		                } catch (IOException e1) {
		                	logger.error("关闭读取文件的缓冲流出错：{}。",e1.getMessage());
		                }
		            }
					if (in != null) {
		                try {
		                    in.close();
		                } catch (IOException e2) {
		                	logger.error("关闭读取文件的缓冲流出错：{}。",e2.getMessage());
		                }
		            }
				}
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			} catch (UnsupportedEncodingException e3) {
				logger.error(e3.getMessage());
			} 
			return map;
		}
		
		public static List<String> parseToList(String path)
		{
			List<String> list = new ArrayList<String>();
			//进入读文件阶段
			InputStreamReader in = null;
			try 
			{
				in = new InputStreamReader(new FileInputStream(new File(path)), "UTF-8");
				BufferedReader br = new BufferedReader(in);
				String currentJsonStr= null;
				String res = null;
				try {
					//按行读取
					while((currentJsonStr = br.readLine()) != null){
						if(!currentJsonStr.trim().equals("")){
							res = currentJsonStr.trim();
							if(res.endsWith(",")) res = res.substring(0, res.length()-1);
							list.add(res);
						}
					}
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
				finally{
					if (br != null) {
		                try {
		                    br.close();
		                } catch (IOException e1) {
		                	logger.error("关闭读取文件的缓冲流出错：{}。",e1.getMessage());
		                }
		            }
					if (in != null) {
		                try {
		                    in.close();
		                } catch (IOException e2) {
		                	logger.error("关闭读取文件的缓冲流出错：{}。",e2.getMessage());
		                }
		            }
				}
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			} catch (UnsupportedEncodingException e3) {
				logger.error(e3.getMessage());
			} 
			return list;
		}
		
		
		@SuppressWarnings("unchecked")
		public static List<String> getSpotPname(String day,String slotName)
		{
			DBCollection coll = conn.getCollection("biglog", "log_" + day);
			BasicDBObject query = new BasicDBObject();
			query.append("rtype", 1);
			query.append("clientMessage.slot_name", slotName);
			return coll.distinct("clientMessage.pname", query);
		}
		
		
		public static int getSpotData(String day,String pname,int rtype)
		{
			DBCollection coll = conn.getCollection("biglog", "log_" + day);
			BasicDBObject query = new BasicDBObject();
			query.append("rtype", rtype);
			query.append("feedback.pname", pname);
			return coll.distinct("feedback.imei", query).size();
		}
		
		
		public static List<String> getPname(String day, String devid) throws SQLException {
			PushStatistic pushStatistic = new PushStatistic();
			List<String> res = new ArrayList<String>();
			
			List<String>  keys = pushStatistic.getSlotNameFromMysql(devid);
			System.out.println("应用个数：" + keys.size());
			
			int i = 0;
			DecimalFormat df = new DecimalFormat("0.0%");
			for (String key : keys) {
				logger.info("日期：{}。现在获取包名的插屏应用是：{}",day,key);
				logger.info("日期：{}。采集进度：{},还剩下{}个应用需要采集....",day,df.format((float)i/keys.size()),keys.size()-i);
				List<String> pnames = getSpotPname(day, key);
				logger.info("日期：{}，应用key：{}，包名：{}",day,key,pnames);
				res.addAll(pnames);
				i++;
			}
			return res;
		}
		
		
		
		public static void getDiff()
		{
			List<String> l1 = parseToList("d:/compare/spot_pname_20141201.txt");
			List<String> l2 = parseToList("d:/compare/push_pname_20141201.txt");
			System.out.println("插屏有，推送木有：" + CompareUtil.getDiffList(l2, l1));
			System.out.println("插屏木有，推送有：" + CompareUtil.getDiffList(l1, l2));
		}
		
		
		public static void getSame()
		{
			List<String> l1 = parseToList("d:/compare/spot_pname_20141201.txt");
			List<String> l2 = parseToList("d:/compare/push_pname_20141201.txt");
			System.out.println("推送和插屏都有的包名：" + CompareUtil.getSameList(l1, l2));
			System.out.println("推送和插屏都有的包名：" + CompareUtil.getSameList(l2, l1));
		}
		
		public static void getSameUnique()
		{
			Map<String , Integer> pushMap = parseToMap("d:/compare/spot_pname_20141203.txt");
			Map<String , Integer> spotMap = parseToMap("d:/compare/push_pname_20141203.txt");
			System.out.println(pushMap.size() + ":" + spotMap.size());
			
			System.out.println("推送和插屏都有的包名：");
			for(Map.Entry<String, Integer> entry : spotMap.entrySet())
			{
				if(pushMap.containsKey(entry.getKey()))  System.out.println(entry.getKey());
			}
			
			System.out.println("插屏和推送都有的包名：");
			for(Map.Entry<String, Integer> entry : pushMap.entrySet())
			{
				if(spotMap.containsKey(entry.getKey())) System.out.println(entry.getKey());
			}
//			System.out.println("推送和插屏都有的包名：" + CompareUtil.getSameList(l1, l2));
//			System.out.println("推送和插屏都有的包名：" + CompareUtil.getSameList(l2, l1));
		}
		
		public static void getData(String day)
		{
			String[] samePnames = {"com.game.PoolMania",
					"com.seventeenbullets.android.island",
					"com.fighter.activity",
					"com.devuni.flashlight",
					"com.bringmore.snipervsniper",
					"com.sniper.activitied",
					"com.game.SkaterBoy",
					"com.bfs.ninjfan",
					"com.bestappshouse.piperoll2ages",
					"com.john.plasmasky",
					"com.xllusion.livewallpaper.sakurapro"};
			
			for (String pname : samePnames) {
				System.out.println("包名：" + pname + "。展示用户数：" + getSpotData(day, pname, Constant.RTYPE_VIEW) + "；安装用户数：" + getSpotData(day, pname, Constant.RTYPE_INSTALL));
			}
		}
		
		
		

		
		public static void compare(String day, String devid) throws SQLException
		{
			Statistic statistic = new Statistic();
//			List<String> pushPnames = null;
			List<String> pushPnames = statistic.getPname(day, devid);
			FileUtil.generate(pushPnames, "push_pname_"+ day);
			List<String> spotPname = getPname(day, devid);
			FileUtil.generate(spotPname, "spot_pname_" + day);
			System.out.println("插屏有，推送木有：" + CompareUtil.getDiffList(pushPnames, spotPname));
			logger.info("插屏有，推送木有：" + CompareUtil.getDiffList(pushPnames, spotPname));
			System.out.println("推送有，插屏没有：" + CompareUtil.getDiffList(spotPname, pushPnames));
			logger.info("推送有，插屏没有：" + CompareUtil.getDiffList(spotPname, pushPnames));
		}
		
		
		public static void comparePushAndSpot()
		{
			Map<String , Integer> pushMap = parseToMap("d:/compare/push_39_20141204.txt.txt");
			Map<String , Integer> spotMap = parseToMap("d:/compare/spot_newuser_39_7_20141204.txt");
			
			int i = 0;
			System.out.println("push：" + pushMap.size());
			for(Map.Entry<String, Integer> entry : spotMap.entrySet())
			{
				if(!pushMap.containsKey(entry.getKey()))  pushMap.put(entry.getKey(), 100);
				else i++;
			}
			System.out.println("相同的个数：" + i);
			System.out.println(pushMap.size());
		}
		
		
		@SuppressWarnings("unchecked")
		public static void getSpotRemain(String day) throws SQLException
		{
			if(!FileUtil.hasFile("spot_remain_" + day))
			{
				DBCollection coll = conn.getCollection("biglog", "log_" + day);
				BasicDBObject query = new BasicDBObject();
				query.append("rtype", 1);
				List<String> list = coll.distinct("clientMessage.imei", query);
				FileUtil.generate(list, "spot_remain_" + day);
			}
		}
		
		
		
		private static void writeRemainData(String day) throws SQLException
		{
			if(!FileUtil.hasFile("remain_" + day))
			{
				PushStatistic statistic = new PushStatistic();
				List<String> arrs = statistic.getRemainUser(day, null);
				FileUtil.generate(arrs, "remain_" + day);
			}
		}
		
		private static void writeNewPlatformRemainData(String day) throws SQLException
		{
			if(!FileUtil.hasFile("new_remain_" + day))
			{
				NewPushStatistic statistic = new NewPushStatistic();
				List<String> arrs = statistic.getNewPlatformRemainUser(day, null);
				FileUtil.generate(arrs, "new_remain_" + day);
			}
		}
		
		public static void getRemain(String day) throws SQLException
		{
			
//			PushStatistic statistic = new PushStatistic();
//			List<String> arrs = statistic.getSevenNewUser(day, null);
//			FileUtil.generate(arrs, "push_newuser_7_" + day);
			
			/**做每天的留存用户*/
			List<String> sevens = TimeUtil.getNextSevensTimeDayArray(day);
			for (String newday : sevens) {
				writeRemainData(newday);
			}
			
			/*获取接下来7天的天字符串*/
//			List<String> sevens = TimeUtil.getNextSevensTimeDayArray(day);
			
			Map<String, Integer> newUserMap = parseToMap("push_newuser_7_" + day);
			System.out.println("日期：" + day + "，当天的新增用户数：" + newUserMap.size());
			Map<String, Integer> map = null;
			for (String newday : sevens) {
				map = parseToMap("remain_" + newday);
				int i = 0;
				for(Map.Entry<String, Integer> entry:newUserMap.entrySet())
				{
					if(map.containsKey(entry.getKey())) i++;
				}
				System.out.println("日期：" + newday + "，当天的留存用户数：" + map.size() + "；" + day + "的新增遗留用户数：" + i++);
			}
		}
		
		public static void getNewPushPlatformRemain(String day) throws SQLException
		{
			
//			PushStatistic statistic = new PushStatistic();
//			List<String> arrs = statistic.getSevenNewUserFromNewPlatform(day, null);
//			FileUtil.generate(arrs, "new_push_newuser_7_" + day);
			
			/**做每天的留存用户*/
			List<String> sevens = TimeUtil.getNextSevensTimeDayArray(day);
			for (String newday : sevens) {
				writeNewPlatformRemainData(newday);
			}
			
			
			/*获取接下来7天的天字符串*/
//			List<String> sevens = TimeUtil.getNextSevensTimeDayArray(day);
			
			Map<String, Integer> newUserMap = parseToMap("new_push_newuser_7_" + day);
			System.out.println("日期：" + day + "，新平台当天的新增用户数：" + newUserMap.size());
			Map<String, Integer> map = null;
			for (String newday : sevens) {
				map = parseToMap("new_remain_" + newday);
				int i = 0;
				for(Map.Entry<String, Integer> entry:newUserMap.entrySet())
				{
					if(map.containsKey(entry.getKey())) i++;
				}
				System.out.println("日期：" + newday + "，新平台当天的留存用户数：" + map.size() + "；" + day + "的新增遗留用户数：" + i++);
			}
		}
		
		public static void getAllPushPlatformRemain(String day,String devid) throws SQLException
		{
			
			Statistic statistic = new Statistic();
			List<String> arrs = statistic.getSevenNewUserForAllPushPlatform(devid, day);
			
			/**做每天的留存用户*/
			List<String> sevens = TimeUtil.getNextSevensTimeDayArray(day);
			
			for (String newday : sevens) {
				writeRemainData(newday);
				writeNewPlatformRemainData(newday);
			}
			
//			Map<String, Integer> newUserMap = parseToMap("new_push_newuser_7_" + day);
			logger.info("devid：{}，日期：{}，7天整个推送平台新增：{}",devid,day,arrs.size());
			System.out.println("日期：" + day + "，devid：" + devid + "；7天整个推送平台新增：" + arrs.size());
			Map<String, Integer> map = null;
			for (String newday : sevens) {
				map = parseToMap("remain_" + newday);
				map = parseToMap("new_remain_" + newday,map);
				int i = 0;
				for(String nu:arrs)
				{
					if(map.containsKey(nu)) i++;
				}
				System.out.println("日期：" + newday + "，当天的留存用户数：" + map.size() + "；" + day + "的新增遗留用户数：" + i++);
			}
		}
		
		
		public static void getAllPushNewUsers(String day) throws SQLException
		{
			PushStatistic statistic = new PushStatistic();
			List<String> oldImeis = statistic.getNewUsers(day, null, Constant.NEW_USER_SEVEN);
			
			//做留存用户
			Map<String, Integer> sevenImeis = new HashMap<String, Integer>();
			Map<String, Integer> todayRemains = new HashMap<String, Integer>();
			
			List<String> times = TimeUtil.getDistanceTimeDayArray("20141231", "20141222");
			for (String string : times) {
				writeNewPlatformRemainData(string);
				if("20141230".equals(string))  todayRemains = parseToMap("new_remain_" + string, todayRemains);
				else{
					sevenImeis = parseToMap("new_remain_" + string, sevenImeis);
				}
			}
			
			Map<String, Integer> newImeis = new HashMap<String, Integer>();
			for(Map.Entry<String, Integer> entry:todayRemains.entrySet())
			{
				if(!sevenImeis.containsKey(entry.getKey()))
				{
					newImeis.put(entry.getKey(), 1);
				}
			}
			
			logger.info("日期：{}，老推送平台新增：{}",day,oldImeis.size());
			logger.info("日期：{}，新推送平台新增：{}",day,newImeis.size());
			
			for (String imei : oldImeis) {
				if(!newImeis.containsKey(imei)) newImeis.put(imei, 0);
			}
			logger.info("日期：{}，全推送平台新增：{}",day,newImeis.size());
		}
		
		
		public static void getSpotNewUsers(String day) throws SQLException
		{
			//做留存用户
			Map<String, Integer> sevenImeis = new HashMap<String, Integer>();
			Map<String, Integer> todayRemains = new HashMap<String, Integer>();
			
			List<String> times = TimeUtil.getDistanceTimeDayArray("20141231", "20141222");
			for (String string : times) {
				getSpotRemain(string);
				if("20141230".equals(string))  todayRemains = parseToMap("spot_remain_" + string, todayRemains);
				else{
					sevenImeis = parseToMap("spot_remain_" + string, sevenImeis);
				}
			}
			
			Map<String, Integer> newImeis = new HashMap<String, Integer>();
			for(Map.Entry<String, Integer> entry:todayRemains.entrySet())
			{
				if(!sevenImeis.containsKey(entry.getKey()))
				{
					newImeis.put(entry.getKey(), 1);
				}
			}
			
			logger.info("日期：{}，插屏平台新增：{}",day,newImeis.size());
		}
		
		
		
		
		
		public static void compare(String day) throws SQLException
		{
			writeRemainData(day);
			writeNewPlatformRemainData(day);
			
			Map<String, Integer> imeis = parseToMap("remain_" + day);
			Map<String, Integer> newImeis = parseToMap("new_remain_" + day);
			
			int i = 0;
			for(Map.Entry<String, Integer> entry : imeis.entrySet()) 
			{
				if(newImeis.containsKey(entry.getKey())) i++;
			}
			
			logger.info("日期：{}，老平台留存用户：{}",day,imeis.size());
			logger.info("日期：{}，新平台留存用户：{}",day,newImeis.size());
			logger.info("日期：{}，重复用户：{}",day,i);
		}
		
		
		
		
		public static void getAllRemain(String day) throws SQLException
		{
			/*获取接下来7天的天字符串*/
			List<String> sevens = TimeUtil.getNextSevensTimeDayArray(day);
			
			Map<String, Integer> newUserMap = parseToMap("push_newuser_7_" + day);
			parseToMap("new_push_newuser_7_" + day, newUserMap);
			System.out.println("日期：" + day + "，整个平台当天的新增用户数：" + newUserMap.size());
			Map<String, Integer> map = null;
			for (String newday : sevens) {
				map = parseToMap("remain_" + newday);
				parseToMap("remain_" + newday, map);
				int i = 0;
				for(Map.Entry<String, Integer> entry:newUserMap.entrySet())
				{
					if(map.containsKey(entry.getKey())) i++;
				}
				System.out.println("日期：" + newday + "，整个平台当天的留存用户数：" + map.size() + "；" + day + "的新增遗留用户数：" + i++);
			}
		}
		
		
		
		
		public static void main(String[] args) throws SQLException {
//			compare("20141202", "39");
//			compare("20141201", "39");
//			compare("20141230");
//			getSame();
//			getSameUnique();
//			getData("20141201");
//			comparePushAndSpot();
//			getRemain("20140918");
//			getRemain("20140919");
//			getRemain("20140920");
//			getRemain("20140921");
//			getRemain("20140922");
//			getRemain("20140923");
//			getRemain("20141016");
//			getRemain("20141017");
//			getRemain("20141018");
//			getRemain("20141019");
//			getRemain("20141020");
//			getRemain("20141021");
//			getRemain("20141119");
//			getRemain("20141120");
//			getRemain("20141121");
//			getRemain("20141122");
//			getRemain("20141123");
//			getRemain("20141124");
//			getRemain("20141125");
			
//			getNewPushPlatformRemain("20141119");
//			getNewPushPlatformRemain("20141120");
//			getNewPushPlatformRemain("20141121");
//			getNewPushPlatformRemain("20141122");
//			getNewPushPlatformRemain("20141123");
//			getNewPushPlatformRemain("20141124");
//			getNewPushPlatformRemain("20141125");
			
//			getSpotRemain("20141230");
//			getSpotNewUsers("20141230");
			getAllPushNewUsers("20141230");
			
			
//			getAllPushPlatformRemain("20141216","10000");
//			getAllPushPlatformRemain("20141217","10000");
//			getAllPushPlatformRemain("20141218","10000");
//			getAllPushPlatformRemain("20141219","10000");
//			getAllPushPlatformRemain("20141220","10000");
//			getAllPushPlatformRemain("20141221","10000");
//			getAllPushPlatformRemain("20141222","10000");
//			getAllPushPlatformRemain("20141223","10000");
			
//			compare("20141225");
			
//			System.out.println(parseDay("20141215"));
		}
		
		
		
		@SuppressWarnings("unchecked")
		public static List<String> getNewUserByDevid(String day,String devid)
		{
			day = parseDay(day);
		    //获取数据库一个实例
			Connection conn = Connection.getInstance();
			DBCollection coll = conn.getCollection("data", "app_new_user");
	//      Datastore ds = MongoDBDataStore.getData();
	//      DBCollection coll = ds.getCollection(AppNewImei.class);
			
	        BasicDBObject query = new BasicDBObject(); 
	        query.append("hour", new BasicDBObject().append("$regex", day));
	        query.append("devid", Integer.parseInt(devid));
	        
	        List<String> list = new ArrayList<String>();
	//        System.out.println("总共个数："+ coll.count(query));
	        try {
	        	list = coll.distinct("value", query);
			} catch (Exception e) {
				//exception: distinct too big, 16mb cap
				String map = "function(){" 
			               + "emit(this.value, {count:1})"
						   + "}";
				
				String reduce = "function (key, values) {"
							  + "var res = {count:0};"
							  + "for (var i = 0; i < values.length; i++) {"
								    + "res.count += values[i].count; "
								+"}"
								+"return res;"
							+"}";
				String outputCollection = "new";
			  //获取数据库一个实例
			  MapReduceOutput out = coll.mapReduce(map, reduce, outputCollection, query);
			  //处理查询结果并入库
			  list = analyzeMapReduceResult(out,list);
			}
			return list;
	}
	
	
	
		private static List<String> analyzeMapReduceResult(MapReduceOutput out,List<String> list) 
		{
			DBCollection dc = out.getOutputCollection();
	        DBCursor cursor = dc.find();
	        while (cursor.hasNext()) 
	        {
				DBObject dbObject = (DBObject) cursor.next();
				String imei =  (String) dbObject.get("_id");
				list.add(imei);
			}
	        return list;
		}
		
		@SuppressWarnings("unchecked")
		public static List<String> getRemainUserByDevid(String day,String slotName)
		{
			day = parseDay(day);
			Connection conn = Connection.getInstance();
			DBCollection coll = conn.getCollection("data", "app_imei");
			
			BasicDBObject query = new BasicDBObject(); 
	        query.append("hour", new BasicDBObject().append("$regex", day));
	        query.append("fid", slotName);
	        return coll.distinct("value", query);
		}
		
		public static String parseDay(String day)
		{
			return day.substring(0,4) + "-" + day.substring(4,6) + "-" + day.substring(6,8);
		}
		

	}