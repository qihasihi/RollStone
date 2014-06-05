package com.school.manager.teachpaltform;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jxl.Sheet;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.teachpaltform.IVideoTimeDAO;
import com.school.entity.teachpaltform.VideoTimeInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.IVideoTimeManager;
import com.school.util.PageResult;
@Service
public class VideoTimeManager extends BaseManager<VideoTimeInfo> implements IVideoTimeManager {

	
	private IVideoTimeDAO videotimedao;
	
	@Autowired
	@Qualifier("videoTimeDAO")
	public void setVideotimedao(IVideoTimeDAO videotimedao) {
		this.videotimedao = videotimedao;
	} 

	
	@Override
	public Boolean doDelete(VideoTimeInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Boolean doSave(VideoTimeInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean doUpdate(VideoTimeInfo obj) {
		// TODO Auto-generated method stub
		return this.videotimedao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<VideoTimeInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return videotimedao;
	}

	@Override
	public List<VideoTimeInfo> getList(VideoTimeInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return this.videotimedao.getList(obj, presult);
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getDeleteSql(VideoTimeInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public VideoTimeInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getSaveSql(VideoTimeInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getUpdateSql(VideoTimeInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

}
