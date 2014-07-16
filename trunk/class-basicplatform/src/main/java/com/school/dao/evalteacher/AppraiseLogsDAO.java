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
 * ������Ŀ
 * 
 * @author zhusx
 * 
 */
@Component
public class AppraiseLogsDAO extends CommonDAO<AppraiseLogsInfo> implements
		IAppraiseLogsDAO<AppraiseLogsInfo> {

	/**
	 * ��ѯ������־
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
	 * ��ѯ����
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
	 * �õ�������۵�SQL
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
	 * ���
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
	 * ������ݣ��꼶����ѯ����������÷�(������ϸ�÷�)
	 * 
	 * @param flag
	 *            1 ���ܷ�ƽ���� 2 �鵥��ƽ����
	 * @param year
	 *            ��ѯ�����
	 * @param grade
	 *            �꼶
	 * @param targetuserid
	 *            �û���ʾ
	 * @return List<List<String>>
	 */
	public List<Map<String, Object>> getQueryBanStatice(int year,
			String userRef, PageResult presult) {

		if (year < 1) {
			// throw new Exception("��ѯ����������÷�(������ϸ�÷�) ����year����Ϊ��!����ϵ����Ա!");
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
	 * ������ݣ��꼶����ѯ����������÷�(������ϸ�÷�)
	 * 
	 * @param flag
	 *            1 ���ܷ�ƽ���� 2 �鵥��ƽ����
	 * @param year
	 *            ��ѯ�����
	 * @param grade
	 *            �꼶
	 * @param targetuserid
	 *            �û���ʾ
	 * @return List<List<String>>
	 */
	public List<Map<String, Object>> getQueryBanStatice(int year,
			String grades, String subjects, PageResult presult) {

		if (year < 1) {
			// throw new Exception("��ѯ����������÷�(������ϸ�÷�) ����year����Ϊ��!����ϵ����Ա!");
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
	 * �����û���ʾ�õ�������Ķ�Ӧֵ(����ͼ)
	 * 
	 * @param targetidentitytype
	 *            1:������ 2:�ον�ʦ 3:������ʦ
	 * @param pjyear
	 *            ���
	 * @param targetuserid
	 *            �û��ı�ʶ
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
	 * ��ȡ�����ε�����ͼ����(����ͼ)
	 * 
	 * @param targetidentitytype
	 *            1:������ 2:�ον�ʦ 3:������ʦ
	 * @param userid
	 *            �û��ı�ʶ
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
	 * ��ѯ������ʦ��ͳ��
	 * 
	 * @param isflag
	 *            (1���ܷ֣�ƽ���֣�2����ϸ���)
	 * @param currentYear
	 *            ��ǰ�����
	 * @param grade
	 *            �꼶
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

	// ///////////////////////�ον�ʦ

	/**
	 * �����ѯ��ʦ��ͳ��ҳ��
	 * 
	 * @param flag
	 *            1 ���ܷ�ƽ���� 2 �鵥��ƽ����
	 * @param pjyear
	 *            ���
	 * @param grade
	 *            �꼶�ַ��� ��һ,�߶�,����
	 * @param coursename
	 *            ѧ�� ����,��ѧ
	 * @param tealevel
	 *            ְ�� ������ʦ,�м���ʦ
	 * @param teaage
	 *            ���� 1,2,3,7,8,9 ���⴦�������22���������ڵ���22��
	 * @param islesstea35age
	 *            �Ƿ������ʦ 1:�ǣ�2����
	 * @param isteares
	 *            �Ƿ������ 1:�ǣ�2����
	 * @param ispreparelessons
	 *            �Ƿ񱸿��� 1:�ǣ�2����
	 * @param isnoconflag
	 *            �������ı�� 1 ���κ����� �����û���κ�ѡ������ÿ���˵����а༶��ƽ�������ݡ���������sql����δʹ�ã�
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
	 * ��ȡ�꼶�鳤��ѯ����
	 * 
	 * @param flag
	 *            1:ƽ���֣�ͳ�� 2:��ϸ
	 * @param pjyear
	 *            ���
	 * @param deptid
	 *            ��ѯ�Ľ����鲿�ű�ʾREF
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
	 * ����Ա�鿴��ʦͳ��(������)ҳ��
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
	 * ����Ա�鿴��������Ϣ
	 * 
	 * @param flag
	 *            -- 1 ���ܷ�ƽ���� 2 �鵥��ƽ����
	 * @param pjyear
	 *            ���
	 * @param studyperiods
	 *            --ѧ�� ����,����
	 * @param coursearray
	 *            --ѧ�� ����,��ѧ
	 * @param grade
	 *            --�꼶 ��һ,�߶�
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
	 * �������鳤��ѯ
	 * 
	 * @param flag
	 *            1���ܷ�ƽ���� 2������
	 * @param currentYear
	 *            ��ǰ�����
	 * @param deptid
	 *            ���ű�ʶ
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
	 * ��ѯ��ʦĳ�༶������ͳ��
	 * 
	 * @param isflag
	 *            (1���ܷ֣�ƽ���֣�2����ϸ���)
	 * @param currentYear
	 *            ��ǰ�����
	 * @param grade
	 *            �꼶
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
