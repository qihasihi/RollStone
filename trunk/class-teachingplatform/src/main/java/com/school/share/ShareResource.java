package com.school.share;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.school.entity.resource.ResourceInfo;
import com.school.manager.inter.resource.IResourceManager;
import com.school.manager.resource.ResourceManager;
import com.school.util.*;
import net.sf.json.JSONObject;

import com.school.util.UtilTool.DateType;
import com.school.util.sendfile.SendFile;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;

/**
 * ������Դ(��ʱ��)
 * @author zhengzhou
 *
 */
public class ShareResource extends TimerTask {
    /**
     * ���ɵ�ģ��XML
     */
    private final String template_name="ShareTemplate.xml";
    /**
     * ÿ�β�ѯ����
     */
    private final Integer _pageSize=1;
    /**
     * ��УID
     */
	private String schoolid=null;


	private ServletContext request;
	public ShareResource(ServletContext application){
		//��УID
		this.request=application;
		 schoolid=UtilTool.utilproperty.get("CURRENT_SCHOOL_ID").toString();
	}
	public void run() {
		// TODO Auto-generated method stub
            String firstDirectory=MD5_NEW.getMD5Result(MD5_NEW.getMD5Result(schoolid)+UtilTool.utilproperty.getProperty("TO_ETT_KEY").toString());
			//String firstDirectory=UtilTool.DateConvertToString(new Date(),DateType.smollDATE)+"_"+schoolid;
			String parentDirectory=request.getRealPath("/")+"/uploadfile/tmp/"; //��·��
			String directionPath=parentDirectory+firstDirectory+"/";
            //�����ļ���ŵ��ļ���
            String dataFileParent=directionPath+"data/";
            //��¼XML�����ַ
			String filename=new StringBuffer().append(firstDirectory).append(".xml").toString();

            String writeUrl=dataFileParent+"/"+filename;
            //��Դ�ļ�zip�����ļ�  (������¼��Դ���ļ�˳��)
            String filetxtName=new StringBuffer().append(new Date().getTime()+"")
                    .append("_").append(schoolid).append("_filename")
                    .append(".txt").toString();
            //����template��·��
            String templatepath=parentDirectory+template_name;
             File fromF=new File(templatepath);
            if(!fromF.exists()){
                //��¼������Ϣ
                System.out.println("��У�쳣!û�з�����Դ�ϴ�ģ��!");
                return;
            }
            //�����ļ��У�������ģ�壬���в���
			File tmpF1=new File(writeUrl);
            try{
                if(!tmpF1.getParentFile().exists())
                    tmpF1.getParentFile().mkdirs();
                if(tmpF1.exists()&&tmpF1.isFile())
                    tmpF1.delete();
                //����ģ��
                FileUtils.copyFile(fromF,tmpF1);
            }catch(Exception e){
                //��¼��־
                System.out.println("��У�쳣!�������ļ�ʧ�ܣ�ԭ��δ֪");
                e.printStackTrace();
            }
			//�õ�Ҫ�������Դ
        ResourceInfo rssearch=new ResourceInfo();
        rssearch.setSharestatus(2);     //�ƶ˹���
        rssearch.setNetsharestatus(-1);//�ȵ����
        rssearch.setResdegree(3);   //�Ǳ�����Դ
        //��ѯ�õ�����
        IResourceManager resourceManager=(ResourceManager) SpringBeanUtil.getBean("resourceManager");
        PageResult presult=new PageResult();
        presult.setPageSize(_pageSize);
        presult.setPageNo(0);
            String zipNameKey=firstDirectory;
           int x=0;
            while(true){
                presult.setPageNo(presult.getPageNo()+1);
                List<ResourceInfo> resList=resourceManager.getList(rssearch,presult);
                if(resList==null||resList.size()<1){
                    break;
                }
                x++;
              //  writeUrl=writeUrl.substring(0,writeUrl.lastIndexOf("."))
                if(!writeResourceToXml(writeUrl,resList)){
                    //��¼������Ϣ�Լ�ʱ��
                    System.out.println("���󣬷�У����ԭ��û�пɸ��µ���Դ��Ϣ!");
                }
//                 String currentDirectoryName=zipNameKey+"_"+presult.getPageNo();
//                File tmp=new File(directionPath+"/"+currentDirectoryName+"/");
//                if(tmp.exists()){
//                    //ɾ���ļ���
//                    tmp.delete();
//                }
//                tmp.mkdirs();//�����ļ���
                //�õ��ļ�������� ͬʱ���д��
                for(ResourceInfo rstmp:resList){
                    //�����ļ�
                    String directory=UtilTool.getResourceMd5Directory(rstmp.getResid().toString());

                   String filePath=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/"+directory+"/";
                    //��ʼ�����ļ�
                    File dfile=new File(filePath);
                    if(dfile.exists()&&dfile.isDirectory()){
                        //�����Ŀ¼��
                        ZipUtil.genZip(filePath,directionPath,directory);
                        UtilTool.writeFile(request, parentDirectory+"/",filetxtName, directionPath+"/"+directory+".zip\r\n");
                    }
                    //����XML zipName�ֶ�
                    OperateXMLUtil.updateXml(writeUrl,"RES_ID",rstmp.getResid()+"",new String[]{"zipName"},new String[]{directory+".zip"});
                }
            }
            if(x==0){
                System.out.println("�쳣����û�п���ͬ������Դ��!");
            }else{  //˵�����ڹ������Դ���������¡�
                //�������ļ����д�� zip  �����뵽����������
                ZipUtil.genZip(dataFileParent,directionPath,filename.split("\\.")[0]);
                String tmp=directionPath+"/"+filename.split("\\.")[0]+".zip";
                UtilTool.writeFile(request, parentDirectory+"/",filetxtName, tmp+"\r\n");

                //��ȡ�ļ��ϴ����У������ϴ�
                UtilTool.uploadResourceToTotalSchool(parentDirectory + "/" + filetxtName);
                //���ýӿڣ��������
                System.out.println("�ļ��ϴ���ɣ����ýӿڣ��������");
                //�õ�key
                String fpath = request.getRealPath("/") + "school.txt";
                BufferedReader br = null;
                StringBuilder content = null;
                try {
                    br = new BufferedReader(new FileReader(fpath.trim()));
                    String lineContent = null;
                    while ((lineContent = br.readLine()) != null) {
                        if (content == null)
                            content = new StringBuilder(lineContent);
                        else
                            content.append("\n" + lineContent);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return;
                } finally {
                    if (br != null)
                        try{
                        br.close();
                        }catch(Exception e){e.printStackTrace();}
                }
                String totalSchoolUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION");
                totalSchoolUrl+="/resourceInter";
                String params="m=fxsczy&key="+content.toString()+"&resolveFilename="+firstDirectory+".zip";
                //������У�ӿڣ������ļ�
                if(ShareResourceUtil.sendResourcePathToTotalSchool(totalSchoolUrl,params)){
                    //��ʼ�������ݿ⣬�����Ѿ�ͬ������Դ
                    if(resourceManager.doUpdateShareNetShareStatus(0, 2, 3, -1)){
                        System.out.println("resource resolved success!");
                    }else{
                        System.out.println("resource update success!but localhost dataset is error!");
                    }
                }else{
                    System.out.println("resource resolved error!");
                }
            }
		}

    /**
     * д��XML��
     * @param saveUrl  XML�����λ��,
     * @param resList  Ҫд�������
     * @return
     */
        private boolean writeResourceToXml(String saveUrl,List<ResourceInfo> resList){
            if(resList==null||resList.size()<1)
                return false;

             boolean returnVal=true;

            // ��ȡѧ����Ϣ
            //ģ�������
            for (int i = 0; i <resList.size(); i++) {
                //��¼¼�������ֵ
                List<String> columnList=new ArrayList<String>();
                List<String> valueList=new ArrayList<String>();

                ResourceInfo s = (ResourceInfo)resList.get(i);
                // ����һ��ѧ��
                columnList.add("RES_ID");valueList.add(s.getResid().toString());
                if(s.getResname()!=null){           //��ԴNAME
                    columnList.add("RES_NAME");valueList.add(s.getResname());
                }
                if(s.getResintroduce()!=null){
                    columnList.add("RES_INTRODUCE");valueList.add(s.getResintroduce());//��Դ����
                }
                if(s.getUserid()!=null){
                    columnList.add("USER_ID");valueList.add(s.getUserid().toString());
                }
                if(s.getUsertype()!=null){   //�����������ͣ�1:ѧ�� 2:��ʦ��
                    columnList.add("USER_TYPE");valueList.add(s.getUsertype().toString());
                } if(s.getResstatus()!=null){//��Դ״̬��0��ɾ��
                    columnList.add("RES_STATUS");valueList.add(s.getResstatus().toString());
                }if(s.getResscore()!=null){       //��Դ����
                    columnList.add("RES_SCORE");valueList.add(s.getResscore().toString());
                }if(s.getClicks()!=null){
                    //���ص������
                    columnList.add("CLICKS");valueList.add(s.getResscore().toString());
                }if(s.getCommentnum()!=null){
                 //�������۴���
                    columnList.add("COMMENTNUM");valueList.add(s.getCommentnum().toString());
                } if(s.getDownloadnum()!=null){
                 //�������ش���
                columnList.add("DOWNLOADNUM");valueList.add(s.getDownloadnum().toString());
                 } if(s.getStorenum()!=null){
                    columnList.add("STORENUM");valueList.add(s.getStorenum().toString());
                          //�����ղش���
                }if(s.getPraisenum()!=null){
                     //�����ܴ���
                    columnList.add("PRAISENUM");valueList.add(s.getPraisenum().toString());
                } if(s.getRecomendnum()!=null){
                   //�����Ƽ�����
                    columnList.add("RECOMENDNUM");valueList.add(s.getRecomendnum().toString());
                }  if(s.getReportnum()!=null){
                     //���ؾٱ�����
                    columnList.add("REPORTNUM");valueList.add(s.getReportnum().toString());
                }if(s.getUsername()!=null){
                     //�ϴ�������
                    columnList.add("USER_NAME");valueList.add(s.getUsername().toString());
                } if(s.getSchoolname()!=null){
                    //ѧУ����
                    columnList.add("SCHOOL_NAME");valueList.add(s.getSchoolname().toString());
                } if(s.getResdegree()!=null){
                     //��Դ�ȼ���1:��׼ 2:���� 3:���أ�
                    columnList.add("RES_DEGREE");valueList.add(s.getResdegree().toString());
                } if(s.getFilesuffixname()!=null){
                  //��Դ��׺��
                    columnList.add("FILE_SUFFIXNAME");valueList.add(s.getFilesuffixname().toString());
                } if(s.getFilesize()!=null){
                        //�ļ���С
                    columnList.add("FILE_SIZE");valueList.add(s.getFilesize().toString());
                } if(s.getUseobject()!=null){
                       //���ö���
                    columnList.add("USE_OBJECT");valueList.add(s.getUseobject().toString());
                }if(s.getRestype()!=null){
                          //��Դ����
                    columnList.add("RES_TYPE");valueList.add(s.getRestype().toString());
                }if(s.getFiletype()!=null){
                         //�ļ�����
                    columnList.add("FILE_TYPE");valueList.add(s.getFiletype().toString());
                }if(s.getGrade()!=null){
                               //�꼶���
                    columnList.add("GRADE");valueList.add(s.getGrade().toString());
                } if(s.getSubject()!=null){
                      //ѧ�Ʊ��
                    columnList.add("SUBJECT");valueList.add(s.getSubject().toString());
                }
                columnList.add("C_TIME");valueList.add(s.getCtime().getTime()+"");
                if(!OperateXMLUtil.addXml(saveUrl,columnList,valueList.toArray())){
                    returnVal=false;
                    //��¼ͬ����־
                    System.out.println("��У�쳣����!��дXML�쳣!");
                    break;
                }
            }
            return returnVal;
        }

    /*
    	UtilTool.writeFile(request,directionPath, filename, "[stat=300]\r\n");
			//�õ�����

			//ѭ����д�ļ�
			PageResult presult=new PageResult();
			presult.setPageNo(0);
			presult.setPageSize(100);
			while(true){
				presult.setPageNo(presult.getPageNo()+1);
				List<List<String>> resourceArrayList=sharemanager.getShareResourceInfo(presult);
				if(resourceArrayList==null||resourceArrayList.size()<1)
					break;
				for (List<String> tmpList : resourceArrayList) {
					StringBuilder tmpBuilder=new StringBuilder();
					tmpBuilder.append(tmpList.get(0));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(1));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(2));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(3));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(4));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(5));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(6));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(7));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(8));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(9));
					tmpBuilder.append("\r\n");
					UtilTool.writeFile(request, directionPath, filename, tmpBuilder.toString());
				}
			}
			UtilTool.writeFile(request,directionPath, filename, "[endstat=300]\r\n");
			//[endstat=300]
			//[stat=400]
			UtilTool.writeFile(request,directionPath, filename, "[stat=400]\r\n");
			presult.setPageNo(0);
			String filetxtName=new StringBuffer().append(new Date().getTime()+"")
							.append("_").append(schoolid).append("_filename")
							.append(".txt").toString();
			//IResourceFileManager resourceFileManager=(ResourceFileManager)applicationContext.getBean("resourceFileManager");
			while(true){
				presult.setPageNo(presult.getPageNo()+1);
				List<List<String>> resourceFleList=sharemanager.getShareResourceFile(presult);
				if(resourceFleList==null||resourceFleList.size()<1)
					break;
				for (List<String> tmpList : resourceFleList) {
					StringBuilder tmpBuilder=new StringBuilder();
					tmpBuilder.append(tmpList.get(0));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(1));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(2));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(3));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(4));
					tmpBuilder.append("\r\n");
					UtilTool.writeFile(request, directionPath, filename, tmpBuilder.toString());
					//��֯�ļ�������ʼ�ϴ�
					String downPath=UtilTool.getResourceUrl(tmpList.get(0).trim(), tmpList.get(1).trim(),tmpList.get(3));
					String downDirectory=downPath.split("/")[0];
					//�õ�resource_file��ͬʱд���ļ��У��ȴ��ϴ�
					//String filerealpath=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/"+downPath+"\r\n";
					String directorypath=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/"+downDirectory+"/";
					//��ʼѹ���ļ�

					ZipUtil.genZip(directorypath, directorypath+"../", downDirectory);
					UtilTool.writeFile(request, parentDirectory,filetxtName, directorypath+"../"+downDirectory+".zip\r\n");
				}
			}
			UtilTool.writeFile(request,directionPath, filename, "[endstat=400]\r\n");
			//[endstat=400]
			//[stat=500]
			UtilTool.writeFile(request, directionPath, filename, "[stat=500]\r\n");
			presult.setPageNo(0);
			while(true){
				presult.setPageNo(presult.getPageNo()+1);
				List<List<String>> extedresourceList=sharemanager.getShareExtendResourceInfo(presult);
				if(extedresourceList==null||extedresourceList.size()<1)
					break;
				for (List<String> tmpList : extedresourceList) {
					StringBuilder tmpBuilder=new StringBuilder();
					tmpBuilder.append(tmpList.get(0));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(1));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(2));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(3));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(4));
					tmpBuilder.append("\r\n");
					UtilTool.writeFile(request, directionPath, filename, tmpBuilder.toString());
				}
			}
			UtilTool.writeFile(request, directionPath, filename, "[endstat=500]\r\n");
    * */



    // д�������ļ�
    public static void callWriteXmlFile(Document doc, Writer w, String encoding) {
        try {
            Source source = new DOMSource(doc);
            Result result = new StreamResult(w);
            Transformer xformer = TransformerFactory.newInstance()
                    .newTransformer();
            xformer.setOutputProperty(OutputKeys.ENCODING, encoding);
            xformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
/**
 * ������Դ����
 * @author zhengzhou
 *
 */
class ShareResourceUtil{



	/**
	 * fasong ziyuan
	 * @param totalSchoolUrl
	 * @return
	 */
	public static boolean sendResourcePathToTotalSchool(String totalSchoolUrl,String params){
		if(totalSchoolUrl==null)return false;

			
		HttpURLConnection httpConnection;
		URL url;
		int code;
		try {
			url = new URL(totalSchoolUrl.toString());

			httpConnection = (HttpURLConnection) url.openConnection();

			httpConnection.setRequestMethod("POST");
            if(params!=null)
			    httpConnection.setRequestProperty("Content-Length",String.valueOf(params.length()));
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
			return false;
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
				return false;
			}
		}else if(code==404){
			// ��ʾ ����
			System.out.println("�쳣����!404��������ϵ������Ա!");
			return false;
		}else if(code==500){
			System.out.println("�쳣����!500��������ϵ������Ա!");
			return false;
		}
		String returnContent=null;
		try {
			returnContent=new String(stringBuffer.toString().getBytes("gbk"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ת����JSON
		System.out.println(returnContent);
		JSONObject jb=JSONObject.fromObject(returnContent);
		String type=jb.containsKey("type")?jb.getString("type"):"";
		String msg=jb.containsKey("msg")?jb.getString("msg"):"";
		if(type!=null&&type.trim().toLowerCase().equals("success")){
			System.out.println(msg);
			return true;
		}else{
			System.out.println(msg);return false;
		}
	}

    /**
     * �õ�ID
     * @param args
     */
    public static void mian(String[] args){
        //xml�����ַ ����������


    }


}
