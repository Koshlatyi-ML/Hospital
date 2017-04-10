package util.load;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    private PropertyLoader() {

    }

    public static Properties getProperties(String resourceName) {
        InputStream inputStream = PropertyLoader.class.
                getClassLoader().getResourceAsStream(resourceName);
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
