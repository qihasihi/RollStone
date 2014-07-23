package com.school.control.teachpaltform.paper;

import com.school.control.base.BaseController;
import com.school.entity.ClassInfo;
import com.school.entity.DictionaryInfo;
import com.school.entity.resource.ResourceInfo;
import com.school.entity.teachpaltform.*;
import com.school.entity.teachpaltform.interactive.TpTopicInfo;
import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;
import com.school.entity.teachpaltform.paper.*;
import com.school.manager.ClassManager;
import com.school.manager.DictionaryManager;
import com.school.manager.SmsManager;
import com.school.manager.UserManager;
import com.school.manager.inter.IClassManager;
import com.school.manager.inter.IDictionaryManager;
import com.school.manager.inter.ISmsManager;
import com.school.manager.inter.IUserManager;
import com.school.manager.inter.resource.IResourceManager;
import com.school.manager.inter.teachpaltform.*;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicThemeManager;
import com.school.manager.inter.teachpaltform.paper.*;
import com.school.manager.resource.ResourceManager;
import com.school.manager.teachpaltform.*;
import com.school.manager.teachpaltform.interactive.TpTopicManager;
import com.school.manager.teachpaltform.interactive.TpTopicThemeManager;
import com.school.manager.teachpaltform.paper.*;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value="/paperques")
public class PaperQuestionController extends BaseController<PaperQuestion>{
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
    private IPaperQuestionManager paperQuestionManager;
    private IPaperManager paperManager;
    private ITpCoursePaperManager tpCoursePaperManager;
    private IStuPaperQuesLogsManager stuPaperQuesLogsManager;
    private IStuPaperLogsManager stuPaperLogsManager;
    private IResourceManager resourceManager;
    private IMicVideoPaperManager micVideoPaperManager;
    private IStuViewMicVideoLogManager stuViewMicVideoLogManager;
    private IQuesTeamRelaManager quesTeamRelaManager;
    public PaperQuestionController(){
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
        this.paperQuestionManager=this.getManager(PaperQuestionManager.class);
        this.paperManager=this.getManager(PaperManager.class);
        this.tpCoursePaperManager=this.getManager(TpCoursePaperManager.class);
        this.stuPaperQuesLogsManager=this.getManager(StuPaperQuesLogsManager.class);
        this.stuPaperLogsManager=this.getManager(StuPaperLogsManager.class);
        this.resourceManager=this.getManager(ResourceManager.class);
        this.micVideoPaperManager=this.getManager(MicVideoPaperManager.class);
        this.stuViewMicVideoLogManager=this.getManager(StuViewMicVideoLogManager.class);
        this.quesTeamRelaManager=this.getManager(QuesTeamRelaManager.class);
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
        String subjectid=request.getParameter("subjectid");
        String addresstype = request.getParameter("addresstype");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
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
        //任务库
        List<DictionaryInfo>courselevel=this.dictionaryManager.getDictionaryByType("COURSE_LEVEL");
        request.setAttribute("courselevel",courselevel);
        return new ModelAndView("/teachpaltform/paper/paper-list");
    }


