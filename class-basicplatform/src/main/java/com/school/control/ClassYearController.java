package com.school.control;

import com.school.control.base.BaseController;
import com.school.entity.ClassYearInfo;
import com.school.manager.inter.IClassYearManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping(value="/classyear")
public class ClassYearController extends BaseController<ClassYearInfo> {
   @Autowired
    private IClassYearManager classYearManager;

	
	@RequestMapping(params="m=list",method=RequestMethod.GET) 
	public ModelAndView toClassyearList(HttpServletRequest request,ModelAndView mp )throws Exception{
		return new ModelAndView("/classyear/list"); 
	}
	
	/**
	 * 获取List
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST)
	public void AjaxGetList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ClassYearInfo classyearinfo = this.getParameter(request, ClassYearInfo.class);
		PageResult pageresult = this.getPageResultParameter(request);
		List<ClassYearInfo> classyearList =classYearManager.getList(classyearinfo, pageresult);
		pageresult.setList(classyearList);
		JsonEntity je = new JsonEntity();
		je.setType("success");
		je.setPresult(pageresult);
		response.getWriter().print(je.toJSON()); 
	}
	
	
	/**
	 * 去修改
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=toupd",method=RequestMethod.POST) 
	public void toUpdateClassyear(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		ClassYearInfo classyearinfo = this.getParameter(request, ClassYearInfo.class);
		if(classyearinfo.getClassyearid()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("CLASS_YEAR_ID_EMPTY")); 
			response.getWriter().print(je.toJSON());
			return;
		}
		List<ClassYearInfo> classyearList = classYearManager.getList(classyearinfo, null);
		je.setObjList(classyearList); 
		je.setType("success");
		response.getWriter().print(je.toJSON());  
	}
	
	/**
	 * 添加
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxsave",method=RequestMethod.POST)
	public void doSubmitAddClassyear(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		ClassYearInfo classyearinfo = this.getParameter(request, ClassYearInfo.class);
		if(classyearinfo.getClassyearname()==null||
				classyearinfo.getClassyearname().trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("CLASS_YEAR_NAME_EMPTY")); 
			response.getWriter().print(je.toJSON());
			return;
		}
		if(classyearinfo.getClassyearvalue()==null||
				classyearinfo.getClassyearvalue().trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("CLASS_YEAR_VALUE_EMPTY")); 
			response.getWriter().print(je.toJSON());
			return;
		}
		if(classyearinfo.getBtime()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("BTIME_EMPTY")); 
			response.getWriter().print(je.toJSON());
			return;
		}
		if(classyearinfo.getEtime()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("ETIME_EMPTY")); 
			response.getWriter().print(je.toJSON());
			return;
		}
		//已存在当前数据 无法添加
		List<ClassYearInfo>classyearList=this.classYearManager.getList(classyearinfo, null);
		if(classyearList!=null&&classyearList.size()>0){
			je.setMsg(UtilTool.msgproperty.getProperty("DATA_EXISTS"));
			response.getWriter().print(je.toJSON());
			return;
		}
		if(classYearManager.doSave(classyearinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());  
	}  
	 
	
	/**
	 * 修改
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params="m=modify",method=RequestMethod.POST)
	public void doSubmitUpdateRole(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		ClassYearInfo classyearinfo = this.getParameter(request, ClassYearInfo.class);
		if(classyearinfo.getClassyearname()==null||
				classyearinfo.getClassyearid()==null||
				classyearinfo.getClassyearvalue()==null||
				classyearinfo.getBtime()==null||
				classyearinfo.getEtime()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		if(classYearManager.doUpdate(classyearinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success"); 
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());  
	}  
	
	/**
	 * 删除
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params="m=del",method=RequestMethod.POST)
	public void doDeleteRole(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		ClassYearInfo classyearinfo = this.getParameter(request, ClassYearInfo.class);
		if(classyearinfo.getClassyearid()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("CLASS_YEAR_ID_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}
		if(classYearManager.doDelete(classyearinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());    
	}  
}
