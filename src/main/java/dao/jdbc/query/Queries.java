package dao.jdbc.query;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class Queries {
    private Queries() {
    }

    static String formatColumnNames(List<String> columns) {
        return "(" + String.join(",", columns) + ")";
    }

    static String formatPlaceholders(int count) {
        return "(" + String.join(",", Collections.nCopies(count, "?")) + ")";
    }

    static String formatColumnPlaceholders(List<String> columns) {
        return columns.stream()
                .map(c -> c += "=?")
                .collect(Collectors.joining(", "));
    }
}
