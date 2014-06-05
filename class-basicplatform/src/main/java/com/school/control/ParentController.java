package com.school.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.ParentManager;
import com.school.manager.UserManager;
import com.school.manager.inter.IParentManager;
import com.school.manager.inter.IUserManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.school.control.base.BaseController;
import com.school.entity.ParentInfo;
import com.school.entity.UserInfo;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;

@Controller
@RequestMapping(value="/parent")
public class ParentController extends BaseController<ParentInfo> {
    private IUserManager userManager;
    private IParentManager parentManager;
    public ParentController(){
        this.userManager=this.getManager(UserManager.class);
        this.parentManager=this.getManager(ParentManager.class);
    }
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
