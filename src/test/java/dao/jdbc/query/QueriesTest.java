package dao.jdbc.query;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class QueriesTest {
    @Test(expected = NullPointerException.class)
    public void formatColumnNamesNull() throws Exception {
        Queries.formatInsertColumnNames(null);
    }

    @Test
    public void formatColumnNamesEmpty() throws Exception {
        String tested = Queries.formatInsertColumnNames(Collections.emptyList());
        assertEquals("()", tested);
    }

    @Test
    public void formatColumnNames() throws Exception {
        List<String> strings = Arrays.asList("a", "b", "c");
        String tested = Queries.formatInsertColumnNames(strings);
        assertEquals("(a,b,c)", tested);
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatPlaceholdersNegative() throws Exception {
        Queries.formatInsertPlaceholders(-1);
    }

    @Test
    public void formatPlaceholdersZero() throws Exception {
        String tested = Queries.formatInsertPlaceholders(0);
        assertEquals("()", tested);
    }

    @Test
    public void formatPlaceholders() throws Exception {
        String tested = Queries.formatInsertPlaceholders(3);
        assertEquals("(?,?,?)", tested);
    }

    @Test(expected = NullPointerException.class)
    public void formatColumnPlaceholdersNull() throws Exception {
        Queries.formatUpdateColumnPlaceholders(null);
    }

    @Test
    public void formatColumnPlaceholdersEmpty() throws Exception {
        String tested = Queries.formatUpdateColumnPlaceholders(Collections.emptyList());
        assertEquals("", tested);
    }

    @Test
    public void formatColumnPlaceholders() throws Exception {
        List<String> strings = Arrays.asList("a", "b", "c");
        String tested = Queries.formatUpdateColumnPlaceholders(strings);
        assertEquals("a=?,b=?,c=?", tested);
    }

    @Test(expected = NullPointerException.class)
    public void formatAliasedColumnsNull() throws Exception {
        Queries.formatAliasedColumns(null);
    }

    @Test
    public void formatAliasedColumnsEmpty() throws Exception {
        String tested = Queries.formatAliasedColumns(Collections.emptyList());
        assertEquals("", tested);
    }

    @Test
    public void formatAliasedColumns() throws Exception {
        List<String> strings = Arrays.asList("a", "b", "c");
        String tested = Queries.formatAliasedColumns(strings);
        assertEquals("a AS \"a\",b AS \"b\",c AS \"c\"", tested);
    }
}