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
    public static void main(String[] args){
        String url="http://localhost:8080/sz_school/user?m=foreighLogin";
        Long t=new Date().getTime();
        String lzxschoolid="001",login_code="lzx_loginToEtt201406",lzx_userid="001",flag_id="1";
        String key=MD5_NEW.getMD5ResultCode(t.toString()+lzxschoolid+lzx_userid+flag_id+login_code+t);
        String param="lzx_userid="+lzx_userid+"&login_time="+t+"&lzx_school_id="+lzxschoolid+"&flag_id="+flag_id+"&login_code="+login_code+"&&login_key="+key;
        System.out.println(sendPostURL(url,param));
    }

    /**
     *��̨���ýӿ�
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
        } catch (Exception e) {			// �쳣��ʾ
            System.out.println("�쳣����!TOTALSCHOOLδ��Ӧ!");
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
                System.out.println("�쳣����!");
                e.printStackTrace();;
                return null;
            }
        }else if(code==404){
            // ��ʾ ����
            System.out.println("�쳣����!404��������ϵ������Ա!");
            return null;
        }else if(code==500){
            System.out.println("�쳣����!500��������ϵ������Ա!");
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
        //ת����JSON
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
