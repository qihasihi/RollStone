package com.school.control;

import com.school.control.base.BaseController;
import com.school.entity.ClassYearInfo;
import com.school.entity.TermInfo;
import com.school.manager.inter.IClassYearManager;
import com.school.manager.inter.ITermManager;
import com.school.util.JsonEntity;
import com.school.util.MD5_NEW;
import com.school.util.UtilTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value="/term")
public class TermController extends BaseController<TermInfo> {
    @Autowired
    private IClassYearManager classYearManager;
    @Autowired
    private ITermManager termManager;


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

    /**
     * 初始化向导修改学期
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=addLzxTerm",method=RequestMethod.POST)
    public void lzxAddTerm(HttpServletRequest request,HttpServletResponse response)throws Exception{
//        lzx_school_id ：乐之行分校id
//        class_year_name :学年名称  varchar(100) 格式：2012~2013学年
//        class_year_value :学年值 varchar(100) 格式：2012~2013
//        year_begin_time :学年开始时间 datetime 格式：2014-03-03
//        year_end_time :学年结束时间 datetime  格式为：2014-03-03
//        one_term_name :第一学期名称 varchar(200)
//        two_term_name：第二学期名称 varchar(200)
//        one_term_begin_time：第一学期开始时间 datetime 格式：2014-03-03
//        one_term_end_time：第一学期结束时间 datetime 格式：2014-03-03
//        two_term_begin_time：第二学期开始时间 datetime 格式：2014-03-03
//        two_term_end_time：第二学期结束时间 datetime 格式：2014-03-03
        String lzxschoolid=request.getParameter("lzx_school_id");
        String classyearname=java.net.URLDecoder.decode(request.getParameter("class_year_name"),"UTF-8");
        String classyearvalue=java.net.URLDecoder.decode(request.getParameter("class_year_value"),"UTF-8");
        String yearbegintime=request.getParameter("year_begin_time");
        String yearendtime=request.getParameter("year_end_time");
        String onetermname=java.net.URLDecoder.decode(request.getParameter("one_term_name"),"UTF-8");
        String twotermname=java.net.URLDecoder.decode(request.getParameter("two_term_name"),"UTF-8");
        String onetermbegintime=request.getParameter("one_term_begin_time");
        String onetermendtime=request.getParameter("one_term_end_time");
        String twotermbegintime=request.getParameter("two_term_begin_time");
        String twotermendtime=request.getParameter("two_term_end_time");
        String timestamp=request.getParameter("timestamp");
        String checkcode=request.getParameter("checkcode");
        String md5key = lzxschoolid+classyearname+classyearvalue+yearbegintime+yearendtime+onetermname+twotermname+onetermbegintime+onetermendtime+twotermbegintime+twotermendtime+timestamp;
        String key = MD5_NEW.getMD5ResultCode(md5key);
        if(!checkcode.trim().equals(key)){
            response.getWriter().print("[{\"status\":\"error\",\"message\":\"验证失败，非法登录\"}]");
            return;
        }
        //验证是否已经添加
        ClassYearInfo obj = new ClassYearInfo();
        obj.setClassyearvalue(classyearvalue);
        List<ClassYearInfo> ciList = this.classYearManager.getList(obj,null);
        if(ciList!=null&&ciList.size()>0){
            response.getWriter().print("[{\"status\":\"error\",\"message\":\"当前学年已添加，请勿重复添加\"}]");
            return;
        }
        //存放值得数据集集合
        List<String> sqlArrayList = new ArrayList<String>();
        //存放sql的集合
        List<List<Object>> objArrayList = new ArrayList<List<Object>>();
        TermInfo ti = new TermInfo();
        ClassYearInfo ci = new ClassYearInfo();
        //添加学年信息
        ci.setClassyearname(classyearname);
        ci.setClassyearvalue(classyearvalue);
        ci.setBtime(UtilTool.StringConvertToDate(yearbegintime));
        ci.setEtime(UtilTool.StringConvertToDate(yearendtime));
        StringBuilder cisql =new StringBuilder();
        List<Object> ciObj =  this.classYearManager.getSaveSql(ci,cisql);
        sqlArrayList.add(cisql.toString());
        objArrayList.add(ciObj);
        //添加学期信息
        StringBuilder tisql;
        List<Object> tiObj=new ArrayList<Object>();
        String ref;
        //第一学期
        ref= UUID.randomUUID().toString();
        ti.setRef(ref);
        ti.setTermname(onetermname);
        ti.setSemesterbegindatestring(onetermbegintime);
        ti.setSemesterenddatestring(onetermendtime);
        ti.setYear(classyearvalue);
        tisql = new StringBuilder();
        tiObj = this.termManager.getSaveSql(ti,tisql);
        sqlArrayList.add(tisql.toString());
        objArrayList.add(tiObj);
        //第二学期
        ref=UUID.randomUUID().toString();
        ti.setRef(ref);
        ti.setTermname(twotermname);
        ti.setSemesterbegindatestring(twotermbegintime);
        ti.setSemesterenddatestring(twotermendtime);
        ti.setYear(classyearvalue);
        tisql = new StringBuilder();
        tiObj = this.termManager.getSaveSql(ti,tisql);
        sqlArrayList.add(tisql.toString());
        objArrayList.add(tiObj);

        Boolean b = this.termManager.doExcetueArrayProc(sqlArrayList, objArrayList);
        StringBuilder sb = new StringBuilder();
        if(b){
            sb.append("[{\"status\":\"success\"}]");
        }else{
            sb.append("[{\"status\":\"error\",\"message\":\"添加失败，请稍后重试\"}]");
        }
        response.getWriter().print(sb.toString());
    }


    /**
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=termList",method = RequestMethod.POST)
    public void getTermList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        TermInfo tm=this.getParameter(request,TermInfo.class);
        List<TermInfo> tmList=this.termManager.getList(tm,null);
        JsonEntity je=new JsonEntity("success","");
        je.setObjList(tmList);
        response.getWriter().println(je.toJSON());
    }

}
