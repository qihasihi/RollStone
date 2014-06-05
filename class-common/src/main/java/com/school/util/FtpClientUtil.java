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
 * FTP上传工具类
 * @author zhengzhou
 */
public class FtpClientUtil {

    static FTPClient ftpClient=new FTPClient();
    /**
     * 静态块
     */
    static{
    //设置将过程中使用到的命令输出到控制台
     ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
    }


    // server：服务器名字
// user：用户名
// password：密码
// path：服务器上的路径
    public static  boolean connectServer(String ip,int port,String user
            , String password,String path){
        boolean success=false;
        try {
            int reply;
            // 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            if(port==22)
                ftpClient.connect(ip);
              else
                ftpClient.connect(ip,port);
            // 登录ftp
            ftpClient.login(user, password);
            // 看返回的值是不是230，如果是，表示登陆成功
            reply = ftpClient.getReplyCode();
            // 以2开头的返回值就会为真
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
     * 修改目录
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
            //关闭ftp
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
     * 上传文件到FTP服务器，支持断点续传
     * @param local 本地文件名称，绝对路径
     * @param remote 远程文件路径，使用/home/directory1/subdirectory/file.ext 按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构
     * @return 上传结果
     * @throws IOException
     */
    public static int upload(String local,String remote) throws IOException{
        //设置PassiveMode传输
        ftpClient.enterLocalPassiveMode();
        //设置以二进制流的方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        int result=0;
        //对远程目录的处理
        String remoteFileName = remote;
        if(remote.contains("/")){
            remoteFileName = remote.substring(remote.lastIndexOf("/")+1);
            String directory = remote.substring(0,remote.lastIndexOf("/")+1);
            if(!directory.equalsIgnoreCase("/")&&!ftpClient.changeWorkingDirectory(directory)){
                //如果远程目录不存在，则递归创建远程服务器目录
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
                            System.out.println("创建目录失败");
                            return FTPUploadStatus.Create_Directory_Fail;
                        }
                    }

                    start = end + 1;
                    end = directory.indexOf("/",start);

                    //检查所有目录是否创建完毕
                    if(end <= start){
                        break;
                    }
                }
            }
        }

        //检查远程是否存在文件
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

            //尝试移动文件内读取指针,实现断点续传
            InputStream is = new FileInputStream(f);
            if(is.skip(remoteSize)==remoteSize){
                ftpClient.setRestartOffset(remoteSize);
                if(ftpClient.storeFile(remote, is)){
                    return FTPUploadStatus.Upload_From_Break_Success;
                }
            }

            //如果断点续传没有成功，则删除服务器上文件，重新上传
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
     * 从FTP服务器上下载文件
     * @param remote 远程文件路径
     * @param local 本地文件路径
     * @return 是否成功
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
            System.out.println("远程文件不唯一");
            return false;
        }
        long lRemoteSize = files[0].getSize();
        if(f.exists()){
            OutputStream out = new FileOutputStream(f,true);
            System.out.println("本地文件大小为:"+f.length());
            if(f.length() >= lRemoteSize){
                System.out.println("本地文件大小大于远程文件大小，下载中止");
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
        //关闭FTP
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
         * 使用默认的端口号、用户名、密码以及根目录连接FTP服务器
         */
        ftp.connectServer("192.168.10.41",21, "test", "test", "");

        // 上传
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

