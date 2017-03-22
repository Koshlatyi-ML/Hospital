package dao.jdbc.query;

import dao.metadata.PlainTableInfo;

import javax.management.Query;
import java.util.Collections;

public class Queries {
    private Queries(){}

    public static String formatColumnNames(PlainTableInfo tableInfo) {
        return "(" + String.join(",", tableInfo.getColumnNames()) + ")";
    }

    public static String formatPlaceholders(PlainTableInfo tableInfo) {
        int count = tableInfo.getColumnNames().size();
        return "(" + String.join(",", Collections.nCopies(count, "?")) + ")";
    }

    public static String formatColumnPlaceholders(PlainTableInfo tableInfo) {
        return String.join("=?,", tableInfo.getColumnNames());
    }

    public static String formatJoin(PlainTableInfo tableInfo1,
                                    PlainTableInfo tableInfo2,
                                    Join join) {
        return  String.format(
                "%s JOIN %s ON %s.%s = %s.%s ",
                join.toString(),
                tableInfo2.getTableName(),
                tableInfo1.getTableName(),
                tableInfo1.getIdColumnName(),
                tableInfo2.getTableName(),
                tableInfo2.getIdColumnName());
    }
}
