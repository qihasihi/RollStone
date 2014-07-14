package com.school.dao.teachpaltform.paper;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.paper.MicVideoPaperInfo;
import com.school.dao.inter.teachpaltform.paper.IMicVideoPaperDAO;
import com.school.util.PageResult;

@Component  
public class MicVideoPaperDAO extends CommonDAO<MicVideoPaperInfo> implements IMicVideoPaperDAO {

	public Boolean doSave(MicVideoPaperInfo micvideopaperinfo) {
		if (micvideopaperinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(micvideopaperinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(MicVideoPaperInfo micvideopaperinfo) {
		if(micvideopaperinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(micvideopaperinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(MicVideoPaperInfo micvideopaperinfo) {
		if (micvideopaperinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(micvideopaperinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<MicVideoPaperInfo> getList(MicVideoPaperInfo micvideopaperinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL j_mic_video_paper_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (micvideopaperinfo.getPaperid() != null) {
			sqlbuilder.append("?,");
			objList.add(micvideopaperinfo.getPaperid());
		} else
			sqlbuilder.append("null,");
		if (micvideopaperinfo.getMicvideoid() != null) {
			sqlbuilder.append("?,");
			objList.add(micvideopaperinfo.getMicvideoid());
		} else
			sqlbuilder.append("null,");
		if (micvideopaperinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(micvideopaperinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (micvideopaperinfo.getCtime() != null) {
			sqlbuilder.append("?,");
			objList.add(micvideopaperinfo.getCtime());
		} else
			sqlbuilder.append("null,");
		if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
			sqlbuilder.append("?,?,");
			objList.add(presult.getPageNo());
			objList.add(presult.getPageSize());
		}else{
			sqlbuilder.append("NULL,NULL,");
		}
		if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
			sqlbuilder.append("?,");
			objList.add(presult.getOrderBy());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");	
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List<MicVideoPaperInfo> micvideopaperinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, MicVideoPaperInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return micvideopaperinfoList;	
	}
	
	public List<Object> getSaveSql(MicVideoPaperInfo micvideopaperinfo, StringBuilder sqlbuilder) {
		if(micvideopaperinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_mic_video_paper_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (micvideopaperinfo.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(micvideopaperinfo.getPaperid());
			} else
				sqlbuilder.append("null,");
			if (micvideopaperinfo.getMicvideoid() != null) {
				sqlbuilder.append("?,");
				objList.add(micvideopaperinfo.getMicvideoid());
			} else
				sqlbuilder.append("null,");
			if (micvideopaperinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(micvideopaperinfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (micvideopaperinfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(micvideopaperinfo.getCtime());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(MicVideoPaperInfo micvideopaperinfo, StringBuilder sqlbuilder) {
		if(micvideopaperinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_mic_video_paper_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (micvideopaperinfo.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(micvideopaperinfo.getPaperid());
			} else
				sqlbuilder.append("null,");
			if (micvideopaperinfo.getMicvideoid() != null) {
				sqlbuilder.append("?,");
				objList.add(micvideopaperinfo.getMicvideoid());
			} else
				sqlbuilder.append("null,");
			if (micvideopaperinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(micvideopaperinfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (micvideopaperinfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(micvideopaperinfo.getCtime());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(MicVideoPaperInfo micvideopaperinfo, StringBuilder sqlbuilder) {
		if(micvideopaperinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_mic_video_paper_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (micvideopaperinfo.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(micvideopaperinfo.getPaperid());
			} else
				sqlbuilder.append("null,");
			if (micvideopaperinfo.getMicvideoid() != null) {
				sqlbuilder.append("?,");
				objList.add(micvideopaperinfo.getMicvideoid());
			} else
				sqlbuilder.append("null,");
			if (micvideopaperinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(micvideopaperinfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (micvideopaperinfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(micvideopaperinfo.getCtime());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.executeArray_SQL(sqlArrayList, objArrayList);
	}
	
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

}
