package com.school.control.ethosaraisal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.*;
import com.school.manager.ethosaraisal.StuEthosManager;
import com.school.manager.inter.*;
import com.school.manager.inter.ethosaraisal.IStuEthosManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import com.school.control.base.BaseController;
import com.school.entity.ClassUser;
import com.school.entity.GradeInfo;
import com.school.entity.StudentInfo;
import com.school.entity.TermInfo;
import com.school.entity.ClassInfo;
import com.school.entity.ethosaraisal.StuEthosInfo;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
/**
 * @author 岳春阳
 * @date 2013-04-28
 * @description 学生校风Controller
 */
@Controller
@RequestMapping(value="/stuethos")
public class StuEthosController extends BaseController<StuEthosInfo> {
    public StuEthosController(){
        this.stuEthosManager=this.getManager(StuEthosManager.class);
        this.gradeManager=this.getManager(GradeManager.class);
        this.termManager=this.getManager(TermManager.class);
        this.classManager=this.getManager(ClassManager.class);
        this.studentManager=this.getManager(StudentManager.class);
        this.operaterexcelmanager=this.getManager(OperateExcelManager.class);
    }
    //学生校风定义区域
    private IGradeManager gradeManager;
    private IStuEthosManager stuEthosManager;
    private ITermManager termManager;
    private IClassManager classManager;
    private IStudentManager studentManager;
    private IOperateExcelManager operaterexcelmanager;

