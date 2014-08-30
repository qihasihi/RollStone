package com.school.dao.resource;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.resource.IResourceDAO;
import com.school.dao.resource.score.UserModelTotalScoreDAO;
import com.school.entity.UserInfo;
import com.school.entity.resource.MyInfoCloudInfo;
import com.school.entity.resource.ResourceInfo;
import com.school.entity.resource.RsHotRankInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ResourceDAO extends CommonDAO<ResourceInfo> implements IResourceDAO {

    public Boolean doSave(ResourceInfo resourceinfo) {
        if (resourceinfo == null)
            return false;
        StringBuilder sqlbuilder = new StringBuilder();
        List<Object> objList = this.getSaveSql(resourceinfo, sqlbuilder);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    public Boolean doDelete(ResourceInfo resourceinfo) {
        if (resourceinfo == null)
            return false;
        StringBuilder sqlbuilder = new StringBuilder();
        List<Object> objList = getDeleteSql(resourceinfo, sqlbuilder);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    public Boolean doUpdate(ResourceInfo resourceinfo) {
        if (resourceinfo == null)
            return false;
        StringBuilder sqlbuilder = new StringBuilder();
        List<Object> objList = this.getUpdateSql(resourceinfo, sqlbuilder);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    public Boolean addClicks(String resId) {
        if (resId == null || resId.length() == 0)
            return false;
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL rs_resource_info_proc_addclicks(?,?)}");
        List<Object> objList = new ArrayList<Object>();
        objList.add(resId);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    public Boolean addDowns(String resId) {
        if (resId == null || resId.length() == 0)
            return false;
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL rs_resource_info_proc_adddowns(?,?)}");
        List<Object> objList = new ArrayList<Object>();
        objList.add(resId);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    public boolean updateResScore(String resid) {
        if(resid==null)
            return false;
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL rs_resource_update_score_proc(?,?)}");
        List<Object> objList = new ArrayList<Object>();
        objList.add(resid);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    /**
     * 同步数据修改netsharestatus 云端状态
     * @param up_netsharestatus  修改后的云端反馈
     * @param sharestatus   条件:分享类型(1:本地共享 2:云端共享 3:不共享)
     * @param resdegree     条件：资源等级（1:标准 2:共享 3:本地）
     * @param netsharestatus 条件：网校分享类型(-1：未同步  0:待审核 1:未通过 2:已删除 3:共享 4:标准)
     * @return
     */
    public boolean doUpdateShareNetShareStatus(int up_netsharestatus, int sharestatus, int resdegree, int netsharestatus) {
        StringBuilder sqlbuilder=new StringBuilder("{CALL rs_resource_info_proc_share_state_update(?,?,?,?,?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(up_netsharestatus);
        objList.add(sharestatus);
        objList.add(resdegree);
        objList.add(netsharestatus);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0)
            return true;
        return false;
    }
    public List<ResourceInfo> getListBySchoolName(String schoolnameLike,Integer iseq,PageResult presult){
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL rs_resource_info_by_school_name(");
        List<Object> objList = new ArrayList<Object>();
        if (schoolnameLike!=null) {
            sqlbuilder.append("?,");
            objList.add(schoolnameLike);
        } else {
            sqlbuilder.append("NULL,");
        }
        if (iseq!=null) {
            sqlbuilder.append("?,");
            objList.add(iseq);
        } else {
            sqlbuilder.append("NULL,");
        }
        if (presult != null && presult.getPageNo() > 0
                && presult.getPageSize() > 0) {
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        } else {
            sqlbuilder.append("NULL,NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types = new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray = new Object[1];
        List<ResourceInfo> resourceinfoList = this.executeResult_PROC(
                sqlbuilder.toString(), objList, types, ResourceInfo.class,
                objArray);
        if (presult != null && objArray[0] != null
                && objArray[0].toString().trim().length() > 0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return resourceinfoList;
    }
    /**
     * 查询，（带权限）
     * @param resourceinfo
     * @param presult
     * @return
     */
    public List<ResourceInfo> getList(ResourceInfo resourceinfo,
                                      PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL rs_resource_info_proc_split(");
        if (resourceinfo == null)
            resourceinfo = new ResourceInfo();
        List<Object> objList = new ArrayList<Object>();

        if (resourceinfo.getResid() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResid());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResname() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResname());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getReskeyword() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getReskeyword());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResintroduce() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResintroduce());
        } else
            sqlbuilder.append("null,")        ;
        if (resourceinfo.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUserid());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getUsertype() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUsertype());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getGrade() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getGrade());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getSubject() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSubject());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getFiletype() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getFiletype());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getRestype() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getRestype());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getFilesuffixname() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getFilesuffixname());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getFilesize() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getFilesize());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getUsername() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUsername());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getSchoolname() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSchoolname());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getUseobject() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUseobject());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResstatus());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResdegree() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResdegree());
        } else
            sqlbuilder.append("null,");

        if (resourceinfo.getSharestatus() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSharestatus());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getReportnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getReportnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getPraisenum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getPraisenum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getDownloadnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getDownloadnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getCommentnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getCommentnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getRecomendnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getRecomendnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getClicks() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getClicks());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getStorenum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getStorenum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResscore() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResscore());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetsharestatus() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetsharestatus());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetreportnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetreportnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetpraisenum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetpraisenum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetdownloadnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetdownloadnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetcommentnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetcommentnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetrecomendnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetrecomendnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetclicks() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetclicks());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetstorenum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetstorenum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getCtime() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getCtimeString());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getConvertstatus()!= null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getConvertstatus());
        } else
            sqlbuilder.append("null,");

        if (presult != null && presult.getPageNo() > 0
                && presult.getPageSize() > 0) {
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        } else {
            sqlbuilder.append("NULL,NULL,");
        }
        if (presult != null && presult.getOrderBy() != null
                && presult.getOrderBy().trim().length() > 0) {
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        } else {
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types = new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray = new Object[1];
        List<ResourceInfo> resourceinfoList = this.executeResult_PROC(
                sqlbuilder.toString(), objList, types, ResourceInfo.class,
                objArray);
        if (presult != null && objArray[0] != null
                && objArray[0].toString().trim().length() > 0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return resourceinfoList;
    }

    public List<Object> getSaveSql(ResourceInfo resourceinfo, StringBuilder sqlbuilder) {
        if(resourceinfo==null || sqlbuilder==null)
            return null;
        sqlbuilder.append("{CALL rs_resource_info_proc_add(");
        List<Object>objList = new ArrayList<Object>();
        if (resourceinfo.getResid() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResid());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResname() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResname());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getReskeyword() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getReskeyword());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResintroduce() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResintroduce());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getFilesuffixname() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getFilesuffixname());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getFilesize() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getFilesize());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUserid());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getUsername() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUsername());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getGrade() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getGrade());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getSubject() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSubject());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getFiletype() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getFiletype());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getRestype() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getRestype());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResstatus());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getSharestatus() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSharestatus());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getSchoolname() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSchoolname());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getUseobject() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUseobject());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResdegree() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResdegree());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getUsertype() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUsertype());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getFilename() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getFilename());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?,");
        objList.add(resourceinfo.getDcschoolid());
        if(resourceinfo.getDifftype()!=null){
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getDifftype());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }

    public List<Object> getDeleteSql(ResourceInfo resourceinfo,
                                     StringBuilder sqlbuilder) {
        if (resourceinfo == null || sqlbuilder == null)
            return null;
        sqlbuilder.append("{CALL rs_resource_info_proc_delete(");
        List<Object> objList = new ArrayList<Object>();
        if (resourceinfo.getReskeyword() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getReskeyword());
        } else
            sqlbuilder.append("null,");

        if (resourceinfo.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUserid());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResname() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResname());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResintroduce() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResintroduce());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResstatus() != null) {
            sqlbuilder.append("?,");


            objList.add(resourceinfo.getResstatus());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResid() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResid());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }

    public List<Object> getUpdateSql(ResourceInfo resourceinfo,
                                     StringBuilder sqlbuilder) {
        if (resourceinfo == null || sqlbuilder == null)
            return null;
        sqlbuilder.append("{CALL rs_resource_info_proc_update(");
        List<Object> objList = new ArrayList<Object>();
        if (resourceinfo.getResid() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResid());
        } else
            sqlbuilder.append("null,");

        if (resourceinfo.getResname() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResname());
        } else
            sqlbuilder.append("null,");


        if (resourceinfo.getReskeyword() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getReskeyword());
        } else
            sqlbuilder.append("null,");

        if (resourceinfo.getResintroduce() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResintroduce());
        }else
            sqlbuilder.append("null,");

        if (resourceinfo.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUserid());
        }else
            sqlbuilder.append("NULL,");

        if (resourceinfo.getUsertype() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUsertype());
        } else
            sqlbuilder.append("null,");

        if (resourceinfo.getUsername() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUsername());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getSchoolname() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSchoolname());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getSubject() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSubject());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getGrade() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getGrade());
        } else
            sqlbuilder.append("null,");

        if (resourceinfo.getRestype() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getRestype());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getFiletype() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getFiletype());
        } else
            sqlbuilder.append("null,");
