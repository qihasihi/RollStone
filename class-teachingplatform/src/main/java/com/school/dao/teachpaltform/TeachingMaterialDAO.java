package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.TeachingMaterialInfo;
import com.school.dao.inter.teachpaltform.ITeachingMaterialDAO;
import com.school.util.PageResult;

@Component  
public class TeachingMaterialDAO extends CommonDAO<TeachingMaterialInfo> implements ITeachingMaterialDAO {

	public Boolean doSave(TeachingMaterialInfo TeachingMaterialInfo) {
		if (TeachingMaterialInfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(TeachingMaterialInfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TeachingMaterialInfo TeachingMaterialInfo) {
		if(TeachingMaterialInfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(TeachingMaterialInfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TeachingMaterialInfo TeachingMaterialInfo) {
		if (TeachingMaterialInfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(TeachingMaterialInfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TeachingMaterialInfo> getList(TeachingMaterialInfo TeachingMaterialInfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
        List<Object> objList=new ArrayList<Object>();
		sqlbuilder.append("{CALL teaching_materia_info_proc_split(");
        if(TeachingMaterialInfo==null){
            sqlbuilder.append("NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,");
        }else{
            if (TeachingMaterialInfo.getVersionid() != null) {
                sqlbuilder.append("?,");
                objList.add(TeachingMaterialInfo.getVersionid());
            } else
                sqlbuilder.append("null,");
            if (TeachingMaterialInfo.getMaterialname() != null) {
                sqlbuilder.append("?,");
                objList.add(TeachingMaterialInfo.getMaterialname());
            } else
                sqlbuilder.append("null,");
            if (TeachingMaterialInfo.getRemark() != null) {
                sqlbuilder.append("?,");
                objList.add(TeachingMaterialInfo.getRemark());
            } else
                sqlbuilder.append("null,");
            if (TeachingMaterialInfo.getCuserid() != null) {
                sqlbuilder.append("?,");
                objList.add(TeachingMaterialInfo.getCuserid());
            } else
                sqlbuilder.append("null,");
            if (TeachingMaterialInfo.getGradeid() != null) {
                sqlbuilder.append("?,");
                objList.add(TeachingMaterialInfo.getGradeid());
            } else
                sqlbuilder.append("null,");
            if (TeachingMaterialInfo.getSubjectid() != null&&TeachingMaterialInfo.getSubjectid()!=0) {
                sqlbuilder.append("?,");
                objList.add(TeachingMaterialInfo.getSubjectid());
            } else
                sqlbuilder.append("null,");
            if (TeachingMaterialInfo.getMaterialid() != null) {
                sqlbuilder.append("?,");
                objList.add(TeachingMaterialInfo.getMaterialid());
            } else
                sqlbuilder.append("null,");
            if (TeachingMaterialInfo.getType() != null) {
                sqlbuilder.append("?,");
                objList.add(TeachingMaterialInfo.getType());
            } else
                sqlbuilder.append("null,");
        }
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
		List<TeachingMaterialInfo> TeachingMaterialInfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TeachingMaterialInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return TeachingMaterialInfoList;	
	}
	
	public List<Object> getSaveSql(TeachingMaterialInfo entity, StringBuilder sqlbuilder) {
		if(entity==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL teaching_materia_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (entity.getGradeid() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getGradeid());
			} else
				sqlbuilder.append("null,");
			if (entity.getMaterialname() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getMaterialname());
			} else
				sqlbuilder.append("null,");
			if (entity.getSubjectid() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getSubjectid());
			} else
				sqlbuilder.append("null,");
			if (entity.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getCtime());
			} else
				sqlbuilder.append("null,");
			if (entity.getVersionid() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getVersionid());
			} else
				sqlbuilder.append("null,");
			if (entity.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getCuserid());
			} else
				sqlbuilder.append("null,");
			if (entity.getMaterialid() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getMaterialid());
			} else
				sqlbuilder.append("null,");
			if (entity.getRemark() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getRemark());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TeachingMaterialInfo entity, StringBuilder sqlbuilder) {
		if(entity==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL teaching_materia_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (entity.getGradeid() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getGradeid());
			} else
				sqlbuilder.append("null,");
			if (entity.getMaterialname() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getMaterialname());
			} else
				sqlbuilder.append("null,");
			if (entity.getSubjectid() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getSubjectid());
			} else
				sqlbuilder.append("null,");
			if (entity.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getCtime());
			} else
				sqlbuilder.append("null,");
			if (entity.getVersionid() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getVersionid());
			} else
				sqlbuilder.append("null,");
			if (entity.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getCuserid());
			} else
				sqlbuilder.append("null,");
			if (entity.getMaterialid() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getMaterialid());
			} else
				sqlbuilder.append("null,");
			if (entity.getRemark() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getRemark());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}
    /**
     * 执行生成教材，版本存储过程。
     * @return
     */
    public Boolean genderOtherTeacherTeachMaterial(){
        StringBuilder sqlbuilder=new StringBuilder("{CALL cal_teach_versionmaterial_gender_other(?)}");
        List<Object> objList=new ArrayList<Object>();
        return this.executeQuery_PROC(sqlbuilder.toString(),objList.toArray());
    }

	public List<Object> getUpdateSql(TeachingMaterialInfo entity, StringBuilder sqlbuilder) {
		if(entity==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL teaching_materia_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (entity.getGradeid() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getGradeid());
			} else
				sqlbuilder.append("null,");
			if (entity.getMaterialname() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getMaterialname());
			} else
				sqlbuilder.append("null,");
			if (entity.getSubjectid() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getSubjectid());
			} else
				sqlbuilder.append("null,");
			if (entity.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getCtime());
			} else
				sqlbuilder.append("null,");
			if (entity.getVersionid() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getVersionid());
			} else
				sqlbuilder.append("null,");
			if (entity.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getCuserid());
			} else
				sqlbuilder.append("null,");
			if (entity.getMaterialid() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getMaterialid());
			} else
				sqlbuilder.append("null,");
			if (entity.getRemark() != null) {
				sqlbuilder.append("?,");
				objList.add(entity.getRemark());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(TeachingMaterialInfo entity,StringBuilder sqlbuilder){
        if(entity==null||sqlbuilder==null)return null;
        sqlbuilder.append("{CALL teaching_materia_info_proc_synchro(");
        List<Object>objList = new ArrayList<Object>();
        if (entity.getVersionid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getVersionid());
        } else
            sqlbuilder.append("null,");
        if (entity.getMaterialname() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getMaterialname());
        } else
            sqlbuilder.append("null,");
        if (entity.getRemark() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getRemark());
        } else
            sqlbuilder.append("null,");
        if (entity.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (entity.getGradeid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getGradeid());
        } else
            sqlbuilder.append("null,");
        if (entity.getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getSubjectid());
        } else
            sqlbuilder.append("null,");
        if (entity.getMaterialid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getMaterialid());
        } else
            sqlbuilder.append("null,");

        sqlbuilder.append("?)}");
        return objList;
    }

    /**
     * 根据residstr得到年级学科属性
     * @param residstr
     * @return
     */
    public List<Map<String,Object>> getTeachingMaterialGradeSubByResId(String residstr){
        if(residstr==null)return null;
        StringBuilder sqlbuilder=new StringBuilder("{CALL teaching_material_get_subandgrad(?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(residstr);
        return this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
    }

}
