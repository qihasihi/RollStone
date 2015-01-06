package com.school.control.resource;

import com.school.control.base.BaseController;
import com.school.entity.resource.ResourceReport;
import com.school.manager.inter.resource.IResourceReportManager;
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
@RequestMapping(value = "/resourcereport")
public class ResourceReportController extends BaseController<ResourceReport> {
    @Autowired
    private IResourceReportManager resourceReportManager;

	/**
	 * 添加报告
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "m=addresourcereport", method = RequestMethod.POST)
	public void addResourceReport(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResourceReport rrTmp = this.getParameter(request, ResourceReport.class);
		JsonEntity jEntity = new JsonEntity();

		if (rrTmp.getResid() == null) {
			jEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jEntity.toJSON());
			return;
		}
		
		if (rrTmp.getContent() == null || rrTmp.getContent().trim().length() < 1) {
			jEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jEntity.toJSON());
			return;
		}
		rrTmp.setUserid(this.logined(request).getUserid().intValue());
		if (this.resourceReportManager.doSave(rrTmp)) {
			jEntity.setMsg(UtilTool.msgproperty.getProperty("SAVE_SUCCESS"));
			jEntity.setType("success");
		} else
			jEntity.setMsg(UtilTool.msgproperty.getProperty("SAVE_ERROR"));
		response.getWriter().print(jEntity.toJSON());
	}
	
	@RequestMapping(params="m=getreportlist",method=RequestMethod.POST)
	public void getCheckTeacher(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity jeEntity=new JsonEntity();
		ResourceReport rr=this.getParameter(request, ResourceReport.class);
		PageResult presult = this.getPageResultParameter(request);
		if (rr.getResid() == null) {
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jeEntity.toJSON());
			return;
		}
		//进行查询
		List<ResourceReport> rrList=this.resourceReportManager.getList(rr,presult);
		jeEntity.setObjList(rrList);
		jeEntity.setType("success");
		response.getWriter().print(jeEntity.toJSON());
	}
}
