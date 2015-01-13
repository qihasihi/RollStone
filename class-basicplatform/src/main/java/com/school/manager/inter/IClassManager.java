
package  com.school.manager.inter;

import com.school.entity.AdminPerformance;
import com.school.entity.ClassInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

import java.util.List;
import java.util.Map;

public interface IClassManager  extends IBaseManager<ClassInfo> {
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
    public int getTotalClass(int schoolId, String year, int from) ;

    public List<ClassInfo> getIm113List(ClassInfo cls, PageResult presult);

    public List<ClassInfo> getClassByGradeTerm(int gradeId,int termId);

    public List<ClassInfo> getAllTerm();

    /**
     * ����Ա��ѯ�༶����ͳ��
     * */
    public List<AdminPerformance> getAdminPerformance(ClassInfo obj,PageResult presult);

    /**
     * ����Ա��ѯ��ʦ�༶����ͳ��,��ȡ�༶
     * */
    public List<ClassInfo> getAdminPerformanceTeaClass(Integer schoolid,Integer gradeid,Integer subjectid);

    /**
     * ����Ա��ѯѧ���༶����ͳ��,��ȡ�༶
     * */
    public List<ClassInfo> getAdminPerformanceStuClass(Integer schoolid,Integer gradeid,Integer subjectid);

    /**
     * ����Ա��ѯ�༶ѧ������ͳ��
     * */
    public List<List<String>> getAdminPerformanceStu(ClassInfo obj,PageResult presult);

    /**
     * ����Ա��ѯѧ������ͳ�ƣ���ȡ��ͷ
     * */
    public List<Map<String,Object>> getAdminPerformanceStuCol(ClassInfo obj);
}
