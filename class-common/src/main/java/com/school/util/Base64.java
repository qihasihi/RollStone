package com.school.util;

import net.sf.json.JSONArray;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created with IntelliJ IDEA.
 * User: panfei
 * Date: 14-3-21
 * Time: ÏÂÎç12:46
 * To change this template use File | Settings | File Templates.
 */
public class Base64 {
    //±àÂë
    public  static String EncodeBase64(String s)
    {
        return (new BASE64Encoder()).encode(s.getBytes());
    }
    //½âÂë
    public static  String DecodeBase64(String s)
    {
        if(s==null) return null;
        BASE64Decoder decoder=new BASE64Decoder();
        try{
            byte[] b=decoder.decodeBuffer(s);
            return new String(b);
        }catch (Exception e)
        {
            return null;
        }
    }
    public  static  void  main(String args[])
    {


    }
}
