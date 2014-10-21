package com.school.control.notice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.DictionaryManager;
import com.school.manager.GradeManager;
import com.school.manager.RoleManager;
import com.school.manager.inter.IDictionaryManager;
import com.school.manager.inter.IGradeManager;
import com.school.manager.inter.IRoleManager;
import com.school.manager.inter.notice.INoticeManager;
import com.school.manager.notice.NoticeManager;
import org.springframework.ejb.config.JeeNamespaceHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.ClassYearInfo;
import com.school.entity.DictionaryInfo;
import com.school.entity.GradeInfo;
import com.school.entity.RoleInfo;
import com.school.entity.activity.ActivityInfo;
import com.school.entity.activity.SiteInfo;
import com.school.entity.notice.NoticeInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
@Controller
@RequestMapping(value="/notice")
public class NoticeController extends BaseController<NoticeInfo> {
    private IGradeManager gradeManager;
    private IDictionaryManager dictionaryManager;
    private INoticeManager noticeManager;
    private IRoleManager roleManager;
    public NoticeController(){
        this.gradeManager=this.getManager(GradeManager.class);
        this.dictionaryManager=this.getManager(DictionaryManager.class);
        this.noticeManager=this.getManager(NoticeManager.class);
        this.roleManager=this.getManager(RoleManager.class);
    }

