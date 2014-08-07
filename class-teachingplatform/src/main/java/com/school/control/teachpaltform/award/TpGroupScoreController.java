package com.school.control.teachpaltform.award;

import com.school.control.base.BaseController;
import com.school.entity.ClassInfo;
import com.school.entity.teachpaltform.TpCourseInfo;
import com.school.entity.teachpaltform.TpGroupStudent;
import com.school.entity.teachpaltform.TpVirtualClassInfo;
import com.school.entity.teachpaltform.award.TpGroupScore;
import com.school.entity.teachpaltform.award.TpStuScore;
import com.school.manager.ClassManager;
import com.school.manager.inter.IClassManager;
import com.school.manager.inter.teachpaltform.ITpCourseClassManager;
import com.school.manager.inter.teachpaltform.ITpCourseManager;
import com.school.manager.inter.teachpaltform.ITpGroupStudentManager;
import com.school.manager.inter.teachpaltform.ITpVirtualClassManager;
import com.school.manager.inter.teachpaltform.award.ITpGroupScoreManager;
import com.school.manager.inter.teachpaltform.award.ITpStuScoreManager;
import com.school.manager.teachpaltform.TpCourseClassManager;
import com.school.manager.teachpaltform.TpCourseManager;
import com.school.manager.teachpaltform.TpGroupStudentManager;
import com.school.manager.teachpaltform.TpVirtualClassManager;
import com.school.manager.teachpaltform.award.TpStuScoreManager;
import com.school.manager.teachpaltform.award.TpGroupScoreManager;
import com.school.util.JsonEntity;
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
public class TpGroupScoreController extends BaseController<TpStuScore>{

    private ITpCourseManager courseManager;
    private ITpGroupScoreManager tpGroupScoreManager;
    private ITpStuScoreManager tpStuScoreManager;
    private ITpCourseClassManager tpCourseClassManager;
    private ITpGroupStudentManager tpGroupStudentManager;
    private IClassManager classManage;
    private ITpVirtualClassManager virtualClassManager;
    public TpGroupScoreController(){
        this.tpGroupScoreManager=this.getManager(TpGroupScoreManager.class);
        this.courseManager=this.getManager(TpCourseManager.class);
        this.tpStuScoreManager=this.getManager(TpStuScoreManager.class);
        this.tpCourseClassManager=this.getManager(TpCourseClassManager.class);
        this.tpGroupStudentManager=this.getManager(TpGroupStudentManager.class);
        this.classManage=this.getManager(ClassManager.class);
        this.virtualClassManager=this.getManager(TpVirtualClassManager.class);
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
            gs.getTpgroupinfo().setSubjectid(Integer.parseInt(subjectid));
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
            if(groupid.trim().length()>1)
                mp.put("ctUid",this.logined(request).getUserid());
        }else{
            if(groupid.length()<2){
                groupid="";
            }
        }
        mp.put("classid",clsid);
        mp.put("classtype",typeid);
        mp.put("leanderGrpid",groupid);
        mp.put("courseid",courseid);
        mp.put("subjectid",subjectid);
        //根据参数得到值 。
        List<Map<String,Object>> dataListMap=tpStuScoreManager.getPageDataList(Long.parseLong(courseid),Long.parseLong(clsid.trim()),Integer.parseInt(typeid.trim()),Integer.parseInt(subjectid));
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
        TpStuScore entity=this.getParameter(request,TpStuScore.class);
        String clstype=request.getParameter("clstype");
        if(entity.getUserid()==null||entity.getCourseid()==null||entity.getGroupid()==null||entity.getSubjectid()==null||entity.getClassid()==null||clstype==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }

        //如果classid<>null 则找schoolid  查询实体班级
        if(Integer.parseInt(clstype.trim())==1){
            //查询班级的dc_school_id
            ClassInfo cls=new ClassInfo();
            cls.setClassid(entity.getClassid().intValue());
            List<ClassInfo> clsList=classManage.getList(cls,null);
            if(clsList==null||clsList.size()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(jsonEntity.toJSON());return;
            }
            entity.setDcschoolid(clsList.get(0).getDcschoolid().longValue());
        }else if(Integer.parseInt(clstype.trim())==2){//虚拟班级
            TpVirtualClassInfo vc=new TpVirtualClassInfo();
            vc.setVirtualclassid(entity.getClassid().intValue());
            List<TpVirtualClassInfo> vtclassList=this.virtualClassManager.getList(vc,null);
            if(vtclassList==null||vtclassList.size()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(jsonEntity.toJSON());return;
            }
            entity.setDcschoolid(vtclassList.get(0).getDcschoolid().longValue());
        }

        if(this.tpStuScoreManager.AddOrUpdate(entity)){
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
        String classid=request.getParameter("classid");
        String clstype=request.getParameter("clstype");
        if(courseid==null||groupid==null||awardnumber==null||!UtilTool.isNumber(awardnumber.trim())||subjectid==null||classid==null||clstype==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        TpGroupScore entity=new TpGroupScore();
        entity.setClassid(Long.parseLong(classid));
        //如果classid<>null 则找schoolid  查询实体班级
        if(Integer.parseInt(clstype.trim())==1){
            //查询班级的dc_school_id
            ClassInfo cls=new ClassInfo();
            cls.setClassid(entity.getClassid().intValue());
            List<ClassInfo> clsList=classManage.getList(cls,null);
            if(clsList==null||clsList.size()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(jsonEntity.toJSON());return;
            }
            entity.setDcschoolid(clsList.get(0).getDcschoolid().longValue());
        }else if(Integer.parseInt(clstype.trim())==2){//虚拟班级
            TpVirtualClassInfo vc=new TpVirtualClassInfo();
            vc.setVirtualclassid(entity.getClassid().intValue());
            List<TpVirtualClassInfo> vtclassList=this.virtualClassManager.getList(vc,null);
            if(vtclassList==null||vtclassList.size()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(jsonEntity.toJSON());return;
            }
            entity.setDcschoolid(vtclassList.get(0).getDcschoolid().longValue());
        }


        entity.setAwardnumber(Integer.parseInt(awardnumber.trim()));
        entity.setCourseid(Long.parseLong(courseid.trim()));
        entity.setGroupid(Long.parseLong(groupid.trim()));
        entity.setSubjectid(Integer.parseInt(subjectid.trim()));
        if(this.tpGroupScoreManager.AddOrUpdate(entity)){
            jsonEntity.setType("success");
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
        }else
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().println(jsonEntity.toJSON());
    }

}
