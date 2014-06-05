package    com.school.dao ;


import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter. ISmsReceiverDAO ;
import com.school.entity.SmsReceiver ;
import com.school.util.PageResult;

@Component  
public class SmsReceiverDAO extends CommonDAO<SmsReceiver > implements  ISmsReceiverDAO  {

	public Boolean doDelete(SmsReceiver  obj) {
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(obj, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.executeArray_SQL(sqlArrayList, objArrayList);
	}

	public Boolean doSave(SmsReceiver  obj) {
		if (obj == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doUpdate(SmsReceiver  obj) {
		if (obj == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public List<Object> getDeleteSql(SmsReceiver  obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call sms_receiver_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRef());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<SmsReceiver> getList(SmsReceiver  obj, PageResult presult) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL sms_receiver_proc_search_split(");
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL,NULL,NULL,NULL");
		else{
			if(obj.getRef()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getRef());
			}else
				sqlbuilder.append("NULL,");
			
			if(obj.getSmstitle()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getSmstitle());
			}else
				sqlbuilder.append("NULL,");
			
			if(obj.getReceiverid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getReceiverid());
			}else
				sqlbuilder.append("NULL,");
			
			if(obj.getStatus()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getStatus());
			}else
				sqlbuilder.append("NULL,");
		}
		if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
			sqlbuilder.append("?,?,");
			objList.add(presult.getPageNo());
			objList.add(presult.getPageSize());
		}else{
			sqlbuilder.append("NULL,NULL,");
		}
		if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
			sqlbuilder.append("?,");
			objList.add(presult.getOrderBy());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");	
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List<SmsReceiver > roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, SmsReceiver .class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));		
		return roleList;	
	}

	public List<Object> getSaveSql(SmsReceiver  obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call sms_receiver_proc_add(");
		List<Object> objList = new ArrayList<Object>();
		if(obj.getReceiverid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getReceiverid());
		}else
			sqlbuilder.append("NULL,");
		
		if(obj.getSmsid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getSmsid());
		}else
			sqlbuilder.append("NULL,");
		
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(SmsReceiver  obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call sms_receiver_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRef());
		}else
			sqlbuilder.append("NULL,");
		
		if(obj.getStatus()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getStatus());
		}else
			sqlbuilder.append("NULL,");
		
		sqlbuilder.append("?)}");
		return objList; 
	}

}
