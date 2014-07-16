package com.school.util;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import sun.net.ftp.FtpProtocolException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FTP�ϴ�������
 * @author zhengzhou
 */
public class FtpClientUtil {

    static FTPClient ftpClient=new FTPClient();
    /**
     * ��̬��
     */
    static{
    //���ý�������ʹ�õ����������������̨
     ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
    }


    // server������������
// user���û���
// password������
// path���������ϵ�·��
    public static  boolean connectServer(String ip,int port,String user
            , String password,String path){
        boolean success=false;
        try {
            int reply;
            // ����FTP������
            // �������Ĭ�϶˿ڣ�����ʹ��ftp.connect(url)�ķ�ʽֱ������FTP������
            if(port==22)
                ftpClient.connect(ip);
              else
                ftpClient.connect(ip,port);
            // ��¼ftp
            ftpClient.login(user, password);
            // �����ص�ֵ�ǲ���230������ǣ���ʾ��½�ɹ�
            reply = ftpClient.getReplyCode();
            // ��2��ͷ�ķ���ֵ�ͻ�Ϊ��
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return success;
            }
            if(path!=null&&path.trim().length()>0){
                changeDirectory(path);
            }
        } catch (Exception ex) {
            System.out.println("not login");
            System.out.println(ex);
        }
        return success;
    }

    /**
     * �޸�Ŀ¼
     * @param path
     */
    public static void changeDirectory(String path){
        if(ftpClient!=null){
            if(path!=null&&path.length()>0)
                try {
                 ftpClient.changeWorkingDirectory(path);
                }catch(IOException ex){
                    System.out.println(path+" change error!");
                    System.out.println(ex);
                }
        }
    }

    public static void closeConnect() {
        try {
            ftpClient.logout();
            ftpClient.disconnect();
            System.out.println("disconnect success");
        } catch (IOException ex) {
            System.out.println("not disconnect");
            System.out.println(ex);
        }
    }

    public static void upload(List<String> localFile) throws FtpProtocolException  {
        if(!ftpClient.isConnected()){
            connectServer(
                    UtilTool.utilproperty.getProperty("FTP_IPADDRESS").toString(),
                    Integer.parseInt(UtilTool.utilproperty.getProperty("FTP_PORT").toString()),
                    UtilTool.utilproperty.getProperty("FTP_USER").toString(),
                    Base64.DecodeBase64(UtilTool.utilproperty.getProperty("FTP_PASSWORD").toString()),
                    Base64.DecodeBase64(UtilTool.utilproperty.getProperty("FTP_DICTIONARY").toString())
            );
        }
        BufferedInputStream inStream = null;
            int i=0;
        try {
            for (;i<localFile.size();i++){
                if(localFile.get(i)==null||localFile.get(i).trim().length()<1){
                    System.out.println(localFile.get(i)+" upload error!");
                }
                File localFilet=new File(localFile.get(i));
                String parentWorkDirectory=ftpClient.printWorkingDirectory();
                upload(localFile.get(i), parentWorkDirectory + "/" + localFilet.getName());
            }
            //�ر�ftp
            closeConnect();
         }catch (IOException e) {
               e.printStackTrace();
         } finally {
             if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
             }
         }
    }



    /**
     * �ϴ��ļ���FTP��������֧�ֶϵ�����
     * @param local �����ļ����ƣ�����·��
     * @param remote Զ���ļ�·����ʹ��/home/directory1/subdirectory/file.ext ����Linux�ϵ�·��ָ����ʽ��֧�ֶ༶Ŀ¼Ƕ�ף�֧�ֵݹ鴴�������ڵ�Ŀ¼�ṹ
     * @return �ϴ����
     * @throws IOException
     */
    public static int upload(String local,String remote) throws IOException{
        //����PassiveMode����
        ftpClient.enterLocalPassiveMode();
        //�����Զ��������ķ�ʽ����
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        int result=0;
        //��Զ��Ŀ¼�Ĵ���
        String remoteFileName = remote;
        if(remote.contains("/")){
            remoteFileName = remote.substring(remote.lastIndexOf("/")+1);
            String directory = remote.substring(0,remote.lastIndexOf("/")+1);
            if(!directory.equalsIgnoreCase("/")&&!ftpClient.changeWorkingDirectory(directory)){
                //���Զ��Ŀ¼�����ڣ���ݹ鴴��Զ�̷�����Ŀ¼
                int start=0;
                int end = 0;
                if(directory.startsWith("/")){
                    start = 1;
                }else{
                    start = 0;
                }
                end = directory.indexOf("/",start);
                while(true){
                    String subDirectory = remote.substring(start,end);
                    if(!ftpClient.changeWorkingDirectory(subDirectory)){
                        if(ftpClient.makeDirectory(subDirectory)){
                            ftpClient.changeWorkingDirectory(subDirectory);
                        }else {
                            System.out.println("����Ŀ¼ʧ��");
                            return FTPUploadStatus.Create_Directory_Fail;
                        }
                    }

                    start = end + 1;
                    end = directory.indexOf("/",start);

                    //�������Ŀ¼�Ƿ񴴽����
                    if(end <= start){
                        break;
                    }
                }
            }
        }

        //���Զ���Ƿ�����ļ�
        FTPFile[] files = ftpClient.listFiles(remoteFileName);
        if(files.length == 1){
            long remoteSize = files[0].getSize();
            File f = new File(local);
            long localSize = f.length();
            if(remoteSize==localSize){
                return FTPUploadStatus.File_Exits;
            }else if(remoteSize > localSize){
                return FTPUploadStatus.Remote_Bigger_Local;
            }

            //�����ƶ��ļ��ڶ�ȡָ��,ʵ�ֶϵ�����
            InputStream is = new FileInputStream(f);
            if(is.skip(remoteSize)==remoteSize){
                ftpClient.setRestartOffset(remoteSize);
                if(ftpClient.storeFile(remote, is)){
                    return FTPUploadStatus.Upload_From_Break_Success;
                }
            }

            //����ϵ�����û�гɹ�����ɾ�����������ļ��������ϴ�
            if(!ftpClient.deleteFile(remoteFileName)){
                return FTPUploadStatus.Delete_Remote_Faild;
            }
            is = new FileInputStream(f);
            if(ftpClient.storeFile(remote, is)){
                result = FTPUploadStatus.Upload_New_File_Success;
            }else{
                result = FTPUploadStatus.Upload_New_File_Failed;
            }
            is.close();
        }else {
            InputStream is = new FileInputStream(local);
            if(ftpClient.storeFile(remoteFileName, is)){
                result = FTPUploadStatus.Upload_New_File_Success;
            }else{
                result = FTPUploadStatus.Upload_New_File_Failed;
            }
            is.close();
        }
        return result;
    }


    /**
     * ��FTP�������������ļ�
     * @param remote Զ���ļ�·��
     * @param local �����ļ�·��
     * @return �Ƿ�ɹ�
     * @throws IOException
     */

    public static boolean downloadFile(String remote,String local) throws IOException {
        if(!ftpClient.isConnected()){
            connectServer(
                    UtilTool.utilproperty.getProperty("FTP_IPADDRESS").toString(),
                    Integer.parseInt(UtilTool.utilproperty.getProperty("FTP_PORT").toString()),
                    UtilTool.utilproperty.getProperty("FTP_USER").toString(),
                    Base64.DecodeBase64(UtilTool.utilproperty.getProperty("FTP_PASSWORD").toString()),
                    Base64.DecodeBase64(UtilTool.utilproperty.getProperty("FTP_DICTIONARY").toString())
            );
        }
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        boolean result;
        File f = new File(local);
        FTPFile[] files = ftpClient.listFiles(remote);
        if(files.length != 1){
            System.out.println("Զ���ļ���Ψһ");
            return false;
        }
        long lRemoteSize = files[0].getSize();
        if(f.exists()){
            OutputStream out = new FileOutputStream(f,true);
            System.out.println("�����ļ���СΪ:"+f.length());
            if(f.length() >= lRemoteSize){
                System.out.println("�����ļ���С����Զ���ļ���С��������ֹ");
                return false;
            }
            ftpClient.setRestartOffset(f.length());
            result = ftpClient.retrieveFile(remote, out);
            out.close();
        }else {
            OutputStream out = new FileOutputStream(f);
            result = ftpClient.retrieveFile(remote, out);
            out.close();
        }
        //�ر�FTP
        closeConnect();
        return result;
    }


    public static void main(String agrs[]) {
        String filepath1[] =
                { "C:\\Users\\zhengzhou\\Desktop\\111.jpg"};
        List<String> filepath=new ArrayList<String>();
        filepath.add(filepath1[0]);

        FtpClientUtil ftp = new FtpClientUtil();
        /*
         * ʹ��Ĭ�ϵĶ˿ںš��û����������Լ���Ŀ¼����FTP������
         */
        ftp.connectServer("192.168.10.41",21, "test", "test", "");

        // �ϴ�
        try
        {
            ftp.upload(filepath);

            downloadFile(ftp.ftpClient.printWorkingDirectory()+"/111.jpg","D:\\222.jpg");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        ftp.closeConnect();

    }
}
/**
 *
 */
class FTPUploadStatus {
    public int getReturnStauts() {
        return returnStauts;
    }

    public void setReturnStauts(int returnStauts) {
        this.returnStauts = returnStauts;
    }

    public int returnStauts;
    public final static int Create_Directory_Fail=1;
    public final static int Upload_New_File_Success=2;
    public final static int File_Exits=3;
    public final static int Remote_Bigger_Local=4;
    public final static int Upload_From_Break_Success=5;
    public final static int Upload_New_File_Failed=6;
    public final static int Delete_Remote_Faild =7;

}

