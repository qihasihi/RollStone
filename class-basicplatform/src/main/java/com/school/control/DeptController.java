package com.school.control;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.entity.*;
import com.school.manager.*;
import com.school.manager.inter.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.util.JsonEntity;
import com.school.util.PageResult; 
import com.school.util.UtilTool;

@Controller
@RequestMapping(value="/dept")
public class DeptController extends BaseController<DeptInfo> {
    private IDeptManager deptManager;
    private IGradeManager gradeManager;
    private ISubjectManager subjectManager;
    private IDictionaryManager dictionaryManager;
    private IDeptUserManager deptUserManager;
    private IRoleManager roleManager;
    private IRoleUserManager roleUserManager;
    private ITeacherManager teacherManager;
    private IUserManager userManager;

    public DeptController(){
        this.deptManager=this.getManager(DeptManager.class);
        this.gradeManager=this.getManager(GradeManager.class);
        this.subjectManager=this.getManager(SubjectManager.class);
        this.dictionaryManager=this.getManager(DictionaryManager.class);
        this.deptUserManager=this.getManager(DeptUserManager.class);
        this.roleManager=this.getManager(RoleManager.class);
        this.roleUserManager=this.getManager(RoleUserManager.class);
        this.teacherManager=this.getManager(TeacherManager.class);
        this.userManager=this.getManager(UserManager.class);
    }

	@RequestMapping(params="m=list",method=RequestMethod.GET) 
	public ModelAndView toDeptList(HttpServletRequest request,ModelAndView mp )throws Exception{
		PageResult p = new PageResult();
		p.setOrderBy("d.dept_id"); 
		p.setPageNo(0);
		p.setPageSize(0);
		List<DeptInfo>deptList=this.deptManager.getList(null, p);
		PageResult pc = new PageResult(); 
		pc.setPageNo(0); 
		pc.setPageSize(0); 
		pc.setOrderBy("grade_id");
		List<GradeInfo>gradeList=this.gradeManager.getList(null,pc);
		List<SubjectInfo>subjectList=this.subjectManager.getList(null, null);
		List<DictionaryInfo>typeList=this.dictionaryManager.getDictionaryByType("DEPT_TYPE");
        List<DictionaryInfo>periodList=this.dictionaryManager.getDictionaryByType("STUDY_PERIODS");

		request.setAttribute("deptList", deptList);
		request.setAttribute("gradeList", gradeList); 
		request.setAttribute("subjectList", subjectList);
		request.setAttribute("typeList", typeList);
        request.setAttribute("periodList",periodList);
		return new ModelAndView("/dept/list");  
	}
	
	
	
