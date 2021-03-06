package com.school.dao.personalspace;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.personalspace.PsFriendGroupInfo;
import com.school.dao.inter.personalspace.IPsFriendGroupDAO;
import com.school.util.PageResult;

@Component  
public class PsFriendGroupDAO extends CommonDAO<PsFriendGroupInfo> implements IPsFriendGroupDAO {

	public Boolean doSave(PsFriendGroupInfo psfriendgroupinfo) {
		if (psfriendgroupinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(psfriendgroupinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(PsFriendGroupInfo psfriendgroupinfo) {
		if(psfriendgroupinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(psfriendgroupinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(PsFriendGroupInfo psfriendgroupinfo) {
		if (psfriendgroupinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(psfriendgroupinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<PsFriendGroupInfo> getList(PsFriendGroupInfo psfriendgroupinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL ps_friend_group_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (psfriendgroupinfo.getGroupid() != null) {
			sqlbuilder.append("?,");
			objList.add(psfriendgroupinfo.getGroupid());
		} else
			sqlbuilder.append("null,");
        if (psfriendgroupinfo.getGroupname() != null) {
            sqlbuilder.append("?,");
            objList.add(psfriendgroupinfo.getGroupname());
        } else
            sqlbuilder.append("null,");
        if (psfriendgroupinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(psfriendgroupinfo.getCuserid());
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
		List<PsFriendGroupInfo> psfriendgroupinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, PsFriendGroupInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return psfriendgroupinfoList;	
	}
	
	public List<Object> getSaveSql(PsFriendGroupInfo psfriendgroupinfo, StringBuilder sqlbuilder) {
		if(psfriendgroupinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL ps_friend_group_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (psfriendgroupinfo.getGroupid() != null) {
				sqlbuilder.append("?,");
				objList.add(psfriendgroupinfo.getGroupid());
			} else
				sqlbuilder.append("null,");
            if (psfriendgroupinfo.getGroupname() != null) {
                sqlbuilder.append("?,");
                objList.add(psfriendgroupinfo.getGroupname());
            } else
                sqlbuilder.append("null,");
            if (psfriendgroupinfo.getCuserid() != null) {
                sqlbuilder.append("?,");
                objList.add(psfriendgroupinfo.getCuserid());
            } else
                sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(PsFriendGroupInfo psfriendgroupinfo, StringBuilder sqlbuilder) {
		if(psfriendgroupinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL ps_friend_group_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
        if (psfriendgroupinfo.getGroupid() != null) {
            sqlbuilder.append("?,");
            objList.add(psfriendgroupinfo.getGroupid());
        } else
            sqlbuilder.append("null,");
        if (psfriendgroupinfo.getGroupname() != null) {
            sqlbuilder.append("?,");
            objList.add(psfriendgroupinfo.getGroupname());
        } else
            sqlbuilder.append("null,");
        if (psfriendgroupinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(psfriendgroupinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(PsFriendGroupInfo psfriendgroupinfo, StringBuilder sqlbuilder) {
		if(psfriendgroupinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL ps_friend_group_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
        if (psfriendgroupinfo.getGroupid() != null) {
            sqlbuilder.append("?,");
            objList.add(psfriendgroupinfo.getGroupid());
        } else
            sqlbuilder.append("null,");
        if (psfriendgroupinfo.getGroupname() != null) {
            sqlbuilder.append("?,");
            objList.add(psfriendgroupinfo.getGroupname());
        } else
            sqlbuilder.append("null,");
        if (psfriendgroupinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(psfriendgroupinfo.getCuserid());
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

}
