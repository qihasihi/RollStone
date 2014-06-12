package com.school.control;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.*;
import com.school.manager.inter.*;
import com.school.util.MD5_NEW;
import jcore.jsonrpc.common.JSONArray;
import jcore.jsonrpc.common.JSONObject;
import net.sf.json.JSON;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.ClassInfo;
import com.school.entity.ClassUser;
import com.school.entity.ClassYearInfo;
import com.school.entity.GradeInfo;
import com.school.entity.SubjectInfo;
import com.school.entity.TermInfo;
import com.school.entity.UserInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;

@Controller
@RequestMapping(value="/cls") 
public class ClassController extends BaseController<ClassInfo>{
    private IClassManager classManager;
    private IClassYearManager classYearManager;
    private IGradeManager gradeManager;
    private ISubjectManager subjectManager;
    private ITermManager termManager;
    private IClassUserManager classUserManager;
    private IOperateExcelManager operaterexcelmanager;
    private IUserManager userManager;
    public ClassController(){
        this.classManager=this.getManager(ClassManager.class);
        this.classYearManager=this.getManager(ClassYearManager.class);
        this.gradeManager=this.getManager(GradeManager.class);
        this.subjectManager=this.getManager(SubjectManager.class);
        this.termManager=this.getManager(TermManager.class);
        this.classUserManager=this.getManager(ClassUserManager.class);
        this.userManager=this.getManager(UserManager.class);
        this.operaterexcelmanager=this.getManager(OperateExcelManager.class);
    }

	@RequestMapping(params="m=list",method=RequestMethod.GET) 
	public ModelAndView toClassList(HttpServletRequest request,ModelMap mp )throws Exception{
		PageResult p = new PageResult();
		p.setOrderBy("c.e_time desc"); 
		List<ClassYearInfo>classyearList=this.classYearManager.getList(null, p); 
		List<GradeInfo>gradeList=this.gradeManager.getList(null, null);
		mp.put("classyearList", classyearList);
		mp.put("gradeList", gradeList);

		//得到全部学科
		List<SubjectInfo> subList=this.subjectManager.getList(null, null);
		request.setAttribute("subList", subList);
		//得到当前年份
		TermInfo currentTm=this.termManager.getAutoTerm();
		mp.put("currentYear", currentTm.getYear());
		
		//得到allowAutoLevel
		int allowAutoLevel=0;
		//验证该是否在条件内执行
		//得到下一个学年
		TermInfo tm=new TermInfo();
		tm.setDYYear(currentTm.getYear());
		PageResult presult=new PageResult();
		presult.setOrderBy(" u.YEAR ASC ");
		presult.setPageSize(1);	
		
		List<TermInfo> tmList=this.termManager.getList(tm, presult);
		if(tmList!=null&&tmList.size()>0){		
			String nextyear=tmList.get(0).getYear();
			//得到当前nextyear下所有的行政班
			presult=new PageResult();
			presult.setPageSize(1);	
			ClassInfo clsentity=new ClassInfo();
			clsentity.setYear(nextyear);
			clsentity.setPattern("行政班");
			List<ClassInfo> nextClsList=this.classManager.getList(clsentity, presult);
			if(nextClsList==null||nextClsList.size()<1){
				allowAutoLevel=1;
			}
		}
		request.setAttribute("allowAutoLevel", allowAutoLevel);
		return new ModelAndView("/class/list");  
	}
	
