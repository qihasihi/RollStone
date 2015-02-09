package com.school.control.teachpaltform;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.entity.*;
import com.school.entity.resource.ResourceInfo;
import com.school.entity.teachpaltform.*;
import com.school.entity.teachpaltform.interactive.TpThemeReplyInfo;
import com.school.entity.teachpaltform.interactive.TpTopicInfo;
import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;
import com.school.entity.teachpaltform.paper.TpCoursePaper;
import com.school.manager.inter.*;
import com.school.manager.inter.resource.IResourceManager;
import com.school.manager.inter.teachpaltform.*;
import com.school.manager.inter.teachpaltform.interactive.ITpThemeReplyManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicThemeManager;
import com.school.manager.inter.teachpaltform.paper.ITpCoursePaperManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "teachercourse")
public class TpCourseController extends TaskController{
    @Autowired
    private IClassManager classManager;
    @Autowired
    private ITpCoursePaperManager tpCoursePaperManager;
    @Autowired
    private ITpTaskManager tpTaskManager;
    @Autowired
    private ITpCourseManager tpCourseManager;
    @Autowired
    private IDictionaryManager dictionaryManager;
    @Autowired
    private ITpTopicThemeManager tpTopicThemeManager;
    @Autowired
    private ITaskPerformanceManager taskPerformanceManager;
    @Autowired
    private ITpCourseClassManager tpCourseClassManager;
    @Autowired
    private ITpGroupManager tpGroupManager;
    @Autowired
    private ITpTopicManager tpTopicManager;
    @Autowired
   private ITpCourseQuestionManager tpCourseQuestionManager;
    @Autowired
    private ITpCourseResourceManager tpCourseResourceManager;
    @Autowired
    private ITpVirtualClassManager tpVirtualClassManager;
    @Autowired
    private ITermManager termManager;
    @Autowired
    private ITeachingMaterialManager teachingMaterialManager;
    @Autowired
    private ITrusteeShipClassManager trusteeShipClassManager;
    @Autowired
    private ITpCourseTeachingMaterialManager tpCourseTeachingMaterialManager;
    @Autowired
    private IGradeManager gradeManager;
    @Autowired
    private ISubjectUserManager subjectUserManager;
    @Autowired
    private ISubjectManager subjectManager;
    @Autowired
    private IClassUserManager classUserManager;
    @Autowired
    private ITpVirtualClassStudentManager tpVirtualClassStudentManager;
    @Autowired
    private ITpGroupStudentManager tpGroupStudentManager;
    @Autowired
    private ITeacherManager teacherManager;
    @Autowired
    private ICommentManager commentManager;
    @Autowired
    private ITpTeacherTeachMaterialManager tpTeacherTeachMaterialManager;
    @Autowired
    private ISchoolManager schoolManager;
    @Autowired
    private IResourceManager resourceManager;
    @Autowired
    private ITpCourseRelatedManager tpCourseRelatedManager;
    @Autowired
    private ITpThemeReplyManager tpThemeReplyManager;

    /**
     * 进入教师课题页
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */

