package com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.TermInfo;
import com.school.dao.inter.ITermDAO;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

@Component  
public class TermDAO extends CommonDAO<TermInfo> implements ITermDAO {

	public Boolean doSave(TermInfo terminfo) {
		if (terminfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(terminfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TermInfo terminfo) {
		if(terminfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(terminfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TermInfo terminfo) {
		if (terminfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(terminfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TermInfo> getList(TermInfo terminfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL term_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if(terminfo==null){
			sqlbuilder.append("NULL,NULL,NULL,NULL,NULL,");
		}else{
			if (terminfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(terminfo.getRef());
			} else
				sqlbuilder.append("null,");
		
			if (terminfo.getTermname() != null) {
				sqlbuilder.append("?,");
				objList.add(terminfo.getTermname());
			} else
				sqlbuilder.append("null,");
			if (terminfo.getYear() != null) {
				sqlbuilder.append("?,");
				objList.add(terminfo.getYear());
			} else
				sqlbuilder.append("null,");

            if (terminfo.getDYYear() != null) {
                sqlbuilder.append("?,");
                objList.add(terminfo.getDYYear());
            } else
                sqlbuilder.append("null,");
            if (terminfo.getxYYear() != null) {
                sqlbuilder.append("?,");
                objList.add(terminfo.getxYYear());
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
		List<TermInfo> terminfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TermInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return terminfoList;	
	}
	
	public List<Object> getSaveSql(TermInfo terminfo, StringBuilder sqlbuilder) {
		if(terminfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL term_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (terminfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(terminfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (terminfo.getTermname() != null) {
				sqlbuilder.append("?,");
				objList.add(terminfo.getTermname());
			} else
				sqlbuilder.append("null,");
			if (terminfo.getYear() != null) {
				sqlbuilder.append("?,");
				objList.add(terminfo.getYear());
			} else
				sqlbuilder.append("null,");
			if (terminfo.getSemesterbegindate() != null) {
				sqlbuilder.append("?,");
				objList.add(UtilTool.DateConvertToString(terminfo.getSemesterbegindate(),DateType.type1));
			} else
				sqlbuilder.append("null,");
			if (terminfo.getSemesterenddate() != null) {
				sqlbuilder.append("?,");
				objList.add(UtilTool.DateConvertToString(terminfo.getSemesterenddate(),DateType.type1));
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TermInfo terminfo, StringBuilder sqlbuilder) {
		if(terminfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL term_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (terminfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(terminfo.getRef());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TermInfo terminfo, StringBuilder sqlbuilder) {
		if(terminfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL term_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (terminfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(terminfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (terminfo.getTermname() != null) {
				sqlbuilder.append("?,");
				objList.add(terminfo.getTermname());
			} else
				sqlbuilder.append("null,");
			if (terminfo.getYear() != null) {
				sqlbuilder.append("?,");
				objList.add(terminfo.getYear());
			} else
				sqlbuilder.append("null,");
			if (terminfo.getSemesterbegindate() != null) {
				sqlbuilder.append("?,");
				objList.add(UtilTool.DateConvertToString(terminfo.getSemesterbegindate(),DateType.type1));
			} else
				sqlbuilder.append("null,");
			if (terminfo.getSemesterenddate() != null) {
				sqlbuilder.append("?,");
				objList.add(UtilTool.DateConvertToString(terminfo.getSemesterenddate(),DateType.type1));
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

	public List<TermInfo> getAvailableTermList() {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL term_proc_search_available_list()}"); 
		List<Object> objList=new ArrayList<Object>(); 
		List<Integer> types=new ArrayList<Integer>();
		return this.executeResult_PROC(sqlbuilder.toString(), objList, types, TermInfo.class, null);
	}

	/**
	 * 得到最大的Term
	 */
	public TermInfo getMaxIdTerm(Boolean flag) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL term_info_proc_maxtermid(");
		List<Object> objList=new ArrayList<Object>();
		if(flag!=null){
			sqlbuilder.append("?");
			objList.add(flag?1:2);
		}else
			sqlbuilder.append("NULL");
		sqlbuilder.append(")}");
		 
		List<Integer> types=new ArrayList<Integer>();
		List<TermInfo> tmList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TermInfo.class, null);
		if(tmList!=null&&tmList.size()>0)
			return tmList.get(0);
		return null;
	}

	public List<Map<String, Object>> getYearTerm() {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL year_term_proc_list()}");
		List<Object> objList=new ArrayList<Object>();
		
		 
		List<Integer> types=new ArrayList<Integer>();
		List<Map<String,Object>> tmList=this.executeResultListMap_PROC(sqlbuilder.toString(), objList);
		if(tmList!=null&&tmList.size()>0)
			return tmList;
		return null;
	}

    @Override
    public Boolean InitTerm() {
        StringBuilder sqlbuilder = new StringBuilder("{CALL term_info_first_init(?)}");
        List<Object> objList=new ArrayList<Object>();
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

}
