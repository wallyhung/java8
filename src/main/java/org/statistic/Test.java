package org.statistic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.pojo.ClientMessage;
import org.pojo.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.Constant;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {
	
	private static final Logger logger = LoggerFactory.getLogger(Test.class);
	
	private static List<ClientMessage> parse(String path)
	{
		//创建Jackson全局的objectMapper 它既可以用于序列化 也可以用于反序列化
		ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		//得到JSON处理的工厂对象
		JsonFactory jsonFactory= objectMapper.getFactory();
		//进入读文件阶段
		InputStreamReader in = null;
		Integer idx = 1;
		List<ClientMessage> list = new ArrayList<ClientMessage>();
		try 
		{
			in = new InputStreamReader(new FileInputStream(new File(path)), "UTF-8");
			BufferedReader br = new BufferedReader(in);
			String currentJsonStr= null;
			try {
				//按行读取
				while((currentJsonStr = br.readLine()) != null){
					if(currentJsonStr.trim().equals("")) continue;
					//进入反序列化阶段
					//通过JSON处理工厂对象创建JSON分析器
					JsonParser jsonParser= jsonFactory.createParser(currentJsonStr);
					try {
						//反序列化的关键
						Data object = jsonParser.readValueAs(Data.class);
						if (object != null && object.getRtype() == Constant.RTYPE_REQUEST){
							list.add(object.getClientMessage());
						}
						
					} catch (Exception e) 
					{
						logger.error("{}：日志解析数据错误在第{}行，具体的内容为：{}",path,idx,currentJsonStr);
						continue;
					}
					idx++;
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
	
	private static void generate(List<ClientMessage> arrs,String filename)
	{
		ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        
//		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			File file = new File("d:/compare/" + filename);
			if(file.exists()) file.delete();
//			fw = new FileWriter(new File(path));
//			BufferedWriter bw = new BufferedWriter(fw);
			bw = new BufferedWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));
			for(ClientMessage arr : arrs){
	            bw.write(objectMapper.writeValueAsString(arr)+"\t\n");
	        }
//			for(Map.Entry<String, Integer> entry:Vmap.entrySet()){
//				bw.write(entry.getKey()+"\t\n");
//			}
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally
        {
        	try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	
	public static void main(String[] args) {
		
//		Faker faker = new Faker();
//
//		String name = faker.name().fullName(); // Miss Samanta Schmidt
//		String firstName = faker.name().firstName(); // Emory
//		String lastName = faker.name().lastName(); // Barton
//
//		String streetAddress = faker.address().stateAbbr(); // 60018 Sawayn Brooks Suite 449
//		
//		System.out.println(name);
//		System.out.println(firstName);
//		System.out.println(lastName);
//		System.out.println(streetAddress);
		
//		List<ClientMessage> s = parse("C:/Users/Administrator/Desktop/hadoopdata/mysql_data.log_20141202095443");
		List<ClientMessage> s = parse("C:/Users/Administrator/Desktop/hadoopdata/mysql_data.log_20141017032206");
		System.out.println(s.size());
		for (ClientMessage clientMessage : s) {
			System.out.println(clientMessage.getApp_name() + ":" + clientMessage.getImei());
		}
		
		generate(s, "无二无任务而4飞");
	}
	
	

}
