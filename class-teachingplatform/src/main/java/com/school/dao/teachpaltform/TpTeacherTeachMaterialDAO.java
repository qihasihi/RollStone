package com.school.dao.teachpaltform;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.teachpaltform.ITpTeacherTeachMaterialDAO;
import com.school.entity.teachpaltform.TpTeacherTeachMaterial;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengzhou on 14-3-29.
 */
@Component
public class TpTeacherTeachMaterialDAO extends CommonDAO<TpTeacherTeachMaterial> implements ITpTeacherTeachMaterialDAO {
    @Override
    public Boolean doSave(TpTeacherTeachMaterial obj) {
        if(obj==null)return false;
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.getSaveSql(obj,sqlbuilder);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean doUpdate(TpTeacherTeachMaterial obj) {
        if(obj==null)return false;
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.getUpdateSql(obj, sqlbuilder);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean doDelete(TpTeacherTeachMaterial obj) {
        if(obj==null)return false;
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.getDeleteSql(obj, sqlbuilder);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<TpTeacherTeachMaterial> getList(TpTeacherTeachMaterial obj, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_j_teacher_material_info_proc_split(");
        List<Object> objList=new ArrayList<Object>();
/*p_user_id INT,
							p_subject_id INT ,
							p_material_id INT,
							p_grade_id INT,
							p_term_id VARCHAR(100),*/

        if (obj.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getUserid());
        } else
            sqlbuilder.append("null,");
        if (obj.getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getSubjectid());
        } else
            sqlbuilder.append("null,");
        if (obj.getMaterialid() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getMaterialid());
        } else
            sqlbuilder.append("null,");
        if (obj.getGradeid() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getGradeid());
        } else
            sqlbuilder.append("null,");
        if (obj.getTermid() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getTermid());
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
        List<TpTeacherTeachMaterial> tptaskinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpTeacherTeachMaterial.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tptaskinfoList;
    }

    @Override
    public List<Object> getSaveSql(TpTeacherTeachMaterial obj, StringBuilder sqlbuilder) {
        if(obj==null || sqlbuilder==null)
            return null;
        /**
         p_user_id INT,
         p_subject_id INT ,
         p_material_id INT,
         p_grade_id INT,
         p_term_id VARCHAR(100),
         p_current_page INT(10),
         p_page_size INT(10),
         p_sort_column VARCHAR(100),
         OUT sumCount INT
         */
        sqlbuilder.append("{CALL tp_j_teacher_material_info_add(");
        List<Object>objList = new ArrayList<Object>();
        if (obj.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getUserid());
        } else
            sqlbuilder.append("null,");
        if (obj.getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getSubjectid());
        } else
            sqlbuilder.append("null,");
        if (obj.getMaterialid() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getMaterialid());
        } else
            sqlbuilder.append("null,");
        if (obj.getGradeid() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getGradeid());
        } else
            sqlbuilder.append("null,");
        if (obj.getTermid() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getTermid());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }

    @Override
    public List<Object> getUpdateSql(TpTeacherTeachMaterial obj, StringBuilder sqlbuilder) {
        return null;
    }

    @Override
    public List<Object> getDeleteSql(TpTeacherTeachMaterial obj, StringBuilder sqlbuilder) {
        if(obj==null || sqlbuilder==null)
            return null;
        /**
         p_user_id INT,
         p_subject_id INT ,
         p_material_id INT,
         p_grade_id INT,
         p_term_id VARCHAR(100),
         p_current_page INT(10),
         p_page_size INT(10),
         p_sort_column VARCHAR(100),
         OUT sumCount INT
         */
        sqlbuilder.append("{CALL tp_j_teacher_material_info_delete(");
        List<Object>objList = new ArrayList<Object>();
        if (obj.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getUserid());
        } else
            sqlbuilder.append("null,");
        if (obj.getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getSubjectid());
        } else
            sqlbuilder.append("null,");
        if (obj.getGradeid() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getGradeid());
        } else
            sqlbuilder.append("null,");
        if (obj.getTermid() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getTermid());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }
}
