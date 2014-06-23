package com.school.control.teachpaltform;

import com.school.control.base.BaseController;
import com.school.entity.DictionaryInfo;
import com.school.entity.teachpaltform.*;
import com.school.manager.DictionaryManager;
import com.school.manager.inter.IDictionaryManager;
import com.school.manager.inter.teachpaltform.*;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicManager;
import com.school.manager.teachpaltform.*;
import com.school.manager.teachpaltform.interactive.TpTopicManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import org.apache.commons.lang.StringUtils;
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
    private IQuestionManager questionManager;
    private ITpCourseManager tpCourseManager;
    private IDictionaryManager dictionaryManager;
    private IQuestionOptionManager questionOptionManager;
    private ITpCourseQuestionManager tpCourseQuestionManager;
    private ITpOperateManager tpOperateManager;
    private ITpTopicManager tpTopicManager;
    private ITpCourseTeachingMaterialManager tpCourseTeachingMaterialManager;

    public QuestionController(){
        this.questionManager=this.getManager(QuestionManager.class);
        this.tpCourseManager=this.getManager(TpCourseManager.class);
        this.dictionaryManager=this.getManager(DictionaryManager.class);
        this.questionOptionManager=this.getManager(QuestionOptionManager.class);
        this.tpCourseQuestionManager=this.getManager(TpCourseQuestionManager.class);
        this.tpOperateManager=this.getManager(TpOperateManager.class);
        this.tpTopicManager=this.getManager(TpTopicManager.class);
        this.tpCourseTeachingMaterialManager=this.getManager(TpCourseTeachingMaterialManager.class);
    }
    /**
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
        //获取当前专题教材
        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(Long.parseLong(courseid));
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);
        if(materialList!=null&&materialList.size()>0)
            subjectid=materialList.get(0).getSubjectid();
        //课题样式
        request.setAttribute("coursename", courseList.get(0).getCoursename());
        TpCourseInfo tcs= new TpCourseInfo();
        tcs.setUserid(this.logined(request).getUserid());
        tcs.setTermid(courseList.get(0).getTermid());
        tcs.setLocalstatus(1);
        if(subjectid!=null)
            tcs.setSubjectid(subjectid);
        List<TpCourseInfo>courseStyleList=this.tpCourseManager.getCourseList(tcs, null);
        request.setAttribute("courseList", courseStyleList);
 
        List<DictionaryInfo> quesTypeList = this.dictionaryManager.getDictionaryByType("TP_QUESTION_TYPE");
        mp.put("quesTypeList",quesTypeList);
        mp.put("courseid",courseList.get(0).getCourseid());
        return new ModelAndView("/teachpaltform/question/ques-admin",mp);
    }

    /**
     * 根据试题ID，加载试题详情
     *
     * @return
     */
    @RequestMapping(params = "m=todetail", method = RequestMethod.GET)
    public ModelAndView toQuestionDetail(HttpServletRequest request, HttpServletResponse response,ModelMap mp) throws Exception {
        String questionid = request.getParameter("id");
        JsonEntity je = new JsonEntity();
        if (questionid == null || questionid.trim().length() < 1) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        QuestionInfo question = new QuestionInfo();
        question.setQuestionid(Long.parseLong(questionid));
        List<QuestionInfo> objList = this.questionManager.getList(question,null);
        if(objList!=null){
            question = new QuestionInfo();
            question=objList.get(0);
            if(question.getQuestiontype()==3||question.getQuestiontype()==4){
                QuestionOption qo = new QuestionOption();
                qo.setQuestionid(question.getQuestionid());
                PageResult p = new PageResult();
                p.setOrderBy("option_type");
                List<QuestionOption> qoList = this.questionOptionManager.getList(qo,p);
                if(qoList!=null&&qoList.size()>0){
                    question.setQuestionOption(qoList);
                }else{
                    je.setMsg("未查到试题选项，请联系管理员");
                    response.getWriter().print(je.getAlertMsgAndBack());
                    return null;
                }
            }
        }else{
            je.setMsg("系统异常，请稍后再试");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        mp.put("question",question);
        return new ModelAndView("/teachpaltform/question/ques-detail",mp);
    }

    /**
     * 修改试题页
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
        if (questionid == null || questionid.trim().length() < 1) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //验证试题
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
        //单选与选择题选项
        if(ques.getQuestiontype()==3||ques.getQuestiontype()==4){
            QuestionOption questionOption=new QuestionOption();
            questionOption.setQuestionid(ques.getQuestionid());
            PageResult p = new PageResult();
            p.setOrderBy("option_type");
            List<QuestionOption>questionOptionList=this.questionOptionManager.getList(questionOption,p);
            mp.put("quesOptionList",questionOptionList);
        }

        List<DictionaryInfo> quesTypeList = this.dictionaryManager.getDictionaryByType("TP_QUESTION_TYPE");
        mp.put("quesTypeList",quesTypeList);
        mp.put("question",ques);

        return new ModelAndView("/teachpaltform/question/ques-update",mp);
    }

    /**
     * 获取试题列表
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
        //得到列表
        List<TpCourseQuestion> tmpList=new ArrayList<TpCourseQuestion>();
        TpCourseQuestion tpCourseQuestion=new TpCourseQuestion();
        if(questionid!=null&&questionid.trim().length()>0)
            tpCourseQuestion.setQuestionid(Long.parseLong(questionid));
        tpCourseQuestion.setCourseid(Long.parseLong(courseid));
        //1：有效 2：已删除
        tpCourseQuestion.setStatus(1);
        if(flag!=null&&flag.trim().length()>0)
            tpCourseQuestion.setFlag(1);//去除已发布并且结束了的任务试题
        tpCourseQuestion.setQuestiontype(questype.equals("0")?null:Integer.parseInt(questype));
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


    /**
     * 执行删除
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


        //查询数据是否存在
        //只得到一条数据
        //得到数据 (只取一条数据)

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
        //开始删除
        //批量操作记录
        List<List<Object>> objListArray=new ArrayList<List<Object>>();
        List<String> sqlStrList=new ArrayList<String>();
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=null;
        //如果是标准或者是
        TpCourseQuestion tmpques=questionsList.get(0);

        //云端数据，则只编写记录
        if(tmpques.getQuoteid()!=null&&tmpques.getQuoteid().intValue()!=0){
            TpCourseInfo quoteInfo=new TpCourseInfo();
            quoteInfo.setCourseid(tmpques.getQuoteid());
            List<TpCourseInfo>quoteList=this.tpCourseManager.getList(quoteInfo,null);
            if(quoteList!=null&&quoteList.size()>0&&quoteList.get(0).getCourselevel()!=3){
                    Long operateNextId=this.tpOperateManager.getNextId(true);
                    //则向tp_operate_info表中加一条数据 进行关联      //此操作会在回收站中显示
                    TpOperateInfo operateInfo=new TpOperateInfo();
                    operateInfo.setCourseid(tmpques.getQuoteid());
                    operateInfo.setTargetid(tmpques.getQuestionid());
                    operateInfo.setOperatetype(1);   //1:删除
                    operateInfo.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_QUESTION.getValue()); //试题
                    operateInfo.setCuserid(this.logined(request).getUserid());
                    operateInfo.setRef(operateNextId);
                    objList=this.tpOperateManager.getSaveSql(operateInfo, sqlbuilder);
                    if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                        objListArray.add(objList);
                        sqlStrList.add(sqlbuilder.toString());
                    }
                    //操作日志
                    sqlbuilder=new StringBuilder();
                    objList=this.tpTopicManager.getAddOperateLog(this.logined(request).getRef(),"TP_OPERATE_INFO"
                            ,tmpques.getQuoteid().toString(),null,null,"DEL","根据试题ID删除，由于是共享或者标准论题，则记录至操作元素表中,不实际删除!",sqlbuilder);
                    //此操作会在回收站中显示
                    if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                        objListArray.add(objList);
                        sqlStrList.add(sqlbuilder.toString());
                    }
            }
        }
        TpCourseQuestion quesUpdate=new TpCourseQuestion();
        quesUpdate.setLocalstatus(2);//逻辑删除        此操作会在回收站中显示
        quesUpdate.setRef(Long.parseLong(ref));
        objList=this.tpCourseQuestionManager.getUpdateSql(quesUpdate,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            objListArray.add(objList);
            sqlStrList.add(sqlbuilder.toString());
        }
        sqlbuilder=new StringBuilder();
        objList=this.questionManager.getAddOperateLog(this.logined(request).getRef(),"tp_j_course_question"
                ,tmpques.getQuestionid().toString(),null,null,"DELETE","根据试题ID逻辑删除",sqlbuilder);
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
     * 添加试题页
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toAddQuestion", method = RequestMethod.GET)
    public ModelAndView toAddQuestion(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
        PageResult presult = new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(1);
        List<TpCourseInfo> courseList = this.tpCourseManager.getTchCourseList(tpCourseInfo, presult);
        if(courseList==null||courseList.size()<1){
            je.setMsg(UtilTool.msgproperty.get("ENTITY_NOT_EXISTS").toString());
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //获取问题类型
        List<DictionaryInfo> questiontypeList = this.dictionaryManager.getDictionaryByType("TP_QUESTION_TYPE");
        request.setAttribute("quesTypeList", questiontypeList);
        request.setAttribute("questionid",this.questionManager.getNextId(true));
        return new ModelAndView("/teachpaltform/question/ques-add");
    }

    /**
     * 添加试题页(根据专题查询出试题列表)
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toAddQuestionByCourse", method = RequestMethod.GET)
    public ModelAndView toAddQuestionByCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //得到该课题的试题。
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
     * 根据专题得到试题列表
     *
     * @throws Exception
     */
    @RequestMapping(params = "m=getQuestionByCourse", method = RequestMethod.POST)
    public void getQuestionListByCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        PageResult presult = this.getPageResultParameter(request);
        String selectCourseid = request.getParameter("selectCourseid");
        TpCourseQuestion tpCourseQuestion = new TpCourseQuestion();
        tpCourseQuestion.setCourseid(Long.parseLong(selectCourseid));
        tpCourseQuestion.setStatus(1);
        List<TpCourseQuestion> questionList = this.tpCourseQuestionManager.getList(tpCourseQuestion,presult);
        for(TpCourseQuestion tcq:questionList){
            QuestionOption obj = new QuestionOption();
            obj.setQuestionid(tcq.getQuestionid());
            PageResult p=new PageResult();
            p.setOrderBy("u.option_type");
            List<QuestionOption> optionList = this.questionOptionManager.getList(obj,p);
            if(optionList!=null){
                tcq.setQuestionOptionList(optionList);
            }
        }
        presult.setList(questionList);
        je.setPresult(presult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 根据专题得到试题添加试题，批量添加
     *
     * @throws Exception
     */
    @RequestMapping(params = "m=addQuestionByCourse", method = RequestMethod.POST)
    public void addQuestionListByCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String addCourseId = request.getParameter("addCourseId");
        String questionids = request.getParameter("questionids");
        //存放值得数据集集合
        List<String> sqlArrayList = new ArrayList<String>();
        //存放sql的集合
        List<List<Object>> objArrayList = new ArrayList<List<Object>>();
        //拼sql的对象和存放值得对象
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
     * 试题添加
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
            je.setMsg("异常错误,未获取到专题标识!");
            response.getWriter().print(je.toJSON());
            return;
        }
        StringBuilder sql = null;
        List<Object> objList = null;
        List<String> sqlListArray = new ArrayList<String>();
        List<List<Object>> objListArray = new ArrayList<List<Object>>();

        /**
         * 查询该专题是否引用了共享专题，增加元素操作记录
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
            je.setMsg("异常错误,未获取到问题标识!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (StringUtils.isBlank(questype)) {
            je.setMsg("异常错误,未获取到问题类型!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if (StringUtils.isBlank(quesname)) {
            je.setMsg("异常错误,未获取到题干!");
            response.getWriter().print(je.toJSON());
            return;
        }
        //试题ID
        Long nextref = Long.parseLong(questionid);

        //试题与专题
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

        //题干
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
        //修改Content，analysis
        this.questionManager.getArrayUpdateLongText("question_info","question_id","content",quesname,nextref.toString(),sqlListArray,objListArray);
        this.questionManager.getArrayUpdateLongText("question_info","question_id","analysis",quesanswer,nextref.toString(),sqlListArray,objListArray);
        if(questype.equals("1")){
            this.questionManager.getArrayUpdateLongText("question_info","question_id","correct_answer",correctanswer,nextref.toString(),sqlListArray,objListArray);
        }


        //添加操作日志
     /*   sql = new StringBuilder();
        objList=this.questionManager.getAddOperateLog(this.logined(request).getRef(),"question_info",nextref.toString()
                ,null,null,"ADD","添加试题:content:"+quesname,sql);
        if (objList != null && sql != null && sql.length() > 0) {
            objListArray.add(objList);
            sqlListArray.add(sql.toString());
        }*/

        //如果是问答、填空则解析为正确答案
        if (questype.equals("1") || (questype.equals("2"))) {

        } else if (questype.equals("3") || questype.equals("4")) {
            if (optionData==null || isrightData==null ||
                    optionData.trim().length()<1||isrightData.trim().length()<1) {
                je.setMsg("异常错误,未获取到选择题选项或答案!");
                response.getWriter().print(je.toJSON());
                return;
            }
            String[]optionArray=optionData.split("#sz#");
            String[]isrightArray=isrightData.split(",");

            if(optionArray!=null&&isrightArray!=null&&optionArray.length!=isrightArray.length){
                je.setMsg("选项与答案数量不一致!");
                response.getWriter().print(je.toJSON());
                return;
            }

            if(optionArray.length>26){
                je.setMsg("选项过多!");
                response.getWriter().print(je.toJSON());
                return;
            }
            for (int i = 0; i < optionArray.length; i++) {
                //选择题选项
                QuestionOption qoption = new QuestionOption();
                qoption.setQuestionid(this.questionOptionManager.getNextId(true));
                //题号ABCD
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

                //添加操作日志
             /*   sql = new StringBuilder();
                objList=this.questionManager.getAddOperateLog(this.logined(request).getRef(),"j_question_option",nextref.toString()
                        ,null,null,"ADD","添加试题选项:content:"+optionArray[i],sql);
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
                //增加专题操作数据
                TpOperateInfo to=new TpOperateInfo();
                to.setRef(this.tpOperateManager.getNextId(true));
                to.setCuserid(this.logined(request).getUserid());
                to.setCourseid(tmpCourse.getQuoteid());
                to.setTargetid(nextref);
                to.setOperatetype(2);                                                                   //添加
                to.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_QUESTION.getValue());                 //专题资源
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
            je.setMsg("您的操作没有执行!");
        }
        response.getWriter().print(je.toJSON());
    }



    /**
     * 执行试题修改
     *
     * @throws Exception
     */
    @RequestMapping(params = "m=doSubUpdQuestion", method = RequestMethod.POST)
    public void doSubUpdQuestion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String questionid = request.getParameter("questionid");
        String courseid=request.getParameter("courseid");
        String correctanswer=request.getParameter("correctanswer");
        if (StringUtils.isBlank(questionid)||StringUtils.isBlank(courseid)) { //||StringUtils.isBlank(correctanswer)
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        //验证试题
        //得到数据 (只取一条数据)
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        presult.setPageNo(1);
        //根据ID得到原有的相关数据
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
        //获取试题ID
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
            je.setMsg("异常错误,未获取到题干!");
            response.getWriter().print(je.toJSON());
            return;
        }

        StringBuilder sql = null;
        List<Object> objList = null;
        List<String> sqlListArray = new ArrayList<String>();
        List<List<Object>> objListArray = new ArrayList<List<Object>>();
        Long nextref = this.questionManager.getNextId(true);

        if(q.getCloudstatus()!=null&&q.getCloudstatus()==3){
            //题干
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

            //修改Content，analysis
            this.questionManager.getArrayUpdateLongText("question_info","question_id","content",quesname,nextref.toString(),sqlListArray,objListArray);
            this.questionManager.getArrayUpdateLongText("question_info","question_id","analysis",quesanswer,nextref.toString(),sqlListArray,objListArray);
            if(q.getQuestiontype().toString().equals("1")){
                this.questionManager.getArrayUpdateLongText("question_info","question_id","correct_answer",correctanswer,nextref.toString(),sqlListArray,objListArray);
            }

            //修改试题与专题
            TpCourseQuestion tpCourseQuestion=new TpCourseQuestion();
            tpCourseQuestion.setQuestionid(nextref);
            tpCourseQuestion.setRef(question.getRef());
            sql = new StringBuilder();
            objList = this.tpCourseQuestionManager.getUpdateSql(tpCourseQuestion, sql);
            if (objList != null && sql != null && sql.length() > 0) {
                objListArray.add(objList);
                sqlListArray.add(sql.toString());
            }

            //引用专题
            if(question.getQuoteid()!=null&&question.getQuoteid().intValue()!=0){
                TpCourseInfo quoteInfo=new TpCourseInfo();
                quoteInfo.setCourseid(question.getQuoteid());
                List<TpCourseInfo>quoteList=this.tpCourseManager.getList(quoteInfo,null);
                if(quoteList!=null&&quoteList.size()>0&&quoteList.get(0).getCourselevel()!=3){
                    //则向tp_operate_info表中加一条数据 进行关联      //此操作会在回收站中显示
                    TpOperateInfo operateInfo=new TpOperateInfo();
                    operateInfo.setCourseid(question.getQuoteid());
                    operateInfo.setTargetid(nextref);
                    operateInfo.setOperatetype(3);   //修改
                    operateInfo.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_QUESTION.getValue()); //问题
                    operateInfo.setCuserid(this.logined(request).getUserid());
                    operateInfo.setRef(this.tpOperateManager.getNextId(true));
                    sql=new StringBuilder();
                    objList=this.tpOperateManager.getSaveSql(operateInfo, sql);
                    if(sql!=null&&sql.toString().trim().length()>0){
                        objListArray.add(objList);
                        sqlListArray.add(sql.toString());
                    }
                    //操作日志
                    sql=new StringBuilder();
                    objList=this.tpTopicManager.getAddOperateLog(this.logined(request).getRef(),"TP_OPERATE_INFO"
                            ,questionid.toString(),null,null,"UPDATE","根据试题ID修改，由于是共享或者标准论题，则记录至操作元素表中!",sql);
                    //此操作会在回收站中显示
                    if(sql!=null&&sql.toString().trim().length()>0){
                        objListArray.add(objList);
                        sqlListArray.add(sql.toString());
                    }
                }

            }
        }else{
            nextref=question.getQuestionid();
            //先清空
            this.questionManager.getArrayUpdateLongText("question_info","question_id","content","",nextref.toString(),sqlListArray,objListArray);
            this.questionManager.getArrayUpdateLongText("question_info","question_id","analysis","",nextref.toString(),sqlListArray,objListArray);
            //修改Content，analysis
            this.questionManager.getArrayUpdateLongText("question_info","question_id","content",quesname,nextref.toString(),sqlListArray,objListArray);
            this.questionManager.getArrayUpdateLongText("question_info","question_id","analysis",quesanswer,nextref.toString(),sqlListArray,objListArray);
            if(q.getQuestiontype().toString().equals("1")){
                this.questionManager.getArrayUpdateLongText("question_info","question_id","correct_answer","",nextref.toString(),sqlListArray,objListArray);
                this.questionManager.getArrayUpdateLongText("question_info","question_id","correct_answer",correctanswer,nextref.toString(),sqlListArray,objListArray);
            }

        }

        //记录修改前的数据
       StringBuilder operateLogRemark= new StringBuilder("修改前的数据: content:");
        operateLogRemark.append(question.getContent());
        operateLogRemark.append("  analysis:");
        operateLogRemark.append(question.getAnalysis());
        //记录修改后的值
        operateLogRemark.append("修改后的值：content:");
        operateLogRemark.append(quesname);
        operateLogRemark.append("  status:");
        operateLogRemark.append(quesanswer);

        //操作日志
      /*  sql=new StringBuilder();
        objList=this.questionManager.getAddOperateLog(this.logined(request).getRef(),"queston_info", question.getQuestionid().toString(),null,null,"UPDATE"
                ,operateLogRemark.toString(),sql);
        if(sql!=null&&sql.toString().trim().length()>0){
            objListArray.add(objList);
            sqlListArray.add(sql.toString());
        }*/


        //如果是问答、填空则解析为正确答案
        if (question.getQuestiontype()==1||question.getQuestiontype()==2) {

        } else if (question.getQuestiontype()==3||question.getQuestiontype()==4) {
            if (optionData==null || isrightData==null ||
                    optionData.trim().length()<1||isrightData.trim().length()<1) {
                je.setMsg("异常错误,未获取到选择题选项或答案!");
                response.getWriter().print(je.toJSON());
                return;
            }
            String[]optionArray=optionData.split("#sz#");
            String[]isrightArray=isrightData.split(",");

            if(optionArray!=null&&isrightArray!=null&&optionArray.length!=isrightArray.length){
                je.setMsg("选项与答案数量不一致!");
                response.getWriter().print(je.toJSON());
                return;
            }

            if(optionArray.length>26){
                je.setMsg("选项过多!");
                response.getWriter().print(je.toJSON());
                return;
            }
            QuestionOption delete = new QuestionOption();
            delete.setQuestionid(question.getQuestionid());
            sql=new StringBuilder();
            objList = this.questionOptionManager.getDeleteSql(delete, sql);
            if (objList != null && sql != null && sql.length() > 0) {
                objListArray.add(objList);
                sqlListArray.add(sql.toString());
            }

            for (int i = 0; i < optionArray.length; i++) {
                //选择题选项
                QuestionOption qoption = new QuestionOption();
                qoption.setQuestionid(this.questionOptionManager.getNextId(true));
                //题号ABCD
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

                //添加操作日志
             /*   sql = new StringBuilder();
                objList=this.questionManager.getAddOperateLog(this.logined(request).getRef(),"j_question_option",nextref.toString()
                        ,null,null,"UPDATE>ADD","修改并添加试题选项:content:"+optionArray[i],sql);
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
     * 恢复回收站中的试题
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

}


