package com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IUserDAO;
import com.school.entity.RoleUser;
import com.school.entity.UserInfo;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

@Component
public class UserDAO extends CommonDAO<UserInfo> implements IUserDAO {

	public Boolean doDelete(UserInfo obj) {
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = getDeleteSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}



	public Boolean doSave(UserInfo obj) {
		if (obj == null)
			return false;
		obj.setRef(UUID.randomUUID().toString());
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

	public Boolean doUpdate(UserInfo obj) {
		if (obj == null){
			return false;
		}
			
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

	public List<Object> getDeleteSql(UserInfo obj, StringBuilder sqlbuilder) {
		if (obj == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{call user_proc_delete(");
		List<Object> objList = new ArrayList<Object>();
		if (obj.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getRef());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getUserid());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getUsername() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getUsername()); 
		} else
			sqlbuilder.append("NULL,");
		if (obj.getStateid() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getStateid());
		} else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

    /**
     * 用ETTUSER_ID将ETT_USER_ID更新为空字段
     * @param ettUserid
     * @param sqlbuilder
     * @return
     */
    public List<Object> getUpdateEttUserByEttUserIdSql(final Integer ettUserid,StringBuilder sqlbuilder){
        if(ettUserid==null||sqlbuilder==null)return null;
        sqlbuilder.append("{CALL user_info_proc_update_ettuserid_byettuserid(?,?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(ettUserid);
        return objList;
    }

    /**
     * 得到学生首页，显示任务提醒
     * @param uid
     * @return
     */
    public List<Map<String,Object>> getCourseTaskCount(final Integer uid){
        if(uid==null)return null;
        StringBuilder sqlbuilder=new StringBuilder("{CALL cal_stu_task_remark_count(?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(uid);
        return this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
    }

	public List<UserInfo> getList(UserInfo obj, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL user_info_proc_split(");
		List<Object> objList = new ArrayList<Object>();
		if (obj == null)
			sqlbuilder.append("NULL,NULL,NULL,NULL,NULL,NULL,NULL,NUll,NUll,NUll,");
		else {
			if (obj.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(obj.getRef());
			} else
				sqlbuilder.append("NULL,");
			if (obj.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(obj.getUserid());
			} else
				sqlbuilder.append("NULL,");
			if (obj.getUsername() != null) {
				sqlbuilder.append("?,"); 
				objList.add(obj.getUsername());
			} else
				sqlbuilder.append("NULL,");
			if (obj.getStateid() != null) {
				sqlbuilder.append("?,");
				objList.add(obj.getStateid());
			} else
				sqlbuilder.append("NULL,");
			if (obj.getPassword() != null) {
				sqlbuilder.append("?,");
				objList.add(obj.getPassword());
			} else
				sqlbuilder.append("NULL,");
            if (obj.getMailaddress() != null) {
                sqlbuilder.append("?,");
                objList.add(obj.getMailaddress());
            } else
                sqlbuilder.append("NULL,");
            if(obj.getLzxuserid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getLzxuserid());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getEttuserid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getEttuserid());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getSchoolid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getSchoolid());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getDcschoolid()!=null&&obj.getDcschoolid()>0){
                sqlbuilder.append("?,");
                objList.add(obj.getDcschoolid());
            }else
                sqlbuilder.append("NULL,");
		}
		if (presult != null && presult.getPageNo() > 0
				&& presult.getPageSize() > 0) {
			sqlbuilder.append("?,?,");
			objList.add(presult.getPageNo());
			objList.add(presult.getPageSize());
		} else {
			sqlbuilder.append("NULL,NULL,");
		}
		if (presult != null && presult.getOrderBy() != null
				&& presult.getOrderBy().trim().length() > 0) {
			sqlbuilder.append("?,");
			objList.add(presult.getOrderBy());
		} else {
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		List<Integer> types = new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray = new Object[1];
		List<UserInfo> userList = this
				.executeResult_PROC(sqlbuilder.toString(), objList, types,
						UserInfo.class, objArray);
		if (presult != null && objArray[0] != null
				&& objArray[0].toString().trim().length() > 0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
		return userList;
	}

	public List<Object> getSaveSql(UserInfo obj, StringBuilder sqlbuilder) {
		if (obj == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{call user_proc_add(");
		List<Object> objList = new ArrayList<Object>();
		if (obj.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getRef());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getUsername() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getUsername());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getPassword() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getPassword());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getStateid() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getStateid());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getMailaddress() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getMailaddress());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getRealname() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getRealname());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getGender() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getGender());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getPassquestion() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getPassquestion());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getQuestionanswer() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getQuestionanswer());
		} else
			sqlbuilder.append("NULL,");
		if(obj.getBirthdate()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getBirthdate());
		}else 
			sqlbuilder.append("NULL,");
        if(obj.getLzxuserid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getLzxuserid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSchoolid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getSchoolid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getDcschoolid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getDcschoolid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getEttuserid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getEttuserid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getHeadimage()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getHeadimage());
        }else
            sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(UserInfo obj, StringBuilder sqlbuilder) {
		if (obj == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{call user_proc_update(");
		List<Object> objList = new ArrayList<Object>();
		if (obj.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getRef());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getUserid());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getUsername() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getUsername());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getPassword() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getPassword());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getStateid() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getStateid());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getIdentitynumber() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getIdentitynumber());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getBirthdate() != null) {
			sqlbuilder.append("?,");
			objList.add(UtilTool.DateConvertToString(obj.getBirthdate(),DateType.type1));
		} else
			sqlbuilder.append("NULL,");
		if (obj.getAddress() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getAddress());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getMailaddress() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getMailaddress());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getRealname() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getRealname());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getGender() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getGender());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getHeadimage() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getHeadimage());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getPassquestion() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getPassquestion());
		} else
			sqlbuilder.append("NULL,");
		if (obj.getQuestionanswer() != null) {
			sqlbuilder.append("?,");
			objList.add(obj.getQuestionanswer());
		} else
			sqlbuilder.append("NULL,");
        if (obj.getIsmodify() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getIsmodify());
        } else
            sqlbuilder.append("NULL,");
        if(obj.getLzxuserid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getLzxuserid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSchoolid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getSchoolid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getEttuserid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getEttuserid());
        }else
            sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public int checkUsername(String username,Integer dcschoolid) {
		// TODO Auto-generated method stub
		int result = 0;
		if(username==null)
			return 0;
		StringBuilder sqlbuilder = new StringBuilder(
				"select count(*) from user_info where 1=1");
		List<Object> objList=new ArrayList<Object>();
		if (username != null) {
			sqlbuilder.append(" and user_name=?");
            objList.add(username);
		}
        if(dcschoolid!=null){
            sqlbuilder.append(" AND dc_school_id=?");
            objList.add(dcschoolid);
        }
		Object o = this.executeSalar_SQL(sqlbuilder.toString(), objList.toArray());

		result = Integer.parseInt(o.toString());
		return result;
	}

	public UserInfo getUser(UserInfo u) {
		// TODO Auto-generated method stub
		if(u==null 
				&& u.getRef()==null
				&& u.getUserid()==null
				&& u.getUsername()==null
				&& u.getStateid()==null)
			return null;
		
		UserInfo user = new UserInfo();
		user.setRef(u.getRef());
		user.setUserid(u.getUserid());
		user.setUsername(u.getUsername());
		user.setStateid(u.getStateid());
        if(u.getDcschoolid()!=null){
            user.setDcschoolid(u.getDcschoolid());
        }
		List<UserInfo> userList= this.getList(user, null);
		if(userList!=null && userList.size()==1 )
			return userList.get(0);
		else
			return null;
	}

	public UserInfo doLogin(UserInfo user) {
		// TODO Auto-generated method stub ,ui.identity_name
		 //INNER JOIN j_user_identity_info ui on u.ref=ui.user_id 		
		if(user==null)return null;
	//	if(user.getUsername()==null)return null;
		List<UserInfo> usrList=this.getList(user,null);		
		if(usrList!=null&&usrList.size()>0){
			return usrList.get(0);
		}else{
			return null;
		}
	}
	
	public List<UserInfo> getUserForSelect(String roleid,String clsid,String cname,String grade,String username,PageResult presult){
		StringBuilder sqlbuilder = new StringBuilder("{call user_activity_proc_list(");
		List<Object> objList = new ArrayList<Object>();
		if(roleid!=null&&!roleid.equals("")){
			objList.add(roleid);
			sqlbuilder.append("?,");
		}else{
			sqlbuilder.append("NULL,");
		}
		if(clsid!=null&&!clsid.equals("")){
			objList.add(clsid);
			sqlbuilder.append("?,");
		}else{
			sqlbuilder.append("NULL,");
		}
		if(cname!=null&&!cname.equals("")){
			objList.add(cname);
			sqlbuilder.append("?,");
		}else{
			sqlbuilder.append("NULL,");
		}
		if(grade!=null&&!grade.equals("")){
			objList.add(grade);
			sqlbuilder.append("?,");
		}else{
			sqlbuilder.append("NULL,");
		}
		if(username!=null&&!username.equals("")){
			objList.add(username);
			sqlbuilder.append("?,");
		}else{
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
		List<UserInfo> userList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, UserInfo.class, objArray); 
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return userList; 		
	}

	public List<UserInfo> getUserByCondition(String year,boolean isselstu,boolean isseljz,RoleUser obj,int dc_school_id,
			PageResult presult) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL user_condition_proc_search_split(");
		List<Object> objList=new ArrayList<Object>();
		if(year!=null){
			objList.add(year);
			sqlbuilder.append("?,");
		}else{
			sqlbuilder.append("NULL,");
		}
		if(isselstu){
			objList.add(1);
			sqlbuilder.append("?,"); 
		}else{
			sqlbuilder.append("NULL,");
		}
		if(isseljz){ 
			objList.add(1);
			sqlbuilder.append("?,"); 
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj==null)
			sqlbuilder.append("NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,");
		else{
			if(obj.getRoleidstr()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getRoleidstr());
			}else
				sqlbuilder.append("NULL,"); 
			if(obj.getSubjectidstr()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getSubjectidstr());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getClsidstr()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getClsidstr());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getDeptidstr()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getDeptidstr());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getJobidstr()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getJobidstr()); 
			}else
				sqlbuilder.append("NULL,");
			if(obj.getGradename()!=null){
				sqlbuilder.append("?,"); 
				objList.add(obj.getGradename());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getUserinfo().getUsername()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getUserinfo().getUsername());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getIdentityname()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getIdentityname());
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
        // add dc_school_id
        objList.add(dc_school_id);
        sqlbuilder.append("?,");

		sqlbuilder.append("?)}");	
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List<UserInfo> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, UserInfo.class, objArray); 
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return roleList; 
	}

	public Map<String,Object> getTestUser(String year, String relation, PageResult presult) {
		StringBuilder sqlbuilder=new StringBuilder();
		sqlbuilder.append("{call user_test_list(");
		List<Object> objList = new ArrayList<Object>();
		if (year!= null) {
			sqlbuilder.append("?,");
			objList.add(year);
		} else
			sqlbuilder.append("NULL,");
		if (relation!= null) {
			sqlbuilder.append("?,");
			objList.add(relation);
		} else
			sqlbuilder.append("NULL,");		
		sqlbuilder.append("?)}");
		objList.add(presult.getPageNo());
		List<Map<String,Object>> objMapList=this.executeResultListMap_PROC(sqlbuilder.toString(), objList);
		if(objMapList!=null&&objMapList.size()>0)
			return objMapList.get(0);
		return null;
	}

    public List<UserInfo>getUserNotCompleteTask(Long taskid,Integer userid,Integer classid,String flag){
        StringBuilder sqlbuilder = new StringBuilder("{call tp_task_notcomplete_proc_split(");
        List<Object> objList = new ArrayList<Object>();
        if(taskid==null)
            return null;
        sqlbuilder.append("?,NULL,");
        objList.add(taskid);
        if(userid!=null){
            objList.add(userid);
            sqlbuilder.append("?,");
        }else
            sqlbuilder.append("null,");
        if(flag!=null){
            objList.add(flag);
            sqlbuilder.append("?,");
        }else
            sqlbuilder.append("null,");
        if(classid!=null){
            objList.add(classid);
            sqlbuilder.append("?,");
        }else
            sqlbuilder.append("null,");

        sqlbuilder.append("?)}");
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<UserInfo> userList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, UserInfo.class, objArray);
        return userList;
    }
}
