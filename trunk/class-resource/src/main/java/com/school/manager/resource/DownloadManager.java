
package  com.school.manager.resource;

import java.util.List;
import java.util.UUID;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.resource.IDownloadDAO;

import com.school.entity.resource.DownloadInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.resource.IDownloadManager;
import com.school.util.PageResult;

@Service
public class  DownloadManager extends BaseManager<DownloadInfo> implements IDownloadManager  { 
	
	private IDownloadDAO downloaddao;
	
	@Autowired
	@Qualifier("downloadDAO")
	public void setDownloaddao(IDownloadDAO downloaddao) {
		this.downloaddao = downloaddao;
	}
	
	public Boolean doSave(DownloadInfo downloadinfo) {
		return downloaddao.doSave(downloadinfo);
	}
	
	public Boolean doDelete(DownloadInfo downloadinfo) {
		return downloaddao.doDelete(downloadinfo);
	}

	public Boolean doUpdate(DownloadInfo downloadinfo) {
		return downloaddao.doUpdate(downloadinfo);
	}
	
	public List<DownloadInfo> getList(DownloadInfo downloadinfo, PageResult presult) {
		return downloaddao.getList(downloadinfo,presult);	
	}
	
	public List<Object> getSaveSql(DownloadInfo downloadinfo, StringBuilder sqlbuilder) {
		return downloaddao.getSaveSql(downloadinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(DownloadInfo downloadinfo, StringBuilder sqlbuilder) {
		return downloaddao.getDeleteSql(downloadinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(DownloadInfo downloadinfo, StringBuilder sqlbuilder) {
		return downloaddao.getUpdateSql(downloadinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return downloaddao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public DownloadInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<DownloadInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}
}

