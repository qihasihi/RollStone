package com.school.share;

import com.school.entity.EttColumnInfo;
import com.school.manager.ColumnManager;
import com.school.manager.inter.IColumnManager;
import com.school.util.MD5_NEW;
import com.school.util.SpringBeanUtil;
import com.school.util.UtilTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by zhengzhou on 14-7-2.
 */
public class SynchroEttColumns  extends TimerTask {
  private  static  String ettColumnUrl=null,school_id=null;
    public SynchroEttColumns(){
        ettColumnUrl= UtilTool.utilproperty.getProperty("ETT_COLUMNS_UPDATE_ADDRESS");
        school_id=UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_ID");
    }

    public static void main(String[] args){
        ettColumnUrl="http://yuechunyang.etiantian.com:8080/totalSchool/ettcolumn?m=getEttColumn";
        school_id="50000";
        Long dtime=new Date().getTime();
        String checkcode= MD5_NEW.getMD5ResultCode(dtime+school_id+dtime);
        String params="school_id="+school_id+"&timestamp="+dtime+"&checkcode="+checkcode;
        Map<String,Object> mapObj=sendPostURL(ettColumnUrl,params);
        System.out.println(mapObj.get("type") + "  " + mapObj.get("msg"));
        JSONArray jsonArray=(JSONArray)mapObj.get("objList");
        System.out.println(jsonArray.toString());
    }
    @Override
    public void run() {
         IColumnManager columnManager= (ColumnManager)SpringBeanUtil.getBean("columnManager");
        Long dtime=new Date().getTime();
        String checkcode= MD5_NEW.getMD5ResultCode(dtime+school_id+dtime);
        String params="school_id="+school_id+"&timestamp="+dtime+"&checkcode="+checkcode;
        Map<String,Object> mapObj=sendPostURL(ettColumnUrl,params);
       // System.out.println( + "  " + mapObj.get("msg"));
        List<String>sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();

        if(mapObj.get("type").toString()=="success"){
            JSONArray jsonArray=(JSONArray)mapObj.get("objList");
            if(jsonArray!=null){
                Iterator<Object> itea=jsonArray.iterator();
                while(itea.hasNext()){
                    JSONObject jsObj=(JSONObject)itea.next();
                    if(jsObj!=null){
                        Object ettcolumnid=jsObj.get("ettcolumnid");
                        Object ettcolumnname=jsObj.get("ettcolumnname");
                        Object status=jsObj.get("status");
                        Object ettcolumnurl=jsObj.get("ettcolumnurl");
                        EttColumnInfo ettEntity=new EttColumnInfo();
                        ettEntity.setEttcolumnid(Integer.parseInt(ettcolumnid.toString()));
                        ettEntity.setEttcolumnname(ettcolumnname.toString());
                        ettEntity.setEttcolumnurl(ettcolumnurl.toString());
                        ettEntity.setStatus(Integer.parseInt(ettcolumnurl.toString()));
                        StringBuilder sqlbuilder=new StringBuilder();
                        List<Object> objList=columnManager.getEttColumnSynchro(ettEntity,sqlbuilder);
                        if(sqlbuilder.toString().length()>0){
                            sqlArrayList.add(sqlbuilder.toString());
                            objArrayList.add(objList);
                        }
                    }
                }
            }
            if(sqlArrayList!=null&&sqlArrayList.size()>0&&sqlArrayList.size()==objArrayList.size()){
                if(columnManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                    System.out.println("������УETT�ɹ�,ʱ��:"+ UtilTool.DateConvertToString(new Date(), UtilTool.DateType.type1));
                }else
                    System.out.println("������УETTʧ�ܣ�ԭ��δ֪");
            }else
                System.out.println("������УETTʧ�ܣ�ԭ��δ֪");
        }else{
            System.out.println(mapObj.get("msg"));
        }
    }
    /**
     *��̨���ýӿ�
     * @param urlstr
     * @return
     */
    public static Map<String,Object> sendPostURL(String urlstr,String params){
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
        String returnContent=stringBuffer.toString();
        Map<String,Object> returnMap=null;
        //ת����JSON
        System.out.println(returnContent);
        JSONObject jb=JSONObject.fromObject(returnContent);
        String type=jb.containsKey("type")?jb.getString("type"):"";
        String msg=jb.containsKey("msg")?jb.getString("msg"):"";
        Object objList=jb.containsKey("objList")?jb.get("objList"):null;

        returnMap=new HashMap<String,Object>();
        returnMap.put("type",type);
        returnMap.put("msg",msg);
        System.out.println(msg);

        if(type!=null&&type.trim().toLowerCase().equals("success")){
            System.out.println(msg);
            JSONArray jr=JSONArray.fromObject(objList);
//            String val="";
//            if(jr.size()>0)
//                val=jr.get(0).toString();
            returnMap.put("objList",jr);

        }else{
            System.out.println(msg);
        }

        return returnMap;
    }
}
