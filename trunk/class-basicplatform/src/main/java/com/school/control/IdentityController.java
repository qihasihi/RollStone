package com.school.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.IdentityManager;
import com.school.manager.inter.IIdentityManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.school.control.base.BaseController;
import com.school.entity.IdentityInfo;
import com.school.entity.UserInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;

@Controller
@RequestMapping(value="/identity")
public class IdentityController extends BaseController<IdentityInfo>{
	private IIdentityManager identityManager;
    public IdentityController(){
        this.identityManager=this.getManager(IdentityManager.class);
    }
	/**
	 * 根据身份查询角色
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "m=ajaxlist", method = RequestMethod.POST)
	public void GetAjaxUserList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		String identityname=request.getParameter("identityname");
		String isadmin=request.getParameter("isadmin");
		
		IdentityInfo i=new IdentityInfo();
		i.setIdentityname(identityname);
		if(isadmin!=null&&isadmin.trim().length()>0)
			i.setIsadmin(Integer.parseInt(isadmin));
		List<IdentityInfo>identityList=this.identityManager.getList(i, null);
		je.setObjList(identityList);
		je.setType("success");
		response.getWriter().print(je.toJSON());
	}
}
