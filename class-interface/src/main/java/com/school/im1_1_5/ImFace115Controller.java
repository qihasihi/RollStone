package com.school.im1_1_5;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.control.base.BaseController;
import com.school.entity.ClassInfo;
import com.school.entity.TeacherInfo;
import com.school.entity.TermInfo;
import com.school.entity.UserInfo;
import com.school.entity.teachpaltform.*;
import com.school.entity.teachpaltform.paper.PaperInfo;
import com.school.entity.teachpaltform.paper.StuPaperLogs;
import com.school.entity.teachpaltform.paper.StuPaperQuesLogs;
import com.school.entity.teachpaltform.paper.StuViewMicVideoLog;
import com.school.im1_1.entity._interface.ImInterfaceInfo;
import com.school.im1_1.manager._interface.ImInterfaceManager;
import com.school.im1_1.manager.inter._interface.IImInterfaceManager;
import com.school.manager.inter.*;
import com.school.manager.inter.teachpaltform.*;
import com.school.manager.inter.teachpaltform.award.ITpStuScoreLogsManager;
import com.school.manager.inter.teachpaltform.award.ITpStuScoreManager;
import com.school.manager.inter.teachpaltform.paper.*;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by yuechunyang on 14-12-25.
 */
@Controller
@RequestMapping(value="/im1.1.5")
public class ImFace115Controller extends BaseController {

