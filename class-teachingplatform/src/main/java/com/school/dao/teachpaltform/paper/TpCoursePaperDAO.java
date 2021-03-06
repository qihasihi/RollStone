package com.school.dao.teachpaltform.paper;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.paper.TpCoursePaper;
import com.school.dao.inter.teachpaltform.paper.ITpCoursePaperDAO;
import com.school.util.PageResult;

@Component  
public class TpCoursePaperDAO extends CommonDAO<TpCoursePaper> implements ITpCoursePaperDAO {

	public Boolean doSave(TpCoursePaper tpcoursepaper) {
		if (tpcoursepaper == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tpcoursepaper, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TpCoursePaper tpcoursepaper) {
		if(tpcoursepaper==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tpcoursepaper, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TpCoursePaper tpcoursepaper) {
		if (tpcoursepaper == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tpcoursepaper, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

    /**
     * ��ѯ�Ƿ����AB��
     * @param tpCoursePaper
     * @param presult
     * @return
     */
    public List<TpCoursePaper> getABSynchroList(TpCoursePaper tpCoursePaper,PageResult presult){
        StringBuilder sqlbuilder = new StringBuilder();
        List<Object> objList=new ArrayList<Object>();
        sqlbuilder.append("{CALL tp_j_course_paper_absynchro(");
        if(tpCoursePaper==null)
            sqlbuilder.append("NULL,NULL,");
        else{
            if(tpCoursePaper.getCourseid()!=null){
                sqlbuilder.append("?,");
                objList.add(tpCoursePaper.getCourseid());
            }else
                sqlbuilder.append("NULL,");
            if(tpCoursePaper.getPapertype()!=null){
                sqlbuilder.append("?,");
                objList.add(tpCoursePaper.getPapertype());
            }else
                sqlbuilder.append("NULL,");
        }
//        sqlbuilder.append(")}");

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
        List<TpCoursePaper> tpcoursepaperList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCoursePaper.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tpcoursepaperList;
    }

    @Override
    public List<TpCoursePaper> getSelRelatePaPerList(TpCoursePaper tpcoursepaper, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_get_add_relate_paper_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if (tpcoursepaper.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getRef());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getPaperid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getPaperid());
        } else
            sqlbuilder.append("null,");

        if (tpcoursepaper.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getPapername() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getPapername());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getLocalstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getLocalstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getCoursename() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getCoursename());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getMaterialid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getMaterialid());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getGradeid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getGradeid());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getSubjectid());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getFiltercourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getFiltercourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getSelecttype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getSelecttype());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getTaskflag() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getTaskflag());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getPapertype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getPapertype());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getSeldatetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getSeldatetype());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getCoursepapername() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getCoursepapername());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getIscloud() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getIscloud());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getMicvideoid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getMicvideoid());
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
        List<TpCoursePaper> tpcoursepaperList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCoursePaper.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tpcoursepaperList;
    }

    public List<TpCoursePaper> getList(TpCoursePaper tpcoursepaper, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_j_course_paper_proc_split(");
		List<Object> objList=new ArrayList<Object>();
        if (tpcoursepaper.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getRef());
        } else
            sqlbuilder.append("null,");
		if (tpcoursepaper.getPaperid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpcoursepaper.getPaperid());
		} else
			sqlbuilder.append("null,");

		if (tpcoursepaper.getCourseid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpcoursepaper.getCourseid());
		} else
			sqlbuilder.append("null,");
        if (tpcoursepaper.getPapername() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getPapername());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getLocalstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getLocalstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getCoursename() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getCoursename());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getMaterialid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getMaterialid());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getGradeid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getGradeid());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getSubjectid());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getFiltercourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getFiltercourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getSelecttype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getSelecttype());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getTaskflag() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getTaskflag());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getPapertype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getPapertype());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getSeldatetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getSeldatetype());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getCoursepapername() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getCoursepapername());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getIscloud() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getIscloud());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getSharetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getSharetype());
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
		List<TpCoursePaper> tpcoursepaperList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCoursePaper.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tpcoursepaperList;	
	}
	
	public List<Object> getSaveSql(TpCoursePaper tpcoursepaper, StringBuilder sqlbuilder) {
		if(tpcoursepaper==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_course_paper_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (tpcoursepaper.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcoursepaper.getPaperid());
			} else
				sqlbuilder.append("null,");

			if (tpcoursepaper.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcoursepaper.getCourseid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TpCoursePaper tpcoursepaper, StringBuilder sqlbuilder) {
		if(tpcoursepaper==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_course_paper_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
            if (tpcoursepaper.getRef() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcoursepaper.getRef());
            } else
                sqlbuilder.append("null,");
			if (tpcoursepaper.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcoursepaper.getPaperid());
			} else
				sqlbuilder.append("null,");

			if (tpcoursepaper.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcoursepaper.getCourseid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TpCoursePaper tpcoursepaper, StringBuilder sqlbuilder) {
		if(tpcoursepaper==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_j_course_paper_proc_update(");
		List<Object>objList = new ArrayList<Object>();
            if (tpcoursepaper.getRef() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcoursepaper.getRef());
            } else
                sqlbuilder.append("null,");
			if (tpcoursepaper.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcoursepaper.getPaperid());
			} else
				sqlbuilder.append("null,");

			if (tpcoursepaper.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpcoursepaper.getCourseid());
			} else
				sqlbuilder.append("null,");
            if (tpcoursepaper.getLocalstatus() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcoursepaper.getLocalstatus());
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

    @Override
    public List<TpCoursePaper> getRelateCoursePaPerList(TpCoursePaper tpcoursepaper, PageResult presult) {
        if(tpcoursepaper==null||tpcoursepaper.getCourseid()==null)
            return null;
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_j_relate_course_paper_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if (tpcoursepaper.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getIscloud() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getIscloud());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getIsrelate() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getIsrelate());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getSharetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getSharetype());
        } else
            sqlbuilder.append("null,");
        if (tpcoursepaper.getPaperid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcoursepaper.getPaperid());
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
        List<TpCoursePaper> tpcoursepaperList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCoursePaper.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tpcoursepaperList;
    }
}
