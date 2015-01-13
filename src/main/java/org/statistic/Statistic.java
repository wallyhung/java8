package org.statistic;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.hive.ql.history.HiveHistory.Keys;
import org.service.NewPushStatistic;
import org.service.PushStatistic;
import org.service.SpotStatistic;
import org.service.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.CompareUtil;
import org.util.Constant;
import org.util.FileUtil;



public class Statistic {
	
	public static final Logger logger = LoggerFactory.getLogger(Statistic.class);
	private PushStatistic pushStatistic = new PushStatistic();
	private SpotStatistic spotStatistic = new SpotStatistic();
	private NewPushStatistic newPushStatistic = new NewPushStatistic();
	private PnameCompare pc = new PnameCompare();
	
	/**
	 * 30天推送或插屏的基础数据采集
	 * @throws SQLException 
	 */
	public void doBaseNewUserData(String day) throws SQLException
	{
		//缓存所有的key和开发者账号信息
		Map<String, String> devMap = pushStatistic.getDevInfo();
		//采集30天的推送新增用户
		List<String> pushKeys = pushStatistic.getSlotNameFromHive(day);
		logger.info("推送应用有{}个：",pushKeys.size());
		
		DecimalFormat df = new DecimalFormat("0.0%");
		
		int i = 0;
		for (String key : pushKeys) {
			logger.info("现在采集推送新增用户的应用是：{}",key);
			logger.info("采集进度：{},还剩下{}个应用需要采集....",df.format((float)i/pushKeys.size()),pushKeys.size()-i);
			pushStatistic.getNewUserIntoHive(day, key, devMap.get(key),Constant.NEW_USER_THIRTY);
			i++;
		}
		
		//采集30天的插屏新增用户
        List<String> spotKeys = spotStatistic.getSlotNameFromHive(day);
        logger.info("插屏应用有{}个：",spotKeys.size());
        
        logger.info("插屏有，推送木有的应用：{}：{}",CompareUtil.getDiffSize(pushKeys, spotKeys),CompareUtil.getDiffList(pushKeys, spotKeys));
        logger.info("推送有，插屏木有的应用：{}：{}",CompareUtil.getDiffSize(spotKeys, pushKeys),CompareUtil.getDiffList(spotKeys, pushKeys));
		
        int t = 0;
		for (String key : spotKeys) {
			logger.info("现在采集插屏新增用户的应用是：{}",key);
			logger.info("采集进度：{},还剩下{}个应用需要采集....",df.format((float)t/spotKeys.size()),spotKeys.size()-t);
			spotStatistic.getNewUserIntoHive(day, key, devMap.get(key),Constant.NEW_USER_THIRTY);
			t++;
		}
        
        //一次创建所有的应用
//        List<Thread> threads = new ArrayList<Thread>(pushKeys.size() + spotKeys.size());
//        List<Callable<String>> tasks = new ArrayList<Callable<String>>(pushKeys.size() + spotKeys.size());
//        for (String key : pushKeys) {
////        	threads.add(new Thread(new CollectDataThread(day, key, devMap.get(key), new PushStatistic())));
//        	tasks.add(new CollectDataTask(day, key, devMap.get(key), new PushStatistic()));
//		}
//        
//        for (String key : spotKeys) {
////        	threads.add(new Thread(new CollectDataThread(day, key, devMap.get(key), new SpotStatistic())));
//        	tasks.add(new CollectDataTask(day, key, devMap.get(key), new SpotStatistic()));
//		}
//        handleTasks(tasks, 3);
	}
	
	
	public List<String> getPname(String day, String devid) throws SQLException {
		List<String> res = new ArrayList<String>();
		List<String>  keys = pushStatistic.getSlotNameFromMysql(devid);
		System.out.println("应用个数：" + keys.size());
		
		int i = 0;
		DecimalFormat df = new DecimalFormat("0.0%");
		for (String key : keys) {
			logger.info("日期：{}。现在获取包名的推送应用是：{}",day,key);
			logger.info("日期：{}。采集进度：{},还剩下{}个应用需要采集....",day,df.format((float)i/keys.size()),keys.size()-i);
			List<String> pnames = pushStatistic.getPnameBySlotName(day, key);
			logger.info("日期：{}，应用key：{}，包名：{}",day,key,pnames);
			res.addAll(pnames);
			i++;
		}
		return res;
	}
	
	
	/**
	 * 做临时任务
	 * @throws SQLException 
	 */
	public void tempTask(String devid,String day) throws SQLException
	{
		//缓存所有的key和开发者账号信息
		Map<String, String> devMap = pushStatistic.getDevInfo();
//		List<String>  keys = pushStatistic.getSlotNameFromMysql(devid);
//		String[] keys = {"3f90b77c8ce74040b4658f36626bdde1"};
		
		List<String>  keys = getRealSlotNamesByDevidFromHive(devid, day, Constant.TABLE_PUSH);
		int length = keys.size();
		logger.info("devid：{}，日期：{}，推送实际应用个数：{}",devid,day,length);
		int i = 0;
		DecimalFormat df = new DecimalFormat("0.0%");
		for (String key : keys) {
			logger.info("现在采集推送新增用户的应用是：{}",key);
			logger.info("采集进度：{},还剩下{}个应用需要采集....",df.format((float)i/length),length-i);
			pushStatistic.getNewUserIntoHive(day, key, devMap.get(key),Constant.NEW_USER_THIRTY);
			i++;
		}
		logger.info("采集进度：{}....",df.format((float)i/length));
		
		
		keys = getRealSlotNamesByDevidFromHive(devid, day, Constant.TABLE_SPOT_USER_SEVEN);
		length = keys.size();
		logger.info("devid：{}，日期：{}，插屏实际应用个数：",devid,day,length);
		i = 0;
		for (String key : keys) {
			logger.info("现在采集插屏新增用户的应用是：{}",key);
			logger.info("采集进度：{},还剩下{}个应用需要采集....",df.format((float)i/length),length-i);
			spotStatistic.getNewUserIntoHive(day, key, devMap.get(key),Constant.NEW_USER_THIRTY);
			i++;
		}
		logger.info("采集进度：{}....",df.format((float)i/length));
		
		keys = getRealSlotNamesByDevidFromHive(devid, day, Constant.TABLE_NEW_PUSH_USER_SEVEN);
		length = keys.size();
		logger.info("devid：{}，日期：{}，新推送实际应用个数：",devid,day,length);
		i = 0;
		for (String key : keys) {
			logger.info("现在采集新推送新增用户的应用是：{}",key);
			logger.info("采集进度：{},还剩下{}个应用需要采集....",df.format((float)i/length),length-i);
			newPushStatistic.getNewUserIntoHive(day, key, devMap.get(key),Constant.NEW_USER_THIRTY);
			i++;
		}
		logger.info("采集进度：{}....",df.format((float)i/length));
	}
	
	
	/**
	 * 获取整个推送平台的新增用户
	 * @param devid
	 * @param day
	 * @throws SQLException
	 */
	public List<String> getSevenNewUserForAllPushPlatform(String devid,String day) throws SQLException
	{
		//缓存所有的key和开发者账号信息
		Map<String, String> devMap = pushStatistic.getDevInfo();
//		List<String>  keys = pushStatistic.getSlotNameFromMysql(devid);
//		String[] keys = {"3f90b77c8ce74040b4658f36626bdde1"};
		
//		List<String>  keys = getRealSlotNamesByDevidFromHive(devid, day, Constant.TABLE_PUSH);
//		int length = keys.size();
//		logger.info("devid：{}，日期：{}，推送实际应用个数：{}",devid,day,length);
//		int i = 0;
//		DecimalFormat df = new DecimalFormat("0.0%");
//		for (String key : keys) {
//			logger.info("现在采集推送新增用户的应用是：{}",key);
//			logger.info("采集进度：{},还剩下{}个应用需要采集....",df.format((float)i/length),length-i);
//			pushStatistic.getNewUserIntoHive(day, key, devMap.get(key),Constant.NEW_USER_SEVEN);
//			i++;
//		}
//		logger.info("采集进度：{}....",df.format((float)i/length));
		
		//获取用户
		List<String> pushImeis = pushStatistic.getNewUsers(day, devid, Constant.FLAG_PUSH, Constant.TABLE_USER_SEVEN);
//		List<String> newPushImeis = pushStatistic.getNewUsers(day, Integer.parseInt(devid), Constant.FLAG_SPOT, Constant.TABLE_NEW_PUSH_USER_SEVEN);
		
		List<String> newPushImeis = pc.parseToList("d:/compare/day/new_newuser_39_20150111.txt");
		
		logger.info("devid：{}，老平台7天新增用户：{}",devid,pushImeis.size());
		logger.info("devid：{}，新平台7天新增用户：{}",devid,newPushImeis.size());
		
		for (String imei : newPushImeis) {
			if(!pushImeis.contains(imei)) pushImeis.add(imei);
		}
		
		logger.info("devid：{}，全平台7天新增用户：{}",devid,pushImeis.size());
		FileUtil.generate(pushImeis, "newuser_" + devid + "_" + day);
		return pushImeis;
	}
	
	
	/**
	 * 做账号的老推送平台的7天新增数据
	 * @param devid
	 * @param day
	 * @throws SQLException
	 */
	public void tempDevidTask(String devid,String day) throws SQLException
	{
		//缓存所有的key和开发者账号信息
		Map<String, String> devMap = spotStatistic.getDevInfo();
//		List<String>  keys = pushStatistic.getSlotNameFromMysql(devid);
		
		List<String>  keys = getRealSlotNamesByDevidFromHive(devid, day, Constant.TABLE_PUSH);
//		String[] keys = {"d086a0ff984848568fc9bbbc53ecd80d","82051513a263407d8abec3ae2c7462e2","3c605a261a7c416ebecaa0993ffc0898","4e06d21a77d64cc1bdb3b2b6d4e98ec5","308d5b02bf1f4684bfb987949fb79ae4","03899f86c9ac40c693baaa7643c95928","ac31ae3c36c7435c968b3c3489f8a11f","fe9322c291654c8cba7860099218ace4","33a3cc82f70a4538b508142295a4d012","11dc8a1d2ff947eda68e04ac55619a2e","a0ad1c55dc2441579c41715adce274e3","3b92c194f32240acb96d9e7c53451efd","686428f4ceff447b91e0ba90816c73f1","304e437c6b694dfd9a1fffcdf06933d9","dded30fb67dd4056a7ba434978d49e72","19053b31095f44a78c2c429366f3987b","522998a813994da3a181190f371a41b8","b9356830ee4646aab84fe2122efb17e0","05b4273d473148e994484e2a3bf3129b","5d9c848f989f4213ae3b300d8e07e92d","dd17937b30904ecda624651a41c3434b","5f647164f85c4589b3c26d9fedaaa98d","035dffa943b348178500ef509abe4c19","ac24eecc96b84e45a5b7017530267f5e","be27be8d7f764419976d09fe5c9c9002","843ccef1077c41fbb9a0f87fa24e8954","00a3a55a163f409f8542ad9c0ba6aa7c","b1718426c5aa427e9bf6072bacb2caff","a36b44aa0d104472bf8185d151f572b6","eb1bcbbf26694abf87d6e8196c5323b3","5f242a32c3654bceb70d660638ccaa59","d6dfddc3e3064c25876731f9a5560090","cc9ee5cfa39c4002ad8315dea7c520c8","3f90b77c8ce74040b4658f36626bdde1","3569327180c448a0bfd0a7300dad2023","c1a4d061f44949668a0d724c946507d3","756c25f65844498ebf2e9454acc74eba","d03d5ccd91da494fb6ef4675d25c7320","814430dd04ce494bb198c9d8f9d1a93c","2280b61f871a4e72a8dfcf35cf260914","50ee54a9a1c142e4bf43a1fecc68152b","73c0a9c3814e4fa990c3fe6cbc3087e0","e733d01691fe4e7aa7ac5f51caf20e73","1253a9eb31f14274822a375725df052b","16a02493a9164ba3a7f470c885fc1293","b03f7cc7b41c4518ab1d4e61d113ce0f","66bff56b0d1f4fd88f013e6754a9b3f0"};
//		String[] keys = {"3f90b77c8ce74040b4658f36626bdde1"};
		int length = keys.size();
//		int length = keys.length;
		System.out.println("当天推送平台实际应用个数：" + length);
		int i = 0;
		DecimalFormat df = new DecimalFormat("0.0%");
		for (String key : keys) {
			logger.info("现在采集推送新增用户的应用是：{}",key);
			logger.info("采集进度：{},还剩下{}个应用需要采集....",df.format((float)i/length),length-i);
			pushStatistic.getNewUserIntoHive(day, key, devMap.get(key),Constant.NEW_USER_SEVEN);
			i++;
		}
		logger.info("采集进度：{}....",df.format((float)i/length));
	}
	
