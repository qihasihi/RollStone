package com.school.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.DictionaryManager;
import com.school.manager.SubjectManager;
import com.school.manager.inter.IDictionaryManager;
import com.school.manager.inter.ISubjectManager;
import com.school.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.DictionaryInfo;
import com.school.entity.JobInfo;
import com.school.entity.SubjectInfo;

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
            je.setMsg("学科名称重复，请重新填写");
            response.getWriter().print(je.toJSON());
            return;
        }
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }
	
	@RequestMapping(params="m=add",method=RequestMethod.POST)
	protected ModelAndView add(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
		SubjectInfo subjectInfo=this.getParameter(request, SubjectInfo.class);//得到常规参数
		//验证参数
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
		SubjectInfo subjectInfo=this.getParameter(request, SubjectInfo.class);//得到常规参数
		//验证参数
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
		SubjectInfo subjectInfo=this.getParameter(request, SubjectInfo.class);//得到常规参数
		//验证参数
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
			//存放值得数据集集合
			List<String> sqlArrayList = new ArrayList<String>();
			//存放sql的集合
			List<List<Object>> objArrayList = new ArrayList<List<Object>>();
			 //拼sql的对象和存放值得对象
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

    @RequestMapping(params="m=addLzxSubject",method=RequestMethod.POST)
    protected void addLzxSubject(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        String lzxschoolid=request.getParameter("lzx_school_id");
        String lzxsubjectid=request.getParameter("subject_id");
        String lzxsubjectname=request.getParameter("subject_name");
        String timestamp=request.getParameter("timestamp");
        String checkcode=request.getParameter("checkcode");
        String md5key = lzxschoolid+lzxsubjectid+lzxsubjectname+timestamp;
        String key = MD5_NEW.getMD5ResultCode(md5key);
        if(!checkcode.trim().equals(key)){
            response.getWriter().print("[{\"status\":\"error\",\"message\":\"验证失败，非法登录\"}]");
            return;
        }
        SubjectInfo si = new SubjectInfo();
        si.setSubjectname(lzxsubjectname);
        List<SubjectInfo> subList = this.subjectManager.getList(si,null);
        StringBuilder sb=new StringBuilder();
        if(subList!=null&&subList.size()>0){
            si=new SubjectInfo();
            si.setLzxsubjectid(Integer.parseInt(lzxsubjectid));
            si.setSubjectid(subList.get(0).getSubjectid());
            Boolean b = this.subjectManager.doUpdate(si);
            if(b){
               sb.append("[{\"status\":\"success\"}]");
            }else{
               sb.append("[{\"status\":\"error\",\"message\":\"添加失败，请稍后重试\"}]");
            }
        }else{
            si.setLzxsubjectid(Integer.parseInt(lzxsubjectid));
            si.setSubjectname(lzxsubjectname);
            Boolean b = this.subjectManager.doSave(si);
            if(b){
                sb.append("[{\"status\":\"success\"}]");
            }else{
                sb.append("[{\"status\":\"error\",\"message\":\"添加失败，请稍后重试\"}]");
            }
        }
        response.getWriter().print(sb.toString());
    }

    @RequestMapping(params="m=updLzxSubject",method=RequestMethod.POST)
    protected void updLzxSubject(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        JsonEntity je = new JsonEntity();
        String lzxschoolid=request.getParameter("lzx_school_id");
        String lzxsubjectid=request.getParameter("subject_id");
        String lzxsubjectname=request.getParameter("subject_name");
        String timestamp=request.getParameter("timestamp");
        String checkcode=request.getParameter("checkcode");
        String md5key = lzxschoolid+lzxsubjectid+lzxsubjectname+timestamp;
        String key = MD5_NEW.getMD5ResultCode(md5key);
        if(!checkcode.trim().equals(key)){
            response.getWriter().print("[{\"status\":\"error\",\"message\":\"验证失败，非法登录\"}]");
            return;
        }
        SubjectInfo si = new SubjectInfo();
        si.setLzxsubjectid(Integer.parseInt(lzxsubjectid));
        List<SubjectInfo> subList = this.subjectManager.getList(si,null);
        StringBuilder sb=new StringBuilder();
        if(subList!=null&&subList.size()>0){
            si=new SubjectInfo();
            si.setSubjectname(lzxsubjectname);
            si.setSubjectid(subList.get(0).getSubjectid());
            Boolean b = this.subjectManager.doUpdate(si);
            if(b){
                sb.append("[{\"status\":\"success\"}]");
            }else{
                sb.append("[{\"status\":\"error\",\"message\":\"修改失败，请稍后重试\"}]");
            }
        }else{
            sb.append("[{\"status\":\"error\",\"message\":\"无此学科信息，请确认已添加后重试\"}]");
        }
        response.getWriter().print(sb.toString());
    }

    @RequestMapping(params="m=delLzxSubject",method=RequestMethod.POST)
    protected void delLzxSubject(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        JsonEntity je = new JsonEntity();
        String lzxschoolid=request.getParameter("lzx_school_id");
        String lzxsubjectid=request.getParameter("subject_id");
        String timestamp=request.getParameter("timestamp");
        String checkcode=request.getParameter("checkcode");
        String md5key = lzxschoolid+lzxsubjectid+timestamp;
        String key = MD5_NEW.getMD5ResultCode(md5key);
        if(!checkcode.trim().equals(key)){
            response.getWriter().print("[{\"status\":\"error\",\"message\":\"验证失败，非法登录\"}]");
            return;
        }
        SubjectInfo si = new SubjectInfo();
        si.setLzxsubjectid(Integer.parseInt(lzxsubjectid));
        List<SubjectInfo> subList = this.subjectManager.getList(si,null);
        StringBuilder sb = new StringBuilder();
        if(subList!=null&&subList.size()>0){
            si=new SubjectInfo();
            si.setSubjectid(subList.get(0).getSubjectid());
            Boolean b = this.subjectManager.doDelete(si);
            if(b){
                sb.append("[{\"status\":\"success\"}]");
            }else{
                sb.append("[{\"status\":\"error\",\"message\":\"删除失败，请稍后重试\"}]");
            }
        }else{
            sb.append("[{\"status\":\"error\",\"message\":\"无此学科信息，已删除或者未添加成功\"}]");
        }
        response.getWriter().print(sb.toString());
    }
}
