package com.school.util.taglib;

import com.school.util.PageUtil.PageUtilTool;
import com.school.util.UtilTool;

import java.text.SimpleDateFormat;
import java.util.Date;

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


    /**
     *
     * @param time
     * @return
     */
    public static String isBegin(String time){//
        if(time==null||time.trim().length()<1)
            return "";
        //��ʼʱ��
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date db=sd.parse(time);
            if(db.after(new Date()))
                return "1";
            return "";
        } catch (Exception e) {
            return "";
        }
    }


}
