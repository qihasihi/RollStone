/**
 * 
 */
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
import com.school.dao.inter.peer.IPjPeerItemDAO;
import com.school.entity.notice.NoticeInfo;
import com.school.entity.peer.PjPeerItemInfo;
import com.school.util.PageResult;

/**
 * @author admin
 *
 */
@Component
public class PjPeerItemDAO extends CommonDAO<PjPeerItemInfo> implements
		IPjPeerItemDAO {

	/* (non-Javadoc)
	 * @see com.school.dao.base.ICommonDAO#doDelete(java.lang.Object)
	 */
	public Boolean doDelete(PjPeerItemInfo obj) {
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

	/* (non-Javadoc)
	 * @see com.school.dao.base.ICommonDAO#doSave(java.lang.Object)
	 */
	public Boolean doSave(PjPeerItemInfo obj) {
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

	/* (non-Javadoc)
	 * @see com.school.dao.base.ICommonDAO#doUpdate(java.lang.Object)
	 */
	public Boolean doUpdate(PjPeerItemInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.school.dao.base.ICommonDAO#getDeleteSql(java.lang.Object, java.lang.StringBuilder)
	 */
	public List<Object> getDeleteSql(PjPeerItemInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||obj.getPeerbaseref()==null){
			return null;
		}
		sqlbuilder.append("{call pj_peer_item_proc_delete(");
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

	/* (non-Javadoc)
	 * @see com.school.dao.base.ICommonDAO#getList(java.lang.Object, com.school.util.PageResult)
	 */
	public List<PjPeerItemInfo> getList(PjPeerItemInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		if(obj==null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder("{call pj_peer_item_proc_search(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getParentref()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getParentref());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getPtype()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getPtype());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getPeerbaseref()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getPeerbaseref());
		}else{
			sqlbuilder.append("NULL,");
		}
		
		sqlbuilder.append("?)}");	
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List<PjPeerItemInfo> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, types, PjPeerItemInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));	
		return list;
	}

	/* (non-Javadoc)
	 * @see com.school.dao.base.ICommonDAO#getSaveSql(java.lang.Object, java.lang.StringBuilder)
	 */
	public List<Object> getSaveSql(PjPeerItemInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		List<Object> objlist = new ArrayList<Object>();
		if(obj==null){
			return null;
		}else{		
			sqlbuilder.append("{call pj_peer_item_proc_add(");
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
			if(obj.getScore()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getScore());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getParentref()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getParentref());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getRemark()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getRemark());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getType()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getType());
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
			if(obj.getLastoperateuserid()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getLastoperateuserid());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getOrdernum()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getOrdernum());
			}else{
				sqlbuilder.append("NULL,");
			}
			sqlbuilder.append("?)}");
		}
		return objlist;
	}

	/* (non-Javadoc)
	 * @see com.school.dao.base.ICommonDAO#getUpdateSql(java.lang.Object, java.lang.StringBuilder)
	 */
	public List<Object> getUpdateSql(PjPeerItemInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}
    public String getNextId(){
        String sql = "select max(ref) from pj_peer_item_info";
        Object o = this.executeSalar_SQL(sql,null);
        return o.toString();
    }

}
