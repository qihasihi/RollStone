package com.school.dao.teachpaltform.award;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.award.TpStuScoreLogs;
import com.school.dao.inter.teachpaltform.award.ITpStuScoreLogsDAO;
import com.school.util.PageResult;

@Component("tpStuScoreLogsDAO")
public class TpStuScoreLogsDAO extends CommonDAO<TpStuScoreLogs> implements ITpStuScoreLogsDAO {

	public Boolean doSave(TpStuScoreLogs tpstuscorelogs) {
		if (tpstuscorelogs == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tpstuscorelogs, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TpStuScoreLogs tpstuscorelogs) {
		if(tpstuscorelogs==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tpstuscorelogs, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TpStuScoreLogs tpstuscorelogs) {
		if (tpstuscorelogs == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tpstuscorelogs, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

    /**
     * 老师，班主任查看学生积分统计页面
     * @param termid
     * @param classid
     * @param subjectid
     * @param orderby
     * @return
     */
    public List<Map<String,Object>> getStuScoreTeachStatices(final String termid,final Integer classid,Integer subjectid,Integer orderby){
        StringBuilder sqlbuilder=new StringBuilder("{call tp_stu_score_teach_statices(?,?,?,?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(termid);
        objList.add(classid);
        objList.add(subjectid);
        if(orderby==null)
            orderby=1;
        objList.add(orderby);
        return this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
    }

	public List<TpStuScoreLogs> getList(TpStuScoreLogs tpstuscorelogs, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_stu_score_logs_proc_split(");
		List<Object> objList=new ArrayList<Object>();
        if (tpstuscorelogs.getTaskid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getTaskid());
        } else
            sqlbuilder.append("null,");
        if (tpstuscorelogs.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getRef());
        } else
            sqlbuilder.append("null,");
        if (tpstuscorelogs.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getUserid());
        } else
            sqlbuilder.append("null,");
        if (tpstuscorelogs.getClassid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getClassid());
        } else
            sqlbuilder.append("null,");

        if (tpstuscorelogs.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpstuscorelogs.getDcschoolid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getDcschoolid());
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
		List<TpStuScoreLogs> tpstuscorelogsList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpStuScoreLogs.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tpstuscorelogsList;	
	}
	
	public List<Object> getSaveSql(TpStuScoreLogs tpstuscorelogs, StringBuilder sqlbuilder) {
		if(tpstuscorelogs==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_stu_score_logs_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (tpstuscorelogs.getTaskid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpstuscorelogs.getTaskid());
			} else
				sqlbuilder.append("null,");
        if (tpstuscorelogs.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getRef());
        } else
            sqlbuilder.append("null,");
        if (tpstuscorelogs.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getUserid());
        } else
            sqlbuilder.append("null,");
        if (tpstuscorelogs.getClassid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getClassid());
        } else
            sqlbuilder.append("null,");

			if (tpstuscorelogs.getJewel() != null) {
				sqlbuilder.append("?,");
				objList.add(tpstuscorelogs.getJewel());
			} else
				sqlbuilder.append("null,");
			if (tpstuscorelogs.getScore() != null) {
				sqlbuilder.append("?,");
				objList.add(tpstuscorelogs.getScore());
			} else
				sqlbuilder.append("null,");
        if (tpstuscorelogs.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpstuscorelogs.getDcschoolid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getDcschoolid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TpStuScoreLogs tpstuscorelogs, StringBuilder sqlbuilder) {
		if(tpstuscorelogs==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_stu_score_logs_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
        if (tpstuscorelogs.getTaskid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getTaskid());
        } else
            sqlbuilder.append("null,");
        if (tpstuscorelogs.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getRef());
        } else
            sqlbuilder.append("null,");
        if (tpstuscorelogs.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getUserid());
        } else
            sqlbuilder.append("null,");
        if (tpstuscorelogs.getClassid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getClassid());
        } else
            sqlbuilder.append("null,");

        if (tpstuscorelogs.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpstuscorelogs.getDcschoolid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getDcschoolid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TpStuScoreLogs tpstuscorelogs, StringBuilder sqlbuilder) {
		if(tpstuscorelogs==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_stu_score_logs_proc_update(");
		List<Object>objList = new ArrayList<Object>();
        if (tpstuscorelogs.getTaskid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getTaskid());
        } else
            sqlbuilder.append("null,");
        if (tpstuscorelogs.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getRef());
        } else
            sqlbuilder.append("null,");
        if (tpstuscorelogs.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getUserid());
        } else
            sqlbuilder.append("null,");
        if (tpstuscorelogs.getClassid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getClassid());
        } else
            sqlbuilder.append("null,");

        if (tpstuscorelogs.getJewel() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getJewel());
        } else
            sqlbuilder.append("null,");
        if (tpstuscorelogs.getScore() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getScore());
        } else
            sqlbuilder.append("null,");
        if (tpstuscorelogs.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpstuscorelogs.getDcschoolid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpstuscorelogs.getDcschoolid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}
}
