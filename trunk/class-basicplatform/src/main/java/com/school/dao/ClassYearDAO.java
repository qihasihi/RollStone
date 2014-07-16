package  com.school.dao;


import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IClassYearDAO;
import com.school.entity.ClassYearInfo;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

@Component  
public class ClassYearDAO extends CommonDAO<ClassYearInfo> implements IClassYearDAO {

	public Boolean doDelete(ClassYearInfo obj) {
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(obj, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	} 

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.executeArray_SQL(sqlArrayList, objArrayList);
	}

	public Boolean doSave(ClassYearInfo obj) {
		if (obj == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doUpdate(ClassYearInfo obj) {
		if (obj == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public List<Object> getDeleteSql(ClassYearInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call class_year_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getClassyearid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClassyearid());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<ClassYearInfo> getList(ClassYearInfo obj, PageResult presult) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL class_year_proc_search_split(");
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL,NULL,NULL,"); 
		else{
			if(obj.getClassyearid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getClassyearid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getClassyearname()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getClassyearname());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getClassyearvalue()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getClassyearvalue()); 
			}else
				sqlbuilder.append("NULL,");
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
		List<ClassYearInfo> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, ClassYearInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return roleList;	
	}
	
	public List<ClassYearInfo> getCurrentYearList(String flag) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL class_year_proc_current_year(");
		List<Object>objList=new ArrayList<Object>();
		if(flag!=null&&flag.length()>0){
			sqlbuilder.append("?");
			objList.add(flag);
		}else
			sqlbuilder.append("NULL"); 
		sqlbuilder.append(")}"); 
		List<ClassYearInfo> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, null, ClassYearInfo.class, null);	
		return roleList;	
	}
	
	


	public List<Object> getSaveSql(ClassYearInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call class_year_proc_add(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getClassyearname()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClassyearname());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getClassyearvalue()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClassyearvalue());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getBtime()!=null){
			sqlbuilder.append("?,");
			objList.add(UtilTool.DateConvertToString(obj.getBtime(), DateType.type1));
		}else
			sqlbuilder.append("NULL,");
		if(obj.getEtime()!=null){
			sqlbuilder.append("?,");
			objList.add(UtilTool.DateConvertToString(obj.getEtime(), DateType.type1));
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(ClassYearInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||obj.getClassyearid()==null)
			return null;
		sqlbuilder.append("{call class_year_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getClassyearid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClassyearid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getClassyearname()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClassyearname());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getClassyearvalue()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClassyearvalue());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getBtime()!=null){
			sqlbuilder.append("?,");
			objList.add(UtilTool.DateConvertToString(obj.getBtime(), DateType.type1));
		}else
			sqlbuilder.append("NULL,");
		if(obj.getEtime()!=null){
			sqlbuilder.append("?,");
			objList.add(UtilTool.DateConvertToString(obj.getEtime(), DateType.type1));
		}else
			sqlbuilder.append("NULL,");
		
		sqlbuilder.append("?)}");
		return objList; 
	}
	/**
	 * 得到过去的年份
	 * @param cyear
	 * @return
	 */
	public List<Map<String,Object>> getClassYearPree(ClassYearInfo cyear){
		if(cyear==null)return null;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=new ArrayList<Object>();
		sqlbuilder.append("{CALL class_year_getPree_classyear(");
		if(cyear.getClassyearvalue()!=null){
			sqlbuilder.append("?");
			objList.add(cyear.getClassyearvalue().trim());
		}
		sqlbuilder.append(")}");
		return this.executeResultListMap_PROC(sqlbuilder.toString(), objList);	
	}

}
