package com.school.control.peer;

import com.school.control.base.BaseController;
import com.school.entity.DeptInfo;
import com.school.entity.DeptUser;
import com.school.entity.TermInfo;
import com.school.entity.peer.PjPeerBaseInfo;
import com.school.entity.peer.PjPeerUserInfo;
import com.school.manager.inter.IDeptManager;
import com.school.manager.inter.ITermManager;
import com.school.manager.inter.peer.IPjPeerBaseManager;
import com.school.manager.inter.peer.IPjPeerUserManager;
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
import java.util.UUID;
/**
 * @author 岳春阳
 * @description 同行评价主题设置controller
 */
@Controller
@RequestMapping(value="/peerbase") 
public class PjPeerBaseController extends BaseController<PjPeerBaseInfo> {
    @Autowired
    private IDeptManager deptManager;
    @Autowired
    private ITermManager termManager;
    @Autowired
    private IPjPeerBaseManager pjPeerBaseManager;
    @Autowired
    private IPjPeerUserManager pjPeerUserManager;


	/**
	 * @author 岳春阳
	 * @description 同行评价主题设置进入列表页面
	 */
	@RequestMapping(params="m=list",method=RequestMethod.GET) 
	public ModelAndView toPjPeerBaseList(HttpServletRequest request,ModelAndView mp )throws Exception{
		DeptInfo dept = new DeptInfo();
		dept.setParentdeptid(-1);
		List<DeptInfo> deptList = this.deptManager.getList(dept, null);
		List<TermInfo> termList = this.termManager.getList(null, null);
		request.setAttribute("term", termList);
		request.setAttribute("dept", deptList);
		return new ModelAndView("/peer/pj_peer_base_list");  
	}
	
