package util.load;

import org.junit.Test;
import java.util.Properties;

import static org.junit.Assert.*;

public class PropertyLoaderTest {
    private PropertyLoader propertyLoader;
    private String examplePropertyName;

    public PropertyLoaderTest() {
        propertyLoader = PropertyLoader.getInstance();
        examplePropertyName = "util/example.properties";
    }

    @Test(expected = NullPointerException.class)
    public void getPropertiesNullResource() throws Exception {
        propertyLoader.getProperties(null);
    }

    @Test(expected = NullPointerException.class)
    public void getPropertiesNonExistentResource() throws Exception {
        propertyLoader.getProperties("nonexistent.properties");
    }


    @Test
    public void getProperties() throws Exception {
        Properties properties = propertyLoader.getProperties(examplePropertyName);

        assertEquals(properties.getProperty("1"), "property1");
        assertEquals(properties.getProperty("2"), "property2");
        assertEquals(properties.getProperty("3"), "property3");
    }
}