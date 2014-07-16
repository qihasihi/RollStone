package com.school.dao.ethosaraisal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.ethosaraisal.IStuEthosDAO;
import com.school.entity.ethosaraisal.StuEthosInfo;
import com.school.util.PageResult;
/**
 * @author 岳春阳
 * @date 2013-04-26
 * @description 学员校风数据访问类 
 */
@Component 
public class StuEthosDAO extends CommonDAO<StuEthosInfo> implements
		IStuEthosDAO {

	public Boolean doDelete(StuEthosInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null||obj.getRef()==null){
			return false;
		}
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = getDeleteSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}
		return false;
	}

	public Boolean doSave(StuEthosInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = getSaveSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if(afficeObj != null && afficeObj.toString().trim().length()>0 
				&& Integer.parseInt(afficeObj.toString())>0){
			return true;
		}
		
		return false;
	}

	public Boolean doUpdate(StuEthosInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = getUpdateSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	
	

	public List<Object> getDeleteSql(StuEthosInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||obj.getRef()==null){
			return null;
		}
		sqlbuilder.append("{call stu_ethos_proc_delete(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getRef()!=null&&obj.getRef()!=""){
			sqlbuilder.append("?,");
			objlist.add(obj.getRef());
		}else{
			sqlbuilder.append("NULL");
		}
		sqlbuilder.append("?)}");
		return objlist;
	}

	public List<StuEthosInfo> getList(StuEthosInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		if(obj==null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder("{call stu_ethos_proc_list(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getClassid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getClassid());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getWeekid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getWeekid());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getUserid()!=null){
			sqlbuilder.append("?");
			objlist.add(obj.getUserid());
		}else{
			sqlbuilder.append("NULL");
		}		
		sqlbuilder.append(")}");
		List<StuEthosInfo> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, null, StuEthosInfo.class, null);
		return list;
	}

	

	public List<Object> getSaveSql(StuEthosInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||sqlbuilder==null||obj.getRef()==null)
			return null;
		sqlbuilder.append("{call stu_ethos_proc_add(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getRef());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getUserid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getUserid());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getClassid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getClassid());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getGrade()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getGrade());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getWeekid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getWeekid());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getSickleave()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getSickleave());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getSickleavenum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getSickleavenum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getSickleavescore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getSickleavescore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getThingleave()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getThingleave());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getThingleavenum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getThingleavenum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getThingleavescore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getThingleavescore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getLeaveearly()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getLeaveearly());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getLeaveearlynum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getLeaveearlynum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getLeaveearlyscore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getLeaveearlyscore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getAbsenteeism()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getAbsenteeism());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getAbsennum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getAbsennum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getAbsenscore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getAbsenscore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getLate()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getLate());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getLatenum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getLatenum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getLatescore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getLatescore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getDiscipline()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getDiscipline());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getDisciplinenum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getDisciplinenum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getDisciplinescore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getDisciplinescore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getGoodthing()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getGoodthing());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getGoodthingnum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getGoodthingnum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getGoodthingscore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getGoodthingscore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getBadge()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getBadge());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getBadgenum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getBadgenum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getBadgescore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getBadgescore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getUniforms()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getUniforms());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getUniformsnum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getUniformsnum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getUniformsscore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getUniformsscore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getLinere()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getLinere());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getLinerenum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getLinerenum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getLinerescore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getLinerescore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getRebook()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getRebook());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getRebooknum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getRebooknum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getRebookscore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getRebookscore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getOperateid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getOperateid());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		return objlist;
	}

	public List<Object> getUpdateSql(StuEthosInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||sqlbuilder==null||obj.getRef()==null)
			return null;
		sqlbuilder.append("{call stu_ethos_proc_update(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getRef());
		}else{
			sqlbuilder.append("NULL,");
		}		
		if(obj.getSickleave()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getSickleave());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getSickleavenum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getSickleavenum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getSickleavescore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getSickleavescore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getThingleave()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getThingleave());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getThingleavenum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getThingleavenum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getThingleavescore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getThingleavescore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getLeaveearly()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getLeaveearly());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getLeaveearlynum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getLeaveearlynum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getLeaveearlyscore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getLeaveearlyscore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getAbsenteeism()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getAbsenteeism());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getAbsennum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getAbsennum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getAbsenscore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getAbsenscore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getLate()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getLate());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getLatenum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getLatenum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getLatescore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getLatescore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getDiscipline()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getDiscipline());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getDisciplinenum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getDisciplinenum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getDisciplinescore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getDisciplinescore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getGoodthing()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getGoodthing());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getGoodthingnum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getGoodthingnum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getGoodthingscore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getGoodthingscore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getBadge()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getBadge());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getBadgenum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getBadgenum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getBadgescore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getBadgescore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getUniforms()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getUniforms());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getUniformsnum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getUniformsnum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getUniformsscore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getUniformsscore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getLinere()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getLinere());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getLinerenum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getLinerenum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getLinerescore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getLinerescore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getRebook()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getRebook());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getRebooknum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getRebooknum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getRebookscore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getRebookscore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getOperateid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getOperateid());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		return objlist;
	}

	public List<List<String>> getEthosKQ(String termid, Integer classid,
			String stuno, String ordercolumn, String dict) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{call ethos_proc_stu_kq(");
		List<Object> objlist = new ArrayList<Object>();
		if(termid!=null&&termid!=""){
			sqlbuilder.append("?,");
			objlist.add(termid);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(classid!=null){
			sqlbuilder.append("?,");
			objlist.add(classid);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(stuno!=null&&stuno!=""){
			sqlbuilder.append("?,");
			objlist.add(stuno);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(ordercolumn!=null&&ordercolumn!=""){
			sqlbuilder.append("?,");
			objlist.add(ordercolumn);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(dict!=null&&dict!=""){
			sqlbuilder.append("?");
			objlist.add(dict);
		}else{
			sqlbuilder.append("NULL");
		}
		sqlbuilder.append(")}");
		List<List<String>> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, null, null, null);
		return list;
	}

	public List<List<String>> getEthosWJ(String termid, Integer classid,
			String stuno, String ordercolumn, String dict) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{call ethos_proc_stu_wj(");
		List<Object> objlist = new ArrayList<Object>();
		if(termid!=null&&termid!=""){
			sqlbuilder.append("?,");
			objlist.add(termid);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(classid!=null){
			sqlbuilder.append("?,");
			objlist.add(classid);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(stuno!=null&&stuno!=""){
			sqlbuilder.append("?,");
			objlist.add(stuno);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(ordercolumn!=null&&ordercolumn!=""){
			sqlbuilder.append("?,");
			objlist.add(ordercolumn);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(dict!=null&&dict!=""){
			sqlbuilder.append("?");
			objlist.add(dict);
		}else{
			sqlbuilder.append("NULL");
		}
		sqlbuilder.append(")}");
		List<List<String>> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, null, null, null);
		return list;
	}

	public List<List<String>> getEthosZH(String termid, Integer classid,
			String stuno,Integer isshowrank) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{call ethos_proc_stu_zh(");
		List<Object> objlist = new ArrayList<Object>();
		if(termid!=null&&termid!=""){
			sqlbuilder.append("?,");
			objlist.add(termid);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(classid!=null){
			sqlbuilder.append("?,");
			objlist.add(classid);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(stuno!=null&&stuno!=""){
			sqlbuilder.append("?,");
			objlist.add(stuno);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(isshowrank != null){
			sqlbuilder.append("?");
			objlist.add(isshowrank);
		}else{
			sqlbuilder.append("NULL");
		}
		sqlbuilder.append(")}");
		List<List<String>> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, null, null, null);
		return list;
	}	
}
