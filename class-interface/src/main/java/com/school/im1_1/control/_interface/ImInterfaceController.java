package com.school.im1_1.control._interface;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.control.base.BaseController;
import com.school.entity.UserInfo;
import com.school.entity.resource.ResourceInfo;
import com.school.entity.teachpaltform.*;
import com.school.entity.teachpaltform.interactive.TpTopicInfo;
import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;
import com.school.entity.teachpaltform.paper.*;
import com.school.im1_1.entity._interface.ImInterfaceInfo;
import com.school.im1_1.manager._interface.ImInterfaceManager;
import com.school.manager.UserManager;
import com.school.manager.inter.IUserManager;
import com.school.manager.inter.resource.IResourceManager;
import com.school.manager.inter.teachpaltform.*;
import com.school.manager.inter.teachpaltform.award.ITpStuScoreLogsManager;
import com.school.manager.inter.teachpaltform.award.ITpStuScoreManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicThemeManager;
import com.school.manager.inter.teachpaltform.paper.*;
import com.school.manager.resource.ResourceManager;
import com.school.manager.teachpaltform.*;
import com.school.manager.teachpaltform.award.TpStuScoreLogsManager;
import com.school.manager.teachpaltform.award.TpStuScoreManager;
import com.school.manager.teachpaltform.interactive.TpTopicManager;
import com.school.manager.teachpaltform.interactive.TpTopicThemeManager;
import com.school.manager.teachpaltform.paper.*;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yuechunyang on 14-7-25.
 */
