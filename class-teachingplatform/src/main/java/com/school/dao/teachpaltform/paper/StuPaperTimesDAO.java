package com.school.dao.teachpaltform.paper;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.paper.StuPaperTimesInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component  
public class StuPaperTimesDAO extends CommonDAO<StuPaperTimesInfo> implements com.school.dao.inter.teachpaltform.paper.IStuPaperTimesDAO {

	public Boolean doSave(StuPaperTimesInfo paperinfo) {
		if (paperinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(paperinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(StuPaperTimesInfo paperinfo) {
		return false;
	}


    public Boolean doUpdate(StuPaperTimesInfo paperinfo) {
		return false;
	}
	
	public List<StuPaperTimesInfo> getList(StuPaperTimesInfo paperinfo,PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL stu_paper_times_info_proc_search(");
		List<Object> objList=new ArrayList<Object>();
        if (paperinfo.getTaskId() != null) {
            sqlbuilder.append("?,");
            objList.add(paperinfo.getTaskId());
        } else
            sqlbuilder.append("null,");
        if (paperinfo.getUserId() != null) {
            sqlbuilder.append("?,");
            objList.add(paperinfo.getUserId());
        } else
            sqlbuilder.append("null,");
        if (paperinfo.getPaperId() != null) {
            sqlbuilder.append("?,");
            objList.add(paperinfo.getPaperId());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		List<StuPaperTimesInfo> paperinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, null, StuPaperTimesInfo.class, null);
		return paperinfoList;	
	}
	
	public List<Object> getSaveSql(StuPaperTimesInfo paperinfo, StringBuilder sqlbuilder) {
		if(paperinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL stu_paper_times_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (paperinfo.getTaskId() != null) {
				sqlbuilder.append("?,");
				objList.add(paperinfo.getTaskId());
			} else
				sqlbuilder.append("null,");
            if (paperinfo.getUserId() != null) {
                sqlbuilder.append("?,");
                objList.add(paperinfo.getUserId());
            } else
                sqlbuilder.append("null,");
            if (paperinfo.getPaperId() != null) {
                sqlbuilder.append("?,");
                objList.add(paperinfo.getPaperId());
            } else
                sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(StuPaperTimesInfo paperinfo, StringBuilder sqlbuilder) {
			return null;
	}

	public List<Object> getUpdateSql(StuPaperTimesInfo paperinfo, StringBuilder sqlbuilder) {

		return null;
	}




//	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
//			List<List<Object>> objArrayList) {
//		return this.executeArray_SQL(sqlArrayList, objArrayList);
//	}
	
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

}
