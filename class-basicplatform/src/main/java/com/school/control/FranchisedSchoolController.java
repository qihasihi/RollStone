package com.school.control;

import com.school.manager.inter.IFranchisedSchoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

/**
 * Created by yuchunfan on 14-10-21.
 */
@Controller
public class FranchisedSchoolController {
    @Autowired
    @Qualifier("franchisedSchoolManager")
    private IFranchisedSchoolManager manager;

    public int getTotalSchool(int schoolId, String classYear) {
        return this.manager.getTotalClass(schoolId, classYear);
    }
}
