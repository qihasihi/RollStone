package com.school.control.personalspace;

import com.school.control.base.BaseController;
import com.school.entity.personalspace.PsFriendGroupInfo;
import com.school.entity.personalspace.PsUserFriend;
import com.school.manager.inter.personalspace.IPsFriendGroupManager;
import com.school.manager.inter.personalspace.IPsUserFriendManager;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
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
 * Created by qihaishi on 14-6-4.
 */
@Controller
@RequestMapping(value="/userfriend")
public class PsUserFriendController extends BaseController<PsUserFriend> {
    @Autowired
    private IPsUserFriendManager psUserFriendManager;
    @Autowired
    private IPsFriendGroupManager psFriendGroupManager;



    /**
     * 好友管理页面
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=manageFriend",method= RequestMethod.GET)
    public ModelAndView toManageFriend(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        PsFriendGroupInfo groupInfo=new PsFriendGroupInfo();
        groupInfo.setCuserid(this.logined(request).getUserid());
        List<PsFriendGroupInfo>groupList=this.psFriendGroupManager.getList(groupInfo,null);

        mp.put("groupList",groupList);
        return new ModelAndView("/personalspace/friend/manage",mp);
    }



    /**
     * 根据分组查找好友
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=loadFriendByGroupId",method= RequestMethod.POST)
    public void getFriendByGroupId(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity jeEntity=new JsonEntity();
        PsUserFriend userFriend=this.getParameter(request, PsUserFriend.class);
        if(userFriend==null||userFriend.getGroupid()==null){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
        userFriend.setUserid(this.logined(request).getUserid());
        List<PsUserFriend>friendList=this.psUserFriendManager.getMyFriendList(userFriend,null);
        jeEntity.setObjList(friendList);
        jeEntity.setType("success");
        response.getWriter().print(jeEntity.toJSON());
    }

    /**
     * 删除分组，将人员移动我的好友下
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=delGroup",method= RequestMethod.POST)
    public void deleteGroup(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity jeEntity=new JsonEntity();
        PsUserFriend userFriend=this.getParameter(request, PsUserFriend.class);
        if(userFriend==null||userFriend.getGroupid()==null){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();


        //修改组
        List<PsUserFriend>friendList=this.psUserFriendManager.getMyFriendList(userFriend,null);
        if(friendList!=null&&friendList.size()>0){
            for(PsUserFriend ufriend:friendList){
                PsUserFriend upd=new PsUserFriend();
                upd.setRef(ufriend.getRef());
                upd.setGroupid(new Long(0));
                sql=new StringBuilder();
                objList=this.psUserFriendManager.getUpdateSql(upd,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }
        PsFriendGroupInfo groupInfo=new PsFriendGroupInfo();
        groupInfo.setGroupid(userFriend.getGroupid());
        sql=new StringBuilder();
        objList=this.psFriendGroupManager.getDeleteSql(groupInfo,sql);
        if(objList!=null&&sql!=null){
            objListArray.add(objList);
            sqlListArray.add(sql.toString());
        }

        if(this.psUserFriendManager.doExcetueArrayProc(sqlListArray,objListArray)){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            jeEntity.setType("success");
        }else
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(jeEntity.toJSON());
    }


    /**
     * 删除好友
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=delFriend",method= RequestMethod.POST)
    public void deleteFriend(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity jeEntity=new JsonEntity();
        PsUserFriend userFriend=this.getParameter(request, PsUserFriend.class);
        if(userFriend==null||userFriend.getFriendid()==null){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
        userFriend.setUserid(this.logined(request).getUserid());
        List<PsUserFriend>friendList=this.psUserFriendManager.getMyFriendList(userFriend,null);
        if(friendList==null||friendList.size()<1){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jeEntity.toJSON());
            return;
        }else {
            List<Object>objList=null;
            StringBuilder sql=null;
            List<List<Object>>objListArray=new ArrayList<List<Object>>();
            List<String>sqlListArray=new ArrayList<String>();

            PsUserFriend delete=new PsUserFriend();
            delete.setRef(friendList.get(0).getRef());
            sql=new StringBuilder();
            objList=this.psUserFriendManager.getDeleteSql(delete,sql);
            if(objList!=null&&sql!=null){
                objListArray.add(objList);
                sqlListArray.add(sql.toString());
            }

            if(this.psUserFriendManager.doExcetueArrayProc(sqlListArray,objListArray)){
                jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                jeEntity.setType("success");
            }else
                jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }

        response.getWriter().print(jeEntity.toJSON());
    }



    /**
     * 发好友申请
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=sendApply",method= RequestMethod.POST)
    public void applyFriend(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity jeEntity=new JsonEntity();
        PsUserFriend userFriend=this.getParameter(request, PsUserFriend.class);
        if(userFriend==null||userFriend.getFriendid()==null){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
        userFriend.setUserid(this.logined(request).getUserid());
        userFriend.setRef(this.psUserFriendManager.getNextId(true));
        if(this.psUserFriendManager.doSave(userFriend)){
            jeEntity.setType("success");
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
        }else
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(jeEntity.toJSON());
    }
}
