package com.school.dao.teachpaltform;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.teachpaltform.ITpCourseTeachingMaterialDAO;
import com.school.entity.teachpaltform.TpCourseTeachingMaterial;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Component  
public class TpCourseTeachingMaterialDAO extends CommonDAO<TpCourseTeachingMaterial> implements ITpCourseTeachingMaterialDAO {

	public Boolean doSave(TpCourseTeachingMaterial tpcourseteachingmaterial) {
		if (tpcourseteachingmaterial == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tpcourseteachingmaterial, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TpCourseTeachingMaterial tpcourseteachingmaterial) {
		if(tpcourseteachingmaterial==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tpcourseteachingmaterial, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TpCourseTeachingMaterial tpcourseteachingmaterial) {
		if (tpcourseteachingmaterial == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tpcourseteachingmaterial, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TpCourseTeachingMaterial> getList(TpCourseTeachingMaterial tpcourseteachingmaterial, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_j_course_teaching_material_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (tpcourseteachingmaterial.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(tpcourseteachingmaterial.getRef());
		} else
			sqlbuilder.append("null,");
		if (tpcourseteachingmaterial.getCourseid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpcourseteachingmaterial.getCourseid());
		} else
			sqlbuilder.append("null,");
		if (tpcourseteachingmaterial.getTeachingmaterialid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpcourseteachingmaterial.getTeachingmaterialid());
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
		List<TpCourseTeachingMaterial> tpcourseteachingmaterialList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseTeachingMaterial.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tpcourseteachingmaterialList;	
	}
	
	public List<Object> getSaveSql(TpCourseTeachingMaterial tpcourseteachingmaterial, StringBuilder sqlbuilder) {
		if(tpcourseteachingmaterial==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_course_teaching_material_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (tpcourseteachingmaterial.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseteachingmaterial.getRef());
			} else
				sqlbuilder.append("null,");
			if (tpcourseteachingmaterial.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseteachingmaterial.getCourseid());
			} else
				sqlbuilder.append("null,");
			if (tpcourseteachingmaterial.getTeachingmaterialid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseteachingmaterial.getTeachingmaterialid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TpCourseTeachingMaterial tpcourseteachingmaterial, StringBuilder sqlbuilder) {
		if(tpcourseteachingmaterial==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_course_teaching_material_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (tpcourseteachingmaterial.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseteachingmaterial.getRef());
			} else
				sqlbuilder.append("null,");
			if (tpcourseteachingmaterial.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseteachingmaterial.getCourseid());
			} else
				sqlbuilder.append("null,");
			if (tpcourseteachingmaterial.getTeachingmaterialid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseteachingmaterial.getTeachingmaterialid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TpCourseTeachingMaterial tpcourseteachingmaterial, StringBuilder sqlbuilder) {
		if(tpcourseteachingmaterial==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_course_teaching_material_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (tpcourseteachingmaterial.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseteachingmaterial.getRef());
			} else
				sqlbuilder.append("null,");
			if (tpcourseteachingmaterial.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseteachingmaterial.getCourseid());
			} else
				sqlbuilder.append("null,");
			if (tpcourseteachingmaterial.getTeachingmaterialid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcourseteachingmaterial.getTeachingmaterialid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

    /**
     * 得到同步的SQL
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSql(TpCourseTeachingMaterial entity,StringBuilder sqlbuilder){
        if(entity==null||sqlbuilder==null)
            return null;
        sqlbuilder.append("{CALL tp_j_course_teaching_material_proc_synchro(");
        List<Object>objList = new ArrayList<Object>();
        if (entity.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getRef());
        } else
            sqlbuilder.append("null,");
        if (entity.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (entity.getTeachingmaterialid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getTeachingmaterialid());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;

    }

}