	/**
	 * @author 岳春阳
	 * @date 2013-04-28
	 * @description 学生校风添加
	 * */
	@RequestMapping(params="m=ajaxsave",method=RequestMethod.POST)
	public void doSubmitSave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		StuEthosInfo obj = this.getParameter(request, StuEthosInfo.class);
		if(obj!=null){
			List<StuEthosInfo> list = this.stuEthosManager.getList(obj, null);
			String uuid = UUID.randomUUID().toString();
			String userRef = this.logined(request).getRef();
			if(list.size()>0){				
				obj.setOperateid(userRef);
				obj.setRef(list.get(0).getRef());
				Boolean b = this.stuEthosManager.doUpdate(obj);
				if(b){
					je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
					je.setType("success");
				}else{  
					je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
				}
			}else{
				
				obj.setOperateid(userRef);
				obj.setRef(uuid);
				Boolean b = this.stuEthosManager.doSave(obj);
				if(b){
					je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
					je.setType("success");
				}else{  
					je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
				}
			}					
		}else{
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());  
	}
	/**
	 * @author 岳春阳
	 * @date 2013-03-27
	 * @description 转到学生校风页面
	 * */
	@RequestMapping(params="m=tolist",method=RequestMethod.GET) 
	public ModelAndView tolist(HttpServletRequest request,HttpServletResponse response,ModelAndView mp )throws Exception{
		return new ModelAndView("/ethosapraisal/admin_ethos_info");  
	}
	/**
	 * @author 岳春阳
	 * @date 2013-03-27
	 * @description 转到学生校风页面
	 * */
	@RequestMapping(params="m=list",method=RequestMethod.POST) 
	public ModelAndView toStuEthosInfo(HttpServletRequest request,HttpServletResponse response,ModelAndView mp )throws Exception{	
		JsonEntity je = new JsonEntity();
		
		String year = request.getParameter("year");
		String grade = request.getParameter("gradeName");
		int classId = Integer.parseInt(request.getParameter("classId").toString());
		String weekId = request.getParameter("weekId");
		
		if (classId <0) {
			je.setMsg("错误，系统尚未获取到您要操作的班级信息，请返回班级校风页面选择班级信息！");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		if (year == null || year.trim().length() < 1 ) {
			je.setMsg("错误，系统尚未获取您要操作的学期信息，请返回班级校风页面选择学期信息！");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		if(grade == null || grade.trim().length()<1){
			je.setMsg("错误，系统尚未获取您要操作的年级信息，请返回班级校风页面选择年级信息！");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		if (weekId == null || weekId.trim().length()<1) {
			je.setMsg("错误，系统尚未获取您要操作的学期信息，请返回班级校风页面选择学期信息！");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		
		return new ModelAndView("/ethosapraisal/stu_ethos_info");  
	} 
	/**
	 * @author 岳春阳
	 * @date 2013-04-28
	 * @description 学生校风加载
	 * */
	@RequestMapping(params="m=loadethos",method=RequestMethod.POST)
	public void getStudentEthos(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		Integer weekid = Integer.parseInt(request.getParameter("weekid").toString());
		Integer classid = Integer.parseInt(request.getParameter("classid").toString());
		if(weekid<1||classid<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		} 
		StuEthosInfo obj = new StuEthosInfo();
		obj.setClassid(classid);
		obj.setWeekid(weekid);
		List<StuEthosInfo> objList = this.stuEthosManager.getList(obj, null);
		je.setObjList(objList);
		je.setType("success");
		response.getWriter().print(je.toJSON());	
	}
	/**
	 * @author 岳春阳
	 * @date 2013-04-28
	 * @description 学生校风得分规则
	 * */
	@RequestMapping(params="m=getscore",method=RequestMethod.POST)
	public void getStudentEthosScore(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		String type = request.getParameter("type");
		if(type.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		} 
		String score = UtilTool.scoreproperty.getProperty(type);
		List<Object> list = new ArrayList<Object>();
		list.add(score);
		if(score!=""&&score!=null){
			je.setType("success");
			je.setObjList(list);
		}else{
			je.setType("error");
			je.setMsg("未查到"+type+"对应的分数");
		}
		
		
		response.getWriter().print(je.toJSON());
	}
	/**
	 * @author 岳春阳
	 * @date 2013-05-07
	 * @description 学生校风删除
	 * 
	 * */
	@RequestMapping(params="m=ajaxdel",method=RequestMethod.POST)
	public void doDeleteStudentEthos(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		String delid = request.getParameter("delid");
		if(delid.length()>0){
			String[] delids = delid.split(",");			
			//存放值得数据集集合
			List<String> sqlArrayList = new ArrayList<String>();
			//存放sql的集合
			List<List<Object>> objArrayList = new ArrayList<List<Object>>();
			StringBuilder sqlbuilder;
			List<Object> objlist;
			StuEthosInfo obj;
			for(int i = 0; i<delids.length;i++){				
				sqlbuilder = new StringBuilder();
				objlist = new ArrayList<Object>();
				obj = new StuEthosInfo();
				obj.setRef(delids[i]);
				objlist=this.stuEthosManager.getDeleteSql(obj, sqlbuilder);
				sqlArrayList.add(sqlbuilder.toString());
				objArrayList.add(objlist);
			}
			Boolean b = this.stuEthosManager.doExcetueArrayProc(sqlArrayList, objArrayList);
			if(b){
				je.setType("success");
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));				
			}else{  
				je.setType("error");
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			}
		}else{
			je.setType("error");
			je.setMsg(UtilTool.msgproperty.getProperty("REF_EMPTY"));
		}
		response.getWriter().print(je.toJSON());
	}
	/**
	 * @author 岳春阳
	 * @date 2013-03-27
	 * @description 转到校风统计
	 * */
	@RequestMapping(params="m=touserstudent",method=RequestMethod.GET) 
	public ModelAndView toUserSelectClass(HttpServletRequest request,HttpServletResponse response,ModelAndView mp )throws Exception{	
		JsonEntity je = new JsonEntity();
		List<TermInfo> termList = termManager.getList(null, null);
		List<GradeInfo> gradeList = gradeManager.getList(null, null);
		request.setAttribute("termList", termList);
		request.setAttribute("gradeList", gradeList);
		
		return new ModelAndView("/ethosapraisal/user/studentview-user");  
	}
	/**
	 * @author 岳春阳
	 * @date 2013-05-07
	 * @description 查询统计学生校风考勤
	 * 
	 * */
	@RequestMapping(params="m=getethoskq",method=RequestMethod.POST)
	public void getEthosKQ(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		String termid = request.getParameter("termid");
	 	String classid= request.getParameter("classid");
	 	String stuno=request.getParameter("stuno");
	 	String ordercolumn=request.getParameter("ordercolumn");
	 	String dict=request.getParameter("dict");
	 	List<List<String>> objList = this.stuEthosManager.getEthosKQ(termid, Integer.parseInt(classid), stuno, ordercolumn, dict);
	 	je.setObjList(objList);
	 	je.setType("success");
	 	response.getWriter().print(je.toJSON());
	}
	/**
	 * @author 岳春阳
	 * @date 2013-05-07
	 * @description 查询统计学生校风违纪
	 * 
	 * */
	@RequestMapping(params="m=getethoswj",method=RequestMethod.POST)
	public void getEthosWJ(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		String termid = request.getParameter("termid");
	 	String classid= request.getParameter("classid");
	 	String stuno=request.getParameter("stuno");
	 	String ordercolumn=request.getParameter("ordercolumn");
	 	String dict=request.getParameter("dict");
	 	List<List<String>> objList = this.stuEthosManager.getEthosWJ(termid, Integer.parseInt(classid), stuno, ordercolumn, dict);
	 	je.setObjList(objList);
	 	je.setType("success");
	 	response.getWriter().print(je.toJSON());
	}
	/**
	 * @author 岳春阳
	 * @date 2013-05-07
	 * @description 查询统计学生校风综合
	 * 
	 * */
	@RequestMapping(params="m=getethoszh",method=RequestMethod.POST)
	public void getEthosZH(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		String termid = request.getParameter("termid");
	 	String classid= request.getParameter("classid");
	 	String stuno=request.getParameter("stuno");
	 	String isshowrank = request.getParameter("isshowrank");
	 	List<List<String>> objList = this.stuEthosManager.getEthosZH(termid, Integer.parseInt(classid), stuno,Integer.parseInt(isshowrank));
	 	je.setObjList(objList);
	 	je.setType("success");
	 	response.getWriter().print(je.toJSON());
	}
	
	/**-------------------------以下是导出数据-------------------------*/
	
	/**
     * @atuthor 岳春阳
     * @date 2013-05-21
	 * @description 导出学生校风信息汇总，以学生查询(考勤)
     */
	@RequestMapping(params="m=expStukq",method=RequestMethod.POST)
    public void explortUserStudentKQ(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
    	JsonEntity je=new JsonEntity();
    	String termid=request.getParameter("termid");
    	if(termid==null||termid.trim().length()<1){
    		je.setMsg("错误，系统检测到您没有正常浏览本系统！请正常浏览本系统!");
    		response.getWriter().print(je.getAlertMsgAndCloseWin());
    		return;
    	}
    	String classid=request.getParameter("classid");
    	if(classid==null||classid.trim().length()<1){
    		je.setMsg("错误，系统检测到您没有正常浏览本系统！请正常浏览本系统!");
    		response.getWriter().print(je.getAlertMsgAndCloseWin());
    		return;
    	}
    	String stuno=request.getParameter("stuno");
    	if(stuno==null||stuno.trim().length()<1)
    		stuno=null;
    	String ordercolumn=request.getParameter("ordercolumn");
    	if(ordercolumn==null||ordercolumn.trim().length()<1)
    		ordercolumn=null;
    	String direct=request.getParameter("dict");
    	if(direct==null||direct.trim().length()<1)
    		direct=null;
    	//得到列名
    	//List<List<String>> objColumn= new ArrayList<List<String>>();
    	String column = "周次,学号,姓名,病假情况,病假次数,事假情况,事假次数,早退情况,早退次数,旷课情况,旷课次数,迟到情况,迟到次数";
    	String [] columns = column.split(",");
    	
    	//得到数据
    	List<List<String>> objData= this.stuEthosManager.getEthosKQ(termid, Integer.parseInt(classid), stuno, ordercolumn, direct);

    	//导出的列明
		List<String> columnList=new ArrayList<String>();
		//if(objColumn!=null&&objColumn.size()>0){
		//	columnList=objColumn.get(0);
		//}
		for(int i = 0 ; i < columns.length; i++){
			columnList.add(columns[i]);
		}
  
    	//准备导出条件
		//准备导出条件
		String title="",filename="";
		TermInfo t=new TermInfo();
		t.setRef(termid.trim());
		List<TermInfo> tmInfo=this.termManager.getList(t, null);
		if(tmInfo!=null&&tmInfo.size()>0){
			//title=tmInfo.get(0).getYear()+""+tmInfo.get(0).getTermname() ;
		}
		
		 //--得到班级信息
		if(classid!=null&&classid.trim().length()>0){
			ClassInfo c=new ClassInfo();
			c.setClassid(Integer.parseInt(classid));
			List<ClassInfo> clsList=this.classManager.getList(c, null);
			if(clsList!=null&&clsList.size()>0){
				title+=""+clsList.get(0).getClassgrade()+"("+clsList.get(0).getClassname()+")";
			}
		}
		if(stuno!=null){
			StudentInfo s = new StudentInfo();
			s.setStuno(stuno);
			List<StudentInfo> stuList=this.studentManager.getList(s, null);
			if(stuList!=null&&stuList.size()>0){
				title+=""+stuList.get(0).getStuname()+"["+stuList.get(0).getStuno()+"]";
			}
		}
		
		title+="校风考勤统计成绩";
		filename=title;
		String flag=this.operaterexcelmanager.ExplortExcel(response, filename, columnList, objData, title, String.class, null);
		if(flag!=null){
			je.setMsg("错误，无法导出!");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
		}
    }
	/**
     * @atuthor 岳春阳
     * @date 2013-05-21
	 * @description 导出学生校风信息汇总，以学生查询(违纪)
     */
	@RequestMapping(params="m=expStuwj",method=RequestMethod.POST)
    public void explortUserStudentWJ(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
    	JsonEntity je=new JsonEntity();
    	String termid=request.getParameter("termid");
    	if(termid==null||termid.trim().length()<1){
    		je.setMsg("错误，系统检测到您没有正常浏览本系统！请正常浏览本系统!");
    		response.getWriter().print(je.getAlertMsgAndCloseWin());
    		return;
    	}
    	String classid=request.getParameter("classid");
    	if(classid==null||classid.trim().length()<1){
    		je.setMsg("错误，系统检测到您没有正常浏览本系统！请正常浏览本系统!");
    		response.getWriter().print(je.getAlertMsgAndCloseWin());
    		return;
    	}
    	String stuno=request.getParameter("stuno");
    	if(stuno==null||stuno.trim().length()<1)
    		stuno=null;
    	String ordercolumn=request.getParameter("ordercolumn");
    	if(ordercolumn==null||ordercolumn.trim().length()<1)
    		ordercolumn=null;
    	String direct=request.getParameter("dict");
    	if(direct==null||direct.trim().length()<1)
    		direct=null;
    	//得到列名
    	//List<List<String>> objColumn= new ArrayList<List<String>>();
    	String column = "周次,学号,姓名,胸卡总分,胸卡未带次,校服总分,校服次数,行规总分,行规次数,还书总分,未还书数";
    	String [] columns = column.split(",");
    	
    	//得到数据
    	List<List<String>> objData= this.stuEthosManager.getEthosWJ(termid, Integer.parseInt(classid), stuno, ordercolumn, direct);

    	//导出的列明
		List<String> columnList=new ArrayList<String>();
		//if(objColumn!=null&&objColumn.size()>0){
		//	columnList=objColumn.get(0);
		//}
		for(int i = 0 ; i < columns.length; i++){
			columnList.add(columns[i]);
		}
  
    	//准备导出条件
		//准备导出条件
		String title="",filename="";
		TermInfo t=new TermInfo();
		t.setRef(termid.trim());
		List<TermInfo> tmInfo=this.termManager.getList(t, null);
		if(tmInfo!=null&&tmInfo.size()>0){
			//title=tmInfo.get(0).getYear()+""+tmInfo.get(0).getTermname() ;
		}
		
		 //--得到班级信息
		if(classid!=null&&classid.trim().length()>0){
			ClassInfo c=new ClassInfo();
			c.setClassid(Integer.parseInt(classid));
			List<ClassInfo> clsList=this.classManager.getList(c, null);
			if(clsList!=null&&clsList.size()>0){
				title+=""+clsList.get(0).getClassgrade()+"("+clsList.get(0).getClassname()+")";
			}
		}
		if(stuno!=null){
			StudentInfo s = new StudentInfo();
			s.setStuno(stuno);
			List<StudentInfo> stuList=this.studentManager.getList(s, null);
			if(stuList!=null&&stuList.size()>0){
				title+=""+stuList.get(0).getStuname()+"["+stuList.get(0).getStuno()+"]";
			}
		}
		
		title+="校风违纪统计成绩";
		filename=title;
		String flag=this.operaterexcelmanager.ExplortExcel(response, filename, columnList, objData, title, String.class, null);
		if(flag!=null){
			je.setMsg("错误，无法导出!");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
		}
    }
	/**
     * @atuthor 岳春阳
     * @date 2013-05-21
	 * @description 导出学生校风信息汇总，以学生查询(综合)
     */
	@RequestMapping(params="m=expStuzh",method=RequestMethod.POST)
    public void explortUserStudentZH(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
    	JsonEntity je=new JsonEntity();
    	String termid=request.getParameter("termid");
    	if(termid==null||termid.trim().length()<1){
    		je.setMsg("错误，系统检测到您没有正常浏览本系统！请正常浏览本系统!");
    		response.getWriter().print(je.getAlertMsgAndCloseWin());
    		return;
    	}
    	String classid=request.getParameter("classid");
    	if(classid==null||classid.trim().length()<1){
    		je.setMsg("错误，系统检测到您没有正常浏览本系统！请正常浏览本系统!");
    		response.getWriter().print(je.getAlertMsgAndCloseWin());
    		return;
    	}
    	String stuno=request.getParameter("stuno");
    	if(stuno==null||stuno.trim().length()<1)
    		stuno=null;
    	String ordercolumn=request.getParameter("ordercolumn");
    	if(ordercolumn==null||ordercolumn.trim().length()<1)
    		ordercolumn=null;
    	String direct=request.getParameter("dict");
    	if(direct==null||direct.trim().length()<1)
    		direct=null;
    	String isshowrank = request.getParameter("isshowrank");
    	//得到列名
    	//List<List<String>> objColumn= new ArrayList<List<String>>();
    	String column = "周次,学号,姓名,病假情况,病假数,病假分数,事假情况,事假数,事假分数,早退情况,早退数,早退分数,旷课情况,旷课数,旷课分数,迟到,迟到次数,迟到分数,未持胸卡次,胸卡扣分,校服情况,校服次数,校服扣分,行规情况,行规违纪次,行规扣分,迟还书情况,迟还书次数,还书扣分,好人好事,好人好事次,好人好事分,总分,排名";
    	String [] columns = column.split(",");
    	
    	//得到数据
    	List<List<String>> objData= this.stuEthosManager.getEthosZH(termid, Integer.parseInt(classid), stuno, Integer.parseInt(isshowrank));

    	//导出的列明
		List<String> columnList=new ArrayList<String>();
		//if(objColumn!=null&&objColumn.size()>0){
		//	columnList=objColumn.get(0);
		//}
		for(int i = 0 ; i < columns.length; i++){
			columnList.add(columns[i]);
		}
  
    	//准备导出条件
		//准备导出条件
		String title="",filename="";
		TermInfo t=new TermInfo();
		t.setRef(termid.trim());
		List<TermInfo> tmInfo=this.termManager.getList(t, null);
		if(tmInfo!=null&&tmInfo.size()>0){
			//title=tmInfo.get(0).getYear()+""+tmInfo.get(0).getTermname() ;
		}
		
		 //--得到班级信息
		if(classid!=null&&classid.trim().length()>0){
			ClassInfo c=new ClassInfo();
			c.setClassid(Integer.parseInt(classid));
			List<ClassInfo> clsList=this.classManager.getList(c, null);
			if(clsList!=null&&clsList.size()>0){
				title+=""+clsList.get(0).getClassgrade()+"("+clsList.get(0).getClassname()+")";
			}
		}
		if(stuno!=null){
			StudentInfo s = new StudentInfo();
			s.setStuno(stuno);
			List<StudentInfo> stuList=this.studentManager.getList(s, null);
			if(stuList!=null&&stuList.size()>0){
				title+=""+stuList.get(0).getStuname()+"["+stuList.get(0).getStuno()+"]";
			}
		}
		
		title+="校风综合统计成绩";
		filename=title;
		String flag=this.operaterexcelmanager.ExplortExcel(response, filename, columnList, objData, title, String.class, null);
		if(flag!=null){
			je.setMsg("错误，无法导出!");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
		}
    }
}
