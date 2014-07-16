package com.school.control;

import com.school.control.base.BaseController;
import com.school.entity.UserModelScoreLogsInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yuechunyang on 14-2-22.
 */
@Controller
@RequestMapping("/userModelTotalScore")
public class UserModelTotalScoreController extends BaseController<UserModelScoreLogsInfo> {
}
