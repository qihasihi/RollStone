package com.school.share;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.util.UtilTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

public class TaskLoopRemindUtil{
    public static  void main(String []args){
        Calendar am8= Calendar.getInstance();
        am8.set(Calendar.HOUR_OF_DAY,8);
        am8.set(Calendar.MINUTE,0);
        am8.set(Calendar.SECOND,0);
        Calendar pm10= Calendar.getInstance();
        pm10.set(Calendar.HOUR_OF_DAY,22);
        pm10.set(Calendar.MINUTE,17);
        pm10.set(Calendar.SECOND,0);
        Date now=new Date();
        System.out.println(am8.toString());
        System.out.println(pm10.toString());

        if(now.after(am8.getTime())&&now.before(pm10.getTime())){
            System.out.println("asdf ");
        }

        String[] arr={"1","2","3","4","5","5","4"};
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
        for(String a:arr){
            Map<String,Object>m=new HashMap<String, Object>();
            m.put("int",a);
            if(!mapList.contains(m)){
                mapList.add(m);
            }
        }

        for(Map<String,Object>tmpM:mapList){
            Set set=tmpM.entrySet();
            Iterator it=set.iterator();
            while (it.hasNext()){
                Map.Entry a= (Map.Entry) it.next();

                System.out.println("key:"+a.getKey()+" value: "+a.getValue());
            }
        }


    }


    public static boolean sendTaskRemindObj(List<Map<String,Object>> cuMapList){
        String addToEtt_URL= UtilTool.utilproperty.getProperty("ETT_INTER_IP")+"taskRemind.do";
        if(cuMapList==null||cuMapList.size()<1)return false;
        HashMap<String,String> paramMap=new HashMap<String,String>();
        paramMap.put("timestamp",new Date().getTime()+"");

        //µ√µΩdata JSONObject
        //Map<String,Object> tmpMap=new HashMap<String, Object>();
        //tmpMap.put("dataList",JSONArray.fromObject(cuMapList).toString());
        //¥Ê»Î
        String dataArr="";
        try{
            dataArr= JSONArray.fromObject(cuMapList).toString();
        }catch (Exception e){
            System.out.println("sendTaskRemindObj jsonarray:" + e);
            return false;
        }
        paramMap.put("dataList",dataArr);
        //paramMap.put("data",JSONObject.fromObject(tmpMap).toString());
        String val = UrlSigUtil.makeSigSimple("taskRemind.do", paramMap);
        paramMap.put("sign",val);
        System.out.println("TaskLoopRemind sendPostUrl:"+addToEtt_URL);
        JSONObject jo=UtilTool.sendPostUrl(addToEtt_URL,paramMap,"UTF-8");
        if(jo!=null&&jo.containsKey("result")&&jo.get("result").toString().trim().equals("1")){
            return true;
        }
        if(jo!=null)
            System.out.println(jo.get("msg"));
        return false;

    }

}
