
package com.school.dao.inter;

import com.school.dao.base.ICommonDAO;
import com.school.entity.ClassInfo;
import com.school.util.PageResult;

import java.util.List;

public interface  IClassDAO extends ICommonDAO<ClassInfo>{
    /**
     * 根据UserRef,YEAR,PAttern得到班级
     * @param userref
     * @param year
     * @param pattern
     * @return
     */
    public List<ClassInfo> getClassByUserYearPattern(String userref,String year,String pattern);

    /**
     * 升级操作
     * @param year
     * @return
     */
    public Boolean doClassLevelUp(String year,Integer dcschoolid);

    /**
     * 得到更新或添加的SQL
     * @param obj;
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSaveOrUpdateSql(ClassInfo obj, StringBuilder sqlbuilder);

    /**
     * 得到已建立的班级数量
     * @param schoolId 分校id
     * @param year 学年的值
     * @return 已有的班级数量
     */
    public int getTotalClass(int schoolId, String year, int from);

    public List<ClassInfo> getIm113List(ClassInfo cls, PageResult presult);
}
