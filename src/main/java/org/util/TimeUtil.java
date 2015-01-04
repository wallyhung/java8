package org.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TimeUtil {


    /**
     * 获取某一天的开始时间
     *
     * @param date
     * @return
     */
    public static Date getDayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取某一天的结束时间
     *
     * @param date
     * @return
     */
    public static Date getDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static String getDayStringFromLong(long m) {
        Date data = new Date(m);
        return getDay(data);
    }


    /**
     * 获取某一天的时间字符串
     *
     * @param date
     * @return
     */
    public static String getDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        return str;
    }
    
    public static Date getDayFromDayString(String day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    
    public static String getDayString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String str = format.format(date);
        return str;
    }
    
    /**
     * 从木有中划线的日期字符串转化
     * @param day
     * @return
     */
    public static Date getDayFromDayStringNon(String day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    

   

    /**
     * 获取某一刻的具体时间
     *
     * @param date
     * @return
     */
    public static String getDayTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }


    /**
     * 获取某一天小时的时间字符串
     *
     * @param date
     * @return
     */
    public static String getDayHour(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH");
        String str = format.format(date);
        return str;
    }

    /**
     * 获取上一个小时的字符串时间
     *
     * @param date
     * @return
     */
    public static String getDayLastHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(Calendar.HOUR_OF_DAY, -1);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH");
        String str = format.format(calendar.getTime());
        return str;
    }
    
    public static String getDayLastHourStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        
        SimpleDateFormat format = new SimpleDateFormat("HH:00:00");
        String str = format.format(calendar.getTime());
        return str;
    }
    
    public static String getHour(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:00:00");
        String str = format.format(date);
        if(str.equals("00:00:00")) str = "24:00:00";
        return str;
    }
    
    public static String getHourStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
        String str = format.format(date);
        return str;
    }
    

    /**
     * 获取上一个小时开始时间的时间戳
     *
     * @param date
     * @return
     */
    public static long getDayLastHourTimestamp(Date date) {
        return getDayLastHourDate(date).getTime();
    }

    /**
     * 获取上一个小时开始时间的日期时间
     *
     * @param date
     * @return
     */
    public static Date getDayLastHourDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(Calendar.HOUR_OF_DAY, -1);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        String str = format.format(calendar.getTime());
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取下一个小时开始时间的日期时间
     *
     * @param date
     * @return
     */
    public static Date getDayNextHourDate(String hour) {
        Date date = getDayFromHourString(hour);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        return calendar.getTime();
    }
    
    
    /**
     * 通过木有下划线的日期字符串获取下一天的日期
     * @param dayStringNon
     * @return
     */
    public static Date getDayNextDayDate(String dayStringNon) {
        Date date = getDayFromDayStringNon(dayStringNon);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }
    


    /**
     * 获取下一个小时开始时间的日期时间
     *
     * @param date
     * @return
     */
    public static String getDayNextHour(String hour) {
        Date date = getDayNextHourDate(hour);
        return getDayHour(date);
    }

    /**
     * 获取上一个小时开始时间的日期时间
     *
     * @param date
     * @return
     */
    public static String getDayLastHour(String hour) {
        Date date = getDayFromHourString(hour);
        return getDayLastHour(date);
    }


    /**
     * 转换小时字符串为Date
     *
     * @param hour
     * @return
     */
    public static Date getDayFromHourString(String hour) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH");
        Date date = null;
        try {
            date = format.parse(hour);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 字符串转换成日期 (Util.DATE)
     *
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取某天的前7天的具体日期
     *
     * @param date
     * @return
     */
    public static Date getSevenDaysBefore(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
//		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-7);
        calendar.add(Calendar.DATE, -7);
        return calendar.getTime();
    }


    /**
     * 获取某天的8天的具体日期
     *
     * @param date
     * @return
     */
    public static Date getEightDaysBefore(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 8);
        return calendar.getTime();
    }


    public static Date getEightDaysBeforeStart(Date date) {
        return getDayStart(getEightDaysBefore(date));
    }


    /**
     * 获取30天前的时间
     *
     * @param date
     * @return
     */
    public static Date getOneMonthBefore(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 30);
        return calendar.getTime();
    }

    
    /**
     * 获取当前日期的前30天的日期
     * @param date
     * @return
     */
     public static String getThirthDaysBefore(Date date) {
         Calendar calendar = Calendar.getInstance();
         calendar.setTimeInMillis(date.getTime());
         calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 31);
         return getDayString(calendar.getTime());
     }
     
     public static String getSevenDaysBeforeString(Date date) {
         Calendar calendar = Calendar.getInstance();
         calendar.setTimeInMillis(date.getTime());
         calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 8);
         return getDayString(calendar.getTime());
     }
     
     
     public static String getSevenDaysNextString(Date date) {
         Calendar calendar = Calendar.getInstance();
         calendar.setTimeInMillis(date.getTime());
         calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 8);
         return getDayString(calendar.getTime());
     }
     
     
     /**
      * 获取当前天的下一天
      * @param date
      * @return
      */
     public static String getDayNextDayString(String day) {
    	 Date date = getDayFromDayStringNon(day);
         Calendar calendar = Calendar.getInstance();
         calendar.setTimeInMillis(date.getTime());
         calendar.add(Calendar.DAY_OF_MONTH, 1);
         return getDayString(calendar.getTime());
     }
     

    /**
     * 获取某天的前一天日期
     *
     * @param date
     * @return
     */
    public static Date getLastDayEnd(Date date) {
        return getDayEnd(getLastDay(date));
    }


    public static Date getLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(Calendar.DATE, -1);    //得到前一天
        return calendar.getTime();
    }


    /**
     * 获取系统的当前时间
     *
     * @return
     */
    public static Date getCurrent() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 获取两个小时字符串相差的小时数组
     *
     * @param hour     当前小时
     * @param dataHour 数据库数据的小时记录
     * @return
     */
    public static List<String> getDistanceTimeHourArray(String hour, String dataHour) {
        List<String> res = new ArrayList<String>();
        if (hour.equals(dataHour)) return res;

        Date hourDate = getDayFromHourString(hour);
        Date dataDate = getDayFromHourString(dataHour);
        if (hourDate.after(dataDate)) {
            dataHour = getDayNextHour(dataHour);
            while (!hour.equals(dataHour)) {
                res.add(dataHour);
                dataHour = getDayNextHour(dataHour);
            }
            res.add(hour);
            return res;
        }
        return res;
    }
    
    
    public static List<String> getDistanceTimeDayArray(String newDay, String oldDay) {
        List<String> res = new ArrayList<String>();
        if (newDay.equals(oldDay)) return res;

        Date newDate = getDayFromDayStringNon(newDay);
        Date oldDate = getDayFromDayStringNon(oldDay);
        if (newDate.after(oldDate)) {
        	oldDay = getDayString(getDayNextDayDate(oldDay));
            while (!newDay.equals(oldDay)) {
                res.add(oldDay);
                oldDay = getDayString(getDayNextDayDate(oldDay));
            }
            return res;
        }
        return res;
    }
    
    
    public static List<String> getNextSevensTimeDayArray(String day) {
    	String newDay = getSevenDaysNextString(getDayFromDayStringNon(day));
    	return getDistanceTimeDayArray(newDay, day);
    }
    
    
    
    
    /**
     * 获取昨天的时间字符串
     * 格式：yyyyMMdd
     * @return
     */
    public static String getLastDayString() {
        return getDayString(getLastDay(new Date()));
    }
    
    /**
     * 获取当天上一天的时间字符串
     * 格式：yyyyMMdd
     * @return
     */
    public static String getLastDayString(String day) {
    	Date date = getDayFromDayStringNon(day);
        return getDayString(getLastDay(date));
    }
    
    

    public static void main(String[] args) {

//        String hour1 = "2014-04-30-14";
//        String hour2 = "2014-04-30-14";
//        List<String> ss = getDistanceTimeHourArray(hour1, hour2);
//        for (String string : ss) {
//            System.out.println(string);
//        }
//        System.out.println(ss.size());
//        System.out.println("----华丽的分割线--");
//        System.out.println(getEightDaysBeforeStart(new Date()));
//        System.out.println(getSevenDaysBefore(new Date()));
//        Date date = StrToDate("2014-05-29 00:00:00");
//        System.out.println(getDayStart(date).getTime());
//        System.out.println(getDayEnd(date).getTime());
//        
//        System.out.println(getHour(new Date()));
//        System.out.println(getHourStr(new Date()));
//        
//        System.out.println(getThirthDaysBefore(new Date()));
//        System.out.println(getOneMonthBefore(new Date()));
//        System.out.println(getLastDayString("20141111"));
//        System.out.println(getDayNextDayString("20141110"));
    	
    	
    	System.out.println(getDistanceTimeDayArray("20141130", "20141121"));
    	System.out.println(getNextSevensTimeDayArray("20141130"));
    }
}
