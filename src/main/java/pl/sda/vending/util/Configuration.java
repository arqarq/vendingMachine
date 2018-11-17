package pl.sda.vending.util;

import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    private final Properties properties;

    public Configuration() {
        properties = new Properties();
        try (InputStream propertiesStream = ClassLoader
                .getSystemResourceAsStream("application.properties")) {
            properties.load(propertiesStream); // po try jest od razu close(), JVM gwarantuje
        } catch (Exception e) {
            e.printStackTrace();
        }
//        properties.list(System.out); // wypisanie wszystkich
    }

    public Long getLongProperty(String paramName, Long defaultValue) {
        final String propertyValue = properties.getProperty(paramName);
        return propertyValue != null ? Long.parseLong(propertyValue) : defaultValue;
    }

    public Integer getIntProperty(String paramName, Integer defaultValue) {
        final String propertyValue = properties.getProperty(paramName);
        return propertyValue != null ? Integer.parseInt(propertyValue) : defaultValue;
    }

    public String getStringProperty(String paramName, String defaultValue) {
        return properties.getProperty(paramName, defaultValue);
    }
}
