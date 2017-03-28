package dao.jdbc.query;

import java.util.Collections;
import java.util.List;

class Queries {
    private Queries() {}

    static String formatColumnNames(List<String> columns) {
        return "(" + String.join(",", columns) + ")";
    }

    static String formatPlaceholders(int count) {
        return "(" + String.join(",", Collections.nCopies(count, "?")) + ")";
    }

    static String formatColumnPlaceholders(List<String> columns) {
        return String.join("=?,", columns);
    }
}