    @Resource(name="classManager")
    protected IClassManager classManager;
    @Resource(name="userManager")
    protected IUserManager userManager;
    @Resource(name="teacherManager")
    protected ITeacherManager teacherManager;
    @Resource(name="tpCourseClassManager")
    protected ITpCourseClassManager tpCourseClassManager;
    @Resource(name="tpStuScoreManager")
    protected ITpStuScoreManager tpStuScoreManager;
    @Resource(name="tpRecordManager")
    protected ITpRecordManager tpRecordManager;
    @Resource(name="paperManager")
    protected IPaperManager paperManager;
    @Resource(name="tpTaskManager")
    protected ITpTaskManager tpTaskManager;
    @Resource(name="stuPaperLogsManager")
    protected IStuPaperLogsManager stuPaperLogsManager;
    @Resource(name="paperQuestionManager")
    protected IPaperQuestionManager paperQuestionManager;
    @Resource(name="stuViewMicVideoLogManager")
    protected IStuViewMicVideoLogManager stuViewMicVideoLogManager;
    @Resource(name="taskPerformanceManager")
    protected ITaskPerformanceManager taskPerformanceManager;
    @Resource(name="tpTaskAllotManager")
    protected ITpTaskAllotManager tpTaskAllotManager;
    @Resource(name="stuPaperQuesLogsManager")
    protected IStuPaperQuesLogsManager stuPaperQuesLogsManager;
    @Resource(name="tpStuScoreLogsManager")
    protected ITpStuScoreLogsManager tpStuScoreLogsManager;
    @Resource(name="termManager")
    protected ITermManager termManager;
    @Resource(name = "tpCourseManager")
    protected ITpCourseManager tpCourseManager;
    @Resource(name="tpTeacherTeachMaterialManager")
    protected ITpTeacherTeachMaterialManager tpTeacherTeachMaterialManager;
    @Resource(name="teachingMaterialManager")
    protected ITeachingMaterialManager teachingMaterialManager;
    @Resource(name="tpCourseTeachingMaterialManager")
    protected ITpCourseTeachingMaterialManager tpCourseTeachingMaterialManager;
    @Resource(name="imInterfaceManager")
    protected IImInterfaceManager imInterfaceManager;
    @Resource(name="myInfoUserManager")
    protected IMyInfoUserManager myInfoUserManager;
    /**
     * ѧ���༶�༶�α�ӿ�
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
        if(!ImFace115Util.ValidateRequestParam(request)){
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
            response.getWriter().print("{\"result\":\"0\",\"message\":\"��֤ʧ�ܣ��Ƿ���¼\"}");
            return;
        }
        int utype = ImFace115Util.getUserType(usertype);
        UserInfo ui = new UserInfo();
        ui.setEttuserid(Integer.parseInt(userid));
        List<UserInfo> userList = this.userManager.getList(ui,null);
        if(userList==null||userList.size()<1){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"��ǰ�û�δ�󶨣�����ϵ����Ա\"}");
            return;
        }
        Integer schoolId=this.getDcSchoolIdByClassId(Integer.parseInt(classid));
        ImInterfaceInfo obj = new ImInterfaceInfo();
        obj.setSchoolid(schoolId);
        obj.setClassid(Integer.parseInt(classid));
        Map m = new HashMap();
        Map m2 = new HashMap();
        List<Map<String,Object>> courseList = this.imInterfaceManager.getStudentCalendar(userList.get(0).getUserid(),schoolId,Integer.parseInt(classid),Integer.parseInt(year),Integer.parseInt(month));
        if(utype!=2){
            long mTime = System.currentTimeMillis();
            int offset = Calendar.getInstance().getTimeZone().getRawOffset();
            Calendar c = Calendar.getInstance();
            c.setTime(new Date(mTime - offset));
            String currentDay =UtilTool.DateConvertToString(c.getTime(),UtilTool.DateType.type1);
            String currentMonth = currentDay.split("-")[1];
            String currentYear = currentDay.split("-")[0];
            if(Integer.parseInt(month)==Integer.parseInt(currentMonth)&&Integer.parseInt(year)==Integer.parseInt(currentYear)){
                List<Map<String,Object>> courseArray = this.imInterfaceManager.getstudentCalendarDetail(userList.get(0).getUserid(), utype, Integer.parseInt(classid), schoolId, currentDay);
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
                        //o2.put("courseDate",o.get("BEGIN_TIME").toString().substring(0,19)+"~"+(o.get("END_TIME")!=null?o.get("END_TIME").toString().substring(0,19):"����"));
                        o2.put("schoolId",o.get("DC_SCHOOL_ID"));
                        o2.put("classId",o.get("CLASS_ID"));
                        o2.put("classType",o.get("CLASS_TYPE"));
                        o2.put("className",o.get("CLASSNAME"));
                        //��ѯ���ñ���
                        Map o3 = new HashMap();
                        //���Ȳ���γ̵������Ϣ
                        TpCourseClass tc = new TpCourseClass();
                        tc.setCourseid(Long.parseLong(o.get("COURSE_ID").toString()));
                        tc.setClassid(Integer.parseInt(o.get("CLASS_ID").toString()));
                        List<TpCourseClass> tpCourseClassList = this.tpCourseClassManager.getList(tc,null);
                        if(tpCourseClassList!=null&&tpCourseClassList.size()>0){
                            List<Map<String,Object>> scoreList = tpStuScoreManager.getPageDataList(tpCourseClassList.get(0).getCourseid(),Long.parseLong(tpCourseClassList.get(0).getClassid().toString()),1,tpCourseClassList.get(0).getSubjectid(),null,userList.get(0).getUserid());
                            if(scoreList!=null&&scoreList.size()>0){
                                o3.put("personTotalScore",scoreList.get(0).get("COURSE_TOTAL_SCORE")!=null?Integer.parseInt(scoreList.get(0).get("COURSE_TOTAL_SCORE").toString()):0);
                                //����С��÷ֺͱ���
                                int teamScore = 0;
                                String teamShow = "";
                                if(Integer.parseInt(scoreList.get(0).get("GSCORE1").toString())>0){
                                    teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE1").toString());
                                    teamShow+="���ڳ�Աȫ���������޳ٵ�����";
                                }
                                if(Integer.parseInt(scoreList.get(0).get("GSCORE2").toString())>0){
                                    teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE2").toString());
                                    teamShow+="/r/n����Ц��������ȫ���һ";
                                }
                                if(Integer.parseInt(scoreList.get(0).get("GSCORE3").toString())>0){
                                    teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE3").toString());
                                    teamShow+="/r/n����С����������ȫ���һ";
                                }
                                if(Integer.parseInt(scoreList.get(0).get("GSCORE4").toString())>0){
                                    teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE4").toString());
                                    teamShow+="/r/n����Υ�����ɴ�����ȫ���һ";
                                }
                                if(Integer.parseInt(scoreList.get(0).get("GSCORE5").toString())>0){
                                    teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE5").toString());
                                    teamShow+="/r/n�������������������ʣ�С����������ʣ���ȫ���һ";
                                }
                                o3.put("teamShow",teamShow);
                                o3.put("teamScore",teamScore);

                                o3.put("taskScore",scoreList.get(0).get("TASK_SCORE")!=null?Integer.parseInt(scoreList.get(0).get("TASK_SCORE").toString()):0);
                                o3.put("presenceScore",scoreList.get(0).get("ATTENDANCENUM")!=null?Integer.parseInt(scoreList.get(0).get("ATTENDANCENUM").toString()):0);
                                o3.put("smileScore",scoreList.get(0).get("SMILINGNUM")!=null?Integer.parseInt(scoreList.get(0).get("SMILINGNUM").toString()):0);
                                o3.put("illegalScore",scoreList.get(0).get("VIODATIONDISNUM")!=null?Integer.parseInt(scoreList.get(0).get("VIODATIONDISNUM").toString()):0);
                                //���µ÷�
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
            m.put("msg","��ǰû��ר��");
        }
        m.put("result","1");
        m.put("msg","�ɹ�");
        m.put("data",m2);
        JSONObject object = JSONObject.fromObject(m);
        response.getWriter().print(object.toString());
    }

    /**
     * ѧ���༶�α�ӿ���ϸ
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
        if(!ImFace115Util.ValidateRequestParam(request)){
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
            response.getWriter().print("{\"result\":\"0\",\"message\":\"��֤ʧ�ܣ��Ƿ���¼\"}");
            return;
        }
        int utype = ImFace115Util.getUserType(usertype);
        UserInfo ui = new UserInfo();
        ui.setEttuserid(Integer.parseInt(userid));
        List<UserInfo> userList = this.userManager.getList(ui, null);
        if(userList==null||userList.size()<1){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"��ǰ�û�δ�󶨣�����ϵ����Ա\"}");
            return;
        }
        Integer schoolId=this.getDcSchoolIdByClassId(Integer.parseInt(classid));
        List<Map<String,Object>> courseArray = this.imInterfaceManager.getstudentCalendarDetail(userList.get(0).getUserid(), utype,Integer.parseInt(classid), schoolId, currentDay);
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
                //o2.put("courseDate",o.get("BEGIN_TIME").toString().substring(0,19)+"~"+(o.get("END_TIME")!=null?o.get("END_TIME").toString().substring(0,19):"����"));
                o2.put("schoolId",o.get("DC_SCHOOL_ID"));
                o2.put("classId",o.get("CLASS_ID"));
                o2.put("classType",o.get("CLASS_TYPE"));
                o2.put("className",o.get("CLASSNAME"));
                //��ѯ���ñ���
                Map o3 = new HashMap();
                //���Ȳ���γ̵������Ϣ
                TpCourseClass tc = new TpCourseClass();
                tc.setCourseid(Long.parseLong(o.get("COURSE_ID").toString()));
                tc.setClassid(Integer.parseInt(o.get("CLASS_ID").toString()));
                List<TpCourseClass> tpCourseClassList = this.tpCourseClassManager.getList(tc,null);
                if(tpCourseClassList!=null&&tpCourseClassList.size()>0){
                    List<Map<String,Object>> scoreList = tpStuScoreManager.getPageDataList(tpCourseClassList.get(0).getCourseid(),Long.parseLong(tpCourseClassList.get(0).getClassid().toString()),1,tpCourseClassList.get(0).getSubjectid(),null,userList.get(0).getUserid());
                    if(scoreList!=null&&scoreList.size()>0){
                        o3.put("personTotalScore",scoreList.get(0).get("COURSE_TOTAL_SCORE")!=null?Integer.parseInt(scoreList.get(0).get("COURSE_TOTAL_SCORE").toString()):0);
                        //����С��÷ֺͱ���
                        int teamScore = 0;
                        String teamShow = "";
                        if(Integer.parseInt(scoreList.get(0).get("GSCORE1").toString())>0){
                            teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE1").toString());
                            teamShow+="���ڳ�Աȫ���������޳ٵ�����";
                        }
                        if(Integer.parseInt(scoreList.get(0).get("GSCORE2").toString())>0){
                            teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE2").toString());
                            teamShow+="/r/n����Ц��������ȫ���һ";
                        }
                        if(Integer.parseInt(scoreList.get(0).get("GSCORE3").toString())>0){
                            teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE3").toString());
                            teamShow+="/r/n����С����������ȫ���һ";
                        }
                        if(Integer.parseInt(scoreList.get(0).get("GSCORE4").toString())>0){
                            teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE4").toString());
                            teamShow+="/r/n����Υ�����ɴ�����ȫ���һ";
                        }
                        if(Integer.parseInt(scoreList.get(0).get("GSCORE5").toString())>0){
                            teamScore+=Integer.parseInt(scoreList.get(0).get("GSCORE5").toString());
                            teamShow+="/r/n�������������������ʣ�С����������ʣ���ȫ���һ";
                        }
                        o3.put("teamShow",teamShow);
                        o3.put("teamScore",teamScore);

                        o3.put("taskScore",scoreList.get(0).get("TASK_SCORE")!=null?Integer.parseInt(scoreList.get(0).get("TASK_SCORE").toString()):0);
                        o3.put("presenceScore",scoreList.get(0).get("ATTENDANCENUM")!=null?Integer.parseInt(scoreList.get(0).get("ATTENDANCENUM").toString()):0);
                        o3.put("smileScore",scoreList.get(0).get("SMILINGNUM")!=null?Integer.parseInt(scoreList.get(0).get("SMILINGNUM").toString()):0);
                        o3.put("illegalScore",scoreList.get(0).get("VIODATIONDISNUM")!=null?Integer.parseInt(scoreList.get(0).get("VIODATIONDISNUM").toString()):0);
                        //���µ÷�
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
        m.put("msg","�ɹ�");
        m.put("data",m2);
        JSONObject object = JSONObject.fromObject(m);
        response.getWriter().print(object.toString());
    }

    /**
     *  ��ӿγ̼�ʵ�ӿ�
     *    jid���û�id
     *    schoolId��schoolId
     *    classId���༶id
     *    courseId���γ�id
     *    attachDescribe����������
     *    attach��������ַ�ַ�����JSON����
     *    time:ʱ���
     *    sign:Md5��
     *
     * @param request
     * @param response
     */
    @RequestMapping(params="m=AddCourseRecord",method={RequestMethod.GET,RequestMethod.POST})
    public void AddCourseRecord(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject returnJo=new JSONObject();
        returnJo.put("result","0");//Ĭ��ʧ��
        if(!ImFace115Util.ValidateRequestParam(request)){  //��֤����
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> paramMap=ImFace115Util.getRequestParam(request);
        //��ȡ����
        String jid=paramMap.get("jid");
       // String schoolId=paramMap.get("schoolId");
        String classId=paramMap.get("classId");
        String courseId=paramMap.get("courseId");
        String content=paramMap.get("attachDescribe");
        String attach=paramMap.get("attach");
        String time=paramMap.get("time");
        String sign=paramMap.get("sign");
        if(jid==null||classId==null||time==null||sign==null){
            returnJo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            returnJo.put("data","");
            response.getWriter().println(returnJo.toString());return;
        }
        //��֤ʱ��
        //��֤�Ƿ�����������
        Long ct=Long.parseLong(time.toString());
        Long nt=new Date().getTime();
        double d=(nt-ct)/(1000*60);
        if(d>3){//����������
            returnJo.put("msg","������Ӧ��ʱ!�ӿ�����������Ч!");
            response.getWriter().print(returnJo.toString());
            return;
        }

        //ȥ��sign
        paramMap.remove("sign");
        //��֤Md5
        Boolean b = UrlSigUtil.verifySigSimple("AddCourseRecord",paramMap,sign);
        if(!b){
            returnJo.put("msg","��֤ʧ�ܣ��Ƿ���¼!");
            response.getWriter().print(returnJo.toString());
            return;
        }

        //��֤�û�ETTUserId,�õ�Userid
        UserInfo validateUid=new UserInfo();
        validateUid.setEttuserid(Integer.parseInt(jid.trim()));
        List<UserInfo> userList=this.userManager.getList(validateUid,null);
        if(userList==null||userList.size()<1||userList.get(0)==null||userList.get(0).getUserid()==null){
            returnJo.put("msg","��ǰ���ʺ�δ��!");
            response.getWriter().println(returnJo.toString());return;
        }
        Integer dcschoolId=this.getDcSchoolIdByClassId(Integer.parseInt(classId.trim()));


        Integer userid=userList.get(0).getUserid();

        TpRecordInfo tpRecordInfo=new TpRecordInfo();
        tpRecordInfo.setUserId(Long.parseLong(userid.toString()));
        tpRecordInfo.setClassId(Long.parseLong(classId.trim()));
        tpRecordInfo.setCourseId(Long.parseLong(courseId.trim()));
        if(dcschoolId!=null)
            tpRecordInfo.setDcSchoolId(Long.parseLong(dcschoolId+""));
        //��ѯ�Ƿ��Ѿ�����
//        List<TpRecordInfo> tpRecordInfoList=this.tpRecordManager.getList(tpRecordInfo,null);
//        if(tpRecordInfoList!=null&&tpRecordInfoList.size()>0){
//            returnJo.put("msg","���ʧ��!�Ѿ����ڸ���Ϣ!");
//            response.getWriter().println(returnJo.toString());return;
//        }
        if(attach!=null)
            tpRecordInfo.setImgUrl(attach.trim());
        if(content!=null)
            tpRecordInfo.setContent(content.trim());
        //���
        if(this.tpRecordManager.doSave(tpRecordInfo)){
            returnJo.put("result","1");//�ɹ�
            returnJo.put("msg","��ӿ��ü�ʵ�ɹ�!");
        }else
            returnJo.put("msg",UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().println(returnJo.toString());
    }

    /**
     * ��ѯ�γ̼�ʵ�ӿ�
     *   classId:�༶id
     *   schoolId:schoolId
     *   jid:�û�id
     *   userType:�û�����
     *   courseId: �γ�id
     *   time:��������ʱ���
     *   sign:Md5��֤��
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=GetClassRecord",method={RequestMethod.GET,RequestMethod.POST})
    public void GetClassRecord(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject returnJo=new JSONObject();
        returnJo.put("result","0");//Ĭ��ʧ��
        if(!ImFace115Util.ValidateRequestParam(request)){  //��֤����
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> paramMap=ImFace115Util.getRequestParam(request);
        //��ȡ����
        String jid=paramMap.get("jid");
       // String schoolId=paramMap.get("schoolId");
        String classId=paramMap.get("classId");
        String userType=paramMap.get("userType");
        String courseId=paramMap.get("courseId");
        String time=paramMap.get("time");
        String sign=paramMap.get("sign");
        if(jid==null||time==null||sign==null||classId==null||courseId==null){
            returnJo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(returnJo.toString());return;
        }
        //��֤ʱ��
        //��֤�Ƿ�����������
        Long ct=Long.parseLong(time.toString());
        Long nt=new Date().getTime();
        double d=(nt-ct)/(1000*60);
        if(d>3){//����������
            returnJo.put("msg","������Ӧ��ʱ!�ӿ�����������Ч!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        //ȥ��sign
        paramMap.remove("sign");
        //��֤Md5
        Boolean b = UrlSigUtil.verifySigSimple("GetClassRecord",paramMap,sign);
        if(!b){
            returnJo.put("msg","��֤ʧ�ܣ��Ƿ���¼!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        //��֤��jid�Ƿ��Ѿ���
        //��֤�û�ETTUserId,�õ�Userid
        UserInfo validateUid=new UserInfo();
        validateUid.setEttuserid(Integer.parseInt(jid.trim()));
        List<UserInfo> userList=this.userManager.getList(validateUid,null);
        if(userList==null||userList.size()<1||userList.get(0)==null||userList.get(0).getUserid()==null){
            returnJo.put("msg","��ǰ���ʺ�δ��!");
            response.getWriter().println(returnJo.toString());return;
        }
        Integer userid=userList.get(0).getUserid();
        //��ѯ����
        TpRecordInfo entity=new TpRecordInfo();
        //entity.setUserId(Long.parseLong(userid.toString()));
        Integer dcSchoolId=this.getDcSchoolIdByClassId(Integer.parseInt(classId.trim()));
        if(dcSchoolId!=null)
          entity.setDcSchoolId(Long.parseLong(dcSchoolId+""));
        entity.setClassId(Long.parseLong(classId.trim()));
        entity.setCourseId(Long.parseLong(courseId.trim()));
        List<TpRecordInfo> recordList=this.tpRecordManager.getList(entity,null);
        List<Map<String,Object>> returnMapList=new ArrayList<Map<String, Object>>();
        JSONObject jo=new JSONObject();
        if(recordList==null||recordList.size()<1){
            jo.put("recordList",returnMapList);

            returnJo.put("msg","û������!");
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
     *�Ծ�����
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=testDetail",method={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView imToPaperDetail(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        if(!ImFace115Util.ValidateRequestParam(request)){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());
            return null;
        }
        HashMap<String,String> paramMap=ImFace115Util.getRequestParam(request);
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
        //��ѯ�û��Ƿ��Ѿ�����
        //��֤paperid�Ƿ����
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
        if(userType!=null&&Integer.parseInt(userType)!=2){
            TpTaskInfo t=new TpTaskInfo();
            t.setUserid(uid);
            t.setTaskid(Long.parseLong(taskid));
            t.setCourseid(tkList.get(0).getCourseid());
            pr.setPageSize(1);
            // ѧ������
            taskList=this.tpTaskManager.getUnionListbyStu(t, pr);
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
        }



        //��֤�Ƿ��Ѿ�����
        if(userType!=null&&Integer.parseInt(userType)!=2&&!isendTask){  //�������ʦ���룬�����������������֤�Ƿ��ύ
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
                userType="1"; //������ڼ�¼����ֱ����ʾ
        }
        //�������û����������������Ϣ

        //�õ���ǰ����������
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

        //��֤��ǰ���Ƿ���allquesidObj��
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
                jsonEntity.setMsg("���Ծ��в����ڸ���!���ʶ��"+quesid);
                response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
            }
            mp.put("quesid",quesid);

        }
        //���ط���
//        List<Map<String,Object>> scoreMapList=this.paperQuestionManager.getPaperQuesAllScore(Long.parseLong(paperid.trim()),null,taskList.get(0).getCourseid());
//        if(scoreMapList==null||scoreMapList.size()<1||!scoreMapList.get(0).containsKey("SCORE")||scoreMapList.get(0).get("SCORE")==null){
//            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
//            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
//        }
        tk=tkList.get(0);
        //�õ�������ķ���
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
        return new ModelAndView("/imjsp-1.1/im1.1.5/test/testdetail",mp);
    }


    /**
     * IM�ֻ������Ծ����ҳ�档
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
        HashMap<String,String> paramMap=ImFace115Util.getRequestParam(request);
        String jid=paramMap.get("jid");
        String taskid=paramMap.get("taskId");
       // String schoolid=paramMap.get("schoolId");
        String clsid=paramMap.get("classId");
        String paperid=paramMap.get("paperId");
        String userType=paramMap.get("userType");
        String quesid=paramMap.get("quesId");
        String sign=paramMap.get("sign");
        String time=paramMap.get("time");
        paramMap.remove("sign");

        if(userType==null||jid==null||taskid==null||time==null||sign==null/*||schoolid==null*/
                ||clsid==null||paperid==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
        }
        //��֤ʱ��
        //��֤�Ƿ�����������
        Long ct=Long.parseLong(time.toString());
        Long nt=new Date().getTime();
        double d=(nt-ct)/(1000*60);
        if(d>3){//����������
            jsonEntity.setMsg("������Ӧ��ʱ!�ӿ�����������Ч!");
            response.getWriter().print(jsonEntity.getAlertMsgAndBack());
            return null;
        }
        //ȥ��sign
        paramMap.remove("sign");
        //��֤Md5
        Boolean b = UrlSigUtil.verifySigSimple("toTestPaper",paramMap,sign);
        if(!b){
            jsonEntity.setMsg("��֤ʧ�ܣ��Ƿ���¼!");
            response.getWriter().print(jsonEntity.getAlertMsgAndBack());
            return null;
        }
        //��֤��jid�Ƿ��Ѿ���
        //��֤�û�ETTUserId,�õ�Userid
        UserInfo validateUid=new UserInfo();
        validateUid.setEttuserid(Integer.parseInt(jid.trim()));
        List<UserInfo> userList=this.userManager.getList(validateUid,null);
        if(userList==null||userList.size()<1||userList.get(0)==null||userList.get(0).getUserid()==null){
            jsonEntity.setMsg("��ǰ���ʺ�δ��!");
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
        }
        Integer userid=userList.get(0).getUserid();
        //�õ�������Ϣ
        TpTaskInfo tk=new TpTaskInfo();
        tk.setTaskid(Long.parseLong(taskid.trim()));
        List<TpTaskInfo> tkList=this.tpTaskManager.getList(tk,null);
        if(tkList==null||tkList.size()<1){
            jsonEntity.setMsg("��ǰ���񲻴���!");
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
        }
        tk=tkList.get(0);

        PageResult presult=new PageResult();
        presult.setPageSize(1);

        //��֤���û��Ƿ��Ѿ��ύ�����
        StuPaperLogs splogs=new StuPaperLogs();
        splogs.setUserid(userid);
        splogs.setTaskid(tk.getTaskid());
        splogs.setPaperid(Long.parseLong(paperid.trim()));
        List<StuPaperLogs> splogsList=this.stuPaperLogsManager.getList(splogs,presult);

        String baseUrl=request.getSession().getAttribute("IP_PROC_NAME")==null?"":request.getSession().getAttribute("IP_PROC_NAME").toString();
        Integer uType=ImFace115Util.getUserType(userType);
        if(uType==2||uType==3){ //�������ʦ��ֱ�ӽ�������ҳ��

            //��������⣬��ֱ����
            StringBuilder directBuilder=new StringBuilder(baseUrl+"im1.1.5?m=testDetail&userid=")
                    .append(userid).append("&taskid=").append(tk.getTaskid())
                    .append("&paperid=").append(paperid).append("&courseid=").append(tk.getCourseid())
                    .append("&classid=").append(clsid).append("&userType=").append(uType);
            if(quesid!=null&&quesid.trim().length()>0){
                directBuilder.append("&quesId=").append(quesid);
            }
            response.sendRedirect(directBuilder.toString());
            return null;
        }

        //�õ��Ծ�
        PaperInfo p=new PaperInfo();
        p.setPaperid(Long.parseLong(paperid));
        List<PaperInfo> paperList=this.paperManager.getList(p,null);
        if(paperList==null||paperList.size()<1){
            jsonEntity.setMsg("�Ծ�ƥ��!");
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
        }

        switch(tk.getTasktype().intValue()){
            case 4: //�ɾ����
                if(!tk.getTaskvalueid().toString().trim().equals(paperid.trim())){
                    jsonEntity.setMsg("�������Ծ�ƥ��!");
                    response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
                }
                break;
            case 6:
                //�����Ƶ�����¼
                StuViewMicVideoLog svmvlog=new StuViewMicVideoLog();
                svmvlog.setMicvideoid(tk.getTaskvalueid());
                svmvlog.setUserid(userid);
                //��֤�Ƿ��Ѿ��鿴����
                List<StuViewMicVideoLog> stuViewMList=this.stuViewMicVideoLogManager.getList(svmvlog,presult);
                if(stuViewMList==null||stuViewMList.size()<1){
                    //��������ؼ�¼(΢��Ƶ)
                    TaskPerformanceInfo tpf=new TaskPerformanceInfo();
                    tpf.setCourseid(tk.getCourseid());
                    tpf.setTaskid(tk.getTaskid());
                    tpf.setUserid(userList.get(0).getRef());
                    tpf.setIsright(1);
                    tpf.setTasktype(tk.getTasktype());
                    tpf.setCriteria(1);//�����΢��Ƶ
                    if(!this.taskPerformanceManager.doSave(tpf)){
                        jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
                        response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
                    }
                    //���û�У������
                    if(!this.stuViewMicVideoLogManager.doSave(svmvlog)){
                        jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
                        response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
                    }
                }
                break;
        }

        //��֤�������Ƿ�����Ч����
        TpTaskAllotInfo tpallot=new TpTaskAllotInfo();
        tpallot.setTaskid(tk.getTaskid());
        tpallot.getUserinfo().setUserid(userid);
        if(!this.tpTaskAllotManager.getYXTkCount(tpallot)){
            //��������ڣ���ֱ�Ӳ鿴����ҳ��
            StringBuilder directBuilder=new StringBuilder(baseUrl+"im1.1.5?m=testDetail&userid=")
                    .append(userid).append("&taskid=").append(tk.getTaskid())
                    .append("&paperid=").append(p.getPaperid()).append("&courseid=").append(tk.getCourseid())
                    .append("&classid=").append(clsid).append("&userType=").append((uType==3?1:uType));//����Ǽҳ���ͨ�������֤�����ߺ��ӵ����̡�
            if(quesid!=null&&quesid.trim().length()>0){
                directBuilder.append("&quesId=").append(quesid);
            }
            response.sendRedirect(directBuilder.toString());
//            jsonEntity.setMsg("��ǰ����δ��ʼ�򲻴���!");
//            response.getWriter().println(jsonEntity.getAlertMsgAndBack());
            return null;
        }



        if(splogsList!=null&&splogsList.size()>0){
            StringBuilder directBuilder=new StringBuilder(baseUrl+"im1.1.5?m=testDetail&userid=")
                    .append(userid).append("&taskid=").append(tk.getTaskid())
                    .append("&paperid=").append(p.getPaperid()).append("&courseid=").append(tk.getCourseid())
                    .append("&classid=").append(clsid).append("&userType=").append((uType==3?1:uType));//����Ǽҳ���ͨ�������֤�����ߺ��ӵ����̡�
            if(quesid!=null&&quesid.trim().length()>0){
                directBuilder.append("&quesId=").append(quesid);
            }
            response.sendRedirect(directBuilder.toString());
            return null;
        }


        //�õ��������ID
        //�õ���ǰ����������
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
        //��֤��ǰ���Ƿ���allquesidObj��
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
                jsonEntity.setMsg("���Ծ��в����ڸ���!���ʶ��"+quesid);
                response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
            }
        }



        //�õ����û��Ѿ��������
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
        //�õ�������ķ���
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
        return new ModelAndView("/imjsp-1.1/im1.1.5/test/papertest",mp);
    }
    /**
     * �����û��ֱ���ӿ�
     *  ˵���������û��֡����߱���ӿ�
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=saveUserScore",method = RequestMethod.POST)
    public void doSaveUserScore(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JSONObject returnJo=new JSONObject();
        returnJo.put("result","0");//Ĭ��ʧ��
        if(!ImFace115Util.ValidateRequestParam(request)){  //��֤����
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> paramMap=ImFace115Util.getRequestParam(request);
        //��ȡ����
        String taskId=paramMap.get("taskId");
        String jid=paramMap.get("jid");
        String classId=paramMap.get("classId");
       // String schoolId=paramMap.get("schoolId");
        String time=paramMap.get("time");
        String sign=paramMap.get("sign");
        if(taskId==null||/*schoolId==null||*/time==null||sign==null||jid==null||classId==null){
            returnJo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(returnJo.toString());return;
        }
        //��֤ʱ��
        //��֤�Ƿ�����������
        Long ct=Long.parseLong(time.toString());
        Long nt=new Date().getTime();
        double d=(nt-ct)/(1000*60);
        if(d>3){//����������
            returnJo.put("msg","������Ӧ��ʱ!�ӿ�����������Ч!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        //ȥ��sign
        paramMap.remove("sign");
        //��֤Md5
        Boolean b = UrlSigUtil.verifySigSimple("saveUserScore",paramMap,sign);
        if(!b){
            returnJo.put("msg","��֤ʧ�ܣ��Ƿ���¼!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        Integer schoolId=this.getDcSchoolIdByClassId(Integer.parseInt(classId));
        UserInfo u=new UserInfo();
        u.setEttuserid(Integer.parseInt(jid));
        u.setDcschoolid(schoolId);
        List<UserInfo>userList=this.userManager.getList(u,null);
        if(userList==null||userList.size()<1){
            returnJo.put("msg","��ǰ���ʺ�δ��!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        UserInfo tmpUser=userList.get(0);
        //��֤���� �Ƿ����
        TpTaskInfo tk=new TpTaskInfo();
        tk.setTaskid(Long.parseLong(taskId.trim()));
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        List<TpTaskInfo> tpTaskList=this.tpTaskManager.getList(tk,presult);
        if(tpTaskList==null||tpTaskList.size()<1){
            returnJo.put("msg","���󣬵�ǰ���񲻴���!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        tk=tpTaskList.get(0);
        //��֤�����Ƿ�����Ч����
        //��֤�������Ƿ�����Ч����
        TpTaskAllotInfo tpallot=new TpTaskAllotInfo();
        tpallot.setTaskid(tk.getTaskid());
        tpallot.getUserinfo().setUserid(tmpUser.getUserid());
        if(!this.tpTaskAllotManager.getYXTkCount(tpallot)){
            returnJo.put("msg", "��ǰ����δ��ʼ�򲻴���!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        //�������֣�����
        //taskinfo:   4:�ɾ����  5����������   6:΢��Ƶ
        //����ת��:    6             7         8
        Integer type=0;
        switch(tk.getTasktype()){
            case 3:     //����
                type=1;break;
            case 1:     //��Դѧϰ
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
        //                /*�����ӷ�ͨ��*/
        if(this.tpStuScoreLogsManager.awardStuScore(tk.getCourseid()
                ,Long.parseLong(classId.trim())
                ,tk.getTaskid()
                ,Long.parseLong(tmpUser.getUserid()+""),jid,type,tmpUser.getDcschoolid())){
            returnJo.put("msg", "��ϲ��,�����1���ֺ�1����ʯ");
            returnJo.put("result","1");
        }else{
            returnJo.put("msg", "���󣬽����ӷ�ʧ�ܣ�ԭ�򣺸������Ѿ�������ؼ�¼");
        }
        response.getWriter().println(returnJo.toString());
    }

    /**
     * ���ר��ǰ�ýӿ�
     */
    @RequestMapping(params = "m=getCourseSubjectAndClass", method = RequestMethod.POST)
    public void getCourseSubjectAndClass(HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        JSONObject returnJo=new JSONObject();
        returnJo.put("result","0");//Ĭ��ʧ��
        if(!ImFace115Util.ValidateRequestParam(request)){  //��֤����
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> paramMap=ImFace115Util.getRequestParam(request);
        //��ȡ����
        String jid=paramMap.get("jid");
        String schoolId=paramMap.get("schoolId");
        String classid = paramMap.get("classId");
        String time=paramMap.get("time");
        String sign=paramMap.get("sign");
        TermInfo termInfo=this.termManager.getAutoTerm();
        if(time==null||sign==null||jid==null||schoolId==null){
            returnJo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(returnJo.toString());return;
        }
        //ȥ��sign
        paramMap.remove("sign");
        //��֤Md5
        Boolean b = UrlSigUtil.verifySigSimple("getCourseSubjectAndClass",paramMap,sign);
        if(!b){
            returnJo.put("msg","��֤ʧ�ܣ��Ƿ���¼!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        //��֤�û�
        UserInfo u=new UserInfo();
        u.setEttuserid(Integer.parseInt(jid));
        u.setDcschoolid(Integer.parseInt(schoolId));
        List<UserInfo>userList=this.userManager.getList(u,null);
        if(userList==null||userList.size()<1){
            returnJo.put("msg","��ǰ���ʺ�δ��!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        u = userList.get(0);
        TeacherInfo t = new TeacherInfo();
        t.setUserid(u.getRef());
        List<TeacherInfo> tl = this.teacherManager.getList(t, null);
        if (tl == null || tl.size() == 0) {
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg", "��ʦ������!");
            jo.put("data", "");
            response.getWriter().print(jo.toString());
            return;
        }
        List<Map<String,Object>> classList = this.imInterfaceManager.getClassInfoForCourse(u.getRef(),Integer.parseInt(classid));
        String subjectids = "";
        List subList = new ArrayList();
        Map classMap = new HashMap();
        if(classList!=null&&classList.size()>0){
            JSONArray jr = new JSONArray();
            for(int i = 0;i<classList.size();i++){
                JSONObject jo = new JSONObject();
                classMap = classList.get(i);
                Boolean si = false;
                for(int j = 0;j<subList.size();j++){
                    if(Integer.parseInt(classMap.get("SUBJECTID").toString())==Integer.parseInt(subList.get(j).toString())){
                        si=true;
                    }
                }
                if(!si){
                    subList.add(classMap.get("SUBJECTID"));
                }
                jo.put("classId",classMap.get("CLASSID"));
                jo.put("className",classMap.get("CLASSNAME"));
                jo.put("subjectId",classMap.get("SUBJECTID"));
                jo.put("gradeId",classMap.get("GRADEID"));
                jr.add(jo);
            }
            if(subList.size()>0){
                for(int j = 0;j<subList.size();j++){
                    subjectids+=subList.get(j);
                    if(j<subList.size()-1)
                        subjectids+=",";
                }
            }
            returnJo.put("result","1");
            Map d = new HashMap();
            d.put("classes",jr);
            d.put("subjectIds",subjectids);
            returnJo.put("data",d);
        }else{
            returnJo.put("msg","�༶����������鿴������!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        response.getWriter().print(returnJo.toString());
    }


    /**
     * ���ר��
     */
    @RequestMapping(params = "m=createCourse", method = RequestMethod.POST)
    public void doAddCourse(HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        JSONObject returnJo=new JSONObject();
        returnJo.put("result","0");//Ĭ��ʧ��
        if(!ImFace115Util.ValidateRequestParam(request)){  //��֤����
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> paramMap=ImFace115Util.getRequestParam(request);
        //��ȡ����
        String courseName=paramMap.get("courseName");
        String subjectId=paramMap.get("subjectId");
        String gradeId=paramMap.get("gradeId");
        String jid=paramMap.get("jid");
        String schoolId=paramMap.get("schoolId");
        String classArray = paramMap.get("classArray");
        String time=paramMap.get("time");
        String sign=paramMap.get("sign");
        TermInfo termInfo=this.termManager.getAutoTerm();
        if(time==null||sign==null||jid==null||schoolId==null||classArray==null||courseName==null){
            returnJo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(returnJo.toString());return;
        }
        //ȥ��sign
        paramMap.remove("sign");
        paramMap.remove("classArray");
        //��֤Md5
        String s = UrlSigUtil.makeSigSimple("createCourse",paramMap);
        Boolean b = UrlSigUtil.verifySigSimple("createCourse",paramMap,sign);
        if(!b){
            returnJo.put("msg","��֤ʧ�ܣ��Ƿ���¼!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        //��֤�û�
        UserInfo u=new UserInfo();
        u.setEttuserid(Integer.parseInt(jid));
        u.setDcschoolid(Integer.parseInt(schoolId));
        List<UserInfo>userList=this.userManager.getList(u,null);
        if(userList==null||userList.size()<1){
            returnJo.put("msg","��ǰ���ʺ�δ��!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        u = userList.get(0);
        List<String> sqlListArray = new ArrayList<String>();
        List<List<Object>> objListArray = new ArrayList<List<Object>>();
        List<Object> objList = null;
        StringBuilder sql = null;

        ////////////////////////// ���ר�⣬������ز���
        TpCourseInfo tc=new TpCourseInfo();
        Long courseid = this.tpCourseManager.getNextId(true);
        tc.setCourseid(courseid);
        tc.setCuserid(u.getUserid());

        TeacherInfo t = new TeacherInfo();
        t.setUserid(u.getRef());
        List<TeacherInfo> tl = this.teacherManager.getList(t, null);
        if (tl == null || tl.size() == 0) {
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg", "��ʦ������!");
            jo.put("data", "");
            response.getWriter().print(jo.toString());
            return;
        }
        tc.setTeachername(tl.get(0).getTeachername());
        tc.setCoursename(courseName);
        tc.setDcschoolid(Integer.parseInt(schoolId));
        sql = new StringBuilder();
        objList = this.tpCourseManager.getSaveSql(tc, sql);
        if (sql == null || objList == null) {
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg", "���ר��ʧ��!");
            jo.put("data", "");
            response.getWriter().print(jo.toString());
            return;
        }
        sqlListArray.add(sql.toString());
        objListArray.add(objList);

        // ���ר��̲Ĺ��������Ȳ�ѯ��ʦ��ǰ�꼶ѧ�ƵĽ̲ģ����û�У�˵����ʦ��û����ӹ����õ�ǰ�꼶ѧ�������̲�
        TpTeacherTeachMaterial teaMaterial = new TpTeacherTeachMaterial();
        teaMaterial.setUserid(u.getUserid());
        teaMaterial.setSubjectid(Integer.parseInt(subjectId));
        teaMaterial.setGradeid(Integer.parseInt(gradeId));
        teaMaterial.setTermid(termInfo.getRef());
        List<TpTeacherTeachMaterial> teaMaterialList = this.tpTeacherTeachMaterialManager.getList(teaMaterial,null);
        //����̲�ר����������
        TpCourseTeachingMaterial courseMaterial = new TpCourseTeachingMaterial();
        courseMaterial.setCourseid(courseid);
        if(teaMaterialList!=null&&teaMaterialList.size()>0){
            if(teaMaterialList.size()>1){
                returnJo.put("msg","��ǰ��ʦ���̲���Ϣ���󣬲������ר��");
                response.getWriter().print(returnJo.toString());
                return;
            }
            courseMaterial.setTeachingmaterialid(teaMaterialList.get(0).getMaterialid());
        }else{
            TeachingMaterialInfo materialInfo = new TeachingMaterialInfo();
            materialInfo.setSubjectid(Integer.parseInt(subjectId));
            materialInfo.setGradeid(Integer.parseInt(gradeId));
            materialInfo.setVersionid(-1);
            List<TeachingMaterialInfo> materialList = this.teachingMaterialManager.getList(materialInfo,null);
            if(materialList==null||materialList.size()>1){
                returnJo.put("msg","��ȡĬ�Ͻ̲ĳ������½pc����ʦ���ý̲�");
                response.getWriter().print(returnJo.toString());
                return;
            }
            courseMaterial.setTeachingmaterialid(materialList.get(0).getMaterialid());
        }
        sql = new StringBuilder();
        objList = new ArrayList<Object>();
        objList = this.tpCourseTeachingMaterialManager.getSaveSql(courseMaterial,sql);
        if (sql == null || objList == null) {
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg", "���ר��̲�ʧ��!");
            jo.put("data", "");
            response.getWriter().print(jo.toString());
            return;
        }
        sqlListArray.add(sql.toString());
        objListArray.add(objList);
        // ��ӹ����༶
        if (classArray != null) {
            JSONArray ja = JSONArray.fromObject(classArray);
            TpCourseClass cc;
            String stime = "";
            String etime = "";
            Integer classid = 0;
            //��ѧ�༶
            for (int i = 0; i < ja.size(); i++) {
                cc = new TpCourseClass();
                JSONObject jb = JSONObject.fromObject(ja.get(i));
                if(jb==null||jb.size()<1){
                    continue;
                }
                classid = jb.getInt("classId");
                stime=jb.getString("startTime");
                etime=jb.getString("endTime");
                if(classid==null||stime==null||etime==null||classid<0||stime.length()<1||etime.length()<1){
                    returnJo.put("msg","ר��༶����ȱ�٣���鿴�����ԣ�");// �쳣���󣬲������룬�޷���������!
                    response.getWriter().print(returnJo.toString());
                    return;
                }
                ClassInfo c = new ClassInfo();
                c.setClassid(classid);
                List<ClassInfo> cList = this.classManager.getList(c,null);
                if(cList==null||cList.size()<1){
                    returnJo.put("msg",classid+"--�༶�����ڣ�������ѡ��");
                    response.getWriter().print(returnJo.toString());
                    return;
                }
                //����ѧ�ڿ�ʼʱ�����ʱ�䣬�ж�ʱ��Ϸ�
                Long termStime = termInfo.getSemesterbegindate().getTime();
                Long termEtime = termInfo.getSemesterenddate().getTime();
                Long lstime = UtilTool.StringConvertToDate(stime).getTime();
                Long letime = UtilTool.StringConvertToDate(etime).getTime();
                if(lstime<termStime){
                    returnJo.put("msg",cList.get(0).getClassgrade()+cList.get(0).getClassname()+"--�༶��ʼʱ�䲻������ѧ�ڿ�ʼʱ��--"+termInfo.getSemesterbegindatestring()+"��������ѡ��");
                    response.getWriter().print(returnJo.toString());
                    return;
                }
                if(letime>termEtime){
                    returnJo.put("msg",cList.get(0).getClassgrade()+cList.get(0).getClassname()+"--�༶����ʱ�䲻������ѧ�ڽ���ʱ��--"+termInfo.getSemesterenddatestring()+"��������ѡ��");
                    response.getWriter().print(returnJo.toString());
                    return;
                }
                if(lstime>letime){
                    returnJo.put("msg","���޸�"+cList.get(0).getClassgrade()+cList.get(0).getClassname()+"--����ʱ��Ӧ���ڿ�ʼʱ��֮��");
                    response.getWriter().print(returnJo.toString());
                    return;
                }
                cc.setClassid(classid);
                cc.setCourseid(courseid);
                cc.setSubjectid(Integer.parseInt(subjectId));
                cc.setGradeid(Integer.parseInt(gradeId));
                cc.setTermid(termInfo.getRef());
                cc.setBegintime(UtilTool.StringConvertToDate(stime));
                cc.setEndtime(UtilTool.StringConvertToDate(etime));
                cc.setClasstype(1);
                cc.setUserid(u.getUserid());
                sql = new StringBuilder();
                objList = this.tpCourseClassManager.getSaveSql(cc, sql);
                if (sql == null || objList == null) {
                    returnJo.put("msg","ר��༶����д�����");// �쳣���󣬲������룬�޷���������!
                    response.getWriter().print(returnJo.toString());
                    return;
                }
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }
        if (sqlListArray.size() > 0 && objListArray.size() > 0) {
            boolean bo = this.tpCourseManager.doExcetueArrayProc(
                    sqlListArray, objListArray);
            if (bo) {
                returnJo.put("msg",UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                returnJo.put("result", "1");
            } else {
                returnJo.put("msg", UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
                returnJo.put("result","0");
            }
        } else {
            returnJo.put("msg", UtilTool.msgproperty
                    .getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
            returnJo.put("result","0");
        }
        response.getWriter().print(returnJo.toString());
    }

    /**
     * �޸�ר��ǰ�ýӿ�
     */
    @RequestMapping(params = "m=getUpdateCourse", method = RequestMethod.POST)
    public void getUpdateCourse(HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        JSONObject returnJo=new JSONObject();
        returnJo.put("result","0");//Ĭ��ʧ��
        if(!ImFace115Util.ValidateRequestParam(request)){  //��֤����
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> paramMap=ImFace115Util.getRequestParam(request);
        //��ȡ����
        String jid=paramMap.get("jid");
        String schoolId=paramMap.get("schoolId");
        String classid = paramMap.get("classId");
        String courseId = paramMap.get("courseId");
        String time=paramMap.get("time");
        String sign=paramMap.get("sign");
        if(time==null||sign==null||jid==null||schoolId==null||courseId==null){
            returnJo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(returnJo.toString());return;
        }
        //ȥ��sign
        paramMap.remove("sign");
        //��֤Md5
        Boolean b = UrlSigUtil.verifySigSimple("getUpdateCourse",paramMap,sign);
        if(!b){
            returnJo.put("msg","��֤ʧ�ܣ��Ƿ���¼!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        //��֤�û�
        UserInfo u=new UserInfo();
        u.setEttuserid(Integer.parseInt(jid));
        u.setDcschoolid(Integer.parseInt(schoolId));
        List<UserInfo>userList=this.userManager.getList(u,null);
        if(userList==null||userList.size()<1){
            returnJo.put("msg","��ǰ���ʺ�δ��!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        u = userList.get(0);
        TeacherInfo t = new TeacherInfo();
        t.setUserid(u.getRef());
        List<TeacherInfo> tl = this.teacherManager.getList(t, null);
        if (tl == null || tl.size() == 0) {
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg", "��ʦ������!");
            jo.put("data", "");
            response.getWriter().print(jo.toString());
            return;
        }
        TpCourseInfo tc=new TpCourseInfo();
        Long courseid = Long.parseLong(courseId);
        tc.setCourseid(courseid);
        List<TpCourseInfo> tcList = this.tpCourseManager.getList(tc,null);
        if(tcList==null||tcList.size()==0){
            returnJo.put("msg","��ǰר�ⲻ���ڣ���鿴������");
            response.getWriter().print(returnJo.toString());
            return;
        }
        if(!tcList.get(0).getCuserid().equals(u.getUserid())){
            returnJo.put("msg","��ǰ�û�û���޸Ĵ�ר���Ȩ�ޣ���鿴������");
            response.getWriter().print(returnJo.toString());
            return;
        }
        List<Map<String,Object>> classList = this.imInterfaceManager.getUpdateCourse(Long.parseLong(courseId),Integer.parseInt(classid));
        List subList = new ArrayList();
        Map classMap = new HashMap();
        JSONArray jr = new JSONArray();
        if(classList!=null&&classList.size()>0){
            for(int i = 0;i<classList.size();i++){
                JSONObject jo = new JSONObject();
                classMap = classList.get(i);
                jo.put("classId",classMap.get("CLASSID"));
                jo.put("className",classMap.get("CLASSNAME"));
                jo.put("startTime",classMap.get("BEGINTIME").toString().substring(0,classMap.get("BEGINTIME").toString().lastIndexOf(".")));
                jo.put("endTime",classMap.get("ENDTIME").toString().substring(0,classMap.get("ENDTIME").toString().lastIndexOf(".")));
                jr.add(jo);
            }
        }
        returnJo.put("result","1");
        returnJo.put("msg","��ѯ�ɹ�");
        Map d = new HashMap();
        d.put("classArray",jr);
        d.put("courseId",courseId);
        d.put("courseName",tcList.get(0).getCoursename());
        returnJo.put("data",d);
        response.getWriter().print(returnJo.toString());
    }

    /**
     * �޸�ר��
     * @param request
     * @param response
     * @return
     * */

    @RequestMapping(params = "m=updateCourse", method = RequestMethod.POST)
    public void doUpdateCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject returnJo=new JSONObject();
        returnJo.put("result","0");//Ĭ��ʧ��
        if(!ImFace115Util.ValidateRequestParam(request)){  //��֤����
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> paramMap=ImFace115Util.getRequestParam(request);
        //��ȡ����
        String courseName=paramMap.get("courseName");
        String courseId = paramMap.get("courseId");
        String subjectId=paramMap.get("subjectId");
        String gradeId=paramMap.get("gradeId");
        String jid=paramMap.get("jid");
        String schoolId=paramMap.get("schoolId");
        String classArray = paramMap.get("classArray");
        String time=paramMap.get("time");
        String sign=paramMap.get("sign");
        TermInfo termInfo=this.termManager.getAutoTerm();
        if(time==null||sign==null||jid==null||schoolId==null||classArray==null||courseName==null||courseId==null){
            returnJo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(returnJo.toString());return;
        }
        //ȥ��sign
        paramMap.remove("sign");
        paramMap.remove("classArray");
        //��֤Md5
        Boolean b = UrlSigUtil.verifySigSimple("updateCourse",paramMap,sign);
        if(!b){
            returnJo.put("msg","��֤ʧ�ܣ��Ƿ���¼!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        //��֤�û�
        UserInfo u=new UserInfo();
        u.setEttuserid(Integer.parseInt(jid));
        u.setDcschoolid(Integer.parseInt(schoolId));
        List<UserInfo>userList=this.userManager.getList(u,null);
        if(userList==null||userList.size()<1){
            returnJo.put("msg","��ǰ���ʺ�δ��!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        u = userList.get(0);
        List<String> sqlListArray = new ArrayList<String>();
        List<List<Object>> objListArray = new ArrayList<List<Object>>();
        List<Object> objList = null;
        StringBuilder sql = null;

        ////////////////////////// �޸�ר�⣬������ز���
        TpCourseInfo tc=new TpCourseInfo();
        Long courseid = Long.parseLong(courseId);
        tc.setCourseid(courseid);

        TeacherInfo t = new TeacherInfo();
        t.setUserid(u.getRef());
        List<TeacherInfo> tl = this.teacherManager.getList(t, null);
        if (tl == null || tl.size() == 0) {
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg", "��ʦ������!");
            jo.put("data", "");
            response.getWriter().print(jo.toString());
            return;
        }
        List<TpCourseInfo> tcList = this.tpCourseManager.getList(tc,null);
        if(tcList==null||tcList.size()==0){
            returnJo.put("msg","��ǰר�ⲻ���ڣ���鿴������");
            response.getWriter().print(returnJo.toString());
            return;
        }
        if(!tcList.get(0).getCuserid().equals(u.getUserid())){
            returnJo.put("msg","��ǰ�û�û���޸Ĵ�ר���Ȩ�ޣ���鿴������");
            response.getWriter().print(returnJo.toString());
            return;
        }
        tc.setCoursename(courseName);

        sql = new StringBuilder();
        objList = this.tpCourseManager.getUpdateSql(tc, sql);
        if (sql == null || objList == null) {
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg", "�޸�ר��ʧ��!");
            jo.put("data", "");
            response.getWriter().print(jo.toString());
            return;
        }
        sqlListArray.add(sql.toString());
        objListArray.add(objList);

        //ɾ���ɵİ༶ר�������¼
        TpCourseClass tcc = new TpCourseClass();
        tcc.setCourseid(courseid);
        tcc.setTermid(termInfo.getRef());
        tcc.setUserid(u.getUserid());
        //��ѯ��ǰר���Ƿ����޸İ༶����Ŀǰ�ǣ������һ���༶��ʼ�ˣ��Ͳ����޸�
        Boolean classSign = false;//�����ж��Ƿ����޸İ༶����ı�ʶ
        List<TpCourseClass> tccList = this.tpCourseClassManager.getList(tcc,null);
        if(tccList!=null&&tccList.size()>0){
            //��ȡ�꼶ѧ�ƣ���Ϊͬһר�⣬�꼶ѧ��һ������ȡһ�ξͿ���
            gradeId=tccList.get(0).getGradeid().toString();
            subjectId=tccList.get(0).getSubjectid().toString();
            for(int i = 0;i<tccList.size();i++){
                //���Ȼ�ȡ��ǰʱ�������
                Long currentTime = System.currentTimeMillis();
                //��ȡ�༶����Ŀ�ʼʱ��
                Long startTime = tccList.get(i).getBegintime().getTime();
                if(startTime>currentTime){
                    classSign=true;
                    break;
                }
            }
        }
        if(!classSign){
            sql = new StringBuilder();
            objList = this.tpCourseClassManager.getDeleteSql(tcc, sql);
            sqlListArray.add(sql.toString());
            objListArray.add(objList);

            // ��ӹ����༶
            if (classArray != null) {
                JSONArray ja = JSONArray.fromObject(classArray);
                TpCourseClass cc;
                String stime = "";
                String etime = "";
                Integer classid = 0;
                //��ѧ�༶
                for (int i = 0; i < ja.size(); i++) {
                    cc = new TpCourseClass();
                    JSONObject jb = JSONObject.fromObject(ja.get(i));
                    if(jb==null||jb.size()<1){
                        continue;
                    }
                    classid = jb.getInt("classId");
                    stime=jb.getString("startTime");
                    etime=jb.getString("endTime");
                    if(classid==null||stime==null||etime==null||classid<0||stime.length()<1||etime.length()<1){
                        returnJo.put("msg","ר��༶����ȱ�٣���鿴�����ԣ�");// �쳣���󣬲������룬�޷���������!
                        response.getWriter().print(returnJo.toString());
                        return;
                    }
                    ClassInfo c = new ClassInfo();
                    c.setClassid(classid);
                    List<ClassInfo> cList = this.classManager.getList(c,null);
                    if(cList==null||cList.size()<1){
                        returnJo.put("msg",classid+"--�༶�����ڣ�������ѡ��");
                        response.getWriter().print(returnJo.toString());
                        return;
                    }
                    //����ѧ�ڿ�ʼʱ�����ʱ�䣬�ж�ʱ��Ϸ�
                    Long termStime = termInfo.getSemesterbegindate().getTime();
                    Long termEtime = termInfo.getSemesterenddate().getTime();
                    Long lstime = UtilTool.StringConvertToDate(stime).getTime();
                    Long letime = UtilTool.StringConvertToDate(etime).getTime();
                    if(lstime<termStime){
                        returnJo.put("msg",cList.get(0).getClassgrade()+cList.get(0).getClassname()+"--�༶��ʼʱ�䲻������ѧ�ڿ�ʼʱ��--"+termInfo.getSemesterbegindatestring()+"��������ѡ��");
                        response.getWriter().print(returnJo.toString());
                        return;
                    }
                    if(letime>termEtime){
                        returnJo.put("msg",cList.get(0).getClassgrade()+cList.get(0).getClassname()+"--�༶����ʱ�䲻������ѧ�ڽ���ʱ��--"+termInfo.getSemesterenddatestring()+"��������ѡ��");
                        response.getWriter().print(returnJo.toString());
                        return;
                    }
                    if(lstime>letime){
                        returnJo.put("msg","���޸�"+cList.get(0).getClassgrade()+cList.get(0).getClassname()+"--����ʱ��Ӧ���ڿ�ʼʱ��֮��");
                        response.getWriter().print(returnJo.toString());
                        return;
                    }
                    cc.setClassid(classid);
                    cc.setCourseid(courseid);
                    cc.setSubjectid(Integer.parseInt(subjectId));
                    cc.setGradeid(Integer.parseInt(gradeId));
                    cc.setTermid(termInfo.getRef());
                    cc.setBegintime(UtilTool.StringConvertToDate(stime));
                    cc.setEndtime(UtilTool.StringConvertToDate(etime));
                    cc.setClasstype(1);
                    cc.setUserid(u.getUserid());
                    sql = new StringBuilder();
                    objList = this.tpCourseClassManager.getSaveSql(cc, sql);
                    if (sql == null || objList == null) {
                        returnJo.put("msg","ר��༶����д�����");// �쳣���󣬲������룬�޷���������!
                        response.getWriter().print(returnJo.toString());
                        return;
                    }
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }
            }
        }
        if (sqlListArray.size() > 0 && objListArray.size() > 0) {
            boolean bo = this.tpCourseManager.doExcetueArrayProc(
                    sqlListArray, objListArray);
            if (bo) {
                returnJo.put("msg",UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                returnJo.put("result", "1");
            } else {
                returnJo.put("msg", UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
                returnJo.put("result","0");
            }
        } else {
            returnJo.put("msg", UtilTool.msgproperty
                    .getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
            returnJo.put("result","0");
        }
        response.getWriter().print(returnJo.toString());
    }




    /**
     * ɾ��ר��
     */
    @RequestMapping(params = "m=deleteCourse", method = RequestMethod.POST)
    public void doDelCourse(HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        JSONObject returnJo=new JSONObject();
        returnJo.put("result","0");//Ĭ��ʧ��
        if(!ImFace115Util.ValidateRequestParam(request)){  //��֤����
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> paramMap=ImFace115Util.getRequestParam(request);
        //��ȡ����
        String jid = paramMap.get("jid");
        String courseId=paramMap.get("courseId");
        String time=paramMap.get("time");
        String sign=paramMap.get("sign");
        String schoolId = paramMap.get("schoolId");
        if(courseId==null||time==null||sign==null){
            returnJo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(returnJo.toString());return;
        }
        //ȥ��sign
        paramMap.remove("sign");
        //��֤Md5
        Boolean b = UrlSigUtil.verifySigSimple("deleteCourse",paramMap,sign);
        if(!b){
            returnJo.put("msg","��֤ʧ�ܣ��Ƿ���¼!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        //��֤�û�
        UserInfo u=new UserInfo();
        u.setEttuserid(Integer.parseInt(jid));
        u.setDcschoolid(Integer.parseInt(schoolId));
        List<UserInfo>userList=this.userManager.getList(u,null);
        if(userList==null||userList.size()<1){
            returnJo.put("msg","��ǰ���ʺ�δ��!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        u = userList.get(0);
        TpCourseInfo del=new TpCourseInfo();
        del.setCourseid(Long.parseLong(courseId));
        List<TpCourseInfo> tcList = this.tpCourseManager.getList(del,null);
        if(tcList==null||tcList.size()==0){
            returnJo.put("msg","��ǰר�ⲻ���ڣ���鿴������");
            response.getWriter().print(returnJo.toString());
            return;
        }
        if(!tcList.get(0).getCuserid().equals(u.getUserid())){
            returnJo.put("msg","��ǰ�û�û��ɾ����ר���Ȩ�ޣ���鿴������");
            response.getWriter().print(returnJo.toString());
            return;
        }
        TeacherInfo t = new TeacherInfo();
        t.setUserid(u.getRef());
        List<TeacherInfo> tl = this.teacherManager.getList(t, null);
        if (tl == null || tl.size() == 0) {
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg", "��ʦ������!");
            jo.put("data", "");
            response.getWriter().print(jo.toString());
            return;
        }
        if(this.tpCourseManager.doDelete(del)){
            returnJo.put("msg", "ɾ���ɹ�");
            returnJo.put("result","1");
        }else{
            returnJo.put("msg", UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            returnJo.put("result","0");
        }
        response.getWriter().print(returnJo.toString());
    }

    /**
     * ѧϰĿ¼�ӿ�
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=StudyModule",method= {RequestMethod.GET,RequestMethod.POST})
    public void getStudyModule1_1_1(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        String userid = request.getParameter("jid");
        String usertype=request.getParameter("userType");
        String schoolid = request.getParameter("schoolId");
        String timestamp = request.getParameter("time");
        String sig = request.getParameter("sign");
        String lastAccessTime = request.getParameter("lastAccessTime");

        if(!ImFace115Util.ValidateRequestParam(request)){
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
        map.put("lastAccessTime",lastAccessTime);
        String sign = UrlSigUtil.makeSigSimple("StudyModule",map,"*ETT#HONER#2014*");
        Boolean b = UrlSigUtil.verifySigSimple("StudyModule",map,sig);
        if(!b){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"��֤ʧ�ܣ��Ƿ���¼\"}");
            return;
        }
        int utype=ImFace115Util.getUserType(usertype);
        UserInfo ui = new UserInfo();
        ui.setEttuserid(Integer.parseInt(userid));
        List<UserInfo> userList = this.userManager.getList(ui,null);
        if(userList==null||userList.size()<1){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"��ǰ�û�δ�󶨣�����ϵ����Ա\"}");
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
        m2.put("showManageClass",validateHasSchool(Integer.parseInt(schoolid.trim())));
        TermInfo tm=this.termManager.getAutoTerm();
        m2.put("newDynamicAll",this.myInfoUserManager.getImMsgData(null,userList.get(0).getUserid(),tm.getYear()));//�ܵ�����
        if(list!=null&&list.size()>0){
            m2.put("classes",list);
            //String etturl = "http://192.168.10.59/study-im-service-1.0/activityNotifyNum.do";
            String etturl = UtilTool.utilproperty.getProperty("ETT_GET_NOTIFYNUM_IP");
            HashMap<String,String> ettMap = new HashMap();
            ettMap.put("jid",userid);
            ettMap.put("userType",usertype);
            ettMap.put("schoolId",schoolid);
            ettMap.put("time",timestamp);
            if(lastAccessTime==null||lastAccessTime.length()<1){
                lastAccessTime="0";
            }
            ettMap.put("lastAccessTime",lastAccessTime);
            String ettSig = UrlSigUtil.makeSigSimple("activityNotifyNum.do",ettMap,"*ETT#HONER#2014*");
            ettMap.put("sign",ettSig);
            JSONObject ettJo = UtilTool.sendPostUrl(etturl,ettMap,"utf-8");
            if(ettJo!=null&&ettJo.toString().length()>0){
                int result = ettJo.containsKey("result")?ettJo.getInt("result"):0;
                if(result==1){
                    int notifynum = 0;
                    JSONObject data = ettJo.containsKey("data")?ettJo.getJSONObject("data"):null;
                    if(data!=null){
                        notifynum = data.containsKey("activityNotifyNum")?data.getInt("activityNotifyNum"):0;
                    }
                    m2.put("activityNotifyNum",notifynum);
                }else{
                    m2.put("activityNotifyNum",-1);
                }
            }else{
                m2.put("activityNotifyNum",-1);
            }
        }else{
            m.put("result","0");
            m.put("msg","��ǰ�û�û��ѧϰĿ¼������ϵ����Ա");
        }
        //����ֱ���Ρ��㲥�Ρ������� ֻ�г��и���ѧ������
        if(usertype.equals("3")||usertype.equals("4")){
            List<Map<String,Object>> gradeList = this.imInterfaceManager.validateGrade(userList.get(0).getRef());
            if(gradeList!=null&&gradeList.size()>0){
                if(gradeList.get(0).get("GRADE").toString().substring(0,1).equals("��")||gradeList.get(0).get("GRADE").toString().substring(0,1).equals("��")){
                    JSONArray jstudy = new JSONArray();
                    JSONObject jb = new JSONObject();
                    HashMap<String,String> urlMap = new HashMap();
                    String signurl="";
                    String url = "";
                    Long curtime = System.currentTimeMillis();
                    //ֱ����
                    jb.put("studyType",1);
                    jb.put("studyName","ֱ����");
                    urlMap.put("jid",userid+"");
                    urlMap.put("time",curtime+"");
                    signurl=UrlSigUtil.makeSigSimple("weixin",urlMap,"*ETT#HONER#2014*");
                    url = UtilTool.utilproperty.getProperty("COURSEONLINE_INTER_IP")+"?jid="+userid+"&time="+curtime+"&sign="+signurl;
                    jb.put("url",url);
                    jstudy.add(jb);
                    //�㲥��
                    jb = new JSONObject();
                    urlMap = new HashMap<String, String>();
                    jb.put("studyType",2);
                    jb.put("studyName","�㲥��");
                    urlMap.put("jid",userid+"");
                    urlMap.put("time",curtime+"");
                    signurl=UrlSigUtil.makeSigSimple("weixin",urlMap,"*ETT#HONER#2014*");
                    url = UtilTool.utilproperty.getProperty("COURSEDEMAND_INTER_IP")+"?jid="+userid+"&time="+curtime+"&sign="+signurl;
                    jb.put("url",url);
                    jstudy.add(jb);
                    //���߲���
                    jb = new JSONObject();
                    urlMap = new HashMap<String, String>();
                    jb.put("studyType",3);
                    jb.put("studyName","���߲���");
                    urlMap.put("jid",userid+"");
                    urlMap.put("time",curtime+"");
                    signurl=UrlSigUtil.makeSigSimple("weixin",urlMap,"*ETT#HONER#2014*");
                    url = UtilTool.utilproperty.getProperty("TESTONLINE_INTER_IP")+"?jid="+userid+"&time="+curtime+"&sign="+signurl;
                    jb.put("url",url);
                    jstudy.add(jb);
                    m2.put("studys",jstudy);
                }
            }
        }
        m.put("result","1");
        m.put("msg","�ɹ�");
        m.put("data",m2);
        JSONObject object = JSONObject.fromObject(m);
        response.setContentType("text/json");
        response.getWriter().print(object.toString());
    }

    /**
     * ����classId�õ�dcSchoolId
     * @param clsid
     * @return
     */
    private Integer getDcSchoolIdByClassId(Integer clsid){
        if(clsid==null)return null;

        ClassInfo cls=new ClassInfo();
        cls.setClassid(clsid);
        List<ClassInfo> clsList=this.classManager.getList(cls,null);
        if(clsList!=null&&clsList.size()>0)
            return clsList.get(0).getDcschoolid();
        return null;
    }

    /**
     * ��֤��У�Ƿ����
     * @param schoolid ��УID
     * @return 0:û��  1����
     */
    public static int validateHasSchool(Integer schoolid){
        HashMap<String,String> paraMap=new HashMap<String, String>();
        paraMap.put("schoolId",schoolid+"");
        paraMap.put("time",System.currentTimeMillis()+"isActiveSchool.do");
        paraMap.put("sign",UrlSigUtil.makeSigSimple("isActiveSchool.do",paraMap));
        String totalSchoolUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION");
        // String totalSchoolUrl="http://192.168.10.41/totalSchool/";
        String url=totalSchoolUrl+"/franchisedSchool?m=isActiveSchool.do";
        JSONObject jo=sendPostUrl(url, paraMap, "UTF-8");
        Integer returnVal=0;
        if(jo!=null&&jo.containsKey("result")&&jo.get("result")!=null){
            returnVal=jo.getInt("result");
        }
        return returnVal;
    }

    public static JSONObject sendPostUrl(String urlstr,Map<String,String> paramMap,String requestEncoding){
        HttpURLConnection httpConnection=null;
        URL url;
        int code;
        try {
            //��֯����
            StringBuffer params = new StringBuffer();
            if(paramMap!=null&&paramMap.size()>0){
                for (Iterator iter = paramMap.entrySet().iterator(); iter
                        .hasNext();)
                {
                    Map.Entry element = (Map.Entry) iter.next();
                    params.append(element.getKey().toString());
                    params.append("=");
                    params.append(URLEncoder.encode(element.getValue().toString(), requestEncoding));
                    params.append("&");
                }

                if (params.length() > 0)
                {
                    params = params.deleteCharAt(params.length() - 1);
                }
            }

            url = new URL(urlstr);

            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setRequestMethod("POST");
            if(params!=null)
                httpConnection.setRequestProperty("Content-Length",
                        String.valueOf(params.toString().length()));
            httpConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
			/*
			 * PrintWriter printWriter = new
			 * PrintWriter(httpConnection.getOutputStream());
			 * printWriter.print(parameters); printWriter.close();
			 */

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    httpConnection.getOutputStream(), "8859_1");
            if(params!=null)
                outputStreamWriter.write(params.toString());
            outputStreamWriter.flush();
            outputStreamWriter.close();

            code = httpConnection.getResponseCode();
        } catch (Exception e) {			// �쳣��ʾ
            System.out.println("�쳣����!�ӿ����ӣ�"+urlstr+"δ��Ӧ!");
            if(httpConnection!=null)httpConnection.disconnect();
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (code == HttpURLConnection.HTTP_OK) {
            try {
                String strCurrentLine;
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(httpConnection.getInputStream()));
                while ((strCurrentLine = reader.readLine()) != null) {
                    stringBuffer.append(strCurrentLine).append("\n");
                }
                reader.close();
                if(httpConnection!=null)httpConnection.disconnect();
            } catch (IOException e) {
                System.out.println("�쳣����!");
                if(httpConnection!=null)httpConnection.disconnect();
                return null;
            }
        }else if(code==404){
            if(httpConnection!=null)httpConnection.disconnect();
            // ��ʾ ����
            System.out.println("�쳣����!404��������ϵ������Ա!");
            return null;
        }else if(code==500){
            if(httpConnection!=null)httpConnection.disconnect();
            System.out.println("�쳣����!500��������ϵ������Ա!���ӣ�"+urlstr+"");
            return null;
        }
        String returnContent=null;
        try {
            //returnContent=java.net.URLDecoder.decode(stringBuffer.toString(),"UTF-8");  ///aa
            returnContent=new String(stringBuffer.toString().getBytes("gbk"),requestEncoding);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //ת����JSON
        System.out.println(returnContent);
        JSONObject jb=JSONObject.fromObject(returnContent);
        System.out.println("ImInterface 1.1.3 JSONObject:"+jb.toString());
        return jb;
    }
}

/**
 * ������
 */
class ImFace115Util{
    /**
     * ��֤RequestParams��ز���
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
     * ��֤RequestParams��ز���
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
     * ת��usertype
     * @param usertype 1:ѧ��  2����ʦ  3���ҳ�
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

    public static JSONArray getEttPhoneAndRealNmae(String jidstr,String schoolid,String userid) throws UnsupportedEncodingException {
        String ettip = UtilTool.utilproperty.getProperty("ETT_INTER_IP");
        System.out.println("ettip------------------------------"+ettip);
        String url=ettip+"queryPhotoAndRealName.do";
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
        System.out.println("jsonObject---------------"+jsonObject);
        int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
        if(type==1){
            Object obj = jsonObject.containsKey("data")?jsonObject.get("data"):null;
            obj = URLDecoder.decode(obj.toString(), "utf-8");
            JSONArray jr = JSONArray.fromObject(obj);
            if(jr!=null)
                return jr;
            else
                return null;
        }else{
            return null;
        }
    }

    public static void main(String[] args){
        String leftime = ImFace115Util.getTaskOvertime("705780");
        System.out.println(leftime+"=========================");
    }

    public static String getTaskOvertime(String tasktime){
        String leftTime = "";
        int time =Integer.parseInt(tasktime);
        int days = 0;
        int hours =0;
        int mins = 0;
        int seconds = 0;
        if(time>0){
            seconds = time%60;
            if(seconds>0||time>=60){
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

            if(days>0){
                leftTime=days+"��";
            }else{
                if(hours>0){
                    leftTime=hours+"Сʱ";
                }else{
                    if(mins>0){
                        leftTime=mins+"����";
                    }else{
                        if(seconds>0){
                            leftTime=seconds+"��";
                        }
                    }
                }
            }
            if(leftTime.indexOf("��")==-1&&leftTime.indexOf("Сʱ")==-1&&leftTime.indexOf("����")==-1&&leftTime.indexOf("��")==-1){
                System.out.println("***********************************************************************************************");
                System.out.println("�༶�������ʱ�������------------------------------------------------------------------------"+leftTime+"*******"+time);
                System.out.println("***********************************************************************************************");
            }
        }
        return leftTime;
    }

}
