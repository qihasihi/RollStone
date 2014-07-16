package com.school.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.SubjectUserManager;
import com.school.manager.inter.ISubjectUserManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.school.control.base.BaseController;
import com.school.entity.SubjectInfo;
import com.school.entity.SubjectUser;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;

@Controller
@Scope("prototype") 
@RequestMapping(value="/subjectuser")
public class SubjectUserController extends BaseController<SubjectInfo> {
	private ISubjectUserManager subjectUserManager;
    public SubjectUserController(){
        this.subjectUserManager=this.getManager(SubjectUserManager.class);
    }
	/**
	 * 设置教师授课科目
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=setTeaSubject",method=RequestMethod.POST)
	public void setTeacherSubject(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je =new JsonEntity(); 
		String useridstr=request.getParameter("useridArray");
		String subjectid=request.getParameter("subjectid");
		if(useridstr==null||useridstr.trim().length()<1
			||subjectid==null||subjectid.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		List<String>sqlListArray=new ArrayList<String>();
		List<List<Object>>objListArray=new ArrayList<List<Object>>();
		List<Object>objList=null;
		StringBuilder sql=null;
		
		String[]useridArray=useridstr.split(",");
		if(useridArray.length>0){
			for (String userid : useridArray) {
				SubjectUser su=new SubjectUser();
				su.getSubjectinfo().setSubjectid(Integer.parseInt(subjectid));
				su.getUserinfo().setRef(userid);
				
				//先删除
				sql=new StringBuilder();
				objList=this.subjectUserManager.getDeleteSql(su, sql);
				if(objList!=null&&sql!=null){
					sqlListArray.add(sql.toString());
					objListArray.add(objList);
				}
				//再添加 
				sql=new StringBuilder();
				objList=this.subjectUserManager.getSaveSql(su, sql);
				if(objList!=null&&sql!=null){
					sqlListArray.add(sql.toString());
					objListArray.add(objList);
				}
				
			}
		}
		
		if(sqlListArray.size()>0&&objListArray.size()>0){
			if(this.subjectUserManager.doExcetueArrayProc(sqlListArray, objListArray)){
				je.setType("success");
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			}else{
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			}
		}else{
			je.setMsg(UtilTool.msgproperty.getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
		}
		response.getWriter().print(je.toJSON());
	}
}
