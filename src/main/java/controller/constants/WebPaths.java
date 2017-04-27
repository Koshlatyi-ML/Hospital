package controller.constants;

import java.util.ResourceBundle;

public class WebPaths {

    private static ResourceBundle bundle = ResourceBundle.getBundle("controller/paths");

    private WebPaths() {}

    public static String get(String key) {
        return bundle.getString(key);
    }
}
