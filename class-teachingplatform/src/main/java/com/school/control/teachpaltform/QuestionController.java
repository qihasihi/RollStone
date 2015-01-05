package com.school.control.teachpaltform;

import com.school.control.base.BaseController;
import com.school.entity.DictionaryInfo;
import com.school.entity.teachpaltform.*;
import com.school.entity.teachpaltform.paper.PaperInfo;
import com.school.entity.teachpaltform.paper.PaperQuestion;
import com.school.manager.inter.IDictionaryManager;
import com.school.manager.inter.teachpaltform.*;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicManager;
import com.school.manager.inter.teachpaltform.paper.IPaperManager;
import com.school.manager.inter.teachpaltform.paper.IPaperQuestionManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * qihaishi
 */
@Controller
@RequestMapping(value = "/question")
public class QuestionController extends BaseController<QuestionInfo> {
    @Autowired
    private IQuestionManager questionManager;
    @Autowired
    private ITpCourseManager tpCourseManager;
    @Autowired
    private IDictionaryManager dictionaryManager;
    @Autowired
    private IQuestionOptionManager questionOptionManager;
    @Autowired
    private ITpCourseQuestionManager tpCourseQuestionManager;
    @Autowired
    private ITpOperateManager tpOperateManager;
    @Autowired
    private ITpTopicManager tpTopicManager;
    @Autowired
    private ITpCourseTeachingMaterialManager tpCourseTeachingMaterialManager;
    @Autowired
    private IPaperQuestionManager paperQuestionManager;
    @Autowired
    private IPaperManager paperManager;


    /**
     * ���ݿ���ID�����������б�
     *
     * @return
     */
    @RequestMapping(params = "m=toQuestionList", method = RequestMethod.GET)
    public ModelAndView toQuestionList(HttpServletRequest request, HttpServletResponse response,ModelMap mp) throws Exception {
        //�õ��ÿ�������⡣
        JsonEntity je = new JsonEntity();
        String courseid = request.getParameter("courseid");
        if (courseid == null || courseid.trim().length() < 1) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //��֤courseid
        TpCourseInfo tpCourseInfo = new TpCourseInfo();
        tpCourseInfo.setCourseid(Long.parseLong(courseid));
        PageResult presult = new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(1);
        List<TpCourseInfo> courseList = this.tpCourseManager.getTchCourseList(tpCourseInfo, presult);
        if (courseList == null || courseList.size() < 1) {
            je.setMsg(UtilTool.msgproperty.get("ENTITY_NOT_EXISTS").toString());
            response.getWriter().print(je.toJSON());
            return null;
        }
        Integer subjectid=null;
        //��ȡ��ǰר��̲�
        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(Long.parseLong(courseid));
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);
        if(materialList!=null&&materialList.size()>0)
            subjectid=materialList.get(0).getSubjectid();
        //������ʽ
        request.setAttribute("coursename", courseList.get(0).getCoursename());
        TpCourseInfo tcs= new TpCourseInfo();
        tcs.setUserid(this.logined(request).getUserid());
        tcs.setTermid(courseList.get(0).getTermid());
        tcs.setLocalstatus(1);
        if(subjectid!=null)
            tcs.setSubjectid(subjectid);
        Object gradeid=request.getSession().getAttribute("session_grade");
        if(gradeid!=null&&gradeid.toString().length()>0&&!gradeid.equals("0"))
            tcs.setGradeid(Integer.parseInt(gradeid.toString()));
        List<TpCourseInfo>courseStyleList=this.tpCourseManager.getCourseList(tcs, null);
        request.setAttribute("courseList", courseStyleList);
 
