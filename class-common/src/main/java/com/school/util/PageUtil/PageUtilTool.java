package com.school.util.PageUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.school.util.UtilTool;

public class PageUtilTool implements java.io.Serializable{ 
	
	/**
	 * �͵�ǰʱ��Ƚ�
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
	 * @param edate ��������������)
	 * @return
	 * @throws Exception
	 */
	public static String  DateDiffNumber(String bdate,String edate) throws Exception{
		if(bdate==null||bdate.trim().length()<1||edate==null||edate.length()<1)
			return null;
		
		long nd = 1000*24*60*60;//һ��ĺ�����
		long nh = 1000*60*60;//һСʱ�ĺ�����
		long nm = 1000*60;//һ���ӵĺ�����
		long ns = 1000;//һ���ӵĺ�����
		String returnStr=null;
		  
		if(bdate.split(" ").length<2)
			bdate+=" 00:00:00";
        if(edate.split(" ").length<2)
            edate+=" 00:00:00";
		  
		//��ʼʱ��
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date db=sd.parse(bdate); 
		
		//����ʱ��
        //��ʼʱ��
        SimpleDateFormat bd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date bb=sd.parse(edate);
//		Calendar c = Calendar.getInstance();
//		c.setTime(db);
//		c.add(Calendar.DAY_OF_MONTH, edate);
//		Date e=c.getTime();
		//��ǰʱ��
		Long curobj=new Date().getTime();
		if(db.getTime()>curobj){
			return "1";
		}

		
		Long diff=bb.getTime()-curobj;
		long day = diff/nd;//����������
		long hour = diff%nd/nh;//��������Сʱ
		long min = diff%nd%nh/nm;//�������ٷ���
		//long sec = diff%nd%nh%nm/ns;//����������
		returnStr=day+"��"+hour+"ʱ"+min+"��";
		if(diff<1)
			return "3";
		//System.out.println(returnStr);
		return returnStr;  
	}


    /**
     *
     * @param����ʱ
     * @return
     * @throws Exception
     */
    public static String  DateTimer(String bdate) throws Exception{
        if(bdate==null||bdate.trim().length()<1)
            return null;

        long nd = 1000*24*60*60;//һ��ĺ�����
        long nh = 1000*60*60;//һСʱ�ĺ�����
        long nm = 1000*60;//һ���ӵĺ�����
        long ns = 1000;//һ���ӵĺ�����
        String returnStr=null;

        if(bdate.split(" ").length<2)
            bdate+=" 00:00:00";

        //��ʼʱ��
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date bb=sd.parse(bdate);
//		Calendar c = Calendar.getInstance();
//		c.setTime(db);
//		c.add(Calendar.DAY_OF_MONTH, edate);
//		Date e=c.getTime();
        //��ǰʱ��
        Long curobj=new Date().getTime();
        if(bb.getTime()>curobj){
            Long diff=bb.getTime()-curobj;
            long day = diff/nd;//����������
            long hour = diff%nd/nh;//��������Сʱ
            long min = diff%nd%nh/nm;//�������ٷ���
            //long sec = diff%nd%nh%nm/ns;//����������
            returnStr=day+"��"+hour+"ʱ"+min+"��";
        }
        return returnStr;
    }
}
