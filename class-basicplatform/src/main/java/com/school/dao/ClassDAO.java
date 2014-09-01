package  com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.school.util.UtilTool;
import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IClassDAO;
import com.school.entity.ClassInfo;
import com.school.entity.ClassUser;
import com.school.util.PageResult;

@Component  
public class ClassDAO extends CommonDAO<ClassInfo> implements IClassDAO {

	public Boolean doDelete(ClassInfo obj) {//
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(obj, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}
 
//	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
//			List<List<Object>> objArrayList) {
//		return this.executeArray_SQL(sqlArrayList, objArrayList);
//	}

	public Boolean doSave(ClassInfo obj) {
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

	public Boolean doUpdate(ClassInfo obj) {
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

	public List<Object> getDeleteSql(ClassInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call class_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
        if(obj.getClassid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getLzxclassid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getLzxclassid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSubjectid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getSubjectid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getIsflag()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getIsflag());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}"); 
		return objList; 
	}

	public List<ClassInfo> getList(ClassInfo obj, PageResult presult) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL class_info_proc_split(");
		List<Object> objList=new ArrayList<Object>(); 
		if(obj==null)  
			sqlbuilder.append("null,null,null,null,null,null,null,null,null,null,0,null,null,");
		else{
            if(obj.getClassid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getClassid());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getLzxclassid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getLzxclassid());
            }else
                sqlbuilder.append("NULL,");

            sqlbuilder.append("?,");
            objList.add(obj.getDcschoolid());

			if(obj.getClassname()!=null){ 
				sqlbuilder.append("?,");
				objList.add(obj.getClassname());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getClassgrade()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getClassgrade());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getType()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getType());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getYear()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getYear());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getPattern()!=null){ 
				sqlbuilder.append("?,");
				objList.add(obj.getPattern());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getUserid()!=null){ 
				sqlbuilder.append("?,");
				objList.add(obj.getUserid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getIslike()!=null){ 
				sqlbuilder.append("?,");
				objList.add(obj.getIslike());
			}else
				sqlbuilder.append("0,");
			if(obj.getSubjectid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getSubjectid());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getDctype()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getDctype());
            }else
                sqlbuilder.append("NULL,");
			if(obj.getIsflag()!=null){ 
				sqlbuilder.append("?,");
				objList.add(obj.getIsflag());
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
		List<ClassInfo> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, ClassInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return roleList;	
	}
	/**
	 * 根据UserRef,YEAR,PAttern得到班级
	 * @param userref
	 * @param year
	 * @param pattern
	 * @return
	 */
	public List<ClassInfo> getClassByUserYearPattern(String userref,String year,String pattern) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL class_proc_theme_split(");
		List<Object> objList=new ArrayList<Object>();
		if(userref!=null&&userref.trim().length()>0){
			sqlbuilder.append("?,");
			objList.add(userref);
		}else
			sqlbuilder.append("NULL,");
		if(year!=null&&year.trim().length()>0){
			sqlbuilder.append("?,");
			objList.add(year);
		}else
			sqlbuilder.append("NULL,");
		if(pattern!=null&&pattern.trim().length()>0){
			sqlbuilder.append("?");
			objList.add(pattern);
		}else
			sqlbuilder.append("NULL");		
		sqlbuilder.append(")}");
		
		List<Integer> types=new ArrayList<Integer>();
		Object[] objArray=new Object[1];
		List<ClassInfo> clsList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, ClassUser.class, objArray);
		if(clsList!=null&&clsList.size()>0)
			return clsList;	
		else
			return null;
	}
	
	public List<Object> getSaveSql(ClassInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call class_proc_add(");
		List<Object>objList = new ArrayList<Object>();

        if(obj.getLzxclassid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getLzxclassid());
        }else
            sqlbuilder.append("NULL,");

        sqlbuilder.append("?,");
        objList.add(obj.getDcschoolid());

        if(obj.getClassgrade()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassgrade());
        }else
            sqlbuilder.append("NULL,");
		if(obj.getClassname()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClassname());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getYear()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getYear());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getType()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getType());
		}else 
			sqlbuilder.append("NULL,");
		if(obj.getPattern()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getPattern());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getSubjectid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getSubjectid());
		}else
			sqlbuilder.append("NULL,");
        if(obj.getDctype()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getDctype());
        }else
            sqlbuilder.append("NULL,");
		if(obj.getIsflag()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getIsflag());
		}else
			sqlbuilder.append("NULL,");
        if(obj.getAllowjoin()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getAllowjoin());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getVerifytime()!=null){
            sqlbuilder.append("?,");
            objList.add(UtilTool.DateConvertToString(obj.getVerifytime(), UtilTool.DateType.type1));
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClsnum()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClsnum());
        }else
            sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(ClassInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call class_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getClassid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClassid()); 
		}else
			sqlbuilder.append("NULL,");
		if(obj.getClassgrade()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClassgrade());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getClassname()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClassname());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getYear()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getYear());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getType()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getType());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getPattern()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getPattern());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getSubjectid()!=null){
			if(obj.getSubjectid()==-999)
				sqlbuilder.append("-999,");
			else{
				sqlbuilder.append("?,");
				objList.add(obj.getSubjectid());
			}
		}else
			sqlbuilder.append("NULL,");
        if(obj.getDctype()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getDctype());
        }else
            sqlbuilder.append("NULL,");
		if(obj.getIsflag()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getIsflag());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList; 
	}

	/**
	 * 升级操作
	 * @param year
	 * @return
	 */
	public Boolean doClassLevelUp(String year,Integer dcschoolid) {
		if (year == null)
			return false;		
		StringBuilder sqlbuilder=new StringBuilder("{CALL class_auto_level_up(?,?,?)}");
		List<Object> objList=new ArrayList<Object>();
        objList.add(year);
        objList.add(dcschoolid);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

    /**
     * 得到更新或添加的SQL(乐知行)
     * @param obj
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSaveOrUpdateSql(ClassInfo obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        sqlbuilder.append("{call class_proc_addOrUpdate(");
        List<Object>objList = new ArrayList<Object>();

        if(obj.getLzxclassid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getLzxclassid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClassgrade()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassgrade());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClassname()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassname());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getYear()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getYear());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getType()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getType());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getPattern()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getPattern());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getSubjectid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getDctype()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getDctype());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getIsflag()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getIsflag());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return objList;
    }

}
