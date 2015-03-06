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
import org.pojo.Imei;
import org.service.NewPushStatistic;
import org.service.PushStatistic;
import org.service.SpotStatistic;
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
import com.mysql.jdbc.StringUtils;


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
		
		
		public static Map<String, Long> parseToImeiMap(String path,Map<String, Long> map)
		{
			//进入读文件阶段
			InputStreamReader in = null;
			try 
			{
				in = new InputStreamReader(new FileInputStream(new File("d:/compare/day/" + path + ".txt")), "UTF-8");
				BufferedReader br = new BufferedReader(in);
				String currentJsonStr= null;
				String res = null;
				try {
					//按行读取
					while((currentJsonStr = br.readLine()) != null){
						if(!currentJsonStr.trim().equals("")){
							res = currentJsonStr.trim();
							String[] ss = res.split("\\|\\|");
							if(!StringUtils.isNullOrEmpty(ss[0])) map.put(ss[0], Long.parseLong(ss[1]));
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
		
		
		/**
		 * 查询插屏平台的包名
		 * @param day
		 * @param slotName
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static List<String> getSpotPname(String day,String slotName)
		{
			DBCollection coll = conn.getCollection("biglog", "log_" + day);
			BasicDBObject query = new BasicDBObject();
			query.append("rtype", 1);
			query.append("clientMessage.slot_name", slotName);
			return coll.distinct("clientMessage.pname", query);
		}
		
		
		
		/**
		 * 查询插屏包名的用户数
		 * @param day
		 * @param pname
		 * @param rtype
		 * @return
		 */
		public static int getSpotData(String day,String pname,int rtype)
		{
			DBCollection coll = conn.getCollection("biglog", "log_" + day);
			BasicDBObject query = new BasicDBObject();
			query.append("rtype", rtype);
//			query.append("feedback.pname", pname);
			query.append("clientMessage.pname", pname);
			query.append("clientMessage.slot_name", "45dd279943c3452a865a85f91f72df6a");
//			return coll.distinct("feedback.imei", query).size();
			return coll.distinct("clientMessage.imei", query).size();
		}
		
		public static void getCount(String day)
		{
			String[] pnames = {"com.morega.ldsg" , "com.call.glint" , "com.ylj.glint" , "com.morega.wifiup" , "com.morega.dxsgd" , "com.morega.dxsg" , "com.morega.dxsgtx","com.morega.wifipass" , "com.morega.wxpass"};
			for (String string : pnames) {
				logger.info("日期：{}，包名：{}，终端数：{}。",day,string,getSpotData(day, string, 1));
			}
		}
		
		/**
		 * 根据开发者账号查询插屏包名
		 * @param day
		 * @param devid
		 * @return
		 * @throws SQLException
		 */
		public static List<String> getPname(String day, String devid) throws SQLException {
			Statistic statistic = new Statistic();
			List<String> res = new ArrayList<String>();
			
			List<String>  keys = statistic.getRealSlotNamesByDevidFromHive(devid, day, Constant.TABLE_SPOT_USER_SEVEN);
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
			FileUtil.generate(res, "pname_spot_" + devid + "_" + day);
			return res;
		}
		
		public static List<String> gePushPname(String day, String devid) throws SQLException {
			NewPushStatistic statistic = new NewPushStatistic();
			List<String> res = statistic.getPnameBySlotName(day, devid);
			FileUtil.generate(res, "pname_push_" + devid + "_" + day);
			return res;
		}
		
		
		/**
		 * 根据包名查询请求数
		 * @param day
		 * @param pname
		 * @throws SQLException
		 */
		public static void getSpotRequestCount(String day) throws SQLException {
			String[] pnames = {"com.ecapyddsadsewcsx.onfdssfewafew","com.arbdios.tndfewwdedaedffdfs"};
//			List<String> pnames = getPname(day, devid);
			DBCollection coll = conn.getCollection("biglog", "log_" + day);
			for (String string : pnames) {
				BasicDBObject query = new BasicDBObject();
				query.append("rtype", 1);
				query.append("clientMessage.pname", string);
				logger.info("日期：{}。插屏包名是：{}。请求数：{}",day,string,coll.count(query));
			}
		}
		
		
		public static void getInsideCount(String day,int rtype,int devid) throws SQLException {
			DBCollection coll = conn.getCollection("biglog", "log_" + day);
				BasicDBObject query = new BasicDBObject();
				query.append("rtype", rtype);
				query.append("clientMessage.slot_name", "8a7c1ad4efb64d72aeff83f0ebb063be");
				query.append("clientMessage.format", "spot_inside");
				logger.info("日期：{}。devid:{}，rtype:{}，数：{}",day,devid,rtype,coll.count(query));
		}
		
		
		public static void getInsideCount1(String day,int rtype,int devid) throws SQLException {
			DBCollection coll = conn.getCollection("biglog", "log_" + day);
				BasicDBObject query = new BasicDBObject();
				query.append("rtype", rtype);
//				query.append("clientMessage.slot_name", "15f08934700148dfa73888d0f03f3b78");
				query.append("app.devId", devid);
				query.append("feedback.s_type", 3);
				logger.info("日期：{}。devid:{}，rtype:{}，数：{}",day,devid,rtype,coll.count(query));
		}
		
		
		
		
		public static void getSpotRequestCount(String day, String devid) throws SQLException {
//			String[] pnames = {"com.morega.ldsg","com.morega.dxsgd","com.morega.wxpass","com.ylj.glint","com.fantasticdroid.flashaler","com.lt.lighting","com.morega.wifipass","com.morega.dxsgtx","com.morega.wifiup","com.morega.ldsgs","com.morega.wifibest","com.morega.dxsg","com.call.glint"};
			List<String> pnames = getPname(day, devid);
			DBCollection coll = conn.getCollection("biglog", "log_" + day);
			for (String string : pnames) {
				BasicDBObject query = new BasicDBObject();
				query.append("rtype", 1);
				query.append("clientMessage.pname", string);
				logger.info("日期：{}。插屏包名是：{}。请求数：{}",day,string,coll.count(query));
			}
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
		
		
		private static void writeNewPlatformImeisData(String day) throws SQLException
		{
			if(!FileUtil.hasFile("new_deny_39_" + day))
			{
				NewPushStatistic statistic = new NewPushStatistic();
				List<String> arrs = statistic.getImeis(day);
				FileUtil.generate(arrs, "new_deny_39_" + day);
			}
		}
		
		private static void writeNewPlatformImeisAndTimeData(String day) throws SQLException
		{
			if(!FileUtil.hasFile("new_remain_time_" + day))
			{
				NewPushStatistic statistic = new NewPushStatistic();
				List<Imei> arrs = statistic.getNewPlatformRemainUser(day);
				FileUtil.generateTxt(arrs, "new_remain_time_" + day);
			}
		}
		
		
		private static void writeNewImeisAndTimeFromSpotData(String day) throws SQLException
		{
			if(!FileUtil.hasFile("new_imeis_time_spot_" + day))
			{
				SpotStatistic statistic = new SpotStatistic();
				List<Imei> arrs = statistic.getNewUsers(day);
				FileUtil.generateTxt(arrs, "new_imeis_time_spot_" + day);
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
		
		
		public static void getAllNewUserBySlotName(String day,String slotName) throws SQLException
		{
			
			PushStatistic push = new PushStatistic();
			List<String> pushImeis = push.getNewUsers(day, slotName, Constant.NEW_USER_SEVEN);
			logger.info("日期：{}，slot_name：{}，老推送平台新增用户数：{}",day,slotName,pushImeis.size());
			
			SpotStatistic spotStatistic = new SpotStatistic();
			List<String> spotImeis = spotStatistic.getNewUsers(day, slotName, Constant.NEW_USER_SEVEN);
			logger.info("日期：{}，slot_name：{}，插屏平台新增用户数：{}",day,slotName,spotImeis.size());
			
			NewPushStatistic newpush = new NewPushStatistic();
			List<String> newpushImeis = newpush.getNewUsers(day, slotName, Constant.NEW_USER_SEVEN);
			logger.info("日期：{}，slot_name：{}，新推送平台新增用户数：{}",day,slotName,newpushImeis.size());
			
			for (String imei : pushImeis) {
				if(!newpushImeis.contains(imei)) newpushImeis.add(imei);
			}
			
			logger.info("日期：{}，slot_name：{}，整个推送平台新增用户数：{}",day,slotName,newpushImeis.size());
			
			for (String imei : spotImeis) {
				if(!newpushImeis.contains(imei)) newpushImeis.add(imei);
			}
			
			logger.info("日期：{}，slot_name：{}，新增用户数：{}",day,slotName,newpushImeis.size());
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
		
		
		public static void getAllPushRemain(String day) throws SQLException
		{
			writeRemainData(day);
			writeNewPlatformRemainData(day);
			
			//做留存用户
			Map<String, Integer> oldImeis = parseToMap("remain_" + day);
			Map<String, Integer> newImeis = parseToMap("new_remain_" + day);
			
			logger.info("日期：{}，老推送平台留存：{}",day,oldImeis.size());
			logger.info("日期：{}，新推送平台留存：{}",day,newImeis.size());
			
			for(Map.Entry<String, Integer> entry:oldImeis.entrySet())
			{
				if(!newImeis.containsKey(entry.getKey()))
				{
					newImeis.put(entry.getKey(), 1);
				}
			}
			FileUtil.generate(newImeis, "all_remain_" + day);
			logger.info("日期：{}，全推送平台留存：{}",day,newImeis.size());
		}		
		
		public static void getAllPushNew(String day) throws SQLException
		{
			PushStatistic statistic = new PushStatistic();
			List<String> oldImeis = statistic.getNewUsers(day, null, Constant.NEW_USER_SEVEN);
			FileUtil.generate(oldImeis, "newuser_" + day);
			
			NewPushStatistic newStatistic = new NewPushStatistic();
			List<String> newImeis = newStatistic.getNewUsers(day, null, Constant.NEW_USER_SEVEN);
			FileUtil.generate(newImeis, "new_newuser_" + day);
			
			//做新增用户
			Map<String, Integer> oldImeisMap = parseToMap("newuser_" + day);
			Map<String, Integer> newImeisMap = parseToMap("new_newuser_" + day);
			
			logger.info("日期：{}，老推送平台新增：{}",day,oldImeis.size());
			logger.info("日期：{}，新推送平台新增：{}",day,newImeis.size());
			
			for(Map.Entry<String, Integer> entry:oldImeisMap.entrySet())
			{
				if(!newImeisMap.containsKey(entry.getKey()))
				{
					newImeisMap.put(entry.getKey(), 1);
				}
			}
			FileUtil.generate(newImeisMap, "all_newuser_" + day);
			logger.info("日期：{}，全推送平台新增：{}",day,newImeisMap.size());
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
		
		
		public static void compare() throws SQLException
		{
			Map<String, Integer> imeis = parseToMap("remain_39_imei_20150121");
			Map<String, Integer> newImeis = parseToMap("20150108_20150115_deny_39_");
			
			logger.info("留存用户：{}",imeis.size());
			logger.info("屏蔽用户：{}",newImeis.size());
			
			int i = 0;
			for(Map.Entry<String, Integer> entry : newImeis.entrySet()) 
			{
				if(imeis.containsKey(entry.getKey())) i++;
			}
//			FileUtil.generate(newImeis, "20150119_all_dex");
			logger.info("更新用户：{}",i);
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
		
		/**
		 * 获取一段时间内的每日留存
		 * @param newDay
		 * @param oldDay
		 * @throws SQLException
		 */
		public static void getSomeDaysRemain(String newDay,String oldDay) throws SQLException
		{
			List<String> days = TimeUtil.getDistanceTimeDayArray(newDay, oldDay);
			
//			for (String day : days) {
//				writeNewPlatformRemainData(day);
////				writeNewPlatformImeisAndTimeData(day);
//				Map<String,Integer> ss = parseToMap("new_remain_" + day);
//				logger.debug("日期：{}的留存数据：" + ss.size());
//			}
			
			for (int j = days.size() -1; j > 0; j--) {
				writeNewPlatformRemainData(days.get(j));
//				writeNewPlatformImeisAndTimeData(day);
				Map<String,Integer> ss = parseToMap("new_remain_" + days.get(j));
				logger.debug("日期：{}的留存数据：" + ss.size());
			}
			
			//将所有的数据都暂存在map中去重
//			Map<String, Long> map = new HashMap<String, Long>();
//			for (String day : days) {
//				map = parseToImeiMap("new_remain_time_" + day, map);
//			}
//			logger.debug("日期：{}---{}的留存数据：" + map.size());
//			FileUtil.generateTxt(map, oldDay + "_" + newDay + "_remain_time_");
		}
		
		
		public static void getSomeDaysImeis(String newDay,String oldDay) throws SQLException
		{
			List<String> days = TimeUtil.getDistanceTimeDayArray(newDay, oldDay);
			
			for (String day : days) {
				writeNewPlatformImeisData(day);
			}
			
			//将所有的数据都暂存在map中去重
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (String day : days) {
				map = parseToMap("new_deny_39_" + day, map);
			}
			logger.debug("日期：{}---{}的屏蔽数据：" + map.size());
			FileUtil.generate(map, oldDay + "_" + newDay + "_deny_39_");
		}
		
		
		
		public static void main(String[] args) throws SQLException {
//			compare("20141202", "39");
//			compare("20141201", "39");
//			compare("20141230");
//			getSame();
//			getSameUnique();
//			getData("20141201");
//			comparePushAndSpot();
//			getRemain("20141125");
			
//			getNewPushPlatformRemain("20141125");
			
//			getSpotRemain("20141230");
//			getSpotNewUsers("20141230");
//			getAllPushNewUsers("20141230");
			
			
//			getAllNewUserBySlotName("20150103", "2c40186da2454aec925688a5c6e01890");
//			getAllPushPlatformRemain("20141223","10000");
			
//			compare();
//			getPname("20150118", "59");
//			getSpotRequestCount("20150120", "59");
//			getSpotRequestCount("20150124");
			
//			getSomeDaysRemain("20150120", "20141220");
			
//			getPname("20150124", "6");
//			gePushPname("20150124", "6");
			
//			getPname("20150125", "6");
//			gePushPname("20150125", "6");
			
//			getAllPushRemain("20150114");
//			writeNewPlatformRemainData("20141228");
//			getAllPushNew("20150107");
			
//			getSomeDaysImeis("20150115", "20150108");
//			getSomeDaysRemain("20150116", "20141217");
			
//			writeNewImeisAndTimeFromSpotData("20150201");
			
//			System.out.println("952a723f75af46308506dc54d6584a26: " + getSpotPname("20150201", "952a723f75af46308506dc54d6584a26"));
//			System.out.println("c397fc1ca840462ea0c634af094d4a94: " + getSpotPname("20150201", "c397fc1ca840462ea0c634af094d4a94"));
//			System.out.println("c97475851a184a5c89f423bea28c977a: " + getSpotPname("20150201", "c97475851a184a5c89f423bea28c977a"));
//			System.out.println("e197a95d9e2140b4ad2e67e98ce6413c: " + getSpotPname("20150201", "e197a95d9e2140b4ad2e67e98ce6413c"));
//			System.out.println("b747fa48a8504a9c9593027f657796c6: " + getSpotPname("20150201", "b747fa48a8504a9c9593027f657796c6"));
			
//			getCount("20150201");
//			getInsideCount("20150208",1,10092);
//			getInsideCount1("20150208",2,10092);
//			getInsideCount1("20150208",3,10092);
//			getInsideCount1("20150208",4,10092);
//			getInsideCount1("20150208",5,10092);
//			getInsideCount1("20150208",6,10092);
			
			
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