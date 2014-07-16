package com.school.control.activity;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.GradeManager;
import com.school.manager.RoleManager;
import com.school.manager.SubjectManager;
import com.school.manager.UserManager;
import com.school.manager.impl.activity.ActivityManager;
import com.school.manager.impl.activity.ActivitySiteManager;
import com.school.manager.impl.activity.ActivityUserManager;
import com.school.manager.impl.activity.SiteManager;
import com.school.manager.inter.IUserManager;
import com.school.manager.inter.activity.IActivityManager;
import com.school.manager.inter.activity.IActivitySiteManager;
import com.school.manager.inter.activity.IActivityUserManager;
import com.school.manager.inter.activity.ISiteManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.ClassYearInfo;
import com.school.entity.GradeInfo;
import com.school.entity.UserInfo;
import com.school.entity.activity.ActivityInfo;
import com.school.entity.activity.ActivitySiteInfo;
import com.school.entity.activity.ActivityUserInfo;
import com.school.entity.activity.SiteInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
/**
 * @author ������
 * @date 2013-03-27
 * @description �Controller
 */
@Controller
@RequestMapping(value="/activity")
public class ActivityController extends BaseController<ActivityInfo>{
	private ISiteManager siteManager;
    private IActivityManager activityManager;
    private IActivityUserManager activityUserManager;
    private IActivitySiteManager activitySiteManager;
    private IUserManager userManager;

    public ActivityController(){
        this.userManager=this.getManager(UserManager.class);
        this.siteManager=this.getManager(SiteManager.class);
        this.activityManager=this.getManager(ActivityManager.class);
        this.activityUserManager=this.getManager(ActivityUserManager.class);
        this.activitySiteManager=this.getManager(ActivitySiteManager.class);
    }
	/**
	 * @author ������
	 * @date 2013-03-27
	 * @description ת����б�ҳ��
	 * */
	@RequestMapping(params="m=list",method=RequestMethod.GET) 
	public ModelAndView toActivityList(HttpServletRequest request,ModelAndView mp )throws Exception{		
		return new ModelAndView("/activity/activity_list");  
	} 
	
	/**
	 * @author ������
	 * @date 2013-03-27
	 * @description ת����б�ҳ��
	 * */
	@RequestMapping(params="m=adminlist",method=RequestMethod.GET) 
	public ModelAndView toActivityAdminList(HttpServletRequest request,ModelAndView mp )throws Exception{		
		return new ModelAndView("/activity/activity_admin_list");  
	} 
	
	/**
	 * @author ������
	 * @date 2013-03-27
	 * @description ת������ҳ��
	 * */
	@RequestMapping(params="m=toadd",method=RequestMethod.GET) 
	public ModelAndView toAdd(HttpServletRequest request,ModelAndView mp )throws Exception{		
		List siteList = this.siteManager.getListForSelect();
		request.setAttribute("siteList", siteList);
		return new ModelAndView("/activity/add/activity_add");
	} 
	
