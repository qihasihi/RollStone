package com.school.control.teachpaltform;

import com.school.control.base.BaseController;
import com.school.entity.*;

import com.school.entity.resource.ResourceInfo;
import com.school.entity.teachpaltform.*;
import com.school.manager.*;
import com.school.manager.inter.*;

import com.school.manager.inter.resource.IResourceManager;
import com.school.manager.inter.teachpaltform.*;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicThemeManager;

import com.school.manager.resource.ResourceManager;
import com.school.manager.teachpaltform.*;
import com.school.manager.teachpaltform.interactive.TpTopicManager;
import com.school.manager.teachpaltform.interactive.TpTopicThemeManager;
import com.school.util.JsonEntity;
import com.school.entity.teachpaltform.interactive.TpTopicInfo;
import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "teachercourse")
public class TpCourseController extends BaseController<TpCourseInfo> {
    public TpCourseController(){
        this.tpTaskManager=this.getManager(TpTaskManager.class);
        this.tpCourseManager=this.getManager(TpCourseManager.class);
        this.dictionaryManager=this.getManager(DictionaryManager.class);
        this.tpTopicThemeManager=this.getManager(TpTopicThemeManager.class);
        this.taskPerformanceManager=this.getManager(TaskPerformanceManager.class);
        this.tpCourseClassManager=this.getManager(TpCourseClassManager.class);
        this.tpGroupManager=this.getManager(TpGroupManager.class);
        this.tpTopicManager=this.getManager(TpTopicManager.class);
        this.tpCourseQuestionManager=this.getManager(TpCourseQuestionManager.class);
        this.tpCourseResourceManager=this.getManager(TpCourseResourceManager.class);
        this.tpVirtualClassManager=this.getManager(TpVirtualClassManager.class);
        this.termManager=this.getManager(TermManager.class);
        this.teachingMaterialManager=this.getManager(TeachingMaterialManager.class);
        this.trusteeShipClassManager=this.getManager(TrusteeShipClassManager.class);
        this.tpCourseTeachingMaterialManager=this.getManager(TpCourseTeachingMaterialManager.class);
        this.gradeManager=this.getManager(GradeManager.class);
        this.subjectUserManager=this.getManager(SubjectUserManager.class);
        this.subjectManager=this.getManager(SubjectManager.class);
        this.classUserManager=this.getManager(ClassUserManager.class);
        this.tpVirtualClassStudentManager=this.getManager(TpVirtualClassStudentManager.class);
        this.tpGroupStudentManager=this.getManager(TpGroupStudentManager.class);
        this.teacherManager=this.getManager(TeacherManager.class);
        this.commentManager=this.getManager(CommentManager.class);
        this.tpTeacherTeachMaterialManager=this.getManager(TpTeacherTeachMaterialManager.class);
        this.schoolManager=this.getManager(SchoolManager.class);
        this.resourceManager=this.getManager(ResourceManager.class);
        this.tpCourseRelatedManager=this.getManager(TpCourseRelatedManager.class);
    }
    private ITpTaskManager tpTaskManager;
    private ITpCourseManager tpCourseManager;
    private IDictionaryManager dictionaryManager;
    private ITpTopicThemeManager tpTopicThemeManager;
    private ITaskPerformanceManager taskPerformanceManager;
    private ITpCourseClassManager tpCourseClassManager;
    private ITpGroupManager tpGroupManager;
    private ITpTopicManager tpTopicManager;
   private ITpCourseQuestionManager tpCourseQuestionManager;
    private ITpCourseResourceManager tpCourseResourceManager;
    private ITpVirtualClassManager tpVirtualClassManager;
    private ITermManager termManager;
    private ITeachingMaterialManager teachingMaterialManager;
    private ITrusteeShipClassManager trusteeShipClassManager;
    private ITpCourseTeachingMaterialManager tpCourseTeachingMaterialManager;
    private IGradeManager gradeManager;
    private ISubjectUserManager subjectUserManager;
    private ISubjectManager subjectManager;
    private IClassUserManager classUserManager;
    private ITpVirtualClassStudentManager tpVirtualClassStudentManager;
    private ITpGroupStudentManager tpGroupStudentManager;
    private ITeacherManager teacherManager;
    private ICommentManager commentManager;
    private ITpTeacherTeachMaterialManager tpTeacherTeachMaterialManager;
    private ISchoolManager schoolManager;
    private IResourceManager resourceManager;
    private ITpCourseRelatedManager tpCourseRelatedManager;
    /**
     * 进入教师课题页
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */

    @RequestMapping(params="toTeacherCourseList",method=RequestMethod.GET)
    public ModelAndView toTeacherCourseList(HttpServletRequest request, HttpServletResponse response,ModelMap mp) throws Exception {
        String materialid=request.getParameter("materialid");
        if(materialid!=null&&materialid.length()>0){
            TeachingMaterialInfo tm = new TeachingMaterialInfo();
            tm.setMaterialid(Integer.parseInt(materialid));
            List<TeachingMaterialInfo> tmList = this.teachingMaterialManager.getList(tm, null);
            mp.put("materialinfo",tmList.get(0));
        }
        PageResult pr = new PageResult();
        pr.setOrderBy("SEMESTER_BEGIN_DATE");
        List<TermInfo> termList = this.termManager.getList(null, pr);
        mp.put("termList", termList);
        mp.put("currtTerm", this.termManager.getMaxIdTerm(false));
        int tscSize = 0;
        //被托管条数
        TrusteeShipClass tsc = new TrusteeShipClass();
        tsc.setReceiveteacherid(this.logined(request).getUserid());
        tsc.setIsaccept(0);
        List<TrusteeShipClass> tscList = this.trusteeShipClassManager.getList(tsc, null);
        if (tscList != null && tscList.size() > 0)
            tscSize += tscList.size();
        //判断是否存在托管
        int isTrust = 0;
        tsc = new TrusteeShipClass();
        tsc.setTrustteacherid(this.logined(request).getUserid());
        tsc.setIsaccept(1);
        tscList = this.trusteeShipClassManager.getList(tsc, null);
        if (tscList != null && tscList.size() > 0)
            isTrust = 1;
        mp.put("isTrust", isTrust);
        //托管被拒绝条数
        tsc = new TrusteeShipClass();
        tsc.setTrustteacherid(this.logined(request).getUserid());
        tsc.setIsaccept(2);
        tscList = this.trusteeShipClassManager.getList(tsc, null);
        if (tscList != null && tscList.size() > 0)
            tscSize += tscList.size();
        mp.put("tscSize", tscSize);
        mp.put("userid", this.logined(request).getUserid());
        return new ModelAndView("/teachpaltform/course/teacherCourseList", mp);
    }

    /**
     * 进入专题详情页面
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toCourseDetial", method = RequestMethod.GET)
    public ModelAndView toCourseDetail(HttpServletRequest request, HttpServletResponse response,
                                               ModelMap mp) throws Exception {
        JsonEntity je=new JsonEntity();
        String courseid=request.getParameter("courseid");
        String materialid=request.getParameter("materialid");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        TpCourseInfo tpCourseInfo=new TpCourseInfo();
        tpCourseInfo.setCourseid(Long.parseLong(courseid));
        PageResult p=new PageResult();
        p.setPageNo(1);
        p.setPageSize(1);
        List<TpCourseInfo>courseList=this.tpCourseManager.getList(tpCourseInfo,p);
        if(courseList==null||courseList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        TpCourseInfo tmpCourse=courseList.get(0);
        //专题教材
        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(tmpCourse.getCourseid());
        if(materialid!=null&&materialid.trim().length()>0)
            ttm.setTeachingmaterialid(Integer.parseInt(materialid));
        else if(tmpCourse.getMaterialids()!=null&&tmpCourse.getMaterialids().length()>0)
            ttm.setTeachingmaterialid(Integer.parseInt(tmpCourse.getMaterialids().split(",")[0]));
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);

        mp.put("courseInfo",tmpCourse);
        mp.put("materialList",materialList);
        return new ModelAndView("/teachpaltform/course/course-detail", mp);
    }

    /**
     * 进入教师课题回收站
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */

    @RequestMapping(params = "m=toTeacherCourseRecycle", method = RequestMethod.GET)
    public ModelAndView toTeacherCourseRecycle(HttpServletRequest request, HttpServletResponse response,
                                            ModelMap mp) throws Exception {

        return new ModelAndView("/teachpaltform/course/course-recycle-bin", mp);
    }

    /**
     * 查询教师课题回收站内容
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=getCourseRecycleList", method = RequestMethod.POST)
    public void getCoursRecycleList(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        UserInfo user = this.logined(request);

        TpCourseInfo tcInfo = this.getParameter(request,
                TpCourseInfo.class);
        tcInfo.setUserid(user.getUserid());
        tcInfo.setLocalstatus(2);
        PageResult presult = this.getPageResultParameter(request);
        List<TpCourseInfo> courseList = this.tpCourseManager.getTchCourseList(
                tcInfo, presult);
        presult.setList(courseList);
        je.setPresult(presult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * 进入专题列表（针对添加试题）
     *
     * @param request
     * @return
     * @throws Exception
     */

    @RequestMapping(params = "m=toCourseQuestionList", method = RequestMethod.GET)
    public ModelAndView toCourseQuestionList(HttpServletRequest request, HttpServletResponse response,ModelMap mp) throws Exception {
        JsonEntity je=new JsonEntity();
        String addCourseId = request.getParameter("addCourseId");
        if(addCourseId==null||addCourseId.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }


        GradeInfo obj = new GradeInfo();
        //obj.setGradename("高");
        List<GradeInfo> gradeList = this.gradeManager.getList(obj, null);
        mp.put("gradeList", gradeList);
        mp.put("addCourseId", addCourseId);

        if(addCourseId!=null&&addCourseId.length()>0){
            TpCourseTeachingMaterial tct=new TpCourseTeachingMaterial();
            tct.setCourseid(Long.parseLong(addCourseId));
            List<TpCourseTeachingMaterial>tctList=tpCourseTeachingMaterialManager.getList(tct,null);
            if(tctList!=null&&tctList.size()>0){
                request.setAttribute("subjectid",tctList.get(0).getSubjectid());
                request.setAttribute("materialInfo",tctList.get(0));
            }
        }

        //关联专题
        TpCourseRelatedInfo r=new TpCourseRelatedInfo();
        r.setCourseid(Long.parseLong(addCourseId));
        List<TpCourseRelatedInfo>courseRelatedList=this.tpCourseRelatedManager.getList(r,null);
        if(courseRelatedList!=null&&courseRelatedList.size()>0)
            mp.put("relateCourse","1");

        return new ModelAndView("/teachpaltform/course/questionCourseList", mp);
    }

