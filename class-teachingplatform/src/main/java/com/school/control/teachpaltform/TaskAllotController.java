package com.school.control.teachpaltform;

import com.school.control.base.BaseController;
import com.school.entity.ClassInfo;
import com.school.entity.DictionaryInfo;
import com.school.entity.teachpaltform.*;
import com.school.entity.teachpaltform.interactive.TpTopicInfo;
import com.school.manager.inter.teachpaltform.IQuestionManager;
import com.school.manager.inter.teachpaltform.IQuestionOptionManager;
import com.school.manager.inter.teachpaltform.ITpTaskAllotManager;
import com.school.manager.inter.teachpaltform.ITpTaskManager;
import com.school.manager.teachpaltform.QuestionManager;
import com.school.manager.teachpaltform.QuestionOptionManager;
import com.school.manager.teachpaltform.TpTaskAllotManager;
import com.school.manager.teachpaltform.TpTaskManager;
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

@Controller
@RequestMapping(value="/taskallot")
public class TaskAllotController extends BaseController<TpTaskAllotInfo>{
    private ITpTaskManager tpTaskManager;
    private ITpTaskAllotManager tpTaskAllotManager;
    private IQuestionManager questionManager;
    private IQuestionOptionManager questionOptionManager;
    public TaskAllotController(){
        this.tpTaskManager=this.getManager(TpTaskManager.class);
        this.tpTaskAllotManager=this.getManager(TpTaskAllotManager.class);
        this.questionManager=this.getManager(QuestionManager.class);
        this.questionOptionManager=this.getManager(QuestionOptionManager.class);
    }
    /**
     * 获取任务对象详情
     * @throws Exception
     */
    @RequestMapping(params="m=ajaxTaskAllot",method=RequestMethod.POST)
    public void ajaxTaskList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        je.setType("success");
        String taskid=request.getParameter("taskid");
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo t=new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(t,null);
        if(taskList==null||taskList.size()<1){
            je.setMsg("当前任务已不存在或删除，请刷新页面重试!");
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo task=taskList.get(0);
        TpTaskAllotInfo ta=new TpTaskAllotInfo();
        ta.setTaskid(Long.parseLong(taskid));
        List<TpTaskAllotInfo>taskAllotInfoList=this.tpTaskAllotManager.getList(ta,null);
        je.getObjList().add(taskAllotInfoList);

        if(task.getTasktype().intValue()==3){
            QuestionInfo q=new QuestionInfo();
            q.setQuestionid(task.getTaskvalueid());
            List<QuestionInfo>questionInfoList=this.questionManager.getList(q,null);
            je.getObjList().add(questionInfoList);

            QuestionOption questionOption=new QuestionOption();
            questionOption.setQuestionid(task.getTaskvalueid());
            PageResult p=new PageResult();
            p.setPageNo(0);
            p.setPageSize(0);
            p.setOrderBy("u.option_type");
            List<QuestionOption>questionOptionList=this.questionOptionManager.getList(questionOption,p);
            je.getObjList().add(questionOptionList);
        }
        response.getWriter().print(je.toJSON());
    }
}
