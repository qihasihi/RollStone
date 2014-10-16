package com.school.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.MyInfoUserManager;
import com.school.manager.inter.IMyInfoUserManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.MyInfoUserInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
@Controller
@RequestMapping(value = "/myinfouser")
public class MyInfoUserController extends BaseController<MyInfoUserInfo>{
    private IMyInfoUserManager myInfoUserManager;
    public MyInfoUserController(){
        this.myInfoUserManager=this.getManager(MyInfoUserManager.class);
    }

	/**
	 * 进入消息列表页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="m=list",method=RequestMethod.GET)
	public ModelAndView toMyInfoList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity jeEntity=new JsonEntity();
		//得到相应的参数
		MyInfoUserInfo myUserEntity=this.getParameter(request, MyInfoUserInfo.class);
		//验证参数
		if(myUserEntity==null||myUserEntity.getMsgid()==null||myUserEntity.getMsgname()==null){
			jeEntity.setMsg("错误，参数异常!");
			response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
			return null;
		}		
		String msgname=new String(myUserEntity.getMsgname().getBytes("8859_1"),"UTF-8");//
		//System.out.println(msgname);
		request.setAttribute("msgName", msgname);
		//直接进入页面。
		return new ModelAndView("/myinfo/list");		
	}	
	
	/**
	 * 查询消息列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST)
	public void toAjaxList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity jeEntity=new JsonEntity();
		//得到相应的参数
		MyInfoUserInfo myUserEntity=this.getParameter(request, MyInfoUserInfo.class);
		//验证参数
		if(myUserEntity==null||myUserEntity.getMsgid()==null){
			jeEntity.setMsg("错误，参数异常!");
			response.getWriter().print(jeEntity.toJSON());
			return;
		}
		//得到分页参数
		PageResult presult=this.getPageResultParameter(request);
		presult.setOrderBy("mu.c_time desc");  
		//开始查询  
		MyInfoUserInfo searchMyInfoUser=new MyInfoUserInfo();
		searchMyInfoUser.setMsgid(myUserEntity.getMsgid());
		searchMyInfoUser.setUserref(this.logined(request).getRef());
		List<MyInfoUserInfo> myUserList=this.myInfoUserManager.getList(searchMyInfoUser, presult);
		presult.setList(myUserList);
		jeEntity.setPresult(presult);  
		jeEntity.setType("success");
		response.getWriter().print(jeEntity.toJSON());
	}	
}
