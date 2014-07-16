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
	 * 根据年份，年级，查询班主任总体得分(不带明细得分)
	 * 
	 * @param flag
	 *            1 查总分平均分 2 查单项平均分
	 * @param year
	 *            查询的年份
	 * @param grade
	 *            年级
	 * @return List<List<String>>
	 */
	public List<Map<String,Object>> getQueryBanStatice(int year,
			String grades,String subjects,PageResult presult) {
		return this.applogsdao.getQueryBanStatice(year, grades,subjects,presult);
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
		return this.applogsdao.getQueryByTargetUserId(targetidentitytype,
				pjyear, targetuserid);
	}

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
		return this.applogsdao.getQueryByTargetUserId(targetidentitytype,
				userid);
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
	public  List<Map<String,Object>> getQueryTeaTiyuStatice(int isflag,
			String currentYear, String grade) {
		return this.applogsdao.getQueryTeaTiyuStatice(isflag, currentYear,
				grade);
	}

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
	 */
	public List<Map<String,Object>> getQueryTeacherStatice(BigDecimal flag,
			String pjyear, String grade, String coursename, String tealevel,
			String teaage, BigDecimal islesstea35age) {
		return this.applogsdao.getQueryTeacherStatice(flag, pjyear, grade,
				coursename, tealevel, teaage, islesstea35age);
	}
	/**
	 * 获取年级组长查询数据
	 * @param flag  1:平均分，统计   2:详细
	 * @param pjyear   年份
	 * @param deptid   查询的教研组部门表示REF
	 * @return  List<List<String>>
	 */
	public List<Map<String,Object>>  getQueryTeaManagerDate(Integer flag,String pjyear,BigDecimal deptid){
		if(flag==null)
			return null;
		return this.applogsdao.getQueryTeaManagerDate(flag, pjyear, deptid);
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
	public  List<Map<String,Object>>   getQueryStatTchResGroup(Integer flag,
			String pjyear, String studyperiods, String coursearray){
		return this.applogsdao.getQueryStatTchResGroup(flag, pjyear, studyperiods, coursearray);
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
	public List<Map<String,Object>>  getQueryStatPrepareLessonGroup(Integer flag,
			String pjyear, String studyperiods, String coursearray, String grade){
		return this.applogsdao.getQueryStatPrepareLessonGroup(flag, pjyear, studyperiods, coursearray, grade);
	}
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
	 * 教研组组长查询
	 * @param flag        1：总分平均分   2：详情
	 * @param currentYear 当前的年份
	 * @param deptid   部门标识
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
