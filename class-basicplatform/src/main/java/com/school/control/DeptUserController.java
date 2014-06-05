package com.school.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.DeptUserManager;
import com.school.manager.inter.IDeptUserManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.school.control.base.BaseController;
import com.school.entity.DeptUser;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
@Controller
@RequestMapping(value="/deptuser")
public class DeptUserController extends BaseController<DeptUser>{
    private IDeptUserManager deptUserManager;
    public DeptUserController(){
        this.deptUserManager=this.getManager(DeptUserManager.class);
    }
	/**
	 * AJAX查询
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=getListByProperty",method=RequestMethod.POST)	
	public void getListBy(HttpServletRequest request,HttpServletResponse response) throws Exception{
		DeptUser dpTmp=this.getParameter(request, DeptUser.class);		
		List<DeptUser> dpList=this.deptUserManager.getList(dpTmp, null);
		JsonEntity jeEntity=new JsonEntity();
		jeEntity.setMsg("查询成功!");
		jeEntity.setType("success");
		jeEntity.setObjList(dpList);
		response.getWriter().print(jeEntity.toJSON());
	}
	/**
	 * 首页AJAX查询
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=indexGetListPage",method=RequestMethod.POST)	
	public void getListPageBy(HttpServletRequest request,HttpServletResponse response) throws Exception{
		DeptUser dpTmp=this.getParameter(request, DeptUser.class);
		JsonEntity jeEntity=new JsonEntity();
		String depttypeid=request.getParameter("depttypeid");
		if(depttypeid==null||depttypeid.trim().length()<1||!UtilTool.isNumber(depttypeid)){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		dpTmp.getDeptinfo().setTypeid(Integer.parseInt(depttypeid.trim()));
		PageResult presult=this.getPageResultParameter(request);
	//	dpTmp.setUserref(this.logined(request).getRef());	
		dpTmp.setOtheruserref(this.logined(request).getRef());
		List<DeptUser> dpList=this.deptUserManager.getList(dpTmp, presult);
		presult.getList().add(dpList);
		presult.getList().add(depttypeid);
		jeEntity.setPresult(presult);
		jeEntity.setMsg("查询成功!");
		jeEntity.setType("success");		
		response.getWriter().print(jeEntity.toJSON());
	}
	
}
