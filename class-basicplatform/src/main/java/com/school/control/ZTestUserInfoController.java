package com.school.control;

import com.school.control.base.BaseController;
import com.school.entity.ZTestUserInfo;
import com.school.manager.inter.IZTestUserInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller

@RequestMapping(value="/ztestuserinfo")
public class ZTestUserInfoController extends BaseController<ZTestUserInfo> {
    @Autowired
    private IZTestUserInfoManager testUserManager;

	/**
	 * 得到用户列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=getuserlist",method=RequestMethod.GET)
	public  ModelAndView getUserList(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
//List<ZTestUserInfo> ztestuserlist=subjectManager.getList();
        List<ZTestUserInfo> ztestuserlist=this.testUserManager.getList();
        mp.put("ztestuserlist", ztestuserlist);
        String type=request.getParameter("type");
        // System.out.println("type:"+type+this.testUserManager.getNextId("seq_question",null));
        return new ModelAndView("/ztestuserlist",mp);
    }

	
}
