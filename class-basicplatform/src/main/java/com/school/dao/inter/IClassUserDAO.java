
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
	 * @param clsisflag  �༶״̬��1������   2������
	 * @return
	 */
	public List<ClassUser> getClassUserByTchAndStu(String tchUserRef,String stuUserRef,String year,Integer clsisflag);
	/**
	 * �õ������µ�ѧ��
	 * @param clsUser
	 * @param presult
	 * @return
	 */
	public List<ClassUser> getThemeList(ClassUser clsUser, PageResult presult);
	
	/**
	 * �����ѯѧ����
	 * @param relationType
	 * @param year
	 * @param clsid
	 * @return
	 */
	public List<Map<String,Object>> getClassUserWithTiaoban(String relationType,String year,String clsid);

    /**
     * ������ʦid��ѧ���ȡ��ʦ��ѧ�������ڵİ༶��Ϣ��
     * @param userid
     * @param year
     * @return
     */
    public List<ClassUser> getListByTchYear(String userid,String year);
    /**
     * ��Դ�ϴ����õ�Grade��subjectid
     * @param userref
     * @param relationType
     * @param year
     * @param gradeid
     * @return
     */
    public List<Map<String,Object>>  getClassUserTeacherBy(String userref,String relationType,String year,Integer gradeid);

    /**
     * �Ƿ����οΰ�����
     * @param userid
     * @param classid
     * @return
     */
    public Integer isTeachingBanZhuRen(String userid,Integer classid);
}