	/**
	 * ��ȡList
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST)
	public void AjaxGetList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		DeptInfo deptinfo = this.getParameter(request, DeptInfo.class);
		List<DeptInfo>deptList=deptManager.getList(deptinfo, null); 
		JsonEntity je = new JsonEntity();
		je.setType("success");
		je.setObjList(deptList); 
		response.getWriter().print(je.toJSON()); 
	}
	
	/**
	 * ���
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxsave",method=RequestMethod.POST)
	public void doAddDept(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		DeptInfo deptinfo = this.getParameter(request, DeptInfo.class);
		if(deptinfo.getDeptname()==null||
				deptinfo.getDeptname().trim().length()<1||
				deptinfo.getParentdeptid()==null||
				//deptinfo.getSubjectid()==null||  
				deptinfo.getTypeid()==null){      
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));  
			response.getWriter().print(je.toJSON()); 
			return;
		}
        DeptInfo d=new DeptInfo();
        d.setDeptname(deptinfo.getDeptname());
        List<DeptInfo>dList=this.deptManager.getList(d,null);
        if(dList!=null&&dList.size()>0){
            je.setMsg("�˲����Ѵ���!");
            response.getWriter().print(je.toJSON());
            return;
        }


		if(this.logined(request)!=null)
			deptinfo.getUserinfo().setUserid(this.logined(request).getUserid()); 
		if(deptManager.doSave(deptinfo)){ 
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success"); 
		}else{   
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());  
	}  
	@RequestMapping(params="m=doUpdateParentId",method=RequestMethod.POST)
	public void doUpdateParentId(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		DeptInfo deptinfo = this.getParameter(request, DeptInfo.class);
		if(deptinfo.getDeptid()==null||deptinfo.getParentdeptid()==null){  
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR")); 
			response.getWriter().print(je.toJSON()); 
			return;
		}
		//��֤Ҫ������ϼ�����
		DeptInfo parent=new DeptInfo();
		parent.setParentdeptid(deptinfo.getParentdeptid());
		List<DeptInfo>parentDeptList=this.deptManager.getList(parent, null);
		if(parentDeptList==null||parentDeptList.size()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
			response.getWriter().print(je.toJSON());
			return;
		}
		//��ʼ�ƶ��޸�
		DeptInfo tmp=new DeptInfo();
		tmp.setDeptid(deptinfo.getDeptid());
		List<DeptInfo>deptList=this.deptManager.getList(tmp, null);
		if(deptList==null||deptList.size()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
			response.getWriter().print(je.toJSON());
			return;
		}
		DeptInfo newDept=deptList.get(0);
		newDept.setParentdeptid(deptinfo.getParentdeptid());
		
		if(deptManager.doUpdate(newDept)){
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
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxmodify",method=RequestMethod.POST)
	public void doUpdateDept(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		DeptInfo deptinfo = this.getParameter(request, DeptInfo.class);
		if(deptinfo.getDeptid()==null||deptinfo.getDeptname()==null||
			deptinfo.getTypeid()==null//||deptinfo.getSubjectid()==null
			){     
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR")); 
			response.getWriter().print(je.toJSON()); 
			return;
		}
		deptinfo.getUserinfo().setUserid(this.logined(request).getUserid());
		if(deptManager.doUpdate(deptinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{   
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());  
	}
	   
	
	@RequestMapping(params="m=dodelete",method=RequestMethod.POST) 
	public void doDeleteDepte(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		DeptInfo deptinfo = this.getParameter(request, DeptInfo.class);
		if(deptinfo.getDeptid()==null){   
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR")); 
			response.getWriter().print(je.toJSON()); 
			return;
		}  
		if(deptManager.doDelete(deptinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{    
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());  
	}
	
	
	/**
	 * ָ��������Ա
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="m=toassignDeptUser",method=RequestMethod.POST)
	public void toassignDeptUser(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity("success","");
		String ref=request.getParameter("ref");
		//String ptype=request.getParameter("ptype");
		
		//��ѯ���в���(�꼶�飬�����飬�����飬����)
		List<DeptInfo>deptList=this.deptManager.getList(null, null);
		//��ѯû�в��ŵ���Ա
		//List<DeptUser> notUdList=this.deptManager.getNotInDeptUser(null,ptype);
		
		//����Ƿ񴫵�REF������
		Integer deptid=null;
		if(ref==null||ref.trim().length()<1&&(deptList!=null&&deptList.size()>4))
			deptid=deptList.get(4).getDeptid();
		else if(ref!=null&&ref.trim().length()>0)
			deptid=Integer.parseInt(ref);
        else{
            je.setMsg("�����Ӳ��ţ������������ͼ�꣬�������ţ���Ϊ������ӳ�Ա!");
            response.getWriter().print(je.toJSON());
            return;
        }
		
		
		 DeptInfo d=new DeptInfo();
		 d.setDeptid(deptid);
		 deptList=this.deptManager.getList(d, null);
		 if(deptList==null||deptList.size()<1){
			je.setMsg("����ϵͳ��δ���ָò�����Ϣ�������Ѿ������ڣ���ˢ�º�����!");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return; 
		 }  
		 //����Ѿ����ִ�����ò��ŵ���Ա		 
		 DeptUser du=new DeptUser();
		 du.setDeptid(deptid);
		 List<DeptUser>tmpList=this.deptUserManager.getList(du, null);
		 
		//���ݲ���������ȡְ��
		 List<RoleInfo>roleList=new ArrayList<RoleInfo>();
		 List<RoleInfo>rList=null;
		 RoleInfo r=new RoleInfo(); 
		 Integer roleid=null;
		 switch(deptList.get(0).getTypeid()){
			case 1:
				roleid=UtilTool._ROLE_DEPT_LEADER_ID;
				r.setRoleid(roleid);
				rList=this.roleManager.getList(r, null);
				roleList.addAll(rList);
				
				roleid=UtilTool._ROLE_DEPT_FU_LEADER_ID;
				r.setRoleid(roleid);
				rList=this.roleManager.getList(r, null);
				roleList.addAll(rList);
				break;
			case 2:
				roleid=UtilTool._ROLE_TEACH_LEADER_ID;
				r.setRoleid(roleid);
				rList=this.roleManager.getList(r, null);
				roleList.addAll(rList);
				
				roleid=UtilTool._ROLE_TEACH_FU_LEADER_ID;
				r.setRoleid(roleid);
				rList=this.roleManager.getList(r, null);
				roleList.addAll(rList);
				break;
			case 3:
				roleid=UtilTool._ROLE_GRADE_LEADER_ID;
				r.setRoleid(roleid);
				rList=this.roleManager.getList(r, null);
				roleList.addAll(rList);
				
				roleid=UtilTool._ROLE_GRADE_FU_LEADER_ID;
				r.setRoleid(roleid);
				rList=this.roleManager.getList(r, null);
				roleList.addAll(rList);
				break;
			case 4:
				roleid=UtilTool._ROLE_FREE_DEPT_LEADER_ID;
				r.setRoleid(roleid);
				rList=this.roleManager.getList(r, null);
				roleList.addAll(rList);
				break;
			case 5:
				roleid=UtilTool._ROLE_PREPARE_LEADER_ID;
				r.setRoleid(roleid);
				rList=this.roleManager.getList(r, null);
				roleList.addAll(rList);
				break;
		 }  
		 
		 je.getObjList().add(deptList.get(0).getDeptid());
		 je.getObjList().add(tmpList);
		 je.getObjList().add(deptList.get(0).getTypeid()); 
		 je.getObjList().add(roleList);
		 response.getWriter().print(je.toJSON());
	} 
	
	
	/**
	 * �Զ���ȫ������Ա
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=autoFillDeptUser",method=RequestMethod.POST)
	public void autoFillDeptUser(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je =new JsonEntity("success","");
		String ptype=request.getParameter("ptype");
		String nameid=request.getParameter("nameid");
		if(ptype==null||ptype.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		//��ѯû�в��ŵ���Ա
		List<DeptUser> notUdList=this.deptManager.getNotInDeptUser(null,ptype,nameid);
		je.setObjList(notUdList);
		response.getWriter().print(je.toJSON());
	}
	
	
	
	@RequestMapping(params="m=doSaveDeptUser",method=RequestMethod.POST)
	public void doSetDepteUser(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity();
		String deptid=request.getParameter("deptid");
		String username=request.getParameter("username");
		if(deptid==null||deptid.trim().length()<1){
			je.setMsg("�쳣����ϵͳ��δ���ָò��ű�ʶ����ˢ�º�����!");
			response.getWriter().print(je.toJSON());
			return;
		}
		if(username==null||username.trim().length()<1){
			je.setMsg("�쳣����ϵͳ��δ�����û���ʶ����ˢ�º�����!");
			response.getWriter().print(je.toJSON());
			return;
		}
		//��֤�Ƿ���ڸò���   
		DeptInfo d=new DeptInfo();
		d.setDeptid(Integer.parseInt(deptid));
		List<DeptInfo>count=this.deptManager.getList(d, null);
		if(count==null||count.size()<1){
			je.setMsg("�쳣����ϵͳ��δ���ָò�����Ϣ�������Ѿ������ڣ���ˢ�º�����!");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return ;
		}
        UserInfo u=new UserInfo();
        u.setUsername(username);
        List<UserInfo>userList=this.userManager.getList(u,null);
        if(userList==null||userList.size()<1){
            je.setMsg("��Ǹ���޷���ȡ��ǰ��ʦ���û���������!");
            response.getWriter().print(je.getAlertMsgAndCloseWin());
            return ;
        }
		//��֤�Ƿ���ڸý�ʦ
		RoleUser ru=new RoleUser();
		ru.getUserinfo().setUserid(userList.get(0).getUserid());
		ru.setRoleid(UtilTool._ROLE_TEACHER_ID);
		List<RoleUser>ruList=this.roleUserManager.getList(ru, null);
		if(ruList==null||ruList.size()<1){
			je.setMsg("��Ǹ���޷���ȡ��ǰ��ʦ!");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return ;
		}
		//ִ�����
		DeptUser ud=new DeptUser();
		ud.setUserref(ruList.get(0).getUserinfo().getRef());
		ud.setDeptid(Integer.parseInt(deptid));
		if(this.deptUserManager.doSave(ud)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		response.getWriter().print(je.toJSON());
	}
	
	
	/**
	 * �������ò��Ÿɲ�ҳ��
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="m=toSetLeader",method=RequestMethod.GET)
	public ModelAndView toSetLeader(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity();
		String ref=request.getParameter("ref");
		String typeid=request.getParameter("ptype");
		if(ref==null||typeid==null){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		//��ѯ�ò����Ƿ����
		DeptInfo tmpdept=new DeptInfo();
		tmpdept.setDeptid(Integer.parseInt(ref));
		List<DeptInfo> deptList=this.deptManager.getList(tmpdept, null);
		if(deptList==null||deptList.size()<1){
			je.setMsg("����ϵͳ��δ������Ҫ���õĲ�����Ϣ�������Ѿ�������!");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		request.setAttribute("deObj", deptList.get(0));
		
		//��ѯ�ò��ŵ���������
		DeptUser ud=new DeptUser();
		ud.setDeptid(Integer.parseInt(ref));
		List<DeptUser>udList=this.deptUserManager.getList(ud, null);
		request.setAttribute("udlist", udList);
		
		//��ѯ���в���(�����飬�����飬����)		
		List<DeptInfo> deList=this.deptManager.getList(null, null);
		request.setAttribute("deList", deList);
		  
		//���ݲ���������ȡְ��
		RoleInfo r=new RoleInfo();
		r.setFlag(1);
		Integer roleid=UtilTool._ROLE_TEACH_LEADER_ID;
		switch(Integer.parseInt(typeid)){
			case 1:roleid=UtilTool._ROLE_DEPT_LEADER_ID;break;
			case 2:roleid=UtilTool._ROLE_TEACH_LEADER_ID;break;
			case 3:roleid=UtilTool._ROLE_GRADE_LEADER_ID;break;
			case 4:roleid=UtilTool._ROLE_FREE_DEPT_LEADER_ID;break;
		}
		r.setRoleid(roleid);
		List<RoleInfo>roleList=this.roleManager.getList(r, null);
		request.setAttribute("roleList", roleList);
		
		return new ModelAndView("/dept/leader/dept-leader");
	}
	
	private boolean validateDeptRole(Integer roleid){
		Integer[]arr={
				UtilTool._ROLE_DEPT_LEADER_ID,
				UtilTool._ROLE_GRADE_LEADER_ID,
				UtilTool._ROLE_TEACH_LEADER_ID,
				UtilTool._ROLE_PREPARE_LEADER_ID,
				UtilTool._ROLE_FREE_DEPT_LEADER_ID};
		for (Integer rid : arr) {
			if(rid.intValue()==roleid){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ���ò��Ÿɲ�
	 */
	@RequestMapping(params="m=doUpdPostName",method=RequestMethod.POST)
	public void doUpdatePostName(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je=new JsonEntity();
		String userid=request.getParameter("uid");
		String role=request.getParameter("roleid");
		String deptid=request.getParameter("deptid");
		if(userid==null||userid.length()<1||deptid==null||deptid.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return; 
		}  
		TeacherInfo t=new TeacherInfo();
		t.setUserid(userid);
		List<TeacherInfo>tList=this.teacherManager.getList(t, null);
		if(tList==null||tList.size()<1){
			je.setMsg("��ǰ�û�������!");
			response.getWriter().print(je.toJSON());
			return;
		}
		
		List<List<Object>>objListArray=new ArrayList<List<Object>>();
		List<String>sqlList=new ArrayList<String>();
		StringBuilder sql=null;
		List<Object>objList=null;
		
		/*
		 * 1.���һ���û���ְ�� ɾ�����û���ǰְ�����ɫ
		 * 2.���������ɲ�  ��յ�ǰtypeid�µ������û�ְ�����ɫ 
		 */
		
		DeptUser ud=new DeptUser();
		ud.setUserref(userid);
		ud.setDeptid(Integer.parseInt(deptid));
		if(role!=null&&role.length()>0){
			Integer roleid=Integer.parseInt(role);
			ud.setRoleid(roleid);
			
			/*
			 * ��ְ��ֻ��һ��������ְ��
			 * ��յ�ǰ�����µ������û���ְ��
			 */
			if(this.validateDeptRole(Integer.parseInt(role))){
				DeptUser dupd=new DeptUser();
				dupd.setDeptid(Integer.parseInt(deptid));
                dupd.setRoleid(roleid);
				sql=new StringBuilder();
				objList=this.deptUserManager.getUpdateSqlLoyal(dupd, sql);
				if(objList!=null&&objList.size()>0){
					objListArray.add(objList);
					sqlList.add(sql.toString());
				}   
				
				//��յ�ǰ�����µ������û���ɫ
				DeptUser ds=new DeptUser();
				ds.setDeptid(Integer.parseInt(deptid));
				List<DeptUser>dList=this.deptUserManager.getList(ds,null);
				if(dList!=null&&dList.size()>0){
					for (DeptUser deptUser : dList) {
						if(deptUser!=null&&deptUser.getRoleid()!=null&&deptUser.getRoleid().equals(roleid)){
							RoleUser rdelete=new RoleUser();
							rdelete.setUserid(deptUser.getUserref());
							rdelete.setRoleid(deptUser.getRoleid());
							
							sql=new StringBuilder();
							objList=this.roleUserManager.getDeleteSql(rdelete,sql);
							if(objList!=null&&sql!=null){
								objListArray.add(objList);
								sqlList.add(sql.toString());
							}
						}
					}
				}
			}	
			
			//����ǰ�û����Ͻ�ɫ
			RoleUser radd=new RoleUser();
			radd.setUserid(userid);
			radd.setRoleid(roleid);
			
			sql=new StringBuilder();
			objList=this.roleUserManager.getSaveSql(radd,sql);
			if(objList!=null&&sql!=null){
				objListArray.add(objList);
				sqlList.add(sql.toString());
			}
		}else{ 
			
			//ɾ����ǰ�û���ɫ
			List<DeptUser>dList=this.deptUserManager.getList(ud,null);
			if(dList!=null&&dList.size()>0){
				RoleUser ru=new RoleUser();
				ru.setRoleid(dList.get(0).getRoleid());
				ru.setUserid(dList.get(0).getUserref());
				
				sql=new StringBuilder();
				objList=this.roleUserManager.getDeleteSql(ru, sql);
				if(objList!=null&&sql!=null){
					objListArray.add(objList);
					sqlList.add(sql.toString());
				}
			}
		} 
		
		
		
		sql=new StringBuilder();
		objList=this.deptUserManager.getUpdateSql(ud, sql);
		if(objList!=null&&objList.size()>0){
			objListArray.add(objList);  
			sqlList.add(sql.toString());
		}
		if(objList!=null&&objList.size()>0){
			boolean isflag=this.deptUserManager.doExcetueArrayProc(sqlList, objListArray);
			if(isflag){  
				je.setType("success");
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));  
			}else
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}else
			je.setMsg(UtilTool.msgproperty.getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
		response.getWriter().print(je.toJSON());
	}
	
	@RequestMapping(params="m=doDelDeptUser",method=RequestMethod.POST)
	public void doDelDeptUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je=new JsonEntity();
		String deptid=request.getParameter("deptid");
		String userid=request.getParameter("uid");
		if(deptid==null||deptid.trim().length()<1
			||userid==null||userid.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		} 
		 
		DeptUser dd=new DeptUser();
		dd.setDeptid(Integer.parseInt(deptid));
		dd.setUserref(userid);
		List<DeptUser>dList=this.deptUserManager.getList(dd, null);
		if(dList==null||dList.size()<1){ 
			je.setMsg("��ǰ�����Ѳ����ڸ��û�!");
			response.getWriter().print(je.toJSON());
			return;
		}
		List<String>sqlList=new ArrayList<String>();
		List<List<Object>>objListArray=new ArrayList<List<Object>>();
		List<Object>objList=null;
		StringBuilder sql=null;
		
		//ɾ��RoleUser 
		for (DeptUser ds: dList) {
			DeptUser dudelete=new DeptUser();
			dudelete.setDeptid(ds.getDeptid());
			dudelete.setUserref(ds.getUserref());
			sql=new StringBuilder();
			objList=this.deptUserManager.getDeleteSql(dudelete, sql);
			if(objList!=null&&sql!=null){
				objListArray.add(objList);
				sqlList.add(sql.toString());
			}
			
			if(ds!=null&&ds.getRoleid()!=null){
				RoleUser ru=new RoleUser();
				ru.setRoleid(ds.getRoleid());
				ru.setUserid(ds.getUserref());
				sql=new StringBuilder();
				objList=this.roleUserManager.getDeleteSql(ru, sql);
				if(objList!=null&&sql!=null){
					objListArray.add(objList);
					sqlList.add(sql.toString());
				}
			}
		}
		
		if(this.deptUserManager.doExcetueArrayProc(sqlList, objListArray)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else 
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		response.getWriter().print(je.toJSON());
	}
	
}
 