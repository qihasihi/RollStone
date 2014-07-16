package com.school.util.sendfile;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import com.school.util.UtilTool;


//客户端发送文件类

public class SocketClient extends Thread {
	protected String hostIp = UtilTool.utilproperty.getProperty("localIP");
	protected int hostPort = Integer.parseInt(UtilTool.utilproperty.getProperty("localPort"));
    protected String schoolid=UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_ID");
	InputStream fis;
	DataOutputStream ps;
	File fi;
	DataInputStream dis;
	String path;
	Socket client;

	public SocketClient() {
	}

	public SocketClient(String path) {
		this.path = path;
	}

	public SocketClient(String path, String aHostIp, int aHostPort) {
		this.path = path;
		this.hostIp = aHostIp;
		this.hostPort = aHostPort;
	}

	public static void main(String[] args) {
		SocketClient client = new SocketClient();
		String path = "f:\\35.xml"; // 测试文件		
		client.sendMessage();
	}

	public void run() {
		sendMessage();
	}

	/**
	 * 将连接到远程服务器
	 */
	public void setUpConnection() {
		try {
			client = new Socket(hostIp, hostPort);
			fis = new FileInputStream(path);
			ps = new DataOutputStream(client.getOutputStream());
			fi = new File(path);
		} catch (UnknownHostException e) {
			System.out
					.println("Error setting up socket connection: unknown host at "
							+ hostIp + ":" + hostPort);
		} catch (IOException e) {
			System.out.println("Error setting up socket connection: " + e);
		}

	}





	/**
	 * 将向远程服务器请求 path 的内容
	 *
	 * @return
	 */
	public boolean sendMessage() {
		setUpConnection();
		if (client == null)
			return false;
        try {
        String head = "Content-Length="+ fi.length() + ";filename="+ fi.getName() + ";schoolid="+schoolid+"\r\n";
        OutputStream outStream = client.getOutputStream();
        outStream.write(head.getBytes());

        PushbackInputStream inStream = new PushbackInputStream(client.getInputStream());
        String response = StreamTool.readLine(inStream);
        String[] items = response.split(";");
       // String responseid = items[0].substring(items[0].indexOf("=")+1);
        String position = items[0].substring(items[0].indexOf("=")+1);
        //if(souceid==null){//代表原来没有上传过此文件，往数据库添加一条绑定记录
           //logService.save(responseid, fi);
       // }
        RandomAccessFile fileOutStream = new RandomAccessFile(fi, "r");
        fileOutStream.seek(Integer.valueOf(position));
        byte[] buffer = new byte[1024];
        int len = -1;
        int length = Integer.valueOf(position);
        while( (len = fileOutStream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
            length += len;
//            Message msg = new Message();
//            msg.getData().putInt("size", length);
//            handler.sendMessage(msg);
        }
        fileOutStream.close();
        outStream.close();
        inStream.close();
        client.close();






			// 将文件名及长度传给客户端。
//			ps.writeUTF(fi.getName());
//			ps.flush();
//
//			ps.writeLong((long) fi.length());
//			ps.flush();
//			int bufferSize = 8192;
//
//            PushbackInputStream inStream = new PushbackInputStream(client.getInputStream());
//            //得到传递的位置
//            String response = StreamTool.readLine(inStream);
//            String[] items = response.split(";");
//            String position = items[0].substring(items[0].indexOf("=")+1);
//
//
//            RandomAccessFile fileOutStream = new RandomAccessFile(fi, "r");
//            fileOutStream.seek(Integer.valueOf(position));
//
//            byte[] buf = new byte[bufferSize];
//			while (true) {
//
//                int read = 0;
//				if (fis != null) {
//					read = fis.read(buf);
//				}
//				if (read == -1) {
//					break;
//				}
//				ps.write(buf, 0, read);
//			}
//            ps.flush();
//            fileOutStream.close();
//            inStream.close();
			System.out.println("文件传输完成");
            return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            return false;
		} finally {

			// 注意关闭socket链接哦，不然客户端会等待server的数据过来，
			// 直到socket超时，导致数据不完整。
			try {
				ps.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}

class StreamTool {

    public static void save(File file, byte[] data) throws Exception {
        FileOutputStream outStream = new FileOutputStream(file);
        outStream.write(data);
        outStream.close();
    }

    public static String readLine(PushbackInputStream in) throws IOException {
        char buf[] = new char[128];
        int room = buf.length;
        int offset = 0;
        int c;
        loop:		while (true) {
            switch (c = in.read()) {
                case -1:
                case '\n':
                    break loop;
                case '\r':
                    int c2 = in.read();
                    if ((c2 != '\n') && (c2 != -1)) in.unread(c2);
                    break loop;
                default:
                    if (--room < 0) {
                        char[] lineBuffer = buf;
                        buf = new char[offset + 128];
                        room = buf.length - offset - 1;
                        System.arraycopy(lineBuffer, 0, buf, 0, offset);

                    }
                    buf[offset++] = (char) c;
                    break;
            }
        }
        if ((c == -1) && (offset == 0)) return null;
        return String.copyValueOf(buf, 0, offset);
    }

    /**
     * 读取流
     * @param inStream
     * @return 字节数组
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while( (len=inStream.read(buffer)) != -1){
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }
}
