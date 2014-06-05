
package  com.school.manager.inter;

import java.util.List;

import com.school.entity.MyInfoUserInfo;
import com.school.manager.base.IBaseManager;

public interface IMyInfoUserManager  extends IBaseManager<MyInfoUserInfo> { 
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
	public boolean doSaveTongYongThpjTimesteup(Integer userref,int typeid);
	
	/**
	 * 查询首页消息一段时间内的消息数量
	 * @param msgid
	 * @param userref
	 * @param btime
	 * @param etime
	 * @return
	 */
	public Integer getMyInfoUserInfoCountFirstPage(Integer msgid,String userref,String btime,String etime);
} 