    //  ajax 获取专题列表（针对试题）
    @RequestMapping(params = "m=getCourseQuestionList", method = RequestMethod.POST)
    public void getCourseQuestionList(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        TpCourseInfo obj = this.getParameter(request, TpCourseInfo.class);
        PageResult p=this.getPageResultParameter(request);
        List<TpCourseInfo> courseList = this.tpCourseManager.getCourseQuestionList(obj, p);
        p.setList(courseList);
        je.setPresult(p);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    //  ajax 获取学期教师的教授的学科和年级信息
    @RequestMapping(params = "m=tchTermCondition", method = RequestMethod.POST)
    public void getTchTermCondition(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        JsonEntity jeEntity = new JsonEntity();

        String termid = request.getParameter("termid");
        if (termid == null) {
            jeEntity.setType("error");
            jeEntity.setMsg("学期参数错误！");
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
        TermInfo term = new TermInfo();
        term.setRef(termid);
        List<TermInfo> tmList = this.termManager.getList(term, null);
        if (tmList == null || tmList.size() == 0) {
            jeEntity.setType("error");
            jeEntity.setMsg("学期参数错误！");
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
        String year = tmList.get(0).getYear();
        List<Map<String,Object>> gradeSubjectList = new ArrayList<Map<String, Object>>();
        SubjectUser su = new SubjectUser();
        su.setUserid(this.logined(request).getRef());
        List<SubjectUser> subuserList = this.subjectUserManager.getList(su, null);

        List<GradeInfo> gradeList = this.gradeManager.getTchGradeList(this.logined(request).getUserid(), year);
        if(gradeList!=null&&gradeList.size()>0){
            for(int i = 0;i<subuserList.size();i++){
                //当前学期、学科、年级下的授课班级
                for(int j=0;j<gradeList.size();j++){
                    ClassUser cu = new ClassUser();
                    cu.setClassgrade(gradeList.get(j).getGradevalue());
                    cu.setUserid(this.logined(request).getRef());
                    cu.setRelationtype("任课老师");
                    cu.setSubjectid(subuserList.get(i).getSubjectid());
                    cu.setYear(year);
                    List<ClassUser>classList=this.classUserManager.getList(cu,null);

                    TpVirtualClassInfo tpVirtualClassInfo=new TpVirtualClassInfo();
                    tpVirtualClassInfo.setCuserid(this.logined(request).getUserid());
                    tpVirtualClassInfo.setStatus(1);
                    List<TpVirtualClassInfo>virtualClassInfoList=this.tpVirtualClassManager.getList(tpVirtualClassInfo,null);
                    if((classList!=null&&classList.size()>0)||(virtualClassInfoList!=null&&virtualClassInfoList.size()>0)){
                        Map<String,Object> map = new HashMap<String, Object>();
                        map.put("gradeid",gradeList.get(j).getGradeid());
                        map.put("gradevalue",gradeList.get(j).getGradevalue());
                        map.put("subjectid",subuserList.get(i).getSubjectid());
                        map.put("subjectname",subuserList.get(i).getSubjectname());
                        gradeSubjectList.add(map);
                    }
                }
            }
        }
        List ls = new ArrayList();
      //  ls.add(subuserList);
       // ls.add(gradeList);
        ls.add(gradeSubjectList);
        ls.add(tmList.get(0));
        jeEntity.setObjList(ls);
        response.getWriter().print(jeEntity.toJSON());
    }

    /**
     * 进入学生课题首页
     */
    @RequestMapping(params = "m=toStudentCourseList", method = RequestMethod.GET)
    public ModelAndView toStudentCourseList(HttpServletRequest request, ModelMap mp) throws Exception {
        PageResult pr = new PageResult();
        UserInfo u = this.logined(request);
        pr.setOrderBy("SEMESTER_BEGIN_DATE");
        List<TermInfo> termList = this.termManager.getList(null, pr);
        mp.put("termList", termList);
        TermInfo currtTerm = this.termManager.getMaxIdTerm(false);
        mp.put("currtTerm", currtTerm);
        List<SubjectInfo> subjectList = this.subjectManager.getHavaCourseSubject(currtTerm.getRef(),u.getRef(),u.getUserid());
        mp.put("subjectList", subjectList);
        if(subjectList!=null&&subjectList.size()>0){
            return new ModelAndView("/teachpaltform/course/studentCourseList");
        }else{
            return new ModelAndView("/teachpaltform/course/noCourse");
        }
    }

    /**
     * 进入学生班級主页
     */
    @RequestMapping(params = "m=toStudeClassList", method = RequestMethod.GET)
    public ModelAndView toStudeClassList(HttpServletRequest request, HttpServletResponse response, ModelMap mp) throws Exception {

        JsonEntity jeEntity = new JsonEntity();
        String classid = request.getParameter("classid");
        String classtype = request.getParameter("classtype");
        String subjectid=request.getParameter("subjectid");

        if (classid == null || classtype == null) {
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }

        mp.put("classid", classid);
        if (classtype.equals("1")) {
            mp.put("classtype", 1);
            ClassUser cu = new ClassUser();
            cu.setClassid(Integer.parseInt(classid));
            cu.setRelationtype("学生");
            cu.setSubjectid(Integer.parseInt(subjectid));
            cu.setCompletenum(1);//查询任务完成率
            List<ClassUser> stuList = this.classUserManager.getList(cu, null);
            mp.put("students", stuList);
        }

        if (classtype.equals("2")) {
            mp.put("classtype", 2);
            TpVirtualClassStudent tvcs = new TpVirtualClassStudent();
            tvcs.setVirtualclassid(Integer.parseInt(classid));
            List<TpVirtualClassStudent> stuList = this.tpVirtualClassStudentManager.getList(tvcs, null);
            mp.put("students", stuList);
        }

        UserInfo user = this.logined(request);
        TermInfo term = this.termManager.getMaxIdTerm(false);
        ClassUser cu = new ClassUser();
        cu.setUserid(user.getRef());
        cu.setRelationtype("学生");
        cu.setYear(term.getYear());
        List<ClassUser> cuList = this.classUserManager.getList(cu, null);
        mp.put("cuList", cuList);

        TpVirtualClassStudent tvcs = new TpVirtualClassStudent();
        tvcs.setUserid(user.getUserid());
        List<TpVirtualClassStudent> vsList = this.tpVirtualClassStudentManager.getList(tvcs, null);
        mp.put("vsList", vsList);

        // 获取该班级所有我的小组信息
        List<TpGroupInfo> groupList = this.tpGroupManager.getMyGroupList(
                Integer.parseInt(classid), Integer.parseInt(classtype), term.getRef(), null, user.getUserid(),null);
        if(groupList!=null && groupList.size()>0){
            for(TpGroupInfo g:groupList){
                TpGroupStudent gs = new TpGroupStudent();
                gs.setGroupid(g.getGroupid());
                List<TpGroupStudent> groupStudentList = this.tpGroupStudentManager.getList(gs, null);
                g.setTpgroupstudent(groupStudentList);
            }
        }
        mp.put("gsList",groupList);
        return new ModelAndView("/teachpaltform/course/studentClassList",mp);
    }

    /**
     * 进入班級课题主页
     */
    @RequestMapping(params = "m=toClassCourseList", method = RequestMethod.GET)
    public ModelAndView toClassCourseList(HttpServletRequest request, HttpServletResponse response, ModelMap mp) throws Exception {
        UserInfo user = this.logined(request);

        JsonEntity jeEntity = new JsonEntity();
        String classid = request.getParameter("classid");
        String classtype = request.getParameter("classtype");
        UserInfo u=this.logined(request);
        TermInfo ti = this.termManager.getMaxIdTerm(false);
        if (ti == null || classid==null || classtype==null) {
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("学期参数错误,请联系教务。"));// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }

        List<ClassUser> clsList = this.classUserManager.getListByTchYear(user.getRef(), ti.getYear());
        TpVirtualClassInfo tvc= new TpVirtualClassInfo();
        tvc.setCuserid(u.getUserid());
        tvc.setStatus(1);
        List<TpVirtualClassInfo> tvcList=this.tpVirtualClassManager.getList(tvc, null);

        TpCourseClass tcc = new TpCourseClass();
        tcc.setUserid(user.getUserid());
        tcc.setTermid(ti.getRef());
        tcc.setClassid(Integer.parseInt(classid));
        List<TpCourseClass> courseList = this.tpCourseClassManager.getList(tcc, null);

        mp.put("courseList", courseList);
        mp.put("classid",classid);
        mp.put("classtype",classtype);
        mp.put("classes", clsList);
        mp.put("vclasses", tvcList);
        return new ModelAndView("/teachpaltform/class/classCourseList",mp);
    }

    /**
     * 进入班級评价主页
     */
    @RequestMapping(params = "m=toClassCommentList", method = RequestMethod.GET)
    public ModelAndView toClassCommentList(HttpServletRequest request, HttpServletResponse response, ModelMap mp) throws Exception {
        UserInfo user = this.logined(request);

        JsonEntity jeEntity = new JsonEntity();
        String courseid = request.getParameter("courseid");
        String classid = request.getParameter("classid");
        String classtype = request.getParameter("classtype");
        String type = request.getParameter("type");
        String tchid = request.getParameter("tchid");
        List<Map<String,Object>> clsList = this.commentManager.getClassList(Long.parseLong(courseid),this.logined(request).getUserid(),Integer.parseInt(type));

        List<Map<String,Object>> tvcList=this.commentManager.getVirClassList(Long.parseLong(courseid),this.logined(request).getUserid(),Integer.parseInt(type));

        mp.put("classes", clsList);
        mp.put("vclasses", tvcList);
        mp.put("courseid",courseid);
        mp.put("tchid", this.logined(request).getUserid());
        return new ModelAndView("/teachpaltform/class/classCommentList",mp);
    }

    /**
     * 进入班級学生详情
     */
    @RequestMapping(params = "m=toClassStudentList", method = RequestMethod.GET)
    public ModelAndView toClassStudentList(HttpServletRequest request, HttpServletResponse response, ModelMap mp) throws Exception {
        UserInfo user = this.logined(request);

        JsonEntity jeEntity = new JsonEntity();
        String classid = request.getParameter("classid");
        String classtype = request.getParameter("classtype");
        String subjectid=request.getParameter("subjectid");
        UserInfo u=this.logined(request);
        TermInfo ti = this.termManager.getMaxIdTerm(false);
        if (ti == null || classid==null || classtype==null||subjectid==null) {
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("学期参数错误,请联系教务。"));// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }

        List<ClassUser> clsList = this.classUserManager.getListByTchYear(user.getRef(), ti.getYear());
        TpVirtualClassInfo tvc= new TpVirtualClassInfo();
        tvc.setCuserid(u.getUserid());
        tvc.setStatus(1);
        List<TpVirtualClassInfo> tvcList=this.tpVirtualClassManager.getList(tvc, null);

        List<ClassUser> cuList = null;
        List<TpVirtualClassStudent> vcuList = null;
        if(Integer.parseInt(classtype)==1){
            ClassUser cu = new ClassUser();
            cu.setClassid(Integer.parseInt(classid));
            cu.setRelationtype("学生");
            cu.setSubjectid(Integer.parseInt(subjectid));
            cu.setCompletenum(1);//查询任务完成比率
            cu.getUserinfo().setStateid(0);

            cuList = this.classUserManager.getList(cu,null);
            mp.put("cuList", cuList);
        }

        if(Integer.parseInt(classtype)==2){
            TpVirtualClassStudent vcs = new TpVirtualClassStudent();
            vcs.setVirtualclassid(Integer.parseInt(classid));
            vcs.setStateid(0);

            vcuList = this.tpVirtualClassStudentManager.getList(vcs,null);
            mp.put("cuList", vcuList);
        }

        mp.put("classid",classid);
        mp.put("classtype",classtype);
        mp.put("classes", clsList);
        mp.put("vclasses", tvcList);
        return new ModelAndView("/teachpaltform/class/classStudentList",mp);
    }

    /**
     * 获取学生课题列表(分页)
     */
    @RequestMapping(params = "m=getStuCourselistAjax", method = RequestMethod.POST)
    public void getStuCourseList(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String subjectid=request.getParameter("subjectid");
        String termid=request.getParameter("termid");
        if(subjectid==null||subjectid.trim().length()<1||termid==null||termid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        PageResult presult = this.getPageResultParameter(request);
        // 分页查询
        TpCourseInfo tcInfo = this.getParameter(request, TpCourseInfo.class);
        tcInfo.setUserid(this.logined(request).getUserid());
        tcInfo.setSelectType(1);
        List<TpCourseInfo> stucourseList = this.tpCourseManager.getStuCourseList(tcInfo, presult);
        String courseids="";
        if(stucourseList!=null&&stucourseList.size()>0){
            for(int i =0;i<stucourseList.size();i++){
                courseids+=stucourseList.get(i).getCourseid();
                if(i<stucourseList.size()-1)
                    courseids+=",";
            }
            List<Map<String,Object>> performanceListNum=this.taskPerformanceManager.getStuSelfPerformanceNum(this.logined(request).getUserid(),courseids,1,termid,Integer.parseInt(subjectid));
            for(int i=0;i<stucourseList.size();i++){
                if(performanceListNum!=null&&performanceListNum.size()>0){
                    for(int j=0;j<performanceListNum.size();j++){
                        if(stucourseList.get(i).getCourseid()==Long.parseLong(performanceListNum.get(j).get("COURSE_ID").toString())){
                            stucourseList.get(i).setUncompletenum(Integer.parseInt(performanceListNum.get(j).get("NUM").toString()));
                            break;
                        }else{
                            stucourseList.get(i).setUncompletenum(0);
                        }
                    }
                }else{
                    stucourseList.get(i).setUncompletenum(0);
                }
            }
        }
        Set<Map<String, Object>> classset = new HashSet<Map<String, Object>>();


        TpVirtualClassStudent ts=new TpVirtualClassStudent();
        ts.setUserid(this.logined(request).getUserid());
        List<TpVirtualClassStudent>tpVirtualClassStudentList=this.tpVirtualClassStudentManager.getList(ts,null);

        TermInfo t=new TermInfo();
        t.setRef(termid);
        List<TermInfo>termInfoList=this.termManager.getList(t,null);
        if(termInfoList==null||termInfoList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        ClassUser cu=new ClassUser();
        cu.setUserid(this.logined(request).getRef());
        cu.setRelationtype("学生");
        cu.setYear(termInfoList.get(0).getYear());
        List<ClassUser>classList=this.classUserManager.getList(cu,null);
        List<ClassUser>tmpClassUser=new ArrayList<ClassUser>();
        if(classList!=null&&classList.size()>0){
            for (ClassUser classUser:classList){
                ClassUser tmpcu=new ClassUser();
                tmpcu.setYear(classUser.getYear());
                tmpcu.setRelationtype("任课老师");
                tmpcu.setSubjectid(Integer.parseInt(subjectid));
                tmpcu.setClassid(classUser.getClassid());
                List<ClassUser>classUserList=this.classUserManager.getList(tmpcu,null);
                if(classUserList!=null&&classUserList.size()>0)
                    tmpClassUser.add(classUserList.get(0));
            }
        }
        List l = new ArrayList();
        l.add(stucourseList);



        // 获取老师 班级参数
        /*if (stucourseList != null && stucourseList.size() > 0) {

            for (TpCourseInfo t : stucourseList) {
                if (t.getClassEntity() != null && t.getClassEntity().size() > 0) {
                    Map<String, Object> temp = t.getClassEntity().get(0);
                    temp.remove("CLASS_TIME");
                    temp.remove("HANDLE_FLAG");               // 防止浅复制
                    classset.add(temp);
                }
            }
            l.add(classset);
        } */
        // 根据获取的教师 班级参数查询小组信息
        List<TpGroupInfo> groupList = new ArrayList<TpGroupInfo>();
       /* if (classset.size() > 0) {
            for (Map<String, Object> mtch : classset) {
                List<TpGroupInfo> gl = this.tpGroupManager.getMyGroupList(
                        Integer.parseInt(mtch.get("CLASS_ID").toString()),
                        Integer.parseInt(mtch.get("CLASS_TYPE").toString()),
                        tcInfo.getTermid(),
                        null,
                        this.logined(request).getUserid());
                if (gl != null && gl.size() > 0)
                    groupList.add(gl.get(0));
            }
        }*/
        //普通班级小组
        if(tmpClassUser.size()>0){
            for (ClassUser clsu:tmpClassUser){
                List<TpGroupInfo> gl = this.tpGroupManager.getMyGroupList(
                        clsu.getClassid(),
                        1,
                        termid,
                        null,
                        this.logined(request).getUserid(),clsu.getSubjectid());
                 groupList.addAll(gl);
            }

        }
        //虚拟班级小组
        if(tpVirtualClassStudentList!=null&&tpVirtualClassStudentList.size()>0){
            for (TpVirtualClassStudent clsu:tpVirtualClassStudentList){
                List<TpGroupInfo> gl = this.tpGroupManager.getMyGroupList(
                        clsu.getVirtualclassid(),
                        2,
                        termid,
                        null,
                        this.logined(request).getUserid(),null);
                groupList.addAll(gl);
            }

        }


        l.add(tmpClassUser);
        l.add(tpVirtualClassStudentList);
        l.add(groupList);
        UserInfo u = this.logined(request);
        List<SubjectInfo> subjectList = this.subjectManager.getHavaCourseSubject(termid,u.getRef(),u.getUserid());
        l.add(subjectList);
        je.setObjList(l);
        response.getWriter().print(je.toJSON());
    }

    /**
     * 获取学期学生课题学科
     * @RequestMapping(params = "m=getStuCourseSubject", method = RequestMethod.POST)
     * public void getStuCourseSubject(HttpServletRequest request,
     * HttpServletResponse response) throws Exception {
     * JsonEntity je = new JsonEntity();
     * PageResult presult = this.getPageResultParameter(request);
     * // 分页查询
     *
     * TeacherCourseInfo tcInfo = this.getParameter(request,
     * TeacherCourseInfo.class);
     * if(tcInfo==null||tcInfo.getTermid()==null){
     * je.setType("error");
     * je.setMsg("学期参数错误！");
     * response.getWriter().print(je.toJSON());
     * }
     * tcInfo.setUserid(this.logined(request).getRef());
     * List<Map<String,Object>> stucourseList = this.teacherCourseManager.getStudentSubjectList(tcInfo);
     * je.setObjList(stucourseList);
     * je.setType("success");
     * response.getWriter().print(je.toJSON());
     * }
     */

    /**
     * 添加课题
     */
    @RequestMapping(params = "m=addCourse", method = RequestMethod.POST)
    public void doAddCourse(HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        TpCourseInfo tc = this.getParameter(request, TpCourseInfo.class);
        if (tc.getCoursename() == null || tc.getCoursename().length() < 1) {
            je.setMsg("没有专题名称参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (tc.getMaterialidvalues() == null || tc.getMaterialidvalues().trim().length() < 1) {
            je.setMsg("没有专题教材编号参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (tc.getSharetype() == null || tc.getSharetype() < 1) {
            je.setMsg("没有获得分享类型参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        String subjectid = request.getParameter("subjectid");
        if (subjectid == null || subjectid.length() < 1) {
            je.setMsg("没有获取学科参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        String gradeid = request.getParameter("gradeid");
        if (gradeid == null || gradeid.length() < 1) {
            je.setMsg("没有获取年级参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        String termid = request.getParameter("termid");
        if (termid == null || termid.length() < 1) {
            je.setMsg("没有获取学期参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        String classstr = request.getParameter("classidstr");
        String vclassstr = request.getParameter("vclassidstr");
        if (classstr == null && vclassstr == null) {
            je.setMsg("没有获取关联班级参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (classstr.trim().length() < 1 && vclassstr.trim().length() < 1) {
            je.setMsg("没有获取关联班级参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        String classtimestr = request.getParameter("classTimeArray");
        String vclasstimestr = request.getParameter("vclassTimeArray");
        if (classtimestr == null && vclasstimestr == null) {
            je.setMsg("没有获取关联班级开课时间参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }

        if (classtimestr.trim().length() == 0 && vclasstimestr.trim().length() == 0) {
            je.setMsg("没有获取关联班级开课时间参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }

        List<String> sqlListArray = new ArrayList<String>();
        List<List<Object>> objListArray = new ArrayList<List<Object>>();
        List<Object> objList = null;
        StringBuilder sql = null;

        ////////////////////////// 添加专题，配置相关参数
        Long courseid = this.tpCourseManager.getNextId(true);
        tc.setCourseid(courseid);
        tc.setCuserid(this.logined(request).getUserid());

        TeacherInfo t = new TeacherInfo();
        t.setUserid(this.logined(request).getRef());
        List<TeacherInfo> tl = this.teacherManager.getList(t, null);
        if (tl == null || tl.size() == 0) {
            je.setMsg("教师信息获取失败，请确认身份或者联系管理员！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        tc.setTeachername(tl.get(0).getTeachername());

        sql = new StringBuilder();
        objList = this.tpCourseManager.getSaveSql(tc, sql);
        if (sql == null || objList == null) {
            je.setMsg("专题数据写入出错！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        sqlListArray.add(sql.toString());
        objListArray.add(objList);

        // 添加专题教材关联
        if (tc.getMaterialidvalues() != null && tc.getMaterialidvalues().split(",").length>0) {
            String[] materialidvalues = tc.getMaterialidvalues().split(",");
            for (int i = 0; i < materialidvalues.length; i++) {
                TpCourseTeachingMaterial tctm = new TpCourseTeachingMaterial();
                tctm.setCourseid(tc.getCourseid());
                tctm.setTeachingmaterialid(Integer.parseInt(materialidvalues[i]));
                sql = new StringBuilder();
                objList = this.tpCourseTeachingMaterialManager.getSaveSql(tctm, sql);
                if (sql == null || objList == null) {
                    je.setMsg("专题教材数据写入出错！");// 异常错误，参数不齐，无法正常访问!
                    je.setType("error");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

        }else{
            je.setMsg("获取教材参数错误，请重试！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }

        // 添加关联班级
        if (classstr != null && classtimestr != null
                && classstr.trim().length() > 0 && classtimestr.trim().length() > 0) {
            String[] classArray = classstr.split(",");
            String[] classtimeArray = classtimestr.split(",");
            //教学班级
            for (int i = 0; i < classArray.length; i++) {
                TpCourseClass cc = new TpCourseClass();
                cc.setClassid(Integer.parseInt(classArray[i]));
                cc.setCourseid(courseid);
                cc.setSubjectid(Integer.parseInt(subjectid));
                cc.setGradeid(Integer.parseInt(gradeid));
                cc.setTermid(termid);
                cc.setBegintime(UtilTool.StringConvertToDate(classtimeArray[i]));
                cc.setClasstype(1);
                cc.setUserid(tc.getCuserid());
                sql = new StringBuilder();
                objList = this.tpCourseClassManager.getSaveSql(cc, sql);
                if (sql == null || objList == null) {
                    je.setMsg("专题班级数据写入出错！");// 异常错误，参数不齐，无法正常访问!
                    je.setType("error");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }


        //虚拟班级
        if (vclassstr != null && vclasstimestr != null
                && vclassstr.trim().length() > 0 && vclasstimestr.trim().length() > 0) {
            String[] vclassArray = vclassstr.split(",");
            String[] vclasstimeArray = vclasstimestr.split(",");
            for (int i = 0; i < vclassArray.length; i++) {
                TpCourseClass cc = new TpCourseClass();
                cc.setClassid(Integer.parseInt(vclassArray[i]));
                cc.setCourseid(courseid);
                cc.setSubjectid(Integer.parseInt(subjectid));
                cc.setGradeid(Integer.parseInt(gradeid));
                cc.setTermid(termid);
                cc.setBegintime(UtilTool.StringConvertToDate(vclasstimeArray[i]));
                cc.setClasstype(2);
                cc.setUserid(tc.getCuserid());
                sql = new StringBuilder();
                objList = this.tpCourseClassManager.getSaveSql(cc, sql);
                if (sql == null || objList == null) {
                    je.setMsg("专题班级数据写入出错！");// 异常错误，参数不齐，无法正常访问!
                    je.setType("error");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }

        //关联专题
        String selectedCourseid=request.getParameter("selectcourseid");
        if(selectedCourseid.length()>0){
            String[] courseids = selectedCourseid.split("\\|");
            int length = courseids.length;
            for(int i = 0;i<length;i++){
                TpCourseRelatedInfo obj = new TpCourseRelatedInfo();
                obj.setCourseid(courseid);
                obj.setRelatedcourseid(Long.parseLong(courseids[i]));
                sql = new StringBuilder();
                objList = this.tpCourseRelatedManager.getSaveSql(obj, sql);
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }
        if (sqlListArray.size() > 0 && objListArray.size() > 0) {
            boolean bo = this.tpCourseManager.doExcetueArrayProc(
                    sqlListArray, objListArray);
            if (bo) {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
            } else {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        } else {
            je.setMsg(UtilTool.msgproperty
                    .getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 添加引用课题（引用从专题库中选择的专题）
     * 1列表过滤掉当前教师引用过的专题（不分学期)
     * 2拷贝副本，增加当前学期tp_j_course_class关联表数据
     */
    @RequestMapping(params = "m=doAddQuoteCourse", method = RequestMethod.POST)
    public void doAddQuoteCourse(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        UserInfo user = this.logined(request);

        String courseids = request.getParameter("courseids");
        String subjectid = request.getParameter("subjectid");
        String gradeid = request.getParameter("gradeid");
        String termid = request.getParameter("termid");

        if (subjectid == null || subjectid.length() < 1) {
            je.setMsg("没有获取学科参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (gradeid == null || gradeid.length() < 1) {
            je.setMsg("没有获取年级参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (courseids == null || courseids.length() < 1) {
            je.setMsg("专题参数错误");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (courseids.split(",").length == 0) {
            je.setMsg("没有专题教材编号参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }

        // 获取用户教师身份信息
        TeacherInfo t = new TeacherInfo();
        t.setUserid(user.getRef());
        List<TeacherInfo> tl = this.teacherManager.getList(t, null);
        if (tl == null || tl.size() == 0) {
            je.setMsg("教师信息获取失败，请确认身份或者联系管理员！");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(je.toJSON());
            return;
        }
        t = tl.get(0);

        List<String> sqlListArray = new ArrayList<String>();
        List<List<Object>> objListArray = new ArrayList<List<Object>>();
        List<Object> objList = null;
        StringBuilder sql = null;

        for (String idVal : courseids.split(",")) {
            Long courseid = Long.parseLong(idVal);
            TpCourseInfo tc = new TpCourseInfo();
            tc.setCourseid(courseid);
            List<TpCourseInfo> tcList = this.tpCourseManager.getList(tc, null);
            if (tcList == null || tcList.size() == 0) {
                je.setMsg("专题信息不存在，请刷新页面重试！");// 异常错误，参数不齐，无法正常访问!
                je.setType("error");
                response.getWriter().print(je.toJSON());
                return;
            }

            tc = tcList.get(0);
            //重置部分专题信息
            Long nextCourseId=this.tpCourseManager.getNextId(true);
            tc.setQuoteid(tc.getCourseid());
            tc.setTeachername(t.getTeachername());
            tc.setCourseid(nextCourseId);
            tc.setCuserid(user.getUserid());

            sql = new StringBuilder();
            objList = this.tpCourseManager.getSaveSql(tc, sql);

            sqlListArray.add(sql.toString());
            objListArray.add(objList);

            //添加专题教材关联
            if(tc.getCourseid()!=null){
                //任务
                TpCourseTeachingMaterial ctm=new TpCourseTeachingMaterial();
                ctm.setCourseid(courseid);//引用专题ID
                List<TpCourseTeachingMaterial> ctmList=this.tpCourseTeachingMaterialManager.getList(ctm,null);
                if(ctmList!=null&&ctmList.size()>0){
                    for (TpCourseTeachingMaterial tctm : ctmList){
                        tctm.setRef(null);
                        tctm.setCtime(null);
                        tctm.setCourseid(tc.getCourseid()); //新建专题
                        sql=new StringBuilder();
                        objList=this.tpCourseTeachingMaterialManager.getSaveSql(tctm,sql);
                        if(sql!=null&&objList!=null){
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                }
            }

            // 添加默认专题班级关联记录
            TpCourseClass cc = new TpCourseClass();
            cc.setClassid(0);
            cc.setCourseid(tc.getCourseid());
            cc.setSubjectid(Integer.parseInt(subjectid));
            cc.setGradeid(Integer.parseInt(gradeid));
            cc.setTermid(termid);
            //沿用专题，开始时间获取当前时间
            Date d = new Date();
            cc.setBegintime(d);
            cc.setClasstype(0);
            cc.setUserid(tc.getCuserid());
            sql = new StringBuilder();
            objList = this.tpCourseClassManager.getSaveSql(cc, sql);
            if (sql == null || objList == null) {
                je.setMsg("专题班级数据写入出错！");// 异常错误，参数不齐，无法正常访问!
                je.setType("error");
                response.getWriter().print(je.toJSON());
                return;
            }
            sqlListArray.add(sql.toString());
            objListArray.add(objList);

            //添加专题引用元素
            if(tc.getCourseid()!=null&&tc.getCourselevel()!=null){
                //任务
                TpTaskInfo taskInfo=new TpTaskInfo();
                taskInfo.setCourseid(courseid);//引用专题ID
                List<TpTaskInfo>taskInfoList=this.tpTaskManager.getList(taskInfo,null);
                if(taskInfoList!=null&&taskInfoList.size()>0){
                    for (TpTaskInfo tmpTask : taskInfoList){
                        tmpTask.setCourseid(nextCourseId); //新建专题
                        tmpTask.setCuserid(this.logined(request).getRef());
                        Long taskid=-1l;
                        taskid=this.tpTaskManager.getNextId(true);
                        System.out.println("taskid:"+taskid);
                        tmpTask.setTaskid(taskid);
                        sql=new StringBuilder();
                        objList=this.tpTaskManager.getSaveSql(tmpTask,sql);
                        if(sql!=null&&objList!=null){
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                }

                //资源
                TpCourseResource tr=new TpCourseResource();
                tr.setCourseid(courseid);//引用专题ID
                tr.setResstatus(1);//已审核资源
                List<TpCourseResource>courseResourceList=this.tpCourseResourceManager.getList(tr,null);
                if(courseResourceList!=null&&courseResourceList.size()>0){
                    for(TpCourseResource tmpCourseResource : courseResourceList){
                        tmpCourseResource.setCourseid(nextCourseId);
                        tmpCourseResource.setReferenceid(Long.parseLong("1"));
                        tmpCourseResource.setRef(this.tpCourseResourceManager.getNextId(true));
                        sql=new StringBuilder();
                        objList=this.tpCourseResourceManager.getSaveSql(tmpCourseResource,sql);
                        if(sql!=null&&objList!=null){
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                }
                //试题
                TpCourseQuestion tq=new TpCourseQuestion();
                tq.setCourseid(courseid);
                List<TpCourseQuestion>courseQuestionList=this.tpCourseQuestionManager.getList(tq,null);
                if(courseQuestionList!=null&&courseQuestionList.size()>0){
                    for (TpCourseQuestion tmpQues : courseQuestionList){
                        tmpQues.setCourseid(nextCourseId);
                        tmpQues.setRef(this.tpCourseQuestionManager.getNextId(true));
                        tmpQues.setReferenceid(Long.parseLong("1"));
                        sql=new StringBuilder();
                        objList=this.tpCourseQuestionManager.getSaveSql(tmpQues,sql);
                        if(sql!=null&&objList!=null){
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                }

                //论题
                TpTopicInfo tt=new TpTopicInfo();
                tt.setCourseid(courseid);
                List<TpTopicInfo>topicInfoList=this.tpTopicManager.getList(tt,null);
                if(topicInfoList!=null&&topicInfoList.size()>0){
                    for(TpTopicInfo tmpTopic :topicInfoList){
                        Long nextTopicid=this.tpTopicManager.getNextId(true);
                        //主题
                        TpTopicThemeInfo themeInfo=new TpTopicThemeInfo();
                        themeInfo.setCourseid(tmpTopic.getCourseid());
                        themeInfo.setTopicid(tmpTopic.getTopicid());
                        List<TpTopicThemeInfo>themeInfoList=this.tpTopicThemeManager.getList(themeInfo,null);
                        if(themeInfoList!=null&&themeInfoList.size()>0){
                            for(TpTopicThemeInfo tmpThemeInfo :themeInfoList){
                            	tmpThemeInfo.setQuoteid(tmpThemeInfo.getThemeid());//记录引用的ID
                            	tmpThemeInfo.setCuserid(this.logined(request).getUserid());
                                tmpThemeInfo.setThemeid(this.tpTopicThemeManager.getNextId(true));
                                tmpThemeInfo.setTopicid(nextTopicid);
                                tmpThemeInfo.setCourseid(nextCourseId);
                                tmpThemeInfo.setStatus(2L);//引用专题下  1：显示   2：不显示
                                sql=new StringBuilder();
                                objList=this.tpTopicThemeManager.getSaveSql(tmpThemeInfo,sql);
                                if(sql!=null&&objList!=null){
                                    sqlListArray.add(sql.toString());
                                    objListArray.add(objList);
                                }
                                
                                if(tmpThemeInfo.getThemecontent()!=null){
                                	  //得到theme_content的更新语句
                                    this.tpTopicThemeManager.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "theme_content"
                                            , tmpThemeInfo.getThemecontent(), tmpThemeInfo.getThemeid().toString(),sqlListArray,objListArray);
                                }
                                if(tmpThemeInfo.getCommentcontent()!=null){
                                	  //得到comment_content的更新语句
                                    this.tpTopicThemeManager.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "comment_content"
                                            , tmpThemeInfo.getCommentcontent(), tmpThemeInfo.getThemeid().toString(),sqlListArray,objListArray);
                            		
                                }
                            }
                        }
                        //引用的TOPIC_ID                        
                        tmpTopic.setQuoteid(tmpTopic.getTopicid());
                        tmpTopic.setCourseid(nextCourseId);
                        tmpTopic.setCuserid(this.logined(request).getUserid());
                        tmpTopic.setTopicid(nextTopicid);
                        sql=new StringBuilder();
                        objList=this.tpTopicManager.getSaveSql(tmpTopic,sql);
                        if(sql!=null&&objList!=null){
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                }
            }
        }

        if (sqlListArray.size() > 0 && objListArray.size() > 0) {
            boolean bo = this.tpCourseManager.doExcetueArrayProc(
                    sqlListArray, objListArray);
            if (bo) {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
            } else {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        } else {;//有可能存在只有专题信息，无下面元素信息的情况，不应该
            //je.setMsg(UtilTool.msgproperty.getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
        }
        response.getWriter().print(je.toJSON());
    }



    /**
     * 添加沿用课题
     * 1列表不过滤数据
     * 2查看是否存在副本
     * 3存在：检测是否有当前学期关联表数据（tp_j_course_class) 有数据跳出
     * 4不存在：创建副本 增加当前学期关联表数据（tp_j_course_class)
     */
    @RequestMapping(params = "m=doContinueUseCourse", method = RequestMethod.POST)
    public void doContinueUseCourse(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        UserInfo user = this.logined(request);

        String courseids = request.getParameter("courseids");
        String subjectid = request.getParameter("subjectid");
        String gradeid = request.getParameter("gradeid");
        String termid = request.getParameter("termid");

        if (subjectid == null || subjectid.length() < 1) {
            je.setMsg("没有获取学科参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (gradeid == null || gradeid.length() < 1) {
            je.setMsg("没有获取年级参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (courseids == null || courseids.length() < 1) {
            je.setMsg("专题参数错误");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (courseids.split(",").length == 0) {
            je.setMsg("没有专题教材编号参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }

        // 获取用户教师身份信息
        TeacherInfo t = new TeacherInfo();
        t.setUserid(user.getRef());
        List<TeacherInfo> tl = this.teacherManager.getList(t, null);
        if (tl == null || tl.size() == 0) {
            je.setMsg("教师信息获取失败，请确认身份或者联系管理员！");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(je.toJSON());
            return;
        }
        t = tl.get(0);

        List<String> sqlListArray = new ArrayList<String>();
        List<List<Object>> objListArray = new ArrayList<List<Object>>();
        List<Object> objList = null;
        StringBuilder sql = null;

        for (String idVal : courseids.split(",")) {
            Long courseid = Long.parseLong(idVal);
            TpCourseInfo tc = new TpCourseInfo();
            tc.setCourseid(courseid);
            List<TpCourseInfo> tcList = this.tpCourseManager.getList(tc, null);
            if (tcList == null || tcList.size() == 0) {
                je.setMsg("专题信息不存在，请刷新页面重试！");// 异常错误，参数不齐，无法正常访问!
                response.getWriter().print(je.toJSON());
                return;
            }

            tc = tcList.get(0);

            //检测是否存在副本
            TpCourseInfo quoteCouse=new TpCourseInfo();
            quoteCouse.setCuserid(this.logined(request).getUserid());
            quoteCouse.setQuoteid(tc.getQuoteid()==null||tc.getQuoteid().intValue()==0?tc.getCourseid():tc.getQuoteid());
            List<TpCourseInfo>quoteList=this.tpCourseManager.getList(quoteCouse,null);
            if(quoteList!=null&&quoteList.size()>0){
                TpCourseInfo tq=quoteList.get(0);
                TpCourseClass cc = new TpCourseClass();
                //cc.setClassid(0);
                //cc.setSubjectid(Integer.parseInt(subjectid));
                //cc.setGradeid(Integer.parseInt(gradeid));
                cc.setCourseid(tq.getCourseid());
                cc.setTermid(termid);
                List<TpCourseClass>tpCourseClassList=this.tpCourseClassManager.getList(cc,null);
                if(tpCourseClassList!=null&&tpCourseClassList.size()>0){
                    je.setMsg("提示：专题"+tq.getCoursename()+"已存在于当前学期!");
                    response.getWriter().print(je.toJSON());
                    return;
                }else{
                    // 添加默认专题班级关联记录
                    cc.setClassid(0);
                    cc.setCourseid(tq.getCourseid());
                    cc.setSubjectid(Integer.parseInt(subjectid));
                    cc.setGradeid(Integer.parseInt(gradeid));
                    cc.setTermid(termid);
                    //沿用专题，开始时间获取当前时间
                    Date d = new Date();
                    cc.setBegintime(d);
                    cc.setClasstype(0);
                    cc.setUserid(tc.getCuserid());
                    sql = new StringBuilder();
                    objList = this.tpCourseClassManager.getSaveSql(cc, sql);
                    if (sql == null || objList == null) {
                        je.setMsg("专题班级数据写入出错!");
                        response.getWriter().print(je.toJSON());
                        return;
                    }
                    if(sql!=null&&sql.length()>0){
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                }
            }else{  //不存在副本
                //重置部分专题信息
                Long nextCourseId=this.tpCourseManager.getNextId(true);
                tc.setQuoteid(tc.getCourseid());
                tc.setTeachername(t.getTeachername());
                tc.setCourseid(nextCourseId);
                tc.setCuserid(user.getUserid());

                sql = new StringBuilder();
                objList = this.tpCourseManager.getSaveSql(tc, sql);

                if(sql!=null&&sql.length()>0){
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }

                //添加专题教材关联
                if(tc.getCourseid()!=null){
                    //任务
                    TpCourseTeachingMaterial ctm=new TpCourseTeachingMaterial();
                    ctm.setCourseid(courseid);//引用专题ID
                    List<TpCourseTeachingMaterial> ctmList=this.tpCourseTeachingMaterialManager.getList(ctm,null);
                    if(ctmList!=null&&ctmList.size()>0){
                        for (TpCourseTeachingMaterial tctm : ctmList){
                            tctm.setRef(null);
                            tctm.setCtime(null);
                            tctm.setCourseid(tc.getCourseid()); //新建专题
                            sql=new StringBuilder();
                            objList=this.tpCourseTeachingMaterialManager.getSaveSql(tctm,sql);
                            if(sql!=null&&objList!=null){
                                sqlListArray.add(sql.toString());
                                objListArray.add(objList);
                            }
                        }
                    }
                }

                // 添加默认专题班级关联记录
                TpCourseClass cc = new TpCourseClass();
                cc.setClassid(0);
                cc.setCourseid(tc.getCourseid());
                cc.setSubjectid(Integer.parseInt(subjectid));
                cc.setGradeid(Integer.parseInt(gradeid));
                cc.setTermid(termid);
                //沿用专题，开始时间获取当前时间
                Date d = new Date();
                cc.setBegintime(d);
                cc.setClasstype(0);
                cc.setUserid(tc.getCuserid());
                sql = new StringBuilder();
                objList = this.tpCourseClassManager.getSaveSql(cc, sql);
                if (sql == null || objList == null) {
                    je.setMsg("专题班级数据写入出错!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                sqlListArray.add(sql.toString());
                objListArray.add(objList);

                //添加专题引用元素
                if(tc.getCourseid()!=null&&tc.getCourselevel()!=null){
                    //任务
                    TpTaskInfo taskInfo=new TpTaskInfo();
                    taskInfo.setCourseid(courseid);//引用专题ID
                    List<TpTaskInfo>taskInfoList=this.tpTaskManager.getList(taskInfo,null);
                    if(taskInfoList!=null&&taskInfoList.size()>0){
                        for (TpTaskInfo tmpTask : taskInfoList){
                            tmpTask.setCourseid(nextCourseId); //新建专题
                            tmpTask.setCuserid(this.logined(request).getRef());
                            Long taskid=-1l;
                            taskid=this.tpTaskManager.getNextId(true);
                           // System.out.println("taskid:"+taskid);
                            tmpTask.setTaskid(taskid);
                            sql=new StringBuilder();
                            objList=this.tpTaskManager.getSaveSql(tmpTask,sql);
                            if(sql!=null&&objList!=null){
                                sqlListArray.add(sql.toString());
                                objListArray.add(objList);
                            }
                        }
                    }

                    //资源
                    TpCourseResource tr=new TpCourseResource();
                    tr.setCourseid(courseid);//引用专题ID
                    List<TpCourseResource>courseResourceList=this.tpCourseResourceManager.getList(tr,null);
                    if(courseResourceList!=null&&courseResourceList.size()>0){
                        for(TpCourseResource tmpCourseResource : courseResourceList){
                            tmpCourseResource.setCourseid(nextCourseId);
                            tmpCourseResource.setReferenceid(Long.parseLong("1"));
                            tmpCourseResource.setRef(this.tpCourseResourceManager.getNextId(true));
                            sql=new StringBuilder();
                            objList=this.tpCourseResourceManager.getSaveSql(tmpCourseResource,sql);
                            if(sql!=null&&objList!=null){
                                sqlListArray.add(sql.toString());
                                objListArray.add(objList);
                            }
                        }
                    }
                    //试题
                    TpCourseQuestion tq=new TpCourseQuestion();
                    tq.setCourseid(courseid);
                    List<TpCourseQuestion>courseQuestionList=this.tpCourseQuestionManager.getList(tq,null);
                    if(courseQuestionList!=null&&courseQuestionList.size()>0){
                        for (TpCourseQuestion tmpQues : courseQuestionList){
                            tmpQues.setCourseid(nextCourseId);
                            tmpQues.setRef(this.tpCourseQuestionManager.getNextId(true));
                            tmpQues.setReferenceid(Long.parseLong("1"));
                            sql=new StringBuilder();
                            objList=this.tpCourseQuestionManager.getSaveSql(tmpQues,sql);
                            if(sql!=null&&objList!=null){
                                sqlListArray.add(sql.toString());
                                objListArray.add(objList);
                            }
                        }
                    }

                    //论题
                    TpTopicInfo tt=new TpTopicInfo();
                    tt.setCourseid(courseid);
                    List<TpTopicInfo>topicInfoList=this.tpTopicManager.getList(tt,null);
                    if(topicInfoList!=null&&topicInfoList.size()>0){
                        for(TpTopicInfo tmpTopic :topicInfoList){
                            Long nextTopicid=this.tpTopicManager.getNextId(true);
                            //主题
                            TpTopicThemeInfo themeInfo=new TpTopicThemeInfo();
                            themeInfo.setCourseid(tmpTopic.getCourseid());
                            themeInfo.setTopicid(tmpTopic.getTopicid());
                            List<TpTopicThemeInfo>themeInfoList=this.tpTopicThemeManager.getList(themeInfo,null);
                            if(themeInfoList!=null&&themeInfoList.size()>0){
                                for(TpTopicThemeInfo tmpThemeInfo :themeInfoList){
                                    tmpThemeInfo.setQuoteid(tmpThemeInfo.getThemeid());//记录引用的ID
                                    tmpThemeInfo.setCuserid(this.logined(request).getUserid());
                                    tmpThemeInfo.setThemeid(this.tpTopicThemeManager.getNextId(true));
                                    tmpThemeInfo.setTopicid(nextTopicid);
                                    tmpThemeInfo.setCourseid(nextCourseId);
                                    tmpThemeInfo.setStatus(2L);//引用专题下  1：显示   2：不显示
                                    sql=new StringBuilder();
                                    objList=this.tpTopicThemeManager.getSaveSql(tmpThemeInfo,sql);
                                    if(sql!=null&&objList!=null){
                                        sqlListArray.add(sql.toString());
                                        objListArray.add(objList);
                                    }

                                    if(tmpThemeInfo.getThemecontent()!=null){
                                        //得到theme_content的更新语句
                                        this.tpTopicThemeManager.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "theme_content"
                                                , tmpThemeInfo.getThemecontent(), tmpThemeInfo.getThemeid().toString(),sqlListArray,objListArray);
                                    }
                                    if(tmpThemeInfo.getCommentcontent()!=null){
                                        //得到comment_content的更新语句
                                        this.tpTopicThemeManager.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "comment_content"
                                                , tmpThemeInfo.getCommentcontent(), tmpThemeInfo.getThemeid().toString(),sqlListArray,objListArray);

                                    }
                                }
                            }
                            //引用的TOPIC_ID
                            tmpTopic.setQuoteid(tmpTopic.getTopicid());
                            tmpTopic.setCourseid(nextCourseId);
                            tmpTopic.setCuserid(this.logined(request).getUserid());
                            tmpTopic.setTopicid(nextTopicid);
                            sql=new StringBuilder();
                            objList=this.tpTopicManager.getSaveSql(tmpTopic,sql);
                            if(sql!=null&&objList!=null){
                                sqlListArray.add(sql.toString());
                                objListArray.add(objList);
                            }
                        }
                    }
                }
            }
        }

        if (sqlListArray.size() > 0 && objListArray.size() > 0) {
            boolean bo = this.tpCourseManager.doExcetueArrayProc(
                    sqlListArray, objListArray);
            if (bo) {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
            } else {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        } else {;//有可能存在只有专题信息，无下面元素信息的情况，不应该
            //je.setMsg(UtilTool.msgproperty.getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 恢复专题
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "m=revertCourse", method = RequestMethod.POST)
    public void doRevertCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        TpCourseInfo tc = this.getParameter(request, TpCourseInfo.class);
        if (tc.getCourseid() == null) {
            je.setMsg("未获取到专题标识！！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(tc.getLocalstatus()==null){
            tc.setLocalstatus(1);
        }
        Boolean b = this.tpCourseManager.doUpdate(tc);
        if (b) {
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
        } else {
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 共享专题
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "m=shareCourse", method = RequestMethod.POST)
    public void doShareCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        TpCourseInfo tc = this.getParameter(request, TpCourseInfo.class);
        if (tc.getCourseid() == null) {
            je.setMsg("未获取到专题标识！！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(tc.getSharetype()==null){
            tc.setLocalstatus(1);
        }
        Boolean b = this.tpCourseManager.doUpdate(tc);
        if (b) {
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
            if(tc.getSharetype()!=null){
                //修改资源分享等级
                TpCourseResource courseResource=new TpCourseResource();
                courseResource.setCourseid(tc.getCourseid());
                List<TpCourseResource>tpCourseResourcesList=this.tpCourseResourceManager.getList(courseResource,null);
                if(tpCourseResourcesList!=null&&tpCourseResourcesList.size()>0){
                    for(TpCourseResource res :tpCourseResourcesList){
                        if(res!=null&&res.getResid()<1){
                            ResourceInfo r=new ResourceInfo();
                            r.setResid(res.getResid());
                            r.setSharestatus(tc.getSharetype());
                            this.resourceManager.doUpdate(r);
                        }
                    }
                }
            }
        } else {
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 修改课题
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "m=updateCourse", method = RequestMethod.POST)
    public void doUpdateCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        TpCourseInfo tc = this.getParameter(request, TpCourseInfo.class);
        if (tc.getCourseid() == null) {
            je.setMsg("专题编号错误！！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (tc.getCoursename() == null || tc.getCoursename().length() < 1) {
            je.setMsg("没有专题名称参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
//        if (tc.getMaterialidvalues() == null || tc.getMaterialidvalues().trim().length() < 1) {
//            je.setMsg("没有专题教材编号参数！");// 异常错误，参数不齐，无法正常访问!
//            je.setType("error");
//            response.getWriter().print(je.toJSON());
//            return;
//        }
        if (tc.getSharetype() == null || tc.getSharetype() < 1) {
            je.setMsg("没有获得分享类型参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        String subjectid = request.getParameter("subjectid");
        if (subjectid == null || subjectid.length() < 1) {
            je.setMsg("没有获取学科参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        String gradeid = request.getParameter("gradeid");
        if (gradeid == null || gradeid.length() < 1) {
            je.setMsg("没有获取年级参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        String termid = request.getParameter("termid");
        if (termid == null || termid.length() < 1) {
            je.setMsg("没有获取学期参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        String classstr = request.getParameter("classidstr");
        String vclassstr = request.getParameter("vclassidstr");
        if (classstr == null && vclassstr == null) {
            je.setMsg("没有获取关联班级参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (classstr.trim().length() < 1 && vclassstr.trim().length() < 1) {
            je.setMsg("没有获取关联班级参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        String classtimestr = request.getParameter("classTimeArray");
        String vclasstimestr = request.getParameter("vclassTimeArray");
        if (classtimestr == null && vclasstimestr == null) {
            je.setMsg("没有获取关联班级开课时间参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (classtimestr.trim().length() == 0 && vclasstimestr.trim().length() == 0) {
            je.setMsg("没有获取关联班级开课时间参数！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        List<String> sqlListArray = new ArrayList<String>();
        List<List<Object>> objListArray = new ArrayList<List<Object>>();
        List<Object> objList = null;
        StringBuilder sql = null;

        ////////////////////////// 添加专题，配置相关参数
        tc.setCuserid(this.logined(request).getUserid());
        TeacherInfo t = new TeacherInfo();
        t.setUserid(this.logined(request).getRef());
        List<TeacherInfo> tl = this.teacherManager.getList(t, null);
        if (tl == null || tl.size() == 0) {
            je.setMsg("教师信息获取失败，请确认身份或者联系管理员！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        tc.setTeachername(tl.get(0).getTeachername());
        sql = new StringBuilder();
        objList = this.tpCourseManager.getUpdateSql(tc, sql);
        if (sql == null || objList == null) {
            je.setMsg("专题数据写入出错！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        sqlListArray.add(sql.toString());
        objListArray.add(objList);


//        // 删除旧的专题教材关联记录
//        TpCourseTeachingMaterial tctm = new TpCourseTeachingMaterial();
//        tctm.setCourseid(tc.getCourseid());
//        sql = new StringBuilder();
//        objList = this.tpCourseTeachingMaterialManager.getDeleteSql(tctm, sql);
//        sqlListArray.add(sql.toString());
//        objListArray.add(objList);
//        // 添加专题教材关联
//        if (tc.getMaterialidvalues() != null && tc.getMaterialidvalues().split(",").length>0) {
//            String[] materialidvalues = tc.getMaterialidvalues().split(",");
//            for (int i = 0; i < materialidvalues.length; i++) {
//                tctm = new TpCourseTeachingMaterial();
//                tctm.setCourseid(tc.getCourseid());
//                tctm.setTeachingmaterialid(Integer.parseInt(materialidvalues[i]));
//                sql = new StringBuilder();
//                objList = this.tpCourseTeachingMaterialManager.getSaveSql(tctm, sql);
//                if (sql == null || objList == null) {
//                    je.setMsg("专题教材数据写入出错！");// 异常错误，参数不齐，无法正常访问!
//                    je.setType("error");
//                    response.getWriter().print(je.toJSON());
//                    return;
//                }
//                sqlListArray.add(sql.toString());
//                objListArray.add(objList);
//            }
//
//        }else{
//            je.setMsg("获取教材参数错误，请重试！");// 异常错误，参数不齐，无法正常访问!
//            je.setType("error");
//            response.getWriter().print(je.toJSON());
//            return;
//        }

        // 添加关联班级
        //删除旧的班级专题关联记录
        TpCourseClass cc = new TpCourseClass();
        cc.setCourseid(tc.getCourseid());
        cc.setTermid(termid);
        cc.setUserid(tc.getCuserid());
        sql = new StringBuilder();
        objList = this.tpCourseClassManager.getDeleteSql(cc, sql);
        sqlListArray.add(sql.toString());
        objListArray.add(objList);
        //添加新的专题班级关联记录
        if (classstr != null && classtimestr != null
                && classstr.trim().length() > 0 && classtimestr.trim().length() > 0) {
            String[] classArray = classstr.split(",");
            String[] classtimeArray = classtimestr.split(",");
            //教学班级
            for (int i = 0; i < classArray.length; i++) {
                cc = new TpCourseClass();
                cc.setClassid(Integer.parseInt(classArray[i]));
                cc.setCourseid(tc.getCourseid());
                cc.setSubjectid(Integer.parseInt(subjectid));
                cc.setGradeid(Integer.parseInt(gradeid));
                cc.setTermid(termid);
                cc.setBegintime(UtilTool.StringConvertToDate(classtimeArray[i]));
                cc.setClasstype(1);
                cc.setUserid(tc.getCuserid());
                sql = new StringBuilder();
                objList = this.tpCourseClassManager.getSaveSql(cc, sql);
                if (sql == null || objList == null) {
                    je.setMsg("专题班级数据写入出错！");// 异常错误，参数不齐，无法正常访问!
                    je.setType("error");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }
        //虚拟班级
        if (vclassstr != null && vclasstimestr != null
                && vclassstr.trim().length() > 0 && vclasstimestr.trim().length() > 0) {
            String[] vclassArray = vclassstr.split(",");
            String[] vclasstimeArray = vclasstimestr.split(",");
            for (int i = 0; i < vclassArray.length; i++) {
                cc = new TpCourseClass();
                cc.setClassid(Integer.parseInt(vclassArray[i]));
                cc.setCourseid(tc.getCourseid());
                cc.setSubjectid(Integer.parseInt(subjectid));
                cc.setGradeid(Integer.parseInt(gradeid));
                cc.setTermid(termid);
                cc.setBegintime(UtilTool.StringConvertToDate(vclasstimeArray[i]));
                cc.setClasstype(2);
                cc.setUserid(tc.getCuserid());
                sql = new StringBuilder();
                objList = this.tpCourseClassManager.getSaveSql(cc, sql);
                if (sql == null || objList == null) {
                    je.setMsg("专题班级数据写入出错！");// 异常错误，参数不齐，无法正常访问!
                    je.setType("error");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }

        //修改资源分享等级
        TpCourseResource courseResource=new TpCourseResource();
        courseResource.setCourseid(tc.getCourseid());
        List<TpCourseResource>tpCourseResourcesList=this.tpCourseResourceManager.getList(courseResource,null);
        if(tpCourseResourcesList!=null&&tpCourseResourcesList.size()>0){
            for(TpCourseResource res :tpCourseResourcesList){
                if(res!=null&&res.getResid()<1){
                    ResourceInfo r=new ResourceInfo();
                    r.setResid(res.getResid());
                    r.setSharestatus(tc.getSharetype());
                    sql=new StringBuilder();
                    objList=this.resourceManager.getUpdateSql(r,sql);
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }
            }
        }

        //关联专题
        String selectedCourseid=request.getParameter("selectcourseid");
        if(selectedCourseid.length()>0){
            //  首先删除旧的关系
            TpCourseRelatedInfo o = new TpCourseRelatedInfo();
            o.setCourseid(tc.getCourseid());
            sql = new StringBuilder();
            objList = this.tpCourseRelatedManager.getDeleteSql(o,sql);
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
            //添加新的关系
            String[] courseids = selectedCourseid.split("\\|");
            int length = courseids.length;
            for(int i = 0;i<length;i++){
                TpCourseRelatedInfo obj = new TpCourseRelatedInfo();
                obj.setCourseid(tc.getCourseid());
                obj.setRelatedcourseid(Long.parseLong(courseids[i]));
                sql = new StringBuilder();
                objList = this.tpCourseRelatedManager.getSaveSql(obj, sql);
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }

        if (sqlListArray.size() > 0 && objListArray.size() > 0) {
            boolean bo = this.tpCourseManager.doExcetueArrayProc(
                    sqlListArray, objListArray);
            if (bo) {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
            } else {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        } else {
            je.setMsg(UtilTool.msgproperty
                    .getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 去修改
     */
    @RequestMapping(params = "m=doChangeCourse", method = RequestMethod.POST)
    public void doChangeCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        TpCourseInfo tcInfo = this.getParameter(request, TpCourseInfo.class);
        if (tcInfo.getCourseid() == null) {
            je.setMsg("专题编号错误！！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }

        if (tcInfo.getCoursestatus() == null && tcInfo.getLocalstatus() == null) {
            je.setMsg("参数错误！！");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }

        if (this.tpCourseManager.doUpdate(tcInfo)) {
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
        } else {
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 获得专题列表ajax
     * 通过
     */
    @RequestMapping(params = "m=getCourseLibraryListAjax", method = RequestMethod.POST)
    public void getCourListAjax(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String searchType=request.getParameter("searchType");
        PageResult presult = this.getPageResultParameter(request);
        TpCourseInfo tcInfo = this.getParameter(request, TpCourseInfo.class);

        // 如果搜索类型为专题库搜索 searchType=1 ,则默认附加专题库搜索前置条件
        if(searchType!=null && searchType.trim().equals("1")){
            //tcInfo.setQuoteid(new Long(0));
            tcInfo.setCourselevel(-3); // -3标识不共享反义，即所有符合共享条件的专题 或者校内共享
            tcInfo.setFilterquote(1);//去除当前教师引用过的专题
            tcInfo.setCuserid(this.logined(request).getUserid());
        }
        List<TpCourseInfo> courseList = this.tpCourseManager.getList(tcInfo, presult);
        presult.setList(courseList);
        je.setPresult(presult);
        response.getWriter().print(je.toJSON());
    }

    /**
     * 获得班级专题评论列表ajax
     */
    @RequestMapping(params = "m=getClassCommentListAjax", method = RequestMethod.POST)
    public void getClassCommentListAjax(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String searchType=request.getParameter("searchType");
        PageResult presult = this.getPageResultParameter(request);
        TpCourseInfo tcInfo = this.getParameter(request, TpCourseInfo.class);

        // 如果搜索类型为专题库搜索 searchType=1 ,则默认附加专题库搜索前置条件
        if(searchType!=null && searchType.trim().equals("1")){
            tcInfo.setQuoteid(new Long(0));
            tcInfo.setCourselevel(-3); // -3标识不共享反义，即所有符合共享条件的专题 或者校内共享
        }
        List<TpCourseInfo> courseList = this.tpCourseManager.getList(tcInfo, presult);
        presult.setList(courseList);
        je.setPresult(presult);
        response.getWriter().print(je.toJSON());
    }

    /**
     * 获取关联专题
     */
    @RequestMapping(params = "m=getRelatedCourse", method = RequestMethod.POST)
    public void getRelatedCourse(HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String materialid=request.getParameter("materialid");
        String coursename=request.getParameter("course_name");
        if (materialid == null || coursename == null) {
            je.setType("error");
            je.setMsg("参数错误，无法请求数据！");
            response.getWriter().print(je.toJSON());
            return;
        }
        UserInfo user = this.logined(request);
        List<Map<String,Object>> relatedCourseList=this.tpCourseManager.getRelatedCourseList(Integer.parseInt(materialid),user.getUserid(),coursename);
        je.setObjList(relatedCourseList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 获取教师课题ajax列表
     */
    @RequestMapping(params = "m=getTchCourListAjax", method = RequestMethod.POST)
    public void getTeacherCourseListAjax(HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String gradeid = request.getParameter("gradeid");
        String subjectid = request.getParameter("subjectid");
        String termid=request.getParameter("atermid");
        if (gradeid == null || subjectid == null) {
            je.setType("error");
            je.setMsg("参数错误，无法请求数据！");
            response.getWriter().print(je.toJSON());
            return;
        }

        UserInfo user = this.logined(request);

        TpCourseInfo tcInfo = this.getParameter(request,
                TpCourseInfo.class);
        tcInfo.setUserid(user.getUserid());

        List l = new ArrayList();
        List<TpTeacherTeachMaterial> entityList=null;
        //得到 该教师的当前教材
        if(gradeid!=null&&gradeid.trim().length()>0&&subjectid!=null&&subjectid.trim().length()>0
                &&termid!=null&&termid.length()>0){
            TpTeacherTeachMaterial tentity=new TpTeacherTeachMaterial();
            tentity.setUserid(this.logined(request).getUserid());
            tentity.setGradeid(Integer.parseInt(gradeid));
            tentity.setSubjectid(Integer.parseInt(subjectid));
            tentity.setTermid(termid);
            entityList=this.tpTeacherTeachMaterialManager.getList(tentity,null);
           // if(entityList!=null&&entityList.size()>0)
                //tcInfo.setMaterialidvalues(entityList.get(0).getMaterialid().toString());
        }
        TermInfo t=new TermInfo();
        t.setRef(termid);
        List<TermInfo>termInfoList=this.termManager.getList(t,null);
        if(termInfoList==null||termInfoList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }


        PageResult presult = this.getPageResultParameter(request);
        List<TpCourseInfo> courseList = this.tpCourseManager.getTchCourseList(
                tcInfo, presult);
        ClassUser cu=new ClassUser();
        cu.setSubjectid(Integer.parseInt(subjectid));
        GradeInfo g=new GradeInfo();
        g.setGradeid(Integer.parseInt(gradeid));
        List<GradeInfo>gradeInfos=this.gradeManager.getList(g,null);
        if(gradeInfos==null||gradeInfos.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        //当前学期、学科、年级下的授课班级
        cu.setClassgrade(gradeInfos.get(0).getGradevalue());
        cu.setUserid(this.logined(request).getRef());
        cu.setRelationtype("任课老师");
        cu.setSubjectid(Integer.parseInt(subjectid));
        cu.setYear(termInfoList.get(0).getYear());
        List<ClassUser>classList=this.classUserManager.getList(cu,null);

        TpVirtualClassInfo tpVirtualClassInfo=new TpVirtualClassInfo();
        tpVirtualClassInfo.setCuserid(this.logined(request).getUserid());
        tpVirtualClassInfo.setStatus(1);
        List<TpVirtualClassInfo>virtualClassInfoList=this.tpVirtualClassManager.getList(tpVirtualClassInfo,null);

       /* Set<Map<String, Object>> classSet = new HashSet<Map<String, Object>>();
        if (courseList != null && courseList.size() > 0) {
            for (TpCourseInfo tc : courseList) {
                for (Map<String, Object> claMap : tc.getClassEntity()) {
                    claMap.remove("CLASS_TIME");
                    claMap.remove("HANDLE_FLAG");
                    if(Integer.parseInt(claMap.get("CLASS_ID").toString())!=0)
                        classSet.add(claMap);
                }
            }
        }*/


        l.add(courseList);
        l.add(classList);
        l.add(virtualClassInfoList);
        if(entityList!=null&&entityList.size()>0)
            l.add(entityList.get(0));
        presult.setList(l);
        je.setPresult(presult);
        response.getWriter().print(je.toJSON());
    }

    /**
     * 获取教师受托管课题ajax列表
     */
    @RequestMapping(params = "m=getTrusteeshipCourListAjax", method = RequestMethod.POST)
    public void getTrusteeshipCourseListAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        TpCourseInfo tcInfo = this.getParameter(request, TpCourseInfo.class);
        if (tcInfo == null
                || tcInfo.getSubjectid() == null
                || tcInfo.getGradeid() == null
                || tcInfo.getTermid() == null) {
            je.setType("error");
            je.setMsg("参数错误，无法请求数据！");
            response.getWriter().print(je.toJSON());
            return;
        }
        UserInfo user = this.logined(request);
        tcInfo.setUserid(user.getUserid());
        PageResult presult = this.getPageResultParameter(request);
        List<TpCourseInfo> courseList = this.tpCourseManager.getTsList(tcInfo, presult);
        presult.setList(courseList);
        je.setPresult(presult);
        response.getWriter().print(je.toJSON());
    }


    /**
     * 进入新建专题页面
     *
     * @return
     * @throws Exception
     */

    @RequestMapping(params = "m=toSaveOrUpdate", method = RequestMethod.GET)
    public ModelAndView toSaveOrUpdate(HttpServletRequest request, HttpServletResponse response, ModelMap mp)
            throws Exception {
        JsonEntity jeEntity = new JsonEntity();
        UserInfo user = this.logined(request);
        String gradeid = request.getParameter("gradeid");
        String subjectid = request.getParameter("subjectid");
        String materialid = request.getParameter("materialid");

        mp.put("materialid", materialid==null?"0":materialid);   // 如果没有教材默认为0
        TpCourseInfo tc = this.getParameter(request, TpCourseInfo.class);

        List<GradeInfo> gradeList = null;
        List<SubjectInfo> subList = null;
        List<SubjectUser> suList = null;
        ClassUser cu = new ClassUser();

        TermInfo ti = this.termManager.getMaxIdTerm(false);
        if (ti == null) {
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("学期参数错误,请联系教务。"));// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }

        GradeInfo grade = new GradeInfo();
        SubjectInfo sub = new SubjectInfo();
        if (tc != null && tc.getCourseid() != null) {
            List<TpCourseInfo> tcList = this.tpCourseManager.getTchCourseList(tc, null);
            TpCourseRelatedInfo tr = new TpCourseRelatedInfo();
            tr.setCourseid(tc.getCourseid());
            List<TpCourseRelatedInfo> trList = this.tpCourseRelatedManager.getList(tr,null);
            if(trList!=null&&trList.size()>0){
                mp.put("trList",trList);
            }
            if (tcList == null || tcList.size() == 0) {
                jeEntity.setMsg(UtilTool.msgproperty.getProperty("专题不存在！"));// 异常错误，参数不齐，无法正常访问!
                response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
                return null;
            }
            tc = tcList.get(0);
            //add by panfei
            //mp.put("materialid", tc.getMaterialids()==null?"0":tc.getMaterialids());

            tc.getClassEntity();

            grade.setGradeid(tc.getGradeid());
            gradeList = this.gradeManager.getList(grade, null);
            grade=gradeList.get(0);

            sub.setSubjectid(tc.getSubjectid());
            subList = this.subjectManager.getList(sub, null);
            sub=subList.get(0);

            cu.setSubjectid(tc.getSubjectid());
            cu.setClassgrade(gradeList.get(0).getGradevalue());
        } else {
            if (gradeid == null || subjectid == null) {
                jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));// 异常错误，参数不齐，无法正常访问!
                response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
                return null;
            }
            grade.setGradeid(Integer.parseInt(gradeid));
            gradeList = this.gradeManager.getList(grade, null);
            grade=gradeList.get(0);

            sub.setSubjectid(Integer.parseInt(subjectid));
            subList = this.subjectManager.getList(sub, null);
            sub=subList.get(0);

            cu.setSubjectid(Integer.parseInt(subjectid));
            cu.setClassgrade(gradeList.get(0).getGradevalue());
            tc = null;
        }

        //获取教学班级
        cu.setUserid(user.getRef());
        cu.setClassgrade(grade.getGradevalue());
        cu.setYear(ti.getYear());
        cu.setSubjectid(Integer.parseInt(subjectid));
        List<ClassUser> clsList = this.classUserManager.getList(cu, null);

        //获取虚拟班级
        TpVirtualClassInfo tvc = new TpVirtualClassInfo();
        tvc.setCuserid(this.logined(request).getUserid());
        tvc.setStatus(1);
        List<TpVirtualClassInfo> tvcList = this.tpVirtualClassManager.getList(tvc, null);

//        TrusteeShipClass tsc = new TrusteeShipClass();
//        tsc.setTrustteacherid(user.getUserid());
//        tsc.setIsaccept(1);
//        //tsc.setTrustclasstype(1);
//        List<TrusteeShipClass> tscList = this.trusteeShipClassManager.getList(tsc, null);
//        //去除被托管出去的班级
//        for (TrusteeShipClass tmp_ts : tscList) {
//            for (ClassUser tmp_cu : clsList) {
//                if (tmp_ts.getTrustclassid().toString().equals(tmp_cu.getClassid().toString())) {
//                    clsList.remove(tmp_cu);
//                    break;
//                }
//            }
//            for (TpVirtualClassInfo tmp_vcu : tvcList) {
//                if (tmp_ts.getTrustclassid().toString().equals(tmp_vcu.getVirtualclassid().toString())) {
//                    tvcList.remove(tmp_vcu);
//                    break;
//                }
//            }
//        }

//        //获取受托管班级
//        tsc = new TrusteeShipClass();
//        tsc.setReceiveteacherid(user.getUserid());
//        tsc.setYear(ti.getYear());
 //       List<Map<String, Object>> tsList = this.trusteeShipClassManager.getTsClassList(tsc);



        mp.put("cuList", clsList);
       // mp.put("tsList", tsList);
        mp.put("tvcList", tvcList);
        mp.put("grade", grade);
        mp.put("subject", sub);
        mp.put("term", ti);
        mp.put("tc", tc);
        return new ModelAndView("/teachpaltform/course/courseSaveOrUpdate", mp);
    }
    /**
     *
     *
     *
     *
     *
     * */
    @RequestMapping(params = "m=getMaterialByWenli", method = RequestMethod.POST)
    public void getMaterialByWenli(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String subjectid = request.getParameter("subjectid");
        String gradeid = request.getParameter("gradeid");
        String wenliType=request.getParameter("wenli");
        TeachingMaterialInfo tm = new TeachingMaterialInfo();
        tm.setGradeid(Integer.parseInt(gradeid));
        tm.setSubjectid(Integer.parseInt(subjectid));
        tm.setType(wenliType);
        List<TeachingMaterialInfo> tmList = this.teachingMaterialManager.getList(tm, null);
        je.setType("success");
        je.setObjList(tmList);
        response.getWriter().print(je.toJSON());
    }

     /**
     * 进入新建专题页面
     *
     * @return
     * @throws Exception
     */

    @RequestMapping(params = "m=toCourseLibrary", method = RequestMethod.GET)
    public ModelAndView toCourseLibrary(HttpServletRequest request, HttpServletResponse response, ModelMap mp)
            throws Exception {
        JsonEntity jeEntity = new JsonEntity();
        UserInfo user = this.logined(request);
        String subjectid = request.getParameter("subjectid");
        String gradeid = request.getParameter("gradeid");
        String termid = request.getParameter("termid");

        SubjectInfo subject = new SubjectInfo();
        subject.setSubjectid(Integer.parseInt(subjectid));
        subject = this.subjectManager.getList(subject, null).get(0);

        GradeInfo grade = new GradeInfo();
        grade.setGradeid(Integer.parseInt(gradeid));
        grade = this.gradeManager.getList(grade, null).get(0);

        TeachingMaterialInfo tm = new TeachingMaterialInfo();
        tm.setGradeid(grade.getGradeid());
        tm.setSubjectid(subject.getSubjectid());
        List<TeachingMaterialInfo> tmList = this.teachingMaterialManager.getList(tm, null);

        TermInfo ti = this.termManager.getMaxIdTerm(false);
        List<SubjectUser> suList = null;
        List<GradeInfo> gradeList = null;
        SubjectUser su = new SubjectUser();
        su.setUserid(user.getRef());
        suList = this.subjectUserManager.getList(su, null);
        gradeList = this.gradeManager.getTchGradeList(user.getUserid(), ti.getYear());

        mp.put("subject", subject);
        mp.put("grade", grade);
        mp.put("termid", termid);
        mp.put("tmList", tmList);
        mp.put("gradeList", gradeList);
        mp.put("suList", suList);
        mp.put("userid", user.getUserid());
        List<SchoolInfo> schoolList = this.schoolManager.getList(null,null);
        mp.put("schoolList",schoolList);
        String schoolname=UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_NAME");
        mp.put("schoolname",schoolname);
        return new ModelAndView("/teachpaltform/course/addSelectedCourse", mp);
    }

    @RequestMapping(params = "m=getClassByOptions", method = RequestMethod.POST)
    public void getClassByOptions(HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        UserInfo user = this.logined(request);
        String gradeid = request.getParameter("gradeid");
        String subjectid = request.getParameter("subjectid");

        if (gradeid == null || gradeid.trim().length() == 0
                || subjectid == null || subjectid.trim().length() == 0) {
            je.setType("error");
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
        }

        ClassUser cu = new ClassUser();
        cu.setUserid(user.getRef());
        cu.setSubjectid(Integer.parseInt(subjectid));

        GradeInfo grade = new GradeInfo();
        grade.setGradeid(Integer.parseInt(gradeid));
        cu.setClassgrade(this.gradeManager.getList(grade, null).get(0).getGradevalue());
        List<ClassUser> clsList = this.classUserManager.getList(cu, null);

        je.setObjList(clsList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * 专题详情--任务
     * 根据课题ID，加载任务列表
     * @return
     */
    @RequestMapping(params="toTaskList",method=RequestMethod.GET)
    public ModelAndView toTaskList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        //得到该课题的所有任务，任务完成情况。
        JsonEntity je= new JsonEntity();
        String courseid=request.getParameter("courseid");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        TpCourseInfo tc=new TpCourseInfo();
        tc.setCourseid(Long.parseLong(courseid));
        tc.setLocalstatus(1);//正常
        List<TpCourseInfo>teacherCourseList=this.tpCourseManager.getList(tc, null);
        if(teacherCourseList==null||teacherCourseList.size()<1){
            je.setMsg("找不到指定课题!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        request.setAttribute("courseid", courseid);
        return new ModelAndView("/teachpaltform/preview/course-task-list");
    }

    /**
     * 根据课题ID，加载试卷列表
     * @return
     */
    @RequestMapping(params="toPaperList",method=RequestMethod.GET)
    public ModelAndView toPaperList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        //得到该课题的所有任务，任务完成情况。
        JsonEntity je= new JsonEntity();
        String courseid=request.getParameter("courseid");

        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        TpCourseInfo tc=new TpCourseInfo();
        tc.setCourseid(Long.parseLong(courseid));
        tc.setLocalstatus(1);//正常
        List<TpCourseInfo>teacherCourseList=this.tpCourseManager.getList(tc, null);
        if(teacherCourseList==null||teacherCourseList.size()<1){
            je.setMsg("找不到指定课题!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        request.setAttribute("courseid",courseid);
        return new ModelAndView("/teachpaltform/preview/course-paper-index");
    }

    /**
     * 专题详情--资源
     * 进入教师资源首页
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toTeacherIdx",method=RequestMethod.GET)
    public ModelAndView toTeacherIdx(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String termid=request.getParameter("termid");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        TpCourseInfo tc=new TpCourseInfo();
        tc.setCourseid(Long.parseLong(courseid));
        List<TpCourseInfo>teacherCourseList=this.tpCourseManager.getList(tc, null);
        if(teacherCourseList==null||teacherCourseList.size()<1){
            je.setMsg("找不到指定课题!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        TpCourseResource tr=new TpCourseResource();
        tr.setCourseid(Long.parseLong(courseid));
        tr.setLocalstatus(1);
        List<TpCourseResource> trList=this.tpCourseResourceManager.getList(tr,null);
        mp.put("courseid", courseid);
        mp.put("termid", termid);
        mp.put("resList", trList);
        return new ModelAndView("/teachpaltform/preview/course-res-index",mp);
    }

    /**
     * 专题详情--试题
     * 根据课题ID，加载试题列表
     *
     * @return
     */
    @RequestMapping(params = "m=toQuestionList", method = RequestMethod.GET)
    public ModelAndView toQuestionList(HttpServletRequest request, HttpServletResponse response,ModelMap mp) throws Exception {
        //得到该课题的试题。
        JsonEntity je = new JsonEntity();
        String courseid = request.getParameter("courseid");
        if (courseid == null || courseid.trim().length() < 1) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //验证courseid
        TpCourseInfo tpCourseInfo = new TpCourseInfo();
        tpCourseInfo.setCourseid(Long.parseLong(courseid));
        tpCourseInfo.setLocalstatus(1);
        PageResult presult = new PageResult();
        presult.setPageSize(1);
        List<TpCourseInfo> courseList = this.tpCourseManager.getList(tpCourseInfo, presult);
        if (courseList == null || courseList.size() < 1) {
            je.setMsg(UtilTool.msgproperty.get("ENTITY_NOT_EXISTS").toString());
            response.getWriter().print(je.toJSON());
            return null;
        }
        List<DictionaryInfo> quesTypeList = this.dictionaryManager.getDictionaryByType("TP_QUESTION_TYPE");
        mp.put("quesTypeList", quesTypeList);
        mp.put("courseid", courseList.get(0).getCourseid());
        return new ModelAndView("/teachpaltform/preview/course-ques-list",mp);
    }

    /**
     * 根据角色进入教学平台
     * @param request
     * @param response
     */
    @RequestMapping(params="m=toCourseByRole",method=RequestMethod.GET)
    public void toCourseByRole(HttpServletRequest request,HttpServletResponse response) throws Exception{
        if(this.validateRole(request,UtilTool._ROLE_STU_ID)){ //进入学生首页
            response.sendRedirect("teachercourse?m=toStudentCourseList");
        }else
            response.sendRedirect("teachercourse?m=toTeacherCourseList"); //进入教师首页
    }
}
