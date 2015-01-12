
package  com.school.manager.inter.teachpaltform.interactive;

import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface ITpTopicThemeManager  extends IBaseManager<TpTopicThemeInfo> {
    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @return
     */
    public void getSynchroSql(TpTopicThemeInfo entity,List<String> sqlArrayList,List<List<Object>> objArrayList);


}
