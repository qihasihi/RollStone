package com.school.im1_1.control._interface;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.control.base.BaseController;
import com.school.entity.UserInfo;
import com.school.entity.resource.ResourceInfo;
import com.school.entity.teachpaltform.*;
import com.school.entity.teachpaltform.interactive.TpTopicInfo;
import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;
import com.school.entity.teachpaltform.paper.MicVideoPaperInfo;
import com.school.entity.teachpaltform.paper.PaperInfo;
import com.school.entity.teachpaltform.paper.PaperQuestion;
import com.school.im1_1.entity._interface.ImInterfaceInfo;
import com.school.im1_1.manager._interface.ImInterfaceManager;
import com.school.manager.UserManager;
import com.school.manager.inter.IUserManager;
import com.school.manager.inter.resource.IResourceManager;
import com.school.manager.inter.teachpaltform.*;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicThemeManager;
import com.school.manager.inter.teachpaltform.paper.IMicVideoPaperManager;
import com.school.manager.inter.teachpaltform.paper.IPaperManager;
import com.school.manager.inter.teachpaltform.paper.IPaperQuestionManager;
import com.school.manager.resource.ResourceManager;
import com.school.manager.teachpaltform.*;
import com.school.manager.teachpaltform.interactive.TpTopicManager;
import com.school.manager.teachpaltform.interactive.TpTopicThemeManager;
import com.school.manager.teachpaltform.paper.MicVideoPaperManager;
import com.school.manager.teachpaltform.paper.PaperManager;
import com.school.manager.teachpaltform.paper.PaperQuestionManager;
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
    private IMicVideoPaperManager micVideoPaperManager;
    public ImInterfaceController(){
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
    }
    /**
     * 学习目录接口
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=StudyModule",method= RequestMethod.GET)
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
    @RequestMapping(params="m=ClassTask",method= RequestMethod.GET)
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
                    taskList = this.imInterfaceManager.getClassTaskTask(Long.parseLong(courseList.get(i).get("COURSEID").toString()),null);
                }else{
                    taskList = this.imInterfaceManager.getClassTaskTask(Long.parseLong(courseList.get(i).get("COURSEID").toString()),userList.get(0).getUserid());
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
    @RequestMapping(params="m=StuClassCalendar",method= RequestMethod.GET)
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
                    for(int i = 0;i<courseArray.size();i++){
                        Map o = courseArray.get(i);
                        Map o2 = new HashMap();
                        o2.put("courseId",o.get("COURSE_ID"));
                        o2.put("courseName",o.get("COURSE_NAME"));
                        o2.put("courseDate",o.get("BEGIN_TIME")+"~"+o.get("END_TIME")!=null?o.get("END_TIME"):"——");
                        o2.put("schoolId",o.get("DC_SCHOOL_ID"));
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
        m2.put("personTotalScore","350");
        m2.put("teamShow","本组小红旗总数排全班第一");
        m2.put("teamScore","130");
        m2.put("taskScore","150");
        m2.put("presenceScore","50");
        m2.put("smileScore","10");
        m2.put("illegalScore","10");
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
    @RequestMapping(params="m=TeaClassCalendar",method= RequestMethod.GET)
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
                for(int i = 0;i<courseArray.size();i++){
                    Map o = courseArray.get(i);
                    Map o2 = new HashMap();
                    o2.put("courseId",o.get("COURSE_ID"));
                    o2.put("courseName",o.get("COURSE_NAME"));
                    o2.put("courseDate",o.get("BEGIN_TIME")+"~"+o.get("END_TIME")!=null?o.get("END_TIME"):"——");
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
    @RequestMapping(params="m=TeaClassCalendarDetail",method= RequestMethod.GET)
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
            for(int i = 0;i<courseArray.size();i++){
                Map o = courseArray.get(i);
                Map o2 = new HashMap();
                o2.put("courseId",o.get("COURSE_ID"));
                o2.put("courseName",o.get("COURSE_NAME"));
                o2.put("courseDate",o.get("BEGIN_TIME")+"~"+o.get("END_TIME")!=null?o.get("END_TIME"):"——");
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
    @RequestMapping(params="m=StuClassCalendarDetail",method= RequestMethod.GET)
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
            for(int i = 0;i<courseArray.size();i++){
                Map o = courseArray.get(i);
                Map o2 = new HashMap();
                o2.put("courseId",o.get("COURSE_ID"));
                o2.put("courseName",o.get("COURSE_NAME"));
                o2.put("courseDate",o.get("BEGIN_TIME")+"~"+o.get("END_TIME")!=null?o.get("END_TIME"):"——");
                o2.put("schoolId",o.get("DC_SCHOOL_ID"));
                courseArray2.add(o2);
            }
            m2.put("courseArray",courseArray2);
        }else{
            m2.put("courseArray",null);
        }
        m2.put("personTotalScore", "350");
        m2.put("teamShow","本组小红旗总数排全班第一");
        m2.put("teamScore","130");
        m2.put("taskScore","150");
        m2.put("presenceScore","50");
        m2.put("smileScore","10");
        m2.put("illegalScore","10");
        m.put("result","1");
        m.put("msg","成功");
        m.put("data",m2);
        JSONObject object = JSONObject.fromObject(m);
        response.getWriter().print(object.toString());
    }

    /**
     * 班级课表接口
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=AddTask",method= {RequestMethod.POST,RequestMethod.GET})
    public void addTask(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        String dataStr = request.getParameter("data");
        String userid = request.getParameter("jid");
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
        map.put("data",dataStr);
        map.put("jid",userid);
        map.put("schoolId",schoolid);
        map.put("timeStamp",timestamp);
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
        String courseid=jb.containsKey("courseid")?jb.getString("courseid"):"";
        String tasktype = jb.containsKey("tasktype")?jb.getString("tasktype"):"";
        String tasktitle = jb.containsKey("tasktitle")?jb.getString("tasktitle"):"";
        String taskcontent = jb.containsKey("taskcontent")?jb.getString("taskcontent"):"";
        String taskanalysis = jb.containsKey("taskanalysis")?jb.getString("taskanalysis"):"";
        String taskattach = jb.containsKey("taskattach")?jb.getString("taskattach"):"";
        Object classesObj = jb.containsKey("classes")?jb.get("classes"):"";
        //验证专题是否存在
        TpCourseInfo courseInfo=new TpCourseInfo();
        courseInfo.setCourseid(Long.parseLong(courseid));
        List<TpCourseInfo>courseList=this.tpCourseManager.getList(courseInfo,null);
        if(courseList==null||courseList.size()<1){
            response.getWriter().print("");
            return;
        }
        /**
         *查询出当前专题有效的任务个数，排序用
         */

        TpTaskInfo t=new TpTaskInfo();
        t.setCourseid(Long.parseLong(courseid));
        //查询没被我删除的任务
        t.setSelecttype(1);
        t.setLoginuserid(387);
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
        tpTaskInfo.setTaskremark(taskanalysis);
        tpTaskInfo.setImtaskattach(taskattach);
        tpTaskInfo.setImtaskcontent(taskcontent);
        tpTaskInfo.setTasktype(Integer.parseInt(tasktype));
        tpTaskInfo.setCourseid(Long.parseLong(courseid));
        sql = new StringBuilder();
        objList = new ArrayList<Object>();
        objList=this.tpTaskManager.getSaveSql(tpTaskInfo,sql);
        sqlListArray.add(sql.toString());
        objListArray.add(objList);
        //组织任务发给班级的数据
        if(classesObj!=null){
            JSONArray jr = JSONArray.fromObject(classesObj);
            for(int i = 0;i<jr.size();i++){
                JSONObject jsonObject = JSONObject.fromObject(jr.get(i));
                //拆分json字符串，得到各个参数
                String classid=jsonObject.containsKey("toclassid")?jsonObject.getString("toclassid"):"";
                String classtype=jsonObject.containsKey("classtype")?jsonObject.getString("classtype"):"";
                String starttime=jsonObject.containsKey("starttime")?jsonObject.getString("starttime"):"";
                String endtime=jsonObject.containsKey("endtime")?jsonObject.getString("endtime"):"";
                TpTaskAllotInfo tpTaskAllotInfo = new TpTaskAllotInfo();
                tpTaskAllotInfo.setCourseid(Long.parseLong(courseid));
                tpTaskAllotInfo.setTaskid(tasknextid);
                tpTaskAllotInfo.setUsertype(Integer.parseInt(classtype));
                tpTaskAllotInfo.setUsertypeid(Long.parseLong(classid));
                tpTaskAllotInfo.setBtime(UtilTool.StringConvertToDate(starttime));
                tpTaskAllotInfo.setEtime(UtilTool.StringConvertToDate(endtime));
                sql = new StringBuilder();
                objList = new ArrayList<Object>();
                objList = this.tpTaskAllotManager.getSaveSql(tpTaskAllotInfo,sql);
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
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
        }
        List<Map<String,Object>> taskUserRecord = new ArrayList<Map<String, Object>>();
        List<Map<String,Object>> returnUserRecord = new ArrayList<Map<String, Object>>();
        Map returnUserMap = new HashMap();
        if(utype==2){
            taskUserRecord = this.imInterfaceManager.getTaskUserRecord(taskList.get(0).getTaskid(),Integer.parseInt(classid),Integer.parseInt(isvir),null);
        }else{
            taskUserRecord = this.imInterfaceManager.getTaskUserRecord(taskList.get(0).getTaskid(),Integer.parseInt(classid),Integer.parseInt(isvir),userList.get(0).getUserid());
        }
        if(taskUserRecord!=null&&taskUserRecord.size()>0){
            for(int i = 0;i<taskUserRecord.size();i++){
                int time =Integer.parseInt(taskUserRecord.get(i).get("REPLYDATE").toString());
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
                    String t = taskUserRecord.get(i).get("C_TIME").toString();
                    t = t.split("-")[1]+"月"+t.split("-")[2].split(" ")[0]+"日";
                    returnUserMap.put("replyDate",t);
                }else{
                    if(hours>0){
                        returnUserMap.put("replyDate",hours+"小时");
                    }else{
                        if(mins>0){
                            returnUserMap.put("replyDate",mins+"分钟");
                        }else{
                            if(seconds>0){
                                returnUserMap.put("replyDate",seconds+"秒");
                            }
                        }
                    }
                }
                returnUserMap.put("jid",taskUserRecord.get(i).get("JID"));
                returnUserMap.put("replyDetail",taskUserRecord.get(i).get("REPLYDETAIL"));
                returnUserMap.put("replyAttach",taskUserRecord.get(i).get("REPLYATTACH"));
                returnUserMap.put("replyAttachType",taskUserRecord.get(i).get("REPLYATTACHTYPE"));
                returnUserMap.put("uPhoto","img");
                returnUserRecord.add(returnUserMap);
            }
        }
        returnMap.put("replyList",returnUserRecord);
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
            }else{
                taskUserRecord = this.imInterfaceManager.getTaskUserRecord(taskList.get(0).getTaskid(),Integer.parseInt(classid),Integer.parseInt(isvir),null);
                if(taskUserRecord!=null&&taskUserRecord.size()>0){
                    for(int i = 0;i<taskUserRecord.size();i++){
                        int time =Integer.parseInt(taskUserRecord.get(i).get("REPLYDATE").toString());
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
                            String t = taskUserRecord.get(i).get("C_TIME").toString();
                            t = t.split("-")[1]+"月"+t.split("-")[2].split(" ")[0]+"日";
                            taskUserRecord.get(i).put("REPLYDATE",t);
                        }else{
                            if(hours>0){
                                taskUserRecord.get(i).put("REPLYDATE",hours+"小时");
                            }else{
                                if(mins>0){
                                    taskUserRecord.get(i).put("REPLYDATE",mins+"分钟");
                                }else{
                                    if(seconds>0){
                                        taskUserRecord.get(i).put("REPLYDATE",seconds+"秒");
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
            return new ModelAndView("/imjsp-1.1/task-detail-question");
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
        if(!ImUtilTool.ValidateRequestParam(request)){  //验证参数
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
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
        List<Object>objList=null;
        StringBuilder sql=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        TpTaskInfo tmpTask=taskList.get(0);
        String quesanswer = replyDetail;
        if(tmpTask.getTasktype()==1){
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
        //执行并返回结果
        if(objListArray.size()>0&&sqlListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlListArray, objListArray);
            if(flag){
                JSONObject jo = new JSONObject();
                jo.put("result",1);
                jo.put("msg","回答完成");
                List<Map<String,Object>> returnUserRecord = new ArrayList<Map<String, Object>>();
                Map returnUserMap = new HashMap();
                List<Map<String,Object>> taskUserRecord = this.imInterfaceManager.getTaskUserRecord(taskList.get(0).getTaskid(),Integer.parseInt(classid),Integer.parseInt(isvir),userList.get(0).getUserid());
                if(taskUserRecord!=null&&taskUserRecord.size()>0){
                    for(int i = 0;i<taskUserRecord.size();i++){
                        int time =Integer.parseInt(taskUserRecord.get(i).get("REPLYDATE").toString());
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
                            String t = taskUserRecord.get(i).get("C_TIME").toString();
                            t = t.split("-")[1]+"月"+t.split("-")[2].split(" ")[0]+"日";
                            returnUserMap.put("replyDate",t);
                        }else{
                            if(hours>0){
                                returnUserMap.put("replyDate",hours+"小时");
                            }else{
                                if(mins>0){
                                    returnUserMap.put("replyDate",mins+"分钟");
                                }else{
                                    if(seconds>0){
                                        returnUserMap.put("replyDate",seconds+"秒");
                                    }
                                }
                            }
                        }
                        returnUserMap.put("jid",taskUserRecord.get(i).get("JID"));
                        returnUserMap.put("replyDetail",taskUserRecord.get(i).get("REPLYDETAIL"));
                        returnUserMap.put("replyAttach",taskUserRecord.get(i).get("REPLYATTACH"));
                        returnUserMap.put("replyAttachType",taskUserRecord.get(i).get("REPLYATTACHTYPE"));
                        returnUserMap.put("uPhoto","img");
                        returnUserRecord.add(returnUserMap);
                    }
                }
                Map m = new HashMap();
                m.put("replyList",returnUserRecord);
                jo.put("data",m);
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
     * 回答任务接口
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=StuPost",method={RequestMethod.GET,RequestMethod.POST})
    public void doReplyTopic(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject returnJo=new JSONObject();
        returnJo.put("result",0);//默认失败
        if(!ImUtilTool.ValidateRequestParam(request)){  //验证参数
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> paramMap=ImUtilTool.getRequestParam(request);
        String topicId = paramMap.get("topicId");
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
        paramMap.remove("sign");
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
        TpTopicInfo ti = new TpTopicInfo();
        ti.setTopicid(Long.parseLong(topicId));
        ti.setSelectType(2);/*查询类型  1:status<>3   2:不连接被删除的 */
        List<TpTopicInfo> tiList  = this.tpTopicManager.getList(ti,null);
        if(tiList==null||tiList.size()==0){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前论题不存在，请查看后重试\"}");
            return;
        }
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
        bool = this.tpTopicThemeManager.doSave(topictheme);
        JSONObject jo = new JSONObject();
        if(bool){
            //获取该学生当前专题下任务
            TpTaskInfo t=new TpTaskInfo();
            t.setCourseid(tiList.get(0).getCourseid());
            t.setUserid(this.logined(request).getUserid());
            List<TpTaskInfo> taskStuList=this.tpTaskManager.getListbyStu(t,null);
            if(taskStuList!=null&&taskStuList.size()>0){
                //检测当前论题是否发布任务
                TpTaskInfo task=new TpTaskInfo();
                PageResult p=new PageResult();
                p.setPageSize(1);
                p.setPageNo(1);
                task.setTaskvalueid(topictheme.getTopicid());
                task.setCourseid(tiList.get(0).getCourseid());
                List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(task, p);
                if(taskList!=null&&taskList.size()>0&&taskList.get(0).getTaskstatus()!=null
                        &&!taskList.get(0).getTaskstatus().equals("1")&&!taskList.get(0).getTaskstatus().equals("3")){
                    for(TpTaskInfo tmpTask:taskStuList){
                        if(tmpTask.getTaskid().equals(taskList.get(0).getTaskid())
                                &&taskList.get(0).getCriteria()!=null&&taskList.get(0).getCriteria()==2){
                            TaskPerformanceInfo tp=new TaskPerformanceInfo();
                            tp.setTaskid(taskList.get(0).getTaskid());
                            tp.setTasktype(taskList.get(0).getTasktype());
                            tp.setCourseid(taskList.get(0).getCourseid());
                            //tp.getTaskinfo().setGroupid(gsList.get(0).getGroupid());
                            tp.setCriteria(2);//发主题
                            tp.setUserid(this.logined(request).getRef());
                            tp.setIsright(1);
                            List<TaskPerformanceInfo>tpList=this.taskPerformanceManager.getList(tp,null);
                            if(tpList==null||tpList.size()<1)
                                this.taskPerformanceManager.doSave(tp);
                        }
                    }
                }
            }
            jo.put("result", "1");
            jo.put("msg","添加成功");
        }else{
            jo.put("result","0");
            jo.put("msg","添加失败");
        }
        response.getWriter().print(jo.toString());
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
        returnJo.put("result",0);//默认失败
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
            returnJo.put("result",1);//成功
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
        returnJo.put("result",0);//默认失败
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
            returnJo.put("result",1);//成功
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
        returnJo.put("result",0);//默认失败
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
            returnJo.put("result",1);
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
        returnJo.put("result",1);
        response.getWriter().print(returnJo.toString());
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
        if(!ImUtilTool.ValidateRequestParam(request)){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());
            return null;
        }
        HashMap<String,String> paramMap=ImUtilTool.getRequestParam(request);
        String jid=paramMap.get("jid");
        String taskid=paramMap.get("taskId");
        String schoolid=paramMap.get("schoolId");
        String clsid=paramMap.get("classId");
        String paperid=paramMap.get("paperId");
        String sign=paramMap.get("sign");
        String time=paramMap.get("time");
        paramMap.remove("sign");

        if(jid==null||taskid==null||time==null||sign==null||schoolid==null||clsid==null||paperid==null){
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

        //验证该任务是否在有效期内
        TpTaskAllotInfo tpallot=new TpTaskAllotInfo();
        tpallot.setTaskid(tk.getTaskid());
        tpallot.setCourseid(tk.getCourseid());
        if(!this.tpTaskAllotManager.getYXTkCount(tpallot)){
            jsonEntity.setMsg("当前任务未开始或不存在!");
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
        }
        switch(tk.getTasktype().intValue()){
            case 4: //成卷测试
                if(!tk.getUsertypeid().toString().trim().equals(paperid.trim())){
                    jsonEntity.setMsg("任务与试卷不匹配!");
                    response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
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
        //得到年有题的分数
        mp.put("allquesidObj",allquesidObj);
        mp.put("paperObj",paperList.get(0));
        mp.put("taskid",taskid);
        mp.put("courseid",tk.getCourseid());
        mp.put("allquesidObj",allquesidObj);
        mp.put("quesSize",allquesidObj.toString().split(",").length);
        mp.put("paperid",paperid);
        return new ModelAndView("/imjsp-1.1/test/papertest",mp);
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
    @RequestMapping(params="getTaskPaperQuestion",method={RequestMethod.GET,RequestMethod.POST})
    public void getTaskPaperQuestion(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject returnJo=new JSONObject();
        returnJo.put("result",0);//默认失败
      /*  if(!ImUtilTool.ValidateRequestParam(request)){  //验证参数
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        } */
        HashMap<String,String> paramMap=ImUtilTool.getRequestParam(request);
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
            }
        }
        //获取提干
        PaperQuestion pq=new PaperQuestion();
        pq.setPaperid(paperid);
        PageResult p=new PageResult();
        p.setOrderBy("u.order_idx");
        p.setPageNo(0);
        p.setPageSize(0);
        List<PaperQuestion>pqList=this.paperQuestionManager.getList(pq,p);

        //获取试题组下题目
        PaperQuestion child =new PaperQuestion();
        child.setPaperid(pq.getPaperid());
        List<PaperQuestion>childList=this.paperQuestionManager.getPaperTeamQuestionList(child,null);


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
                map.put("isQesTeam",paperQuestion.getQuestiontype()==6?1:0); //6：试题组
                if(paperQuestion.getQuestionTeam()!=null&&paperQuestion.getQuestionTeam().size()>0){
                    Map<String,Object> childMap=new HashMap<String,Object>();
                    for(PaperQuestion qTeam:paperQuestion.getQuestionTeam()){
                        childMap.put("teamQesId",qTeam.getQuestionid());
                        childMap.put("teamQesType",qTeam.getQuestiontype()==1?1:0);
                    }
                    map.put("teamQesList",childMap);
                }
                returnMapList.add(map);
            }
        }


        JSONObject jo=new JSONObject();
        jo.put("testId",paperid);
        jo.put("quesList",returnMapList);

        returnJo.put("data",jo.toString());
        returnJo.put("result",1);
        response.getWriter().print(returnJo.toString());
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
                if(obj==null||obj.toString().trim().length()<1)
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
                if(obj==null||obj.toString().trim().length()<1||obj.toString().trim().equals("m"))
                    continue;
                Object val=request.getParameter(obj.toString());
                returnMap.put(obj.toString(),val.toString());
            }
        }
        return returnMap;
    }

    /**
     * 转换usertype
     * @param usertype
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
