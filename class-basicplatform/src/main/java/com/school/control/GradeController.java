package com.school.control;

import com.school.control.base.BaseController;
import com.school.entity.GradeInfo;
import com.school.entity.TermInfo;
import com.school.manager.inter.IGradeManager;
import com.school.manager.inter.ITermManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
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

@Controller
@RequestMapping(value="/grade") 
public class GradeController extends BaseController<GradeInfo> {
    @Autowired
    private IGradeManager gradeManager;
    @Autowired
    private ITermManager termManager;

	@RequestMapping(params="m=list",method=RequestMethod.GET) 
	public ModelAndView toGradeList(HttpServletRequest request,ModelAndView mp )throws Exception{
		return new ModelAndView("/grade/list");
	}

    /**
     * ��ȡList
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=ajaxlist",method=RequestMethod.POST)
    public void AjaxGetList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        GradeInfo gradeinfo = this.getParameter(request, GradeInfo.class);
        PageResult pageresult = this.getPageResultParameter(request);
        List<GradeInfo> gradeList =gradeManager.getList(gradeinfo, pageresult);
        pageresult.setList(gradeList);
        JsonEntity je = new JsonEntity();
        je.setType("success");
        je.setPresult(pageresult);
        response.getWriter().print(je.toJSON());
    }


    /**
     * ��Դϵͳ��ȡList
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=ajaxReslist",method=RequestMethod.POST)
    public void AjaxResGetList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        GradeInfo g=new GradeInfo();
        List<GradeInfo> gradeList=this.gradeManager.getList(g,null);
        List<GradeInfo> returnGrade=null;
        if(!this.validateRole(request,UtilTool._ROLE_SXJW_ID)){
            //�õ���ǰѧ��
            TermInfo tm=this.termManager.getAutoTerm();
            List<GradeInfo> userGradeList=this.gradeManager.getListByUserYear(this.logined(request).getUserid(),tm.getYear());
            if(userGradeList!=null&&userGradeList.size()>0&&gradeList!=null&&gradeList.size()>0){
                returnGrade=new ArrayList<GradeInfo>();
                   for(GradeInfo gentity:userGradeList){
                       if(gentity!=null){
                           //��� ����һ�꼶��Сѧһ�꼶����ѧһ�꼶��������ȡ��һ���֣�����ƥ��
                           String firstCode=gentity.getGradevalue().substring(0,1);
                           for(GradeInfo e:gradeList){
                               if(e!=null&&e.getGradeid()!=null&&e.getGradevalue().indexOf(firstCode)>=0){
                                    if(!returnGrade.contains(e))
                                        returnGrade.add(e);
                               }
                           }
                       }
                   }
            }
        }else
            returnGrade=gradeList;
        JsonEntity je = new JsonEntity();
        je.setType("success");
        je.setObjList(returnGrade);
        response.getWriter().print(je.toJSON());
    }


    /**
	 * ȥ�޸�
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=toupd",method=RequestMethod.POST) 
	public void toUpdateClassyear(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		GradeInfo gradeinfo = this.getParameter(request, GradeInfo.class);
		if(gradeinfo.getGradeid()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("GRADE_ID_EMPTY")); 
			response.getWriter().print(je.toJSON());
			return;
		}
		List<GradeInfo> gradeList = gradeManager.getList(gradeinfo, null);
		je.setObjList(gradeList); 
		je.setType("success");
		response.getWriter().print(je.toJSON());  
	}
	
	/**
	 * ���
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxsave",method=RequestMethod.POST)
	public void doSubmitAddClassyear(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		GradeInfo gradeinfo = this.getParameter(request, GradeInfo.class);
		if(gradeinfo.getGradename()==null||
				gradeinfo.getGradename().trim().length()<1||
				gradeinfo.getGradevalue()==null||gradeinfo.getGradevalue().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR")); 
			response.getWriter().print(je.toJSON());
			return;
		}
		//�Ѵ��ڵ�ǰ���� �޷����
		List<GradeInfo>gradeList=this.gradeManager.getList(gradeinfo, null);
		if(gradeList!=null&&gradeList.size()>0){
			je.setMsg(UtilTool.msgproperty.getProperty("DATA_EXISTS"));
			response.getWriter().print(je.toJSON());
			return;
		}
		if(gradeManager.doSave(gradeinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());  
	}  
	 
	
	/**
	 * �޸�
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params="m=modify",method=RequestMethod.POST)
	public void doSubmitUpdateRole(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		GradeInfo gradeinfo = this.getParameter(request, GradeInfo.class);
		if(gradeinfo.getGradename()==null||
				gradeinfo.getGradeid()==null||
				gradeinfo.getGradevalue()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		if(gradeManager.doUpdate(gradeinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success"); 
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());  
	}  
	
	/**
	 * ɾ��
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params="m=del",method=RequestMethod.POST)
	public void doDeleteRole(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		GradeInfo gradeinfo = this.getParameter(request, GradeInfo.class);
		if(gradeinfo.getGradeid()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("GRADE_ID_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}
		if(gradeManager.doDelete(gradeinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());    
	}  
}
