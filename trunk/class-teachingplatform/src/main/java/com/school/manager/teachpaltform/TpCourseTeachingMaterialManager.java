
package com.school.manager.teachpaltform;

import java.util.List;
import java.util.UUID;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITpCourseTeachingMaterialDAO;

import com.school.entity.teachpaltform.TpCourseTeachingMaterial;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpCourseTeachingMaterialManager;
import com.school.util.PageResult;

@Service
public class  TpCourseTeachingMaterialManager extends BaseManager<TpCourseTeachingMaterial> implements ITpCourseTeachingMaterialManager  { 
	
	private ITpCourseTeachingMaterialDAO tpcourseteachingmaterialdao;
	
	@Autowired
	// @Qualifier("tpcourseteachingmaterialDAO")
	public void setTpcourseteachingmaterialdao(ITpCourseTeachingMaterialDAO tpcourseteachingmaterialdao) {
		this.tpcourseteachingmaterialdao = tpcourseteachingmaterialdao;
	}
	
	public Boolean doSave(TpCourseTeachingMaterial tpcourseteachingmaterial) {
		return tpcourseteachingmaterialdao.doSave(tpcourseteachingmaterial);
	}
	
	public Boolean doDelete(TpCourseTeachingMaterial tpcourseteachingmaterial) {
		return tpcourseteachingmaterialdao.doDelete(tpcourseteachingmaterial);
	}

	public Boolean doUpdate(TpCourseTeachingMaterial tpcourseteachingmaterial) {
		return tpcourseteachingmaterialdao.doUpdate(tpcourseteachingmaterial);
	}
	
	public List<TpCourseTeachingMaterial> getList(TpCourseTeachingMaterial tpcourseteachingmaterial, PageResult presult) {
		return tpcourseteachingmaterialdao.getList(tpcourseteachingmaterial,presult);	
	}
	
	public List<Object> getSaveSql(TpCourseTeachingMaterial tpcourseteachingmaterial, StringBuilder sqlbuilder) {
		return tpcourseteachingmaterialdao.getSaveSql(tpcourseteachingmaterial,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpCourseTeachingMaterial tpcourseteachingmaterial, StringBuilder sqlbuilder) {
		return tpcourseteachingmaterialdao.getDeleteSql(tpcourseteachingmaterial,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpCourseTeachingMaterial tpcourseteachingmaterial, StringBuilder sqlbuilder) {
		return tpcourseteachingmaterialdao.getUpdateSql(tpcourseteachingmaterial,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return tpcourseteachingmaterialdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}

    /**
     * 得到同步的SQL
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSql(TpCourseTeachingMaterial entity,StringBuilder sqlbuilder){
        return tpcourseteachingmaterialdao.getSynchroSql(entity,sqlbuilder);
    }

	public TpCourseTeachingMaterial getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TpCourseTeachingMaterial> getBaseDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}
}

