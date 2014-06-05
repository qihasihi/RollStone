package com.school.control.activity;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.impl.activity.ActivityManager;
import com.school.manager.impl.activity.SiteManager;
import com.school.manager.inter.activity.ISiteManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.activity.ActivityInfo;
import com.school.entity.activity.SiteInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
/**
 * @author 岳春阳
 * @date 2013-03-27
 * @description 活动场地Controller
 */
@Controller
@RequestMapping(value="/activitysite")
public class SiteController extends BaseController<SiteInfo>{
    private ISiteManager siteManager;
    public SiteController(){
        this.siteManager=getManager(SiteManager.class);
    }

	@RequestMapping(params="m=list",method=RequestMethod.GET) 
	public ModelAndView toSiteList(HttpServletRequest request,ModelAndView mp )throws Exception{		
		return new ModelAndView("/activitysite/site_list");  
	}
	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST) 
	public void getSiteList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		PageResult p = new PageResult();
		SiteInfo siteinfo = this.getParameter(request, SiteInfo.class);
		p.setOrderBy("at.c_time desc"); 
		List<SiteInfo> siteList=this.siteManager.getList(siteinfo, p);
		p.setList(siteList);
		JsonEntity je = new JsonEntity();
		je.setType("success");
		je.setPresult(p);
		response.getWriter().print(je.toJSON());
	}
	@RequestMapping(params="m=ajaxsave",method=RequestMethod.POST) 
	public void doAjaxSave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		SiteInfo obj = this.getParameter(request, SiteInfo.class);
		if(obj.getSitename().trim().length()<1
				||obj.getSitecontain()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("REF_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}
		obj.setUserid(this.logined(request).getRef());
		Boolean b = this.siteManager.doSave(obj);
		if(b){			
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());
	}
	@RequestMapping(params="m=ajaxdel",method=RequestMethod.POST) 
	public void doAjaxDelete(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		SiteInfo siteinfo = this.getParameter(request, SiteInfo.class);
		if(siteinfo.getRef()<=0){
			je.setMsg(UtilTool.msgproperty.getProperty("REF_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}else{
			Boolean b = this.siteManager.doDelete(siteinfo);
			if(b){
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
				je.setType("success");
			}else{  
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			}
			response.getWriter().print(je.toJSON());    
		}
	}
	
	@RequestMapping(params="m=toupdate",method=RequestMethod.POST) 
	public void toUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		SiteInfo siteinfo = this.getParameter(request, SiteInfo.class);
		if(siteinfo.getRef()<=0){
			je.setMsg(UtilTool.msgproperty.getProperty("REF_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}else{
			List<SiteInfo> list = this.siteManager.getList(siteinfo, null);
			je.setObjList(list);
			je.setType("success");
			response.getWriter().print(je.toJSON());
		}
	}
	@RequestMapping(params="m=todetail",method=RequestMethod.GET) 
	public ModelAndView toDetail(HttpServletRequest request,ModelAndView mp) throws Exception{
		JsonEntity je = new JsonEntity();
		int ref = Integer.parseInt(request.getParameter("ref").toString());
		if(ref<1){
			je.setMsg(UtilTool.msgproperty.getProperty("REF_EMPTY"));
			je.getAlertMsgAndBack();
			return null;
		}
		SiteInfo siteinfo = new SiteInfo();
		siteinfo.setRef(ref);
		List<SiteInfo> list = this.siteManager.getList(siteinfo, null);
		SiteInfo obj = list.get(0);	
		List<ActivityInfo> activityList = this.getManager(ActivityManager.class).getActivityListBySite(obj.getRef());
		request.setAttribute("siteinfo", obj);
		request.setAttribute("activityList", activityList);
		return new ModelAndView("/activitysite/site_details");  
	}
	@RequestMapping(params="m=doupdate",method=RequestMethod.POST) 
	public void doUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		SiteInfo siteinfo = this.getParameter(request, SiteInfo.class);
		if(siteinfo.getRef()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("REF_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}
		Boolean b = this.siteManager.doUpdate(siteinfo);
		if(b){			
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());
	}
}
