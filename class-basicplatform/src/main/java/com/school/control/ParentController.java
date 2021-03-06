package com.school.control;

import com.school.control.base.BaseController;
import com.school.entity.ParentInfo;
import com.school.entity.UserInfo;
import com.school.manager.inter.IParentManager;
import com.school.manager.inter.IUserManager;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value="/parent")
public class ParentController extends BaseController<ParentInfo> {
    @Autowired
    private IUserManager userManager;
    @Autowired
    private IParentManager parentManager;

	@RequestMapping(params="m=associatechild",method=RequestMethod.POST)
	public void associateChild(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		JsonEntity je = new JsonEntity();
		ParentInfo parentInfo = this.getParameter(request, ParentInfo.class);
		UserInfo child = new UserInfo();
		child.setUsername(request.getParameter("child_username"));
		child.setPassword(request.getParameter("child_password"));
		if(child.getUsername()==null||child.getUsername().equals("")
				||child.getPassword()==null||child.getPassword().equals("")){
			je.setMsg(UtilTool.msgproperty.getProperty("USER_LOGIN_INFO_UNCOMPLETE"));
			je.setType("error");
		}else{
			child = userManager.doLogin(child);
			if(child != null){
				parentInfo.setCusername(child.getUsername());
				System.out.print(parentManager.doUpdate(parentInfo));
				je.setType("success");
			}else{
				je.setMsg(UtilTool.msgproperty.getProperty("USER_ASSOCIATE_CHILD_FAILED"));
				je.setType("error");
			}
		}
		response.getWriter().print(je.toJSON());
	}
	
}
