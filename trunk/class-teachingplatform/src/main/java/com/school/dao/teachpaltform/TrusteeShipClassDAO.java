package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.TrusteeShipClass;
import com.school.dao.inter.teachpaltform.ITrusteeShipClassDAO;
import com.school.util.PageResult;

@Component  
public class TrusteeShipClassDAO extends CommonDAO<TrusteeShipClass> implements ITrusteeShipClassDAO {

	public Boolean doSave(TrusteeShipClass trusteeshipclass) {
		if (trusteeshipclass == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(trusteeshipclass, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TrusteeShipClass trusteeshipclass) {
		if(trusteeshipclass==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(trusteeshipclass, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TrusteeShipClass trusteeshipclass) {
		if (trusteeshipclass == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(trusteeshipclass, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TrusteeShipClass> getList(TrusteeShipClass trusteeshipclass, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL j_trusteeship_class_proc_split(");
		List<Object> objList=new ArrayList<Object>();

		if (trusteeshipclass.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(trusteeshipclass.getRef());
		} else
			sqlbuilder.append("null,");
        if (trusteeshipclass.getTrustclassid() != null) {
            sqlbuilder.append("?,");
            objList.add(trusteeshipclass.getTrustclassid());
        } else
            sqlbuilder.append("null,");
        if (trusteeshipclass.getTrustclasstype() != null) {
            sqlbuilder.append("?,");
            objList.add(trusteeshipclass.getTrustclasstype());
        } else
            sqlbuilder.append("null,");
        if (trusteeshipclass.getIsaccept() != null) {
            sqlbuilder.append("?,");
            objList.add(trusteeshipclass.getIsaccept());
        } else
            sqlbuilder.append("null,");
		if (trusteeshipclass.getTrustteacherid() != null) {
			sqlbuilder.append("?,");
			objList.add(trusteeshipclass.getTrustteacherid());
		} else
			sqlbuilder.append("null,");
        if (trusteeshipclass.getReceiveteacherid() != null) {
            sqlbuilder.append("?,");
            objList.add(trusteeshipclass.getReceiveteacherid());
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
		List<TrusteeShipClass> trusteeshipclassList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TrusteeShipClass.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return trusteeshipclassList;	
	}

    //获得被托管的班级列表
    public List<Map<String,Object>> getTsClassList(TrusteeShipClass trusteeshipclass) {
        if(trusteeshipclass==null
                || trusteeshipclass.getReceiveteacherid()==null
                || trusteeshipclass.getYear()==null)
            return null;
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL j_trusteeship_class_proc_getcls(?,?,?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(trusteeshipclass.getReceiveteacherid());
        objList.add(trusteeshipclass.getYear());
        List<Map<String,Object>> trusteeshipclassList=this.executeResultListMap_PROC(sqlbuilder.toString(), objList);
        return trusteeshipclassList;
    }

    // 查询可以托管的老师
    public List<Map<String,Object>> getTrusteeShipTchs(String subject,String grade,String year ,String tchname) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL j_trusteeship_class_get_tstch(?,?,?,");
        List<Object> objList=new ArrayList<Object>();
        objList.add(subject);
        objList.add(grade);
        objList.add(year);
        if (tchname!= null) {
            sqlbuilder.append("?,");
            objList.add(tchname);
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        List<Map<String,Object>> trusteeshipclassList=this.executeResultListMap_PROC(sqlbuilder.toString(), objList);
        return trusteeshipclassList;
    }

	public List<Object> getSaveSql(TrusteeShipClass trusteeshipclass, StringBuilder sqlbuilder) {
		if(trusteeshipclass==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_trusteeship_class_proc_add(");
		List<Object>objList = new ArrayList<Object>();
        if (trusteeshipclass.getTrustteacherid() != null) {
            sqlbuilder.append("?,");
            objList.add(trusteeshipclass.getTrustteacherid());
        } else
            sqlbuilder.append("null,");
        if (trusteeshipclass.getReceiveteacherid() != null) {
            sqlbuilder.append("?,");
            objList.add(trusteeshipclass.getReceiveteacherid());
        } else
            sqlbuilder.append("null,");
        if (trusteeshipclass.getTrustclassid() != null) {
            sqlbuilder.append("?,");
            objList.add(trusteeshipclass.getTrustclassid());
        } else
            sqlbuilder.append("null,");
        if (trusteeshipclass.getTrustclasstype() != null) {
            sqlbuilder.append("?,");
            objList.add(trusteeshipclass.getTrustclasstype());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TrusteeShipClass trusteeshipclass, StringBuilder sqlbuilder) {
		if(trusteeshipclass==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_trusteeship_class_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
        if (trusteeshipclass.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(trusteeshipclass.getRef());
        } else
            sqlbuilder.append("null,");
        if (trusteeshipclass.getTrustclassid() != null) {
            sqlbuilder.append("?,");
            objList.add(trusteeshipclass.getTrustclassid());
        } else
            sqlbuilder.append("null,");
        if (trusteeshipclass.getTrustclasstype() != null) {
            sqlbuilder.append("?,");
            objList.add(trusteeshipclass.getTrustclasstype());
        } else
            sqlbuilder.append("null,");
        if (trusteeshipclass.getReceiveteacherid() != null) {
            sqlbuilder.append("?,");
            objList.add(trusteeshipclass.getReceiveteacherid());
        } else
            sqlbuilder.append("null,");
        if (trusteeshipclass.getTrustteacherid() != null) {
            sqlbuilder.append("?,");
            objList.add(trusteeshipclass.getTrustteacherid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TrusteeShipClass trusteeshipclass, StringBuilder sqlbuilder) {
		if(trusteeshipclass==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_trusteeship_class_proc_update(");
		List<Object>objList = new ArrayList<Object>();
        if (trusteeshipclass.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(trusteeshipclass.getRef());
        } else
            sqlbuilder.append("null,");
        if (trusteeshipclass.getTrustclassid() != null) {
            sqlbuilder.append("?,");
            objList.add(trusteeshipclass.getTrustclassid());
        } else
            sqlbuilder.append("null,");
        if (trusteeshipclass.getIsaccept() != null) {
            sqlbuilder.append("?,");
            objList.add(trusteeshipclass.getIsaccept());
        } else
            sqlbuilder.append("null,");
        if (trusteeshipclass.getReceiveteacherid() != null) {
            sqlbuilder.append("?,");
            objList.add(trusteeshipclass.getReceiveteacherid());
        } else
            sqlbuilder.append("null,");
        if (trusteeshipclass.getTrustteacherid() != null) {
            sqlbuilder.append("?,");
            objList.add(trusteeshipclass.getTrustteacherid());
        } else
            sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

}
