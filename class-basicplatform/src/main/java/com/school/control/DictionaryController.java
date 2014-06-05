package com.school.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.DictionaryManager;
import com.school.manager.inter.IDictionaryManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.DictionaryInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;


@Controller
@RequestMapping(value="/dictionary")
public class DictionaryController extends BaseController<DictionaryInfo>{
	private IDictionaryManager dictionaryManager;
    public DictionaryController(){
        this.dictionaryManager=this.getManager(DictionaryManager.class);
    }
	
	@RequestMapping(params="m=list",method=RequestMethod.GET) 
	public ModelAndView toDictionaryList(HttpServletRequest request,ModelAndView mp )throws Exception{
		return new ModelAndView("/dictionary/list");
	}
	 
	/**
	 * AjaxList
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST) 
	public void toDictionaryList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		DictionaryInfo dictionaryInfo = this.getParameter(request, DictionaryInfo.class);
		PageResult pageresult = this.getPageResultParameter(request);
		pageresult.setOrderBy("c_time desc");  
		List<DictionaryInfo> dictionaryList = dictionaryManager.getList(dictionaryInfo, pageresult);
		pageresult.setList(dictionaryList);
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
	public void toUpdateDictionary(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		DictionaryInfo dictionaryInfo = this.getParameter(request, DictionaryInfo.class);
		if(dictionaryInfo.getRef()==null){
			response.getWriter().print(je.toJSON());
			return;
		}
		List<DictionaryInfo> dictionaryList = dictionaryManager.getList(dictionaryInfo, null);
		je.setObjList(dictionaryList); 
		je.setType("success");
		response.getWriter().print(je.toJSON());  
	}
	
	/**
	 * 添加
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxsave",method=RequestMethod.POST)
	public void doSubmitAddDictionary(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		DictionaryInfo dictionaryInfo = this.getParameter(request, DictionaryInfo.class);
		dictionaryInfo.setRef(dictionaryManager.getNextId());
		if(dictionaryManager.doSave(dictionaryInfo)){
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
	public void doSubmitUpdateDictionary(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		DictionaryInfo dictionaryInfo = this.getParameter(request, DictionaryInfo.class);
		if(dictionaryInfo.getRef()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		if(dictionaryManager.doUpdate(dictionaryInfo)){
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
	public void doDeleteDictionary(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		DictionaryInfo dictionaryInfo = this.getParameter(request, DictionaryInfo.class);
		if(dictionaryInfo.getRef()==null){
			response.getWriter().print(je.toJSON());
			return;
		}
		if(dictionaryManager.doDelete(dictionaryInfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());    
	}  
	
}
