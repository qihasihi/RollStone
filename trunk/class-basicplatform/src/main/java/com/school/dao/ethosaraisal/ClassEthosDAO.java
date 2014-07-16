package com.school.dao.ethosaraisal;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.ethosaraisal.IClassEthosDAO;
import com.school.entity.ethosaraisal.ClassEthosInfo;
import com.school.util.PageResult;
/**
 * @author 岳春阳
 * @date 2013-04-26
 * @description 班级校风数据访问类 
 */
@Component 
public class ClassEthosDAO extends CommonDAO<ClassEthosInfo> implements
		IClassEthosDAO {

	public Boolean doDelete(ClassEthosInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean doSave(ClassEthosInfo obj) {
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

	public Boolean doUpdate(ClassEthosInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getDeleteSql(ClassEthosInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ClassEthosInfo> getList(ClassEthosInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getSaveSql(ClassEthosInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||sqlbuilder==null||obj.getRef()==null)
			return null;
		sqlbuilder.append("{call class_ethos_proc_add(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getRef());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getWeekid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getWeekid());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getGrade()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getGrade());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getClassid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getClassid());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getAssemblyremark()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getAssemblyremark());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getAssemblyscore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getAssemblyscore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getHygieneremark()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getHygieneremark());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getHygienescore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getHygienescore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getMoneyremark()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getMoneyremark());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getMoneyscore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getMoneyscore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getDormitoryremark()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getDormitoryremark());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getDormitoryscore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getDormitoryscore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getOtherremark()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getOtherremark());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getOtherscore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getOtherscore());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getAwardremark()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getAwardremark());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getAwardscore()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getAwardscore());
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

	public List<Object> getUpdateSql(ClassEthosInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ClassEthosInfo> loadClassEthos(ClassEthosInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder("{call class_ethos_proc_load(");
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
		if(obj.getGrade()!=null){
			sqlbuilder.append("?");
			objlist.add(obj.getGrade());
		}else{
			sqlbuilder.append("NULL");
		}		
		sqlbuilder.append(")}");
		List<ClassEthosInfo> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, null, ClassEthosInfo.class, null);
		return list;
	}

	public List<List<String>> getClassEthosList(String termid,String grade, Integer weekid,Integer weekidend,Integer classid,String order
			) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder("{call class_ethos_proc_list(");
		List<Object> objlist = new ArrayList<Object>();
		if(termid!=null){
			sqlbuilder.append("?,");
			objlist.add(termid);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(grade!=null){
			sqlbuilder.append("?,");
			objlist.add(grade);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(weekid!=null){
			sqlbuilder.append("?,");
			objlist.add(weekid);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(weekidend!=null){
			sqlbuilder.append("?,");
			objlist.add(weekidend);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(classid!=null){
			sqlbuilder.append("?,");
			objlist.add(classid);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(order!=null){
			sqlbuilder.append("?");
			objlist.add(order);
		}else{
			sqlbuilder.append("NULL");
		}
		sqlbuilder.append(")}");
		List<List<String>> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, null, null, null);
		return list;
	}

	public List<List<String>> getEthosForClass(String termid, String grade,
			Integer weekid, Integer classid) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder("{call ethos_proc_class(");
		List<Object> objlist = new ArrayList<Object>();
		if(termid!=null){
			sqlbuilder.append("?,");
			objlist.add(termid);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(weekid!=null){
			sqlbuilder.append("?,");
			objlist.add(weekid);
		}else{
			sqlbuilder.append("NULL,");
		}
		if(grade!=null){
			sqlbuilder.append("?,");
			objlist.add(grade);
		}else{
			sqlbuilder.append("NULL,");
		}		
		if(classid!=null){
			sqlbuilder.append("?");
			objlist.add(classid);
		}else{
			sqlbuilder.append("NULL");
		}
		
		sqlbuilder.append(")}");
		List<List<String>> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, null, null, null);
		return list;
	}

	
}
