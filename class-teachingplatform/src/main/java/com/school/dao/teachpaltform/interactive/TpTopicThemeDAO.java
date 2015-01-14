package com.school.dao.teachpaltform.interactive;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.teachpaltform.interactive.ITpTopicThemeDAO;
import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Component  
public class TpTopicThemeDAO extends CommonDAO<TpTopicThemeInfo> implements ITpTopicThemeDAO {

	public Boolean doSave(TpTopicThemeInfo tptopicthemeinfo) {
		if (tptopicthemeinfo == null)
			return false;
        List<String> sqlList=new ArrayList<String>();
        List<List<Object>> paraList=new ArrayList<List<Object>>();
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tptopicthemeinfo, sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlList.add(sqlbuilder.toString());
            paraList.add(objList);
        }
        //得到theme_content的更新语句
        this.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "theme_content"
                , tptopicthemeinfo.getThemecontent(), tptopicthemeinfo.getThemeid().toString(),sqlList,paraList);
        //得到comment_content的更新语句
        this.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "comment_content"
                , tptopicthemeinfo.getCommentcontent(), tptopicthemeinfo.getThemeid().toString(),sqlList,paraList);
		//开始执行
        if(sqlList!=null&&paraList!=null&&sqlList.size()==paraList.size()&&sqlList.size()>0)
            return this.doExcetueArrayProc(sqlList,paraList);
        else
          return false;
	}
	
	public Boolean doDelete(TpTopicThemeInfo tptopicthemeinfo) {
		if(tptopicthemeinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tptopicthemeinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

    /**
     * 得到评论数
     * @param topicid
     * @param clsid
     * @return
     */
    public Integer getPingLunShu(final Long topicid,final  Integer clsid){
        if(topicid==null)
            return 0;
        StringBuilder sqlBuilder=new StringBuilder();
        sqlBuilder.append("{CALL tp_theme_reply_info_pinglunshu(?,");
        List<Object> objParam=new ArrayList<Object>();
        objParam.add(topicid);
        if(clsid!=null){
            sqlBuilder.append("?,");
            objParam.add(clsid);
        }else
            sqlBuilder.append("NULL,");
        sqlBuilder.append("?)}");
        Object afficeObj=this.executeSacle_PROC(sqlBuilder.toString(), objParam.toArray());
        if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
            return Integer.parseInt(afficeObj.toString());
        }
        return 0;
    }


	public Boolean doUpdate(TpTopicThemeInfo tptopicthemeinfo) {
		if (tptopicthemeinfo == null)
			return false;
        List<String> sqlList=new ArrayList<String>();
        List<List<Object>> paraList=new ArrayList<List<Object>>();
        StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tptopicthemeinfo, sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlList.add(sqlbuilder.toString());
            paraList.add(objList);
        }
        if(tptopicthemeinfo.getThemecontent()!=null){
            //先清空
            this.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "theme_content"
                    , "", tptopicthemeinfo.getThemeid().toString(),sqlList,paraList);
            //得到theme_content的更新语句
            this.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "theme_content"
                    , tptopicthemeinfo.getThemecontent(), tptopicthemeinfo.getThemeid().toString(),sqlList,paraList);
        }
        if(tptopicthemeinfo.getCommentcontent()!=null){
            //先清空
            this.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "comment_content"
                    , "", tptopicthemeinfo.getThemeid().toString(),sqlList,paraList);
            //得到comment_content的更新语句
            this.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "comment_content"
                    , tptopicthemeinfo.getCommentcontent(), tptopicthemeinfo.getThemeid().toString(),sqlList,paraList);
        }
        //开始执行
        if(sqlList!=null&&paraList!=null&&sqlList.size()==paraList.size()&&sqlList.size()>0)
            return this.doExcetueArrayProc(sqlList,paraList);
        else
            return false;
	}
	
	public List<TpTopicThemeInfo> getList(TpTopicThemeInfo tptopicthemeinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_topic_theme_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
        if (tptopicthemeinfo.getViewcount() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getViewcount());
        } else
            sqlbuilder.append("null,");

        if (tptopicthemeinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getCommentuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCommentuserid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getIsessence() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getIsessence());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getThemetitle() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getThemetitle());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getThemecontent() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getThemecontent());
        } else
            sqlbuilder.append("null,");

        if (tptopicthemeinfo.getCommentmtime() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCommentmtime());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getCommenttitle() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCommenttitle());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getThemeid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getThemeid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getIstop() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getIstop());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getTopicid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getTopicid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getCommentcontent() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCommentcontent());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getLoginuserref() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getLoginuserref());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getSelectType() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getSelectType());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getQuoteid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getQuoteid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getStatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getStatus());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getClsid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getClsid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getClsType() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getClsType());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getSearchRoleType() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getSearchRoleType());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getImattach() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getImattach());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getImattachtype() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getImattachtype());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getSourceid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getSourceid());
        } else
            sqlbuilder.append("null,");
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
		List<TpTopicThemeInfo> tptopicthemeinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpTopicThemeInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tptopicthemeinfoList;	
	}
	
	public List<Object> getSaveSql(TpTopicThemeInfo tptopicthemeinfo, StringBuilder sqlbuilder) {
		if(tptopicthemeinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_topic_theme_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (tptopicthemeinfo.getViewcount() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicthemeinfo.getViewcount());
			} else
				sqlbuilder.append("null,");

			if (tptopicthemeinfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicthemeinfo.getCuserid());
			} else
				sqlbuilder.append("null,");
			if (tptopicthemeinfo.getCommentuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicthemeinfo.getCommentuserid());
			} else
				sqlbuilder.append("null,");
			if (tptopicthemeinfo.getCloudstatus() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicthemeinfo.getCloudstatus());
			} else
				sqlbuilder.append("null,");
			if (tptopicthemeinfo.getIsessence() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicthemeinfo.getIsessence());
			} else
				sqlbuilder.append("null,");
			if (tptopicthemeinfo.getThemetitle() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicthemeinfo.getThemetitle());
			} else
				sqlbuilder.append("null,");

			if (tptopicthemeinfo.getCommentmtime() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicthemeinfo.getCommentmtime());
			} else
				sqlbuilder.append("null,");
			if (tptopicthemeinfo.getCommenttitle() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicthemeinfo.getCommenttitle());
			} else
				sqlbuilder.append("null,");
			if (tptopicthemeinfo.getThemeid() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicthemeinfo.getThemeid());
			} else
				sqlbuilder.append("null,");
			if (tptopicthemeinfo.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicthemeinfo.getCourseid());
			} else
				sqlbuilder.append("null,");
			if (tptopicthemeinfo.getIstop() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicthemeinfo.getIstop());
			} else
				sqlbuilder.append("null,");
			if (tptopicthemeinfo.getTopicid() != null) {
				sqlbuilder.append("?,");
				objList.add(tptopicthemeinfo.getTopicid());
			} else
				sqlbuilder.append("null,");
			if (tptopicthemeinfo.getQuoteid() != null) {
	            sqlbuilder.append("?,");
	            objList.add(tptopicthemeinfo.getQuoteid());
	        } else
	            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getStatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getStatus());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getImattach() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getImattach());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getImattachtype() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getImattachtype());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getSourceid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getSourceid());
        } else
            sqlbuilder.append("null,");

		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TpTopicThemeInfo tptopicthemeinfo, StringBuilder sqlbuilder) {
		if(tptopicthemeinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_topic_theme_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
        if (tptopicthemeinfo.getViewcount() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getViewcount());
        } else
            sqlbuilder.append("null,");

        if (tptopicthemeinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getCommentuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCommentuserid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getIsessence() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getIsessence());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getThemetitle() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getThemetitle());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getThemecontent() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getThemecontent());
        } else
            sqlbuilder.append("null,");

        if (tptopicthemeinfo.getCommentmtime() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCommentmtime());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getCommenttitle() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCommenttitle());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getThemeid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getThemeid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getIstop() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getIstop());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getTopicid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getTopicid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getCommentcontent() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCommentcontent());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getQuoteid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getQuoteid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getStatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getStatus());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getImattach() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getImattach());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getImattachtype() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getImattachtype());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getSourceid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getSourceid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TpTopicThemeInfo tptopicthemeinfo, StringBuilder sqlbuilder) {
		if(tptopicthemeinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_topic_theme_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
        if (tptopicthemeinfo.getViewcount() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getViewcount());
        } else
            sqlbuilder.append("null,");

        if (tptopicthemeinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getCommentuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCommentuserid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getIsessence() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getIsessence());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getThemetitle() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getThemetitle());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getCommentmtime() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCommentmtime());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getCommenttitle() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCommenttitle());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getThemeid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getThemeid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getIstop() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getIstop());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getTopicid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getTopicid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getQuoteid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getQuoteid());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getStatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getStatus());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getLastfabiao() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getLastfabiao());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getImattach() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getImattach());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getImattachtype() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getImattachtype());
        } else
            sqlbuilder.append("null,");
        if (tptopicthemeinfo.getSourceid() != null) {
            sqlbuilder.append("?,");
            objList.add(tptopicthemeinfo.getSourceid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}
	
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @return
     */
    public void getSynchroSql(TpTopicThemeInfo entity,List<String> sqlArrayList,List<List<Object>> objArrayList){
        if(entity==null||sqlArrayList==null||objArrayList==null)return;
        StringBuilder sqlbuilder=new StringBuilder();
        sqlbuilder.append("{CALL tp_topic_theme_info_proc_synchro(");
        List<Object>objList = new ArrayList<Object>();
        if (entity.getViewcount() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getViewcount());
        } else
            sqlbuilder.append("null,");

        if (entity.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (entity.getCommentuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCommentuserid());
        } else
            sqlbuilder.append("null,");
        if (entity.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (entity.getIsessence() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getIsessence());
        } else
            sqlbuilder.append("null,");
        if (entity.getThemetitle() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getThemetitle());
        } else
            sqlbuilder.append("null,");

        if (entity.getCommentmtime() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCommentmtime());
        } else
            sqlbuilder.append("null,");
        if (entity.getCommenttitle() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCommenttitle());
        } else
            sqlbuilder.append("null,");
        if (entity.getThemeid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getThemeid());
        } else
            sqlbuilder.append("null,");
        if (entity.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (entity.getIstop() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getIstop());
        } else
            sqlbuilder.append("null,");
        if (entity.getTopicid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getTopicid());
        } else
            sqlbuilder.append("null,");
        if (entity.getImattach() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getImattach());
        } else
            sqlbuilder.append("null,");
        if (entity.getImattachtype() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getImattachtype());
        } else
            sqlbuilder.append("null,");
        if (entity.getSourceid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getSourceid());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        sqlArrayList.add(sqlbuilder.toString());
        objArrayList.add(objList);
        //得到theme_content的更新语句
        if(entity.getThemecontent()!=null)
            this.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "theme_content"
                    , entity.getThemecontent(), entity.getThemeid().toString(),sqlArrayList,objArrayList);
        //得到comment_content的更新语句
        if(entity.getCommentcontent()!=null)
            this.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "comment_content"
                    , entity.getCommentcontent(), entity.getThemeid().toString(),sqlArrayList,objArrayList);

    }
    
    

}
