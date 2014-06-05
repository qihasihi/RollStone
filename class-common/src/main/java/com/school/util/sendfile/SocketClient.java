package com.school.util.sendfile;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import com.school.util.UtilTool;


//�ͻ��˷����ļ���

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
		String path = "f:\\35.xml"; // �����ļ�		
		client.sendMessage();
	}

	public void run() {
		sendMessage();
	}

	/**
	 * �����ӵ�Զ�̷�����
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
	 * ����Զ�̷��������� path ������
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
        //if(souceid==null){//����ԭ��û���ϴ������ļ��������ݿ����һ���󶨼�¼
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






			// ���ļ��������ȴ����ͻ��ˡ�
//			ps.writeUTF(fi.getName());
//			ps.flush();
//
//			ps.writeLong((long) fi.length());
//			ps.flush();
//			int bufferSize = 8192;
//
//            PushbackInputStream inStream = new PushbackInputStream(client.getInputStream());
//            //�õ����ݵ�λ��
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
			System.out.println("�ļ��������");
            return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            return false;
		} finally {

			// ע��ر�socket����Ŷ����Ȼ�ͻ��˻�ȴ�server�����ݹ�����
			// ֱ��socket��ʱ���������ݲ�������
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
     * ��ȡ��
     * @param inStream
     * @return �ֽ�����
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
