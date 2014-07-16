package com.school.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.GradeManager;
import com.school.manager.inter.IGradeManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.ClassYearInfo;
import com.school.entity.GradeInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;

@Controller
@RequestMapping(value="/grade") 
public class GradeController extends BaseController<GradeInfo> {
	private IGradeManager gradeManager;
    public GradeController(){
        this.gradeManager=this.getManager(GradeManager.class);
    }
	@RequestMapping(params="m=list",method=RequestMethod.GET) 
	public ModelAndView toGradeList(HttpServletRequest request,ModelAndView mp )throws Exception{
		return new ModelAndView("/grade/list");
	}
	
	/**
	 * 获取List
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST)
	public void AjaxGetList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		GradeInfo gradeinfo = this.getParameter(request, GradeInfo.class);
		PageResult pageresult = this.getPageResultParameter(request);
		List<GradeInfo> gradeList =gradeManager.getList(gradeinfo, pageresult);
		pageresult.setList(gradeList);
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
		GradeInfo gradeinfo = this.getParameter(request, GradeInfo.class);
		if(gradeinfo.getGradeid()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("GRADE_ID_EMPTY")); 
			response.getWriter().print(je.toJSON());
			return;
		}
		List<GradeInfo> gradeList = gradeManager.getList(gradeinfo, null);
		je.setObjList(gradeList); 
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
		GradeInfo gradeinfo = this.getParameter(request, GradeInfo.class);
		if(gradeinfo.getGradename()==null||
				gradeinfo.getGradename().trim().length()<1||
				gradeinfo.getGradevalue()==null||gradeinfo.getGradevalue().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR")); 
			response.getWriter().print(je.toJSON());
			return;
		}
		//已存在当前数据 无法添加
		List<GradeInfo>gradeList=this.gradeManager.getList(gradeinfo, null);
		if(gradeList!=null&&gradeList.size()>0){
			je.setMsg(UtilTool.msgproperty.getProperty("DATA_EXISTS"));
			response.getWriter().print(je.toJSON());
			return;
		}
		if(gradeManager.doSave(gradeinfo)){
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
		GradeInfo gradeinfo = this.getParameter(request, GradeInfo.class);
		if(gradeinfo.getGradename()==null||
				gradeinfo.getGradeid()==null||
				gradeinfo.getGradevalue()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		if(gradeManager.doUpdate(gradeinfo)){
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
		GradeInfo gradeinfo = this.getParameter(request, GradeInfo.class);
		if(gradeinfo.getGradeid()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("GRADE_ID_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}
		if(gradeManager.doDelete(gradeinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());    
	}  
}