	/**
	 * 整个推送平台的新增用户
	 * @param devid
	 * @param day
	 * @throws SQLException
	 */
	public void tempAllDevidTask(String devid,String day) throws SQLException
	{
		//获取老平台的新增用户
		tempDevidTask(devid, day);
		List<String> imeis = pushStatistic.getNewUsers(day, devid, Constant.FLAG_PUSH, Constant.TABLE_USER_SEVEN);
//		List<String> imeis = pc.getNewUserByDevid(day, devid);
		logger.info("老推送：" + day + "。账号：" + devid + "的7天新增数：" + imeis.size());
		
		//新平台7天的新增
		List<String> newImeis = newPushStatistic.getNewUsers(day, devid, Constant.FLAG_SPOT, Constant.TABLE_NEW_PUSH_USER_SEVEN);
		logger.info("新推送：" + day + "。账号：" + devid + "的7天新增数：" + newImeis.size());
		
		for (String imei : imeis) {
			if(!newImeis.contains(imei)) newImeis.add(imei);
		}
		logger.info("整个推送平台：" + day + "。账号：" + devid + "的7天新增数：" + newImeis.size());
		FileUtil.generate(newImeis, "newuser_" + devid + "_" + day);
	}
	
	public void tempThirtyAllDevidTask(String devid,String day) throws SQLException
	{
		//获取老平台的新增用户
//		tempTask(devid, day);
		List<String> imeis = pushStatistic.getNewUsers(day, devid, Constant.FLAG_PUSH, Constant.TABLE_USER);
		logger.info("老推送：" + day + "。账号：" + devid + "的30天新增数：" + imeis.size());
		
		//新平台7天的新增
		List<String> newImeis = newPushStatistic.getNewUsers(day, devid, Constant.FLAG_NEW_PUSH, Constant.TABLE_USER);
		logger.info("新推送：" + day + "。账号：" + devid + "的30天新增数：" + newImeis.size());
		
		for (String imei : imeis) {
			if(!newImeis.contains(imei)) newImeis.add(imei);
		}
		logger.info("整个推送平台：" + day + "。账号：" + devid + "的30天新增数：" + newImeis.size());
		FileUtil.generate(newImeis, "newuser_30_" + devid + "_" + day);
	}
	
	
	 public static void handleTasks(List<Callable<String>> tasks, int threadCount)
	 { 
	        try 
	        {  
	            ExecutorService executor = Executors.newFixedThreadPool(threadCount);  
//	            int i = 0;
//	            DecimalFormat df = new DecimalFormat("0.0%");
	            List<Future<String>> results = new ArrayList<Future<String>>(tasks.size());
	            for(Callable<String> task : tasks) {  
	            	Future<String> future = executor.submit(task);
	            	results.add(future);
//	               System.out.println("是否执行完成：{}" + future.isDone());
//	                logger.info("现在采集的线程：{}",task.getName());
//	                logger.info("采集进度：{},还剩下{}个应用需要采集....",df.format((float)i/threads.size()),threads.size() - i);
//	                i++;
	            }  
	            
	            
	            for (Future<String> future : results) {
	            	  try{   
	                      while(!future.isDone());//Future返回如果没有完成，则一直循环等待，直到Future返回完成  
	                      System.out.println(future.get());     //打印各个线程（任务）执行的结果   
	                  }catch(InterruptedException e){   
	                      e.printStackTrace();   
	                  }catch(ExecutionException e){   
	                      e.printStackTrace();   
	                  }finally{   
	                      //启动一次顺序关闭，执行以前提交的任务，但不接受新任务  
	                	  executor.shutdown();   
	                  }   
				}
	            
//	            executor.shutdown();  
//	            executor.awaitTermination(60, TimeUnit.SECONDS); 
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } 
	  }  
	 
	 
	 public static void handleTasksTest(List<Thread> threads, int threadCount)
	 { 
	        try 
	        {  
	            ExecutorService executor = Executors.newFixedThreadPool(threadCount);  
	            int i = 0;
	            DecimalFormat df = new DecimalFormat("0.0%");
	            for(Thread task : threads) {  
	                executor.submit(task);
	                logger.info("现在采集的线程：{}",task.getName());
	                logger.info("采集进度：{},还剩下{}个应用需要采集....",df.format((float)i/threads.size()),threads.size() - i);
	                i++;
	            }  
	            executor.shutdown();  
	            executor.awaitTermination(60, TimeUnit.SECONDS); 
	            executor.shutdown();  
	            executor.awaitTermination(60, TimeUnit.SECONDS); 
	            
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } 
	  }  
	 
