package com.school.control.activity;

import com.school.control.base.BaseController;
import com.school.entity.activity.ActivitySiteInfo;
import com.school.manager.inter.activity.IActivitySiteManager;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author ������
 * @date 2013-03-27
 * @description �Controller
 */
@Controller
@RequestMapping(value="/activitysiterelation")
public class ActivitySiteController extends BaseController<ActivitySiteInfo> {
    @Autowired
    private IActivitySiteManager activitySiteManager;

	/**
	 * @author ������
	 * @date 2013-04-03
	 * @description ���ӻ
	 * */
	@RequestMapping(params="m=ajaxsave",method=RequestMethod.POST)
	public void doSubmitActivitySite(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		ActivitySiteInfo obj = this.getParameter(request, ActivitySiteInfo.class);
		if(obj.getActivityid().trim().length()<1
				||obj.getSiteIds().trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		obj.setUserid(this.logined(request).getRef());
		Boolean b = this.activitySiteManager.doSave(obj);
		if(b){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());  
	}
}
