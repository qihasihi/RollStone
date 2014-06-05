package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.TpVirtualClassStudent;
import com.school.dao.inter.teachpaltform.ITpVirtualClassStudentDAO;
import com.school.util.PageResult;

@Component  
public class TpVirtualClassStudentDAO extends CommonDAO<TpVirtualClassStudent> implements ITpVirtualClassStudentDAO {

	public Boolean doSave(TpVirtualClassStudent tpvirtualclassstudent) {
		if (tpvirtualclassstudent == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tpvirtualclassstudent, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TpVirtualClassStudent tpvirtualclassstudent) {
		if(tpvirtualclassstudent==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tpvirtualclassstudent, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TpVirtualClassStudent tpvirtualclassstudent) {
		if (tpvirtualclassstudent == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tpvirtualclassstudent, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TpVirtualClassStudent> getList(TpVirtualClassStudent tpvirtualclassstudent, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_j_virtual_class_student_proc_split(");
		List<Object> objList=new ArrayList<Object>();
        if (tpvirtualclassstudent.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpvirtualclassstudent.getRef());
        } else
            sqlbuilder.append("null,");
		if (tpvirtualclassstudent.getVirtualclassid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpvirtualclassstudent.getVirtualclassid());
		} else
			sqlbuilder.append("null,");
        if (tpvirtualclassstudent.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpvirtualclassstudent.getCuserid());
        } else
            sqlbuilder.append("null,");
		if (tpvirtualclassstudent.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpvirtualclassstudent.getUserid());
		} else
			sqlbuilder.append("null,");
        if (tpvirtualclassstudent.getStateid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpvirtualclassstudent.getStateid());
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
		List<TpVirtualClassStudent> tpvirtualclassstudentList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpVirtualClassStudent.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tpvirtualclassstudentList;	
	}

    public List<Map<String,Object>> getStudentList(String grade,Integer classid,String stuname,String year,Integer virclassid){
        StringBuilder sqlbuilder = new StringBuilder();
        if(virclassid==null)
            return null;
        sqlbuilder.append("{CALL tp_j_virtual_class_student_by_gradeclass(");
        List<Object> objList=new ArrayList<Object>();
        if (grade != null) {
            sqlbuilder.append("?,");
            objList.add(grade);
        } else
            sqlbuilder.append("null,");
        if (classid != null) {
            sqlbuilder.append("?,");
            objList.add(classid);
        } else
            sqlbuilder.append("null,");
        if (stuname != null) {
            sqlbuilder.append("?,");
            objList.add(stuname);
        } else
            sqlbuilder.append("null,");
        if (year != null) {
            sqlbuilder.append("?,");
            objList.add(year);
        } else
            sqlbuilder.append("null,");
        if (virclassid != null) {
            sqlbuilder.append("?,");
            objList.add(virclassid);
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");

        List<Map<String,Object>> tpvirtualclassstudentList=this.executeResultListMap_PROC(sqlbuilder.toString(), objList);
        return tpvirtualclassstudentList;
    }
	
	public List<Object> getSaveSql(TpVirtualClassStudent tpvirtualclassstudent, StringBuilder sqlbuilder) {
		if(tpvirtualclassstudent==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_virtual_class_student_proc_add(");
		List<Object>objList = new ArrayList<Object>();
        if (tpvirtualclassstudent.getVirtualclassid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpvirtualclassstudent.getVirtualclassid());
        } else
            sqlbuilder.append("null,");
        if (tpvirtualclassstudent.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpvirtualclassstudent.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tpvirtualclassstudent.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpvirtualclassstudent.getUserid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TpVirtualClassStudent tpvirtualclassstudent, StringBuilder sqlbuilder) {
		if(tpvirtualclassstudent==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_virtual_class_student_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
        if (tpvirtualclassstudent.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpvirtualclassstudent.getRef());
        } else
            sqlbuilder.append("null,");
        if (tpvirtualclassstudent.getVirtualclassid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpvirtualclassstudent.getVirtualclassid());
        } else
            sqlbuilder.append("null,");
        if (tpvirtualclassstudent.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpvirtualclassstudent.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tpvirtualclassstudent.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpvirtualclassstudent.getUserid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TpVirtualClassStudent tpvirtualclassstudent, StringBuilder sqlbuilder) {
		if(tpvirtualclassstudent==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_virtual_class_student_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (tpvirtualclassstudent.getVirtualclassid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpvirtualclassstudent.getVirtualclassid());
			} else
				sqlbuilder.append("null,");
			if (tpvirtualclassstudent.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(tpvirtualclassstudent.getRef());
			} else
				sqlbuilder.append("null,");
			if (tpvirtualclassstudent.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(tpvirtualclassstudent.getCtime());
			} else
				sqlbuilder.append("null,");
			if (tpvirtualclassstudent.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpvirtualclassstudent.getUserid());
			} else
				sqlbuilder.append("null,");
			if (tpvirtualclassstudent.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpvirtualclassstudent.getCuserid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.executeArrayQuery_PROC(sqlArrayList, objArrayList);
	}
	
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

}
