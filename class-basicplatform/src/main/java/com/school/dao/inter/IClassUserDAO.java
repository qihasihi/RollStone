
package com.school.dao.inter;

import java.util.List;
import java.util.Map;

import com.school.dao.base.ICommonDAO;
import com.school.entity.ClassUser;
import com.school.util.PageResult;

public interface  IClassUserDAO extends ICommonDAO<ClassUser>{

	/**
	 * 
	 * @param tchUserRef
	 * @param stuUserRef
	 * @param year
	 * @param clsisflag  班级状态：1：启用   2：禁用
	 * @return
	 */
	public List<ClassUser> getClassUserByTchAndStu(String tchUserRef,String stuUserRef,String year,Integer clsisflag);
	/**
	 * 得到论题下的学生
	 * @param clsUser
	 * @param presult
	 * @return
	 */
	public List<ClassUser> getThemeList(ClassUser clsUser, PageResult presult);
	
	/**
	 * 调班查询学生。
	 * @param relationType
	 * @param year
	 * @param clsid
	 * @return
	 */
	public List<Map<String,Object>> getClassUserWithTiaoban(String relationType,String year,String clsid);

    /**
     * 根据老师id和学年获取老师在学年所教授的班级信息。
     * @param userid
     * @param year
     * @return
     */
    public List<ClassUser> getListByTchYear(String userid,String year);
    /**
     * 资源上传，得到Grade，subjectid
     * @param userref
     * @param relationType
     * @param year
     * @param gradeid
     * @return
     */
    public List<Map<String,Object>>  getClassUserTeacherBy(String userref,String relationType,String year,Integer gradeid);

    /**
     * 是否是任课班主任
     * @param userid
     * @param classid
     * @return
     */
    public Integer isTeachingBanZhuRen(String userid,Integer classid);
}
