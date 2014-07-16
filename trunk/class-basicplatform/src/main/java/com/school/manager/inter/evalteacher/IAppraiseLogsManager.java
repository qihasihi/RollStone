package com.school.manager.inter.evalteacher;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.school.entity.evalteacher.AppraiseLogsInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;


public interface IAppraiseLogsManager extends IBaseManager<AppraiseLogsInfo>  {

	/**
	 * ���
	 */
	public abstract Integer save(AppraiseLogsInfo t);	
	
	/**
	 * ��ѯ
	 */
	public abstract List<AppraiseLogsInfo> getList(AppraiseLogsInfo t,PageResult presult);
	
	public List<Map<String,Object>>  getQueryBanStatice(int year,String userRef,PageResult presult);
	/**
	 * ��ȡ��ӵ�SQL���
	 * @param t
	 * @return
	 */
	public List<Object> getSaveSql(AppraiseLogsInfo t,StringBuilder sqlbuilder);

	public abstract boolean doMoreProcedure(List<String> sqlStrList,
			List<List<Object>> objArrayList);
	
	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList);
	/**
	 * ��ѯ����
	 * @param t
	 * @return
	 */
	public int getListNum(AppraiseLogsInfo t);
	/**
	 * ������ݣ��꼶����ѯ����������÷�(������ϸ�÷�)
	 * 
	 * @param flag
	 *            1 ���ܷ�ƽ���� 2 �鵥��ƽ����
	 * @param year
	 *            ��ѯ�����
	 * @param grade
	 *            �꼶
	 * @param targetuserid �û���ʾ
	 * @return List<List<String>>
	 */
	public List<Map<String,Object>> getQueryBanStatice(int year,String grades,String subjects,PageResult presult);
	/**
	 * �����û���ʾ�õ�������Ķ�Ӧֵ(����ͼ)
	 *  @param targetidentitytype 1:������   2:�ον�ʦ  3:������ʦ
	 * @param pjyear      ���
	 * @param targetuserid  �û��ı�ʶ
	 * @return
	 */
	public List<List<String>> getQueryByTargetUserId(Integer targetidentitytype
			,String pjyear,BigDecimal targetuserid);
	/**
	 * ��ȡ�����ε�����ͼ����(����ͼ)
	 * @param targetidentitytype 1:������   2:�ον�ʦ  3:������ʦ
	 * @param userid  �û��ı�ʶ
	 */
	public List<List<String>> getQueryByTargetUserId(Integer targetidentitytype,BigDecimal userid);
	
	/**
	 * ��ѯ������ʦ��ͳ��
	 * @param isflag   (1���ܷ֣�ƽ���֣�2����ϸ���)
	 * @param currentYear  ��ǰ�����
	 * @param grade     �꼶
	 * @param targetuserid �û���ʾ
	 * @return
	 */
	public abstract List<Map<String, Object>> getQueryTeaTiyuStatice(int isflag,
			String currentYear, String grade);
	

	/**
	 * �����ѯ��ʦ��ͳ��ҳ��
	 * @param flag   1 ���ܷ�ƽ���� 2 �鵥��ƽ����
	 * @param pjyear ���
	 * @param grade �꼶�ַ���  ��һ,�߶�,����
	 * @param coursename 	ѧ��  ����,��ѧ
	 * @param tealevel ְ�� ������ʦ,�м���ʦ
	 * @param teaage	���� 1,2,3,7,8,9  ���⴦�������22���������ڵ���22��
	 * @param islesstea35age �Ƿ������ʦ 1:�ǣ�2����	
	 */
	public List<Map<String, Object>> getQueryTeacherStatice(BigDecimal flag,String pjyear,
			String grade,String coursename,String tealevel,String teaage,BigDecimal islesstea35age
			);
	
	/**
	 * ��ȡ�꼶�鳤��ѯ����
	 * @param flag  1:ƽ���֣�ͳ��   2:��ϸ
	 * @param pjyear   ���
	 * @param deptid   ��ѯ�Ľ����鲿�ű�ʾREF
	 * @return  List<List<String>>
	 */
	public List<Map<String, Object>> getQueryTeaManagerDate(Integer flag,String pjyear,BigDecimal deptid);
	
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
			String pjyear, String studyperiods, String coursearray);

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
	public List<Map<String, Object>> getQueryStatPrepareLessonGroup(Integer flag,
			String pjyear, String studyperiods, String coursearray, String grade);
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
			BigDecimal targetuserid) ;

	public abstract List getQueryTeaTiyuStatice(int i, String currentYear,
			String grade, BigDecimal userId);
	
	/**
	 * �������鳤��ѯ
	 * @param flag        1���ܷ�ƽ����   2������
	 * @param currentYear ��ǰ�����
	 * @param deptid   ���ű�ʶ
	 * @return
	 */
	public List getQueryTchResNew(BigDecimal flag,String currentYear,BigDecimal deptid);
	
	/**
	 * ˢ������ͳ������
	 */
	public void refreshPJStat();
	
	public List<Map<String, Object>> getQueryTeaClassStatice(AppraiseLogsInfo ali);
}