@Controller
@RequestMapping(value="/imapi1_1")
public class ImInterfaceController extends BaseController<ImInterfaceInfo>{
    private ImInterfaceManager imInterfaceManager;
    private ITpCourseManager tpCourseManager;
    private ITpTaskManager tpTaskManager;
    private ITpTaskAllotManager tpTaskAllotManager;
    private IResourceManager resourceManager;
    private IQuestionManager questionManager;
    private IQuestionOptionManager questionOptionManager;
    private IUserManager userManager;
    private IQuestionAnswerManager questionAnswerManager;
    private ITaskPerformanceManager taskPerformanceManager;
    private ITpRecordManager tpRecordManager;
    private IPaperQuestionManager paperQuestionManager;
    private IPaperManager paperManager;
    private ITpTopicManager tpTopicManager;
    private ITpTopicThemeManager tpTopicThemeManager;
    private IStuPaperQuesLogsManager stuPaperQuesLogsManager;
    private IMicVideoPaperManager micVideoPaperManager;
    private IStuPaperLogsManager stuPaperLogsManager;
    private ITpStuScoreLogsManager tpStuScoreLogsManager;
    private IStuViewMicVideoLogManager stuViewMicVideoLogManager;
    private ITpStuScoreManager tpStuScoreManager;
    private ITpCourseClassManager tpCourseClassManager;
    public ImInterfaceController(){
        this.tpStuScoreLogsManager=this.getManager(TpStuScoreLogsManager.class);
        this.stuPaperLogsManager=this.getManager(StuPaperLogsManager.class);
        this.stuPaperQuesLogsManager=this.getManager(StuPaperQuesLogsManager.class);
        this.paperManager=this.getManager(PaperManager.class);
        this.imInterfaceManager=this.getManager(ImInterfaceManager.class);
        this.tpCourseManager = this.getManager(TpCourseManager.class);
        this.tpTaskManager = this.getManager(TpTaskManager.class);
        this.tpTaskAllotManager = this.getManager(TpTaskAllotManager.class);
        this.resourceManager = this.getManager(ResourceManager.class);
        this.questionManager = this.getManager(QuestionManager.class);
        this.questionOptionManager = this.getManager(QuestionOptionManager.class);
        this.userManager = this.getManager(UserManager.class);
        this.questionAnswerManager = this.getManager(QuestionAnswerManager.class);
        this.taskPerformanceManager = this.getManager(TaskPerformanceManager.class);
        this.tpRecordManager=this.getManager(TpRecordManager.class);
        this.paperQuestionManager=this.getManager(PaperQuestionManager.class);
        this.tpTopicManager=this.getManager(TpTopicManager.class);
        this.tpTopicThemeManager=this.getManager(TpTopicThemeManager.class);
        this.micVideoPaperManager=this.getManager(MicVideoPaperManager.class);
        this.stuViewMicVideoLogManager=this.getManager(StuViewMicVideoLogManager.class);
        this.tpStuScoreManager = this.getManager(TpStuScoreManager.class);
        this.tpCourseClassManager = this.getManager(TpCourseClassManager.class);
    }
    /**
     * 学习目录接口
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=StudyModule",method= {RequestMethod.GET,RequestMethod.POST})
    public void getStudyModule(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        String userid = request.getParameter("jid");
        String usertype=request.getParameter("userType");
        String schoolid = request.getParameter("schoolId");
        String timestamp = request.getParameter("time");
        String sig = request.getParameter("sign");
        if(!ImUtilTool.ValidateRequestParam(request)){
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> map = new HashMap();
        map.put("jid",userid);
        map.put("userType",usertype);
        map.put("schoolId",schoolid);
        map.put("time",timestamp);
        String sign = UrlSigUtil.makeSigSimple("StudyModule",map,"*ETT#HONER#2014*");
        Boolean b = UrlSigUtil.verifySigSimple("StudyModule",map,sig);
        if(!b){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"验证失败，非法登录\"}");
            return;
        }
        int utype=ImUtilTool.getUserType(usertype);
        UserInfo ui = new UserInfo();
        ui.setEttuserid(Integer.parseInt(userid));
        List<UserInfo> userList = this.userManager.getList(ui,null);
        if(userList==null||userList.size()<1){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前用户未绑定，请联系管理员\"}");
            return;
        }
        ImInterfaceInfo obj = new ImInterfaceInfo();
        obj.setSchoolid(Integer.parseInt(schoolid));
        obj.setUserid(userList.get(0).getUserid());
        obj.setUsertype(utype);
        List<Map<String,Object>> list = this.imInterfaceManager.getStudyModule(obj);
        if(list!=null&&list.size()>0){
            for(Map map1:list){
                map1.put("SCHOOLID" ,schoolid);
            }
        }
        Map m = new HashMap();
        Map m2 = new HashMap();
        if(list!=null&&list.size()>0){
            m2.put("classes",list);
            m2.put("activityNotifyNum","12");
        }else{
            m.put("result","0");
            m.put("msg","当前用户没有学习目录，请联系管理员");
        }
        m.put("result","1");
        m.put("msg","成功");
        m.put("data",m2);
        JSONObject object = JSONObject.fromObject(m);
        response.getWriter().print(object.toString());
    }

    /**
     * 班级任务接口
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=ClassTask",method= {RequestMethod.GET,RequestMethod.POST})
    public void getClassTask(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je = new JsonEntity();
        String classid = request.getParameter("classId");
        String schoolid = request.getParameter("schoolId");
        String classtype = request.getParameter("classType");
        String isvirtual = request.getParameter("isVirtual");
        String userid = request.getParameter("jid");
        String usertype = request.getParameter("userType");
        String timestamp = request.getParameter("time");
        String sig = request.getParameter("sign");
        if(!ImUtilTool.ValidateRequestParam(request)){
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> map = new HashMap();
        map.put("classId",classid);
        map.put("schoolId",schoolid);
        map.put("classType",classtype);
        map.put("isVirtual",isvirtual);
        map.put("jid",userid);
        map.put("userType",usertype);
        map.put("time",timestamp);
        String sign = UrlSigUtil.makeSigSimple("ClassTask",map,"*ETT#HONER#2014*");
        Boolean b = UrlSigUtil.verifySigSimple("ClassTask", map, sig);
        if(!b){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"验证失败，非法登录\"}");
            return;
        }
        int utype=ImUtilTool.getUserType(usertype);
        UserInfo ui = new UserInfo();
        ui.setEttuserid(Integer.parseInt(userid));
        List<UserInfo> userList = this.userManager.getList(ui, null);
        if(userList==null||userList.size()<1){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前用户未绑定，请联系管理员\"}");
            return;
        }
        ImInterfaceInfo obj = new ImInterfaceInfo();
        obj.setSchoolid(Integer.parseInt(schoolid));
        obj.setClassid(Integer.parseInt(classid));
        if(utype==2){
            obj.setUserid(userList.get(0).getUserid());
        }
        List<Map<String,Object>> courseList = this.imInterfaceManager.getClassTaskCourse(obj);
        Map m = new HashMap();
        if(courseList!=null&&courseList.size()>0){
            for(int i = 0;i<courseList.size();i++){
                List<Map<String,Object>> taskList;
                if(utype==2){
                    taskList = this.imInterfaceManager.getClassTaskTask(Long.parseLong(courseList.get(i).get("COURSEID").toString()),null,Integer.parseInt(classid));
                }else{
                    taskList = this.imInterfaceManager.getClassTaskTask(Long.parseLong(courseList.get(i).get("COURSEID").toString()),userList.get(0).getUserid(),Integer.parseInt(classid));
                }
                if(taskList!=null&&taskList.size()>0){
                    for(int j = 0;j<taskList.size();j++){
                        int time =Integer.parseInt(taskList.get(j).get("LEFTTIME").toString());
                        int days = 0;
                        int hours =0;
                        int mins = 0;
                        int seconds = 0;
                        if(time>0){
                            seconds = time%60;
                            if(seconds>0){
                                mins = time/60;
                            }else{
                                seconds = seconds*60;
                            }
                            if(mins>0){
                                hours = mins/60;
                            }
                            if(hours>0){
                                days= hours/24;
                            }
                        }
                        if(days>0){
                            taskList.get(j).put("LEFTTIME",days+"天");
                        }else{
                            if(hours>0){
                                taskList.get(j).put("LEFTTIME",hours+"小时");
                            }else{
                                if(mins>0){
                                    taskList.get(j).put("LEFTTIME",mins+"分钟");
                                }else{
                                    if(seconds>0){
                                        taskList.get(j).put("LEFTTIME",seconds+"秒");
                                    }
                                }
                            }
                        }
                        String typename = "";
                        switch (Integer.parseInt(taskList.get(j).get("TASKTYPE").toString())){
                            case 1:
                                typename="资源学习";
                                break;
                            case 2:
                                typename="互动交流";
                                break;
                            case 3:
                                typename="试题";
                                break;
                            case 4:
                                typename="成卷测试";
                                break;
                            case 5:
                                typename="自主测试";
                                break;
                            case 6:
                                typename="微课程学习";
                                break;
                            case 7:
                                typename="图片";
                                break;
                            case 8:
                                typename="文字";
                                break;
                            case 9:
                                typename="视频";
                                break;
                        }
                        if(Integer.parseInt(taskList.get(j).get("TASKTYPE").toString())==3){
                            taskList.get(j).put("TASKNAME","任务 "+taskList.get(j).get("ORDERIDX")+" "+typename);
                        }else{
                            taskList.get(j).put("TASKNAME","任务 "+taskList.get(j).get("ORDERIDX")+" "+typename+" "+taskList.get(j).get("TASKNAME"));
                        }
                    }
                }
                courseList.get(i).put("taskList",taskList);
            }
        }else{
            m.put("result","0");
            m.put("msg","当前没有专题");
        }

        m.put("result","1");
        m.put("msg","成功");
        m.put("data",courseList);
        JSONObject object = JSONObject.fromObject(m);
        response.getWriter().print(object.toString());
    }

    /**
     * 学生班级班级课表接口
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=StuClassCalendar",method= {RequestMethod.GET,RequestMethod.POST})
    public void getStuClassCalendar(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je = new JsonEntity();
        String userid = request.getParameter("jid");
        String usertype=request.getParameter("userType");
        String classid = request.getParameter("classId");
        String schoolid = request.getParameter("schoolId");
        String month = request.getParameter("requestMonth");
        String year = request.getParameter("requestYear");
        String timestamp = request.getParameter("time");
        String sig = request.getParameter("sign");
        if(!ImUtilTool.ValidateRequestParam(request)){
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> map = new HashMap();
        map.put("jid",userid);
        map.put("userType",usertype);
        map.put("classId",classid);
        map.put("schoolId",schoolid);
        map.put("time",timestamp);
        map.put("requestMonth",month);
        map.put("requestYear",year);
       // String sign = UrlSigUtil.makeSigSimple("StuClassCalendar",map,"*ETT#HONER#2014*");
        Boolean b = UrlSigUtil.verifySigSimple("StuClassCalendar",map,sig);
        if(!b){
            response.getWriter().print("{\"result\":\"0\",\"message\":\"验证失败，非法登录\"}");
            return;
        }
        int utype = ImUtilTool.getUserType(usertype);
        UserInfo ui = new UserInfo();
        ui.setEttuserid(Integer.parseInt(userid));
        List<UserInfo> userList = this.userManager.getList(ui,null);
        if(userList==null||userList.size()<1){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前用户未绑定，请联系管理员\"}");
            return;
        }
        ImInterfaceInfo obj = new ImInterfaceInfo();
        obj.setSchoolid(Integer.parseInt(schoolid));
        obj.setClassid(Integer.parseInt(classid));
        Map m = new HashMap();
        Map m2 = new HashMap();
        List<Map<String,Object>> courseList = this.imInterfaceManager.getStudentCalendar(userList.get(0).getUserid(),Integer.parseInt(schoolid),Integer.parseInt(classid),Integer.parseInt(year),Integer.parseInt(month));
         if(utype!=2){
            long mTime = System.currentTimeMillis();
            int offset = Calendar.getInstance().getTimeZone().getRawOffset();
            Calendar c = Calendar.getInstance();
            c.setTime(new Date(mTime - offset));
            String currentDay =UtilTool.DateConvertToString(c.getTime(),UtilTool.DateType.type1);
            String currentMonth = currentDay.split("-")[1];
            if(Integer.parseInt(month)==Integer.parseInt(currentMonth)){
                List<Map<String,Object>> courseArray = this.imInterfaceManager.getstudentCalendarDetail(userList.get(0).getUserid(), utype, Integer.parseInt(classid), Integer.parseInt(schoolid), currentDay);
                if(courseArray!=null&&courseArray.size()>0){
                    List<Map<String,Object>> courseArray2 = new ArrayList<Map<String, Object>>();
                    Date d = new Date();
                    for(int i = 0;i<courseArray.size();i++){
                        Map o = courseArray.get(i);
                        Map o2 = new HashMap();
                        o2.put("courseId",o.get("COURSE_ID"));
                        o2.put("courseName",o.get("COURSE_NAME"));
                        if(o.get("BEGIN_TIME")!=null&&o.get("END_TIME")!=null){
                            int thisDay = d.getDay();
                            Date btime = UtilTool.StringConvertToDate(o.get("BEGIN_TIME").toString(),UtilTool.DateType.type1);
                            int bDay = btime.getDay();
                            Date etime = UtilTool.StringConvertToDate(o.get("END_TIME").toString(),UtilTool.DateType.type1);
                            int eDay = etime.getDay();
                            if(thisDay==bDay&&thisDay==eDay){
                                if(o.get("BEGIN_TIME").toString().indexOf(":")>0&&o.get("END_TIME").toString().indexOf(":")>0){
                                    String coureBtime = o.get("BEGIN_TIME").toString().substring((o.get("BEGIN_TIME").toString().indexOf(" ")+1),o.get("BEGIN_TIME").toString().lastIndexOf(":"));
                                    String coureEtime = o.get("END_TIME").toString().substring((o.get("END_TIME").toString().indexOf(" ")+1),o.get("END_TIME").toString().lastIndexOf(":"));
                                    o2.put("courseDate",coureBtime+"~"+coureEtime);
                                }else{
                                    o2.put("courseDate",null);
                                }
                            }else{
                                o2.put("courseDate",null);
                            }
                        }else{
                            o2.put("courseDate",null);
                        }
                        //o2.put("courseDate",o.get("BEGIN_TIME").toString().substring(0,19)+"~"+(o.get("END_TIME")!=null?o.get("END_TIME").toString().substring(0,19):"——"));
                        o2.put("schoolId",o.get("DC_SCHOOL_ID"));
                        o2.put("classId",o.get("CLASS_ID"));
                        o2.put("classType",o.get("CLASS_TYPE"));
                        o2.put("className",o.get("CLASSNAME"));
                        //查询课堂表现
                        Map o3 = new HashMap();
                        //首先查出课程的相关信息
                        TpCourseClass tc = new TpCourseClass();
                        tc.setCourseid(Long.parseLong(o.get("COURSE_ID").toString()));
                        tc.setClassid(Integer.parseInt(o.get("CLASS_ID").toString()));
                        List<TpCourseClass> tpCourseClassList = this.tpCourseClassManager.getList(tc,null);
                        if(tpCourseClassList!=null&&tpCourseClassList.size()>0){
                            List<Map<String,Object>> scoreList = tpStuScoreManager.getPageDataList(tpCourseClassList.get(0).getCourseid(),Long.parseLong(tpCourseClassList.get(0).getClassid().toString()),1,tpCourseClassList.get(0).getSubjectid(),null,userList.get(0).getUserid());
                            if(scoreList!=null&&scoreList.size()>0){
                                o3.put("personTotalScore",scoreList.get(0).get("COURSE_TOTAL_SCORE")!=null?Integer.parseInt(scoreList.get(0).get("COURSE_TOTAL_SCORE").toString()):0);
                                //计算小组得分和表现
                                int teamScore = 0;
                                String teamShow = "";
                                if(Integer.parseInt(scoreList.get(0).get("GSCORE1").toString())>0){
                                    teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE1").toString());
                                    teamShow+="组内成员全部出勤且无迟到早退";
                                }
                                if(Integer.parseInt(scoreList.get(0).get("GSCORE2").toString())>0){
                                    teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE2").toString());
                                    teamShow+="/r/n本组笑脸总数排全班第一";
                                }
                                if(Integer.parseInt(scoreList.get(0).get("GSCORE3").toString())>0){
                                    teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE3").toString());
                                    teamShow+="/r/n本组小红旗总数排全班第一";
                                }
                                if(Integer.parseInt(scoreList.get(0).get("GSCORE4").toString())>0){
                                    teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE4").toString());
                                    teamShow+="/r/n本组违反纪律次数排全班第一";
                                }
                                if(Integer.parseInt(scoreList.get(0).get("GSCORE5").toString())>0){
                                    teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE5").toString());
                                    teamShow+="/r/n本组完成网上任务完成率（小组任务完成率）排全班第一";
                                }
                                o3.put("teamShow",teamShow);
                                o3.put("teamScore",teamScore);

                                o3.put("taskScore",scoreList.get(0).get("TASK_SCORE")!=null?Integer.parseInt(scoreList.get(0).get("TASK_SCORE").toString()):0);
                                o3.put("presenceScore",scoreList.get(0).get("ATTENDANCENUM")!=null?Integer.parseInt(scoreList.get(0).get("ATTENDANCENUM").toString()):0);
                                o3.put("smileScore",scoreList.get(0).get("SMILINGNUM")!=null?Integer.parseInt(scoreList.get(0).get("SMILINGNUM").toString()):0);
                                o3.put("illegalScore",scoreList.get(0).get("VIODATIONDISNUM")!=null?Integer.parseInt(scoreList.get(0).get("VIODATIONDISNUM").toString()):0);
                                //网下得分
                                int offline = 0;
                                if(Integer.parseInt(scoreList.get(0).get("ATTENDANCENUM").toString())!=0){
                                    offline+=Integer.parseInt(scoreList.get(0).get("ATTENDANCENUM").toString());
                                }
                                if(Integer.parseInt(scoreList.get(0).get("SMILINGNUM").toString())!=0){
                                    offline+=Integer.parseInt(scoreList.get(0).get("SMILINGNUM").toString());
                                }
                                if(Integer.parseInt(scoreList.get(0).get("VIOLATIONDISNUM").toString())!=0){
                                    offline+=Integer.parseInt(scoreList.get(0).get("VIOLATIONDISNUM").toString());
                                }
                                o3.put("offlineScore",offline);
                                o3.put("comentScore",scoreList.get(0).get("COMMENT_SCORE")!=null?Integer.parseInt(scoreList.get(0).get("COMMENT_SCORE").toString()):0);
                                o3.put("onlineScore",scoreList.get(0).get("WSSCORE")!=null?Integer.parseInt(scoreList.get(0).get("WSSCORE").toString()):0);
                            }
                        }
                        o2.put("courseShow",o3);
                        courseArray2.add(o2);
                    }
                    m2.put("courseArray",courseArray2);
                }else{
                    m2.put("courseArray",null);
                }
            }
        }
        if(courseList!=null&&courseList.size()>0){
            List<Map<String,Object>> courseList2 = new ArrayList<Map<String, Object>>();
            for(int i = 0 ;i<courseList.size();i++){
                Map o = courseList.get(i);
                Map o2 = new HashMap();
                o2.put("orderDay",o.get("E_DAY"));
                o2.put("hasCourse",o.get("HASCOURSE"));
                courseList2.add(o2);
            }
            m2.put("courseList",courseList2);
        }else{
            m.put("result","0");
            m.put("msg","当前没有专题");
        }
        m.put("result","1");
        m.put("msg","成功");
        m.put("data",m2);
        JSONObject object = JSONObject.fromObject(m);
        response.getWriter().print(object.toString());
    }

    /**
     * 教师班级课表接口
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=TeaClassCalendar",method= {RequestMethod.GET,RequestMethod.POST})
    public void getTeaClassCalendar(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je = new JsonEntity();
        String userid = request.getParameter("jid");
        String usertype=request.getParameter("userType");
        String schoolid = request.getParameter("schoolId");
        String month = request.getParameter("requestMonth");
        String year = request.getParameter("requestYear");
        String timestamp = request.getParameter("time");
        String sig = request.getParameter("sign");
        if(!ImUtilTool.ValidateRequestParam(request)){
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> map = new HashMap();
        map.put("jid",userid);
        map.put("userType",usertype);
        map.put("schoolId",schoolid);
        map.put("time",timestamp);
        map.put("requestMonth",month);
        map.put("requestYear",year);
       // String sign = UrlSigUtil.makeSigSimple("StuClassCalendar",map,"*ETT#HONER#2014*");
        Boolean b = UrlSigUtil.verifySigSimple("TeaClassCalendar",map,sig);
        if(!b){
            response.getWriter().print("{\"result\":\"0\",\"message\":\"验证失败，非法登录\"}");
            return;
        }
        int utype = ImUtilTool.getUserType(usertype);
        UserInfo ui = new UserInfo();
        ui.setEttuserid(Integer.parseInt(userid));
        List<UserInfo> userList = this.userManager.getList(ui,null);
        if(userList==null||userList.size()<1){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前用户未绑定，请联系管理员\"}");
            return;
        }
        List<Map<String,Object>> courseList = this.imInterfaceManager.getTeacherCalendar(userList.get(0).getUserid(), Integer.parseInt(schoolid), Integer.parseInt(year), Integer.parseInt(month));
        Map m = new HashMap();
        Map m2 = new HashMap();
        long mTime = System.currentTimeMillis();
        int offset = Calendar.getInstance().getTimeZone().getRawOffset();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(mTime - offset));
        String currentDay =UtilTool.DateConvertToString(c.getTime(),UtilTool.DateType.type1);
        String currentMonth = currentDay.split("-")[1];
        if(Integer.parseInt(month)==Integer.parseInt(currentMonth)){
            List<Map<String,Object>> courseArray = this.imInterfaceManager.getTeacherCalendarDetail(userList.get(0).getUserid(), utype, Integer.parseInt(schoolid), currentDay);
            if(courseArray!=null&&courseArray.size()>0){
                List<Map<String,Object>> courseArray2 = new ArrayList<Map<String, Object>>();
                Date d = new Date();
                for(int i = 0;i<courseArray.size();i++){
                    Map o = courseArray.get(i);
                    Map o2 = new HashMap();
                    o2.put("courseId",o.get("COURSE_ID"));
                    o2.put("courseName",o.get("COURSE_NAME"));
                    if(o.get("BEGIN_TIME")!=null&&o.get("END_TIME")!=null){
                        int thisDay = d.getDay();
                        Date btime = UtilTool.StringConvertToDate(o.get("BEGIN_TIME").toString(),UtilTool.DateType.type1);
                        int bDay = btime.getDay();
                        Date etime = UtilTool.StringConvertToDate(o.get("END_TIME").toString(),UtilTool.DateType.type1);
                        int eDay = etime.getDay();
                        if(thisDay==bDay&&thisDay==eDay){
                            if(o.get("BEGIN_TIME").toString().indexOf(":")>0&&o.get("END_TIME").toString().indexOf(":")>0){
                                String coureBtime = o.get("BEGIN_TIME").toString().substring((o.get("BEGIN_TIME").toString().indexOf(" ")+1),o.get("BEGIN_TIME").toString().lastIndexOf(":"));
                                String coureEtime = o.get("END_TIME").toString().substring((o.get("END_TIME").toString().indexOf(" ")+1),o.get("END_TIME").toString().lastIndexOf(":"));
                                o2.put("courseDate",coureBtime+"~"+coureEtime);
                            }else{
                                o2.put("courseDate",null);
                            }
                        }else{
                            o2.put("courseDate",null);
                        }
                    }else{
                        o2.put("courseDate",null);
                    }
                    //o2.put("courseDate",o.get("BEGIN_TIME")+"~"+o.get("END_TIME")!=null?o.get("END_TIME"):"——");
                    o2.put("schoolId",o.get("DC_SCHOOL_ID"));
                    o2.put("classId",o.get("CLASS_ID"));
                    o2.put("classType",o.get("CLASS_TYPE"));
                    o2.put("className",o.get("CLASSNAME"));
                    courseArray2.add(o2);
                }
                m2.put("courseArray",courseArray2);
            }else{
                m2.put("courseArray",null);
            }
        }
        if(courseList!=null&&courseList.size()>0){
            List<Map<String,Object>> courseList2 = new ArrayList<Map<String, Object>>();
            for(int i = 0 ;i<courseList.size();i++){
                Map o = courseList.get(i);
                Map o2 = new HashMap();
                o2.put("orderDay",o.get("E_DAY"));
                o2.put("hasCourse",o.get("HASCOURSE"));
                courseList2.add(o2);
            }
            m2.put("courseList",courseList2);
        }else{
            m.put("result","0");
            m.put("msg","当前没有专题");
        }
        m.put("result","1");
        m.put("msg","成功");
        m.put("data",m2);
        JSONObject object = JSONObject.fromObject(m);
        response.getWriter().print(object.toString());
    }

    /**
     * 教师班级课表接口详细
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=TeaClassCalendarDetail",method= {RequestMethod.GET,RequestMethod.POST})
    public void getTeaClassCalendarByDay(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je = new JsonEntity();
        String userid = request.getParameter("jid");
        String usertype=request.getParameter("userType");
        String schoolid = request.getParameter("schoolId");
        String currentDay = request.getParameter("requestDay");
        String timestamp = request.getParameter("time");
        String sig = request.getParameter("sign");
        if(!ImUtilTool.ValidateRequestParam(request)){
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> map = new HashMap();
        map.put("jid",userid);
        map.put("userType",usertype);
        map.put("schoolId",schoolid);
        map.put("time",timestamp);
        map.put("requestDay",currentDay);
       // String sign = UrlSigUtil.makeSigSimple("StuClassCalendar",map,"*ETT#HONER#2014*");
        Boolean b = UrlSigUtil.verifySigSimple("TeaClassCalendarDetail",map,sig);
        if(!b){
            response.getWriter().print("{\"result\":\"0\",\"message\":\"验证失败，非法登录\"}");
            return;
        }
        int utype = ImUtilTool.getUserType(usertype);
        UserInfo ui = new UserInfo();
        ui.setEttuserid(Integer.parseInt(userid));
        List<UserInfo> userList = this.userManager.getList(ui,null);
        if(userList==null||userList.size()<1){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前用户未绑定，请联系管理员\"}");
            return;
        }
        List<Map<String,Object>> courseArray = this.imInterfaceManager.getTeacherCalendarDetail(userList.get(0).getUserid(),utype, Integer.parseInt(schoolid), currentDay);
        Map m = new HashMap();
        Map m2 = new HashMap();
        if(courseArray!=null&&courseArray.size()>0){
            List<Map<String,Object>> courseArray2 = new ArrayList<Map<String, Object>>();
            Date d = new Date();
            for(int i = 0;i<courseArray.size();i++){
                Map o = courseArray.get(i);
                Map o2 = new HashMap();
                o2.put("courseId",o.get("COURSE_ID"));
                o2.put("courseName",o.get("COURSE_NAME"));
                if(o.get("BEGIN_TIME")!=null&&o.get("END_TIME")!=null){
                    int thisDay = d.getDay();
                    Date btime = UtilTool.StringConvertToDate(o.get("BEGIN_TIME").toString(),UtilTool.DateType.type1);
                    int bDay = btime.getDay();
                    Date etime = UtilTool.StringConvertToDate(o.get("END_TIME").toString(),UtilTool.DateType.type1);
                    int eDay = etime.getDay();
                    if(thisDay==bDay&&thisDay==eDay){
                        if(o.get("BEGIN_TIME").toString().indexOf(":")>0&&o.get("END_TIME").toString().indexOf(":")>0){
                            String coureBtime = o.get("BEGIN_TIME").toString().substring((o.get("BEGIN_TIME").toString().indexOf(" ")+1),o.get("BEGIN_TIME").toString().lastIndexOf(":"));
                            String coureEtime = o.get("END_TIME").toString().substring((o.get("END_TIME").toString().indexOf(" ")+1),o.get("END_TIME").toString().lastIndexOf(":"));
                            o2.put("courseDate",coureBtime+"~"+coureEtime);
                        }else{
                            o2.put("courseDate",null);
                        }
                    }else{
                        o2.put("courseDate",null);
                    }
                }else{
                    o2.put("courseDate",null);
                }
                //o2.put("courseDate",o.get("BEGIN_TIME")+"~"+o.get("END_TIME")!=null?o.get("END_TIME"):"——");
                o2.put("schoolId",o.get("DC_SCHOOL_ID"));
                o2.put("classId",o.get("CLASS_ID"));
                o2.put("classType",o.get("CLASS_TYPE"));
                o2.put("className",o.get("CLASSNAME"));
                courseArray2.add(o2);
            }
            m2.put("courseArray",courseArray2);
        }else{
            m2.put("courseArray",null);
        }
        m.put("result","1");
        m.put("msg","成功");
        m.put("data",m2);
        JSONObject object = JSONObject.fromObject(m);
        response.getWriter().print(object.toString());
    }

    /**
     * 学生班级课表接口详细
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=StuClassCalendarDetail",method= {RequestMethod.GET,RequestMethod.POST})
    public void getStuClassCalendarByDay(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je = new JsonEntity();
        String userid = request.getParameter("jid");
        String usertype=request.getParameter("userType");
        String schoolid = request.getParameter("schoolId");
        String currentDay = request.getParameter("requestDay");
        String classid = request.getParameter("classId");
        String timestamp = request.getParameter("time");
        String sig = request.getParameter("sign");
        if(!ImUtilTool.ValidateRequestParam(request)){
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> map = new HashMap();
        map.put("jid",userid);
        map.put("userType",usertype);
        map.put("classId",classid);
        map.put("schoolId",schoolid);
        map.put("time",timestamp);
        map.put("requestDay",currentDay);
       //String sign = UrlSigUtil.makeSigSimple("StuClassCalendarDetail",map,"*ETT#HONER#2014*");
        Boolean b = UrlSigUtil.verifySigSimple("StuClassCalendarDetail",map,sig);
        if(!b){
            response.getWriter().print("{\"result\":\"0\",\"message\":\"验证失败，非法登录\"}");
            return;
        }
        int utype = ImUtilTool.getUserType(usertype);
        UserInfo ui = new UserInfo();
        ui.setEttuserid(Integer.parseInt(userid));
        List<UserInfo> userList = this.userManager.getList(ui, null);
        if(userList==null||userList.size()<1){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前用户未绑定，请联系管理员\"}");
            return;
        }
        List<Map<String,Object>> courseArray = this.imInterfaceManager.getstudentCalendarDetail(userList.get(0).getUserid(), utype,Integer.parseInt(classid), Integer.parseInt(schoolid), currentDay);
        Map m = new HashMap();
        Map m2 = new HashMap();
        if(courseArray!=null&&courseArray.size()>0){
            List<Map<String,Object>> courseArray2 = new ArrayList<Map<String, Object>>();
            Date d = new Date();
            for(int i = 0;i<courseArray.size();i++){
                Map o = courseArray.get(i);
                Map o2 = new HashMap();
                o2.put("courseId",o.get("COURSE_ID"));
                o2.put("courseName",o.get("COURSE_NAME"));
                if(o.get("BEGIN_TIME")!=null&&o.get("END_TIME")!=null){
                    int thisDay = d.getDay();
                    Date btime = UtilTool.StringConvertToDate(o.get("BEGIN_TIME").toString(),UtilTool.DateType.type1);
                    int bDay = btime.getDay();
                    Date etime = UtilTool.StringConvertToDate(o.get("END_TIME").toString(),UtilTool.DateType.type1);
                    int eDay = etime.getDay();
                    if(thisDay==bDay&&thisDay==eDay){
                        if(o.get("BEGIN_TIME").toString().indexOf(":")>0&&o.get("END_TIME").toString().indexOf(":")>0){
                            String coureBtime = o.get("BEGIN_TIME").toString().substring((o.get("BEGIN_TIME").toString().indexOf(" ")+1),o.get("BEGIN_TIME").toString().lastIndexOf(":"));
                            String coureEtime = o.get("END_TIME").toString().substring((o.get("END_TIME").toString().indexOf(" ")+1),o.get("END_TIME").toString().lastIndexOf(":"));
                            o2.put("courseDate",coureBtime+"~"+coureEtime);
                        }else{
                            o2.put("courseDate",null);
                        }
                    }else{
                        o2.put("courseDate",null);
                    }
                }else{
                    o2.put("courseDate",null);
                }
                //o2.put("courseDate",o.get("BEGIN_TIME").toString().substring(0,19)+"~"+(o.get("END_TIME")!=null?o.get("END_TIME").toString().substring(0,19):"——"));
                o2.put("schoolId",o.get("DC_SCHOOL_ID"));
                o2.put("classId",o.get("CLASS_ID"));
                o2.put("classType",o.get("CLASS_TYPE"));
                o2.put("className",o.get("CLASSNAME"));
                //查询课堂表现
                Map o3 = new HashMap();
                //首先查出课程的相关信息
                TpCourseClass tc = new TpCourseClass();
                tc.setCourseid(Long.parseLong(o.get("COURSE_ID").toString()));
                tc.setClassid(Integer.parseInt(o.get("CLASS_ID").toString()));
                List<TpCourseClass> tpCourseClassList = this.tpCourseClassManager.getList(tc,null);
                if(tpCourseClassList!=null&&tpCourseClassList.size()>0){
                    List<Map<String,Object>> scoreList = tpStuScoreManager.getPageDataList(tpCourseClassList.get(0).getCourseid(),Long.parseLong(tpCourseClassList.get(0).getClassid().toString()),1,tpCourseClassList.get(0).getSubjectid(),null,userList.get(0).getUserid());
                    if(scoreList!=null&&scoreList.size()>0){
                        o3.put("personTotalScore",scoreList.get(0).get("COURSE_TOTAL_SCORE")!=null?Integer.parseInt(scoreList.get(0).get("COURSE_TOTAL_SCORE").toString()):0);
                        //计算小组得分和表现
                        int teamScore = 0;
                        String teamShow = "";
                        if(Integer.parseInt(scoreList.get(0).get("GSCORE1").toString())>0){
                            teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE1").toString());
                            teamShow+="组内成员全部出勤且无迟到早退";
                        }
                        if(Integer.parseInt(scoreList.get(0).get("GSCORE2").toString())>0){
                            teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE2").toString());
                            teamShow+="/r/n本组笑脸总数排全班第一";
                        }
                        if(Integer.parseInt(scoreList.get(0).get("GSCORE3").toString())>0){
                            teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE3").toString());
                            teamShow+="/r/n本组小红旗总数排全班第一";
                        }
                        if(Integer.parseInt(scoreList.get(0).get("GSCORE4").toString())>0){
                            teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE4").toString());
                            teamShow+="/r/n本组违反纪律次数排全班第一";
                        }
                        if(Integer.parseInt(scoreList.get(0).get("GSCORE5").toString())>0){
                            teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE5").toString());
                            teamShow+="/r/n本组完成网上任务完成率（小组任务完成率）排全班第一";
                        }
                        o3.put("teamShow",teamShow);
                        o3.put("teamScore",teamScore);

                        o3.put("taskScore",scoreList.get(0).get("TASK_SCORE")!=null?Integer.parseInt(scoreList.get(0).get("TASK_SCORE").toString()):0);
                        o3.put("presenceScore",scoreList.get(0).get("ATTENDANCENUM")!=null?Integer.parseInt(scoreList.get(0).get("ATTENDANCENUM").toString()):0);
                        o3.put("smileScore",scoreList.get(0).get("SMILINGNUM")!=null?Integer.parseInt(scoreList.get(0).get("SMILINGNUM").toString()):0);
                        o3.put("illegalScore",scoreList.get(0).get("VIODATIONDISNUM")!=null?Integer.parseInt(scoreList.get(0).get("VIODATIONDISNUM").toString()):0);
                        //网下得分
                        int offline = 0;
                        if(Integer.parseInt(scoreList.get(0).get("ATTENDANCENUM").toString())!=0){
                            offline+=Integer.parseInt(scoreList.get(0).get("ATTENDANCENUM").toString());
                        }
                        if(Integer.parseInt(scoreList.get(0).get("SMILINGNUM").toString())!=0){
                            offline+=Integer.parseInt(scoreList.get(0).get("SMILINGNUM").toString());
                        }
                        if(Integer.parseInt(scoreList.get(0).get("VIOLATIONDISNUM").toString())!=0){
                            offline+=Integer.parseInt(scoreList.get(0).get("VIOLATIONDISNUM").toString());
                        }
                        o3.put("offlineScore",offline);
                        o3.put("comentScore",scoreList.get(0).get("COMMENT_SCORE")!=null?Integer.parseInt(scoreList.get(0).get("COMMENT_SCORE").toString()):0);
                        o3.put("onlineScore",scoreList.get(0).get("WSSCORE")!=null?Integer.parseInt(scoreList.get(0).get("WSSCORE").toString()):0);
                    }
                }
                o2.put("courseShow",o3);
                courseArray2.add(o2);
            }
            m2.put("courseArray",courseArray2);
        }else{
            m2.put("courseArray",null);
        }

        m.put("result","1");
        m.put("msg","成功");
        m.put("data",m2);
        JSONObject object = JSONObject.fromObject(m);
        response.getWriter().print(object.toString());
    }

    /**
     * 添加任务接口
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=AddTask",method= {RequestMethod.POST,RequestMethod.GET})
    public void addTask(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        //接收map
        HashMap<String,String> map = ImUtilTool.getRequestParam(request);
        String dataStr = map.get("data");
        String userid = map.get("jid");
        String schoolid = map.get("schoolId");
        String timestamp = map.get("time");
        String sig = request.getParameter("sign");
        if(!ImUtilTool.ValidateRequestParam(request)){
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        map.remove("data");
        map.remove("sign");
        String sign = UrlSigUtil.makeSigSimple("AddTask",map,"*ETT#HONER#2014*");
        Boolean b = UrlSigUtil.verifySigSimple("AddTask",map,sig);
        if(!b){
            response.getWriter().print("{\"result\":\"0\",\"message\":\"验证失败，非法登录\"}");
            return;
        }
        UserInfo ui = new UserInfo();
        ui.setEttuserid(Integer.parseInt(userid));
        List<UserInfo> userList = this.userManager.getList(ui,null);
        if(userList==null||userList.size()<1){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前用户未绑定，请联系管理员\"}");
            return;
        }
        JSONObject jb=JSONObject.fromObject(dataStr);
        //拆分json字符串，得到各个参数
        Long courseid=jb.containsKey("courseId")?jb.getLong("courseId"):0;
        int tasktype = jb.containsKey("taskType")?jb.getInt("taskType"):0;
        String tasktitle = jb.containsKey("taskTitle")?jb.getString("taskTitle"):"";
        String taskcontent = jb.containsKey("taskContent")?jb.getString("taskContent"):"";
        String taskanalysis = jb.containsKey("taskAnalysis")?jb.getString("taskAnalysis"):"";
        String taskattach = jb.containsKey("taskAttach")?jb.getString("taskAttach"):"";
        int attachtype = jb.containsKey("attachType")?jb.getInt("attachType"):0;
        Object classesObj = jb.containsKey("classes")?jb.get("classes"):"";
        //验证专题是否存在
        TpCourseInfo courseInfo=new TpCourseInfo();
        courseInfo.setCourseid(courseid);
        List<TpCourseInfo>courseList=this.tpCourseManager.getList(courseInfo,null);
        if(courseList==null||courseList.size()<1){
            response.getWriter().print("\"{\\\"result\\\":\\\"0\\\",\\\"msg\\\":\\\"专题不存在，请检查重试\\\"}\"");
            return;
        }
        /**
         *查询出当前专题有效的任务个数，排序用
         */