	/**
	 * 班级成员详情  
	 * @param request
	 * @param mp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="m=detail",method=RequestMethod.GET) 
	public ModelAndView toClassUserDetail(HttpServletRequest request,HttpServletResponse response,ModelAndView mp )throws Exception{
		String classid=request.getParameter("classid");
		JsonEntity jeEntity=new JsonEntity();
		if(classid==null||classid.trim().length()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jeEntity.getAlertMsgAndBack());return null;
		}
		//验证班级
		ClassInfo clsInfo=new ClassInfo();
		clsInfo.setClassid(Integer.parseInt(classid.trim()));
		List<ClassInfo> clsList=this.classManager.getList(clsInfo, null);
		if(clsList==null||clsList.size()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
			response.getWriter().print(jeEntity.getAlertMsgAndBack());return null;
		}
		//PageResult p = new PageResult();
		ClassUser cu=new ClassUser();
		cu.setRelationtype("学生");
		if(classid!=null&&classid.trim().length()>0)  
			cu.setClassid(Integer.parseInt(classid));
        //只显示启用的学生帐号，不显示禁用的学生
        cu.getUserinfo().setStateid(0);
		List<ClassUser>stuList=this.classUserManager.getList(cu,null); 
		//得到全部学科
		List<SubjectInfo> subList=this.subjectManager.getList(null, null);
		request.setAttribute("subList", subList);
		//得到全部年级
		List<GradeInfo> gradeList=this.gradeManager.getList(null, null);
		request.setAttribute("gdList", gradeList);
		
		request.setAttribute("stuList", stuList);		
		request.setAttribute("clsinfo", clsList.get(0));
		
		
		return new ModelAndView("/class/detail/detail");    
	}

    /**
     * 根据班级ID得到班级人员
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=getClsUserByClsId",method=RequestMethod.POST)
    public void getClsUserByClsId(HttpServletRequest request,HttpServletResponse response) throws Exception{
        PageResult presult=this.getPageResultParameter(request);
        String clsid=request.getParameter("classid");
       // String relationType=request.getParameter("relationtype");
        JsonEntity jsonEntity=new JsonEntity();
        if(clsid==null||clsid.trim().length()<1){//||relationType==null||relationType.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        ClassUser cu=new ClassUser();
        cu.setRelationtype("学生");
        cu.setClassid(Integer.parseInt(clsid));
        //只显示启用的学生帐号，不显示禁用的学生
        cu.getUserinfo().setStateid(0);
        List<ClassUser> stuList=this.classUserManager.getList(cu,presult);
        presult.getList().add(stuList);
        presult.getList().add(clsid);
        jsonEntity.setType("success");
        jsonEntity.setPresult(presult);
        response.getWriter().print(jsonEntity.toJSON());
    }
	
	@RequestMapping(params="m=exportClsExcel",method=RequestMethod.GET)
	public String exportClsExcel(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity();
		String classid=request.getParameter("classid");
		if(classid==null||classid.length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return null; 
		}
		String msg=exportStudent(response, classid);
		if(msg!=null&&msg.length()>0){
			je.setMsg(msg);
			response.getWriter().print(je.getAlertMsgAndBack());
			return null;
		}  
		return null;
	}
	
	public String exportStudent(HttpServletResponse response,String classid) throws Exception{
		List<List<String>> dataList = new ArrayList<List<String>>();
		List<String> columns = new ArrayList<String>();
		columns.add("学号");
		columns.add("姓名");
		columns.add("性别");
		//columns.add("年份");

		String filename = null;
		String title = null;
		ClassUser cu=new ClassUser();
		cu.setRelationtype("学生");
		cu.setClassid(Integer.parseInt(classid));
        cu.getUserinfo().setStateid(0);//不查询禁用的学生信息
		List<ClassUser>clsUserList=this.classUserManager.getList(cu, null);
		if(clsUserList==null||clsUserList.size()<1){
			return "数据为空!";
		}
		for (ClassUser clsu : clsUserList) {
			List<String> tempList = new ArrayList<String>();
			tempList.add(clsu.getStuno());
			tempList.add(clsu.getUserinfo().getRealname());
			tempList.add(clsu.getUserinfo().getSex());
		//	tempList.add(clsu.getClassgrade()+clsu.getClassname());
		//	tempList.add(clsu.getYear());
			dataList.add(tempList);
		}
		filename=title=clsUserList.get(0).getYear()+clsUserList.get(0).getClassgrade()+clsUserList.get(0).getClassname()+"学生名单";
		return this.operaterexcelmanager.ExplortExcel(response,filename, columns,
				dataList, title, String.class, null);
	}
	  
	/**
	 * 获取List
	 * @param request
	 * @param response 
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST)
	public void AjaxGetList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ClassInfo classinfo = this.getParameter(request, ClassInfo.class);
		String type=request.getParameter("dtype");
		if(type!=null&&type.trim().length()>0){ 
			classinfo.setType(type);
		}
		PageResult pageresult = this.getPageResultParameter(request);
		pageresult.setOrderBy(" u.c_time desc ");//排序，启用，在前，禁用在后
		List<ClassInfo> classList =classManager.getList(classinfo, pageresult);
		pageresult.setList(classList);
		JsonEntity je = new JsonEntity();
		je.setType("success"); 
		je.setPresult(pageresult); 
		response.getWriter().print(je.toJSON()); 
	}
	/** 
	 * 根据条件导出班级模板。
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=explortClsTemplate",method=RequestMethod.POST)
	public void explortClsTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ClassInfo classinfo = this.getParameter(request, ClassInfo.class);
		String type=request.getParameter("dtype");
		if(type!=null&&type.trim().length()>0){
			classinfo.setType(type);
		}
		if(classinfo.getClassgrade()==null||classinfo.getClassgrade().trim().length()<1)
			classinfo.setClassgrade(null);
		
		if(classinfo.getPattern()==null||classinfo.getPattern().trim().length()<1)
			classinfo.setPattern(null);
		
		if(classinfo.getYear()==null||classinfo.getYear().trim().length()<1)
			classinfo.setYear(null);
	
		if(classinfo.getClassname()==null||classinfo.getClassname().trim().length()<1)
			classinfo.setClassname(null);
		
		PageResult pageresult = this.getPageResultParameter(request);
		pageresult.setOrderBy(" u.isflag asc ");//排序，启用，在前，禁用在后
		List<ClassInfo> classList =classManager.getList(classinfo, pageresult);
		
		JsonEntity jEntity=new JsonEntity();
		if(classList==null||classList.size()<1){
			jEntity.setMsg("无法继续导出班级模块，原因：根据相关条件，未查找到对应班级!");
			response.getWriter().print(jEntity.getAlertMsgAndCloseWin());return;
		}
		//开始组织数据
		List<String> sheetNameList=new ArrayList<String>();
		List<List<String>> columnsList=new ArrayList<List<String>>();
		List datalist=new ArrayList();
		List<String> titleList=new ArrayList<String>();
		List<Class<? extends Object>> entityClsList=new ArrayList<Class<? extends Object>>();
		List<String> explortObjList=new ArrayList<String>();
		for (int i=0;i<classList.size();i++) {
            if(i>=4)break;       //只显示4条
            ClassInfo clstmp = classList.get(i);
			if(clstmp!=null){
				//sheet
				sheetNameList.add(clstmp.getClassgrade()+clstmp.getClassname());
				//column
				List<String> coltmpList=new ArrayList<String>();
				coltmpList.add("学号");
				coltmpList.add("姓名");
				coltmpList.add("性别");
				columnsList.add(coltmpList);
				//title
				String title=clstmp.getYear()+" "+clstmp.getClassgrade()+"("+clstmp.getClassname()+")";
				titleList.add(title);
				//datalist   (空)
				List<List<String>> dataArrayList=new ArrayList<List<String>>();
				datalist.add(dataArrayList);
				//entityCls
				entityClsList.add(null);
				//explortObjList
				explortObjList.add(null);
			}
		}
		
		
		String filename=classList.get(0).getYear()+"学年班级学生导入模板";
		//导出
		this.operaterexcelmanager.ExplortExcel(response, filename,sheetNameList,columnsList
				,datalist,titleList,entityClsList,explortObjList);
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
		ClassInfo classinfo = this.getParameter(request, ClassInfo.class);
		if(classinfo.getClassid()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("CLASS_CLASSID_EMPTY")); 
			response.getWriter().print(je.toJSON());
			return;
		}
		List<ClassInfo> classList = classManager.getList(classinfo, null);
		je.setObjList(classList); 
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
		ClassInfo classinfo = this.getParameter(request, ClassInfo.class);
		String type=request.getParameter("dtype");
		String year=request.getParameter("dyear");
		String pattern=request.getParameter("dpattern");
		if(classinfo.getClassgrade()==null||
				classinfo.getClassgrade().trim().length()<1||
				classinfo.getClassname()==null||
				classinfo.getClassname().length()<1||type==null||year==null||pattern==null){  
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR")); 
			response.getWriter().print(je.toJSON()); 
			return;
		}
		classinfo.setType(type);
		classinfo.setYear(year);
		classinfo.setPattern(pattern);
		classinfo.setIslike(1);		
		//已存在当前数据 无法添加
		List<ClassInfo>classList=this.classManager.getList(classinfo, null);
		if(classList!=null&&classList.size()>0){
			je.setMsg(UtilTool.msgproperty.getProperty("DATA_EXISTS"));
			response.getWriter().print(je.toJSON());
			return;
		}
		classinfo.setIsflag(1);
		if(classManager.doSave(classinfo)){
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
		ClassInfo classinfo = this.getParameter(request, ClassInfo.class);
		if(classinfo.getClassid()==null
				//||classinfo.getYear()==null
				){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		ClassInfo clsInfo=new ClassInfo();
		clsInfo.setClassid(classinfo.getClassid());
		List<ClassInfo> clsList=this.classManager.getList(clsInfo, null);
		if(clsList==null||clsList.size()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
			response.getWriter().print(je.toJSON());return;
		}		
		ClassInfo clsEntity=clsList.get(0);
		if(classinfo.getClassgrade()!=null&&!classinfo.getClassgrade().equals(clsEntity.getClassgrade()))
			clsEntity.setClassgrade(classinfo.getClassgrade());
		if(classinfo.getClassname()!=null&&!classinfo.getClassname().equals(clsEntity.getClassname()))
			clsEntity.setClassname(classinfo.getClassname());
		if(classinfo.getType()!=null&&!classinfo.getType().equals(clsEntity.getType()))
			clsEntity.setType(classinfo.getType());
		if(classinfo.getPattern()!=null&&!classinfo.getPattern().equals(clsEntity.getPattern()))
			clsEntity.setPattern(classinfo.getPattern());
		if(classinfo.getPattern()!=null&&classinfo.getPattern().equals("分层班")
				&&classinfo.getSubjectid()!=null&&classinfo.getSubjectid()!=clsEntity.getSubjectid())
			clsEntity.setSubjectid(classinfo.getSubjectid());
		else if(classinfo.getPattern()!=null&&classinfo.getPattern().equals("行政班"))
			clsEntity.setSubjectid(-999);
		if(classinfo.getYear()!=null&&!classinfo.getYear().equals(clsEntity.getYear()))
			clsEntity.setYear(classinfo.getYear());	
		if(classinfo.getIsflag()!=null&&classinfo.getIsflag()!=clsEntity.getIsflag())
			clsEntity.setIsflag(classinfo.getIsflag());
		if(classManager.doUpdate(clsEntity)){
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
		ClassInfo classinfo = this.getParameter(request, ClassInfo.class);
		if(classinfo.getClassid()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("CLASS_CLASSID_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}
		//跟据ID得到这个班级现在的学生人数
		List<ClassInfo> clsList=this.classManager.getList(classinfo, null);
		if(clsList==null||clsList.size()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
			response.getWriter().print(je.toJSON());
			return;
		}
		//验证： 当班级人数为0时，删除启用，可以删除本班级。
//		if(clsList.get(0).getNum()!=null&&UtilTool.isNumber(clsList.get(0).getNum().toString())
//				&&Integer.parseInt(clsList.get(0).getNum().toString())>0){
//			je.setMsg("异常错误，该班级还存在学生，请清空学生后再删除班级!");
//			response.getWriter().print(je.toJSON());return;
//		}
		if(classManager.doDelete(classinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());    
	}  
	
	/**
	 * 进入COPY班级学生
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=toCopyClsStudent",method=RequestMethod.POST)
	public void toCopyClassStudent(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String clsid=request.getParameter("classid");
		JsonEntity jeEntity=new JsonEntity();
		if(clsid==null||clsid.toString().trim().length()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		ClassInfo clsEntity=new ClassInfo();
		clsEntity.setClassid(Integer.parseInt(clsid.toString().trim()));
		List<ClassInfo> classList=this.classManager.getList(clsEntity, null);
		if(classList==null||classList.size()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
			response.getWriter().print(jeEntity.toJSON());
			return;
		}
		
		//如果目的班级是行政班，可选班级为上一学年的行政班级

		//灰色名字为已经分配到了与“目的班级”同类型的其它班级中，
			//确定时提示，如果继续操作，从其它班级删除，调到此班。
		 PageResult presult=new PageResult();
		 presult.setPageNo(1);
		 presult.setPageSize(10000);
		 presult.setOrderBy(" u.class_id asc ");
		
		clsEntity=classList.get(0);
		 
		 List<ClassInfo> tmpClsList=null;
		 if(clsEntity.getPattern().trim().equals("行政班")){
			 ClassYearInfo cyEntity=new ClassYearInfo();
			 cyEntity.setClassyearvalue(clsEntity.getYear());
			 List<Map<String,Object>> objMapList=this.classYearManager.getClassYearPree(cyEntity);
			 if(objMapList!=null&&objMapList.size()>0){				 
				 //得到上一年份
				 String cyValue=objMapList.get(0).get("CLASS_YEAR_VALUE").toString();
				 
				 ClassInfo tmpClsEntity=new ClassInfo();
				 tmpClsEntity.setYear(cyValue);
				 tmpClsEntity.setPattern("行政班");
				 tmpClsEntity.setClassgrade(clsEntity.getClassgrade());
				 //tmpClsEntity.setSubjectid(clsEntity.getSubjectid());
				 
				 tmpClsList=this.classManager.getList(tmpClsEntity, presult);
			 }
		 }else if(clsEntity.getPattern().trim().equals("分层班")){
				//如果目的班级是分层班，可选班级为本学年的行政班
			 ClassInfo tmpEntity=new ClassInfo();
			 tmpEntity.setYear(clsEntity.getYear());
			 tmpEntity.setPattern("行政班");
			 tmpEntity.setClassgrade(clsEntity.getClassgrade());
			 tmpClsList=this.classManager.getList(tmpEntity, presult);
		 }
		 jeEntity.setObjList(tmpClsList);
		 jeEntity.setType("success");
		 response.getWriter().print(jeEntity.toJSON());
	}
	/**
	 * 得到调班学生及情况
	 * @param request
	 * @param response
	 */
	@RequestMapping(params="m=tiaobancu",method=RequestMethod.POST)
	public void getTiaoBanClassUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity jeEntity=new JsonEntity();
		ClassInfo clsInfo=this.getParameter(request, ClassInfo.class);
//		if(clsInfo==null||clsInfo.getClassid()==null){
//			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
//			response.getWriter().print(jeEntity.toJSON());return;
//		}
		ClassInfo tmpSearchCls=new ClassInfo();
		tmpSearchCls.setClassid(clsInfo.getClassid());
		List<ClassInfo> clsList=this.classManager.getList(tmpSearchCls, null);
		if(clsList==null||clsList.size()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		//得到学生
		ClassInfo tmpCls=clsList.get(0);
		List<Map<String,Object>> objMapList=this.classUserManager.getClassUserWithTiaoban(clsInfo.getPattern(), clsInfo.getYear(),clsInfo.getClassid().toString());
		jeEntity.setObjList(objMapList);
		jeEntity.setType("success");
		response.getWriter().print(jeEntity.toJSON());
	}
	
