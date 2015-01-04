package org.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompareUtil {
	

	/**
	 * 比较两个数组的不同元素的个数 即list2在list中不同的個數
	 * @param list1     大集合
	 * @param list2     小集合
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static int getDiffSize(List list1,List list2)
	{
		int sum = 0;
		Object[] array_1 =  list1.toArray();
		Object[] array_2 =  list2.toArray();
		Arrays.sort(array_1);
		Arrays.sort(array_2);
		
		int len = array_2.length;  
		for (int i = 0; i < len; i++)  
		{  
		    if (Arrays.binarySearch(array_1, array_2[i]) < 0)  sum++;
		}  
		return sum;
	}
	
	@SuppressWarnings("rawtypes")
	public static int getSameSize(List list1,List list2)
	{
		int sum = 0;
		Object[] array_1 =  list1.toArray();
		Object[] array_2 =  list2.toArray();
		Arrays.sort(array_1);
		Arrays.sort(array_2);
		
		int len = array_2.length;  
		for (int i = 0; i < len; i++)  
		{  
		    if (Arrays.binarySearch(array_1, array_2[i]) >= 0)  sum++;
		}  
		return sum;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getSameList(List list1,List list2)
	{
		List same = new ArrayList();
		Object[] array_1 =  list1.toArray();
		Object[] array_2 =  list2.toArray();
		Arrays.sort(array_1);
		Arrays.sort(array_2);
		
		int len = array_2.length;  
		for (int i = 0; i < len; i++)  
		{  
		    if (Arrays.binarySearch(array_1, array_2[i]) >= 0)  same.add(array_2[i]);
		}  
		return same;
	}
	
	
	
	/**
	 * 大集合在小集合里面查询，查询不到，则就是大集合和小集合的差集
	 * @param list1 小集合
	 * @param list2大集合
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getDiffList(List list1,List list2)
	{
		List diff = new ArrayList();
		Object[] array_1 =  list1.toArray();
		Object[] array_2 =  list2.toArray();
		Arrays.sort(array_1);
		Arrays.sort(array_2);
		
		int len = array_2.length;  
		for (int i = 0; i < len; i++)  
		{  
		    if (Arrays.binarySearch(array_1, array_2[i]) < 0)  diff.add(array_2[i]);
		}  
		return diff;
	}
	
	
	
	public static void main(String[] args) {
		List<String> list1= new ArrayList<String>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		list1.add("d");
		list1.add("z");
		
		List<String> list2= new ArrayList<String>();
		list2.add("b");
		list2.add("c");
		list2.add("d");
		list2.add("e");
		System.out.println("数组list2与list1相比，不同的数据个数：" + getDiffSize(list1, list2));
		System.out.println("数组list1与list2相比，不同的数据个数：" + getDiffSize(list2, list1));
		
		System.out.println("数组list2与list1相比，不同的数据：" + getDiffList(list1, list2));
		System.out.println("数组list1与list2相比，不同的数据：" + getDiffList(list2, list1));
		
	}

}
