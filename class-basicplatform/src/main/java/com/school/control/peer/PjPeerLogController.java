package com.school.control.peer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.inter.peer.IPjPeerBaseManager;
import com.school.manager.inter.peer.IPjPeerItemManager;
import com.school.manager.inter.peer.IPjPeerLogManager;
import com.school.manager.inter.peer.IPjPeerUserManager;
import com.school.manager.peer.PjPeerBaseManager;
import com.school.manager.peer.PjPeerItemManager;
import com.school.manager.peer.PjPeerLogManager;
import com.school.manager.peer.PjPeerUserManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.DeptInfo;
import com.school.entity.DeptUser;
import com.school.entity.RoleUser;
import com.school.entity.peer.PjPeerBaseInfo;
import com.school.entity.peer.PjPeerItemInfo;
import com.school.entity.peer.PjPeerLogInfo;
import com.school.entity.peer.PjPeerUserInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;
/**
 * @author 岳春阳
 * @description 同行评价评价项设置controller
 */
@Controller
@RequestMapping(value="/peerlog")
public class PjPeerLogController extends BaseController<PjPeerLogInfo> {
    private IPjPeerBaseManager pjPeerBaseManager;
    private IPjPeerUserManager pjPeerUserManager;
    private IPjPeerItemManager pjPeerItemManager;
    private IPjPeerLogManager pjPeerLogManager;

    public PjPeerLogController() {
        this.pjPeerBaseManager = this.getManager(PjPeerBaseManager.class);
        this.pjPeerUserManager = this.getManager(PjPeerUserManager.class);
        this.pjPeerItemManager = this.getManager(PjPeerItemManager.class);
        this.pjPeerLogManager = this.getManager(PjPeerLogManager.class);
    }

