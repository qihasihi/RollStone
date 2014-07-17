package com.school.dao.teachpaltform.paper;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.paper.StuViewMicVideoLog;
import com.school.dao.inter.teachpaltform.paper.IStuViewMicVideoLogDAO;
import com.school.util.PageResult;

@Component  
public class StuViewMicVideoLogDAO extends CommonDAO<StuViewMicVideoLog> implements IStuViewMicVideoLogDAO {

	public Boolean doSave(StuViewMicVideoLog stuviewmicvideolog) {
		if (stuviewmicvideolog == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(stuviewmicvideolog, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(StuViewMicVideoLog stuviewmicvideolog) {
		if(stuviewmicvideolog==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(stuviewmicvideolog, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(StuViewMicVideoLog stuviewmicvideolog) {
		if (stuviewmicvideolog == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(stuviewmicvideolog, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<StuViewMicVideoLog> getList(StuViewMicVideoLog stuviewmicvideolog, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL stu_view_mic_video_logs_proc_split(");
		List<Object> objList=new ArrayList<Object>();
        if (stuviewmicvideolog.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(stuviewmicvideolog.getRef());
        } else
            sqlbuilder.append("null,");
        if (stuviewmicvideolog.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(stuviewmicvideolog.getUserid());
        } else
            sqlbuilder.append("null,");
		if (stuviewmicvideolog.getMicvideoid() != null) {
			sqlbuilder.append("?,");
			objList.add(stuviewmicvideolog.getMicvideoid());
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
		List<StuViewMicVideoLog> stuviewmicvideologList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, StuViewMicVideoLog.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return stuviewmicvideologList;	
	}
	
	public List<Object> getSaveSql(StuViewMicVideoLog stuviewmicvideolog, StringBuilder sqlbuilder) {
		if(stuviewmicvideolog==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL stu_view_mic_video_logs_proc_add(");
		List<Object>objList = new ArrayList<Object>();


        if (stuviewmicvideolog.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(stuviewmicvideolog.getRef());
        } else
            sqlbuilder.append("null,");

        if (stuviewmicvideolog.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(stuviewmicvideolog.getUserid());
        } else
            sqlbuilder.append("null,");
	    if (stuviewmicvideolog.getMicvideoid() != null) {
				sqlbuilder.append("?,");
				objList.add(stuviewmicvideolog.getMicvideoid());
		} else
				sqlbuilder.append("null,");

		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(StuViewMicVideoLog stuviewmicvideolog, StringBuilder sqlbuilder) {
		if(stuviewmicvideolog==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL stu_view_mic_video_logs_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
        if (stuviewmicvideolog.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(stuviewmicvideolog.getRef());
        } else
            sqlbuilder.append("null,");

        if (stuviewmicvideolog.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(stuviewmicvideolog.getUserid());
        } else
            sqlbuilder.append("null,");
        if (stuviewmicvideolog.getMicvideoid() != null) {
            sqlbuilder.append("?,");
            objList.add(stuviewmicvideolog.getMicvideoid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(StuViewMicVideoLog stuviewmicvideolog, StringBuilder sqlbuilder) {
		if(stuviewmicvideolog==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL stu_view_mic_video_logs_proc_update(");
		List<Object>objList = new ArrayList<Object>();
        if (stuviewmicvideolog.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(stuviewmicvideolog.getRef());
        } else
            sqlbuilder.append("null,");

        if (stuviewmicvideolog.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(stuviewmicvideolog.getUserid());
        } else
            sqlbuilder.append("null,");
        if (stuviewmicvideolog.getMicvideoid() != null) {
            sqlbuilder.append("?,");
            objList.add(stuviewmicvideolog.getMicvideoid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

}
