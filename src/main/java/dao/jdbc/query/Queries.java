package dao.jdbc.query;

import dao.metadata.PlainTableInfo;

import java.util.Collections;
import java.util.List;

public class Queries {
    private Queries() {}

    public static String formatColumnNames(List<String> columns) {
        return "(" + String.join(",", columns) + ")";
    }

    public static String formatPlaceholders(int count) {
        return "(" + String.join(",", Collections.nCopies(count, "?")) + ")";
    }

    public static String formatColumnPlaceholders(List<String> columns) {
        return String.join("=?,", columns);
    }
}
