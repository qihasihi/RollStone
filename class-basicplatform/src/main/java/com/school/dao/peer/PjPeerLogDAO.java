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
import com.school.dao.inter.peer.IPjPeerLogDAO;
import com.school.entity.DeptInfo;
import com.school.entity.peer.PjPeerBaseInfo;
import com.school.entity.peer.PjPeerLogInfo;
import com.school.util.PageResult;

@Component
public class PjPeerLogDAO extends CommonDAO<PjPeerLogInfo> implements
		IPjPeerLogDAO {

	public Boolean doDelete(PjPeerLogInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null||obj.getPeerbaseref()==null){
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

	public Boolean doSave(PjPeerLogInfo obj) {
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

	public Boolean doUpdate(PjPeerLogInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getDeleteSql(PjPeerLogInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||obj.getPeerbaseref()==null){
			return null;
		}
		sqlbuilder.append("{call pj_peer_log_proc_delete(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getPeerbaseref()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getPeerbaseref());
		}else{
			sqlbuilder.append("NULL");
		}
		if(obj.getPtype()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getPtype());
		}else{
			sqlbuilder.append("NULL");
		}
		sqlbuilder.append("?)}");
		return objlist;
	}

	public List<PjPeerLogInfo> getList(PjPeerLogInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder("{call pj_peer_log_proc_search(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj==null){
			sqlbuilder.append("NULL,NULL,");
		}else{
			if(obj.getPeerbaseref()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getPeerbaseref());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getPtype()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getPtype());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getCuserid()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getCuserid());
			}else{
				sqlbuilder.append("NULL,");
			}
		}
		sqlbuilder.append("?)}");	
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List<PjPeerLogInfo> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, types, PjPeerLogInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));	
		return list;
	}


	public List<Object> getSaveSql(PjPeerLogInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		List<Object> objlist = new ArrayList<Object>();
		if(obj==null){
			return null;
		}else{		
			sqlbuilder.append("{call pj_peer_log_proc_add(");
			if(obj.getPeeritemref()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getPeeritemref());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getScore()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getScore());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getUserid()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getUserid());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getCuserid()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getCuserid());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getSysdelflag()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getSysdelflag());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getPeerbaseref()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getPeerbaseref());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getPtype()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getPtype());
			}else{
				sqlbuilder.append("NULL,");
			}
			sqlbuilder.append("?)}");
		}
		return objlist;
	}

	public List<Object> getUpdateSql(PjPeerLogInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<List<String>> getLogStat(String peerbaseref,
			Integer ptype, String deptid) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder("{call pj_peer_log_proc_stat(");
		List<Object> objlist = new ArrayList<Object>();
		if(peerbaseref!=null){
			sqlbuilder.append("?,");
			objlist.add(peerbaseref);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(ptype!=null){
			sqlbuilder.append("?,");
			objlist.add(ptype);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(deptid != null){
			sqlbuilder.append("?");
			objlist.add(deptid);
		}else{
			sqlbuilder.append("NULL");
		}
		sqlbuilder.append(")}");
		List<List<String>> logList = this.executeResultProcedure(sqlbuilder.toString(), objlist);
		return logList;
	}

	public List<DeptInfo> getDeptInfo(String deptref) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder("{call pj_peer_log_proc_getdept(");
		List<Object> objlist = new ArrayList<Object>();
		if(deptref!=null){
			sqlbuilder.append("?");
			objlist.add(deptref);
		}else{
			sqlbuilder.append("NULL");
		}
		sqlbuilder.append(")}");
		List<DeptInfo> deptList = this.executeResult_PROC(sqlbuilder.toString(), objlist, null, DeptInfo.class, null);
		return deptList;
	}


}
