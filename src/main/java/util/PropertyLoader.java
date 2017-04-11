package util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    private static final Logger LOG = LogManager.getLogger(PropertyLoader.class);

    private PropertyLoader() {

    }

    public static Properties getProperties(String resourceName) {
        Properties properties = new Properties();
        InputStream inputStream =
                PropertyLoader.class.getClassLoader().getResourceAsStream(resourceName);

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            LOG.log(Level.ERROR, "Can't load properties from stream", e);
            throw new PropertyLoaderException(e);
        }

        return properties;
    }
}