	 public  void getNewUserByDevid(String devid,String day) throws SQLException
	 {
		 Statistic statistic = new Statistic();
		 statistic.tempDevidTask(devid, day);
		 List<String> arrs = pushStatistic.getNewUsers(day, devid, Constant.FLAG_PUSH, Constant.TABLE_USER_SEVEN);
		 FileUtil.generate(arrs, "push_" + devid + "_" + day + ".txt");
	 }
	 
	 
	 /**
	  * 老用户留存
	  * @param devid
	  * @param day
	  * @throws SQLException
	  */
	public void getRemainUserByDevid(String devid,String day) throws SQLException
	 {
		//缓存所有的key和开发者账号信息
		List<String>  keys = getRealSlotNamesByDevidFromHive(devid, day, Constant.TABLE_PUSH);
//		String[] keys = {"3f90b77c8ce74040b4658f36626bdde1"};
		
		int length = keys.size();
		logger.info("devid：{}，{}天 ，应用个数：{}",devid,day,length);
		
		int i = 0;
		DecimalFormat df = new DecimalFormat("0.0%");
		Map<String,Integer> remainMap = new HashMap<String, Integer>();
		
		for (String key : keys) {
			logger.info("现在采集推送留存用户的应用是：{}",key);
			logger.info("采集进度：{},还剩下{}个应用需要采集....",df.format((float)i/length),length-i);
			List<String> users = pushStatistic.getRemainUser(day, key);  //hive重新做
//			List<String> users = pc.getRemainUserByDevid(day, key);  //mongo中有7天数据
			for (String user : users) {
//				if(remainMap.get(user) == null) remainMap.put(user, 1);
//				else remainMap.put(user, remainMap.get(user) + 1);
				remainMap.put(user, 1);
			}
			i++;
		}
		logger.info("采集进度：{}....",df.format((float)i/length));
		//生成文件
		FileUtil.generate(remainMap, "old_remain_" + devid + "_" + day);
	 }
	 
	 
	@SuppressWarnings("static-access")
//	public void getAllRemainUserByDevid(String devid,String day) throws SQLException
//	{
//		//获取老平台的留存用户
//	    getRemainUserByDevid(devid, day);
//		List<String> imeis = pc.parseToList("d:/compare/day/old_remain_" + devid + "_" + day + ".txt");
//		logger.info("老推送：" + day + "。账号：" + devid + "的留存数：" + imeis.size());
//		
//		//新平台的留存
//		List<String> newImeis = newPushStatistic.getNewPlatformRemainUserByDevid(day, devid);
//		logger.info("新推送：" + day + "。账号：" + devid + "的留存数：" + newImeis.size());
//		
//		for (String imei : newImeis) {
//			if(!imeis.contains(imei)) imeis.add(imei);
//		}
//		logger.info("整个推送平台：" + day + "。账号：" + devid + "的留存数：" + imeis.size());
//		FileUtil.generate(imeis, "remain_" + devid + "_" + day);
//	}
	
	
	public void getAllRemainUserByDevid(String devid,String day) throws SQLException
	{
		//获取老平台的留存用户
	    getRemainUserByDevid(devid, day);
		Map<String,Integer> imeis = pc.parseToMap("old_remain_" + devid + "_" + day);
		logger.info("老推送：" + day + "。账号：" + devid + "的留存数：" + imeis.size());
		
		//新平台的留存
		List<String> newImeis = newPushStatistic.getNewPlatformRemainUserByDevid(day, devid);
		logger.info("新推送：" + day + "。账号：" + devid + "的留存数：" + newImeis.size());
		
		for (String imei : newImeis) {
			if(!imeis.containsKey(imei)) imeis.put(imei, 1);
		}
		logger.info("整个推送平台：" + day + "。账号：" + devid + "的留存数：" + imeis.size());
		FileUtil.generate(imeis, "remain_" + devid + "_" + day);
	}
	
	
	@SuppressWarnings("static-access")
	public void getThirtyAllRemainUserByDevid(String devid,String day) throws SQLException
	{
		//获取老平台的留存用户
	    getRemainUserByDevid(devid, day);
		Map<String,Integer> imeis = pc.parseToMap("old_remain_30_" + devid + "_" + day);
		logger.info("老推送：" + day + "。账号：" + devid + "的留存数：" + imeis.size());
		
		//新平台的留存
//		List<String> newImeis = newPushStatistic.getNewPlatformRemainUserByDevid(day, devid);
		List<String> newImeis = newPushStatistic.getNewPlatformRemainUser(day, "3f90b77c8ce74040b4658f36626bdde1");
		logger.info("新推送：" + day + "。账号：" + devid + "的留存数：" + newImeis.size());
		
		for (String imei : newImeis) {
			if(!imeis.containsKey(imei)) imeis.put(imei, 1);
		}
		logger.info("整个推送平台：" + day + "。账号：" + devid + "的留存数：" + imeis.size());
		FileUtil.generate(imeis, "remain_30_" + devid + "_" + day);
	}
	 
	 
	 
