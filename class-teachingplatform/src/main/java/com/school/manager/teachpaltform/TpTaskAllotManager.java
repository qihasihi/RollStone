
package  com.school.manager.teachpaltform;

import java.util.List;
import java.util.Map;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITpTaskAllotDAO;

import com.school.entity.teachpaltform.TpTaskAllotInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpTaskAllotManager;
import com.school.util.PageResult;

@Service
public class  TpTaskAllotManager extends BaseManager<TpTaskAllotInfo> implements ITpTaskAllotManager  { 
	
	private ITpTaskAllotDAO tptaskallotdao;
	
	@Autowired
	@Qualifier("tpTaskAllotDAO")
	public void setTptaskallotdao(ITpTaskAllotDAO tptaskallotdao) {
		this.tptaskallotdao = tptaskallotdao;
	}
	
	public Boolean doSave(TpTaskAllotInfo tptaskallotinfo) {
		return this.tptaskallotdao.doSave(tptaskallotinfo);
	}
	
	public Boolean doDelete(TpTaskAllotInfo tptaskallotinfo) {
		return this.tptaskallotdao.doDelete(tptaskallotinfo);
	}

	public Boolean doUpdate(TpTaskAllotInfo tptaskallotinfo) {
		return this.tptaskallotdao.doUpdate(tptaskallotinfo);
	}
	
	public List<TpTaskAllotInfo> getList(TpTaskAllotInfo tptaskallotinfo, PageResult presult) {
		return this.tptaskallotdao.getList(tptaskallotinfo,presult);	
	}
	
	public List<Object> getSaveSql(TpTaskAllotInfo tptaskallotinfo, StringBuilder sqlbuilder) {
		return this.tptaskallotdao.getSaveSql(tptaskallotinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpTaskAllotInfo tptaskallotinfo, StringBuilder sqlbuilder) {
		return this.tptaskallotdao.getDeleteSql(tptaskallotinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpTaskAllotInfo tptaskallotinfo, StringBuilder sqlbuilder) {
		return this.tptaskallotdao.getUpdateSql(tptaskallotinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.tptaskallotdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TpTaskAllotInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TpTaskAllotInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return tptaskallotdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public List<TpTaskAllotInfo> getTaskByGroup(Long groupid) {
        return this.tptaskallotdao.getTaskByGroup(groupid);
    }

    @Override
    public List<Map<String,Object>> getCompleteNum(Long groupid, Long taskid) {
        return this.tptaskallotdao.getCompleteNum(groupid,taskid);
    }

    @Override
    public List<Map<String,Object>> getNum(Long groupid, Long taskid) {
        return this.tptaskallotdao.getNum(groupid,taskid);
    }

    public List<Map<String,Object>> getClassId(final TpTaskAllotInfo tallot){
        return this.tptaskallotdao.getClassId(tallot);
    }
    /**
     * 得到有效的任务数量
     * @param entity
     * @return
     */
    public boolean getYXTkCount(TpTaskAllotInfo entity){
        return this.tptaskallotdao.getYXTkCount(entity);
    }
    /**
     * 根据任务和人，查班级
     * @param taskid
     * @param userid
     * @return
     */
    public List<Map<String,Object>> getTaskAllotBLClassId(final Long taskid,final Integer userid){
        return this.tptaskallotdao.getTaskAllotBLClassId(taskid,userid);
    }

    @Override
    public List<TpTaskAllotInfo> getTaskRemindObjList(TpTaskAllotInfo tallot, PageResult pageResult) {
        return this.tptaskallotdao.getTaskRemindObjList(tallot,pageResult);
    }
}

