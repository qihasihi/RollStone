package com.school.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.school.control.base.BaseController;
import com.school.entity.UserColumnRightInfo;

@Controller
@RequestMapping(value="userColumnRight")
public class UserColumnRightController extends BaseController<UserColumnRightInfo> {

}
