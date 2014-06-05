package com.school.dao.evalteacher;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.asm.Type;
import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.evalteacher.ITimeStepInfoDAO;
import com.school.entity.evalteacher.TimeStepInfo;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

@Component 
public class TimeStepInfoDAO extends CommonDAO<TimeStepInfo> implements ITimeStepInfoDAO<TimeStepInfo> {
	/**
	 *分页List
	 */
	public List<TimeStepInfo> getPageList(TimeStepInfo t, PageResult result) {
		// TODO Auto-generated method stub
		StringBuilder sqlBuilder = new StringBuilder("{call time_setup_proc_split(");
		List<Object> param = new ArrayList<Object>();
		if(t !=null&& t.getRef()!=null){
			param.add(t.getRef());
			sqlBuilder.append("?,");
		}else
			sqlBuilder.append("null,");
		sqlBuilder.append("?)}");
 		//返回参数及类型
		List<Integer>orclReturnTypeList = new ArrayList<Integer>();
		orclReturnTypeList.add(Types.INTEGER);
		Object[] pageCountSum=new Object[1];
		List<TimeStepInfo> reList =this.executeResult_PROC(sqlBuilder.toString(), param, orclReturnTypeList, TimeStepInfo.class,pageCountSum);
		if(result!=null&&pageCountSum!=null&&pageCountSum.length>0){
			result.setRecTotal(Integer.parseInt(pageCountSum[0].toString().trim()));
		}
		return reList;
	}

	public Integer save(TimeStepInfo t) {
		// TODO Auto-generated method stub
		if(t==null||t.getYearid()==null||t.getPjstarttime()==null|t.getPjendtime()==null){
			return null;
		}
		Object obj = null;
		StringBuilder sql = new StringBuilder("{call time_setup_proc_add(?,?,?,?,?)}");
		List<Object> objList = new ArrayList<Object>();
		objList.add(t.getYearid());
		objList.add(UtilTool.DateConvertToString(t.getPjstarttime(), DateType.type1));
		objList.add(UtilTool.DateConvertToString(t.getPjendtime(), DateType.type1));
		objList.add(t.getPjdesc());
		obj=this.executeSacle_PROC(sql.toString(), objList.toArray());
		if(obj!=null && obj.toString().trim().length()>0){
			return Integer.parseInt(obj.toString().trim());
		}
		return 0;
	}

	public Integer update(TimeStepInfo t) {
		// TODO Auto-generated method stub
		if(t==null||t.getYearid()==null||t.getPjstarttime()==null|t.getPjendtime()==null){
			return null;
		}
		StringBuilder sql = new StringBuilder("{call time_setup_proc_update(?,?,?,?,?)}");
		Object [] paramVal = new Object[] {t.getRef(),UtilTool.DateConvertToString(t.getPjstarttime(), DateType.type1),
									UtilTool.DateConvertToString(t.getPjendtime(), DateType.type1),t.getPjdesc()};
		Object returnVal = null;
		returnVal =this.executeSacle_PROC(sql.toString(), paramVal);
		if(returnVal!=null&&returnVal.toString().trim().length()>0){
			return Integer.parseInt(returnVal.toString());
		}
		return 0;
	}

	public Integer delete(TimeStepInfo t) {
		// TODO Auto-generated method stub
		if(t==null||t.getYearid()==null){
		    return null;
		}
		StringBuilder sql = new StringBuilder("{call time_setup_proc_delete(?,?)}");
		Object [] param = new Object[]{t.getYearid()};
		Object returnVal = null;
		returnVal =this.executeSacle_PROC(sql.toString(), param);
		if(returnVal!=null&&returnVal.toString().trim().length()>1){
			return Integer.parseInt(returnVal.toString());
		}
		return 0;
	}
	/**
	 * 过期时间判断
	 */
	public int getCount(TimeStepInfo t) {
		// TODO Auto-generated method stub
		if(t==null || t.getYearid()==null){
			return -1;
		}
		StringBuilder sql = new StringBuilder("{ call PJ_PKG.query_pt_begin_or_over(?,?)}");
		Object [] param = new Object[]{t.getYearid()};
		Object returnVal =null;
		returnVal =this.executeSacle_PROC(sql.toString(), param);
		if(returnVal!=null && returnVal.toString().trim().length()>=0){
			return Integer.parseInt(returnVal.toString());
		}
		return -1;
	}
	/**
	 * 根据评价年获取一条数据
	 */
	public List<TimeStepInfo> getListByYear(TimeStepInfo t) {
		// TODO Auto-generated method stub
		if(t==null ||t.getYearid()==null){
			return null;
		}
		List<Object> param = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder("{call time_setup_proc_list(?,?)}");
		if(t.getYearid()!=null){
			param.add(t.getYearid());
		}
		List list = null;
		Object[] pageCountSum=new Object[1];
		List<Integer> oracleTypes=new ArrayList<Integer>();
		oracleTypes.add(Type.INT);
		list=this.executeResult_PROC(sql.toString(), param, oracleTypes, TimeStepInfo.class, pageCountSum);
		return list;
	}

	public Boolean doSave(TimeStepInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean doUpdate(TimeStepInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean doDelete(TimeStepInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TimeStepInfo> getList(TimeStepInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getSaveSql(TimeStepInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getUpdateSql(TimeStepInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getDeleteSql(TimeStepInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		// TODO Auto-generated method stub
		return null;
	}

}
