
package  com.school.manager.inter;

import java.util.List;

import com.school.entity.ClassInfo;
import com.school.manager.base.IBaseManager;

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
} 