    /**
	 * @author 岳春阳
	 * @description 同行评价进入职工评价页面
	 */
	@RequestMapping(params="m=dolist",method=RequestMethod.GET) 
	public ModelAndView toPjPeerDoList(HttpServletRequest request,ModelAndView mp )throws Exception{
		return new ModelAndView("/peer/pj_peer_do");  
	}
	/**
	 * @author 岳春阳
	 * @description 同行评价进入职工评价页面
	 */
	@RequestMapping(params="m=jslist",method=RequestMethod.GET) 
	public ModelAndView toPjPeerZgList(HttpServletRequest request,HttpServletResponse response,ModelAndView mp )throws Exception{
		JsonEntity je = new JsonEntity();
		String peerbaseref = "";
		String ptype = "";
		String deptid = "";
		ptype=request.getParameter("ptype");
		//获取主题id
		PageResult presult = this.getPageResultParameter(request);
		PjPeerBaseInfo pb = new PjPeerBaseInfo();
		Date dt=new Date();//如果不需要格式,可直接用dt,dt就是当前系统时间
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置显示格式
        String nowTime="";
        nowTime= df.format(dt);
        Date d =UtilTool.StringConvertToDate(nowTime);
        pb.setNtime(d);
        presult.setSort("pj.c_time desc");
		List<PjPeerBaseInfo> pbList = this.pjPeerBaseManager.getList(pb, presult);
		if(pbList!=null&&pbList.size()==1){
			peerbaseref=pbList.get(0).getRef();
			request.setAttribute("year", pbList.get(0).getYear());
		}else{
			je.setMsg("当前未查到可评价用户，请联系管理员");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		//peerbaseref=pbList.get(2).getRef();
		//获取用户评价类别
		PjPeerUserInfo pjuser = new PjPeerUserInfo();
		pjuser.setUserid(this.logined(request).getRef());		
		pjuser.setPeerbaseid(peerbaseref);		
		List<PjPeerUserInfo> pjuserList = this.pjPeerUserManager.getList(pjuser, null);
		if(pjuserList!=null&pjuserList.size()==1){
			deptid=pjuserList.get(0).getDeptid().toString();
			if(ptype==null||ptype.length()==0){
				request.setAttribute("ptype", pjuserList.get(0).getPtype());
				ptype = pjuserList.get(0).getPtype().toString();
			}
			String typename="";
			if(ptype.equals("1")){
				typename="教师评价";
			}else if(ptype.equals("2")){
				typename="职工评价";
			}
			request.setAttribute("typename", typename);
		}else{
			je.setMsg("未查到当前用户参评类别，请确认当前用户已参与评价");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}		
		PjPeerItemInfo itemobj = new PjPeerItemInfo();
		itemobj.setPeerbaseref(peerbaseref);
		itemobj.setPtype(Integer.parseInt(ptype));
		List<PjPeerItemInfo> peerItemList = this.pjPeerItemManager.getList(itemobj, null);
		//开始组织数据
		List<PjPeerItemInfo> parentitemList=new ArrayList<PjPeerItemInfo>();		//父节点
		List<PjPeerItemInfo> childitemList=new ArrayList<PjPeerItemInfo>();			//子节点集合
		
		if(peerItemList==null||peerItemList.size()<1){
			je.setMsg("系统尚未获取到本次评价的项目，因此您不能进行评价，如有疑问，请联系相关教师! 错误代码：peeritem is empty!");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		//将混合数据分层
		for (PjPeerItemInfo tmpMap : peerItemList) {
			if(tmpMap!=null)
				if(tmpMap.getParentref().toString().trim().equals("0"))
					parentitemList.add(tmpMap);
				else
					childitemList.add(tmpMap);			
		}
		if(parentitemList.size()<1){
			je.setMsg("系统尚未获取到您本次评价大项，如有疑问，请联系相关教师!错误代码：parentitem is empty!");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		pjuser = new PjPeerUserInfo();
		pjuser.setPeerbaseid(peerbaseref);
		pjuser.setPtype(Integer.parseInt(ptype));
		pjuser.setDeptid(Integer.parseInt(deptid));
		List<PjPeerUserInfo> experimenterList = this.pjPeerUserManager.getList(pjuser, null);
//		for(PjPeerUserInfo pj :experimenterList){
//			if(pj.getUserinfo().getRef().equals(this.logined(request).getRef()))
//				experimenterList.remove(pj);
//		}
		for(int i = 0;i<experimenterList.size();i++){
			if(experimenterList.get(i).getUserinfo().getRef().equals(this.logined(request).getRef())){
				experimenterList.remove(i);
				break;
			}
		}
		PjPeerLogInfo logobj = new PjPeerLogInfo();
		logobj.setPeerbaseref(peerbaseref);
		logobj.setPtype(Integer.parseInt(ptype));
		logobj.setCuserid(this.logined(request).getRef());
		List<PjPeerLogInfo> peerlogList = this.pjPeerLogManager.getList(logobj, null);
		request.setAttribute("peerlogList", peerlogList);
		request.setAttribute("experimenterList", experimenterList);
		request.setAttribute("peerbaseref", peerbaseref);
		request.setAttribute("parentitemList", parentitemList);
		request.setAttribute("childitemList", childitemList);
		request.setAttribute("remark", parentitemList.get(0).getRemark());
        request.setAttribute("p_type",ptype);
		if(Integer.parseInt(ptype)==1){
			return new ModelAndView("/peer/pj_peer_do_js_list");  
		}else{
			return new ModelAndView("/peer/pj_peer_do_zg_list"); 
		}
	}
	
	/**
	 * @author 岳春阳
	 * @throws Exception 
	 * @description 执行添加评价
	 */
	@RequestMapping(params="m=dosave",method=RequestMethod.POST)
	public void doAdd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		String param = request.getParameter("paramStr");
		String peerbaseref = request.getParameter("peerbaseref");
		String ptype = request.getParameter("ptype");
		String[] params =param.split("&");
		if(params!=null&params.length>0){
			//首先先根据主题id和评价类型做删除，因为有可能是修改			
			PjPeerLogInfo obj = null;
			obj = new PjPeerLogInfo();
			obj.setPeerbaseref(peerbaseref);
			obj.setPtype(Integer.parseInt(ptype));
			Boolean bl = this.pjPeerLogManager.doDelete(obj);
			//存放值得数据集集合
			List<String> sqlArrayList = new ArrayList<String>();
			//存放sql的集合
			List<List<Object>> objArrayList = new ArrayList<List<Object>>();
		    //拼sql的对象和存放值得对象
			List<Object> objList;
			StringBuilder sql ;	
			Boolean b = false;
			for(int i = 0;i<params.length;i++){
				//获取每一条对象，一共分三部分。。0：评价项id  1：被评价人id  2：分数
				String[] objStr = params[i].split("\\|");
				obj = new PjPeerLogInfo();
				obj.setPeeritemref(Integer.parseInt(objStr[0]));
				obj.setUserid(objStr[1]);
				obj.setScore(Integer.parseInt(objStr[2]));
				obj.setCuserid(this.logined(request).getRef());
				obj.setPeerbaseref(peerbaseref);
				obj.setPtype(Integer.parseInt(ptype));
				objList = new ArrayList<Object>();
				sql = new StringBuilder();
				objList = this.pjPeerLogManager.getSaveSql(obj, sql);
				sqlArrayList.add(sql.toString());
				objArrayList.add(objList);
			}
			b = this.pjPeerItemManager.doExcetueArrayProc(sqlArrayList, objArrayList);
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
	 * @author 岳春阳
	 * @description 同行评价进入部门领导查询页面
	 */
	@RequestMapping(params="m=todeptlogstat",method=RequestMethod.GET) 
	public ModelAndView toPjPeerLogDeptStat(HttpServletRequest request,HttpServletResponse response,ModelAndView mp )throws Exception{
		PjPeerBaseInfo obj = new PjPeerBaseInfo();
		List<PjPeerBaseInfo> baseList = this.pjPeerBaseManager.getList(obj, null);
		List<DeptUser> deptuser = this.logined(request).getDeptUsers();
		String deptid = "";
		if(deptuser!=null&&deptuser.size()>0){
			for(int i = 0 ;i<deptuser.size();i++){
				if(deptuser.get(i).getRoleid()!=null){
					deptid+=deptuser.get(i).getDeptid()+"|";
				}
			}
		}
		List<DeptInfo> smalldeptList = this.pjPeerLogManager.getDeptInfo(deptid);
		List<DeptInfo> deptList = new ArrayList<DeptInfo>();
		if(smalldeptList!=null&&smalldeptList.size()>1){
			for(int i = 0;i<smalldeptList.size();i++){
				if(smalldeptList.get(i).getParentdeptid()==-1){
					deptList.add(smalldeptList.get(i));
					smalldeptList.remove(i);
				}
			}
		}
		request.setAttribute("deptList", deptList);
		request.setAttribute("smalldeptList", smalldeptList);
		request.setAttribute("baseList", baseList);
		return new ModelAndView("/peer/pj_peer_log_list_dept");  
	}
	
	/**
	 * @author 岳春阳
	 * @description 同行评价进入部门领导查询页面
	 */
	@RequestMapping(params="m=getdeptlogstat",method=RequestMethod.GET) 
	public ModelAndView getPjPeerLogDeptStat(HttpServletRequest request,HttpServletResponse response,ModelAndView mp)throws Exception{
		JsonEntity je = new JsonEntity();
		String peerbaseref = request.getParameter("peerbaseref");
		String ptype = request.getParameter("ptype");
		String deptref = request.getParameter("deptref");
		//传回所选的部门
		request.setAttribute("olddeptref", deptref);
		//部门信息
		List<DeptUser> deptuser = this.logined(request).getDeptUsers();
		String deptid = "";
		if(deptuser!=null&&deptuser.size()>0){
			for(int i = 0 ;i<deptuser.size();i++){
				if(deptuser.get(i).getRoleid()!=null){
					deptid+=deptuser.get(i).getDeptid()+"|";
				}
			}
		}
		if(deptid.length()<1){
			je.setMsg("您没有当前查询权限，请联系管理员");
			response.getWriter().print(je.getAlertMsgAndBack());
			return null;
		}
		if(peerbaseref==null||peerbaseref==""){
			je.setMsg("系统未获取到评价主题，请刷新重试");
			response.getWriter().print(je.getAlertMsgAndBack());
			return null;
		}
		if(ptype==null||ptype==""){
			je.setMsg("系统未获取到评价类别，请刷新重试");
			response.getWriter().print(je.getAlertMsgAndBack());
			return null;
		}
		if(deptref==null||deptref==""){
			deptref=deptid;
		}
		//获取统计列表
		List<List<String>> logList = this.pjPeerLogManager.getLogStat(peerbaseref, Integer.parseInt(ptype), deptref);		
		request.setAttribute("logList", logList);
		//查询评价项列表
		PjPeerItemInfo obj = new PjPeerItemInfo();
		obj.setPeerbaseref(peerbaseref);
		obj.setPtype(Integer.parseInt(ptype));
		obj.setParentref(0);
		List<PjPeerItemInfo> item = this.pjPeerItemManager.getList(obj, null);
		if(item!=null&&item.size()>0){
			request.setAttribute("remark", item.get(0).getRemark());			
		}
		request.setAttribute("itemList", item);
		//查询主题列表
		PjPeerBaseInfo baseObj = new PjPeerBaseInfo();
		List<PjPeerBaseInfo> baseList = this.pjPeerBaseManager.getList(baseObj, null);
		request.setAttribute("baseList", baseList);
		//部门列表
		List<DeptInfo> smalldeptList = this.pjPeerLogManager.getDeptInfo(deptid);
		List<DeptInfo> deptList = new ArrayList<DeptInfo>();
		if(smalldeptList!=null&&smalldeptList.size()>1){
			for(int i = 0;i<smalldeptList.size();i++){
				if(smalldeptList.get(i).getParentdeptid()==-1){
					deptList.add(smalldeptList.get(i));
					smalldeptList.remove(i);
				}
			}
		}
		request.setAttribute("deptList", deptList);
		request.setAttribute("smalldeptList", smalldeptList);
		
		je.setType("success");
		je.setMsg("成功");
		response.getWriter().print(je.toJSON());
		return new ModelAndView("/peer/pj_peer_log_list_dept");  
	}
}
