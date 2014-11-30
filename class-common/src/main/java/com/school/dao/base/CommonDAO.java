package com.school.dao.base;

import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.logging.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.CallableStatementCreatorFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;
import org.springframework.transaction.annotation.Transactional;

/**
 * CopyRright (c)2003-2010 项目名称: oa 文件名称: CommonDAO.java 描述: 使用的JDK版本: JDK1.6
 * 包名: com.bhsf.oa.dao.impl 创 建 人： ZHENGZHOU 生成日期： 2010-4-26 修改人： 修改日期: 修改原因描述
 * 版本号: 1.1
 */
@Component
public abstract class CommonDAO<T> implements ICommonDAO<T> {
    @Resource(name = "jdbcTemplate")
    protected JdbcTemplate jdbcTemplate;
	protected final Log log = LogFactory.getLog(getClass());





    public Object executeSeq_PROC(String sql, Object... paraValue) {
        return jdbcTemplate.queryForObject(sql,paraValue,String.class);
    }



	/*
	 * (non-Javadoc)
	 * 执行SQL语句 返回一个参数
	 * @see com.school.dao.base.ICommonDAO#executeSalar_SQL(java.lang.String,
	 * java.lang.Object[])
	 */
	public Object executeSalar_SQL(String sql, Object[] paraValue) {
		// TODO Auto-generated method stub
        return jdbcTemplate.queryForObject(sql,paraValue,java.lang.String.class);

	}


	/*
	 * (non-Javadoc)
	 * 执行返回一个参数的存储过程
	 * @see com.school.dao.base.ICommonDAO#executeSacle_PROC(java.lang.String,
	 * java.lang.Object)
	 */
	public Object executeSacle_PROC(String sql, Object... paraValue) {
        //return jdbcTemplate.c(sql,paraValue,java.lang.String.class);
        return jdbcTemplate.execute(new SchoolCallableStatementCreator(sql.toString(),paraValue),
                new SchoolCallableStatementCallback(false,paraValue==null?0:paraValue.length));
    }



	
	/**
	 * 执行得到相对应的返回值 
	 * @param returnType
	 * @param sql
	 * @param paraValue
	 * @return
	 */
	public Object executeSacle_PROC(Integer returnType,String sql, Object... paraValue) {
        return jdbcTemplate.execute(new SchoolCallableStatementCreator(sql.toString(),paraValue),
                new SchoolCallableStatementCallback(false,paraValue==null?0:paraValue.length));
	}


	/*
	 * (non-Javadoc)
	 * 执行不带返回参数的存储过程
	 * @see com.school.dao.base.ICommonDAO#executeQuery_PROC(java.lang.String,
	 * java.lang.Object)
	 */
	public boolean executeQuery_PROC(String sql, Object... paraValue) {
        jdbcTemplate.update(sql,paraValue);
        return true;
	}


	/*
	 * (non-Javadoc)
	 * 执行多个存储过程。
	 * @see
	 * com.school.dao.base.ICommonDAO#executeArrayQuery_PROC(java.util.List,
	 * java.util.List)
	 */
    @Transactional
	public boolean executeArrayQuery_PROC(List sqlbuilder, List paraV) {
        boolean returnVal=true;
			for (int i = 0; i < sqlbuilder.size(); i++) {
				Object sql= sqlbuilder.get(i);
				if (sql != null && !sql.toString().equals("")) {
                    List p=(List)paraV.get(i);
                    Object afficeRows=jdbcTemplate.execute(new SchoolCallableStatementCreator(sql.toString(),p==null?null:p.toArray()),
                           new SchoolCallableStatementCallback(false,p==null?0:p.size()));
//                    IN=jdbcTemplate.update(sql.toString(),p==null?null:p.toArray());
					if(afficeRows==null||!UtilTool.isNumber(afficeRows.toString())
                           ||Integer.parseInt(afficeRows.toString().trim())<1){
                        returnVal=false;
                        break;
                    }
				}
			}
        return returnVal;
	}

//    protected  void lockTable(Connection conn,String tableName){
//        String sql = "lock tables "+tableName+" write";
//        PreparedStatement pstatement = null;
//        try{
//            this.unlockTable(conn);
//            pstatement=conn.prepareStatement(sql);
//            pstatement.executeQuery();
//        }catch (Exception e){
//            this.unlockTable(conn);
//            log.debug(e.getMessage());
//            e.printStackTrace();
//        }finally {
//            try {
//                if (pstatement != null)
//                    pstatement.close();
////                if (conn != null)
////                    conn.close();
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }



