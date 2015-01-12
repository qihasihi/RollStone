package  com.school.dao;


import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IGradeDAO;
import com.school.entity.GradeInfo;
import com.school.util.PageResult;

@Component  
public class GradeDAO extends CommonDAO<GradeInfo> implements IGradeDAO {

	public Boolean doDelete(GradeInfo obj) {
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

	public Boolean doSave(GradeInfo obj) {
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

	public Boolean doUpdate(GradeInfo obj) {
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

	public List<Object> getDeleteSql(GradeInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call grade_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getGradeid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getGradeid());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<GradeInfo> getList(GradeInfo obj, PageResult presult) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL grade_proc_search_split(");
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL,NULL,NULL,");
		else{
			if(obj.getGradeid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getGradeid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getGradename()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getGradename());
			}else
				sqlbuilder.append("NULL,");
            if(obj.getGradevalue()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getGradevalue());
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
		List<GradeInfo> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, GradeInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return roleList;	
	}

    //获取老师当前学年所教年级
    public List<GradeInfo> getTchGradeList(Integer userid,String year) {
        if(userid==null || year==null)
            return null;
        StringBuilder sqlbuilder=new StringBuilder("{CALL grade_proc_search_bytch(");
        List<Object> objList=new ArrayList<Object>();
        sqlbuilder.append("?,?,?)}");
        objList.add(userid);
        objList.add(year);
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<GradeInfo> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, GradeInfo.class, objArray);
        return roleList;
    }

    @Override
    public List<GradeInfo> getAdminPerformanceTeaGrade(Integer schoolid) {
        StringBuilder sqlbuilder=new StringBuilder("{CALL admin_performance_proc_getgrade(");
        List<Object> objList=new ArrayList<Object>();
        if(schoolid!=null){
            sqlbuilder.append("?");
            objList.add(schoolid);
        }else{
            return null;
        }
        sqlbuilder.append(")}");
        List<GradeInfo> list = this.executeResult_PROC(sqlbuilder.toString(),objList,null,GradeInfo.class,null);
        return list;
    }

    @Override
    public List<GradeInfo> getAdminPerformanceStuGrade(Integer schoolid) {
        StringBuilder sqlbuilder=new StringBuilder("{CALL admin_performance_proc_stu_grade(");
        List<Object> objList=new ArrayList<Object>();
        if(schoolid!=null){
            sqlbuilder.append("?");
            objList.add(schoolid);
        }else{
            return null;
        }
        sqlbuilder.append(")}");
        List<GradeInfo> list = this.executeResult_PROC(sqlbuilder.toString(),objList,null,GradeInfo.class,null);
        return list;
    }

    public List<Object> getSaveSql(GradeInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call grade_proc_add(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getGradename()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getGradename());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getGradevalue()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getGradevalue());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(GradeInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call grade_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getGradeid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getGradeid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getGradename()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getGradename());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getGradevalue()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getGradevalue()); 
		}else
			sqlbuilder.append("NULL,");
		
		sqlbuilder.append("?)}");
		return objList; 
	}

}
