package com.school.control.peer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.DeptManager;
import com.school.manager.inter.IDeptManager;
import com.school.manager.inter.peer.IPjPeerBaseManager;
import com.school.manager.inter.peer.IPjPeerItemManager;
import com.school.manager.peer.PjPeerBaseManager;
import com.school.manager.peer.PjPeerItemManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.school.control.base.BaseController;
import com.school.entity.DeptInfo;
import com.school.entity.peer.PjPeerBaseInfo;
import com.school.entity.peer.PjPeerItemInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;

/**
 * @author 岳春阳
 * @description 同行评价评价项设置controller
 */
@Controller
@RequestMapping(value="/peeritem")
public class PjPeerItemController extends BaseController<PjPeerItemInfo> {
    private IPjPeerBaseManager pjPeerBaseManager;
    private IPjPeerItemManager pjPeerItemManager;
    private IDeptManager deptManager;
    public PjPeerItemController() {
        this.pjPeerBaseManager = this.getManager(PjPeerBaseManager.class);
        this.pjPeerItemManager=this.getManager(PjPeerItemManager.class);
        this.deptManager=this.getManager(DeptManager.class);
    }

    /**
	 * @author 岳春阳
	 * @description 同行评价评价项设置进入列表页面
	 */
	@RequestMapping(params="m=list",method=RequestMethod.GET) 
	public ModelAndView toPjPeerItemList(HttpServletRequest request,ModelAndView mp )throws Exception{
		JsonEntity je = new JsonEntity();
		//List<TermInfo> termList = this.termManager.getList(null, null);
		PageResult presult = this.getPageResultParameter(request);
		presult.setPageNo(0);
		presult.setPageSize(0);
		presult.setOrderBy(" pj.c_time desc");
		List<PjPeerBaseInfo> baseList = this.pjPeerBaseManager.getList(null, presult);
		String ptype = request.getParameter("ptype");
		String peerbaseref = request.getParameter("peerbaseref");
		if(ptype==null){
			ptype="1";
		}
		if(peerbaseref==null){
			peerbaseref=baseList.get(0).getRef();
		}
		String year = request.getParameter("year");
		//request.setAttribute("term", termList);
		request.setAttribute("peerBase", baseList);
		request.setAttribute("peerbaseref", peerbaseref);
		request.setAttribute("ptype", ptype);
		if(peerbaseref != null && ptype!=null&&!peerbaseref.equals("-1")){
			PjPeerBaseInfo baseobj = new PjPeerBaseInfo();
			baseobj.setRef(peerbaseref);
			List<PjPeerBaseInfo> checkBaseList = this.pjPeerBaseManager.getList(baseobj, null);
			if(checkBaseList!=null){
				request.setAttribute("btime", checkBaseList.get(0).getBtimestring());
				request.setAttribute("etime", checkBaseList.get(0).getEtimestring());
			}
			PjPeerItemInfo obj = new PjPeerItemInfo();
			obj.setParentref(0);
			obj.setPtype(Integer.parseInt(ptype));
			obj.setPeerbaseref(peerbaseref);
			List<PjPeerItemInfo> parentItemList = this.pjPeerItemManager.getList(obj, null);
			if(parentItemList!=null&&parentItemList.size()>0){
				request.setAttribute("parentItemList", parentItemList);
				List<PjPeerItemInfo> childItemList = new ArrayList<PjPeerItemInfo>();
				for(int i = 0;i<parentItemList.size();i++){
					obj = new PjPeerItemInfo();
					obj.setParentref(parentItemList.get(i).getRef());
					obj.setPtype(Integer.parseInt(ptype));
					obj.setPeerbaseref(peerbaseref);
					List<PjPeerItemInfo> itemList = this.pjPeerItemManager.getList(obj, null);
					if(itemList!=null){
						for(int j = 0;j<itemList.size();j++){
							childItemList.add(itemList.get(j));
						}
					}
				}
				if(childItemList.size()>0){
					request.setAttribute("childItemList", childItemList);
				}
				request.setAttribute("remark", parentItemList.get(0).getRemark());
			}			
		}
		return new ModelAndView("/peer/pj_peer_item_list");  
	}
	
