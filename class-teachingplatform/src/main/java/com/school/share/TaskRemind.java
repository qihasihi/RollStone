package com.school.share;

import com.school.entity.ClassUser;
import com.school.entity.teachpaltform.TpGroupStudent;
import com.school.entity.teachpaltform.TpTaskAllotInfo;
import com.school.entity.teachpaltform.TpTaskInfo;
import com.school.manager.ClassUserManager;
import com.school.manager.inter.IClassUserManager;
import com.school.manager.inter.teachpaltform.ITpGroupStudentManager;
import com.school.manager.inter.teachpaltform.ITpTaskAllotManager;
import com.school.manager.inter.teachpaltform.ITpTaskManager;
import com.school.manager.teachpaltform.TpGroupStudentManager;
import com.school.manager.teachpaltform.TpTaskAllotManager;
import com.school.manager.teachpaltform.TpTaskManager;
import com.school.util.SpringBeanUtil;
import com.school.util.SynchroUtil;
import com.school.util.UtilTool;

import javax.servlet.ServletContext;
import java.util.*;

import static com.school.share.TaskLoopRemindUtil.sendTaskRemindObj;

/**
 * Created by qihaishi on 14-12-26.
 */
public class TaskRemind extends TimerTask {
    //记录Log4J
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
    private ITpTaskAllotManager taskAllotManager;
    private ITpTaskManager taskManager;
    private IClassUserManager classUserManager;
    private ITpGroupStudentManager groupStudentManager;

    private ServletContext request;
    public TaskRemind(ServletContext application){
        this.request=application;
        taskAllotManager=(TpTaskAllotManager) SpringBeanUtil.getBean("tpTaskAllotManager");
        taskManager=(TpTaskManager) SpringBeanUtil.getBean("tpTaskManager");
        classUserManager=(ClassUserManager)SpringBeanUtil.getBean("classUserManager");
        groupStudentManager=(TpGroupStudentManager)SpringBeanUtil.getBean("tpGroupStudentManager");
    }


    @Override
    public void run() {

        TpTaskInfo taskInfo=new TpTaskInfo();
        taskInfo.setSelecttype(TpTaskInfo.QUERY_TYPE.晚22至早8.getValue());
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
                        map.put("content", UtilTool.ecode("提醒你完成任务[任务 " + task.getOrderidx() + " " + taskname + "]，快去完成它吧！"));
                        map.put("classId",tt.getAllotid());
                        map.put("taskType",task.getTasktype());
                        map.put("isVirtual","0");
                        map.put("classType",tt.getClasstype());
                        map.put("cUserId",task.getUserid());
                        if(tt.getDcschoolid()==null||tt.getDcschoolid().toString().length()<1)
                            continue;
                        map.put("schoolId",tt.getDcschoolid());
                        map.put("userType","3");
                        String userId="";
                        if(tt.getUsertype()==0){
                            ClassUser cu=new ClassUser();
                            cu.setClassid(Integer.parseInt(tt.getUsertypeid()+""));
                            cu.setRelationtype("学生");
                            List<ClassUser>userList=this.classUserManager.getList(cu,null);
                            if(userList!=null&&userList.size()>0){
                                for(ClassUser classUser:userList){
                                    if(userId.length()>0)
                                        userId+=",";
                                    if(classUser.getEttuserid()!=null&&classUser.getEttuserid()>0)
                                        userId+=classUser.getEttuserid();
                                }
                            }
                        }else if(tt.getUsertype()==2){
                            TpGroupStudent gs=new TpGroupStudent();
                            gs.setGroupid(tt.getUsertypeid());
                            List<TpGroupStudent>gsList=this.groupStudentManager.getList(gs,null);
                            if(gsList!=null&&gsList.size()>0){
                                for(TpGroupStudent groupStudent:gsList){
                                    if(userId.length()>0)
                                        userId+=",";
                                    if(groupStudent.getEttuserid()!=null&&groupStudent.getEttuserid()>0)
                                        userId+=groupStudent.getEttuserid();
                                }
                            }
                        }
                        if(userId==null||userId.trim().length()<1)
                            continue;
                        map.put("userIds",userId);
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
                System.out.println("sendTaskRemind: error! TaskRemind 任务提醒添加失败!");
                return;
            }else{
                if(objListArray.size()>0&&sqlList.size()>0){
                    if(this.taskAllotManager.doExcetueArrayProc(sqlList,objListArray)){
                        System.out.println("TaskRemind修改任务提醒状态成功!");
                    }else
                        System.out.println("TaskRemind修改任务提醒状态失败!");
                }
            }
        }
    }
}