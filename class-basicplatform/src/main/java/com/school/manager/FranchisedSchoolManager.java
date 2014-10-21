package com.school.manager;

import com.school.dao.inter.IFranchisedSchoolDAO;
import com.school.manager.inter.IFranchisedSchoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FranchisedSchoolManager implements IFranchisedSchoolManager {
	
	@Autowired
	@Qualifier("franchisedSchoolDAO")
	public IFranchisedSchoolDAO franchisedSchoolDAO;
	

    @Override
    public int getTotalClass(int schoolId, String classYear)  {
        return this.franchisedSchoolDAO.getTotalClass(schoolId, classYear);
    }
}
