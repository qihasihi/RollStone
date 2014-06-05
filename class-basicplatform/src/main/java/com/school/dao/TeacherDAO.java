package  com.school.dao;


import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.ITeacherDAO;
import com.school.entity.TeacherInfo;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

@Component  
public class TeacherDAO extends CommonDAO<TeacherInfo> implements ITeacherDAO {

	public Boolean doDelete(TeacherInfo obj) {
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(obj, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false; 
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.executeArrayQuery_PROC(sqlArrayList, objArrayList);
	}
 
	public Boolean doSave(TeacherInfo obj) {
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

	public Boolean doUpdate(TeacherInfo obj) {
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

	public List<Object> getDeleteSql(TeacherInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call teacher_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getTeacherid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getTeacherid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getUserid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getUserid());
		}else
			sqlbuilder.append("NULL,"); 
		if(obj.getTeachername()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getTeachername());
		}else
			sqlbuilder.append("NULL,"); 
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<TeacherInfo> getList(TeacherInfo obj, PageResult presult) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL teacher_proc_search_split(");
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL,NULL,NULL,NULL,NULL,NULL,");
		else{ 
			if(obj.getTeacherid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getTeacherid()); 
			}else
				sqlbuilder.append("NULL,");
			if(obj.getTeachername()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getTeachername());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getTeachersex()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getTeachersex());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getTuserid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getTuserid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getUserid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getUserinfo().getUsername()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserinfo().getUsername());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getUserinfo().getRealname()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserinfo().getRealname());
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
		List<TeacherInfo> teacherList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TeacherInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return teacherList;	
	}
	
	public List<TeacherInfo> getListByTchnameOrUsername(TeacherInfo obj, PageResult presult) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL teacher_proc_search_by_tnorun(");
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL,NULL,NULL,NULL,NULL,NULL,");
		else{ 
			if(obj.getTeacherid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getTeacherid()); 
			}else
				sqlbuilder.append("NULL,");
			if(obj.getTeachername()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getTeachername());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getTeachersex()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getTeachersex());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getTuserid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getTuserid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getUserid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getUserinfo().getUsername()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserinfo().getUsername());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getUserinfo().getRealname()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserinfo().getRealname());
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
		List<TeacherInfo> teacherList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TeacherInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return teacherList;	
	}


	public List<Object> getSaveSql(TeacherInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call teacher_proc_add(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getTeachername()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getTeachername()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getTeachersex()!=null){ 
			sqlbuilder.append("?,");
			objList.add(obj.getTeachersex()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getTeacheraddress()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getTeacheraddress()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getTeacherphone()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getTeacherphone());  
		}else
			sqlbuilder.append("NULL,");
		if(obj.getTeachercardid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getTeachercardid()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getTeacherpost()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getTeacherpost()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getUserid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getUserid()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getPassword()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getPassword()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getTeacherlevel()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getTeacherlevel()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getTeacherbirth()!=null){
			sqlbuilder.append("?,");
			objList.add(UtilTool.DateConvertToString(obj.getTeacherbirth(), DateType.type1)); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getEntrytime()!=null){
			sqlbuilder.append("?,");
			objList.add(UtilTool.DateConvertToString(obj.getEntrytime(), DateType.type1)); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getImgheardsrc()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getImgheardsrc()); 
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	} 

	public List<Object> getUpdateSql(TeacherInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||obj.getUserid()==null)
			return null; 
		sqlbuilder.append("{call teacher_proc_update("); 
		List<Object>objList = new ArrayList<Object>();
		if(obj.getTeacherid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getTeacherid());  
		}else
			sqlbuilder.append("NULL,");
		if(obj.getTeachername()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getTeachername());  
		}else
			sqlbuilder.append("NULL,");
		if(obj.getTeachersex()!=null){ 
			sqlbuilder.append("?,"); 
			objList.add(obj.getTeachersex()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getTeacheraddress()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getTeacheraddress()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getTeacherphone()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getTeacherphone());  
		}else
			sqlbuilder.append("NULL,");
		if(obj.getTeachercardid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getTeachercardid()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getTeacherpost()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getTeacherpost()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getUserid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getUserid()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getPassword()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getPassword()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getTeacherlevel()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getTeacherlevel()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getTeacherbirth()!=null){
			sqlbuilder.append("?,");
			objList.add(UtilTool.DateConvertToString(obj.getTeacherbirth(), DateType.type1)); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getEntrytime()!=null){
			sqlbuilder.append("?,");
			objList.add(UtilTool.DateConvertToString(obj.getEntrytime(), DateType.type1)); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getImgheardsrc()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getImgheardsrc()); 
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}"); 
		return objList; 
	}

	public List<TeacherInfo> getTeacherListByUserId(String userid,String year) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder=new StringBuilder("{CALL teacher_proc_getlistby_stuid(?,?,?)}");
		List<Object> objList=new ArrayList<Object>();
		objList.add(userid);
		objList.add(year);
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List<TeacherInfo> teacherList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TeacherInfo.class, objArray);
		if(objArray[0]!=null&&Integer.parseInt(objArray[0].toString())>0)
			return teacherList;	
		else
			return null;
	} 

}
