package com.school.control.activity;

import com.school.control.base.BaseController;
import com.school.entity.activity.ActivityUserInfo;
import com.school.manager.inter.activity.IActivityUserManager;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author 岳春阳
 * @date 2013-03-27
 * @description 活动Controller
 */
@Controller
@RequestMapping(value="/activityuser")
public class ActivityUserController extends BaseController<ActivityUserInfo>{
    @Autowired
    private IActivityUserManager activityUserManager;

	/**
	 * @author 岳春阳
	 * @date 2013-04-17
	 * @description 添加用户报名
	 * */
	@RequestMapping(params="m=ajaxsave",method=RequestMethod.POST)
	public void doSubmitActivityUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		ActivityUserInfo obj = this.getParameter(request, ActivityUserInfo.class);	
		String activityid = request.getParameter("activityid");
		if(activityid.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		obj.setCuserid(this.logined(request).getRef());
		obj.setActivityref(activityid);
		obj.setUserids(this.logined(request).getRef());
		Boolean b = this.activityUserManager.doSave(obj);
		if(b){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());  
	}
	/**
	 * @author 岳春阳
	 * @date 2013-04-17
	 * @description 删除活动用户报名
	 * */
	@RequestMapping(params="m=ajaxdel",method=RequestMethod.POST)
	public void doDelActivityUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		String activityid = request.getParameter("activityid");
		ActivityUserInfo obj = new ActivityUserInfo();
		obj.setActivityref(activityid);
		obj.setUserid(this.logined(request).getRef());
		if(activityid==null||activityid==""){
			je.setMsg(UtilTool.msgproperty.getProperty("REF_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}else{
			Boolean b = this.activityUserManager.doDelete(obj);
			if(b){
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
				je.setType("success");
			}else{  
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			}
			response.getWriter().print(je.toJSON());    
		}
	} 
	/**
	 * @author 岳春阳
	 * @date 2013-04-17
	 * @description 参加、不参加
	 * */
	@RequestMapping(params="m=ajaxupd",method=RequestMethod.POST)
	public void doUpdActivityUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		String activityid = request.getParameter("activityid");
		int attitude = Integer.parseInt(request.getParameter("attitude").toString());
		ActivityUserInfo obj = new ActivityUserInfo();
		obj.setActivityref(activityid);
		obj.setUserid(this.logined(request).getRef());
		obj.setAttitude(attitude);
		if(activityid==null||activityid==""){
			je.setMsg(UtilTool.msgproperty.getProperty("REF_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}else{
			Boolean b = this.activityUserManager.doUpdate(obj);
			if(b){
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
				je.setType("success");
			}else{  
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			}
			response.getWriter().print(je.toJSON());    
		}
	} 
}
