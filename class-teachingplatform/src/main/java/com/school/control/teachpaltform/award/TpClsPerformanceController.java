package com.school.control.teachpaltform.award;

import com.school.control.base.BaseController;
import com.school.entity.teachpaltform.TpCourseClass;
import com.school.entity.teachpaltform.TpCourseInfo;
import com.school.entity.teachpaltform.TpGroupStudent;
import com.school.entity.teachpaltform.award.TpClassPerformanceAwardInfo;
import com.school.entity.teachpaltform.award.TpClsPerformanceInfo;
import com.school.manager.inter.teachpaltform.ITpCourseClassManager;
import com.school.manager.inter.teachpaltform.ITpCourseManager;
import com.school.manager.inter.teachpaltform.ITpGroupStudentManager;
import com.school.manager.inter.teachpaltform.award.ITpClassPerformanceAwardManager;
import com.school.manager.inter.teachpaltform.award.ITpClsPerformanceManager;
import com.school.manager.teachpaltform.TpCourseClassManager;
import com.school.manager.teachpaltform.TpCourseManager;
import com.school.manager.teachpaltform.TpGroupStudentManager;
import com.school.manager.teachpaltform.award.TpClassPerformanceAwardManager;
import com.school.manager.teachpaltform.award.TpClsPerformanceManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 课堂表现控制器
 * Created by zhengzhou on 14-6-27.
 */
@Controller
@RequestMapping(value="/clsperformance")
public class TpClsPerformanceController  extends BaseController<TpClsPerformanceInfo>{

    private ITpCourseManager courseManager;
    private ITpClsPerformanceManager tpClsPerformanceManager;
    private ITpClassPerformanceAwardManager tpClsPerformanceAwardManager;
    private ITpCourseClassManager tpCourseClassManager;
    private ITpGroupStudentManager tpGroupStudentManager;
    public TpClsPerformanceController(){
        this.tpClsPerformanceManager=this.getManager(TpClsPerformanceManager.class);
        this.courseManager=this.getManager(TpCourseManager.class);
        this.tpClsPerformanceAwardManager=this.getManager(TpClassPerformanceAwardManager.class);
        this.tpCourseClassManager=this.getManager(TpCourseClassManager.class);
        this.tpGroupStudentManager=this.getManager(TpGroupStudentManager.class);
    }
    /**
     * 进入课堂表现页面的首页(分角色)
     * @param request
     * @param mp
     * @return
     */
    @RequestMapping(params="m=toIndex",method = RequestMethod.GET)
    public ModelAndView toIndex(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception {
        String courseid=request.getParameter("courseid"); //专题ID
        String clsid=request.getParameter("classid");     //班级ID
        String typeid=request.getParameter("classtype");  //班级类型
        String subjectid=request.getParameter("subjectid");
        String termid=request.getParameter("termid");
        JsonEntity jsonEntity=new JsonEntity();
        //参数验证。
        if(courseid==null||courseid.trim().length()<1
                ||subjectid==null||subjectid.trim().length()<1
                ){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        //得到专题名称
        TpCourseInfo courseInfo=new TpCourseInfo();
        courseInfo.setCourseid(Long.parseLong(courseid.trim()));
        List<TpCourseInfo> courseList=this.courseManager.getList(courseInfo,null);
        if(courseList!=null&&courseList.size()>0){
            mp.put("coursename",courseList.get(0).getCoursename());
        }
        String groupid=",";
        if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
            //参数验证。
            if(termid==null){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
            }
            TpGroupStudent gs=new TpGroupStudent();
            gs.setUserid(this.logined(request).getUserid());
            gs.setIsleader(1);
            List<TpGroupStudent> tgList=this.tpGroupStudentManager.getList(gs,null);

            if(tgList!=null&&tgList.size()>0&&tgList.get(0)!=null){
                for(TpGroupStudent tgs:tgList){
                    groupid+=tgs.getGroupid()+",";
                }
            }

            //得到学生所在的班级及classtype
            List<Map<String,Object>> tpccList=this.tpCourseClassManager.getTpClassCourse(Long.parseLong(courseid.trim()),this.logined(request).getUserid(),termid);
            if(tpccList==null||tpccList.size()<1){
                jsonEntity.setMsg("异常错误，这当前学期中本专题没有您的班级信息!请重试!");
                response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
            }
            clsid=tpccList.get(0).get("CLASS_ID").toString();
            typeid=tpccList.get(0).get("CLASS_TYPE").toString();
        }
        if(groupid.length()<2){
            groupid="";
        }
        mp.put("leanderGrpid",groupid);
        mp.put("courseid",courseid);
        mp.put("subjectid",subjectid);
        //根据参数得到值 。
        List<Map<String,Object>> dataListMap=tpClsPerformanceManager.getPageDataList(Long.parseLong(courseid),Long.parseLong(clsid.trim()),Integer.parseInt(typeid.trim()),Integer.parseInt(subjectid));
        mp.put("dataListMap",dataListMap);
        return new ModelAndView("/teachpaltform/classPerformanceAward/clsPerformanceAwardIndex",mp);
    }

    /**
     * 添加或者修改记录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=doAddOrUpdate",method = RequestMethod.POST)
    public void doAddOrUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        TpClsPerformanceInfo entity=this.getParameter(request,TpClsPerformanceInfo.class);
        if(entity.getUserid()==null||entity.getCourseid()==null||entity.getGroupid()==null||entity.getSubjectid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        if(this.tpClsPerformanceManager.AddOrUpdate(entity)){
            jsonEntity.setType("success");
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
        }else
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().println(jsonEntity.toJSON());
    }
    /**
     * 添加或者修改记录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=doAddOrUpdateGroup",method = RequestMethod.POST)
    public void doAddOrUpdateGroupAward(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        String courseid=request.getParameter("courseid");
        String groupid=request.getParameter("groupid");
        String subjectid=request.getParameter("subjectid");
        String awardnumber=request.getParameter("awardNumber");
        if(courseid==null||groupid==null||awardnumber==null||!UtilTool.isNumber(awardnumber.trim())||subjectid==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        TpClassPerformanceAwardInfo entity=new TpClassPerformanceAwardInfo();
        entity.setAwardnumber(Integer.parseInt(awardnumber.trim()));
        entity.setCourseid(Long.parseLong(courseid.trim()));
        entity.setGroupid(Long.parseLong(groupid.trim()));
        entity.setSubjectid(Integer.parseInt(subjectid.trim()));
        if(this.tpClsPerformanceAwardManager.AddOrUpdate(entity)){
            jsonEntity.setType("success");
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
        }else
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().println(jsonEntity.toJSON());
    }

}
