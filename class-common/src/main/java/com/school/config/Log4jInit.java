package com.school.config;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * log4j��ʼ����
 */
public class Log4jInit extends HttpServlet {
    static Logger logger = Logger.getLogger(Log4jInit.class);
    public Log4jInit() {
    }

    public void init(ServletConfig config) throws ServletException {
        String prefix = config.getServletContext().getRealPath("/");
        String file = config.getInitParameter("log4j");
        String filePath = prefix + file;
        Properties props = new Properties();
        try {
            FileInputStream istream = new FileInputStream(filePath);
            props.load(istream);
            istream.close();
            //toPrint(props.getProperty("log4j.appender.file.File"));
            String logFile = prefix + props.getProperty("log4j.appender.file.File");//����·��
            props.setProperty("log4j.appender.file.File",logFile);
            PropertyConfigurator.configure(props);//װ��log4j������Ϣ
        } catch (IOException e) {
            toPrint("Could not read configuration file [" + filePath + "].");
            toPrint("Ignoring configuration file [" + filePath + "].");
            return;
        }
    }

    public static void toPrint(String content) {
        System.out.println(content);
    }
}