
package com.school.manager.inter.personalspace;

import com.school.entity.personalspace.PsUserFriend;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

import java.util.List;

public interface IPsUserFriendManager extends IBaseManager<PsUserFriend> {
    List<PsUserFriend>getMyFriendList(PsUserFriend t,PageResult pageResult);
} 