	/**
	 * @author ������
	 * @date 2013-03-27
	 * @description ��ȡ��б�
	 * */
	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST)
	public void getActivityList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		PageResult p = new PageResult();
		p.setOrderBy(" ac.c_time desc");
		ActivityInfo obj = this.getParameter(request, ActivityInfo.class);
		obj.setUserid(this.logined(request).getRef());
		List<ActivityInfo> objlist = this.activityManager.getList(obj, p);
		if(objlist!=null){
			for(ActivityInfo a:objlist){
				List<SiteInfo> list = this.siteManager.getListByActivity(a.getRef().toString());
				a.setSiteinfo(list);				
			}
		}
		p.setList(objlist);
		je.setType("success");
		je.setPresult(p);
		response.getWriter().print(je.toJSON());		
	}
	/**
	 * @author ������
	 * @date 2013-03-27
	 * @description ��ȡ����Ա��б�
	 * */
	@RequestMapping(params="m=ajaxadminlist",method=RequestMethod.POST)
	public void getActivityAdminList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		PageResult p = new PageResult();
		p.setOrderBy(" ac.c_time desc");
		ActivityInfo obj = this.getParameter(request, ActivityInfo.class);
		obj.setUserid(this.logined(request).getRef());
		List<ActivityInfo> objlist = this.activityManager.getAdminList(obj, p);
		if(objlist!=null){
			for(ActivityInfo a:objlist){
				List<SiteInfo> list = this.siteManager.getListByActivity(a.getRef().toString());
				a.setSiteinfo(list);				
			}
		}
		p.setList(objlist);
		je.setType("success");
		je.setPresult(p);
		response.getWriter().print(je.toJSON());		
	}
	/**
	 * @author ������
	 * @date 2013-03-27
	 * @description ��ӻ
	 * */
	@RequestMapping(params="m=ajaxsave",method=RequestMethod.POST)
	public void doSubmitAddActivity(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		String userRef = this.logined(request).getRef();
		//���ֵ�����ݼ�����
		List<String> sqlArrayList = new ArrayList<String>();
		//���sql�ļ���
		List<List<Object>> objArrayList = new ArrayList<List<Object>>();
	    //ƴsql�Ķ���ʹ��ֵ�ö���
		List<Object> objList = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		List<Object> objList2 = new ArrayList<Object>();
		StringBuilder sql2 = new StringBuilder();
		List<Object> objList3 = new ArrayList<Object>();
		StringBuilder sql3 = new StringBuilder();
		//��ȡ�������uuid
		String uuid = UUID.randomUUID().toString();
		//��ȡǰ̨�����������ݣ����ڻʵ����
		ActivityInfo activityinfo = this.getParameter(request, ActivityInfo.class);
		if(activityinfo.getTmpsite().trim().length()<1
		   ||activityinfo.getAtname().trim().length()<1
		   ||activityinfo.getBegintimestring().trim().length()<1
		   ||activityinfo.getEndtimestring().trim().length()<1
		   ||activityinfo.getEstimationnum()==null){
			if(activityinfo.getIssign()==1&&activityinfo.getTmpuid().trim().length()<1){
				je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
				response.getWriter().print(je.toJSON());
				return;
			}				
		}
		//������ǰ̨�����������ݸ�ֵ
		activityinfo.setRef(uuid);
		activityinfo.setUserid(userRef);
		//������ͳ��ع�ϵ��ʵ����󣬲���ֵ
		ActivitySiteInfo activitySiteInfo = new ActivitySiteInfo();
		activitySiteInfo.setSiteIds(activityinfo.getTmpsite());
		activitySiteInfo.setUserid(userRef);
		activitySiteInfo.setActivityid(uuid);
		//������û����󲢸�ֵ
		ActivityUserInfo auserinfo = new ActivityUserInfo();
		auserinfo.setUserids(activityinfo.getTmpuid());
		auserinfo.setActivityref(uuid);
		auserinfo.setCuserid(userRef);
		
		//��ȡsql�ʹ��ֵ�ö���
		objList = this.activityManager.getSaveSql(activityinfo, sql);
		//��ӵ�������
		if(objList!=null&&sql!=null){
			sqlArrayList.add(sql.toString());
			objArrayList.add(objList);
		}
		//��ȡsql�ʹ��ֵ�ö���
		objList2 = this.activitySiteManager.getSaveSql(activitySiteInfo, sql2);
		//��ӵ�������
		if(objList2!=null&&sql2!=null){
			sqlArrayList.add(sql2.toString());
			objArrayList.add(objList2);
		}
		//��ȡsql�ʹ��ֵ�ö���
		if(auserinfo.getUserids().length()>0){
			objList3 = this.activityUserManager.getSaveSql(auserinfo, sql3);
			//��ӵ�������
			if(objList3!=null&&sql3!=null){
				sqlArrayList.add(sql3.toString());
				objArrayList.add(objList3);
			}
		}	
		
		Boolean b = this.activityManager.doExcetueArrayProc(sqlArrayList, objArrayList);
		if(b){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());  
		
	}
	
	/**
	 * @author ������
	 * @date 2013-03-27
	 * @description ת����û�ѡ��ҳ��
	 * */
	@RequestMapping(params="m=userlist",method=RequestMethod.GET) 
	public ModelAndView toUserList(HttpServletRequest request,ModelAndView mp )throws Exception{		
		List roleList = this.getManager(RoleManager.class).getList(null, null);
		List gradeList = this.getManager(GradeManager.class).getList(null, null);
		List subList =  this.getManager(SubjectManager.class).getList(null, null);
		request.setAttribute("roleList", roleList);
		request.setAttribute("gradeList", gradeList);
		request.setAttribute("subList", subList);
		return new ModelAndView("/activity/activity_add_selectuser");  
	}
	
	/**
	 * @author ������
	 * @date 2013-03-27
	 * @description ��ӻ��ȡѡ���û��б�
	 * */
	@RequestMapping(params="m=ajaxuserlist",method=RequestMethod.POST)
	public void getUserList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		String roleid = request.getParameter("roleid");
		String clsid = request.getParameter("clsid");
		String cname = request.getParameter("cname");
		String grade = request.getParameter("grade");
		String username = request.getParameter("username");
		PageResult p = this.getPageResultParameter(request);
		List<UserInfo> list = this.userManager.getUserForSelect(roleid, clsid, cname, grade,username, p);
		p.setList(list);
		je.setType("success");
		je.setPresult(p);
		response.getWriter().print(je.toJSON());
	}
	
	/**
	 * @author ������
	 * @date 2013-03-27
	 * @description �鿴���س�ͻ���
	 * */
	@RequestMapping(params="m=cksite",method=RequestMethod.POST)
	public void getCkSite(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		int siteId = Integer.parseInt(request.getParameter("siteId").toString());
		if(siteId<1){
				je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
				response.getWriter().print(je.toJSON());
				return;
		}
		List<ActivityInfo> list = this.activityManager.getActivityListBySite(siteId);
		je.setObjList(list);
		je.setType("success");
		response.getWriter().print(je.toJSON());
	}
	
	/**
	 * @author ������
	 * @date 2013-04-16
	 * @description ת���޸�ҳ��
	 * */
	@RequestMapping(params="m=toupd",method=RequestMethod.GET)
	public ModelAndView toUpdate(HttpServletRequest request,ModelAndView mp)throws Exception{
		JsonEntity je =new JsonEntity();
		String ref = request.getParameter("ref");
		if(ref.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			je.getAlertMsgAndBack();
			return null;
			}
		List<ActivityInfo> list = this.activityManager.getActivityByRef(ref);
		ActivityInfo activity = list.get(0);
		//���ݻ��ʶ��ȡ����
		List<SiteInfo> site = this.siteManager.getListByActivity(activity.getRef());
		activity.setSiteinfo(site);
		//���ݻ��ʶ��ȡ�û�
		ActivityUserInfo obj = new ActivityUserInfo();
		obj.setActivityref(activity.getRef());
		List<ActivityUserInfo> user = this.activityUserManager.getList(obj, null);
		activity.setActivityuserinfo(user);
		request.setAttribute("activity", activity);
		List siteList = this.siteManager.getListForSelect();
		request.setAttribute("siteList", siteList);
		return new ModelAndView("/activity/activity_update");  
	}
	
	/**
	 * @author ������
	 * @date 2013-04-16
	 * @description ת����ϸҳ��
	 * */
	@RequestMapping(params="m=todetail",method=RequestMethod.GET)
	public ModelAndView toDetail(HttpServletRequest request,ModelAndView mp)throws Exception{
		JsonEntity je =new JsonEntity();
		String ref = request.getParameter("ref");
		if(ref.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			je.getAlertMsgAndBack();
			return null;
		}
		List<ActivityInfo> list = this.activityManager.getActivityByRef(ref);
		ActivityInfo activity = list.get(0);
		//���ݻ��ʶ��ȡ����
		List<SiteInfo> site = this.siteManager.getListByActivity(activity.getRef());
		activity.setSiteinfo(site);
		//���ݻ��ʶ��ȡ�û�
		ActivityUserInfo obj = new ActivityUserInfo();
		obj.setActivityref(activity.getRef());
		List<ActivityUserInfo> user = this.activityUserManager.getList(obj, null);
		activity.setActivityuserinfo(user);
		//��ȡ�ȷ�ϲμ���
		obj.setAttitude(0);
		List<ActivityUserInfo> yuser = this.activityUserManager.getList(obj, null);
		//��ȡ����ܲμ���
		obj.setAttitude(1);
		List<ActivityUserInfo> yornuser = this.activityUserManager.getList(obj, null);
		//��ȡ����μ���
		obj.setAttitude(2);
		List<ActivityUserInfo> nuser = this.activityUserManager.getList(obj, null);
		request.setAttribute("activity", activity);
		request.setAttribute("yuser", yuser);
		request.setAttribute("yornuser", yornuser);
		request.setAttribute("nuser", nuser);
		//List siteList = this.siteManager.getListForSelect();
		//request.setAttribute("siteList", siteList);
		return new ModelAndView("/activity/activity_details");  
	}
	
	/**
	 * @author ������
	 * @date 2013-04-16
	 * @description �޸�
	 * */
	@RequestMapping(params="m=ajaxupd",method=RequestMethod.POST)
	public void toUpdate(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		String userRef = this.logined(request).getRef();
		//���ֵ�����ݼ�����
		List<String> sqlArrayList = new ArrayList<String>();
		//���sql�ļ���
		List<List<Object>> objArrayList = new ArrayList<List<Object>>();
	    //ƴsql�Ķ���ʹ��ֵ�ö���
		List<Object> objList = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		List<Object> objList2 = new ArrayList<Object>();
		StringBuilder sql2 = new StringBuilder();
		List<Object> objList3 = new ArrayList<Object>();
		StringBuilder sql3 = new StringBuilder();
		
		//��ȡǰ̨�����������ݣ����ڻʵ����
		ActivityInfo activityinfo = this.getParameter(request, ActivityInfo.class);	
		if(activityinfo.getRef().trim().length()<1){
						je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
						response.getWriter().print(je.toJSON());
						return;
				}
		//��ȡ�������uuid
		String uuid = activityinfo.getRef();
		
		
		//������ͳ��ع�ϵ��ʵ����󣬲���ֵ
		ActivitySiteInfo activitySiteInfo = new ActivitySiteInfo();
		activitySiteInfo.setSiteIds(activityinfo.getTmpsite());
		activitySiteInfo.setUserid(userRef);
		activitySiteInfo.setActivityid(uuid);
		//������û����󲢸�ֵ
		ActivityUserInfo auserinfo = new ActivityUserInfo();
		auserinfo.setUserids(activityinfo.getTmpuid());
		auserinfo.setActivityref(uuid);
		auserinfo.setCuserid(userRef);
		auserinfo.setUserid(null);
		

		//��ɾ������ع�ϵ�ͻ�û���ϵ�����
		Boolean i = this.activitySiteManager.doDelete(activitySiteInfo);
		Boolean s = this.activityUserManager.doDelete(auserinfo);
		
		//��ȡsql�ʹ��ֵ�ö���
		objList = this.activityManager.getUpdateSql(activityinfo, sql);
		//��ӵ�������
		if(objList!=null&&sql!=null){
			sqlArrayList.add(sql.toString());
			objArrayList.add(objList);
		}
		//��ȡsql�ʹ��ֵ�ö���
		objList2 = this.activitySiteManager.getSaveSql(activitySiteInfo, sql2);
		//��ӵ�������
		if(objList2!=null&&sql2!=null){
			sqlArrayList.add(sql2.toString());
			objArrayList.add(objList2);
		}
		//��ȡsql�ʹ��ֵ�ö���
		if(auserinfo.getUserids().length()>0){
			objList3 = this.activityUserManager.getSaveSql(auserinfo, sql3);
			//��ӵ�������
			if(objList3!=null&&sql3!=null){
				sqlArrayList.add(sql3.toString());
				objArrayList.add(objList3);
			}
		}
		Boolean b = this.activityManager.doExcetueArrayProc(sqlArrayList, objArrayList);
		if(i&&s&&b){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());  
	}
	/**
	 * @author ������
	 * @date 2013-04-16
	 * @description ɾ��
	 * */
	@RequestMapping(params="m=dodel",method=RequestMethod.POST)
	public void doDelete(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		String ref = request.getParameter("ref");
		if(ref==null||ref==""){
			je.setMsg(UtilTool.msgproperty.getProperty("REF_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}else{
			ActivityUserInfo user = new ActivityUserInfo();
			user.setActivityref(ref);
			ActivitySiteInfo site = new ActivitySiteInfo();
			site.setActivityid(ref);
			
			//��ɾ������ع�ϵ�ͻ�û���ϵ
			Boolean i = this.activitySiteManager.doDelete(site);
			Boolean s = this.activityUserManager.doDelete(user);
			
			//ɾ���
			ActivityInfo obj = new ActivityInfo();
			obj.setRef(ref);
			Boolean b = this.activityManager.doDelete(obj);
			if(i&&s&&b){
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
				je.setType("success");
			}else{  
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			}
			response.getWriter().print(je.toJSON());  
		}
	}
	
	/**
	 * @author ������
	 * @date 2013-04-16
	 * @description �������
	 * */
	@RequestMapping(params="m=changestate",method=RequestMethod.POST)
	public void doChangeStatee(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		ActivityInfo obj = this.getParameter(request, ActivityInfo.class);
		if(obj.getRef().trim().length()<1||obj.getState()<0){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		Boolean b = this.activityManager.doUpdate(obj);
		if(b){			
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());
	}
}
