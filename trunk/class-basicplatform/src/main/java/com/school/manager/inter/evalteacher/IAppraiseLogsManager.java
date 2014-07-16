package com.school.manager.inter.evalteacher;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.school.entity.evalteacher.AppraiseLogsInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;


public interface IAppraiseLogsManager extends IBaseManager<AppraiseLogsInfo>  {

	/**
	 * 添加
	 */
	public abstract Integer save(AppraiseLogsInfo t);	
	
	/**
	 * 查询
	 */
	public abstract List<AppraiseLogsInfo> getList(AppraiseLogsInfo t,PageResult presult);
	
	public List<Map<String,Object>>  getQueryBanStatice(int year,String userRef,PageResult presult);
	/**
	 * 获取添加的SQL语句
	 * @param t
	 * @return
	 */
	public List<Object> getSaveSql(AppraiseLogsInfo t,StringBuilder sqlbuilder);

	public abstract boolean doMoreProcedure(List<String> sqlStrList,
			List<List<Object>> objArrayList);
	
	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList);
	/**
	 * 查询数量
	 * @param t
	 * @return
	 */
	public int getListNum(AppraiseLogsInfo t);
	/**
	 * 根据年份，年级，查询班主任总体得分(不带明细得分)
	 * 
	 * @param flag
	 *            1 查总分平均分 2 查单项平均分
	 * @param year
	 *            查询的年份
	 * @param grade
	 *            年级
	 * @param targetuserid 用户表示
	 * @return List<List<String>>
	 */
	public List<Map<String,Object>> getQueryBanStatice(int year,String grades,String subjects,PageResult presult);
	/**
	 * 根据用户表示得到评教项的对应值(柱形图)
	 *  @param targetidentitytype 1:班主任   2:任课教师  3:体育教师
	 * @param pjyear      年份
	 * @param targetuserid  用户的标识
	 * @return
	 */
	public List<List<String>> getQueryByTargetUserId(Integer targetidentitytype
			,String pjyear,BigDecimal targetuserid);
	/**
	 * 获取班主任的折线图数据(线形图)
	 * @param targetidentitytype 1:班主任   2:任课教师  3:体育教师
	 * @param userid  用户的标识
	 */
	public List<List<String>> getQueryByTargetUserId(Integer targetidentitytype,BigDecimal userid);
	
	/**
	 * 查询体育教师的统计
	 * @param isflag   (1：总分，平均分，2：详细情况)
	 * @param currentYear  当前的年份
	 * @param grade     年级
	 * @param targetuserid 用户表示
	 * @return
	 */
	public abstract List<Map<String, Object>> getQueryTeaTiyuStatice(int isflag,
			String currentYear, String grade);
	

	/**
	 * 管理查询教师的统计页面
	 * @param flag   1 查总分平均分 2 查单项平均分
	 * @param pjyear 年份
	 * @param grade 年级字符串  高一,高二,高三
	 * @param coursename 	学科  物理,化学
	 * @param tealevel 职称 初级教师,中级教师
	 * @param teaage	教龄 1,2,3,7,8,9  特殊处理如果有22，则代表大于等于22年
	 * @param islesstea35age 是否青年教师 1:是，2：否	
	 */
	public List<Map<String, Object>> getQueryTeacherStatice(BigDecimal flag,String pjyear,
			String grade,String coursename,String tealevel,String teaage,BigDecimal islesstea35age
			);
	
	/**
	 * 获取年级组长查询数据
	 * @param flag  1:平均分，统计   2:详细
	 * @param pjyear   年份
	 * @param deptid   查询的教研组部门表示REF
	 * @return  List<List<String>>
	 */
	public List<Map<String, Object>> getQueryTeaManagerDate(Integer flag,String pjyear,BigDecimal deptid);
	
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
			String pjyear, String studyperiods, String coursearray);

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
	public List<Map<String, Object>> getQueryStatPrepareLessonGroup(Integer flag,
			String pjyear, String studyperiods, String coursearray, String grade);
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
	public List getQueryTeacherStatice(BigDecimal flag,
			String currentYear, String gradeStr, String coursename,
			String tealevel, String teaage, BigDecimal islesstea35age,
			BigDecimal isteares, BigDecimal ispreparelessons, BigDecimal isnoconflag,
			BigDecimal targetuserid) ;

	public abstract List getQueryTeaTiyuStatice(int i, String currentYear,
			String grade, BigDecimal userId);
	
	/**
	 * 教研组组长查询
	 * @param flag        1：总分平均分   2：详情
	 * @param currentYear 当前的年份
	 * @param deptid   部门标识
	 * @return
	 */
	public List getQueryTchResNew(BigDecimal flag,String currentYear,BigDecimal deptid);
	
	/**
	 * 刷新评教统计数据
	 */
	public void refreshPJStat();
	
	public List<Map<String, Object>> getQueryTeaClassStatice(AppraiseLogsInfo ali);
}