        TpTaskInfo t=new TpTaskInfo();
        t.setCourseid(courseid);
        //查询没被我删除的任务
        t.setSelecttype(1);
        t.setLoginuserid(userList.get(0).getUserid());
        t.setStatus(1);

        //已发布的任务
        List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(t, null);
        Integer orderIdx=1;
        if(taskList!=null&&taskList.size()>0)
            orderIdx+=taskList.size();
        //得到最新的任务id
        Long tasknextid=this.tpTaskManager.getNextId(true);
        //批量执行存储过程用的几个变量
        StringBuilder sql=null;
        List<Object>objList=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        //组织任务数据
        TpTaskInfo tpTaskInfo = new TpTaskInfo();
        tpTaskInfo.setCriteria(2);
        tpTaskInfo.setTaskvalueid(Long.valueOf(1));
        tpTaskInfo.setOrderidx(orderIdx);
        tpTaskInfo.setTaskid(tasknextid);
        tpTaskInfo.setTaskname(tasktitle);
        tpTaskInfo.setImtaskanalysis(taskanalysis);
        if(taskattach!=null&&taskattach.length()>0){
            tpTaskInfo.setImtaskattach(taskattach);
            tpTaskInfo.setImtaskattachtype(attachtype);
        }
        tpTaskInfo.setImtaskcontent(taskcontent);
        tpTaskInfo.setTasktype(tasktype);
        tpTaskInfo.setCourseid(courseid);
        tpTaskInfo.setCuserid(userList.get(0).getRef());
        sql = new StringBuilder();
        objList = new ArrayList<Object>();
        objList=this.tpTaskManager.getSaveSql(tpTaskInfo,sql);
        sqlListArray.add(sql.toString());
        objListArray.add(objList);
        //组织任务发给班级的数据
        if(classesObj!=null){
            JSONArray jr = JSONArray.fromObject(classesObj);
            if(jr!=null&&jr.size()>0){
                for(int i = 0;i<jr.size();i++){
                    JSONObject jsonObject = JSONObject.fromObject(jr.get(i));
                    //拆分json字符串，得到各个参数
                    if(jsonObject!=null&&jsonObject.size()>0){
                        Long classid=jsonObject.containsKey("taskUserTeamId")?jsonObject.getLong("taskUserTeamId"):0;
                        int classtype=jsonObject.containsKey("taskUserType")?jsonObject.getInt("taskUserType"):0;
                        String starttime=jsonObject.containsKey("startTime")?jsonObject.getString("startTime"):"";
                        String endtime=jsonObject.containsKey("endTime")?jsonObject.getString("endTime"):"";
                        TpTaskAllotInfo tpTaskAllotInfo = new TpTaskAllotInfo();
                        tpTaskAllotInfo.setCourseid(courseid);
                        tpTaskAllotInfo.setTaskid(tasknextid);
                        tpTaskAllotInfo.setUsertype(classtype);
                        tpTaskAllotInfo.setUsertypeid(Long.parseLong(classid+""));
                        tpTaskAllotInfo.setBtime(UtilTool.StringConvertToDate(starttime));
                        tpTaskAllotInfo.setEtime(UtilTool.StringConvertToDate(endtime));
                        tpTaskAllotInfo.setCuserid(userList.get(0).getRef());
                        sql = new StringBuilder();
                        objList = new ArrayList<Object>();
                        objList = this.tpTaskAllotManager.getSaveSql(tpTaskAllotInfo,sql);
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                }
            }else{
                response.getWriter().print("\"{\\\"result\\\":\\\"0\\\",\\\"msg\\\":\\\"未获取到任务对象，请重试\\\"}\"");
                return;
            }
        }
        Map m = new HashMap();
        Boolean bl = this.imInterfaceManager.doExcetueArrayProc(sqlListArray,objListArray);
        if(bl){
            m.put("result","1");
            m.put("message","添加成功");
        }else{
            m.put("result","0");
            m.put("message","添加失败");
        }
        JSONObject jbStr=JSONObject.fromObject(m);
        response.getWriter().print(jbStr.toString());
    }

