
package  com.school.manager.inter;

import com.school.entity.GradeInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface IGradeManager  extends IBaseManager<GradeInfo> {

    public List<GradeInfo> getTchGradeList(Integer userid,String year);
} 
