package controller.constants;

import java.util.*;

public class WebResources {

    private static ResourceBundle bundle = ResourceBundle.getBundle("controller/resources");

    private WebResources() {}

    public static String get(String key) {
        return bundle.getString(key);
    }

    public static final List<String> allowedFilenameExtensions =
            Arrays.asList(".css", ".js", ".js.map", ".png", ".ico",
                    ".woff", ".woff2", "ttf", ".jpg");
}
