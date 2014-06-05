package com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.MyInfoTemplateInfo;
import com.school.dao.inter.IMyInfoTemplateDAO;
import com.school.util.PageResult;

@Component  
public class MyInfoTemplateDAO extends CommonDAO<MyInfoTemplateInfo> implements IMyInfoTemplateDAO {

	public Boolean doSave(MyInfoTemplateInfo myinfotemplateinfo) {
		if (myinfotemplateinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(myinfotemplateinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(MyInfoTemplateInfo myinfotemplateinfo) {
		if(myinfotemplateinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(myinfotemplateinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(MyInfoTemplateInfo myinfotemplateinfo) {
		if (myinfotemplateinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(myinfotemplateinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<MyInfoTemplateInfo> getList(MyInfoTemplateInfo myinfotemplateinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL myinfo_template_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (myinfotemplateinfo.getTemplatename() != null) {
			sqlbuilder.append("?,");
			objList.add(myinfotemplateinfo.getTemplatename());
		} else
			sqlbuilder.append("null,");
		if (myinfotemplateinfo.getTemplateid() != null) {
			sqlbuilder.append("?,");
			objList.add(myinfotemplateinfo.getTemplateid());
		} else
			sqlbuilder.append("null,");
		if (myinfotemplateinfo.getTemplatesearator() != null) {
			sqlbuilder.append("?,");
			objList.add(myinfotemplateinfo.getTemplatesearator());
		} else
			sqlbuilder.append("null,");
		if (myinfotemplateinfo.getEnable() != null) {
			sqlbuilder.append("?,");
			objList.add(myinfotemplateinfo.getEnable());
		} else
			sqlbuilder.append("null,");

		if (myinfotemplateinfo.getTemplatecontent() != null) {
			sqlbuilder.append("?,");
			objList.add(myinfotemplateinfo.getTemplatecontent());
		} else
			sqlbuilder.append("null,");
		if (myinfotemplateinfo.getTemplateurl() != null) {
			sqlbuilder.append("?,");
			objList.add(myinfotemplateinfo.getTemplateurl());
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
		List<MyInfoTemplateInfo> myinfotemplateinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, MyInfoTemplateInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return myinfotemplateinfoList;	
	}
	
	public List<Object> getSaveSql(MyInfoTemplateInfo myinfotemplateinfo, StringBuilder sqlbuilder) {
		if(myinfotemplateinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL myinfo_template_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
		if (myinfotemplateinfo.getTemplatename() != null) {
			sqlbuilder.append("?,");
			objList.add(myinfotemplateinfo.getTemplatename());
		} else
			sqlbuilder.append("null,");
		if (myinfotemplateinfo.getTemplateid() != null) {
			sqlbuilder.append("?,");
			objList.add(myinfotemplateinfo.getTemplateid());
		} else
			sqlbuilder.append("null,");
		if (myinfotemplateinfo.getTemplatesearator() != null) {
			sqlbuilder.append("?,");
			objList.add(myinfotemplateinfo.getTemplatesearator());
		} else
			sqlbuilder.append("null,");
		if (myinfotemplateinfo.getEnable() != null) {
			sqlbuilder.append("?,");
			objList.add(myinfotemplateinfo.getEnable());
		} else
			sqlbuilder.append("null,");

		if (myinfotemplateinfo.getTemplatecontent() != null) {
			sqlbuilder.append("?,");
			objList.add(myinfotemplateinfo.getTemplatecontent());
		} else
			sqlbuilder.append("null,");
		if (myinfotemplateinfo.getTemplateurl() != null) {
			sqlbuilder.append("?,");
			objList.add(myinfotemplateinfo.getTemplateurl());
		} else
			sqlbuilder.append("null,");	
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(MyInfoTemplateInfo myinfotemplateinfo, StringBuilder sqlbuilder) {
		if(myinfotemplateinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL myinfo_template_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (myinfotemplateinfo.getTemplatename() != null) {
				sqlbuilder.append("?,");
				objList.add(myinfotemplateinfo.getTemplatename());
			} else
				sqlbuilder.append("null,");
			if (myinfotemplateinfo.getTemplateid() != null) {
				sqlbuilder.append("?,");
				objList.add(myinfotemplateinfo.getTemplateid());
			} else
				sqlbuilder.append("null,");
			if (myinfotemplateinfo.getTemplatesearator() != null) {
				sqlbuilder.append("?,");
				objList.add(myinfotemplateinfo.getTemplatesearator());
			} else
				sqlbuilder.append("null,");
			if (myinfotemplateinfo.getEnable() != null) {
				sqlbuilder.append("?,");
				objList.add(myinfotemplateinfo.getEnable());
			} else
				sqlbuilder.append("null,");

			if (myinfotemplateinfo.getTemplatecontent() != null) {
				sqlbuilder.append("?,");
				objList.add(myinfotemplateinfo.getTemplatecontent());
			} else
				sqlbuilder.append("null,");
			if (myinfotemplateinfo.getTemplateurl() != null) {
				sqlbuilder.append("?,");
				objList.add(myinfotemplateinfo.getTemplateurl());
			} else
				sqlbuilder.append("null,");		
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(MyInfoTemplateInfo myinfotemplateinfo, StringBuilder sqlbuilder) {
		if(myinfotemplateinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL myinfo_template_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		if (myinfotemplateinfo.getTemplatename() != null) {
			sqlbuilder.append("?,");
			objList.add(myinfotemplateinfo.getTemplatename());
		} else
			sqlbuilder.append("null,");
		if (myinfotemplateinfo.getTemplateid() != null) {
			sqlbuilder.append("?,");
			objList.add(myinfotemplateinfo.getTemplateid());
		} else
			sqlbuilder.append("null,");
		if (myinfotemplateinfo.getTemplatesearator() != null) {
			sqlbuilder.append("?,");
			objList.add(myinfotemplateinfo.getTemplatesearator());
		} else
			sqlbuilder.append("null,");
		if (myinfotemplateinfo.getEnable() != null) {
			sqlbuilder.append("?,");
			objList.add(myinfotemplateinfo.getEnable());
		} else
			sqlbuilder.append("null,");

		if (myinfotemplateinfo.getTemplatecontent() != null) {
			sqlbuilder.append("?,");
			objList.add(myinfotemplateinfo.getTemplatecontent());
		} else
			sqlbuilder.append("null,");
		if (myinfotemplateinfo.getTemplateurl() != null) {
			sqlbuilder.append("?,");
			objList.add(myinfotemplateinfo.getTemplateurl());
		} else
			sqlbuilder.append("null,");	
		sqlbuilder.append("?)}");
		return objList; 
	}
}
