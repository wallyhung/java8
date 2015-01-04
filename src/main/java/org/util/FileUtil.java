package org.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

public class FileUtil {
	
	public static void generate(List<String> arrs,String filename)
	{
//		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			File file = new File("d:/compare/day/" + filename + ".txt");
			if(file.exists()) file.delete();
//			fw = new FileWriter(new File(path));
//			BufferedWriter bw = new BufferedWriter(fw);
			bw = new BufferedWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));
			for(String arr : arrs){
	            bw.write(arr + "\t\n");
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
	
	
	public static void generate(Map<String,Integer> map,String filename)
	{
//		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			File file = new File("d:/compare/day/" + filename + ".txt");
			if(file.exists()) file.delete();
//			fw = new FileWriter(new File(path));
//			BufferedWriter bw = new BufferedWriter(fw);
			bw = new BufferedWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));
			for(Map.Entry<String,Integer> arr : map.entrySet()){
	            bw.write(arr.getKey() + "\t\n");
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
	
	
	public static boolean hasFile(String filename)
	{
		File file = new File("d:/compare/day/" + filename + ".txt");
		if(file.exists()) return true;
		return false;
	}


}