        List<DictionaryInfo> quesTypeList = this.dictionaryManager.getDictionaryByType("TP_QUESTION_TYPE");
        mp.put("quesTypeList",quesTypeList);
        mp.put("courseid",courseList.get(0).getCourseid());
        return new ModelAndView("/teachpaltform/question/ques-admin",mp);
    }

    /**
     * ��������ID��������������
     *
     * @return
     */
    @RequestMapping(params = "m=todetail", method = RequestMethod.GET)
    public ModelAndView toQuestionDetail(HttpServletRequest request, HttpServletResponse response,ModelMap mp) throws Exception {
        String questionid = request.getParameter("id");
        String courseid=request.getParameter("courseid");
        JsonEntity je = new JsonEntity();
        if (questionid == null || questionid.trim().length() < 1||
                courseid==null||courseid.trim().length()<1) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        TpCourseQuestion  question = new TpCourseQuestion();
        question.setQuestionid(Long.parseLong(questionid));
        question.setCourseid(Long.parseLong(courseid));
        List<TpCourseQuestion> objList = this.tpCourseQuestionManager.getList(question,null);

        List<TpCourseQuestion> tmpList=new ArrayList<TpCourseQuestion>();
        if(objList!=null){
            List<TpCourseQuestion> childList=this.tpCourseQuestionManager.getQuestionTeamList(question,null);
            PageResult pchild = new PageResult();
            pchild.setPageNo(0);
            pchild.setPageSize(0);
            pchild.setOrderBy("option_type");
            List<QuestionOption>questionOptionList=this.questionOptionManager.getCourseQuesOptionList(question, pchild);

            List<QuestionOption>tmpOptionList;
            List<TpCourseQuestion>questionTeam;
            if(objList!=null&&objList.size()>0){
                for(TpCourseQuestion tq :objList){
                    questionTeam=new ArrayList<TpCourseQuestion>();
                    //������
                    if(childList!=null&&childList.size()>0){
                        for (TpCourseQuestion childp :childList){
                            //������ѡ��
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
                        //��ͨ����ѡ��
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
        }else{
            je.setMsg("ϵͳ�쳣�����Ժ�����");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        mp.put("pqList",tmpList);
        return new ModelAndView("/teachpaltform/question/ques-detail",mp);
    }

    /**
     * �޸�����ҳ
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toUpdQuestion", method = RequestMethod.GET)
    public ModelAndView toUpdQuestion(HttpServletRequest request, HttpServletResponse response,ModelMap mp) throws Exception {
        JsonEntity je = new JsonEntity();
        String questionid = request.getParameter("questionid");
        String paperid=request.getParameter("paperid");
        String courseid=request.getParameter("courseid");
        if (questionid == null || questionid.trim().length() < 1
                ||courseid==null||courseid.trim().length()<1) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //��֤����
        QuestionInfo questionInfo = new QuestionInfo();
        questionInfo.setQuestionid(Long.parseLong(questionid));
        PageResult presult = new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(1);
        List<QuestionInfo> questionList = this.questionManager.getList(questionInfo, presult);
        if (questionList == null || questionList.size() < 1) {
            je.setMsg(UtilTool.msgproperty.get("ENTITY_NOT_EXISTS").toString());
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        QuestionInfo ques=questionList.get(0);
        //��ѡ��ѡ����ѡ��
        if(ques.getQuestiontype()==3||ques.getQuestiontype()==4){
            QuestionOption questionOption=new QuestionOption();
            questionOption.setQuestionid(ques.getQuestionid());
            PageResult p = new PageResult();
            p.setPageSize(0);
            p.setPageNo(0);
            p.setOrderBy("option_type");
            List<QuestionOption>questionOptionList=this.questionOptionManager.getList(questionOption,p);
            mp.put("quesOptionList",questionOptionList);
        }

        List<DictionaryInfo> quesTypeList = this.dictionaryManager.getDictionaryByType("TP_QUESTION_TYPE");
        mp.put("quesTypeList",quesTypeList);
        mp.put("question",ques);
        mp.put("courseid",courseid);
        mp.put("paperid",paperid);

        return new ModelAndView("/teachpaltform/question/ques-update",mp);
    }


    /**
     * �޸�����ҳ--ģʽ����
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toUpdDialogQuestion", method = RequestMethod.GET)
    public ModelAndView toUpdDialogQuestion(HttpServletRequest request, HttpServletResponse response,ModelMap mp) throws Exception {
        JsonEntity je = new JsonEntity();
        String questionid = request.getParameter("questionid");
        String paperid=request.getParameter("paperid");
        String courseid=request.getParameter("courseid");
        if (questionid == null || questionid.trim().length() < 1
                ||courseid==null||courseid.trim().length()<1) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //��֤����
        QuestionInfo questionInfo = new QuestionInfo();
        questionInfo.setQuestionid(Long.parseLong(questionid));
        PageResult presult = new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(1);
        List<QuestionInfo> questionList = this.questionManager.getList(questionInfo, presult);
        if (questionList == null || questionList.size() < 1) {
            je.setMsg(UtilTool.msgproperty.get("ENTITY_NOT_EXISTS").toString());
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        QuestionInfo ques=questionList.get(0);
        //��ѡ��ѡ����ѡ��
        if(ques.getQuestiontype()==3||ques.getQuestiontype()==4){
            QuestionOption questionOption=new QuestionOption();
            questionOption.setQuestionid(ques.getQuestionid());
            PageResult p = new PageResult();
            p.setPageSize(0);
            p.setPageNo(0);
            p.setOrderBy("option_type");
            List<QuestionOption>questionOptionList=this.questionOptionManager.getList(questionOption,p);
            mp.put("quesOptionList",questionOptionList);
        }

        List<DictionaryInfo> quesTypeList = this.dictionaryManager.getDictionaryByType("TP_QUESTION_TYPE");
        mp.put("quesTypeList",quesTypeList);
        mp.put("question",ques);
        mp.put("courseid",courseid);
        mp.put("paperid",paperid);

        return new ModelAndView("/teachpaltform/task/teacher/dialog/childPage/addpaper/update-ques",mp);
    }


    /**
     * ��ȡ�����б�
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=questionAjaxList", method = RequestMethod.POST)
    public void ajaxQuestionList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity jsonEntity=new JsonEntity();
        PageResult presult=this.getPageResultParameter(request);
        String courseid=request.getParameter("courseid");
        String questype=request.getParameter("questype");
        String questionid=request.getParameter("questionid");
        String flag=request.getParameter("taskflag");
        if(courseid==null||courseid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());
            return;
        }
//        if(questype==null||questype.trim().length()<1){
//            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
//            response.getWriter().print(jsonEntity.toJSON());
//            return;
//        }
        //�õ��б�
        List<TpCourseQuestion> tmpList=new ArrayList<TpCourseQuestion>();
        TpCourseQuestion tpCourseQuestion=new TpCourseQuestion();
        if(questionid!=null&&questionid.trim().length()>0)
            tpCourseQuestion.setQuestionid(Long.parseLong(questionid));
        tpCourseQuestion.setCourseid(Long.parseLong(courseid));
        //1����Ч 2����ɾ��
        tpCourseQuestion.setStatus(1);
        if(flag!=null&&flag.trim().length()>0)
            tpCourseQuestion.setFlag(1);//ȥ���ѷ������ҽ����˵���������
        tpCourseQuestion.setQuestiontype(questype.equals("0")?null:Integer.parseInt(questype));
        tpCourseQuestion.setUserid(this.logined(request).getUserid());
        presult.setOrderBy(" u.c_time DESC,q.c_time DESC ");
        List<TpCourseQuestion> tpCourseQuestionList=this.tpCourseQuestionManager.getList(tpCourseQuestion,presult);
        //�������Ӽ�
        List<TpCourseQuestion> childList=this.tpCourseQuestionManager.getQuestionTeamList(tpCourseQuestion,null);
        PageResult pchild = new PageResult();
        pchild.setPageNo(0);
        pchild.setPageSize(0);
        pchild.setOrderBy("option_type");
        //ѡ��
        List<QuestionOption>questionOptionList=this.questionOptionManager.getCourseQuesOptionList(tpCourseQuestion, pchild);


        List<QuestionOption>tmpOptionList;
        List<TpCourseQuestion>questionTeam;
        if(tpCourseQuestionList!=null&&tpCourseQuestionList.size()>0){
            for(TpCourseQuestion tq :tpCourseQuestionList){
                questionTeam=new ArrayList<TpCourseQuestion>();
                //������
                if(childList!=null&&childList.size()>0){
                    for (TpCourseQuestion childp :childList){
                        //������ѡ��
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
                    //��ͨ����ѡ��
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
     * ִ��ɾ��
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=del", method = RequestMethod.POST)
    public void doDelQuestion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        String ref=request.getParameter("ref");
        if(ref==null||ref.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }


        //��ѯ�����Ƿ����
        //ֻ�õ�һ������
        //�õ����� (ֻȡһ������)

        TpCourseQuestion question=new TpCourseQuestion();
        question.setRef(Long.parseLong(ref));
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        presult.setPageNo(1);
        List<TpCourseQuestion> questionsList=this.tpCourseQuestionManager.getList(question,presult);
        if(questionsList==null||questionsList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());return;
        }
        //��ʼɾ��
        //����������¼
        List<List<Object>> objListArray=new ArrayList<List<Object>>();
        List<String> sqlStrList=new ArrayList<String>();
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=null;
        //����Ǳ�׼������
        TpCourseQuestion tmpques=questionsList.get(0);

        //�ƶ����ݣ���ֻ��д��¼
        if(tmpques.getQuoteid()!=null&&tmpques.getQuoteid().intValue()!=0){
            TpCourseInfo quoteInfo=new TpCourseInfo();
            quoteInfo.setCourseid(tmpques.getQuoteid());
            List<TpCourseInfo>quoteList=this.tpCourseManager.getList(quoteInfo,null);
            if(quoteList!=null&&quoteList.size()>0&&quoteList.get(0).getCourselevel()!=3){
                    Long operateNextId=this.tpOperateManager.getNextId(true);
                    //����tp_operate_info���м�һ������ ���й���      //�˲������ڻ���վ����ʾ
                    TpOperateInfo operateInfo=new TpOperateInfo();
                    operateInfo.setCourseid(tmpques.getQuoteid());
                    operateInfo.setTargetid(tmpques.getQuestionid());
                    operateInfo.setOperatetype(1);   //1:ɾ��
                    operateInfo.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_QUESTION.getValue()); //����
                    operateInfo.setCuserid(this.logined(request).getUserid());
                    operateInfo.setRef(operateNextId);
                    sqlbuilder=new StringBuilder();
                    objList=this.tpOperateManager.getSaveSql(operateInfo, sqlbuilder);
                    if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                        objListArray.add(objList);
                        sqlStrList.add(sqlbuilder.toString());
                    }
                    //������־
                    sqlbuilder=new StringBuilder();
                    objList=this.tpTopicManager.getAddOperateLog(this.logined(request).getRef(),"TP_OPERATE_INFO"
                            ,tmpques.getQuoteid().toString(),null,null,"DEL","��������IDɾ���������ǹ�����߱�׼���⣬���¼������Ԫ�ر���,��ʵ��ɾ��!",sqlbuilder);
                    //�˲������ڻ���վ����ʾ
                    if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                        objListArray.add(objList);
                        sqlStrList.add(sqlbuilder.toString());
                    }
            }
        }
        TpCourseQuestion quesUpdate=new TpCourseQuestion();
        quesUpdate.setLocalstatus(2);//�߼�ɾ��        �˲������ڻ���վ����ʾ
        quesUpdate.setRef(Long.parseLong(ref));
        sqlbuilder=new StringBuilder();
        objList=this.tpCourseQuestionManager.getUpdateSql(quesUpdate,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            objListArray.add(objList);
            sqlStrList.add(sqlbuilder.toString());
        }
        sqlbuilder=new StringBuilder();
        objList=this.questionManager.getAddOperateLog(this.logined(request).getRef(),"tp_j_course_question"
                ,tmpques.getQuestionid().toString(),null,null,"DELETE","��������ID�߼�ɾ��",sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            objListArray.add(objList);
            sqlStrList.add(sqlbuilder.toString());
        }
        if(sqlStrList.size()>0&&sqlStrList.size()==objListArray.size()){
            if(this.questionManager.doExcetueArrayProc(sqlStrList,objListArray)){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
            }else
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        response.getWriter().print(je.toJSON());
    }


    /**
     * �������ҳ
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toAddQuestion", method = RequestMethod.GET)
    public ModelAndView toAddQuestion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //�õ��ÿ�������⡣
        JsonEntity je = new JsonEntity();
        String courseid = request.getParameter("courseid");
        if (courseid == null || courseid.trim().length() < 1) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //��֤courseid
        TpCourseInfo tpCourseInfo = new TpCourseInfo();
        tpCourseInfo.setCourseid(Long.parseLong(courseid));
        PageResult presult = new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(1);
        List<TpCourseInfo> courseList = this.tpCourseManager.getTchCourseList(tpCourseInfo, presult);
        if(courseList==null||courseList.size()<1){
            je.setMsg(UtilTool.msgproperty.get("ENTITY_NOT_EXISTS").toString());
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //��ȡ��������
        List<DictionaryInfo> questiontypeList = this.dictionaryManager.getDictionaryByType("TP_QUESTION_TYPE");
        request.setAttribute("quesTypeList", questiontypeList);
        request.setAttribute("questionid",this.questionManager.getNextId(true));
        return new ModelAndView("/teachpaltform/question/ques-add");
    }

    /**
     * �������ҳ��ģʽ���壩
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toDialogAddQuestion", method = RequestMethod.GET)
    public ModelAndView toDialogAddQuestion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //�õ��ÿ�������⡣
        JsonEntity je = new JsonEntity();
        String courseid = request.getParameter("courseid");
        if (courseid == null || courseid.trim().length() < 1) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //��֤courseid
        TpCourseInfo tpCourseInfo = new TpCourseInfo();
        tpCourseInfo.setCourseid(Long.parseLong(courseid));
        PageResult presult = new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(1);
        List<TpCourseInfo> courseList = this.tpCourseManager.getTchCourseList(tpCourseInfo, presult);
        if(courseList==null||courseList.size()<1){
            je.setMsg(UtilTool.msgproperty.get("ENTITY_NOT_EXISTS").toString());
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //��ȡ��������
        List<DictionaryInfo> questiontypeList = this.dictionaryManager.getDictionaryByType("TP_QUESTION_TYPE");
        request.setAttribute("quesTypeList", questiontypeList);
        request.setAttribute("questionid",this.questionManager.getNextId(true));
        return new ModelAndView("/teachpaltform/task/teacher/dialog/add-ques");
    }



    /**
     * ��ӳɾ��������  �½�����ҳ��ģʽ���壩
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toDialogAddPaperQues", method = RequestMethod.GET)
    public ModelAndView toDialogAddPaperQuestion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //�õ��ÿ�������⡣
        JsonEntity je = new JsonEntity();
        String courseid = request.getParameter("courseid");
        if (courseid == null || courseid.trim().length() < 1) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //��֤courseid
        TpCourseInfo tpCourseInfo = new TpCourseInfo();
        tpCourseInfo.setCourseid(Long.parseLong(courseid));
        PageResult presult = new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(1);
        List<TpCourseInfo> courseList = this.tpCourseManager.getTchCourseList(tpCourseInfo, presult);
        if(courseList==null||courseList.size()<1){
            je.setMsg(UtilTool.msgproperty.get("ENTITY_NOT_EXISTS").toString());
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //��ȡ��������
        List<DictionaryInfo> questiontypeList = this.dictionaryManager.getDictionaryByType("TP_QUESTION_TYPE");
        request.setAttribute("quesTypeList", questiontypeList);
        request.setAttribute("questionid",this.questionManager.getNextId(true));
        return new ModelAndView("/teachpaltform/task/teacher/dialog/childPage/addpaper/add-ques");
    }


    /**
     * �������ҳ(����ר���ѯ�������б�)
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toAddQuestionByCourse", method = RequestMethod.GET)
    public ModelAndView toAddQuestionByCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //�õ��ÿ�������⡣
        JsonEntity je = new JsonEntity();
        String addCourseId = request.getParameter("addCourseId");
        String selectCourseid = request.getParameter("selectCourseid");
        if (selectCourseid == null || selectCourseid.trim().length() < 1) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        TpCourseInfo tpCourseInfo = new TpCourseInfo();
        tpCourseInfo.setCourseid(Long.parseLong(selectCourseid));
        List<TpCourseInfo> courseList = this.tpCourseManager.getCourseQuestionList(tpCourseInfo, null);
        if(courseList==null||courseList.size()<1){
            je.setMsg(UtilTool.msgproperty.get("ENTITY_NOT_EXISTS").toString());
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        request.setAttribute("courseObj",courseList.get(0));
        request.setAttribute("addCourseId",addCourseId);
        request.setAttribute("selectCourseid",selectCourseid);
        return new ModelAndView("/teachpaltform/question/ques-add-bycourse");
    }

    /**
     * ����ר��õ������б�
     *
     * @throws Exception
     */
    @RequestMapping(params = "m=getQuestionByCourse", method = RequestMethod.POST)
    public void getQuestionListByCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        PageResult presult = this.getPageResultParameter(request);
        String addCourseId = request.getParameter("addCourseid");
        String selectCourseid = request.getParameter("selectCourseid");
        String questype=request.getParameter("questype");
        TpCourseQuestion tpCourseQuestion = new TpCourseQuestion();
        tpCourseQuestion.setCourseid(Long.parseLong(selectCourseid));
        tpCourseQuestion.setStatus(1);
        if(questype!=null)
            tpCourseQuestion.setQuestiontype(Integer.parseInt(questype));
        List<TpCourseQuestion> questionList = this.tpCourseQuestionManager.getList(tpCourseQuestion,presult);
        List<TpCourseQuestion> childList=this.tpCourseQuestionManager.getQuestionTeamList(tpCourseQuestion,null);
        PageResult pchild = new PageResult();
        pchild.setPageNo(0);
        pchild.setPageSize(0);
        pchild.setOrderBy("option_type");
        List<QuestionOption>questionOptionList=this.questionOptionManager.getCourseQuesOptionList(tpCourseQuestion, pchild);
        //��ѡ������
        tpCourseQuestion = new TpCourseQuestion();
        tpCourseQuestion.setCourseid(Long.parseLong(addCourseId));
        tpCourseQuestion.setStatus(1);
        List<TpCourseQuestion> selectedQuestionList = this.tpCourseQuestionManager.getList(tpCourseQuestion,null);

        List<TpCourseQuestion> tmpList=new ArrayList<TpCourseQuestion>();
        List<QuestionOption>tmpOptionList;
        List<TpCourseQuestion>questionTeam;
        if(questionList!=null&&questionList.size()>0){
            for(TpCourseQuestion tq :questionList){
                questionTeam=new ArrayList<TpCourseQuestion>();
                //������
                if(childList!=null&&childList.size()>0){
                    for (TpCourseQuestion childp :childList){
                        //������ѡ��
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
                    //��ͨ����ѡ��
                    List<QuestionOption> tmp1OptionList=new ArrayList<QuestionOption>();
                    for(QuestionOption qo:questionOptionList){
                        if(qo.getQuestionid().equals(tq.getQuestionid())){
                            tmp1OptionList.add(qo);
                        }
                    }

                    tq.setQuestionOptionList(tmp1OptionList);
                }

                //�����ѡ����
                for(TpCourseQuestion o:selectedQuestionList){
                    if(tq.getQuestionid().toString().equals(o.getQuestionid().toString())){
                        tq.setState(1);
                        break;
                    }
                }
                tmpList.add(tq);
            }
        }

    /*    for(TpCourseQuestion tcq:questionList){
            QuestionOption obj = new QuestionOption();
            obj.setQuestionid(tcq.getQuestionid());
            PageResult p=new PageResult();
            p.setOrderBy("u.option_type");
            List<QuestionOption> optionList = this.questionOptionManager.getList(obj,p);
            if(optionList!=null){
                tcq.setQuestionOptionList(optionList);
            }
            //�����ѡ����
            for(TpCourseQuestion o:selectedQuestionList){
                if(tcq.getQuestionid().toString().equals(o.getQuestionid().toString())){
                    tcq.setState(1);
                    break;
                }
            }
        }
    */
        presult.setList(questionList);
        je.setPresult(presult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * ����ר��õ�����������⣬�������
     *
     * @throws Exception
     */
    @RequestMapping(params = "m=addQuestionByCourse", method = RequestMethod.POST)
    public void addQuestionListByCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String addCourseId = request.getParameter("addCourseId");
        String questionids = request.getParameter("questionids");
        //���ֵ�����ݼ�����
        List<String> sqlArrayList = new ArrayList<String>();
        //���sql�ļ���
        List<List<Object>> objArrayList = new ArrayList<List<Object>>();
        //ƴsql�Ķ���ʹ��ֵ�ö���
        List<Object> objList;
        StringBuilder sql ;
        if (addCourseId == null || addCourseId.trim().length() < 1) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsg());
            return;
        }
        if (questionids == null || questionids.trim().length() < 1) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsg());
            return;
        }
        String[] questionArray = questionids.split("\\|");
        for(int i = 0;i<questionArray.length;i++){
            TpCourseQuestion tpCourseQuestion = new TpCourseQuestion();
            tpCourseQuestion.setCourseid(Long.parseLong(addCourseId));
            tpCourseQuestion.setQuestionid(Long.parseLong(questionArray[i].toString()));
            sql = new StringBuilder();
            objList = this.tpCourseQuestionManager.getSaveSql(tpCourseQuestion,sql);
            sqlArrayList.add(sql.toString());
            objArrayList.add(objList);
        }
        Boolean b = this.tpCourseQuestionManager.doExcetueArrayProc(sqlArrayList,objArrayList);
        if(b){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
        }else{
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * �������
     *
     * @throws Exception
     */
    @RequestMapping(params = "m=doSubAddQuestion", method = RequestMethod.POST)
    public void doSubAddQuestion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String courseid = request.getParameter("courseid");
        String questype = request.getParameter("questype");
        String questionid= request.getParameter("questionid");
        if (StringUtils.isBlank(courseid)) {
            je.setMsg("�쳣����,δ��ȡ��ר���ʶ!");
            response.getWriter().print(je.toJSON());
            return;
        }
        StringBuilder sql = null;
        List<Object> objList = null;
        List<String> sqlListArray = new ArrayList<String>();
        List<List<Object>> objListArray = new ArrayList<List<Object>>();

        /**
         * ��ѯ��ר���Ƿ������˹���ר�⣬����Ԫ�ز�����¼
         */
        TpCourseInfo tc=new TpCourseInfo();
        tc.setCourseid(Long.parseLong(courseid));
        List<TpCourseInfo>tcList=this.tpCourseManager.getList(tc, null);
        if(tcList==null||tcList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }





        String quesname = request.getParameter("quesname");
        String correctanswer = request.getParameter("correctanswer");
        String quesanswer = request.getParameter("quesanswer");
        //String[] optionArray = request.getParameterValues("optionArray");
        //String[] isrightArray = request.getParameterValues("is_Right");
        String optionData=request.getParameter("optionArray");
        String isrightData=request.getParameter("rightArray");
        if (questionid==null||questionid.length()<1) {
            je.setMsg("�쳣����,δ��ȡ�������ʶ!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (StringUtils.isBlank(questype)) {
            je.setMsg("�쳣����,δ��ȡ����������!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (StringUtils.isBlank(quesname)) {
            je.setMsg("�쳣����,δ��ȡ�����!");
            response.getWriter().print(je.toJSON());
            return;
        }
        //����ID
        Long nextref = Long.parseLong(questionid);

        //������ר��
        TpCourseQuestion tpCourseQuestion=new TpCourseQuestion();
        tpCourseQuestion.setQuestionid(nextref);
        tpCourseQuestion.setCourseid(Long.parseLong(courseid));
        tpCourseQuestion.setRef(this.tpCourseQuestionManager.getNextId(true));
        sql = new StringBuilder();
        objList = this.tpCourseQuestionManager.getSaveSql(tpCourseQuestion, sql);
        if (objList != null && sql != null && sql.length() > 0) {
            objListArray.add(objList);
            sqlListArray.add(sql.toString());
        }

        //���
        QuestionInfo qb = new QuestionInfo();
        qb.setQuestionid(nextref);
        qb.setCuserid(this.logined(request).getRef());
        qb.setCusername(this.logined(request).getRealname());
        qb.setQuestiontype(Integer.parseInt(questype));
        qb.setQuestionlevel(3);
        if(questype.equals("2")){
            qb.setCorrectanswer(correctanswer);
        }
        sql = new StringBuilder();
        objList = this.questionManager.getSaveSql(qb, sql);
        if (objList != null && sql != null && sql.length() > 0) {
            objListArray.add(objList);
            sqlListArray.add(sql.toString());
        }
        //�޸�Content��analysis
        this.questionManager.getArrayUpdateLongText("question_info","question_id","content",quesname,nextref.toString(),sqlListArray,objListArray);
        if(quesanswer!=null&&quesanswer.trim().length()>0)
            this.questionManager.getArrayUpdateLongText("question_info","question_id","analysis",quesanswer,nextref.toString(),sqlListArray,objListArray);
        if(questype.equals("1")){
            this.questionManager.getArrayUpdateLongText("question_info","question_id","correct_answer",correctanswer,nextref.toString(),sqlListArray,objListArray);
        }


        //��Ӳ�����־
     /*   sql = new StringBuilder();
        objList=this.questionManager.getAddOperateLog(this.logined(request).getRef(),"question_info",nextref.toString()
                ,null,null,"ADD","�������:content:"+quesname,sql);
        if (objList != null && sql != null && sql.length() > 0) {
            objListArray.add(objList);
            sqlListArray.add(sql.toString());
        }*/

        //������ʴ���������Ϊ��ȷ��
        if (questype.equals("1") || (questype.equals("2"))) {

        } else if (questype.equals("3") || questype.equals("4")) {
            if (optionData==null || isrightData==null ||
                    optionData.trim().length()<1||isrightData.trim().length()<1) {
                je.setMsg("�쳣����,δ��ȡ��ѡ����ѡ����!");
                response.getWriter().print(je.toJSON());
                return;
            }
            String[]optionArray=optionData.split("#sz#");
            String[]isrightArray=isrightData.split(",");

            if(optionArray!=null&&isrightArray!=null&&optionArray.length!=isrightArray.length){
                je.setMsg("ѡ�����������һ��!");
                response.getWriter().print(je.toJSON());
                return;
            }

            if(optionArray.length>26){
                je.setMsg("ѡ�����!");
                response.getWriter().print(je.toJSON());
                return;
            }
            for (int i = 0; i < optionArray.length; i++) {
                //ѡ����ѡ��
                QuestionOption qoption = new QuestionOption();
                qoption.setQuestionid(this.questionOptionManager.getNextId(true));
                //���ABCD
                qoption.setOptiontype(UtilTool.AZ[i]);
                qoption.setQuestionid(nextref);
                qoption.setContent(optionArray[i]);
                qoption.setIsright(Integer.parseInt(isrightArray[i]));
                sql = new StringBuilder();
                objList = this.questionOptionManager.getSaveSql(qoption, sql);
                if (objList != null && sql != null && sql.length() > 0) {
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }

                //��Ӳ�����־
             /*   sql = new StringBuilder();
                objList=this.questionManager.getAddOperateLog(this.logined(request).getRef(),"j_question_option",nextref.toString()
                        ,null,null,"ADD","�������ѡ��:content:"+optionArray[i],sql);
                if (objList != null && sql != null && sql.length() > 0) {
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }*/
            }
        }


        TpCourseInfo tmpCourse=tcList.get(0);
        if(tmpCourse.getQuoteid()!=null&&tmpCourse.getQuoteid().intValue()!=0){
            TpCourseInfo quoteInfo=new TpCourseInfo();
            quoteInfo.setCourseid(tmpCourse.getQuoteid());
            List<TpCourseInfo>quoteList=this.tpCourseManager.getList(quoteInfo,null);
            if(quoteList!=null&&quoteList.size()>0&&quoteList.get(0).getCourselevel()!=3){
                //����ר���������
                TpOperateInfo to=new TpOperateInfo();
                to.setRef(this.tpOperateManager.getNextId(true));
                to.setCuserid(this.logined(request).getUserid());
                to.setCourseid(tmpCourse.getQuoteid());
                to.setTargetid(nextref);
                to.setOperatetype(2);                                                                   //���
                to.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_QUESTION.getValue());                 //ר����Դ
                sql=new StringBuilder();
                objList=this.tpOperateManager.getSaveSql(to,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }

        }

        if (objListArray.size() > 0 && sqlListArray.size() > 0) {
            boolean flag = this.questionManager.doExcetueArrayProc(sqlListArray, objListArray);
            if (flag) {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
                je.getObjList().add(nextref);
            } else {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        } else {
            je.setMsg("���Ĳ���û��ִ��!");
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * �Ծ��������--ģʽ����
     *
     * @throws Exception
     */
    @RequestMapping(params = "m=doSubAddPaperQuestion", method = RequestMethod.POST)
    public void doSubAddPaperQuestion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String courseid = request.getParameter("courseid");
        String questype = request.getParameter("questype");
        String questionid= request.getParameter("questionid");
        String paperid = request.getParameter("paperid");
        if (StringUtils.isBlank(courseid)) {
            je.setMsg("�쳣����,δ��ȡ��ר���ʶ!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (StringUtils.isBlank(paperid)) {
            je.setMsg("�쳣����,δ��ȡ���Ծ��ʶ!");
            response.getWriter().print(je.toJSON());
            return;
        }
        StringBuilder sql = null;
        List<Object> objList = null;
        List<String> sqlListArray = new ArrayList<String>();
        List<List<Object>> objListArray = new ArrayList<List<Object>>();

        /**
         * ��ѯ��ר���Ƿ������˹���ר�⣬����Ԫ�ز�����¼
         */
        TpCourseInfo tc=new TpCourseInfo();
        tc.setCourseid(Long.parseLong(courseid));
        List<TpCourseInfo>tcList=this.tpCourseManager.getList(tc, null);
        if(tcList==null||tcList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }





        String quesname = request.getParameter("quesname");
        String correctanswer = request.getParameter("correctanswer");
        String quesanswer = request.getParameter("quesanswer");
        //String[] optionArray = request.getParameterValues("optionArray");
        //String[] isrightArray = request.getParameterValues("is_Right");
        String optionData=request.getParameter("optionArray");
        String isrightData=request.getParameter("rightArray");
        if (questionid==null||questionid.length()<1) {
            je.setMsg("�쳣����,δ��ȡ�������ʶ!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (StringUtils.isBlank(questype)) {
            je.setMsg("�쳣����,δ��ȡ����������!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (StringUtils.isBlank(quesname)) {
            je.setMsg("�쳣����,δ��ȡ�����!");
            response.getWriter().print(je.toJSON());
            return;
        }
        PaperInfo paperInfo=new PaperInfo();
        paperInfo.setPaperid(Long.parseLong(paperid));
        List<PaperInfo>paperList=this.paperManager.getList(paperInfo,null);
        if(paperList==null||paperList.size()<1){
            je.setMsg(UtilTool.utilproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }
        //����ID
        Long nextref = Long.parseLong(questionid);

        //������ר��
        TpCourseQuestion tpCourseQuestion=new TpCourseQuestion();
        tpCourseQuestion.setQuestionid(nextref);
        tpCourseQuestion.setCourseid(Long.parseLong(courseid));
        tpCourseQuestion.setRef(this.tpCourseQuestionManager.getNextId(true));
        sql = new StringBuilder();
        objList = this.tpCourseQuestionManager.getSaveSql(tpCourseQuestion, sql);
        if (objList != null && sql != null && sql.length() > 0) {
            objListArray.add(objList);
            sqlListArray.add(sql.toString());
        }


        //�Ծ������ϵ
        Integer papperQuesSize=this.paperQuestionManager.paperQuesCount(Long.parseLong(paperid));
        Integer maxIdx=papperQuesSize+1;
        PaperQuestion paperQuestion=new PaperQuestion();
        paperQuestion.setPaperid(Long.parseLong(paperid));
        paperQuestion.setQuestionid(nextref);
        paperQuestion.setOrderidx(maxIdx);
        sql = new StringBuilder();
        objList = this.paperQuestionManager.getSaveSql(paperQuestion, sql);
        if (objList != null && sql != null && sql.length() > 0) {
            objListArray.add(objList);
            sqlListArray.add(sql.toString());
        }




        //���
        QuestionInfo qb = new QuestionInfo();
        qb.setQuestionid(nextref);
        qb.setCuserid(this.logined(request).getRef());
        qb.setCusername(this.logined(request).getRealname());
        qb.setQuestiontype(Integer.parseInt(questype));
        qb.setQuestionlevel(3);
        if(questype.equals("2")){
            qb.setCorrectanswer(correctanswer);
        }
        sql = new StringBuilder();
        objList = this.questionManager.getSaveSql(qb, sql);
        if (objList != null && sql != null && sql.length() > 0) {
            objListArray.add(objList);
            sqlListArray.add(sql.toString());
        }
        //�޸�Content��analysis
        this.questionManager.getArrayUpdateLongText("question_info","question_id","content",quesname,nextref.toString(),sqlListArray,objListArray);
        if(quesanswer!=null&&quesanswer.trim().length()>0)
            this.questionManager.getArrayUpdateLongText("question_info","question_id","analysis",quesanswer,nextref.toString(),sqlListArray,objListArray);
        if(questype.equals("1")){
            this.questionManager.getArrayUpdateLongText("question_info","question_id","correct_answer",correctanswer,nextref.toString(),sqlListArray,objListArray);
        }


        //��Ӳ�����־
     /*   sql = new StringBuilder();
        objList=this.questionManager.getAddOperateLog(this.logined(request).getRef(),"question_info",nextref.toString()
                ,null,null,"ADD","�������:content:"+quesname,sql);
        if (objList != null && sql != null && sql.length() > 0) {
            objListArray.add(objList);
            sqlListArray.add(sql.toString());
        }*/

        //������ʴ���������Ϊ��ȷ��
        if (questype.equals("1") || (questype.equals("2"))) {

        } else if (questype.equals("3") || questype.equals("4")) {
            if (optionData==null || isrightData==null ||
                    optionData.trim().length()<1||isrightData.trim().length()<1) {
                je.setMsg("�쳣����,δ��ȡ��ѡ����ѡ����!");
                response.getWriter().print(je.toJSON());
                return;
            }
            String[]optionArray=optionData.split("#sz#");
            String[]isrightArray=isrightData.split(",");

            if(optionArray!=null&&isrightArray!=null&&optionArray.length!=isrightArray.length){
                je.setMsg("ѡ�����������һ��!");
                response.getWriter().print(je.toJSON());
                return;
            }

            if(optionArray.length>26){
                je.setMsg("ѡ�����!");
                response.getWriter().print(je.toJSON());
                return;
            }
            for (int i = 0; i < optionArray.length; i++) {
                //ѡ����ѡ��
                QuestionOption qoption = new QuestionOption();
                qoption.setQuestionid(this.questionOptionManager.getNextId(true));
                //���ABCD
                qoption.setOptiontype(UtilTool.AZ[i]);
                qoption.setQuestionid(nextref);
                qoption.setContent(optionArray[i]);
                qoption.setIsright(Integer.parseInt(isrightArray[i]));
                sql = new StringBuilder();
                objList = this.questionOptionManager.getSaveSql(qoption, sql);
                if (objList != null && sql != null && sql.length() > 0) {
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }

                //��Ӳ�����־
             /*   sql = new StringBuilder();
                objList=this.questionManager.getAddOperateLog(this.logined(request).getRef(),"j_question_option",nextref.toString()
                        ,null,null,"ADD","�������ѡ��:content:"+optionArray[i],sql);
                if (objList != null && sql != null && sql.length() > 0) {
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }*/
            }
        }


        TpCourseInfo tmpCourse=tcList.get(0);
        if(tmpCourse.getQuoteid()!=null&&tmpCourse.getQuoteid().intValue()!=0){
            TpCourseInfo quoteInfo=new TpCourseInfo();
            quoteInfo.setCourseid(tmpCourse.getQuoteid());
            List<TpCourseInfo>quoteList=this.tpCourseManager.getList(quoteInfo,null);
            if(quoteList!=null&&quoteList.size()>0&&quoteList.get(0).getCourselevel()!=3){
                //����ר���������
                TpOperateInfo to=new TpOperateInfo();
                to.setRef(this.tpOperateManager.getNextId(true));
                to.setCuserid(this.logined(request).getUserid());
                to.setCourseid(tmpCourse.getQuoteid());
                to.setTargetid(nextref);
                to.setOperatetype(2);                                                                   //���
                to.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_QUESTION.getValue());                 //ר����Դ
                sql=new StringBuilder();
                objList=this.tpOperateManager.getSaveSql(to,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }

        }

        if (objListArray.size() > 0 && sqlListArray.size() > 0) {
            boolean flag = this.questionManager.doExcetueArrayProc(sqlListArray, objListArray);
            if (flag) {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
                je.getObjList().add(nextref);
                if(paperid!=null&&paperid.trim().length()>0){
                    PaperQuestion p=new PaperQuestion();
                    p.setPaperid(Long.parseLong(paperid));
                    PaperQuestion pq=new PaperQuestion();
                    pq.setPaperid(p.getPaperid());
                    Float sumScore=this.paperQuestionManager.getSumScore(pq);
                    if(sumScore!=null){
                        PaperInfo paper=new PaperInfo();
                        paper.setPaperid(pq.getPaperid());
                        paper.setScore(sumScore);
                        this.paperManager.doUpdate(paper);
                    }
                }
            } else {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        } else {
            je.setMsg("���Ĳ���û��ִ��!");
        }
        response.getWriter().print(je.toJSON());
    }





    /**
     * ִ�������޸�
     *
     * @throws Exception
     */
    @RequestMapping(params = "m=doSubUpdQuestion", method = RequestMethod.POST)
    public void doSubUpdQuestion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String questionid = request.getParameter("questionid");
        String courseid=request.getParameter("courseid");
        String correctanswer=request.getParameter("correctanswer");
        String paperid=request.getParameter("paperid");
        if (StringUtils.isBlank(questionid)||StringUtils.isBlank(courseid)) { //||StringUtils.isBlank(correctanswer)
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        //��֤����
        //�õ����� (ֻȡһ������)
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        presult.setPageNo(1);
        //����ID�õ�ԭ�е��������
        TpCourseQuestion question=new TpCourseQuestion();
        question.setQuestionid(Long.parseLong(questionid));
        question.setCourseid(Long.parseLong(courseid));
        List<TpCourseQuestion>questionList=this.tpCourseQuestionManager.getList(question,presult);
        if(questionList==null||questionList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());return;
        }
        question=questionList.get(0);
        QuestionInfo q=new QuestionInfo();
        q.setQuestionid(question.getQuestionid());
        //��ȡ����ID
        List<QuestionInfo>questionInfoList=this.questionManager.getList(q,null);
        if(questionInfoList==null||questionInfoList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());return;
        }
        q=questionInfoList.get(0);



        String quesname = request.getParameter("quesname");
        String quesanswer = request.getParameter("quesanswer");
        String optionData=request.getParameter("optionArray");
        String isrightData=request.getParameter("rightArray");

        if (StringUtils.isBlank(quesname)) {
            je.setMsg("�쳣����,δ��ȡ�����!");
            response.getWriter().print(je.toJSON());
            return;
        }

        StringBuilder sql = null;
        List<Object> objList = null;
        List<String> sqlListArray = new ArrayList<String>();
        List<List<Object>> objListArray = new ArrayList<List<Object>>();
        Long nextref = this.questionManager.getNextId(true);
        boolean delOption=true;
        if(q.getCloudstatus()!=null&&q.getQuestionid()>0){
            //���
            delOption=false;
            QuestionInfo qb = new QuestionInfo();
            qb.setQuestionid(nextref);
            qb.setCuserid(this.logined(request).getRef());
            qb.setCusername(this.logined(request).getRealname());
            qb.setQuestiontype(q.getQuestiontype());
            qb.setQuestionlevel(3);
            if(q.getQuestiontype().toString().equals("2")){
                qb.setCorrectanswer(correctanswer);
            }
            sql = new StringBuilder();
            objList = this.questionManager.getSaveSql(qb, sql);
            if (objList != null && sql != null && sql.length() > 0) {
                objListArray.add(objList);
                sqlListArray.add(sql.toString());
            }

            //�޸�Content��analysis
            this.questionManager.getArrayUpdateLongText("question_info","question_id","content",quesname,nextref.toString(),sqlListArray,objListArray);
            if(quesanswer!=null&&quesanswer.trim().length()>0)
                this.questionManager.getArrayUpdateLongText("question_info","question_id","analysis",quesanswer,nextref.toString(),sqlListArray,objListArray);
            if(q.getQuestiontype().toString().equals("1")){
                this.questionManager.getArrayUpdateLongText("question_info","question_id","correct_answer",correctanswer,nextref.toString(),sqlListArray,objListArray);
            }

            //�޸�������ר��
            TpCourseQuestion tpCourseQuestion=new TpCourseQuestion();
            tpCourseQuestion.setQuestionid(nextref);
            tpCourseQuestion.setRef(question.getRef());
            sql = new StringBuilder();
            objList = this.tpCourseQuestionManager.getUpdateSql(tpCourseQuestion, sql);
            if (objList != null && sql != null && sql.length() > 0) {
                objListArray.add(objList);
                sqlListArray.add(sql.toString());
            }

            //�޸��������Ծ��ϵ ������ר������
            if(paperid!=null&&paperid.trim().length()>0){
                PaperQuestion sel=new PaperQuestion();
                sel.setQuestionid(q.getQuestionid());
                sel.setPaperid(Long.parseLong(paperid));
                List<PaperQuestion>paperQuestionList=this.paperQuestionManager.getList(sel,null);
                if(paperQuestionList!=null&&paperQuestionList.size()>0){
                    PaperQuestion del=new PaperQuestion();
                    del.setRef(paperQuestionList.get(0).getRef());
                    sql = new StringBuilder();
                    objList=this.paperQuestionManager.getDeleteSql(del,sql);
                    if(sql!=null&&sql.toString().trim().length()>0){
                        objListArray.add(objList);
                        sqlListArray.add(sql.toString());
                    }

                    PaperQuestion add=new PaperQuestion();
                    add.setQuestionid(nextref);
                    add.setPaperid(Long.parseLong(paperid));
                    add.setOrderidx(paperQuestionList.get(0).getOrderidx());
                    add.setScore(paperQuestionList.get(0).getScore());
                    sql = new StringBuilder();
                    objList=this.paperQuestionManager.getSaveSql(add,sql);
                    if(sql!=null&&sql.toString().trim().length()>0){
                        objListArray.add(objList);
                        sqlListArray.add(sql.toString());
                    }
                }
            }

            //����ר��
            if(question.getQuoteid()!=null&&question.getQuoteid().intValue()!=0){
                TpCourseInfo quoteInfo=new TpCourseInfo();
                quoteInfo.setCourseid(question.getQuoteid());
                List<TpCourseInfo>quoteList=this.tpCourseManager.getList(quoteInfo,null);
                if(quoteList!=null&&quoteList.size()>0&&quoteList.get(0).getCourselevel()!=3){
                    //����tp_operate_info���м�һ������ ���й���      //�˲������ڻ���վ����ʾ
                    TpOperateInfo operateInfo=new TpOperateInfo();
                    operateInfo.setCourseid(question.getQuoteid());
                    operateInfo.setTargetid(nextref);
                    operateInfo.setOperatetype(3);   //�޸�
                    operateInfo.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_QUESTION.getValue()); //����
                    operateInfo.setCuserid(this.logined(request).getUserid());
                    operateInfo.setRef(this.tpOperateManager.getNextId(true));
                    sql=new StringBuilder();
                    objList=this.tpOperateManager.getSaveSql(operateInfo, sql);
                    if(sql!=null&&sql.toString().trim().length()>0){
                        objListArray.add(objList);
                        sqlListArray.add(sql.toString());
                    }
                    //������־
                  /*  sql=new StringBuilder();
                    objList=this.tpTopicManager.getAddOperateLog(this.logined(request).getRef(),"TP_OPERATE_INFO"
                            ,questionid.toString(),null,null,"UPDATE","��������ID�޸ģ������ǹ�����߱�׼���⣬���¼������Ԫ�ر���!",sql);
                    //�˲������ڻ���վ����ʾ
                    if(sql!=null&&sql.toString().trim().length()>0){
                        objListArray.add(objList);
                        sqlListArray.add(sql.toString());
                    } */
                }

            }
        }else{
            nextref=question.getQuestionid();
            //�����
            this.questionManager.getArrayUpdateLongText("question_info","question_id","content","",nextref.toString(),sqlListArray,objListArray);
            this.questionManager.getArrayUpdateLongText("question_info","question_id","analysis","",nextref.toString(),sqlListArray,objListArray);
            //�޸�Content��analysis
            this.questionManager.getArrayUpdateLongText("question_info","question_id","content",quesname,nextref.toString(),sqlListArray,objListArray);
            this.questionManager.getArrayUpdateLongText("question_info","question_id","analysis",quesanswer,nextref.toString(),sqlListArray,objListArray);
            if(q.getQuestiontype().toString().equals("1")){
                this.questionManager.getArrayUpdateLongText("question_info","question_id","correct_answer","",nextref.toString(),sqlListArray,objListArray);
                this.questionManager.getArrayUpdateLongText("question_info","question_id","correct_answer",correctanswer,nextref.toString(),sqlListArray,objListArray);
            }

        }

        //��¼�޸�ǰ������
       StringBuilder operateLogRemark= new StringBuilder("�޸�ǰ������: content:");
        operateLogRemark.append(question.getContent());
        operateLogRemark.append("  analysis:");
        operateLogRemark.append(question.getAnalysis());
        //��¼�޸ĺ��ֵ
        operateLogRemark.append("�޸ĺ��ֵ��content:");
        operateLogRemark.append(quesname);
        operateLogRemark.append("  status:");
        operateLogRemark.append(quesanswer);

        //������־
      /*  sql=new StringBuilder();
        objList=this.questionManager.getAddOperateLog(this.logined(request).getRef(),"queston_info", question.getQuestionid().toString(),null,null,"UPDATE"
                ,operateLogRemark.toString(),sql);
        if(sql!=null&&sql.toString().trim().length()>0){
            objListArray.add(objList);
            sqlListArray.add(sql.toString());
        }*/


        //������ʴ���������Ϊ��ȷ��
        if (question.getQuestiontype()==1||question.getQuestiontype()==2) {

        } else if (question.getQuestiontype()==3||question.getQuestiontype()==4) {
            if (optionData==null || isrightData==null ||
                    optionData.trim().length()<1||isrightData.trim().length()<1) {
                je.setMsg("�쳣����,δ��ȡ��ѡ����ѡ����!");
                response.getWriter().print(je.toJSON());
                return;
            }
            String[]optionArray=optionData.split("#sz#");
            String[]isrightArray=isrightData.split(",");

            if(optionArray!=null&&isrightArray!=null&&optionArray.length!=isrightArray.length){
                je.setMsg("ѡ�����������һ��!");
                response.getWriter().print(je.toJSON());
                return;
            }

            if(optionArray.length>26){
                je.setMsg("ѡ�����!");
                response.getWriter().print(je.toJSON());
                return;
            }

            /**
             * �ƶ�����ѡ�ɾ��
             */
            if(delOption){
                QuestionOption delete = new QuestionOption();
                delete.setQuestionid(question.getQuestionid());
                sql=new StringBuilder();
                objList = this.questionOptionManager.getDeleteSql(delete, sql);
                if (objList != null && sql != null && sql.length() > 0) {
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
            for (int i = 0; i < optionArray.length; i++) {
                //ѡ����ѡ��
                QuestionOption qoption = new QuestionOption();
                //���ABCD
                qoption.setOptiontype(UtilTool.AZ[i]);
                qoption.setQuestionid(nextref);
                qoption.setContent(optionArray[i]);
                qoption.setIsright(Integer.parseInt(isrightArray[i]));
                sql = new StringBuilder();
                objList = this.questionOptionManager.getSaveSql(qoption, sql);
                if (objList != null && sql != null && sql.length() > 0) {
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }

                //��Ӳ�����־
             /*   sql = new StringBuilder();
                objList=this.questionManager.getAddOperateLog(this.logined(request).getRef(),"j_question_option",nextref.toString()
                        ,null,null,"UPDATE>ADD","�޸Ĳ��������ѡ��:content:"+optionArray[i],sql);
                if (objList != null && sql != null && sql.length() > 0) {
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }*/
            }
        }

        if (objListArray.size() > 0 && sqlListArray.size() > 0) {
            boolean flag = this.questionManager.doExcetueArrayProc(sqlListArray, objListArray);
            if (flag) {
                je.setMsg(UtilTool.msgproperty.getProperty("MODIFY_SUCCESS"));
                je.setType("success");
            } else {
                je.setMsg(UtilTool.msgproperty.getProperty("MODIFY_ERROR"));
            }
        } else {
            je.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * �ָ�����վ�е�����
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doRestoreQuestion",method=RequestMethod.POST)
    public void doRestoreTask(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String ref=request.getParameter("ref");
        if(ref==null||ref.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpCourseQuestion t=new TpCourseQuestion();
        t.setRef(Long.parseLong(ref));
        t.setLocalstatus(1);
        if(this.tpCourseQuestionManager.doUpdate(t)){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }


    /**
     * �Ծ���ع���
     * */


    /**
     * ���⵼��ҳ��
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=toImportPaperQuesPage", method = RequestMethod.POST)
    public ModelAndView toImportQuesPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        String courseid=request.getParameter("corseid");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return null;
        }
        request.setAttribute("corseid",courseid);
        return new ModelAndView("/teachpaltform/paper/add/import-ques");
    }

    /**
     * ��ȡҪ�����Ծ�������б�
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=importPaperQuesList", method = RequestMethod.POST)
    public void importQuestionList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity jsonEntity=new JsonEntity();
        PageResult presult=this.getPageResultParameter(request);
        String courseid=request.getParameter("courseid");
        String coursename=request.getParameter("coursename");


        //�õ��б�
        List<TpCourseQuestion> tmpList=new ArrayList<TpCourseQuestion>();
        TpCourseQuestion tpCourseQuestion=new TpCourseQuestion();
        tpCourseQuestion.setCourseid(Long.parseLong(courseid));
        //1����Ч 2����ɾ��
        tpCourseQuestion.setStatus(1);
        if(courseid!=null&&courseid.trim().length()>0)
            tpCourseQuestion.setCourseid(Long.parseLong(courseid));
        if(coursename!=null&&coursename.trim().length()>0)
            tpCourseQuestion.setCoursename(coursename);


        /*if(questionid!=null&&questionid.trim().length()>0)
            tpCourseQuestion.setQuestionid(Long.parseLong(questionid));
        if(flag!=null&&flag.trim().length()>0)
            tpCourseQuestion.setFlag(1);//ȥ���ѷ������ҽ����˵���������
        tpCourseQuestion.setQuestiontype(questype.equals("0")?null:Integer.parseInt(questype));*/
        tpCourseQuestion.setUserid(this.logined(request).getUserid());
        presult.setOrderBy(" u.c_time DESC,q.c_time DESC ");
        List<TpCourseQuestion> tpCourseQuestionList=this.tpCourseQuestionManager.getList(tpCourseQuestion,presult);
        if(tpCourseQuestionList!=null&&tpCourseQuestionList.size()>0){
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
                tmpList.add(tq);
            }
        }
        presult.setList(tmpList);
        jsonEntity.setPresult(presult);
        jsonEntity.setType("success");
        response.getWriter().print(jsonEntity.toJSON());
    }

}


