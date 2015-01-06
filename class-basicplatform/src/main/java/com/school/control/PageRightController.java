package com.school.control;

import com.school.control.base.BaseController;
import com.school.entity.ColumnInfo;
import com.school.entity.PageRightInfo;
import com.school.entity.RightUser;
import com.school.manager.inter.IColumnManager;
import com.school.manager.inter.IPageRightManager;
import com.school.manager.inter.IRightUserManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Controller
@RequestMapping(value="/pageright")
public class PageRightController extends BaseController<PageRightInfo>{
    @Autowired
	private IColumnManager columnManager;
    @Autowired
    private IPageRightManager pageRightManager;
    @Autowired
    private IRightUserManager rightUserManager;

	
	@RequestMapping(params="m=list",method=RequestMethod.GET)
	public ModelAndView getListBy(HttpServletRequest request,ModelMap mp) throws Exception{
//		List<PageRightInfo> pagerightList=this.pageRightManager.getList(null, null);
//		mp.put("prList", pagerightList);
		//得到 所有的栏目。
		ColumnInfo colInfo=new ColumnInfo();		
		List<ColumnInfo> colList=this.columnManager.getList(colInfo, null);
		mp.put("columnList", colList);
		return new ModelAndView("/pageright/pagerightlist",mp);		
	}
	
	@RequestMapping(params="m=ajaxsave",method=RequestMethod.POST)
	public void add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je=new JsonEntity();
		PageRightInfo prinfo=this.getParameter(request, PageRightInfo.class);//得到常规参数
		//验证参数
		if(prinfo.getPagename()==null||prinfo.getPagename().trim().length()<1
				||prinfo.getPagevalue()==null||prinfo.getPagevalue().trim().length()<1
				||prinfo.getColumnid()==null
				||!UtilTool.isNumber(prinfo.getColumnid().trim())
				){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		String nextRefId=this.pageRightManager.getNextId();
		prinfo.setRef(nextRefId);
		if(prinfo.getRemark()!=null&&prinfo.getRemark().trim().length()<1)
			prinfo.setRemark(null);
		//得到栏目
		ColumnInfo coltmp=new ColumnInfo();
		coltmp.setColumnid(Integer.parseInt(prinfo.getColumnid().trim()));
		List<ColumnInfo> colList=this.columnManager.getList(coltmp, null);
		if(colList==null||colList.size()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
			response.getWriter().print(je.toJSON());return;
		}		
		if(pageRightManager.doSave(prinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());
	}
	
	/**
	 * AjaxList
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST) 
	public void toRightList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		PageRightInfo pr = this.getParameter(request, PageRightInfo.class);
		PageResult presult=this.getPageResultParameter(request);
		presult.setOrderBy(" p.PAGE_RIGHT_ID DESC ");
		List<PageRightInfo> pageRightList = pageRightManager.getList(pr, presult);
		JsonEntity je = new JsonEntity();
		je.setType("success");
		presult.setList(pageRightList);
		je.setPresult(presult);
		response.getWriter().print(je.toJSON()); 
	}
	
	/**
	 * 去修改
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=toupd",method=RequestMethod.POST) 
	public void toUpdateRight(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		PageRightInfo pr = this.getParameter(request, PageRightInfo.class);
		if(pr.getRef()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("PAGERIGHT_PAGERIGHTID_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}
		List<PageRightInfo> pList = pageRightManager.getList(pr, null);
		je.setObjList(pList); 
		je.setType("success");
		response.getWriter().print(je.toJSON());  
	}
	
	
	/**
	 * 修改
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params="m=modify",method=RequestMethod.POST)
	public void doSubmitUpdateRight(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity();
		PageRightInfo prinfo=this.getParameter(request, PageRightInfo.class);//得到常规参数
		//验证参数
		if(prinfo.getRef()==null||prinfo.getRef().trim().length()<1
				||prinfo.getPagename()==null||prinfo.getPagename().trim().length()<1
				||prinfo.getPagevalue()==null
				||prinfo.getColumnid()==null||prinfo.getColumnid().trim().length()<1
				||!UtilTool.isNumber(prinfo.getColumnid())){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		if(prinfo.getRemark()==null||prinfo.getRemark().trim().length()<1)
			prinfo.setRemark(null);
		PageRightInfo prinfo1=new PageRightInfo();
		prinfo1.setRef(prinfo.getRef());
		List<PageRightInfo> pgList=this.pageRightManager.getList(prinfo1, null);
		if(pgList==null||pgList.size()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
			response.getWriter().print(je.toJSON());return;
		}
		//得到栏目
		ColumnInfo coltmp=new ColumnInfo();
		coltmp.setColumnid(Integer.parseInt(prinfo.getColumnid().trim()));
		List<ColumnInfo> colList=this.columnManager.getList(coltmp, null);
		if(colList==null||colList.size()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
			response.getWriter().print(je.toJSON());return;
		}
		
		if(pageRightManager.doUpdate(prinfo)){
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
	public void doDeleteRight(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		PageRightInfo prinfo=this.getParameter(request, PageRightInfo.class);//得到常规参数
		if(prinfo.getRef()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("PAGERIGHT_PAGERIGHTID_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}
		//验证REF
		PageRightInfo pr1info=new PageRightInfo();
		pr1info.setRef(prinfo.getRef());
		List<PageRightInfo> prList=this.pageRightManager.getList(pr1info, null);
		if(prList==null||prList.size()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
			response.getWriter().print(je.toJSON());return;
		}
		
		if(pageRightManager.doDelete(prinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());    
	} 
	
	/**
	 * 模式窗体  此方法关联以下三个功能  
	 * 1：角色页面指定权限 
	 * 2：用户管理页面批量指定权限
	 * 3：用户管理页根据user_id单独指定权限 
	 * @param request
	 * @param mp 
	 * @return
	 * @throws Exception
	 */ 
	@RequestMapping(params="m=dialoglist",method=RequestMethod.GET) 
	public ModelAndView toDialogPage(HttpServletRequest request,ModelMap mp)throws Exception{
		String userid=request.getParameter("userid");
		//如果给一个用户指定权限，会默认选中该用户当前所具有的权限 
		if(userid!=null&&UtilTool.isNumber(userid)){
			RightUser ru=new RightUser();
			ru.getUserinfo().setUserid(Integer.parseInt(userid));
			List<RightUser>prDataList=this.rightUserManager.getList(ru, null);
			mp.put("prDataList", prDataList); 
		} 
		
		List<PageRightInfo> pagerightList=this.pageRightManager.getList(null, null); 
		mp.put("prList", pagerightList);
		return new ModelAndView("/pageright/dialoglist",mp);
	}

}
