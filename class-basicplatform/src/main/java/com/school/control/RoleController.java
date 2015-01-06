package com.school.control;

import com.school.control.base.BaseController;
import com.school.entity.*;
import com.school.manager.inter.*;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value="/role")
public class RoleController extends BaseController<RoleInfo>{
    @Autowired
	private IRoleManager roleManager;
    @Autowired
    private IColumnManager columnManager;
    @Autowired
    private IDictionaryManager dictionaryManager;
    @Autowired
    private IRoleColumnRightManager roleColumnRightManager;
    @Autowired
    private IIdentityManager identityManager;

	@RequestMapping(params="m=list",method=RequestMethod.GET) 
	public ModelAndView toRoleList(HttpServletRequest request,ModelMap mp )throws Exception{
		List<RoleInfo> roleList = roleManager.getList(new RoleInfo(), null);
		List<DictionaryInfo>identityList=this.dictionaryManager.getDictionaryByType("IDENTITY_TYPE");
		ColumnInfo colInfo=new ColumnInfo();		
		List<ColumnInfo>columnList=this.columnManager.getList(colInfo, null);
		mp.put("identityList", identityList);
		mp.put("columnList", columnList);
		mp.put("roleList", roleList);
		return new ModelAndView("/rolepage/list");
	} 
	 
