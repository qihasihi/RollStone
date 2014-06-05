/**
  * 
  */
 package com.school.util.Pdf2Swf;
 
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import sun.security.jca.GetInstance;
 
 /**
  * PDFתSWF����
  * 
  * @date 2012-11-5
  * @author xhw
  * 
  */
 public class Pdf2Swf {
 
	 private static Pdf2Swf instance;
     /**
      * SWTOOLS�İ�װ·��,��װĿ¼Ϊ:"E:/����/swftools"
      */
     //public static final String SWFTOOLS_PATH = "E:/����/";
     /**
      * pdf�ļ���׺��
      */
     public static final String FILE_NAME_OF_PDF = "pdf";
     /**
      * swf�ļ���׺��
      */
     public static final String FILE_NAME_OF_SWF = "swf";
 
     /**
      * ����ļ���·��
      * 
      * @param file
      *            �ļ���·�� ,�磺"c:/test/test.swf"
      * @return �ļ���·��
      */
     public String getFilePath(String file) {
         String result = file.substring(0, file.lastIndexOf("/"));
         if (file.substring(2, 3) == "/") {
             result = file.substring(0, file.lastIndexOf("/"));
         } else if (file.substring(2, 3) == "\\") {
             result = file.substring(0, file.lastIndexOf("\\"));
         }
         return result;
     }
 
     /**
      * �½�һ��Ŀ¼
      * 
      * @param folderPath
      *            �½�Ŀ¼��·�� �磺"c:\\newFolder"
      */
     public void newFolder(String folderPath) {
         try {
             File myFolderPath = new File(folderPath.toString());
             if (!myFolderPath.exists()) {
                 myFolderPath.mkdir();
             }
         } catch (Exception e) {
             System.out.println("�½�Ŀ¼��������");
             e.printStackTrace();
         }
     }
 
     /**
      * the exit value of the subprocess represented by this Process object. By
      * convention, the value 0 indicates normal termination.
      * 
      * @param sourcePath
      *            pdf�ļ�·�� ���磺"c:/hello.pdf"
      * @param destPath
      *            swf�ļ�·��,�磺"c:/test/test.swf"
      * @return ��������·��أ�0��ʧ��������أ�1
      * @throws IOException
      */
     public  int convertPDF2SWF(HttpServletRequest request,String sourcePath, String destPath) throws IOException {
         // ���Ŀ���ļ���·�����µģ����½�·��
         newFolder(getFilePath(destPath));
 
         // Դ�ļ��������򷵻�
         File source = new File(sourcePath);
         if (!source.exists()) {
             return 0;
         }
 
         // ����pdf2swf�������ת��
         String command = request.getRealPath("/")+"util/docconverter/swftools/pdf2swf.exe  -t \"" + sourcePath + "\" -o  \"" + destPath + "\" -s flashversion=9 -s languagedir=D:\\xpdf\\xpdf-chinese-simplified ";
         System.out.println("�������:" + command + "\n��ʼת��...");
         // �����ⲿ����
         Process process = Runtime.getRuntime().exec(command);
         final InputStream is1 = process.getInputStream();
         new Thread(new Runnable() {
             public void run() {
                 BufferedReader br = new BufferedReader(new InputStreamReader(is1));
                 try {
                     while (br.readLine() != null)
                         ;
                 } catch (IOException e) {
                     e.printStackTrace();
                 }

             }
         }).start(); // �����������߳������process.getInputStream()�Ļ�����
         InputStream is2 = process.getErrorStream();
         BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
         // ������������
         StringBuilder buf = new StringBuilder();
         String line = null;
         while ((line = br2.readLine()) != null)
             // ѭ���ȴ�ffmpeg���̽���
             buf.append(line);
         while (br2.readLine() != null);
         try {
             process.waitFor();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         System.out.println("ת������...");
         return process.exitValue();
     }
 
     /**
      * pdf�ļ�ת��Ϊswf�ļ�����
      * 
      * @param sourcePath
      *            pdf�ļ�·�� ���磺"c:/hello.pdf"
      * @param destPath
      *            swf�ļ�·��,�磺"c:/test/test.swf"
      */
     public static  void PDF2SWF(HttpServletRequest request,String sourcePath,String destPath){
    	 long begin_time = new Date().getTime();
         if(instance==null){
        	 instance=new Pdf2Swf();
         }
         try {
			instance.convertPDF2SWF(request,sourcePath, destPath);
			
		} catch (IOException e) {  
			// TODO Auto-generated catch block
			e.printStackTrace();
			 System.out.println("ת������ʧ�ܣ�");
		}
         long end_time = new Date().getTime();
         System.out.println("ת������ʱ :[" + (end_time - begin_time) + "]ms");
         System.out.println("ת���ļ��ɹ�����");
        
     }
 
     public static void main(String[] args) throws IOException {
         String sourcePath = "e:/test." + FILE_NAME_OF_PDF;
         String destPath = "e:/hello/test_1352107155307_" + new Date().getTime() + "." + FILE_NAME_OF_SWF;
        // pdf2swf(sourcePath, destPath);
     }
 }