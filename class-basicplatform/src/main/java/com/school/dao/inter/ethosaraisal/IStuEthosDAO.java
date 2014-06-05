package com.school.dao.inter.ethosaraisal;

import java.util.List;

import com.school.dao.base.ICommonDAO;
import com.school.entity.ethosaraisal.StuEthosInfo;

public interface IStuEthosDAO extends ICommonDAO<StuEthosInfo> {
    public List<List<String>> getEthosKQ(String termid,Integer classid,String stuno,String ordercolumn,String dict);
    public List<List<String>> getEthosWJ(String termid,Integer classid,String stuno,String ordercolumn,String dict);
    public List<List<String>> getEthosZH(String termid,Integer classid,String stuno,Integer isshowrank);
}
