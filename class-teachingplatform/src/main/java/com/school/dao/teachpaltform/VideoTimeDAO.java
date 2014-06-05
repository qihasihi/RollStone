package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.teachpaltform.IVideoTimeDAO;
import com.school.entity.UserInfo;
import com.school.entity.teachpaltform.VideoTimeInfo;
import com.school.util.PageResult;

@Component  
public class VideoTimeDAO extends CommonDAO<VideoTimeInfo> implements
		IVideoTimeDAO {

	public Boolean doDelete(VideoTimeInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean doSave(VideoTimeInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean doUpdate(VideoTimeInfo obj) {
		if (obj == null) {
			return false;
		}

		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public List<Object> getDeleteSql(VideoTimeInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<VideoTimeInfo> getList(VideoTimeInfo obj, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL video_time_interval_proc_split(");
		List<Object> objList = new ArrayList<Object>();
		if (obj == null)
			sqlbuilder.append("NULL,NULL,");
		else {
			if (obj.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(obj.getRef());
			} else
				sqlbuilder.append("NULL,");
			if (obj.getFlag() != null) {
				sqlbuilder.append("?,");
				objList.add(obj.getFlag());
			} else
				sqlbuilder.append("NULL,");
		}
		if (presult != null && presult.getPageNo() > 0
				&& presult.getPageSize() > 0) {
			sqlbuilder.append("?,?,");
			objList.add(presult.getPageNo());
			objList.add(presult.getPageSize());
		} else {
			sqlbuilder.append("NULL,NULL,");
		}
		if (presult != null && presult.getOrderBy() != null
				&& presult.getOrderBy().trim().length() > 0) {
			sqlbuilder.append("?,");
			objList.add(presult.getOrderBy());
		} else {
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		List<Integer> types = new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray = new Object[1];
		List<VideoTimeInfo> timeList = this
				.executeResult_PROC(sqlbuilder.toString(), objList, types,
						VideoTimeInfo.class, objArray);
		if (presult != null && objArray[0] != null
				&& objArray[0].toString().trim().length() > 0)
			presult
					.setRecTotal(Integer
							.parseInt(objArray[0].toString().trim()));
		return timeList;
	}

	public List<Object> getSaveSql(VideoTimeInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getUpdateSql(VideoTimeInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call video_time_interval_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRef()); 
		}else  
			sqlbuilder.append("NULL,");
		if(obj.getFlag()!=null){  
			sqlbuilder.append("?,");
			objList.add(obj.getFlag());  
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;     
	}

}
