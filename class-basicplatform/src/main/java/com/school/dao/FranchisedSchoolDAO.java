package com.school.dao;

import com.school.dao.inter.IFranchisedSchoolDAO;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class FranchisedSchoolDAO implements
		IFranchisedSchoolDAO {
    Connection conn = null;
    CallableStatement stm = null;
    public FranchisedSchoolDAO () {
        getConnection();
    }

    public boolean getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://192.168.10.41:3306/total_school", "mytest", "testdb");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public int getTotalClass(int schoolId, String classYear) {
        int res = 0;
        StringBuilder sqlBuilder = new StringBuilder("{call franchisedschool_totalclass_proc_search(");
        sqlBuilder.append(schoolId);
        sqlBuilder.append(",'");
        sqlBuilder.append(classYear);
        sqlBuilder.append("',?)}");
        try {
            stm = conn.prepareCall(sqlBuilder.toString());
            stm.registerOutParameter(1, Types.INTEGER);
            stm.execute();
            res = stm.getInt(1);
            stm.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
