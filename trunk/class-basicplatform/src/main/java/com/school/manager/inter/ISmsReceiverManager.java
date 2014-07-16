
package   com.school.manager.inter ;

import java.util.List;

import com.school.entity.SmsReceiver ;
import com.school.manager.base.IBaseManager;

public interface ISmsReceiverManager  extends IBaseManager<SmsReceiver > { 
	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList);

} 
