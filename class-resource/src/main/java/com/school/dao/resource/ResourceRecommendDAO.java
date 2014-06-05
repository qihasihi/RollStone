package com.school.dao.resource;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.resource.ResourceRecommend;
import com.school.dao.inter.resource.IResourceRecommendDAO;
import com.school.util.PageResult;

@Component
public class ResourceRecommendDAO extends CommonDAO<ResourceRecommend>
		implements IResourceRecommendDAO {

	public Boolean doSave(ResourceRecommend resourcerecommend) {
		if (resourcerecommend == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(resourcerecommend, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doDelete(ResourceRecommend resourcerecommend) {
		if (resourcerecommend == null 
				|| (resourcerecommend.getRef()==null&&resourcerecommend.getResid()==null))
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = getDeleteSql(resourcerecommend, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doUpdate(ResourceRecommend resourcerecommend) {
		if (resourcerecommend == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(resourcerecommend, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public List<ResourceRecommend> getList(ResourceRecommend resourcerecommend,
			PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL rs_resource_recommend_proc_split(");
		List<Object> objList = new ArrayList<Object>();
		if (resourcerecommend.getEndtime() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerecommend.getEndtime());
		} else
			sqlbuilder.append("null,");
		if (resourcerecommend.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerecommend.getResid());
		} else
			sqlbuilder.append("null,");
		if (resourcerecommend.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerecommend.getRef());
		} else
			sqlbuilder.append("null,");
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
		List<ResourceRecommend> resourcerecommendList = this
				.executeResult_PROC(sqlbuilder.toString(), objList, types,
						ResourceRecommend.class, objArray);
		if (presult != null && objArray[0] != null
				&& objArray[0].toString().trim().length() > 0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
		return resourcerecommendList;
	}

	public List<Object> getSaveSql(ResourceRecommend resourcerecommend,
			StringBuilder sqlbuilder) {
		if (resourcerecommend == null || sqlbuilder == null|| resourcerecommend.getResid() == null)
			return null;
		sqlbuilder.append("{CALL rs_resource_recommend_proc_addorupdate(");
		List<Object> objList = new ArrayList<Object>();
		if (resourcerecommend.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerecommend.getRef());
		} else
			sqlbuilder.append("null,");
		if (resourcerecommend.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerecommend.getResid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(ResourceRecommend resourcerecommend,
			StringBuilder sqlbuilder) {
		if (resourcerecommend == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL rs_resource_recommend_proc_delete(");
		List<Object> objList = new ArrayList<Object>();
		if (resourcerecommend.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerecommend.getResid());
		} else
			sqlbuilder.append("null,");
		if (resourcerecommend.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerecommend.getRef());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(ResourceRecommend resourcerecommend,
			StringBuilder sqlbuilder) {
		if (resourcerecommend == null || sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL rs_resource_recommend_proc_update(");
		List<Object> objList = new ArrayList<Object>();
		if (resourcerecommend.getEndtime() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerecommend.getEndtime());
		} else
			sqlbuilder.append("null,");
		if (resourcerecommend.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerecommend.getResid());
		} else
			sqlbuilder.append("null,");
		if (resourcerecommend.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerecommend.getRef());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

}
