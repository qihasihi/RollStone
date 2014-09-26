package  com.school.dao;


import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IClassUserDAO;
import com.school.entity.ClassUser;
import com.school.util.PageResult;

@Component  
public class ClassUserDAO extends CommonDAO<ClassUser> implements IClassUserDAO {

	public Boolean doDelete(ClassUser obj) {
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(obj, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}
	public Boolean doSave(ClassUser obj) {
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

	public Boolean doUpdate(ClassUser obj) {
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

	public List<Object> getDeleteSql(ClassUser obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call classuser_proc_delete(");
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
		if(obj.getClassinfo().getClassid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClassinfo().getClassid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getRelationtype()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRelationtype());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getSportsex()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getSportsex());
		}else
			sqlbuilder.append("NULL,");
	
		if(obj.getClassgrade()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClassgrade());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getSubjectid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getSubjectid());
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
		if(obj.getClassinfo().getType()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClassinfo().getType());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getClassinfo().getIsflag()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClassinfo().getIsflag());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getAfteryear()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getAfteryear());
		}else
			sqlbuilder.append("NULL,");
		
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<ClassUser> getList(ClassUser obj, PageResult presult) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL j_class_user_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,");
		else{
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
			if(obj.getUserinfo().getUserid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserinfo().getUserid());
			}else
				sqlbuilder.append("NULL,");		   
			if(obj.getUserinfo().getUsername()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserinfo().getUsername());
			}else
				sqlbuilder.append("NULL,");		   
			if(obj.getClassinfo().getClassid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getClassinfo().getClassid());
			}else
				sqlbuilder.append("NULL,");		
			if(obj.getClassinfo().getClassname()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getClassinfo().getClassname());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getClassinfo().getClassgrade()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getClassinfo().getClassgrade());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getClassinfo().getType()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getClassinfo().getType());
			}else
				sqlbuilder.append("NULL,");		      
			if(obj.getClassinfo().getYear()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getClassinfo().getYear());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getClassinfo().getPattern()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getClassinfo().getPattern());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getRelationtype()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getRelationtype());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getNorelationtype()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getNorelationtype());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getSportsex()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getSportsex());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getRealname()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getRealname());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getClassinfo().getIsflag()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getClassinfo().getIsflag());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getHistoryyear()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getHistoryyear());
			}else
				sqlbuilder.append("NULL,");
            if(obj.getSubjectid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getSubjectid());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getUserinfo().getStateid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getUserinfo().getStateid());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getCompletenum()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getCompletenum());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getClassinfo().getDcschoolid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getClassinfo().getDcschoolid());
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
		List<ClassUser> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, ClassUser.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return roleList;	
	}

    public List<ClassUser> getListByTchYear(String userid,String year) {
        if(userid==null || year==null)
            return null;
        StringBuilder sqlbuilder=new StringBuilder("{CALL j_class_user_proc_split_bytch(?,?,?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(userid);
        objList.add(year);
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<ClassUser> cuList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, ClassUser.class, objArray);
        return cuList;
    }

	public List<Object> getSaveSql(ClassUser obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call classuser_proc_add(");
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
		if(obj.getClassinfo().getClassid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClassinfo().getClassid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getRelationtype()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRelationtype());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getSportsex()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getSportsex());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getSubjectid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getSubjectid());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(ClassUser obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call j_class_user_proc_update(");
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
		if(obj.getClassinfo().getClassid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClassinfo().getClassid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getRelationtype()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRelationtype());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getSportsex()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getSportsex());
		}else
			sqlbuilder.append("NULL,");
		
		sqlbuilder.append("?)}");
		return objList; 
	}
	/**
	 * 得到论题下的学生
	 * @param presult
	 * @return
	 */
	public List<ClassUser> getThemeList(ClassUser obj, PageResult presult){
		StringBuilder sqlbuilder=new StringBuilder("{CALL class_user_proc_theme_split(");
		List<Object> objList=new ArrayList<Object>();		
		if(obj.getClassid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClassid());
		}else
			sqlbuilder.append("NULL,");
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
		if(obj.getRelationtype()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRelationtype());
		}else
			sqlbuilder.append("NULL,");		   
		if(obj.getYear()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getYear());
		}else
			sqlbuilder.append("NULL,");		
		if(obj.getPattern()!=null){
			sqlbuilder.append("?");
			objList.add(obj.getPattern());
		}else
			sqlbuilder.append("NULL");		
		sqlbuilder.append(")}");
		List<Integer> types=new ArrayList<Integer>();
		Object[] objArray=new Object[1];
		List<ClassUser> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, ClassUser.class, objArray);
		if(roleList!=null)
			return roleList;	
		else
			return null;
	}

	public List<ClassUser> getClassUserByTchAndStu(String tchUserRef, String stuUserRef,
			String year,Integer clsisflag) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL classuser_proc_getby_tchid_stuid(?,?,?");
		List<Object> objList=new ArrayList<Object>();
		objList.add(tchUserRef);
		objList.add(stuUserRef);
		objList.add(year);
		if(clsisflag==null)
			sqlbuilder.append(",NULL");
		else{
			sqlbuilder.append(",?");
			objList.add(clsisflag);
		}
		sqlbuilder.append(",?)}");
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List<ClassUser> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, ClassUser.class, objArray);
		if(roleList!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			return roleList;	
		else
			return null;
	}
	/**
	 * 调班查询学生。
	 * @param relationType
	 * @param year
	 * @param clsid
	 * @return
	 */
	public List<Map<String,Object>> getClassUserWithTiaoban(String relationType,String year,String clsid){
		StringBuilder sqlbuilder=new StringBuilder("{CALL class_user_tiaoban(");
		List<Object> objList=new ArrayList<Object>();
		if(relationType==null)
			sqlbuilder.append("NULL,");
		else{
			sqlbuilder.append("?,");
			objList.add(relationType);
		}
		if(year==null)
			sqlbuilder.append("NULL,");
		else{
			sqlbuilder.append("?,");
			objList.add(year);
		}
		if(clsid==null)
			sqlbuilder.append("NULL");
		else{
			sqlbuilder.append("?");
			objList.add(clsid);
		}		
		sqlbuilder.append(")}");
		return this.executeResultListMap_PROC(sqlbuilder.toString(), objList);
	}

    /**
     * 资源上传，得到Grade，subjectid
     * @param userref
     * @param relationType
     * @param year
     * @param gradeid
     * @return
     */
    public List<Map<String,Object>>  getClassUserTeacherBy(String userref,String relationType,String year,Integer gradeid){
        StringBuilder sqlbuilder=new StringBuilder("{CALL j_class_user_proc_teachersearch(");
        List<Object> objList=new ArrayList<Object>();
        if(relationType==null)
            sqlbuilder.append("NULL,");
        else{
            sqlbuilder.append("?,");
            objList.add(relationType);
        }
        if(userref==null)
            sqlbuilder.append("NULL,");
        else{
            sqlbuilder.append("?,");
            objList.add(userref);
        }
        if(year==null)
            sqlbuilder.append("NULL,");
        else{
            sqlbuilder.append("?,");
            objList.add(year);
        }
        if(gradeid==null)
            sqlbuilder.append("NULL");
        else{
            sqlbuilder.append("?");
            objList.add(gradeid);
        }
        sqlbuilder.append(")}");
        return this.executeResultListMap_PROC(sqlbuilder.toString(), objList);
    }

    @Override
    public Integer isTeachingBanZhuRen(String userid, Integer classid,String termid,String gradevalue) {
        if (userid == null)
            return 0;
        StringBuilder sqlbuilder = new StringBuilder("{call isTeacherAndBanZhuRen(?,");
        List<Object>objList=new ArrayList<Object>();
        objList.add(userid);
        if(classid!=null){
            objList.add(classid);
            sqlbuilder.append("?,");
        }else
            sqlbuilder.append("NULL,");
        if(termid!=null&&termid.length()>0){
            objList.add(termid);
            sqlbuilder.append("?,");
        }else
            sqlbuilder.append("NULL,");
        if(gradevalue!=null&&gradevalue.length()>0){
            objList.add(gradevalue);
            sqlbuilder.append("?,");
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return Integer.parseInt(afficeObj.toString());
        }
        return 0;
    }
}
