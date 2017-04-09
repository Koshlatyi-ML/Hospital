package util.load;

import org.junit.Test;
import java.util.Properties;

import static org.junit.Assert.*;

public class PropertyLoaderTest {
    private String examplePropertyName;

    public PropertyLoaderTest() {
        examplePropertyName = "util/example.properties";
    }

    @Test(expected = NullPointerException.class)
    public void getPropertiesNullResource() throws Exception {
        PropertyLoader.getProperties(null);
    }

    @Test(expected = NullPointerException.class)
    public void getPropertiesNonExistentResource() throws Exception {
        PropertyLoader.getProperties("nonexistent.properties");
    }


    @Test
    public void getProperties() throws Exception {
        Properties properties = PropertyLoader.getProperties(examplePropertyName);

        assertEquals(properties.getProperty("1"), "property1");
        assertEquals(properties.getProperty("2"), "property2");
        assertEquals(properties.getProperty("3"), "property3");
    }
}