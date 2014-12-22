package com.school.util.taglib;

import com.school.util.UtilTool;

/**
 * Created by zhengzhou on 14-10-13.
 */
public class SchoolTab{
    public SchoolTab(){super();}
    /**
     * 自定义标签,使用lastIndexOf
     * @param text
     * @param searchString
     * @return
     */
    public static int lastIndexOf(String text, String searchString) {
        return text.lastIndexOf(searchString);
    }

    /**
     * 自定义标签,使用replaceAll
     * @param text
     * @param findCodeReg
     * @param replaceCode
     * @return
     */
    public static String replaceAll(String text,String findCodeReg,String replaceCode){
        if(text==null)return "";
        return text.replaceAll(findCodeReg,replaceCode);
    }

    /**
     * 后台验证正则表达式,第二个参数全部匹配第一个参数(是否存在)
     * @param validateStr
     * @param reg
     * @return
     */
    public static boolean doExecReg(String validateStr,String reg){
        boolean returnBo=false;
        if(validateStr!=null&&reg!=null&&reg.trim().length()>0)
            return UtilTool.matchingText(reg,validateStr.toLowerCase());
        return returnBo;
    }

}
