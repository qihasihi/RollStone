package com.school.control.teachpaltform.paper;

import com.school.control.base.BaseController;
import com.school.entity.ClassInfo;
import com.school.entity.DictionaryInfo;
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
import com.school.manager.inter.teachpaltform.*;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicThemeManager;
import com.school.manager.inter.teachpaltform.paper.*;
import com.school.manager.teachpaltform.*;
import com.school.manager.teachpaltform.interactive.TpTopicManager;
import com.school.manager.teachpaltform.interactive.TpTopicThemeManager;
import com.school.manager.teachpaltform.paper.*;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.geom.FlatteningPathIterator;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value="/paper")
public class PaperController extends BaseController<PaperInfo>{
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
    private IStuPaperLogsManager stuPaperLogsManager;
    private IStuPaperQuesLogsManager stuPaperQuesLogsManager;
    public PaperController(){
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
        this.stuPaperLogsManager=this.getManager(StuPaperLogsManager.class);
        this.stuPaperQuesLogsManager=this.getManager(StuPaperQuesLogsManager.class);
    }
    /**
	 * ���ݿ���ID�������Ծ��б�
	 * @return
     */
	@RequestMapping(params="toPaperList",method=RequestMethod.GET)
         public ModelAndView toPaperList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        //�õ��ÿ������������������������
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
            tc.setLocalstatus(null);//����
        }else{
            tc.setUserid(this.logined(request).getUserid());
            tc.setCourseid(Long.parseLong(courseid));
            tc.setLocalstatus(1);//����
        }
        List<TpCourseInfo>teacherCourseList=this.tpCourseManager.getTchCourseList(tc, null);
        if(teacherCourseList==null||teacherCourseList.size()<1){
            je.setMsg("�Ҳ���ָ������!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //��ȡ��ǰר��̲�
        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(Long.parseLong(courseid));
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);
        if(materialList!=null&&materialList.size()>0)
            subjectid=materialList.get(0).getSubjectid().toString();
        //������ʽ
        request.setAttribute("coursename", teacherCourseList.get(0).getCoursename());
        TpCourseInfo tcs= new TpCourseInfo();
        tcs.setUserid(this.logined(request).getUserid());
        tcs.setTermid(teacherCourseList.get(0).getTermid());
        tcs.setLocalstatus(1);
        if(subjectid!=null)
            tcs.setSubjectid(Integer.parseInt(subjectid));
        Object gradeid=request.getSession().getAttribute("session_grade");
        if(gradeid!=null&&gradeid.toString().length()>0&&!gradeid.equals("0"))
            tcs.setGradeid(Integer.parseInt(gradeid.toString()));
        List<TpCourseInfo>courseList=this.tpCourseManager.getCourseList(tcs, null);
        request.setAttribute("courseList", courseList);


        String termid=teacherCourseList.get(0).getTermid();
        request.setAttribute("courseid", courseid);
        request.setAttribute("termid", termid);
        request.setAttribute("subjectid", subjectid);

        //ר��ȼ�
        Integer courselevel=teacherCourseList.get(0).getCourselevel();
        request.setAttribute("courselevel",courselevel);

        return new ModelAndView("/teachpaltform/paper/paper-list");
    }


    /**
     * ��׼�������Ծ�Ԥ��
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toPreviewPaper",method=RequestMethod.GET)
    public ModelAndView toPreviewPaper(HttpServletRequest request,HttpServletResponse response)throws Exception{
        //�õ��ÿ������������������������
        JsonEntity je= new JsonEntity();
        String courseid=request.getParameter("courseid");
        String paperid=request.getParameter("paperid");
        String mic=request.getParameter("mic");
        if(courseid==null||courseid.trim().length()<1||paperid==null||paperid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        TpCourseInfo tc=new TpCourseInfo();
        tc.setCourseid(Long.parseLong(courseid));
        List<TpCourseInfo>teacherCourseList=this.tpCourseManager.getList(tc, null);
        if(teacherCourseList==null||teacherCourseList.size()<1){
            je.setMsg("�Ҳ���ָ������!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        List tpCoursePaperList=null;
        if(mic==null){
            TpCoursePaper t=new TpCoursePaper();
            t.setCourseid(Long.parseLong(courseid));
            t.setPaperid(Long.parseLong(paperid));
            tpCoursePaperList=this.tpCoursePaperManager.getList(t, null);
            if(tpCoursePaperList==null||tpCoursePaperList.size()<1){
                je.setMsg("��Ǹ���Ծ��Ѳ�����!");
                je.getAlertMsgAndBack();
                return null;
            }
        }else{
            PaperInfo pp=new PaperInfo();
            pp.setPaperid(Long.parseLong(paperid));
            tpCoursePaperList=this.paperManager.getList(pp, null);
            if(tpCoursePaperList==null||tpCoursePaperList.size()<1){
                je.setMsg("��Ǹ���Ծ��Ѳ�����!");
                je.getAlertMsgAndBack();
                return null;
            }
        }


        //��ȡ���
        PaperQuestion pq=new PaperQuestion();
        pq.setPaperid(Long.parseLong(paperid));
        PageResult p=new PageResult();
        p.setOrderBy("u.order_idx");
        p.setPageNo(0);
        p.setPageSize(0);
        List<PaperQuestion>pqList=this.paperQuestionManager.getList(pq,p);

        //��ȡ����������Ŀ
        PaperQuestion child =new PaperQuestion();
        child.setPaperid(pq.getPaperid());
        List<PaperQuestion>childList=this.paperQuestionManager.getPaperTeamQuestionList(child,null);

        //��ȡѡ��
        QuestionOption questionOption=new QuestionOption();
        questionOption.setPaperid(pq.getPaperid());
        PageResult pchild = new PageResult();
        pchild.setPageNo(0);
        pchild.setPageSize(0);
        pchild.setOrderBy("option_type");
        List<QuestionOption>questionOptionList=this.questionOptionManager.getPaperQuesOptionList(questionOption, pchild);

        //������������ѡ��
        List<PaperQuestion> tmpList=new ArrayList<PaperQuestion>();
        List<QuestionOption>tmpOptionList;
        List<PaperQuestion>questionTeam;
        if(pqList!=null&&pqList.size()>0){
            for(PaperQuestion paperQuestion:pqList){
                questionTeam=new ArrayList<PaperQuestion>();
                //������
                if(childList!=null&&childList.size()>0){
                    for (PaperQuestion childp :childList){
                        //������ѡ��
                        if(paperQuestion.getRef().equals(childp.getRef())){
                            if(questionOptionList!=null&&questionOptionList.size()>0){
                                tmpOptionList=new ArrayList<QuestionOption>();
                                for(QuestionOption qo:questionOptionList){
                                    if(qo.getQuestionid().equals(childp.getQuestionid())){
                                        tmpOptionList.add(qo);
                                    }
                                }
                                childp.setQuestionOption(tmpOptionList);
                                questionTeam.add(childp);
                            }
                        }
                    }
                    paperQuestion.setQuestionTeam(questionTeam);
                }

                if(questionOptionList!=null&&questionOptionList.size()>0){
                    //��ͨ����ѡ��
                    List<QuestionOption> tmp1OptionList=new ArrayList<QuestionOption>();
                    for(QuestionOption qo:questionOptionList){
                        if(qo.getQuestionid().equals(paperQuestion.getQuestionid())){
                            tmp1OptionList.add(qo);
                        }
                    }

                    paperQuestion.setQuestionOption(tmp1OptionList);
                }
                tmpList.add(paperQuestion);
            }
        }

       /* if(pqList!=null&&pqList.size()>0){
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
        }*/


        //�õ���ǰ����������
        List<Map<String,Object>> listMapStr=this.paperQuestionManager.getPaperQuesAllId(Long.parseLong(paperid));
        if(listMapStr==null||listMapStr.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(je.getAlertMsgAndBack());return null;
        }
        Object allquesidObj=listMapStr.get(0).get("ALLQUESID");
        if(allquesidObj==null||allquesidObj.toString().trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(je.getAlertMsgAndBack());return null;
        }

        request.setAttribute("pqList", tmpList);
        request.setAttribute("paper", tpCoursePaperList.get(0));
        request.setAttribute("courseid", courseid);
        request.setAttribute("coursename",teacherCourseList.get(0).getCoursename());
        request.setAttribute("ALLQUESID", allquesidObj);
        return new ModelAndView("/teachpaltform/paper/preview-paper");
    }


    /**
     * ɾ�����ָ��Ծ�
     * @throws Exception
     */
    @RequestMapping(params="m=doOperatePaper",method=RequestMethod.POST)
    public void doOperatePaper(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String ref=request.getParameter("ref");
        String flag=request.getParameter("flag");
        if(ref==null||ref.trim().length()<1||
                flag==null||flag.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpCoursePaper cp=new TpCoursePaper();
        cp.setRef(Integer.parseInt(ref));
        cp.setLocalstatus(Integer.parseInt(flag));
        if(this.tpCoursePaperManager.doUpdate(cp)){
            je.setType("success");
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }

    /**
     * ��ȡ��ɾ���Ծ�
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=loadRecyclePaper",method=RequestMethod.POST)
    public void loadRecycleQuestion(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String courseid=request.getParameter("courseid");
        if(courseid==null||courseid.length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return;
        }
        TpCoursePaper coursePaper=new TpCoursePaper();
        coursePaper.setCourseid(Long.parseLong(courseid));
        coursePaper.setLocalstatus(2); //��ɾ��
        coursePaper.setSeldatetype(1);//���һ����
        PageResult pageResult=this.getPageResultParameter(request);
        pageResult.setOrderBy(" u.m_time desc");
        List<TpCoursePaper>tpCoursePaperList=this.tpCoursePaperManager.getList(coursePaper,pageResult);
        pageResult.setList(tpCoursePaperList);
        je.setPresult(pageResult);
        response.getWriter().print(je.toJSON());
    }

    /**
     * �������ҳ ��ȡ�Ծ�
     * @throws Exception
     */
    @RequestMapping(params="toQueryTaskPaperList",method=RequestMethod.POST)
    public void toQueryResourceList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String type=request.getParameter("type");
        String paperid=request.getParameter("paperid");
        String taskflag=request.getParameter("taskflag");
        if(courseid==null||courseid.trim().length()<1||
                type==null||type.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        PageResult p=this.getPageResultParameter(request);
        p.setOrderBy(" p.paper_type,u.c_time desc,u.paper_id desc ");
        TpCoursePaper t= new TpCoursePaper();
        t.setCourseid(Long.parseLong(courseid));
        t.setLocalstatus(1);
        t.setSelecttype(Integer.parseInt(type));
        if(paperid!=null&&paperid.trim().length()>0)
            t.setPaperid(Long.parseLong(paperid));
        //ѧϰ�ο�
        //t.setResourcetype(1);
        //��ѯû�з��������Դ
        if(taskflag!=null&&taskflag.trim().length()>0)
            t.setTaskflag(1);
        List<TpCoursePaper>paperList=this.tpCoursePaperManager.getList(t, p);
        je.setPresult(p);
        je.setObjList(paperList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }



    /**
     * ��ȡѧ�������б�
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
        // ѧ������
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

                    /*ѡ�����*/
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
                //��ȡ�����¼���ĵ�
                QuestionAnswer qa=new QuestionAnswer();
                qa.setTaskid(task.getTaskid());
                qa.setUserid(this.logined(request).getRef());
                List<QuestionAnswer>qaList=this.questionAnswerManager.getList(qa, null);
                task.setQuestionAnswerList(qaList);

                //ѧϰʱ��
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
     * �������--ѡ���Ծ�
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=toSelTaskPaper",method=RequestMethod.GET)
    public ModelAndView toSelTaskPaper(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String courseid=request.getParameter("courseid");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        TpCourseInfo tc=new TpCourseInfo();
        tc.setCourseid(Long.parseLong(courseid));
        List<TpCourseInfo>teacherCourseList=this.tpCourseManager.getTchCourseList(tc, null);
        if(teacherCourseList==null||teacherCourseList.size()<1){
            je.setMsg("�Ҳ���ָ������!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //ר��ȼ�
        Integer courselevel=teacherCourseList.get(0).getCourselevel();
        request.setAttribute("courselevel",courselevel);
        request.setAttribute("courseid",courseid);
        return new ModelAndView("/teachpaltform/paper/select-paper");
    }


    /**
     * ��ȡ�Ծ��б�
     * @throws Exception
     */
    @RequestMapping(params="m=ajaxPaperList",method=RequestMethod.POST)
    public void ajaxTaskBankList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        PageResult p=this.getPageResultParameter(request);
        p.setOrderBy(" p.paper_type,u.c_time desc,u.paper_id desc ");
        TpCoursePaper t=new TpCoursePaper();
        t.setCourseid(Long.parseLong(courseid));
        t.setLocalstatus(1);
        t.setSelecttype(7); //��ѯ�������������Ե��Ծ�
        List<TpCoursePaper>coursePaperList=this.tpCoursePaperManager.getList(t,p);
        p.setList(coursePaperList);
        je.setPresult(p);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * ��ѯ�����Ծ��б�
     * @throws Exception
     */
    @RequestMapping(params="m=getImportPaperList",method=RequestMethod.POST)
    public void getImportPaperList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
       // String materialid=request.getParameter("materialid");
        String gradeid=request.getParameter("gradeid");
        String subjectid=request.getParameter("subjectid");
        String coursename=request.getParameter("coursename");
        if(courseid==null||courseid.trim().length()<1
                ||coursename==null||coursename.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }

        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(Long.parseLong(courseid));
        if(gradeid!=null&&gradeid.length()>0)
            ttm.setGradeid(Integer.parseInt(gradeid));
        if(subjectid!=null&&subjectid.length()>0)
            ttm.setSubjectid(Integer.parseInt(subjectid));
        String order=" u.c_time desc";
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);
        if(materialList!=null&&materialList.size()>0){
            order="field(tcm.teaching_material_id";
            for(TpCourseTeachingMaterial ctm:materialList){
                order+=","+ctm.getTeachingmaterialid();
            }
            order+=") desc";
        }
        PageResult p=this.getPageResultParameter(request);
        p.setOrderBy(order);
        TpCoursePaper t=new TpCoursePaper();
        t.setFiltercourseid(Long.parseLong(courseid));//�ų���ǰר��
        t.setLocalstatus(1);
        t.setCoursename(coursename);


      //  if(materialid!=null&&materialid.length()>0)
      //      t.setMaterialid(Integer.parseInt(materialid));
        if(gradeid!=null&&gradeid.length()>0)
            t.setGradeid(Integer.parseInt(gradeid));
        if(subjectid!=null&&subjectid.length()>0)
            t.setSubjectid(Integer.parseInt(subjectid));

        List<TpCoursePaper>coursePaperList=this.tpCoursePaperManager.getList(t, p);
        p.setList(coursePaperList);
        je.setPresult(p);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * ɾ������
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

        //����������¼
        List<List<Object>> objListArray=new ArrayList<List<Object>>();
        List<String> sqlStrList=new ArrayList<String>();
        StringBuilder sql=null;
        List<Object>objList=null;




        TpTaskInfo tmpTask=taskList.get(0);

        TpTaskInfo sel=new TpTaskInfo();
        sel.setCourseid(tmpTask.getCourseid());
        //��ѯû����ɾ��������
        sel.setSelecttype(1);
        sel.setLoginuserid(this.logined(request).getUserid());
        sel.setStatus(1);

        //1 2 3 4 5
        //�ѷ���������
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
            to.setTargetid(tmpTask.getTaskid());             //�½�����ID
            to.setOperatetype(1);              //ɾ��
            to.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_TASK.getValue());                 //ר������
            sql=new StringBuilder();
            objList=this.tpOperateManager.getSaveSql(to,sql);
            if(objList!=null&&sql!=null){
                sqlStrList.add(sql.toString());
                objListArray.add(objList);
            }

            //������־
            sql=new StringBuilder();
            objList=this.tpOperateManager.getAddOperateLog(this.logined(request).getRef(),"TP_OPERATE_INFO", to.getTargetid().toString(),null,null,"DELETE"
                    ,"���Ӳ�����¼",sql);
            if(sql!=null&&sql.toString().trim().length()>0){
                objListArray.add(objList);
                sqlStrList.add(sql.toString());
            }
        }else{*/
        t.setStatus(2); //�޸ı���״̬Ϊ��ɾ��
        t.setOrderidx(-1);
        sql=new StringBuilder();
        objList=this.tpTaskManager.getUpdateSql(t,sql);
        if(sql!=null&&sql.toString().trim().length()>0){
            objListArray.add(objList);
            sqlStrList.add(sql.toString());
        }

        //������־
       /* sql=new StringBuilder();
        objList=this.tpOperateManager.getAddOperateLog(this.logined(request).getRef(),"TP_TASK_INFO", t.getTaskid().toString(),null,null,"UPDATE"
                ,"�޸ı���״̬",sql);
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
     * �������
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toAddTask",method=RequestMethod.GET)
    public ModelAndView toAddTask(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je =new JsonEntity();
        String courseid=request.getParameter("courseid");
        String termid=request.getParameter("termid");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg("����,ϵͳδ��ȡ�������ʶ!");
            je.getAlertMsgAndBack();
            return null;
        }
        //��������
        List<DictionaryInfo>tasktypeList=this.dictionaryManager.getDictionaryByType("TP_TASK_TYPE");
        //��ÿ�������İ༶
        TpCourseClass c=new TpCourseClass();
        c.setCourseid(Long.parseLong(courseid));
        List<TpCourseClass>courseclassList=this.tpCourseClassManager.getList(c, null);
        if(courseclassList==null||courseclassList.size()<1){
            je.setMsg("����δ��ȡ���ÿ���İ༶��Ϣ!�����ú��������!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }

        //��֤�Ƿ�����ѧ��������С��
		/*for (TpCourseClass cc : courseclassList) {
			if(cc.getClassid()!=null&&cc.getClasstype().intValue()==1){
				//��ȡ�༶ѧ��������Ĭ��С��
				TpGroupInfo tmpg=new TpGroupInfo();
				tmpg.setClassid(cc.getClassid());
				//tmpg.setTeacherid(t.getTeacherId());
				tmpg.setCuserid(this.logined(request).getUserid());
				List<TpGroupInfo>tmpgList=this.tpGroupManager.getList(tmpg, null);

				ClassUser tmpu=new ClassUser();
				tmpu.setClassid(cc.getClassid());
				tmpu.setRelationtype("ѧ��");
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
					saveg.setGroupname("ȫ��");
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
				//�༶������
				ClassUser cu=new ClassUser();
				cu.getClassinfo().setClassid(cc.getClassid());
				cu.setRelationtype("ѧ��");
				int count=0;
				List<ClassUser>cuList=this.classUserManager.getList(cu, null);
				//С��������
				if(cuList!=null&&cuList.size()>0)
					count=cuList.size();
				TpGroupStudent gs=new TpGroupStudent();
				gs.setClassid(cc.getClassid());
				gs.setUserid(this.logined(request).getUserid());
				List<TpGroupStudent>gsList=this.tpGroupStudentManager.getGroupStudentByClass(gs, null);
				if(count>0&&gsList!=null&&gsList.size()>0){
					if(count>gsList.size()){
						je.setMsg("��ʾ��"+cc.getClassname()+"����ѧ��δ����С��!������!");
						response.getWriter().print(je.getAlertMsgAndSendRedirect("group?m=toGroupManager&termid="+termid));
						return null;
					}
				}else{
					je.setMsg("��ʾ��"+cc.getClassname()+"С����ԱΪ��!������!");
					response.getWriter().print(je.getAlertMsgAndSendRedirect("group?m=toGroupManager&termid="+termid));
					return null;
				}
			}else{
                //��ȡ�༶ѧ��������Ĭ��С��
                TpGroupInfo tmpg=new TpGroupInfo();
                tmpg.setClassid(cc.getClassid());
                //tmpg.setTeacherid(t.getTeacherId());
                tmpg.setCuserid(this.logined(request).getUserid());
                List<TpGroupInfo>tmpgList=this.tpGroupManager.getList(tmpg, null);

                TpVirtualClassStudent tmpu=new TpVirtualClassStudent();
                tmpu.setVirtualclassid(cc.getClassid());
                //tmpu.setRelationtype("ѧ��");
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
                    saveg.setGroupname("ȫ��");
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
                //�༶������
                TpVirtualClassStudent cu=new TpVirtualClassStudent();
                cu.setVirtualclassid(cc.getClassid());
                //cu.setRelationtype("ѧ��");
                int count=0;
                List<TpVirtualClassStudent>cuList=this.tpVirtualClassStudentManager.getList(cu, null);
                //С��������
                if(cuList!=null&&cuList.size()>0)
                    count=cuList.size();
                TpGroupStudent gs=new TpGroupStudent();
                gs.setClassid(cc.getClassid());
                gs.setUserid(this.logined(request).getUserid());
                List<TpGroupStudent>gsList=this.tpGroupStudentManager.getGroupStudentByClass(gs, null);
                if(count>0&&gsList!=null&&gsList.size()>0){
                    if(count>gsList.size()){
                        je.setMsg("��ʾ��"+cc.getClassname()+"����ѧ��δ����С��!������!");
                        response.getWriter().print(je.getAlertMsgAndSendRedirect("group?m=toGroupManager&termid="+termid));
                        return null;
                    }
                }else{
                    je.setMsg("��ʾ��"+cc.getClassname()+"С����ԱΪ��!������!");
                    response.getWriter().print(je.getAlertMsgAndSendRedirect("group?m=toGroupManager&termid="+termid));
                    return null;
                }
            }
		} */
        //С��
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
     * ��ȡ��������
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
     * �Ծ����
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
            je.setMsg("����,δ��ȡ�������ʶ!");
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

        //����Ծ�
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

        //���������ר�����
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
                //����ר���������
                TpOperateInfo to=new TpOperateInfo();
                to.setRef(this.tpOperateManager.getNextId(true));
                to.setCuserid(this.logined(request).getUserid());
                to.setCourseid(tmpCourse.getQuoteid());
                //to.setTargetid(tasknextid);
                to.setOperatetype(2);                                                              //���
                to.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_TASK.getValue());                 //ר������
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
                //���������Ϣ����
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
                    System.out.println("�������̬ʧ��!ԭ��δ��ȡ��ѧ���б�!");
                }*/

            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }else{
            je.setMsg("���Ĳ���û��ִ��!");
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * �����Ծ�ѡ�⡢�༭ҳ��
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=editPaperQuestion",method={RequestMethod.GET,RequestMethod.POST})
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
            je.setMsg("��Ǹ���Ծ��Ѳ�����!");
            je.getAlertMsgAndBack();
            return null;
        }

        //��ȡ���
        PaperQuestion pq=new PaperQuestion();
        pq.setPaperid(Long.parseLong(paperid));
        PageResult p=new PageResult();
        p.setOrderBy("u.order_idx");
        p.setPageNo(0);
        p.setPageSize(0);
        List<PaperQuestion>pqList=this.paperQuestionManager.getList(pq,p);

        //��ȡ����������Ŀ
        PaperQuestion child =new PaperQuestion();
        child.setPaperid(pq.getPaperid());
        List<PaperQuestion>childList=this.paperQuestionManager.getPaperTeamQuestionList(child,null);

        //��ȡѡ��
        QuestionOption questionOption=new QuestionOption();
        questionOption.setPaperid(pq.getPaperid());
        PageResult pchild = new PageResult();
        pchild.setPageNo(0);
        pchild.setPageSize(0);
        pchild.setOrderBy("option_type");
        List<QuestionOption>questionOptionList=this.questionOptionManager.getPaperQuesOptionList(questionOption, pchild);

        //������������ѡ��
        List<PaperQuestion> tmpList=new ArrayList<PaperQuestion>();
        List<QuestionOption>tmpOptionList;
        List<PaperQuestion>questionTeam;
        if(pqList!=null&&pqList.size()>0){
            for(PaperQuestion paperQuestion:pqList){
                questionTeam=new ArrayList<PaperQuestion>();
                 //������
                if(childList!=null&&childList.size()>0){
                    for (PaperQuestion childp :childList){
                        //������ѡ��
                        if(paperQuestion.getRef().equals(childp.getRef())){
                            if(questionOptionList!=null&&questionOptionList.size()>0){
                                tmpOptionList=new ArrayList<QuestionOption>();
                                for(QuestionOption qo:questionOptionList){
                                    if(qo.getQuestionid().equals(childp.getQuestionid())){
                                        tmpOptionList.add(qo);
                                    }
                                }
                                childp.setQuestionOption(tmpOptionList);
                                questionTeam.add(childp);
                            }
                        }
                    }
                    paperQuestion.setQuestionTeam(questionTeam);
                }

                if(questionOptionList!=null&&questionOptionList.size()>0){
                    //��ͨ����ѡ��
                    List<QuestionOption> tmp1OptionList=new ArrayList<QuestionOption>();
                    for(QuestionOption qo:questionOptionList){
                        if(qo.getQuestionid().equals(paperQuestion.getQuestionid())){
                            tmp1OptionList.add(qo);
                        }
                    }

                    paperQuestion.setQuestionOption(tmp1OptionList);
                }
                tmpList.add(paperQuestion);
            }
        }


    /*
        List<PaperQuestion> tmpList=new ArrayList<PaperQuestion>();
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
    */
        Integer paperQuesSize=this.paperQuestionManager.paperQuesCount(Long.parseLong(paperid));
        //��ǰ�Ծ������������ȥ��ɣ�
        request.setAttribute("pageQuesSize",paperQuesSize);
        request.setAttribute("pqList", tmpList);
        request.setAttribute("childList", childList);
        /*request.setAttribute("optionList", questionOptionList);*/
        request.setAttribute("paper", tpCoursePaperList.get(0));
        request.setAttribute("courseid",courseid);
        return new ModelAndView("/teachpaltform/paper/edit-paper");
    }


    /**
     * ģʽ�����ѯ�����Ծ�
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=dialogPaper",method=RequestMethod.GET)
    public ModelAndView dialogPaper(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je =new JsonEntity();//
        String courseid=request.getParameter("courseid");
        String paperid=request.getParameter("paperid");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            je.getAlertMsgAndBack();
            return null;
        }
        TpCourseInfo tc=new TpCourseInfo();
        tc.setUserid(this.logined(request).getUserid());
        tc.setCourseid(Long.parseLong(courseid));
        tc.setLocalstatus(1);//����
        List<TpCourseInfo>teacherCourseList=this.tpCourseManager.getTchCourseList(tc, null);
        if(teacherCourseList==null||teacherCourseList.size()<1){
            je.setMsg("�Ҳ���ָ������!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }

        //��ȡ��ǰר��̲�
        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(Long.parseLong(courseid));
        Object subjectid=request.getSession().getAttribute("session_subject");
        Object gradeid=request.getSession().getAttribute("session_grade");
        Object materialid=request.getSession().getAttribute("session_material");
        if(subjectid!=null)
            ttm.setSubjectid(Integer.parseInt(subjectid.toString()));
        if(gradeid!=null)
            ttm.setGradeid(Integer.parseInt(gradeid.toString()));
        if(materialid!=null)
            ttm.setTeachingmaterialid(Integer.parseInt(materialid.toString()));
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);
        if(materialList!=null&&materialList.size()>0){
            subjectid=materialList.get(0).getSubjectid().toString();
            materialid=materialList.get(0).getTeachingmaterialid().toString();
            gradeid=materialList.get(0).getGradeid().toString();
        }

        //��ѯ����ר����Ծ�
        TpCoursePaper sel=new TpCoursePaper();
        sel.setCourseid(Long.parseLong(courseid));
        List<TpCoursePaper>coursePaperList=this.tpCoursePaperManager.getRelateCoursePaPerList(sel,null);

        List<TpCoursePaper>standardList=new ArrayList<TpCoursePaper>();
        List<TpCoursePaper>nativeList=new ArrayList<TpCoursePaper>();
        if(coursePaperList!=null&&coursePaperList.size()>0){
            for(TpCoursePaper cp:coursePaperList){
                if(cp.getPaperid()>0){
                    standardList.add(cp);
                }else if(cp.getPaperid()<0){
                    nativeList.add(cp);
                }
            }
        }


        request.setAttribute("standardList", standardList);
        request.setAttribute("nativeList", nativeList);
        request.setAttribute("courseid", courseid);
        request.setAttribute("paperid", paperid);
        request.setAttribute("subjectid", subjectid);
        request.setAttribute("materialid", materialid);
        request.setAttribute("gradeid", gradeid);
        return new ModelAndView("/teachpaltform/paper/add/import-paper");
    }

    /**
     * ģʽ�����ѯ��������
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=dialogQuestion",method=RequestMethod.GET)
    public ModelAndView dialogQuestion(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je =new JsonEntity();//
        String courseid=request.getParameter("courseid");
        String paperid=request.getParameter("paperid");
        if(courseid==null||courseid.trim().length()<1||
                paperid==null||paperid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            je.getAlertMsgAndBack();
            return null;
        }
        TpCourseInfo tc=new TpCourseInfo();
        tc.setUserid(this.logined(request).getUserid());
        tc.setCourseid(Long.parseLong(courseid));
        tc.setLocalstatus(1);//����
        List<TpCourseInfo>teacherCourseList=this.tpCourseManager.getTchCourseList(tc, null);
        if(teacherCourseList==null||teacherCourseList.size()<1){
            je.setMsg("�Ҳ���ָ������!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }

        //��ȡ��ǰר��̲�

        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(Long.parseLong(courseid));
        Object subjectid=request.getSession().getAttribute("session_subject");
        Object gradeid=request.getSession().getAttribute("session_grade");
        Object materialid=request.getSession().getAttribute("session_material");
        if(subjectid!=null)
            ttm.setSubjectid(Integer.parseInt(subjectid.toString()));
        if(gradeid!=null)
            ttm.setGradeid(Integer.parseInt(gradeid.toString()));
        if(materialid!=null)
            ttm.setTeachingmaterialid(Integer.parseInt(materialid.toString()));
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);
        if(materialList!=null&&materialList.size()>0){
            subjectid=materialList.get(0).getSubjectid().toString();
            materialid=materialList.get(0).getTeachingmaterialid().toString();
            gradeid=materialList.get(0).getGradeid().toString();
        }

        //��ѯ����ר����Ծ�
/*        TpCoursePaper sel=new TpCoursePaper();
        sel.setCourseid(Long.parseLong(courseid));
        List<TpCoursePaper>coursePaperList=this.tpCoursePaperManager.getRelateCoursePaPerList(sel,null);
        request.setAttribute("coursePaperList", coursePaperList);*/



        request.setAttribute("courseid", courseid);
        request.setAttribute("paperid", paperid);
        request.setAttribute("subjectid", subjectid);
        request.setAttribute("materialid", materialid);
        request.setAttribute("gradeid", gradeid);
        return new ModelAndView("/teachpaltform/paper/add/import-ques");
    }



    /**
     * �����޸�
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
        //С��
        String[]groupArray=request.getParameterValues("groupArray");
        //�༶
        String[]clsArray=request.getParameterValues("clsArray");
        String[]bClsArray=request.getParameterValues("btimeClsArray");
        String[]eClsArray=request.getParameterValues("etimeClsArray");


        if(StringUtils.isBlank(courseid)){
            je.setMsg("����,δ��ȡ�������ʶ!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if((groupArray==null||groupArray.length<1)
                &&(clsArray==null||clsArray.length<1)){
            je.setMsg("����,δ��ȡ���������!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(!(StringUtils.isNotBlank(questype)&&questype.equals("5"))&&criteriaArray.length<1){
            je.setMsg("����,δ��ȡ��������ɱ�׼!");
            response.getWriter().print(je.toJSON());
            return;
        }
        StringBuilder sql=null;
        List<Object>objList=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        Long tasknextid;
        TpTaskInfo ta=new TpTaskInfo();

        //�κ���ҵ 3
        if(tasktype.toString().equals("3")){
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("����ϵͳδ��ȡ�������ʶ!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpCourseQuestion q=new TpCourseQuestion();
            q.setQuestionid(Long.parseLong(taskvalueid));
            List<TpCourseQuestion>quesList=this.tpCourseQuestionManager.getList(q,null);
            if(quesList==null||quesList.size()<1){
                je.setMsg("��ʾ����ǰ�����Ѳ����ڻ�ɾ��!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));
        }else if(tasktype.toString().equals("2")){//����
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("����ϵͳδ��ȡ�������ʶ!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpTopicInfo i=new TpTopicInfo();
            i.setTopicid(Long.parseLong(taskvalueid));
            List<TpTopicInfo>iList=this.tpTopicManager.getList(i, null);
            if(iList==null||iList.size()<1){
                je.setMsg("��ʾ����ǰ�����Ѳ����ڻ�ɾ������ˢ��ҳ�������!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));
        }else if(tasktype.toString().equals("1")){//��Դ
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("����ϵͳδ��ȡ����Դ��ʶ!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpCourseResource t=new TpCourseResource();
            t.setResid(Long.parseLong(taskvalueid));
            List<TpCourseResource>resList=this.tpCourseResourceManager.getList(t, null);
            if(resList==null||resList.size()<1){
                je.setMsg("��ʾ����ǰ��Դ�Ѳ����ڻ�ɾ������ˢ��ҳ�������!");
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
        //��ȡ����
        TpTaskInfo sel=new TpTaskInfo();
        sel.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo>taskList=this.tpTaskManager.getList(sel,null);
        if(taskList==null||taskList.size()<1){
            je.setMsg("��ʾ����ǰ�����Ѳ����ڻ�ɾ������ˢ��ҳ�������!");
            response.getWriter().print(je.toJSON());
            return;
        }

        TpTaskInfo tmpTask=taskList.get(0);
        /**
         * �ο�����
         * 1����Ԫ���޸ģ�����������
         * 2�½����񣬲��������ݵ�Ԫ�ز������С�
         * 3ɾ������������ݡ��������������Ϣ��
         */
        if(tmpTask.getCloudstatus().intValue()==3){
            //��������Ƿ��޸ģ��ǣ���������������ֱ��ʹ��
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
                //����ר���������
                TpOperateInfo to=new TpOperateInfo();
                to.setRef(this.tpOperateManager.getNextId(true));
                to.setCuserid(this.logined(request).getUserid());
                to.setCourseid(tmpTask.getQuoteid());
                to.setTargetid(tasknextid);             //�½�����ID
                to.setReferenceid(tmpTask.getTaskid()); //����������ID
                to.setOperatetype(3);              //�޸�
                to.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_TASK.getValue());                 //ר������
                sql=new StringBuilder();
                objList=this.tpOperateManager.getSaveSql(to,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }*/
        }else{
            /**
             *�Խ������޸�
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


        //ɾ���������
        TpTaskAllotInfo tdelete=new TpTaskAllotInfo();
        tdelete.setTaskid(tmpTask.getTaskid());
        tdelete.setCourseid(tmpTask.getCourseid());
        sql=new StringBuilder();
        objList=this.tpTaskAllotManager.getDeleteSql(tdelete,sql);
        if(sql!=null&&objList!=null){
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }
        //���������� С��
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
        //���������� �༶
        if(clsArray!=null&&clsArray.length>0){
            for (int i=0;i<clsArray.length;i++) {
                TpTaskAllotInfo tal=new TpTaskAllotInfo();
                //��֤��ʲô���͵İ༶
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
                    je.setMsg("����!����༶��Ч!");
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
                //���������Ϣ����
                //this.tpTaskManager.doSaveTaskMsg(ta);
            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }else{
            je.setMsg("���Ĳ���û��ִ��!");
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * ��ת��ѧ��������ҳ
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toStuTaskIndex",method=RequestMethod.GET)
    public ModelAndView toStuTaskIndex(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
//		if(!this.validateRole(new BigDecimal(UtilTool._STUDENT_ROLE_ID))){
//			je.setMsg("��Ǹ����ǰҳ��ֻ����ѧ���û��鿴!");
//			response.getWriter().print(je.getAlertMsgAndBack());
//			return null;
//		}
        String courseid=request.getParameter("courseid");
        String termid=request.getParameter("termid");
        //���ݿ���ID��ѧ��ID�������
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
//			je.setMsg("��ʾ��û�з���������Ϣ!");
//			response.getWriter().print(je.getAlertMsgAndBack());
//			return null;
//		}
        //��ѯѧ�������б�
        Integer subjectid=null;
        //��ȡ��ǰר��̲�
        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(Long.parseLong(courseid));
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);
        if(materialList!=null&&materialList.size()>0)
            subjectid=materialList.get(0).getSubjectid();

        //�����б�
        TpCourseInfo tt=new TpCourseInfo();
        tt.setCourseid(Long.parseLong(courseid));
        tt.setLocalstatus(1);   //1������ 2��ɾ��
        tt.setUserid(this.logined(request).getUserid());
        List<TpCourseInfo>courseList=this.tpCourseManager.getStuCourseList(tt, null);
        if(courseList==null||courseList.size()<1){
            je.setMsg("����û�з��ֵ�ǰר��!��ˢ�º�����!");
            response.getWriter().print(je.getAlertMsgAndBack());return null;
        }else{
            request.setAttribute("coursename", courseList.get(0).getCoursename());
            //������ʽ
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
		 *  ¬��(422812115) ���� 2013-11-22 4:23:07
		 *  �л�ר����е�ǰר�⣿���ң�ѧ���ˣ�ר���ܲ��ܰ�ѧ������һ��

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
				//�κ���ҵ
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
					//��ȷ�𰸣�ֻ�пκ���ҵ�У�
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

						//��ȡ����
						InteractiveTopicInfo itopic=new InteractiveTopicInfo();
						itopic.setThemeid(tp.getTaskvalueid());
						itopic.setCuserid(this.logined(request).getRef());
						List<InteractiveTopicInfo>topicList=this.interactiveTopicManager.getList(itopic, null);
						tp.setTpThemeList(topicList);
						//��ȡ��������
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

				//��ȡ�����¼���ĵ�
				QuestionAnswer qa=new QuestionAnswer();
				qa.setTaskid(tp.getTaskid());
				qa.setUserid(this.logined(request).getRef());
				List<QuestionAnswer>qaList=this.questionAnswerManager.getList(qa, null);
				tp.setTpQuestionAnswerList(qaList);

				//��ѯ���״̬(Ц�������)
				TaskPerformanceInfo tper=new TaskPerformanceInfo();
				tper.setTaskid(tp.getTaskid());
				tper.setUserid(this.logined(request).getRef());
				List<TaskPerformanceInfo>performList=this.taskPerformanceManager.getStuPerformanceStatus(tper);
				tp.setTpTaskPerformList(performList);

				//����δ��
				TaskPerformanceInfo taskdo=new TaskPerformanceInfo();
				taskdo.setTaskid(tp.getTaskid());
				taskdo.setUserid(this.logined(request).getRef());
				List<TaskPerformanceInfo>doperformList=this.taskPerformanceManager.getList(taskdo, null);
				tp.setTpDoPerformList(doperformList);

				//��ɱ�׼
				TaskCriteriaInfo tc=new TaskCriteriaInfo();
				tc.setTaskid(tp.getTaskid());
				List<TaskCriteriaInfo>taskCriList=this.taskCriteriaManager.getList(tc,null);
				tp.setTpTaskCriterList(taskCriList);

				//С����Ա
				GroupStudent gs=new GroupStudent();
				gs.setGroupid(tp.getGroupid());
				List<GroupStudent>groupStuList=this.groupStudentManager.getList(gs,null);
				tp.setTpGroupStuList(groupStuList);

				//С��������ɵ�����
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
     * ѧ���ύ����
     * @throws Exception
     */
    @RequestMapping(params="doStuSubmitTask",method=RequestMethod.POST)
    public void doStuSubmitTask(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");
        String tasktype=request.getParameter("tasktype");
        //String groupid=request.getParameter("groupid");
        //��Դ�����⡢�κ���ҵID
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

        //�κ���ҵ
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
                je.setMsg("����ϵͳδ��ȡ����������!");
                response.getWriter().print(je.toJSON());
                return;
            }


            if(questype.equals("1")){//�ʴ�
                if(quesanswer==null||quesanswer.trim().length()<1){
                    je.setMsg("����δ��ȡ���ʴ����!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                //¼������¼
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

                //¼�����״̬
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
            }else if(questype.equals("2")){//���
                if(fbanswerArray==null||fbanswerArray.length<1){
                    je.setMsg("����δ��ȡ���ʴ����!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                //�õ������ʦ���õĴ�
                QuestionInfo qb=new QuestionInfo();
                qb.setQuestionid(tmpTask.getTaskvalueid());
                List<QuestionInfo>qbList=this.questionManager.getList(qb, null);
                if(qbList==null||qbList.size()<1){
                    je.setMsg("����!��ǰ�����Ѳ�����!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                boolean fbflag=false;
                String fbanswer=qbList.get(0).getCorrectanswer();
                String[]answerArray=null;
                if(fbanswer!=null&&fbanswer.length()>0){
                    answerArray=fbanswer.split("\\|");
                    if(answerArray.length<1){
                        je.setMsg("����!δ��ȡ��������ʦ���õĴ�!");
                        response.getWriter().print(je.toJSON());
                        return;
                    }else if(answerArray.length!=fbanswerArray.length){
                        je.setMsg("����!�������Ŀ�������!");
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
                    je.setMsg("����!ϵͳδ��ȡ��������!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                //¼������¼
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

                //¼�����״̬
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
            }else if(questype.equals("3")){//��ѡ
                if(optionArray==null||optionArray.length<1){
                    je.setMsg("����δ��ȡ��ѡ�����!");
                    response.getWriter().print(je.toJSON());
                    return;
                }

                //�õ������ʦ���õĴ�
                QuestionOption qo=new QuestionOption();
                qo.setQuestionid(tmpTask.getTaskvalueid());
                qo.setIsright(1);
                List<QuestionOption>qbList=this.questionOptionManager.getList(qo, null);
                if(qbList==null||qbList.size()<1){
                    je.setMsg("����!δ��ȡ����ʦ���õ�ѡ�����!");
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
                //�õ���ǰѡ������
                QuestionOption qstu=new QuestionOption();
                qstu.setRef(Integer.parseInt(optionArray[0]));
                List<QuestionOption>qbstuList=this.questionOptionManager.getList(qstu, null);
                if(qbstuList==null||qbstuList.size()<1){
                    je.setMsg("����!��ǰѡ���Ѳ�����!");
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
                qa.setAnswercontent(qbstuList.get(0).getOptiontype());//ѡ�����
                qa.setRightanswer(selflag==true?1:0);
                sql=new StringBuilder();
                objList=this.questionAnswerManager.getSaveSql(qa, sql);
                if(objList!=null&&sql!=null){
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }

                //¼�����״̬
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

            }else if(questype.equals("4")){//��ѡ
                if(optionArray==null||optionArray.length<1){
                    je.setMsg("����δ��ȡ����ѡ���!");
                    response.getWriter().print(je.toJSON());
                    return;
                }

                //�õ������ʦ���õĴ�
                QuestionOption qb=new QuestionOption();
                qb.setQuestionid(tmpTask.getTaskvalueid());
                qb.setIsright(1);
                List<QuestionOption>qbList=this.questionOptionManager.getList(qb, null);
                if(qbList==null||qbList.size()<1){
                    je.setMsg("����!δ��ȡ����ʦ���õĸ�ѡ���!");
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

                    //�õ���ǰѡ������
                    QuestionOption qstu=new QuestionOption();
                    qstu.setRef(Integer.parseInt(opnid));
                    List<QuestionOption>qbstuList=this.questionOptionManager.getList(qstu, null);
                    if(qbstuList==null||qbstuList.size()<1){
                        je.setMsg("����!��ǰѡ���Ѳ�����!");
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
                    qa.setAnswercontent(qbstuList.get(0).getOptiontype()); //ѡ�����
                    qa.setRightanswer(selflag==true?1:0);
                    sql=new StringBuilder();
                    objList=this.questionAnswerManager.getSaveSql(qa, sql);
                    if(objList!=null&&sql!=null){
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                }

                //¼�����״̬
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
                je.setMsg("����,δ��ȡ����Դѧϰ�ĵ�!");
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

            //¼�����״̬
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
            je.setMsg("���Ĳ���û��ִ��!");
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * ��ȡ�����µ�����
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
            je.setMsg("����ϵͳδ��ȡ�������ʶ!");
            response.getWriter().print(je.toJSON());
            return;
        }
//		if(clsid==null||clsid.trim().length()<1){
//			je.setMsg("����ϵͳδ��ȡ���༶��ʶ!");
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
        //��ѯδ�������������
        if(taskflag!=null&&taskflag.trim().length()>0)
            t.setFlag(1);
        List<TpTopicInfo>topList=this.tpTopicManager.getList(t,p);
        p.setList(topList);
        je.setPresult(p);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * �ύѧϰ�ĵ�
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
            je.setMsg("��ǰ��Դû�з���������Ϣ!");
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
            tp.setCriteria(2);//�ύ�ĵ�
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
            je.setMsg("��ǰ��Դû�з����ύѧϰ�ĵ�����!");
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
     * �����Դ�Ƿ�����
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
        * ��ѯ��ǰ��Դ�Ƿ���ѧϰ�ĵ�����
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
     * ����ѧϰ�ĵ�
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
        //��ѧ���ĵ�
        if(type!=null&&type.equals("1"))
            qa.setUserid(this.logined(request).getRef());
        List<QuestionAnswer>qaList=this.questionAnswerManager.getList(qa, null);
        je.getObjList().add(qaList!=null&&qaList.size()>0?qaList.get(0):null);

        /*
        * ��ѯ��ǰ��Դ�Ƿ�����
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
                 * ��ѯ��ǰѧ���Ƿ񱻷��������
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
     * �޸�ѧϰ�ĵ�
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
     * ѧ���Ὠ��
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
            je.setMsg("�����ύ�ɹ�!");
            je.setType("success");
        }else{
            je.setMsg("�����ύʧ��!");
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * ��ӻ�����������鿴��¼
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
        //����Ƿ��в鿴��׼
        TpTaskInfo t=new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskid));
        t.setCriteria(1);
        List<TpTaskInfo>taskCriList=this.tpTaskManager.getList(t, null);

        TpTaskInfo task=new TpTaskInfo();
        task.setTaskid(Long.parseLong(taskid));
        task.setCourseid(Long.parseLong(courseid));
        task.setTasktype(Integer.parseInt(tasktype));
        //task.setGroupid(groupid);

        //¼�����״̬
        TaskPerformanceInfo tp=new TaskPerformanceInfo();
        tp.setTaskid(task.getTaskid());
        tp.setTasktype(task.getTasktype());
        tp.setCourseid(task.getCourseid());
        tp.setCriteria(1);//�鿴
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
                    je.setMsg("����!��Ӳ鿴��¼ʧ��!������!");
                    response.getWriter().print(je.toJSON());
                }
            }
        }else{
            response.sendRedirect("tptopic?m=toDetailTopic&topicid="+themeid+"&taskid="+taskid+"&courseid="+courseid);
        }
    }


    /**
     * �����Դѧϰ�鿴��¼
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
        //����Ƿ��в鿴��׼
        TpTaskInfo t=new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskid));
        t.setCriteria(1);
        List<TpTaskInfo>taskCriList=this.tpTaskManager.getList(t, null);

        TpTaskInfo task=new TpTaskInfo();
        task.setTaskid(Long.parseLong(taskid));
        task.setCourseid(Long.parseLong(courseid));
        task.setTasktype(Integer.parseInt(tasktype));
        //task.setGroupid(groupid);

        //¼�����״̬
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
                    je.setMsg("����!��Ӳ鿴��¼ʧ��!������!");
                    response.getWriter().print(je.toJSON());
                }
            }
        }else{
            response.sendRedirect("tpres?toStudentIdx&courseid="+courseid+"&tpresdetailid="+tpresdetailid+"&taskid="+taskid+"&groupid="+groupid);
        }
    }


    /**
     * ��ѯ������
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
     * ����������ҳ��
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
     * ����������ͳ��
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
        //��ȡ�������
        TpTaskInfo ti = new TpTaskInfo();
        ti.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo> tiList = this.tpTaskManager.getList(ti,null);
        request.setAttribute("taskInfo",tiList.get(0));
        //��ȡ����
        if(tiList.get(0).getTasktype()==3||tiList.get(0).getTasktype()==4){
            QuestionOption qo = new QuestionOption();
            qo.setQuestionid(tiList.get(0).getTaskvalueid());
            PageResult pr = new PageResult();
            pr.setPageSize(0);
            pr.setPageNo(0);
            pr.setOrderBy("u.option_type");
            List<QuestionOption> optionList = this.questionOptionManager.getList(qo,pr);
            for(QuestionOption o : optionList){
                if(o.getIsright()==1){
                    request.setAttribute("rightid",o.getRef());
                }
            }
        }
        //��ȡ������صİ༶
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
     * ��ѯѧ������������
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

        //�����¼
        List<TaskPerformanceInfo>tList=this.taskPerformanceManager.getPerformListByTaskid(t, clsid,Integer.parseInt(type));
        //����ͳ��
        List<Map<String,Object>> optionnumList = new ArrayList<Map<String, Object>>();
        //����ͳ��
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
        pr.setPageSize(0);
        pr.setPageNo(0);
        pr.setOrderBy("u.option_type");
        List<QuestionOption> optionList = this.questionOptionManager.getList(qo,pr);
        int totalNum = 0;
        for(int i =0;i<optionnumList.size();i++){
            totalNum+=Integer.parseInt(optionnumList.get(i).get("NUM").toString());
        }
        List<Map<String,Object>> option = new ArrayList<Map<String, Object>>();
        //��̬ƴ����Ҫ��ѡ���ֲ�����
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
        //����������ͳ��ͼ
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
     * ��ѯѧ������������
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
        //�����¼
        List<TaskPerformanceInfo>tList=this.taskPerformanceManager.getPerformListByTaskid(t,clsid,Integer.parseInt(type));
        //����ͳ��
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
     * ����ѧ���ˣ��鿴��������������ҳ��
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
     * ��ʦ�ˣ��鿴ѧ����������������ҳ��
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
     * ѧ���ˣ��鿴��������������
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
     * �ָ�����վ�е�����
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

        //����������¼
        List<List<Object>> objListArray=new ArrayList<List<Object>>();
        List<String> sqlStrList=new ArrayList<String>();
        StringBuilder sql=null;
        List<Object>objList=null;




        TpTaskInfo tmpTask=taskList.get(0);

        TpTaskInfo sel=new TpTaskInfo();
        sel.setCourseid(tmpTask.getCourseid());
        //��ѯû����ɾ��������
        sel.setSelecttype(1);
        sel.setLoginuserid(this.logined(request).getUserid());
        sel.setStatus(1);

        //�ѷ���������
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
     * ��ȡС������
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
     * ���������Ծ���ҳ
     * @return
     */
    @RequestMapping(params="m=toMarking",method=RequestMethod.GET)
    public ModelAndView toMarkingPaper(HttpServletRequest request,HttpServletResponse response)throws Exception{
        String paperid = request.getParameter("paperid");
        String taskid = request.getParameter("taskid");
        String clsid = request.getParameter("classid");
        String classtype=request.getParameter("classtype");
        JsonEntity je = new JsonEntity();
        if(paperid==null||paperid.length()<1||taskid==null||taskid.trim().length()<1){
            je.setMsg("������ˢ��ҳ������");
            je.getAlertMsgAndBack();
        }

        //��ȡ������صİ༶
        TpTaskAllotInfo ta = new TpTaskAllotInfo();
        ta.setTaskid(Long.parseLong(taskid));
        List<TpTaskAllotInfo> taList = this.tpTaskAllotManager.getList(ta,null);

        List<Map> classList = new ArrayList<Map>();
        boolean isValidate=false;

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

                if(o.getEtime()!=null&&o.getEtime().getTime()<new Date().getTime()){
                    if(clsid==null||clsid.length()<1){
                        clsid = ciList.get(0).getClassid().toString();
                        classtype= "1";
                    }
                }else if(ciList.get(0).getClassid().toString().equals(clsid)){
                    isValidate=!isValidate;
                }



            }else if(o.getUsertype()==1){
                TpVirtualClassInfo vci = new TpVirtualClassInfo();
                vci.setVirtualclassid(Integer.parseInt(o.getUsertypeid().toString()));
                List<TpVirtualClassInfo> vciList = this.tpVirtualClassManager.getList(vci,null);
                Map map = new HashMap();
                map.put("classid",vciList.get(0).getVirtualclassid());
                map.put("classname",vciList.get(0).getVirtualclassname());
                map.put("classtype",2);
                classList.add(map);

                if(o.getEtime()!=null&&o.getEtime().getTime()<new Date().getTime()){
                    if(clsid==null||clsid.length()<1){
                        clsid = vciList.get(0).getVirtualclassid().toString();
                        classtype= "2";
                    }
                }else if(vciList.get(0).getVirtualclassid().toString().equals(clsid)){
                    isValidate=true;
                }
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
                    map.put("classtype",1);
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

                    if(o.getEtime()!=null&&o.getEtime().getTime()<new Date().getTime()){
                        if(clsid==null||clsid.length()<1){
                            clsid = objList.get(0).getClassid().toString();
                            classtype= "1";
                        }
                    }else if(objList.get(0).getClassid().toString().equals(clsid)){
                        isValidate=!isValidate;
                    }
                }else{
                    TpVirtualClassInfo vc = new TpVirtualClassInfo();
                    vc.setVirtualclassid(classid);
                    List<TpVirtualClassInfo> objList = this.tpVirtualClassManager.getList(vc,null);
                    Map map = new HashMap();
                    map.put("classid",objList.get(0).getVirtualclassid());
                    map.put("classname",objList.get(0).getVirtualclassname());
                    map.put("classtype",1);
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
                    if(o.getEtime()!=null&&o.getEtime().getTime()<new Date().getTime()){
                        if(clsid==null||clsid.length()<1){
                            clsid = objList.get(0).getVirtualclassid().toString();
                            classtype= "1";
                        }
                    }else if(objList.get(0).getVirtualclassid().toString().equals(clsid)){
                        isValidate=!isValidate;
                    }
                }
            }
        }
        //��֤�Ƿ��Ѿ�����
//        if(isValidate){
//            je.setMsg("�ð༶������δ�������޷���������");
//            response.getWriter().println(je.getAlertMsgAndBack());return null;
//        }



        //��ȡ�Ծ�������������ύ��������������
        List<PaperQuestion> objList = this.paperQuestionManager.getQuestionByPaper(Long.parseLong(paperid),Integer.parseInt(clsid),Integer.parseInt(classtype),Long.parseLong(taskid.trim()));
        //��ȡ�Ծ�����������
        List<Map<String,Object>> quesIdList = this.paperQuestionManager.getPaperQuesAllId(Long.parseLong(paperid));
        if(quesIdList!=null&&quesIdList.size()>0){
            String quesidsStr = quesIdList.get(0).get("ALLQUESID").toString();
            request.setAttribute("quesidStr",quesidsStr);
        }
        PaperInfo pi = new PaperInfo();
        pi.setPaperid(Long.parseLong(paperid));
        List<PaperInfo> piList = this.paperManager.getList(pi,null);
        StuPaperLogs stuPaperLogs = new StuPaperLogs();
        stuPaperLogs.setPaperid(Long.parseLong(paperid));
        stuPaperLogs.setTaskid(Long.parseLong(taskid.trim()));
        List<StuPaperLogs> stuPaperLogsList = this.stuPaperLogsManager.getList(stuPaperLogs,null);
        Boolean sign = true;
        if(stuPaperLogsList!=null&&stuPaperLogsList.size()>0){
            for(int i = 0;i<stuPaperLogsList.size();i++){
                if(stuPaperLogsList.get(i).getIsmarking()!=0){
                    sign=false;
                    break;
                }
            }
        }
        request.setAttribute("classid",clsid);
        request.setAttribute("classtype",classtype);
        request.setAttribute("classes",classList);
        request.setAttribute("questionList",objList);
        request.setAttribute("papertype",piList.get(0).getPapertype());
        Float paperScore=100F;
        if(piList.get(0).getPapertype()==3)
            paperScore=piList.get(0).getScore();
        request.setAttribute("paperScore",paperScore);

        request.setAttribute("papername",piList.get(0).getPapername());
        request.setAttribute("ismark",sign);
        return new ModelAndView("teachpaltform/paper/marking/marking-list");
    }

        /**
         * ������ͳ��
         * @return
         */
        @RequestMapping(params="m=getpercentscore",method=RequestMethod.POST)
        public void getPercentScore(HttpServletRequest request,HttpServletResponse response)throws Exception{
            JsonEntity je = new JsonEntity();
            String paperid = request.getParameter("paperid");
            String type = request.getParameter("type");
            String classid=request.getParameter("classid");
            if(paperid==null||paperid.trim().length()<1||type==null||type.trim().length()<1
                    ||classid==null||classid.trim().length()<1){
                je.setType("error");
                je.setMsg("��������!");
                response.getWriter().println(je.toJSON());return;
            }
            //��ȡ�Ծ���ֺܷ͸������ε�����
            PaperInfo pi = new PaperInfo();
            pi.setPaperid(Long.parseLong(paperid));
            List<PaperInfo> piList = this.paperManager.getList(pi,null);
            List<Map<String,Object>> objList = new ArrayList<Map<String, Object>>();
            Map map = new HashMap();
            if(piList!=null&&piList.size()==1){
               if(type.equals("1")){
                   Float totalScore = piList.get(0).getScore();
                   double percentNum = Math.ceil((totalScore-totalScore*0.6)/4);
                   List<Map<String,Object>> percentList = new ArrayList<Map<String, Object>>();
                   //����������ͳ��ͼ
                   DefaultPieDataset dataset = new DefaultPieDataset();
                   String htm = "";
                   for(int i = 0;i<5;i++){
                       double dbnum = totalScore-percentNum*i;
                       double dbnum2;
                       if(i<4){
                           dbnum2 = totalScore-percentNum*(i+1);
                       }else{
                           dbnum2=0.0;
                       }
                       int bignum = Integer.parseInt(new java.text.DecimalFormat("0").format(dbnum));
                       int smallnum = Integer.parseInt(new java.text.DecimalFormat("0").format(dbnum2));
                       List<Map<String,Object>> numList = this.stuPaperLogsManager.getPaperPercentNum(Long.parseLong(paperid),bignum,smallnum,Integer.parseInt(classid.trim()));
                       //m.put(smallnum + "~" + bignum, numList.get(0).get("NUM"));
                       htm+="<tr><td>"+smallnum + "~" + bignum+"</td><td>"+numList.get(0).get("NUM")+"</td></tr>";
                       int num = Integer.parseInt(numList.get(0).get("NUM").toString());
                       int totalnum = Integer.parseInt(numList.get(0).get("TOTALNUM").toString());
                       if(totalnum>0){
                           dataset.setValue(smallnum + "~" + bignum, (double) num / totalnum);
                       }
                   }
                   map.put("htm",htm);
                   objList.add(map);
                   je.setObjList(objList);
                   je.setType("success");
                   JFreeChart chart = ChartFactory.createPieChart("", dataset, true, false, false);
                   FileOutputStream fos = null;
                   String fname="/optionPie/pt"+new Date().getTime()+".png";
                   try{
                       fos = new FileOutputStream(UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+fname);
                       ChartUtilities.writeChartAsPNG(fos, chart, 193, 140);
                   }finally{
                       fos.close();
                   }
                   je.getObjList().add(UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+fname);
               }else{
                   int totalScore = 100;
                   int percentNum = 10;
                   List<Map<String,Object>> percentList = new ArrayList<Map<String, Object>>();
                   Map m = new HashMap();
                   //����������ͳ��ͼ
                   DefaultPieDataset dataset = new DefaultPieDataset();
                   String htm = "";
                   for(int i = 0;i<5;i++){
                       int dbnum = totalScore-percentNum*i;
                       int dbnum2;
                       if(i<4){
                           dbnum2 = totalScore-percentNum*(i+1);
                       }else{
                           dbnum2=0;
                       }
                       int bignum = dbnum;
                       int smallnum = dbnum2;
                       List<Map<String,Object>> numList = this.stuPaperLogsManager.getPaperPercentNum2(Long.parseLong(paperid),
                               bignum, smallnum, Integer.parseInt(classid.trim()));
                       m.put(smallnum + "~" + bignum, numList.get(0).get("NUM"));
                       int num = Integer.parseInt(numList.get(0).get("NUM").toString());
                       int totalnum = Integer.parseInt(numList.get(0).get("TOTALNUM").toString());
                       if(totalnum>0){
                           int little = (100-10*(i+1));
                           if(little<60)
                               little=0;
                           int big = 100-10*i;
                           dataset.setValue(little + "~" + big, (double) num / totalnum);
                       }
                   }
                   percentList.add(m);
                   je.setObjList(percentList);

                   je.setType("success");
                   JFreeChart chart = ChartFactory.createPieChart("", dataset, true, false, false);
                   FileOutputStream fos = null;
                   String fname="/optionPie/pt"+new Date().getTime()+".png";
                   try{
                       fos = new FileOutputStream(UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+fname);
                       ChartUtilities.writeChartAsPNG(fos, chart, 193, 140);
                   }finally{
                       if(fos!=null)
                         fos.close();
                   }

                   je.getObjList().add(UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+fname);
               }
            }else{
                je.setType("error");
                je.setMsg("��������δ��ȡ���Ծ���Ϣ");
            }
            response.getWriter().print(je.toJSON());
        }


        /**
         * ���������Ծ�ͳ��
         * @return
         */
    @RequestMapping(params="m=toMarkingLogs",method=RequestMethod.GET)
    public ModelAndView toMarkingLogs(HttpServletRequest request,HttpServletResponse response)throws Exception{
        String paperid = request.getParameter("paperid");
        String quesid = request.getParameter("quesid");
        String idx = request.getParameter("idx");
        String classid=request.getParameter("classid");
       String taskid=request.getParameter("taskid");
        JsonEntity je = new JsonEntity();
        if(paperid==null||paperid.length()<1||quesid==null||quesid.length()<1||classid==null||classid.trim().length()<1||taskid==null||taskid.trim().length()<1){
            je.setMsg("������ˢ��ҳ������");
            response.getWriter().println(je.getAlertMsgAndBack());
        }
        List<StuPaperLogs> logsList = this.stuPaperLogsManager.getMarkingLogs(Long.parseLong(paperid),Long.parseLong(quesid),Integer.parseInt(classid),Long.parseLong(taskid.trim()));
        request.setAttribute("logs",logsList);
        request.setAttribute("idx",idx);
        return new ModelAndView("teachpaltform/paper/marking/marking-logs");
    }

    /**
     * ���������Ծ�ҳ��
     * @return
     */
    @RequestMapping(params="m=toMarkingDetail",method=RequestMethod.GET)
    public ModelAndView toMarkingDetail(HttpServletRequest request,HttpServletResponse response)throws Exception{
        String paperid = request.getParameter("paperid");
        String qid = request.getParameter("questionid");
        String classid = request.getParameter("classid");
        String classtype = request.getParameter("classtype");
        String taskid=request.getParameter("taskid");
        JsonEntity je=new JsonEntity();
        if(taskid==null||paperid==null||classid==null){
            je.setMsg("������ˢ��ҳ������");
            response.getWriter().println(je.getAlertMsgAndBack());return null;
        }
        //��֤����
        TpTaskInfo tk=new TpTaskInfo();
        tk.setTaskid(Long.parseLong(taskid.trim()));
        List<TpTaskInfo> tkList=this.tpTaskManager.getList(tk,null);
        if(tkList==null||tkList.size()<1){
            je.setMsg("����û�и�������Ϣ!");
            response.getWriter().println(je.getAlertMsgAndBack());return null;
        }



        Long questionid = null;
        if(qid!=null){
            questionid=Long.parseLong(qid);
        }
        String quesid = request.getParameter("quesid");
        String idx = request.getParameter("idx");

        if(paperid==null||paperid.length()<1||quesid==null||quesid.length()<1||taskid==null||taskid.trim().length()<1){
            je.setMsg("������ˢ��ҳ������");
            response.getWriter().println(je.getAlertMsgAndBack());
            return null;
        }
        PaperInfo paper=new PaperInfo();
        paper.setPaperid(Long.parseLong(paperid));
        List<PaperInfo> paperList=this.paperManager.getList(paper,null);
        if(paperList==null||paperList.size()<1){
            je.setMsg("���Ծ��Ѿ���ɾ������ˢ�������б�ҳ!");
            response.getWriter().println(je.getAlertMsgAndBack());return null;
        }


        List<Map<String,Object>> detailList = this.stuPaperLogsManager.getMarkingDetail(Long.parseLong(paperid),questionid,Long.parseLong(quesid),1,Integer.parseInt(classid),Integer.parseInt(classtype),Long.parseLong(taskid.trim()));
//        if(detailList==null||detailList.size()<1){
//            je.setMsg("���Ծ�û��ѧ�����д��⣬�޷������Ծ�!");
//            response.getWriter().print(je.getAlertMsgAndBack());
//            return null;
//        }
        List<Map<String,Object>> numList = this.stuPaperLogsManager.getMarkingNum(Long.parseLong(paperid),Long.parseLong(quesid),Integer.parseInt(classid),Integer.parseInt(classtype));
        if(detailList==null||detailList.size()==0){
            detailList = this.stuPaperLogsManager.getMarkingDetail(Long.parseLong(paperid),questionid,Long.parseLong(quesid),0,Integer.parseInt(classid),Integer.parseInt(classtype),Long.parseLong(taskid.trim()));
            request.setAttribute("sign",true);
            if(detailList==null||detailList.size()<1){
                je.setMsg("���Ծ�û��ѧ�����д��⣬�޷������Ծ�!");
                response.getWriter().print(je.getAlertMsgAndBack());
                return null;
            }
        }else{
            request.setAttribute("sign",false);
        }
        int type = Integer.parseInt(detailList.get(0).get("QUESTION_TYPE").toString());
        int type2 = 0;
        if(detailList.get(0).get("QUESTION_TYPE2")!=null){
            type2 =  Integer.parseInt(detailList.get(0).get("QUESTION_TYPE2").toString());
        }
        int extension=0;
        if(detailList.get(0).get("EXTENSION2")!=null)
            extension = Integer.parseInt(detailList.get(0).get("EXTENSION2").toString());
        if(type2>0&&type2==6&&extension==5){
//            if(extension==5){
                QuestionOption qo = new QuestionOption();
                qo.setQuestionid(Long.parseLong(qid));
                List<QuestionOption> optionList = this.questionOptionManager.getList(qo,null);
                request.setAttribute("option",optionList);

                QuestionOption q = new QuestionOption();
                q.setQuestionid(Long.parseLong(quesid));
                q.setIsright(1);
                List<QuestionOption> rightoption = this.questionOptionManager.getList(q,null);
                request.setAttribute("rightoption",rightoption);
//            }
        }
        else{
            if(type==3||type==4||type==7||type==8){
                QuestionOption qo = new QuestionOption();
                qo.setQuestionid(Long.parseLong(quesid));
                List<QuestionOption> optionList = this.questionOptionManager.getList(qo,null);
                request.setAttribute("option",optionList);
            }
        }
        if(type==3||type==4||type==7||type==8){
            //�����ѡ���⣬�����ȷ�ʣ�ѡ��ȷ���
            List<Map<String,Object>> zqlMapList=this.paperQuestionManager.getClsPaperQuesZQLV(
                    Long.parseLong(paperid),
                    Long.parseLong(quesid),
                    Integer.parseInt(classid.trim()),
                    Long.parseLong(taskid.trim()));
            if(zqlMapList==null||zqlMapList.size()<1||zqlMapList.get(0)==null||!zqlMapList.get(0).containsKey("ZQL")){
                je.setMsg("������ˢ��ҳ������");
                response.getWriter().println(je.getAlertMsgAndBack());
                return null;
            }
            request.setAttribute("zqlMap",zqlMapList.get(0));
            List<Map<String,Object>> optTJMapList=this.paperQuestionManager.getClsPaperQuesOptTJ(
                    Long.parseLong(paperid),
                    Long.parseLong(quesid),
                    Integer.parseInt(classid.trim()),
                    Long.parseLong(taskid.trim()));
            if(optTJMapList==null||optTJMapList.size()<1||optTJMapList.get(0)==null){
                je.setMsg("������ˢ��ҳ������");
                response.getWriter().println(je.getAlertMsgAndBack());
                return null;
            }
            request.setAttribute("optTJMapList",optTJMapList);
        }


        //�õ���ǰ����������
        List<Map<String,Object>> listMapStr=this.paperQuestionManager.getPaperQuesAllId(Long.parseLong(paperid));
        if(listMapStr==null||listMapStr.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(je.getAlertMsgAndBack());return null;
        }
        Object allquesidObj=listMapStr.get(0).get("ALLQUESID");
        if(allquesidObj==null||allquesidObj.toString().trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(je.getAlertMsgAndBack());return null;
        }


        //���ط���
        List<Map<String,Object>> scoreMapList=this.paperQuestionManager.getPaperQuesAllScore(Long.parseLong(paperid.trim()),Long.parseLong(quesid),tkList.get(0).getCourseid());
        if(scoreMapList==null||scoreMapList.size()<1||!scoreMapList.get(0).containsKey("SCORE")||scoreMapList.get(0).get("SCORE")==null){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(je.getAlertMsgAndBack());return null;
        }
        request.setAttribute("quesid",quesid);
        request.setAttribute("allquesidObj",allquesidObj);
        request.setAttribute("score",scoreMapList.get(0).get("SCORE"));
        request.setAttribute("detail",detailList.get(0));
        request.setAttribute("num",numList.get(0));
        request.setAttribute("papertype",paperList.get(0).getPapertype());
        request.setAttribute("paperScore",paperList.get(0).getScore());
        return new ModelAndView("teachpaltform/paper/marking/marking-detail");
    }


    /**
     * ���������Ծ�
     * @return
     */
    @RequestMapping(params="m=doMarking",method=RequestMethod.POST)
    public void doMarking(HttpServletRequest request,HttpServletResponse response)throws Exception{
        String ref = request.getParameter("ref");
        String score = request.getParameter("score");
        String userid = request.getParameter("userid");
        JsonEntity je = new JsonEntity();
        if(ref==null||ref.length()<1||score==null||score.length()<1){
            je.setMsg("������ˢ��ҳ������");
            je.getAlertMsgAndBack();
        }
        //����������¼
        List<List<Object>> objListArray=new ArrayList<List<Object>>();
        List<String> sqlStrList=new ArrayList<String>();
        StringBuilder sql=null;
        List<Object>objList=null;
        //ѧ�������¼
        StuPaperQuesLogs sp = new StuPaperQuesLogs();
        sp.setRef(Integer.parseInt(ref));
        sp.setScore(Float.parseFloat(score));
        List<StuPaperQuesLogs> quesLogs = this.stuPaperQuesLogsManager.getList(sp,null);
        sql=new StringBuilder();
        sp.setIsmarking(0);
        objList = this.stuPaperQuesLogsManager.getUpdateSql(sp,sql);
        objListArray.add(objList);
        sqlStrList.add(sql.toString());
        //�Ծ�÷ָ���
        sql = new StringBuilder();
        StuPaperLogs sl = new StuPaperLogs();
        sl.setUserid(quesLogs.get(0).getUserid());
        sl.setPaperid(quesLogs.get(0).getPaperid());
        sl.setScore(Float.parseFloat(score));
        objList=this.stuPaperLogsManager.getUpdateScoreSql(sl,sql);
        objListArray.add(objList);
        sqlStrList.add(sql.toString());
        Boolean b =this.stuPaperLogsManager.doExcetueArrayProc(sqlStrList,objListArray);
        if(b){
            je.setType("success");
            //�ж��Ծ��Ƿ�������ϣ����ȵõ��Ծ����������

//            PaperQuestion paperQuestion = new PaperQuestion();
//            paperQuestion.setPaperid(quesLogs.get(0).getPaperid());
//            List<PaperQuestion> paperQuestionList = this.paperQuestionManager.getList(paperQuestion,null);
//            //Ȼ��õ����ĵ�����
            StuPaperQuesLogs stuPaperQuesLogs = new StuPaperQuesLogs();
            stuPaperQuesLogs.setPaperid(quesLogs.get(0).getPaperid());
            stuPaperQuesLogs.setIsmarking(1);
            stuPaperQuesLogs.setUserid(quesLogs.get(0).getUserid());
            stuPaperQuesLogs.setTaskid(quesLogs.get(0).getTaskid());
            List<StuPaperQuesLogs> stuPaperQuesLogsList = this.stuPaperQuesLogsManager.getList(stuPaperQuesLogs,null);
//            //�Ա������������ȣ���ô������ϣ������Ծ��״̬����
            if(stuPaperQuesLogsList==null||stuPaperQuesLogsList.size()<1){
                StuPaperLogs stuPaperLogs = new StuPaperLogs();
                stuPaperLogs.setPaperid(quesLogs.get(0).getPaperid());
                stuPaperLogs.setUserid(quesLogs.get(0).getUserid());
                stuPaperLogs.setTaskid(quesLogs.get(0).getTaskid());
                stuPaperLogs.setIsmarking(0);
                //�õ��ܷ�
                List<Map<String,Object>> paperScoreListMap=this.paperQuestionManager.getPaperScoreByUser(quesLogs.get(0).getPaperid(),quesLogs.get(0).getUserid(),quesLogs.get(0).getTaskid());
                float sumScore=0F;
                if(paperScoreListMap!=null&&paperScoreListMap.size()>0&&paperScoreListMap.get(0).containsKey("SUM_SCORE")&&paperScoreListMap.get(0).get("SUM_SCORE")!=null){
                    stuPaperLogs.setScore(Float.parseFloat(paperScoreListMap.get(0).get("SUM_SCORE").toString()));
                }
                Boolean bl = this.stuPaperLogsManager.doUpdateScore(stuPaperLogs);
            }
        }else{
            je.setType("error");
            je.setMsg("ϵͳ�쳣����ˢ��ҳ������");
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * ѧ���������(����)
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=genderZiZhuPaper",method = RequestMethod.GET)
    public ModelAndView doGenderZiZhuPaper(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        String taskid=request.getParameter("taskid");
        JsonEntity jsonEntity=new JsonEntity();
        if(taskid==null||taskid.toString().trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());
            return null;
        }
        //�õ�������Ϣ
        TpTaskInfo tk=new TpTaskInfo();
        tk.setTaskid(Long.parseLong(taskid.trim()));
        tk.setTasktype(5);
        List<TpTaskInfo> tkList=this.tpTaskManager.getList(tk,null);
        if(tkList==null||tkList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());
            return null;
        }
        TpTaskInfo tkEntity=tkList.get(0);
        //�õ���������ʱ���ɵ�TaskValueId
        Long tkvalueid=tkEntity.getTaskvalueid();

        PaperInfo pentity=new PaperInfo();
      //  pentity.setpar
        pentity.setParentpaperid(tkvalueid);
        pentity.setCuserid(this.logined(request).getUserid());
        List<PaperInfo> paperList=this.paperManager.getList(pentity,null);
        if(paperList==null||paperList.size()<1){

            TpTaskInfo t=new TpTaskInfo();
            t.setUserid(this.logined(request).getUserid());
            t.setTaskid(Long.parseLong(taskid));
            t.setCourseid(tkList.get(0).getCourseid());
             PageResult pr=new PageResult();
            pr.setPageSize(1);
            // ѧ������
            List<TpTaskInfo>taskList=this.tpTaskManager.getListbyStu(t, pr);
            if(taskList==null||taskList.size()<1||taskList.get(0).getBtime()==null||taskList.get(0).getEtime()==null){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
            }
            if(taskList.get(0).getTaskstatus().equals("3")){
                jsonEntity.setMsg("�����ѽ��������޷�������ԡ�");
                response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
            }
            //��������
            if(!this.paperManager.doGenderZiZhuPaper(tkEntity.getTaskid(),this.logined(request).getUserid())){
                jsonEntity.setMsg("�����Ծ�ʧ��!ԭ��δ֪");
                response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
            }else{
                //�ٲ�һ��
                paperList=this.paperManager.getList(pentity,null);
                List<Map<String,Object>> listMapStr=this.paperQuestionManager.getPaperQuesAllId(paperList.get(0).getPaperid());
                if(listMapStr==null||listMapStr.size()<1){
                    jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                    response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
                }
                Object allquesidObj=listMapStr.get(0).get("ALLQUESID");
                if(allquesidObj==null||allquesidObj.toString().trim().length()<1){
                    jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                    response.getWriter().println(jsonEntity.getAlertMsgAndBack());return null;
                }
                jsonEntity.setMsg("�����Ծ�ɹ�!������" + (allquesidObj.toString().trim().split(",").length) + "����!");
            }
        }
//        else
//            jsonEntity.setMsg("�Ծ��Ѵ���!��" + tkEntity.getQuesnum() + "����!");
        Long paperid=paperList.get(0).getPaperid();
        String baseUrl=request.getSession().getAttribute("IP_PROC_NAME")==null?"":request.getSession().getAttribute("IP_PROC_NAME").toString();
        response.sendRedirect(baseUrl+"paperques?m=testPaper&paperid=" + paperid + "&courseid=" + tkEntity.getCourseid() + "&taskid="+taskid);
        return null;
    }


    /**
     * �õ���һ�����ĵ��Ծ�
     * @throws Exception
     */
    @RequestMapping(params="m=nextPiYue",method=RequestMethod.POST)
    public void getPiyueNext(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        String taskid=request.getParameter("taskid");
        String paperid=request.getParameter("paperid");
        String ismarking=request.getParameter("ismarking");
        if(taskid==null||paperid==null||ismarking==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.toJSON());
            return;
        }
        StuPaperQuesLogs paperQuesLogs=new StuPaperQuesLogs();
        paperQuesLogs.setTaskid(Long.parseLong(taskid.trim()));
        paperQuesLogs.setPaperid(Long.parseLong(paperid.trim()));
        paperQuesLogs.setIsmarking(Integer.parseInt(ismarking.trim()));
        List<StuPaperQuesLogs> spqLogs=this.stuPaperQuesLogsManager.getList(paperQuesLogs,null);
        if(spqLogs!=null&&spqLogs.size()>0){
            jsonEntity.getObjList().add(spqLogs.get(0));
        }
        jsonEntity.setType("success");
        response.getWriter().println(jsonEntity.toJSON());

    }

    /**
     * �õ�������ͼƬ
     * @param paperid
     * @param questid
     * @param classid
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value="/img/{paperid}/{quesid}/{classid}/{taskid}/write",method = {RequestMethod.GET,RequestMethod.GET})
    public void getOptTJImg(@PathVariable("paperid") Long paperid,
                            @PathVariable("quesid") Long questid,
                            @PathVariable("classid") Long classid,
                            @PathVariable("taskid") Long taskid,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        if(paperid==null||questid==null||classid==null)return;
        //�õ����ͳ��ֵ

        List<Map<String,Object>> optTjLvList=this.paperQuestionManager.getClsPaperQuesOptTJ(paperid,questid,classid.intValue(),taskid);
        if(optTjLvList==null||optTjLvList.size()<1)return;



        //��̬ƴ����Ҫ��ѡ���ֲ�����
        DecimalFormat di = new DecimalFormat("#.00");
        //����������ͳ��ͼ
        DefaultPieDataset dataset = new DefaultPieDataset();
        for(Map<String,Object> tmpMap:optTjLvList){
            if(tmpMap.containsKey("OPTION_TYPE")&&tmpMap.containsKey("BILI"))
            dataset.setValue(tmpMap.get("OPTION_TYPE").toString(),Float.parseFloat(tmpMap.get("BILI").toString()));
        }
        JFreeChart chart = ChartFactory.createPieChart("", dataset, true, false, false);
        FileOutputStream fos = null;
        String imgRealPath=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/optionPie/tk"+paperid+questid+classid+".png";
        File f=new File(imgRealPath);
        if(!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try{
            fos = new FileOutputStream(imgRealPath);
            ChartUtilities.writeChartAsPNG(fos, chart, 193, 140);
        }finally{
            if(fos!=null)
                 fos.close();
        }
        //����
        System.gc();
        //���ͼƬ��
        UtilTool.writeImage(response,imgRealPath);
    }
}
