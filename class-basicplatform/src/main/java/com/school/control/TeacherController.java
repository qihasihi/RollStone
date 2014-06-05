package com.school.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.ClassUserManager;
import com.school.manager.DictionaryManager;
import com.school.manager.GradeManager;
import com.school.manager.TeacherManager;
import com.school.manager.inter.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.ClassInfo;
import com.school.entity.ClassUser;
import com.school.entity.DictionaryInfo;
import com.school.entity.GradeInfo;
import com.school.entity.TeacherInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;

@Controller
@Scope("prototype") 
@RequestMapping(value="/teacher")
public class TeacherController extends BaseController<TeacherInfo>{
//    private ISubjectManager subjectManager;
//    private ITermManager termManager;
    private IGradeManager gradeManager;
    private ITeacherManager teacherManager;
    private IClassUserManager classUserManager;
    private IDictionaryManager dictionaryManager;
    public TeacherController(){
        this.gradeManager=this.getManager(GradeManager.class);
        this.teacherManager=this.getManager(TeacherManager.class);
        this.classUserManager=this.getManager(ClassUserManager.class);
        this.dictionaryManager=this.getManager(DictionaryManager.class);
    }
	/**
	 * 打开查询教师模式窗体
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */ 
	@RequestMapping(params="m=toSetGradeLeader",method=RequestMethod.GET) 
	public ModelAndView toSetTeacherCourse(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String gradeid=request.getParameter("gradeid");
		List<GradeInfo>gradeList=this.gradeManager.getList(null, null);
		List<DictionaryInfo>dicList=this.dictionaryManager.getDictionaryByType("MAX_LEADER_NUM");
		Integer MAX_LEADER_NUM=2;
		if(dicList!=null&&dicList.size()>0)
			MAX_LEADER_NUM=Integer.parseInt(dicList.get(0).getDictionaryvalue());
		request.setAttribute("gradeList",gradeList);
		request.setAttribute("gradeid",gradeid);
		request.setAttribute("max_leader_num",MAX_LEADER_NUM);
		return new ModelAndView("/teacher/dialoglist");
	} 
	
	/**   
	 * 查询教师列表  
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST)
	public void getTeacherListByGrade(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		PageResult pageresult = this.getPageResultParameter(request);
		String gradeid=request.getParameter("gradeid");
		String realname=request.getParameter("realname");
		if(gradeid==null||gradeid.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		GradeInfo g=new GradeInfo(); 
		g.setGradeid(Integer.parseInt(gradeid));
		List<GradeInfo>gradeList=this.gradeManager.getList(g, null);
		
		ClassUser cu=new ClassUser();
		if(gradeList!=null&&gradeList.size()>0){
			cu.setClassgrade(gradeList.get(0).getGradevalue());
		}
		cu.setNorelationtype("学生");
		if(realname!=null&&realname.trim().length()>0){ 
			cu.setRealname(realname);
		} 
		List<ClassUser>teacherList=this.classUserManager.getList(cu, pageresult);
		pageresult.setList(teacherList);
		je.setPresult(pageresult); 
		je.setType("success"); 
		response.getWriter().print(je.toJSON());
	}
	
	/**
	 * 查询教师列表
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxlistByTnOrUn",method=RequestMethod.POST)
	public void getTeacherListByTchnameOrUsername(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		PageResult pageresult = this.getPageResultParameter(request);
		TeacherInfo t = this.getParameter(request, TeacherInfo.class);
		if(t==null)
			t=new TeacherInfo();
		String realname=request.getParameter("realname");
		if(realname!=null&&realname.trim().length()>0)
			t.getUserinfo().setRealname(realname);
		List<TeacherInfo>teacherList=this.teacherManager.getListByTchnameOrUsername(t, pageresult);
		pageresult.setList(teacherList);
		je.setPresult(pageresult); 
		je.setType("success"); 
		response.getWriter().print(je.toJSON());
	}
	
	@RequestMapping(params="m=getTeacher",method=RequestMethod.POST)	
	public void getTeacherList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		String realname=request.getParameter("realname");
		if(!UtilTool.nullOrEmpty(realname)){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());return;
		}
		TeacherInfo teaInfo=new TeacherInfo();
		teaInfo.getUserinfo().setRealname(realname);
		List<TeacherInfo> teaList=this.teacherManager.getList(teaInfo,null);
		je.setObjList(teaList);
		je.setType("success");
		response.getWriter().print(je.toJSON());
	}
}
