package com.school.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.ClassYearManager;
import com.school.manager.TermManager;
import com.school.manager.inter.IClassYearManager;
import com.school.manager.inter.ITermManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.ClassYearInfo;
import com.school.entity.TermInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;

@Controller
@RequestMapping(value="/term")
public class TermController extends BaseController<TermInfo> {
	private IClassYearManager classYearManager;
    private ITermManager termManager;
    public TermController(){
        this.classYearManager=this.getManager(ClassYearManager.class);
        this.termManager=this.getManager(TermManager.class);
    }

	/**
	 * 进入学期管理
	 * @param request
	 * @param mp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="m=list",method=RequestMethod.GET) 
	public ModelAndView toTermList(HttpServletRequest request,ModelMap mp)throws Exception{
		List<ClassYearInfo> yearList=this.classYearManager.getList(null, null);
		mp.put("yearList", yearList); 
		return new ModelAndView("/term/list");  
	}
	
	/**
	 * 获取列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST)
	public void getList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity();
//		String year=request.getParameter("year");
//		TermInfo t=new TermInfo();
//		if(year!=null&&year.trim().length()>0)
//			t.setYear(year);
//		PageResult presult=this.getPageResultParameter(request);
//		List<TermInfo>termList=this.termManager.getList(t, presult);
//		List<ClassYearInfo> yearList=this.classYearManager.getList(null, null);
		List<Map<String, Object>> objList = new ArrayList<Map<String,Object>>();
		objList = this.termManager.getYearTerm();
		
		je.setObjList(objList);
		je.setType("success");
		response.getWriter().print(je.toJSON());
	}
	
	/**
	 * 添加学期
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxsave",method=RequestMethod.POST)
	public void doSave(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity();
		TermInfo t=this.getParameter(request, TermInfo.class);
		if(t.getTermname()==null||t.getYear()==null
		   ||t.getSemesterbegindate()==null||t.getSemesterenddate()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		if(this.termManager.doSave(t)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());	
	}
	
	
	/**
	 * 去修改
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=toupd",method=RequestMethod.POST)
	public void toUpdate(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity();
		TermInfo t=this.getParameter(request, TermInfo.class);
		if(t.getRef()==null||t.getRef().trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		} 
		List<TermInfo>tList=this.termManager.getList(t, null);
		je.setObjList(tList); 
		je.setType("success");
		response.getWriter().print(je.toJSON());	
	}
	
	/**
	 * 修改学期
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=modify",method=RequestMethod.POST)
	public void doUpdate(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity();
		TermInfo t=this.getParameter(request, TermInfo.class);
		if(t.getRef()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return; 
		} 
		if(this.termManager.doUpdate(t)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());	
	}
	
	/**
	 * 修改学期
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=del",method=RequestMethod.POST)
	public void doDelete(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity();
		TermInfo t=this.getParameter(request, TermInfo.class);
		if(t.getRef()==null||t.getRef().trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return; 
		} 
		if(this.termManager.doDelete(t)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success"); 
		}else{ 
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		} 
		response.getWriter().print(je.toJSON());	
	}
	
	/**
	 * 初始化向导修改学期
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=setupmodify",method=RequestMethod.POST)
	public void dosetupUpdate(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity();
		String ref1 = request.getParameter("ref1");
		String ref2 = request.getParameter("ref2");
		String btime1 = request.getParameter("btime1");
		String btime2 = request.getParameter("btime2");
		String etime1 = request.getParameter("etime1");
		String etime2 = request.getParameter("etime2");
		if(ref1==null||ref2==null||
				btime1==null||btime2==null||
				etime1==null||etime2==null){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return; 
		} 
		TermInfo tobj = null;
		//存放值得数据集集合
		List<String> sqlArrayList = new ArrayList<String>();
		//存放sql的集合
		List<List<Object>> objArrayList = new ArrayList<List<Object>>();
		//存放第一学期数据
		tobj = new TermInfo();
		tobj.setRef(ref1);
		tobj.setSemesterbegindate(UtilTool.StringConvertToDate(btime1));
		tobj.setSemesterenddate(UtilTool.StringConvertToDate(etime1));
		 //拼sql的对象和存放值得对象
		List<Object> objList;
		StringBuilder sql ;	
		objList = new ArrayList<Object>();
		sql = new StringBuilder();
		
		objList = this.termManager.getUpdateSql(tobj, sql);
		objArrayList.add(objList);
		sqlArrayList.add(sql.toString());
		//下面存放第二学期数据
		tobj = new TermInfo();
		tobj.setRef(ref2);
		tobj.setSemesterbegindate(UtilTool.StringConvertToDate(btime2));
		tobj.setSemesterenddate(UtilTool.StringConvertToDate(etime2));
		
		objList = new ArrayList<Object>();
		sql = new StringBuilder();
		
		objList = this.termManager.getUpdateSql(tobj, sql);
		
		objArrayList.add(objList);
		sqlArrayList.add(sql.toString());
		
		Boolean b = this.termManager.doExcetueArrayProc(sqlArrayList, objArrayList);
		
		
		if(b){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");

		}else{
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());	
	}
}