    @RequestMapping(params="toTeacherCourseList",method=RequestMethod.GET)
    public ModelAndView toTeacherCourseList(HttpServletRequest request, HttpServletResponse response,ModelMap mp) throws Exception {
        JsonEntity je=new JsonEntity();
        String materialid=request.getParameter("materialid");
        String termid=request.getParameter("termid");
        String subjectid=request.getParameter("subjectid");
        String gradeid=request.getParameter("gradeid");

        if(materialid!=null&&materialid.length()>0){
            TeachingMaterialInfo tm = new TeachingMaterialInfo();
            tm.setMaterialid(Integer.parseInt(materialid));
            List<TeachingMaterialInfo> tmList = this.teachingMaterialManager.getList(tm, null);
            if(tmList!=null&&tmList.size()>0)
                mp.put("materialinfo",tmList.get(0));
        }
        TermInfo termInfo=null;
        if(termid!=null){
            TermInfo t=new TermInfo();
            t.setRef(termid);
            List<TermInfo>termList=this.termManager.getList(t,null);
            if(termList==null||termList.size()<1){
                je.setMsg(UtilTool.msgproperty.getProperty("系统未获取到学期信息!"));
                response.getWriter().print(je.getAlertMsgAndBack());
                return null;
            }
            termInfo=termList.get(0);
        }else
            termInfo=this.termManager.getMaxIdTerm(false);

        if(termInfo==null){
            je.setMsg(UtilTool.msgproperty.getProperty("系统未获取到学期信息!"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }

        PageResult pr = new PageResult();
        pr.setOrderBy("SEMESTER_BEGIN_DATE");
        List<TermInfo> termList = this.termManager.getList(null, pr);
        mp.put("termList", termList);
        mp.put("currtTerm", this.termManager.getMaxIdTerm(false));
        mp.put("selTerm",termInfo);



        //获取首页年级学科
        List<GradeInfo> gradeList = this.gradeManager.getTchGradeList(this.logined(request).getUserid(), termInfo.getYear());
        List<Map<String,Object>> gradeSubjectList = new ArrayList<Map<String, Object>>();

        if(gradeList!=null&&gradeList.size()>0){
            //当前学期、学科、年级下的授课班级
            for(int j=0;j<gradeList.size();j++){
                ClassUser cu = new ClassUser();
                cu.setClassgrade(gradeList.get(j).getGradevalue());
                cu.setUserid(this.logined(request).getRef());
                cu.setRelationtype("任课老师");
                //cu.setSubjectid(subuserList.get(i).getSubjectid());
                cu.setYear(termInfo.getYear());
                List<ClassUser>classList=this.classUserManager.getList(cu,null);
                List<SubjectInfo>subjectInfoList=new ArrayList<SubjectInfo>();
                if(classList!=null&&classList.size()>0){
                    for(ClassUser classUser :classList){
                        SubjectInfo s=new SubjectInfo();
                        s.setSubjectid(classUser.getSubjectid());
                        s.setSubjectname(classUser.getSubjectname());
                        if(!subjectInfoList.contains(s))
                            subjectInfoList.add(s);
                    }
                    if(subjectInfoList.size()>0){
                        for(SubjectInfo subjectInfo:subjectInfoList){
                            Map<String,Object> map = new HashMap<String, Object>();
                            map.put("gradeid",gradeList.get(j).getGradeid());
                            map.put("gradevalue",gradeList.get(j).getGradevalue());
                            map.put("subjectid",subjectInfo.getSubjectid());
                            map.put("subjectname",subjectInfo.getSubjectname());
                            if(!gradeSubjectList.contains(map))
                                gradeSubjectList.add(map);
                        }
                    }
                }
            }
        }
        mp.put("gradeSubjectList",gradeSubjectList);

     /*   if(gradeSubjectList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("系统未获取到授课信息!"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        } */
        String gradeValue=null;
        if(gradeSubjectList.size()>0){
            Map<String,Object>objectMap=gradeSubjectList.get(0);
            if(subjectid!=null&&subjectid.length()>0&&gradeid!=null&&gradeid.length()>0){
                SubjectInfo s=new SubjectInfo();
                s.setSubjectid(Integer.parseInt(subjectid));
                List<SubjectInfo>subList=this.subjectManager.getList(s,null);

                GradeInfo gradeInfo=new GradeInfo();
                gradeInfo.setGradeid(Integer.parseInt(gradeid));
                List<GradeInfo>gList=this.gradeManager.getList(gradeInfo,null);

                if(subList!=null&&gList!=null){
                    Map<String,Object>subGradeMap=new HashMap<String, Object>();
                    subGradeMap.put("subjectid",subList.get(0).getSubjectid());
                    subGradeMap.put("gradeid",gList.get(0).getGradeid());
                    subGradeMap.put("subjectname",subList.get(0).getSubjectname());
                    subGradeMap.put("gradevalue",gList.get(0).getGradevalue());
                    objectMap=subGradeMap;
                    gradeValue=gList.get(0).getGradevalue();
                }
            }
            mp.put("subGradeInfo",objectMap);
        }
        Integer userType=this.classUserManager.isTeachingBanZhuRen(this.logined(request).getRef(),null,termid,gradeValue);
        mp.put("userType",userType);
        mp.put("userid", this.logined(request).getUserid());

        String baseUrl=request.getSession().getAttribute("IP_PROC_NAME")==null?"":request.getSession().getAttribute("IP_PROC_NAME").toString();
        //没有授课信息的班主任,直接跳到日历页面
        ClassUser c = new ClassUser();
        c.setUserid(this.logined(request).getRef());
        c.setRelationtype("任课老师");
        c.setYear(termInfo.getYear());
        List<ClassUser>clsList=this.classUserManager.getList(c,null);
        if(this.validateRole(request,UtilTool._ROLE_CLASSADVISE_ID)&&(clsList==null||clsList.size()<1))
            response.sendRedirect(baseUrl+"teachercourse?toTeacherCalendarPage&termid="+termInfo.getRef());

        return new ModelAndView("/teachpaltform/course/teacherCourseList", mp);
    }


    /**
     * 资源系统快速添加任务页
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toQryTeaCourseList",method=RequestMethod.GET)
    public ModelAndView toQryTeaCourseList(HttpServletRequest request, HttpServletResponse response,ModelMap mp) throws Exception {
        JsonEntity je=new JsonEntity();
        //获取当前学期
        TermInfo termInfo=this.termManager.getMaxIdTerm();
        if(termInfo==null){
            je.setMsg(UtilTool.msgproperty.getProperty("系统未获取到学期信息!"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        PageResult pageResult=new PageResult();
        pageResult.setOrderBy("grade_id desc");
        pageResult.setPageNo(0);
        pageResult.setPageSize(0);
        TpCourseInfo tc=new TpCourseInfo();
        tc.setTermid(termInfo.getRef());
        tc.setUserid(this.logined(request).getUserid());
        List<TpCourseInfo>subGradeList=this.tpCourseManager.getCourseSubGradeList(tc,pageResult);

        //获取首页年级学科
        List<GradeInfo> gradeList = this.gradeManager.getTchGradeList(this.logined(request).getUserid(), termInfo.getYear());
        List<Map<String,Object>> gradeSubjectList = new ArrayList<Map<String, Object>>();

        if(gradeList!=null&&gradeList.size()>0){
            //当前学期、学科、年级下的授课班级
            for(int j=0;j<gradeList.size();j++){
                ClassUser cu = new ClassUser();
                cu.setClassgrade(gradeList.get(j).getGradevalue());
                cu.setUserid(this.logined(request).getRef());
                cu.setRelationtype("任课老师");
                //cu.setSubjectid(subuserList.get(i).getSubjectid());
                cu.setYear(termInfo.getYear());
                List<ClassUser>classList=this.classUserManager.getList(cu,null);
                List<SubjectInfo>subjectInfoList=new ArrayList<SubjectInfo>();
                if(classList!=null&&classList.size()>0){
                    for(ClassUser classUser :classList){
                        SubjectInfo s=new SubjectInfo();
                        s.setSubjectid(classUser.getSubjectid());
                        s.setSubjectname(classUser.getSubjectname());
                        if(!subjectInfoList.contains(s))
                            subjectInfoList.add(s);
                    }
                    if(subjectInfoList.size()>0){
                        for(SubjectInfo subjectInfo:subjectInfoList){
                            Map<String,Object> map = new HashMap<String, Object>();
                            map.put("gradeid",gradeList.get(j).getGradeid());
                            map.put("gradevalue",gradeList.get(j).getGradevalue());
                            map.put("subjectid",subjectInfo.getSubjectid());
                            map.put("subjectname",subjectInfo.getSubjectname());
                            if(!gradeSubjectList.contains(map))
                                gradeSubjectList.add(map);
                        }
                    }
                }
            }
        }
        mp.put("gradeSubList",gradeSubjectList);


        mp.put("currtTerm", termInfo);
        mp.put("subGradeList",subGradeList);
        mp.put("userid", this.logined(request).getUserid());
        return new ModelAndView("/teachpaltform/task/teacher/dialog/select-course", mp);
    }



    /**
     * 教务查看专题统计
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toJWPerformPage",method=RequestMethod.GET)
    public ModelAndView toJWPerformPage(HttpServletRequest request, HttpServletResponse response,ModelMap mp) throws Exception {
        JsonEntity je=new JsonEntity();
        String termid=request.getParameter("termid");
        String subjectid=request.getParameter("subjectid");
        String gradeid=request.getParameter("gradeid");


        TermInfo termInfo=null;
        if(termid!=null){
            TermInfo t=new TermInfo();
            t.setRef(termid);
            List<TermInfo>termList=this.termManager.getList(t,null);
            if(termList==null||termList.size()<1){
                je.setMsg(UtilTool.msgproperty.getProperty("系统未获取到学期信息!"));
                response.getWriter().print(je.getAlertMsgAndBack());
                return null;
            }
            termInfo=termList.get(0);
        }else
            termInfo=this.termManager.getMaxIdTerm(false);

        if(termInfo==null){
            je.setMsg(UtilTool.msgproperty.getProperty("系统未获取到学期信息!"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }

        PageResult pr = new PageResult();
        pr.setOrderBy("SEMESTER_BEGIN_DATE");
        List<TermInfo> termList = this.termManager.getList(null, pr);
        mp.put("termList", termList);
        mp.put("currtTerm", this.termManager.getMaxIdTerm(false));
        mp.put("selTerm",termInfo);

        //获取首页年级学科
        List<GradeInfo> gradeList = this.gradeManager.getTchGradeList(this.logined(request).getUserid(), termInfo.getYear());
        List<Map<String,Object>> gradeSubjectList = new ArrayList<Map<String, Object>>();

        if(gradeList!=null&&gradeList.size()>0){
            //当前学期、学科、年级下的授课班级
            for(int j=0;j<gradeList.size();j++){
                ClassUser cu = new ClassUser();
                cu.setClassgrade(gradeList.get(j).getGradevalue());
                cu.setUserid(this.logined(request).getRef());
                cu.setRelationtype("任课老师");
                //cu.setSubjectid(subuserList.get(i).getSubjectid());
                cu.setYear(termInfo.getYear());
                List<ClassUser>classList=this.classUserManager.getList(cu,null);
                List<SubjectInfo>subjectInfoList=new ArrayList<SubjectInfo>();
                if(classList!=null&&classList.size()>0){
                    for(ClassUser classUser :classList){
                        SubjectInfo s=new SubjectInfo();
                        s.setSubjectid(classUser.getSubjectid());
                        s.setSubjectname(classUser.getSubjectname());
                        if(!subjectInfoList.contains(s))
                            subjectInfoList.add(s);
                    }
                    if(subjectInfoList.size()>0){
                        for(SubjectInfo subjectInfo:subjectInfoList){
                            Map<String,Object> map = new HashMap<String, Object>();
                            map.put("gradeid",gradeList.get(j).getGradeid());
                            map.put("gradevalue",gradeList.get(j).getGradevalue());
                            map.put("subjectid",subjectInfo.getSubjectid());
                            map.put("subjectname",subjectInfo.getSubjectname());
                            if(!gradeSubjectList.contains(map))
                                gradeSubjectList.add(map);
                        }
                    }
                }
            }
        }
        mp.put("gradeSubjectList",gradeSubjectList);

/*        if(gradeSubjectList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("系统未获取到授课信息!"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        } */

        if(gradeSubjectList.size()>0){
            Map<String,Object>objectMap=gradeSubjectList.get(0);
            if(subjectid!=null&&subjectid.trim().length()>0&&gradeid!=null&&gradeid.trim().length()>0){
                SubjectInfo s=new SubjectInfo();
                s.setSubjectid(Integer.parseInt(subjectid));
                List<SubjectInfo>subList=this.subjectManager.getList(s,null);

                GradeInfo gradeInfo=new GradeInfo();
                gradeInfo.setGradeid(Integer.parseInt(gradeid));
                List<GradeInfo>gList=this.gradeManager.getList(gradeInfo,null);

                if(subList!=null&&gList!=null){
                    Map<String,Object>subGradeMap=new HashMap<String, Object>();
                    subGradeMap.put("subjectid",subList.get(0).getSubjectid());
                    subGradeMap.put("gradeid",gList.get(0).getGradeid());
                    subGradeMap.put("subjectname",subList.get(0).getSubjectname());
                    subGradeMap.put("gradevalue",gList.get(0).getGradevalue());
                    objectMap=subGradeMap;
                }
            }

            GradeInfo g=new GradeInfo();
            g.setGradeid(Integer.parseInt(objectMap.get("gradeid").toString()));
            List<GradeInfo>gradeInfoList=this.gradeManager.getList(g,null);
            if(gradeInfoList==null||gradeInfoList.size()<1){
                je.setMsg(UtilTool.msgproperty.getProperty("系统未获取到年级信息!"));
                response.getWriter().print(je.getAlertMsgAndBack());
                return null;
            }

            ClassUser c = new ClassUser();
            List<ClassUser>clsList=null;
            //当前学期、学科、年级下的授课班级
            c.setClassgrade(gradeInfoList.get(0).getGradevalue());
            c.setUserid(this.logined(request).getRef());
            c.setRelationtype("任课老师");
            c.setSubjectid(Integer.parseInt(objectMap.get("subjectid").toString()));
            c.setYear(termInfo.getYear());
            clsList=this.classUserManager.getList(c,null);
            if(clsList!=null&&clsList.size()>0)
                mp.put("isLession",1);
            c.setRelationtype("班主任");
            c.setSubjectid(null);
            clsList=this.classUserManager.getList(c,null);
            if(clsList!=null&&clsList.size()>0)
                mp.put("isBanzhuren",1);
            mp.put("subGradeInfo",objectMap);
        }

        List<GradeInfo> gList = this.gradeManager.getAdminPerformanceTeaGrade(this.logined(request).getDcschoolid());
        mp.put("gradeList",gList);

        return new ModelAndView("/teachpaltform/course/jw-performance", mp);
    }



    /**
     * 教师日历页面
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toTeacherCalendarPage",method=RequestMethod.GET)
    public ModelAndView toTeacherCalendarPage(HttpServletRequest request, HttpServletResponse response,ModelMap mp) throws Exception {
        JsonEntity je=new JsonEntity();
        String termid=request.getParameter("termid");
        String subjectid=request.getParameter("subjectid");
        String gradeid=request.getParameter("gradeid");


        TermInfo termInfo=null;
        if(termid!=null){
            TermInfo t=new TermInfo();
            t.setRef(termid);
            List<TermInfo>termList=this.termManager.getList(t,null);
            if(termList==null||termList.size()<1){
                je.setMsg(UtilTool.msgproperty.getProperty("系统未获取到学期信息!"));
                response.getWriter().print(je.getAlertMsgAndBack());
                return null;
            }
            termInfo=termList.get(0);
        }else
            termInfo=this.termManager.getMaxIdTerm(false);

        if(termInfo==null){
            je.setMsg(UtilTool.msgproperty.getProperty("系统未获取到学期信息!"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }

        PageResult pr = new PageResult();
        pr.setOrderBy("SEMESTER_BEGIN_DATE");
        List<TermInfo> termList = this.termManager.getList(null, pr);
        mp.put("termList", termList);
        mp.put("currtTerm", this.termManager.getMaxIdTerm(false));
        mp.put("selTerm",termInfo);

        //获取首页年级学科
        List<GradeInfo> gradeList = this.gradeManager.getTchGradeList(this.logined(request).getUserid(), termInfo.getYear());
        List<Map<String,Object>> gradeSubjectList = new ArrayList<Map<String, Object>>();

        if(gradeList!=null&&gradeList.size()>0){
            //当前学期、学科、年级下的授课班级
            for(int j=0;j<gradeList.size();j++){
                ClassUser cu = new ClassUser();
                cu.setClassgrade(gradeList.get(j).getGradevalue());
                cu.setUserid(this.logined(request).getRef());
                cu.setRelationtype("任课老师");
                //cu.setSubjectid(subuserList.get(i).getSubjectid());
                cu.setYear(termInfo.getYear());
                List<ClassUser>classList=this.classUserManager.getList(cu,null);
                List<SubjectInfo>subjectInfoList=new ArrayList<SubjectInfo>();
                if(classList!=null&&classList.size()>0){
                    for(ClassUser classUser :classList){
                        SubjectInfo s=new SubjectInfo();
                        s.setSubjectid(classUser.getSubjectid());
                        s.setSubjectname(classUser.getSubjectname());
                        if(!subjectInfoList.contains(s))
                            subjectInfoList.add(s);
                    }
                    if(subjectInfoList.size()>0){
                        for(SubjectInfo subjectInfo:subjectInfoList){
                            Map<String,Object> map = new HashMap<String, Object>();
                            map.put("gradeid",gradeList.get(j).getGradeid());
                            map.put("gradevalue",gradeList.get(j).getGradevalue());
                            map.put("subjectid",subjectInfo.getSubjectid());
                            map.put("subjectname",subjectInfo.getSubjectname());
                            if(!gradeSubjectList.contains(map))
                                gradeSubjectList.add(map);
                        }
                    }
                }
            }
        }
        mp.put("gradeSubjectList",gradeSubjectList);

/*        if(gradeSubjectList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("系统未获取到授课信息!"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        } */

        if(gradeSubjectList.size()>0){
            Map<String,Object>objectMap=gradeSubjectList.get(0);
            if(subjectid!=null&&subjectid.trim().length()>0&&gradeid!=null&&gradeid.trim().length()>0){
                SubjectInfo s=new SubjectInfo();
                s.setSubjectid(Integer.parseInt(subjectid));
                List<SubjectInfo>subList=this.subjectManager.getList(s,null);

                GradeInfo gradeInfo=new GradeInfo();
                gradeInfo.setGradeid(Integer.parseInt(gradeid));
                List<GradeInfo>gList=this.gradeManager.getList(gradeInfo,null);

                if(subList!=null&&gList!=null){
                    Map<String,Object>subGradeMap=new HashMap<String, Object>();
                    subGradeMap.put("subjectid",subList.get(0).getSubjectid());
                    subGradeMap.put("gradeid",gList.get(0).getGradeid());
                    subGradeMap.put("subjectname",subList.get(0).getSubjectname());
                    subGradeMap.put("gradevalue",gList.get(0).getGradevalue());
                    objectMap=subGradeMap;
                }
            }

            GradeInfo g=new GradeInfo();
            g.setGradeid(Integer.parseInt(objectMap.get("gradeid").toString()));
            List<GradeInfo>gradeInfoList=this.gradeManager.getList(g,null);
            if(gradeInfoList==null||gradeInfoList.size()<1){
                je.setMsg(UtilTool.msgproperty.getProperty("系统未获取到年级信息!"));
                response.getWriter().print(je.getAlertMsgAndBack());
                return null;
            }

            ClassUser c = new ClassUser();
            List<ClassUser>clsList=null;
            //当前学期、学科、年级下的授课班级
            c.setClassgrade(gradeInfoList.get(0).getGradevalue());
            c.setUserid(this.logined(request).getRef());
            c.setRelationtype("任课老师");
            c.setSubjectid(Integer.parseInt(objectMap.get("subjectid").toString()));
            c.setYear(termInfo.getYear());
            clsList=this.classUserManager.getList(c,null);
            if(clsList!=null&&clsList.size()>0)
                mp.put("isLession",1);
            c.setRelationtype("班主任");
            c.setSubjectid(null);
            clsList=this.classUserManager.getList(c,null);
            if(clsList!=null&&clsList.size()>0)
                mp.put("isBanzhuren",1);
            mp.put("subGradeInfo",objectMap);
        }

        return new ModelAndView("/teachpaltform/course/teacherCalendar", mp);
    }




