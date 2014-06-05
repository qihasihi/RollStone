package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.ClassCadresInfo;
import com.school.dao.inter.teachpaltform.IClassCadresDAO;
import com.school.util.PageResult;

@Component  
public class ClassCadresDAO extends CommonDAO<ClassCadresInfo> implements IClassCadresDAO {

	public Boolean doSave(ClassCadresInfo classcadresinfo) {
		if (classcadresinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(classcadresinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(ClassCadresInfo classcadresinfo) {
		if(classcadresinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(classcadresinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(ClassCadresInfo classcadresinfo) {
		if (classcadresinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(classcadresinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<ClassCadresInfo> getList(ClassCadresInfo classcadresinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL class_cadres_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (classcadresinfo.getSubjectid() != null) {
			sqlbuilder.append("?,");
			objList.add(classcadresinfo.getSubjectid());
		} else
			sqlbuilder.append("null,");
		if (classcadresinfo.getCadrestype() != null) {
			sqlbuilder.append("?,");
			objList.add(classcadresinfo.getCadrestype());
		} else
			sqlbuilder.append("null,");
		if (classcadresinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(classcadresinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (classcadresinfo.getCtime() != null) {
			sqlbuilder.append("?,");
			objList.add(classcadresinfo.getCtime());
		} else
			sqlbuilder.append("null,");
		if (classcadresinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(classcadresinfo.getUserid());
		} else
			sqlbuilder.append("null,");
		if (classcadresinfo.getClassid() != null) {
			sqlbuilder.append("?,");
			objList.add(classcadresinfo.getClassid());
		} else
			sqlbuilder.append("null,");
		if (classcadresinfo.getClasstype() != null) {
			sqlbuilder.append("?,");
			objList.add(classcadresinfo.getClasstype());
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
		List<ClassCadresInfo> classcadresinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, ClassCadresInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return classcadresinfoList;	
	}
	
	public List<Object> getSaveSql(ClassCadresInfo classcadresinfo, StringBuilder sqlbuilder) {
		if(classcadresinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL class_cadres_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (classcadresinfo.getSubjectid() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getSubjectid());
			} else
				sqlbuilder.append("null,");
			if (classcadresinfo.getCadrestype() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getCadrestype());
			} else
				sqlbuilder.append("null,");
			if (classcadresinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (classcadresinfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getCtime());
			} else
				sqlbuilder.append("null,");
			if (classcadresinfo.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getUserid());
			} else
				sqlbuilder.append("null,");
			if (classcadresinfo.getClassid() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getClassid());
			} else
				sqlbuilder.append("null,");
			if (classcadresinfo.getClasstype() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getClasstype());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(ClassCadresInfo classcadresinfo, StringBuilder sqlbuilder) {
		if(classcadresinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL class_cadres_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (classcadresinfo.getSubjectid() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getSubjectid());
			} else
				sqlbuilder.append("null,");
			if (classcadresinfo.getCadrestype() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getCadrestype());
			} else
				sqlbuilder.append("null,");
			if (classcadresinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (classcadresinfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getCtime());
			} else
				sqlbuilder.append("null,");
			if (classcadresinfo.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getUserid());
			} else
				sqlbuilder.append("null,");
			if (classcadresinfo.getClassid() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getClassid());
			} else
				sqlbuilder.append("null,");
			if (classcadresinfo.getClasstype() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getClasstype());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(ClassCadresInfo classcadresinfo, StringBuilder sqlbuilder) {
		if(classcadresinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL class_cadres_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (classcadresinfo.getSubjectid() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getSubjectid());
			} else
				sqlbuilder.append("null,");
			if (classcadresinfo.getCadrestype() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getCadrestype());
			} else
				sqlbuilder.append("null,");
			if (classcadresinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (classcadresinfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getCtime());
			} else
				sqlbuilder.append("null,");
			if (classcadresinfo.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getUserid());
			} else
				sqlbuilder.append("null,");
			if (classcadresinfo.getClassid() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getClassid());
			} else
				sqlbuilder.append("null,");
			if (classcadresinfo.getClasstype() != null) {
				sqlbuilder.append("?,");
				objList.add(classcadresinfo.getClasstype());
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
