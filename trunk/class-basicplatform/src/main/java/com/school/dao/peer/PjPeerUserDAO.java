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
import com.school.dao.inter.peer.IPjPeerUserDAO;
import com.school.entity.peer.PjPeerUserInfo;
import com.school.util.PageResult;
@Component
public class PjPeerUserDAO extends CommonDAO<PjPeerUserInfo> implements IPjPeerUserDAO {

	public Boolean doDelete(PjPeerUserInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null){
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

	public Boolean doSave(PjPeerUserInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = this.getSaveSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if(afficeObj != null && afficeObj.toString().trim().length()>0 
				&& Integer.parseInt(afficeObj.toString())>0){
			return true;
		}
		return false;
	}

	public Boolean doUpdate(PjPeerUserInfo obj) {
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

	public List<Object> getDeleteSql(PjPeerUserInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{ call pj_peer_user_proc_delete(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getPeerbaseid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getPeerbaseid());
		}else{
			sqlbuilder.append("NULL,");
		}
		
		sqlbuilder.append("?)}");
		return objlist;
	}

	public List<PjPeerUserInfo> getList(PjPeerUserInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder("{call pj_peer_user_proc_search_split(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj==null){
			sqlbuilder.append("NULL,NULL,NULL,NULL,");
		}else{
			if(obj.getPeerbaseid()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getPeerbaseid());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getDeptid()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getDeptid());
			}else{
				sqlbuilder.append("NULL,");
			}	
			if(obj.getPtype()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getPtype());
			}else{
				sqlbuilder.append("NULL,");
			}	
			if(obj.getUserid()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getUserid());
			}else{
				sqlbuilder.append("NULL,");
			}
			
		}
		if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
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
		List<PjPeerUserInfo> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, types, PjPeerUserInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));	
		return list;
	}

	

	public List<Object> getSaveSql(PjPeerUserInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{ call pj_peer_user_proc_add(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getUserid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getUserid());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getPeerbaseid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getPeerbaseid());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getPtype()>0){
			sqlbuilder.append("?,");
			objlist.add(obj.getPtype());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getDeptid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getDeptid());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		return objlist;
	}

	public List<Object> getUpdateSql(PjPeerUserInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{ call pj_peer_user_proc_updatetype(");
		List<Object> objlist = new ArrayList<Object>();		
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getRef());
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
		return objlist;
	}


}
