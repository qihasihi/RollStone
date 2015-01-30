package  com.school.dao;


import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IStudentDAO;
import com.school.entity.StudentInfo;
import com.school.util.PageResult;

@Component  
public class StudentDAO extends CommonDAO<StudentInfo> implements IStudentDAO {

	public Boolean doDelete(StudentInfo obj) {
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

	public Boolean doSave(StudentInfo obj) {
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

	public Boolean doUpdate(StudentInfo obj) {
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

	public List<Object> getDeleteSql(StudentInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call student_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRef());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getStuid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getStuid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getUserinfo().getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getUserinfo().getRef());
		}else
			sqlbuilder.append("NULL,");
		
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<StudentInfo> getList(StudentInfo obj, PageResult presult) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL student_proc_search_split(");
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,");
		else{
			if(obj.getRef()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getRef());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getStuid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getStuid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getStuno()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getStuno());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getStuname()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getStuname());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getStusex()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getStusex());
			}else
				sqlbuilder.append("NULL,");

			if(obj.getUserref()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserref());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getUserid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserid());
			}else
				sqlbuilder.append("NULL,");
            if(obj.getUsername()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getUsername());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getDcSchoolId()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getDcSchoolId());
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
		List<StudentInfo> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, StudentInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return roleList;	
	}

	public List<Object> getSaveSql(StudentInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call student_proc_add(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRef());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getUserinfo().getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getUserinfo().getRef());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getStuno()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getStuno());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getStuname()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getStuname());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getStusex()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getStusex());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getStuaddress()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getStuaddress());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getLinkman()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getLinkman());
		}else
			sqlbuilder.append("NULL,");
		
		if(obj.getLinkmanphone()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getLinkmanphone());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(StudentInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call student_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRef());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getStuid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getStuid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getStuno()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getStuno());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getStuname()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getStuname());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getStusex()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getStusex());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getStuaddress()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getStuaddress());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getLinkman()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getLinkman());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getUserinfo().getRef()!=null){ 
			sqlbuilder.append("?,");
			objList.add(obj.getUserinfo().getRef());
		}else
			sqlbuilder.append("NULL,"); 
		if(obj.getLinkmanphone()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getLinkmanphone());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getCardstatus()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getCardstatus());
        }else
            sqlbuilder.append("NULL,");
		
		sqlbuilder.append("?)}");
		return objList; 
	}

	public List<StudentInfo> getStudentByClass(Integer classid, String year,
			String pattern) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder=new StringBuilder("{CALL student_proc_list_byclass(");
		List<Object> objList=new ArrayList<Object>();
		if(classid!=null&&classid>0){
			sqlbuilder.append("?,");
			objList.add(classid);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(year!=null){
			sqlbuilder.append("?,");
			objList.add(year);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(pattern!=null){
			sqlbuilder.append("?");
			objList.add(pattern);
		}else{
			sqlbuilder.append("NULL");
		}
		sqlbuilder.append(")}");			
		List<StudentInfo> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, null, StudentInfo.class, null);					
		return roleList;
	}

}
