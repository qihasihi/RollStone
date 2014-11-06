
package  com.school.manager.teachpaltform;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITpCourseResourceDAO;

import com.school.entity.teachpaltform.TpCourseResource;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpCourseResourceManager;
import com.school.util.PageResult;

@Service
public class  TpCourseResourceManager extends BaseManager<TpCourseResource> implements ITpCourseResourceManager  { 
	
	private ITpCourseResourceDAO tpcourseresourcedao;
	
	@Autowired
	@Qualifier("tpCourseResourceDAO")
	public void setTpcourseresourcedao(ITpCourseResourceDAO tpcourseresourcedao) {
		this.tpcourseresourcedao = tpcourseresourcedao;
	}
	
	public Boolean doSave(TpCourseResource tpcourseresource) {
		return this.tpcourseresourcedao.doSave(tpcourseresource);
	}
	
	public Boolean doDelete(TpCourseResource tpcourseresource) {
		return this.tpcourseresourcedao.doDelete(tpcourseresource);
	}

	public Boolean doUpdate(TpCourseResource tpcourseresource) {
		return this.tpcourseresourcedao.doUpdate(tpcourseresource);
	}
	
	public List<TpCourseResource> getList(TpCourseResource tpcourseresource, PageResult presult) {
		return this.tpcourseresourcedao.getList(tpcourseresource,presult);	
	}
	
	public List<Object> getSaveSql(TpCourseResource tpcourseresource, StringBuilder sqlbuilder) {
		return this.tpcourseresourcedao.getSaveSql(tpcourseresource,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpCourseResource tpcourseresource, StringBuilder sqlbuilder) {
		return this.tpcourseresourcedao.getDeleteSql(tpcourseresource,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpCourseResource tpcourseresource, StringBuilder sqlbuilder) {
		return this.tpcourseresourcedao.getUpdateSql(tpcourseresource,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.tpcourseresourcedao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TpCourseResource getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TpCourseResource> getBaseDAO() {
		// TODO Auto-generated method stub
		return tpcourseresourcedao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(TpCourseResource entity,StringBuilder sqlbuilder){
        return this.tpcourseresourcedao.getSynchroSql(entity,sqlbuilder);
    }

    @Override
    public Boolean doAddDynamic(TpCourseResource courseres) {
        return this.tpcourseresourcedao.doAddDynamic(courseres);
    }

    @Override
    public List<TpCourseResource> getResourceForRelatedCourse(TpCourseResource tpcourseresource, PageResult presult) {
        return this.tpcourseresourcedao.getResourceForRelatedCourse(tpcourseresource,presult);
    }

    @Override
    public List<TpCourseResource> getLikeResource(Integer gradeid, Integer subjectid, String name, PageResult presult) {
        return this.tpcourseresourcedao.getLikeResource(gradeid,subjectid,name,presult);
    }

    @Override
    public List<TpCourseResource> getResourceUnion(TpCourseResource tpCourseResource, PageResult presult) {
        return this.tpcourseresourcedao.getResourceUnion(tpCourseResource,presult);
    }
}