    /**
     * 跳转到管理员公告列表
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=test",method=RequestMethod.GET)
    public ModelAndView toTest(HttpServletRequest request,HttpServletResponse response,ModelMap mp )throws Exception{
        return new ModelAndView("/test/testImg",mp);
    }

	/**
	 * 跳转到管理员公告列表
	 * @param request
	 * @param mp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="m=list",method=RequestMethod.GET) 
	public ModelAndView toNoticeList(HttpServletRequest request,HttpServletResponse response,ModelMap mp )throws Exception{
//		List<RoleInfo> roleList = this.roleManager.getList(null, null);
//		request.setAttribute("role", roleList);
		List gradeList = this.gradeManager.getList(null, null);
        mp.put("gradeList", gradeList);
		List<DictionaryInfo> typeList = this.dictionaryManager.getDictionaryByType("NOTICE_TYPE");
		mp.put("typeList", typeList);
        //得到身份表信息
        List<DictionaryInfo> dicList=this.dictionaryManager.getDictionaryByType("IDENTITY_TYPE");
        mp.put("roleDicList",dicList);

		return new ModelAndView("/notice/notice_list",mp);
	}
	/**
	 * 跳转到用户列表
	 * @param request
	 * @param mp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="m=userlist",method=RequestMethod.GET) 
	public ModelAndView toNoticeUserList(HttpServletRequest request,ModelAndView mp )throws Exception{
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		return new ModelAndView("/notice/notice_user_list");  
	}
	/**
	 * ajax获取公告列表---- 管理员
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST) 
	public void getNoticeList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		PageResult p = this.getPageResultParameter(request);
		p.setOrderBy("n.c_time desc");
		NoticeInfo obj = this.getParameter(request, NoticeInfo.class);
		Date d=new Date();
		System.out.println(d.getTime());
        Integer dcSchoolID=this.logined(request).getDcschoolid();
        obj.setDcschoolid(dcSchoolID);
		List<NoticeInfo> objlist = this.noticeManager.getList(obj, p);
		
		Date d1=new Date();
		System.out.println(d1.getTime());
		System.out.println(d1.getTime()-d.getTime());
		p.setList(objlist);
		je.setType("success");
		je.setPresult(p);
		response.getWriter().print(je.toJSON());
	}
	/**
	 * ajax获取用户列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxuserlist",method=RequestMethod.POST) 
	public void getNoticeUserList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		PageResult p = new PageResult();
		p.setOrderBy(" n.is_top asc,n.c_time desc");
		NoticeInfo obj = this.getParameter(request, NoticeInfo.class);
		obj.setCuserid(this.logined(request).getRef());
        obj.setDcschoolid(this.logined(request).getDcschoolid());
		List<NoticeInfo> objlist = this.noticeManager.getUserList(obj, p);
		p.setList(objlist);
		je.setType("success");
		je.setPresult(p);
		response.getWriter().print(je.toJSON());
	}
	/**
	 * 跳转到添加页面
	 * @param request
	 * @param mp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="m=toadd",method=RequestMethod.GET) 
	public ModelAndView toAddPage(HttpServletRequest request,ModelAndView mp )throws Exception{
		List<RoleInfo> roleList = this.roleManager.getList(null, null);
		request.setAttribute("role", roleList);
		List gradeList = this.gradeManager.getList(null, null);
		request.setAttribute("gradeList", gradeList);
		List<DictionaryInfo> typeList = this.dictionaryManager.getDictionaryByType("NOTICE_TYPE");
		request.setAttribute("typeList", typeList);
		return new ModelAndView("/notice/notice_add");  
	}
	/**
	 * 执行添加公告
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=doadd",method=RequestMethod.POST) 
	public void doAddNotice(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		NoticeInfo obj = this.getParameter(request, NoticeInfo.class);
		if(obj==null){
			je.setMsg("系统异常，数据没有接收到，请重试");
			response.getWriter().print(je.toJSON());return;
		}else{
			if(!UtilTool.nullOrEmpty(obj.getNoticetitle())){
				je.setMsg("请填写公告标题");
				response.getWriter().print(je.toJSON());return;
			}
			String ref = UUID.randomUUID().toString();
			obj.setRef(ref);
			String userid = this.logined(request).getRef();
			obj.setCuserid(userid);
            obj.setDcschoolid(this.logined(request).getDcschoolid());
            //整理执行语句
            List<String> sqlList=new ArrayList<String>();
            List<List<Object>> objArrayList=new ArrayList<List<Object>>();
            StringBuilder sqlbuilder=new StringBuilder();
            List<Object> objList = new ArrayList<Object>();
            objList=this.noticeManager.getSaveSql(obj,sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                sqlList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
            //更新程序
            if(obj.getNoticecontent()!=null&&obj.getNoticecontent().trim().length()>0){
                //得到theme_content的更新语句
                this.noticeManager.getArrayUpdateLongText("notice_info", "ref", "notice_content"
                        , obj.getNoticecontent(), obj.getRef(), sqlList, objArrayList);
            }
			Boolean b = this.noticeManager.doExcetueArrayProc(sqlList,objArrayList);
			if(b){			
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
				je.setType("success");
			}else{
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			}
			response.getWriter().print(je.toJSON());
		}
	}
	/**
	 * 跳转到修改页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="m=toupd",method=RequestMethod.POST) 
	public void toUpdatePage(HttpServletRequest request,HttpServletResponse response )throws Exception{
		JsonEntity je = new JsonEntity();
		String ref = request.getParameter("ref");
        //System.out.print("*******aaa***ccc***bbb***"+ref);
		NoticeInfo obj = new NoticeInfo();
		obj.setRef(ref);
		List<NoticeInfo> objList = this.noticeManager.getList(obj, null);
		if(objList!=null){
			je.setObjList(objList);
			je.setType("success");
			response.getWriter().print(je.toJSON());
		}else{
			je.setType("error");
			je.setMsg("未知错误，请刷新页面重试");
		}		
		//request.setAttribute("ntc", objList.get(0));
	}
	/**
	 * 执行修改公告
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=doupd",method=RequestMethod.POST) 
	public void doUpdNotice(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		NoticeInfo obj = this.getParameter(request, NoticeInfo.class);
		if(obj.getRef()==null){
			je.setMsg("系统异常，数据没有接收到缺少标识，请重试");
			je.setType("error");
		}else{
            //整理执行语句
            List<String> sqlList=new ArrayList<String>();
            List<List<Object>> objArrayList=new ArrayList<List<Object>>();
            StringBuilder sqlbuilder=new StringBuilder();
            List<Object> objList = new ArrayList<Object>();
            objList=this.noticeManager.getUpdateSql(obj,sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                sqlList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
            //更新程序
            if(obj.getNoticecontent()!=null&&obj.getNoticecontent().trim().length()>0){
                //得到theme_content的更新语句
                this.noticeManager.getArrayUpdateLongText("notice_info", "ref", "notice_content"
                        , obj.getNoticecontent(), obj.getRef(), sqlList, objArrayList);
            }
            Boolean b = this.noticeManager.doExcetueArrayProc(sqlList,objArrayList);
			if(b){			
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
				je.setType("success");
			}else{
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			}
		}
        response.getWriter().print(je.toJSON());
	}
	/**
	 * 执行删除公告
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=dodel",method=RequestMethod.POST) 
	public void doDelNotice(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		String ref = request.getParameter("ref");
		if(ref==null){
			je.setMsg("系统异常，数据没有接收到缺少标识，请重试");
			je.setType("error");
		}else{
			String[] refs = ref.split("!");
			Boolean b = false;
			if(refs.length>1){
				//存放值得数据集集合
				List<String> sqlArrayList = new ArrayList<String>();
				//存放sql的集合
				List<List<Object>> objArrayList = new ArrayList<List<Object>>();
			    //拼sql的对象和存放值得对象
				List<Object> objList;
				StringBuilder sql ;			
				for(int i =0 ;i<refs.length;i++){
					NoticeInfo obj = new NoticeInfo();
					obj.setRef(refs[i]);
					objList= new ArrayList<Object>();
					sql = new StringBuilder();
					objList = this.noticeManager.getDeleteSql(obj, sql);
					sqlArrayList.add(sql.toString());
					objArrayList.add(objList);
				}
//				NoticeInfo obj = new NoticeInfo();
//				obj.setRef("ref");
				b = this.noticeManager.doExcetueArrayProc(sqlArrayList, objArrayList);
			}else{
				NoticeInfo obj = new NoticeInfo();
				obj.setRef(ref);
				b=this.noticeManager.doDelete(obj);
			}
			
			if(b){			
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
				je.setType("success");
			}else{
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			}
			response.getWriter().print(je.toJSON());
		}
	}
	/**
	 * 执行浏览点击次数操作
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=doclick",method=RequestMethod.POST) 
	public void doClickNotice(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		NoticeInfo obj = this.getParameter(request, NoticeInfo.class);
		if(obj.getRef()==null){
			je.setMsg("系统异常，数据没有接收到缺少标识，请重试");
			je.setType("error");
		}else{
			Boolean b = this.noticeManager.noticeClick(obj.getRef());
			
		}
	}
	/**
	 * 跳转到详细页面
	 * @param request
	 * @param mp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="m=detail",method=RequestMethod.GET) 
	public ModelAndView doNoticeDetail(HttpServletRequest request,HttpServletResponse response,ModelAndView mp ) throws Exception{
		JsonEntity je = new JsonEntity();
		NoticeInfo obj = this.getParameter(request, NoticeInfo.class);		
		Boolean b = this.noticeManager.noticeClick(obj.getRef());
		List<NoticeInfo> list = this.noticeManager.getList(obj, null);
		if(list.size()<1){
			je.setMsg("原内容已删除!");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		request.setAttribute("notice", list.get(0));		
		return new ModelAndView("/notice/notice_detail");  
	}
	@RequestMapping(params="m=tomore",method=RequestMethod.GET) 
	public ModelAndView toMoreNotice(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
		JsonEntity je = new JsonEntity();
		NoticeInfo ntice=this.getParameter(request, NoticeInfo.class);
		if(!UtilTool.nullOrEmpty(ntice.getNoticetype())){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.getAlertMsgAndCloseWin());return null;
		}
		//this.noticeManager.getUserList(ntice, null);
		return new ModelAndView("/notice/notice_user_list");
	}
	@RequestMapping(params="m=usernoticelist",method=RequestMethod.POST) 
	public void getAjaxUserList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je=new JsonEntity();
		NoticeInfo ntice=this.getParameter(request, NoticeInfo.class);
		if(ntice==null||ntice.getNoticetype()==null||ntice.getNoticetype().trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());return;
		}
		PageResult presult=this.getPageResultParameter(request);
		if(presult.getOrderBy()==null||presult.getOrderBy()=="")
			presult.setOrderBy("n.C_TIME DESC");//n.IS_TOP ASC,
		List<NoticeInfo> nList=this.noticeManager.getUserList(ntice, presult);
		presult.setList(nList);
		je.setPresult(presult);
		je.setType("success");
		response.getWriter().print(je.toJSON());
	}
}
