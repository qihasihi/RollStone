package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.TpPaperInfo;
import com.school.dao.inter.teachpaltform.ITpPaperDAO;
import com.school.util.PageResult;

@Component  
public class TpPaperDAO extends CommonDAO<TpPaperInfo> implements ITpPaperDAO {

	public Boolean doSave(TpPaperInfo tppaperinfo) {
		if (tppaperinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tppaperinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TpPaperInfo tppaperinfo) {
		if(tppaperinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tppaperinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TpPaperInfo tppaperinfo) {
		if (tppaperinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tppaperinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TpPaperInfo> getList(TpPaperInfo tppaperinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_paper_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (tppaperinfo.getCtime() != null) {
			sqlbuilder.append("?,");
			objList.add(tppaperinfo.getCtime());
		} else
			sqlbuilder.append("null,");
		if (tppaperinfo.getMtime() != null) {
			sqlbuilder.append("?,");
			objList.add(tppaperinfo.getMtime());
		} else
			sqlbuilder.append("null,");
		if (tppaperinfo.getPaperid() != null) {
			sqlbuilder.append("?,");
			objList.add(tppaperinfo.getPaperid());
		} else
			sqlbuilder.append("null,");
		if (tppaperinfo.getCuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(tppaperinfo.getCuserid());
		} else
			sqlbuilder.append("null,");
		if (tppaperinfo.getCourseid() != null) {
			sqlbuilder.append("?,");
			objList.add(tppaperinfo.getCourseid());
		} else
			sqlbuilder.append("null,");
		if (tppaperinfo.getCloudstatus() != null) {
			sqlbuilder.append("?,");
			objList.add(tppaperinfo.getCloudstatus());
		} else
			sqlbuilder.append("null,");
		if (tppaperinfo.getPapername() != null) {
			sqlbuilder.append("?,");
			objList.add(tppaperinfo.getPapername());
		} else
			sqlbuilder.append("null,");
		if (tppaperinfo.getUsecount() != null) {
			sqlbuilder.append("?,");
			objList.add(tppaperinfo.getUsecount());
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
		List<TpPaperInfo> tppaperinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpPaperInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tppaperinfoList;	
	}
	
	public List<Object> getSaveSql(TpPaperInfo tppaperinfo, StringBuilder sqlbuilder) {
		if(tppaperinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_paper_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (tppaperinfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getCtime());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getMtime() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getMtime());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getPaperid());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getCuserid());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getCourseid());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getCloudstatus() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getCloudstatus());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getPapername() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getPapername());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getUsecount() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getUsecount());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TpPaperInfo tppaperinfo, StringBuilder sqlbuilder) {
		if(tppaperinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_paper_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (tppaperinfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getCtime());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getMtime() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getMtime());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getPaperid());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getCuserid());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getCourseid());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getCloudstatus() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getCloudstatus());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getPapername() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getPapername());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getUsecount() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getUsecount());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TpPaperInfo tppaperinfo, StringBuilder sqlbuilder) {
		if(tppaperinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_paper_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (tppaperinfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getCtime());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getMtime() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getMtime());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getPaperid());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getCuserid());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getCourseid());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getCloudstatus() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getCloudstatus());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getPapername() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getPapername());
			} else
				sqlbuilder.append("null,");
			if (tppaperinfo.getUsecount() != null) {
				sqlbuilder.append("?,");
				objList.add(tppaperinfo.getUsecount());
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
