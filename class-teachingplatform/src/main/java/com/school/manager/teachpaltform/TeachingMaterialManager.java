
package  com.school.manager.teachpaltform;

import java.util.List;
import java.util.Map;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITeachingMaterialDAO;

import com.school.entity.teachpaltform.TeachingMaterialInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITeachingMaterialManager;
import com.school.util.PageResult;

@Service
public class  TeachingMaterialManager extends BaseManager<TeachingMaterialInfo> implements ITeachingMaterialManager  {
	
	private ITeachingMaterialDAO teachingmaterialdao;
	
	@Autowired
	@Qualifier("teachingMaterialDAO")
	public void setteachingmaterialdao(ITeachingMaterialDAO teachingmaterialdao) {
		this.teachingmaterialdao = teachingmaterialdao;
	}
	
	public Boolean doSave(TeachingMaterialInfo TeachingMaterialInfo) {
		return this.teachingmaterialdao.doSave(TeachingMaterialInfo);
	}
	
	public Boolean doDelete(TeachingMaterialInfo TeachingMaterialInfo) {
		return this.teachingmaterialdao.doDelete(TeachingMaterialInfo);
	}

	public Boolean doUpdate(TeachingMaterialInfo TeachingMaterialInfo) {
		return this.teachingmaterialdao.doUpdate(TeachingMaterialInfo);
	}
	
	public List<TeachingMaterialInfo> getList(TeachingMaterialInfo TeachingMaterialInfo, PageResult presult) {
		return this.teachingmaterialdao.getList(TeachingMaterialInfo,presult);
	}
	
	public List<Object> getSaveSql(TeachingMaterialInfo TeachingMaterialInfo, StringBuilder sqlbuilder) {
		return this.teachingmaterialdao.getSaveSql(TeachingMaterialInfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(TeachingMaterialInfo TeachingMaterialInfo, StringBuilder sqlbuilder) {
		return this.teachingmaterialdao.getDeleteSql(TeachingMaterialInfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(TeachingMaterialInfo TeachingMaterialInfo, StringBuilder sqlbuilder) {
		return this.teachingmaterialdao.getUpdateSql(TeachingMaterialInfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.teachingmaterialdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
    /**
     * 根据residstr得到年级学科属性
     * @param residstr
     * @return
     */
    public List<Map<String,Object>> getTeachingMaterialGradeSubByResId(String residstr){
        return this.teachingmaterialdao.getTeachingMaterialGradeSubByResId(residstr);
    }
    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(TeachingMaterialInfo entity,StringBuilder sqlbuilder){
        return this.teachingmaterialdao.getSynchroSql(entity,sqlbuilder);
    }
	
	public TeachingMaterialInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * 执行生成教材，版本存储过程。
     * @return
     */
    public Boolean genderOtherTeacherTeachMaterial(){
        return this.teachingmaterialdao.genderOtherTeacherTeachMaterial();
    }

	@Override
	protected ICommonDAO<TeachingMaterialInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return teachingmaterialdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

