package com.school.im1_1.control._interface;

import com.etiantian.unite.utils.CodecUtil;
import com.etiantian.unite.utils.UrlSigUtil;
import com.school.control.base.BaseController;
import com.school.entity.teachpaltform.TpCourseInfo;
import com.school.entity.teachpaltform.TpTaskAllotInfo;
import com.school.entity.teachpaltform.TpTaskInfo;
import com.school.im1_1.entity._interface.ImInterfaceInfo;
import com.school.im1_1.manager._interface.ImInterfaceManager;
import com.school.manager.inter.teachpaltform.ITpCourseManager;
import com.school.manager.inter.teachpaltform.ITpTaskAllotManager;
import com.school.manager.inter.teachpaltform.ITpTaskManager;
import com.school.manager.teachpaltform.TpCourseManager;
import com.school.manager.teachpaltform.TpTaskAllotManager;
import com.school.manager.teachpaltform.TpTaskManager;
import com.school.util.JsonEntity;
import com.school.util.MD5_NEW;
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
    public ImInterfaceController(){
        this.imInterfaceManager=this.getManager(ImInterfaceManager.class);
        this.tpCourseManager = this.getManager(TpCourseManager.class);
        this.tpTaskManager = this.getManager(TpTaskManager.class);
        this.tpTaskAllotManager = this.getManager(TpTaskAllotManager.class);
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
        String timestamp = request.getParameter("timeStamp");
        String sig = request.getParameter("sign");
        HashMap<String,String> map = new HashMap();
        map.put("jid",userid);
        map.put("userType",usertype);
        map.put("schoolId",schoolid);
        map.put("timeStamp",timestamp);
        String sign = UrlSigUtil.makeSigSimple("StudyModule",map,"*ETT#HONER#2014*");
       // Boolean b = UrlSigUtil.verifySigSimple("StudyModule",map,sig);
        if(!sig.equals(sign)){
            response.getWriter().print("{\"result\":\"error\",\"message\":\"验证失败，非法登录\"}");
            return;
        }
        ImInterfaceInfo obj = new ImInterfaceInfo();
        obj.setSchoolid(Integer.parseInt(schoolid));
        obj.setUserid(Integer.parseInt(userid));
        obj.setUsertype(Integer.parseInt(usertype));
        List<Map<String,Object>> list = this.imInterfaceManager.getStudyModule(obj);
        Map m = new HashMap();
        Map m2 = new HashMap();
        if(list!=null&&list.size()>0){
            m2.put("classes",list);
            m2.put("activityNotifyNum","12");
        }else{
            m.put("result","error");
            m.put("message","当前用户没有学习目录，请联系管理员");
        }
        m.put("result","success");
        m.put("message","成功");
        m.put("data",m2);
        JSONObject object = JSONObject.fromObject(m);
        response.setContentType("text/json");
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
        String timestamp = request.getParameter("timeStamp");
        String sig = request.getParameter("sign");
        HashMap<String,String> map = new HashMap();
        map.put("classId",classid);
        map.put("schoolId",schoolid);
        map.put("timeStamp",timestamp);
        String sign = UrlSigUtil.makeSigSimple("ClassTask",map,"*ETT#HONER#2014*");
       // Boolean b = UrlSigUtil.verifySigSimple("ClassTask",map,sig);
        if(!sig.equals(sign)){
            response.getWriter().print("{\"result\":\"error\",\"message\":\"验证失败，非法登录\"}");
            return;
        }
        ImInterfaceInfo obj = new ImInterfaceInfo();
        obj.setSchoolid(Integer.parseInt(schoolid));
        obj.setClassid(Integer.parseInt(classid));
        List<Map<String,Object>> courseList = this.imInterfaceManager.getClassTaskCourse(obj);
        Map m = new HashMap();
        if(courseList!=null&&courseList.size()>0){
            for(int i = 0;i<courseList.size();i++){
                List<Map<String,Object>> taskList = this.imInterfaceManager.getClassTaskTask(Long.parseLong(courseList.get(i).get("COURSEID").toString()));
                courseList.get(i).put("taskList",taskList);
            }
        }else{
            m.put("result","error");
            m.put("message","当前没有专题");
        }

        m.put("result","success");
        m.put("message","成功");
        m.put("data",courseList);
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
    @RequestMapping(params="m=ClassCalendar",method= RequestMethod.GET)
    public void getClassCalendar(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je = new JsonEntity();
        String userid = request.getParameter("jid");
        String usertype=request.getParameter("userType");
        String classid = request.getParameter("classId");
        String schoolid = request.getParameter("schoolId");
        String timestamp = request.getParameter("timeStamp");
        String sig = request.getParameter("sign");
        HashMap<String,String> map = new HashMap();
        map.put("jid",userid);
        map.put("userType",usertype);
        map.put("classId",classid);
        map.put("schoolId",schoolid);
        map.put("timeStamp",timestamp);
        String sign = UrlSigUtil.makeSigSimple("ClassCalendar",map,"*ETT#HONER#2014*");
       // Boolean b = UrlSigUtil.verifySigSimple("ClassCalendar",map,sig);
        if(!sig.equals(sign)){
            response.getWriter().print("{\"result\":\"error\",\"message\":\"验证失败，非法登录\"}");
            return;
        }
        ImInterfaceInfo obj = new ImInterfaceInfo();
        obj.setSchoolid(Integer.parseInt(schoolid));
        obj.setClassid(Integer.parseInt(classid));
        List<Map<String,Object>> courseList = this.imInterfaceManager.getClassTaskCourse(obj);
        Map m = new HashMap();
        Map m2 = new HashMap();
        if(courseList!=null&&courseList.size()>0){
           m2.put("courseList",courseList);
        }else{
            m.put("result","error");
            m.put("message","当前没有专题");
        }
        m2.put("personTotalScore","350");
        m2.put("teamScore","130");
        m2.put("taskScore","150");
        m2.put("presenceScore","50");
        m2.put("smileScore","10");
        m2.put("illegalScore","10");
        m.put("result","success");
        m.put("message","成功");
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
        String timestamp = request.getParameter("timeStamp");
        String sig = request.getParameter("sign");
        HashMap<String,String> map = new HashMap();
        map.put("data",dataStr);
        map.put("jid",userid);
        map.put("schoolId",schoolid);
        map.put("timeStamp",timestamp);
        String sign = UrlSigUtil.makeSigSimple("AddTask",map,"*ETT#HONER#2014*");
       // Boolean b = UrlSigUtil.verifySigSimple("AddTask",map,sig);
        if(!sig.equals(sign)){
            response.getWriter().print("{\"result\":\"error\",\"message\":\"验证失败，非法登录\"}");
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
            m.put("result","success");
            m.put("message","添加成功");
        }else{
            m.put("result","error");
            m.put("message","添加失败");
        }
        JSONObject jbStr=JSONObject.fromObject(m);
        response.getWriter().print(jbStr.toString());
    }

}
