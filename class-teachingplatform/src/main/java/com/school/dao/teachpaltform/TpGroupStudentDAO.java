package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.TpGroupStudent;
import com.school.dao.inter.teachpaltform.ITpGroupStudentDAO;
import com.school.util.PageResult;

@Component  
public class TpGroupStudentDAO extends CommonDAO<TpGroupStudent> implements ITpGroupStudentDAO {

	public Boolean doSave(TpGroupStudent tpgroupstudent) {
		if (tpgroupstudent == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tpgroupstudent, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TpGroupStudent tpgroupstudent) {
		if(tpgroupstudent==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tpgroupstudent, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TpGroupStudent tpgroupstudent) {
		if (tpgroupstudent == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tpgroupstudent, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TpGroupStudent> getList(TpGroupStudent tpgroupstudent, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_j_group_student_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (tpgroupstudent.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(tpgroupstudent.getRef());
		} else
			sqlbuilder.append("null,");
        if (tpgroupstudent.getGroupid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupstudent.getGroupid());
        } else
            sqlbuilder.append("null,");
		if (tpgroupstudent.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpgroupstudent.getUserid());
		} else
			sqlbuilder.append("null,");
        if (tpgroupstudent.getIsleader() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupstudent.getIsleader());
        } else
            sqlbuilder.append("null,");
        if (tpgroupstudent.getStateid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupstudent.getStateid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupstudent.getTpgroupinfo().getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupstudent.getTpgroupinfo().getSubjectid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupstudent.getClassid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupstudent.getClassid());
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
		List<TpGroupStudent> tpgroupstudentList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpGroupStudent.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tpgroupstudentList;	
	}
	
	public List<Object> getSaveSql(TpGroupStudent tpgroupstudent, StringBuilder sqlbuilder) {
		if(tpgroupstudent==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_group_student_proc_add(");
		List<Object>objList = new ArrayList<Object>();

        if (tpgroupstudent.getGroupid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupstudent.getGroupid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupstudent.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupstudent.getUserid());
        } else
            sqlbuilder.append("null,");

        if (tpgroupstudent.getIsleader() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupstudent.getIsleader());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");

		return objList;
	}

	public List<Object> getDeleteSql(TpGroupStudent tpgroupstudent, StringBuilder sqlbuilder) {
		if(tpgroupstudent==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_group_student_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
        if (tpgroupstudent.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupstudent.getRef());
        } else
            sqlbuilder.append("null,");
        if (tpgroupstudent.getGroupid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupstudent.getGroupid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupstudent.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupstudent.getUserid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupstudent.getIsleader() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupstudent.getIsleader());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TpGroupStudent tpgroupstudent, StringBuilder sqlbuilder) {
		if(tpgroupstudent==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_group_student_proc_update(");
		List<Object>objList = new ArrayList<Object>();
        if (tpgroupstudent.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupstudent.getRef());
        } else
            sqlbuilder.append("null,");
        if (tpgroupstudent.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupstudent.getUserid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupstudent.getGroupid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupstudent.getGroupid());
        } else
            sqlbuilder.append("null,");
        if (tpgroupstudent.getIsleader() != null) {
            sqlbuilder.append("?,");
            objList.add(tpgroupstudent.getIsleader());
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

    // 获取班级小组学生
    public List<TpGroupStudent> getGroupStudentByClass(TpGroupStudent gs,PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_get_group_stu_by_class(");
        List<Object> objList=new ArrayList<Object>();
        if (gs.getGroupid() != null) {
            sqlbuilder.append("?,");
            objList.add(gs.getGroupid());
        } else
            sqlbuilder.append("null,");
        if (gs.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(gs.getUserid());
        } else
            sqlbuilder.append("null,");
        if (gs.getClassid() != null) {
            sqlbuilder.append("?,");
            objList.add(gs.getClassid());
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
        List<TpGroupStudent> groupstudentList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpGroupStudent.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return groupstudentList;
    }

    // 获取班级未分配小组的学生
    public List<Map<String,Object>> getNoGroupStudentList(Integer classid,Integer classtype,Integer userid,Integer subjectid,String termid) {
        if(classid==null||userid==null||classtype==null||termid==null)
            return null;
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_group_student_qry_nogroupstu(?,?,?,?,?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(classid);
        objList.add(classtype);
        objList.add(userid);
        objList.add(subjectid);
        objList.add(termid);
        return this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
    }

}
