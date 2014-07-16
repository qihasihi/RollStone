package com.school.util.PageUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.school.util.UtilTool;

public class PageUtilTool implements java.io.Serializable{ 
	
	/**
	 * 和当前时间比较
	 * @param sdate
	 * @return
	 */
	public String DateDiffNow(String sdate){////
		if(sdate==null||sdate.length()<1)
			return null;
		String returnVal=null;
		Date d = UtilTool.StringConvertToDate(sdate);
		Date now = new Date();
		if(d.getTime()==now.getTime()){
			returnVal="=";
		}else if(d.getTime()<now.getTime()){
			returnVal="<";
		}else if(d.getTime()>now.getTime()){
			returnVal=">";
		}
		return returnVal;
	}
	
	public String BeginDiffEndDate(String bdate,String edate){
		if(bdate==null||bdate.length()<1||edate==null||edate.length()<1){
			return null;
		}
		String returnVal=null;
		Date b = UtilTool.StringConvertToDate(bdate);
		Date e = UtilTool.StringConvertToDate(edate);
		if(b.getTime()==e.getTime()){
			returnVal="=";
		}else if(b.getTime()<e.getTime()){
			returnVal="<";
		}else if(b.getTime()>e.getTime()){
			returnVal=">";
		}
		return returnVal;
	}
	/**
	 * 
	 * @param
	 * @param edate 天数（几天后结束)
	 * @return
	 * @throws Exception
	 */
	public static String  DateDiffNumber(String bdate,String edate) throws Exception{
		if(bdate==null||bdate.trim().length()<1||edate==null||edate.length()<1)
			return null;
		
		long nd = 1000*24*60*60;//一天的毫秒数
		long nh = 1000*60*60;//一小时的毫秒数
		long nm = 1000*60;//一分钟的毫秒数
		long ns = 1000;//一秒钟的毫秒数
		String returnStr=null;
		  
		if(bdate.split(" ").length<2)
			bdate+=" 00:00:00";
        if(edate.split(" ").length<2)
            edate+=" 00:00:00";
		  
		//开始时间
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date db=sd.parse(bdate); 
		
		//结束时间
        //开始时间
        SimpleDateFormat bd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date bb=sd.parse(edate);
//		Calendar c = Calendar.getInstance();
//		c.setTime(db);
//		c.add(Calendar.DAY_OF_MONTH, edate);
//		Date e=c.getTime();
		//当前时间
		Long curobj=new Date().getTime();
		if(db.getTime()>curobj){
			return "1";
		}

		
		Long diff=bb.getTime()-curobj;
		long day = diff/nd;//计算差多少天
		long hour = diff%nd/nh;//计算差多少小时
		long min = diff%nd%nh/nm;//计算差多少分钟
		//long sec = diff%nd%nh%nm/ns;//计算差多少秒
		returnStr=day+"天"+hour+"时"+min+"分";
		if(diff<1)
			return "3";
		//System.out.println(returnStr);
		return returnStr;  
	}


    /**
     *
     * @param倒计时
     * @return
     * @throws Exception
     */
    public static String  DateTimer(String bdate) throws Exception{
        if(bdate==null||bdate.trim().length()<1)
            return null;

        long nd = 1000*24*60*60;//一天的毫秒数
        long nh = 1000*60*60;//一小时的毫秒数
        long nm = 1000*60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数
        String returnStr=null;

        if(bdate.split(" ").length<2)
            bdate+=" 00:00:00";

        //开始时间
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date bb=sd.parse(bdate);
//		Calendar c = Calendar.getInstance();
//		c.setTime(db);
//		c.add(Calendar.DAY_OF_MONTH, edate);
//		Date e=c.getTime();
        //当前时间
        Long curobj=new Date().getTime();
        if(bb.getTime()>curobj){
            Long diff=bb.getTime()-curobj;
            long day = diff/nd;//计算差多少天
            long hour = diff%nd/nh;//计算差多少小时
            long min = diff%nd%nh/nm;//计算差多少分钟
            //long sec = diff%nd%nh%nm/ns;//计算差多少秒
            returnStr=day+"天"+hour+"时"+min+"分";
        }
        return returnStr;
    }
}
