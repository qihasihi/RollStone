package com.school.util;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


/**
 * Created by zhengzhou on 14-6-20.
 */
public class CursorTagDemo extends TagSupport {

    private String val;
    public void setVal(String v){
        this.val=v;
    }

    @Override
    public int doEndTag() throws JspException {

        String returnVal=val;
        if(val!=null){
            switch(Integer.parseInt(val)){
                case 1:
                    returnVal="A";break;
                case 2:
                    returnVal="B";break;
                case 3:
                    returnVal="C";break;
                case 4:
                    returnVal="D";break;
                case 5:
                    returnVal="E";break;
                case 6:
                    returnVal="F";break;
                case 7:
                    returnVal="G";break;
                case 8:
                    returnVal="HF";break;
                case 9:
                    returnVal="I";break;
                case 10:
                    returnVal="J";break;
                case 11:
                    returnVal="K";break;
                case 12:
                    returnVal="L";break;
                case 13:
                    returnVal="M";break;
                case 14:
                    returnVal="N";break;
            }
        }
        try{
        pageContext.getOut().println(returnVal);
        }catch(IOException e){
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }

}
