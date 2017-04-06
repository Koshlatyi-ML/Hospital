package dao.jdbc.query;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class Queries {
    private Queries() {
    }

    static String formatInsertColumnNames(List<String> columns) {
        return "(" + String.join(",", columns) + ")";
    }

    static String formatInsertPlaceholders(int count) {
        if (count < 0) {
            throw new IllegalArgumentException();
        }

        return "(" + String.join(",", Collections.nCopies(count, "?")) + ")";
    }

    static String formatUpdateColumnPlaceholders(List<String> columns) {
        return columns.stream()
                .map(c -> c += "=?")
                .collect(Collectors.joining(","));
    }


    static String formatAliasedColumns(List<String> strings) {
        return strings.stream()
                .map(s -> s += " AS \"" + s + "\"")
                .collect(Collectors.joining(","));
    }
}
