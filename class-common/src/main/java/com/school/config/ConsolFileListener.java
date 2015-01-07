package com.school.config;

import org.apache.commons.logging.impl.LogFactoryImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.PrintStream;

/**
 * 捕获System.out并写入配置文件中
 * Created by zhengzhou on 15-1-7.
 */
public class ConsolFileListener implements ServletContextListener {

    //fatal
    private void log(Object info) {
        LogFactoryImpl.getLog(getClass()).info(info);
    }
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        PrintStream printStream = new PrintStream(System.out) {
            //----------------println
            public void println(boolean x) {
                log(Boolean.valueOf(x));
            }
            public void println(char x) {
                log(Character.valueOf(x));
            }
            public void println(char[] x) {
                log(x == null ? null : new String(x));
            }


            public void println(double x) {
                log(Double.valueOf(x));
            }


            public void println(float x) {
                log(Float.valueOf(x));
            }


            public void println(int x) {
                log(Integer.valueOf(x));
            }


            public void println(long x) {
                log(x);
            }


            public void println(Object x) {
                log(x);
            }


            public void println(String x) {
                log(x);
            }
            //----------------print
            public void print(boolean x) {
                log(Boolean.valueOf(x));
            }
            public void print(char x) {
                log(Character.valueOf(x));
            }
            public void print(char[] x) {
                log(x == null ? null : new String(x));
            }


            public void print(double x) {
                log(Double.valueOf(x));
            }


            public void print(float x) {
                log(Float.valueOf(x));
            }


            public void print(int x) {
                log(Integer.valueOf(x));
            }


            public void print(long x) {
                log(x);
            }


            public void print(Object x) {
                log(x);
            }


            public void print(String x) {
                log(x);
            }


        };

        System.setOut(printStream);
        System.setErr(printStream);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
