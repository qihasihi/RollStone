
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TpOperateInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

import java.util.List;

public interface ITpOperateManager  extends IBaseManager<TpOperateInfo> {
    /**
     * �õ�������Ҫ���µĲ�����¼
     * @param ftime  �ϴθ��µ�ʱ��
     * @param presult ��ҳ
     * @return
     */
    public List<TpOperateInfo> getSynchroList(String ftime,PageResult presult);
}
