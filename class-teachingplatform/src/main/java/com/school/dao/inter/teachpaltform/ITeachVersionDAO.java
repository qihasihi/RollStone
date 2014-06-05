
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TeachVersionInfo;

import java.util.List;

public interface ITeachVersionDAO extends ICommonDAO<TeachVersionInfo>{

    public List<Object> getSynchroSql(TeachVersionInfo entity, StringBuilder sqlbuilder);
}
