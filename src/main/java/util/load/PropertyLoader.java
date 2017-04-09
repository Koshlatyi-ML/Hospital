package util.load;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PropertyLoader {
//    private static final PropertyLoader INSTANCE = new PropertyLoader();

    private PropertyLoader() {

    }

//    public static PropertyLoader getInstance() {
//        return INSTANCE;
//    }

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
