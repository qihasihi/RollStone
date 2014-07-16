
package com.school.dao.inter.personalspace;

import com.school.dao.base.ICommonDAO;
import com.school.entity.personalspace.PsUserFriend;
import com.school.util.PageResult;

import java.util.List;

public interface IPsUserFriendDAO extends ICommonDAO<PsUserFriend>{
    List<PsUserFriend> getMyFriendList(PsUserFriend t,PageResult pageResult);
}