    /**
     * 班级课表接口
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=TaskInfo",method= {RequestMethod.POST,RequestMethod.GET})
    public void getTaskInfo(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        String taskid = request.getParameter("taskId");
        String classid = request.getParameter("classId");
        String classtype = request.getParameter("classType");
        String isvir = request.getParameter("isVirtual");
        String userid = request.getParameter("jid");
        String usertype = request.getParameter("userType");
        String schoolid = request.getParameter("schoolId");
        String timestamp = request.getParameter("time");
        String sig = request.getParameter("sign");
        if(!ImUtilTool.ValidateRequestParam(request)){
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> map = new HashMap();
        map.put("taskId",taskid);
        map.put("classId",classid);
        map.put("classType",classtype);
        map.put("isVirtual",isvir);
        map.put("jid",userid);
        map.put("userType",usertype);
        map.put("schoolId",schoolid);
        map.put("time",timestamp);
        String sign = UrlSigUtil.makeSigSimple("TaskInfo",map,"*ETT#HONER#2014*");
        Boolean b = UrlSigUtil.verifySigSimple("TaskInfo",map,sig);
        if(!b){
            response.getWriter().print("{\"result\":\"0\",\"message\":\"验证失败，非法登录\"}");
            return;
        }
        int utype = ImUtilTool.getUserType(usertype);
        UserInfo ui = new UserInfo();
        ui.setEttuserid(Integer.parseInt(userid));
        List<UserInfo> userList = this.userManager.getList(ui,null);
        if(userList==null||userList.size()<1){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前用户未绑定，请联系管理员\"}");
            return;
        }
        TpTaskInfo tpTaskInfo = new TpTaskInfo();
        tpTaskInfo.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo> taskList = this.tpTaskManager.getList(tpTaskInfo,null);
        if(taskList==null||taskList.size()==0){
            response.getWriter().print("{\"result\":\"0\",\"message\":\"当前任务不存在\"}");
            return;
        }
        List<Map<String,Object>> taskinfo = this.imInterfaceManager.getTaskInfo(taskList.get(0).getTaskid(),Integer.parseInt(classid));
        List<Map<String,Object>> returnInfo = new ArrayList<Map<String, Object>>();
        Map returnMap = new HashMap();
        if(taskList.get(0).getTasktype()==1){
            ResourceInfo rs = new ResourceInfo();
            rs.setResid(taskList.get(0).getTaskvalueid());
            List<ResourceInfo> rsList = this.resourceManager.getList(rs,null);
            String attchStr = UtilTool.getResourceLocation(rsList.get(0).getResid(),1)+UtilTool.getResourceMd5Directory(rsList.get(0).getResid().toString())+"/001"+rsList.get(0).getFilesuffixname();
            Map att = new HashMap();
            att.put("attach",attchStr);
            List attList = new ArrayList();
            attList.add(att);
            returnMap.put("attachs", attList);
            returnMap.put("attachType", rsList.get(0).getFilesuffixname());
            returnMap.put("isOver",taskinfo.get(0).get("ISOVER"));
            //拼接tasmname显示任务主体
            String typename = "";
            switch (taskList.get(0).getTasktype()){
                case 1:
                    typename="资源学习";
                    break;
                case 2:
                    typename="互动交流";
                    break;
                case 3:
                    typename="试题";
                    break;
                case 4:
                    typename="成卷测试";
                    break;
                case 5:
                    typename="自主测试";
                    break;
                case 6:
                    typename="微课程学习";
                    break;
                case 7:
                    typename="图片";
                    break;
                case 8:
                    typename="文字";
                    break;
                case 9:
                    typename="视频";
                    break;
            }
            returnMap.put("taskContent", "任务 " + taskList.get(0).getOrderidx() + " " + typename+" "+rsList.get(0).getResname());
            returnMap.put("taskAnalysis",taskinfo.get(0).get("TASKANALYSIS"));
        }else{
            returnMap.put("attachs", taskinfo.get(0).get("ATTACHS"));
            //returnMap.put("attachType", taskinfo.get(0).get("ATTACHTYPE"));
            returnMap.put("isOver",taskinfo.get(0).get("ISOVER"));
            //拼接tasmname显示任务主体
            String typename = "";
            switch (taskList.get(0).getTasktype()){
                case 1:
                    typename="资源学习";
                    break;
                case 2:
                    typename="互动交流";
                    break;
                case 3:
                    typename="试题";
                    break;
                case 4:
                    typename="成卷测试";
                    break;
                case 5:
                    typename="自主测试";
                    break;
                case 6:
                    typename="微课程学习";
                    break;
                case 7:
                    typename="图片";
                    break;
                case 8:
                    typename="文字";
                    break;
                case 9:
                    typename="视频";
                    break;
            }
            returnMap.put("taskContent",taskinfo.get(0).get("TASKCONTENT"));
            returnMap.put("taskAnalysis",taskinfo.get(0).get("TASKANALYSIS"));
        }
        //判断学生是否回答了任务
        Boolean iscomplete =false;
        if(utype!=2){
            TaskPerformanceInfo tf = new TaskPerformanceInfo();
            tf.setTaskid(Long.parseLong(taskid));
            tf.setUserid(userList.get(0).getRef());
            List<TaskPerformanceInfo> pList = this.taskPerformanceManager.getList(tf,null);
            if(pList!=null&&pList.size()>0){
                iscomplete=true;
            }
        }
        if(iscomplete){
            List<Map<String,Object>> taskUserRecord = new ArrayList<Map<String, Object>>();
            List<Map<String,Object>> returnUserRecord = new ArrayList<Map<String, Object>>();
            Map returnUserMap = null;
            if(utype==2){
                taskUserRecord = this.imInterfaceManager.getTaskUserRecord(taskList.get(0).getTaskid(),Integer.parseInt(classid),Integer.parseInt(isvir),null);
            }else{
                taskUserRecord = this.imInterfaceManager.getTaskUserRecord(taskList.get(0).getTaskid(),Integer.parseInt(classid),Integer.parseInt(isvir),userList.get(0).getUserid());
            }
            if(taskUserRecord!=null&&taskUserRecord.size()>0){
                StringBuilder jids = new StringBuilder();
                jids.append("[");
                for(int i = 0;i<taskUserRecord.size();i++){
                    returnUserMap = new HashMap();
                    String replyDate = UtilTool.convertTimeForTask(Integer.parseInt(taskUserRecord.get(i).get("REPLYDATE").toString()),taskUserRecord.get(i).get("C_TIME").toString());
                    returnUserMap.put("replyDate",replyDate);
                    returnUserMap.put("jid",taskUserRecord.get(i).get("JID"));
                    returnUserMap.put("replyDetail",taskUserRecord.get(i).get("REPLYDETAIL"));
                    returnUserMap.put("replyAttach",taskUserRecord.get(i).get("REPLYATTACH"));
                    returnUserMap.put("replyAttachType",taskUserRecord.get(i).get("REPLYATTACHTYPE"));
                    if(taskUserRecord.get(i).get("JID")!=null){
                        jids.append("{\"jid\":"+Integer.parseInt(taskUserRecord.get(i).get("JID").toString())+"},");
                    }else{
                        returnUserMap.put("uPhoto","http://attach.etiantian.com/ett20/study/common/upload/unknown.jpg");
                        returnUserMap.put("uName",taskUserRecord.get(i).get("realname"));
                    }
                   // returnUserMap.put("uPhoto","img");
                   // returnUserMap.put("uName","小虎");
                    returnUserRecord.add(returnUserMap);
                }
                String jidstr = jids.toString().substring(0,jids.toString().lastIndexOf(","))+"]";
                String url="http://192.168.10.26:8008/study-im-service-1.0/queryPhotoAndRealName.do";
                //String url = "http://wangjie.etiantian.com:8080/queryPhotoAndRealName.do";
                HashMap<String,String> signMap = new HashMap();
                signMap.put("userList",jidstr);
                signMap.put("schoolId",schoolid);
                signMap.put("srcJid",userid);
                signMap.put("userType","3");
                signMap.put("timestamp",""+System.currentTimeMillis());
                String signture = UrlSigUtil.makeSigSimple("queryPhotoAndRealName.do",signMap,"*ETT#HONER#2014*");
                signMap.put("sign",signture);
                JSONObject jsonObject = UtilTool.sendPostUrl(url,signMap,"utf-8");
                int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
                if(type==1){
                    Object obj = jsonObject.containsKey("data")?jsonObject.get("data"):null;
                    JSONArray jr = JSONArray.fromObject(obj);
                    if(jr!=null&&jr.size()>0){
                        for(int i = 0;i<jr.size();i++){
                            JSONObject jo = jr.getJSONObject(i);
                            for(int j = 0;j<returnUserRecord.size();j++){
                                returnUserMap = new HashMap();
                                if(jo.getInt("jid")==Integer.parseInt(returnUserRecord.get(j).get("jid").toString())){
                                    returnUserRecord.get(j).put("uPhoto", jo.getString("headUrl"));
                                    returnUserRecord.get(j).put("uName", jo.getString("realName"));
                                }
                            }
                        }
                    }
                }
            }
            returnMap.put("replyList",returnUserRecord);
        }else{
            returnMap.put("replyList",null);
        }
        returnInfo.add(returnMap);
        Map m = new HashMap();
        m.put("result","1");
        m.put("msg","成功");
        m.put("data",returnInfo);
        JSONObject jbStr=JSONObject.fromObject(m);
        response.getWriter().print(jbStr.toString());
    }

    /**
     * 进入任务详情页面（试题选择题和填空题）jsp
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=toQuestionJsp",method={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView toQuestionJsp(HttpServletRequest request, HttpServletResponse response,ModelMap mp) throws Exception {
        //System.out.println(System.currentTimeMillis());
        String taskid = request.getParameter("taskId");
        String classid = request.getParameter("classId");
        String classtype = request.getParameter("classType");
        String isvir = request.getParameter("isVirtual");
        String userid = request.getParameter("jid");
        String usertype = request.getParameter("userType");
        String schoolid = request.getParameter("schoolId");
        String timestamp = request.getParameter("time");
        String sig = request.getParameter("sign");
        if(!ImUtilTool.ValidateRequestParam(request)){
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return new ModelAndView("");
        }
        HashMap<String,String> map = new HashMap();
        map.put("taskId",taskid);
        map.put("classId",classid);
        map.put("classType",classtype);
        map.put("isVirtual",isvir);
        map.put("jid",userid);
        map.put("userType",usertype);
        map.put("schoolId",schoolid);
        map.put("time",timestamp);
        // String sign = UrlSigUtil.makeSigSimple("TaskInfo",map,"*ETT#HONER#2014*");
        Boolean b = UrlSigUtil.verifySigSimple("toQuestionJsp",map,sig);
        if(!b){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"验证失败，非法登录\"}");
            return new ModelAndView("");
        }
        int utype = ImUtilTool.getUserType(usertype);
        UserInfo ui = new UserInfo();
        ui.setEttuserid(Integer.parseInt(userid));
        List<UserInfo> userList = this.userManager.getList(ui, null);
        if(userList==null||userList.size()<1){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前用户未绑定，请联系管理员\"}");
            return new ModelAndView("");
        }
        request.setAttribute("userRef", userList.get(0).getRef());
        TpTaskInfo tpTaskInfo = new TpTaskInfo();
        tpTaskInfo.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo> taskList = this.tpTaskManager.getList(tpTaskInfo,null);
        if(taskList==null||taskList.size()==0){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前任务不存在\"}");
            return new ModelAndView("");
        }
        if(taskList.get(0).getTasktype()==3){
            QuestionInfo questionInfo = new QuestionInfo();
            questionInfo.setQuestionid(taskList.get(0).getTaskvalueid());
            List<QuestionInfo> questionInfoList = this.questionManager.getList(questionInfo,null);
            request.setAttribute("content",questionInfoList.get(0).getContent());
            request.setAttribute("analysis",questionInfoList.get(0).getAnalysis());
            request.setAttribute("currentanswer",questionInfoList.get(0).getCorrectanswer());
            request.setAttribute("quesType",questionInfoList.get(0).getQuestiontype());
            request.setAttribute("quesid",questionInfoList.get(0).getQuestionid());
            if(questionInfoList.get(0).getQuestiontype()==2){
                request.setAttribute("type","填空题");
            }else if(questionInfoList.get(0).getQuestiontype()==3){
                request.setAttribute("type","单选题");
            }else if(questionInfoList.get(0).getQuestiontype()==4){
                request.setAttribute("type","多选题");
            }else{
                request.setAttribute("type","问答题");
            }
            if(questionInfoList.get(0).getQuestiontype()==3||questionInfoList.get(0).getQuestiontype()==4){
                QuestionOption questionOption = new QuestionOption();
                questionOption.setQuestionid(questionInfoList.get(0).getQuestionid());
               // System.out.println("查询试题开始："+System.currentTimeMillis());
                List<QuestionOption> questionOptionList=this.questionOptionManager.getList(questionOption,null);
              //  System.out.println("查询试题结束："+System.currentTimeMillis());
                List<Map<String,Object>> option = new ArrayList<Map<String, Object>>();
                request.setAttribute("option",questionOptionList);
                if(utype==2){
                    List<Map<String,Object>> optionnumList = new ArrayList<Map<String, Object>>();
                    optionnumList = this.taskPerformanceManager.getPerformanceOptionNum(Long.parseLong(taskid),Long.parseLong(classid));
                    //动态拼成想要的选项表分布比例
                    int totalNum = 0;
                    for(int i =0;i<optionnumList.size();i++){
                        totalNum+=Integer.parseInt(optionnumList.get(i).get("NUM").toString());
                    }
                    DecimalFormat di = new DecimalFormat("#.00");
                    for(QuestionOption o:questionOptionList){
                        Map m = new HashMap();
                        if(optionnumList.size()>0){
                            for(int i =0;i<optionnumList.size();i++){
                                if(o.getOptiontype().equals(optionnumList.get(i).get("OPTION_TYPE"))){
                                    m.put("OPTION_TYPE",o.getOptiontype());
                                    m.put("NUM",di.format((double)Integer.parseInt(optionnumList.get(i).get("NUM").toString())/totalNum*100));
                                    m.put("ISRIGHT",o.getIsright());
                                    m.put("CONTENT",o.getContent());
                                    break;
                                }else{
                                    m.put("OPTION_TYPE",o.getOptiontype());
                                    m.put("NUM",0);
                                    m.put("ISRIGHT",o.getIsright());
                                    m.put("CONTENT",o.getContent());
                                }
                            }
                        }else{
                            m.put("OPTION_TYPE",o.getOptiontype());
                            m.put("NUM",0);
                        }
                        option.add(m);
                    }
                    request.setAttribute("optionNum",option);
                }else{
                   // System.out.println("查询用户数据："+System.currentTimeMillis());
                    List<Map<String,Object>> optionnumList = new ArrayList<Map<String, Object>>();
                    optionnumList = this.taskPerformanceManager.getPerformanceOptionNum(Long.parseLong(taskid),Long.parseLong(classid));
                   // System.out.println("查询用户数据结束："+System.currentTimeMillis());
                    int totalNum = 0;
                    for(int i =0;i<optionnumList.size();i++){
                        totalNum+=Integer.parseInt(optionnumList.get(i).get("NUM").toString());
                    }
                    DecimalFormat di = new DecimalFormat("#.00");
                    for(int i = 0;i<questionOptionList.size();i++){
                        if(questionOptionList.get(i).getIsright()==1){
                            for(int j = 0;j<optionnumList.size();j++){
                                if(questionOptionList.get(i).getOptiontype().equals(optionnumList.get(j).get("OPTION_TYPE"))){
                                    request.setAttribute("rightNum",di.format((double)Integer.parseInt(optionnumList.get(j).get("NUM").toString())/totalNum*100));
                                }else{
                                    request.setAttribute("rightNum","0");
                                }
                            }
                        }
                    }
                }
            }
            List<Map<String,Object>> taskUserRecord = new ArrayList<Map<String, Object>>();
            if(utype!=2){
                QuestionAnswer questionAnswer = new QuestionAnswer();
                questionAnswer.setTaskid(Long.parseLong(taskid));
                questionAnswer.setUserid(userList.get(0).getRef());
                List<QuestionAnswer> questionAnswerList=this.questionAnswerManager.getList(questionAnswer,null);
                request.setAttribute("answer",questionAnswerList);
                request.setAttribute("myanswer",questionAnswerList.size()>0?questionAnswerList.get(0).getAnswercontent():null);
            //用户回答列表
                taskUserRecord = this.imInterfaceManager.getTaskUserRecord(taskList.get(0).getTaskid(),Integer.parseInt(classid),Integer.parseInt(isvir),null);
                if(taskUserRecord!=null&&taskUserRecord.size()>0){
                    StringBuilder jids = new StringBuilder();
                    jids.append("[");
                    for(int i = 0;i<taskUserRecord.size();i++){
                        String replyDate = UtilTool.convertTimeForTask(Integer.parseInt(taskUserRecord.get(i).get("REPLYDATE").toString()),taskUserRecord.get(i).get("C_TIME").toString());
                        taskUserRecord.get(i).put("REPLYDATE",replyDate);
                        if(taskUserRecord.get(i).get("JID")!=null){
                            jids.append("{\"jid\":"+Integer.parseInt(taskUserRecord.get(i).get("JID").toString())+"},");
                        }else{
                            taskUserRecord.get(i).put("uPhoto", "http://attach.etiantian.com/ett20/study/common/upload/unknown.jpg");
                            taskUserRecord.get(i).put("uName", taskUserRecord.get(i).get("realname"));
                        }
                    }
                    String jidstr = jids.toString().substring(0,jids.toString().lastIndexOf(","))+"]";
                    String url="http://192.168.10.26:8008/study-im-service-1.0/queryPhotoAndRealName.do";
                    //String url = "http://wangjie.etiantian.com:8080/queryPhotoAndRealName.do";
                    HashMap<String,String> signMap = new HashMap();
                    signMap.put("userList",jidstr);
                    signMap.put("schoolId",schoolid);
                    signMap.put("srcJid",userid);
                    signMap.put("userType","3");
                    signMap.put("timestamp",""+System.currentTimeMillis());
                    String signture = UrlSigUtil.makeSigSimple("queryPhotoAndRealName.do",signMap,"*ETT#HONER#2014*");
                    signMap.put("sign",signture);
                    JSONObject jsonObject = UtilTool.sendPostUrl(url,signMap,"utf-8");
                    int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
                    if(type==1){
                        Object obj = jsonObject.containsKey("data")?jsonObject.get("data"):null;
                        JSONArray jr = JSONArray.fromObject(obj);
                        if(jr!=null&&jr.size()>0){
                            for(int i = 0;i<jr.size();i++){
                                JSONObject jo = jr.getJSONObject(i);
                                for(int j = 0;j<taskUserRecord.size();j++){
                                    if(jo.getInt("jid")==Integer.parseInt(taskUserRecord.get(j).get("JID").toString())){
                                        taskUserRecord.get(j).put("uPhoto",jo.getString("headUrl"));
                                        taskUserRecord.get(j).put("uName",jo.getString("realName"));
                                    }
                                }
                            }
                        }
                    }
                }
                request.setAttribute("userRecord",taskUserRecord);
            }
            request.setAttribute("question",questionInfoList);
            request.setAttribute("taskType",taskList.get(0).getTasktype());
            request.setAttribute("courseid",taskList.get(0).getCourseid());
            request.setAttribute("taskid",taskList.get(0).getTaskid());
            request.setAttribute("usertype",utype);
           // System.out.println(System.currentTimeMillis());
            if(questionInfoList.get(0).getQuestiontype()==1){
                return new ModelAndView("/imjsp-1.1/task-detail-question-wenda");
            }else{
                return new ModelAndView("/imjsp-1.1/task-detail-question");
            }
        }
        return new ModelAndView("");
    }

    /**
     * jsp回答任务
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=doStuSubmitTask",method=RequestMethod.POST)
    public void doStuSubmitTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");
        String tasktype=request.getParameter("tasktype");
        String userRef = request.getParameter("userRef");
        //String groupid=request.getParameter("groupid");
        //资源、论题、课后作业ID
        String quesid=request.getParameter("quesid");
        String questype=request.getParameter("questype");
        if(courseid==null||courseid.trim().length()<1||
                taskid==null||taskid.trim().length()<1||
                tasktype==null||tasktype.trim().length()<1||
                //groupid==null||groupid.trim().length()<1||
                quesid==null||quesid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        //组织批量执行sql的集合
        List<Object>objList=null;
        StringBuilder sql=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();

        //课后作业
        String[]fbanswerArray=request.getParameterValues("fbanswerArray");
        String[]optionArray=request.getParameterValues("optionArray");

        TpTaskInfo t=new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskid));
        t.setCourseid(Long.parseLong(courseid));
        t.setTasktype(Integer.parseInt(tasktype));
        //t.setGroupid(groupid);
        List<TpTaskInfo>taskList=this.tpTaskManager.getList(t,null);
        if(taskList==null||taskList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo tmpTask=taskList.get(0);

        //用得到的参数组织数据并生成存储过程执行
        if(questype.equals("2")){//填空
            if(fbanswerArray==null||fbanswerArray.length<1){
                je.setMsg("异常错误，未获取到问答题答案!");
                response.getWriter().print(je.toJSON());
                return;
            }
            //得到该题教师设置的答案
            QuestionInfo qb=new QuestionInfo();
            qb.setQuestionid(tmpTask.getTaskvalueid());
            List<QuestionInfo>qbList=this.questionManager.getList(qb, null);
            if(qbList==null||qbList.size()<1){
                je.setMsg("异常错误!当前试题已不存在!");
                response.getWriter().print(je.toJSON());
                return;
            }
            boolean fbflag=false;
            String fbanswer=qbList.get(0).getCorrectanswer();
            String[]answerArray=null;
            if(fbanswer!=null&&fbanswer.length()>0){
                answerArray=fbanswer.split("\\|");
                if(answerArray.length<1){
                    je.setMsg("异常错误!未获取到填空题教师设置的答案!");
                    response.getWriter().print(je.toJSON());
                    return;
                }else if(answerArray.length!=fbanswerArray.length){
                    je.setMsg("异常错误!填空题题目或答案有误!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                for (int i = 0; i < answerArray.length; i++) {
                    if(answerArray[i].equals(fbanswerArray[i])){
                        fbflag=true;
                    }else{
                        fbflag=false;
                    }
                }
            }else{
                je.setMsg("异常错误!系统未获取到填空题答案!");
                response.getWriter().print(je.toJSON());
                return;
            }
            //录入答题记录
            String fbstr="";
            for (String s : fbanswerArray) {
                if(fbstr.length()>0)
                    fbstr+="|";
                fbstr+=s;
            }
            QuestionAnswer qa=new QuestionAnswer();
            qa.setTaskid(t.getTaskid());
            qa.setCourseid(t.getCourseid());
            qa.setTasktype(t.getTasktype());
            qa.setQuesparentid(Long.parseLong(quesid));
            qa.setAnswercontent(fbstr);
            qa.setUserid(userRef);
            qa.setRightanswer(fbflag==true?1:0);
            sql=new StringBuilder();
            objList=this.questionAnswerManager.getSaveSql(qa, sql);
            if(objList!=null&&sql!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

            //录入完成状态
            TaskPerformanceInfo tp=new TaskPerformanceInfo();
            tp.setTaskid(t.getTaskid());
            tp.setTasktype(t.getTasktype());
            tp.setCourseid(t.getCourseid());
            tp.setUserid(userRef);
            sql=new StringBuilder();
            tp.setCriteria(tmpTask.getCriteria());
            tp.setIsright(fbflag==true?1:0);
            objList=this.taskPerformanceManager.getSaveSql(tp, sql);
            if(objList!=null&&sql!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }else if(questype.equals("3")){//单选
            if(optionArray==null||optionArray.length<1){
                je.setMsg("异常错误，未获取到选择题答案!");
                response.getWriter().print(je.toJSON());
                return;
            }

            //得到该题教师设置的答案
            QuestionOption qo=new QuestionOption();
            qo.setQuestionid(tmpTask.getTaskvalueid());
            qo.setIsright(1);
            List<QuestionOption>qbList=this.questionOptionManager.getList(qo, null);
            if(qbList==null||qbList.size()<1){
                je.setMsg("异常错误!未获取到教师设置的选择题答案!");
                response.getWriter().print(je.toJSON());
                return;
            }
            boolean selflag=false;
            for (QuestionOption questionBank : qbList) {
                if(questionBank!=null&&questionBank.getRef().toString().equals(optionArray[0])){
                    selflag=true;
                    break;
                }

            }
            //得到当前选项名称
            QuestionOption qstu=new QuestionOption();
            qstu.setRef(Integer.parseInt(optionArray[0]));
            List<QuestionOption>qbstuList=this.questionOptionManager.getList(qstu, null);
            if(qbstuList==null||qbstuList.size()<1){
                je.setMsg("异常错误!当前选项已不存在!");
                response.getWriter().print(je.toJSON());
                return;
            }

            QuestionAnswer qa=new QuestionAnswer();
            qa.setTaskid(t.getTaskid());
            qa.setCourseid(t.getCourseid());
            qa.setTasktype(t.getTasktype());
            qa.setUserid(userRef);
            qa.setQuesparentid(Long.parseLong(quesid));
            qa.setQuesid(Long.parseLong(optionArray[0]));
            qa.setAnswercontent(qbstuList.get(0).getOptiontype());//选择题答案
            qa.setRightanswer(selflag==true?1:0);
            sql=new StringBuilder();
            objList=this.questionAnswerManager.getSaveSql(qa, sql);
            if(objList!=null&&sql!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

            //录入完成状态
            TaskPerformanceInfo tp=new TaskPerformanceInfo();
            tp.setTaskid(t.getTaskid());
            tp.setTasktype(t.getTasktype());
            tp.setCourseid(t.getCourseid());
            tp.setUserid(userRef);
            sql=new StringBuilder();
            tp.setCriteria(tmpTask.getCriteria());
            tp.setIsright(selflag==true?1:0);
            objList=this.taskPerformanceManager.getSaveSql(tp, sql);
            if(objList!=null&&sql!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

        }else if(questype.equals("4")){//多选
            if(optionArray==null||optionArray.length<1){
                je.setMsg("异常错误，未获取到复选题答案!");
                response.getWriter().print(je.toJSON());
                return;
            }

            //得到该题教师设置的答案
            QuestionOption qb=new QuestionOption();
            qb.setQuestionid(tmpTask.getTaskvalueid());
            qb.setIsright(1);
            List<QuestionOption>qbList=this.questionOptionManager.getList(qb, null);
            if(qbList==null||qbList.size()<1){
                je.setMsg("异常错误!未获取到教师设置的复选题答案!");
                response.getWriter().print(je.toJSON());
                return;
            }
            boolean selflag=false;
            int flag=0;
            for(String opnid : optionArray){
                selflag=false;
                for (QuestionOption questionBank : qbList) {
                    if(questionBank!=null&&
                            questionBank.getRef().toString().equals(opnid)){
                        selflag=true;
                        break;
                    }
                }
                if(!selflag)
                    ++flag;

                //得到当前选项名称
                QuestionOption qstu=new QuestionOption();
                qstu.setRef(Integer.parseInt(opnid));
                List<QuestionOption>qbstuList=this.questionOptionManager.getList(qstu, null);
                if(qbstuList==null||qbstuList.size()<1){
                    je.setMsg("异常错误!当前选项已不存在!");
                    response.getWriter().print(je.toJSON());
                    return;
                }

                QuestionAnswer qa=new QuestionAnswer();
                qa.setTaskid(t.getTaskid());
                qa.setCourseid(t.getCourseid());
                qa.setTasktype(t.getTasktype());
                //qa.setGroupid(groupid);
                qa.setUserid(userRef);
                qa.setQuesparentid(Long.parseLong(quesid));
                qa.setQuesid(Long.parseLong(opnid));
                qa.setAnswercontent(qbstuList.get(0).getOptiontype()); //选择题答案
                qa.setRightanswer(selflag==true?1:0);
                sql=new StringBuilder();
                objList=this.questionAnswerManager.getSaveSql(qa, sql);
                if(objList!=null&&sql!=null){
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }
            }

            //录入完成状态
            TaskPerformanceInfo tp=new TaskPerformanceInfo();
            tp.setTaskid(t.getTaskid());
            tp.setTasktype(t.getTasktype());
            tp.setCourseid(t.getCourseid());
            tp.setUserid(userRef);
            tp.setCriteria(tmpTask.getCriteria());
            tp.setIsright(flag<1?1:0);
            sql=new StringBuilder();
            objList=this.taskPerformanceManager.getSaveSql(tp, sql);
            if(objList!=null&&sql!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }
        //执行并返回结果
        if(objListArray.size()>0&&sqlListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlListArray, objListArray);
            if(flag){
                je.setType("success");
            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }else{
            je.setMsg("您的操作没有执行!");
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * 回答任务接口
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=ReplyTask",method={RequestMethod.GET,RequestMethod.POST})
    public void doReplyTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        if(!ImUtilTool.ValidateRequestParam(request)){  //验证参数
//            JSONObject jo=new JSONObject();
//            jo.put("result","0");
//            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
//            jo.put("data","");
//            response.getWriter().print(jo.toString());
//            return;
//        }
        HashMap<String,String> paramMap=ImUtilTool.getRequestParam(request);
        String taskid = paramMap.get("taskId");
        String classid =paramMap.get("classId");
        String isvir =paramMap.get("isVirtual");
        String userid = paramMap.get("jid");
        String usertype = paramMap.get("userType");
        String schoolid = paramMap.get("schoolId");
        String replyDetail =paramMap.get("replyDetail");
        String replyAttach = paramMap.get("replyAttach");
        String attachType = paramMap.get("attachType");
        String timestamp = paramMap.get("time");
        if(replyAttach==null||replyAttach.length()==0){
            paramMap.remove("replyAttach");
            paramMap.remove("attachType");
        }
        String sig = paramMap.get("sign");
        //  String sign = UrlSigUtil.makeSigSimple("TaskInfo",paramMap,"*ETT#HONER#2014*");
        paramMap.remove("sign");
        Boolean b = UrlSigUtil.verifySigSimple("ReplyTask",paramMap,sig);
        if(!b){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"验证失败，非法登录\"}");
            return;
        }
        int utype = ImUtilTool.getUserType(usertype);
        UserInfo ui = new UserInfo();
        ui.setEttuserid(Integer.parseInt(userid));
        List<UserInfo> userList = this.userManager.getList(ui,null);
        if(userList==null||userList.size()<1){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前用户未绑定，请联系管理员\"}");
            return;
        }
        TpTaskInfo tpTaskInfo = new TpTaskInfo();
        tpTaskInfo.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo> taskList = this.tpTaskManager.getList(tpTaskInfo,null);
        if(taskList==null||taskList.size()==0){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前任务不存在，请查看后重试\"}");
            return;
        }
        TpTaskAllotInfo allot = new TpTaskAllotInfo();
        allot.setTaskid(Long.parseLong(taskid));
        allot.setUsertypeid(Long.parseLong(classid));
        List<TpTaskAllotInfo> allotList = this.tpTaskAllotManager.getList(allot,null);
        Long etime = allotList.get(0).getEtime().getTime();
        Long currenttime = System.currentTimeMillis();
        if(etime<currenttime){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前任务已结束，请查看后重试\"}");
            return;
        }
        List<Object>objList=null;
        StringBuilder sql=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        TpTaskInfo tmpTask=taskList.get(0);
        String quesanswer = replyDetail;
        if(tmpTask.getTasktype()==1){
            if(tmpTask.getCriteria()!=null&&tmpTask.getCriteria()==1){//查看标准的，添加完成
                TaskPerformanceInfo tp=new TaskPerformanceInfo();
                tp.setTaskid(taskList.get(0).getTaskid());
                tp.setTasktype(taskList.get(0).getTasktype());
                tp.setCourseid(taskList.get(0).getCourseid());
                tp.setCriteria(1);//查看
                tp.setUserid(userList.get(0).getRef());
                tp.setIsright(1);
                List<TaskPerformanceInfo>tpList=this.taskPerformanceManager.getList(tp,null);
                if(tpList==null||tpList.size()<1){
                    sql=new StringBuilder();
                    objList=this.taskPerformanceManager.getSaveSql(tp,sql);
                    if(sql!=null&&objList!=null){
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                }
            }else{//提交心得标准的，添加回答数据和完成
                QuestionAnswer qa=new QuestionAnswer();
                qa.setCourseid(tmpTask.getCourseid());
                qa.setQuesparentid(tmpTask.getTaskvalueid());
                qa.setQuesid(Long.parseLong("0"));
                qa.setUserid(userList.get(0).getRef());
                qa.setAnswercontent(quesanswer);
                qa.setRightanswer(1);
                qa.setTasktype(tmpTask.getTasktype());
                qa.setTaskid(tmpTask.getTaskid());
                if(replyAttach!=null&&replyAttach.length()>0){
                    qa.setReplyattach(replyAttach);
                    qa.setReplyattachtype(Integer.parseInt(attachType));
                }
                sql=new StringBuilder();
                objList=this.questionAnswerManager.getSaveSql(qa,sql);
                if(sql!=null&&objList!=null){
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }


                if(tmpTask.getCriteria()!=null&&tmpTask.getCriteria()==2){
                    TaskPerformanceInfo tp=new TaskPerformanceInfo();
                    tp.setTaskid(taskList.get(0).getTaskid());
                    tp.setTasktype(taskList.get(0).getTasktype());
                    tp.setCourseid(taskList.get(0).getCourseid());
                    tp.setCriteria(2);//提交心得
                    tp.setUserid(userList.get(0).getRef());
                    tp.setIsright(1);
                    List<TaskPerformanceInfo>tpList=this.taskPerformanceManager.getList(tp,null);
                    if(tpList==null||tpList.size()<1){
                        sql=new StringBuilder();
                        objList=this.taskPerformanceManager.getSaveSql(tp,sql);
                        if(sql!=null&&objList!=null){
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                }else{
                    response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前任务没有提交心得要求\"}");
                    return;
                }
            }
        }else if(tmpTask.getTasktype()==3){
            QuestionAnswer qa=new QuestionAnswer();
            qa.setCourseid(tmpTask.getCourseid());
            qa.setQuesparentid(tmpTask.getTaskvalueid());
            qa.setQuesid(Long.parseLong("0"));
            qa.setUserid(userList.get(0).getRef());
            qa.setAnswercontent(quesanswer);
            qa.setRightanswer(1);
            qa.setTasktype(tmpTask.getTasktype());
            qa.setTaskid(tmpTask.getTaskid());
            if(replyAttach!=null&&replyAttach.length()>0){
                qa.setReplyattach(replyAttach);
                qa.setReplyattachtype(Integer.parseInt(attachType));
            }
            sql=new StringBuilder();
            objList=this.questionAnswerManager.getSaveSql(qa,sql);
            if(sql!=null&&objList!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
            //添加完成情况
            TaskPerformanceInfo tp=new TaskPerformanceInfo();
            tp.setTaskid(taskList.get(0).getTaskid());
            tp.setTasktype(taskList.get(0).getTasktype());
            tp.setCourseid(taskList.get(0).getCourseid());
            tp.setCriteria(1);//提交心得
            tp.setUserid(userList.get(0).getRef());
            tp.setIsright(1);
            List<TaskPerformanceInfo>tpList=this.taskPerformanceManager.getList(tp,null);
            if(tpList==null||tpList.size()<1){
                sql=new StringBuilder();
                objList=this.taskPerformanceManager.getSaveSql(tp,sql);
                if(sql!=null&&objList!=null){
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }
            }
        }else if(tmpTask.getTasktype()==2){
            TaskPerformanceInfo tp=new TaskPerformanceInfo();
            tp.setTaskid(taskList.get(0).getTaskid());
            tp.setTasktype(taskList.get(0).getTasktype());
            tp.setCourseid(taskList.get(0).getCourseid());
            tp.setCriteria(1);//查看
            tp.setUserid(userList.get(0).getRef());
            tp.setIsright(1);
            List<TaskPerformanceInfo>tpList=this.taskPerformanceManager.getList(tp,null);
            if(tpList==null||tpList.size()<1){
                sql=new StringBuilder();
                objList=this.taskPerformanceManager.getSaveSql(tp,sql);
                if(sql!=null&&objList!=null){
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }
            }
        }else{
            if(tmpTask.getCriteria()!=null&&tmpTask.getCriteria()==1){//查看标准的，添加完成
                TaskPerformanceInfo tp=new TaskPerformanceInfo();
                tp.setTaskid(taskList.get(0).getTaskid());
                tp.setTasktype(taskList.get(0).getTasktype());
                tp.setCourseid(taskList.get(0).getCourseid());
                tp.setCriteria(1);//查看
                tp.setUserid(userList.get(0).getRef());
                tp.setIsright(1);
                List<TaskPerformanceInfo>tpList=this.taskPerformanceManager.getList(tp,null);
                if(tpList==null||tpList.size()<1){
                    sql=new StringBuilder();
                    objList=this.taskPerformanceManager.getSaveSql(tp,sql);
                    if(sql!=null&&objList!=null){
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                }
            }else{
                QuestionAnswer qa=new QuestionAnswer();
                qa.setCourseid(tmpTask.getCourseid());
                qa.setQuesparentid(tmpTask.getTaskvalueid());
                qa.setQuesid(Long.parseLong("0"));
                qa.setUserid(userList.get(0).getRef());
                qa.setAnswercontent(quesanswer);
                qa.setRightanswer(1);
                qa.setTasktype(tmpTask.getTasktype());
                qa.setTaskid(tmpTask.getTaskid());
                if(replyAttach!=null&&replyAttach.length()>0){
                    qa.setReplyattach(replyAttach);
                    qa.setReplyattachtype(Integer.parseInt(attachType));
                }
                sql=new StringBuilder();
                objList=this.questionAnswerManager.getSaveSql(qa,sql);
                if(sql!=null&&objList!=null){
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }

                if(tmpTask.getCriteria()!=null&&tmpTask.getCriteria()==2){
                    TaskPerformanceInfo tp=new TaskPerformanceInfo();
                    tp.setTaskid(taskList.get(0).getTaskid());
                    tp.setTasktype(taskList.get(0).getTasktype());
                    tp.setCourseid(taskList.get(0).getCourseid());
                    tp.setCriteria(2);//提交心得
                    tp.setUserid(userList.get(0).getRef());
                    tp.setIsright(1);
                    List<TaskPerformanceInfo>tpList=this.taskPerformanceManager.getList(tp,null);
                    if(tpList==null||tpList.size()<1){
                        sql=new StringBuilder();
                        objList=this.taskPerformanceManager.getSaveSql(tp,sql);
                        if(sql!=null&&objList!=null){
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                }else{
                    response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前任务没有提交心得要求\"}");
                    return;
                }
            }
        }
        //执行并返回结果
        if(objListArray.size()>0&&sqlListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlListArray, objListArray);
            if(flag){
                JSONObject jo = new JSONObject();
                jo.put("result","1");
                jo.put("msg","回答完成");
                if(tmpTask.getCriteria()!=null&&tmpTask.getCriteria()==2){//提交标准的返回回答列表
                    List<Map<String,Object>> returnUserRecord = new ArrayList<Map<String, Object>>();
                    Map returnUserMap =null;
                    List<Map<String,Object>> taskUserRecord = this.imInterfaceManager.getTaskUserRecord(taskList.get(0).getTaskid(),Integer.parseInt(classid),Integer.parseInt(isvir),userList.get(0).getUserid());
                    if(taskUserRecord!=null&&taskUserRecord.size()>0){
                        StringBuilder jids = new StringBuilder();
                        jids.append("[");
                        for(int i = 0;i<taskUserRecord.size();i++){
                            returnUserMap = new HashMap();
                            String replyDate = UtilTool.convertTimeForTask(Integer.parseInt(taskUserRecord.get(i).get("REPLYDATE").toString()),taskUserRecord.get(i).get("C_TIME").toString());
                            returnUserMap.put("replyDate",replyDate);
                            returnUserMap.put("jid",taskUserRecord.get(i).get("JID"));
                            returnUserMap.put("replyDetail",taskUserRecord.get(i).get("REPLYDETAIL"));
                            returnUserMap.put("replyAttach",taskUserRecord.get(i).get("REPLYATTACH"));
                            returnUserMap.put("replyAttachType",taskUserRecord.get(i).get("REPLYATTACHTYPE"));
                            if(taskUserRecord.get(i).get("JID")!=null){
                                jids.append("{\"jid\":"+Integer.parseInt(taskUserRecord.get(i).get("JID").toString())+"},");
                            }else{
                                returnUserMap.put("uPhoto","http://attach.etiantian.com/ett20/study/common/upload/unknown.jpg");
                                returnUserMap.put("uName",taskUserRecord.get(i).get("realname"));
                            }
                            // returnUserMap.put("uPhoto","img");
                            // returnUserMap.put("uName","小虎");
                            returnUserRecord.add(returnUserMap);
                        }
                        String jidstr = jids.toString().substring(0,jids.toString().lastIndexOf(","))+"]";
                        String url="http://192.168.10.26:8008/study-im-service-1.0/queryPhotoAndRealName.do";
                        //String url = "http://wangjie.etiantian.com:8080/queryPhotoAndRealName.do";
                        HashMap<String,String> signMap = new HashMap();
                        signMap.put("userList",jidstr);
                        signMap.put("schoolId",schoolid);
                        signMap.put("srcJid",userid);
                        signMap.put("userType","3");
                        signMap.put("timestamp",""+System.currentTimeMillis());
                        String signture = UrlSigUtil.makeSigSimple("queryPhotoAndRealName.do",signMap,"*ETT#HONER#2014*");
                        signMap.put("sign",signture);
                        JSONObject jsonObject = UtilTool.sendPostUrl(url,signMap,"utf-8");
                        int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
                        if(type==1){
                            Object obj = jsonObject.containsKey("data")?jsonObject.get("data"):null;
                            JSONArray jr = JSONArray.fromObject(obj);
                            if(jr!=null&&jr.size()>0){
                                for(int i = 0;i<jr.size();i++){
                                    JSONObject jObject = jr.getJSONObject(i);
                                    for(int j = 0;j<returnUserRecord.size();j++){
                                        returnUserMap = new HashMap();
                                        if(jObject.getInt("jid")==Integer.parseInt(returnUserRecord.get(j).get("jid").toString())){
                                            returnUserRecord.get(j).put("uPhoto", jObject.getString("headUrl"));
                                            returnUserRecord.get(j).put("uName", jObject.getString("realName"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Map m = new HashMap();
                    m.put("replyList",returnUserRecord);
                    jo.put("data",m);
                }
                response.getWriter().print(jo.toString());
            }else{
                response.getWriter().print("{\"result\":\"0\",\"msg\":\"回答失败\"}");
                return;
            }
        }else{
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"操作没有执行\"}");
            return;
        }
    }

    /**
     * 回答任务接口(互动交流类)
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=StuPost",method={RequestMethod.GET,RequestMethod.POST})
    public void doReplyTopic(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject returnJo=new JSONObject();
        returnJo.put("result","0");//默认失败
//        if(!ImUtilTool.ValidateRequestParam(request)){  //验证参数
//            JSONObject jo=new JSONObject();
//            jo.put("result","0");
//            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
//            jo.put("data","");
//            response.getWriter().print(jo.toString());
//            return;
//        }
        HashMap<String,String> paramMap=ImUtilTool.getRequestParam(request);
        String taskId = paramMap.get("taskId");
        String classid = paramMap.get("classId");
        String userid = paramMap.get("jid");
        String usertype = paramMap.get("userType");
        String schoolid =paramMap.get("schoolId");
        String themeTitle =paramMap.get("themeTitle");
        String themeContent = paramMap.get("themeContent");
        String replyAttach = paramMap.get("replyAttach");
        String attachType = paramMap.get("attachType");
        String sig = request.getParameter("sign");
        //String sign = UrlSigUtil.makeSigSimple("TaskInfo",paramMap,"*ETT#HONER#2014*");
        //验证，首先去掉sign，在进行md5验证
        if(replyAttach==null||replyAttach.length()==0){
            paramMap.remove("replyAttach");
            paramMap.remove("attachType");
        }
        paramMap.remove("sign");
        if(replyAttach!=null&&replyAttach.length()==0){
            paramMap.remove("replyAttach");
            paramMap.remove("attachType");
        }
        Boolean b = UrlSigUtil.verifySigSimple("StuPost",paramMap,sig);
        if(!b){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"验证失败，非法登录\"}");
            return;
        }
        int utype = ImUtilTool.getUserType(usertype);
        UserInfo ui = new UserInfo();
        ui.setEttuserid(Integer.parseInt(userid));
        List<UserInfo> userList = this.userManager.getList(ui,null);
        if(userList==null||userList.size()<1){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前用户未绑定，请联系管理员\"}");
            return;
        }
        //验证当前任务，并得到论题id
        TpTaskInfo t = new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskId));
        List<TpTaskInfo> tList = this.tpTaskManager.getList(t,null);
        if(tList==null&&tList.size()==0){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前任务不存在，请联系管理员\"}");
            return;
        }
        TpTaskAllotInfo allot = new TpTaskAllotInfo();
        allot.setTaskid(Long.parseLong(taskId));
        allot.setUsertypeid(Long.parseLong(classid));
        List<TpTaskAllotInfo> allotList = this.tpTaskAllotManager.getList(allot,null);
        Long etime = allotList.get(0).getEtime().getTime();
        Long currenttime = System.currentTimeMillis();
        if(etime<currenttime){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前任务已结束，请查看后重试\"}");
            return;
        }
        Long topicId = tList.get(0).getTaskvalueid();
        TpTopicInfo ti = new TpTopicInfo();
        ti.setTopicid(topicId);
        ti.setSelectType(2);/*查询类型  1:status<>3   2:不连接被删除的 */
        List<TpTopicInfo> tiList  = this.tpTopicManager.getList(ti,null);
        if(tiList==null||tiList.size()==0){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前论题不存在，请查看后重试\"}");
            return;
        }
        List<Object>objList=null;
        StringBuilder sql=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        //添加数据
        TpTopicThemeInfo topictheme = new TpTopicThemeInfo();
        Long themenextid=this.tpTopicThemeManager.getNextId(true);
        topictheme.setThemeid(themenextid);
        topictheme.setThemetitle(themeTitle);
        topictheme.setThemecontent(themeContent);
        if(replyAttach!=null&&replyAttach.length()>0){
            topictheme.setImattach(replyAttach);
            topictheme.setImattachtype(Integer.parseInt(attachType));
        }
        topictheme.setSourceid(1);
        topictheme.setTopicid(tiList.get(0).getTopicid());
        topictheme.setCourseid(tiList.get(0).getCourseid());
        topictheme.setViewcount(0);
        topictheme.setCuserid(userList.get(0).getUserid());
        topictheme.setIsessence(0);     //是否精华
        topictheme.setIstop(0);         //是否置顶
        topictheme.setCloudstatus(-1);  //上传云端 -1:未上传
        Boolean bool = false;
        //bool = this.tpTopicThemeManager.doSave(topictheme);
        sql = new StringBuilder();
        objList = new ArrayList<Object>();
        objList = this.tpTopicThemeManager.getSaveSql(topictheme,sql);
        sqlListArray.add(sql.toString());
        objListArray.add(objList);
        //更新程序
        if(topictheme.getThemecontent()!=null&&topictheme.getThemecontent().trim().length()>0){
            //得到theme_content的更新语句
            this.tpTopicThemeManager.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "theme_content"
                    , topictheme.getThemecontent(), topictheme.getThemeid().toString(), sqlListArray, objListArray);
        }
        JSONObject jo = new JSONObject();
        //添加到任务完成
        TaskPerformanceInfo tp=new TaskPerformanceInfo();
        tp.setTaskid(tList.get(0).getTaskid());
        tp.setTasktype(tList.get(0).getTasktype());
        tp.setCourseid(tList.get(0).getCourseid());
        //tp.getTaskinfo().setGroupid(gsList.get(0).getGroupid());
        tp.setCriteria(2);//发主题
        tp.setUserid(userList.get(0).getRef());
        tp.setIsright(1);
        List<TaskPerformanceInfo>tpList=this.taskPerformanceManager.getList(tp,null);
        if(tpList==null||tpList.size()<1){
            sql = new StringBuilder();
            objList = new ArrayList<Object>();
            objList = this.taskPerformanceManager.getSaveSql(tp,sql);
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }
        bool = this.imInterfaceManager.doExcetueArrayProc(sqlListArray,objListArray);
        if(bool){
            jo.put("result", "1");
            jo.put("msg","添加成功");
        }else{
            jo.put("result","0");
            jo.put("msg","添加失败");
        }
        response.getWriter().print(jo.toString());
    }

    /**
     * 进入论题详情页面jsp
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=toTopicJsp",method={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView toTopicJsp(HttpServletRequest request, HttpServletResponse response,ModelMap mp) throws Exception {
        JsonEntity je = new JsonEntity();
        if(!ImUtilTool.ValidateRequestParam(request)){  //验证参数
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return null;
        }
        HashMap<String,String> paramMap=ImUtilTool.getRequestParam(request);
        String taskId = paramMap.get("taskId");
        String userid = paramMap.get("jid");
        String classid = paramMap.get("classId");
        String classtype = paramMap.get("classtype");
        String isVirtual = paramMap.get("isVirtual");
        String usertype = paramMap.get("userType");
        String schoolid =paramMap.get("schoolId");
        String sig = request.getParameter("sign");
        //String sign = UrlSigUtil.makeSigSimple("TaskInfo",paramMap,"*ETT#HONER#2014*");
        //验证，首先去掉sign，在进行md5验证
        paramMap.remove("sign");
        Boolean b = UrlSigUtil.verifySigSimple("toTopicJsp",paramMap,sig);
        if(!b){
            je.setMsg("验证失败，非法登录");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        int utype = ImUtilTool.getUserType(usertype);
        UserInfo ui = new UserInfo();
        ui.setEttuserid(Integer.parseInt(userid));
        List<UserInfo> userList = this.userManager.getList(ui,null);
        if(userList==null||userList.size()<1){
            je.setMsg("当前用户未绑定");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //验证当前任务，并得到论题id
        TpTaskInfo task = new TpTaskInfo();
        task.setTaskid(Long.parseLong(taskId));
        List<TpTaskInfo> taskList = this.tpTaskManager.getList(task,null);
        if(taskList==null&&taskList.size()==0){
            je.setMsg("当前任务不存在");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        Long topicId = taskList.get(0).getTaskvalueid();
        TpTopicInfo ti = new TpTopicInfo();
        ti.setTopicid(topicId);
        ti.setSelectType(2);/*查询类型  1:status<>3   2:不连接被删除的 */
        List<TpTopicInfo> tiList  = this.tpTopicManager.getList(ti,null);
        if(tiList==null||tiList.size()==0){
            je.setMsg("当前论题不存在");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //接下来查询论题下的主题
        List<Map<String,Object>> themeList = this.imInterfaceManager.getTopicUserRecord(topicId,Integer.parseInt(classid),Integer.parseInt(isVirtual),userList.get(0).getUserid());
        request.setAttribute("topic",tiList.get(0));
        request.setAttribute("themeList",themeList);
        return new ModelAndView("/imjsp-1.1/topic-detail");
    }



    /**
     *  添加课程纪实接口
     *    jid：用户id
     *    schoolId：schoolId
     *    classId：班级id
     *    courseId：课程id
     *    attachDescribe：附件描述
     *    attach：附件地址字符串（JSON串）
     *    time:时间戳
     *    sign:Md5串
     *
     * @param request
     * @param response
     */
    @RequestMapping(params="m=AddCourseRecord",method={RequestMethod.GET,RequestMethod.POST})
    public void AddCourseRecord(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject returnJo=new JSONObject();
        returnJo.put("result","0");//默认失败
        if(!ImUtilTool.ValidateRequestParam(request)){  //验证参数
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> paramMap=ImUtilTool.getRequestParam(request);
        //获取参数
        String jid=paramMap.get("jid");
        String schoolId=paramMap.get("schoolId");
        String classId=paramMap.get("classId");
        String courseId=paramMap.get("courseId");
        String content=paramMap.get("attachDescribe");
        String attach=paramMap.get("attach");
        String time=paramMap.get("time");
        String sign=paramMap.get("sign");
        if(jid==null||schoolId==null||classId==null||time==null||sign==null){
            returnJo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(returnJo.toString());return;
        }
        //验证时间
        //验证是否在三分钟内
        Long ct=Long.parseLong(time.toString());
        Long nt=new Date().getTime();
        double d=(nt-ct)/(1000*60);
        if(d>3){//大于三分钟
            returnJo.put("msg","异常错误，响应超时!接口三分钟内有效!");
            response.getWriter().print(returnJo.toString());
            return;
        }

        //去除sign
        paramMap.remove("sign");
        //验证Md5
        Boolean b = UrlSigUtil.verifySigSimple("AddCourseRecord",paramMap,sign);
        if(!b){
            returnJo.put("msg","验证失败，非法登录!");
            response.getWriter().print(returnJo.toString());
            return;
        }

        //验证用户ETTUserId,得到Userid
        UserInfo validateUid=new UserInfo();
        validateUid.setEttuserid(Integer.parseInt(jid.trim()));
        List<UserInfo> userList=this.userManager.getList(validateUid,null);
        if(userList==null||userList.size()<1||userList.get(0)==null||userList.get(0).getUserid()==null){
            returnJo.put("msg","当前云帐号未绑定!");
            response.getWriter().println(returnJo.toString());return;
        }
        Integer userid=userList.get(0).getUserid();

        TpRecordInfo tpRecordInfo=new TpRecordInfo();
        tpRecordInfo.setUserId(Long.parseLong(userid.toString()));
        tpRecordInfo.setClassId(Long.parseLong(classId.trim()));
        tpRecordInfo.setCourseId(Long.parseLong(courseId.trim()));

        tpRecordInfo.setDcSchoolId(Long.parseLong(schoolId.trim()));
                 //查询是否已经存在
//        List<TpRecordInfo> tpRecordInfoList=this.tpRecordManager.getList(tpRecordInfo,null);
//        if(tpRecordInfoList!=null&&tpRecordInfoList.size()>0){
//            returnJo.put("msg","添加失败!已经存在该信息!");
//            response.getWriter().println(returnJo.toString());return;
//        }
        if(attach!=null)
            tpRecordInfo.setImgUrl(attach.trim());
        if(content!=null)
            tpRecordInfo.setContent(content.trim());
        //添加
        if(this.tpRecordManager.doSave(tpRecordInfo)){
            returnJo.put("result","1");//成功
            returnJo.put("msg","添加课堂纪实成功!");
        }else
            returnJo.put("msg",UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().println(returnJo.toString());
    }


    /**
     * 删除课程纪实接口
     *     jid:用户J Id
     *     schoolId: 分校ID
     *     classId:班级Id
     *     recordId:纪实ID(唯一标识ref)
     *     sign: Md5串
     *     time:时间戳
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=DeleteRecord",method={RequestMethod.GET,RequestMethod.POST})
    public void DelCourseRecord(HttpServletRequest request,HttpServletResponse response) throws  Exception{
        JSONObject returnJo=new JSONObject();
        returnJo.put("result","0");//默认失败
        if(!ImUtilTool.ValidateRequestParam(request)){  //验证参数
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> paramMap=ImUtilTool.getRequestParam(request);
        //获取参数
        String jid=paramMap.get("jid");
        String schoolId=paramMap.get("schoolId");
        String classId=paramMap.get("classId");
        String recordId=paramMap.get("recordId");
        String time=paramMap.get("time");
        String sign=paramMap.get("sign");
        if(jid==null||recordId==null||time==null||sign==null){
            returnJo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(returnJo.toString());return;
        }
        //验证时间
        //验证是否在三分钟内
        Long ct=Long.parseLong(time.toString());
        Long nt=new Date().getTime();
        double d=(nt-ct)/(1000*60);
        if(d>3){//大于三分钟
            returnJo.put("msg","异常错误，响应超时!接口三分钟内有效!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        //去除sign
        paramMap.remove("sign");
        //验证Md5
        Boolean b = UrlSigUtil.verifySigSimple("DeleteRecord",paramMap,sign);
        if(!b){
            returnJo.put("msg","验证失败，非法登录!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        //验证该jid是否已经绑定
        //验证用户ETTUserId,得到Userid
        UserInfo validateUid=new UserInfo();
        validateUid.setEttuserid(Integer.parseInt(jid.trim()));
        List<UserInfo> userList=this.userManager.getList(validateUid,null);
        if(userList==null||userList.size()<1||userList.get(0)==null||userList.get(0).getUserid()==null){
            returnJo.put("msg","当前云帐号未绑定!");
            response.getWriter().println(returnJo.toString());return;
        }
        Integer userid=userList.get(0).getUserid();
        //验证是否存在该记录
        TpRecordInfo tpRecordInfo=new TpRecordInfo();
        tpRecordInfo.setRef(Integer.parseInt(recordId));
        List<TpRecordInfo> recordList=this.tpRecordManager.getList(tpRecordInfo,null);
        if(recordList==null||recordList.size()<1){
            returnJo.put("msg","当前数据不存在，无需删除!");
            response.getWriter().println(returnJo.toString());return;
        }
        if(this.tpRecordManager.doDelete(tpRecordInfo)){
            returnJo.put("result","1");//成功
            returnJo.put("msg","删除课堂纪实成功!");
        }else
            returnJo.put("msg",UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().println(returnJo.toString());
    }

    /**
     * 查询课程纪实接口
     *   classId:班级id
     *   schoolId:schoolId
     *   jid:用户id
     *   userType:用户类型
     *   courseId: 课程id
     *   time:毫秒数，时间戳
     *   sign:Md5验证码
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=GetClassRecord",method={RequestMethod.GET,RequestMethod.POST})
    public void GetClassRecord(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject returnJo=new JSONObject();
        returnJo.put("result","0");//默认失败
        if(!ImUtilTool.ValidateRequestParam(request)){  //验证参数
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> paramMap=ImUtilTool.getRequestParam(request);
        //获取参数
        String jid=paramMap.get("jid");
        String schoolId=paramMap.get("schoolId");
        String classId=paramMap.get("classId");
        String userType=paramMap.get("userType");
        String courseId=paramMap.get("courseId");
        String time=paramMap.get("time");
        String sign=paramMap.get("sign");
        if(jid==null||schoolId==null||time==null||sign==null||classId==null||courseId==null){
            returnJo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(returnJo.toString());return;
        }
        //验证时间
        //验证是否在三分钟内
        Long ct=Long.parseLong(time.toString());
        Long nt=new Date().getTime();
        double d=(nt-ct)/(1000*60);
        if(d>3){//大于三分钟
            returnJo.put("msg","异常错误，响应超时!接口三分钟内有效!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        //去除sign
        paramMap.remove("sign");
        //验证Md5
        Boolean b = UrlSigUtil.verifySigSimple("GetClassRecord",paramMap,sign);
        if(!b){
            returnJo.put("msg","验证失败，非法登录!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        //验证该jid是否已经绑定
        //验证用户ETTUserId,得到Userid
        UserInfo validateUid=new UserInfo();
        validateUid.setEttuserid(Integer.parseInt(jid.trim()));
        List<UserInfo> userList=this.userManager.getList(validateUid,null);
        if(userList==null||userList.size()<1||userList.get(0)==null||userList.get(0).getUserid()==null){
            returnJo.put("msg","当前云帐号未绑定!");
            response.getWriter().println(returnJo.toString());return;
        }
        Integer userid=userList.get(0).getUserid();
        //查询数据
        TpRecordInfo entity=new TpRecordInfo();
        entity.setUserId(Long.parseLong(userid.toString()));
        entity.setDcSchoolId(Long.parseLong(schoolId.trim()));
        entity.setClassId(Long.parseLong(classId.trim()));
        entity.setCourseId(Long.parseLong(courseId.trim()));
        List<TpRecordInfo> recordList=this.tpRecordManager.getList(entity,null);
        List<Map<String,Object>> returnMapList=new ArrayList<Map<String, Object>>();
        JSONObject jo=new JSONObject();
        if(recordList==null||recordList.size()<1){
            jo.put("recordList",returnMapList);

            returnJo.put("msg","没有数据!");
            returnJo.put("data",jo.toString());
            returnJo.put("result","1");
            response.getWriter().println(returnJo.toString());return;
        }
        for (TpRecordInfo tEntity:recordList){
            if(tEntity!=null){
                Map<String,Object> tmpMap=new HashMap<String, Object>();
                tmpMap.put("recordId",tEntity.getRef());
                tmpMap.put("attachDescribe",tEntity.getContent());
                tmpMap.put("attach",tEntity.getImgUrl());
                returnMapList.add(tmpMap);
            }
        }

        jo.put("recordList",returnMapList);
        returnJo.put("data",jo.toString());
        returnJo.put("result","1");
        response.getWriter().print(returnJo.toString());
    }

    /**
     *
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=testDetail",method={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView imToPaperDetail(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        if(!ImUtilTool.ValidateRequestParam(request)){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());
            return null;
        }
        HashMap<String,String> paramMap=ImUtilTool.getRequestParam(request);
        String userid=paramMap.get("userid");
        String taskid=paramMap.get("taskid");
        String courseid=paramMap.get("courseid");
        String paperid=paramMap.get("paperid");
        String quesid=paramMap.get("quesId");
        String classid=paramMap.get("classid");
        String userType=paramMap.get("userType");
        if(userid==null||taskid==null||courseid==null||paperid==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());
            return null;
        }
        //查询用户是否已经交卷
        //验证paperid是否存在
        PaperInfo paperInfo=new PaperInfo();
        paperInfo.setPaperid(Long.parseLong(paperid.trim()));
        List<PaperInfo> paperList=this.paperManager.getList(paperInfo,null);
        if(paperList==null||paperList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        Integer uid=Integer.parseInt(userid.trim());

        boolean isendTask=false;
        List<TpTaskInfo> taskList=null;
            PageResult pr=new PageResult();
            pr.setPageSize(1);

            TpTaskInfo tk=new TpTaskInfo();
            tk.setTaskid(Long.parseLong(taskid));
            List<TpTaskInfo> tkList=this.tpTaskManager.getList(tk,pr);
            if(tkList==null||tkList.size()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
            }

            TpTaskInfo t=new TpTaskInfo();
            t.setUserid(uid);
            t.setTaskid(Long.parseLong(taskid));
            t.setCourseid(tkList.get(0).getCourseid());
            pr.setPageSize(1);
            // 学生任务
            taskList=this.tpTaskManager.getListbyStu(t, pr);
            if(taskList==null||taskList.size()<1||taskList.get(0).getBtime()==null||taskList.get(0).getEtime()==null){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
            }
            if(taskList.get(0).getTaskstatus().equals("3")){
                isendTask=true;
            }

        if(taskList==null||taskList.size()<1||taskList.get(0).getBtime()==null||taskList.get(0).getEtime()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }

        //验证是否已经答题
        if(userType!=null&&Integer.parseInt(userType)!=2){  //如果是老师进入，则不验证是否提交
            StuPaperLogs splog=new StuPaperLogs();
            splog.setUserid(uid);
            splog.setPaperid(Long.parseLong(paperid));
            splog.setTaskid(Long.parseLong(taskid.trim()));
            splog.setIsinpaper(2);
            pr=new PageResult();
            pr.setPageSize(1);
            List<StuPaperLogs> spList=stuPaperLogsManager.getList(splog,pr);
            if(spList==null||spList.size()<1){
                if(Integer.parseInt(userType)!=3){
                    jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                    response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
                }
            }else
                userType="1"; //如果存在记录，则直接显示
        }
        //如果任务还没结束，则添加相关信息

        //得到当前的所有问题
        List<Map<String,Object>> listMapStr=this.paperQuestionManager.getPaperQuesAllId(Long.parseLong(paperid));
        if(listMapStr==null||listMapStr.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
        }
        Object allquesidObj=listMapStr.get(0).get("ALLQUESID");
        if(allquesidObj==null||allquesidObj.toString().trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
        }

        //验证当前题是否在allquesidObj中
        if(quesid!=null&&quesid.trim().length()>0){
            boolean isHasQues=false;
            String[] quesObjArray=allquesidObj.toString().split(",");
            if(quesObjArray.length>0){
                for (String qid:quesObjArray){
                    if(qid!=null&&qid.trim().length()>0){
                        String tmpid=qid;
                        if(tmpid.trim().indexOf("|")!=-1){
                            String[] tArray=tmpid.trim().split("\\|");
                            if(tArray.length>1){
                                tmpid=tArray[1];
                            }
                        }
                        if(tmpid.trim().equals(quesid.trim())){
                            isHasQues=!isHasQues;
                            break;
                        }
                    }
                }
            }
            if(!isHasQues){
                jsonEntity.setMsg("该试卷中不存在该题!题标识："+quesid);
                response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
            }
            mp.put("quesid",quesid);

        }
        //加载分数
//        List<Map<String,Object>> scoreMapList=this.paperQuestionManager.getPaperQuesAllScore(Long.parseLong(paperid.trim()),null,taskList.get(0).getCourseid());
//        if(scoreMapList==null||scoreMapList.size()<1||!scoreMapList.get(0).containsKey("SCORE")||scoreMapList.get(0).get("SCORE")==null){
//            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
//            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
//        }
        tk=taskList.get(0);
        //得到年有题的分数
        mp.put("userType",userType);
        mp.put("classid",classid);
       // mp.put("allquesidObj",allquesidObj);
        mp.put("paperObj",paperList.get(0));
        mp.put("taskid",taskid);
        mp.put("courseid",tk.getCourseid());
        mp.put("allquesidObj",allquesidObj);
        mp.put("quesSize",allquesidObj.toString().split(",").length);
        mp.put("paperid",paperid);
        mp.put("userid",userid);
        return new ModelAndView("/imjsp-1.1/test/testdetail",mp);
    }

    /**
     * IM手机进入试卷测试页面。
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=toTestPaper",method={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView imToTestPaper(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
//        if(!ImUtilTool.ValidateRequestParam(request)){
//            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
//            response.getWriter().println(jsonEntity.getAlertMsgAndBack());
//            return null;
//        }
        HashMap<String,String> paramMap=ImUtilTool.getRequestParam(request);
        String jid=paramMap.get("jid");
        String taskid=paramMap.get("taskId");
        String schoolid=paramMap.get("schoolId");
        String clsid=paramMap.get("classId");
        String paperid=paramMap.get("paperId");
        String userType=paramMap.get("userType");
        String quesid=paramMap.get("quesId");
        String sign=paramMap.get("sign");
        String time=paramMap.get("time");
        paramMap.remove("sign");

        if(userType==null||jid==null||taskid==null||time==null||sign==null||schoolid==null||clsid==null||paperid==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
        }
        //验证时间
        //验证是否在三分钟内
        Long ct=Long.parseLong(time.toString());
        Long nt=new Date().getTime();
        double d=(nt-ct)/(1000*60);
        if(d>3){//大于三分钟
            jsonEntity.setMsg("异常错误，响应超时!接口三分钟内有效!");
            response.getWriter().print(jsonEntity.getAlertMsgAndBack());
            return null;
        }
        //去除sign
        paramMap.remove("sign");
        //验证Md5
        Boolean b = UrlSigUtil.verifySigSimple("toTestPaper",paramMap,sign);
        if(!b){
            jsonEntity.setMsg("验证失败，非法登录!");
            response.getWriter().print(jsonEntity.getAlertMsgAndBack());
            return null;
        }
        //验证该jid是否已经绑定
        //验证用户ETTUserId,得到Userid
        UserInfo validateUid=new UserInfo();
        validateUid.setEttuserid(Integer.parseInt(jid.trim()));
        List<UserInfo> userList=this.userManager.getList(validateUid,null);
        if(userList==null||userList.size()<1||userList.get(0)==null||userList.get(0).getUserid()==null){
            jsonEntity.setMsg("当前云帐号未绑定!");
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
        }
        Integer userid=userList.get(0).getUserid();
        //得到任务信息
        TpTaskInfo tk=new TpTaskInfo();
        tk.setTaskid(Long.parseLong(taskid.trim()));
        List<TpTaskInfo> tkList=this.tpTaskManager.getList(tk,null);
        if(tkList==null||tkList.size()<1){
            jsonEntity.setMsg("当前任务不存在!");
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
        }
        tk=tkList.get(0);

        PageResult presult=new PageResult();
        presult.setPageSize(1);

        //验证该用户是否已经提交过答卷
        StuPaperLogs splogs=new StuPaperLogs();
        splogs.setUserid(userid);
        splogs.setTaskid(tk.getTaskid());
        splogs.setPaperid(Long.parseLong(paperid.trim()));
        List<StuPaperLogs> splogsList=this.stuPaperLogsManager.getList(splogs,presult);


        Integer uType=ImUtilTool.getUserType(userType);
        if(uType==2||uType==3){ //如果是老师，直接进入详情页面

            //如果是跳题，则直接跳
            StringBuilder directBuilder=new StringBuilder("imapi1_1?m=testDetail&userid=")
                    .append(userid).append("&taskid=").append(tk.getTaskid())
                    .append("&paperid=").append(paperid).append("&courseid=").append(tk.getCourseid())
                    .append("&classid=").append(clsid).append("&userType=").append(uType);
            if(quesid!=null&&quesid.trim().length()>0){
                directBuilder.append("&quesId=").append(quesid);
            }
            response.sendRedirect(directBuilder.toString());
            return null;
        }


        //验证该任务是否在有效期内
        TpTaskAllotInfo tpallot=new TpTaskAllotInfo();
        tpallot.setTaskid(tk.getTaskid());
        tpallot.getUserinfo().setUserid(userid);
        if(!this.tpTaskAllotManager.getYXTkCount(tpallot)){
            jsonEntity.setMsg("当前任务未开始或不存在!");
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
        }
        switch(tk.getTasktype().intValue()){
            case 4: //成卷测试
                if(!tk.getTaskvalueid().toString().trim().equals(paperid.trim())){
                    jsonEntity.setMsg("任务与试卷不匹配!");
                    response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
                }
                break;
            case 6:
                //添加视频浏览记录
                StuViewMicVideoLog svmvlog=new StuViewMicVideoLog();
                svmvlog.setMicvideoid(tk.getTaskvalueid());
                svmvlog.setUserid(userid);
                //验证是否已经查看过。
                List<StuViewMicVideoLog> stuViewMList=this.stuViewMicVideoLogManager.getList(svmvlog,presult);
                if(stuViewMList==null||stuViewMList.size()<1){
                    //添加完成相关记录(微视频)
                    TaskPerformanceInfo tpf=new TaskPerformanceInfo();
                    tpf.setCourseid(tk.getCourseid());
                    tpf.setTaskid(tk.getTaskid());
                    tpf.setUserid(userList.get(0).getRef());
                    tpf.setIsright(1);
                    tpf.setTasktype(tk.getTasktype());
                    tpf.setCriteria(1);//如果是微视频
                    if(!this.taskPerformanceManager.doSave(tpf)){
                        jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
                        response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
                    }
                    //如果没有，则添加
                    if(!this.stuViewMicVideoLogManager.doSave(svmvlog)){
                        jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
                        response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
                    }
                }
                break;
        }
        //得到试卷
        PaperInfo p=new PaperInfo();
        p.setPaperid(Long.parseLong(paperid));
        List<PaperInfo> paperList=this.paperManager.getList(p,null);
        if(paperList==null||paperList.size()<1){
            jsonEntity.setMsg("试卷不匹配!");
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
        }


        if(splogsList!=null&&splogsList.size()>0){
            StringBuilder directBuilder=new StringBuilder("imapi1_1?m=testDetail&userid=")
                    .append(userid).append("&taskid=").append(tk.getTaskid())
                    .append("&paperid=").append(p.getPaperid()).append("&courseid=").append(tk.getCourseid())
                    .append("&classid=").append(clsid).append("&userType=").append((uType==3?1:uType));//如果是家长，通过相关验证，则走孩子的流程。
            if(quesid!=null&&quesid.trim().length()>0){
                directBuilder.append("&quesId=").append(quesid);
            }
            response.sendRedirect(directBuilder.toString());
            return null;
        }


        //得到所有题的ID
        //得到当前的所有问题
        List<Map<String,Object>> listMapStr=this.paperQuestionManager.getPaperQuesAllId(Long.parseLong(paperid));
        if(listMapStr==null||listMapStr.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
        }
        Object allquesidObj=listMapStr.get(0).get("ALLQUESID");
        if(allquesidObj==null||allquesidObj.toString().trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
        }
        //验证当前题是否在allquesidObj中
        if(quesid!=null&&quesid.trim().length()>0){
            boolean isHasQues=false;
            String[] quesObjArray=allquesidObj.toString().split(",");
            if(quesObjArray.length>0){
                for (String qid:quesObjArray){
                    if(qid!=null&&qid.trim().length()>0){
                        String tmpid=qid;
                        if(tmpid.trim().indexOf("|")!=-1){
                               String[] tArray=tmpid.trim().split("\\|");
                            if(tArray.length>1){
                                tmpid=tArray[1];
                            }
                        }
                        if(tmpid.trim().equals(quesid.trim())){
                            isHasQues=!isHasQues;
                            break;
                        }
                    }
                }
            }
            if(!isHasQues){
                jsonEntity.setMsg("该试卷中不存在该题!题标识："+quesid);
                response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
            }
        }



        //得到该用户已经答过的题
        StuPaperQuesLogs tspqLogs=new StuPaperQuesLogs();
        tspqLogs.setUserid(userid);
        tspqLogs.setPaperid(Long.parseLong(paperid));
        List<StuPaperQuesLogs> stuQuesLogs=this.stuPaperQuesLogsManager.getList(tspqLogs,null);
        StringBuilder answerQuesId=new StringBuilder(",");
        if(stuQuesLogs!=null&&stuQuesLogs.size()>0){
            for(StuPaperQuesLogs sp:stuQuesLogs){
                if(sp.getQuesid()!=null){
                    answerQuesId.append(sp.getQuesid()).append(",");
                }
            }
        }


        mp.put("answerQuesId",answerQuesId.toString());
        if(quesid!=null)
            mp.put("quesid",quesid);
        //得到所有题的分数
//        mp.put("allquesidObj",allquesidObj);
        mp.put("userType",uType);
        mp.put("classid",clsid);
        mp.put("paperObj",paperList.get(0));
        mp.put("taskid",taskid);
        mp.put("courseid",tk.getCourseid());
        mp.put("allquesidObj",allquesidObj);
        mp.put("quesSize",allquesidObj.toString().split(",").length);
        mp.put("paperid",paperid);
        mp.put("userid",userid);
        return new ModelAndView("/imjsp-1.1/test/papertest",mp);
    }

    /**
     * IM手机进入试卷测试页面。
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=toRemoteResourceJsp",method={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView toRemoteResourceJsp( HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        if(!ImUtilTool.ValidateRequestParam(request)){  //验证参数
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return null;
        }
        HashMap<String,String> paramMap=ImUtilTool.getRequestParam(request);
        String taskId = paramMap.get("taskId");
        String userid = paramMap.get("jid");
        String classid = paramMap.get("classId");
        String classtype = paramMap.get("classType");
        String isvir = paramMap.get("isVirtual");
        String usertype = paramMap.get("userType");
        String schoolid =paramMap.get("schoolId");
        String sig = request.getParameter("sign");
        String sign = UrlSigUtil.makeSigSimple("toRemoteResourceJsp",paramMap,"*ETT#HONER#2014*");
        //验证，首先去掉sign，在进行md5验证
        paramMap.remove("sign");
        Boolean b = UrlSigUtil.verifySigSimple("toRemoteResourceJsp",paramMap,sig);
        if(!b){
            je.setMsg("验证失败，非法登录");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        int utype = ImUtilTool.getUserType(usertype);
        UserInfo ui = new UserInfo();
        ui.setEttuserid(Integer.parseInt(userid));
        List<UserInfo> userList = this.userManager.getList(ui,null);
        if(userList==null||userList.size()<1){
            je.setMsg("当前用户未绑定");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //验证当前任务，并得到远程资源id
        TpTaskInfo task = new TpTaskInfo();
        task.setTaskid(Long.parseLong(taskId));
        List<TpTaskInfo> taskList = this.tpTaskManager.getList(task,null);
        if(taskList==null||taskList.size()==0){
            je.setMsg("当前任务不存在");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        if(taskList.get(0).getResourcetype()==null||taskList.get(0).getResourcetype()==1){
            je.setMsg("当前任务资源不符合要求");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        Long resid = taskList.get(0).getTaskvalueid();
        List<Map<String,Object>> taskUserRecord = new ArrayList<Map<String, Object>>();
        List<Map<String,Object>> returnUserRecord = new ArrayList<Map<String, Object>>();
        Map returnUserMap = null;
        if(utype!=2){
            //首先判断这个任务是否完成，跳转到不同的jsp
            //判断任务完成标准
            int criteria = taskList.get(0).getCriteria();
            //定义判断完成的标示
            Boolean isComplete = false;
            if(criteria==1){
                QuestionAnswer qa = new QuestionAnswer();
                qa.setTaskid(taskList.get(0).getTaskid());
                qa.setUserid(userList.get(0).getRef());
                List<QuestionAnswer> qaList = this.questionAnswerManager.getList(qa,null);
                if(qaList!=null&&qaList.size()>0){
                    isComplete=true;
                }
            }else{
                TaskPerformanceInfo tp =new TaskPerformanceInfo();
                tp.setTaskid(taskList.get(0).getTaskid());
                tp.setUserid(userList.get(0).getRef());
                List<TaskPerformanceInfo> tpList = this.taskPerformanceManager.getList(tp,null);
                if(tpList!=null&&tpList.size()>0){
                    isComplete=true;
                }
            }
            if(isComplete){
                taskUserRecord = this.imInterfaceManager.getTaskUserRecord(taskList.get(0).getTaskid(),Integer.parseInt(classid),Integer.parseInt(isvir),null);
                request.setAttribute("resname",taskList.get(0).getResourcename());
                request.setAttribute("userRecord",taskUserRecord);
                if(taskUserRecord!=null&&taskUserRecord.size()>0){
                    StringBuilder jids = new StringBuilder();
                    jids.append("[");
                    for(int i = 0;i<taskUserRecord.size();i++){
                        returnUserMap = new HashMap();
                        String replyDate = UtilTool.convertTimeForTask(Integer.parseInt(taskUserRecord.get(i).get("REPLYDATE").toString()),taskUserRecord.get(i).get("C_TIME").toString());
                        returnUserMap.put("replyDate",replyDate);
                        returnUserMap.put("jid",taskUserRecord.get(i).get("JID"));
                        returnUserMap.put("replyDetail",taskUserRecord.get(i).get("REPLYDETAIL"));
                        returnUserMap.put("replyAttach",taskUserRecord.get(i).get("REPLYATTACH"));
                        returnUserMap.put("replyAttachType",taskUserRecord.get(i).get("REPLYATTACHTYPE"));
                        if(taskUserRecord.get(i).get("JID")!=null){
                            jids.append("{\"jid\":"+Integer.parseInt(taskUserRecord.get(i).get("JID").toString())+"},");
                        }else{
                            returnUserMap.put("uPhoto","http://attach.etiantian.com/ett20/study/common/upload/unknown.jpg");
                            returnUserMap.put("uName",taskUserRecord.get(i).get("realname"));
                        }
                        // returnUserMap.put("uPhoto","img");
                        // returnUserMap.put("uName","小虎");
                        returnUserRecord.add(returnUserMap);
                    }
                    String jidstr = jids.toString().substring(0,jids.toString().lastIndexOf(","))+"]";
                    String url="http://192.168.10.26:8008/study-im-service-1.0/queryPhotoAndRealName.do";
                    //String url = "http://wangjie.etiantian.com:8080/queryPhotoAndRealName.do";
                    HashMap<String,String> signMap = new HashMap();
                    signMap.put("userList",jidstr);
                    signMap.put("schoolId",schoolid);
                    signMap.put("srcJid",userid);
                    signMap.put("userType","3");
                    signMap.put("timestamp",""+System.currentTimeMillis());
                    String signture = UrlSigUtil.makeSigSimple("queryPhotoAndRealName.do",signMap,"*ETT#HONER#2014*");
                    signMap.put("sign",signture);
                    JSONObject jsonObject = UtilTool.sendPostUrl(url,signMap,"utf-8");
                    int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
                    if(type==1){
                        Object obj = jsonObject.containsKey("data")?jsonObject.get("data"):null;
                        JSONArray jr = JSONArray.fromObject(obj);
                        if(jr!=null&&jr.size()>0){
                            for(int i = 0;i<jr.size();i++){
                                JSONObject jo = jr.getJSONObject(i);
                                for(int j = 0;j<returnUserRecord.size();j++){
                                    returnUserMap = new HashMap();
                                    if(jo.getInt("jid")==Integer.parseInt(returnUserRecord.get(j).get("jid").toString())){
                                        returnUserRecord.get(j).put("uPhoto", jo.getString("headUrl"));
                                        returnUserRecord.get(j).put("uName", jo.getString("realName"));
                                    }
                                }
                            }
                        }
                    }
                }
                request.setAttribute("replyList",returnUserRecord);
                return new ModelAndView("/imjsp-1.1/remote-resource-detail");
            }else{
                if(taskList.get(0).getRemotetype()!=null&&taskList.get(0).getRemotetype()==2){
                    String url = "http://192.168.10.26:8008/study-im-service-1.0/getResourceZSDX.do";
                    Long time = System.currentTimeMillis();
                    HashMap<String,String> param = new HashMap<String, String>();
                    param.put("resourceId",resid.toString());
                    param.put("timestamp",time.toString());
                    String signure =  UrlSigUtil.makeSigSimple("getResourceZSDX.do",param,"*ETT#HONER#2014*");
                    param.put("sign",signure);
                    JSONObject returnval = UtilTool.sendPostUrl(url,param,"GBK");
                    String data=returnval.containsKey("data")?returnval.getString("data"):"";
                    int result = returnval.containsKey("result")?returnval.getInt("result"):0;
                    if(result==1){
                        response.sendRedirect(data);
                    }else{
                        return null;
                    }
                }else{

                }
            }
        }else{
            taskUserRecord = this.imInterfaceManager.getTaskUserRecord(taskList.get(0).getTaskid(),Integer.parseInt(classid),Integer.parseInt(isvir),userList.get(0).getUserid());
            if(taskUserRecord!=null&&taskUserRecord.size()>0){
                request.setAttribute("resname",taskList.get(0).getResourcename());
                request.setAttribute("userRecord",taskUserRecord);
                return new ModelAndView("");
            }else{
                request.getRequestDispatcher("http://www.baidu.com").forward(request,response);
            }
        }
        return null;
    }


    /**
     * 获取任务试卷试题
     * @param request
     * @param response
     * @throws Exception
     * taskId	Int	任务id
    classId	Int	班级id
    groupId	int	可以为空
    schoolId	Int	班级所属分析id
    jid	Int	用户id
    userType	Int	用户类型
    time	int
    sign	String

     */
    @RequestMapping(params="m=getTaskPaperQuestion",method={RequestMethod.GET,RequestMethod.POST})
    public void getTaskPaperQuestion(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject returnJo=new JSONObject();
        returnJo.put("result","0");//默认失败
        if(!ImUtilTool.ValidateRequestParam(request)){  //验证参数
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> paramMap=ImUtilTool.getRequestParam(request);
        JSONObject jo=new JSONObject();
        //获取参数
        String taskId=paramMap.get("taskId");
        String classId=paramMap.get("classId");
        String groupId=paramMap.get("groupId");
        String schoolId=paramMap.get("schoolId");
        String jid=paramMap.get("jid");
        String userType=paramMap.get("userType");
        String time=paramMap.get("time");
        String sign=paramMap.get("sign");
        if(taskId==null||schoolId==null||time==null||sign==null||userType==null||jid==null){
            returnJo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(returnJo.toString());return;
        }
        //验证时间
        //验证是否在三分钟内
        Long ct=Long.parseLong(time.toString());
        Long nt=new Date().getTime();
        double d=(nt-ct)/(1000*60);
        if(d>3){//大于三分钟
            returnJo.put("msg","异常错误，响应超时!接口三分钟内有效!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        //去除sign
        paramMap.remove("sign");
        paramMap.remove("getTaskPaperQuestion");
        //验证Md5
        Boolean b = UrlSigUtil.verifySigSimple("getTaskPaperQuestion",paramMap,sign);
        if(!b){
            returnJo.put("msg","验证失败，非法登录!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        UserInfo u=new UserInfo();
        u.setEttuserid(Integer.parseInt(jid));
        u.setDcschoolid(Integer.parseInt(schoolId));
        List<UserInfo>userList=this.userManager.getList(u,null);
        if(userList==null||userList.size()<1){
            returnJo.put("msg","当前云帐号未绑定!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        UserInfo tmpUser=userList.get(0);

        List<Map<String,Object>> returnMapList=new ArrayList<Map<String, Object>>();
        TpTaskInfo taskInfo=new TpTaskInfo();
        taskInfo.setTaskid(Long.parseLong(taskId));
        //查询没被我删除的任务
        taskInfo.setSelecttype(1);
        taskInfo.setStatus(1);
        //已发布的任务
        List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(taskInfo, null);
        if(taskList==null||taskList.size()<1){
            returnJo.put("msg","未获取到任务数据!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        Long paperid=null;
        TpTaskInfo tmpTask=taskList.get(0);
        if(!(tmpTask.getTasktype()>3&&tmpTask.getTasktype()<7)){
            returnJo.put("msg","非试卷类任务!");
            response.getWriter().print(returnJo.toString());
            return;
        }





        if(tmpTask.getTasktype()==4){
            paperid=tmpTask.getTaskvalueid();
        }else if(tmpTask.getTasktype()==6){
            MicVideoPaperInfo micPaper=new MicVideoPaperInfo();
            micPaper.setMicvideoid(tmpTask.getTaskvalueid());
            List<MicVideoPaperInfo>micVideoPaperInfoList=this.micVideoPaperManager.getList(micPaper,null);
            if(micVideoPaperInfoList==null||micVideoPaperInfoList.size()<1){
                returnJo.put("msg","未获取到微视频试卷数据!");
                response.getWriter().print(returnJo.toString());
                return;
            }
            paperid=micVideoPaperInfoList.get(0).getPaperid();
        }else if(tmpTask.getTasktype()==5){
            if(ImUtilTool.getUserType(userType)!=2){  //学生
                //判断任务时间
                //得到创建任务时生成的TaskValueId
                Long tkvalueid=tmpTask.getTaskvalueid();
                PaperInfo pentity=new PaperInfo();
                pentity.setParentpaperid(tkvalueid);
                pentity.setCuserid(tmpUser.getUserid());
                List<PaperInfo> paperList=this.paperManager.getList(pentity,null);
                if(paperList==null||paperList.size()<1){

                    TpTaskInfo t=new TpTaskInfo();
                    t.setUserid(tmpUser.getUserid());
                    t.setTaskid(Long.parseLong(taskId));
                    t.setCourseid(tmpTask.getCourseid());
                    PageResult pr=new PageResult();
                    pr.setPageSize(1);
                    // 学生任务
                    List<TpTaskInfo>taskStuList=this.tpTaskManager.getListbyStu(t, pr);
                    if(taskStuList==null||taskStuList.size()<1||taskStuList.get(0).getBtime()==null||taskStuList.get(0).getEtime()==null){
                        returnJo.put("msg", UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                        response.getWriter().println(returnJo.toString());return;
                    }
                    if(taskStuList.get(0).getTaskstatus().equals("3")){
                        returnJo.put("msg", "任务已结束，你无法进入测试!");
                        response.getWriter().println(returnJo.toString());return;
                    }
                    //生成试题
                    if(!this.paperManager.doGenderZiZhuPaper(tmpTask.getTaskid(),tmpUser.getUserid())){
                        returnJo.put("msg", "生成试卷失败!!");
                        response.getWriter().println(returnJo.toString());return;
                    }else{
                        //再查一遍
                        paperList=this.paperManager.getList(pentity,null);
                    }
                }
                paperid=paperList.get(0).getPaperid();
            }else{
                //教师获取未完成学生名单
                TpTaskInfo task=new TpTaskInfo();
                task.setTaskid(Long.parseLong(taskId));
                Integer cid=null;
                if(classId!=null&&classId.trim().length()>0)
                    cid=Integer.parseInt(classId);

                List<Map<String,Object>> userMapList=new ArrayList<Map<String, Object>>();
                List<UserInfo>notCompleteList=this.userManager.getUserNotCompleteTask(task.getTaskid(),null,cid,"1");
                if(notCompleteList!=null&&notCompleteList.size()>0){

                    for (UserInfo user:notCompleteList){
                        //if(user.getEttuserid()==null)return;
                        Map<String,Object> uMap=new HashMap<String, Object>();
                        //uMap.put("stuJid",user.getEttuserid());
                        uMap.put("stuName",user.getRealname());
                        userMapList.add(uMap);
                    }
                }
                jo.put("stuList",userMapList.size()>0?userMapList:null);
            }
        }
        List<PaperQuestion>pqList=null;
        List<PaperQuestion>childList=null;
        //获取提干
        if(paperid!=null&&paperid.toString().length()>0){
            PaperQuestion pq=new PaperQuestion();
            pq.setPaperid(paperid);
            PageResult p=new PageResult();
            p.setOrderBy("u.order_idx");
            p.setPageNo(0);
            p.setPageSize(0);
            pqList=this.paperQuestionManager.getList(pq,p);

            //获取试题组下题目
            PaperQuestion child =new PaperQuestion();
            child.setPaperid(pq.getPaperid());
            childList=this.paperQuestionManager.getPaperTeamQuestionList(child,null);
        }










        //整合试题组
        List<PaperQuestion> tmpList=new ArrayList<PaperQuestion>();
        List<PaperQuestion>questionTeam;
        if(pqList!=null&&pqList.size()>0){
            for(PaperQuestion paperQuestion:pqList){
                questionTeam=new ArrayList<PaperQuestion>();
                //试题组
                if(childList!=null&&childList.size()>0){
                    for (PaperQuestion childp :childList){
                        if(paperQuestion.getRef().equals(childp.getRef())){
                            questionTeam.add(childp);
                        }
                    }
                    paperQuestion.setQuestionTeam(questionTeam);
                }
                tmpList.add(paperQuestion);


                Map<String,Object> map=new HashMap<String,Object>();
                map.put("quesId",paperQuestion.getQuestionid());
                map.put("quesType",paperQuestion.getQuestiontype()==1?1:0); //1：其他 主观题
                returnMapList.add(map);
               // map.put("isQesTeam",paperQuestion.getQuestiontype()==6?1:0); //6：试题组
                if(paperQuestion.getQuestionTeam()!=null&&paperQuestion.getQuestionTeam().size()>0){
                    List<Map<String,Object>> childMapList=new ArrayList<Map<String, Object>>();
                   /*
                    for(PaperQuestion qTeam:paperQuestion.getQuestionTeam()){

                        Map<String,Object> childMap=new HashMap<String,Object>();
                        childMap.put("teamQesId",qTeam.getQuestionid());
                        childMap.put("teamQesType",qTeam.getQuestiontype()==1?1:0);
                        if(!childMapList.contains(childMap))
                            childMapList.add(childMap);
                    }
                    map.put("teamQesList",childMapList);
                    */
                    for(PaperQuestion qTeam:paperQuestion.getQuestionTeam()){
                        Map<String,Object> childMap=new HashMap<String,Object>();
                        childMap.put("quesId",qTeam.getQuestionid());
                        childMap.put("quesType",qTeam.getQuestiontype()==1?1:0);
                        returnMapList.add(childMap);
                    }
                }
            }
        }


        //如果是学生,查询是否完成试卷、总分、排名
        if(ImUtilTool.getUserType(userType)!=2){  //学生
            TaskPerformanceInfo complete=new TaskPerformanceInfo();
            complete.setTaskid(Long.parseLong(taskId));
            complete.setUserid(tmpUser.getRef());
            if(tmpTask.getTasktype()==4){
                complete.setCriteria(1);
            }else if(tmpTask.getTasktype()==6){
                complete.setCriteria(2);
            }else if(tmpTask.getTasktype()==5){
                complete.setCriteria(1);
            }
            List<TaskPerformanceInfo>completeList=this.taskPerformanceManager.getList(complete,null);
            jo.put("isDone",completeList!=null&&completeList.size()>0?1:0);



            TpTaskInfo t=new TpTaskInfo();
            t.setUserid(tmpUser.getUserid());
            t.setTaskid(Long.parseLong(taskId));
            t.setCourseid(tmpTask.getCourseid());
            PageResult pr=new PageResult();
            pr.setPageSize(1);

            // 学生任务
            List<TpTaskInfo>taskStuList=this.tpTaskManager.getListbyStu(t, pr);
            if(taskStuList==null||taskStuList.size()<1||taskStuList.get(0).getBtime()==null||taskStuList.get(0).getEtime()==null){
                returnJo.put("msg", UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                response.getWriter().println(returnJo.toString());return;
            }
            if(taskStuList.get(0).getTaskstatus().equals("3")){
                //已结束的任务获取总分、排名
                TaskPerformanceInfo taskPerformanceInfo=new TaskPerformanceInfo();
                taskPerformanceInfo.setTaskid(Long.parseLong(taskId));
                Long clsid=null;
                if(classId!=null&&classId.length()>0&&!classId.equals("null")){
                    clsid=Long.parseLong(classId);
                }else{
                    clsid=Long.parseLong("0");
                }
                //任务记录排名
                List<TaskPerformanceInfo>tList=this.taskPerformanceManager.getPerformListByTaskid(taskPerformanceInfo,clsid,1);
                TaskPerformanceInfo scoreRankInfo=null;
                if(tList!=null&&tList.size()>0){
                    scoreRankInfo=tList.get(0);
                    jo.put("order",scoreRankInfo.getRank());
                    jo.put("totalScore",scoreRankInfo.getScore());
                }
            }
        }




        jo.put("testId",paperid==null?0:paperid);
        jo.put("quesList",returnMapList.size()>0?returnMapList:null);

        returnJo.put("data",jo.toString());
        returnJo.put("result","1");
        response.getWriter().print(returnJo.toString());
    }


    /**
     * 班级统计接口
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=getClassStatics",method={RequestMethod.GET,RequestMethod.POST})
    public void getClassStatics(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject returnJo=new JSONObject();
        returnJo.put("result","0");//默认失败
        if(!ImUtilTool.ValidateRequestParam(request)){  //验证参数
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> paramMap=ImUtilTool.getRequestParam(request);
        JSONObject jo=new JSONObject();
        //获取参数
        String classId=paramMap.get("classId");
        String subjectId=paramMap.get("subjectId");
        String schoolId=paramMap.get("schoolId");
        String jid=paramMap.get("jid");
        String userType=paramMap.get("userType");
        String time=paramMap.get("time");
        String sign=paramMap.get("sign");
        if(schoolId==null||time==null||sign==null||userType==null||jid==null){
            returnJo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(returnJo.toString());return;
        }
        //验证时间
        //验证是否在三分钟内
        Long ct=Long.parseLong(time.toString());
        Long nt=new Date().getTime();
        double d=(nt-ct)/(1000*60);
        if(d>3){//大于三分钟
            returnJo.put("msg","异常错误，响应超时!接口三分钟内有效!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        //去除sign
        paramMap.remove("sign");
        paramMap.remove("getClassStatics");
        //验证Md5
        Boolean b = UrlSigUtil.verifySigSimple("getClassStatics",paramMap,sign);
        if(!b){
            returnJo.put("msg","验证失败，非法登录!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        UserInfo u=new UserInfo();
        u.setEttuserid(Integer.parseInt(jid));
        u.setDcschoolid(Integer.parseInt(schoolId));
        List<UserInfo>userList=this.userManager.getList(u,null);
        if(userList==null||userList.size()<1){
            returnJo.put("msg","当前云帐号未绑定!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        List<Map<String,Object>>tmpRankList=new ArrayList<Map<String, Object>>();
        ImInterfaceInfo obj=new ImInterfaceInfo();
        obj.setSubjectid(Integer.parseInt(subjectId));
        obj.setClassid(Integer.parseInt(classId));
        List<Map<String,Object>>teamRankList=this.imInterfaceManager.getQryStatPerson(obj);
        if(teamRankList!=null&&teamRankList.size()>0){
            for(Map<String,Object>map:teamRankList){
                Map<String,Object>tmpMap=new HashMap<String, Object>();
                tmpMap.put("jid",map.get("ETT_USER_ID"));
                tmpMap.put("uName",map.get("REALNAME"));
                tmpMap.put("uScore",map.get("COURSE_TOTAL_SCORE"));
                tmpMap.put("uTeam",map.get("GROUP_NAME"));
                tmpMap.put("uPosition",map.get("RANK"));
                tmpRankList.add(tmpMap);
            }
        }

        jo.put("teamRankList",tmpRankList==null||tmpRankList.size()<1?null:tmpRankList);

        if(ImUtilTool.getUserType(userType)!=2){  //学生
            obj.setUserid(userList.get(0).getUserid());
            List<Map<String,Object>>teamRankStuList=this.imInterfaceManager.getQryStatPersonStu(obj);
            if(teamRankStuList!=null&&teamRankStuList.size()>0){
                jo.put("userScore",teamRankStuList.get(0).get("COURSE_TOTAL_SCORE"));
                jo.put("doTaskNum",teamRankStuList.get(0).get("TASK_SCORE"));
                jo.put("presenceNum",teamRankStuList.get(0).get("ATTENDANCE_NUM"));
            }else{
                jo.put("userScore",0);
                jo.put("doTaskNum",0);
                jo.put("presenceNum",0);
            }
        }


        returnJo.put("data",jo.toString());
        returnJo.put("result","1");
        response.getWriter().print(returnJo.toString());
    }




    /**
     * 任务获得积分保存接口
     *  说明：任务获得积分、道具保存接口
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=saveUserScore",method = RequestMethod.POST)
    public void doSaveUserScore(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JSONObject returnJo=new JSONObject();
        returnJo.put("result","0");//默认失败
        if(!ImUtilTool.ValidateRequestParam(request)){  //验证参数
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> paramMap=ImUtilTool.getRequestParam(request);
        //获取参数
        String taskId=paramMap.get("taskId");
        String jid=paramMap.get("jid");
        String classId=paramMap.get("classId");
        String schoolId=paramMap.get("schoolId");
        String time=paramMap.get("time");
        String sign=paramMap.get("sign");
        if(taskId==null||schoolId==null||time==null||sign==null||jid==null||classId==null){
            returnJo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(returnJo.toString());return;
        }
        //验证时间
        //验证是否在三分钟内
        Long ct=Long.parseLong(time.toString());
        Long nt=new Date().getTime();
        double d=(nt-ct)/(1000*60);
        if(d>3){//大于三分钟
            returnJo.put("msg","异常错误，响应超时!接口三分钟内有效!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        //去除sign
        paramMap.remove("sign");
        //验证Md5
        Boolean b = UrlSigUtil.verifySigSimple("saveUserScore",paramMap,sign);
        if(!b){
            returnJo.put("msg","验证失败，非法登录!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        UserInfo u=new UserInfo();
        u.setEttuserid(Integer.parseInt(jid));
        u.setDcschoolid(Integer.parseInt(schoolId));
        List<UserInfo>userList=this.userManager.getList(u,null);
        if(userList==null||userList.size()<1){
            returnJo.put("msg","当前云帐号未绑定!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        UserInfo tmpUser=userList.get(0);
        //验证任务 是否存在
        TpTaskInfo tk=new TpTaskInfo();
        tk.setTaskid(Long.parseLong(taskId.trim()));
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        List<TpTaskInfo> tpTaskList=this.tpTaskManager.getList(tk,presult);
        if(tpTaskList==null||tpTaskList.size()<1){
            returnJo.put("msg","错误，当前任务不存在!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        tk=tpTaskList.get(0);
        //验证任务是否在有效期内
        //验证该任务是否在有效期内
        TpTaskAllotInfo tpallot=new TpTaskAllotInfo();
        tpallot.setTaskid(tk.getTaskid());
        tpallot.getUserinfo().setUserid(tmpUser.getUserid());
        if(!this.tpTaskAllotManager.getYXTkCount(tpallot)){
            returnJo.put("msg", "当前任务未开始或不存在!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        //操作积分，道具
        //taskinfo:   4:成卷测试  5：自主测试   6:微视频
        //规则转换:    6             7         8
        Integer type=0;
        switch(tk.getTasktype()){
            case 3:     //试题
                type=1;break;
            case 1:     //资源学习
                type=3;break;
            case 2:
                type=4;break;
            case 4:
                type=6;
                break;
            case 6:
                type=8;
                break;
            case 5:
                type=7;
                break;
        }
        //                /*奖励加分通过*/
        if(this.tpStuScoreLogsManager.awardStuScore(tk.getCourseid()
                ,Long.parseLong(classId.trim())
                ,tk.getTaskid()
                ,Long.parseLong(tmpUser.getUserid()+""),jid,type)){
            returnJo.put("msg", "恭喜您,获得了1积分和1蓝宝石");
            returnJo.put("result","1");
        }else{
            returnJo.put("msg", "异常错误，奖励加分失败，原因：该任务已经存在相关记录");
        }
        response.getWriter().println(returnJo.toString());
    }


}

/**
 * 工具类
 */
class ImUtilTool{
    /**
     * 验证RequestParams相关参数
     * @param request
     * @return
     * @throws Exception
     */
    public static Boolean ValidateRequestParam(HttpServletRequest request) throws Exception{
        Enumeration eObj=request.getParameterNames();
        boolean returnBo=true;
        if(eObj!=null){
            while(eObj.hasMoreElements()){
                Object obj=eObj.nextElement();
                if(obj==null||obj.toString().trim().length()<1||request.getQueryString().toString().equals(obj))
                    continue;

                Object val=request.getParameter(obj.toString());
                if(val==null||val.toString().trim().length()<1){
                    returnBo=!returnBo;
                    break;
                }
            }
        }

        return returnBo;
    }
    /**
     * 验证RequestParams相关参数
     * @param request
     * @return
     * @throws Exception
     */
    public static HashMap<String,String> getRequestParam(HttpServletRequest request) throws Exception{
        Enumeration eObj=request.getParameterNames();
        HashMap<String,String> returnMap=null;
        if(eObj!=null){
            returnMap=new HashMap<String, String>();
            while(eObj.hasMoreElements()){
                Object obj=eObj.nextElement();
                if(obj==null||obj.toString().trim().length()<1||obj.toString().trim().equals("m")||obj.toString().equals(request.getQueryString()))
                    continue;
                Object val=request.getParameter(obj.toString());
                returnMap.put(obj.toString(),val.toString());
            }
        }
        return returnMap;
    }

    /**
     * 转换usertype
     * @param usertype 1:学生  2：老师  3：家长
     * return usertype
     * */
    public static Integer getUserType(String usertype){
        if(usertype==null||usertype.length()<1){
            return 0;
        }
        int type = 0;
        if(usertype.equals("1")||usertype.equals("2")){
            type=2;
        }else if(usertype.equals("3")||usertype.equals("4")){
            type=1;
        }else if(usertype.equals("6")){
            type=3;
        }
        return type;
    }
}
