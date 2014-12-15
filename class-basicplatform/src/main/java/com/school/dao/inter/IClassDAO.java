
package com.school.dao.inter;

import com.school.dao.base.ICommonDAO;
import com.school.entity.ClassInfo;
import com.school.util.PageResult;

import java.util.List;

public interface  IClassDAO extends ICommonDAO<ClassInfo>{
    /**
     * ����UserRef,YEAR,PAttern�õ��༶
     * @param userref
     * @param year
     * @param pattern
     * @return
     */
    public List<ClassInfo> getClassByUserYearPattern(String userref,String year,String pattern);

    /**
     * ��������
     * @param year
     * @return
     */
    public Boolean doClassLevelUp(String year,Integer dcschoolid);

    /**
     * �õ����»���ӵ�SQL
     * @param obj;
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSaveOrUpdateSql(ClassInfo obj, StringBuilder sqlbuilder);

    /**
     * �õ��ѽ����İ༶����
     * @param schoolId ��Уid
     * @param year ѧ���ֵ
     * @return ���еİ༶����
     */
    public int getTotalClass(int schoolId, String year, int from);

    public List<ClassInfo> getIm113List(ClassInfo cls, PageResult presult);
}
