
package com.school.dao.inter;

import com.school.dao.base.ICommonDAO;
import com.school.entity.MyInfoUserInfo;

import java.util.List;
import java.util.Map;

public interface IMyInfoUserDAO extends ICommonDAO<MyInfoUserInfo>{
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

    /**
     * 得到首页的动态数据个数
     * @param userref
     * @return
     */
    public List<Map<String,Object>> getSYMsgDataCount(String userref);
    /**
     * 得到首页的动态数据
     * @param userref
     * @return
     */
    public List<MyInfoUserInfo> getSYMsgData(String userref);
}
