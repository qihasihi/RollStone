package com.school.dao.evalteacher;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.evalteacher.IAppraiseLogsDAO;
import com.school.entity.evalteacher.AppraiseLogsInfo;
import com.school.util.PageResult;
import com.school.util.UtilTool;

/**
 * 评教项目
 * 
 * @author zhusx
 * 
 */
@Component
public class AppraiseLogsDAO extends CommonDAO<AppraiseLogsInfo> implements
		IAppraiseLogsDAO<AppraiseLogsInfo> {

	/**
	 * 查询评价日志
	 */
	public List<AppraiseLogsInfo> getList(AppraiseLogsInfo t, PageResult presult) {
		List<Object> objPara = new ArrayList<Object>();
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL appraise_logs_proc_split(");
		if (presult == null || presult.getPageNo() < 1
				|| presult.getPageSize() < 1)
			sqlbuilder.append("10000000000,1,");
		else {
			sqlbuilder.append("?,?,");
			objPara.add(presult.getPageNo());
			objPara.add(presult.getPageSize());
		}
		if (t == null) {
			sqlbuilder.append("0,0,0,0,0,0,NULL,0,");
		} else {
			if (t.getRef() != null) {
				sqlbuilder.append("?,");
				objPara.add(t.getRef());
			} else
				sqlbuilder.append("0,");
			if (t.getUserid() != null) {
				sqlbuilder.append("?,");
				objPara.add(t.getUserid());
			} else
				sqlbuilder.append("0,");
			if (t.getItemid() != null) {
				sqlbuilder.append("?,");
				objPara.add(t.getItemid());
			} else
				sqlbuilder.append("0,");
			if (t.getOptionid() != null) {
				sqlbuilder.append("?,");
				objPara.add(t.getOptionid());
			} else
				sqlbuilder.append("0,");
			if (t.getTargetuserref() != null) {
				sqlbuilder.append("?,");
				objPara.add(t.getTargetuserref());
			} else
				sqlbuilder.append("0,");
			if (t.getYearid() != null) {
				sqlbuilder.append("?,");
				objPara.add(t.getYearid());
			} else
				sqlbuilder.append("NULL,");
			if (t.getTargetidentitytype() != null) {
				sqlbuilder.append("?,");
				objPara.add(t.getTargetidentitytype());
			} else
				sqlbuilder.append("0,");
		}
		if (presult == null || presult.getOrderIdx() < 1)
			sqlbuilder.append("1,");
		else {
			sqlbuilder.append("?,");
			objPara.add(presult.getOrderIdx());
		}
		sqlbuilder.append("?,?)}");
		List<Integer> oracleTypeList = new ArrayList<Integer>();
		oracleTypeList.add(Types.INTEGER);
		Object[] obj = new Object[1];
		List applogsList = this.executeResult_PROC(sqlbuilder.toString(),
				objPara, oracleTypeList, AppraiseLogsInfo.class, obj);
		if (presult != null && obj != null && obj.length > 0
				&& UtilTool.isNumber(obj[0].toString()))
			presult.setRecTotal(Integer.valueOf(obj[0].toString().trim()));
		return applogsList;
	}

	/**
	 * 查询数量
	 * 
	 * @param t
	 * @return
	 */
	public int getListNum(AppraiseLogsInfo t) {
		// query_user_appraise_num
		if (t == null || t.getTargetuserref() == null || t.getUserid() == null
				|| t.getYearid() == null)
			return 0;
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL appraise_logs_proc_usernum(?,?,?,?)}");
		Object[] objPara = { t.getUserid(), t.getTargetuserref(), t.getYearid() };
		Object obj = this.executeSacle_PROC(sqlbuilder.toString(), objPara);
		if (obj == null || obj.toString().length() < 1
				|| !UtilTool.isNumber(obj.toString().trim()))
			return 0;
		return Integer.parseInt(obj.toString().trim());

	}

	/**
	 * 得到添加评价的SQL
	 */
	public List<Object> getSaveSql(AppraiseLogsInfo t, StringBuilder sqlbuilder) {
		if (sqlbuilder == null || t == null || t.getUserid() == null
				|| t.getItemid() == null || t.getOptionid() == null
				|| t.getTargetuserref() == null || t.getYearid() == null
				|| t.getTargetclassuserref() == null || t.getScore() < 0
				|| t.getClassid() < 1)
			return null;
		sqlbuilder.append("{CALL appraise_logs_proc_add(?,?,?,?,?,?,?,?,?)}");
		List<Object> objList = new ArrayList<Object>();

		objList.add(t.getUserid());
		objList.add(t.getItemid());
		objList.add(t.getOptionid());
		objList.add(t.getScore());
		objList.add(t.getTargetuserref());
		objList.add(t.getYearid());
		objList.add(t.getTargetclassuserref());
		objList.add(t.getClassid());

		return objList;
	}

	/**
	 * 添加
	 */
	public Integer save(AppraiseLogsInfo t) {
		if (t == null || t.getUserid() == null || t.getItemid() == null
				|| t.getOptionid() == null || t.getTargetuserref() == null
				|| t.getYearid() == null || t.getTargetidentitytype() == null
				|| t.getTargetclassuserref() != null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> paraList = this.getSaveSql(t, sqlbuilder);
		Object obj = this.executeSacle_PROC(sqlbuilder.toString(), paraList);
		if (obj != null && UtilTool.isNumber(obj.toString().trim()))
			return Integer.parseInt(obj.toString());
		return null;
	}

	/**
	 * 根据年份，年级，查询班主任总体得分(不带明细得分)
	 * 
	 * @param flag
	 *            1 查总分平均分 2 查单项平均分
	 * @param year
	 *            查询的年份
	 * @param grade
	 *            年级
	 * @param targetuserid
	 *            用户表示
	 * @return List<List<String>>
	 */
	public List<Map<String, Object>> getQueryBanStatice(int year,
			String userRef, PageResult presult) {

		if (year < 1) {
			// throw new Exception("查询班主任总体得分(不带明细得分) 参数year不能为空!请联系管理员!");
			return null;
		}
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL pj_query_stat_split(");
		List<Object> objList = new ArrayList<Object>();
		if (year > 0) {
			sqlbuilder.append("?,");
			objList.add(year);
		} else
			sqlbuilder.append("NULL,");
		if (userRef != null && userRef.length() > 0) {
			sqlbuilder.append("?,");
			objList.add(userRef.trim());
		} else
			sqlbuilder.append("NULL,");
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

		List<Map<String, Object>> ml = this.executeResultListMap_PROC(
				sqlbuilder.toString(), objList);
		return ml;
	}

	/**
	 * 根据年份，年级，查询班主任总体得分(不带明细得分)
	 * 
	 * @param flag
	 *            1 查总分平均分 2 查单项平均分
	 * @param year
	 *            查询的年份
	 * @param grade
	 *            年级
	 * @param targetuserid
	 *            用户表示
	 * @return List<List<String>>
	 */
	public List<Map<String, Object>> getQueryBanStatice(int year,
			String grades, String subjects, PageResult presult) {

		if (year < 1) {
			// throw new Exception("查询班主任总体得分(不带明细得分) 参数year不能为空!请联系管理员!");
			return null;
		}
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL pj_query_total_stat_split(");
		List<Object> objList = new ArrayList<Object>();
		if (year > 0) {
			sqlbuilder.append("?,");
			objList.add(year);
		} else
			sqlbuilder.append("NULL,");
		if (grades != null && grades.length() > 0) {
			sqlbuilder.append("?,");
			objList.add(grades.trim());
		} else
			sqlbuilder.append("NULL,");
		if (subjects != null && subjects.length() > 0) {
			sqlbuilder.append("?,");
			objList.add(subjects.trim());
		} else
			sqlbuilder.append("NULL,");
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
		return this.executeResultListMap_PROC(sqlbuilder.toString(), objList);
	}

	/**
	 * 根据用户表示得到评教项的对应值(柱形图)
	 * 
	 * @param targetidentitytype
	 *            1:班主任 2:任课教师 3:体育教师
	 * @param pjyear
	 *            年份
	 * @param targetuserid
	 *            用户的标识
	 * @return
	 */
	public List<List<String>> getQueryByTargetUserId(
			Integer targetidentitytype, String pjyear, BigDecimal targetuserid) {
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL pj_stat_pkg.queryStatIdenByTarUserId(");
		List<Object> objList = new ArrayList<Object>();
		if (pjyear != null && pjyear.trim().length() > 0) {
			sqlbuilder.append("?,");
			objList.add(pjyear);
		} else
			sqlbuilder.append("NULL,");

		if (targetuserid != null) {
			sqlbuilder.append("?,");
			objList.add(targetuserid);
		} else
			sqlbuilder.append("0,");

		targetidentitytype = targetidentitytype == null ? 1
				: targetidentitytype;
		sqlbuilder.append("?,");
		objList.add(targetidentitytype);
		sqlbuilder.append("?)}");

		return this.executeResult_PROC(sqlbuilder.toString(), objList, null,
				null, null);
	}

	// querystathisiden1bytaruserid
	/**
	 * 获取班主任的折线图数据(线形图)
	 * 
	 * @param targetidentitytype
	 *            1:班主任 2:任课教师 3:体育教师
	 * @param userid
	 *            用户的标识
	 */
	public List<List<String>> getQueryByTargetUserId(
			Integer targetidentitytype, BigDecimal userid) {
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL pj_stat_pkg.queryStatHisIdenByTarUserId(?,?,?)}");
		targetidentitytype = targetidentitytype == null ? 1
				: targetidentitytype;
		List<Object> objPara = new ArrayList<Object>();
		objPara.add(userid);
		objPara.add(targetidentitytype);

		return this.executeResult_PROC(sqlbuilder.toString(), objPara, null,
				null, null);
	}

	/**
	 * 查询体育教师的统计
	 * 
	 * @param isflag
	 *            (1：总分，平均分，2：详细情况)
	 * @param currentYear
	 *            当前的年份
	 * @param grade
	 *            年级
	 * @return
	 */
	public List<Map<String, Object>> getQueryTeaTiyuStatice(int flag,
			String currentYear, String grade) {
		if (currentYear == null || currentYear.length() < 1)
			return null;
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL pj_stat_pkg.queryNewStatIden3(?,?,");
		List<Object> objList = new ArrayList<Object>();
		objList.add(flag);
		objList.add(currentYear);
		if (grade == null || grade.trim().length() < 1)
			sqlbuilder.append("NULL,");
		else {
			sqlbuilder.append("?,");
			objList.add(grade.trim());
		}
		sqlbuilder.append("?)}");
		return this.executeResultListMap_PROC(sqlbuilder.toString(), objList);
	}

	// ///////////////////////任课教师

	/**
	 * 管理查询教师的统计页面
	 * 
	 * @param flag
	 *            1 查总分平均分 2 查单项平均分
	 * @param pjyear
	 *            年份
	 * @param grade
	 *            年级字符串 高一,高二,高三
	 * @param coursename
	 *            学科 物理,化学
	 * @param tealevel
	 *            职称 初级教师,中级教师
	 * @param teaage
	 *            教龄 1,2,3,7,8,9 特殊处理如果有22，则代表大于等于22年
	 * @param islesstea35age
	 *            是否青年教师 1:是，2：否
	 * @param isteares
	 *            是否教研组 1:是，2：否
	 * @param ispreparelessons
	 *            是否备课组 1:是，2：否
	 * @param isnoconflag
	 *            无条件的标记 1 无任何条件 （如果没有任何选项，则汇总每个人的所有班级的平均分数据。单独处理sql，暂未使用）
	 */
	public List<Map<String, Object>> getQueryTeacherStatice(BigDecimal flag,
			String pjyear, String grade, String coursename, String tealevel,
			String teaage, BigDecimal islesstea35age) {
		List<Object> objList = new ArrayList<Object>();
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL PJ_STAT_PKG.queryStatNewIden2(");
		// flag
		if (flag == null)
			sqlbuilder.append("1,");
		else {
			sqlbuilder.append("?,");
			objList.add(flag);
		}
		// pjyear
		if (pjyear == null)
			sqlbuilder.append("NULL,");
		else {
			sqlbuilder.append("?,");
			objList.add(pjyear.trim());
		}
		// grade
		if (grade == null)
			sqlbuilder.append("NULL,");
		else {
			sqlbuilder.append("?,");
			objList.add(grade.trim());
		}
		// coursename
		if (coursename == null)
			sqlbuilder.append("NULL,");
		else {
			sqlbuilder.append("?,");
			objList.add(coursename.trim());
		}
		// tealevel
		if (tealevel == null)
			sqlbuilder.append("NULL,");
		else {
			sqlbuilder.append("?,");
			objList.add(tealevel.trim());
		}
		// teaage
		if (teaage == null)
			sqlbuilder.append("NULL,");
		else {
			sqlbuilder.append("?,");
			objList.add(teaage.trim());
		}
		// islesstea35age
		if (islesstea35age == null)
			sqlbuilder.append("0,");
		else {
			sqlbuilder.append("?,");
			objList.add(islesstea35age);
		}
		sqlbuilder.append("?)}");
		return this.executeResultListMap_PROC(sqlbuilder.toString(), objList);
	}

	/**
	 * 获取年级组长查询数据
	 * 
	 * @param flag
	 *            1:平均分，统计 2:详细
	 * @param pjyear
	 *            年份
	 * @param deptid
	 *            查询的教研组部门表示REF
	 * @return List<List<String>>
	 */
	public List<Map<String, Object>> getQueryTeaManagerDate(Integer flag,
			String pjyear, BigDecimal deptid) {
		if (flag == null || deptid == null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL pj_stat_pkg.querytchres(?");
		List<Object> objList = new ArrayList<Object>();
		objList.add(flag);
		// pjyear
		if (pjyear == null)
			sqlbuilder.append(",NULL");
		else {
			sqlbuilder.append(",?");
			objList.add(pjyear);
		}
		// deptid
		if (deptid == null)
			sqlbuilder.append(",0");
		else {
			sqlbuilder.append(",?");
			objList.add(deptid);
		}
		// outDateTable
		sqlbuilder.append(",?)}");

		return this.executeResultListMap_PROC(sqlbuilder.toString(), objList);
	}

	/**
	 * 管理员查看教师统计(教研组)页面
	 * 
	 * @param flag
	 * @param pjyear
	 * @param studyperiods
	 * @param coursearray
	 * @return
	 */
	public List<Map<String, Object>> getQueryStatTchResGroup(Integer flag,
			String pjyear, String studyperiods, String coursearray) {
		if (flag == null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL PJ_STAT_PKG.queryStatTchResGroup(?");
		List<Object> objList = new ArrayList<Object>();
		objList.add(flag);
		if (pjyear == null)
			sqlbuilder.append(",NULL");
		else {
			sqlbuilder.append(",?");
			objList.add(pjyear);
		}
		if (studyperiods == null)
			sqlbuilder.append(",NULL");
		else {
			sqlbuilder.append(",?");
			objList.add(studyperiods);
		}
		if (coursearray == null)
			sqlbuilder.append(",NULL");
		else {
			sqlbuilder.append(",?");
			objList.add(coursearray);
		}
		sqlbuilder.append(",?)}");
		return this.executeResultListMap_PROC(sqlbuilder.toString(), objList);
	}

	/**
	 * 管理员查看备课组信息
	 * 
	 * @param flag
	 *            -- 1 查总分平均分 2 查单项平均分
	 * @param pjyear
	 *            年份
	 * @param studyperiods
	 *            --学段 高中,初中
	 * @param coursearray
	 *            --学科 物理,化学
	 * @param grade
	 *            --年级 高一,高二
	 * @return
	 */
	public List<Map<String, Object>> getQueryStatPrepareLessonGroup(
			Integer flag, String pjyear, String studyperiods,
			String coursearray, String grade) {
		if (flag == null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL PJ_STAT_PKG.queryStatPrepareLessonGroup(?");
		List<Object> objList = new ArrayList<Object>();
		objList.add(flag);
		if (pjyear == null)
			sqlbuilder.append(",NULL");
		else {
			sqlbuilder.append(",?");
			objList.add(pjyear);
		}
		if (studyperiods == null)
			sqlbuilder.append(",NULL");
		else {
			sqlbuilder.append(",?");
			objList.add(studyperiods);
		}
		if (coursearray == null)
			sqlbuilder.append(",NULL");
		else {
			sqlbuilder.append(",?");
			objList.add(coursearray);
		}
		if (grade == null)
			sqlbuilder.append(",NULL");
		else {
			sqlbuilder.append(",?");
			objList.add(grade.trim());
		}
		sqlbuilder.append(",?)}");
		return this.executeResultListMap_PROC(sqlbuilder.toString(), objList);
	}

	public List getQueryTeaTiyuStatice(int flag, String currentYear,
			String grade, BigDecimal userId) {
		if (currentYear == null || currentYear.length() < 1)
			return null;
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL pj_stat_pkg.queryStatIden3(?,?,");
		List<Object> objList = new ArrayList<Object>();
		objList.add(flag);
		objList.add(currentYear);
		if (grade == null || grade.trim().length() < 1)
			sqlbuilder.append("NULL,");
		else {
			sqlbuilder.append("?,");
			objList.add(grade.trim());
		}
		if (userId != null) {
			sqlbuilder.append("?,");
			objList.add(userId);
		} else
			sqlbuilder.append("0,");
		sqlbuilder.append("?)}");
		return this.executeResult_PROC(sqlbuilder.toString(), objList, null,
				null, null);
	}

	public List getQueryTeacherStatice(BigDecimal flag, String currentYear,
			String gradeStr, String coursename, String tealevel, String teaage,
			BigDecimal islesstea35age, BigDecimal isteares,
			BigDecimal ispreparelessons, BigDecimal isnoconflag,
			BigDecimal targetuserid) {
		List<Object> objList = new ArrayList<Object>();
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL PJ_STAT_PKG.queryStatIden2(");
		// flag
		if (flag == null)
			sqlbuilder.append("1,");
		else {
			sqlbuilder.append("?,");
			objList.add(flag);
		}
		// pjyear
		if (currentYear == null)
			sqlbuilder.append("NULL,");
		else {
			sqlbuilder.append("?,");
			objList.add(currentYear.trim());
		}
		// grade
		if (gradeStr == null)
			sqlbuilder.append("NULL,");
		else {
			sqlbuilder.append("?,");
			objList.add(gradeStr.trim());
		}
		// coursename
		if (coursename == null)
			sqlbuilder.append("NULL,");
		else {
			sqlbuilder.append("?,");
			objList.add(coursename.trim());
		}
		// tealevel
		if (tealevel == null)
			sqlbuilder.append("NULL,");
		else {
			sqlbuilder.append("?,");
			objList.add(tealevel.trim());
		}
		// teaage
		if (teaage == null)
			sqlbuilder.append("NULL,");
		else {
			sqlbuilder.append("?,");
			objList.add(teaage.trim());
		}
		// islesstea35age
		if (islesstea35age == null)
			sqlbuilder.append("0,");
		else {
			sqlbuilder.append("?,");
			objList.add(islesstea35age);
		}

		// isteares
		if (isteares == null)
			sqlbuilder.append("0,");
		else {
			sqlbuilder.append("?,");
			objList.add(isteares);
		}
		// ispreparelessons
		if (ispreparelessons == null)
			sqlbuilder.append("0,");
		else {
			sqlbuilder.append("?,");
			objList.add(ispreparelessons);
		}
		// isnoconflag
		if (isnoconflag == null)
			sqlbuilder.append("0,");
		else {
			sqlbuilder.append("?,");
			objList.add(isnoconflag);
		}
		// targetuserid
		if (targetuserid == null)
			sqlbuilder.append("0,");
		else {
			sqlbuilder.append("?,");
			objList.add(targetuserid);
		}
		sqlbuilder.append("?)}");
		return this.executeResult_PROC(sqlbuilder.toString(), objList, null,
				null, null);
	}

	/**
	 * 教研组组长查询
	 * 
	 * @param flag
	 *            1：总分平均分 2：详情
	 * @param currentYear
	 *            当前的年份
	 * @param deptid
	 *            部门标识
	 * @return
	 */
	public List getQueryTchResNew(BigDecimal flag, String currentYear,
			BigDecimal deptid) {
		List<Object> objList = new ArrayList<Object>();
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL PJ_STAT_PKG.queryTchResNew(");
		if (flag == null)
			sqlbuilder.append("1,");
		else {
			sqlbuilder.append("?,");
			objList.add(flag);
		}
		if (currentYear == null)
			sqlbuilder.append("NULL,");
		else {
			sqlbuilder.append("?,");
			objList.add(currentYear);
		}
		if (deptid == null)
			sqlbuilder.append("0,");
		else {
			sqlbuilder.append("?,");
			objList.add(deptid);
		}
		sqlbuilder.append("?)}");
		return this.executeResultListMap_PROC(sqlbuilder.toString(), objList);
	}

	public Boolean doSave(AppraiseLogsInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean doUpdate(AppraiseLogsInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean doDelete(AppraiseLogsInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getUpdateSql(AppraiseLogsInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getDeleteSql(AppraiseLogsInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		// TODO Auto-generated method stub
		return this.executeArrayQuery_PROC(sqlArrayList, objArrayList);
	}

	public void refreshPJStat() {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL pj_query_stat_by_pj_year(?)}");
		this.executeSacle_PROC(sqlbuilder.toString(), null);
	}

	/**
	 * 查询教师某班级的详情统计
	 * 
	 * @param isflag
	 *            (1：总分，平均分，2：详细情况)
	 * @param currentYear
	 *            当前的年份
	 * @param grade
	 *            年级
	 * @return
	 */
	public List<Map<String, Object>> getQueryTeaClassStatice(
			AppraiseLogsInfo ali) {
		if (ali == null || ali.getTargetuserref() == null
				|| ali.getClassid() == null || ali.getYearid() == null) {
			return null;
		}
		StringBuilder sqlbuilder = new StringBuilder(
				"{CALL pj_appraise_logs_info_proc_tch_count(?,?,?,?)}");
		List<Object> objList = new ArrayList<Object>();
		objList.add(ali.getTargetuserref());
		objList.add(ali.getClassid());
		objList.add(ali.getYearid());
		return this.executeResultListMap_PROC(sqlbuilder.toString(), objList);
	}
}
