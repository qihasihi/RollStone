package com.school.control;

import com.school.control.base.BaseController;
import com.school.entity.*;
import com.school.manager.ZTestUserInfoManager;
import com.school.manager.inter.ITestManager;
import com.school.manager.inter.IZTestUserInfoManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller

@RequestMapping(value="/ztestuserinfo")
public class ZTestUserInfoController extends BaseController<ZTestUserInfo> {
    private IZTestUserInfoManager testUserManager;
    public ZTestUserInfoController(){
        this.testUserManager=this.getManager(ZTestUserInfoManager.class);
    }
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
