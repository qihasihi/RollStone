package com.school.control;

import com.school.control.base.BaseController;
import com.school.entity.DeptUser;
import com.school.manager.inter.IDeptUserManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Controller
@RequestMapping(value="/deptuser")
public class DeptUserController extends BaseController<DeptUser>{
    @Autowired
    private IDeptUserManager deptUserManager;

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
