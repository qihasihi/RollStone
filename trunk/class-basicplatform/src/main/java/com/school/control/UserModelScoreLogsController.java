package com.school.control;

import com.school.control.base.BaseController;
import com.school.entity.UserModelScoreLogsInfo;
import com.school.manager.inter.resource.score.IUserModelScoreLogsManager;
import com.school.manager.inter.resource.score.IUserModelTotalScoreManager;
import com.school.manager.resource.score.UserModelScoreLogsManager;
import com.school.manager.resource.score.UserModelTotalScoreManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by yuechunyang on 14-2-22.
 */
@Controller
@RequestMapping("/userModelScoreLogs")
public class UserModelScoreLogsController extends BaseController<UserModelScoreLogsInfo> {
    private IUserModelScoreLogsManager userModelScoreLogsManager;
    private IUserModelTotalScoreManager userModelTotalScoreManager;

    public UserModelScoreLogsController(){
        this.userModelScoreLogsManager=this.getManager(UserModelScoreLogsManager.class);
        this.userModelTotalScoreManager=this.getManager(UserModelTotalScoreManager.class);
    }
    /**
     * 跳转到管理员公告列表
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=userScoreDetail",method=RequestMethod.GET)
    public ModelAndView toUserScoreDetail(HttpServletRequest request,ModelMap mp )throws Exception{
        Integer userid = this.logined(request).getUserid();
        List<Map<String,Object>> scoreList = this.userModelScoreLogsManager.getUserScoreDetails(userid);
        mp.put("scoreList",scoreList);
        List<Map<String,Object>> totalScoreList = this.userModelTotalScoreManager.getUserScoreInfo(userid);
        if(totalScoreList!=null)
            mp.put("userScoreInfo",totalScoreList.get(0));
        return new ModelAndView("/user/user_score_details",mp);
    }
    /**
     * ajax获取用户积分详情
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=userShareResourceScore",method= RequestMethod.POST)
    public void getUserShareResourceScore(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String month = request.getParameter("selMonth").toString();
        String scoretypeid=request.getParameter("scoretypeid");
        PageResult presult = this.getPageResultParameter(request);
        if(month==null||month.length()<=0){
            je.getAlertMsg();
            return;
        }
        UserModelScoreLogsInfo obj = new UserModelScoreLogsInfo();
        obj.setScoreTypeId(Integer.parseInt(scoretypeid));
        obj.setSelmonth(month);
        obj.setUserId(this.logined(request).getUserid());
        List<UserModelScoreLogsInfo> objList = this.userModelScoreLogsManager.getUserResourceScoreList(obj,presult);
        presult.setList(objList);
        je.setPresult(presult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }
}
