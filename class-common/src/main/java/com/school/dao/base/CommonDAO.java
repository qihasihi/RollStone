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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.logging.*;
import org.springframework.stereotype.Component;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

/**
 * CopyRright (c)2003-2010 ��Ŀ����: oa �ļ�����: CommonDAO.java ����: ʹ�õ�JDK�汾: JDK1.6
 * ����: com.bhsf.oa.dao.impl �� �� �ˣ� ZHENGZHOU �������ڣ� 2010-4-26 �޸��ˣ� �޸�����: �޸�ԭ������
 * �汾��: 1.1
 */
@Component
public abstract class CommonDAO<T> implements ICommonDAO<T> {
	protected final Log log = LogFactory.getLog(getClass());
	private CallableStatement cbstate = null;

	/**
	 * �õ� jndiContext
	 * 
	 * @return
	 * @throws Exception
	 */
	private Context jndiLookup() throws Exception {
		InitialContext ctx = new InitialContext();
		return (Context) ctx.lookup("java:comp/env");
	}

	/**
	 * �õ�DataSource ����
	 * 
	 * @param envCtx
	 *            JNDI ���õ�Context
	 * @return DataSource����
	 * @throws NamingException
	 */
	private DataSource getDateSource(Context envCtx) {
		// if (datasource != null)
		// return datasource;
		if (null == envCtx)
			return null;
		try {
			return (DataSource) envCtx.lookup("jdbc/mschool");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����DataSource �õ�Connection���� ������������
	 * 
	 * @param ds
	 *            DataSource����Դ
	 * @return
	 */
	private Connection getConnection(DataSource ds) {
		if (ds == null)
			return null;
		try {
			// if(conn!=null){conn.return conn;}
			return ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �м䷽�������ڵõ�ͳһ���ݿ����ӡ�
	 * 
	 * @return
	 */
	private Connection connectionDB() {
		try {
			return getConnection(getDateSource(jndiLookup()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��PrepareStatement ����
	 * 
	 * @param paraValue
	 * @throws Exception
	 */
	private PreparedStatement setStateParameter(PreparedStatement pstatement,
			Object... paraValue) throws Exception {

		if (paraValue == null || paraValue.length < 1) {
			return null;
		}
		try {
			for (int i = 0; i < paraValue.length; i++) { // ����
				if (paraValue[i].getClass().getSimpleName().equals("String")) {
					pstatement.setString(i + 1, paraValue[i].toString());
				} else if (paraValue[i].getClass().getSimpleName().equals(
						"Blob")) {
					pstatement.setBlob(i + 1, (Blob) paraValue[i]);
				} else {
					pstatement.setObject(i + 1, paraValue[i]);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			pstatement = null;
		} finally {
			return pstatement;
		}
	}

    public Object executeSeq_PROC(String sql, Object... paraValue) {
        Object returnVal = null; // ִ��ʧ��
        Connection conn = null;

        conn = connectionDB();
        try {
            conn.setAutoCommit(false);
            /*����*/
            this.lockTable(conn,paraValue[0].toString());
            cbstate = conn.prepareCall(sql);
            if (paraValue != null) {
                for (int i = 0; i < paraValue.length; i++) {
                    cbstate.setObject(i + 1, paraValue[i]);
                }
            }
            cbstate.registerOutParameter((paraValue == null ? 0
                    : paraValue.length) + 1, Types.INTEGER);
            cbstate.execute();
            returnVal = cbstate.getObject((paraValue == null ? 0
                    : paraValue.length) + 1);

            conn.commit();
            conn.setAutoCommit(true);
            /*����*/
            this.unlockTable(conn);
        } catch (SQLException ex) {
            try {
                if (conn != null)
                    conn.rollback();
                this.unlockTable(conn);
            } catch (SQLException e) {
            }
            ex.printStackTrace();

        } finally {
            try {
                if (cbstate != null)
                    cbstate.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                log.debug(e.getMessage());
            }
            return returnVal;
        }
    }

    /*
         * (non-Javadoc)
         * ִ�еõ�ParedStatement
         * @see
         * com.school.dao.base.ICommonDAO#getParedStatement(java.sql.Connection,
         * java.sql.PreparedStatement, java.lang.String, java.lang.Object)
         */
	public PreparedStatement getParedStatement(Connection conn,
			PreparedStatement pstatement, String sql, Object... paraValue) {
		try {
			pstatement = conn.prepareStatement(sql);
			if (paraValue != null)
				setStateParameter(pstatement, paraValue);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug(e.getMessage());
		} finally {
			return pstatement;
		}
	}


	/*
	 * (non-Javadoc)
	 * ִ��SQL��� ����һ������
	 * @see com.school.dao.base.ICommonDAO#executeSalar_SQL(java.lang.String,
	 * java.lang.Object[])
	 */
	public Object executeSalar_SQL(String sql, Object[] paraValue) {
		// TODO Auto-generated method stub
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstatement = null;
		conn = connectionDB();
		pstatement = getParedStatement(conn, pstatement, sql, paraValue);
		Object obj = null;
		try {
			rs = pstatement.executeQuery();
			if (rs.next()) {
				obj = rs.getObject(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.debug(e.getMessage());
			obj = null;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstatement != null)
					pstatement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.debug(e.getMessage());

			}
			return obj;
		}
	}


	/*
	 * (non-Javadoc)
	 * ִ�з���һ�������Ĵ洢����
	 * @see com.school.dao.base.ICommonDAO#executeSacle_PROC(java.lang.String,
	 * java.lang.Object)
	 */
	public Object executeSacle_PROC(String sql, Object... paraValue) {
        Object returnVal = null; // ִ��ʧ��
        Connection conn = null;

        conn = connectionDB();
        try {
            conn.setAutoCommit(false);
            cbstate = conn.prepareCall(sql);
            if (paraValue != null) {
                for (int i = 0; i < paraValue.length; i++) {
                    cbstate.setObject(i + 1, paraValue[i]);
                }
            }
            cbstate.registerOutParameter((paraValue == null ? 0
                    : paraValue.length) + 1, Types.INTEGER);
            cbstate.execute();
            returnVal = cbstate.getObject((paraValue == null ? 0
                    : paraValue.length) + 1);

            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException e) {
            }
            ex.printStackTrace();

        } finally {
            try {
                if (cbstate != null)
                    cbstate.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                log.debug(e.getMessage());
            }
            return returnVal;
        }
    }



	
	/**
	 * ִ�еõ����Ӧ�ķ���ֵ 
	 * @param returnType
	 * @param sql
	 * @param paraValue
	 * @return
	 */
	public Object executeSacle_PROC(Integer returnType,String sql, Object... paraValue) {
		Object returnVal = null; // ִ��ʧ��
		Connection conn = null;

		conn = connectionDB();
		try {
			conn.setAutoCommit(false);
			cbstate = conn.prepareCall(sql);
			if (paraValue != null) {
				for (int i = 0; i < paraValue.length; i++) {
					cbstate.setObject(i + 1, paraValue[i]);
				}
			}
			cbstate.registerOutParameter((paraValue == null ? 0
					: paraValue.length) + 1,returnType);
			cbstate.execute();
			if(returnType!=null&&returnType==Types.INTEGER)
				returnVal = cbstate.getInt((paraValue == null ? 0
						: paraValue.length) + 1);
			else
				returnVal = cbstate.getObject((paraValue == null ? 0
						: paraValue.length) + 1);
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException ex) {
			try {
				if (conn != null)
					conn.rollback();
			} catch (SQLException e) {
			}
			ex.printStackTrace();
		
		} finally {
			try {
				if (cbstate != null)
					cbstate.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.debug(e.getMessage());
			}
			return returnVal;
		}
	}


	/*
	 * (non-Javadoc)
	 * ִ�в������ز����Ĵ洢����
	 * @see com.school.dao.base.ICommonDAO#executeQuery_PROC(java.lang.String,
	 * java.lang.Object)
	 */
	public boolean executeQuery_PROC(String sql, Object... paraValue) {
		Object returnVal = null; // ִ��ʧ��
		Connection conn = null;

		conn = connectionDB();
		try {
			conn.setAutoCommit(false);
			cbstate = conn.prepareCall(sql);
			if (paraValue != null) {
				for (int i = 0; i < paraValue.length; i++) {
					cbstate.setObject(i + 1, paraValue[i]);
				}
			}
			cbstate.execute();

			conn.commit();
			conn.setAutoCommit(true);
			return true;
		} catch (SQLException ex) {

			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.debug(e.getMessage());
				return false;
			}
			log.debug(ex.getMessage());
			return false;

		} finally {
			try {
				if (cbstate != null)
					cbstate.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.debug(e.getMessage());
			}
		}

	}


	/*
	 * (non-Javadoc)
	 * ִ�ж���洢���̡�
	 * @see
	 * com.school.dao.base.ICommonDAO#executeArrayQuery_PROC(java.util.List,
	 * java.util.List)
	 */
	public boolean executeArrayQuery_PROC(List sqlbuilder, List paraV) {
		CallableStatement cs = null;
		Connection conn = null;
		conn = connectionDB();
        List p=null;Object sql=null;
        try {
			conn.setAutoCommit(false);
			for (int i = 0; i < sqlbuilder.size(); i++) {
				 sql= sqlbuilder.get(i);
				if (sql != null && !sql.toString().equals("")) {
					p= (List) paraV.get(i);
					cs = conn.prepareCall(sql.toString().trim());
					if (p != null) {
						for (int j = 0; j < p.size(); j++) {
							
							cs.setObject(j + 1,p.get(j).toString().replaceAll("'", "��"));
                            System.out.println(p.get(j).toString().replaceAll("'", "��"));
						}
					}
                    System.out.print("sql:" + sql + "result:");
					cs.registerOutParameter((p == null ? 0: p.size())+1,Types.INTEGER);

					cs.execute() ;
					Object returnObj = cs
							.getObject((p == null ? 0 : p.size()) + 1);
                   System.out.println(returnObj);

					cs.close();
					if (returnObj == null||Integer.parseInt(returnObj.toString().trim()) < 1) {
						conn.rollback();
						return false;
					}
				}
			}
			conn.commit();
		//	conn.setAutoCommit(true);
			return true;

		} catch (SQLException e) {
            System.out.println(sql+"  ");
			log.debug(e.getMessage());
			try {
				conn.rollback();
				if (cs != null)
					cs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				log.debug(e1.getMessage());
			}
            e.printStackTrace();
			return false;
		} finally {
			try {
				if (cs != null)
					cs.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.debug(e.getMessage());

			}

		}

	}

    protected  void lockTable(Connection conn,String tableName){
        String sql = "lock tables "+tableName+" write";
        PreparedStatement pstatement = null;
        try{
            this.unlockTable(conn);
            pstatement=conn.prepareStatement(sql);
            pstatement.executeQuery();
        }catch (Exception e){
            this.unlockTable(conn);
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (pstatement != null)
                    pstatement.close();
//                if (conn != null)
//                    conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }



    protected  void unlockTable(Connection conn){
        String sql = " UNLOCK TABLES ";
        PreparedStatement pstatement = null;
        try{
            pstatement=conn.prepareStatement(sql);
            pstatement.executeQuery();
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (pstatement != null)
                    pstatement.close();
//                if (conn != null)
//                    conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }




	/*
	 * (non-Javadoc)
	 * ִ��SQL���,����List<Map<String,Object>>
	 * @see com.school.dao.base.ICommonDAO#queryListMap_SQL(java.lang.String,
	 * java.util.List)
	 */
	public List<Map<String, Object>> queryListMap_SQL(String sql,
			List<Object> objPara) {
		Connection conn = this.connectionDB();
		PreparedStatement pstatement = null;
		pstatement = this.getParedStatement(conn, pstatement, sql,
				objPara == null ? null : objPara.toArray());

		List<Map<String, Object>> returnListMaps = null;
		ResultSet rs = null;
		try {
			rs = pstatement.executeQuery();

			if (rs != null) {
				returnListMaps = new ArrayList<Map<String, Object>>();
				ResultSetMetaData rsdata = rs.getMetaData();
				// ��ȡ����
				while (rs.next()) {
					Map<String, Object> tmpMap = new HashMap<String, Object>();
					for (int j = 0; j < rsdata.getColumnCount(); j++) {
						String columnStr = rsdata.getColumnLabel(j + 1);
						// �õ����ͣ�����ת��
						String dataType = rsdata.getColumnTypeName(j + 1);

						Object valObj = null;
						if (dataType.trim().equals("CLOB")
								|| dataType.trim().equals("BLOB"))
							valObj = UtilTool.clobToString(rs.getClob(j + 1)); // ת��String
						else if (dataType.trim().toUpperCase().equals("DATE"))
							valObj = UtilTool.DateConvertToString(rs
									.getTimestamp(j + 1),
									com.school.util.UtilTool.DateType.type1);
						else
							valObj = rs.getObject(j + 1);

						tmpMap.put(columnStr, valObj);
					}
					if (!tmpMap.isEmpty())
						returnListMaps.add(tmpMap);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return returnListMaps;
	}

	/*
	 * (non-Javadoc)
	 * ִ��SQL��䣬����ʵ��List
	 * @sql      SQL���
	 * @objPara  ��������
	 * @cls  ʵ���������User.class��
	 * @see com.school.dao.base.ICommonDAO#queryEntity_SQL(java.lang.String,
	 * java.util.List, java.lang.Class)
	 */
	public List queryEntity_SQL(String sql, List<Object> objPara,
			Class<? extends Object> cls) {
		Connection conn = this.connectionDB();
		PreparedStatement pstatement = null;
		pstatement = this.getParedStatement(conn, pstatement, sql,
				objPara == null ? null : objPara.toArray());

		List returnListMaps = null;
		ResultSet rs = null;
		try {
			rs = pstatement.executeQuery();

			returnListMaps = entityConvertor(rs, cls);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return returnListMaps;
	}

	/*
	 * (non-Javadoc)
	 * ����ִ��SQL���(���£���ӣ�ɾ������������)
	 * @see com.school.dao.base.ICommonDAO#executeArray_SQL(java.util.List,
	 * java.util.List)
	 */
	public boolean executeArray_SQL(List sqlbuilder, List paraV) {
		Connection conn = connectionDB();
		PreparedStatement pstatement = null;
		int afficeRows = 1;
		try {
			conn.setAutoCommit(false);
			for (int i = 0; i < sqlbuilder.size(); i++) {
				Object sql = sqlbuilder.get(i);
				if (sql != null && !sql.toString().equals("")) {
					Object[] p = (Object[]) paraV.get(i);
					pstatement = getParedStatement(conn, pstatement, sql
							.toString().trim(), p);
					afficeRows = pstatement.executeUpdate();
					if (afficeRows < 1) {
						pstatement.close();
						break;
					}
				}

			}
			if (afficeRows > 0) {
				conn.commit();
				conn.setAutoCommit(true);
				return true;
			} else {
				conn.rollback();
				conn.setAutoCommit(true);
				return false;
			}
		} catch (SQLException e) {
			log.debug(e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				log.debug(e1.getMessage());
			}
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (pstatement != null)
					pstatement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.debug(e.getMessage());
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * ִ��SQL��ѯ������List<map<String,Object>>
	 * @see
	 * com.school.dao.base.ICommonDAO#executeResultListMap_PROC(java.lang.String
	 * , java.util.List)
	 */
	public List<Map<String, Object>> executeResultListMap_PROC(String sql,
			List<Object> paraV) {
		// ����
		CallableStatement cs = null;
		Connection conn = null;
		// ����ֵ
		List<Map<String, Object>> returnVal = new ArrayList<Map<String, Object>>();
		conn = connectionDB();
		try {
			conn.setAutoCommit(false);
			if (sql != null && !sql.toString().equals("")) {
				cs = conn.prepareCall(sql.toString().trim());
				if (paraV != null) {
					for (int j = 0; j < paraV.size(); j++) {
						cs.setObject(j + 1, paraV.get(j));
					}
				}

				ResultSet rs = cs.executeQuery();
				if (rs != null) {
					ResultSetMetaData rsdata = rs.getMetaData();

					while (rs != null && rs.next()) {
						Map<String, Object> objMap = new HashMap<String, Object>();
						for (int j = 0; j < rsdata.getColumnCount(); j++) {

							String dataType = rsdata.getColumnTypeName(j + 1);
							String objectVal = null;
							if (dataType.trim().equals("CLOB")
									|| dataType.trim().equals("BLOB"))
								objectVal = UtilTool.clobToString(rs
										.getClob(j + 1));
							else if (dataType.trim().toUpperCase().equals(
									"DATE"))
								objectVal = UtilTool
										.DateConvertToString(
												(Date) rs.getTimestamp(j + 1),
												com.school.util.UtilTool.DateType.type1);
							else
								objectVal = rs.getString(j + 1);

							objMap.put(rsdata.getColumnName(j + 1)
									.toUpperCase(), objectVal);

						}
						if (objMap.size() > 0)
							returnVal.add(objMap);
					}
					rs.close();
				}
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();

		} finally {
			try {
				if (cs != null)
					cs.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {

			}
			return returnVal;
		}

	}

	/*
	 * (non-Javadoc)
	 * ִ�д洢���̣�����List<ʵ��>����
	 * @sql SQL���
	 * @paraV  ��������
	 * @oracleType  ���ز�������(һ����INT��CURSOR����)
	 * @clazz  ʵ����󼯺�
	 * @reObj  ���ص�reObjֵ
	 * @see com.school.dao.base.ICommonDAO#executeResult_PROC(java.lang.String,
	 * java.util.List, java.util.List, java.lang.Class, java.lang.Object[])
	 */
	public List executeResult_PROC(String sql, List paraV,
			List<Integer> oracleType, Class<? extends Object> clazz,
			Object[] reObj) {
		// ����
		CallableStatement cs = null;
		Connection conn = null;
		// ����ֵ
		List returnVal = new ArrayList();
		conn = connectionDB();

		try {
			conn.setAutoCommit(false);
			if (sql != null && !sql.toString().equals("")) {
				cs = conn.prepareCall(sql.toString().trim());
				if (paraV != null) {
					for (int j = 0; j < paraV.size(); j++) {
						cs.setObject(j + 1, paraV.get(j));
					}
				}
				if (oracleType != null && oracleType.size() > 0) {
					for (int i = 1; i <= oracleType.size(); i++) {
						cs.registerOutParameter((paraV == null ? 0 : paraV
								.size())
								+ i, oracleType.get(i - 1));
					}
				}
				ResultSet rs = cs.executeQuery();
				Object returnObj = null;
				if (oracleType != null && oracleType.size() > 0) {
					int paraVSize = paraV == null ? 0 : paraV.size();
					for (int i = 1; i <= oracleType.size(); i++) {
						if (oracleType.get(i - 1) == Types.CLOB
								|| oracleType.get(i - 1) == Types.BLOB)
							returnObj = UtilTool.clobToString(cs
									.getClob(paraVSize + i));
						else if (oracleType.get(i - 1) == Types.DATE)
							returnObj = (Date) cs.getTimestamp(paraVSize + i);
						else
							returnObj = cs.getObject(paraVSize + i);
						if (returnObj != null && reObj != null
								&& reObj.length > (i - 1))
							// reObj[reObj == null ? 0 : (reObj.length - 1)] =
							// returnObj;
							reObj[reObj == null ? 0 : (i - 1)] = returnObj;
					}
				}
				returnVal = this.entityConvertor(rs, clazz);
				if (rs != null)
					rs.close();
				conn.commit();
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("exception commondao 407!" + e.getMessage());
			try {
				conn.rollback();
				if (cs != null)
					cs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				log.debug(e1.getMessage());
			}
			returnVal = null;
		} finally {
			try {
				if (cs != null)
					cs.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catbug(e.getMessage());
			}
			return returnVal;
		}

	}


	/*
	 * (non-Javadoc)AUTHOR:֣��
	 * ִ��SQL���,����List<List<String>>
	 * @see com.school.dao.base.ICommonDAO#executeList_SQL(java.lang.String,
	 * java.lang.Object[])
	 */
	public List<List<String>> executeList_SQL(String sql, Object[] paraValue) {
		Connection conn = null;
		ResultSet rs = null;
		int numberOfColumns;
		List<List<String>> ls = new ArrayList<List<String>>(900);

		PreparedStatement pstatement = null;
		try {
			conn = connectionDB();

			pstatement = getParedStatement(conn, pstatement, sql, paraValue);
			rs = pstatement.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			numberOfColumns = rsmd.getColumnCount();
			while (rs != null && rs.next()) {
				List<String> list = new ArrayList<String>();
				for (int j = 1; j <= numberOfColumns; j++) {
					list.add(rs.getString(j));
				}
				ls.add(list);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				conn.rollback();
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				log.debug(ex.getMessage());
			}
			log.debug(e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstatement != null)
					pstatement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.debug(e.getMessage());
			}

			return ls;
		}
	}

	/*
	 * (non-Javadoc)
	 * ʵ�����ӳ��
	 * @see com.school.dao.base.ICommonDAO#entityConvertor(java.sql.ResultSet,
	 * java.lang.Class)
	 */
	public List entityConvertor(ResultSet rs, Class<? extends Object> clazz)
			throws Exception {
		List returnVal = new ArrayList();
		if (rs != null) {
			ResultSetMetaData rsdata = rs.getMetaData();
			// ��ȡ����
			String[] columnNames = new String[rsdata.getColumnCount()];
			for (int i = 0; i < rsdata.getColumnCount(); i++) {
				columnNames[i] = rsdata.getColumnLabel(i + 1);
			}
			// �����ݽ��з�װ
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
					// �������Map��ʵ������,�򷵻�List
					objList = new ArrayList<Object>();
				for (int j = 0; j < rsdata.getColumnCount(); j++) {
					// �õ�ֵ
					String dataType = rsdata.getColumnTypeName(j + 1);
					Object valObj = null;
					if (dataType.trim().equals("CLOB"))
						valObj = UtilTool.clobToString(rs.getClob(j + 1)); // ת��String
					else if (dataType.trim().equals("BLOB"))
						valObj = UtilTool.blobToString(rs.getBlob(j + 1)); // ת��String
					else if (dataType.trim().toUpperCase().equals("DATE")
							|| dataType.trim().toUpperCase()
									.equals("TIMESTAMP")
							|| dataType.trim().toUpperCase().equals("DATETIME"))
						if (clazz == null)
							valObj = UtilTool.DateConvertToString(rs
									.getTimestamp(j + 1),
									com.school.util.UtilTool.DateType.type1);
						else
							valObj = (Date) rs.getTimestamp(j + 1);
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
					// ���з�װ��Entity
					if (clazz != null && obj != null) {
						Method[] methods = clazz.getMethods(); // ��ȡ
						// ��������з���
						// �������з����е�set���� ���÷����ȡ�����ֶ�ͨ��set�����Զ����е��ֶθ�ֵ
						String colName = columnNames[j];
						// ��ȡSet��������
						String methodName = "set"
								+ colName.substring(0, 1).toUpperCase() // ��1����д
								+ colName.substring(1).toLowerCase().replace(
										"_", "");

						if (valObj != null) {
							for (Method m : methods) {
                                //����ȫ��Сд�Ƚϣ��������Сд
								if (methodName.toLowerCase().equals(m.getName().toLowerCase())) {
                                  // System.out.println(methodName+"   "+m.getParameterTypes()[0].toString()+"  "+valObj);
                                    Object val=valObj;
                                    //  System.out.println(m.getName()+"  "+val);
                                    if(m.getParameterTypes()!=null&&m.getParameterTypes().length>0&&val!=null&&
                                            !m.getParameterTypes()[0].getSimpleName().toLowerCase().equals("string")
                                            &&!m.getParameterTypes()[0].getSimpleName().toLowerCase().equals("date"))
                                        val= ConvertUtils.convert(val.toString(), m.getParameterTypes()[0]);

                                    m.invoke(obj, val); // obj:��ʵ��������
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

	/**
	 * ִ�����ݿ��FUNCTION ����Object
	 * @param sql
	 * @param paraV
	 * @return Object
	 */
	public Object executeFunction(String sql, List paraV) {
		// ����
		CallableStatement cs = null;
		Connection conn = null;
		// ����ֵ
		Object returnVal = null;
		conn = connectionDB();
		ResultSet rs;
		try {
			conn.setAutoCommit(false);
			if (sql != null && !sql.toString().equals("")) {
				cs = conn.prepareCall(sql.toString().trim());
				cs.registerOutParameter(1, Types.INTEGER);
				if (paraV != null) {
					for (int j = 1; j <= paraV.size(); j++) {
						cs.setObject(j + 1, paraV.get(j - 1));
					}
				}
				cs.execute();
				returnVal = cs.getObject(1);
				cs.close();
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return returnVal;
		}
	}

	/*
	 * (non-Javadoc) ִ�д洢���̣������ؽ����List<List<String>>
	 * 
	 * @see
	 * com.bhsf.oa.dao.impl.ICommonDAO#executeArratProcedure(java.util.List,
	 * java.util.List)
	 */
	public List<List<String>> executeResultProcedure(String sql, List paraV) {
		// ����
		CallableStatement cs = null;
		Connection conn = null;
		// ����ֵ
		List<List<String>> returnVal = new ArrayList<List<String>>();
		conn = connectionDB();

		try {
			conn.setAutoCommit(false);
			if (sql != null && !sql.toString().equals("")) {
				cs = conn.prepareCall(sql.toString().trim());
				if (paraV != null) {
					for (int j = 0; j < paraV.size(); j++) {
						cs.setObject(j + 1, paraV.get(j));
					}
				}
				ResultSet rs = cs.executeQuery();
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
							returnVal.add(strList);
					}
					rs.close();
				}
				cs.close();
			}

			conn.commit();
			conn.setAutoCommit(true);

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
			}

		} finally {
			try {
				if (cs != null)
					cs.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.debug(e.getMessage());
			}
			return returnVal;

		}
	}

    /**
     * ִ��FUNCTION nextval���õ���һ��ID
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
     * ����ִ��PROCEDURE
     * @param sqlArrayList List<String>����
     * @param objArrayList List<List<Object>>����
     * @return
     */
	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.executeArrayQuery_PROC(sqlArrayList, objArrayList);
	}

    /**
     * �õ���һ��UUID
     * @return
     */
	public String getNextId() {
		return UUID.randomUUID().toString();
	}

	/**
	 * ���³��ֶ�(clob,text,blob��)
	 * @param tblname ����
     * @param refName  �����г�
	 * @param columnsName ���µ�����
	 * @param value      ��Ӧ��ֵ
	 * @param ref   ����ֵ
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
	 * �����ۼ�
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
	 * ִ�����Log
	 * 
	 * @param operateUserref
	 *            ������
	 * @param table
	 *            �����ı�
	 * @param rowsid
	 *            ����������ID
	 * @param freevalue
	 *            ����֮ǰ��ֵ��ʲô
	 * @param currentVal
	 *            ����֮���ֵ��ʲô
	 * @param operateType
	 *            �������ͣ� add:��� update:�޸� delete:ɾ��
	 * @param remark
	 *            ��ע
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
	 * �õ����Log
	 * 
	 * @param operateUserref
	 *            ������
	 * @param table
	 *            �����ı�
	 * @param rowsid
	 *            ����������ID
	 * @param freevalue
	 *            ����֮ǰ��ֵ��ʲô
	 * @param currentVal
	 *            ����֮���ֵ��ʲô
	 * @param operateType
	 *            �������ͣ� add:��� update:�޸� delete:ɾ��
	 * @param remark
	 *            ��ע
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
	 * �õ����Log
	 * 
	 * @param operateUserref
	 *            ������
	 * @param table
	 *            �����ı�
	 * @param rowsid
	 *            ����������ID	
	 * @param operateType
	 *            �������ͣ� add:��� update:�޸� delete:ɾ��	
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


    /**
     * �õ���һ������ֵ
     *@param bo ��������true:����  false:����
     * @return
     */
    public Long getNextId(boolean bo){
        String id=((Long)System.currentTimeMillis()).toString()+""+new Random().nextInt(9999);
        id=id.substring(4);
        Long nextid=Long.parseLong(id);
        if(bo)
            nextid=Long.parseLong("-"+nextid);
        return nextid;
    }

}
