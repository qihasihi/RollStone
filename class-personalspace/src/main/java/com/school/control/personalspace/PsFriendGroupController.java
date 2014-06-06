package com.school.control.personalspace;

import com.school.control.base.BaseController;
import com.school.entity.personalspace.PsFriendGroupInfo;
import com.school.manager.inter.personalspace.IPsFriendGroupManager;
import com.school.manager.personalspace.PsFriendGroupManager;
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
 * Created by qihaishi on 14-6-4.
 */
@Controller
@RequestMapping(value="/friendgroup")
public class PsFriendGroupController extends BaseController<PsFriendGroupInfo> {
    private IPsFriendGroupManager psFriendGroupManager;

    public PsFriendGroupController(){
        this.psFriendGroupManager=this.getManager(PsFriendGroupManager.class);
    }

    /**
     * ajax获取好友分组
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=ajaxGroupList",method= RequestMethod.POST)
    public void loadGroup(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        PsFriendGroupInfo groupInfo=this.getParameter(request,PsFriendGroupInfo.class);
        groupInfo.setCuserid(this.logined(request).getUserid());
        List<PsFriendGroupInfo>groupList=this.psFriendGroupManager.getList(groupInfo,null);
        je.setObjList(groupList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params="m=addGroup",method= RequestMethod.POST)
    public void addGroup(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        PsFriendGroupInfo groupInfo=this.getParameter(request,PsFriendGroupInfo.class);
        if(groupInfo==null||groupInfo.getGroupname()==null||groupInfo.getGroupname().trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        groupInfo.setCuserid(this.logined(request).getUserid());
        groupInfo.setGroupid(this.psFriendGroupManager.getNextId(true));
        if(this.psFriendGroupManager.doSave(groupInfo)){
            je.setType("success");
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }


    @RequestMapping(params="m=modify",method= RequestMethod.POST)
    public void modify(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        PsFriendGroupInfo groupInfo=this.getParameter(request,PsFriendGroupInfo.class);
        if(groupInfo==null||groupInfo.getGroupname()==null||groupInfo.getGroupname().trim().length()<1
                ||groupInfo.getGroupid()==null||groupInfo.getGroupid().toString().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        groupInfo.setCuserid(this.logined(request).getUserid());
        groupInfo.setGroupid(groupInfo.getGroupid());
        if(this.psFriendGroupManager.doUpdate(groupInfo)){
            je.setType("success");
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }
}
