package com.lucky.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ConfigReader {
    private static final String CONFIG_FILE = "/Users/Stitch/Downloads/config.properties";
    private static final Properties properties;
    private static final Logger logger = Logger.getLogger(ConfigReader.class.getName());

    static {
        properties = new Properties();
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new IOException("Unable to load config.properties file");
            }
        } catch (IOException e) {
            logger.info("发生算术异常"+e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}