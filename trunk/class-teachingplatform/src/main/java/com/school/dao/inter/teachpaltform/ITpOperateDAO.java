
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TpOperateInfo;
import com.school.util.PageResult;

import java.util.List;

public interface ITpOperateDAO extends ICommonDAO<TpOperateInfo>{
    /**
     * �õ�������Ҫ���µĲ�����¼
     * @param ftime  �ϴθ��µ�ʱ��
     * @param presult ��ҳ
     * @return
     */
    public List<TpOperateInfo> getSynchroList(String ftime,PageResult presult);
}
