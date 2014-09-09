package com.school.control.teachpaltform;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.entity.*;
import com.school.entity.teachpaltform.*;
import com.school.entity.teachpaltform.interactive.TpTopicInfo;
import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;
import com.school.entity.teachpaltform.paper.PaperInfo;
import com.school.entity.teachpaltform.paper.StuPaperLogs;
import com.school.entity.teachpaltform.paper.StuPaperQuesLogs;
import com.school.entity.teachpaltform.paper.TpCoursePaper;
import com.school.im1_1.manager.inter._interface.IImInterfaceManager;
import com.school.manager.*;
import com.school.manager.inter.*;
import com.school.manager.inter.resource.IResourceManager;
import com.school.manager.inter.teachpaltform.*;
import com.school.manager.inter.teachpaltform.award.ITpStuScoreLogsManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicThemeManager;
import com.school.manager.inter.teachpaltform.paper.IPaperManager;
import com.school.manager.inter.teachpaltform.paper.IPaperQuestionManager;
import com.school.manager.inter.teachpaltform.paper.IStuPaperQuesLogsManager;
import com.school.manager.inter.teachpaltform.paper.ITpCoursePaperManager;
import com.school.manager.resource.ResourceManager;
import com.school.manager.teachpaltform.*;
import com.school.manager.teachpaltform.award.TpStuScoreLogsManager;
import com.school.manager.teachpaltform.interactive.TpTopicManager;
import com.school.manager.teachpaltform.interactive.TpTopicThemeManager;
import com.school.manager.teachpaltform.paper.PaperManager;
import com.school.manager.teachpaltform.paper.PaperQuestionManager;
import com.school.manager.teachpaltform.paper.StuPaperQuesLogsManager;
import com.school.manager.teachpaltform.paper.TpCoursePaperManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import com.school.control.base.BaseController;

@Controller
@RequestMapping(value="/task")
public class TaskController extends BaseController<TpTaskInfo>{
    private ITpTaskManager tpTaskManager;
    private ITpTaskAllotManager tpTaskAllotManager;
    private IQuestionOptionManager questionOptionManager;
    private ITpCourseManager tpCourseManager;
    private IDictionaryManager dictionaryManager;
    private ITpTopicThemeManager  tpTopicThemeManager;
    private IQuestionAnswerManager questionAnswerManager;
    private IQuestionManager questionManager;
    private ITaskPerformanceManager taskPerformanceManager;
    private IUserManager userManager;
    private ITpOperateManager tpOperateManager;
    private ITpCourseClassManager tpCourseClassManager;
    private ITpGroupManager tpGroupManager;
    private ITpGroupStudentManager tpGroupStudentManager;
    private ITpTopicManager tpTopicManager;
    private ITpCourseQuestionManager tpCourseQuestionManager;
    private ITpCourseResourceManager tpCourseResourceManager;
    private IClassManager classManager;
    private ITpVirtualClassManager tpVirtualClassManager;
    private ITaskSuggestManager taskSuggestManager;
    private ITpCourseTeachingMaterialManager tpCourseTeachingMaterialManager;
    private ISmsManager smsManager;
    private ITpCoursePaperManager tpCoursePaperManager;
    private IPaperManager paperManager;
    private IStuPaperQuesLogsManager stuPaperQuesLogsManager;
    private IGradeManager gradeManager;
    private IResourceManager resourceManager;
    private ITpStuScoreLogsManager tpStuScoreLogsManager;
    private IPaperQuestionManager paperQuestionManager;
    private IImInterfaceManager imInterfaceManager;
    public TaskController(){
        this.gradeManager=this.getManager(GradeManager.class);
        this.resourceManager=this.getManager(ResourceManager.class);
        this.tpCourseTeachingMaterialManager=this.getManager(TpCourseTeachingMaterialManager.class);
        this.tpTaskManager=this.getManager(TpTaskManager.class);
        this.tpTaskAllotManager=this.getManager(TpTaskAllotManager.class);
        this.questionOptionManager=this.getManager(QuestionOptionManager.class);
        this.tpCourseManager=this.getManager(TpCourseManager.class);
        this.dictionaryManager=this.getManager(DictionaryManager.class);
        this.tpTopicThemeManager=this.getManager(TpTopicThemeManager.class);
        this.questionAnswerManager=this.getManager(QuestionAnswerManager.class);
        this.questionManager=this.getManager(QuestionManager.class);
        this.taskPerformanceManager=this.getManager(TaskPerformanceManager.class);
        this.userManager=this.getManager(UserManager.class);
        this.tpOperateManager=this.getManager(TpOperateManager.class);
        this.tpCourseClassManager=this.getManager(TpCourseClassManager.class);
        this.tpGroupManager=this.getManager(TpGroupManager.class);
        this.tpGroupStudentManager = this.getManager(TpGroupStudentManager.class);
        this.tpTopicManager=this.getManager(TpTopicManager.class);
        this.tpCourseQuestionManager=this.getManager(TpCourseQuestionManager.class);
        this.tpCourseResourceManager=this.getManager(TpCourseResourceManager.class);
        this.classManager=this.getManager(ClassManager.class);
        this.tpVirtualClassManager=this.getManager(TpVirtualClassManager.class);
        this.taskSuggestManager=this.getManager(TaskSuggestManager.class);
        this.smsManager=this.getManager(SmsManager.class);
        this.tpCoursePaperManager=this.getManager(TpCoursePaperManager.class);
        this.paperManager=this.getManager(PaperManager.class);
        this.stuPaperQuesLogsManager=this.getManager(StuPaperQuesLogsManager.class);
        this.paperQuestionManager=this.getManager(PaperQuestionManager.class);
        this.tpStuScoreLogsManager=this.getManager(TpStuScoreLogsManager.class);
    }
    /**
     * 根据课题ID，加载任务列表
     * @return
     */
    @RequestMapping(params="toTaskList",method=RequestMethod.GET)
    public ModelAndView toTaskList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        //得到该课题的所有任务，任务完成情况。
        JsonEntity je= new JsonEntity();
        String courseid=request.getParameter("courseid");
        String subjectid=request.getParameter("subjectid");
        String gradeid=request.getParameter("gradeid");
        String materialid=request.getParameter("material_id");
        String addresstype = request.getParameter("addresstype");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        System.out.println("==========================="+TpCourseInfo.class.getResource(""));
        TpCourseInfo tc=new TpCourseInfo();
        if(addresstype!=null&&addresstype!=""){
            tc.setUserid(null);
            tc.setCourseid(null);
            tc.setLocalstatus(null);//正常
        }else{
            tc.setUserid(this.logined(request).getUserid());
            tc.setCourseid(Long.parseLong(courseid));
            tc.setLocalstatus(1);//正常
        }
        List<TpCourseInfo>teacherCourseList=this.tpCourseManager.getTchCourseList(tc, null);
        if(teacherCourseList==null||teacherCourseList.size()<1){
            je.setMsg("找不到指定课题!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //获取当前专题教材
        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(Long.parseLong(courseid));
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);
        if(materialList!=null&&materialList.size()>0)
            subjectid=materialList.get(0).getSubjectid().toString();
        //课题样式
        request.setAttribute("coursename", teacherCourseList.get(0).getCoursename());
        TpCourseInfo tcs= new TpCourseInfo();
        tcs.setUserid(this.logined(request).getUserid());
        tcs.setTermid(teacherCourseList.get(0).getTermid());
        tcs.setLocalstatus(1);
        if(subjectid!=null)
            tcs.setSubjectid(Integer.parseInt(subjectid));
        List<TpCourseInfo>courseList=this.tpCourseManager.getCourseList(tcs, null);
        request.setAttribute("courseList", courseList);


        String termid=teacherCourseList.get(0).getTermid();
        request.setAttribute("courseid", courseid);
        request.setAttribute("termid", termid);
        request.setAttribute("subjectid", subjectid);
        if(gradeid!=null&&!gradeid.toString().equals("0"))
            request.getSession().setAttribute("session_grade",gradeid);
        if(subjectid!=null&&!subjectid.toString().equals("0"))
            request.getSession().setAttribute("session_subject",subjectid);
        if(materialid!=null&&!materialid.toString().equals("0"))
            request.getSession().setAttribute("session_material",materialid);

        //任务库
        List<DictionaryInfo>courselevel=this.dictionaryManager.getDictionaryByType("COURSE_LEVEL");
        request.setAttribute("courselevel",courselevel);
        //SESSION添加专题状态
        request.getSession().setAttribute("coursestate", teacherCourseList.get(0).getLocalstatus());
        return new ModelAndView("/teachpaltform/task/teacher/task-list");
    }

