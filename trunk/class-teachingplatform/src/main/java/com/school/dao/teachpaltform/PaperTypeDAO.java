package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.PaperTypeInfo;
import com.school.dao.inter.teachpaltform.IPaperTypeDAO;
import com.school.util.PageResult;

@Component  
public class PaperTypeDAO extends CommonDAO<PaperTypeInfo> implements IPaperTypeDAO {

	public Boolean doSave(PaperTypeInfo papertypeinfo) {
		if (papertypeinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(papertypeinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(PaperTypeInfo papertypeinfo) {
		if(papertypeinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(papertypeinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(PaperTypeInfo papertypeinfo) {
		if (papertypeinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(papertypeinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<PaperTypeInfo> getList(PaperTypeInfo papertypeinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL paper_type_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (papertypeinfo.getOrderidx() != null) {
			sqlbuilder.append("?,");
			objList.add(papertypeinfo.getOrderidx());
		} else
			sqlbuilder.append("null,");
		if (papertypeinfo.getPapertypename() != null) {
			sqlbuilder.append("?,");
			objList.add(papertypeinfo.getPapertypename());
		} else
			sqlbuilder.append("null,");
		if (papertypeinfo.getCtime() != null) {
			sqlbuilder.append("?,");
			objList.add(papertypeinfo.getCtime());
		} else
			sqlbuilder.append("null,");
		if (papertypeinfo.getMtime() != null) {
			sqlbuilder.append("?,");
			objList.add(papertypeinfo.getMtime());
		} else
			sqlbuilder.append("null,");
		if (papertypeinfo.getSubject() != null) {
			sqlbuilder.append("?,");
			objList.add(papertypeinfo.getSubject());
		} else
			sqlbuilder.append("null,");
		if (papertypeinfo.getPeriods() != null) {
			sqlbuilder.append("?,");
			objList.add(papertypeinfo.getPeriods());
		} else
			sqlbuilder.append("null,");
		if (papertypeinfo.getPapertypeid() != null) {
			sqlbuilder.append("?,");
			objList.add(papertypeinfo.getPapertypeid());
		} else
			sqlbuilder.append("null,");
		if (papertypeinfo.getCuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(papertypeinfo.getCuserid());
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
		List<PaperTypeInfo> papertypeinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, PaperTypeInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return papertypeinfoList;	
	}
	
	public List<Object> getSaveSql(PaperTypeInfo papertypeinfo, StringBuilder sqlbuilder) {
		if(papertypeinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL paper_type_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (papertypeinfo.getOrderidx() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getOrderidx());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getPapertypename() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getPapertypename());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getCtime());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getMtime() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getMtime());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getSubject() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getSubject());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getPeriods() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getPeriods());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getPapertypeid() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getPapertypeid());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getCuserid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(PaperTypeInfo papertypeinfo, StringBuilder sqlbuilder) {
		if(papertypeinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL paper_type_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (papertypeinfo.getOrderidx() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getOrderidx());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getPapertypename() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getPapertypename());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getCtime());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getMtime() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getMtime());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getSubject() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getSubject());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getPeriods() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getPeriods());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getPapertypeid() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getPapertypeid());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getCuserid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(PaperTypeInfo papertypeinfo, StringBuilder sqlbuilder) {
		if(papertypeinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL paper_type_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (papertypeinfo.getOrderidx() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getOrderidx());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getPapertypename() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getPapertypename());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getCtime());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getMtime() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getMtime());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getSubject() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getSubject());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getPeriods() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getPeriods());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getPapertypeid() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getPapertypeid());
			} else
				sqlbuilder.append("null,");
			if (papertypeinfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(papertypeinfo.getCuserid());
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
