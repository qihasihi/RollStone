package com.school.control.ethosaraisal;

import com.school.control.base.BaseController;
import com.school.entity.ClassInfo;
import com.school.entity.GradeInfo;
import com.school.entity.TermInfo;
import com.school.entity.ethosaraisal.ClassEthosInfo;
import com.school.entity.ethosaraisal.WeekInfo;
import com.school.manager.inter.IClassManager;
import com.school.manager.inter.IGradeManager;
import com.school.manager.inter.IOperateExcelManager;
import com.school.manager.inter.ITermManager;
import com.school.manager.inter.ethosaraisal.IClassEthosManager;
import com.school.manager.inter.ethosaraisal.IWeekManager;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * @author 岳春阳
 * @date 2013-05-08
 * @description 班级校风Controller
 */
@Controller
@RequestMapping(value="/classethos")
public class ClassEthosController extends BaseController<ClassEthosInfo>{

    //学生校风定义区域
    @Autowired
    private ITermManager termManager;
    @Autowired
    private IGradeManager gradeManager;
    @Autowired
    private IClassEthosManager classEthosManager;
    @Autowired
    private IWeekManager weekManager;
    @Autowired
    private IOperateExcelManager operaterexcelmanager;
    @Autowired
    private IClassManager classManager;
	/**
	 * @author 岳春阳
	 * @date 2013-03-27
	 * @description 转到班级校风页面
	 * */
	@RequestMapping(params="m=tolist",method=RequestMethod.GET) 
	public ModelAndView tolist(HttpServletRequest request,HttpServletResponse response,ModelAndView mp )throws Exception{	
		JsonEntity je = new JsonEntity();
		List<TermInfo> termList = this.termManager.getList(null, null);
		List<GradeInfo> gradeList = this.gradeManager.getList(null, null);
		request.setAttribute("termList", termList);
		request.setAttribute("gradeList", gradeList);
		
		return new ModelAndView("/ethosapraisal/admin_ethos_info");  
	}
	
