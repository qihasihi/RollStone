package com.school;

import com.school.util.MD5_NEW;
import net.sf.json.*;

import java.io.*;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengzhou on 14-6-17.
 */
public class Test {
    public static void main(String[] args)throws Exception{
        String url="http://school.etiantian.com/lezhixingt1/user?m=foreighLogin";
        Long t=new Date().getTime();
        String lzxschoolid="001",login_code="lzx_loginToEtt201406",lzx_userid="0122f4c5f18341838478575be29aff24",flag_id="2";
        String key=MD5_NEW.getMD5ResultCode(t.toString()+lzxschoolid+lzx_userid+flag_id+login_code+t);
        String param="login_time="+t+"&lzx_school_id="+lzxschoolid+"&login_key="+key+"&lzx_userid="+lzx_userid+"&flag_id="+flag_id+"&login_code="+login_code;
        System.out.println(url+"&"+param);
        System.out.println(sendPostURL(url,param));


//        String url="http://school.etiantian.com/lezhixingt1/cls?m=lzxUpdate";
//        Long t=new Date().getTime();
//        String lzxschoolid="001",lzx_userid="002",flag_id="1";
//        String key=MD5_NEW.getMD5ResultCode(t.toString()+lzxschoolid+t);
//
//        JSONArray ja=new JSONArray();
//        JSONObject jo=new JSONObject();
//        jo.put("lzx_classid","211222");
//        jo.put("class_name",java.net.URLEncoder.encode("4班","UTF-8"));
//        jo.put("class_grade",java.net.URLEncoder.encode("高一","UTF-8"));
//        jo.put("year","2013~2014");
//        jo.put("type","NORMAL");
//        jo.put("isflag","1");
//        ja.add(jo);
//        jo=new JSONObject();
//        jo.put("lzx_classid","2112");
//        jo.put("class_name",java.net.URLEncoder.encode("44班","UTF-8"));
//        jo.put("class_grade",java.net.URLEncoder.encode("高二","UTF-8"));
//        jo.put("year", "2013~2014");
//        jo.put("type","NORMAL");
//        jo.put("isflag","1");
//        ja.add(jo);
//        String param="timestamp="+t+"&lzx_school_id="+lzxschoolid+"&key="+key+"&clsarrayjson="+ja.toString();
//        System.out.println(url+"&"+param);
//        System.out.println(sendPostURL(url,param));
    }

    /**
     *后台调用接口
     * @param urlstr
     * @return
     */
    public static String sendPostURL(String urlstr,String params){
        HttpURLConnection httpConnection;
        URL url;
        int code;
        try {
            url = new URL(urlstr);

            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setRequestMethod("POST");
            if(params!=null)
                httpConnection.setRequestProperty("Content-Length",
                        String.valueOf(params.length()));
            httpConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
			/*
			 * PrintWriter printWriter = new
			 * PrintWriter(httpConnection.getOutputStream());
			 * printWriter.print(parameters); printWriter.close();
			 */

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    httpConnection.getOutputStream(), "8859_1");
            if(params!=null)
                outputStreamWriter.write(params);

            outputStreamWriter.flush();
            outputStreamWriter.close();

            code = httpConnection.getResponseCode();
        } catch (Exception e) {			// 异常提示
            System.out.println("异常错误!TOTALSCHOOL未响应!");
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (code == HttpURLConnection.HTTP_OK) {
            try {
                String strCurrentLine;
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(httpConnection.getInputStream()));
                while ((strCurrentLine = reader.readLine()) != null) {
                    stringBuffer.append(strCurrentLine).append("\n");
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("异常错误!");
                e.printStackTrace();;
                return null;
            }
        }else if(code==404){
            // 提示 返回
            System.out.println("异常错误!404错误，请联系管理人员!");
            return null;
        }else if(code==500){
            System.out.println("异常错误!500错误，请联系管理人员!");
            return null;
        }
        String returnContent=null;
        try {
            returnContent=new String(stringBuffer.toString().getBytes("gbk"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Map<String,Object> returnMap=null;
        //转换成JSON
        System.out.println(returnContent);
//        JSONObject jb=JSONObject.fromObject(returnContent);
//        String type=jb.containsKey("type")?jb.getString("type"):"";
//        String msg=jb.containsKey("msg")?jb.getString("msg"):"";
//        Object objList=jb.containsKey("objList")?jb.get("objList"):null;
//
//        returnMap=new HashMap<String,Object>();
//        returnMap.put("type",type);
//        returnMap.put("msg",msg);
//        System.out.println(msg);
//
//        if(type!=null&&type.trim().toLowerCase().equals("success")){
//            System.out.println(msg);
//            JSONArray jr=JSONArray.fromObject(objList);
//            String val="";
//            if(jr.size()>0)
//                val=jr.get(0).toString();
//            returnMap.put("objList",val);
//
//        }else{
//            System.out.println(msg);
//        }

        return returnContent;
    }

}
