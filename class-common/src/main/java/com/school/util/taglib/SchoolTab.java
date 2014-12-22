package com.school.util.taglib;

import com.school.util.UtilTool;

/**
 * Created by zhengzhou on 14-10-13.
 */
public class SchoolTab{
    public SchoolTab(){super();}
    /**
     * �Զ����ǩ,ʹ��lastIndexOf
     * @param text
     * @param searchString
     * @return
     */
    public static int lastIndexOf(String text, String searchString) {
        return text.lastIndexOf(searchString);
    }

    /**
     * �Զ����ǩ,ʹ��replaceAll
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
     * ��̨��֤������ʽ,�ڶ�������ȫ��ƥ���һ������(�Ƿ����)
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
