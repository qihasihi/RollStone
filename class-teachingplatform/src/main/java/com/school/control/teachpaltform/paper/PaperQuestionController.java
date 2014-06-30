package com.school.control.teachpaltform.paper;

import com.school.control.base.BaseController;
import com.school.entity.teachpaltform.QuestionOption;
import com.school.entity.teachpaltform.paper.PaperQuestion;
import com.school.manager.inter.teachpaltform.IQuestionOptionManager;
import com.school.manager.inter.teachpaltform.paper.IPaperQuestionManager;
import com.school.manager.teachpaltform.QuestionOptionManager;
import com.school.manager.teachpaltform.paper.PaperQuestionManager;
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

/**
 * Created by zhengzhou on 14-6-30.
 */
@Controller
@RequestMapping(value = "/paperquestion")
public class PaperQuestionController extends BaseController<PaperQuestion> {
    private IPaperQuestionManager paperQuestionManager;
    private IQuestionOptionManager questionOptionManager;
    public PaperQuestionController(){
        this.paperQuestionManager=this.getManager(PaperQuestionManager.class);
        this.questionOptionManager=this.getManager(QuestionOptionManager.class);
    }
    /**
     * 进入测评系统刚开测试。
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=testPaper",method = RequestMethod.GET)
    public ModelAndView testPaper(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        String paperid=request.getParameter("paperid");
        JsonEntity jsonEntity=new JsonEntity();
        if(paperid==null||paperid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        //得到当前的所有问题
        PaperQuestion pq=new PaperQuestion();
        pq.setPaperid(Long.parseLong(paperid));
        List<PaperQuestion> pqList=this.paperQuestionManager.getList(pq,null);
        //保存入ModelMap中
        if(pqList!=null&&pqList.size()>0){
            for (PaperQuestion pqentity:pqList){
                if(pqentity!=null&&pqentity.getQuestionid()!=null){
                    QuestionOption qo=new QuestionOption();
                    qo.setQuestionid(pqentity.getQuestionid());
                    pqentity.getQuestioninfo().setQuestionOption(this.questionOptionManager.getList(qo,null));
                }
            }
        }
        mp.put("quesList",pqList);
        mp.put("quesSize",pqList.size());
        mp.put("paperid",paperid);
        return new ModelAndView("/teachpaltform/paper/stuTest",mp);
    }

    /**
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doSaveTestPaper",method=RequestMethod.POST)
    public void doSaveTestPaper(HttpServletRequest request,HttpServletResponse response)throws Exception{

    }

}