//    protected  void unlockTable(Connection conn){
//        String sql = " UNLOCK TABLES ";
//        PreparedStatement pstatement = null;
//        try{
//            pstatement=conn.prepareStatement(sql);
//            pstatement.executeQuery();
//        }catch (Exception e){
//            log.debug(e.getMessage());
//            e.printStackTrace();
//        }finally {
//            try {
//                if (pstatement != null)
//                    pstatement.close();
////                if (conn != null)
////                    conn.close();
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }




	/*
	 * (non-Javadoc)
	 * 执行SQL语句,返回List<Map<String,Object>>
	 * @see com.school.dao.base.ICommonDAO#queryListMap_SQL(java.lang.String,
	 * java.util.List)
	 */
	public List<Map<String, Object>> queryListMap_SQL(String sql,
			List<Object> objPara) {
        return (List)jdbcTemplate.execute(new SchoolCallableStatementCreator(false,sql,objPara==null?null:objPara.toArray()),
                new SchoolCallableStatementCallback(null,objPara.size(),Map.class)
        );
	}

	/*
	 * (non-Javadoc)
	 * 执行SQL语句，返回实体List
	 * @sql      SQL语句
	 * @objPara  参数集合
	 * @cls  实体对象（例：User.class）
	 * @see com.school.dao.base.ICommonDAO#queryEntity_SQL(java.lang.String,
	 * java.util.List, java.lang.Class)
	 */
	public List queryEntity_SQL(String sql, List<Object> objPara,
			Class<? extends Object> cls) {
		return jdbcTemplate.queryForList(sql,objPara,cls);
	}

	/*
	 * (non-Javadoc)
	 * 批量执行SQL语句(更新，添加，删除等批量操作)
	 * @see com.school.dao.base.ICommonDAO#executeArray_SQL(java.util.List,
	 * java.util.List)
	 */
	public boolean executeArray_SQL(List sqlbuilder, List paraV) {
        boolean returnVal=true;
        for (int i = 0; i < sqlbuilder.size(); i++) {
            Object sql= sqlbuilder.get(i);
            if (sql != null && !sql.toString().equals("")) {
                if(jdbcTemplate.queryForInt(sql.toString(),paraV.toArray())<1){
                    returnVal=false;
                    break;
                }
            }
        }
        return returnVal;
	}

	/*
	 * (non-Javadoc)
	 * 执行SQL查询，返回List<map<String,Object>>
	 * @see
	 * com.school.dao.base.ICommonDAO#executeResultListMap_PROC(java.lang.String
	 * , java.util.List)
	 */
	public List<Map<String, Object>> executeResultListMap_PROC(String sql,
			List<Object> paraV) {
        return (List)jdbcTemplate.execute(new SchoolCallableStatementCreator(false,sql,paraV==null?null:paraV.toArray()),
                new SchoolCallableStatementCallback(null,paraV.size(),Map.class)
        );
	}

	/*
	 * (non-Javadoc)
	 * 执行存储过程，返回List<实体>集合
	 * @sql SQL语句
	 * @paraV  参数集合
	 * @oracleType  返回参数类型(一般是INT，CURSOR两种)
	 * @clazz  实体对象集合
	 * @reObj  返回的reObj值
	 * @see com.school.dao.base.ICommonDAO#executeResult_PROC(java.lang.String,
	 * java.util.List, java.util.List, java.lang.Class, java.lang.Object[])
	 */
	public List executeResult_PROC(String sql, List paraV,
			List<Integer> oracleType, Class<? extends Object> clazz,
			Object[] reObj) {
        boolean hasPage=true;
        if(reObj==null||reObj.length<1||oracleType==null)
            hasPage=false;

		return (List)jdbcTemplate.execute(new SchoolCallableStatementCreator(hasPage,sql,paraV==null?null:paraV.toArray()),
                new SchoolCallableStatementCallback(reObj,paraV==null?0:paraV.size(),clazz)
               );
	}


	/*
	 * (non-Javadoc)AUTHOR:郑舟
	 * 执行SQL语句,返回List<List<String>>
	 * @see com.school.dao.base.ICommonDAO#executeList_SQL(java.lang.String,
	 * java.lang.Object[])
	 */
	public List<List<String>> executeList_SQL(String sql, Object[] paraValue) {


        return (List)jdbcTemplate.execute(new SchoolCallableStatementCreator(sql,paraValue),
                new SchoolCallableStatementCallback()
        );
	}



	/**
	 * 执行数据库的FUNCTION 返回Object
	 * @param sql
	 * @param paraV
	 * @return Object
	 */
	public Object executeFunction(String sql, List paraV) {
        return jdbcTemplate.execute(new SchoolCallableStatementCreator(sql,paraV),
                new CallableStatementCallback<Object>() {
                    @Override
                    public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                        cs.execute();
                        return cs.getObject(1);

                    }
                }
        );
	}

	/*
	 * (non-Javadoc) 执行存储过程，并返回结果集List<List<String>>
	 * 
	 * @see
	 * com.bhsf.oa.dao.impl.ICommonDAO#executeArratProcedure(java.util.List,
	 * java.util.List)
	 */
	public List<List<String>> executeResultProcedure(String sql, List paraV) {
		// 配置
        return (List<List<String>>)jdbcTemplate.execute(new SchoolCallableStatementCreator(sql,paraV),
                new SchoolCallableStatementCallback());
	}

    /**
     * 执行FUNCTION nextval，得到下一个ID
     * @param seqname
     * @return
     */
	public Integer getBaseNextId(String seqname) {
		if (seqname == null || seqname.length() < 1)
			return null;
		StringBuilder sqlbuilder = new StringBuilder("{call ?=nextval(?)}");
		List<Object> objList = new ArrayList<Object>();
		objList.add(seqname);
		Object object = this.executeFunction(sqlbuilder.toString(), objList);
		if (object != null && object.toString().trim().length() > 0)
			return Integer.parseInt(object.toString().trim());
		return null;

	}

    /**
     * 批量执行PROCEDURE
     * @param sqlArrayList List<String>对象
     * @param objArrayList List<List<Object>>对象
     * @return
     */
	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.executeArrayQuery_PROC(sqlArrayList, objArrayList);
	}

    /**
     * 得到下一个UUID
     * @return
     */
	public String getNextId() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 更新长字段(clob,text,blob等)
	 * @param tblname 表名
     * @param refName  主键列称
	 * @param columnsName 更新的列名
	 * @param value      对应的值
	 * @param ref   主键值
	 * @param sqlList
	 * @param objListArray
	 */
	public void getArrayUpdateLongText(String tblname, String refName,
			String columnsName, String value, String ref,
			List<String> sqlList, List<List<Object>> objListArray) {
		int beishu = 1;
		if (value == null)
			return;
		if (value != null && value.trim().length() > 2000)
			beishu = value.trim().length() / 2000
					+ (value.trim().length() % 2000 > 0 ? 1 : 0);
		StringBuilder sqlbuilder = new StringBuilder();

		for (int i = 0; i < beishu; i++) {
			sqlbuilder = new StringBuilder();
			String tmpVal = value.trim().substring(i * 2000);
			if (i != beishu - 1)
				tmpVal = value.trim().substring(i * 2000, i * 2000 + 2000);
			List<Object> objList = getUpdateLongText(tblname, refName,
					columnsName, tmpVal, ref, sqlbuilder);
			if (sqlbuilder != null && sqlbuilder.toString().trim().length() > 0) {
				sqlList.add(sqlbuilder.toString());
				objListArray.add(objList);
			}
		}
	}

	public List<Object> getUpdateLongText(String tblname, String refName,
			String columnsName, String value, String topicid,
			StringBuilder sqlbuilder) {
		sqlbuilder
				.append("{CALL update_long_content_pro(?,?,?,?,?,?)}");
		List<Object> objList = new ArrayList<Object>();
		objList.add(tblname);
		objList.add(refName);
		objList.add(topicid);
		objList.add(columnsName);
		objList.add(value.replaceAll("'","\\\""));
		return objList;
	}
	/**
	 * 数量累计
	 * @param tblname
	 * @param refname
	 * @param columnName
	 * @param topicid
	 * @param sqlbuilder
	 * @return
	 */
	public List<Object> getUpdateNumAdd(String tblname,String refname,
			String columnName,String topicid,Integer type,StringBuilder sqlbuilder){
		sqlbuilder.append("{CALL update_number_pro(?,?,?,?,?,?)}");
		List<Object> objList = new ArrayList<Object>();
		objList.add(tblname);
		objList.add(refname);
		objList.add(topicid);
		objList.add(columnName);
		objList.add(type);
		return objList;
	}

	/**
	 * 执行添加Log
	 * 
	 * @param operateUserref
	 *            操作人
	 * @param table
	 *            操作的表
	 * @param rowsid
	 *            操作的主键ID
	 * @param freevalue
	 *            操作之前的值是什么
	 * @param currentVal
	 *            操作之后的值是什么
	 * @param operateType
	 *            操作类型： add:添加 update:修改 delete:删除
	 * @param remark
	 *            备注
	 * @return
	 */
	public Boolean executeAddOperateLog(String operateUserref, String table,
			String rowsid, String freevalue, String currentVal,
			String operateType, String remark) {
		StringBuilder sql = new StringBuilder();
		List<Object> objList = this.getAddOperateLog(operateUserref,table,rowsid,freevalue,currentVal,operateType,remark,sql);
		Object obj= this.executeSacle_PROC(sql.toString(), objList.toArray());
		if(obj==null||obj.toString().trim().length()<1||Integer.parseInt(obj.toString().trim())<1)
			return false;
		return true;
	}

	/**
	 * 得到添加Log
	 * 
	 * @param operateUserref
	 *            操作人
	 * @param table
	 *            操作的表
	 * @param rowsid
	 *            操作的主键ID
	 * @param freevalue
	 *            操作之前的值是什么
	 * @param currentVal
	 *            操作之后的值是什么
	 * @param operateType
	 *            操作类型： add:添加 update:修改 delete:删除
	 * @param remark
	 *            备注
	 * @return
	 */
	public List<Object> getAddOperateLog(String operateUserref, String table,
			String rowsid, String freevalue, String currentVal,
			String operateType, String remark,StringBuilder sqlbuilder) {
		if(sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL operate_log_info_add(");
		List<Object> objList = new ArrayList<Object>();
		if(operateUserref!=null){
			objList.add(operateUserref);
			sqlbuilder.append("?,");
		}else
			sqlbuilder.append("NULL,");
		if(table!=null){
			objList.add(table);
			sqlbuilder.append("?,");
		}else
			sqlbuilder.append("NULL,");
		if(rowsid!=null){
			objList.add(rowsid);
			sqlbuilder.append("?,");
		}else
			sqlbuilder.append("NULL,");
		if(freevalue!=null){
			objList.add(freevalue);
			sqlbuilder.append("?,");
		}else
			sqlbuilder.append("NULL,");
		if(currentVal!=null){
			objList.add(currentVal);
			sqlbuilder.append("?,");
		}else
			sqlbuilder.append("NULL,");
		if(operateType!=null){
			objList.add(operateType);
			sqlbuilder.append("?,");
		}else
			sqlbuilder.append("NULL,");
		if(remark!=null){
			objList.add(remark);
			sqlbuilder.append("?,");
		}else
			sqlbuilder.append("NULL,");		
		sqlbuilder.append("?)}");


		return objList;
	} 
	
	/**
	 * 得到添加Log
	 * 
	 * @param operateUserref
	 *            操作人
	 * @param table
	 *            操作的表
	 * @param rowsid
	 *            操作的主键ID	
	 * @param operateType
	 *            操作类型： add:添加 update:修改 delete:删除	
	 * @return
	 */
	public List<Object> getDelOperateLog(String operateUserref, String table,
			String rowsid,
			String operateType,StringBuilder sqlbuilder) {
		if(sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL operate_log_info_delete(");
		List<Object> objList = new ArrayList<Object>();
		if(operateUserref!=null){
			objList.add(operateUserref);
			sqlbuilder.append("?,");
		}else
			sqlbuilder.append("NULL,");
		if(table!=null){
			objList.add(table);
			sqlbuilder.append("?,");
		}else
			sqlbuilder.append("NULL,");
		if(rowsid!=null){
			objList.add(rowsid);
			sqlbuilder.append("?,");
		}else
			sqlbuilder.append("NULL,");
		
		if(operateType!=null){
			objList.add(operateType);
			sqlbuilder.append("?,");
		}else
			sqlbuilder.append("NULL,");
		
		sqlbuilder.append("?)}");
		return objList;
	}


    private static String getFixLenthString(int strLength) {

        Random rm = new Random();

        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);

        // 返回固定的长度的随机数
        return fixLenthString.substring(1, strLength + 1);
    }


    /**
     * 得到下一个序列值
     *@param bo 正负数，true:负数  false:正数
     * @return
     */
    public Long getNextId(boolean bo){
        String id=((Long)System.currentTimeMillis()).toString()+""+getFixLenthString(6);
        id=id.substring(6);
        Long nextid=Long.parseLong(id);
        if(bo)
            nextid=Long.parseLong("-"+nextid);
        return nextid;
    }
    /*
    * (non-Javadoc)
    * 实体对象映射
    * @see com.school.dao.base.ICommonDAO#entityConvertor(java.sql.ResultSet,
    * java.lang.Class)
    */
    public List entityConvertor(ResultSet rs, Class<? extends Object> clazz)
            throws Exception {
        List returnVal = new ArrayList();
        if (rs != null) {
            ResultSetMetaData rsdata = rs.getMetaData();
            // 获取列明
            String[] columnNames = new String[rsdata.getColumnCount()];
            for (int i = 0; i < rsdata.getColumnCount(); i++) {
                columnNames[i] = rsdata.getColumnLabel(i + 1);
            }
            // 将数据进行封装
            while (rs.next()) {
                Object obj = null;
                Map<String, Object> objMap = null;
                List<Object> objList = null;
                if (clazz != null)
                    if (!clazz.getName().trim().equals(
                            HashMap.class.getName().trim()))
                        obj = clazz.newInstance();
                    else
                        objMap = new HashMap<String, Object>();
                else
                    // 如果不是Map和实体类型,则返回List
                    objList = new ArrayList<Object>();
                for (int j = 0; j < rsdata.getColumnCount(); j++) {
                    // 得到值
                    String dataType = rsdata.getColumnTypeName(j + 1);
                    Object valObj = null;
                    if (dataType.trim().equals("CLOB"))
                        valObj = UtilTool.clobToString(rs.getClob(j + 1)); // 转成String
                    else if (dataType.trim().equals("BLOB"))
                        valObj = UtilTool.blobToString(rs.getBlob(j + 1)); // 转成String
                    else if (dataType.trim().toUpperCase().equals("DATE")
                            || dataType.trim().toUpperCase()
                            .equals("TIMESTAMP")
                            || dataType.trim().toUpperCase().equals("DATETIME"))
                        if (clazz == null)
                            valObj = UtilTool.DateConvertToString(rs
                                    .getTimestamp(j + 1),
                                    com.school.util.UtilTool.DateType.type1);
                        else{
                            try{valObj = (Date) rs.getTimestamp(j + 1);}catch(Exception e){}
                        }
                    else
                        valObj = rs.getObject(j + 1);
                    if (objList != null) {
                        objList.add(valObj);
                    } else {
                        if (objMap != null) {
                            objMap.put(rsdata.getColumnName(j + 1)
                                    .toUpperCase(), valObj);
                        }
                    }
                    // 进行封装成Entity
                    if (clazz != null && obj != null) {
                        Method[] methods = clazz.getMethods(); // 获取
                        // 该类的所有方法
                        // 查找所有方法中的set方法 利用反射把取出的字段通过set方法对对象中的字段赋值
                        String colName = columnNames[j];
                        // 获取Set方法名字
                        String methodName = "set"
                                + colName.substring(0, 1).toUpperCase() // 第1个大写
                                + colName.substring(1).toLowerCase().replace(
                                "_", "");

                        if (valObj != null) {
                            for (Method m : methods) {
                                //进行全额小写比较，不区别大小写
                                if (methodName.toLowerCase().equals(m.getName().toLowerCase())) {
                                    // System.out.println(methodName+"   "+m.getParameterTypes()[0].toString()+"  "+valObj);
                                    Object val=valObj;
                                    //  System.out.println(m.getName()+"  "+val);
                                    if(m.getParameterTypes()!=null&&m.getParameterTypes().length>0&&val!=null&&
                                            !m.getParameterTypes()[0].getSimpleName().toLowerCase().equals("string")
                                            &&!m.getParameterTypes()[0].getSimpleName().toLowerCase().equals("date"))
                                        val= ConvertUtils.convert(val.toString(), m.getParameterTypes()[0]);

                                    m.invoke(obj, val); // obj:被实例化的类
                                    break;
                                }
                            }
                        }
                    }
                }
                if (obj == null) {
                    if (objList != null)
                        returnVal.add(objList);
                    else if (objMap != null)
                        returnVal.add(objMap);
                } else
                    returnVal.add(obj);
            }
        }
        return returnVal;
    }

}

/**
 * 重写执行存储过程前的相关方法
 */
class SchoolCallableStatementCreator implements CallableStatementCreator{
    private String sql;
    private Object[] objPara;
    private boolean hasPageReturn=false;
    public SchoolCallableStatementCreator(String sql,Object... objPara){
        this.sql=sql;
        this.objPara=objPara;
    }
    public SchoolCallableStatementCreator(boolean hasPageReturn,String sql,Object... objPara){
        this.sql=sql;
        this.objPara=objPara;
        this.hasPageReturn=hasPageReturn;
    }
    public SchoolCallableStatementCreator(){}

    @Override
    public CallableStatement createCallableStatement(Connection connection) throws SQLException {
        return getParedStatement(connection, sql, objPara);
    }

    /**
     * 给PrepareStatement 赋参
     *
     * @param paraValue
     * @throws Exception
     */
    private static CallableStatement setStateParameter(CallableStatement pstatement,
                                                Object... paraValue) throws SQLException{
        if (paraValue == null || paraValue.length < 1) {
            return pstatement;
        }
            for (int i = 0; i < paraValue.length; i++) { // 赋参
                if(paraValue[i]==null)continue;
                if (paraValue[i].getClass().getSimpleName().equals("String")) {
                    pstatement.setString(i + 1, paraValue[i].toString());
                } else if (paraValue[i].getClass().getSimpleName().equals(
                        "Blob")) {
                    pstatement.setBlob(i + 1, (Blob) paraValue[i]);
                } else {
                    pstatement.setObject(i + 1, paraValue[i]);
                }
            }

            return pstatement;
    }
    /*
         * (non-Javadoc)
         * 执行得到ParedStatement
         * @see
         * com.school.dao.base.ICommonDAO#getParedStatement(java.sql.Connection,
         * java.sql.PreparedStatement, java.lang.String, java.lang.Object)
         */
    public CallableStatement getParedStatement(Connection conn ,
                                               String sql, Object... paraValue)throws SQLException{
        CallableStatement  pstatement = conn.prepareCall(sql);
        if (paraValue != null)
            setStateParameter(pstatement, paraValue);
        if(hasPageReturn)
             pstatement.registerOutParameter(paraValue.length+1,Types.INTEGER);
        return pstatement;
    }
}

/**
 * 执行复杂的查询完成后的返回方法
 */
class  SchoolCallableStatementCallback implements  CallableStatementCallback<Object> {
    private Object[] pageObj=null;
    private Class<? extends  Object> clzz=null;
    private int pageSize=-1;
    private boolean isSearch=true;
    public SchoolCallableStatementCallback(Object[] pageObj,int pageSize,Class<? extends  Object> clzz){
        this.pageObj=pageObj;
        this.clzz=clzz;
        this.pageSize=pageSize;
    }
    public SchoolCallableStatementCallback(boolean isSearch,int pageSize){
        this.isSearch=isSearch;
        this.pageSize=pageSize;
    }
    public SchoolCallableStatementCallback(Class<? extends  Object> clzz){
        this.clzz=clzz;
    }
    public SchoolCallableStatementCallback(){}

    @Override
    public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
        if(isSearch){
            ResultSet rs=cs.executeQuery();//执行
            //得到页码
            if(pageSize>-1&&pageObj!=null&&pageObj.length>0)//大于0表示有返回参数
                pageObj[0]=cs.getObject(pageSize+1);
            List returnList=null;
            try {
                if(clzz==Map.class){
                    returnList=new ArrayList();
                    if (rs != null) {
                        ResultSetMetaData rsdata = rs.getMetaData();
                        // 获取列明
                        while (rs.next()) {
                            Map<String, Object> tmpMap = new HashMap<String, Object>();
                            for (int j = 0; j < rsdata.getColumnCount(); j++) {
                                String columnStr = rsdata.getColumnLabel(j + 1);
                                // 得到类型，进行转换
                                String dataType = rsdata.getColumnTypeName(j + 1);

                                Object valObj = null;
                                if (dataType.trim().equals("CLOB")
                                        || dataType.trim().equals("BLOB"))
                                    valObj = UtilTool.clobToString(rs.getClob(j + 1)); // 转成String
                                else if (dataType.trim().toUpperCase().equals("DATE"))
                                    valObj = UtilTool.DateConvertToString(rs
                                            .getTimestamp(j + 1),
                                            com.school.util.UtilTool.DateType.type1);
                                else
                                    valObj = rs.getObject(j + 1);

                                tmpMap.put(columnStr.toUpperCase(), valObj);
                            }
                            if (!tmpMap.isEmpty())
                                returnList.add(tmpMap);
                        }
                    }
                }else if(clzz!=null){
                    returnList=entityConvertor(rs,clzz);
                }else{
                    returnList=new ArrayList();
                    if (rs != null) {
                        ResultSetMetaData rsdata = rs.getMetaData();
                        while (rs != null && rs.next()) {
                            List<String> strList = new ArrayList<String>();
                            for (int j = 0; j < rsdata.getColumnCount(); j++) {
                                String dataType = rsdata.getColumnTypeName(j + 1);
                                if (dataType.trim().equals("CLOB")
                                        || dataType.trim().equals("BLOB"))
                                    strList.add(UtilTool.clobToString(rs
                                            .getClob(j + 1)));
                                else if (dataType.trim().toUpperCase().equals(
                                        "DATE"))
                                    strList.add(UtilTool.DateConvertToString(
                                            (Date) rs.getTimestamp(j + 1),
                                            DateType.type1));
                                else
                                    strList.add(rs.getString(j + 1));
                            }
                            if (rsdata.getColumnCount() > 0)
                                returnList.add(strList);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                rs.close();
            }

            return returnList;
        }else{
            cs.execute();
            Object returnVal=null;
            //得到页码
            if(pageSize>-1)//大于0表示有返回参数
                returnVal=cs.getObject(pageSize+1);
            return returnVal;
        }
    }
    /*
        * (non-Javadoc)
        * 实体对象映射
        * @see com.school.dao.base.ICommonDAO#entityConvertor(java.sql.ResultSet,
        * java.lang.Class)
        */
    public List entityConvertor(ResultSet rs, Class<? extends Object> clazz)
            throws Exception {
        List returnVal = new ArrayList();
        if (rs != null) {
            ResultSetMetaData rsdata = rs.getMetaData();
            // 获取列明
            if(rsdata==null||rsdata.getColumnCount()<1)
                return null;
            String[] columnNames = new String[rsdata.getColumnCount()];
            for (int i = 0; i < rsdata.getColumnCount(); i++) {
                columnNames[i] = rsdata.getColumnLabel(i + 1);
            }
            // 将数据进行封装
            while (rs.next()) {
                Object obj = null;
                Map<String, Object> objMap = null;
                List<Object> objList = null;
                if (clazz != null)
                    if (!clazz.getName().trim().equals(
                            HashMap.class.getName().trim()))
                        obj = clazz.newInstance();
                    else
                        objMap = new HashMap<String, Object>();
                else
                    // 如果不是Map和实体类型,则返回List
                    objList = new ArrayList<Object>();
                for (int j = 0; j < rsdata.getColumnCount(); j++) {
                    // 得到值
                    String dataType = rsdata.getColumnTypeName(j + 1);
                    Object valObj = null;
                    if (dataType.trim().equals("CLOB"))
                        valObj = UtilTool.clobToString(rs.getClob(j + 1)); // 转成String
                    else if (dataType.trim().equals("BLOB"))
                        valObj = UtilTool.blobToString(rs.getBlob(j + 1)); // 转成String
                    else if (dataType.trim().toUpperCase().equals("DATE")
                            || dataType.trim().toUpperCase()
                            .equals("TIMESTAMP")
                            || dataType.trim().toUpperCase().equals("DATETIME"))
                        if (clazz == null)
                            valObj = UtilTool.DateConvertToString(rs
                                    .getTimestamp(j + 1),
                                    com.school.util.UtilTool.DateType.type1);
                        else{
                            try{valObj = (Date) rs.getTimestamp(j + 1);}catch(Exception e){}
                        }
                    else
                        valObj = rs.getObject(j + 1);
                    if (objList != null) {
                        objList.add(valObj);
                    } else {
                        if (objMap != null) {
                            objMap.put(rsdata.getColumnName(j + 1)
                                    .toUpperCase(), valObj);
                        }
                    }
                    // 进行封装成Entity
                    if (clazz != null && obj != null) {
                        Method[] methods = clazz.getMethods(); // 获取
                        // 该类的所有方法
                        // 查找所有方法中的set方法 利用反射把取出的字段通过set方法对对象中的字段赋值
                        String colName = columnNames[j];
                        // 获取Set方法名字
                        String methodName = "set"
                                + colName.substring(0, 1).toUpperCase() // 第1个大写
                                + colName.substring(1).toLowerCase().replace(
                                "_", "");

                        if (valObj != null) {
                            for (Method m : methods) {
                                //进行全额小写比较，不区别大小写
                                if (methodName.toLowerCase().equals(m.getName().toLowerCase())) {
                                    // System.out.println(methodName+"   "+m.getParameterTypes()[0].toString()+"  "+valObj);
                                    Object val=valObj;
                                    //  System.out.println(m.getName()+"  "+val);
                                    if(m.getParameterTypes()!=null&&m.getParameterTypes().length>0&&val!=null&&
                                            !m.getParameterTypes()[0].getSimpleName().toLowerCase().equals("string")
                                            &&!m.getParameterTypes()[0].getSimpleName().toLowerCase().equals("date"))
                                        val= ConvertUtils.convert(val.toString(), m.getParameterTypes()[0]);

                                    m.invoke(obj, val); // obj:被实例化的类
                                    break;
                                }
                            }
                        }
                    }
                }
                if (obj == null) {
                    if (objList != null)
                        returnVal.add(objList);
                    else if (objMap != null)
                        returnVal.add(objMap);
                } else
                    returnVal.add(obj);
            }
        }
        return returnVal;
    }

}
