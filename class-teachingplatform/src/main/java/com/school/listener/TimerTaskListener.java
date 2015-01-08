package com.school.listener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//import com.school.share.*;
import com.school.share.*;
import com.school.util.DESPlus;
import com.school.util.UtilTool;
import org.apache.log4j.Logger;

public class TimerTaskListener implements ServletContextListener {
    //记录Log4J
    private Logger logger = Logger.getLogger(this.getClass());
    /**
     * 配置分校信息
     * @param request
     */
	public void configSchool(ServletContext request){

      //  Map<String,Object> writeMap=new HashMap<String,Object>();
        String schoolId = UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_ID");
        String schoolname=UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_NAME");
        //   if(schoolId==null||schoolId.trim().length()<1||schoolname==null||schoolname.trim().length()<1){
        //得到文件中的KEY
        String fpath = request.getRealPath("/") + "school.txt";
        BufferedReader br = null;
        StringBuilder content = null;
        try {
            br = new BufferedReader(new FileReader(fpath.trim()));
            String lineContent = null;
            while ((lineContent = br.readLine()) != null) {
                if (content == null)
                    content = new StringBuilder(lineContent);
                else
                    content.append("\n" + lineContent);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
            e.printStackTrace();
            return;
        } finally {
            if (br != null)
                try{
                    br.close();
                }catch(Exception e){e.printStackTrace();}
        }
        //解析key  并写入schoolid,schoolname
        try {
            DESPlus des = new DESPlus();
            String schoolkeyfn = des.decrypt(content.toString());
            //得到分校已经买的栏目
            String[] schoolArray = schoolkeyfn.split("\\*");
            if(schoolId==null||schoolId.length()<1){
                String schoolid=schoolArray[0];

//                        WriteProperties.writeProperties(request.getRealPath("/")
//                                + "/WEB-INF/classes/properties/util.properties",
//                                "CURRENT_SCHOOL_ID", schoolid);
               // writeMap.put("CURRENT_SCHOOL_ID",schoolid);
                UtilTool.utilproperty.setProperty("CURRENT_SCHOOL_ID",
                        schoolid);
            }
            if(schoolArray.length>4){       //加载是否精简版
                if(schoolArray[4]!=null&&UtilTool.isNumber(schoolArray[4].trim()))
                    UtilTool._IS_SIMPLE_RESOURCE=Integer.parseInt(schoolArray[4]);
            }
            //得到Schoolid后，得到名称,并赋值
            if(schoolArray.length>3){
                //对中文进行解码
                String schoolnameChina=java.net.URLDecoder.decode(schoolArray[3],"UTF-8");
                if(schoolname==null||!schoolnameChina.equals(schoolname)){
//                            WriteProperties.writeProperties(request.getRealPath("/")+ "/WEB-INF/classes/properties/util.properties","CURRENT_SCHOOL_NAME",schoolnameChina);
                            UtilTool.utilproperty.setProperty("CURRENT_SCHOOL_NAME",
                                    schoolnameChina);
                  //  writeMap.put("CURRENT_SCHOOL_NAME",schoolnameChina);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
            e.printStackTrace();
        }
//        if(writeMap.size()>0){
//            //写入XML中
//            WriteProperties.writeProperties(request.getRealPath("/") + "/WEB-INF/classes/properties/util.properties", writeMap);
//        }
    }





	private Timer taskRemindTimer=null,taskLoopRemindTimer=null,schoolTimer = null,teachMaterialTimer=null,vsTimer=null,upCourseTimer=null,ucTimer=null,dcTimer=null,rsRankTimer=null,upEttColumn=null,upSchoolTmr=null;

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
        if(schoolTimer!=null)
            schoolTimer.cancel();
        if(teachMaterialTimer!=null)
            teachMaterialTimer.cancel();
        if(vsTimer!=null)
            vsTimer.cancel();
        if(upCourseTimer!=null)
            upCourseTimer.cancel();
        if(ucTimer!=null)
            ucTimer.cancel();
        if(dcTimer!=null)
            dcTimer.cancel();
        if(rsRankTimer!=null)
            rsRankTimer.cancel();
        if(upEttColumn!=null)
            upEttColumn.cancel();
        if(upSchoolTmr!=null)
            upSchoolTmr.cancel();
        if(taskLoopRemindTimer!=null)
            taskLoopRemindTimer.cancel();
        if(taskRemindTimer!=null)
            taskRemindTimer.cancel();
	}
    //时间间隔(一天)
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
    private static final long PERIOD_HOUR = 60 * 60 * 1000;
    private static final long PERIOD_15MIN = 15 * 60 * 1000;
    public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
        //启动配置分校信息
        configSchool(arg0.getServletContext());
        String timerSwitch=UtilTool.utilproperty.getProperty("IS_TASK_PROJECT");
        if(timerSwitch==null||timerSwitch.length()<1||timerSwitch.equals("0"))
            return;

    /**************************每天凌晨1点开始执行分校信息更新***********************************/
	//	每天晚上1点开始执行
        schoolTimer=new Timer(true);
		Calendar cal = Calendar.getInstance();
		  //每天定点执行
		cal.set(Calendar.HOUR_OF_DAY,1);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
        Date date=cal.getTime(); //第一次执行定时任务的时间
        //如果第一次执行定时任务的时间 小于当前的时间
        //此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
        if (date.before(new Date())) {
            date = this.addDay(date, 1);
        }
        schoolTimer.schedule(new UpdateSchool(arg0.getServletContext()),date,PERIOD_DAY);
        /**************************每天凌晨1点10开始执行教材信息更新***********************************/
        teachMaterialTimer=new Timer(true);
        Calendar  teachMaterialcal = Calendar.getInstance();
        //每天定点执行
        teachMaterialcal.set(Calendar.HOUR_OF_DAY,1);
        teachMaterialcal.set(Calendar.MINUTE,10);
        teachMaterialcal.set(Calendar.SECOND,0);
        Date tmdate=teachMaterialcal.getTime(); //第一次执行定时任务的时间
        if (tmdate.before(new Date())) {
            tmdate = this.addDay(tmdate, 1);
        }
        teachMaterialTimer.schedule(new ShareTeachingMaterial(arg0.getServletContext()),tmdate,PERIOD_DAY);
        /**************************每天凌晨1点20开始执行版本信息更新***********************************/
        vsTimer=new Timer(true);
        Calendar  vscal = Calendar.getInstance();
        //每天定点执行
        vscal.set(Calendar.HOUR_OF_DAY,1);
        vscal.set(Calendar.MINUTE,20);
        vscal.set(Calendar.SECOND,0);
        Date vsdate=vscal.getTime(); //第一次执行定时任务的时间
        if (vsdate.before(new Date())) {
            vsdate = this.addDay(vsdate, 1);
        }
        vsTimer.schedule(new ShareTeachVersion(arg0.getServletContext()),vsdate,PERIOD_DAY);
//        /**************************每天凌晨1点30--2.00开始执行资源上行***********************************/
//        ucTimer=new Timer(true);
//        Calendar  uCourseCal = Calendar.getInstance();
//        //每天定点执行
//        uCourseCal.set(Calendar.HOUR_OF_DAY,1);
//        Random rd=new Random();
//        uCourseCal.set(Calendar.MINUTE,(30+rd.nextInt(29)));
//        uCourseCal.set(Calendar.SECOND,0);
//        Date ucdate=uCourseCal.getTime(); //第一次执行定时任务的时间
//        if (ucdate.before(new Date())) {
//            ucdate = this.addDay(ucdate, 1);
//        }
     //  ucTimer.schedule(new ShareResource(arg0.getServletContext()),ucdate,PERIOD_DAY);


        /********************第天凌晨2.10分开始执行排行榜数据**************************/
//        rsRankTimer=new Timer(true);
//        Calendar  rkCourseCal = Calendar.getInstance();
//        //每天定点执行
//        rkCourseCal.set(Calendar.HOUR_OF_DAY,2);
//        Random rkdcrd=new Random();
//        rkCourseCal.set(Calendar.MINUTE,(10+rkdcrd.nextInt(10)));
//        rkCourseCal.set(Calendar.SECOND,0);
//        Date rsRankDate=rkCourseCal.getTime(); //第一次执行定时任务的时间
//        if (rsRankDate.before(new Date())) {
//            rsRankDate = this.addDay(rsRankDate, 1);
//        }
     //   rsRankTimer.schedule(new UpdateHotResData(arg0.getServletContext()),rsRankDate,PERIOD_DAY);
        /**************************三天执行一次 凌晨3点00--3.30开始执行专题下行***********************************/
        dcTimer=new Timer(true);
        Calendar  dCourseCal = Calendar.getInstance();
        //每天定点执行
        dCourseCal.set(Calendar.HOUR_OF_DAY,3);
        Random dcrd=new Random();
        dCourseCal.set(Calendar.MINUTE,(dcrd.nextInt(29)));
        dCourseCal.set(Calendar.SECOND,0);
        Date dcdate=dCourseCal.getTime(); //第一次执行定时任务的时间
        if (dcdate.before(new Date())) {
            dcdate = this.addDay(dcdate, 1);
        }
        long courseCal=PERIOD_DAY*3;
        dcTimer.schedule(new UpdateCourse(arg0.getServletContext()),dcdate,courseCal);
        /**************************每天凌晨4点00--4.30开始执行专题下行***********************************/
        upEttColumn=new Timer(true);
        Calendar  ueCourseCal = Calendar.getInstance();
        //每天定点执行
        ueCourseCal.set(Calendar.HOUR_OF_DAY,4);
        Random uecrd=new Random();
        ueCourseCal.set(Calendar.MINUTE,(00+uecrd.nextInt(29)));
        ueCourseCal.set(Calendar.SECOND,0);
        Date uedate=ueCourseCal.getTime(); //第一次执行定时任务的时间
        if (uedate.before(new Date())) {
            uedate = this.addDay(uedate, 1);
        }
        upEttColumn.schedule(new SynchroEttColumns(),uedate,PERIOD_DAY);



        /**************************每天早上8点20~晚上22点每隔20分钟发送任务提醒***********************************/
        //	每天早上8：20开始执行
        taskLoopRemindTimer=new Timer();
        Calendar calremind = Calendar.getInstance();
        //每天定点执行
        calremind.set(Calendar.HOUR_OF_DAY,12);
        calremind.set(Calendar.MINUTE,0);
        calremind.set(Calendar.SECOND,0);
        Date remindLoopDate=calremind.getTime(); //第一次执行定时任务的时间
        //如果第一次执行定时任务的时间 小于当前的时间
        //此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
        if (remindLoopDate.before(new Date())) {
            remindLoopDate = this.addDay(remindLoopDate, 1);
        }
        taskLoopRemindTimer.schedule(new TaskLoopRemind(arg0.getServletContext()),1 * 60 * 1000);//

        /**************************每天早上7:50发送昨天晚上10点到今天早上8点开始的任务提醒***********************************/
        //	每天早上7:50开始执行
        taskRemindTimer=new Timer(true);
        Calendar remind = Calendar.getInstance();
        //每天定点执行
        remind.set(Calendar.HOUR_OF_DAY,7);
        remind.set(Calendar.MINUTE,50);
        remind.set(Calendar.SECOND,0);
        Date remindDate=remind.getTime(); //第一次执行定时任务的时间
        //如果第一次执行定时任务的时间 小于当前的时间
        //此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
        if (remindDate.before(new Date())) {
            remindDate = this.addDay(remindDate, 1);
        }
        taskRemindTimer.schedule(new TaskRemind(arg0.getServletContext()),remindDate,PERIOD_DAY);
	}

    // 增加或减少天数
    private Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }


}

