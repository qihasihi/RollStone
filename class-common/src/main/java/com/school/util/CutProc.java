package com.school.util;

import java.io.*;

public class CutProc {
    private BufferedReader reader = null;
    private FileOutputStream out = null;
    private PrintWriter writer = null;
    public CutProc(){};
    public boolean cutProc(String path) {                //参数path为文件的绝对路径
        File file = new File(path);
        path = file.getParent();                        //获取文件所在目录
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        while(true) {                                   //外循环读取全部文件，直到读到文件结尾捕获异常退出
            try {
                String line = reader.readLine();
                do {
                    line = reader.readLine();
                    if (line == null)
                        return true;
                    System.out.println("in while:" + line);
                } while(!line.contains("DEFINER"));
                String []args = line.split("`");
                File f = new File(path + "/"+args[5]+".sql");
                out = new FileOutputStream(f);
                writer = new PrintWriter(out);
                writer.println("DELIMITER $$");
                writer.println("");


                writer.println("USE `m_school`$$");
                writer.println("");
                writer.println("DROP PROCEDURE IF EXISTS `" + args[5] + "`$$");
                writer.println("");
                line = line.substring(9);                   //截取首句前面的“/*!50003 ”
                while(!line.contains("*/$$")) {
                    try {
                        writer.println(line);
                        line = reader.readLine();
                    } catch (Exception e) {
                        System.out.println(e.getStackTrace());
                        return false;
                    }
                }
                try {
                    writer.println("END $$");
                    writer.println("");
                    writer.println("DELIMITER ;");
                    writer.close();
                    out.close();
                } catch(Exception e) {
                    System.out.println(e.getStackTrace());
                    return false;
                }
            } catch(Exception e) {                          //读到文件结尾捕获异常
                return false;
            }
        }
    }
}