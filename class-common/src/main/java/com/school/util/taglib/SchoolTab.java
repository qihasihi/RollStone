package com.school.util.taglib;
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

}
