package com.school.dao.personalspace;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.personalspace.PsUserFriend;
import com.school.dao.inter.personalspace.IPsUserFriendDAO;
import com.school.util.PageResult;

@Component  
public class PsUserFriendDAO extends CommonDAO<PsUserFriend> implements IPsUserFriendDAO {

	public Boolean doSave(PsUserFriend psuserfriend) {
		if (psuserfriend == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(psuserfriend, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(PsUserFriend psuserfriend) {
		if(psuserfriend==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(psuserfriend, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(PsUserFriend psuserfriend) {
		if (psuserfriend == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(psuserfriend, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<PsUserFriend> getList(PsUserFriend psuserfriend, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL ps_j_user_friend_proc_split(");
		List<Object> objList=new ArrayList<Object>();
        if (psuserfriend.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(psuserfriend.getRef());
        } else
            sqlbuilder.append("null,");
        if (psuserfriend.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(psuserfriend.getUserid());
        } else
            sqlbuilder.append("null,");
        if (psuserfriend.getFriendid() != null) {
            sqlbuilder.append("?,");
            objList.add(psuserfriend.getFriendid());
        } else
            sqlbuilder.append("null,");
		if (psuserfriend.getIsaccept() != null) {
			sqlbuilder.append("?,");
			objList.add(psuserfriend.getIsaccept());
		} else
			sqlbuilder.append("null,");
		if (psuserfriend.getGroupid() != null) {
			sqlbuilder.append("?,");
			objList.add(psuserfriend.getGroupid());
		} else
			sqlbuilder.append("null,");

		if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
			sqlbuilder.append("?,?,");
			objList.add(presult.getPageNo());
			objList.add(presult.getPageSize());
		}else{
			sqlbuilder.append("NULL,NULL,");
		}
		if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
			sqlbuilder.append("?,");
			objList.add(presult.getOrderBy());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");	
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List<PsUserFriend> psuserfriendList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, PsUserFriend.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return psuserfriendList;	
	}
	
	public List<Object> getSaveSql(PsUserFriend psuserfriend, StringBuilder sqlbuilder) {
		if(psuserfriend==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL ps_j_user_friend_proc_add(");
		List<Object>objList = new ArrayList<Object>();
        if (psuserfriend.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(psuserfriend.getRef());
        } else
            sqlbuilder.append("null,");
        if (psuserfriend.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(psuserfriend.getUserid());
        } else
            sqlbuilder.append("null,");
        if (psuserfriend.getFriendid() != null) {
            sqlbuilder.append("?,");
            objList.add(psuserfriend.getFriendid());
        } else
            sqlbuilder.append("null,");
        if (psuserfriend.getIsaccept() != null) {
            sqlbuilder.append("?,");
            objList.add(psuserfriend.getIsaccept());
        } else
            sqlbuilder.append("null,");
        if (psuserfriend.getGroupid() != null) {
            sqlbuilder.append("?,");
            objList.add(psuserfriend.getGroupid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(PsUserFriend psuserfriend, StringBuilder sqlbuilder) {
		if(psuserfriend==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL ps_j_user_friend_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
        if (psuserfriend.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(psuserfriend.getRef());
        } else
            sqlbuilder.append("null,");
        if (psuserfriend.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(psuserfriend.getUserid());
        } else
            sqlbuilder.append("null,");
        if (psuserfriend.getFriendid() != null) {
            sqlbuilder.append("?,");
            objList.add(psuserfriend.getFriendid());
        } else
            sqlbuilder.append("null,");
        if (psuserfriend.getIsaccept() != null) {
            sqlbuilder.append("?,");
            objList.add(psuserfriend.getIsaccept());
        } else
            sqlbuilder.append("null,");
        if (psuserfriend.getGroupid() != null) {
            sqlbuilder.append("?,");
            objList.add(psuserfriend.getGroupid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(PsUserFriend psuserfriend, StringBuilder sqlbuilder) {
		if(psuserfriend==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL ps_j_user_friend_proc_update(");
		List<Object>objList = new ArrayList<Object>();
        if (psuserfriend.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(psuserfriend.getRef());
        } else
            sqlbuilder.append("null,");
        if (psuserfriend.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(psuserfriend.getUserid());
        } else
            sqlbuilder.append("null,");
        if (psuserfriend.getFriendid() != null) {
            sqlbuilder.append("?,");
            objList.add(psuserfriend.getFriendid());
        } else
            sqlbuilder.append("null,");
        if (psuserfriend.getIsaccept() != null) {
            sqlbuilder.append("?,");
            objList.add(psuserfriend.getIsaccept());
        } else
            sqlbuilder.append("null,");
        if (psuserfriend.getGroupid() != null) {
            sqlbuilder.append("?,");
            objList.add(psuserfriend.getGroupid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.executeArray_SQL(sqlArrayList, objArrayList);
	}
	
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public List<PsUserFriend> getMyFriendList(PsUserFriend t, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL ps_qry_myfriend_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if (t.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(t.getUserid());
        } else
            sqlbuilder.append("null,");
        if (t.getGroupid() != null) {
            sqlbuilder.append("?,");
            objList.add(t.getGroupid());
        } else
            sqlbuilder.append("null,");
        if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        }else{
            sqlbuilder.append("NULL,NULL,");
        }
        if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        }else{
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<PsUserFriend> psuserfriendList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, PsUserFriend.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return psuserfriendList;
    }
}
