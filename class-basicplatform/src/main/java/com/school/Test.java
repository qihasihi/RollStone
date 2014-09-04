package com.school;

import com.school.util.MD5_NEW;
import net.sf.json.*;

import java.io.*;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;

/**
 * Created by zhengzhou on 14-6-17.
 */
public class Test {

    private static String getFixLenthString(int strLength) {

        Random rm = new Random();

        // ��������
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

        // ����õĻ�������ת��Ϊ�ַ���
        String fixLenthString = String.valueOf(pross);

        // ���ع̶��ĳ��ȵ������
        return fixLenthString.substring(1, strLength + 1);
    }


    public static void main(String[] args)throws Exception{
      /* String url="http://localhost:8080/sz_school/tpuser?m=toJWIndex";
        String param="schoolId=1&jId=2&schoolName="+java.net.URLEncoder.encode("������У","UTF-8");
        System.out.println(url+"&"+param);
        System.out.println(sendPostURL(url,param)); */






            int i=0;
            Set set=new HashSet();
            while (i<10000){
                i++;

                String id=((Long)System.currentTimeMillis()).toString()+""+getFixLenthString(6);;
                System.out.println(id);
                id=id.substring(6);

                Long nextid=Long.parseLong(id);
                set.add(nextid);
            }
            System.out.println(set.size());




        //��֤��ӷ�У��Ϣ
    /*    String totalSchoolUrl="http://localhost:8080/totalSchool/franchisedSchool?jwValidateSchool";
        String totalParams="schoolid=52&schoolname="+java.net.URLEncoder.encode("��ʿ","UTF-8");
        sendPostURL(totalSchoolUrl,totalParams); */


//        String url="http://school.etiantian.com/lezhixingt1/cls?m=lzxUpdate";
//        Long t=new Date().getTime();
//        String lzxschoolid="001",lzx_userid="002",flag_id="1";
//        String key=MD5_NEW.getMD5ResultCode(t.toString()+lzxschoolid+t);
//
//        JSONArray ja=new JSONArray();
//        JSONObject jo=new JSONObject();
//        jo.put("lzx_classid","211222");
//        jo.put("class_name",java.net.URLEncoder.encode("4��","UTF-8"));
//        jo.put("class_grade",java.net.URLEncoder.encode("��һ","UTF-8"));
//        jo.put("year","2013~2014");
//        jo.put("type","NORMAL");
//        jo.put("isflag","1");
//        ja.add(jo);
//        jo=new JSONObject();
//        jo.put("lzx_classid","2112");
//        jo.put("class_name",java.net.URLEncoder.encode("44��","UTF-8"));
//        jo.put("class_grade",java.net.URLEncoder.encode("�߶�","UTF-8"));
//        jo.put("year", "2013~2014");
//        jo.put("type","NORMAL");
//        jo.put("isflag","1");
//        ja.add(jo);
//        String param="timestamp="+t+"&lzx_school_id="+lzxschoolid+"&key="+key+"&clsarrayjson="+ja.toString();
//        System.out.println(url+"&"+param);
//        System.out.println(sendPostURL(url,param));
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