	/** 
	 * AjaxList
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST) 
	public void toRoleList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		RoleInfo roleinfo = this.getParameter(request, RoleInfo.class);
		PageResult pageresult = this.getPageResultParameter(request);
		List<RoleInfo> roleList = roleManager.getList(roleinfo, pageresult);
		pageresult.setList(roleList);
		JsonEntity je = new JsonEntity();
		je.setType("success");
		je.setPresult(pageresult);
		response.getWriter().print(je.toJSON()); 
	}
	
	
	/**
	 * ȥ�޸�  
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=toupd",method=RequestMethod.POST) 
	public void toUpdateRole(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		RoleInfo roleinfo = this.getParameter(request, RoleInfo.class);
		if(roleinfo.getRoleid()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("ROLE_ROLEID_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}
		List<RoleInfo> roleList = roleManager.getList(roleinfo, null);
		RoleColumnRightInfo rr=new RoleColumnRightInfo();
		rr.setRoleid(roleinfo.getRoleid());
		List<RoleColumnRightInfo>roleRightList=this.roleColumnRightManager.getList(rr, null);
		je.getObjList().add(roleList.get(0));
		je.getObjList().add(roleRightList);
		je.setType("success");
		response.getWriter().print(je.toJSON());  
	}  
	
	/**
	 * ���
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxsave",method=RequestMethod.POST)
	public void doSubmitAddRole(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		RoleInfo roleinfo = this.getParameter(request, RoleInfo.class);
		String rightstr=request.getParameter("rightstr");
		if(roleinfo.getRolename()==null||roleinfo.getRolename().trim().length()<1
			||roleinfo.getIdentityname()==null
			||roleinfo.getIdentityname().trim().length()<1
			||rightstr==null||rightstr.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR")); 
			response.getWriter().print(je.toJSON());
			return;
		}
		RoleInfo r=new RoleInfo();
		r.setRolename(roleinfo.getRolename());
		List<RoleInfo>rList=this.roleManager.getList(r, null);
		if(rList!=null&&rList.size()>0){
			je.setMsg("�˽�ɫ�Ѵ���!");
			response.getWriter().print(je.toJSON());
			return;
		}
		roleinfo.setFlag(0);
		
		List<Object>objList=null;
		List<List<Object>>objListArray=new ArrayList<List<Object>>();
		List<String>sqlList=new ArrayList<String>();
		StringBuilder sql=null;
		
		if(roleManager.doSave(roleinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
			
			RoleInfo rs=new RoleInfo();  
			rs.setRolename(roleinfo.getRolename());
			List<RoleInfo>rsList=this.roleManager.getList(rs, null);
			if(rsList!=null&&rsList.size()>0){
				//���������ɫ����
				IdentityInfo identity=new IdentityInfo();
				identity.setRoleid(rsList.get(0).getRoleid());
				identity.setIdentityname(roleinfo.getIdentityname());
				identity.setRef(this.identityManager.getNextId());
				identity.setMuserid(this.logined(request).getRef());
				sql=new StringBuilder();
				objList=this.identityManager.getSaveSql(identity, sql);
				if(objList!=null&&objList.size()>0){
					objListArray.add(objList);
					sqlList.add(sql.toString());
				}
				
				String[]columnrightArr=rightstr.split(",");
				for (String columnrightid : columnrightArr) {
					RoleColumnRightInfo rr=new RoleColumnRightInfo();
					rr.setRoleid(rsList.get(0).getRoleid());
					rr.setColumnrightid(Integer.parseInt(columnrightid));
					rr.setRef(this.roleColumnRightManager.getNextId());
					rr.setMuserid(this.logined(request).getRef());
					sql=new StringBuilder();
					objList=this.roleColumnRightManager.getSaveSql(rr, sql);
					if(objList!=null&&objList.size()>0){
						objListArray.add(objList);
						sqlList.add(sql.toString());
					}
				}
				if(this.roleColumnRightManager.doExcetueArrayProc(sqlList, objListArray)){
					System.out.println("��ӽ�ɫ--��ӽ�ɫȨ�޳ɹ�");
				}else{
					System.out.println("��ӽ�ɫ--��ӽ�ɫȨ��ʧ��");
				}
			}

			
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
		JsonEntity je=new JsonEntity();
		RoleInfo roleinfo = this.getParameter(request, RoleInfo.class);
		String rightstr=request.getParameter("rightstr");
		if(roleinfo.getRoleid()==null||!UtilTool.isNumber(roleinfo.getRoleid().toString())
			||roleinfo.getRolename()==null||roleinfo.getRolename().trim().length()<1
			||roleinfo.getIdentityname()==null
			||roleinfo.getIdentityname().trim().length()<1
			||rightstr==null||rightstr.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR")); 
			response.getWriter().print(je.toJSON());
			return;
		}
		RoleInfo r=new RoleInfo();
		r.setRoleid(roleinfo.getRoleid());
		List<RoleInfo>rList=this.roleManager.getList(r, null);
		if(rList==null||rList.size()<1){
			je.setMsg("�˽�ɫ�Ѳ�����!");
			response.getWriter().print(je.toJSON());
			return;
		}

        RoleInfo rsel=new RoleInfo();
        rsel.setRolename(roleinfo.getRolename());
        List<RoleInfo>rrList=this.roleManager.getList(rsel, null);
        if(rrList!=null&&rrList.size()>0){
            je.setMsg("�˽�ɫ�Ѵ���!");
            response.getWriter().print(je.toJSON());
            return;
        }


		roleinfo.setFlag(0);
		List<Object>objList=null;
		List<List<Object>>objListArray=new ArrayList<List<Object>>();
		List<String>sqlList=new ArrayList<String>();
		StringBuilder sql=null;
		
		if(roleManager.doUpdate(roleinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
			
			if(rList!=null&&rList.size()>0){
				//ɾ����ݹ���
				IdentityInfo identitydel=new IdentityInfo();
				identitydel.setRoleid(rList.get(0).getRoleid());
				sql=new StringBuilder();
				objList=this.identityManager.getDeleteSql(identitydel, sql);
				if(objList!=null&&objList.size()>0){
					objListArray.add(objList);
					sqlList.add(sql.toString());
				}
				//���������ɫ����
				IdentityInfo identity=new IdentityInfo();
				identity.setRoleid(rList.get(0).getRoleid());
				identity.setIdentityname(roleinfo.getIdentityname());
				identity.setRef(this.identityManager.getNextId());
				identity.setMuserid(this.logined(request).getRef());
				sql=new StringBuilder();
				objList=this.identityManager.getSaveSql(identity, sql);
				if(objList!=null&&objList.size()>0){
					objListArray.add(objList);
					sqlList.add(sql.toString());
				}
				
				//ɾ����ɫȨ�޺������
				RoleColumnRightInfo rdelete=new RoleColumnRightInfo();
				rdelete.setRoleid(roleinfo.getRoleid());
				sql=new StringBuilder();
				objList=this.roleColumnRightManager.getDeleteSql(rdelete, sql);
				if(objList!=null&&objList.size()>0){
					objListArray.add(objList);
					sqlList.add(sql.toString());
				}
				
				String[]columnrightArr=rightstr.split(",");
				for (String columnrightid : columnrightArr) {
					RoleColumnRightInfo rr=new RoleColumnRightInfo();
					rr.setRoleid(rList.get(0).getRoleid());
					rr.setColumnrightid(Integer.parseInt(columnrightid));
					rr.setRef(this.roleColumnRightManager.getNextId());
					rr.setMuserid(this.logined(request).getRef());
					sql=new StringBuilder();
					objList=this.roleColumnRightManager.getSaveSql(rr, sql);
					if(objList!=null&&objList.size()>0){
						objListArray.add(objList);
						sqlList.add(sql.toString());
					}
				}
				if(objListArray!=null&&objListArray.size()>0){
					if(this.roleColumnRightManager.doExcetueArrayProc(sqlList, objListArray)){
						System.out.println("��ӽ�ɫ--��ӽ�ɫȨ�޳ɹ�");
					}else{
						System.out.println("��ӽ�ɫ--��ӽ�ɫȨ��ʧ��");
					}
				}
			}
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
		RoleInfo roleinfo = this.getParameter(request, RoleInfo.class);
		if(roleinfo.getRoleid()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("ROLE_ROLEID_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}
		if(roleManager.doDelete(roleinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());    
	}  
	
	
	@RequestMapping(params="m=uploadImg",method=RequestMethod.POST)
	public void uploadImg(MultipartHttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
//		MultipartHttpServletRequest multipartrequest = (MultipartHttpServletRequest)request;
//		CommonsMultipartFile file = (CommonsMultipartFile)multipartrequest.getFile("upload");
//		String filename = file.getOriginalFilename();
		if(this.getUpload(request)==null||this.getUpload(request).length<1){
			je.setMsg("ϵͳδ�õ����ϴ����ļ�!");
			response.getWriter().print(je.toJSON());
			return;
		}
		List<String>fname=this.getFileArrayName(this.getUpload(request).length);
		if(fname!=null&&fname.size()>0){
			if(this.fileupLoad(request)){
				for(int i=0;i<fname.size();i++){
					String currentPathFname=fname.get(i);
					File f=new File(UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+currentPathFname);
				}
			}else{
				je.setMsg("�ļ��ϴ�ʧ��!");
			}
		}else{
			je.setMsg("�ļ���������ʧ��!");
		}
		response.getWriter().print(je.toJSON());
	}
	
	
}