//        if (resourceinfo.getFilename() != null) {
//            sqlbuilder.append("?,");
//            objList.add(resourceinfo.getFilename());
//        } else
//            sqlbuilder.append("null,");
        if (resourceinfo.getFilesize() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getFilesize());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResstatus());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResdegree() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResdegree());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getSharestatus() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSharestatus());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getUseobject() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUseobject());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResscore() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResscore());
        } else
            sqlbuilder.append("null,");

        if (resourceinfo.getReportnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getReportnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getStorenum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getStorenum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getPraisenum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getPraisenum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getCommentnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getCommentnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getDownloadnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getDownloadnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getClicks() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getClicks());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getRecomendnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getRecomendnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getFilesuffixname() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getFilesuffixname());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getConvertstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getConvertstatus());
        } else
            sqlbuilder.append("null,");

        sqlbuilder.append("?)}");
        return objList;
    }

    public List<ResourceInfo> getListByExtendValue(ResourceInfo resourceinfo, PageResult presult) {

        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL rs_resource_proc_searchby_value(");
        if (resourceinfo == null)
            resourceinfo = new ResourceInfo();
        List<Object> objList = new ArrayList<Object>();

        if (resourceinfo.getResid() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResid());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResname() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResname());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getReskeyword() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getReskeyword());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResintroduce() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResintroduce());
        } else
            sqlbuilder.append("null,")        ;
        if (resourceinfo.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUserid());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getUsertype() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUsertype());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getGradevalues() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getGradevalues());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getSubjectvalues() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSubjectvalues());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getFiletypevalues() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getFiletypevalues());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getRestypevalues() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getRestypevalues());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getFilesuffixname() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getFilesuffixname());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getFilesize() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getFilesize());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getUsername() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUsername());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getSchoolname() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSchoolname());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getUseobject() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUseobject());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResstatus());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResdegree() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResdegree());
        } else
            sqlbuilder.append("null,");

        if (resourceinfo.getSharestatus() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSharestatus());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getReportnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getReportnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getPraisenum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getPraisenum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getDownloadnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getDownloadnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getCommentnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getCommentnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getRecomendnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getRecomendnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getClicks() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getClicks());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getStorenum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getStorenum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResscore() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResscore());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetsharestatus() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetsharestatus());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetreportnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetreportnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetpraisenum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetpraisenum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetdownloadnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetdownloadnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetcommentnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetcommentnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetrecomendnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetrecomendnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetclicks() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetclicks());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetstorenum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetstorenum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getCtime() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getCtimeString());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getIsunion() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getIsunion());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getCurrentloginsubid()!= null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getCurrentloginsubid());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getCurrentlogingrdid() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getCurrentlogingrdid());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getSharestatusvalues() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSharestatusvalues());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getType() != null && resourceinfo.getType().length() > 0) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getType());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getReverse() != null && resourceinfo.getReverse()) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getReverse());
        } else
            sqlbuilder.append("null,");
        if(resourceinfo.getVersionvalues()!=null&&resourceinfo.getVersionvalues().length()>0){
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getVersionvalues());
        } else
            sqlbuilder.append("null,");
        if(resourceinfo.getIsrecommend()!=null){
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getIsrecommend());
        } else
            sqlbuilder.append("null,");
        if(resourceinfo.getCurrentcourseid()!=null){
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getCurrentcourseid());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?,");
        objList.add(resourceinfo.getDcschoolid());
        if (presult != null && presult.getPageNo() > 0
                && presult.getPageSize() > 0) {
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        } else {
            sqlbuilder.append("NULL,NULL,");
        }
        if (presult != null && presult.getOrderBy() != null
                && presult.getOrderBy().trim().length() > 0) {
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        } else {
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types = new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray = new Object[1];
        List<ResourceInfo> resourceinfoList = this.executeResult_PROC(
                sqlbuilder.toString(), objList, types, ResourceInfo.class,
                objArray);
        if (presult != null && objArray[0] != null
                && objArray[0].toString().trim().length() > 0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return resourceinfoList;
    }

    /**
     * 得到我管理的列表
     * @param resourceinfo
     * @param presult
     * @return
     */
    public List<ResourceInfo> getCheckListByExtendValue(ResourceInfo resourceinfo, PageResult presult) {

        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL rs_resource_proc_check_searchby_value(");
        if (resourceinfo == null)
            resourceinfo = new ResourceInfo();
        List<Object> objList = new ArrayList<Object>();

        if (resourceinfo.getResid() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResid());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResname() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResname());
        } else
            sqlbuilder.append("null,");

        if (resourceinfo.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUserid());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getUsertype() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUsertype());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getGradevalues() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getGradevalues());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getSubjectvalues() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSubjectvalues());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getFiletypevalues() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getFiletypevalues());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getRestypevalues() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getRestypevalues());
        } else
            sqlbuilder.append("null,");

        if (resourceinfo.getUsername() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUsername());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getSchoolname() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSchoolname());
        } else
            sqlbuilder.append("null,");

        if (resourceinfo.getResstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResstatus());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResdegree() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResdegree());
        } else
            sqlbuilder.append("null,");

        if (resourceinfo.getSharestatus() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSharestatus());
        } else
            sqlbuilder.append("null,");

        if (resourceinfo.getCtime() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getCtimeString());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getIsunion() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getIsunion());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getCurrentloginsubid()!= null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getCurrentloginsubid());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getCurrentlogingrdid() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getCurrentlogingrdid());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getSharestatusvalues() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSharestatusvalues());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getType() != null && resourceinfo.getType().length() > 0) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getType());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getReverse() != null && resourceinfo.getReverse()) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getReverse());
        } else
            sqlbuilder.append("null,");
        if (presult != null && presult.getPageNo() > 0
                && presult.getPageSize() > 0) {
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        } else {
            sqlbuilder.append("NULL,NULL,");
        }
        if (presult != null && presult.getOrderBy() != null
                && presult.getOrderBy().trim().length() > 0) {
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        } else {
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types = new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray = new Object[1];
        List<ResourceInfo> resourceinfoList = this.executeResult_PROC(
                sqlbuilder.toString(), objList, types, ResourceInfo.class,
                objArray);
        if (presult != null && objArray[0] != null
                && objArray[0].toString().trim().length() > 0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return resourceinfoList;
    }

    public List<ResourceInfo> getListOfExcellentResource(ResourceInfo resourceinfo,
                                                         PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL rs_resource_proc_excellent_resource(");
        if (resourceinfo == null)
            resourceinfo = new ResourceInfo();
        List<Object> objList = new ArrayList<Object>();
        if (resourceinfo.getResid() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResid());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResname() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResname());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getReskeyword() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getReskeyword());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResintroduce() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResintroduce());
        } else
            sqlbuilder.append("null,")        ;
        if (resourceinfo.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUserid());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getUsertype() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUsertype());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getGrade() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getGrade());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getSubject() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSubject());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getFiletype() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getFiletype());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getRestype() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getRestype());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getFilesuffixname() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getFilesuffixname());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getFilesize() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getFilesize());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getUsername() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUsername());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getSchoolname() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSchoolname());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getUseobject() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUseobject());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResstatus());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResdegree() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResdegree());
        } else
            sqlbuilder.append("null,");

        if (resourceinfo.getSharestatus() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getSharestatus());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getReportnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getReportnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getPraisenum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getPraisenum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getDownloadnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getDownloadnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getCommentnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getCommentnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getRecomendnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getRecomendnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getClicks() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getClicks());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getStorenum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getStorenum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getResscore() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getResscore());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetsharestatus() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetsharestatus());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetreportnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetreportnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetpraisenum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetpraisenum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetdownloadnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetdownloadnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetcommentnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetcommentnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetrecomendnum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetrecomendnum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetclicks() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetclicks());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getNetstorenum() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getNetstorenum());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getType() != null && resourceinfo.getType().length() > 0) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getType());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getReverse() != null && resourceinfo.getReverse()) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getReverse());
        } else
            sqlbuilder.append("null,");
        if (resourceinfo.getCtime() != null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getCtimeString());
        } else
            sqlbuilder.append("null,");
        if (presult != null && presult.getPageNo() > 0
                && presult.getPageSize() > 0) {
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        } else {
            sqlbuilder.append("NULL,NULL,");
        }
        if (presult != null && presult.getOrderBy() != null
                && presult.getOrderBy().trim().length() > 0) {
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        } else {
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types = new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray = new Object[1];
        List<ResourceInfo> resourceinfoList = this.executeResult_PROC(
                sqlbuilder.toString(), objList, types, ResourceInfo.class,objArray);
        if (presult != null && objArray[0] != null
                && objArray[0].toString().trim().length() > 0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return resourceinfoList;
    }

    public List<UserInfo> getListByUserSort(ResourceInfo resourceinfo,
                                            PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL rs_resource_info_proc_by_user_sort(");
        List<Object> objList = new ArrayList<Object>();
        if (resourceinfo!=null && resourceinfo.getUsername()!=null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUsername());
        } else {
            sqlbuilder.append("NULL,");
        }
        if (resourceinfo!=null && resourceinfo.getUserid()!=null) {
            sqlbuilder.append("?,");
            objList.add(resourceinfo.getUserid());
        } else {
            sqlbuilder.append("NULL,");
        }
        if (presult != null && presult.getPageNo() > 0
                && presult.getPageSize() > 0) {
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        } else {
            sqlbuilder.append("NULL,NULL,");
        }
        if (presult != null && presult.getOrderBy() != null
                && presult.getOrderBy().trim().length() > 0) {
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        } else {
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types = new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray = new Object[1];
        List<UserInfo> userList = this.executeResult_PROC(sqlbuilder.toString(), objList, types, UserInfo.class,
                objArray);
        if (presult != null && objArray[0] != null
                && objArray[0].toString().trim().length() > 0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return userList;
    }

    /**
     * 查询用户
     * @param usernamelike
     * @param userid
     * @param presult
     * @return
     */
    public List<ResourceInfo> getListByUser(String usernamelike,Integer userid,
                                            PageResult presult,Integer dcSchoolID) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL rs_resource_info_by_username_split(");
        List<Object> objList = new ArrayList<Object>();
        if (usernamelike!=null) {
            sqlbuilder.append("?,");
            objList.add(usernamelike);
        } else {
            sqlbuilder.append("NULL,");
        }
        if (userid!=null) {
            sqlbuilder.append("?,");
            objList.add(userid);
        } else {
            sqlbuilder.append("NULL,");
        }
        if (presult != null && presult.getPageNo() > 0
                && presult.getPageSize() > 0) {
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        } else {
            sqlbuilder.append("NULL,NULL,");
        }
        if (presult != null && presult.getOrderBy() != null
                && presult.getOrderBy().trim().length() > 0) {
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        } else {
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?,");
        objList.add(dcSchoolID);
        sqlbuilder.append("?)}");
        List<Integer> types = new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray = new Object[1];
        List<ResourceInfo> userList = this.executeResult_PROC(sqlbuilder.toString(), objList, types, ResourceInfo.class,
                objArray);
        if (presult != null && objArray[0] != null
                && objArray[0].toString().trim().length() > 0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return userList;
    }

    /**
     * 根据ResId得到知识点集合
     * @param resid
     * @return
     */
    public List<Map<String,Object>> getResourceCourseList(String resid){
        String sql="{CALL rs_resource_info_getCourseName(?)}";
        List<Object> objlist=new ArrayList<Object>();
        objlist.add(resid);
        return this.executeResultListMap_PROC(sql,objlist);
    }

    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(ResourceInfo entity,StringBuilder sqlbuilder){
        if(entity==null||sqlbuilder==null)return null;
        sqlbuilder.append("{CALL rs_resource_info_proc_synchro(");
        List<Object>objList = new ArrayList<Object>();
        if (entity.getResid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getResid());
        } else
            sqlbuilder.append("null,");
        if (entity.getResname() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getResname());
        } else
            sqlbuilder.append("null,");
        if (entity.getReskeyword() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getReskeyword());
        } else
            sqlbuilder.append("null,");
        if (entity.getResintroduce() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getResintroduce());
        } else
            sqlbuilder.append("null,");
        if (entity.getFilesuffixname() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getFilesuffixname());
        } else
            sqlbuilder.append("null,");
        if (entity.getFilesize() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getFilesize());
        } else
            sqlbuilder.append("null,");
        if (entity.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getUserid());
        } else
            sqlbuilder.append("null,");
        if (entity.getUsername() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getUsername());
        } else
            sqlbuilder.append("null,");
        if (entity.getGrade() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getGrade());
        } else
            sqlbuilder.append("null,");
        if (entity.getSubject() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getSubject());
        } else
            sqlbuilder.append("null,");
        if (entity.getFiletype() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getFiletype());
        } else
            sqlbuilder.append("null,");
        if (entity.getRestype() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getRestype());
        } else
            sqlbuilder.append("null,");
        if (entity.getResstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getResstatus());
        } else
            sqlbuilder.append("null,");
        if (entity.getSharestatus() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getSharestatus());
        } else
            sqlbuilder.append("null,");
        if (entity.getSchoolname() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getSchoolname());
        } else
            sqlbuilder.append("null,");
        if (entity.getUseobject() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getUseobject());
        } else
            sqlbuilder.append("null,");
        if (entity.getResdegree() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getResdegree());
        } else
            sqlbuilder.append("null,");
        if (entity.getUsertype() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getUsertype());
        } else
            sqlbuilder.append("null,");
        if (entity.getFilename() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getFilename());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }

    /**
     * 得到更新的SQL
     * @param obj
     * @param sqlbuilder
     * @return
     */
    public List<Object> getUpdateResNum(ResourceInfo obj, StringBuilder sqlbuilder){
        if(obj==null||sqlbuilder==null)return null;
        sqlbuilder.append("{CALL rs_resource_info_update_resNum(");
        List<Object>objList = new ArrayList<Object>();

        if(obj.getClicks()==null)
            sqlbuilder.append("NULL,");
        else{
            sqlbuilder.append("?,");
            objList.add(obj.getClicks());
        }
        if(obj.getCommentnum()==null)
            sqlbuilder.append("NULL,");
        else{
            sqlbuilder.append("?,");
            objList.add(obj.getCommentnum());
        }
        if(obj.getDownloadnum()==null)
            sqlbuilder.append("NULL,");
        else{
            sqlbuilder.append("?,");
            objList.add(obj.getDownloadnum());
        }
        if(obj.getStorenum()==null)
            sqlbuilder.append("NULL,");
        else{
            sqlbuilder.append("?,");
            objList.add(obj.getStorenum());
        }
        if(obj.getPraisenum()==null)
            sqlbuilder.append("NULL,");
        else{
            sqlbuilder.append("?,");
            objList.add(obj.getPraisenum());
        }
        if(obj.getRecomendnum()==null)
            sqlbuilder.append("NULL,");
        else{
            sqlbuilder.append("?,");
            objList.add(obj.getRecomendnum());
        }
        if(obj.getReportnum()==null)
            sqlbuilder.append("NULL,");
        else{
            sqlbuilder.append("?,");
            objList.add(obj.getReportnum());
        }
        objList.add(obj.getResid());
        sqlbuilder.append("?,?)}");
        return objList;
    }
    /**
     * 得到我的资源列表
     * @param entity
     * @param presult
     * @return
     */
    public List<ResourceInfo> getMyResList(ResourceInfo entity, PageResult presult){
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL rs_resource_info_myres_proc_split(");
        List<Object> objList = new ArrayList<Object>();
        if (entity.getResid()!=null) {
            sqlbuilder.append("?,");
            objList.add(entity.getResid());
        } else {
            sqlbuilder.append("NULL,");
        }
        if (entity.getResname()!=null) {
            sqlbuilder.append("?,");
            objList.add(entity.getResname());
        } else {
            sqlbuilder.append("NULL,");
        }
        if (entity.getUserid()!=null) {
            sqlbuilder.append("?,");
            objList.add(entity.getUserid());
        } else {
            sqlbuilder.append("NULL,");
        }
        if (entity.getGrade()!=null) {
            sqlbuilder.append("?,");
            objList.add(entity.getGrade());
        } else {
            sqlbuilder.append("NULL,");
        }
        if (entity.getSubject()!=null) {
            sqlbuilder.append("?,");
            objList.add(entity.getSubject());
        } else {
            sqlbuilder.append("NULL,");
        }
        if (entity.getFiletype()!=null) {
            sqlbuilder.append("?,");
            objList.add(entity.getFiletype());
        } else {
            sqlbuilder.append("NULL,");
        }
        if (entity.getRestype()!=null) {
            sqlbuilder.append("?,");
            objList.add(entity.getRestype());
        } else {
            sqlbuilder.append("NULL,");
        }
        if (entity.getResstatus()!=null) {
            sqlbuilder.append("?,");
            objList.add(entity.getResstatus());
        } else {
            sqlbuilder.append("NULL,");
        }
        if (entity.getResdegree()!=null) {
            sqlbuilder.append("?,");
            objList.add(entity.getResdegree());
        } else {
            sqlbuilder.append("NULL,");
        }
        if (entity.getSharestatus()!=null) {
            sqlbuilder.append("?,");
            objList.add(entity.getSharestatus());
        } else {
            sqlbuilder.append("NULL,");
        }
        if (presult != null && presult.getPageNo() > 0
                && presult.getPageSize() > 0) {
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        } else {
            sqlbuilder.append("NULL,NULL,");
        }
        if (presult != null && presult.getOrderBy() != null
                && presult.getOrderBy().trim().length() > 0) {
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        } else {
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types = new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray = new Object[1];
        List<ResourceInfo> userList = this.executeResult_PROC(sqlbuilder.toString(), objList, types, ResourceInfo.class,
                objArray);
        if (presult != null && objArray[0] != null
                && objArray[0].toString().trim().length() > 0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return userList;
    }

    /**
     * 得到我的资源排行榜
     * @param entity
     * @param presult
     * @return
     */
    public List<ResourceInfo> getMyResStaticesRank(ResourceInfo entity, PageResult presult){
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL rs_resource_info_statices_getmyrank(");
        List<Object> objList = new ArrayList<Object>();
        if (entity.getUserid()!=null) {
            sqlbuilder.append("?,");
            objList.add(entity.getUserid());
        } else {
            sqlbuilder.append("NULL,");
        }

        if (presult != null && presult.getPageNo() > 0
                && presult.getPageSize() > 0) {
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        } else {
            sqlbuilder.append("NULL,NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types = new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray = new Object[1];
        List<ResourceInfo> userList = this.executeResult_PROC(sqlbuilder.toString(), objList, types, ResourceInfo.class,
                objArray);
        if (presult != null && objArray[0] != null
                && objArray[0].toString().trim().length() > 0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return userList;
    }

    /**
     * 得到首页排行
     * @param entity
     * @param pagesize
     * @return
     */
    public List<Map<String,Object>> getResourceIdxViewRank(ResourceInfo entity,Integer pagesize){
        List<Object> objList=new ArrayList<Object>();
        StringBuilder sqlbuilder=new StringBuilder("{CALL rs_resource_info_idx_viewrank(");
        if(entity.getType()!=null){
            sqlbuilder.append("?,");
            objList.add(entity.getType());
        }else
            sqlbuilder.append("NULL,");
        if(entity.getGradevalues()!=null){
            sqlbuilder.append("?,");
            objList.add(entity.getGradevalues());
        }else
            sqlbuilder.append("NULL,");
        if(entity.getSubjectvalues()!=null){
            sqlbuilder.append("?,");
            objList.add(entity.getSubjectvalues());
        }else
            sqlbuilder.append("NULL,");
        if(pagesize!=null){
            sqlbuilder.append("?");
            objList.add(pagesize);
        }else
            sqlbuilder.append("NULL");
        sqlbuilder.append(")}");
        return this.executeResultListMap_PROC(sqlbuilder.toString(),objList);

    }

    /**
     * 得到首页排行下载排行
     * @param entity
     * @param pagesize
     * @return
     */
    public List<Map<String,Object>> getResourceIdxDownRank(ResourceInfo entity,Integer pagesize){
        List<Object> objList=new ArrayList<Object>();
        StringBuilder sqlbuilder=new StringBuilder("{CALL rs_resource_info_idx_downrank(");

        if(entity.getGradevalues()!=null){
            sqlbuilder.append("?,");
            objList.add(entity.getGradevalues());
        }else
            sqlbuilder.append("NULL,");
        if(entity.getSubjectvalues()!=null){
            sqlbuilder.append("?,");
            objList.add(entity.getSubjectvalues());
        }else
            sqlbuilder.append("NULL,");
        if(pagesize!=null){
            sqlbuilder.append("?");
            objList.add(pagesize);
        }else
            sqlbuilder.append("NULL");
        sqlbuilder.append(")}");
        return this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
    }
    /**
     * 得到浏览量排行
     * @param rs
     * @param presult
     * @return
     */
    public List<ResourceInfo> getResourceIdxViewRankPage(ResourceInfo rs, PageResult presult){
        List<Object> objList=new ArrayList<Object>();
        StringBuilder sqlbuilder=new StringBuilder("{CALL rs_resource_info_by_clicks_rank(");

        if(rs.getGradevalues()!=null){
            sqlbuilder.append("?,");
            objList.add(rs.getGradevalues());
        }else
            sqlbuilder.append("NULL,");
        if(rs.getSubjectvalues()!=null){
            sqlbuilder.append("?,");
            objList.add(rs.getSubjectvalues());
        }else
            sqlbuilder.append("NULL,");
        if(rs.getType()!=null){
            sqlbuilder.append("?,");
            objList.add(rs.getType());
        }else
            sqlbuilder.append("NULL,");
        if(presult==null){
            sqlbuilder.append("NULL,NULL,NULL,");
        }else{
            if(presult.getPageNo()>0&&presult.getPageSize()>0){
                sqlbuilder.append("?,?,");
                objList.add(presult.getPageNo());
                objList.add(presult.getPageSize());
            }else
                sqlbuilder.append("NULL,NULL,");
            if(presult.getOrderBy()!=null){
                sqlbuilder.append("?,");
                objList.add(presult.getOrderBy());
            }else
                sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types = new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray = new Object[1];
        List<ResourceInfo> userList = this.executeResult_PROC(sqlbuilder.toString(), objList, types, ResourceInfo.class,
                objArray);
        if (presult != null && objArray[0] != null
                && objArray[0].toString().trim().length() > 0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return userList;
    }

    /**
     * 得到浏览量排行
     * @param rs
     * @param presult
     * @return
     */
    public List<ResourceInfo> getResourceIdxDownRankPage(ResourceInfo rs, PageResult presult){
        List<Object> objList=new ArrayList<Object>();
        StringBuilder sqlbuilder=new StringBuilder("{CALL rs_resource_info_by_down_rank(");

        if(rs.getGradevalues()!=null){
            sqlbuilder.append("?,");
            objList.add(rs.getGradevalues());
        }else
            sqlbuilder.append("NULL,");
        if(rs.getSubjectvalues()!=null){
            sqlbuilder.append("?,");
            objList.add(rs.getSubjectvalues());
        }else
            sqlbuilder.append("NULL,");
        if(rs.getType()!=null){
            sqlbuilder.append("?,");
            objList.add(rs.getType());
        }else
            sqlbuilder.append("NULL,");
        if(presult==null){
            sqlbuilder.append("NULL,NULL,NULL,");
        }else{
            if(presult.getPageNo()>0&&presult.getPageSize()>0){
                sqlbuilder.append("?,?,");
                objList.add(presult.getPageNo());
                objList.add(presult.getPageSize());
            }else
                sqlbuilder.append("NULL,NULL,");
            if(presult.getOrderBy()!=null){
                sqlbuilder.append("?,");
                objList.add(presult.getOrderBy());
            }else
                sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types = new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray = new Object[1];
        List<ResourceInfo> userList = this.executeResult_PROC(sqlbuilder.toString(), objList, types, ResourceInfo.class,
                objArray);
        if (presult != null && objArray[0] != null
                && objArray[0].toString().trim().length() > 0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return userList;
    }

    /**
     * 得到排行榜的添加SQL语句
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getRsHotRankSaveSql(RsHotRankInfo entity,StringBuilder sqlbuilder){
        if(entity==null||sqlbuilder==null)return null;
        sqlbuilder.append("{CALL rs_hot_rank_save(");
        List<Object>objList = new ArrayList<Object>();
        if (entity.getResid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getResid());
        } else
            sqlbuilder.append("null,");
        if (entity.getSchoolid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getSchoolid());
        } else
            sqlbuilder.append("null,");
        if (entity.getType() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getType());
        } else
            sqlbuilder.append("null,");
        if (entity.getDownloadnum() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getDownloadnum());
        } else
            sqlbuilder.append("null,");
        if (entity.getClicks() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getClicks());
        } else
            sqlbuilder.append("null,");
        if (entity.getCommentnum() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCommentnum());
        } else
            sqlbuilder.append("null,");
        if (entity.getStorenum() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getStorenum());
        } else
            sqlbuilder.append("null,");
        if (entity.getPraisenum() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getPraisenum());
        } else
            sqlbuilder.append("null,");
        if (entity.getRecomendnum() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getRecomendnum());
        } else
            sqlbuilder.append("null,");
        if (entity.getReportnum() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getReportnum());
        } else
            sqlbuilder.append("null,");

        sqlbuilder.append("?)}");
        return objList;
    }
    /**
     * 得到排行榜的删除SQL语句
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getRsHotRankDelSql(RsHotRankInfo entity,StringBuilder sqlbuilder){
        if(entity==null||sqlbuilder==null)return null;
        sqlbuilder.append("{CALL rs_hot_rank_delete(");
        List<Object>objList = new ArrayList<Object>();

        if (entity.getType() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getType());
        } else
            sqlbuilder.append("null,");

        sqlbuilder.append("?)}");
        return objList;
    }

    /**
     * 得到云端消息消息
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getMyInfoCloudSaveSql(MyInfoCloudInfo entity,StringBuilder sqlbuilder){
        if(entity==null||sqlbuilder==null)return null;
        sqlbuilder.append("{CALL myinfo_cloud_info_proc_add(");
        List<Object>objList = new ArrayList<Object>();
        if (entity.getTargetid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getTargetid());
        } else
            sqlbuilder.append("null,");
        if (entity.getType() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getType());
        } else
            sqlbuilder.append("null,");
        if (entity.getData() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getData());
        } else
            sqlbuilder.append("null,");
        if (entity.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getUserid());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }

    /**
     * 查询云端相关动态
     * @param rs
     * @param presult
     * @return
     */
    public List<MyInfoCloudInfo> getMyInfoCloudList(MyInfoCloudInfo rs, PageResult presult){
        List<Object> objList=new ArrayList<Object>();
        StringBuilder sqlbuilder=new StringBuilder("{CALL myinfo_cloud_info_proc_split(");

        if(rs.getTargetid()!=null){
            sqlbuilder.append("?,");
            objList.add(rs.getTargetid());
        }else
            sqlbuilder.append("NULL,");
        if(rs.getType()!=null){
            sqlbuilder.append("?,");
            objList.add(rs.getType());
        }else
            sqlbuilder.append("NULL,");
        if(rs.getUserid()!=null){
            sqlbuilder.append("?,");
            objList.add(rs.getUserid());
        }else
            sqlbuilder.append("NULL,");
        if(presult==null){
            sqlbuilder.append("NULL,NULL,NULL,");
        }else{
            if(presult.getPageNo()>0&&presult.getPageSize()>0){
                sqlbuilder.append("?,?,");
                objList.add(presult.getPageNo());
                objList.add(presult.getPageSize());
            }else
                sqlbuilder.append("NULL,NULL,");
            if(presult.getOrderBy()!=null){
                sqlbuilder.append("?,");
                objList.add(presult.getOrderBy());
            }else
                sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types = new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray = new Object[1];
        List<MyInfoCloudInfo> userList = this.executeResult_PROC(sqlbuilder.toString(), objList, types, MyInfoCloudInfo.class,
                objArray);
        if (presult != null && objArray[0] != null
                && objArray[0].toString().trim().length() > 0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return userList;
    }
    /**
     * 查询他人云态云端相关动态
     * @param rs
     * @param presult
     * @return
     */
    public List<MyInfoCloudInfo> getMyInfoCloudOtherList(MyInfoCloudInfo rs, PageResult presult){
        List<Object> objList=new ArrayList<Object>();
        StringBuilder sqlbuilder=new StringBuilder("{CALL myinfo_cloud_info_proc_othermsg_split(");
        if(rs.getUserid()!=null){
            sqlbuilder.append("?,");
            objList.add(rs.getUserid());
        }else
            sqlbuilder.append("NULL,");
        if(rs.getTargetid()!=null){
            sqlbuilder.append("?,");
            objList.add(rs.getTargetid());
        }else
            sqlbuilder.append("NULL,");
        if(rs.getType()!=null){
            sqlbuilder.append("?,");
            objList.add(rs.getType());
        }else
            sqlbuilder.append("NULL,");

        if(presult==null){
            sqlbuilder.append("NULL,NULL,NULL,");
        }else{
            if(presult.getPageNo()>0&&presult.getPageSize()>0){
                sqlbuilder.append("?,?,");
                objList.add(presult.getPageNo());
                objList.add(presult.getPageSize());
            }else
                sqlbuilder.append("NULL,NULL,");
            if(presult.getOrderBy()!=null){
                sqlbuilder.append("?,");
                objList.add(presult.getOrderBy());
            }else
                sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types = new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray = new Object[1];
        List<MyInfoCloudInfo> userList = this.executeResult_PROC(sqlbuilder.toString(), objList, types, MyInfoCloudInfo.class,
                objArray);
        if (presult != null && objArray[0] != null
                && objArray[0].toString().trim().length() > 0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return userList;
    }


}
