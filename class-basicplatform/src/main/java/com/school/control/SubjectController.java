package com.school.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.DictionaryManager;
import com.school.manager.SubjectManager;
import com.school.manager.inter.IDictionaryManager;
import com.school.manager.inter.ISubjectManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.DictionaryInfo;
import com.school.entity.JobInfo;
import com.school.entity.SubjectInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import com.school.util.WriteProperties;

@Controller
@RequestMapping(value="/subject")
public class SubjectController extends BaseController<SubjectInfo>{
    private ISubjectManager subjectManager;
    private IDictionaryManager dictionaryManager;
    public SubjectController(){
        this.subjectManager=this.getManager(SubjectManager.class);
        this.dictionaryManager=this.getManager(DictionaryManager.class);
    }

	@RequestMapping(params="m=list",method=RequestMethod.GET)
	protected ModelAndView getListBy(HttpServletRequest request,ModelMap mp) throws Exception{	
		List<SubjectInfo> subjectList=subjectManager.getList(null, null);
		mp.put("subjectList", subjectList);
		List<DictionaryInfo> dictList = this.dictionaryManager.getDictionaryByType("subject_type");
		request.setAttribute("dictList", dictList);
		return new ModelAndView("/subject/list",mp);		
	}

    @RequestMapping(params="m=checkname",method=RequestMethod.POST)
    protected void checkName(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        JsonEntity je = new JsonEntity();
        String name = request.getParameter("subjectname");
        SubjectInfo subjectInfo = new SubjectInfo();
        subjectInfo.setSubjectname(name);
        List<SubjectInfo> subjectList=subjectManager.getList(subjectInfo, null);
        if(subjectList!=null&&subjectList.size()>0){
            je.setMsg("ѧ�������ظ�����������д");
            response.getWriter().print(je.toJSON());
            return;
        }
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }
	
	@RequestMapping(params="m=add",method=RequestMethod.POST)
	protected ModelAndView add(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
		SubjectInfo subjectInfo=this.getParameter(request, SubjectInfo.class);//�õ��������
		//��֤����
		if(subjectInfo!=null
				&&subjectInfo.getSubjectname()!=null||subjectInfo.getSubjectname().length()>0){
			subjectInfo.setSubjecttype(2);
			this.subjectManager.doSave(subjectInfo);
		}
		List<SubjectInfo> subjectList=subjectManager.getList(null, null);
		mp.put("subjectList", subjectList);
		return new ModelAndView("/subject/list",mp);		
	}
	
	@RequestMapping(params="m=del",method=RequestMethod.GET)
	protected ModelAndView del(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
		SubjectInfo subjectInfo=this.getParameter(request, SubjectInfo.class);//�õ��������
		//��֤����
		if(subjectInfo!=null
				&&subjectInfo.getSubjectid()!=null||subjectInfo.getSubjectid()!=0){
			this.subjectManager.doDelete(subjectInfo);	
		}
		List<SubjectInfo> subjectList=subjectManager.getList(null, null);
		mp.put("subjectList", subjectList);
		return new ModelAndView("/subject/list",mp);		
	}
	

	@RequestMapping(params="m=update",method=RequestMethod.POST)
	protected ModelAndView update(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
		SubjectInfo subjectInfo=this.getParameter(request, SubjectInfo.class);//�õ��������
		//��֤����
		if(subjectInfo!=null
				&&subjectInfo.getSubjectname()!=null
				&&subjectInfo.getSubjectname().length()>0){
			this.subjectManager.doUpdate(subjectInfo);	
		}
		JsonEntity je = new JsonEntity();
		List<SubjectInfo> subjectList=subjectManager.getList(null, null);
		mp.put("subjectList", subjectList);
		return new ModelAndView("/subject/list",mp);		
	}	
	
	@RequestMapping(params="m=setupadd",method=RequestMethod.POST)
	protected void setupadd(HttpServletRequest request,HttpServletResponse response) throws Exception{		
		JsonEntity je = new JsonEntity();
		String subjectname = request.getParameter("subjectname");
		if(subjectname!=null&&subjectname.length()>0){
			SubjectInfo subjectInfo = null;
			//���ֵ�����ݼ�����
			List<String> sqlArrayList = new ArrayList<String>();
			//���sql�ļ���
			List<List<Object>> objArrayList = new ArrayList<List<Object>>();
			 //ƴsql�Ķ���ʹ��ֵ�ö���
			List<Object> objList;
			StringBuilder sql ;	
			
			String[] subjectnames = subjectname.split("-");
			for(int i = 0;i<subjectnames.length;i++){
				if(subjectnames[i].length()>0){
					subjectInfo = new SubjectInfo();
					subjectInfo.setSubjectname(subjectnames[i]);
					subjectInfo.setSubjecttype(2);
					objList = new ArrayList<Object>();
					sql = new StringBuilder();
					objList = this.subjectManager.getSaveSql(subjectInfo, sql);
					objArrayList.add(objList);
					sqlArrayList.add(sql.toString());
				}
			}
			Boolean b = this.subjectManager.doExcetueArrayProc(sqlArrayList, objArrayList);
			if(b){
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
				je.setType("success");
			}else{
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			}
			response.getWriter().print(je.toJSON());	
		}else{
			je.setType("success");
			response.getWriter().print(je.toJSON());
		}
	}
    @RequestMapping(params="m=ajaxlist",method=RequestMethod.POST)
    protected void getAjaxList(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        JsonEntity je = new JsonEntity();
        List<SubjectInfo> subList = this.subjectManager.getList(new SubjectInfo(), null);
        je.setObjList(subList);
        response.getWriter().print(je.toJSON());
    }
}
