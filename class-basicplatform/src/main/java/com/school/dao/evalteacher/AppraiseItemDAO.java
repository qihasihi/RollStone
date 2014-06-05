package com.school.dao.evalteacher;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.evalteacher.IAppraiseItemDAO;
import com.school.entity.evalteacher.AppraiseItemInfo;
import com.school.util.PageResult;

/**
 * 评教项目
 * 
 * @author zhengzhou
 * 
 */
@Component 
public class AppraiseItemDAO extends CommonDAO<AppraiseItemInfo> implements
		IAppraiseItemDAO<AppraiseItemInfo> {

	/**
	 * 删除
	 */
	public Integer delete(AppraiseItemInfo t) {
		if (t == null || t.getRef() == null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getDeleteSql(t, sqlbuilder);
		Object obj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (obj != null && obj.toString().trim().length() > 0)
			return Integer.parseInt(obj.toString().trim());
		return 0;
	}

	/**
	 * 获取删除的SQL
	 * 
	 * @param t
	 * @param sqlbuilder
	 * @return
	 */
	public List<Object> getDeleteSql(AppraiseItemInfo t,
			StringBuilder sqlbuilder) {
		if (t == null || t.getRef() == null)
			return null;
		sqlbuilder.append("{CALL appraise_item_proc_delete(?,?)}");
		List<Object> objList = new ArrayList<Object>();
		objList.add(t.getRef());
		return objList;
	}

	/**
	 * 分页查询
	 */
	public List<AppraiseItemInfo> getList(AppraiseItemInfo t, PageResult presult) {

		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL appraise_item_proc_split(");
		List<Object> objList = new ArrayList<Object>();
		if (presult == null || presult.getPageNo() < 1
				|| presult.getPageSize() < 1)
			sqlbuilder.append("1000000000,1,");
		else {
			sqlbuilder.append("?,?,");
			objList.add(presult.getPageSize());
			objList.add(presult.getPageNo());
		}
		if (t == null)
			sqlbuilder.append("0,0,NULL,NULL,0,");
		else {
			if (t.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(t.getRef());
			} else
				sqlbuilder.append("null,");
			if (t.getItemid() != null) {
				sqlbuilder.append("?,");
				objList.add(t.getItemid());
			}
			sqlbuilder.append("null,");
			if (t.getName() != null) {
				sqlbuilder.append("?,");
				objList.add(t.getName());
			} else
				sqlbuilder.append("null,");
			if (t.getYearid() != null) {
				sqlbuilder.append("?,");
				objList.add(t.getYearid());
			} else
				sqlbuilder.append("null,");
			if (t.getTargetidentitytype() != null) {
				sqlbuilder.append("?,");
				objList.add(t.getTargetidentitytype());
			} else
				sqlbuilder.append("null,");
		}
		if (presult == null || presult.getOrderIdx() == null
				|| presult.getOrderIdx() < 1)
			sqlbuilder.append("1,");
		else {
			sqlbuilder.append("?,");
			objList.add(presult.getOrderIdx());
		}
		sqlbuilder.append("?)}");
		Object[] sumNum = new Object[1];
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		List appitemList = this.executeResult_PROC(sqlbuilder.toString(),
				objList, types, AppraiseItemInfo.class, sumNum);
		if (presult != null && sumNum != null && sumNum.length > 0
				&& sumNum[0].toString().trim().length() > 0)
			presult.setRecTotal(Integer.parseInt(sumNum[1].toString().trim()));
		return appitemList;
	}

	/**
	 * 保存
	 */
	public Integer save(AppraiseItemInfo t) {
		if (t == null)
			return 0;
		StringBuilder sqlbuilder = new StringBuilder();
		List objList = getSaveSql(t, sqlbuilder);
		Object obj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList == null ? null : objList.toArray());
		if (obj != null)
			return Integer.parseInt(obj.toString().trim());
		return null;
	}

	public List<Object> getSaveSql(AppraiseItemInfo t, StringBuilder sqlbuilder) {
		if (t == null)
			return null;
		sqlbuilder.append("{CALL appraise_item_proc_add(");
		List<Object> objParam = new ArrayList<Object>();
		if(t.getItemid()==null)
			sqlbuilder.append("null,");
		else{
			sqlbuilder.append("?,");
			objParam.add(t.getItemid());
		}
		
		if(t.getName()==null)
			sqlbuilder.append("null,");
		else{
			sqlbuilder.append("?,");
			objParam.add(t.getName().trim());
		}
		
		if(t.getYearid()==null)
			sqlbuilder.append("null,");
		else{
			sqlbuilder.append("?,");
			objParam.add(t.getYearid());
		}
		
		if(t.getIstitle()==null)
			sqlbuilder.append("null,");
		else{
			sqlbuilder.append("?,");
			objParam.add(t.getIstitle());
		}
		
		if(t.getOptionid()==null)
			sqlbuilder.append("null,");
		else{
			sqlbuilder.append("?,");
			objParam.add(t.getOptionid());
		}
		
		if(t.getTargetidentitytype()==null)
			sqlbuilder.append("null,");
		else{
			sqlbuilder.append("?,");
			objParam.add(t.getTargetidentitytype());
		}
		sqlbuilder.append("?)}");
		return objParam;
	}

	/**
	 * 修改
	 */
	public Integer update(AppraiseItemInfo t) {
		if (t == null || t.getRef() == null || t.getName() == null
				|| t.getIstitle() == null || t.getOptionid() == null
				|| t.getYearid() == null || t.getTargetidentitytype() == null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder();
		List objList = this.getUpdateSql(t, sqlbuilder);
		Object obj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList == null ? null : objList.toArray());
		if (obj != null && obj.toString().trim().length() < 1)
			return Integer.parseInt(obj.toString().trim());
		return null;
	}

	/**
	 * 获取修改的SQL语句
	 * 
	 * @param t
	 * @return
	 */
	public List<Object> getUpdateSql(AppraiseItemInfo t,
			StringBuilder sqlbuilder) {
		if (t == null || t.getRef() == null || t.getName() == null)
			return null;
		sqlbuilder.append("{CALL appraise_item_proc_update(?,?,?)}");
		List<Object> objList = new ArrayList<Object>();
		objList.add(t.getRef());
		objList.add(t.getName().trim());
		return objList;
	}

	/**
	 * 得到数量
	 * 
	 * @param t
	 * @return
	 */
	public Integer getAppraiseTitleCount(AppraiseItemInfo t) {
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL appraise_item_proc_count(?,");
		List<Object> objList = new ArrayList<Object>();
		if (t == null || t.getTargetidentitytype() == null)
			objList.add(0);
		else
			objList.add(t.getTargetidentitytype());
		if (t != null) {
			if (t.getYearid() != null && t.getYearid()!= 0) {
				sqlbuilder.append("?,");
				objList.add(t.getYearid());
			} else
				sqlbuilder.append("NULL,");
		} else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		Object obj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (obj != null && obj.toString().trim().length() > 0)
			return Integer.valueOf(obj.toString().trim());
		return 0;
	}

	public List<AppraiseItemInfo> entityListConvertor(
			List<Map<String, Object>> mapList) {
		List<AppraiseItemInfo> list = new ArrayList<AppraiseItemInfo>();
		if (mapList.size() > 0 && mapList != null) {
			for (int i = 0; i < mapList.size(); i++) {
				AppraiseItemInfo appraiseinfo = new AppraiseItemInfo();
				if (mapList.get(i).containsKey("NAME")) {
					appraiseinfo
							.setName(mapList.get(i).get("NAME").toString() != null ? mapList
									.get(i).get("NAME").toString()
									: null);
				}
				list.add(appraiseinfo);
			}
		}
		return list;
	}

	public Boolean doSave(AppraiseItemInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean doUpdate(AppraiseItemInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean doDelete(AppraiseItemInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getNextSeqId() {
		// TODO Auto-generated method stub
		return null;
	}

	public List getAppraiseTitle(AppraiseItemInfo t, BigDecimal targetid,
			Boolean order) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		// TODO Auto-generated method stub
	//	this.executeArray_SQL(sqlArrayList, objArrayList);
		return this.executeArrayQuery_PROC(sqlArrayList, objArrayList);
	}
}
