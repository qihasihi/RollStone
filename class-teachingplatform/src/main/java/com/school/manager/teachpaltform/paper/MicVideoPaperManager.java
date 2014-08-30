
package  com.school.manager.teachpaltform.paper;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.paper.IMicVideoPaperDAO;

import com.school.entity.teachpaltform.paper.MicVideoPaperInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.paper.IMicVideoPaperManager;
import com.school.util.PageResult;

@Service
public class  MicVideoPaperManager extends BaseManager<MicVideoPaperInfo> implements IMicVideoPaperManager  { 
	
	private IMicVideoPaperDAO micvideopaperdao;
	
	@Autowired
	@Qualifier("micVideoPaperDAO")
	public void setMicvideopaperdao(IMicVideoPaperDAO micvideopaperdao) {
		this.micvideopaperdao = micvideopaperdao;
	}
	
	public Boolean doSave(MicVideoPaperInfo micvideopaperinfo) {
		return this.micvideopaperdao.doSave(micvideopaperinfo);
	}
	
	public Boolean doDelete(MicVideoPaperInfo micvideopaperinfo) {
		return this.micvideopaperdao.doDelete(micvideopaperinfo);
	}

	public Boolean doUpdate(MicVideoPaperInfo micvideopaperinfo) {
		return this.micvideopaperdao.doUpdate(micvideopaperinfo);
	}
	
	public List<MicVideoPaperInfo> getList(MicVideoPaperInfo micvideopaperinfo, PageResult presult) {
		return this.micvideopaperdao.getList(micvideopaperinfo,presult);	
	}
	
	public List<Object> getSaveSql(MicVideoPaperInfo micvideopaperinfo, StringBuilder sqlbuilder) {
		return this.micvideopaperdao.getSaveSql(micvideopaperinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(MicVideoPaperInfo micvideopaperinfo, StringBuilder sqlbuilder) {
		return this.micvideopaperdao.getDeleteSql(micvideopaperinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(MicVideoPaperInfo micvideopaperinfo, StringBuilder sqlbuilder) {
		return this.micvideopaperdao.getUpdateSql(micvideopaperinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.micvideopaperdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public MicVideoPaperInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<MicVideoPaperInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return micvideopaperdao;
	}

}