    /**
     * 进入任务元素详情页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=toTaskElementDetial",method=RequestMethod.GET)
    public ModelAndView toTaskElementDetailPage(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String courseid=request.getParameter("courseid");
        String type = request.getParameter("tasktype");
        //资源类型
        List<DictionaryInfo>resourceTypeList=this.dictionaryManager.getDictionaryByType("RES_TYPE");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        request.setAttribute("courseid",courseid);
        request.setAttribute("resType", resourceTypeList);
        request.setAttribute("fileSystemIpPort", UtilTool.utilproperty.getProperty("RESOURCE_FILE_UPLOAD_HEAD"));
        request.setAttribute("nextid", this.resourceManager.getNextId(true));



        if(type.equals("1")){
            //栏目
            IColumnManager columnManager=(ColumnManager)this.getManager(ColumnManager.class);
            EttColumnInfo ettColumnInfo = new EttColumnInfo();
            ettColumnInfo.setRoletype(2);
            List<EttColumnInfo> columnList =columnManager.getEttColumnSplit(ettColumnInfo,null);
            if(columnList!=null&&columnList.size()>0){
                Boolean b= false;
                for(EttColumnInfo obj:columnList){
                    if(obj.getEttcolumnid()==7||obj.getEttcolumnid()==320){
                        b=true;
                    }
                }
                request.setAttribute("sign",b);
            }else{
                request.setAttribute("sign",false);
            }
            request.setAttribute("gradeList", this.gradeManager.getList(null, null));
            return new ModelAndView("/teachpaltform/task/teacher/resource-element-detail");
        }else{
            return new ModelAndView("/teachpaltform/task/teacher/element-detail");
        }
    }

    /**
     * 获取教师任务列表
     * @throws Exception
     */
     @RequestMapping(params="m=ajaxTaskList",method=RequestMethod.POST)
     public void ajaxTaskList(HttpServletRequest request,HttpServletResponse response)throws Exception{
         JsonEntity je = new JsonEntity();
         String courseid=request.getParameter("courseid");
         if(courseid==null||courseid.trim().length()<1){
             je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
             response.getWriter().print(je.toJSON());
             return;
         }
         PageResult p=this.getPageResultParameter(request);
         p.setOrderBy("u.order_idx desc,u.c_time desc");
         TpTaskInfo t=new TpTaskInfo();
         t.setCourseid(Long.parseLong(courseid));
         //查询没被我删除的任务
         t.setSelecttype(1);
         t.setLoginuserid(this.logined(request).getUserid());
         t.setStatus(1);

        //已发布的任务
        List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(t, p);
         if(taskList!=null&&taskList.size()>0){
             for(TpTaskInfo task:taskList){
                 if(task.getTasktype().toString().equals("10")&&(task.getTaskstatus()!="3" && task.getTaskstatus()!="1")){
                     String url=UtilTool.utilproperty.getProperty("GET_ETT_LIVE_ADDRESS");
                     String lessionid=task.getTaskid().toString().replace("-","");
                     HashMap<String,String> signMap = new HashMap();
                     signMap.put("courseName",task.getCoursename().toString());
                     signMap.put("courseId",lessionid);
                     signMap.put("userId",this.logined(request).getUserid().toString());
                     signMap.put("userName",this.logined(request).getUsername());
                     signMap.put("rec","2");
                     signMap.put("srcId","90");
                     signMap.put("timestamp",System.currentTimeMillis()+"");
                     String signture = UrlSigUtil.makeSigSimple("getTutorUrl.do",signMap,"*ETT#HONER#2014*");
                     signMap.put("sign",signture);
                     JSONObject jsonObject = UtilTool.sendPostUrl(url,signMap,"utf-8");
                     int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
                     if(type==1){
                         String liveurl= jsonObject.containsKey("data")?jsonObject.getString("data"):"";
                         if(liveurl!=null&&liveurl.trim().length()>0)
                             task.setLiveaddress(java.net.URLDecoder.decode(liveurl,"UTF-8"));
                     }
                 }
             }
         }
        p.setList(taskList);
        je.setPresult(p);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * 获取任务列表
     * @throws Exception
     */
    @RequestMapping(params="toQueryLiveList",method=RequestMethod.POST)
    public void toQueryLiveList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");
        if(courseid==null||courseid.trim().length()<1||taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo t=new TpTaskInfo();
        t.setCourseid(Long.parseLong(courseid));
        t.setTaskid(Long.parseLong(taskid));
        //查询没被我删除的任务
        t.setSelecttype(1);
        t.setLoginuserid(this.logined(request).getUserid());
        t.setStatus(1);

        //已发布的任务
        List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(t, null);
        je.setObjList(taskList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * 修改任务排序
     * @throws Exception
     */
    @RequestMapping(params="m=doUpdOrderIdx",method=RequestMethod.POST)
    public void doUpdOrderIdx(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String taskid=request.getParameter("taskid");
        String courseid=request.getParameter("courseid");
        String orderix=request.getParameter("orderidx");
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(orderix==null||orderix.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo tmpTask=new TpTaskInfo();
        tmpTask.setCourseid(Long.parseLong(courseid));
        //查询没被我删除的任务
        tmpTask.setSelecttype(1);
        tmpTask.setLoginuserid(this.logined(request).getUserid());
        tmpTask.setStatus(1);
        tmpTask.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo>tmpTaskList=this.tpTaskManager.getTaskReleaseList(tmpTask,null);
        if(tmpTaskList==null||tmpTaskList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }
        tmpTask=tmpTaskList.get(0);

        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();

        TpTaskInfo t=new TpTaskInfo();
        t.setCourseid(Long.parseLong(courseid));
        //查询没被我删除的任务
        t.setSelecttype(1);
        t.setLoginuserid(this.logined(request).getUserid());
        t.setStatus(1);
        PageResult pageResult=new PageResult();
        pageResult.setOrderBy("u.order_idx desc");
        pageResult.setPageNo(0);
        pageResult.setPageSize(0);
        //1 2 3 4 5 6 7 8 9 10
        //已发布的任务
        Integer descIdx=Integer.parseInt(orderix);
        List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(t,pageResult);

        Integer objIdx=descIdx;
        //0是调至最后获取最大索引
        if(descIdx==0)
            descIdx=taskList.get(0).getOrderidx()+1;
        //descIdx=descIdx-1<0?1:descIdx-1;

        if(taskList!=null&&taskList.size()>0){
            if(tmpTask.getOrderidx()>descIdx){  //从7往3调
                for(TpTaskInfo task:taskList){
                    if(task.getOrderidx()>=descIdx&&task.getOrderidx()<tmpTask.getOrderidx()){
                        System.out.println("orderidx:" + task.getOrderidx());
                        TpTaskInfo upd=new TpTaskInfo();
                        upd.setTaskid(task.getTaskid());
                        upd.setOrderidx(task.getOrderidx()+1);
                        sql=new StringBuilder();
                        objList=this.tpTaskManager.getUpdateSql(upd,sql);
                        if(sql!=null&&objList!=null){
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }

                    }
                }
            }else if(tmpTask.getOrderidx()<descIdx){ //从1往9调
                objIdx=objIdx-1<=0?1:objIdx-1;
                for(TpTaskInfo task:taskList){
                    if(task.getOrderidx()>tmpTask.getOrderidx()&&task.getOrderidx()<descIdx){
                        TpTaskInfo upd=new TpTaskInfo();
                        upd.setTaskid(task.getTaskid());
                        upd.setOrderidx(task.getOrderidx()-1);
                        sql=new StringBuilder();
                        objList=this.tpTaskManager.getUpdateSql(upd,sql);
                        if(sql!=null&&objList!=null){
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                }
            }
        }

        TpTaskInfo upd=new TpTaskInfo();
        upd.setTaskid(tmpTask.getTaskid());
        if(orderix.equals("0"))
            upd.setOrderidx(descIdx-1<=0?1:descIdx-1);
        else
            upd.setOrderidx(objIdx);
        sql=new StringBuilder();
        objList=this.tpTaskManager.getUpdateSql(upd,sql);
        if(sql!=null&&objList!=null){
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }
        if(sqlListArray.size()>0&&objListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlListArray,objListArray);
            if(flag){
                je.setType("success");
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 获取学生任务列表
     * @throws Exception
     */
    @RequestMapping(params="m=ajaxStuTaskList",method=RequestMethod.POST)
    public void ajaxStuTaskList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        PageResult p=this.getPageResultParameter(request);
        p.setOrderBy("t.order_idx desc ");
        TpTaskInfo t=new TpTaskInfo();
        t.setCourseid(Long.parseLong(courseid));
        t.setUserid(this.logined(request).getUserid());
        // 学生任务
        List<TpTaskInfo>taskList=this.tpTaskManager.getListbyStu(t, p);
        if(taskList!=null&&taskList.size()>0){
            for(TpTaskInfo task:taskList){
                if(task.getTasktype()==3){
                    QuestionOption questionOptions=new QuestionOption();
                    questionOptions.setQuestionid(task.getTaskvalueid());
                    PageResult pp = new PageResult();
                    pp.setOrderBy("u.option_type");
                    pp.setPageNo(0);
                    pp.setPageSize(0);
                    List<QuestionOption>questionOptionList=this.questionOptionManager.getList(questionOptions,pp);
                    task.setQuestionOptionList(questionOptionList);

                    QuestionInfo q=new QuestionInfo();
                    q.setQuestionid(task.getTaskvalueid());
                    List<QuestionInfo>qList=this.questionManager.getList(q,null);
                    if(qList==null||qList.size()<1){
                        je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
                        response.getWriter().print(je.toJSON());
                        return;
                    }
                    task.setRightAnswerList(qList);

                    /*选择题答案*/
                    if(qList.get(0).getQuestiontype()==3||qList.get(0).getQuestiontype()==4){
                        QuestionOption qo=new QuestionOption();
                        qo.setIsright(1);
                        qo.setQuestionid(q.getQuestionid());
                        List<QuestionOption>questionOptionList1=this.questionOptionManager.getList(qo,null);
                        task.setRightOptionAnswerList(questionOptionList1);
                    }
                }else if(task.getTasktype()==2){
                    TpTopicThemeInfo tt=new TpTopicThemeInfo();
                    tt.setTopicid(task.getTaskvalueid());
                    tt.setLoginuserref(this.logined(request).getRef());
                    tt.setCourseid(task.getCourseid());
                    List<TpTopicThemeInfo>tpTopicThemeInfoList=this.tpTopicThemeManager.getList(tt,null);
                    task.setTpTopicThemeInfoList(tpTopicThemeInfoList);
                }else if(task.getTasktype()==10){
                    if(task.getTaskstatus()!="3" && task.getTaskstatus()!="1"){
                        String url=UtilTool.utilproperty.getProperty("GET_ETT_LIVE_ADDRESS");
                        String lessionid=task.getTaskid().toString().replace("-","");
                        HashMap<String,String> signMap = new HashMap();
                        signMap.put("courseName",task.getCoursename().toString());
                        signMap.put("courseId",lessionid);
                        signMap.put("userId",this.logined(request).getUserid().toString());
                        signMap.put("userName",this.logined(request).getUsername());
                        signMap.put("rec","3");
                        signMap.put("srcId","90");
                        signMap.put("timestamp",System.currentTimeMillis()+"");
                        String signture = UrlSigUtil.makeSigSimple("getTutorUrl.do",signMap,"*ETT#HONER#2014*");
                        signMap.put("sign",signture);
                        JSONObject jsonObject = UtilTool.sendPostUrl(url,signMap,"utf-8");
                        int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
                        if(type==1){
                            String liveurl= jsonObject.containsKey("data")?jsonObject.getString("data"):"";
                            if(liveurl!=null&&liveurl.trim().length()>0)
                                task.setLiveaddress(java.net.URLDecoder.decode(liveurl,"UTF-8"));
                        }
                    }
                }
                //获取答题记录和心得
                QuestionAnswer qa=new QuestionAnswer();
                qa.setTaskid(task.getTaskid());
                qa.setUserid(this.logined(request).getRef());
                List<QuestionAnswer>qaList=this.questionAnswerManager.getList(qa, null);
                task.setQuestionAnswerList(qaList);

                //学习时间
                TaskPerformanceInfo tp=new TaskPerformanceInfo();
                tp.setTaskid(task.getTaskid());
                tp.setUserid(this.logined(request).getRef());
                tp.setCourseid(task.getCourseid());
                List<TaskPerformanceInfo>taskPerformanceInfoList=this.taskPerformanceManager.getList(tp,null);
                task.setTaskPerformanceList(taskPerformanceInfoList);
            }
        }
        p.setList(taskList);
        je.setPresult(p);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 获取未完成任务学生列表
     * @throws Exception
     */
    @RequestMapping(params="m=ajaxNoCompleteTaskUser",method=RequestMethod.POST)
    public void ajaxNoCompleteTaskStuList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");
        if(courseid==null||courseid.trim().length()<1||
                taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo t=new TpTaskInfo();
        t.setCourseid(Long.parseLong(courseid));
        t.setTaskid(Long.parseLong(taskid));
        t.setUserid(this.logined(request).getUserid());
        List<UserInfo>stuList=this.userManager.getUserNotCompleteTask(t.getTaskid(),this.logined(request).getUserid(),null,"1");
        je.setType("success");//
        je.setObjList(stuList);//
        response.getWriter().print(je.toJSON());
    }

    /**
     * 获取任务列表
     * @throws Exception
     */
    @RequestMapping(params="m=ajaxTaskBankList",method=RequestMethod.POST)
    public void ajaxTaskBankList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String tasktype=request.getParameter("tasktype");
        String cloudtype=request.getParameter("cloudtype");

        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        PageResult p=this.getPageResultParameter(request);
        p.setOrderBy("u.cloud_status desc,u.c_time desc");
        if(p.getPageNo()==0)
            p.setPageNo(1);
        TpTaskInfo t=new TpTaskInfo();
        t.setCourseid(Long.parseLong(courseid));
        //查询没被我删除的任务
        t.setSelecttype(1);
        t.setLoginuserid(this.logined(request).getUserid());
        t.setStatus(1);

        if(tasktype!=null&&tasktype.length()>0){
            if(tasktype.indexOf("ques_")!=-1)
                t.setQuestiontype(tasktype.substring(tasktype.lastIndexOf("_") + 1));
            else{
                t.setTasktype(Integer.parseInt(tasktype));
            }
        }
        if(cloudtype!=null&&cloudtype.length()>0)
            t.setCloudtype(Integer.parseInt(cloudtype));


        //已发布的任务
        List<TpTaskInfo>taskList=this.tpTaskManager.getList(t, p);
        //任务下的试题
        if(taskList!=null&&taskList.size()>0){
            for(TpTaskInfo task :taskList){
                if(task.getTasktype()==3){
                    QuestionOption questionOptions=new QuestionOption();
                    questionOptions.setQuestionid(task.getTaskvalueid());
                    PageResult pp = new PageResult();
                    pp.setOrderBy("u.option_type");
                    pp.setPageNo(0);
                    pp.setPageSize(0);
                    List<QuestionOption>questionOptionList=this.questionOptionManager.getList(questionOptions,pp);
                    task.setQuestionOptionList(questionOptionList);
                }
            }
        }
        p.setList(taskList);
        je.setPresult(p);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * 删除任务
     * @throws Exception
     */
    @RequestMapping(params="m=doDelTask",method=RequestMethod.POST)
    public void doDelTask(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String taskid=request.getParameter("taskid");
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo t=new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo>taskList=this.tpTaskManager.getList(t, null);
        if(taskList==null||taskList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }

        //批量操作记录
        List<List<Object>> objListArray=new ArrayList<List<Object>>();
        List<String> sqlStrList=new ArrayList<String>();
        StringBuilder sql=null;
        List<Object>objList=null;




        TpTaskInfo tmpTask=taskList.get(0);

        TpTaskInfo sel=new TpTaskInfo();
        sel.setCourseid(tmpTask.getCourseid());
        //查询没被我删除的任务
        sel.setSelecttype(1);
        sel.setLoginuserid(this.logined(request).getUserid());
        sel.setStatus(1);

        //1 2 3 4 5
        //已发布的任务
        List<TpTaskInfo>taskReleaseList=this.tpTaskManager.getTaskReleaseList(sel, null);
        Integer orderIdx=tmpTask.getOrderidx();
        if(taskReleaseList!=null&&taskReleaseList.size()>0){
            for(TpTaskInfo task:taskReleaseList){
                if(task.getOrderidx()!=null){
                    if(task.getOrderidx()>orderIdx){
                        TpTaskInfo upd=new TpTaskInfo();
                        upd.setTaskid(task.getTaskid());
                        upd.setOrderidx(task.getOrderidx()-1);
                        sql=new StringBuilder();
                        objList=this.tpTaskManager.getUpdateSql(upd,sql);
                        if(sql!=null&&sql.toString().trim().length()>0){
                            objListArray.add(objList);
                            sqlStrList.add(sql.toString());
                        }
                    }
                }
            }
        }


        TpTaskAllotInfo ta=new TpTaskAllotInfo();
        ta.setTaskid(tmpTask.getTaskid());
        ta.setCourseid(tmpTask.getCourseid());
        sql=new StringBuilder();
        objList=this.tpTaskAllotManager.getDeleteSql(ta,sql);
        if(objList!=null&&sql!=null){
            sqlStrList.add(sql.toString());
            objListArray.add(objList);
        }
        QuestionAnswer qa=new QuestionAnswer();
        qa.setTaskid(tmpTask.getTaskid());
        qa.setCourseid(tmpTask.getCourseid());
        sql=new StringBuilder();
        objList=this.questionAnswerManager.getDeleteSql(qa,sql);
        if(objList!=null&&sql!=null){
            sqlStrList.add(sql.toString());
            objListArray.add(objList);
        }

        if(tmpTask.getTaskid()>0){
            /*TpOperateInfo to=new TpOperateInfo();
            to.setRef(this.tpOperateManager.getNextId(true));
            to.setCuserid(this.logined(request).getUserid());
            to.setCourseid(tmpTask.getCourseid());
            to.setTargetid(tmpTask.getTaskid());             //新建任务ID
            to.setOperatetype(1);              //删除
            to.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_TASK.getValue());                 //专题任务
            sql=new StringBuilder();
            objList=this.tpOperateManager.getSaveSql(to,sql);
            if(objList!=null&&sql!=null){
                sqlStrList.add(sql.toString());
                objListArray.add(objList);
            }

            //操作日志
            sql=new StringBuilder();
            objList=this.tpOperateManager.getAddOperateLog(this.logined(request).getRef(),"TP_OPERATE_INFO", to.getTargetid().toString(),null,null,"DELETE"
                    ,"增加操作记录",sql);
            if(sql!=null&&sql.toString().trim().length()>0){
                objListArray.add(objList);
                sqlStrList.add(sql.toString());
            }*/
        }else{
            TpTaskInfo del=new TpTaskInfo();
            del.setTaskid(tmpTask.getTaskid());
            //t.setStatus(2); //修改本地状态为已删除
            //t.setOrderidx(-1);
            sql=new StringBuilder();
            objList=this.tpTaskManager.getDeleteSql(del, sql);
            if(sql!=null&&sql.toString().trim().length()>0){
                objListArray.add(objList);
                sqlStrList.add(sql.toString());
            }
        }
        //操作日志
       /* sql=new StringBuilder();
        objList=this.tpOperateManager.getAddOperateLog(this.logined(request).getRef(),"TP_TASK_INFO", t.getTaskid().toString(),null,null,"UPDATE"
                ,"修改本地状态",sql);
        if(sql!=null&&sql.toString().trim().length()>0){
            objListArray.add(objList);
            sqlStrList.add(sql.toString());
        })*/

        if(sqlStrList.size()>0&&objListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlStrList,objListArray);
            if(flag){
                je.setType("success");
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * 添加任务
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toAddTask",method=RequestMethod.GET)
    public ModelAndView toAddTask(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je =new JsonEntity();
        String courseid=request.getParameter("courseid");
        String termid=request.getParameter("termid");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg("异常错误,系统未获取到课题标识!");
            je.getAlertMsgAndBack();
            return null;
        }
        //任务类型
        List<DictionaryInfo>tasktypeList=this.dictionaryManager.getDictionaryByType("TP_TASK_TYPE");
        //与该课题关联的班级
        TpCourseClass c=new TpCourseClass();
        c.setCourseid(Long.parseLong(courseid));
        List<TpCourseClass>courseclassList=this.tpCourseClassManager.getList(c, null);
        if(courseclassList==null||courseclassList.size()<1){
            je.setMsg("异常错误，未获取到该课题的班级信息!请设置后操作任务!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        String subjectid=null,gradeid=null;

        //获取当前专题教材
        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(Long.parseLong(courseid));
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);
        if(materialList!=null&&materialList.size()>0){
            subjectid=materialList.get(0).getSubjectid().toString();
            //gradeid=materialList.get(0).getGradeid().toString();
        }



        //验证是否所有学生都已有小组
		/*for (TpCourseClass cc : courseclassList) {
			if(cc.getClassid()!=null&&cc.getClasstype().intValue()==1){
				//获取班级学生，增加默认小组
				TpGroupInfo tmpg=new TpGroupInfo();
				tmpg.setClassid(cc.getClassid());
				//tmpg.setTeacherid(t.getTeacherId());
				tmpg.setCuserid(this.logined(request).getUserid());
				List<TpGroupInfo>tmpgList=this.tpGroupManager.getList(tmpg, null);
				
				ClassUser tmpu=new ClassUser();
				tmpu.setClassid(cc.getClassid());
				tmpu.setRelationtype("学生");
				List<ClassUser>tmpuList=this.classUserManager.getList(tmpu, null);
				
				if((tmpgList==null||tmpgList.size()<1)&&(tmpuList!=null&&tmpuList.size()>0)){
					Map<String,ClassUser>tmpmap=new HashMap<String, ClassUser>();
					for (ClassUser classUser : tmpuList) {
						if(!tmpmap.containsKey(classUser.getUserid()))
							tmpmap.put(classUser.getUserid(), classUser);
					}
					
					List<String>sqlList=new ArrayList<String>();
					List<List<Object>>objListArray=new ArrayList<List<Object>>();
					StringBuilder sql=null;
					List<Object>objList=null;
					Long nextGroupId=this.tpGroupManager.getNextId(true);
                    TpGroupInfo saveg=new TpGroupInfo();
					saveg.setGroupid(nextGroupId);
					saveg.setClassid(cc.getClassid());
					saveg.setGroupname("全部");
					saveg.setCuserid(this.logined(request).getUserid());
					saveg.setTermid(courseclassList.get(0).getTermid());
					saveg.setClasstype(1);
					sql=new StringBuilder();
					objList=this.tpGroupManager.getSaveSql(saveg, sql);
					if(sql!=null&&objList!=null){
						sqlList.add(sql.toString()); 
						objListArray.add(objList);
					}
					for (Map.Entry<String,ClassUser>currentMap : tmpmap.entrySet()) {
						ClassUser cuser=currentMap.getValue();
						TpGroupStudent savegs=new TpGroupStudent();
						savegs.setUserid(cuser.getUid());
						savegs.setGroupid(nextGroupId);
						sql=new StringBuilder();
						objList=this.tpGroupStudentManager.getSaveSql(savegs, sql);
						if(sql!=null&&objList!=null){
							sqlList.add(sql.toString());
							objListArray.add(objList);
						} 
					}
					
					if(objListArray.size()>0&&sqlList.size()>0){
						this.tpTaskManager.doExcetueArrayProc(sqlList, objListArray);
					} 
				}
				//班级内人数
				ClassUser cu=new ClassUser();
				cu.getClassinfo().setClassid(cc.getClassid());
				cu.setRelationtype("学生");
				int count=0;
				List<ClassUser>cuList=this.classUserManager.getList(cu, null);
				//小组内人数
				if(cuList!=null&&cuList.size()>0)
					count=cuList.size();
				TpGroupStudent gs=new TpGroupStudent();
				gs.setClassid(cc.getClassid());
				gs.setUserid(this.logined(request).getUserid());
				List<TpGroupStudent>gsList=this.tpGroupStudentManager.getGroupStudentByClass(gs, null);
				if(count>0&&gsList!=null&&gsList.size()>0){
					if(count>gsList.size()){
						je.setMsg("提示："+cc.getClassname()+"尚有学生未设置小组!请设置!");
						response.getWriter().print(je.getAlertMsgAndSendRedirect("group?m=toGroupManager&termid="+termid));
						return null;  
					}
				}else{
					je.setMsg("提示："+cc.getClassname()+"小组人员为空!请设置!");
					response.getWriter().print(je.getAlertMsgAndSendRedirect("group?m=toGroupManager&termid="+termid));
					return null; 
				}
			}else{
                //获取班级学生，增加默认小组
                TpGroupInfo tmpg=new TpGroupInfo();
                tmpg.setClassid(cc.getClassid());
                //tmpg.setTeacherid(t.getTeacherId());
                tmpg.setCuserid(this.logined(request).getUserid());
                List<TpGroupInfo>tmpgList=this.tpGroupManager.getList(tmpg, null);

                TpVirtualClassStudent tmpu=new TpVirtualClassStudent();
                tmpu.setVirtualclassid(cc.getClassid());
                //tmpu.setRelationtype("学生");
                List<TpVirtualClassStudent>tmpuList=this.tpVirtualClassStudentManager.getList(tmpu, null);

                if((tmpgList==null||tmpgList.size()<1)&&(tmpuList!=null&&tmpuList.size()>0)){
                    Map<Integer,TpVirtualClassStudent>tmpmap=new HashMap<Integer, TpVirtualClassStudent>();
                    for (TpVirtualClassStudent classUser : tmpuList) {
                        if(!tmpmap.containsKey(classUser.getUserid()))
                            tmpmap.put(classUser.getUserid(), classUser);
                    }

                    List<String>sqlList=new ArrayList<String>();
                    List<List<Object>>objListArray=new ArrayList<List<Object>>();
                    StringBuilder sql=null;
                    List<Object>objList=null;
                    Long nextGroupId=this.tpGroupManager.getNextId(true);
                    TpGroupInfo saveg=new TpGroupInfo();
                    saveg.setGroupid(nextGroupId);
                    saveg.setClassid(cc.getClassid());
                    saveg.setGroupname("全部");
                    saveg.setCuserid(this.logined(request).getUserid());
                    saveg.setTermid(courseclassList.get(0).getTermid());
                    saveg.setClasstype(1);
                    sql=new StringBuilder();
                    objList=this.tpGroupManager.getSaveSql(saveg, sql);
                    if(sql!=null&&objList!=null){
                        sqlList.add(sql.toString());
                        objListArray.add(objList);
                    }
                    for (Map.Entry<Integer,TpVirtualClassStudent>currentMap : tmpmap.entrySet()) {
                        TpVirtualClassStudent cuser=currentMap.getValue();
                        TpGroupStudent savegs=new TpGroupStudent();
                        savegs.setUserid(cuser.getUserid());
                        savegs.setGroupid(nextGroupId);
                        sql=new StringBuilder();
                        objList=this.tpGroupStudentManager.getSaveSql(savegs, sql);
                        if(sql!=null&&objList!=null){
                            sqlList.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }

                    if(objListArray.size()>0&&sqlList.size()>0){
                        this.tpTaskManager.doExcetueArrayProc(sqlList, objListArray);
                    }
                }
                //班级内人数
                TpVirtualClassStudent cu=new TpVirtualClassStudent();
                cu.setVirtualclassid(cc.getClassid());
                //cu.setRelationtype("学生");
                int count=0;
                List<TpVirtualClassStudent>cuList=this.tpVirtualClassStudentManager.getList(cu, null);
                //小组内人数
                if(cuList!=null&&cuList.size()>0)
                    count=cuList.size();
                TpGroupStudent gs=new TpGroupStudent();
                gs.setClassid(cc.getClassid());
                gs.setUserid(this.logined(request).getUserid());
                List<TpGroupStudent>gsList=this.tpGroupStudentManager.getGroupStudentByClass(gs, null);
                if(count>0&&gsList!=null&&gsList.size()>0){
                    if(count>gsList.size()){
                        je.setMsg("提示："+cc.getClassname()+"尚有学生未设置小组!请设置!");
                        response.getWriter().print(je.getAlertMsgAndSendRedirect("group?m=toGroupManager&termid="+termid));
                        return null;
                    }
                }else{
                    je.setMsg("提示："+cc.getClassname()+"小组人员为空!请设置!");
                    response.getWriter().print(je.getAlertMsgAndSendRedirect("group?m=toGroupManager&termid="+termid));
                    return null;
                }
            }
		} */
        //微视频
        TpCourseResource tr=new TpCourseResource();
        tr.setCourseid(Long.parseLong(courseid));
        tr.setDifftype(1);
        List<TpCourseResource>micList=this.tpCourseResourceManager.getList(tr,null);
        if(micList!=null&&micList.size()>0)
            mp.put("hasVideo",1);


		//小组
		TpGroupInfo g=new TpGroupInfo();
		//g.setCuserid(this.logined(request).getUserid());
        g.setTermid(courseclassList.get(0).getTermid());
        g.setSubjectid(Integer.parseInt(subjectid));
        List<TpGroupInfo>groupList=this.tpGroupManager.getList(g, null);
        mp.put("tasktypeList", tasktypeList);
        mp.put("courseclassList",courseclassList);
        mp.put("groupList", groupList);
        mp.put("termid", termid);
        mp.put("gradeid", request.getSession().getAttribute("session_grade"));
        mp.put("subjectid",request.getSession().getAttribute("session_subject"));
        mp.put("materialid",request.getSession().getAttribute("session_material"));
        TpCourseQuestion cq=new TpCourseQuestion();
        cq.setCourseid(Long.parseLong(courseid));
        Integer objectiveQuesCount=this.tpCourseQuestionManager.getObjectiveQuesCount(cq);
        mp.put("objectiveQuesCount", objectiveQuesCount);   //专题下客观题数量
        return new ModelAndView("/teachpaltform/task/teacher/task-add",mp);
    }

    /**
     * 获取问题类型
     * @throws Exception
     */
    @RequestMapping(params="toQryQuestionType",method=RequestMethod.POST)
    public void toQryQuestionType(HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        List<DictionaryInfo>questiontypeList=this.dictionaryManager.getDictionaryByType("TP_QUESTION_TYPE");
        je.setObjList(questiontypeList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 任务添加
     * @throws Exception
     */
    @RequestMapping(params="doSubAddTask",method=RequestMethod.POST)
    public void doSubAddTask(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String tasktype=request.getParameter("tasktype");
        String taskname=request.getParameter("taskname");
        String taskvalueid=request.getParameter("taskvalueid");
        String taskremark=request.getParameter("taskremark");
        String quesnum=request.getParameter("quesnum");
        String clstype=request.getParameter("clstype");
        if(tasktype==null||tasktype.trim().length()<1
            //||StringUtils.isBlank(taskname
                ){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String courseid=request.getParameter("courseid");
        String questype=request.getParameter("questype");
        String[]criteriaArray=request.getParameterValues("taskcri");
        //小组
        String[]groupArray=request.getParameterValues("groupArray");
        //班级
        String[]clsArray=request.getParameterValues("clsArray");
        String[]bClsArray=request.getParameterValues("btimeClsArray");
        String[]eClsArray=request.getParameterValues("etimeClsArray");


        if(StringUtils.isBlank(courseid)){
            je.setMsg("异常错误,未获取到课题标识!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if((groupArray==null||groupArray.length<1)
                &&(clsArray==null||clsArray.length<1)){
            je.setMsg("异常错误,未获取到任务对象!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(!(StringUtils.isNotBlank(questype)&&questype.equals("5"))&&criteriaArray.length<1){
            je.setMsg("异常错误,未获取到任务完成标准!");
            response.getWriter().print(je.toJSON());
            return;
        }
        StringBuilder sql=null;
        List<Object>objList=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        TpTaskInfo ta=new TpTaskInfo();


        TpCourseInfo courseInfo=new TpCourseInfo();
        courseInfo.setCourseid(Long.parseLong(courseid));
        List<TpCourseInfo>courseList=this.tpCourseManager.getList(courseInfo,null);
        if(courseList==null||courseList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpCourseInfo tmpCourse=courseList.get(0);


        //课后作业 3
        if(tasktype.toString().equals("3")){
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("异常错误，系统未获取到试题标识!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpCourseQuestion q=new TpCourseQuestion();
            q.setQuestionid(Long.parseLong(taskvalueid));
            List<TpCourseQuestion>quesList=this.tpCourseQuestionManager.getList(q,null);
            if(quesList==null||quesList.size()<1){
                je.setMsg("提示：当前试题已不存在或删除!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));
        }else if(tasktype.toString().equals("2")){//论题
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("异常错误，系统未获取到论题标识!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpTopicInfo i=new TpTopicInfo();
            i.setTopicid(Long.parseLong(taskvalueid));
            List<TpTopicInfo>iList=this.tpTopicManager.getList(i, null);
            if(iList==null||iList.size()<1){
                je.setMsg("提示：当前论题已不存在或删除，请刷新页面后重试!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));
        }else if(tasktype.toString().equals("1")){//资源
            String resourcetype=request.getParameter("resourcetype");
            resourcetype=resourcetype==null||resourcetype.length()<1?"1":resourcetype;
            if(resourcetype.equals("1")){
                if(taskvalueid==null||taskvalueid.trim().length()<1){
                    je.setMsg("异常错误，系统未获取到资源标识!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                TpCourseResource t=new TpCourseResource();
                t.setResid(Long.parseLong(taskvalueid));
                List<TpCourseResource>resList=this.tpCourseResourceManager.getList(t, null);
                if(resList==null||resList.size()<1){
                    je.setMsg("提示：当前资源已不存在或删除，请刷新页面后重试!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                ta.setTaskvalueid(Long.parseLong(taskvalueid));
                ta.setResourcetype(1);


                TpCourseResource tt=new TpCourseResource();
                tt.setCourseid(Long.parseLong(courseid));
                tt.setResid(Long.parseLong(taskvalueid));
                List<TpCourseResource>tpCourseResourceList=this.tpCourseResourceManager.getList(tt,null);
                if(tpCourseResourceList==null||tpCourseResourceList.size()<1){
                    je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
                    response.getWriter().print(je.toJSON());
                    return;
                }
                if(tpCourseResourceList.get(0).getResourcetype().equals(2)){
                    tt.setRef(tpCourseResourceList.get(0).getRef());
                    tt.setResourcetype(1);
                    sql=new StringBuilder();
                    objList=this.tpCourseResourceManager.getUpdateSql(tt,sql);
                    if(objList!=null&&sql!=null&&sql.length()>0){
                        objListArray.add(objList);
                        sqlListArray.add(sql.toString());
                    }
                }
            }else{
                String remotetype=request.getParameter("remotetype");
                String resourcename=request.getParameter("resourcename");
                ta.setTaskvalueid(Long.parseLong(taskvalueid));
                ta.setResourcetype(2);
                ta.setRemotetype(Integer.parseInt(remotetype));
                ta.setResourcename(resourcename);
            }
        }else if(tasktype.toString().equals("4")){//成卷测试
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("异常错误，系统未获取到试卷标识!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpCoursePaper tpCoursePaper=new TpCoursePaper();
            tpCoursePaper.setPaperid(Long.parseLong(taskvalueid));
            List<TpCoursePaper>iList=this.tpCoursePaperManager.getList(tpCoursePaper,null);
            if(iList==null||iList.size()<1){
                je.setMsg("提示：当前试卷已不存在或删除，请刷新页面后重试!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));
        }else if(tasktype.toString().equals("5")){//自主测试
            //添加试卷
            Long paperid=this.paperManager.getNextId(true);
            PaperInfo p=new PaperInfo();
            p.setPaperid(paperid);
            p.setPapername("自主测试");
            p.setCuserid(this.logined(request).getUserid());
            p.setPapertype(PaperInfo.PAPER_TYPE.FREE.getValue());
            sql=new StringBuilder();
            objList=this.paperManager.getSaveSql(p,sql);
            if(objList!=null&&sql!=null){
                objListArray.add(objList);
                sqlListArray.add(sql.toString());
            }

            //添加数据与专题关联
            TpCoursePaper tpCoursePaper=new TpCoursePaper();
            tpCoursePaper.setPaperid(paperid);
            tpCoursePaper.setCourseid(Long.parseLong(courseid));
            sql=new StringBuilder();
            objList=this.tpCoursePaperManager.getSaveSql(tpCoursePaper,sql);
            if(objList!=null&&sql!=null){
                objListArray.add(objList);
                sqlListArray.add(sql.toString());
            }
            ta.setTaskvalueid(paperid);
        }else if(tasktype.toString().equals("6")){//微视频测试
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("异常错误，系统未获取到微视频标识!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpCourseResource tpCourseResource=new TpCourseResource();
            tpCourseResource.setResid(Long.parseLong(taskvalueid));
            tpCourseResource.setCourseid(Long.parseLong(courseid));
            List<TpCourseResource>iList=this.tpCourseResourceManager.getList(tpCourseResource,null);
            if(iList==null||iList.size()<1){
                je.setMsg("提示：当前资源已不存在或删除，请刷新页面后重试!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));
        }else if(tasktype.toString().equals("10")){//直播课
            if(taskname==null||taskname.trim().length()<1){
                je.setMsg("异常错误，系统未获取到直播主题!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.valueOf(1));
            ta.setTaskname(taskname);
        }
        /**
         *查询出当前专题有效的任务个数，排序用
         */

        TpTaskInfo t=new TpTaskInfo();
        t.setCourseid(Long.parseLong(courseid));
        //查询没被我删除的任务
        t.setSelecttype(1);
        t.setLoginuserid(this.logined(request).getUserid());
        t.setStatus(1);

        //已发布的任务
        List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(t, null);
        Integer orderIdx=1;
        if(taskList!=null&&taskList.size()>0)
            orderIdx+=taskList.size();

        //添加任务
        Long tasknextid=this.tpTaskManager.getNextId(true);
        ta.setTaskid(tasknextid);
        //ta.setTaskname(taskname);
        ta.setTasktype(Integer.parseInt(tasktype));
        ta.setCourseid(Long.parseLong(courseid));
        ta.setCuserid(this.logined(request).getRef());
        ta.setCriteria(Integer.parseInt(criteriaArray[0]));
        ta.setOrderidx(orderIdx);
        if(taskremark!=null)
            ta.setTaskremark(taskremark);
        if(quesnum!=null&&quesnum.trim().length()>0)
            ta.setQuesnum(Integer.parseInt(quesnum));
        else
            ta.setQuesnum(10);
        sql=new StringBuilder();
        objList=this.tpTaskManager.getSaveSql(ta, sql);
        if(objList!=null&&sql!=null&&sql.length()>0){
            objListArray.add(objList);
            sqlListArray.add(sql.toString());
        }

        //添加任务对象 小组
        if(groupArray!=null&&groupArray.length>0){
            for (int i=0;i<groupArray.length;i++) {
                TpTaskAllotInfo tal=new TpTaskAllotInfo();
                tal.setTaskid(tasknextid);
                tal.setUsertype(2);
                tal.setUsertypeid(Long.parseLong(groupArray[i]));
                tal.setBtime(UtilTool.StringConvertToDate(bClsArray[i]));
                tal.setEtime(UtilTool.StringConvertToDate(eClsArray[i]));
                tal.setCuserid(this.logined(request).getRef());
                //tal.setCriteria(criteriaArray[0]);
                tal.setCourseid(Long.parseLong(courseid));
                sql=new StringBuilder();
                objList=this.tpTaskAllotManager.getSaveSql(tal, sql);
                if(objList!=null&&sql!=null&&sql.length()>0){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }
        //添加任务对象 班级
        if(clsArray!=null&&clsArray.length>0){
            if(clstype==null||clstype.trim().length()<1){
                je.setMsg("异常错误!系统未获取到班级类型!");
                response.getWriter().print(je.toJSON());
                return;
            }
            String[]classTypeArray=clstype.split(",");
            for (int i=0;i<clsArray.length;i++) {
                TpTaskAllotInfo tal=new TpTaskAllotInfo();
                //验证班级类型
                if(classTypeArray[i].equals("1")){
                    /*ClassInfo c=new ClassInfo();
                    c.setClassid(Integer.parseInt(clsArray[i]));
                    List<ClassInfo>clsList=this.classManager.getList(c,null);*/
                    tal.setUsertype(0);
                }else if(classTypeArray[i].equals("2")){
                    /*TpVirtualClassInfo tv=new TpVirtualClassInfo();
                    tv.setVirtualclassid(Integer.parseInt(clsArray[i]));
                    List<TpVirtualClassInfo>vclsList=this.tpVirtualClassManager.getList(tv,null);*/
                    tal.setUsertype(1);
                }else{
                    je.setMsg("异常错误!任务班级无效!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                tal.setTaskid(tasknextid);
                tal.setUsertypeid(Long.parseLong(clsArray[i]));
                tal.setBtime(UtilTool.StringConvertToDate(bClsArray[i]));
                tal.setEtime(UtilTool.StringConvertToDate(eClsArray[i]));
                tal.setCuserid(this.logined(request).getRef());
                //tal.setCriteria(criteriaArray[0]);
                tal.setCourseid(Long.parseLong(courseid));
                sql=new StringBuilder();
                objList=this.tpTaskAllotManager.getSaveSql(tal, sql);
                if(objList!=null&&sql!=null&&sql.length()>0){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }

        if(tmpCourse.getQuoteid()!=null&&tmpCourse.getQuoteid().intValue()!=0){
            TpCourseInfo quoteInfo=new TpCourseInfo();
            quoteInfo.setCourseid(tmpCourse.getQuoteid());
            List<TpCourseInfo>quoteList=this.tpCourseManager.getList(quoteInfo,null);
            if(quoteList!=null&&quoteList.size()>0&&quoteList.get(0).getCourselevel()!=3){
                //增加专题操作数据
                TpOperateInfo to=new TpOperateInfo();
                to.setRef(this.tpOperateManager.getNextId(true));
                to.setCuserid(this.logined(request).getUserid());
                to.setCourseid(tmpCourse.getQuoteid());
                to.setTargetid(tasknextid);
                to.setOperatetype(2);                                                              //添加
                to.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_TASK.getValue());                 //专题任务
                sql=new StringBuilder();
                objList=this.tpOperateManager.getSaveSql(to,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }





        if(objListArray.size()>0&&sqlListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlListArray, objListArray);
            if(flag){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
                //添加任务消息提醒
              /*  List<UserInfo>taskUserList=this.userManager.getUserNotCompleteTask(ta,null);
                if(taskUserList!=null&&taskUserList.size()>0){
                    for (UserInfo u:taskUserList){
                        TpTaskInfo dynamicTask=new TpTaskInfo();
                        dynamicTask.setCourseid(ta.getCourseid());
                        dynamicTask.setTaskid(ta.getTaskid());
                        dynamicTask.setCuserid(u.getRef());
                        if(this.tpTaskManager.doSaveTaskMsg(dynamicTask))
                            System.out.println("doSaveTaskMsg SUCCESS!");
                        else
                            System.out.println("doSaveTaskMsg ERROR!");
                    }
                }else{
                    System.out.println("添加任务动态失败!原因：未获取到学生列表!");
                }*/

            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }else{
            je.setMsg("您的操作没有执行!");
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * 进入任务修改页
     * @return
     * @throws Exception
     */
    @RequestMapping(params="doUpdTask",method=RequestMethod.GET)
    public ModelAndView doUpdTask(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je =new JsonEntity();//
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");

        if(courseid==null||courseid.trim().length()<1){
            je.setMsg("异常错误,系统未获取到课题标识!");
            je.getAlertMsgAndBack();
            return null;
        }
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg("异常错误,系统未获取到任务标识!");
            je.getAlertMsgAndBack();
            return null;
        }
        //任务类型
        List<DictionaryInfo>tasktypeList=this.dictionaryManager.getDictionaryByType("TP_TASK_TYPE");
        //与该课题关联的班级
        TpCourseClass c=new TpCourseClass();
        c.setCourseid(Long.parseLong(courseid));
        List<TpCourseClass>courseclassList=this.tpCourseClassManager.getList(c, null);
        if(courseclassList==null||courseclassList.size()<1){
            je.setMsg("异常错误，未获取到该课题的班级信息!请设置后操作任务!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }

        //获取当前专题教材
        String subjectid=null;
        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(Long.parseLong(courseid));
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);
        if(materialList!=null&&materialList.size()>0){
            subjectid=materialList.get(0).getSubjectid().toString();
            //gradeid=materialList.get(0).getGradeid().toString();
        }

        //小组
        TpGroupInfo g=new TpGroupInfo();
        //g.setCuserid(this.logined(request).getUserid());
        g.setSubjectid(Integer.parseInt(subjectid));
        g.setTermid(courseclassList.get(0).getTermid());
        List<TpGroupInfo>groupList=this.tpGroupManager.getList(g, null);
		request.setAttribute("tasktypeList", tasktypeList);
		request.setAttribute("courseclassList", courseclassList);
		request.setAttribute("groupList", groupList);


        //该任务已设置的相关信息
        TpTaskInfo t=new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskid));
        t.setCourseid(Long.parseLong(courseid));
        List<TpTaskInfo> tpList=this.tpTaskManager.getList(t, null);
        if(tpList==null||tpList.size()<1){
            je.setMsg("抱歉该任务已不存在!");
            je.getAlertMsgAndBack();
            return null;
        }
        //任务
        TpTaskInfo taskinfo=tpList.get(0);
        //任务对象
        TpTaskAllotInfo tg=new TpTaskAllotInfo();
        tg.setTaskid(Long.parseLong(taskid));
        List<TpTaskAllotInfo>taskgroupList=this.tpTaskAllotManager.getList(tg, null);


        request.setAttribute("taskInfo", taskinfo);
        request.setAttribute("taskgroupList", taskgroupList);
        request.setAttribute("courseid", courseid);
        request.setAttribute("taskid", taskid);
        request.setAttribute("gradeid", request.getSession().getAttribute("session_grade"));
        request.setAttribute("subjectid",request.getSession().getAttribute("session_subject"));
        request.setAttribute("materialid",request.getSession().getAttribute("session_material"));
        TpCourseQuestion cq=new TpCourseQuestion();
        cq.setCourseid(Long.parseLong(courseid));
        Integer objectiveQuesCount=this.tpCourseQuestionManager.getObjectiveQuesCount(cq);
        request.setAttribute("objectiveQuesCount", objectiveQuesCount);   //专题下客观题数量
        return new ModelAndView("/teachpaltform/task/teacher/task-update");
    }


    /**
     * 任务修改
     * @throws Exception
     */
    @RequestMapping(params="doSubUpdTask",method=RequestMethod.POST)
    public void doSubUpdTask(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String taskid=request.getParameter("taskid");
        String tasktype=request.getParameter("tasktype");
        String taskname=request.getParameter("taskname");
        String taskvalueid=request.getParameter("taskvalueid");
        String taskremark=request.getParameter("taskremark");
        String quesnum=request.getParameter("quesnum");
        String clstype=request.getParameter("clstype");
        if(tasktype==null||tasktype.trim().length()<1
                ||taskid==null||taskvalueid==null
            // ||taskname==null||taskname.trim().length()<1
                ){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String courseid=request.getParameter("courseid");
        String questype=request.getParameter("questype");
        String[]criteriaArray=request.getParameterValues("taskcri");
        //小组
        String[]groupArray=request.getParameterValues("groupArray");
        //班级
        String[]clsArray=request.getParameterValues("clsArray");
        String[]bClsArray=request.getParameterValues("btimeClsArray");
        String[]eClsArray=request.getParameterValues("etimeClsArray");


        if(StringUtils.isBlank(courseid)){
            je.setMsg("异常错误,未获取到课题标识!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if((groupArray==null||groupArray.length<1)
                &&(clsArray==null||clsArray.length<1)){
            je.setMsg("异常错误,未获取到任务对象!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(!(StringUtils.isNotBlank(questype)&&questype.equals("5"))&&criteriaArray.length<1){
            je.setMsg("异常错误,未获取到任务完成标准!");
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo sel=new TpTaskInfo();
        sel.setCourseid(Long.parseLong(courseid));
        //查询没被我删除的任务
        sel.setSelecttype(1);
        sel.setLoginuserid(this.logined(request).getUserid());
        sel.setStatus(1);
        //已发布的任务
        List<TpTaskInfo>taskReleaseList=this.tpTaskManager.getTaskReleaseList(sel, null);
        Integer orderIdx=1;
        if(taskReleaseList!=null&&taskReleaseList.size()>0)
            orderIdx+=taskReleaseList.size();


        StringBuilder sql=null;
        List<Object>objList=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        Long tasknextid;
        TpTaskInfo ta=new TpTaskInfo();
        ta.setOrderidx(orderIdx);

        //课后作业 3
        if(tasktype.toString().equals("3")){
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("异常错误，系统未获取到试题标识!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpCourseQuestion q=new TpCourseQuestion();
            q.setQuestionid(Long.parseLong(taskvalueid));
            List<TpCourseQuestion>quesList=this.tpCourseQuestionManager.getList(q,null);
            if(quesList==null||quesList.size()<1){
                je.setMsg("提示：当前试题已不存在或删除!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));
        }else if(tasktype.toString().equals("2")){//论题
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("异常错误，系统未获取到论题标识!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpTopicInfo i=new TpTopicInfo();
            i.setTopicid(Long.parseLong(taskvalueid));
            List<TpTopicInfo>iList=this.tpTopicManager.getList(i, null);
            if(iList==null||iList.size()<1){
                je.setMsg("提示：当前论题已不存在或删除，请刷新页面后重试!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));
        }else if(tasktype.toString().equals("1")){//资源
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("异常错误，系统未获取到资源标识!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpCourseResource t=new TpCourseResource();
            t.setResid(Long.parseLong(taskvalueid));
            List<TpCourseResource>resList=this.tpCourseResourceManager.getList(t, null);
            if(resList==null||resList.size()<1){
                je.setMsg("提示：当前资源已不存在或删除，请刷新页面后重试!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));

            TpCourseResource tt=new TpCourseResource();
            tt.setCourseid(Long.parseLong(courseid));
            tt.setResid(Long.parseLong(taskvalueid));
            List<TpCourseResource>tpCourseResourceList=this.tpCourseResourceManager.getList(tt,null);
            if(tpCourseResourceList==null||tpCourseResourceList.size()<1){
                je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
                response.getWriter().print(je.toJSON());
                return;
            }
            if(tpCourseResourceList.get(0).getResourcetype().equals(2)){
                tt.setRef(tpCourseResourceList.get(0).getRef());
                tt.setResourcetype(1);
                sql=new StringBuilder();
                objList=this.tpCourseResourceManager.getUpdateSql(tt,sql);
                if(objList!=null&&sql!=null&&sql.length()>0){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }else if(tasktype.toString().equals("4")){//成卷测试
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("异常错误，系统未获取到试卷标识!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpCoursePaper tpCoursePaper=new TpCoursePaper();
            tpCoursePaper.setPaperid(Long.parseLong(taskvalueid));
            List<TpCoursePaper>iList=this.tpCoursePaperManager.getList(tpCoursePaper,null);
            if(iList==null||iList.size()<1){
                je.setMsg("提示：当前试卷已不存在或删除，请刷新页面后重试!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));
        }else if(tasktype.toString().equals("5")){//自主测试

        }else if(tasktype.toString().equals("6")){//微视频测试
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("异常错误，系统未获取到微视频标识!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpCourseResource tpCourseResource=new TpCourseResource();
            tpCourseResource.setResid(Long.parseLong(taskvalueid));
            tpCourseResource.setCourseid(Long.parseLong(courseid));
            List<TpCourseResource>iList=this.tpCourseResourceManager.getList(tpCourseResource,null);
            if(iList==null||iList.size()<1){
                je.setMsg("提示：当前资源已不存在或删除，请刷新页面后重试!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));
        }else if(tasktype.toString().equals("10")){//直播课
            if(taskname==null||taskname.trim().length()<1){
                je.setMsg("异常错误，系统未获取到直播主题!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskname(taskname);
            ta.setTaskvalueid(Long.valueOf(1));
        }
        //获取任务
        TpTaskInfo selT=new TpTaskInfo();
        selT.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo>taskList=this.tpTaskManager.getList(selT,null);
        if(taskList==null||taskList.size()<1){
            je.setMsg("提示：当前任务已不存在或删除，请刷新页面后重试!");
            response.getWriter().print(je.toJSON());
            return;
        }

        TpTaskInfo tmpTask=taskList.get(0);
        /**
         * 参考任务
         * 1自身元素修改，拷贝副本。
         * 2新建任务，并增加数据到元素操作表中。
         * 3删除任务分配数据。增加任务分配信息。
         */
        if(tmpTask.getCloudstatus().intValue()==3){
            //检测任务是否修改，是：创建副本，否则直接使用
            ta.setTaskid(tmpTask.getTaskid());
            ta.setTasktype(Integer.parseInt(tasktype));
            ta.setCourseid(Long.parseLong(courseid));
            ta.setCriteria(Integer.parseInt(criteriaArray[0]));
            if(taskremark!=null)
                ta.setTaskremark(taskremark);
            if(taskname!=null)
                ta.setTaskname(taskname);
            if(quesnum!=null&&quesnum.trim().length()>0)
                ta.setQuesnum(Integer.parseInt(quesnum));
            else
                ta.setQuesnum(10);
            tasknextid=this.tpTaskManager.getNextId(true);
            ta.setCuserid(this.logined(request).getRef());
            sql=new StringBuilder();
            objList=this.tpTaskManager.getSaveSql(ta, sql);
            if(objList!=null&&sql!=null&&sql.length()>0){
                objListArray.add(objList);
                sqlListArray.add(sql.toString());
            }
           /* if(tmpTask.getQuoteid()!=null){
                //增加专题操作数据
                TpOperateInfo to=new TpOperateInfo();
                to.setRef(this.tpOperateManager.getNextId(true));
                to.setCuserid(this.logined(request).getUserid());
                to.setCourseid(tmpTask.getQuoteid());
                to.setTargetid(tasknextid);             //新建任务ID
                to.setReferenceid(tmpTask.getTaskid()); //关联旧任务ID
                to.setOperatetype(3);              //修改
                to.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_TASK.getValue());                 //专题任务
                sql=new StringBuilder();
                objList=this.tpOperateManager.getSaveSql(to,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }*/
        }else{
            /**
             *自建任务修改
             */
            tasknextid=tmpTask.getTaskid();
            ta.setTaskid(tasknextid);
            if(taskname!=null)
                ta.setTaskname(taskname);
            ta.setTasktype(Integer.parseInt(tasktype));
            ta.setCourseid(Long.parseLong(courseid));
            ta.setCuserid(this.logined(request).getRef());
            ta.setCriteria(Integer.parseInt(criteriaArray[0]));
            if(taskremark!=null)
                ta.setTaskremark(taskremark);
            if(quesnum!=null&&quesnum.trim().length()>0)
                ta.setQuesnum(Integer.parseInt(quesnum));
            else
                ta.setQuesnum(10);
            sql=new StringBuilder();
            objList=this.tpTaskManager.getUpdateSql(ta, sql);
            if(objList!=null&&sql!=null&&sql.length()>0){
                objListArray.add(objList);
                sqlListArray.add(sql.toString());
            }
        }


        //删除分配对象
        TpTaskAllotInfo tdelete=new TpTaskAllotInfo();
        tdelete.setTaskid(tmpTask.getTaskid());
        tdelete.setCourseid(tmpTask.getCourseid());
        sql=new StringBuilder();
        objList=this.tpTaskAllotManager.getDeleteSql(tdelete,sql);
        if(sql!=null&&objList!=null){
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }
        //添加任务对象 小组
        if(groupArray!=null&&groupArray.length>0){
            for (int i=0;i<groupArray.length;i++) {
                TpTaskAllotInfo tal=new TpTaskAllotInfo();
                tal.setTaskid(tasknextid);
                tal.setUsertype(2);
                tal.setUsertypeid(Long.parseLong(groupArray[i]));
                tal.setBtime(UtilTool.StringConvertToDate(bClsArray[i]));
                tal.setEtime(UtilTool.StringConvertToDate(eClsArray[i]));
                tal.setCuserid(this.logined(request).getRef());
                //tal.setCriteria(criteriaArray[0]);
                tal.setCourseid(Long.parseLong(courseid));
                sql=new StringBuilder();
                objList=this.tpTaskAllotManager.getSaveSql(tal, sql);
                if(objList!=null&&sql!=null&&sql.length()>0){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }
        //添加任务对象 班级
        if(clsArray!=null&&clsArray.length>0){
            if(clstype==null||clstype.trim().length()<1){
                je.setMsg("异常错误!系统未获取到班级类型!");
                response.getWriter().print(je.toJSON());
                return;
            }
            String[]classTypeArray=clstype.split(",");
            for (int i=0;i<clsArray.length;i++) {
                TpTaskAllotInfo tal=new TpTaskAllotInfo();
                //验证是什么类型的班级
                if(classTypeArray[i].equals("1")){
                    /*ClassInfo c=new ClassInfo();
                    c.setClassid(Integer.parseInt(clsArray[i]));
                    List<ClassInfo>clsList=this.classManager.getList(c,null);*/
                    tal.setUsertype(0);
                }else if(classTypeArray[i].equals("2")){
                    /*TpVirtualClassInfo tv=new TpVirtualClassInfo();
                    tv.setVirtualclassid(Integer.parseInt(clsArray[i]));
                    List<TpVirtualClassInfo>vclsList=this.tpVirtualClassManager.getList(tv,null);*/
                    tal.setUsertype(1);
                }else{
                    je.setMsg("异常错误!任务班级无效!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                tal.setTaskid(tasknextid);
                tal.setUsertypeid(Long.parseLong(clsArray[i]));
                tal.setBtime(UtilTool.StringConvertToDate(bClsArray[i]));
                tal.setEtime(UtilTool.StringConvertToDate(eClsArray[i]));
                tal.setCuserid(this.logined(request).getRef());
                //tal.setCriteria(criteriaArray[0]);
                tal.setCourseid(Long.parseLong(courseid));
                sql=new StringBuilder();
                objList=this.tpTaskAllotManager.getSaveSql(tal, sql);
                if(objList!=null&&sql!=null&&sql.length()>0){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }

        if(objListArray.size()>0&&sqlListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlListArray, objListArray);
            if(flag){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
                //添加任务消息提醒
                //this.tpTaskManager.doSaveTaskMsg(ta);
            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }else{
            je.setMsg("您的操作没有执行!");
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 跳转到学生任务首页
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toStuTaskIndex",method=RequestMethod.GET)
    public ModelAndView toStuTaskIndex(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
//		if(!this.validateRole(new BigDecimal(UtilTool._STUDENT_ROLE_ID))){
//			je.setMsg("抱歉，当前页面只允许学生用户查看!");
//			response.getWriter().print(je.getAlertMsgAndBack());
//			return null;  
//		}
        String courseid=request.getParameter("courseid");
        String termid=request.getParameter("termid");
        //根据课题ID和学生ID查出任务
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }

        TpTaskInfo t=new TpTaskInfo();
        t.setCourseid(Long.parseLong(courseid));
        t.setUserid(this.logined(request).getUserid());
//		List<TpTaskInfo> taskList=this.tpTaskManager.getListbyStu(t,null);
//		if(taskList==null||taskList.size()<1){
//			je.setMsg("提示：没有发现任务信息!");
//			response.getWriter().print(je.getAlertMsgAndBack());
//			return null;
//		}
        //查询学生课题列表
        Integer subjectid=null;
        //获取当前专题教材
        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(Long.parseLong(courseid));
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);
        if(materialList!=null&&materialList.size()>0)
            subjectid=materialList.get(0).getSubjectid();

        //课题列表
        TpCourseInfo tt=new TpCourseInfo();
        tt.setCourseid(Long.parseLong(courseid));
        tt.setLocalstatus(1);   //1：正常 2：删除
        tt.setUserid(this.logined(request).getUserid());
        List<TpCourseInfo>courseList=this.tpCourseManager.getStuCourseList(tt, null);
        if(courseList==null||courseList.size()<1){
            je.setMsg("异常错误，没有发现当前专题!请刷新后重试!");
            response.getWriter().print(je.getAlertMsgAndBack());return null;
        }else{
            request.setAttribute("coursename", courseList.get(0).getCoursename());
            //课题样式
            TpCourseInfo tcs1= new TpCourseInfo();
            tcs1.setUserid(this.logined(request).getUserid());
            tcs1.setTermid(courseList.get(0).getTermid());
            tcs1.setSubjectid(subjectid);
            if(termid==null||termid.trim().length()<1)
                termid=courseList.get(0).getTermid();
            List<TpCourseInfo>courseList1=this.tpCourseManager.getStuCourseList(tcs1, null);
            request.setAttribute("courseList",courseList1);
        }

		/*
		 *  卢艳(422812115) 下午 2013-11-22 4:23:07
		 *  切换专题里，有当前专题？而且，学生端，专题能不能按学科区分一下

		List<TeacherCourseInfo> course2List=new ArrayList<TeacherCourseInfo>();
		if(subjectid!=null){
			for (TeacherCourseInfo tcTmp : course1List) {
				System.out.println(tcTmp.getSubjectid()+"   "+subjectid);
				if(tcTmp!=null&&tcTmp.getSubjectid()!=null&&tcTmp.getSubjectid()==subjectid){
					System.out.println(subjectid+"  "+tcTmp.getSubjectid());
					course2List.add(tcTmp);
				}
			}
		}else
			course2List=course1List;
		request.setAttribute("courseList", course2List);
		 */
		
    /*
		for (TpTaskInfo tp : taskList) {
			if(tp!=null&&tp.getTaskvalueid()!=null){
				//课后作业    
				if(tp.getTasktype().intValue()==3){
					QuestionInfo qb=new QuestionInfo();
					qb.setQuestionid(tp.getTaskvalueid());

					List<QuestionInfo>qbList=this.questionManager.getList(qb, null);
					if(qbList!=null&&qbList.size()>0){
						tp.setQuestionList(qbList);
                        QuestionInfo quesparent=qbList.get(0);
						if(quesparent!=null&&(quesparent.getQuestiontype().intValue()==3||quesparent.getQuestiontype().intValue()==4))
						{
							QuestionOption qchild=new QuestionOption();
							qchild.setQuestionid(quesparent.getQuestionid());
							PageResult page=new PageResult();
							page.setOrderBy("u.option_type");
							page.setPageNo(0); 
							page.setPageSize(0); 
							List<QuestionOption>qchildList=this.questionOptionManager.getList(qchild, page);
							tp.setQuestionOptionList(qchildList);
						}
					}
					//正确答案（只有课后作业有）
					Integer qtype=qbList.get(0).getQuestype();
					if(qtype.intValue()==2||qtype.intValue()==3||qtype.intValue()==4){
						QuestionBank qright=new QuestionBank();
						qright.setIsright("1");
						if(qtype.intValue()==2){
							qright.setQuestionid(tp.getTaskvalueid());
							qright.setParentquesid("0");
						}else if(qtype.intValue()==3||qtype.intValue()==4){
							qright.setParentquesid(tp.getTaskvalueid());
						}
						List<QuestionBank>quesRightList=this.questionBankManager.getList(qright, null);
						tp.setTpQuesRightList(quesRightList);
					}
				}else if(tp.getTasktype().intValue()==2){ 
					if(tp.getTaskvalueid()!=null){
						InteractiveThemeInfo it=new InteractiveThemeInfo();
						it.setThemeid(tp.getTaskvalueid());
						List<InteractiveThemeInfo>itList=this.interactiveThemeManager.getList(it, null);
						tp.setTpResTopicList(itList);
						
						//获取主题
						InteractiveTopicInfo itopic=new InteractiveTopicInfo();
						itopic.setThemeid(tp.getTaskvalueid());
						itopic.setCuserid(this.logined(request).getRef());
						List<InteractiveTopicInfo>topicList=this.interactiveTopicManager.getList(itopic, null);
						tp.setTpThemeList(topicList);
						//获取答题人数
						TaskPerformanceInfo tper=new TaskPerformanceInfo();
						tper.setTaskid(tp.getTaskid());
						List<TaskPerformanceInfo>performList=this.taskPerformanceManager.getReplyColumsCount(tper);
						tp.setViewOrReplyList(performList);
						 
					}
				}else if(tp.getTasktype().intValue()==1){
					if(tp.getTaskvalueid()!=null){
						TpResourceBaseInfo rd=new TpResourceBaseInfo();
						rd.setRef(tp.getTaskvalueid());
						List<TpResourceBaseInfo>itList=this.tpResourceBaseManager.getList(rd, null);
						tp.setTpResTopicList(itList);
					}
					
				}
			
				//获取答题记录和心得
				QuestionAnswer qa=new QuestionAnswer();
				qa.setTaskid(tp.getTaskid());
				qa.setUserid(this.logined(request).getRef());
				List<QuestionAnswer>qaList=this.questionAnswerManager.getList(qa, null);
				tp.setTpQuestionAnswerList(qaList);
				
				//查询完成状态(笑脸或哭脸)
				TaskPerformanceInfo tper=new TaskPerformanceInfo();
				tper.setTaskid(tp.getTaskid());
				tper.setUserid(this.logined(request).getRef());
				List<TaskPerformanceInfo>performList=this.taskPerformanceManager.getStuPerformanceStatus(tper);
				tp.setTpTaskPerformList(performList);
				
				//已做未做
				TaskPerformanceInfo taskdo=new TaskPerformanceInfo();
				taskdo.setTaskid(tp.getTaskid());
				taskdo.setUserid(this.logined(request).getRef());
				List<TaskPerformanceInfo>doperformList=this.taskPerformanceManager.getList(taskdo, null);
				tp.setTpDoPerformList(doperformList);
				
				//完成标准
				TaskCriteriaInfo tc=new TaskCriteriaInfo();
				tc.setTaskid(tp.getTaskid());
				List<TaskCriteriaInfo>taskCriList=this.taskCriteriaManager.getList(tc,null);
				tp.setTpTaskCriterList(taskCriList);
				
				//小组人员
				GroupStudent gs=new GroupStudent();
				gs.setGroupid(tp.getGroupid());
				List<GroupStudent>groupStuList=this.groupStudentManager.getList(gs,null);
				tp.setTpGroupStuList(groupStuList);
				 
				//小组内已完成的人数
				QuestionAnswer quescount=new QuestionAnswer();
				quescount.setTaskid(tp.getTaskid());
				quescount.setGroupid(tp.getGroupid());
				List<QuestionAnswer>comletecountList=this.questionAnswerManager.getListNoRepeat(quescount);
				tp.setTpQuesAnswerCountList(comletecountList);
			}
		}*/
        request.setAttribute("courseid", courseid);
        request.setAttribute("termid", termid);

        return new ModelAndView("/teachpaltform/task/student/task-index");
    }


    /**
     * 学生提交任务
     * @throws Exception
     */
    @RequestMapping(params="doStuSubmitTask",method=RequestMethod.POST)
    public void doStuSubmitTask(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");
        String tasktype=request.getParameter("tasktype");
        String hasannex=request.getParameter("hasannex");
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


        List<Object>objList=null;
        StringBuilder sql=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();

        //课后作业
        String quesanswer=request.getParameter("quesanswer");
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

        if(Integer.parseInt(tasktype)==3){
            if(questype==null||!UtilTool.isNumber(questype)){
                je.setMsg("异常错误，系统未获取到问题类型!");
                response.getWriter().print(je.toJSON());
                return;
            }


            if(questype.equals("1")){//问答
                if(quesanswer==null||quesanswer.trim().length()<1){
                    je.setMsg("异常错误，未获取到问答题答案!");
                    response.getWriter().print(je.toJSON());
                    return;
                }



                //录入答题记录
                QuestionAnswer qa=new QuestionAnswer();
                qa.setTaskid(t.getTaskid());
                qa.setCourseid(t.getCourseid());
                qa.setTasktype(t.getTasktype());
                qa.setAnswercontent(quesanswer);
                qa.setQuesparentid(Long.parseLong(quesid));
                qa.setUserid(this.logined(request).getRef());
                qa.setRightanswer(1);

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
                tp.setCriteria(tmpTask.getCriteria());
                tp.setUserid(this.logined(request).getRef());
                tp.setIsright(1);
                sql=new StringBuilder();
                objList=this.taskPerformanceManager.getSaveSql(tp, sql);
                if(objList!=null&&sql!=null){
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }
            }else if(questype.equals("2")){//填空
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
                qa.setUserid(this.logined(request).getRef());
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
                tp.setUserid(this.logined(request).getRef());
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
                qa.setUserid(this.logined(request).getRef());
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
                tp.setUserid(this.logined(request).getRef());
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
                    qa.setUserid(this.logined(request).getRef());
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
                tp.setUserid(this.logined(request).getRef());
                tp.setCriteria(tmpTask.getCriteria());
                tp.setIsright(flag<1?1:0);
                sql=new StringBuilder();
                objList=this.taskPerformanceManager.getSaveSql(tp, sql);
                if(objList!=null&&sql!=null){
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }
            }
        }else if(Integer.parseInt(tasktype)==1){
            if(quesanswer==null||quesanswer.trim().length()<1){
                je.setMsg("异常错误,未获取到资源学习心得!");
                response.getWriter().print(je.toJSON());
                return;
            }
            quesanswer=new String(quesanswer.getBytes("iso-8859-1"),"UTF-8");

            String annexName=null;
            boolean isfileOk=true;
            if(hasannex!=null&&hasannex.equals("1")){
                MultipartFile[] muFile=this.getUpload(request);
                if(muFile!=null&&muFile.length>0){//文件上传
                    //this.setFname(annexName=this.getUploadFileName()[0]);
                    this.setFname(annexName=new Date().getTime()+""
                            +this.getUploadFileName()[0].substring(this.getUploadFileName()[0].lastIndexOf("."))
                    );
                    if(!fileupLoad(request)){
                        isfileOk=!isfileOk;
                    }
                }else
                    isfileOk=false;
            }

            if(!isfileOk){
                je.setMsg("文件上传失败!请刷新页面后重试!");
                response.getWriter().println(je.toJSON());return ;
            }


            QuestionAnswer qa=new QuestionAnswer();
            qa.setCourseid(Long.parseLong(courseid));
            qa.setQuesparentid(Long.parseLong(quesid));
            qa.setQuesid(Long.parseLong("0"));
            qa.setUserid(this.logined(request).getRef());
            qa.setAnswercontent(quesanswer);
            qa.setRightanswer(1);
            qa.setTasktype(tmpTask.getTasktype());
            qa.setTaskid(tmpTask.getTaskid());
            if(annexName!=null&&annexName.trim().length()>0)
                qa.setReplyattach(annexName);
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
                tp.setUserid(this.logined(request).getRef());
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
                je.setMsg("当前资源没有分配提交学习心得任务!");
                response.getWriter().print(je.toJSON());
                return;
            }
        }

        if(objListArray.size()>0&&sqlListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlListArray, objListArray);
            if(flag){
                //得到班级ID
                TpTaskAllotInfo tallot=new TpTaskAllotInfo();
                tallot.setTaskid(taskList.get(0).getTaskid());
                tallot.getUserinfo().setUserid(this.logined(request).getUserid());
                List<Map<String,Object>> clsMapList=this.tpTaskAllotManager.getClassId(tallot);
                if(clsMapList==null||clsMapList.size()<1||clsMapList.get(0)==null||!clsMapList.get(0).containsKey("CLASS_ID")
                        ||clsMapList.get(0).get("CLASS_ID")==null){
                    je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                    response.getWriter().println(je.toJSON());return ;
                }

                //taskinfo:   4:成卷测试  5：自主测试   6:微视频
                //规则转换:    6             7         8
                Integer type=0;
                switch(taskList.get(0).getTasktype()){
                    case 3:     //试题
                        type=1;break;
                    case 1:     //资源学习
                        type=2;break;
                    case 2:
                        type=4;
                        break;
                }
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                String jid=this.logined(request).getEttuserid()==null?null:this.logined(request).getEttuserid().toString();
                /*奖励加分通过*/
                if(this.tpStuScoreLogsManager.awardStuScore(Long.parseLong(courseid.trim())
                        ,Long.parseLong(clsMapList.get(0).get("CLASS_ID").toString())
                        ,Long.parseLong(taskid.trim())
                        ,Long.parseLong(this.logined(request).getUserid()+""),jid,type,this.logined(request).getDcschoolid())){
                    je.setMsg("恭喜您,获得了1积分和1蓝宝石(没有调用接口)");
                }else
                    System.out.println("awardScore error");
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
     * 获取课题下的论题
     * @throws Exception
     */
    @RequestMapping(params="toQryTopicList",method=RequestMethod.POST)
    public void toQryTopicList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String clsid=request.getParameter("clsid");
        String topicid=request.getParameter("topicid");
        String taskflag=request.getParameter("taskflag");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg("异常错误，系统未获取到课题标识!");
            response.getWriter().print(je.toJSON());
            return;
        }
//		if(clsid==null||clsid.trim().length()<1){
//			je.setMsg("异常错误，系统未获取到班级标识!");
//			response.getWriter().print(je.toJSON());
//			return;
//		}
        PageResult p=this.getPageResultParameter(request);
        TpTopicInfo t=new TpTopicInfo();
        t.setCuserid(this.logined(request).getUserid());
        t.setCourseid(Long.parseLong(courseid));
        t.setStatus(1);
        if(topicid!=null&&topicid.trim().length()>0)
            t.setTopicid(Long.parseLong(topicid));
        //查询未发布任务的论题
        if(taskflag!=null&&taskflag.trim().length()>0)
            t.setFlag(1);
        List<TpTopicInfo>topList=this.tpTopicManager.getList(t,p);
        p.setList(topList);
        je.setPresult(p);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * 提交学习心得
     * @throws Exception
     */
    @RequestMapping(params="m=doSubStudyNotes",method=RequestMethod.POST)
    public void doSubStudyNotes(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String resourceid=request.getParameter("resid");
        String answercontent=request.getParameter("answercontent");
        String hasannex=request.getParameter("hasannex");

        if(courseid==null||courseid.trim().length()<1||
                resourceid==null||resourceid.trim().length()<1||
                answercontent==null||answercontent.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo taskInfo=new TpTaskInfo();
        taskInfo.setCourseid(Long.parseLong(courseid));
        taskInfo.setTaskvalueid(Long.parseLong(resourceid));
        List<TpTaskInfo>taskList=this.tpTaskManager.getList(taskInfo,null);
        if(taskList==null||taskList.size()<1){
            je.setMsg("当前资源没有分配任务信息!");
            response.getWriter().print(je.toJSON());
            return;
        }
        taskInfo=taskList.get(0);
        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlList=new ArrayList<String>();

        if(taskInfo.getCriteria()!=null&&taskInfo.getCriteria()==2){
            TaskPerformanceInfo tp=new TaskPerformanceInfo();
            tp.setTaskid(taskList.get(0).getTaskid());
            tp.setTasktype(taskList.get(0).getTasktype());
            tp.setCourseid(taskList.get(0).getCourseid());
            tp.setCriteria(2);//提交心得
            tp.setUserid(this.logined(request).getRef());
            tp.setIsright(1);
            List<TaskPerformanceInfo>tpList=this.taskPerformanceManager.getList(tp,null);
            if(tpList==null||tpList.size()<1){
                sql=new StringBuilder();
                objList=this.taskPerformanceManager.getSaveSql(tp,sql);
                if(sql!=null&&objList!=null){
                    sqlList.add(sql.toString());
                    objListArray.add(objList);
                }
            }
        }else{
            je.setMsg("当前资源没有分配提交学习心得任务!");
            response.getWriter().print(je.toJSON());
            return;
        }




        String annexName=null;
        boolean isfileOk=true;
        if(hasannex!=null&&hasannex.equals("1")){
            MultipartFile[] muFile=this.getUpload(request);
            if(muFile!=null&&muFile.length>0){//文件上传
                //this.setFname(annexName=this.getUploadFileName()[0]);
                this.setFname(annexName=new Date().getTime()+""
                        +this.getUploadFileName()[0].substring(this.getUploadFileName()[0].lastIndexOf("."))
                );
                if(!fileupLoad(request)){
                    isfileOk=!isfileOk;
                }
            }else
                isfileOk=false;
            answercontent=new String(answercontent.getBytes("iso-8859-1"),"UTF-8");
        }

        if(!isfileOk){
            je.setMsg("文件上传失败!请刷新页面后重试!");
            response.getWriter().println(je.toJSON());return ;
        }




        QuestionAnswer qa=new QuestionAnswer();
        qa.setCourseid(Long.parseLong(courseid));
        qa.setQuesparentid(Long.parseLong(resourceid));
        qa.setQuesid(Long.parseLong("0"));
        qa.setUserid(this.logined(request).getRef());
        qa.setAnswercontent(answercontent);
        qa.setRightanswer(1);
        qa.setTasktype(taskInfo.getTasktype());
        qa.setTaskid(taskInfo.getTaskid());
        if(annexName!=null&&annexName.length()>0)
            qa.setReplyattach(annexName);
        sql=new StringBuilder();
        objList=this.questionAnswerManager.getSaveSql(qa,sql);
        if(sql!=null&&objList!=null){
            sqlList.add(sql.toString());
            objListArray.add(objList);
        }

        if(this.tpTaskManager.doExcetueArrayProc(sqlList,objListArray)){
            je.setType("success");
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));


            //得到班级ID
            TpTaskAllotInfo tallot=new TpTaskAllotInfo();
            tallot.setTaskid(taskList.get(0).getTaskid());
            tallot.getUserinfo().setUserid(this.logined(request).getUserid());
            List<Map<String,Object>> clsMapList=this.tpTaskAllotManager.getClassId(tallot);
            if(clsMapList==null||clsMapList.size()<1||clsMapList.get(0)==null||!clsMapList.get(0).containsKey("CLASS_ID")
                    ||clsMapList.get(0).get("CLASS_ID")==null){
                je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                response.getWriter().println(je.toJSON());return ;
            }

            //taskinfo:   4:成卷测试  5：自主测试   6:微视频
            //规则转换:    6             7         8
            Integer type=0;
            switch(taskList.get(0).getTasktype()){
                case 3:     //试题
                    type=1;break;
                case 1:     //资源学习
                    type=2;break;
                case 2:
                    type=4;
                    break;
            }
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            String jid=this.logined(request).getEttuserid()==null?null:this.logined(request).getEttuserid().toString();
                /*奖励加分通过*/
            if(this.tpStuScoreLogsManager.awardStuScore(Long.parseLong(courseid.trim())
                    ,Long.parseLong(clsMapList.get(0).get("CLASS_ID").toString())
                    ,taskList.get(0).getTaskid()
                    ,Long.parseLong(this.logined(request).getUserid()+""),jid,type,this.logined(request).getDcschoolid())){
                je.setMsg("恭喜您,获得了1积分和1蓝宝石(没有调用接口)");
            }else
                System.out.println("awardScore error");



        }else{
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * 检测资源是否发任务
     * @throws Exception
     */
    @RequestMapping(params="checkStudyNotes",method=RequestMethod.POST)
    public void checkStudyNotes(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String resourceid=request.getParameter("resid");

        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(resourceid==null||resourceid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        /*
        * 查询当前资源是否发了学习心得任务
        */
        TpTaskInfo taskInfo=new TpTaskInfo();
        taskInfo.setCourseid(Long.parseLong(courseid));
        taskInfo.setTaskvalueid(Long.parseLong(resourceid));
        taskInfo.setCriteria(2);
        List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(taskInfo, null);



        je.setObjList(taskList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 加载学习心得
     * @throws Exception
     */
    @RequestMapping(params="loadStudyNotes",method=RequestMethod.POST)
    public void loadStudyNotes(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String type=request.getParameter("usertype");
        String courseid=request.getParameter("courseid");
        String resourceid=request.getParameter("resourceid");

        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(resourceid==null||resourceid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }


        QuestionAnswer qa=new QuestionAnswer();
        qa.setCourseid(Long.parseLong(courseid));
        qa.setQuesparentid(Long.parseLong(resourceid));
        //查学生心得
        if(type!=null&&type.equals("1"))
            qa.setUserid(this.logined(request).getRef());
        List<QuestionAnswer>qaList=this.questionAnswerManager.getList(qa, null);
        je.getObjList().add(qaList!=null&&qaList.size()>0?qaList.get(0):null);

        /*
        * 查询当前资源是否发任务
        */
        if(type!=null&&type.equals("1")){
            PageResult p=new PageResult();
            p.setPageNo(1);
            p.setPageSize(1);
            TpTaskInfo taskInfo=new TpTaskInfo();
            taskInfo.setCourseid(Long.parseLong(courseid));
            taskInfo.setTaskvalueid(Long.parseLong(resourceid));
            List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(taskInfo,p);
            if(taskList!=null&&taskList.size()>0&&taskList.get(0).getTaskstatus()!=null){
                /**
                 * 查询当前学生是否被分配该任务
                 * */
                boolean isAllot=false;
                TpTaskInfo t=new TpTaskInfo();
                t.setCourseid(taskList.get(0).getCourseid());
                t.setUserid(this.logined(request).getUserid());
                List<TpTaskInfo> taskStuList=this.tpTaskManager.getListbyStu(t,null);
                if(taskStuList!=null&&taskStuList.size()>0){
                    for(TpTaskInfo tmpTask:taskStuList){
                        if(tmpTask.getTaskid().equals(taskList.get(0).getTaskid())
                                &&taskList.get(0).getCriteria()!=null&&taskList.get(0).getCriteria().intValue()==2){
                            isAllot=true;
                        }
                    }
                }
                if(!taskList.get(0).getTaskstatus().equals("1")&&!taskList.get(0).getTaskstatus().equals("3")&&isAllot){
                    je.getObjList().add("on");
                }else if(taskList.get(0).getTaskstatus().equals("3")&&isAllot){
                    je.getObjList().add("off");
                }

            }

        }



        je.setType("success");
        response.getWriter().print(je.toJSON());
    }



    /**
     * 修改学习心得
     * @throws Exception
     */
    @RequestMapping(params="doUpdStudyNotes",method=RequestMethod.POST)
    public void doUpdStudyNotes(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String ref=request.getParameter("ref");
        String content=request.getParameter("content");
        String hasannex=request.getParameter("hasannex");


        if(ref==null||ref.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(content==null||content.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        content=new String(content.getBytes("iso-8859-1"),"UTF-8");

        String annexName=null;
        boolean isfileOk=true;
        if(hasannex!=null&&hasannex.equals("1")){
            MultipartFile[] muFile=this.getUpload(request);
            if(muFile!=null&&muFile.length>0){//文件上传
                //this.setFname(annexName=this.getUploadFileName()[0]);
                this.setFname(annexName=new Date().getTime()+""
                        +this.getUploadFileName()[0].substring(this.getUploadFileName()[0].lastIndexOf("."))
                );
                if(!fileupLoad(request)){
                    isfileOk=!isfileOk;
                }
            }else
                isfileOk=false;
        }

        if(!isfileOk){
            je.setMsg("文件上传失败!请刷新页面后重试!");
            response.getWriter().println(je.toJSON());return ;
        }



        QuestionAnswer qa=new QuestionAnswer();
        qa.setRef(Integer.parseInt(ref));
        qa.setAnswercontent(content);
        if(annexName!=null&&annexName.trim().length()>0)
            qa.setReplyattach(annexName);
        if(this.questionAnswerManager.doUpdate(qa)){
            je.setType("success");
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
        }else{
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * 学生提建议
     * @throws Exception
     */
    @RequestMapping(params="doSubmitSuggest",method=RequestMethod.POST)
    public void doSubmitSuggest(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je =new JsonEntity();

        String suggestcontent=request.getParameter("suggestcontent");
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");
        String isanonmous=request.getParameter("isanonmous");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(suggestcontent==null||suggestcontent.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(isanonmous==null||isanonmous.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        TaskSuggestInfo ts=new TaskSuggestInfo();
        ts.setTaskid(Long.parseLong(taskid));
        ts.setCourseid(Long.parseLong(courseid));
        ts.setIsanonymous(Integer.parseInt(isanonmous));
        ts.setUserid(this.logined(request).getRef());
        ts.setSuggestcontent(suggestcontent);

        if(this.taskSuggestManager.doSave(ts)){
            je.setMsg("建议提交成功!");
            je.setType("success");
        }else{
            je.setMsg("建议提交失败!");
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * 添加互动交流论题查看记录
     * @throws Exception
     */
    @RequestMapping(params="doAddViewRecord",method=RequestMethod.POST)
    public void doAddViewRecord(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String themeid=request.getParameter("themeid");
        String courseid=request.getParameter("courseid");
        String tasktype=request.getParameter("tasktype");
        String groupid=request.getParameter("groupid");
        String taskid=request.getParameter("taskid");


        if(themeid==null||themeid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(tasktype==null||tasktype.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
//		if(groupid==null||groupid.trim().length()<1){
//			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
//			response.getWriter().print(je.toJSON());return;
//		}
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        //检测是否有查看标准
        TpTaskInfo t=new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskid));
        t.setCriteria(1);
        List<TpTaskInfo>taskCriList=this.tpTaskManager.getList(t, null);

        TpTaskInfo task=new TpTaskInfo();
        task.setTaskid(Long.parseLong(taskid));
        task.setCourseid(Long.parseLong(courseid));
        task.setTasktype(Integer.parseInt(tasktype));
        //task.setGroupid(groupid);

        //录入完成状态
        TaskPerformanceInfo tp=new TaskPerformanceInfo();
        tp.setTaskid(task.getTaskid());
        tp.setTasktype(task.getTasktype());
        tp.setCourseid(task.getCourseid());
        tp.setCriteria(1);//查看
        tp.setUserid(this.logined(request).getRef());
        tp.setIsright(1);
        List<TaskPerformanceInfo>tList=this.taskPerformanceManager.getList(tp,null);
        if(taskCriList!=null&&taskCriList.size()>0&&taskCriList.get(0).getTaskstatus()!=null
                &&!taskCriList.get(0).getTaskstatus().equals("1")&&!taskCriList.get(0).getTaskstatus().equals("3")){
            if(tList!=null&&tList.size()>0){
                response.sendRedirect("tptopic?m=toDetailTopic&topicid="+themeid+"&taskid="+taskid+"&courseid="+courseid);
            }else{
                if(this.taskPerformanceManager.doSave(tp)){
                       /*奖励加分*/
                    //得到班级ID
                    TpTaskAllotInfo tallot=new TpTaskAllotInfo();
                    tallot.setTaskid(Long.parseLong(taskid));

                    tallot.getUserinfo().setUserid(this.logined(request).getUserid());
                    List<Map<String,Object>> clsMapList=this.tpTaskAllotManager.getClassId(tallot);
                    if(clsMapList==null||clsMapList.size()<1||clsMapList.get(0)==null||!clsMapList.get(0).containsKey("CLASS_ID")
                            ||clsMapList.get(0).get("CLASS_ID")==null){
                        je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                        response.getWriter().println(je.toJSON());return ;
                    }

                    //taskinfo:   4:成卷测试  5：自主测试   6:微视频
                    //规则转换:    6             7         8
                    Integer type=0;
                    switch(taskCriList.get(0).getTasktype()){
                        case 3:     //试题
                            type=1;break;
                        case 1:     //资源学习
                            type=2;break;
                        case 2:
                            type=4;
                            break;
                    }
                    String jid=this.logined(request).getEttuserid()==null?null:this.logined(request).getEttuserid().toString();


                    String msg=null;
                        /*奖励加分通过*/
                    if(this.tpStuScoreLogsManager.awardStuScore(taskCriList.get(0).getCourseid()
                            , Long.parseLong(clsMapList.get(0).get("CLASS_ID").toString())
                            , taskCriList.get(0).getTaskid()
                            , Long.parseLong(this.logined(request).getUserid() + ""),jid, type,this.logined(request).getDcschoolid())){
                        msg="查看并提交心得:恭喜您,获得了1积分和1蓝宝石(没有调用接口)";
//                        request.getSession().setAttribute("msg",msg);
                    }else
                        System.out.println("awardScore err ");


                    response.sendRedirect("tptopic?m=toDetailTopic&topicid="+themeid+"&taskid="+taskid+"&courseid="+courseid);
                }else{
                    je.setMsg("异常错误!添加查看记录失败!请重试!");
                    response.getWriter().print(je.toJSON());
                }
            }
        }else{
            response.sendRedirect("tptopic?m=toDetailTopic&topicid="+themeid+"&taskid="+taskid+"&courseid="+courseid);
        }
    }


    /**
     * 添加直播课记录
     * @throws Exception
     */
    @RequestMapping(params="doAddLiveLessionRecord",method=RequestMethod.POST)
    public void doAddLiveLessionRecord(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String tasktype=request.getParameter("tasktype");
        String taskid=request.getParameter("taskid");
        String liveaddress=request.getParameter("liveaddress");

        if(liveaddress==null||liveaddress.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(tasktype==null||tasktype.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        //检测是否有查看标准
        TpTaskInfo t=new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskid));
        t.setCriteria(1);
        List<TpTaskInfo>taskCriList=this.tpTaskManager.getList(t, null);

        TpTaskInfo task=new TpTaskInfo();
        task.setTaskid(Long.parseLong(taskid));
        task.setCourseid(Long.parseLong(courseid));
        task.setTasktype(Integer.parseInt(tasktype));
        //task.setGroupid(groupid);

        //录入完成状态
        TaskPerformanceInfo tp=new TaskPerformanceInfo();
        tp.setTaskid(task.getTaskid());
        tp.setTasktype(task.getTasktype());
        tp.setCourseid(task.getCourseid());
        tp.setCriteria(1);//查看
        tp.setUserid(this.logined(request).getRef());
        tp.setIsright(1);
        List<TaskPerformanceInfo>tList=this.taskPerformanceManager.getList(tp,null);
        if(taskCriList!=null&&taskCriList.size()>0&&taskCriList.get(0).getTaskstatus()!=null
                &&!taskCriList.get(0).getTaskstatus().equals("1")&&!taskCriList.get(0).getTaskstatus().equals("3")){
            if(tList!=null&&tList.size()>0){
                response.sendRedirect(liveaddress);
            }else{
                if(this.taskPerformanceManager.doSave(tp)){
                       /*奖励加分*/
                    //得到班级ID
                    /*
                    TpTaskAllotInfo tallot=new TpTaskAllotInfo();
                    tallot.setTaskid(Long.parseLong(taskid));

                    tallot.getUserinfo().setUserid(this.logined(request).getUserid());
                    List<Map<String,Object>> clsMapList=this.tpTaskAllotManager.getClassId(tallot);
                    if(clsMapList==null||clsMapList.size()<1||clsMapList.get(0)==null||!clsMapList.get(0).containsKey("CLASS_ID")
                            ||clsMapList.get(0).get("CLASS_ID")==null){
                        je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                        response.getWriter().println(je.toJSON());return ;
                    }

                    //taskinfo:   4:成卷测试  5：自主测试   6:微视频
                    //规则转换:    6             7         8
                    Integer type=0;
                    switch(taskCriList.get(0).getTasktype()){
                        case 3:     //试题
                            type=1;break;
                        case 1:     //资源学习
                            type=2;break;
                        case 2:
                            type=4;
                            break;
                    }
                    String jid=this.logined(request).getEttuserid()==null?null:this.logined(request).getEttuserid().toString();


                    String msg=null;
                    if(this.tpStuScoreLogsManager.awardStuScore(taskCriList.get(0).getCourseid()
                            , Long.parseLong(clsMapList.get(0).get("CLASS_ID").toString())
                            , taskCriList.get(0).getTaskid()
                            , Long.parseLong(this.logined(request).getUserid() + ""),jid, type,this.logined(request).getDcschoolid())){
                        msg="查看并提交心得:恭喜您,获得了1积分和1蓝宝石(没有调用接口)";
//                        request.getSession().setAttribute("msg",msg);
                    }else
                        System.out.println("awardScore err ");
                    */

                    response.sendRedirect(liveaddress);
                }else{
                    je.setMsg("异常错误!添加查看记录失败!请重试!");
                    response.getWriter().print(je.toJSON());
                }
            }
        }else{
            response.sendRedirect(liveaddress);
        }
    }




    /**
     * 添加资源学习查看记录
     * @throws Exception
     */
    @RequestMapping(params="doAddResViewRecord",method=RequestMethod.POST)
    public void doAddResViewRecord(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String tpresdetailid=request.getParameter("tpresdetailid");
        String courseid=request.getParameter("courseid");
        String tasktype=request.getParameter("tasktype");
        String groupid=request.getParameter("groupid");
        String taskid=request.getParameter("taskid");


        if(tpresdetailid==null||tpresdetailid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(tasktype==null||tasktype.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
//		if(groupid==null||groupid.trim().length()<1){
//			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
//			response.getWriter().print(je.toJSON());return;
//		}
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        //检测是否有查看标准
        TpTaskInfo t=new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskid));
        t.setCriteria(1);
        List<TpTaskInfo>taskCriList=this.tpTaskManager.getList(t, null);

        TpTaskInfo task=new TpTaskInfo();
        task.setTaskid(Long.parseLong(taskid));
        task.setCourseid(Long.parseLong(courseid));
        task.setTasktype(Integer.parseInt(tasktype));
        //task.setGroupid(groupid);

        //录入完成状态
        TaskPerformanceInfo tp=new TaskPerformanceInfo();
        tp.setTaskid(task.getTaskid());
        tp.setCourseid(task.getCourseid());
        tp.setTasktype(task.getTasktype());
        tp.setCriteria(1);
        tp.setUserid(this.logined(request).getRef());
        tp.setIsright(1);
        List<TaskPerformanceInfo>tList=this.taskPerformanceManager.getList(tp,null);
        if(taskCriList!=null&&taskCriList.size()>0&&taskCriList.get(0).getTaskstatus()!=null
                &&!taskCriList.get(0).getTaskstatus().equals("1")&&!taskCriList.get(0).getTaskstatus().equals("3")){
            if(tList!=null&&tList.size()>0){
                response.sendRedirect("tpres?toStudentIdx&courseid="+courseid+"&tpresdetailid="+tpresdetailid+"&taskid="+taskid+"&groupid="+groupid);
            }else{
                if(this.taskPerformanceManager.doSave(tp)){
                    /*奖励加分*/
                    //得到班级ID
                    TpTaskAllotInfo tallot=new TpTaskAllotInfo();
                    tallot.setTaskid(Long.parseLong(taskid));

                    tallot.getUserinfo().setUserid(this.logined(request).getUserid());
                    List<Map<String,Object>> clsMapList=this.tpTaskAllotManager.getClassId(tallot);
                    if(clsMapList==null||clsMapList.size()<1||clsMapList.get(0)==null||!clsMapList.get(0).containsKey("CLASS_ID")
                            ||clsMapList.get(0).get("CLASS_ID")==null){
                        je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                        response.getWriter().println(je.toJSON());return ;
                    }

                    //taskinfo:   4:成卷测试  5：自主测试   6:微视频
                    //规则转换:    6             7         8
                    Integer type=0;
                    switch(taskCriList.get(0).getTasktype()){
                        case 3:     //试题
                            type=1;break;
                        case 1:     //资源学习
                            type=2;break;
                        case 2:
                            type=4;
                            break;
                    }
                    String jid=this.logined(request).getEttuserid()==null?null:this.logined(request).getEttuserid().toString();


                    String msg=null;
                        /*奖励加分通过*/
                    if(this.tpStuScoreLogsManager.awardStuScore(taskCriList.get(0).getCourseid()
                            , Long.parseLong(clsMapList.get(0).get("CLASS_ID").toString())
                            , taskCriList.get(0).getTaskid()
                            , Long.parseLong(this.logined(request).getUserid() + ""),jid, type,this.logined(request).getDcschoolid())){
                        msg="查看并提交心得:恭喜您,获得了1积分和1蓝宝石(没有调用接口)";
                    //    request.getSession().setAttribute("msg",msg);
                    }else
                        System.out.println("awardScore err ");
                    response.sendRedirect("tpres?toStudentIdx&courseid="+courseid+"&tpresdetailid="+tpresdetailid+"&taskid="+taskid+"&groupid="+groupid);
                }else{
                    je.setMsg("异常错误!添加查看记录失败!请重试!");
                    response.getWriter().print(je.toJSON());
                }
            }
        }else{
            response.sendRedirect("tpres?toStudentIdx&courseid="+courseid+"&tpresdetailid="+tpresdetailid+"&taskid="+taskid+"&groupid="+groupid);
        }
    }


    /**
     * 查询任务建议
     * @throws Exception
     */
    @RequestMapping(params="m=ajaxTaskSuggest",method=RequestMethod.POST)
    public void loadTaskSuggest(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");
        if(courseid==null||courseid.trim().length()<1||taskid==null||taskid.length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return;
        }
        PageResult pageResult=this.getPageResultParameter(request);
        TaskSuggestInfo suggestInfo=new TaskSuggestInfo();
        suggestInfo.setCourseid(Long.parseLong(courseid));
        suggestInfo.setTaskid(Long.parseLong(taskid));
        List<TaskSuggestInfo>suggestList=this.taskSuggestManager.getList(suggestInfo,pageResult);
        pageResult.setList(suggestList);
        je.setPresult(pageResult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 进入任务建议页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=toTaskSuggestList",method=RequestMethod.GET)
    public ModelAndView toTaskSuggest(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");
        if(courseid==null||courseid.trim().length()<1||taskid==null||taskid.length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        request.setAttribute("courseid",courseid);
        request.setAttribute("taskid",taskid);
        return new ModelAndView("/teachpaltform/task/teacher/task-suggest");
    }

    /**
     * 任务完成情况统计
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toTaskPerformance",method=RequestMethod.GET)
    public ModelAndView toTaskPerformanceInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String taskid=request.getParameter("taskid");
        String questype = request.getParameter("questype");
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //获取任务对象
        TpTaskInfo ti = new TpTaskInfo();
        ti.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo> tiList = this.tpTaskManager.getList(ti,null);
        request.setAttribute("taskInfo",tiList.get(0));
        //获取试题
        if(tiList.get(0).getTasktype()==3||tiList.get(0).getTasktype()==4){
            QuestionOption qo = new QuestionOption();
            qo.setQuestionid(tiList.get(0).getTaskvalueid());
            PageResult pr = new PageResult();
            pr.setPageNo(0);
            pr.setPageSize(0);
            pr.setOrderBy("u.option_type");
            List<QuestionOption> optionList = this.questionOptionManager.getList(qo,pr);
            for(QuestionOption o : optionList){
                if(o.getIsright()==1){
                    request.setAttribute("rightid",o.getRef());
                }
            }
        }
        //获取任务相关的班级
        TpTaskAllotInfo ta = new TpTaskAllotInfo();
        ta.setTaskid(Long.parseLong(taskid));
        List<TpTaskAllotInfo> taList = this.tpTaskAllotManager.getList(ta,null);

        List<Map> classList = new ArrayList<Map>();
        for(TpTaskAllotInfo o:taList){
            if(o.getUsertype()==0){
                ClassInfo ci = new ClassInfo();
                ci.setClassid(Integer.parseInt(o.getUsertypeid().toString()));
                List<ClassInfo> ciList = this.classManager.getList(ci,null);
                Map map = new HashMap();
                map.put("classid",ciList.get(0).getClassid());
                map.put("classname",ciList.get(0).getClassname());
                map.put("classtype",1);
                classList.add(map);
            }else if(o.getUsertype()==1){
                TpVirtualClassInfo vci = new TpVirtualClassInfo();
                vci.setVirtualclassid(Integer.parseInt(o.getUsertypeid().toString()));
                List<TpVirtualClassInfo> vciList = this.tpVirtualClassManager.getList(vci,null);
                Map map = new HashMap();
                map.put("classid",vciList.get(0).getVirtualclassid());
                map.put("classname",vciList.get(0).getVirtualclassname());
                map.put("classtype",2);
                classList.add(map);
            }else if(o.getUsertype()==2){
                TpGroupInfo tg = new TpGroupInfo();
                tg.setGroupid(o.getUsertypeid());
                List<TpGroupInfo> tgList = this.tpGroupManager.getList(tg,null);
                Integer classid=tgList.get(0).getClassid();
                if(tgList.get(0).getClasstype()==1){
                    ClassInfo ci = new ClassInfo();
                    ci.setClassid(classid);
                    List<ClassInfo> objList = this.classManager.getList(ci,null);
                    Map map = new HashMap();
                    map.put("classid",objList.get(0).getClassid());
                    map.put("classname",objList.get(0).getClassname());
                    map.put("classtype",8);
                    if(classList.size()>0){
                        Boolean b = false;
                        for(Map m:classList){
                            if(Integer.parseInt(m.get("classid").toString())==Integer.parseInt(map.get("classid").toString())){
                                b=true;
                            }
                        }
                        if(b==false)
                            classList.add(map);
                    }else{
                        classList.add(map);
                    }
                }else{
                    TpVirtualClassInfo vc = new TpVirtualClassInfo();
                    vc.setVirtualclassid(classid);
                    List<TpVirtualClassInfo> objList = this.tpVirtualClassManager.getList(vc,null);
                    Map map = new HashMap();
                    map.put("classid",objList.get(0).getVirtualclassid());
                    map.put("classname",objList.get(0).getVirtualclassname());
                    map.put("classtype",9);
                    if(classList.size()>0){
                        Boolean b = false;
                        for(Map m:classList){
                            if(Integer.parseInt(m.get("classid").toString())==Integer.parseInt(map.get("classid").toString())){
                                b=true;
                            }
                        }
                        if(b==false)
                            classList.add(map);
                    }else{
                        classList.add(map);
                    }
                }


            }
        }
        request.setAttribute("courseid",taList.get(0).getCourseid());
        request.setAttribute("taskid",taskid);
        request.setAttribute("questype",questype);
        request.setAttribute("classList",classList);
        if(questype.equals("3")||questype.equals("4")){
            return new ModelAndView("/teachpaltform/task/teacher/task-performance-xz");
        }else if(questype.equals("-1")){//试卷
            return new ModelAndView("/teachpaltform/task/teacher/paper-task-performance");
        }else if(questype.equals("-2")){//直播课
            return new ModelAndView("/teachpaltform/task/teacher/live-task-performance");
        }else{
            return new ModelAndView("/teachpaltform/task/teacher/task-performance-zg");
        }
    }

    /**
     * 得到并生成图片
     * @param classid
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value="/img/{taskid}/{classid}/{classtype}/op",method = {RequestMethod.GET,RequestMethod.GET})
    public void getOptTJImg(@PathVariable("taskid") Long taskid,
                            @PathVariable("classid") Long classid,
                            @PathVariable("classtype") Long classtype,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        if(taskid==null||classid==null||classtype==null)return;
        //得到相关统计值
        //数量统计
        List<Map<String,Object>> optionnumList = new ArrayList<Map<String, Object>>();
        if(classtype==8||classtype==9){
            optionnumList = this.taskPerformanceManager.getPerformanceOptionNum2(taskid,classid);
        }else{
            optionnumList = this.taskPerformanceManager.getPerformanceOptionNum(taskid, classid);
        }

        //动态拼成想要的选项表分布比例
        DecimalFormat di = new DecimalFormat("#.00");
        //以下是生成统计图
        DefaultPieDataset dataset = new DefaultPieDataset();
        if(optionnumList==null||optionnumList.size()<1){
            dataset.setValue("没有数据",0);
        }else{
            for(Map<String,Object> tmpMap:optionnumList){
                if(tmpMap.containsKey("OPTION_TYPE")&&tmpMap.containsKey("NUM"))
                    dataset.setValue(tmpMap.get("OPTION_TYPE").toString(),Integer.parseInt(tmpMap.get("NUM").toString()));
            }
        }
        JFreeChart chart = ChartFactory.createPieChart("", dataset, true, false, false);
        FileOutputStream fos = null;
        String imgRealPath=request.getRealPath("/")+"userUploadFile/optionPie/op"+taskid+classid+classtype+".png";
        try{
            fos = new FileOutputStream(imgRealPath);
            ChartUtilities.writeChartAsPNG(fos, chart, 193, 140);

        }finally{
            fos.close();
        }
        //清理
        System.gc();
        //读取图片流
        UtilTool.writeImage(response,imgRealPath);
    }
    /**
     * 查询学生任务完成情况
     * @throws Exception
     */
    @RequestMapping(params="xzloadStuPerformance",method=RequestMethod.POST)
    public void xzloadStuPerformance(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String classid=request.getParameter("classid");
        String taskid=request.getParameter("taskid");
        String questype = request.getParameter("questype");
        String questionid=request.getParameter("questionid");
        if(classid==null||classid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TaskPerformanceInfo t=new TaskPerformanceInfo();
        t.setTaskid(Long.parseLong(taskid));
        Long clsid=null;
        String type=request.getParameter("classtype");
        if(classid!=null&&classid.length()>0){
            clsid=Long.parseLong(classid);
        }else{
            clsid=Long.parseLong("0");
        }

        //任务记录
        List<TaskPerformanceInfo>tList=this.taskPerformanceManager.getPerformListByTaskid(t, clsid,Integer.parseInt(type));
        //数量统计
        List<Map<String,Object>> optionnumList = new ArrayList<Map<String, Object>>();
        //数量统计
        List<Map<String,Object>> numList = new ArrayList<Map<String, Object>>();
        List<TpGroupInfo> tiList=new ArrayList<TpGroupInfo>();
        if(Integer.parseInt(type)==8||Integer.parseInt(type)==9){
            numList=this.taskPerformanceManager.getPerformanceNum2(Long.parseLong(taskid),clsid);
            optionnumList = this.taskPerformanceManager.getPerformanceOptionNum2(Long.parseLong(taskid),clsid);
            TpGroupStudent tgsinfo = new TpGroupStudent();
            tgsinfo.setClassid(Integer.parseInt(classid));
            List<TpGroupStudent> tgsList = this.tpGroupStudentManager.getGroupStudentByClass(tgsinfo,null);
            TpGroupInfo ti = new TpGroupInfo();
            ti.setClassid(Integer.parseInt(classid));
            tiList = this.tpGroupManager.getList(ti,null);
            for(int i = 0;i<tiList.size();i++){
                TpGroupStudent ts = new TpGroupStudent();
                for(int j = 0;j<tgsList.size();j++){
                    if(tiList.get(i).getGroupid()==tgsList.get(j).getGroupid()){
                        tiList.get(i).setTpgroupstudent(tgsList);
                    }
                }
            }
        }else{
            numList=this.taskPerformanceManager.getPerformanceNum(Long.parseLong(taskid),clsid,Integer.parseInt(type));
            optionnumList = this.taskPerformanceManager.getPerformanceOptionNum(Long.parseLong(taskid),clsid);
        }
        QuestionOption qo = new QuestionOption();
        qo.setQuestionid(Long.parseLong(questionid));
        PageResult pr = new PageResult();
        pr.setPageNo(0);
        pr.setPageSize(0);
        pr.setOrderBy("u.option_type");
        List<QuestionOption> optionList = this.questionOptionManager.getList(qo,pr);
        int totalNum = 0;
        for(int i =0;i<optionnumList.size();i++){
            totalNum+=Integer.parseInt(optionnumList.get(i).get("NUM").toString());
        }
        List<Map<String,Object>> option = new ArrayList<Map<String, Object>>();
        //动态拼成想要的选项表分布比例
        DecimalFormat di = new DecimalFormat("#.00");
        for(QuestionOption o:optionList){
            Map m = new HashMap();
            if(optionnumList.size()>0){
                for(int i =0;i<optionnumList.size();i++){
                    if(o.getOptiontype().equals(optionnumList.get(i).get("OPTION_TYPE"))){
                        m.put("OPTION_TYPE",o.getOptiontype());
                        m.put("NUM",di.format((double)Integer.parseInt(optionnumList.get(i).get("NUM").toString())/totalNum*100));
                        break;
                    }else{
                        m.put("OPTION_TYPE",o.getOptiontype());
                        m.put("NUM",0);
                    }
                }
            }else{
                m.put("OPTION_TYPE",o.getOptiontype());
                m.put("NUM",0);
            }
            option.add(m);
        }
        //以下是生成统计图
        DefaultPieDataset dataset = new DefaultPieDataset();
        for(Map o:optionnumList){
            dataset.setValue(o.get("OPTION_TYPE").toString(),Integer.parseInt(o.get("NUM").toString()));
        }
        JFreeChart chart = ChartFactory.createPieChart("", dataset, true, false, false);
        FileOutputStream fos = null;
        Date d = new Date();
        try{
            fos = new FileOutputStream(request.getRealPath("/")+"userUploadFile/optionPie/"+d.getTime()+"taskPie.png");
            ChartUtilities.writeChartAsPNG(fos, chart, 193, 140);
        }finally{
            fos.close();
        }
        //未完成人数
        TpTaskInfo task=new TpTaskInfo();
        task.setTaskid(Long.parseLong(taskid));
        Integer cid=null;
        if(classid!=null&&!classid.equals("0"))
            cid=Integer.parseInt(classid);

        List<UserInfo>notCompleteList=this.userManager.getUserNotCompleteTask(task.getTaskid(),null,cid,"1");
        je.getObjList().add(numList);
        je.getObjList().add(option);
        je.getObjList().add(tList);
        je.getObjList().add(tiList);
        je.getObjList().add(notCompleteList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }
    /**
     * 查询学生任务完成情况
     * @throws Exception
     */
    @RequestMapping(params="zgloadStuPerformance",method=RequestMethod.POST)
    public void zgloadStuPerformance(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String classid=request.getParameter("classid");
        String taskid=request.getParameter("taskid");
        String questype = request.getParameter("questype");
        String type=request.getParameter("classtype");
        if(classid==null||classid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TaskPerformanceInfo t=new TaskPerformanceInfo();
        t.setTaskid(Long.parseLong(taskid));
        Long clsid=null;
        if(classid!=null&&classid.length()>0&&!classid.equals("null")){
            clsid=Long.parseLong(classid);
        }else{
            clsid=Long.parseLong("0");
        }
        //任务记录
        List<TaskPerformanceInfo>tList=this.taskPerformanceManager.getPerformListByTaskid(t,clsid,Integer.parseInt(type));
        //数量统计
        List<Map<String,Object>> numList = new ArrayList<Map<String, Object>>();
        List<TpGroupInfo> tiList=new ArrayList<TpGroupInfo>();
        if(Integer.parseInt(type)==8||Integer.parseInt(type)==9){
            numList=this.taskPerformanceManager.getPerformanceNum2(Long.parseLong(taskid),clsid);
            TpGroupStudent tgsinfo = new TpGroupStudent();
            tgsinfo.setClassid(Integer.parseInt(classid));
            List<TpGroupStudent> tgsList = this.tpGroupStudentManager.getGroupStudentByClass(tgsinfo,null);
            TpGroupInfo ti = new TpGroupInfo();
            ti.setClassid(Integer.parseInt(classid));
            tiList = this.tpGroupManager.getList(ti,null);
            for(int i = 0;i<tiList.size();i++){
                TpGroupStudent ts = new TpGroupStudent();
                for(int j = 0;j<tgsList.size();j++){
                    if(tiList.get(i).getGroupid().toString().equals(tgsList.get(j).getGroupid().toString())){
                        tiList.get(i).setTpgroupstudent2(tgsList.get(j));
                    }
                }
            }
        }else{
            numList=this.taskPerformanceManager.getPerformanceNum(Long.parseLong(taskid),clsid,Integer.parseInt(type));
        }
        //未完成人数
        TpTaskInfo task=new TpTaskInfo();
        task.setTaskid(Long.parseLong(taskid));
        Integer cid=null;
        if(classid!=null&&!classid.equals("0"))
            cid=Integer.parseInt(classid);

        List<UserInfo>notCompleteList=this.userManager.getUserNotCompleteTask(task.getTaskid(),null,cid,"1");
        je.getObjList().add(numList);
        je.getObjList().add(tList);
        je.getObjList().add(tiList);
        je.getObjList().add(notCompleteList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 微视频试卷 查看回答
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="loadStuMicQuesPerformance",method=RequestMethod.GET)
    public ModelAndView loadStuMicQuesPerformance(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je=new JsonEntity();
        String courseid=request.getParameter("courseid");
        String questionid=request.getParameter("questionid");
        String paperid=request.getParameter("paperid");
        String taskid=request.getParameter("taskid");
        String type=request.getParameter("type"); //1:主观 2:客观
        if(courseid==null||courseid.trim().length()<1
                ||questionid==null||questionid.trim().length()<1
                ||type==null||type.trim().length()<1
                ||paperid==null||paperid.trim().length()<1
                ||taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        List<Map<String,Object>>allquesidList=paperQuestionManager.getPaperQuesAllId(Long.parseLong(paperid));
        if(allquesidList==null||allquesidList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }

        StuPaperQuesLogs logs=new StuPaperQuesLogs();
        logs.setPaperid(Long.parseLong(paperid));
        logs.setQuesid(Long.parseLong(questionid));
        logs.setTaskid(Long.parseLong(taskid));
        List<StuPaperQuesLogs>logsList=this.stuPaperQuesLogsManager.getList(logs,null);
        mp.put("logList",logsList);
        if(type.equals("1")){
            return new ModelAndView("/teachpaltform/task/teacher/mic-paper-zg", mp);
        }else{
            List<Map<String,Object>> numList =this.taskPerformanceManager.getPerformanceNum(Long.parseLong(taskid),null,null);
            List<Map<String,Object>> optionnumList =this.taskPerformanceManager.getMicPerformanceOptionNum(Long.parseLong(taskid),Long.parseLong(questionid));
            QuestionOption qo = new QuestionOption();
            qo.setQuestionid(Long.parseLong(questionid));
            PageResult pr = new PageResult();
            pr.setPageNo(0);
            pr.setPageSize(0);
            pr.setOrderBy("u.option_type");
            List<QuestionOption> optionList = this.questionOptionManager.getList(qo,pr);
            int totalNum = 0;
            for(int i =0;i<optionnumList.size();i++){
                totalNum+=Integer.parseInt(optionnumList.get(i).get("NUM").toString());
            }
            List<Map<String,Object>> option = new ArrayList<Map<String, Object>>();
            //动态拼成想要的选项表分布比例
            DecimalFormat di = new DecimalFormat("#.00");
            for(QuestionOption o:optionList){
                Map m = new HashMap();
                if(optionnumList.size()>0){
                    for(int i =0;i<optionnumList.size();i++){
                        if(o.getOptiontype().equals(optionnumList.get(i).get("OPTION_TYPE"))){
                            m.put("OPTION_TYPE",o.getOptiontype());
                            m.put("NUM",di.format((double)Integer.parseInt(optionnumList.get(i).get("NUM").toString())/totalNum*100));
                            break;
                        }else{
                            m.put("OPTION_TYPE",o.getOptiontype());
                            m.put("NUM",0);
                        }
                    }
                }else{
                    m.put("OPTION_TYPE",o.getOptiontype());
                    m.put("NUM",0);
                }
                option.add(m);
            }
            //以下是生成统计图
            DefaultPieDataset dataset = new DefaultPieDataset();
            for(Map o:optionnumList){
                dataset.setValue(o.get("OPTION_TYPE").toString(),Integer.parseInt(o.get("NUM").toString()));
            }
            JFreeChart chart = ChartFactory.createPieChart("", dataset, true, false, false);
            FileOutputStream fos = null;

            try{
                fos = new FileOutputStream(request.getRealPath("/")+"images/taskMicPie.png");
                ChartUtilities.writeChartAsPNG(fos, chart, 193, 140);
            }finally{
                fos.close();
            }

            mp.put("numList",numList);
            mp.put("optionList",option);

            Integer right=0;
            if(option!=null)
            {
                for(StuPaperQuesLogs p:logsList){
                    if(p.getIsright()!=null&&p.getIsright().toString().equals("1"))
                        right+=1;
                }
            }
            mp.put("right",right);
            return new ModelAndView("/teachpaltform/task/teacher/mic-paper-kg", mp);
        }
    }
    /**
     * 查询学生试卷任务完成情况
     * @throws Exception
     */
    @RequestMapping(params="loadPaperPerformance",method=RequestMethod.POST)
    public void loadPaperPerformance(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String classid=request.getParameter("classid");
        String taskid=request.getParameter("taskid");
        String type=request.getParameter("classtype");
        String orderstr=request.getParameter("orderstr");
        if(classid==null||classid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TaskPerformanceInfo t=new TaskPerformanceInfo();
        t.setTaskid(Long.parseLong(taskid));
        Long clsid=null;
        if(classid!=null&&classid.length()>0&&!classid.equals("null")){
            clsid=Long.parseLong(classid);
        }else{
            clsid=Long.parseLong("0");
        }
        if(orderstr!=null&&orderstr.trim().length()>0)
            t.setOrderstr(orderstr);
        else
            t.setOrderstr("t.c_time desc");
        //未完成人数
        TpTaskInfo task=new TpTaskInfo();
        task.setTaskid(Long.parseLong(taskid));
        Integer cid=null;
        if(classid!=null&&!classid.equals("0"))
            cid=Integer.parseInt(classid);

        List<UserInfo>notCompleteList=this.userManager.getUserNotCompleteTask(task.getTaskid(),null,cid,"1");
        //任务记录
        List<TaskPerformanceInfo>tList=this.taskPerformanceManager.getPerformListByTaskid(t,clsid,Integer.parseInt(type));
        //数量统计
        List<Map<String,Object>> numList;
        numList = new ArrayList<Map<String, Object>>();
        List<TpGroupInfo> tiList=new ArrayList<TpGroupInfo>();
        List<TpGroupInfo>tmpGroupList=new ArrayList<TpGroupInfo>();
        if(Integer.parseInt(type)==8||Integer.parseInt(type)==9){
            numList=this.taskPerformanceManager.getPerformanceNum2(Long.parseLong(taskid),clsid);
            TpGroupStudent tgsinfo = new TpGroupStudent();
            tgsinfo.setClassid(Integer.parseInt(classid));
            List<TpGroupStudent> tgsList = this.tpGroupStudentManager.getGroupStudentByClass(tgsinfo,null);


            TpGroupInfo ti = new TpGroupInfo();
            ti.setClassid(Integer.parseInt(classid));
            tiList = this.tpGroupManager.getList(ti,null);


            TpTaskAllotInfo ta=new TpTaskAllotInfo();
            ta.setTaskid(Long.parseLong(taskid));
            ta.setUsertype(2);
            List<TpTaskAllotInfo> taskAllotInfoList=this.tpTaskAllotManager.getList(ta,null);
            if(taskAllotInfoList!=null&&taskAllotInfoList.size()>0&&tiList!=null){
                for(TpTaskAllotInfo allotInfo:taskAllotInfoList){
                    for(TpGroupInfo groupInfo:tiList){
                        if(allotInfo.getUsertypeid().equals(groupInfo.getGroupid()))
                            tmpGroupList.add(groupInfo);
                    }
                }
            }

            if(tmpGroupList.size()>0){
                for(int i = 0;i<tmpGroupList.size();i++){
                    for(int j = 0;j<tgsList.size();j++){
                        if(tmpGroupList.get(i).getGroupid().toString().equals(tgsList.get(j).getGroupid().toString())){
                            tmpGroupList.get(i).setTpgroupstudent2(tgsList.get(j));
                        }
                    }
                }
            }

        }else{
            numList=this.taskPerformanceManager.getPerformanceNum(Long.parseLong(taskid),clsid,Integer.parseInt(type));
        }
        je.getObjList().add(numList);
        je.getObjList().add(tList);
        je.getObjList().add(tmpGroupList);
        je.getObjList().add(notCompleteList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 进入学生端，查看个人任务完成情况页面
     * */
    @RequestMapping(params="m=tostuSelfPerformance",method=RequestMethod.GET)
    public ModelAndView toloadStuSelfPerformance(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        String termid=request.getParameter("termid");
        String subjectid=request.getParameter("subjectid");
        List<Map<String,Object>> performanceList = this.taskPerformanceManager.getStuSelfPerformance(this.logined(request).getUserid(),Long.parseLong("0"),0,termid,Integer.parseInt(subjectid));
        List<Map<String,Object>> courseObj = new ArrayList<Map<String, Object>>();
        for(Map o:performanceList){
            Map m = new HashMap();
            m.put("courseid",o.get("COURSE_ID"));
            m.put("coursename",o.get("COURSE_NAME"));
            courseObj.add(m);
        }
        mp.put("course",courseObj);
        mp.put("userid",this.logined(request).getUserid());
        return new ModelAndView("/teachpaltform/task/student/task-stu-performance", mp);
    }

    /**
     * 教师端，查看学生个人任务完成情况页面
     * */
    @RequestMapping(params="m=showStuSelfPerformance",method=RequestMethod.GET)
    public ModelAndView teacherToStuselfPerformance(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        String termid=request.getParameter("termid");
        String subjectid=request.getParameter("subjectid");
        Integer userid=Integer.parseInt(request.getParameter("userid"));
        List<Map<String,Object>> performanceList = this.taskPerformanceManager.getStuSelfPerformance(userid,Long.parseLong("0"),0,termid,Integer.parseInt(subjectid));
        List<Map<String,Object>> courseObj = new ArrayList<Map<String, Object>>();
        for(Map o:performanceList){
            Map m = new HashMap();
            m.put("courseid",o.get("COURSE_ID"));
            m.put("coursename",o.get("COURSE_NAME"));
            courseObj.add(m);
        }
        mp.put("course",courseObj);
        return new ModelAndView("/teachpaltform/task/teacher/stu-task-performance", mp);
    }

    /**
     * 学生端，查看个人任务完成情况
     * */
    @RequestMapping(params="m=stuSelfPerformance",method=RequestMethod.POST)
    public void loadStuSelfPerformance(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String courseid = request.getParameter("courseid");
        String termid=request.getParameter("termid");
        String subjectid=request.getParameter("subjectid");
        if(courseid==null)
            courseid="0";
        List<Map<String,Object>> performanceList = this.taskPerformanceManager.getStuSelfPerformance(this.logined(request).getUserid(),Long.parseLong(courseid),1,termid,Integer.parseInt(subjectid));
        Integer totalNum=performanceList.size();
        Integer completeNum=0;
        Integer unBeginNum=0;
        Integer endUnCompleteNum=0;
        for(Map o :performanceList){
            String bdatestr=o.get("B_TIME").toString();
            String edatestr=o.get("E_TIME").toString();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currTime = df.parse(df.format(new Date()));
            Date bDate = df.parse(bdatestr);
            Date eDate=df.parse(edatestr);
            if(bDate.after(currTime)){  //before
                unBeginNum++;
            }
            if(Integer.parseInt(o.get("STATUS").toString())>0){
                completeNum++;
            }
            if(Integer.parseInt(o.get("STATUS").toString())==0&&eDate.before(currTime)){
                endUnCompleteNum++;
            }
        }
        Integer unCompleteNum=totalNum-completeNum-unBeginNum-endUnCompleteNum;
        List<Map<String,Object>> performanceListNum=new ArrayList<Map<String, Object>>();
        Map o = new HashMap();
        o.put("totalNum",totalNum);
        o.put("completeNum",completeNum);
        o.put("unBeginNum",unBeginNum);
        o.put("endUnCompleteNum",endUnCompleteNum);
        o.put("unCompleteNum",unCompleteNum);
        performanceListNum.add(o);
        //List<Map<String,Object>> performanceListNum=this.taskPerformanceManager.getStuSelfPerformanceNum(this.logined(request).getUserid(),Long.parseLong(courseid),1,termid,Integer.parseInt(subjectid));
        je.getObjList().add(performanceList);
        je.getObjList().add(performanceListNum);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 恢复回收站中的任务
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doRestoreTask",method=RequestMethod.POST)
    public void doRestoreTask(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String taskid=request.getParameter("taskid");
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }

        TpTaskInfo t=new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo>taskList=this.tpTaskManager.getList(t, null);
        if(taskList==null||taskList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }

        //批量操作记录
        List<List<Object>> objListArray=new ArrayList<List<Object>>();
        List<String> sqlStrList=new ArrayList<String>();
        StringBuilder sql=null;
        List<Object>objList=null;




        TpTaskInfo tmpTask=taskList.get(0);

        TpTaskInfo sel=new TpTaskInfo();
        sel.setCourseid(tmpTask.getCourseid());
        //查询没被我删除的任务
        sel.setSelecttype(1);
        sel.setLoginuserid(this.logined(request).getUserid());
        sel.setStatus(1);

        //已发布的任务
        List<TpTaskInfo>taskReleaseList=this.tpTaskManager.getTaskReleaseList(sel, null);
        Integer orderIdx=1;
        if(taskReleaseList!=null&&taskReleaseList.size()>0)
            orderIdx+=taskReleaseList.size();


        TpTaskInfo taskInfo=new TpTaskInfo();
        taskInfo.setStatus(1);
        taskInfo.setTaskid(Long.parseLong(taskid));
        taskInfo.setOrderidx(orderIdx);
        sql=new StringBuilder();
        objList=this.tpTaskManager.getUpdateSql(taskInfo,sql);
        if(sql!=null&&sql.toString().trim().length()>0){
            objListArray.add(objList);
            sqlStrList.add(sql.toString());
        }
        if(this.tpTaskManager.doExcetueArrayProc(sqlStrList,objListArray)){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }


    /**
     * 给未完成任务的学生发提醒
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doSendTaskMsg",method=RequestMethod.POST)
    public void doSendTaskMsg(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String uidArray=request.getParameter("uidArray");
        String taskid=request.getParameter("taskid");
        String usertypeid=request.getParameter("usertypeid");
        if(uidArray==null||uidArray.trim().length()<1
                ||taskid==null||taskid.trim().length()<1
            //||usertypeid==null||usertypeid.trim().length()<1
                ){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String[] useridArray=uidArray.split(",");
        String userName="";
        for(String uid:useridArray){
            UserInfo u=new UserInfo();
            u.setUserid(Integer.parseInt(uid));
            List<UserInfo>userInfoList=this.userManager.getList(u,null);
            if(userInfoList!=null&&userInfoList.size()>0){
                if(userName.length()>0)
                    userName+=";";
                userName+=userInfoList.get(0).getUsername();
            }
        }

        TpTaskInfo taskInfo=new TpTaskInfo();
        taskInfo.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo>tpTaskInfoList=this.tpTaskManager.getList(taskInfo,null);
        if(tpTaskInfoList==null||tpTaskInfoList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo tmpTask=tpTaskInfoList.get(0);

        TpTaskAllotInfo ta=new TpTaskAllotInfo();
        ta.setTaskid(tmpTask.getTaskid());
        if(usertypeid!=null&&!usertypeid.equals("null")&&!"0".equals(usertypeid))
            ta.setUsertypeid(Long.parseLong(usertypeid));
        List<TpTaskAllotInfo>taskAllotInfoList=this.tpTaskAllotManager.getList(ta,null);
        if(tpTaskInfoList==null||tpTaskInfoList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }
        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();
        for (TpTaskAllotInfo tmpTaskAllot:taskAllotInfoList){
            //任务1：资源学习 野生动物.wmv 截止时间是XXX（此处具体到分钟即可），特提醒你尽快完成任务。

            String content="";
            try {
                content="任务："+tmpTask.getTaskTypeName()+" "+tmpTask.getTaskobjname()+" 截止时间是"+tmpTaskAllot.getEtimeString()+"，请尽快完成任务!";
            }catch (Exception e){
                content="";
            }
            SmsInfo smsInfo=new SmsInfo();
            smsInfo.setReceiverlist(userName);
            smsInfo.setSmstitle("任务未完成提醒");
            smsInfo.setSenderid(this.logined(request).getUserid());
            smsInfo.setSmscontent(content);
            smsInfo.setSmsstatus(1);
            sql=new StringBuilder();
            objList=this.smsManager.getSaveSql(smsInfo,sql);
            if(sql!=null&&objList!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }
        if(this.smsManager.doExcetueArrayProc(sqlListArray,objListArray)){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }

    /**
     * 获取小组名单
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=loadGroupStudent",method=RequestMethod.POST)
    public void loadGroupStudent(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String groupid=request.getParameter("groupid");
        if(groupid==null||groupid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpGroupStudent gs=new TpGroupStudent();
        gs.setGroupid(Long.parseLong(groupid));
        List<TpGroupStudent>groupStudentList=this.tpGroupStudentManager.getList(gs,null);
        je.setObjList(groupStudentList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 进入im端任务统计详情页面
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=toImTaskInfo",method={RequestMethod.POST,RequestMethod.GET})
    public ModelAndView toImTaskInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String taskid = request.getParameter("taskid");
        String classid = request.getParameter("classid");
        String type = request.getParameter("type");//1：教师 2：学生 用来判断班级是否显示
        //
        if(taskid==null||classid==null||type==null){
            je.setMsg("缺少参数");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //首先查询任务详情
        TpTaskInfo taskInfo = new TpTaskInfo();
        taskInfo.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo> taskInfoList = this.tpTaskManager.getList(taskInfo,null);
        if(taskInfoList==null||taskInfoList.size()==0){
            je.setMsg("当前任务不存在，请刷新页面重试");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        request.setAttribute("taskinfo",taskInfoList.get(0));
        //接下来查询任务的回答列表
        List<Map<String,Object>> taskUserRecord = new ArrayList<Map<String, Object>>();
        List<Map<String,Object>> returnUserRecord = new ArrayList<Map<String, Object>>();
        Map returnUserMap = null;
        if(type.equals("1")){
            taskUserRecord = this.imInterfaceManager.getTaskUserRecord(Long.parseLong(taskid),Integer.parseInt(classid),Integer.parseInt("0"),null);
        }else{
            taskUserRecord = this.imInterfaceManager.getTaskUserRecord(Long.parseLong(taskid),Integer.parseInt(classid),Integer.parseInt("0"),this.logined(request).getUserid());
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
                returnUserRecord.add(returnUserMap);
            }
            String jidstr = jids.toString().substring(0,jids.toString().lastIndexOf(","))+"]";
            String url="http://192.168.10.26:8008/study-im-service-1.0/queryPhotoAndRealName.do";
            //String url = "http://wangjie.etiantian.com:8080/queryPhotoAndRealName.do";
            HashMap<String,String> signMap = new HashMap();
            signMap.put("userList",jidstr);
            signMap.put("schoolId",this.logined(request).getDcschoolid()+"");
            signMap.put("srcJid",this.logined(request).getEttuserid()+"");
            signMap.put("userType","3");
            signMap.put("timestamp",""+System.currentTimeMillis());
            String signture = UrlSigUtil.makeSigSimple("queryPhotoAndRealName.do", signMap, "*ETT#HONER#2014*");
            signMap.put("sign",signture);
            JSONObject jsonObject = UtilTool.sendPostUrl(url,signMap,"utf-8");
            int t = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
            if(t==1){
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
        if(type.equals("1")){
            //获取任务相关的班级
            TpTaskAllotInfo ta = new TpTaskAllotInfo();
            ta.setTaskid(Long.parseLong(taskid));
            List<TpTaskAllotInfo> taList = this.tpTaskAllotManager.getList(ta,null);

            List<Map> classList = new ArrayList<Map>();
            for(TpTaskAllotInfo o:taList){
                if(o.getUsertype()==0){
                    ClassInfo ci = new ClassInfo();
                    ci.setClassid(Integer.parseInt(o.getUsertypeid().toString()));
                    List<ClassInfo> ciList = this.classManager.getList(ci,null);
                    Map map = new HashMap();
                    map.put("classid",ciList.get(0).getClassid());
                    map.put("classname",ciList.get(0).getClassname());
                    map.put("classtype",1);
                    classList.add(map);
                }else if(o.getUsertype()==2){//小组，要获取小组属于的班级
                    TpGroupInfo tg = new TpGroupInfo();
                    tg.setGroupid(o.getUsertypeid());
                    List<TpGroupInfo> tgList = this.tpGroupManager.getList(tg,null);
                    Integer clsid=tgList.get(0).getClassid();
                    if(tgList.get(0).getClasstype()==1){
                        ClassInfo ci = new ClassInfo();
                        ci.setClassid(clsid);
                        List<ClassInfo> objList = this.classManager.getList(ci,null);
                        Map map = new HashMap();
                        map.put("classid",objList.get(0).getClassid());
                        map.put("classname",objList.get(0).getClassname());
                        map.put("classtype",2);
                        if(classList.size()>0){
                            Boolean b = false;
                            for(Map m:classList){
                                if(Integer.parseInt(m.get("classid").toString())==Integer.parseInt(map.get("classid").toString())){
                                    b=true;
                                }
                            }
                            if(b==false)
                                classList.add(map);
                        }else{
                            classList.add(map);
                        }
                    }
                }
            }
            request.setAttribute("classlist",classList);
        }
        request.setAttribute("type",type);
        return new ModelAndView("/teachpaltform/task/student/im-task-detail");
    }
    @RequestMapping(params="m=stuQuesAnserList")
    public ModelAndView stuToQuesAnswerList(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        String taskid=request.getParameter("taskid");
        JsonEntity jsonEntity=new JsonEntity();
        //验证参数
        if(taskid==null||taskid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        //查找当前班级
        TpTaskInfo tk=new TpTaskInfo();
        tk.setTaskid(Long.parseLong(taskid.trim()));
        List<TpTaskInfo> tkList=this.tpTaskManager.getList(tk,null);
        if(tkList==null||tkList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        tk=tkList.get(0);
        Integer questype=Integer.parseInt(tk.getTasktype().toString());
        if(questype!=7&&questype!=8&&questype!=9){
            jsonEntity.setMsg("错误，该任务不是移动端任务！只有移动端任务才能进入!");
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        //查询班级
        List<Map<String,Object>> objMapList=this.tpTaskAllotManager.getTaskAllotBLClassId(Long.parseLong(taskid.trim()),this.logined(request).getUserid());
        String classid=null;
        if(objMapList!=null&&objMapList.get(0)!=null&&objMapList.get(0).containsKey("CLASS_ID")){
            classid=objMapList.get(0).get("CLASS_ID").toString();
        }
        if(classid==null||classid.trim().length()<1){
            jsonEntity.setMsg("错误，班级获取失败!!");
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        mp.put("classid",classid);
        return new ModelAndView("/teachpaltform/task/im_stu_task",mp);
    }

    @RequestMapping(params="m=teaQuesAnserList")
    public ModelAndView teaToQuesAnswerList(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        String taskid=request.getParameter("taskid");
        JsonEntity jsonEntity=new JsonEntity();
        //验证参数
        if(taskid==null||taskid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        //查找当前班级
        TpTaskInfo tk=new TpTaskInfo();
        tk.setTaskid(Long.parseLong(taskid.trim()));
        List<TpTaskInfo> tkList=this.tpTaskManager.getList(tk,null);
        if(tkList==null||tkList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        tk=tkList.get(0);
        Integer questype=Integer.parseInt(tk.getTasktype().toString());
        if(questype!=7&&questype!=8&&questype!=9){
            jsonEntity.setMsg("错误，该任务不是移动端任务！只有移动端任务才能进入!");
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        //查询班级
        List<ClassInfo> clsList=new ArrayList<ClassInfo>();
        //查询当前任务发送的对象
        TpTaskAllotInfo tpTaskAllotInfo = new TpTaskAllotInfo();
        tpTaskAllotInfo.setTaskid(Long.parseLong(taskid));
        List<TpTaskAllotInfo> tpTaskAllotInfoList = this.tpTaskAllotManager.getList(tpTaskAllotInfo,null);
        for(TpTaskAllotInfo obj:tpTaskAllotInfoList){
            if(obj.getUsertype()==0){
                    ClassInfo cls=new ClassInfo();
                    cls.setClassid(obj.getUsertypeid().intValue());
                    List<ClassInfo> clstmpList=this.classManager.getList(cls,null);
                    if(clstmpList!=null&&clstmpList.size()>0)
                            clsList.add(clstmpList.get(0));

            }else if(obj.getUsertype()==2){
                //首先查询当前传入的班级下的小组
                TpGroupInfo tpGroupInfo = new TpGroupInfo();
                tpGroupInfo.setGroupid(obj.getUsertypeid());
                List<TpGroupInfo> tpGroupInfoList=this.tpGroupManager.getList(tpGroupInfo,null);
                if(tpGroupInfoList!=null&&tpGroupInfoList.size()>0){
                    ClassInfo cls=new ClassInfo();
                    cls.setClassid(tpGroupInfoList.get(0).getClassid());
                    List<ClassInfo> clstmpList=this.classManager.getList(cls,null);
                    if(clstmpList!=null&&clstmpList.size()>0&&!clstmpList.contains(clstmpList.get(0)))
                        clsList.add(clstmpList.get(0));
                }
            }
        }
        mp.put("clsList",clsList);
        return new ModelAndView("/teachpaltform/task/im_tea_task",mp);
    }

    /**
     * IM端任务，进入学生回答列表
     * @return
     */
    @RequestMapping(params = "m=toAnswerList")
    public ModelAndView toQuesAserList(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        String clsid=request.getParameter("classid");
        String taskid=request.getParameter("taskid");
        JsonEntity jsonEntity=new JsonEntity();
        //验证参数
        if(taskid==null||taskid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        if(clsid==null){
            TpTaskAllotInfo tpallot=new TpTaskAllotInfo();
            tpallot.setTaskid(Long.parseLong(taskid));
            List<TpTaskAllotInfo> tpallotList=this.tpTaskAllotManager.getList(tpallot,null);
            if(tpallotList==null||tpallotList.size()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
            }
            tpallot=tpallotList.get(0);
            if(tpallot.getUsertype()==0){
                clsid=tpallot.getUsertypeid()+"";
            }else if(tpallot.getUsertype()==2){
                TpGroupInfo tg=new TpGroupInfo();
                tg.setGroupid(tpallot.getUsertypeid());
                List<TpGroupInfo> tgList=this.tpGroupManager.getList(tg,null);
                if(tgList==null||tgList.size()>0){
                    jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                    response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
                }
                clsid=tgList.get(0).getClassid()+"";
            }
        }
        TpTaskInfo tk=new TpTaskInfo();
        tk.setTaskid(Long.parseLong(taskid.trim()));
        List<TpTaskInfo> tkList=this.tpTaskManager.getList(tk,null);
        if(tkList==null||tkList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        tk=tkList.get(0);
        Integer questype=Integer.parseInt(tk.getTasktype().toString());
        if(questype!=7&&questype!=8&&questype!=9){
            jsonEntity.setMsg("错误，该任务不是移动端任务！只有移动端任务才能进入!");
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        QuestionAnswer qa=new QuestionAnswer();
        qa.setClassid(Integer.parseInt(clsid));
        qa.setTaskid(Long.parseLong(taskid.trim()));
        List<QuestionAnswer> qaList=this.questionAnswerManager.getList(qa,null);
        mp.put("qaList",qaList);
        mp.put("tk",tk);
        return new ModelAndView("/teachpaltform/task/im_task_detail",mp);
    }
}
