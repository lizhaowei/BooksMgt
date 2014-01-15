package org.lzw.booksmgt.conf;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.lzw.booksmgt.utils.BookConst;

/**
 * created by Li Zhaowei on 2014-1-13 下午9:20:55 Detailed comment *
 */
public class ConfigurationHandler {

    private static Logger log = Logger.getLogger(ConfigurationHandler.class);
    private static ConfigurationHandler instance;

    public static synchronized ConfigurationHandler getInstance() {
        if (instance == null) {
            instance = new ConfigurationHandler();
        }
        return instance;
    }

    private Properties p;

    private ConfigurationHandler() {
        init();
    }

    private void init() {
        URL conf = ConfigurationHandler.class.getResource("/configuration.properties");
        p = new Properties();
        try {
            p.load(conf.openStream());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public String getValue(String key) {
        return p.getProperty(key);
    }

    public static void main(String[] args) {
        String s = ConfigurationHandler.getInstance().getValue(BookConst.DATABASE_URL);
        System.out.println(s);
    }
}
