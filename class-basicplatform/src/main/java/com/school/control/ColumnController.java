package com.school.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.school.control.base.BaseController;
import com.school.entity.ColumnInfo;

@Controller
@RequestMapping(value="/column")
public class ColumnController extends BaseController<ColumnInfo> {

}
