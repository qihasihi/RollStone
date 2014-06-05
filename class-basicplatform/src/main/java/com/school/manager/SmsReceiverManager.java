
package   com.school.manager ;

import java.util.List;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.ISmsReceiverDAO;
import com.school.util.PageResult;
import com.school.entity.SmsReceiver ;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.ISmsReceiverManager;

@Service
public class   SmsReceiverManager  extends BaseManager<SmsReceiver > implements ISmsReceiverManager  { 
	
	@Autowired
	@Qualifier("smsReceiverDAO")
	private  ISmsReceiverDAO  smsreceiverdao;

	public ISmsReceiverDAO getSmsreceiverdao() {
		return smsreceiverdao;
	}

	public void setSmsreceiverdao(ISmsReceiverDAO smsreceiverdao) {
		this.smsreceiverdao = smsreceiverdao;
	}

	public List<Object> getSaveSql(SmsReceiver obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return smsreceiverdao.getSaveSql(obj, sqlbuilder);
	}

	public List<Object> getUpdateSql(SmsReceiver obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getDeleteSql(SmsReceiver obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return smsreceiverdao.getDeleteSql(obj, sqlbuilder);
	}

	public SmsReceiver getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<SmsReceiver> getBaseDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean doSave(SmsReceiver obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean doUpdate(SmsReceiver obj) {
		// TODO Auto-generated method stub
		return smsreceiverdao.doUpdate(obj);
	}

	@Override
	public Boolean doDelete(SmsReceiver obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmsReceiver> getList(SmsReceiver obj, PageResult presult) {
		// TODO Auto-generated method stub
		return smsreceiverdao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.smsreceiverdao.getNextId();
	}
	
	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		// TODO Auto-generated method stub
		return smsreceiverdao.executeArrayQuery_PROC(sqlArrayList, objArrayList);
	}
}

