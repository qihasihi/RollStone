
package  com.school.manager.inter.teachpaltform.interactive;

import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface ITpTopicThemeManager  extends IBaseManager<TpTopicThemeInfo> {
    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @return
     */
    public void getSynchroSql(TpTopicThemeInfo entity,List<String> sqlArrayList,List<List<Object>> objArrayList);


}