	/**
	 * 班级详情页，调班执行
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	//cls?m=doAddClsUserDetail
	@RequestMapping(params="m=doAddClsUserDetail",method=RequestMethod.POST)
	public void doAddClsUserDetail(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity jeEntity=new JsonEntity();
		ClassInfo clsInfo=this.getParameter(request, ClassInfo.class);
		if(clsInfo==null||clsInfo.getClassid()==null){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		String uid=request.getParameter("uid");
		if(uid==null||uid.trim().length()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		
		//验证班级是否存在
		ClassInfo clsEntity=new ClassInfo();
		clsEntity.setClassid(clsInfo.getClassid());
		List<ClassInfo> clsList=this.classManager.getList(clsEntity, null);
		if(clsList==null||clsList.size()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		clsEntity=clsList.get(0);
		//开始添加数据 
		String[] uidArray=uid.split(",");
		StringBuilder sqlbuilder=null;
		List<String> sqlArrayList=new ArrayList<String>();
		List<List<Object>> objArrayList=new ArrayList<List<Object>>();
		for (String tmpuid : uidArray) {
			if(tmpuid==null||tmpuid.trim().length()<1)continue;
			//验证用户是否存在
			UserInfo utmp=new UserInfo();
			utmp.setRef(tmpuid.trim());
			List<UserInfo> uList=this.userManager.getList(utmp, null);
			if(uList==null||uList.size()<1){
				jeEntity.setMsg("异常错误，学生ID:"+tmpuid.trim()+" 没有发现该学生!");
				response.getWriter().print(jeEntity.toJSON());
				return;
			}
			//UserInfo currentUsTmp=uList.get(0);
			//查找用户是否存在于同年份，同类型，同学科的存在,并删除
			ClassUser cutmp=new ClassUser();
			if(clsEntity.getPattern().trim().equals("分层班"))
				if(clsEntity.getSubjectid()!=null)
					cutmp.setSubjectid(clsEntity.getSubjectid());	//subjectid
			cutmp.setClassgrade(clsEntity.getClassgrade());	//classgrade 
			cutmp.setRelationtype("学生");			//relation_type
			cutmp.setPattern(clsEntity.getPattern());	//pattern
			cutmp.setUserid(tmpuid.trim());		//userid
			cutmp.setYear(clsEntity.getYear());
			List<ClassUser> cutmpList=this.classUserManager.getList(cutmp, null);
			if(cutmpList!=null&&cutmpList.size()>0){				
				sqlbuilder=new StringBuilder();
				List<Object> objList=this.classUserManager.getDeleteSql(cutmp, sqlbuilder);
				if(sqlbuilder!=null&&objList!=null&&sqlbuilder.toString().trim().length()>0){
					sqlArrayList.add(sqlbuilder.toString());
					objArrayList.add(objList);
				}
				String remark="批量删除，条件："+clsEntity.getPattern()+"，学科:"+clsEntity.getSubjectid()+","+clsEntity.getClassgrade()+",学生,"+tmpuid+",";
					   remark+=","+clsEntity.getYear();
				
				sqlbuilder=new StringBuilder();
				objList=this.classUserManager.getAddOperateLog(this.logined(request).getRef(),"j_class_user"
						,null,null, null,"DELETE", remark,sqlbuilder);
				if(sqlbuilder!=null&&objList!=null&&sqlbuilder.toString().trim().length()>0){
					sqlArrayList.add(sqlbuilder.toString());
					objArrayList.add(objList);
				}
			}
			
			//添加
			ClassUser addcu=new ClassUser();
			addcu.setRef(this.classUserManager.getNextId());
			addcu.setClassid(clsEntity.getClassid());
			addcu.setUserid(tmpuid.trim());
			addcu.setRelationtype("学生");
			addcu.setSubjectid(clsEntity.getSubjectid());
			sqlbuilder=new StringBuilder();
			List<Object> objList=this.classUserManager.getSaveSql(addcu, sqlbuilder);
			if(sqlbuilder!=null&&objList!=null&&sqlbuilder.toString().trim().length()>0){
				sqlArrayList.add(sqlbuilder.toString());
				objArrayList.add(objList);
			}
		}
		
		if(sqlArrayList.size()>0&&objArrayList.size()>0&&sqlArrayList.size()==objArrayList.size()){
			if(this.classUserManager.doExcetueArrayProc(sqlArrayList, objArrayList)){
				jeEntity.setType("success");
				jeEntity.setMsg("操作成功!");
			}else
				jeEntity.setMsg("操作失败，原因：未知!");
		}else{
			jeEntity.setMsg("异常错误，没有可执行的操作!请重试!");
		}
		response.getWriter().print(jeEntity.toJSON());
	} 
	/**
	 * 自动升级
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=levelup",method=RequestMethod.POST)
	public void doClsLevelUp(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String year=request.getParameter("year");
		
		JsonEntity jeEntity=new JsonEntity();
		if(year==null||year.trim().length()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		//验证该是否在条件内执行
		//得到下一个学年
		TermInfo tm=new TermInfo();
		tm.setDYYear(year);
		PageResult presult=new PageResult();
		presult.setOrderBy(" u.YEAR ASC ");
		presult.setPageSize(1);		
		List<TermInfo> tmList=this.termManager.getList(tm, presult);
		if(tmList==null||tmList.size()<1){
			jeEntity.setMsg("异常错误，发有发现下一个年份!请确认是跨年!");
			response.getWriter().print(jeEntity.toJSON());return;
		}
		String nextyear=tmList.get(0).getYear();
		//得到当前nextyear下所有的行政班
		presult=new PageResult();
		presult.setPageSize(1);	
		ClassInfo clsentity=new ClassInfo();
		clsentity.setYear(nextyear);
		clsentity.setPattern("行政班");
		List<ClassInfo> nextClsList=this.classManager.getList(clsentity, presult);
		if(nextClsList!=null&&nextClsList.size()>0){
			jeEntity.setMsg("异常错误，在"+nextyear+"中存在于新的行政班级!请确认!");
			response.getWriter().print(jeEntity.toJSON());return;
		}
		
		if(this.classManager.doClassLevelUp(year)){
			jeEntity.setMsg("自动升级成功!请刷新页面!");
			jeEntity.setType("success");
		}else
			jeEntity.setMsg("升级失败!原因：未知!");
		response.getWriter().print(jeEntity.toJSON());
	}

    @RequestMapping(params ="m=lzxUpdate")
    public void lzxUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String clsArrayjson=request.getParameter("clsarrayjson");
        if(clsArrayjson==null||clsArrayjson.toString().trim().length()<1){
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，没有发现您要添加或修改的班级数据Json!\"}");
            return;
        }
        net.sf.json.JSONArray clsJr=net.sf.json.JSONArray.fromObject(clsArrayjson);
        if(clsJr==null||clsJr.isEmpty()){
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，没有发现您要添加或修改的班级数据Json!\"}");return;
        }
        String hasClsid=null;
        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        Iterator jrIte=clsJr.iterator();
        while(jrIte.hasNext()){
            net.sf.json.JSONObject clsJo=(net.sf.json.JSONObject)jrIte.next();
            Object timeStr=clsJo.get("timestamp");
            Object schoolid=clsJo.get("lzx_school_id");
            Object classid=clsJo.get("classid");
            Object className=clsJo.get("class_name");
            Object pattern=clsJo.get("pattern");
            Object classGrade=clsJo.get("class_grade");
            Object year=clsJo.get("year");
            Object type= clsJo.get("type");
            Object subjectid=clsJo.get("subject_id");
            Object isflag=clsJo.get("isflag");
            Object key=clsJo.get("key");
            //验证相关参数
            if(timeStr==null||timeStr.toString().trim().length()<1||!UtilTool.isNumber(timeStr.toString())){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，登陆时间戳参数缺少!\"}");return;
            }
            if(schoolid==null||schoolid.toString().trim().length()<1||!UtilTool.isNumber(schoolid.toString())){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，分校ID为空!!\"}");return;
            }
            if(classid==null||classid.toString().trim().length()<1||!UtilTool.isNumber(classid.toString())){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，班级ID为空!!\"}");return;
            }
            if(className==null||className.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，班级名称为空!!\"}");
                return;
            }
            if(pattern==null||pattern.toString().trim().length()<1){
                //response.getWriter().print("异常错误，班级类型为空!!");return;
                pattern="行政班";
            }
            if(classGrade==null||classGrade.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，年级为空!!\"}");
                return;
            }
            if(year==null||year.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，学年为空!!\"}");
               return;
            }
            if(type==null||type.toString().trim().length()<1){
               // response.getWriter().print("异常错误，班级类型为空!!");return;
                type="NORMAL";
            }
            if(pattern!=null&&pattern.toString().trim().equals("分层班")){
                if(subjectid==null||subjectid.toString().trim().length()<1||!UtilTool.isNumber(subjectid.toString())){
                    response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，分层班的班级要添加学科ID,学科ID为空!!\"}");
                    return;
                }
            }
            if(isflag==null||isflag.toString().trim().length()<1||!UtilTool.isNumber(isflag.toString())){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，班级是否启用为空!!\"}");return;
            }
            if(key==null||key.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，key为空!!\"}");
                return;
            }

            //验证是否在三分钟内
            Long ct=Long.parseLong(timeStr.toString());
            Long nt=new Date().getTime();
            double d=(nt-ct)/(1000*60);
            if(d>3){//大于三分钟
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，响应超时!接口三分钟内有效!\"}");
               return;
            }
            //验证key
            String md5key=timeStr.toString()+schoolid;

            md5key+=classid.toString()+className.toString()+classGrade.toString()+type.toString()+timeStr.toString();
            md5key= MD5_NEW.getMD5ResultCode(md5key);//生成md5加密
            if(!md5key.trim().equals(key.toString().trim())){//如果不一致，则说明非法登陆
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，非法登陆!!\"}");return;
            }
            //验证通过
            ClassInfo cls=new ClassInfo();
            cls.setClassname(className.toString());
            cls.setClassgrade(classGrade.toString());
            cls.setYear(year.toString());
            cls.setPattern(pattern.toString());
            List<ClassInfo> clsList=this.classManager.getList(cls,null);
            if(clsList!=null&&clsList.size()>0){
                hasClsid=(hasClsid==null?classid.toString():hasClsid+","+classid.toString());
                continue;
            }

            cls.setType(type.toString());

            cls.setClassid(Integer.parseInt(classid.toString()));
            cls.setIsflag(Integer.parseInt(isflag.toString()));
            if(pattern.toString().trim().equals("分层班")){
                cls.setSubjectid(Integer.parseInt(subjectid.toString()));
            }
            StringBuilder sqlbuilder=new StringBuilder();
            List<Object> objList=this.classManager.getSaveOrUpdateSql(cls,sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
        }

        if(sqlArrayList!=null&&sqlArrayList.size()>0&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()){
            if(this.classManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                 if(hasClsid!=null){
                     response.getWriter().println("{\"type\":\"success\",\"msg\":\""+hasClsid+"\"}");
                 }else
                      response.getWriter().println("{\"type\":\"success\"}");
             }else{
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，原因：未知!\"}");return;
            }
        }else{
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"没有可添加或修改的班级记录可以操作!\"}");return;
        }
    }

    /**
     * 乐知行，删除方法
     * @param request
     * @param response
     */
    @RequestMapping(params="m=lzxDel",method=RequestMethod.POST)
    public void lzxDel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String clsArrayjson=request.getParameter("clsarrayjson");
        if(clsArrayjson==null||clsArrayjson.toString().trim().length()<1){
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，没有发现您要添加或修改的班级数据Json!\"}");return;
        }
        net.sf.json.JSONArray clsJr=net.sf.json.JSONArray.fromObject(clsArrayjson);
        if(clsJr==null||clsJr.isEmpty()){
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，没有发现您要添加或修改的班级数据Json!\"}");return;
        }
        String noDelClsId=null;
        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        Iterator jrIte=clsJr.iterator();
        while(jrIte.hasNext()){
            net.sf.json.JSONObject clsJo=(net.sf.json.JSONObject)jrIte.next();
            Object timeStr=clsJo.get("timestamp");
            Object schoolid=clsJo.get("lzx_school_id");
            Object classid=clsJo.get("classid");
            Object key=clsJo.get("key");
            //验证相关参数
            if(timeStr==null||timeStr.toString().trim().length()<1||!UtilTool.isNumber(timeStr.toString())){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，登陆时间戳参数缺少!\"}");return;
            }
            if(schoolid==null||schoolid.toString().trim().length()<1||!UtilTool.isNumber(schoolid.toString())){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，分校ID为空!!\"}");return;
            }
            if(classid==null||classid.toString().trim().length()<1||!UtilTool.isNumber(classid.toString())){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，班级ID为空!!\"}");return;
            }
            if(key==null||key.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，key为空!!\"}");
                return;
            }

            //验证是否在三分钟内
            Long ct=Long.parseLong(timeStr.toString());
            Long nt=new Date().getTime();
            double d=(nt-ct)/(1000*60);
            if(d>3){//大于三分钟
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，响应超时!接口三分钟内有效!\"}");return;
            }
            //验证key
            String md5key=timeStr.toString()+schoolid;

            md5key+=classid.toString()+timeStr.toString();
            md5key= MD5_NEW.getMD5ResultCode(md5key);//生成md5加密
            if(!md5key.trim().equals(key.toString().trim())){//如果不一致，则说明非法登陆
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，非法登陆!!\"}");
                return;
            }
            //查看是否存在学生记录
            ClassUser cutmp=new ClassUser();
            cutmp.setClassid(Integer.parseInt(classid.toString()));
            PageResult presult=new PageResult();
            presult.setPageSize(1);
            presult.setPageNo(1);
            List<ClassUser> cuList=this.classUserManager.getList(cutmp,presult);
            if(cuList!=null&&cuList.size()>0){
                noDelClsId=(noDelClsId==null?classid.toString():noDelClsId+","+classid);
                continue;
            }
            //验证通过
            ClassInfo cls=new ClassInfo();
            cls.setClassid(Integer.parseInt(classid.toString()));
            StringBuilder sqlbuilder=new StringBuilder();
            List<Object> objList=this.classManager.getDeleteSql(cls, sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
        }

        if(sqlArrayList!=null&&sqlArrayList.size()>0&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()){
            if(this.classManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                if(noDelClsId!=null&&noDelClsId.toString().trim().length()>0){
                    response.getWriter().println("{\"type\":\"success\",\"msg\":\""+noDelClsId+"\"}");
                }else
                    response.getWriter().println("{\"type\":\"success\"}");
            }else{
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，原因：未知!\"}");
            }
        }else{
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"没有可添加或修改的班级记录可以操作!\"}");
        }
    }
}




















