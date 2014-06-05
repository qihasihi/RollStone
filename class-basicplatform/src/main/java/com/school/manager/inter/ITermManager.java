
package  com.school.manager.inter;

import java.util.List;
import java.util.Map;

import com.school.entity.TermInfo;
import com.school.manager.base.IBaseManager;

public interface ITermManager  extends IBaseManager<TermInfo> { 
	public TermInfo getMaxIdTerm();
	public TermInfo getMaxIdTerm(Boolean flag);
	List<TermInfo>getAvailableTermList();
	public TermInfo getAutoTerm();
	public List<Map<String,Object>> getYearTerm();
    public Boolean InitTerm();
} 
