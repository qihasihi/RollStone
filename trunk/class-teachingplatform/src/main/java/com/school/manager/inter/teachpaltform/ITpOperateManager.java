
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TpOperateInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

import java.util.List;

public interface ITpOperateManager  extends IBaseManager<TpOperateInfo> {
    /**
     * 得到本次需要更新的操作记录
     * @param ftime  上次更新的时间
     * @param presult 分页
     * @return
     */
    public List<TpOperateInfo> getSynchroList(String ftime,PageResult presult);
}
