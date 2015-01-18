package com.school.util;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * junit用到
 * Created by zhengzhou on 14-12-26.
 */
public class PropertiesFileConfig {
    //几个配置文件的相对地址，
    public static final String[] propertiesConfigPath={
            "/../../../aggregator-class/src/main/resources/properties/ethosStudentScoreRule.properties",
            "/../../../aggregator-class/src/main/resources/properties/msg.properties",
            "/../../../aggregator-class/src/main/resources/properties/SimpleResUtil.properties",
            "/../../../aggregator-class/src/main/resources/properties/stuScoreAwardUtil.properties",
            "/../../../aggregator-web/profiles/dev/resources/properties/util.properties",
    };
    public static String getPropertiesConfigPath(int i){
        ServletRequestAttributes srabutes =(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =null;
        if(srabutes==null){//
            request=new MockHttpServletRequest();
        }else
            request=((ServletRequestAttributes)srabutes).getRequest();
        return request.getRealPath("/")+""+propertiesConfigPath[i];
    }
}