	 public  void getSlotNameByDevidFromMysql(String devid) throws SQLException
	 {
		 
		 List<String> arrs = pushStatistic.getSlotNameFromMysql(devid);
		 StringBuilder sb = new StringBuilder();
		 sb.append("('");
		 for (String fid : arrs) {
			 sb.append(fid).append("','");
		}
		 sb.append(")");
		 System.out.println(sb.toString());
	 }
	 
	 
	 public List<String> getRealSlotNamesByDevidFromHive(String devid,String day,String table) throws SQLException
	 {
		 
		 List<String> l1 = pushStatistic.getSlotNameFromMysql(devid);
//		 FileUtil.generate(l1, "fid_39");
		 logger.info("devid：" + devid + "，mysql应用个数：" + l1.size());
		 List<String> l2 = pushStatistic.getDistinctFieldsFromHive(day, null, table, "slot_name");
//		 FileUtil.generate(l2, "fid_20141215");
//		 System.out.println("两个相同的个数：" + CompareUtil.getSameSize(l1, l2) + "，分别为：" + CompareUtil.getSameList(l1, l2));
		 
		 Iterator<String> iterator = l1.iterator();
		 while ( iterator.hasNext()) {
			String slotName = iterator.next();
			if(!l2.contains(slotName)) iterator.remove();
		}
//		 for (int i = 0; i < l1.size(); i++) {
//			if(!l2.contains(l1.get(i))) l1.remove(l1.get(i));
//		}
		 logger.info("devid：{}，table：{} 中 {} 天实际有请求的应用个数：{}",devid,table,day,l1.size());
		 return l1;
	 }
	 
	
	public static void main(String[] args) throws SQLException {
		Statistic statistic = new Statistic();
//		statistic.doBaseNewUserData("20141125");
//		System.out.println(TimeUtil.getLastDayString());
//		statistic.doBaseNewUserData(TimeUtil.getLastDayString());
		
//		statistic.tempTask("6", "20150101");
//		statistic.tempTask("6", "20150102");
//		statistic.tempTask("6", "20150103");
//		statistic.tempTask("6", "20150104");
//		statistic.tempTask("6", "20150105");
//		statistic.tempTask("6", "20150106");
//		statistic.tempTask("6", "20150107");
//		statistic.tempTask("6", "20150108");
		
		
//		statistic.getAllRemainUserByDevid("10000", "20141216");
//		statistic.getAllRemainUserByDevid("10000", "20141217");
//		statistic.getAllRemainUserByDevid("10000", "20141218");
//		statistic.getAllRemainUserByDevid("10000", "20141219");
//		statistic.getAllRemainUserByDevid("10000", "20141220");
//		statistic.getAllRemainUserByDevid("10000", "20141221");
//		statistic.getAllRemainUserByDevid("10000", "20141222");
//		statistic.getAllRemainUserByDevid("10000", "20141223");
		
		
//		statistic.getSevenNewUserForAllPushPlatform("39", "20141220");
		statistic.getSevenNewUserForAllPushPlatform("39", "20150111");
		
		PushStatistic push = new PushStatistic();
//		System.out.println("20141222all：" + push.getNewUsersCount("20141222", "6",0,Constant.TABLE_USER));
//		System.out.println("20141223all：" + push.getNewUsersCount("20141223", "6",0,Constant.TABLE_USER));
//		System.out.println("20141224all：" + push.getNewUsersCount("20141224", "6",0,Constant.TABLE_USER));
//		System.out.println("20141225all：" + push.getNewUsersCount("20141225", "6",0,Constant.TABLE_USER));
//		System.out.println("20141226all：" + push.getNewUsersCount("20141226", "6",0,Constant.TABLE_USER));
//		System.out.println("20141227all：" + push.getNewUsersCount("20141227", "6",0,Constant.TABLE_USER));
//		System.out.println("20141228all：" + push.getNewUsersCount("20141228", "6",0,Constant.TABLE_USER));
		
	}
	
	
	
	
	class CollectDataThread implements Runnable
	{
		private String day;
		private String slotName;
		private String devid;
		private StatisticService producer; 
		
		public CollectDataThread(String day, String slotName, String devid,StatisticService producer) {
			this.day = day;
			this.slotName = slotName;
			this.devid = devid;
			this.producer = producer;
		}

		@Override
		public void run() {
			try {
				producer.getNewUserIntoHive(day, slotName, devid,Constant.NEW_USER_THIRTY);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	class CollectDataTask implements Callable<String>
	{
		private String day;
		private String slotName;
		private String devid;
		private StatisticService producer; 
		
		public CollectDataTask(String day, String slotName, String devid,StatisticService producer) {
			this.day = day;
			this.slotName = slotName;
			this.devid = devid;
			this.producer = producer;
		}

		@Override
		public String call() throws Exception {
			producer.getNewUserIntoHive(day, slotName, devid,Constant.NEW_USER_THIRTY);
			return "当前采集应用key为：" + slotName + "。" + Thread.currentThread().getName() + "---";
		}
	}


}
