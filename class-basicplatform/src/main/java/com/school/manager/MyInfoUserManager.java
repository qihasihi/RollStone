
package  com.school.manager;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.IMyInfoUserDAO;

import com.school.entity.MyInfoUserInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IMyInfoUserManager;
import com.school.util.PageResult;

@Service
public class  MyInfoUserManager extends BaseManager<MyInfoUserInfo> implements IMyInfoUserManager  { 
	
	private IMyInfoUserDAO myinfouserdao;
	
	@Autowired
	@Qualifier("myInfoUserDAO")
	public void setMyinfouserdao(IMyInfoUserDAO myinfouserdao) {
		this.myinfouserdao = myinfouserdao;
	}
	
	public Boolean doSave(MyInfoUserInfo myinfouserinfo) {
		return myinfouserdao.doSave(myinfouserinfo);
	}
	
	public Boolean doDelete(MyInfoUserInfo myinfouserinfo) {
		return myinfouserdao.doDelete(myinfouserinfo);
	}

	public Boolean doUpdate(MyInfoUserInfo myinfouserinfo) {
		return myinfouserdao.doUpdate(myinfouserinfo);
	}
	
	public List<MyInfoUserInfo> getList(MyInfoUserInfo myinfouserinfo, PageResult presult) {
		return myinfouserdao.getList(myinfouserinfo,presult);	
	}
	
	public List<Object> getSaveSql(MyInfoUserInfo myinfouserinfo, StringBuilder sqlbuilder) {
		return myinfouserdao.getSaveSql(myinfouserinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(MyInfoUserInfo myinfouserinfo, StringBuilder sqlbuilder) {
		return myinfouserdao.getDeleteSql(myinfouserinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(MyInfoUserInfo myinfouserinfo, StringBuilder sqlbuilder) {
		return myinfouserdao.getUpdateSql(myinfouserinfo,sqlbuilder);
	}

	@Override
	protected ICommonDAO<MyInfoUserInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return myinfouserdao;
	}
	/**
	 * 添加消息，通用 评教系统
	 * @param templateid  模版ID
	 * @param userref   接收人REF
	 * @param msgid    消息类型
	 * @param msgname   消息名称
	 * @param operateuserref  操作人
	 * @param data    组合数据类型
	 * @return
	 */
	public boolean doSaveTongYongThpjTimesteup(Integer userref,int typeid){
		return myinfouserdao.doSaveTongYongThpjTimesteup(userref, typeid);
	}
	/**
	 * 查询首页消息一段时间内的消息数量
	 * @param msgid
	 * @param userref
	 * @param btime
	 * @param etime
	 * @return
	 */
	public Integer getMyInfoUserInfoCountFirstPage(Integer msgid,String userref,String btime,String etime){
		return myinfouserdao.getMyInfoUserInfoCountFirstPage(msgid, userref, btime, etime);
	}
	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return myinfouserdao.getNextId();
	}

	public MyInfoUserInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}
}

