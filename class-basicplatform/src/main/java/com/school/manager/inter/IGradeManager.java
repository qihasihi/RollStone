
package  com.school.manager.inter;

import com.school.entity.GradeInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface IGradeManager  extends IBaseManager<GradeInfo> {

    public List<GradeInfo> getTchGradeList(Integer userid,String year);

    /**
     * ����Ա��ѯ��ʦ����ͳ�ƣ���ȡ�꼶
     * */
    public List<GradeInfo> getAdminPerformanceTeaGrade(Integer schoolid);

    /**
     * ����Ա��ѯѧ������ͳ�ƣ���ȡ�꼶
     * */
    public List<GradeInfo> getAdminPerformanceStuGrade(Integer schoolid);
} 
