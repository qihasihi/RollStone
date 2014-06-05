package  com.school.dao;


import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IPageRightDAO;
import com.school.entity.PageRightInfo;
import com.school.util.PageResult;

@Component  
public class PageRightDAO extends CommonDAO<PageRightInfo> implements IPageRightDAO {

	public Boolean doDelete(PageRightInfo obj) {
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

	public Boolean doSave(PageRightInfo obj) {
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

	public Boolean doUpdate(PageRightInfo obj) {
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

	public List<Object> getDeleteSql(PageRightInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call page_right_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRef());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<PageRightInfo> getList(PageRightInfo obj, PageResult presult) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL page_right_proc_search_split(");
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL,NULL,NULL,NULL,NULL,NULL,");
		else{
			if(obj.getRef()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getRef());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getPagerightid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getPagerightid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getPagerighttype()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getPagerighttype());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getColumnid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getColumnid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getPagevalue()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getPagevalue());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getPagenamevalue()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getPagenamevalue());
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
		List<PageRightInfo> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, PageRightInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return roleList;	
	}


	public List<Object> getSaveSql(PageRightInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call page_right_proc_add("); 
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRef());
		}else
			sqlbuilder.append("NULL,"); 
		if(obj.getPagevalue()!=null){ 
			sqlbuilder.append("?,"); 
			objList.add(obj.getPagevalue());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getPagename()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getPagename());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getPagerightparentid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getPagerightparentid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getPagerighttype()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getPagerighttype());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getRemark()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRemark());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getColumnid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getColumnid());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(PageRightInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call page_right_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRef());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getPagerightid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getPagerightid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getPagevalue()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getPagevalue());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getPagename()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getPagename());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getPagerightparentid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getPagerightparentid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getPagerighttype()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getPagerighttype());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getRemark()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRemark());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getColumnid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getColumnid());
		}else
			sqlbuilder.append("NULL,");		
		sqlbuilder.append("?)}");
		return objList; 
	}
	
	/**
	 * 得到用户的栏目访问权限
	 * @param userref
	 * @return
	 */
	public List<PageRightInfo> getUserColumnList(String columnid,String userref) {
		StringBuilder sqlbuilder = new StringBuilder();		
		List<Object> objList = new ArrayList<Object>();
		sqlbuilder.append("{CALL user_column_right_list(?,?)}");
		List<Integer> types = new ArrayList<Integer>();
		//types.add(Types.INTEGER);
		Object[] objArray = new Object[0];
		objList.add(columnid);
		objList.add(userref);
		List<PageRightInfo> pagerightList = this.executeResult_PROC(sqlbuilder
				.toString(), objList, types, PageRightInfo.class, objArray);
		return pagerightList;
	}

}
