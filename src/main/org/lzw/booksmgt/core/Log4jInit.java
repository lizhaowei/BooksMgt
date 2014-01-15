package org.lzw.booksmgt.core;

import java.net.URL;

import org.apache.log4j.PropertyConfigurator;

/**
 * created by zwli on 2014-1-11 Detailed comment
 * 
 */
public class Log4jInit {

    public static void initLog4j() {
        String log4jName = "log4j.properties";
        URL url = Log4jInit.class.getResource("/" + log4jName);
        // System.out.println(url.getFile());
        PropertyConfigurator.configure(url);
    }

    public static void main(String[] args) {
        initLog4j();
    }
}