    /**
     * 修改试卷试题排序
     * @throws Exception
     */
    @RequestMapping(params="m=doUpdOrderIdx",method=RequestMethod.POST)
    public void doUpdOrderIdx(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String paperid=request.getParameter("paperid");
        String  questionid=request.getParameter("questionid");
        String orderix=request.getParameter("orderidx");
        if(paperid==null||paperid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(orderix==null||orderix.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(questionid==null||questionid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        PaperQuestion pq=new PaperQuestion();
        pq.setPaperid(Long.parseLong(paperid));
        pq.setQuestionid(Long.parseLong(questionid));
        List<PaperQuestion>tmpList=this.paperQuestionManager.getList(pq,null);
        if(tmpList==null||tmpList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }
        pq=tmpList.get(0);

        PageResult p=new PageResult();
        p.setOrderBy("u.order_idx desc");
        PaperQuestion paperQuestion=new PaperQuestion();
        paperQuestion.setPaperid(Long.parseLong(paperid));
        List<PaperQuestion>paperQuestionList=this.paperQuestionManager.getList(paperQuestion,p);


        //获取提干
        PaperQuestion pp=new PaperQuestion();
        pp.setPaperid(Long.parseLong(paperid));
        PageResult pre=new PageResult();
        pre.setOrderBy("u.order_idx");
        pre.setPageNo(0);
        pre.setPageSize(0);
        List<PaperQuestion>pqList=this.paperQuestionManager.getList(pp,pre);

        //获取试题组下题目
        PaperQuestion child =new PaperQuestion();
        child.setPaperid(pq.getPaperid());
        List<PaperQuestion>childList=this.paperQuestionManager.getPaperTeamQuestionList(child,null);

        //获取试题组试题Size
        QuesTeamRela qtr=new QuesTeamRela();
        qtr.setTeamid(pq.getQuestionid());
        List<QuesTeamRela>qtrList=this.quesTeamRelaManager.getList(qtr,null);
        Integer childSize=0;
        if(qtrList!=null&&qtrList.size()>0)
            childSize=qtrList.size();


        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();

        Integer descIdx=Integer.parseInt(orderix);
        Integer Idx=descIdx;
        //0是调至最后获取最大索引
        if(descIdx==0)
            descIdx=(pqList==null?0:pqList.size())+(childList==null?0:childList.size());


        if(paperQuestionList!=null&&paperQuestionList.size()>0){
            Integer objIdx=pq.getOrderidx();
            if(objIdx>descIdx){  //从7往2调 7>2
                for(PaperQuestion paperQues:paperQuestionList){
                    Integer listIdx=paperQues.getOrderidx();
                    if(listIdx>=descIdx&&listIdx<objIdx){
                        PaperQuestion upd=new PaperQuestion();
                        upd.setPaperid(Long.parseLong(paperid));
                        upd.setQuestionid(paperQues.getQuestionid());
                        upd.setOrderidx(paperQues.getOrderidx()+1+childSize);
                        sql=new StringBuilder();
                        objList=this.paperQuestionManager.getUpdateSql(upd,sql);
                        if(sql!=null&&objList!=null){
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                }
            }else if(objIdx<descIdx){ //2<10
                Idx=Idx-1<=0?1:childSize>0?Idx-childSize:Idx-1;
                for(PaperQuestion paperQues:paperQuestionList){
                    Integer listIdx=paperQues.getOrderidx();
                    if(listIdx>(objIdx+childSize)&&listIdx<(descIdx+childSize)){
                        PaperQuestion upd=new PaperQuestion();
                        upd.setPaperid(Long.parseLong(paperid));
                        upd.setQuestionid(paperQues.getQuestionid());
                        upd.setOrderidx(paperQues.getOrderidx()-1-childSize);
                        sql=new StringBuilder();
                        objList=this.paperQuestionManager.getUpdateSql(upd,sql);
                        if(sql!=null&&objList!=null){
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                }
            }
        }

        PaperQuestion upd=new PaperQuestion();
        upd.setPaperid(pq.getPaperid());
        if(orderix.equals("0"))
            upd.setOrderidx(descIdx);
        else
            upd.setOrderidx(Idx);
        upd.setQuestionid(pq.getQuestionid());
        sql=new StringBuilder();
        objList=this.paperQuestionManager.getUpdateSql(upd,sql);
        if(sql!=null&&objList!=null){
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }
        if(sqlListArray.size()>0&&objListArray.size()>0){
            boolean flag=this.paperQuestionManager.doExcetueArrayProc(sqlListArray,objListArray);
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
        p.setOrderBy("t.c_time desc");
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
     * 获取试卷题目列表
     * @throws Exception
     */
    @RequestMapping(params="m=ajaxPaperQuestionList",method=RequestMethod.POST)
    public void ajaxTaskBankList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String paperid=request.getParameter("paperid");
        if(courseid==null||courseid.trim().length()<1||
                paperid==null||paperid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        List<PaperQuestion> tmpList=new ArrayList<PaperQuestion>();
        PageResult p=this.getPageResultParameter(request);
        p.setOrderBy("u.c_time desc");
        PaperQuestion pq=new PaperQuestion();
        pq.setPaperid(Long.parseLong(paperid));
        List<PaperQuestion>pqList=this.paperQuestionManager.getList(pq,p);
        if(pqList!=null&&pqList.size()>0){
            for (PaperQuestion ques:pqList){
                if(ques.getQuestiontype()==3||ques.getQuestiontype()==4){
                    QuestionOption questionOption=new QuestionOption();
                    questionOption.setQuestionid(ques.getQuestionid());
                    PageResult pchild = new PageResult();
                    pchild.setPageNo(0);
                    pchild.setPageSize(0);
                    pchild.setOrderBy("u.option_type");
                    List<QuestionOption>questionOptionList=this.questionOptionManager.getList(questionOption,pchild);
                    ques.setQuestionOption(questionOptionList);
                }
                tmpList.add(ques);
            }
        }
        p.setList(tmpList);
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

       /* if(tmpTask.getCloudstatus()!=null&&(tmpTask.getCloudstatus().intValue()==3||tmpTask.getCloudstatus().intValue()==4)){
            TpOperateInfo to=new TpOperateInfo();
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
            }
        }else{*/
        t.setStatus(2); //修改本地状态为已删除
        t.setOrderidx(-1);
        sql=new StringBuilder();
        objList=this.tpTaskManager.getUpdateSql(t,sql);
        if(sql!=null&&sql.toString().trim().length()>0){
            objListArray.add(objList);
            sqlStrList.add(sql.toString());
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
        //小组
        TpGroupInfo g=new TpGroupInfo();
        g.setCuserid(this.logined(request).getUserid());
        g.setTermid(courseclassList.get(0).getTermid());
        List<TpGroupInfo>groupList=this.tpGroupManager.getList(g, null);
        mp.put("tasktypeList", tasktypeList);
        mp.put("courseclassList",courseclassList);
        mp.put("groupList", groupList);
        mp.put("termid", termid);
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
     * 试卷添加
     * @throws Exception
     */
    @RequestMapping(params="doSubAddPaper",method=RequestMethod.POST)
    public void doSubAddPaper(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String papername=request.getParameter("papername");
        String courseid=request.getParameter("courseid");
        if(papername==null||papername.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(StringUtils.isBlank(courseid)){
            je.setMsg("异常错误,未获取到课题标识!");
            response.getWriter().print(je.toJSON());
            return;
        }
        TpCourseInfo courseInfo=new TpCourseInfo();
        courseInfo.setCourseid(Long.parseLong(courseid));
        List<TpCourseInfo>courseList=this.tpCourseManager.getList(courseInfo,null);
        if(courseList==null||courseList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        // TpCourseInfo tmpCourse=courseList.get(0);

        TpCoursePaper cp=new TpCoursePaper();
        cp.setCourseid(Long.parseLong(courseid));
        cp.setPapername(papername);
        List<TpCoursePaper>coursePaperList=this.tpCoursePaperManager.getList(cp,null);
        if(coursePaperList!=null&&coursePaperList.size()>0){
            je.setMsg(UtilTool.msgproperty.getProperty("PAPER_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }

        StringBuilder sql=null;
        List<Object>objList=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();

        //添加试卷
        Long paperid=this.paperManager.getNextId(true);
        PaperInfo p=new PaperInfo();
        p.setPaperid(paperid);
        p.setPapername(papername);
        p.setCuserid(this.logined(request).getUserid());
        p.setPapertype(PaperInfo.PAPER_TYPE.ORGANIZE.getValue());
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

     /*   if(tmpCourse.getQuoteid()!=null&&tmpCourse.getQuoteid().intValue()!=0){
            TpCourseInfo quoteInfo=new TpCourseInfo();
            quoteInfo.setCourseid(tmpCourse.getQuoteid());
            List<TpCourseInfo>quoteList=this.tpCourseManager.getList(quoteInfo,null);
            if(quoteList!=null&&quoteList.size()>0&&quoteList.get(0).getCourselevel()!=3){
                //增加专题操作数据
                TpOperateInfo to=new TpOperateInfo();
                to.setRef(this.tpOperateManager.getNextId(true));
                to.setCuserid(this.logined(request).getUserid());
                to.setCourseid(tmpCourse.getQuoteid());
                //to.setTargetid(tasknextid);
                to.setOperatetype(2);                                                              //添加
                to.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_TASK.getValue());                 //专题任务
                sql=new StringBuilder();
                objList=this.tpOperateManager.getSaveSql(to,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }*/


        if(objListArray.size()>0&&sqlListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlListArray, objListArray);
            if(flag){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
                je.getObjList().add(paperid);
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
     * 进入试卷选题、编辑页面
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=editPaperQuestion",method=RequestMethod.GET)
    public ModelAndView toEditPaperQuestion(HttpServletRequest request)throws Exception{
        JsonEntity je =new JsonEntity();//
        String courseid=request.getParameter("courseid");
        String paperid=request.getParameter("paperid");

        if(courseid==null||courseid.trim().length()<1
                ||paperid==null||paperid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            je.getAlertMsgAndBack();
            return null;
        }


        TpCoursePaper t=new TpCoursePaper();
        t.setCourseid(Long.parseLong(courseid));
        t.setPaperid(Long.parseLong(paperid));
        List<TpCoursePaper>tpCoursePaperList=this.tpCoursePaperManager.getList(t, null);
        if(tpCoursePaperList==null||tpCoursePaperList.size()<1){
            je.setMsg("抱歉该试卷已不存在!");
            je.getAlertMsgAndBack();
            return null;
        }

        request.setAttribute("courseid", courseid);
        request.setAttribute("paper", tpCoursePaperList.get(0));
        return new ModelAndView("/teachpaltform/paper/edit-paper-ques");
    }


    /**
     * 导入试卷的试题
     * @throws Exception
     */
    @RequestMapping(params="m=doImportPaperQues",method=RequestMethod.POST)
    public void doImportPaperQues(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String paperid=request.getParameter("paperid");
        String courseid=request.getParameter("courseid");
        String currentcourseid=request.getParameter("currentcourseid");
        String currentpaperid=request.getParameter("currentpaperid");
        if(paperid==null||paperid.trim().length()<1||
                courseid==null||courseid.trim().length()<1||
                currentcourseid==null||currentcourseid.trim().length()<1||
                currentpaperid==null||currentpaperid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }

        TpCourseInfo courseInfo=new TpCourseInfo();
        courseInfo.setCourseid(Long.parseLong(currentcourseid));
        List<TpCourseInfo>courseList=this.tpCourseManager.getList(courseInfo,null);
        if(courseList==null||courseList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        PaperQuestion pq=new PaperQuestion();
        pq.setPaperid(Long.parseLong(currentpaperid));
        PageResult p=new PageResult();
        p.setOrderBy("u.order_idx");
        p.setPageNo(0);
        p.setPageSize(0);
        List<PaperQuestion>pqList=this.paperQuestionManager.getList(pq,p);
        if(pqList==null||pqList.size()<1){
            je.setMsg("当前试卷暂无试题!");
            response.getWriter().print(je.toJSON());
            return;
        }

        StringBuilder sql=null;
        List<Object>objList=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();

        //添加试题至试卷中
     /*   PaperQuestion maxidx=new PaperQuestion();
        maxidx.setPaperid(Long.parseLong(paperid));
        PageResult maxp=new PageResult();
        maxp.setOrderBy("u.order_idx desc");
        maxp.setPageSize(1);
        maxp.setPageNo(1);
        List<PaperQuestion>maxList=this.paperQuestionManager.getList(maxidx,maxp);*/






        for(PaperQuestion question:pqList){
            //操作试卷中的试题,添加至专题试题库
            TpCourseQuestion courseQuestion=new TpCourseQuestion();
            courseQuestion.setCourseid(Long.parseLong(courseid));
            courseQuestion.setQuestionid(question.getQuestionid());
            List<TpCourseQuestion>tpCourseQuestionList=this.tpCourseQuestionManager.getList(courseQuestion,null);
            if(tpCourseQuestionList==null||tpCourseQuestionList.size()<1){
                courseQuestion.setRef(this.tpCourseQuestionManager.getNextId(true));
                sql=new StringBuilder();
                objList=this.tpCourseQuestionManager.getSaveSql(courseQuestion,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }


            PaperQuestion paperQuestion=new PaperQuestion();
            paperQuestion.setPaperid(Long.parseLong(paperid));
            paperQuestion.setQuestionid(question.getQuestionid());
            List<PaperQuestion>paperQuestionList=this.paperQuestionManager.getList(paperQuestion,null);



            if(paperQuestionList==null||paperQuestionList.size()<1){
                //获取试题组下提干+试题组数量用于排序
                PaperQuestion parent=new PaperQuestion();
                parent.setPaperid(Long.parseLong(paperid));
                List<PaperQuestion>parentList=this.paperQuestionManager.getList(parent,p);

                PaperQuestion child =new PaperQuestion();
                child.setPaperid(pq.getPaperid());
                List<PaperQuestion>childList=this.paperQuestionManager.getPaperTeamQuestionList(child,null);
                Integer maxIdx=(parentList==null?0:parentList.size())+(childList==null?0:childList.size());
                maxIdx+=1;

                PaperQuestion addPaper=new PaperQuestion();
                addPaper.setPaperid(Long.parseLong(paperid));
                addPaper.setQuestionid(question.getQuestionid());
                addPaper.setOrderidx(maxIdx);
                if(question.getScore()!=null)
                    addPaper.setScore(question.getScore());
                this.paperQuestionManager.getSaveSql(addPaper,sql);
                /*sql=new StringBuilder();
                objList=this.paperQuestionManager.getSaveSql(addPaper,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }*/

            }
        }

        if(objListArray.size()>0&&sqlListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlListArray, objListArray);
            if(flag){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
                this.modifyPaperTotalScore(Long.parseLong(paperid));
            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }else{
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
        }
        response.getWriter().print(je.toJSON());
    }



    /**
     * 获取导入试题列表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=ajaxImportQuesList", method = RequestMethod.POST)
    public void ajaxQuestionList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity jsonEntity=new JsonEntity();
        PageResult presult=this.getPageResultParameter(request);
        String courseid=request.getParameter("courseid");
        String paperid=request.getParameter("paperid");
        String coursename=request.getParameter("coursename");
        String materialid=request.getParameter("materialid");
        String gradeid=request.getParameter("gradeid");
        String subjectid=request.getParameter("subjectid");
        if(courseid==null||courseid.trim().length()<1||
                paperid==null||paperid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());
            return;
        }
        //得到列表
        TpCourseQuestion tpCourseQuestion=new TpCourseQuestion();
        tpCourseQuestion.setCurrentCourseid(Long.parseLong(courseid));
        tpCourseQuestion.setPaperid(Long.parseLong(paperid));
        if(coursename!=null&&coursename.trim().length()>0){
            tpCourseQuestion.setCoursename(coursename);
            if(materialid!=null&&materialid.length()>0)
                tpCourseQuestion.setMaterialid(Integer.parseInt(materialid));
            if(gradeid!=null&&gradeid.length()>0)
                tpCourseQuestion.setGradeid(Integer.parseInt(gradeid));
            if(subjectid!=null&&subjectid.length()>0)
                tpCourseQuestion.setSubjectid(Integer.parseInt(subjectid));
        }else
            tpCourseQuestion.setCourseid(Long.parseLong(courseid));

        //1：有效 2：已删除
        tpCourseQuestion.setStatus(1);
        presult.setOrderBy("paper_ques_flag,u.c_time DESC,q.c_time DESC ");
        List<TpCourseQuestion> tpCourseQuestionList=this.tpCourseQuestionManager.getList(tpCourseQuestion,presult);
        List<TpCourseQuestion> childList=this.tpCourseQuestionManager.getQuestionTeamList(tpCourseQuestion,null);
        PageResult pchild = new PageResult();
        pchild.setPageNo(0);
        pchild.setPageSize(0);
        pchild.setOrderBy("option_type");
        List<QuestionOption>questionOptionList=this.questionOptionManager.getCourseQuesOptionList(tpCourseQuestion, pchild);

       /* if(tpCourseQuestionList!=null&&tpCourseQuestionList.size()>0){
            for(TpCourseQuestion tq :tpCourseQuestionList){
                if(tq.getQuestiontype()==3||tq.getQuestiontype()==4){
                    QuestionOption questionOption=new QuestionOption();
                    questionOption.setQuestionid(tq.getQuestionid());
                    PageResult p = new PageResult();
                    p.setPageNo(0);
                    p.setPageSize(0);
                    p.setOrderBy("u.option_type");
                    List<QuestionOption>questionOptionList=this.questionOptionManager.getList(questionOption,p);
                    tq.setQuestionOptionList(questionOptionList);
                }
                if(childList!=null&&childList.size()>0){
                    for(TpCourseQuestion cq:childList){
                        if(cq.getRef().equals(tq.getRef())){

                        }
                    }
                }
                tmpList.add(tq);
            }
        } */

        List<TpCourseQuestion> tmpList=new ArrayList<TpCourseQuestion>();
        List<QuestionOption>tmpOptionList;
        List<TpCourseQuestion>questionTeam;
        if(tpCourseQuestionList!=null&&tpCourseQuestionList.size()>0){
            for(TpCourseQuestion tq :tpCourseQuestionList){
                questionTeam=new ArrayList<TpCourseQuestion>();
                //试题组
                if(childList!=null&&childList.size()>0){
                    for (TpCourseQuestion childp :childList){
                        //试题组选项
                        if(tq.getRef().equals(childp.getRef())){
                            if(questionOptionList!=null&&questionOptionList.size()>0){
                                tmpOptionList=new ArrayList<QuestionOption>();
                                for(QuestionOption qo:questionOptionList){
                                    if(qo.getQuestionid().equals(childp.getQuestionid())){
                                        tmpOptionList.add(qo);
                                    }
                                }
                                childp.setQuestionOptionList(tmpOptionList);
                                questionTeam.add(childp);
                            }
                        }
                    }
                    tq.setQuestionTeam(questionTeam);
                }

                if(questionOptionList!=null&&questionOptionList.size()>0){
                    //普通试题选项
                    List<QuestionOption> tmp1OptionList=new ArrayList<QuestionOption>();
                    for(QuestionOption qo:questionOptionList){
                        if(qo.getQuestionid().equals(tq.getQuestionid())){
                            tmp1OptionList.add(qo);
                        }
                    }

                    tq.setQuestionOptionList(tmp1OptionList);
                }
                tmpList.add(tq);
            }
        }


        presult.setList(tmpList);
        jsonEntity.setPresult(presult);
        jsonEntity.setType("success");
        response.getWriter().print(jsonEntity.toJSON());
    }


    /**
     * 通过导入试题,添加试卷试题
     * @throws Exception
     */
    @RequestMapping(params="m=doAddImportQues",method=RequestMethod.POST)
    public void doAddImportQues(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String paperid=request.getParameter("paperid");
        String courseid=request.getParameter("courseid");
        String quesid=request.getParameter("quesid");
        if(paperid==null||paperid.trim().length()<1||
                courseid==null||courseid.trim().length()<1||
                quesid==null||quesid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String[]questionidArray=quesid.split(",");

        TpCourseInfo courseInfo=new TpCourseInfo();
        courseInfo.setCourseid(Long.parseLong(courseid));
        List<TpCourseInfo>courseList=this.tpCourseManager.getList(courseInfo,null);
        if(courseList==null||courseList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }

        StringBuilder sql=null;
        List<Object>objList=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();


        for(String  questionid:questionidArray){
            //操作试卷中的试题,添加至专题试题库
            TpCourseQuestion courseQuestion=new TpCourseQuestion();
            courseQuestion.setCourseid(Long.parseLong(courseid));
            courseQuestion.setQuestionid(Long.parseLong(questionid));
            List<TpCourseQuestion>tpCourseQuestionList=this.tpCourseQuestionManager.getList(courseQuestion,null);
            if(tpCourseQuestionList==null||tpCourseQuestionList.size()<1){
                courseQuestion.setRef(this.tpCourseQuestionManager.getNextId(true));
                sql=new StringBuilder();
                objList=this.tpCourseQuestionManager.getSaveSql(courseQuestion,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }


            PaperQuestion paperQuestion=new PaperQuestion();
            paperQuestion.setPaperid(Long.parseLong(paperid));
            paperQuestion.setQuestionid(Long.parseLong(questionid));
            List<PaperQuestion>paperQuestionList=this.paperQuestionManager.getList(paperQuestion,null);
            if(paperQuestionList==null||paperQuestionList.size()<1){
                //获取试题组下提干+试题组数量用于排序
                PaperQuestion parent=new PaperQuestion();
                parent.setPaperid(Long.parseLong(paperid));
                PageResult p=new PageResult();
                p.setOrderBy("u.order_idx");
                p.setPageNo(0);
                p.setPageSize(0);
                List<PaperQuestion>parentList=this.paperQuestionManager.getList(parent,p);

                PaperQuestion child =new PaperQuestion();
                child.setPaperid(Long.parseLong(paperid));
                List<PaperQuestion>childList=this.paperQuestionManager.getPaperTeamQuestionList(child,null);
                Integer maxIdx=(parentList==null?0:parentList.size())+(childList==null?0:childList.size());
                maxIdx+=1;

                PaperQuestion addPaper=new PaperQuestion();
                addPaper.setPaperid(Long.parseLong(paperid));
                addPaper.setQuestionid(Long.parseLong(questionid));
                addPaper.setOrderidx(maxIdx);
                addPaper.setScore(new Float(5));
                this.paperQuestionManager.getSaveSql(addPaper,sql);
               /* sql=new StringBuilder();
                objList=this.paperQuestionManager.getSaveSql(addPaper,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }*/
            }
        }

        if(objListArray.size()>0&&sqlListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlListArray, objListArray);
            if(flag){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
                this.modifyPaperTotalScore(Long.parseLong(paperid));
            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }else{
            je.setMsg("您的操作没有执行!");
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * 通过新建试题,添加试卷试题
     * @throws Exception
     */
    @RequestMapping(params="m=doAddQues",method=RequestMethod.POST)
    public void doAddQues(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String paperid=request.getParameter("paperid");
        String courseid=request.getParameter("courseid");
        String questionid=request.getParameter("questionid");
        if(paperid==null||paperid.trim().length()<1||
                courseid==null||courseid.trim().length()<1||
                questionid==null||questionid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }

        TpCourseInfo courseInfo=new TpCourseInfo();
        courseInfo.setCourseid(Long.parseLong(courseid));
        List<TpCourseInfo>courseList=this.tpCourseManager.getList(courseInfo,null);
        if(courseList==null||courseList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }

        StringBuilder sql=null;
        List<Object>objList=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();

        //添加试题至试卷中
        PaperQuestion maxidx=new PaperQuestion();
        maxidx.setPaperid(Long.parseLong(paperid));
        PageResult maxp=new PageResult();
        maxp.setOrderBy("u.order_idx desc");
        maxp.setPageSize(1);
        maxp.setPageNo(1);
        List<PaperQuestion>maxList=this.paperQuestionManager.getList(maxidx,maxp);
        Integer maxIdx=1;
        if(maxList!=null&&maxList.size()>0)
            maxIdx=maxList.get(0).getOrderidx();


        //操作试卷中的试题,添加至专题试题库
        TpCourseQuestion courseQuestion=new TpCourseQuestion();
        courseQuestion.setCourseid(Long.parseLong(courseid));
        courseQuestion.setQuestionid(Long.parseLong(questionid));
        List<TpCourseQuestion>tpCourseQuestionList=this.tpCourseQuestionManager.getList(courseQuestion,null);
        if(tpCourseQuestionList==null||tpCourseQuestionList.size()<1){
            courseQuestion.setRef(this.tpCourseQuestionManager.getNextId(true));
            sql=new StringBuilder();
            objList=this.tpCourseQuestionManager.getSaveSql(courseQuestion,sql);
            if(objList!=null&&sql!=null){
                objListArray.add(objList);
                sqlListArray.add(sql.toString());
            }
        }


        PaperQuestion paperQuestion=new PaperQuestion();
        paperQuestion.setPaperid(Long.parseLong(paperid));
        paperQuestion.setQuestionid(Long.parseLong(questionid));
        List<PaperQuestion>paperQuestionList=this.paperQuestionManager.getList(paperQuestion,null);
        if(paperQuestionList==null||paperQuestionList.size()<1){
            maxIdx+=1;
            PaperQuestion addPaper=new PaperQuestion();
            addPaper.setPaperid(Long.parseLong(paperid));
            addPaper.setQuestionid(Long.parseLong(questionid));
            addPaper.setOrderidx(maxIdx);
            addPaper.setScore(new Float(5));
            sql=new StringBuilder();
            objList=this.paperQuestionManager.getSaveSql(addPaper,sql);
            if(objList!=null&&sql!=null){
                objListArray.add(objList);
                sqlListArray.add(sql.toString());
            }
        }

        if(objListArray.size()>0&&sqlListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlListArray, objListArray);
            if(flag){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
                this.modifyPaperTotalScore(maxidx.getPaperid());
            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }else{
            je.setMsg("您的操作没有执行!");
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 修改试卷试题分数
     * @throws Exception
     */
    @RequestMapping(params="m=doUpdPaperQuesScore",method=RequestMethod.POST)
    public void doUpdPaperQuesScore(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String paperid=request.getParameter("paperid");
        String questionid=request.getParameter("questionid");
        String score=request.getParameter("score");
        String ref=request.getParameter("ref");
        if(paperid==null||paperid.trim().length()<1||
                questionid==null||questionid.trim().length()<1||
                score==null||score.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();
        //试题组分数修改
        if(ref!=null&&ref.trim().length()>0){
            PaperQuestion pq=new PaperQuestion();
            pq.setRef(Integer.parseInt(ref));
            List<PaperQuestion>pList=this.paperQuestionManager.getList(pq,null);
            if(pList==null||pList.size()<1){
                je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                response.getWriter().print(je.toJSON());
                return;
            }

            QuesTeamRela qt=new QuesTeamRela();
            qt.setTeamid(pList.get(0).getQuestionid());
            qt.setQuesid(Long.parseLong(questionid));
            qt.setScore(new Float(score));
            sql=new StringBuilder();
            objList=this.quesTeamRelaManager.getUpdateSql(qt,sql);
            if(sql!=null&&objList!=null){
                objListArray.add(objList);
                sqlListArray.add(sql.toString());
            }
        }else{
            PaperQuestion pq=new PaperQuestion();
            pq.setPaperid(Long.parseLong(paperid));
            pq.setQuestionid(Long.parseLong(questionid));
            pq.setScore(new Float(score));
            sql=new StringBuilder();
            objList=this.paperQuestionManager.getUpdateSql(pq,sql);
            if(sql!=null&&objList!=null){
                objListArray.add(objList);
                sqlListArray.add(sql.toString());
            }
        }


        if(this.paperQuestionManager.doExcetueArrayProc(sqlListArray,objListArray)){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");

            PaperQuestion p=new PaperQuestion();
            p.setPaperid(Long.parseLong(paperid));
            if(ref!=null&&ref.trim().length()>0){
                //更新试题组试题分数
                p.setRef(Integer.parseInt(ref));
                this.paperQuestionManager.updateQuesTeamScore(p);
            }
            Float sumScore=this.paperQuestionManager.getSumScore(p);
            this.modifyPaperTotalScore(p.getPaperid());
            je.getObjList().add(sumScore);

            if(ref!=null&&ref.trim().length()>0){
                List<PaperQuestion>pScoreList=this.paperQuestionManager.getList(p,null);
                if(pScoreList!=null&&pScoreList.size()>0)
                    je.getObjList().add(pScoreList.get(0).getScore());
            }

        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }


    /**
     * 删除试卷试题
     * @throws Exception
     */
    @RequestMapping(params="m=doDelPaperQues",method=RequestMethod.POST)
    public void doDelPaperQues(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String paperid=request.getParameter("paperid");
        String questionid=request.getParameter("questionid");
        if(paperid==null||paperid.trim().length()<1||
                questionid==null||questionid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }

        PaperQuestion pq=new PaperQuestion();
        pq.setPaperid(Long.parseLong(paperid));
        pq.setQuestionid(Long.parseLong(questionid));
        List<PaperQuestion>tmpList=this.paperQuestionManager.getList(pq,null);
        if(tmpList==null||tmpList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }
        pq=tmpList.get(0);

        PageResult p=new PageResult();
        p.setOrderBy("u.order_idx");
        PaperQuestion paperQuestion=new PaperQuestion();
        paperQuestion.setPaperid(Long.parseLong(paperid));
        List<PaperQuestion>paperQuestionList=this.paperQuestionManager.getList(paperQuestion,p);

        //获取当前删除试题的子集Size
        //获取试题组下题目
        PaperQuestion child =new PaperQuestion();
        child.setPaperid(pq.getPaperid());
        child.setQuestionid(pq.getQuestionid());
        List<PaperQuestion>childList=this.paperQuestionManager.getPaperTeamQuestionList(child,null);

        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();


        Integer orderIdx=pq.getOrderidx();
        if(paperQuestionList!=null&&paperQuestionList.size()>0){
            for(PaperQuestion paperQues:paperQuestionList){
                if(paperQues.getOrderidx()!=null){
                    if(paperQues.getOrderidx()>orderIdx){
                        PaperQuestion upd=new PaperQuestion();
                        upd.setPaperid(paperQues.getPaperid());
                        upd.setQuestionid(paperQues.getQuestionid());
                        upd.setOrderidx(paperQues.getOrderidx()-1-(childList==null?0:childList.size()));
                        sql=new StringBuilder();
                        objList=this.paperQuestionManager.getUpdateSql(upd,sql);
                        if(sql!=null&&sql.toString().trim().length()>0){
                            objListArray.add(objList);
                            sqlListArray.add(sql.toString());
                        }
                    }
                }
            }
        }

        PaperQuestion del=new PaperQuestion();
        del.setRef(pq.getRef());
        sql=new StringBuilder();
        objList=this.paperQuestionManager.getDeleteSql(del,sql);
        if(sql!=null&&objList!=null){
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }
        if(sqlListArray.size()>0&&objListArray.size()>0&&sqlListArray.size()==objListArray.size())
        if(this.paperQuestionManager.doExcetueArrayProc(sqlListArray,objListArray)){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
            this.modifyPaperTotalScore(pq.getPaperid());
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }

    /**
     * 修改总分
     * @param paperid
     */
    private void modifyPaperTotalScore(Long paperid){
        if(paperid==null||paperid.toString().length()<1)
            return;
        PaperQuestion pq=new PaperQuestion();
        pq.setPaperid(paperid);
        Float sumScore=this.paperQuestionManager.getSumScore(pq);
        if(sumScore!=null){
            PaperInfo paper=new PaperInfo();
            paper.setPaperid(pq.getPaperid());
            paper.setScore(sumScore);
            this.paperManager.doUpdate(paper);
        }
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
        //  String taskname=request.getParameter("taskname");
        String taskvalueid=request.getParameter("taskvalueid");
        String taskremark=request.getParameter("taskremark");
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
        StringBuilder sql=null;
        List<Object>objList=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        Long tasknextid;
        TpTaskInfo ta=new TpTaskInfo();

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
        }
        //获取任务
        TpTaskInfo sel=new TpTaskInfo();
        sel.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo>taskList=this.tpTaskManager.getList(sel,null);
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
            //ta.setTaskname(taskname);
            ta.setTasktype(Integer.parseInt(tasktype));
            ta.setCourseid(Long.parseLong(courseid));
            ta.setCuserid(this.logined(request).getRef());
            ta.setCriteria(Integer.parseInt(criteriaArray[0]));
            if(taskremark!=null)
                ta.setTaskremark(taskremark);
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
            for (int i=0;i<clsArray.length;i++) {
                TpTaskAllotInfo tal=new TpTaskAllotInfo();
                //验证是什么类型的班级
                ClassInfo c=new ClassInfo();
                c.setClassid(Integer.parseInt(clsArray[i]));
                List<ClassInfo>clsList=this.classManager.getList(c,null);

                TpVirtualClassInfo tv=new TpVirtualClassInfo();
                tv.setVirtualclassid(Integer.parseInt(clsArray[i]));
                List<TpVirtualClassInfo>vclsList=this.tpVirtualClassManager.getList(tv,null);
                if(clsList!=null&&clsList.size()>0){
                    tal.setUsertype(0);
                }else if(vclsList!=null&&vclsList.size()>0){
                    tal.setUsertype(1);
                }else {
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

            QuestionAnswer qa=new QuestionAnswer();
            qa.setTaskid(t.getTaskid());
            qa.setCourseid(t.getCourseid());
            qa.setTasktype(t.getTasktype());
            //qa.setGroupid(groupid);
            qa.setUserid(this.logined(request).getRef());
            qa.setQuesparentid(Long.parseLong(quesid));
            qa.setAnswercontent(quesanswer);
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
            tp.setIsright(1);
            tp.setUserid(this.logined(request).getRef());
            sql=new StringBuilder();
            objList=this.taskPerformanceManager.getSaveSql(tp, sql);
            if(objList!=null&&sql!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }

        if(objListArray.size()>0&&sqlListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlListArray, objListArray);
            if(flag){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
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

        QuestionAnswer qa=new QuestionAnswer();
        qa.setCourseid(Long.parseLong(courseid));
        qa.setQuesparentid(Long.parseLong(resourceid));
        qa.setQuesid(Long.parseLong("0"));
        qa.setUserid(this.logined(request).getRef());
        qa.setAnswercontent(answercontent);
        qa.setRightanswer(1);
        qa.setTasktype(taskInfo.getTasktype());
        qa.setTaskid(taskInfo.getTaskid());
        sql=new StringBuilder();
        objList=this.questionAnswerManager.getSaveSql(qa,sql);
        if(sql!=null&&objList!=null){
            sqlList.add(sql.toString());
            objListArray.add(objList);
        }

        if(this.tpTaskManager.doExcetueArrayProc(sqlList,objListArray)){
            je.setType("success");
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
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

        QuestionAnswer qa=new QuestionAnswer();
        qa.setRef(Integer.parseInt(ref));
        qa.setAnswercontent(content);
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
        }else{
            return new ModelAndView("/teachpaltform/task/teacher/task-performance-zg");
        }
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
            fos = new FileOutputStream(request.getRealPath("/")+"images/taskPie.png");
            ChartUtilities.writeChartAsPNG(fos, chart, 193, 140);
        }finally{
            fos.close();
        }
        je.getObjList().add(numList);
        je.getObjList().add(option);
        je.getObjList().add(tList);
        je.getObjList().add(tiList);
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
        je.getObjList().add(numList);
        je.getObjList().add(tList);
        je.getObjList().add(tiList);
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
    @RequestMapping(params = "m=loadRelatePaper",method=RequestMethod.POST)
    public void loadRelatePaper(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        String resid=request.getParameter("resid");
        String courseid=request.getParameter("courseid");
        JsonEntity jsonEntity=new JsonEntity();
        if(courseid==null||courseid.trim().length()<1||resid==null||resid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return;
        }

        //得到相关的试卷ID
        MicVideoPaperInfo mvpaper=new MicVideoPaperInfo();
        mvpaper.setMicvideoid(Long.parseLong(resid));
        List<MicVideoPaperInfo> mvpaperList=this.micVideoPaperManager.getList(mvpaper,null);
        if(mvpaperList!=null&&mvpaperList.size()>0){
            jsonEntity.getObjList().add(mvpaperList.get(0));
        }
        jsonEntity.setType("success");
        response.getWriter().println(jsonEntity.toJSON());
    }



    /**
     * 进入测试页面
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toTestPaper",method=RequestMethod.GET)
    public ModelAndView toTestPaper(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        String paperid=request.getParameter("paperid");
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");
        JsonEntity jsonEntity=new JsonEntity();
        if(paperid==null||paperid.trim().length()<1||courseid==null||courseid.trim().length()<1||taskid==null||taskid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }

        //验证任务是否存在
        TpTaskInfo tpTask=new TpTaskInfo();
        tpTask.setTaskid(Long.parseLong(taskid));
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        List<TpTaskInfo> tkList=this.tpTaskManager.getList(tpTask,presult);
        if(tkList==null||tkList.size()<1||tkList.get(0)==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }

        TpTaskInfo t=new TpTaskInfo();
        t.setUserid(this.logined(request).getUserid());
        t.setTaskid(Long.parseLong(taskid));
        // 学生任务
        List<TpTaskInfo>taskList=this.tpTaskManager.getListbyStu(t, presult);
        if(taskList==null||taskList.size()<1||taskList.get(0).getBtime()==null||taskList.get(0).getEtime()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }

        mp.put("taskstatus", taskList.get(0).getTaskstatus());
        //验证任务是否在相应时间范围内
        tpTask=tkList.get(0);
        if(tpTask.getTasktype().intValue()==6){//6：微视频
            //得到taskvalueid 得到相关资源
            ResourceInfo rtmp=new ResourceInfo();
            rtmp.setResid(Long.parseLong(tpTask.getTaskvalueid().toString()));
            List<ResourceInfo> resList=this.resourceManager.getList(rtmp,presult);
            //验证微视频是否存在相关数据
            if(resList==null||resList.size()<1||resList.get(0)==null){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
            }
            mp.put("resObj", resList.get(0));
            //得到相关的试卷ID
            MicVideoPaperInfo mvpaper=new MicVideoPaperInfo();
            mvpaper.setMicvideoid(resList.get(0).getResid());
            List<MicVideoPaperInfo> mvpaperList=this.micVideoPaperManager.getList(mvpaper,presult);
            if(mvpaperList==null||mvpaperList.size()<1||mvpaperList.get(0)==null){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
            }
            paperid=mvpaperList.get(0).getPaperid().toString();
            //得到学生是否已经有过相关查看记录
            StuViewMicVideoLog svm=new StuViewMicVideoLog();
            svm.setUserid(this.logined(request).getUserid());
            svm.setMicvideoid(resList.get(0).getResid());
            List<StuViewMicVideoLog> stuViewMicList=this.stuViewMicVideoLogManager.getList(svm,presult);
            if(stuViewMicList!=null&&stuViewMicList.size()>0){
                mp.put("isViewVideo",1);
            }
            //验证是否已经交卷
            StuPaperLogs splog=new StuPaperLogs();
            splog.setUserid(this.logined(request).getUserid());
            splog.setPaperid(Long.parseLong(paperid));
            splog.setIsinpaper(2);
            PageResult pr=new PageResult();
            pr.setPageSize(1);
            List<StuPaperLogs> spList=this.stuPaperLogsManager.getList(splog,pr);
            if(spList!=null&&spList.size()>0){ //已经交卷，不能再进入
                response.sendRedirect("paperques?m=toTestDetail&paperid="+paperid);return null;
            }


            //得到当前的所有问题ID
            List<Map<String,Object>> listMapStr=this.paperQuestionManager.getPaperQuesAllId(Long.parseLong(paperid));
            if(listMapStr==null||listMapStr.size()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
            }
            Object allquesidObj=listMapStr.get(0).get("ALLQUESID");
            if(allquesidObj==null||allquesidObj.toString().trim().length()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
            }
            mp.put("allquesidObj",allquesidObj);


            //如果是微视频测试，则进入相关测试页面
            mp.put("quesSize",allquesidObj.toString().split(",").length);
            mp.put("taskid",taskid);
            mp.put("courseid",courseid);
            mp.put("paperid",paperid);
            return new ModelAndView("/teachpaltform/paper/stuSmailViewTest",mp);
        }
        //重定向，但不换地址
        request.getRequestDispatcher("paperques?m=testPaper&paperid="+paperid+"&courseid="+courseid+"&taskid="+taskid).forward(request,response);
        return null; 
    }

    /**
     * 进入测评系统开测试。
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=testPaper",method = RequestMethod.GET)
    public ModelAndView testPaper(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        //最后要跳入的页面
        String toPage="stuTest";
        String paperid=request.getParameter("paperid");
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");
        JsonEntity jsonEntity=new JsonEntity();
        if(paperid==null||paperid.trim().length()<1||courseid==null||courseid.trim().length()<1||taskid==null||taskid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }

        //验证paperid是否存在
        PaperInfo paperInfo=new PaperInfo();
        paperInfo.setPaperid(Long.parseLong(paperid.trim()));
        List<PaperInfo> paperList=this.paperManager.getList(paperInfo,null);
        if(paperList==null||paperList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }

        TpTaskInfo t=new TpTaskInfo();
        t.setUserid(this.logined(request).getUserid());
        t.setTaskid(Long.parseLong(taskid));
        PageResult pr=new PageResult();
        pr.setPageSize(1);
        // 学生任务
        List<TpTaskInfo>taskList=this.tpTaskManager.getListbyStu(t, pr);
        if(taskList==null||taskList.size()<1||taskList.get(0).getBtime()==null||taskList.get(0).getEtime()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }

        mp.put("taskstatus", taskList.get(0).getTaskstatus());
        //验证是否已经交卷
        StuPaperLogs splog=new StuPaperLogs();
        splog.setUserid(this.logined(request).getUserid());
        splog.setPaperid(Long.parseLong(paperid));
        splog.setIsinpaper(2);

        List<StuPaperLogs> spList=this.stuPaperLogsManager.getList(splog,pr);
        if(spList!=null&&spList.size()>0){ //已经交卷，不能再进入
            response.sendRedirect("paperques?m=toTestDetail&paperid="+paperid);return null;
        }

        //得到当前的所有问题
        List<Map<String,Object>> listMapStr=this.paperQuestionManager.getPaperQuesAllId(Long.parseLong(paperid));
        if(listMapStr==null||listMapStr.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        Object allquesidObj=listMapStr.get(0).get("ALLQUESID");
        if(allquesidObj==null||allquesidObj.toString().trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        mp.put("allquesidObj",allquesidObj);
        mp.put("paperObj",paperList.get(0));
        mp.put("taskid",taskid);
        mp.put("courseid",courseid);
        mp.put("allquesidObj",allquesidObj);
        mp.put("quesSize",allquesidObj.toString().split(",").length);
        mp.put("paperid",paperid);
        return new ModelAndView("/teachpaltform/paper/"+toPage,mp);
    }

    /**
     * 试卷测试，下一个试题
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=nextTestQues",method = RequestMethod.POST)
    public void testNextQues(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String quesid=request.getParameter("quesid");
        String paperid=request.getParameter("paperid");
        JsonEntity jsonEntity=new JsonEntity();
        if(quesid==null||quesid.toString().trim().length()<1||paperid==null||paperid.toString().trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.toJSON());return ;
        }
        String q=quesid;

        if(q.indexOf("|")!=-1){
            Object[] quesObjArray=quesid.split("\\|");
            quesid=quesObjArray[1].toString();
            String parentQuesid=quesObjArray[0].toString();
            if(parentQuesid!=null&&parentQuesid.toString().trim().length()>0){
                //查询题干
                QuestionInfo parentQues=new QuestionInfo();
                parentQues.setQuestionid(Long.parseLong(parentQuesid));
                List<QuestionInfo> pquesList=this.questionManager.getList(parentQues,null);
                if(pquesList==null||pquesList.size()<1){
                    jsonEntity.setMsg("错误，主试题题干不存在!请刷新重试!");
                    response.getWriter().println(jsonEntity.toJSON());return ;
                }
               jsonEntity.getObjList().add(pquesList.get(0));
            }
        }
        //验证试题
        QuestionInfo quest=new QuestionInfo();
        quest.setQuestionid(Long.parseLong(quesid.trim()));
        List<QuestionInfo> quesList=this.questionManager.getList(quest,null);
        if(quesList==null||quesList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.toJSON());return ;
        }

        //得到是否存在于当前试卷中
        //得到当前的所有问题
        List<Map<String,Object>> listMapStr=this.paperQuestionManager.getPaperQuesAllId(Long.parseLong(paperid));
        if(listMapStr==null||listMapStr.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.toJSON());return ;
        }
        Object allquesidObj=listMapStr.get(0).get("ALLQUESID");
        if(allquesidObj==null||allquesidObj.toString().trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.toJSON());return ;
        }
        String[] quesArray=allquesidObj.toString().trim().split(",");
        if(quesArray.length<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.toJSON());return ;
        }

        boolean ishas=false;
        for (String qid:quesArray){
            if(qid==null||qid.length()<1)continue;
            if(qid.indexOf("|")!=-1)
                if(qid.split("\\|")[1].equals(quesid)){
                    ishas=true;
                    break;
                }
            if(qid.equals(quesid)){
                ishas=true;
                break;
            }
        }
        //加载选项
        QuestionOption qo=new QuestionOption();
        qo.setQuestionid(Long.parseLong(quesid));
        List<QuestionOption> qoList=questionOptionManager.getList(qo,null);
        quesList.get(0).setQuestionOption(qoList);

        if(!ishas){
            jsonEntity.setMsg("错误。当前试题不存在于试卷中。请核对!");
            response.getWriter().println(jsonEntity.toJSON());return;
        }




        //试题
        jsonEntity.getObjList().add(quesList.get(0));
        jsonEntity.setType("success");
        response.getWriter().println(jsonEntity.toJSON());
    }

    /**
     * 教师进入学生答卷详情页面
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=teaViewStuPaper",method=RequestMethod.GET)
    public ModelAndView teaToViewStuPaper(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        String userid=request.getParameter("userid");
        String taskid=request.getParameter("taskid");
        JsonEntity jsonEntity=new JsonEntity();
        if(userid==null||userid.trim().length()<1||taskid==null||taskid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        //根据任务，得到该学生的试卷信息
        TpTaskInfo tk=new TpTaskInfo();
        tk.setTaskid(Long.parseLong(taskid.trim()));
        List<TpTaskInfo> tkList=this.tpTaskManager.getList(tk,null);
        if(tkList==null||tkList.size()<1){
            jsonEntity.setMsg("异常错误，没有发现要查询的任务信息!该任务已经不存在!");
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
        }
        Integer tktype =tkList.get(0).getTasktype();
        Long tkvalueid=tkList.get(0).getTaskvalueid();
        // 4:成卷测试  5：自主测试
        if(tktype==4){
            request.setAttribute("paperid",tkvalueid);
            return toStuTestDetail(request,response,mp);
        }else{
            PaperInfo pinfo=new PaperInfo();
            pinfo.setParentpaperid(tkvalueid);
            pinfo.setCuserid(Integer.parseInt(userid.trim()));
            List<PaperInfo> paperList=this.paperManager.getList(pinfo,null);
            if(paperList==null||paperList.size()<1){
                jsonEntity.setMsg("该学生暂未作答!");
                response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
            }
            request.setAttribute("paperid",paperList.get(0).getPaperid());
            return toStuTestDetail(request,response,mp);
        }
    }

    /**
     * 进入详情页面
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toTestDetail",method=RequestMethod.GET)
    public ModelAndView toStuTestDetail(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        String paperid=(request.getAttribute("paperid")==null?request.getParameter("paperid"):request.getAttribute("paperid").toString());
        String userid=request.getParameter("userid");
        JsonEntity jsonEntity=new JsonEntity();
        if(paperid==null||paperid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
        }

        //验证paperid是否存在
        PaperInfo paperInfo=new PaperInfo();
        paperInfo.setPaperid(Long.parseLong(paperid.trim()));
        List<PaperInfo> paperList=this.paperManager.getList(paperInfo,null);
        if(paperList==null||paperList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        Integer uid=this.logined(request).getUserid();
        if(userid!=null&&userid.trim().length()>0)
            uid=Integer.parseInt(userid.trim());

        //验证是否已经答题
        StuPaperLogs splog=new StuPaperLogs();
        splog.setUserid(uid);
        splog.setPaperid(Long.parseLong(paperid));
        splog.setIsinpaper(2);
        PageResult pr=new PageResult();
        pr.setPageSize(1);
        List<StuPaperLogs> spList=this.stuPaperLogsManager.getList(splog,pr);
        if(spList==null||spList.size()<1){
            return testPaper(request,response,mp);
        }


        //验证是否已经答题
        StuPaperQuesLogs stuPaperQuesLogs=new StuPaperQuesLogs();
        stuPaperQuesLogs.setUserid(uid);
        stuPaperQuesLogs.setPaperid(Long.parseLong(paperid.trim()));
        List<StuPaperQuesLogs> stuPageQuesList=this.stuPaperQuesLogsManager.getList(stuPaperQuesLogs,null);
        if(stuPageQuesList==null||stuPageQuesList.size()<1){//已经做过，则跳入显示页面
            jsonEntity.setMsg("异常错误，原因：没有您的答题记录，但是有交卷记录!");
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
        }
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
        String[] quesArray=allquesidObj.toString().trim().split(",");
        if(quesArray.length<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
        }
        String qidStr="",pqidStr=",";
        Map<String,Object> relationMap=new HashMap<String, Object>(); //key:questionid   value:parentQuestionid
        for (String qid:quesArray){
            if(qid!=null&&qid.trim().length()>0){
                String tmpQid=qid.trim();
                if(qid.trim().indexOf("|")!=-1){
                    String[] qidArrayStr=qid.trim().split("\\|");
                    tmpQid=qidArrayStr[1];
                    if(pqidStr.trim().indexOf(","+qidArrayStr[0]+",")==-1){
                            pqidStr+=qidArrayStr[0]+",";
                    }
                    //添加关系
                    relationMap.put(qidArrayStr[1],qidArrayStr[0]);
                }
                qidStr+=tmpQid+",";
            }
        }
        if(qidStr.trim().length()>0)
            qidStr=qidStr.substring(0,qidStr.trim().length()-1);
        QuestionInfo searchQues=new QuestionInfo();
        searchQues.setQuestionidStr(qidStr);
        List<QuestionInfo> quesList=this.questionManager.getList(searchQues,null);

//        PaperQuestion pq=new PaperQuestion();
//        pq.setPaperid(Long.parseLong(paperid));
//        List<PaperQuestion> pqList=this.paperQuestionManager.getList(pq,null);
        //保存入ModelMap中
        if(quesList!=null&&quesList.size()>0){
            //查询父试题的题干
            if(pqidStr!=null&&pqidStr.trim().length()>1){
                pqidStr=pqidStr.substring(1);
                pqidStr=pqidStr.substring(0,pqidStr.trim().length()-1);
                QuestionInfo searchPQues=new QuestionInfo();
                searchPQues.setQuestionidStr(pqidStr);
                List<QuestionInfo> parentQuesList=this.questionManager.getList(searchPQues,null);
                //比较
                if(parentQuesList!=null&&parentQuesList.size()>0&&relationMap.size()>0){
                    for (QuestionInfo qentity:quesList){
                        if(qentity!=null&&qentity.getQuestionid()!=null){
                            //检索是否存在于Map中。
                            if(relationMap.containsKey(qentity.getQuestionid().toString())){
                                //得到存于value中的parentQues实体
                                for (QuestionInfo pq:parentQuesList){
                                    if(pq!=null&&pq.getQuestionid()!=null){
                                        if(pq.getQuestionid().toString().equals(relationMap.get(qentity.getQuestionid().toString()))){
                                            qentity.setParentQues(pq); //加入ques中
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //查询所有题的选项
            for (QuestionInfo pqentity:quesList){
                if(pqentity!=null&&pqentity.getQuestionid()!=null){
                    QuestionOption qo=new QuestionOption();
                    qo.setQuestionid(pqentity.getQuestionid());
                    pqentity.setQuestionOption(this.questionOptionManager.getList(qo,null));
                }
            }
        }
        mp.put("allquesidObj",allquesidObj);
        mp.put("quesList",quesList);
        mp.put("paperObj",paperList.get(0));
        //得到当前学生答案
        mp.put("stuAnswer",stuPageQuesList);

       return new ModelAndView("/teachpaltform/paper/stuTestDetail",mp); }

    /**
     * 交卷
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params ="m=doInPaper",method=RequestMethod.POST)
    public void doInPaper(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String paperid=request.getParameter("paperid");
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");
        JsonEntity jsonEntity=new JsonEntity();
        if(paperid==null||paperid.length()<1||courseid==null||courseid.trim().length()<1||taskid==null||taskid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.toJSON());return ;
        }
        //验证任务
        TpTaskInfo tk=new TpTaskInfo();
        tk.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo> tkList=this.tpTaskManager.getList(tk,null);
        if(tkList==null||tkList.size()<1||!tkList.get(0).getCourseid().toString().equals(courseid.trim())){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.toJSON());return ;
        }
        tk=tkList.get(0);


        Integer userid=this.logined(request).getUserid();
        Integer isinpaper=2;
        StuPaperLogs splog=new StuPaperLogs();
        splog.setUserid(userid);
        splog.setPaperid(Long.parseLong(paperid));
        splog.setIsinpaper(isinpaper);

        PageResult presult=new PageResult();
        presult.setPageSize(1);
        List<StuPaperLogs> spList=this.stuPaperLogsManager.getList(splog,presult);
        if(spList!=null&&spList.size()>0){
            jsonEntity.setMsg("异常错误，您已经提交过该试卷。无法进行修改!");
            response.getWriter().print(jsonEntity.toJSON());return;
        }
//
        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        TaskPerformanceInfo tpf=new TaskPerformanceInfo();
        tpf.setCourseid(Long.parseLong(courseid.trim()));
        tpf.setTaskid(tk.getTaskid());
        tpf.setUserid(this.logined(request).getRef());

        List<TaskPerformanceInfo> tpfList=this.taskPerformanceManager.getList(tpf,null);
        //查询是否存在数据，如果存在，则更新
        tpf.setIsright(1);
        tpf.setTasktype(tk.getTasktype());
        if(tkList.get(0).getTasktype()==6)
            tpf.setCriteria(2);//如果是微视频
        else
            tpf.setCriteria(1);//提交试卷
        StringBuilder sqlbuilder=new StringBuilder();
        if(tpfList==null||tpfList.size()<1){
            List<Object> objList=this.taskPerformanceManager.getSaveSql(tpf,sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                objArrayList.add(objList);
                sqlArrayList.add(sqlbuilder.toString());
            }
        }else{
            tpf.setRef(tpfList.get(0).getRef());
            List<Object> objList=this.taskPerformanceManager.getUpdateSql(tpf, sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                objArrayList.add(objList);
                sqlArrayList.add(sqlbuilder.toString());
            }
        }

        //得到判断题得分记录得到总分，进行记录
        List<Map<String,Object>> stuScoreSumList=this.stuPaperLogsManager.getStuPaperSumScore(this.logined(request).getUserid(),splog.getPaperid());
        if(stuScoreSumList!=null&&stuScoreSumList.get(0)!=null&&stuScoreSumList.get(0).containsKey("V_SCORE"));
         splog.setScore(Float.parseFloat(stuScoreSumList.get(0).get("V_SCORE").toString()));

        sqlbuilder=new StringBuilder();
        List<Object> objList=this.stuPaperLogsManager.getSaveSql(splog,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            objArrayList.add(objList);
            sqlArrayList.add(sqlbuilder.toString());
        }
        if(sqlArrayList!=null&&sqlArrayList.size()>0&&sqlArrayList.size()==objArrayList.size()){
            if(this.stuPaperLogsManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                jsonEntity.setType("success");
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            }else
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }else
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        response.getWriter().println(jsonEntity.toJSON());
    }


    /**
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doSaveTestPaper",method=RequestMethod.POST)
    public void doSaveTestPaper(HttpServletRequest request,HttpServletResponse response)throws Exception{
       // request.setCharacterEncoding("GBK");
        String testQuesData=request.getParameter("testQuesData");
        String paperid=request.getParameter("paperid");
        JsonEntity jsonEntity=new JsonEntity();
        if(testQuesData==null||testQuesData.length()<1||paperid==null||paperid.length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.toJSON());return ;
        }
        String hasannex=request.getParameter("hasannex");
        String annexName=null;
        boolean isfileOk=true;
        if(hasannex!=null&&hasannex.trim().equals("1")){
            testQuesData=new String(testQuesData.getBytes("iso-8859-1"),"UTF-8");
            MultipartFile[] muFile=this.getUpload(request);
            if(muFile!=null&&muFile.length>0){//文件上传
                //this.setFname(annexName=this.getUploadFileName()[0]);
                this.setFname(annexName=new Date().getTime()+""
                        +this.getUploadFileName()[0].substring(this.getUploadFileName()[0].lastIndexOf("."))
                );
                if(!fileupLoad(request)){
                    isfileOk=!isfileOk;
                }
            }
        }else
            testQuesData=java.net.URLDecoder.decode(testQuesData,"UTF-8");

        if(!isfileOk){
            jsonEntity.setMsg("文件上传失败!请刷新页面后重试");
            response.getWriter().println(jsonEntity.toJSON());return ;
        }
        //去换行符
        testQuesData=testQuesData.replace("\n","\\\\n");
        Integer userid=this.logined(request).getUserid();
        JSONArray jsonArray=JSONArray.fromObject(testQuesData);
        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        Iterator jrIte=jsonArray.iterator();
        while(jrIte.hasNext()){
            net.sf.json.JSONObject clsJo=(net.sf.json.JSONObject)jrIte.next();
            Object questionidObj=clsJo.get("questionid");
            Object answerObj=clsJo.get("answer");
            Object scoreObj=clsJo.get("score");
            Object questypeObj=clsJo.get("questype");
            //验证这个是否正确
            Float score=Float.parseFloat(scoreObj.toString());
            String answer=answerObj.toString();
            Long questionid=Long.parseLong(questionidObj.toString());
            StuPaperQuesLogs stpq=new StuPaperQuesLogs();
            stpq.setPaperid(Long.parseLong(paperid));
            stpq.setQuesid(questionid);
            stpq.setUserid(userid);
            PageResult presult=new PageResult();
            presult.setPageSize(1);
            //查询是否存在
            List<StuPaperQuesLogs> spqlogList=this.stuPaperQuesLogsManager.getList(stpq,presult);
            stpq.setAnswer(answer);
            stpq.setScore(score);
            stpq.setAnnexName(annexName);
            Integer questype=Integer.parseInt(questypeObj.toString());
            if(questype==4||questype==3){
                stpq.setIsright(score>0?1:2);
                stpq.setIsmarking(0);
            }
            //如果存在，则修改
            if(spqlogList!=null&&spqlogList.size()>0){
                StringBuilder sqlbuilder=new StringBuilder();
                List<Object> objList=stuPaperQuesLogsManager.getUpdateSql(stpq,sqlbuilder);
                if(sqlbuilder.toString().length()>0){
                    sqlArrayList.add(sqlbuilder.toString());
                    objArrayList.add(objList);
                }
                continue;
            }
            StringBuilder sqlbuilder=new StringBuilder();
            List<Object> objList=stuPaperQuesLogsManager.getSaveSql(stpq,sqlbuilder);
            if(sqlbuilder.toString().length()>0){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
        }
        if(sqlArrayList!=null&&sqlArrayList.size()>0&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()){
            if(this.stuPaperQuesLogsManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                jsonEntity.setMsg("交卷成功!");
                jsonEntity.setType("success");
            }else
                jsonEntity.setMsg("交卷失败!原因：未知!");
        }else{
            jsonEntity.setMsg("交卷失败!原因：暂未发现您要提交的数据!");
        }
        response.getWriter().println(jsonEntity.toJSON());
    }

    /**
     * 保存学生与微视频的记录
     * @param request
     * @param response
     */
    @RequestMapping(params="m=saveStuMic",method=RequestMethod.POST)
    public void doSaveStuMicVideo(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        //微视频的iD
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");
        String resid=request.getParameter("resid");
        if(resid==null||resid.trim().length()<1||taskid==null||taskid.trim().length()<1||courseid==null||courseid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.toJSON());return;
        }
        //验证任务
        TpTaskInfo tk=new TpTaskInfo();
        tk.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo> tkList=this.tpTaskManager.getList(tk,null);
        if(tkList==null||tkList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.toJSON());return ;
        }
        tk=tkList.get(0);
        if(tk.getTasktype().intValue()==6){
            //添加完成相关记录(微视频)
            TaskPerformanceInfo tpf=new TaskPerformanceInfo();
            tpf.setCourseid(Long.parseLong(courseid.trim()));
            tpf.setTaskid(tk.getTaskid());
            tpf.setUserid(this.logined(request).getRef());
            tpf.setIsright(1);
            tpf.setTasktype(tk.getTasktype());
            tpf.setCriteria(2);//如果是微视频
             if(!this.taskPerformanceManager.doSave(tpf)){
                 jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
                 response.getWriter().println(jsonEntity.toJSON());return;
             }
        }
        //添加视频浏览记录
        StuViewMicVideoLog svmvlog=new StuViewMicVideoLog();
        svmvlog.setMicvideoid(Long.parseLong(resid.trim()));
        svmvlog.setUserid(this.logined(request).getUserid());
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        //验证是否已经查看过。
        List<StuViewMicVideoLog> stuViewMList=this.stuViewMicVideoLogManager.getList(svmvlog,presult);
        if(stuViewMList==null||stuViewMList.size()<1){
            //如果没有，则添加
            if(this.stuViewMicVideoLogManager.doSave(svmvlog))
                jsonEntity.setType("success");
            else
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));

        }else
            jsonEntity.setType("success");
        response.getWriter().println(jsonEntity.toJSON());
    }

}