    /**
     * 标记课程日历
     * @param request
     * @param response
     * @param mp
     * @throws Exception
     */
    @RequestMapping(params="markCourseCalendar",method=RequestMethod.POST)
    public void markCourseCalendar(HttpServletRequest request, HttpServletResponse response,ModelMap mp) throws Exception {
        JsonEntity je=new JsonEntity();
        String year=request.getParameter("year");
        String month=request.getParameter("month");
        String subjectid=request.getParameter("subjectid");
        String gradeid=request.getParameter("gradeid");
        String clsid=request.getParameter("classid");
        String termid=request.getParameter("termid");

        if(year==null||month==null||year.trim().length()<1||month.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String gradeValue=null;
        if(gradeid!=null&&gradeid.trim().length()>0&&!gradeid.equals("0")){
            GradeInfo gradeInfo=new GradeInfo();
            gradeInfo.setGradeid(Integer.parseInt(gradeid));
            List<GradeInfo>gradeInfoList=this.gradeManager.getList(gradeInfo,null);
            if(gradeInfoList!=null&&gradeInfoList.size()>0)
                gradeValue=gradeInfoList.get(0).getGradevalue();
        }
        ClassUser classUser=new ClassUser();
        classUser.setUserid(this.logined(request).getRef());
        if(gradeValue!=null)
            classUser.setClassgrade(gradeValue);


        //当前学期当前年级身份  教师1 班主任2 授课班主任3
        Integer userType=this.classUserManager.isTeachingBanZhuRen(this.logined(request).getRef(),null,termid,gradeValue);

        Integer usertype=null;
        if(this.validateRole(request,UtilTool._ROLE_CLASSADVISE_ID)&&userType>1)
           usertype=3;
        else if(this.validateRole(request,UtilTool._ROLE_TEACHER_ID))
            usertype=2;
        else if(this.validateRole(request,UtilTool._ROLE_STU_ID))
            usertype=1;

        if(usertype==null){
            je.setMsg("异常错误!用户类型错误!");
            response.getWriter().print(je.toJSON());
            return;
        }

        List<Map<String,Object>>courseCalendarList=this.tpCourseManager.getCourseCalendar(usertype, this.logined(request).getUserid(),
                this.logined(request).getDcschoolid(), year, month, gradeid, subjectid, (clsid == null ? null : Integer.parseInt(clsid)),termid);

        je.setType("success");
        je.setObjList(courseCalendarList);
        response.getWriter().print(je.toJSON());
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
        String subjectid = request.getParameter("subjectid");
        String termid = request.getParameter("termid");
        String gradeid = request.getParameter("gradeid");

        TpCourseInfo tcInfo = (TpCourseInfo) this.getParameter(request,
                TpCourseInfo.class);
        tcInfo.setUserid(user.getUserid());
        tcInfo.setLocalstatus(2);
        tcInfo.setTermid(termid);
        tcInfo.setSubjectid(Integer.parseInt(subjectid));
        tcInfo.setGradeid(Integer.parseInt(gradeid));
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
//            Object gradeid=request.getSession().getAttribute("session_grade");
//            if(gradeid!=null&&gradeid.toString().length()>0)
//                tct.setGradeid(Integer.parseInt(gradeid.toString()));
//            Object materialid=request.getSession().getAttribute("session_material");
//            if(materialid!=null&&materialid.toString().length()>0)
//                tct.setTeachingmaterialid(Integer.parseInt(materialid.toString()));
            List<TpCourseTeachingMaterial>tctList=tpCourseTeachingMaterialManager.getList(tct,null);
            if(tctList!=null&&tctList.size()>0){
                mp.put("subjectid", tctList.get(0).getSubjectid());
                mp.put("materialInfo", tctList.get(0));
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
        TpCourseInfo obj = (TpCourseInfo) this.getParameter(request, TpCourseInfo.class);
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
        /*
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
        */

        if(gradeList!=null&&gradeList.size()>0){
            //当前学期、学科、年级下的授课班级
            for(int j=0;j<gradeList.size();j++){
                ClassUser cu = new ClassUser();
                cu.setClassgrade(gradeList.get(j).getGradevalue());
                cu.setUserid(this.logined(request).getRef());
                cu.setRelationtype("任课老师");
                //cu.setSubjectid(subuserList.get(i).getSubjectid());
                cu.setYear(year);
                List<ClassUser>classList=this.classUserManager.getList(cu,null);
                List<SubjectInfo>subjectInfoList=new ArrayList<SubjectInfo>();
                if(classList!=null&&classList.size()>0){
                    for(ClassUser classUser :classList){
                        SubjectInfo s=new SubjectInfo();
                        s.setSubjectid(classUser.getSubjectid());
                        s.setSubjectname(classUser.getSubjectname());
                        if(!subjectInfoList.contains(s))
                            subjectInfoList.add(s);
                    }
                    if(subjectInfoList.size()>0){
                        for(SubjectInfo subjectInfo:subjectInfoList){
                            Map<String,Object> map = new HashMap<String, Object>();
                            map.put("gradeid",gradeList.get(j).getGradeid());
                            map.put("gradevalue",gradeList.get(j).getGradevalue());
                            map.put("subjectid",subjectInfo.getSubjectid());
                            map.put("subjectname",subjectInfo.getSubjectname());
                            if(!gradeSubjectList.contains(map))
                                gradeSubjectList.add(map);
                        }
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
        String termid=request.getParameter("termid");

        PageResult pr = new PageResult();
        UserInfo u = this.logined(request);
        pr.setOrderBy("SEMESTER_BEGIN_DATE");
        List<TermInfo> termList = this.termManager.getList(null, pr);
        mp.put("termList", termList);
        TermInfo currtTerm = null;
        if(termid!=null&&termid.trim().length()>0){
            TermInfo tm=new TermInfo();
            tm.setRef(termid);
            List<TermInfo> tmList=this.termManager.getList(tm,null);
            if(tmList!=null&&tmList.size()>0){
                currtTerm=tmList.get(0);
            }
        }
        if(currtTerm==null)
            currtTerm=this.termManager.getMaxIdTerm(false);
        mp.put("selTerm", currtTerm);
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

    /*

    /**
     * 进入班級课题主页
    @RequestMapping(params = "m=toClassCourseList", method = RequestMethod.GET)
    public ModelAndView toClassCourseList(HttpServletRequest request, HttpServletResponse response, ModelMap mp) throws Exception {
        UserInfo user = this.logined(request);

        JsonEntity jeEntity = new JsonEntity();
        String classid = request.getParameter("classid");
        String classtype = request.getParameter("classtype");
        String subjectid=request.getParameter("subjectid");
        String gradeid = request.getParameter("gradeid");
        String termid=request.getParameter("termid");
        UserInfo u=this.logined(request);

        if (termid == null || classid==null || classtype==null) {
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }

        TermInfo termInfo=new TermInfo();
        termInfo.setRef(termid);
        List<TermInfo>termList=this.termManager.getList(termInfo,null);
        if (termList==null||termList.size()<1) {
            jeEntity.setMsg("系统学期错误!");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }
        //查询年级数据
        GradeInfo gi = new GradeInfo();
        gi.setGradeid(Integer.parseInt(gradeid));
        List<GradeInfo> giList = this.gradeManager.getList(gi,null);
        ClassUser c = new ClassUser();
        //当前学期、学科、年级下的授课班级
        c.setClassgrade(giList.get(0).getGradevalue());
        c.setUserid(this.logined(request).getRef());
        c.setRelationtype("任课老师");
        c.setSubjectid(Integer.parseInt(subjectid));
        c.setYear(termList.get(0).getYear());
        List<ClassUser>clsList=this.classUserManager.getList(c,null);
        //List<ClassUser> clsList = this.classUserManager.getListByTchYear(user.getRef(), ti.getYear());
        TpVirtualClassInfo tvc= new TpVirtualClassInfo();
        tvc.setCuserid(u.getUserid());
        tvc.setStatus(1);
        List<TpVirtualClassInfo> tvcList=this.tpVirtualClassManager.getList(tvc, null);

        TpCourseClass tcc = new TpCourseClass();
        tcc.setUserid(user.getUserid());
        tcc.setTermid(termid);
        tcc.setClassid(Integer.parseInt(classid));
        List<TpCourseClass> courseList = this.tpCourseClassManager.getList(tcc, null);

        mp.put("courseList", courseList);
        mp.put("classid",classid);
        mp.put("classtype",classtype);
        mp.put("classes", clsList);
        mp.put("vclasses", tvcList);
        return new ModelAndView("/teachpaltform/classmanager/classCourseList",mp);
    }

    */

    /**
     * 进入班級课题主页
     */
    @RequestMapping(params = "m=toClassCourseList", method = RequestMethod.POST)
    public void toClassCourseList(HttpServletRequest request, HttpServletResponse response, ModelMap mp) throws Exception {
        UserInfo user = this.logined(request);

        JsonEntity jeEntity = new JsonEntity();
        String classid = request.getParameter("classid");
        String classtype = request.getParameter("classtype");
        String subjectid=request.getParameter("subjectid");
        String gradeid = request.getParameter("gradeid");
        String termid=request.getParameter("termid");
        UserInfo u=this.logined(request);

        if (termid == null || classid==null || classtype==null) {
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(jeEntity.toJSON());
            return;
        }

        TermInfo termInfo=new TermInfo();
        termInfo.setRef(termid);
        List<TermInfo>termList=this.termManager.getList(termInfo,null);
        if (termList==null||termList.size()<1) {
            jeEntity.setMsg("系统学期错误!");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
        TpCourseClass tcc = new TpCourseClass();
        tcc.setUserid(user.getUserid());
        tcc.setTermid(termid);
        tcc.setClassid(Integer.parseInt(classid));
        if(subjectid!=null&&subjectid.trim().length()>0&&!subjectid.equals("0"))
            tcc.setSubjectid(Integer.parseInt(subjectid));
        if(gradeid!=null&&gradeid.trim().length()>0&&!gradeid.equals("0"))
            tcc.setGradeid(Integer.parseInt(gradeid));
        List<TpCourseClass> courseList = this.tpCourseClassManager.getList(tcc, null);


       jeEntity.setObjList(courseList);
       jeEntity.setType("success");
       response.getWriter().print(jeEntity.toJSON());
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
        return new ModelAndView("/teachpaltform/classmanager/classCommentList",mp);
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
        String gradeid = request.getParameter("gradeid");
        String termid=request.getParameter("termid");
        UserInfo u=this.logined(request);
        TermInfo ti = this.termManager.getMaxIdTerm(false);
        if(termid!=null&&termid.trim().length()>0){
            TermInfo tm=new TermInfo();
            tm.setRef(termid);
            List<TermInfo> tmList=this.termManager.getList(tm,null);
            if(tmList!=null&&tmList.size()>0){
                ti=tmList.get(0);
            }
        }
        if (ti == null || classid==null || classtype==null||subjectid==null) {
            jeEntity.setMsg("学期参数错误,请联系教务。");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }
        //查询年级数据
        GradeInfo gi = new GradeInfo();
        gi.setGradeid(Integer.parseInt(gradeid));
        List<GradeInfo> giList = this.gradeManager.getList(gi,null);
        ClassUser c = new ClassUser();
        //当前学期、学科、年级下的授课班级
        c.setClassgrade(giList.get(0).getGradevalue());
        c.setUserid(this.logined(request).getRef());
        c.setRelationtype("任课老师");
        c.setSubjectid(Integer.parseInt(subjectid));
        c.setYear(ti.getYear());
        List<ClassUser>clsList=this.classUserManager.getList(c,null);
        //List<ClassUser> clsList = this.classUserManager.getListByTchYear(user.getRef(), ti.getYear());
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
        return new ModelAndView("/teachpaltform/classmanager/classStudentList",mp);
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
        String classid=request.getParameter("classid");
        if(subjectid==null||subjectid.trim().length()<1||termid==null||termid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TermInfo term=new TermInfo();
        term.setRef(termid);
        List<TermInfo>termInfoList=this.termManager.getList(term,null);
        if(termInfoList==null||termInfoList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        term=termInfoList.get(0);
        //没有发现该学生本学期的班级
//        List<TpCourseClass> tClsList=this.tpCourseClassManager.getTpClsEntityByUserSubTermId(Integer.parseInt(subjectid.trim()),this.logined(request).getUserid(),termid);
//        if(tClsList==null||tClsList.size()<1){
//             je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
//            response.getWriter().print(je.toJSON());
//            return;
//        }

        ClassUser cu=new ClassUser();
        cu.setUserid(this.logined(request).getRef());
        cu.setRelationtype("学生");
        cu.setYear(term.getYear());
        List<ClassUser> classList=this.classUserManager.getList(cu,null);
        if(classList==null&&classList.size()<1){
            je.setMsg("抱歉，您当前学期不在班级中，请联系相关教师进行处理!");
            response.getWriter().print(je.toJSON());
            return;
        }


        //如果没有传入classid参数，则第一位获取
        if(classid==null||classid.trim().length()<1){
            classid=classList.get(0).getClassid().toString();
        }
        //查询得到班级
        PageResult presult = this.getPageResultParameter(request);
        // 分页查询
        TpCourseInfo tcInfo = (TpCourseInfo) this.getParameter(request, TpCourseInfo.class);
        tcInfo.setUserid(this.logined(request).getUserid());
        tcInfo.setClassid(Integer.parseInt(classid.trim()));
        tcInfo.setSelectType(1);
        List<TpCourseInfo> stucourseList = this.tpCourseManager.getStuCourseList(tcInfo, presult);
        String courseids="";
        if(stucourseList!=null&&stucourseList.size()>0){
            for(int i =0;i<stucourseList.size();i++){
                courseids+=stucourseList.get(i).getCourseid();
                if(i<stucourseList.size()-1)
                    courseids+=",";


                //直播课连接
                if(stucourseList.get(i).getIslive()!=null&&Integer.parseInt(stucourseList.get(i).getIslive().toString())>0){
                    PageResult p=this.getPageResultParameter(request);
                    p.setOrderBy("t.order_idx desc ");
                    p.setPageSize(0);
                    p.setPageNo(0);
                    TpTaskInfo t=new TpTaskInfo();
                    t.setCourseid(stucourseList.get(i).getCourseid());
                    t.setUserid(this.logined(request).getUserid());
                    // 学生任务
                    List<TpTaskInfo>taskList=this.tpTaskManager.getListbyStu(t, p);
                    if(taskList!=null&&taskList.size()>0){
                        for(TpTaskInfo tmpTask:taskList){
                            if(tmpTask.getTasktype()==10&&tmpTask.getTaskstatus()!="3"&&tmpTask.getTaskstatus()!="1"){
                                String url=UtilTool.utilproperty.getProperty("GET_ETT_LIVE_ADDRESS");
                                HashMap<String,String> signMap = new HashMap();
                                signMap.put("courseName",tmpTask.getCoursename());
                                signMap.put("courseId",tmpTask.getTaskid().toString().replace("-",""));
                                signMap.put("userId",this.logined(request).getUserid().toString());
                                signMap.put("userName",this.logined(request).getRealname());
                                signMap.put("rec","3");
                                signMap.put("srcId","90");
                                signMap.put("timestamp",System.currentTimeMillis()+"");
                                String signture = UrlSigUtil.makeSigSimple("getTutorUrl.do",signMap,"*ETT#HONER#2014*");
                                signMap.put("sign",signture);
                                try{
                                    JSONObject jsonObject = UtilTool.sendPostUrl(url,signMap,"utf-8");
                                    int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
                                    if(type==1){
                                        String liveurl= jsonObject.containsKey("data")?jsonObject.getString("data"):"";
                                        if(liveurl!=null&&liveurl.trim().length()>0){
                                            //stucourseList.get(i).setLiveaddress(java.net.URLDecoder.decode(liveurl, "UTF-8"));
                                            stucourseList.get(i).setLiveaddress(liveurl);
                                            stucourseList.get(i).setTaskid(tmpTask.getTaskid());
                                        }
                                    }
                                }catch (Exception e){
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }
                        }
                    }
                } 
                

            }
            //计算任务数量
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
            //计算是否小组组长

            //查找当前班级，学科的登陆人是否是小组组长
            TpGroupStudent gs=new TpGroupStudent();
            gs.setIsleader(1);
            gs.setUserid(this.logined(request).getUserid());
            gs.getTpgroupinfo().setSubjectid(Integer.parseInt(subjectid));
            gs.setClassid(Integer.parseInt(classid));
            List<TpGroupStudent> gsList=this.tpGroupStudentManager.getList(gs,null);
            //得到小组Id,组织数据进行查询
            StringBuilder groupIdStr=new StringBuilder(",");
            if(gsList!=null&&gsList.size()>0){
                for (TpGroupStudent gsTmp:gsList){
                    if(gsTmp!=null&&gsTmp.getGroupid()!=null){
                        if(groupIdStr.toString().indexOf("," + gsTmp.getGroupid().toString() + ",")==-1){
                            if(groupIdStr.toString().trim().length()>1)
                                groupIdStr.append(",");
                            groupIdStr.append(gsTmp.getGroupid().toString());
                        }
                    }
                }
            }

            if(groupIdStr.toString().trim().length()>1)
                groupIdStr=new StringBuilder(groupIdStr.toString().substring(1));
            else
                groupIdStr=null;
            if(groupIdStr!=null&&groupIdStr.toString().trim().length()>0){
                if(courseids!=null&&courseids.trim().length()<1)
                    courseids=null;
                ClassInfo classInfo=new ClassInfo();
                classInfo.setClassid(Integer.parseInt(classid));
                List<ClassInfo> clsList=this.classManager.getList(classInfo,null);
                if(clsList!=null&&clsList.size()>0&&clsList.get(0).getDctype()==3){
                   for(TpCourseInfo tctmp:stucourseList){
                       List<Map<String,Object>> courseScoreIsOverList=tpCourseManager.getCourseScoreIsOver(Integer.parseInt(classid),Integer.parseInt(subjectid),tctmp.getCourseid()+"",groupIdStr.toString(),2);
                       if(courseScoreIsOverList!=null&&courseScoreIsOverList.size()>0){
                                  for(Map<String,Object> csOMap:courseScoreIsOverList){
                                    if(csOMap!=null&&csOMap.containsKey("STUSCORECOUNT")&&csOMap.containsKey("GROUPCOUNT")
                                            &&csOMap.containsKey("COURSE_ID")){
                                        String tcid=csOMap.get("COURSE_ID")!=null?csOMap.get("COURSE_ID").toString():null;
                                        String gCount=csOMap.get("GROUPCOUNT")!=null?csOMap.get("GROUPCOUNT").toString():null;
                                        String stuScoreCount=csOMap.get("STUSCORECOUNT")!=null?csOMap.get("STUSCORECOUNT").toString():null;
                                        if(tcid==null||tcid.trim().length()<1
                                                ||gCount==null||stuScoreCount==null
                                                )continue;
                                            if(tctmp!=null&&tctmp.getCourseid().toString().equals(tcid)&&(Integer.parseInt(stuScoreCount)==0||Integer.parseInt(gCount)==Integer.parseInt(stuScoreCount))){
                                                    tctmp.setCourseScoreIsOver(0);
                                                    break;
                                            }
                                        }
                                    }
                       }else{
                           //查询course内是否已经录入分数
                           List<Map<String,Object>> mlist=tpCourseManager.getCourseScoreIsOver(Integer.parseInt(classid),Integer.parseInt(subjectid),tctmp.getCourseid()+"",null,1);
                           if(mlist!=null&&mlist.size()>0)  //如果已录小组，则认为已经录入
                                tctmp.setCourseScoreIsOver(0);
                       }
                   }
                }else{
                    for(TpCourseInfo tctmp:stucourseList){
                        tctmp.setCourseScoreIsOver(0);
                    }
                }
            }else{
                for(TpCourseInfo tctmp:stucourseList){
                    tctmp.setCourseScoreIsOver(0);
                }
            }
        }
        Set<Map<String, Object>> classset = new HashSet<Map<String, Object>>();


//        TpVirtualClassStudent ts=new TpVirtualClassStudent();
//        ts.setUserid(this.logined(request).getUserid());
//        List<TpVirtualClassStudent>tpVirtualClassStudentList=this.tpVirtualClassStudentManager.getList(ts,null);



        List<ClassUser>tmpClassUser=new ArrayList<ClassUser>();
//        if(classList!=null&&classList.size()>0){
//            for (ClassUser classUser:classList){
                ClassUser tmpcu=new ClassUser();
                tmpcu.setYear(term.getYear());
                tmpcu.setRelationtype("任课老师");
                tmpcu.setSubjectid(Integer.parseInt(subjectid));
                tmpcu.setClassid(Integer.parseInt(classid));
                List<ClassUser>classUserList=this.classUserManager.getList(tmpcu,null);
                if(classUserList!=null&&classUserList.size()>0)
                    tmpClassUser.add(classUserList.get(0));
//            }
//        }
        List l = new ArrayList();




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
//            for (ClassUser clsu:tmpClassUser){
                List<TpGroupInfo> gl = this.tpGroupManager.getMyGroupList(
                        Integer.parseInt(classid),
                        1,
                        termid,
                        null,
                        this.logined(request).getUserid(),Integer.parseInt(subjectid));
                 groupList.addAll(gl);
//            }

        }
        //虚拟班级小组
//        if(tpVirtualClassStudentList!=null&&tpVirtualClassStudentList.size()>0){
//            for (TpVirtualClassStudent clsu:tpVirtualClassStudentList){
//                List<TpGroupInfo> gl = this.tpGroupManager.getMyGroupList(
//                        clsu.getVirtualclassid(),
//                        2,
//                        termid,
//                        null,
//                        this.logined(request).getUserid(),null);
//                groupList.addAll(gl);
//            }
//
//        }

        List<ClassInfo> clsList=new ArrayList<ClassInfo>();
        for (ClassUser tcu:classList){
            if(tcu!=null&&tcu.getClassid()!=null&&tcu.getClassname()!=null&&tcu.getClassgrade()!=null){
                ClassInfo cls=new ClassInfo();
                cls.setClassid(tcu.getClassid());
                cls.setClassname(tcu.getClassname());
                cls.setClassgrade(tcu.getClassgrade());
                cls.setGradeid(tcu.getClassinfo().getGradeid());
                boolean ishas=false;
                if(!clsList.contains(cls)){
                    clsList.add(cls);
                }
            }
        }

        l.add(stucourseList);  //学生专题数据
      //  l.add(tmpClassUser);    //学生班级数据
        l.add(clsList);      //班级
        l.add(null);    //学生虚拟班级数据

        l.add(groupList);  //学生小组数据
        UserInfo u = this.logined(request);
        List<SubjectInfo> subjectList = this.subjectManager.getHavaCourseSubject(termid,u.getRef(),u.getUserid());
        l.add(subjectList); //学生学科数据
        presult.setList(l);
        je.setPresult(presult);
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
        TpCourseInfo tc = (TpCourseInfo) this.getParameter(request, TpCourseInfo.class);
        tc.setDcschoolid(this.logined(request).getDcschoolid());
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

        String classEndTimeStr = request.getParameter("classEndTimeArray");
        String vclassEndTimeStr = request.getParameter("vclassEndTimeArray");

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
        if (classstr != null && classtimestr != null&& classEndTimeStr != null
                && classstr.trim().length() > 0 && classtimestr.trim().length() > 0 && classEndTimeStr.trim().length() > 0
                ) {
            String[] classArray = classstr.split(",");
            String[] classtimeArray = classtimestr.split(",");
            String[] classEndtimeArray = classEndTimeStr.split(",");
            //教学班级
            for (int i = 0; i < classArray.length; i++) {
                TpCourseClass cc = new TpCourseClass();
                cc.setClassid(Integer.parseInt(classArray[i]));
                cc.setCourseid(courseid);
                cc.setSubjectid(Integer.parseInt(subjectid));
                cc.setGradeid(Integer.parseInt(gradeid));
                cc.setTermid(termid);
                cc.setBegintime(UtilTool.StringConvertToDate(classtimeArray[i]));
                if(!classEndtimeArray[i].toString().equals("0"))
                    cc.setEndtime(UtilTool.StringConvertToDate(classEndtimeArray[i]));
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
        if (vclassstr != null && vclasstimestr != null &&  vclassEndTimeStr != null
                && vclassstr.trim().length() > 0 && vclasstimestr.trim().length() > 0 && vclassEndTimeStr.trim().length()>0) {
            String[] vclassArray = vclassstr.split(",");
            String[] vclasstimeArray = vclasstimestr.split(",");
            String[] vclassEndtimeArray = vclassEndTimeStr.split(",");
            for (int i = 0; i < vclassArray.length; i++) {
                TpCourseClass cc = new TpCourseClass();
                cc.setClassid(Integer.parseInt(vclassArray[i]));
                cc.setCourseid(courseid);
                cc.setSubjectid(Integer.parseInt(subjectid));
                cc.setGradeid(Integer.parseInt(gradeid));
                cc.setTermid(termid);
                cc.setBegintime(UtilTool.StringConvertToDate(vclasstimeArray[i]));
                if(!vclassEndtimeArray[i].equals("0"))
                    cc.setEndtime(UtilTool.StringConvertToDate(vclassEndtimeArray[i]));
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
     * 资源系统
     * 添加课题
     */
    @Transactional
    @RequestMapping(params = "m=addCourseByRes", method = RequestMethod.POST)
    public void doAddCourseByRes(HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        TpCourseInfo tc = (TpCourseInfo) this.getParameter(request, TpCourseInfo.class);
        tc.setDcschoolid(this.logined(request).getDcschoolid());
        String resid=request.getParameter("resid");

        if (resid == null || resid.trim().length() < 1) {
            je.setMsg("没有资源标识参数!");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(je.toJSON());
            return;
        }
        if (tc.getCourseid() == null || tc.getCourseid().toString().length()< 1) {
            je.setMsg("没有专题标识参数!");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(je.toJSON());
            return;
        }
        if (tc.getCoursename() == null || tc.getCoursename().length() < 1) {
            je.setMsg("没有专题名称参数!");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(je.toJSON());
            return;
        }
        if (tc.getMaterialidvalues() == null || tc.getMaterialidvalues().trim().length() < 1) {
            je.setMsg("没有专题教材编号参数!");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(je.toJSON());
            return;
        }
        if (tc.getSharetype() == null || tc.getSharetype() < 1) {
            je.setMsg("没有获得分享类型参数!");// 异常错误，参数不齐，无法正常访问!
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        String subjectid = request.getParameter("subjectid");
        if (subjectid == null || subjectid.length() < 1) {
            je.setMsg("没有获取学科参数!");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(je.toJSON());
            return;
        }
        String gradeid = request.getParameter("gradeid");
        if (gradeid == null || gradeid.length() < 1) {
            je.setMsg("没有获取年级参数!");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(je.toJSON());
            return;
        }
        String termid = request.getParameter("termid");
        if (termid == null || termid.length() < 1) {
            je.setMsg("没有获取学期参数!");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(je.toJSON());
            return;
        }
        String classstr = request.getParameter("classidstr");
        String vclassstr = request.getParameter("vclassidstr");
        if (classstr == null && vclassstr == null) {
            je.setMsg("没有获取关联班级参数!");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(je.toJSON());
            return;
        }
        if (classstr.trim().length() < 1 && vclassstr.trim().length() < 1) {
            je.setMsg("没有获取关联班级参数!");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(je.toJSON());
            return;
        }
        String classtimestr = request.getParameter("classTimeArray");
        String vclasstimestr = request.getParameter("vclassTimeArray");
        if (classtimestr == null && vclasstimestr == null) {
            je.setMsg("没有获取关联班级开课时间参数!");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(je.toJSON());
            return;
        }

        if (classtimestr.trim().length() == 0 && vclasstimestr.trim().length() == 0) {
            je.setMsg("没有获取关联班级开课时间参数!");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(je.toJSON());
            return;
        }

        String classEndTimeStr = request.getParameter("classEndTimeArray");
        String vclassEndTimeStr = request.getParameter("vclassEndTimeArray");

        List<String> sqlListArray = new ArrayList<String>();
        List<List<Object>> objListArray = new ArrayList<List<Object>>();
        List<Object> objList = null;
        StringBuilder sql = null;

        ////////////////////////// 添加专题，配置相关参数
        Long courseid = tc.getCourseid();
        tc.setCourseid(courseid);
        tc.setCuserid(this.logined(request).getUserid());

        TeacherInfo t = new TeacherInfo();
        t.setUserid(this.logined(request).getRef());
        List<TeacherInfo> tl = this.teacherManager.getList(t, null);
        if (tl == null || tl.size() == 0) {
            je.setMsg("教师信息获取失败，请确认身份或者联系管理员!");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(je.toJSON());
            return;
        }
        tc.setTeachername(tl.get(0).getTeachername());

        sql = new StringBuilder();
        objList = this.tpCourseManager.getSaveSql(tc, sql);
        if (sql == null || objList == null) {
            je.setMsg("专题数据写入出错！");// 异常错误，参数不齐，无法正常访问!
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
                    response.getWriter().print(je.toJSON());
                    return;
                }
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

        }else{
            je.setMsg("获取教材参数错误，请重试！");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(je.toJSON());
            return;
        }

        // 添加关联班级
        if (classstr != null && classtimestr != null&& classEndTimeStr != null
                && classstr.trim().length() > 0 && classtimestr.trim().length() > 0 && classEndTimeStr.trim().length() > 0
                ) {
            String[] classArray = classstr.split(",");
            String[] classtimeArray = classtimestr.split(",");
            String[] classEndtimeArray = classEndTimeStr.split(",");
            //教学班级
            for (int i = 0; i < classArray.length; i++) {
                TpCourseClass cc = new TpCourseClass();
                cc.setClassid(Integer.parseInt(classArray[i]));
                cc.setCourseid(courseid);
                cc.setSubjectid(Integer.parseInt(subjectid));
                cc.setGradeid(Integer.parseInt(gradeid));
                cc.setTermid(termid);
                cc.setBegintime(UtilTool.StringConvertToDate(classtimeArray[i]));
                if(!classEndtimeArray[i].toString().equals("0"))
                    cc.setEndtime(UtilTool.StringConvertToDate(classEndtimeArray[i]));
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
        if (vclassstr != null && vclasstimestr != null &&  vclassEndTimeStr != null
                && vclassstr.trim().length() > 0 && vclasstimestr.trim().length() > 0 && vclassEndTimeStr.trim().length()>0) {
            String[] vclassArray = vclassstr.split(",");
            String[] vclasstimeArray = vclasstimestr.split(",");
            String[] vclassEndtimeArray = vclassEndTimeStr.split(",");
            for (int i = 0; i < vclassArray.length; i++) {
                TpCourseClass cc = new TpCourseClass();
                cc.setClassid(Integer.parseInt(vclassArray[i]));
                cc.setCourseid(courseid);
                cc.setSubjectid(Integer.parseInt(subjectid));
                cc.setGradeid(Integer.parseInt(gradeid));
                cc.setTermid(termid);
                cc.setBegintime(UtilTool.StringConvertToDate(vclasstimeArray[i]));
                if(!vclassEndtimeArray[i].equals("0"))
                    cc.setEndtime(UtilTool.StringConvertToDate(vclassEndtimeArray[i]));
                cc.setClasstype(2);
                cc.setUserid(tc.getCuserid());
                sql = new StringBuilder();
                objList = this.tpCourseClassManager.getSaveSql(cc, sql);
                if (sql == null || objList == null) {
                    je.setMsg("专题班级数据写入出错！");// 异常错误，参数不齐，无法正常访问!
                    response.getWriter().print(je.toJSON());
                    return;
                }
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }

        //关联专题
        String selectedCourseid=request.getParameter("selectcourseid");
        if(selectedCourseid!=null&&selectedCourseid.length()>0){
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
                //添加默认
                je=this.doAddCourseResTask(request,response);
                if(je!=null&&!je.getType().toString().equals("success")){
                    transactionRollback();
                }
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
     * 添加引用专题前检查是否引用过，如果引用过，提示是恢复还是重新引用
     * */
    @RequestMapping(params = "m=checkQuoteCourse", method = RequestMethod.POST)
    public void checkQuoteCourse(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        int userid = this.logined(request).getUserid();
        String quoteid = request.getParameter("courseid");
        if(quoteid==null){
            je.setMsg("专题id丢失");
            je.getAlertMsgAndBack();
            return;
        }
        TpCourseInfo obj = new TpCourseInfo();
        obj.setCuserid(userid);
        obj.setQuoteid(Long.parseLong(quoteid));
        List<TpCourseInfo> tcList = this.tpCourseManager.checkQuoteCourse(obj);
        if(tcList!=null&&tcList.size()==1){
            je.setType("success");
            je.setObjList(tcList);
        }else{
            je.setType("error");
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 删除引用专题
     * */
    @RequestMapping(params = "m=delQuoteCourse", method = RequestMethod.POST)
    public void delQuoteCourse(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String courseid = request.getParameter("courseid");
        if(courseid==null){
            je.setMsg("专题id丢失");
            response.getWriter().print(je.toJSON());
            return;
        }
        TpCourseInfo obj = new TpCourseInfo();
        obj.setCourseid(Long.parseLong(courseid));
        Boolean b = this.tpCourseManager.doDelete(obj);
        if(b){
            je.setType("success");
        }else{
            je.setType("error");
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
        String materialid = request.getParameter("materialid");

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
            tc.setCourselevel(tc.getCourselevel());
            tc.setDcschoolid(this.logined(request).getDcschoolid());
            //默认为不分享
            //tc.setSharetype(3);

            sql = new StringBuilder();
            objList = this.tpCourseManager.getSaveSql(tc, sql);

            sqlListArray.add(sql.toString());
            objListArray.add(objList);

            //添加专题教材关联
            if(tc.getCourseid()!=null){
                //任务
                TpCourseTeachingMaterial ctm=new TpCourseTeachingMaterial();
                ctm.setCourseid(nextCourseId);//引用专题ID
                ctm.setTeachingmaterialid(Integer.parseInt(materialid));
                sql=new StringBuilder();
                objList=this.tpCourseTeachingMaterialManager.getSaveSql(ctm,sql);
                if(sql!=null&&objList!=null){
                     sqlListArray.add(sql.toString());
                     objListArray.add(objList);
                }
            }
            //查询当前要添加的年级学科
            TpTeacherTeachMaterial teacherTeachMaterial = new TpTeacherTeachMaterial();
            teacherTeachMaterial.setUserid(this.logined(request).getUserid());
            teacherTeachMaterial.setMaterialid(Integer.parseInt(materialid));
            List<TpTeacherTeachMaterial> teacherTeachMaterialList = this.tpTeacherTeachMaterialManager.getList(teacherTeachMaterial,null);
            //获取当默认年级学科
            Integer currGradeid = null;
            if(teacherTeachMaterialList!=null&&teacherTeachMaterialList.size()>0){
                currGradeid = teacherTeachMaterialList.get(0).getGradeid();
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
                        tmpTask.setCloudstatus(5);//引用的任务
                        Long taskid=-1l;
                        taskid=this.tpTaskManager.getNextId(true);
                        tmpTask.setTaskid(taskid);
                        //互动交流任务
                        if(tmpTask.getTasktype().toString().equals("2")){
                            //论题
                           TpTopicInfo tt=new TpTopicInfo();
                            tt.setCourseid(courseid);
                            tt.setTopicid(tmpTask.getTaskvalueid());
                            List<TpTopicInfo>topicInfoList=this.tpTopicManager.getList(tt,null);
                            if(topicInfoList!=null&&topicInfoList.size()>0){
                                TpTopicInfo tmpTopic=topicInfoList.get(0);

                                Long nextTopicid=this.tpTopicManager.getNextId(true);
                                //主题
                                TpTopicThemeInfo themeInfo=new TpTopicThemeInfo();
                                themeInfo.setCourseid(tmpTopic.getCourseid());
                                themeInfo.setTopicid(tmpTopic.getTopicid());
                                themeInfo.setSelectType(2);//查询内容
                                List<TpTopicThemeInfo>themeInfoList=this.tpTopicThemeManager.getList(themeInfo,null);
                                if(themeInfoList!=null&&themeInfoList.size()>0){
                                    for(TpTopicThemeInfo tmpThemeInfo :themeInfoList){
                                        tmpThemeInfo.setQuoteid(tmpThemeInfo.getThemeid());//记录引用的ID
                                        tmpThemeInfo.setCuserid(this.logined(request).getUserid());
                                        tmpThemeInfo.setThemeid(this.tpTopicThemeManager.getNextId(true));
                                        tmpThemeInfo.setTopicid(nextTopicid);
                                        tmpThemeInfo.setCourseid(nextCourseId);
                                        tmpThemeInfo.setCloudstatus(3);// 3：通过
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

                                tmpTask.setTaskvalueid(nextTopicid);

                            }
                        }else if(tmpTask.getTasktype().toString().equals("10")) //直播课跳过
                            continue;



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

                //试卷
                TpCoursePaper coursePaper=new TpCoursePaper();
                coursePaper.setCourseid(courseid);
                List<TpCoursePaper>coursePaperList=this.tpCoursePaperManager.getList(coursePaper,null);
                if(coursePaperList!=null&&coursePaperList.size()>0){
                    for (TpCoursePaper tmpPaper : coursePaperList){

                        tmpPaper.setCourseid(nextCourseId);
                        sql=new StringBuilder();
                        objList=this.tpCoursePaperManager.getSaveSql(tmpPaper,sql);
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
                        themeInfo.setSelectType(2);//查询内容
                        List<TpTopicThemeInfo>themeInfoList=this.tpTopicThemeManager.getList(themeInfo,null);
                        if(themeInfoList!=null&&themeInfoList.size()>0){
                            for(TpTopicThemeInfo tmpThemeInfo :themeInfoList){
                            	tmpThemeInfo.setQuoteid(tmpThemeInfo.getThemeid());//记录引用的ID
                            	tmpThemeInfo.setCuserid(tmpThemeInfo.getCuserid());
                                tmpThemeInfo.setThemeid(this.tpTopicThemeManager.getNextId(true));
                                tmpThemeInfo.setTopicid(nextTopicid);
                                tmpThemeInfo.setCourseid(nextCourseId);
                                tmpThemeInfo.setCloudstatus(3);// 3：通过
                                tmpThemeInfo.setStatus(1L);//引用专题下  1：显示   2：不显示
                                tmpThemeInfo.setPinglunshu(tmpThemeInfo.getPinglunshu());
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
                                //根据引用的ID,得到相关评论
                                TpThemeReplyInfo tr1=new TpThemeReplyInfo();
                                tr1.setThemeid(tmpThemeInfo.getQuoteid());
                                tr1.setToreplyid(-1L);
                                List<TpThemeReplyInfo> trList=this.tpThemeReplyManager.getList(tr1,null);
                                if(trList!=null&&trList.size()>0){
                                    for (TpThemeReplyInfo trTmp:trList){
                                        if(trTmp!=null){
                                            trTmp.setThemeid(tmpThemeInfo.getThemeid());
                                            trTmp.setToreplyid(null);
                                            Long oldReplyId=trTmp.getReplyid();
                                            Long nreplyId=this.tpThemeReplyManager.getNextId(true);
                                            trTmp.setReplyid(nreplyId);
                                            trTmp.setCtime(trTmp.getCtime());

                                            StringBuilder sqlbuilder=new StringBuilder();
                                            objList=this.tpThemeReplyManager.getSaveSql(trTmp,sqlbuilder);
                                            if(sqlbuilder!=null&&objList!=null){
                                                sqlListArray.add(sqlbuilder.toString());
                                                objListArray.add(objList);
                                            }
                                            if(trTmp.getReplycontent()!=null){
                                                //得到theme_content的更新语句
                                                this.tpTopicThemeManager.getArrayUpdateLongText("tp_theme_reply_info", "reply_id", "reply_content"
                                                        , trTmp.getReplycontent(), trTmp.getReplyid().toString(),sqlListArray,objListArray);
                                            }
                                            loadReplyInfo(oldReplyId,trTmp.getReplyid(),tmpThemeInfo.getThemeid(),sqlListArray,objListArray);
                                        }
                                    }
                                }

                            }
                        }
                        //引用的TOPIC_ID
                        tmpTopic.setQuoteid(tmpTopic.getTopicid());
                        tmpTopic.setCourseid(nextCourseId);
                        tmpTopic.setCuserid(tmpTopic.getCuserid());
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

    private void loadReplyInfo(Long oreplyid,Long nreplyid,Long themeid,List<String> sqlListArray, List<List<Object>> objListArray){
        //加载回帖
        TpThemeReplyInfo tr=new TpThemeReplyInfo();
        tr.setToreplyid(oreplyid);
        List<TpThemeReplyInfo> tpThemeList=this.tpThemeReplyManager.getList(tr,null);
        if(tpThemeList!=null&&tpThemeList.size()>0){
            for (TpThemeReplyInfo trTmp:tpThemeList){
                if(trTmp!=null){
                    StringBuilder sqlbuilder=new StringBuilder();
                    trTmp.setToreplyid(nreplyid);
                    Long oldReplyId=trTmp.getReplyid();
                    Long newThemeId=this.tpThemeReplyManager.getNextId(true);
                    trTmp.setReplyid(newThemeId);
                    trTmp.setThemeid(themeid);
                    trTmp.setCtime(trTmp.getCtime());
                    List<Object> objList=this.tpThemeReplyManager.getSaveSql(trTmp,sqlbuilder);
                    if(sqlbuilder!=null&&objList!=null){
                        sqlListArray.add(sqlbuilder.toString());
                        objListArray.add(objList);
                    }
                    if(trTmp.getReplycontent()!=null){
                        //得到theme_content的更新语句
                        this.tpTopicThemeManager.getArrayUpdateLongText("tp_theme_reply_info", "reply_id", "reply_content"
                                , trTmp.getReplycontent(), trTmp.getReplyid().toString(),sqlListArray,objListArray);
                    }
                    loadReplyInfo(oldReplyId,trTmp.getReplyid(),themeid,sqlListArray,objListArray);
                }
            }
        }
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


                    //试卷
                    TpCoursePaper coursePaper=new TpCoursePaper();
                    coursePaper.setCourseid(courseid);
                    List<TpCoursePaper>coursePaperList=this.tpCoursePaperManager.getList(coursePaper,null);
                    if(coursePaperList!=null&&coursePaperList.size()>0){
                        for (TpCoursePaper tmpPaper : coursePaperList){
                            tmpPaper.setCourseid(nextCourseId);
                            sql=new StringBuilder();
                            objList=this.tpCoursePaperManager.getSaveSql(tmpPaper,sql);
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
        TpCourseInfo tc = (TpCourseInfo) this.getParameter(request, TpCourseInfo.class);
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
        TpCourseInfo tc = (TpCourseInfo) this.getParameter(request, TpCourseInfo.class);
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
        TpCourseInfo tc = (TpCourseInfo) this.getParameter(request, TpCourseInfo.class);
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

        String classEndTimeStr = request.getParameter("classEndTimeArray");
        String vclassEndTimeStr = request.getParameter("vclassEndTimeArray");


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
        TpCourseInfo tcSel=new TpCourseInfo();
        tcSel.setCourseid(tc.getCourseid());
        List<TpCourseInfo> tpCourseInfos=this.tpCourseManager.getList(tcSel,null);
        if(tpCourseInfos==null||tpCourseInfos.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }
        //沿用专题等级
        tc.setCourselevel(tpCourseInfos.get(0).getCourselevel());
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
        // 添加关联班级
        if (classstr != null && classtimestr != null&& classEndTimeStr != null
                && classstr.trim().length() > 0 && classtimestr.trim().length() > 0 && classEndTimeStr.trim().length() > 0
                ) {
            String[] classArray = classstr.split(",");
            String[] classtimeArray = classtimestr.split(",");
            String[] classEndtimeArray = classEndTimeStr.split(",");
            //教学班级
            for (int i = 0; i < classArray.length; i++) {
                cc = new TpCourseClass();
                cc.setClassid(Integer.parseInt(classArray[i]));
                cc.setCourseid(tc.getCourseid());
                cc.setSubjectid(Integer.parseInt(subjectid));
                cc.setGradeid(Integer.parseInt(gradeid));
                cc.setTermid(termid);
                cc.setBegintime(UtilTool.StringConvertToDate(classtimeArray[i]));
                if(!classEndtimeArray[i].toString().equals("0"))
                    cc.setEndtime(UtilTool.StringConvertToDate(classEndtimeArray[i]));
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
        if (vclassstr != null && vclasstimestr != null &&  vclassEndTimeStr != null
                && vclassstr.trim().length() > 0 && vclasstimestr.trim().length() > 0 && vclassEndTimeStr.trim().length()>0) {
            String[] vclassArray = vclassstr.split(",");
            String[] vclasstimeArray = vclasstimestr.split(",");
            String[] vclassEndtimeArray = vclassEndTimeStr.split(",");
            for (int i = 0; i < vclassArray.length; i++) {
                cc = new TpCourseClass();
                cc.setClassid(Integer.parseInt(vclassArray[i]));
                cc.setCourseid(tc.getCourseid());
                cc.setSubjectid(Integer.parseInt(subjectid));
                cc.setGradeid(Integer.parseInt(gradeid));
                cc.setTermid(termid);
                cc.setBegintime(UtilTool.StringConvertToDate(vclasstimeArray[i]));
                if(!vclassEndtimeArray[i].toString().equals("0"))
                    cc.setEndtime(UtilTool.StringConvertToDate(vclassEndtimeArray[i]));
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

        /**
         * 自建专题修改分享等级
         * 之前已共享的资源不修改
         * 专题条件：专题ID小于0、专题等级自建、专题为分享专题
         * 资源条件：资源ID小于0、资源为不分享
         */
        TpCourseResource courseResource=new TpCourseResource();
        courseResource.setCourseid(tc.getCourseid());
        List<TpCourseResource>tpCourseResourcesList=this.tpCourseResourceManager.getList(courseResource,null);
        if(tpCourseResourcesList!=null&&tpCourseResourcesList.size()>0&&tc.getSharetype()!=3
                &&tc.getCourseid()<1&&tc.getCourselevel()==3){
            for(TpCourseResource res :tpCourseResourcesList){
                if(res!=null&&res.getResid()<1&&res.getSharestatus()==3){
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
        TpCourseInfo tcInfo = (TpCourseInfo) this.getParameter(request, TpCourseInfo.class);
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
        TpCourseInfo tcInfo = (TpCourseInfo) this.getParameter(request, TpCourseInfo.class);
        tcInfo.setDcschoolid(this.logined(request).getDcschoolid());

        // 如果搜索类型为专题库搜索 searchType=1 ,则默认附加专题库搜索前置条件
        if(searchType!=null && searchType.trim().equals("1")){
            //tcInfo.setQuoteid(new Long(0));
            tcInfo.setCourselevel(-3); // -3标识不共享反义，即所有符合共享条件的专题 或者校内共享
            if(tcInfo.getFiltergrade()!=null){
                tcInfo.setFilterquote(1);//去除当前教师引用过的专题
                tcInfo.setCuserid(this.logined(request).getUserid());
            }
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
        TpCourseInfo tcInfo = (TpCourseInfo) this.getParameter(request, TpCourseInfo.class);

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
        if (materialid == null ) {
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
     * 获取教师教材
     */
    @RequestMapping(params = "m=getTchMaterial", method = RequestMethod.POST)
    public void getTeacherMaterial(HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String gradeid = request.getParameter("gradeid");
        String subjectid = request.getParameter("subjectid");
        String termid=request.getParameter("atermid");
        if (subjectid == null || termid==null) { //gradeid == null ||
            je.setMsg("参数错误，无法请求数据！");
            response.getWriter().print(je.toJSON());
            return;
        }

        List<TpTeacherTeachMaterial> entityList=null;
        //得到 该教师的当前教材
        if(subjectid!=null&&subjectid.trim().length()>0 //gradeid!=null&&gradeid.trim().length()>0&&
                &&termid!=null&&termid.length()>0){
            TpTeacherTeachMaterial tentity=new TpTeacherTeachMaterial();
            tentity.setUserid(this.logined(request).getUserid());
            System.out.println("**********************************************************************查询前，赋值gradeid====="+gradeid);
            tentity.setGradeid(Integer.parseInt(gradeid));
            System.out.println("**********************************************************************查询前，赋值gradeid后====="+gradeid);
            tentity.setSubjectid(Integer.parseInt(subjectid));
            tentity.setTermid(termid);
            entityList=this.tpTeacherTeachMaterialManager.getList(tentity,null);
            // if(entityList!=null&&entityList.size()>0)
            //tcInfo.setMaterialidvalues(entityList.get(0).getMaterialid().toString());
        }
        je.setType("success");
        je.getObjList().add(entityList==null||entityList.size()<1?null:entityList.get(0));
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
        String resid=request.getParameter("resid");
        if (gradeid == null || subjectid == null) {
            je.setType("error");
            je.setMsg("参数错误，无法请求数据！");
            response.getWriter().print(je.toJSON());
            return;
        }

        UserInfo user = this.logined(request);

        TpCourseInfo tcInfo = (TpCourseInfo) this.getParameter(request,
                TpCourseInfo.class);
        tcInfo.setUserid(user.getUserid());

        List l = new ArrayList();

        TermInfo t=new TermInfo();
        t.setRef(termid);
        List<TermInfo>termInfoList=this.termManager.getList(t,null);
        if(termInfoList==null||termInfoList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(resid!=null&&resid.trim().length()>0)
            tcInfo.setResid(Long.parseLong(resid));


        PageResult presult = this.getPageResultParameter(request);
        List<TpCourseInfo> courseList = this.tpCourseManager.getTchCourseList(
                tcInfo, presult);
        if(courseList!=null&&courseList.size()>0){
            for(TpCourseInfo tctmp:courseList){
                if(tctmp!=null){
                    if(tctmp.getClassesid()!=null&&tctmp.getClassesid().toString().trim().length()>0){
                        String[] clsidArray=tctmp.getClassesid().toString().split(",");
                        if(clsidArray.length<1)
                            continue;
                        for (String clsid:clsidArray){
                            if(clsid!=null&&clsid.trim().length()>0){
                                //查询得到班级类型
                                ClassInfo cls=new ClassInfo();
                                cls.setClassid(Integer.parseInt(clsid.trim()));
                                cls.setDcschoolid(this.logined(request).getDcschoolid());
                                List<ClassInfo> clsList=this.classManager.getList(cls,null);
                                if(clsList==null||clsList.size()<1){
                                    if(tctmp.getCourseScoreIsOverStr()==null||tctmp.getCourseScoreIsOverStr().trim().length()<1)
                                        tctmp.setCourseScoreIsOverStr("0");
                                    else{
                                        tctmp.setCourseScoreIsOverStr(tctmp.getCourseScoreIsOverStr()+",0");
                                    }
                                    continue;
                                }else if(clsList.get(0).getDctype()!=3){
                                    if(tctmp.getCourseScoreIsOverStr()==null||tctmp.getCourseScoreIsOverStr().trim().length()<1)
                                        tctmp.setCourseScoreIsOverStr("0");
                                    else{
                                        tctmp.setCourseScoreIsOverStr(tctmp.getCourseScoreIsOverStr()+",0");
                                    }
                                    continue;
                                }
                                List<Map<String,Object>> courseScoreIsOverList=tpCourseManager.getCourseScoreIsOver(Integer.parseInt(clsid.trim()),tctmp.getSubjectid(),tctmp.getCourseid()+"",null,1);
                                if(courseScoreIsOverList!=null&&courseScoreIsOverList.size()>0){
                                    for(Map<String,Object> csOMap:courseScoreIsOverList){
                                        if(csOMap!=null&&csOMap.containsKey("STUSCORECOUNT")&&csOMap.containsKey("GROUPCOUNT")
                                                &&csOMap.containsKey("COURSE_ID")){
                                            String tcid=csOMap.get("COURSE_ID")!=null?csOMap.get("COURSE_ID").toString():null;
                                            String gCount=csOMap.get("GROUPCOUNT")!=null?csOMap.get("GROUPCOUNT").toString():null;
                                            String stuScoreCount=csOMap.get("STUSCORECOUNT")!=null?csOMap.get("STUSCORECOUNT").toString():null;
                                            if(tcid==null||tcid.trim().length()<1
                                                    ||gCount==null||stuScoreCount==null
                                                    )continue;
                                            if(tctmp!=null&&tctmp.getCourseid().toString().equals(tcid)&&(Integer.parseInt(stuScoreCount)==0||Integer.parseInt(gCount)==Integer.parseInt(stuScoreCount))){
                                                if(tctmp.getCourseScoreIsOverStr()==null||tctmp.getCourseScoreIsOverStr().trim().length()<1)
                                                     tctmp.setCourseScoreIsOverStr("0");
                                                else{
                                                    tctmp.setCourseScoreIsOverStr(tctmp.getCourseScoreIsOverStr()+",0");
                                                }
                                                break;
                                            }else{
                                                if(tctmp.getCourseScoreIsOverStr()==null||tctmp.getCourseScoreIsOverStr().trim().length()<1)
                                                    tctmp.setCourseScoreIsOverStr("1");
                                                else{
                                                    tctmp.setCourseScoreIsOverStr(tctmp.getCourseScoreIsOverStr()+",1");
                                                }
                                            }
                                        }
                                    }
                                }else{
                                    if(tctmp.getCourseScoreIsOverStr()==null||tctmp.getCourseScoreIsOverStr().trim().length()<1)
                                        tctmp.setCourseScoreIsOverStr("1");
                                    else{
                                        tctmp.setCourseScoreIsOverStr(tctmp.getCourseScoreIsOverStr()+",1");
                                    }
                                }
                            }
                        }
                    }

                    //直播课连接
                    if(tctmp.getIslive()!=null&&Integer.parseInt(tctmp.getIslive().toString())>0){
                        PageResult pageResult=new PageResult();
                        pageResult.setOrderBy("u.order_idx,u.c_time ");
                        TpTaskInfo liveTask=new TpTaskInfo();
                        liveTask.setCourseid(tctmp.getCourseid());
                        liveTask.setTasktype(10);
                        liveTask.setSelecttype(1);
                        liveTask.setLoginuserid(this.logined(request).getUserid());
                        liveTask.setStatus(1);
                        List<TpTaskInfo>liveTaskList=this.tpTaskManager.getTaskReleaseList(liveTask,pageResult);
                        if(liveTaskList!=null&&liveTaskList.size()>0){
                            for(TpTaskInfo tmpTask:liveTaskList){
                                if(tmpTask.getTaskstatus()!="3"&&tmpTask.getTaskstatus()!="1"){
                                    String url=UtilTool.utilproperty.getProperty("GET_ETT_LIVE_ADDRESS");
                                    HashMap<String,String> signMap = new HashMap();
                                    signMap.put("courseName",tctmp.getCoursename());
                                    signMap.put("courseId",tmpTask.getTaskid().toString().replace("-",""));
                                    signMap.put("userId",this.logined(request).getUserid().toString());
                                    signMap.put("userName",this.logined(request).getRealname());
                                    signMap.put("rec","2");
                                    signMap.put("srcId","90");
                                    signMap.put("timestamp",System.currentTimeMillis()+"");
                                    String signture = UrlSigUtil.makeSigSimple("getTutorUrl.do",signMap,"*ETT#HONER#2014*");
                                    signMap.put("sign",signture);
                                    try{
                                        JSONObject jsonObject = UtilTool.sendPostUrl(url,signMap,"utf-8");
                                        int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
                                        if(type==1){
                                            String liveurl= jsonObject.containsKey("data")?jsonObject.getString("data"):"";
                                            if(liveurl!=null&&liveurl.trim().length()>0){
                                                tctmp.setLiveaddress(liveurl);
                                                tctmp.setTaskid(tmpTask.getTaskid());
                                            }

                                        }

                                    }catch (Exception e){
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        

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

        presult.setList(l);
        je.setPresult(presult);
        response.getWriter().print(je.toJSON());
    }


    /**
     * 获取日历课题ajax列表
     */
    @RequestMapping(params = "getCourseCalanedListAjax", method = RequestMethod.POST)
    public void getCourseCalanedListAjax(HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String gradeid = request.getParameter("gradeid");
        String subjectid = request.getParameter("subjectid");
        String termid=request.getParameter("atermid");
        String year=request.getParameter("year");
        String month=request.getParameter("month");
        String day=request.getParameter("day");
        PageResult pageResult=this.getPageResultParameter(request);

        if (year == null || month==null || day==null || termid == null) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }

        UserInfo user = this.logined(request);

        TpCourseInfo tcInfo = (TpCourseInfo) this.getParameter(request,
                TpCourseInfo.class);
        tcInfo.setUserid(user.getUserid());

        Calendar calendar=Calendar.getInstance();

        calendar.set(Integer.parseInt(year),Integer.parseInt(month)-1,Integer.parseInt(day));

        tcInfo.setSeldate(UtilTool.DateConvertToString(calendar.getTime(), UtilTool.DateType.smollDATE));

        String gradeValue=null;
        if(gradeid!=null&&gradeid.trim().length()>0){
            GradeInfo gradeInfo=new GradeInfo();
            gradeInfo.setGradeid(Integer.parseInt(gradeid));
            List<GradeInfo>gList=this.gradeManager.getList(gradeInfo,null);
            if(gList!=null&&gList.size()>0)
                gradeValue=gList.get(0).getGradevalue();
        }

        //当前学期、年级的身份信息 1:教师2:班主任3:任课班主任
        Integer userType=this.classUserManager.isTeachingBanZhuRen(this.logined(request).getRef(),null,termid,gradeValue);

        Integer usertype=2;
        subjectid=tcInfo.getSubjectid()+"";
        if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
            usertype=1;
            tcInfo.setSubjectid(null);
        }else if(this.validateRole(request,UtilTool._ROLE_CLASSADVISE_ID)&&userType>1)
            usertype=3;
        else if(this.validateRole(request,UtilTool._ROLE_TEACHER_ID))
            usertype=2;

        if(usertype==null){
            je.setMsg("异常错误!角色错误!");
            response.getWriter().print(je.toJSON());
            return;
        }

        tcInfo.setUsertype(usertype);
        TermInfo t=new TermInfo();
        t.setRef(termid);
        List<TermInfo>termInfoList=this.termManager.getList(t,null);
        if(termInfoList==null||termInfoList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }

        tcInfo.setTermid(termid);
        tcInfo.setDcschoolid(this.logined(request).getDcschoolid());
        List<TpCourseInfo> courseList = this.tpCourseManager.getCalanderCourseList(
                tcInfo, null);
        String courseids="";
        //如果是学生或者老师，则查询积分录入情况
        if(usertype==1||usertype==2||usertype==3){
            if(courseList!=null&&courseList.size()>0){
                //组织courseid
                for (TpCourseInfo tc:courseList){
                    if(tc!=null&&tc.getCourseid()!=null){
                        if(courseids.trim().length()>0)
                            courseids+=",";
                        courseids+=tc.getCourseid();
                    }
                }
                //计算是否小组组长
                Integer pro_type=1;
                if(usertype==1){
                       for(TpCourseInfo tctmp:courseList){

                           //查找当前班级，学科的登陆人是否是小组组长
                           TpGroupStudent gs=new TpGroupStudent();
                           gs.setIsleader(1);
                           gs.setUserid(this.logined(request).getUserid());
                           gs.getTpgroupinfo().setSubjectid(tctmp.getSubjectid());
                           gs.setClassid(tctmp.getClassid());
                           List<TpGroupStudent> gsList=this.tpGroupStudentManager.getList(gs,null);
                           //得到小组Id,组织数据进行查询
                           StringBuilder groupIdStr=new StringBuilder(",");
                           if(gsList!=null&&gsList.size()>0){
                               for (TpGroupStudent gsTmp:gsList){
                                   if(gsTmp!=null&&gsTmp.getGroupid()!=null){
                                       if(groupIdStr.toString().indexOf("," + gsTmp.getGroupid().toString() + ",")==-1){
                                           if(groupIdStr.toString().trim().length()>1)
                                               groupIdStr.append(",");
                                           groupIdStr.append(gsTmp.getGroupid().toString());
                                       }
                                   }
                               }
                           }

                           if(groupIdStr.toString().trim().length()>1)
                               groupIdStr=new StringBuilder(groupIdStr.toString().substring(1));
                           else
                               groupIdStr=null;
                           //说明是该班的小组组长
                           if(groupIdStr!=null&&groupIdStr.toString().trim().length()>0){

                               ClassInfo cls=new ClassInfo();
                               cls.setClassid(tctmp.getClassid());
                               List<ClassInfo> clsList=this.classManager.getList(cls,null);
                               if(clsList==null||clsList.size()<1||clsList.get(0).getDctype()!=3){
                                   tctmp.setCourseScoreIsOver(0);
                                   continue;
                               }

                           List<Map<String,Object>> courseScoreIsOverList=tpCourseManager.getCourseScoreIsOver(tctmp.getClassid(),tctmp.getSubjectid(),courseids,groupIdStr.toString(),2);
                           if(courseScoreIsOverList!=null&&courseScoreIsOverList.size()>0){
                                for(Map<String,Object> csOMap:courseScoreIsOverList){
                                    if(csOMap!=null&&csOMap.containsKey("STUSCORECOUNT")&&csOMap.containsKey("GROUPCOUNT")
                                            &&csOMap.containsKey("COURSE_ID")){
                                        String tcid=csOMap.get("COURSE_ID")!=null?csOMap.get("COURSE_ID").toString():null;
                                        String gCount=csOMap.get("GROUPCOUNT")!=null?csOMap.get("GROUPCOUNT").toString():null;
                                        String stuScoreCount=csOMap.get("STUSCORECOUNT")!=null?csOMap.get("STUSCORECOUNT").toString():null;
                                        if(tcid==null||tcid.trim().length()<1
                                                ||gCount==null||stuScoreCount==null
                                                )continue;
                                        if(tctmp!=null&&tctmp.getCourseid().toString().equals(tcid)&&(Integer.parseInt(stuScoreCount)==0||Integer.parseInt(gCount)==Integer.parseInt(stuScoreCount))){
                                            tctmp.setCourseScoreIsOver(0);
                                            break;
                                        }
                                    }
                                }
                            }
                        }else
                               tctmp.setCourseScoreIsOver(0);
                    }
                }else{
                    for(TpCourseInfo tctmp:courseList){
                        if(tctmp!=null){
                            ClassInfo cls=new ClassInfo();
                            cls.setClassid(tctmp.getClassid());
                            List<ClassInfo> clsList=this.classManager.getList(cls,null);
                            if(clsList==null||clsList.size()<1||clsList.get(0).getDctype()!=3||!tctmp.getCuserid().equals(this.logined(request).getUserid())){
                                tctmp.setCourseScoreIsOver(0);
                                continue;
                            }

                            List<Map<String,Object>> courseScoreIsOverList=tpCourseManager.getCourseScoreIsOver(tctmp.getClassid(),tctmp.getSubjectid(),tctmp.getCourseid()+"",null,1);
                            if(courseScoreIsOverList!=null&&courseScoreIsOverList.size()>0){
                                for(Map<String,Object> csOMap:courseScoreIsOverList){
                                    if(csOMap!=null&&csOMap.containsKey("STUSCORECOUNT")&&csOMap.containsKey("GROUPCOUNT")
                                            &&csOMap.containsKey("COURSE_ID")){
                                        String tcid=csOMap.get("COURSE_ID")!=null?csOMap.get("COURSE_ID").toString():null;
                                        String gCount=csOMap.get("GROUPCOUNT")!=null?csOMap.get("GROUPCOUNT").toString():null;
                                        String stuScoreCount=csOMap.get("STUSCORECOUNT")!=null?csOMap.get("STUSCORECOUNT").toString():null;
                                        if(tcid==null||tcid.trim().length()<1
                                                ||gCount==null||stuScoreCount==null
                                                )continue;
                                        if(tctmp!=null&&tctmp.getCourseid().toString().equals(tcid)&&(Integer.parseInt(stuScoreCount)==0||Integer.parseInt(gCount)==Integer.parseInt(stuScoreCount))){
                                            tctmp.setCourseScoreIsOver(0);
                                            break;
                                        }
                                    }
                                }
                            }
//                            else
//                                tctmp.setCourseScoreIsOver(0);
                        }
                    }
                }
          }
        }

        je.setObjList(courseList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 获取教师受托管课题ajax列表
     */
    @RequestMapping(params = "m=getTrusteeshipCourListAjax", method = RequestMethod.POST)
    public void getTrusteeshipCourseListAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        TpCourseInfo tcInfo = (TpCourseInfo) this.getParameter(request, TpCourseInfo.class);
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
        TpCourseInfo tc = (TpCourseInfo) this.getParameter(request, TpCourseInfo.class);

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

            //得到当前专题的教材版本
            TpCourseTeachingMaterial ctmInfo=new TpCourseTeachingMaterial();
            ctmInfo.setCourseid(tc.getCourseid());
            List<TpCourseTeachingMaterial> tctmList=this.tpCourseTeachingMaterialManager.getList(ctmInfo,null);
            if(tctmList!=null&&tctmList.size()>0){
                mp.put("maid",tctmList.get(0).getTeachingmaterialid());
            }
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
        cu.setRelationtype("任课老师");
        cu.setSubjectid(Integer.parseInt(subjectid));
        List<ClassUser> clsList = this.classUserManager.getList(cu, null);

//        //获取虚拟班级
//        TpVirtualClassInfo tvc = new TpVirtualClassInfo();
//        tvc.setCuserid(this.logined(request).getUserid());
//        tvc.setStatus(1);
//        List<TpVirtualClassInfo> tvcList = this.tpVirtualClassManager.getList(tvc, null);

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
        //mp.put("tvcList", tvcList);
        mp.put("grade", grade);
        mp.put("subject", sub);
        mp.put("term", ti);
        mp.put("tc", tc);
        return new ModelAndView("/teachpaltform/course/courseSaveOrUpdate", mp);
    }


    /**
     * 资源系统
     * 加载专题新建页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "toSaveCourse", method = RequestMethod.GET)
    public ModelAndView toSaveCourse(HttpServletRequest request, HttpServletResponse response, ModelMap mp)
            throws Exception {
        JsonEntity jeEntity = new JsonEntity();
        UserInfo user = this.logined(request);
        String gradeid = request.getParameter("gradeid");
        String subjectid = request.getParameter("subjectid");
        String materialid = request.getParameter("materialid");

        TpCourseInfo tc = (TpCourseInfo) this.getParameter(request, TpCourseInfo.class);

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

            //得到当前专题的教材版本
            TpCourseTeachingMaterial ctmInfo=new TpCourseTeachingMaterial();
            ctmInfo.setCourseid(tc.getCourseid());
            List<TpCourseTeachingMaterial> tctmList=this.tpCourseTeachingMaterialManager.getList(ctmInfo,null);
            if(tctmList!=null&&tctmList.size()>0){
                mp.put("maid",tctmList.get(0).getTeachingmaterialid());
            }
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
        cu.setRelationtype("任课老师");
        cu.setSubjectid(Integer.parseInt(subjectid));
        List<ClassUser> clsList = this.classUserManager.getList(cu, null);


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
        mp.put("grade", grade);
        mp.put("subject", sub);
        mp.put("term", ti);
        mp.put("tc", tc);
        mp.put("addCourseId",this.tpCourseManager.getNextId(true));
        return new ModelAndView("/teachpaltform/task/teacher/dialog/add-course", mp);
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
        String materialid=request.getParameter("materialid");
        if(materialid==null||materialid.trim().length()<1)
            materialid="";

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
        gradeList = this.gradeManager.getList(new GradeInfo(),null);//this.gradeManager.getTchGradeList(user.getUserid(), ti.getYear());

        mp.put("subject", subject);
        mp.put("materialid",materialid);
        mp.put("grade", grade);
        mp.put("termid", termid);
        mp.put("tmList", tmList);
        mp.put("gradeList", gradeList);
        mp.put("suList", suList);
        mp.put("userid", user.getUserid());
        List<SchoolInfo> schoolList = this.schoolManager.getList(null,null);
        mp.put("schoolList",schoolList);
        String schoolname=this.logined(request).getDcschoolname();
        mp.put("schoolname",schoolname);
        return new ModelAndView("/teachpaltform/course/addSelectedCourse", mp);
    }

    @RequestMapping(params = "m=getMaterial", method = RequestMethod.POST)
    public void getMaterial(HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String subjectid = request.getParameter("subjectid");
        String gradeid = request.getParameter("gradeid");
        TeachingMaterialInfo tm = new TeachingMaterialInfo();
        tm.setGradeid(Integer.parseInt(gradeid));
        tm.setSubjectid(Integer.parseInt(subjectid));
        List<TeachingMaterialInfo> tmList = this.teachingMaterialManager.getList(tm, null);
        je.setType("success");
        je.setObjList(tmList);
        response.getWriter().print(je.toJSON());
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
        TpCourseResource trsel=new TpCourseResource();
        trsel.setCourseid(Long.parseLong(courseid));
        trsel.setLocalstatus(1);
        List<TpCourseResource> trList=this.tpCourseResourceManager.getList(trsel,null);




        //页数定位
        Integer pageno=1;
        String tpresdetailid=request.getParameter("tpresdetailid");
        if(tpresdetailid!=null&&tpresdetailid.trim().length()>0){
            TpCourseResource tr=new TpCourseResource();
            tr.setCourseid(Long.parseLong(courseid));
            tr.setResstatus(1);
            tr.setResourcetype(1);
            PageResult pageResult=new PageResult();
            pageResult.setPageNo(0);
            pageResult.setPageSize(0);
            pageResult.setOrderBy("  aa.diff_type desc,aa.ctime desc,aa.operate_time desc ");
            List<TpCourseResource>resourceList=this.tpCourseResourceManager.getList(tr,pageResult);
            if(resourceList!=null&&resourceList.size()>0){
                for(int i=0;i<resourceList.size();i++){
                    if(tpresdetailid.equals(resourceList.get(i).getResid().toString())){
                        pageno=i<10?1:(i+1)/10<1?1:i/10+1;
                        break;
                    }
                }
            }
        }


        mp.put("pageno",pageno);

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
        String baseUrl=request.getSession().getAttribute("IP_PROC_NAME")==null?"":request.getSession().getAttribute("IP_PROC_NAME").toString();
        if(this.validateRole(request,UtilTool._ROLE_STU_ID)){ //进入学生首页
            response.sendRedirect(baseUrl+"teachercourse?m=toStudentCourseList");
        }else
            response.sendRedirect(baseUrl+"teachercourse?toTeacherCourseList"); //进入教师首页
    }

    /**
     * 根据ClassId，CourseId得到学科
     *  逻辑说明：验证当前角色是班主任，则查询全部学科，如果是老师，则查询当前的班级学科。 如果都存在，以班主任优先。
     * @param request
     * @throws Exception
     */
    @RequestMapping(params="m=getCourseSubByClsId",method=RequestMethod.POST)
    public void getCourseSubjectByClsId(HttpServletRequest request,HttpServletResponse response)throws Exception{
        String clsid=request.getParameter("classid");
        String termid=request.getParameter("termid");
        String subjectid=request.getParameter("subjectid");
        JsonEntity jsonEntity=new JsonEntity();
        if(clsid==null||clsid.trim().length()<1||termid==null||termid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.toJSON());return;
        }
        //如果是班主任，则查询全部学科
        if(this.validateRole(request,UtilTool._ROLE_CLASSADVISE_ID)){
            // 如果是任课教师，则查询当前学科
            TpCourseClass tcc=new TpCourseClass();
            tcc.setClassid(Integer.parseInt(clsid.trim()));
            tcc.setTermid(termid);
            List<TpCourseClass> tccList=this.tpCourseClassManager.getTpCourseClassByClsTermId(Integer.parseInt(clsid.trim()),termid.trim());
//            if(tccList!=null&&tccList.size()>0){
             jsonEntity.setObjList(tccList);
//            }

        }else if(this.validateRole(request,UtilTool._ROLE_TEACHER_ID)){
            if(subjectid==null||subjectid.trim().length()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().println(jsonEntity.toJSON());return;
            }
            // 如果是任课教师，则查询当前学科
            TpCourseClass tcc=new TpCourseClass();
            tcc.setClassid(Integer.parseInt(clsid.trim()));
            tcc.setTermid(termid);
            List<TpCourseClass> tccList=this.tpCourseClassManager.getTpCourseClassByClsTermId(Integer.parseInt(clsid.trim()),termid.trim());
            if(tccList!=null&&tccList.size()>0){
//                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
//                response.getWriter().print(jsonEntity.toJSON());
//                return;
//            }
                //得到该老师的教授学科
    //            SubjectUser su=new SubjectUser();
    //            su.setUserid(this.logined(request).getRef());
    //            List<SubjectUser> sbUlist=this.subjectUserManager.getList(su,null);
    //            if(sbUlist==null||sbUlist.size()<1){
    //                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
    //                response.getWriter().print(jsonEntity.toJSON());
    //                return;
    //            }
                List<TpCourseClass> tmpTccList=new ArrayList<TpCourseClass>();
                //对比
                for (TpCourseClass tccTmp:tccList){
                    if(tccTmp!=null&&tccTmp.getSubjectid().intValue()==Integer.parseInt(subjectid.trim())){
                        if(!tmpTccList.contains(tccTmp))
                            tmpTccList.add(tccTmp);
                    }
                }
                jsonEntity.setObjList(tmpTccList);
            }
        }else{
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("NO_SERVICE_RIGHT"));
            response.getWriter().print(jsonEntity.toJSON());
            return;
        }
        jsonEntity.setType("success");
        response.getWriter().println(jsonEntity.toJSON());
    }
}