	/**
	 * @author 岳春阳
	 * @throws Exception 
	 * @description 执行添加评价主题
	 */
	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST)
	public void getPjPeerBaseList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		PageResult presult = this.getPageResultParameter(request);
		presult.setOrderBy(" pj.c_time desc");
		String ref = request.getParameter("ref");
		PjPeerBaseInfo obj = new PjPeerBaseInfo();
		if(ref!=""||ref!=null)
			obj.setRef(ref);
		List<PjPeerBaseInfo> list = this.pjPeerBaseManager.getList(obj, presult);
		if(list!=null&&list.size()>0){
			for(int i =0;i<list.size();i++){
				String[] deptids = list.get(i).getDeptref().split("\\|");
				int deptid = 0;
				String deptname = "";
				for(int j =0;j<deptids.length;j++){
					deptid = Integer.parseInt(deptids[j]);
					DeptInfo dept = new DeptInfo();
					dept.setDeptid(deptid);
					List<DeptInfo> deptList = this.deptManager.getList(dept, null);
					deptname+=deptList.get(0).getDeptname();
					if(j<deptids.length-1)
						deptname+=",";
				}
				list.get(i).setDeptname(deptname);
			}
		}
		presult.setList(list);
		je.setType("success");
		je.setPresult(presult);			
		
		response.getWriter().print(je.toJSON());
	}
	
	/**
	 * @author 岳春阳
	 * @throws Exception 
	 * @description 执行添加评价主题
	 */
	@RequestMapping(params="m=doAddOrUpdate",method=RequestMethod.POST)
	public void doAdd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		PjPeerBaseInfo obj = this.getParameter(request, PjPeerBaseInfo.class);
		String deptref = obj.getDeptref();
		if(obj.getYear()==null){
			je.setMsg("请选择学年");
			response.getWriter().print(je.toJSON());
			return;
		}else if(obj.getBtimestring()==null){
			je.setMsg("请选择开始时间");
			response.getWriter().print(je.toJSON());
			return;
		}else if(obj.getEtimestring()==null){
			je.setMsg("请选择结束时间");
			response.getWriter().print(je.toJSON());
			return;
		}else if(obj.getName()==null){
			je.setMsg("请填写评价主题");
			response.getWriter().print(je.toJSON());
			return;
		}else{
			List ck = this.pjPeerBaseManager.checkUserList(deptref);
			if(ck!=null&&ck.size()>0){
				String msg = "以下人员重复出现在不同部门，请调整后再操作：  ";
				for(int i = 0;i<ck.size();i++){
					msg+=ck.get(i)+"--";
				}
                je.setType("error");
				je.setMsg(msg);
				response.getWriter().print(je.toJSON());
				return;
			}
			if(obj.getRef()==null||obj.getRef().length()<1){				
				String baseref = UUID.randomUUID().toString();
				obj.setRef(baseref);
				obj.setCuserid(this.logined(request).getRef());
				//Boolean b = this.pjPeerBaseManager.doSave(obj);
				
				//存放值得数据集集合
				List<String> sqlArrayList = new ArrayList<String>();
				//存放sql的集合
				List<List<Object>> objArrayList = new ArrayList<List<Object>>();
			    //拼sql的对象和存放值得对象
				List<Object> objList;
				StringBuilder sql ;	
				Boolean b = false;
				//获得主题的相关sql和变量，并放到集合里
				objList = new ArrayList<Object>();
				sql = new StringBuilder();
				objList = this.pjPeerBaseManager.getSaveSql(obj, sql);
				sqlArrayList.add(sql.toString());
				objArrayList.add(objList);
				
				//获取参评用户相关sql和变量，并放到集合里
				//首先根据部门id查询部门下所有人员，包括子部门
				List<DeptUser> userlist = this.pjPeerBaseManager.getUserListByDeptref(deptref);
				//创建参评用户对象
				PjPeerUserInfo pjuser;
				//查到用户，循环得到sql和变量添加到集合里，批量执行添加
				if(userlist!=null&&userlist.size()>0){
					for(int i = 0; i < userlist.size();i++){
						objList = new ArrayList<Object>();
						sql = new StringBuilder();
						pjuser = new PjPeerUserInfo();
						pjuser.setUserid(userlist.get(i).getUserref());
						pjuser.setPeerbaseid(baseref);
						if(userlist.get(i).getTypeid()==1){
							pjuser.setPtype(2);
						}else{
							pjuser.setPtype(1);
						}
						pjuser.setDeptid(userlist.get(i).getDeptid());
						objList = this.pjPeerUserManager.getSaveSql(pjuser, sql);
						sqlArrayList.add(sql.toString());
						objArrayList.add(objList);
					}
				}
				
				//统一执行，一条失败全部失败
				b = this.pjPeerBaseManager.doExcetueArrayProc(sqlArrayList, objArrayList);
				
				if(b){
					je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
					je.setType("success");
				}else{  
					je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
					je.setType("error");
				}
			}else{
				Integer isupd = Integer.parseInt(request.getParameter("isupddept"));
				if(isupd==0){
					PjPeerUserInfo user = new PjPeerUserInfo();
					user.setPeerbaseid(obj.getRef());
					Boolean sign = this.pjPeerUserManager.doDelete(user);
				}
				
				//存放值得数据集集合
				List<String> sqlArrayList = new ArrayList<String>();
				//存放sql的集合
				List<List<Object>> objArrayList = new ArrayList<List<Object>>();
			    //拼sql的对象和存放值得对象
				List<Object> objList;
				StringBuilder sql ;	
				Boolean b = false;
				//获得主题的相关sql和变量，并放到集合里
				objList = new ArrayList<Object>();
				sql = new StringBuilder();
				objList = this.pjPeerBaseManager.getUpdateSql(obj, sql);
				sqlArrayList.add(sql.toString());
				objArrayList.add(objList);
				
				//获取参评用户相关sql和变量，并放到集合里
				if(isupd==0){
					//首先根据部门id查询部门下所有人员，包括子部门
					List<DeptUser> userlist = this.pjPeerBaseManager.getUserListByDeptref(deptref);
					//创建参评用户对象
					PjPeerUserInfo pjuser;
					//查到用户，循环得到sql和变量添加到集合里，批量执行添加
					if(userlist!=null&&userlist.size()>0){
						for(int i = 0; i < userlist.size();i++){
							objList = new ArrayList<Object>();
							sql = new StringBuilder();
							pjuser = new PjPeerUserInfo();
							pjuser.setUserid(userlist.get(i).getUserref());
							pjuser.setPeerbaseid(obj.getRef());
							if(userlist.get(i).getTypeid()==1){
								pjuser.setPtype(2);
							}else{
								pjuser.setPtype(1);
							}
							pjuser.setDeptid(userlist.get(i).getDeptid());
							objList = this.pjPeerUserManager.getSaveSql(pjuser, sql);
							sqlArrayList.add(sql.toString());
							objArrayList.add(objList);
						}
					}
				}
				//统一执行，一条失败全部失败
				b = this.pjPeerBaseManager.doExcetueArrayProc(sqlArrayList, objArrayList);
				
				if(b){
					je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
					je.setType("success");
				}else{  
					je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
					je.setType("error");
				}
			}
		}
		response.getWriter().print(je.toJSON());  
	}
	
	/**
	 * @author 岳春阳
	 * @throws Exception 
	 * @description 执行添加评价主题
	 */
	@RequestMapping(params="m=del",method=RequestMethod.POST)
	public void doDelete(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		PjPeerBaseInfo obj = this.getParameter(request, PjPeerBaseInfo.class);
		if(obj.getRef()==null||obj.getRef()==""){
			je.setMsg("未接收到需要的参数，请刷新页面重试");
			response.getWriter().print(je.toJSON());
			return;
		}
		Boolean b = this.pjPeerBaseManager.doDelete(obj);
		if(b){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			je.setType("error");
		}
		response.getWriter().print(je.toJSON());
	}
	
}
