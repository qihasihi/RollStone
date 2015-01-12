
package  com.school.manager.inter.teachpaltform.interactive;

import com.school.entity.teachpaltform.interactive.TpThemeReplyInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

import java.util.List;

public interface ITpThemeReplyManager  extends IBaseManager<TpThemeReplyInfo> {

    public List<TpThemeReplyInfo> getListByThemeIdStr(final String themeidStr,final Integer searchType, PageResult presult);
}
