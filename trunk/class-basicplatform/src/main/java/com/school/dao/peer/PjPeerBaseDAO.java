package com.school.dao.peer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.peer.IPjPeerBaseDAO;
import com.school.entity.DeptUser;
import com.school.entity.UserInfo;
import com.school.entity.peer.PjPeerBaseInfo;
import com.school.util.PageResult;
@Component
public class PjPeerBaseDAO extends CommonDAO<PjPeerBaseInfo> implements
		IPjPeerBaseDAO {

	
	public Boolean doDelete(PjPeerBaseInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null||obj.getRef()==null){
			return false;
		}
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = getDeleteSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}
		return false;
	}

	public Boolean doSave(PjPeerBaseInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = getSaveSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if(afficeObj != null && afficeObj.toString().trim().length()>0 
				&& Integer.parseInt(afficeObj.toString())>0){
			return true;
		}		
		return false;
	}

	public Boolean doUpdate(PjPeerBaseInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = getUpdateSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public List<Object> getDeleteSql(PjPeerBaseInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||obj.getRef()==null){
			return null;
		}
		sqlbuilder.append("{call pj_peer_base_proc_delete(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getRef()!=null&&obj.getRef()!=""){
			sqlbuilder.append("?,");
			objlist.add(obj.getRef());
		}else{
			sqlbuilder.append("NULL");
		}
		sqlbuilder.append("?)}");
		return objlist;
	}

	public List<PjPeerBaseInfo> getList(PjPeerBaseInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder("{call pj_peer_base_proc_search_split(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj==null){
			sqlbuilder.append("NULL,NULL,");
		}else{
			if(obj.getRef()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getRef());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getNtimestring()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getNtimestring());
			}else{
				sqlbuilder.append("NULL,");
			}
			
		}
		if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
			System.out.println(presult.getPageNo());
			sqlbuilder.append("?,?,");
			objlist.add(presult.getPageNo());
			objlist.add(presult.getPageSize());
		}else{
			sqlbuilder.append("NULL,NULL,");
		}
		if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
			sqlbuilder.append("?,");
			objlist.add(presult.getOrderBy());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");	
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List<PjPeerBaseInfo> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, types, PjPeerBaseInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));	
		return list;
	}

	public List<Object> getSaveSql(PjPeerBaseInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		List<Object> objlist = new ArrayList<Object>();
		if(obj==null){
			return null;
		}else{		
			sqlbuilder.append("{call pj_peer_base_proc_add(");
			if(obj.getRef()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getRef());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getName()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getName());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getBtimestring()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getBtimestring());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getEtimestring()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getEtimestring());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getYear()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getYear());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getCuserid()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getCuserid());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getDeptref()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getDeptref());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getRemark()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getRemark());
			}else{
				sqlbuilder.append("NULL,");
			}
			sqlbuilder.append("?)}");
		}
		return objlist;
	}

	public List<Object> getUpdateSql(PjPeerBaseInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		List<Object> objlist = new ArrayList<Object>();
		if(obj==null){
			return null;
		}else{		
			sqlbuilder.append("{call pj_peer_base_proc_update(");
			if(obj.getRef()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getRef());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getName()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getName());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getBtimestring()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getBtimestring());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getEtimestring()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getEtimestring());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getYear()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getYear());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getCuserid()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getCuserid());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getDeptref()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getDeptref());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getRemark()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getRemark());
			}else{
				sqlbuilder.append("NULL,");
			}
			sqlbuilder.append("?)}");
		}
		return objlist;
	}

	public List checkUserList(String deptref) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder("{call pj_peer_base_proc_checkuser(");
		List<Object> objlist = new ArrayList<Object>();
		if(deptref==null){
			sqlbuilder.append("NULL,");
		}else{
			sqlbuilder.append("?,");
			objlist.add(deptref);
		}	
		sqlbuilder.append("?)}");
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List list = this.executeResult_PROC(sqlbuilder.toString(), objlist, types, null,objArray);		
		return list;
	}

	public List<DeptUser> getUserListByDeptref(String deptref) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder("{call pj_peer_base_proc_getuser(");
		List<Object> objlist = new ArrayList<Object>();
		if(deptref==null){
			sqlbuilder.append("NULL,");
		}else{
			sqlbuilder.append("?,");
			objlist.add(deptref);
		}	
		sqlbuilder.append("?)}");
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List<DeptUser> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, types, DeptUser.class,objArray);		
		return list;
	}

}