	/**
	 * @author 岳春阳
	 * @description 同行评价评价项设置添加
	 */
	@RequestMapping(params="m=dosave",method=RequestMethod.POST) 
	public void doSave(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		String pjname=request.getParameter("pjname");
		String pjlevel=request.getParameter("pjlevel");
		String pjscore = request.getParameter("pjscore");
		String pjlevelscore=request.getParameter("pjlevelscore");
		String remark = request.getParameter("remark");
		String peerbaseref = request.getParameter("peerbaseref");
		String ptype = request.getParameter("ptype");
		//验证是修改还是添加
		PjPeerItemInfo objSign = new PjPeerItemInfo();
		objSign.setPtype(Integer.parseInt(ptype));
		objSign.setPeerbaseref(peerbaseref);
		List<PjPeerItemInfo> parentItemList = this.pjPeerItemManager.getList(objSign,null);
		//如果是修改，就显删除
		if(parentItemList!=null&&parentItemList.size()>0){
			Boolean b = this.pjPeerItemManager.doDelete(objSign);
		}
		if(pjname==null||pjname==""){
			je.setMsg("请填写评价项");
			response.getWriter().print(je.toJSON());
			return;
		}
		if(pjlevel==null||pjlevel==""){
			je.setMsg("请填写评价项等级");
			response.getWriter().print(je.toJSON());
			return;
		}
		if(pjscore==null||pjscore==""){
			je.setMsg("请填写评价项分数");
			response.getWriter().print(je.toJSON());
			return;
		}
		if(pjlevelscore==null||pjlevelscore==""){
			je.setMsg("请填写评价项等级分数");
			response.getWriter().print(je.toJSON());
			return;
		}
		String[] pjnames = pjname.split("\\|");
		String[] pjlevels = pjlevel.split("\\*");
		String[] pjscores = pjscore.split("\\|");
		String[] pjlevelscores = pjlevelscore.split("\\*");
		Boolean sign = false;
		if(pjnames.length==pjlevels.length&&pjlevels.length==pjscores.length&&pjscores.length==pjlevelscores.length){
			sign=true;
		}else{
			je.setMsg("系统异常，请检查评价项");
			response.getWriter().print(je.toJSON());
			return;
		}
		PjPeerItemInfo obj = null;
		//存放值得数据集集合
		List<String> sqlArrayList = new ArrayList<String>();
		//存放sql的集合
		List<List<Object>> objArrayList = new ArrayList<List<Object>>();
	    //拼sql的对象和存放值得对象
		List<Object> objList;
		StringBuilder sql ;	
		Boolean b = false;
        Object o = this.pjPeerItemManager.getNextId();
		if(sign){
			int ref = Integer.parseInt(o.toString())+1;
			for(int i =0;i<pjnames.length;i++){				
				obj = new PjPeerItemInfo();
				obj.setRef(ref);
				obj.setName(pjnames[i]);
				obj.setScore(Integer.parseInt(pjscores[i]));
				obj.setParentref(0);
				obj.setRemark(remark);
				obj.setPeerbaseref(peerbaseref);
				obj.setPtype(Integer.parseInt(ptype));
				obj.setLastoperateuserid(this.logined(request).getRef());
				obj.setOrdernum(ref);
				objList = new ArrayList<Object>();
				sql = new StringBuilder();
				objList = this.pjPeerItemManager.getSaveSql(obj, sql);
				sqlArrayList.add(sql.toString());
				objArrayList.add(objList);
				int parentref = ref;
				ref+=1;				
				String[] levels = pjlevels[i].split("\\|");
				String[] levelscores = pjlevelscores[i].split("\\|");
				for(int j = 0;j<levels.length;j++){
					obj = new PjPeerItemInfo();
					obj.setRef(ref);
					obj.setName(levels[j]);
					obj.setScore(Integer.parseInt(levelscores[j]));
					obj.setParentref(parentref);
					obj.setRemark(remark);
					obj.setPeerbaseref(peerbaseref);
					obj.setPtype(Integer.parseInt(ptype));
					obj.setLastoperateuserid(this.logined(request).getRef());
					obj.setOrdernum(ref);
					objList = new ArrayList<Object>();
					sql = new StringBuilder();
					objList = this.pjPeerItemManager.getSaveSql(obj, sql);
					sqlArrayList.add(sql.toString());
					objArrayList.add(objList);
					ref+=1;	
				}
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
	 * @description 同行评价评价项设置添加
	 */
	@RequestMapping(params="m=itemview",method=RequestMethod.POST)
    public ModelAndView getItemView(HttpServletRequest request,HttpServletResponse response,ModelAndView mp )throws Exception{
		JsonEntity je=new JsonEntity();
		if(request.getParameter("peerbaseref")==null){
			je.setMsg("异常错误，系统尚未发现您要预览的baseref,请刷新后重试！错误代码：peerbaseref is notfound!");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		//验证peerbaseref 信息
		PjPeerBaseInfo pb=new PjPeerBaseInfo();
		String ptype = request.getParameter("ptype");
		pb.setRef(request.getParameter("peerbaseref"));
		//获取评价类别
		String typename="";
		if(ptype.equals("1")){
			typename="教师评价";
		}else if(ptype.equals("2")){
			typename="职工评价";
		}
		request.setAttribute("typename", typename);
		List<PjPeerBaseInfo> pbList=this.pjPeerBaseManager.getList(pb, null);
		if(pbList==null||pbList.size()<1){
			je.setMsg("异常错误，系统尚未获取到您要预览的基础信息，请刷新页面后重试！pbList is notfound!");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		request.setAttribute("pbase", pbList.get(0));
		//获取参评部门
		String[] deptids = pbList.get(0).getDeptref().split("\\|");
		DeptInfo du = null;
		String deptnames="";
		if(deptids.length>1){
			for(int i = 0;i<deptids.length;i++){
				du = new DeptInfo();
				du.setDeptid(Integer.parseInt(deptids[i]));
				List<DeptInfo> ld = this.deptManager.getList(du, null);
				deptnames+=ld.get(0).getDeptname();
				if(i+1<deptids.length){
					deptnames+="、";
				}
			}
			
		}
		request.setAttribute("deptnames", deptnames);
		String pjidObj=request.getParameter("pjid");
		if(pjidObj.toString().trim().length()<1){
			je.setMsg("异常错误，系统尚未检测到必要的数据，请刷新面面后重试!错误代码：pjidobj is empty！");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		//进行分割
		String[] idArray=pjidObj.split("\\|");
		if(idArray.length<1){
			je.setMsg("添加失败!原因：idArray split is failed!");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;			
		}
		String saveparentObj=null,genderchild=null;;
		
		boolean isflag=false;
		//循环得到数据
		for (String tmpid : idArray) {
			if(tmpid!=null&&UtilTool.isNumber(tmpid)){
				//评价项目
				String pjItemStr=request.getParameter("pjparent"+tmpid);
				if(pjItemStr==null||pjItemStr.length()<1){
					je.setMsg("异常错误！数据不完整!错误代码：pjItemStr is empty! strTmpIdx:"+tmpid);
					isflag=true;
					break;
				}
				String pjscore=request.getParameter("pjscore"+tmpid);
				if(pjscore==null||pjscore.length()<1||!UtilTool.isDouble(pjscore)){
					je.setMsg("异常错误！数据不完整!错误代码：pjscore is empty! strTmpIdx:"+tmpid);
					isflag=true;
					break;
				}
				if(saveparentObj==null)
					saveparentObj=tmpid+","+pjItemStr+","+pjscore;
				else
					saveparentObj+="|"+tmpid+","+pjItemStr+","+pjscore;
				
				
				//得到等级
				String[] cldStrArray=request.getParameterValues("pjchild"+tmpid);
				if(cldStrArray==null||cldStrArray.length<1){
					je.setMsg("异常错误，数据不完整!错误代码：cldStrArray is empty!");
					isflag=true;
					break;
				}
				String[] levelScoreArray=request.getParameterValues("levelscore"+tmpid);
				if(levelScoreArray==null||levelScoreArray.length<1){
					je.setMsg("异常错误，数据不完整!错误代码：levelScoreArray is empty!");
					isflag=true;
					break;
				}
				if(levelScoreArray.length!=cldStrArray.length){
					je.setMsg("异常错误，数据不完整!错误代码：levelScoreArray.length<>cldStrArray.length!");
					isflag=true;
					break;
				}
				
				//组织CHILDREN数据
				for (int i = 0; i < levelScoreArray.length; i++) {
					if(levelScoreArray[i]==null||levelScoreArray[i].trim().length()<1
						||cldStrArray[i]==null||cldStrArray[i].trim().length()<1){
						je.setMsg("异常错误，数据不完整!错误代码：levelScoreArray["+i+"] OR cldStrArray["+i+"] is empty! ");
						isflag=true;
						break;
					}
					if(genderchild==null)
						genderchild=tmpid+","+cldStrArray[i]+","+levelScoreArray[i];
					else
						genderchild+="|"+tmpid+","+cldStrArray[i]+","+levelScoreArray[i];
				}
				if(isflag)break;
			}
		}
		if(isflag){		
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		request.setAttribute("parentobj", saveparentObj);
		request.setAttribute("childobj", genderchild);
		request.setAttribute("remark", request.getParameter("remark"));
//		je.setType("success");
//		je.setMsg("成功");
//		response.getWriter().print(je.toJSON());
		return new ModelAndView("/peer/pj_peer_item_view");  
	}
}
