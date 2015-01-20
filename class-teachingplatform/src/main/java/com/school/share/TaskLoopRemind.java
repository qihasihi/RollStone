package com.school.share;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.entity.ClassUser;
import com.school.entity.UserInfo;
import com.school.entity.teachpaltform.TaskRemindInfo;
import com.school.entity.teachpaltform.TpGroupStudent;
import com.school.entity.teachpaltform.TpTaskAllotInfo;
import com.school.entity.teachpaltform.TpTaskInfo;
import com.school.manager.ClassUserManager;
import com.school.manager.inter.IClassUserManager;
import com.school.manager.inter.teachpaltform.ITaskRemindManager;
import com.school.manager.inter.teachpaltform.ITpGroupStudentManager;
import com.school.manager.inter.teachpaltform.ITpTaskAllotManager;
import com.school.manager.inter.teachpaltform.ITpTaskManager;
import com.school.manager.teachpaltform.TaskRemindManager;
import com.school.manager.teachpaltform.TpGroupStudentManager;
import com.school.manager.teachpaltform.TpTaskAllotManager;
import com.school.manager.teachpaltform.TpTaskManager;
import com.school.util.SpringBeanUtil;
import com.school.util.SynchroUtil;
import com.school.util.UtilTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletContext;
import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import static com.school.share.TaskLoopRemindUtil.sendTaskRemindObj;

/**
 * Created by qihaishi on 14-12-25.
 */

public class TaskLoopRemind extends TimerTask {
    //记录Log4J
    private ITpTaskAllotManager taskAllotManager;
    private ITpTaskManager taskManager;
    private IClassUserManager classUserManager;
    private ITpGroupStudentManager groupStudentManager;

    private ServletContext request;
    public TaskLoopRemind(ServletContext application){
        this.request=application;
        taskAllotManager=(TpTaskAllotManager) SpringBeanUtil.getBean("tpTaskAllotManager");
        taskManager=(TpTaskManager) SpringBeanUtil.getBean("tpTaskManager");
        classUserManager=(ClassUserManager)SpringBeanUtil.getBean("classUserManager");
        groupStudentManager=(TpGroupStudentManager)SpringBeanUtil.getBean("tpGroupStudentManager");
    }

    @Override
    public void run() {
        //早8~晚10执行
        Calendar am8= Calendar.getInstance();
        am8.set(Calendar.HOUR_OF_DAY,8);
        am8.set(Calendar.MINUTE,0);
        am8.set(Calendar.SECOND,0);
        Calendar pm10= Calendar.getInstance();
        pm10.set(Calendar.HOUR_OF_DAY,22);
        pm10.set(Calendar.MINUTE,0);
        pm10.set(Calendar.SECOND,0);
        Date now=new Date();
        if(!(now.after(am8.getTime())&&now.before(pm10.getTime()))){
            return;
        }

        /**
         * 获取当前时间至下一个小时内所有未发提醒的任务
         */
        System.out.println("TaskLoopRemind Timer 执行时间:" + UtilTool.DateConvertToString(new Date(), UtilTool.DateType.type1));
        TpTaskInfo taskInfo=new TpTaskInfo();
        taskInfo.setSelecttype(TpTaskInfo.QUERY_TYPE.HOUR.getValue());
        List<TpTaskInfo>taskList=this.taskManager.getTaskRemindList(taskInfo,null);
        if(taskList!=null&&taskList.size()>0){
            //信息Map
            List<Map<String,Object>>mapList=new ArrayList<Map<String, Object>>();
            List<Object>objList=null;
            StringBuilder sql=null;
            List<List<Object>>objListArray=new ArrayList<List<Object>>();
            List<String>sqlList=new ArrayList<String>();

            //获取任务对象
            for(TpTaskInfo task:taskList){
                TpTaskAllotInfo allotInfo=new TpTaskAllotInfo();
                allotInfo.setTaskid(task.getTaskid());
                List<TpTaskAllotInfo>taskAllotList=this.taskAllotManager.getList(allotInfo,null);

                if(taskAllotList!=null&&taskAllotList.size()>0){
                    for(TpTaskAllotInfo tt:taskAllotList){
                        Map<String,Object>map=new HashMap<String, Object>();
                        map.put("taskId",task.getTaskid());
                        Object taskname=task.getTaskobjnameremind()==null?"":task.getTaskobjnameremind();
                        map.put("content",UtilTool.ecode(task.getRealname()+"老师提醒你去完成【任务 "+task.getOrderidx()+" "+task.getTaskTypeName()+" "+taskname+"】"));
                        map.put("classId",tt.getAllotid());
                        map.put("taskType",task.getTasktype());
                        map.put("isVirtual","0");
                        map.put("classType",tt.getClasstype());
                        if(tt.getDcschoolid()==null||task.getEttuserid()==null||tt.getDcschoolid().toString().length()<1)
                            continue;
                        map.put("cUserId",task.getEttuserid());
                        map.put("schoolId",tt.getDcschoolid());
                        map.put("userType","3");
                        List<Integer> userId=new ArrayList<Integer>();
                        if(tt.getUsertype()==0){
                            ClassUser cu=new ClassUser();
                            cu.setClassid(Integer.parseInt(tt.getUsertypeid()+""));
                            cu.setRelationtype("学生");
                            List<ClassUser>userList=this.classUserManager.getList(cu,null);
                            if(userList!=null&&userList.size()>0){
                                for(ClassUser classUser:userList){
                                    if(classUser.getEttuserid()!=null&&classUser.getEttuserid()>0)
                                        userId.add(classUser.getEttuserid());
                                }
                            }
                        }else if(tt.getUsertype()==2){
                            TpGroupStudent gs=new TpGroupStudent();
                            gs.setGroupid(tt.getUsertypeid());
                            List<TpGroupStudent>gsList=this.groupStudentManager.getList(gs,null);
                            if(gsList!=null&&gsList.size()>0){
                                for(TpGroupStudent groupStudent:gsList){

                                    if(groupStudent.getEttuserid()!=null&&groupStudent.getEttuserid()>0)
                                        userId.add(groupStudent.getEttuserid());
                                }
                            }
                        }
                        if(userId==null||userId.size()<1)
                            continue;
                        String tmpIds="";
                        for(Integer uid:userId){
                            if(tmpIds.length()>0)
                                tmpIds+=",";
                            tmpIds+=uid.toString();
                        }
                        map.put("userIds",tmpIds);
                        mapList.add(map);


                        TpTaskAllotInfo upd=new TpTaskAllotInfo();
                        upd.setRef(tt.getRef());
                        upd.setRemindstatus(1);
                        sql=new StringBuilder();
                        objList=this.taskAllotManager.getUpdateSql(upd,sql);
                        if(objList!=null&&sql!=null){
                            objListArray.add(objList);
                            sqlList.add(sql.toString());
                        }
                    }

                }
            }

            //向网校发送数据
            if(!sendTaskRemindObj(mapList)){
                System.out.println("sendTaskLoopRemind: error! TaskLoopRemind 任务提醒添加失败!");
                return;
            }else{
               if(objListArray.size()>0&&sqlList.size()>0){
                    if(this.taskManager.doExcetueArrayProc(sqlList,objListArray)){
                        System.out.println("TaskLoopRemind修改任务提醒状态成功!");
                    }else
                        System.out.println("TaskLoopRemind修改任务提醒状态失败!");
                }
            }
        }
    }
}

