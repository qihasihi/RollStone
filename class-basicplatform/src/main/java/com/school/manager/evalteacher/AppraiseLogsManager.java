package com.school.manager.evalteacher;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jxl.Sheet;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.evalteacher.IAppraiseLogsDAO;
import com.school.entity.evalteacher.AppraiseLogsInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.evalteacher.IAppraiseLogsManager;
import com.school.util.PageResult;
@Service
public class AppraiseLogsManager extends BaseManager<AppraiseLogsInfo>
		implements IAppraiseLogsManager {
	
	private IAppraiseLogsDAO<AppraiseLogsInfo> applogsdao;

	@Autowired
	@Qualifier("appraiseLogsDAO")
	public void setApplogsdao(IAppraiseLogsDAO<AppraiseLogsInfo> applogsdao) {
		this.applogsdao = applogsdao;
	}

	public BaseManager<AppraiseLogsInfo> getBaseManager() {
		// TODO Auto-generated method stub
		return this;
	}

	public int getCount(AppraiseLogsInfo model, boolean islike) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object[] getListWithPage(AppraiseLogsInfo model, StringBuilder sql,
			PageResult pageSut) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<AppraiseLogsInfo> getList(AppraiseLogsInfo t, PageResult presult) {
		// TODO Auto-generated method stub
		return applogsdao.getList(t, presult);
	}

	public List<Object> getSaveSql(AppraiseLogsInfo t, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return applogsdao.getSaveSql(t, sqlbuilder);
	}

	public Integer save(AppraiseLogsInfo t) {
		// TODO Auto-generated method stub
		return applogsdao.save(t);
	}

	public AppraiseLogsInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean doMoreProcedure(List<String> sqlStrList,
			List<List<Object>> objArrayList) {
		// TODO Auto-generated method stub
		return this.applogsdao.executeArray_SQL(sqlStrList, objArrayList);
	}
	
	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		// TODO Auto-generated method stub
		System.out.println("bbbbvvvvv");
		return this.applogsdao.executeArrayQuery_PROC(sqlArrayList, objArrayList);
	}

	public int getListNum(AppraiseLogsInfo t) {
		// TODO Auto-generated method stub
		return this.applogsdao.getListNum(t);
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
	 * @return List<List<String>>
	 */
	public List<Map<String,Object>> getQueryBanStatice(int year,
			String grades,String subjects,PageResult presult) {
		return this.applogsdao.getQueryBanStatice(year, grades,subjects,presult);
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
		return this.applogsdao.getQueryByTargetUserId(targetidentitytype,
				pjyear, targetuserid);
	}

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
		return this.applogsdao.getQueryByTargetUserId(targetidentitytype,
				userid);
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
	public  List<Map<String,Object>> getQueryTeaTiyuStatice(int isflag,
			String currentYear, String grade) {
		return this.applogsdao.getQueryTeaTiyuStatice(isflag, currentYear,
				grade);
	}

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
	 */
	public List<Map<String,Object>> getQueryTeacherStatice(BigDecimal flag,
			String pjyear, String grade, String coursename, String tealevel,
			String teaage, BigDecimal islesstea35age) {
		return this.applogsdao.getQueryTeacherStatice(flag, pjyear, grade,
				coursename, tealevel, teaage, islesstea35age);
	}
	/**
	 * ��ȡ�꼶�鳤��ѯ����
	 * @param flag  1:ƽ���֣�ͳ��   2:��ϸ
	 * @param pjyear   ���
	 * @param deptid   ��ѯ�Ľ����鲿�ű�ʾREF
	 * @return  List<List<String>>
	 */
	public List<Map<String,Object>>  getQueryTeaManagerDate(Integer flag,String pjyear,BigDecimal deptid){
		if(flag==null)
			return null;
		return this.applogsdao.getQueryTeaManagerDate(flag, pjyear, deptid);
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
	public  List<Map<String,Object>>   getQueryStatTchResGroup(Integer flag,
			String pjyear, String studyperiods, String coursearray){
		return this.applogsdao.getQueryStatTchResGroup(flag, pjyear, studyperiods, coursearray);
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
	public List<Map<String,Object>>  getQueryStatPrepareLessonGroup(Integer flag,
			String pjyear, String studyperiods, String coursearray, String grade){
		return this.applogsdao.getQueryStatPrepareLessonGroup(flag, pjyear, studyperiods, coursearray, grade);
	}
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
	public List getQueryTeacherStatice(BigDecimal flag,
			String currentYear, String gradeStr, String coursename,
			String tealevel, String teaage, BigDecimal islesstea35age,
			BigDecimal isteares, BigDecimal ispreparelessons, BigDecimal isnoconflag,
			BigDecimal targetuserid) {
		// TODO Auto-generated method stub
		return this.applogsdao.getQueryTeacherStatice(flag, currentYear, gradeStr,
				coursename, tealevel, teaage, islesstea35age,isteares,ispreparelessons,isnoconflag,targetuserid);
	}

	public List getQueryTeaTiyuStatice(int i, String currentYear, String grade,
			BigDecimal userId) {
		// TODO Auto-generated method stub
		return  this.applogsdao.getQueryTeaTiyuStatice(i,currentYear,grade,userId);
	}
	
	/**
	 * �������鳤��ѯ
	 * @param flag        1���ܷ�ƽ����   2������
	 * @param currentYear ��ǰ�����
	 * @param deptid   ���ű�ʶ
	 * @return
	 */
	public List getQueryTchResNew(BigDecimal flag,String currentYear,BigDecimal deptid){
		return this.applogsdao.getQueryTchResNew(flag, currentYear, deptid);
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

	@Override
	protected ICommonDAO<AppraiseLogsInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean doSave(AppraiseLogsInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean doUpdate(AppraiseLogsInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean doDelete(AppraiseLogsInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.applogsdao.getNextId();
	}
	

	public void refreshPJStat() {
		// TODO Auto-generated method stub
		this.applogsdao.refreshPJStat();
	}

	public List<Map<String, Object>> getQueryBanStatice(int year,
			String userRef, PageResult presult) {
		// TODO Auto-generated method stub
		return this.applogsdao.getQueryBanStatice(year, userRef, presult);
	}

	public List<Map<String, Object>> getQueryTeaClassStatice(
			AppraiseLogsInfo ali) {
		// TODO Auto-generated method stub
		return this.applogsdao.getQueryTeaClassStatice(ali);
	}
}
