package com.school.control;

import com.school.control.base.BaseController;
import com.school.entity.RoleColumnRightInfo;
import com.school.manager.inter.IRoleColumnRightManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value="roleColumnRight")
public class RoleColumnRightController extends BaseController<RoleColumnRightInfo>  {
    @Autowired
    public IRoleColumnRightManager roleColumnRightManager;

	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST)
	public void ajaxList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		RoleColumnRightInfo roleColumnInfo = this.getParameter(request, RoleColumnRightInfo.class);
        PageResult p=new PageResult();
        p.setPageNo(0);
        p.setPageSize(0);
        p.setOrderBy("u.column_id");
		List<RoleColumnRightInfo>rList=this.roleColumnRightManager.getList(roleColumnInfo, null); 
		JsonEntity je = new JsonEntity();
		je.setType("success");
		je.setObjList(rList); 
		response.getWriter().print(je.toJSON()); 
	}
}
