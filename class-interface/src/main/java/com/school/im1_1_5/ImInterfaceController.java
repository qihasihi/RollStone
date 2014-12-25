package com.school.im1_1_5;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.control.base.BaseController;
import com.school.entity.UserInfo;
import com.school.im1_1.entity._interface.ImInterfaceInfo;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by yuechunyang on 14-12-25.
 */
@Controller
@RequestMapping(value="/im1.1.5")
public class ImInterfaceController extends BaseController {
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
            jo.put("msg", UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
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
        Boolean b = UrlSigUtil.verifySigSimple("StuClassCalendar", map, sig);
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
            String currentYear = currentDay.split("-")[0];
            if(Integer.parseInt(month)==Integer.parseInt(currentMonth)&&Integer.parseInt(year)==Integer.parseInt(currentYear)){
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
                                    o2.put("courseDate","");
                                }
                            }else{
                                o2.put("courseDate","");
                            }
                        }else{
                            o2.put("courseDate","");
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
                                o2.put("courseDate","");
                            }
                        }else{
                            o2.put("courseDate","");
                        }
                    }else{
                        o2.put("courseDate","");
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
                            o2.put("courseDate","");
                        }
                    }else{
                        o2.put("courseDate","");
                    }
                }else{
                    o2.put("courseDate","");
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
                            o2.put("courseDate","");
                        }
                    }else{
                        o2.put("courseDate","");
                    }
                }else{
                    o2.put("courseDate","");
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
}