	/**
	 * @author 岳春阳
	 * @date 2013-03-27
	 * @description 转到年级查询校风统计
	 * */
	@RequestMapping(params="m=tousergrade",method=RequestMethod.GET) 
	public ModelAndView toUserSelectGrade(HttpServletRequest request,HttpServletResponse response,ModelAndView mp )throws Exception{	
		JsonEntity je = new JsonEntity();
		List<TermInfo> termList = this.termManager.getList(null, null);
		List<GradeInfo> gradeList = this.gradeManager.getList(null, null);
		request.setAttribute("termList", termList);
		request.setAttribute("gradeList", gradeList);
		
		return new ModelAndView("/ethosapraisal/user/gradeview-user");  
	}
	/**
	 * @author 岳春阳
	 * @date 2013-03-27
	 * @description 转到班级查询校风统计
	 * */
	@RequestMapping(params="m=touserclass",method=RequestMethod.GET) 
	public ModelAndView toUserSelectClass(HttpServletRequest request,HttpServletResponse response,ModelAndView mp )throws Exception{	
		JsonEntity je = new JsonEntity();
		List<TermInfo> termList = this.termManager.getList(null, null);
		List<GradeInfo> gradeList = this.gradeManager.getList(null, null);
		request.setAttribute("termList", termList);
		request.setAttribute("gradeList", gradeList);
		
		return new ModelAndView("/ethosapraisal/user/classview-user");  
	}
	/**
	 * @author 岳春阳
	 * @date 2013-05-08
	 * @description 班级校风添加
	 * */
	@RequestMapping(params="m=ajaxsave",method=RequestMethod.POST)
	public void doSubmitSave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		ClassEthosInfo obj = this.getParameter(request, ClassEthosInfo.class);
		if(obj!=null){
			List<ClassEthosInfo> list = this.classEthosManager.getList(obj, null);
			String uuid = UUID.randomUUID().toString();
			String userRef = this.logined(request).getRef();
			if(list!=null&&list.size()>0){				
				obj.setOperateid(userRef);
				obj.setRef(list.get(0).getRef());
				Boolean b = this.classEthosManager.doUpdate(obj);
				if(b){
					je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
					je.setType("success");
				}else{  
					je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
				}
			}else{				
				obj.setOperateid(userRef);
				obj.setRef(uuid);
				Boolean b = this.classEthosManager.doSave(obj);
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
	 * @date 2013-05-08
	 * @description 班级校风加载
	 * */
	@RequestMapping(params="m=loadethos",method=RequestMethod.POST)
	public void loadClassEthos(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		ClassEthosInfo obj = this.getParameter(request, ClassEthosInfo.class);
		if(obj.getClassid()==null){
			je.setMsg("系统异常，未获取到班级标识");
			response.getWriter().print(je.toJSON());
			return;
		}else{
			List<ClassEthosInfo> objList = this.classEthosManager.loadClassEthos(obj);
			je.setObjList(objList);
			je.setType("success");
			response.getWriter().print(je.toJSON());	
		}
	}
	/**
	 * @author 岳春阳
	 * @date 2013-05-08
	 * @description 校风统计按年级查询
	 * */
	@RequestMapping(params="m=getClassEthosList",method=RequestMethod.POST)
	public void getClassEthosList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
//		String termid = request.getParameter("termid");
//		Integer weekid = Integer.parseInt(request.getParameter("weekid"));
//		Integer weekidend=Integer.parseInt(request.getParameter("weekend"));
//		String grade = request.getParameter("grade");
//		String order = request.getParameter("ordercolumn");
		String termid = request.getParameter("termid");
		String grade = request.getParameter("grade");
		String weekid = request.getParameter("weekid");
		String classid = request.getParameter("classid");
		String weekend=request.getParameter("weekend");
		String ordercolumn=request.getParameter("ordercolumn");
		List<List<String>> objList = this.classEthosManager.getClassEthosList(termid, grade, weekid==null?null:Integer.parseInt(weekid), weekend==null?null:Integer.parseInt(weekend), classid==null?null:Integer.parseInt(classid), ordercolumn);
		je.setObjList(objList);
		je.setType("success");
		response.getWriter().print(je.toJSON());
	}
	/**
	 * @author 岳春阳
	 * @date 2013-05-08
	 * @description 校风统计按班级查询
	 * */
	@RequestMapping(params="m=getEthosForClass",method=RequestMethod.POST)
	public void getEthosForClass(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		String termid = request.getParameter("termid");
		String grade = request.getParameter("grade");
		String weekid = request.getParameter("weekid");
		String classid = request.getParameter("classid");
		List<List<String>> objList = this.classEthosManager.getEthosForClass(termid, grade, Integer.parseInt(weekid), Integer.parseInt(classid));
		je.setObjList(objList);
		je.setType("success");
		response.getWriter().print(je.toJSON());
	}
	/**
     * @atuthor 岳春阳
     * @date 2013-05-21
	 * @description 导出班级校风按年级
     */
	@RequestMapping(params="m=expClassList",method=RequestMethod.POST)
    public void explortUserStudentKQ(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
		String termid = request.getParameter("termid");
		String grade = request.getParameter("grade");
		String weekid = request.getParameter("weekid");
		String classid = request.getParameter("classid");
		String weekend=request.getParameter("weekend");
		String ordercolumn=request.getParameter("ordercolumn");
		if(ordercolumn==null||ordercolumn.trim().length()<1)
			ordercolumn=null;
		JsonEntity je = new JsonEntity();
		// 验证
		if (termid == null || termid.trim().length() < 1) {
			je.setMsg("错误，系统检测到您尚未选择学期，无法查询，请选择后重试！");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return;
		}
		if (weekid == null || weekid.trim().length() < 1) {
			je.setMsg("错误，系统尚未检测到您尚未选择周次，无法查询，请选择后重试！");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return;
		}
    	
    	//得到列名
    	//List<List<String>> objColumn= new ArrayList<List<String>>();
    	String column = "班级,集会分数,卫生分数,财产分数,宿舍集体分,其他分数,考勤汇总分,违纪汇总分,好人好事,奖励加分,总分,排名";
    	String [] columns = column.split(",");
    	
    	//得到数据
    	List<List<String>> objData= this.classEthosManager.getClassEthosList(termid,grade, null,null,null, ordercolumn);

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
			title=tmInfo.get(0).getYear()+""+tmInfo.get(0).getTermname() ;
		}
		//第一个周次
		 WeekInfo w=new WeekInfo();
		  w.setRef(Integer.parseInt(weekid.trim()));
		  List<WeekInfo> wkList=this.weekManager.getList(w,null);
		  if(wkList!=null&&wkList.size()>0)
			  title+=" "+grade+"年级 "+wkList.get(0).getWeekname();
		  //第二个周次
		  if(weekend!=null&&weekend.trim().length()>0){
			  w=new WeekInfo();
			  w.setRef(Integer.parseInt(weekend.trim()));
			  wkList=this.weekManager.getList(w,null);
			  if(wkList!=null&&wkList.size()>0)
				  title+="至"+wkList.get(0).getWeekname();
		  }
		title+="第一周至第一周";
		
		
		
		title+="各班级校风情况";
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
	 * @description 导出班级详情统计
     */
	@RequestMapping(params="m=expClass",method=RequestMethod.POST)
    public void explortUserStatices(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	JsonEntity je = new JsonEntity();
    	String termid = request.getParameter("termid");
    
    	if (termid== null || termid.trim().length()<1) {
			je.setMsg("系统尚未获取学期信息！");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return ;
		}
    	String weekid = request.getParameter("weekid");
    	
    	if (weekid== null || weekid.trim().length()<1) {
			je.setMsg("系统尚未获取周次信息！");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return ;
		}
    	String grade = request.getParameter("grade");
    
    	if (grade== null || grade.trim().length()<1) {
			je.setMsg("系统尚未获取年级信息！");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return ;
		}	
    	String classid = request.getParameter("classid");
    	if (classid== null || classid.trim().length()<1) {
			je.setMsg("系统尚未获取班级信息！");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return ;
		}
    	List<List<String>> classStatices = this.classEthosManager.getEthosForClass(termid, grade, Integer.parseInt(weekid), Integer.parseInt(classid));
    	
    	
    	List<List<String>> dataStatices=new ArrayList<List<String>>();
    	//数据处理
    	if(classStatices!=null&&classStatices.size()>0){    		
    		for (int i = 0; i < classStatices.size(); i++) {
    			if(classStatices.get(i)!=null&&classStatices.get(i).size()>0){
    				List<String> strList=null;
    				for (int j = 0; j < classStatices.get(i).size(); j++) {
						if(j%4==0){
							if(j!=0)
								dataStatices.add(strList);
							strList=new ArrayList<String>();							
						}
						
							strList.add(classStatices.get(i).get(j));
							
					  if(j==classStatices.get(i).size()-1){
							dataStatices.add(strList);
					  }
					}    				
    			}
			}
    	}
    	//处理导出参数
    	//准备导出条件
		String title="",filename="";
		TermInfo t=new TermInfo();
		t.setRef(termid.trim());
		List<TermInfo> tmInfo=this.termManager.getList(t, null);
		if(tmInfo!=null&&tmInfo.size()>0){
			title=tmInfo.get(0).getYear()+""+tmInfo.get(0).getTermname() ;
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
		//第一个周次
		 WeekInfo w=new WeekInfo();
		  w.setRef(Integer.parseInt(weekid.trim()));
		  List<WeekInfo> wkList=this.weekManager.getList(w,null);
		  if(wkList!=null&&wkList.size()>0)
			  title+=" "+grade+"年级 "+wkList.get(0).getWeekname();
		title+=" "+grade+"年级 "+"第一周";
		  title+=" 班级校风汇总情况";
		  filename=title;
		  String flag=this.operaterexcelmanager.ExplortExcel(response, filename, null, classStatices, title, String.class, null);
			if(flag!=null){
				je.setMsg("错误，无法导出!");
				response.getWriter().print(je.getAlertMsgAndCloseWin());
			}	
    }  
}
