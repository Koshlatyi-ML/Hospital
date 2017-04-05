package dao.metadata.constant;

import dao.metadata.ColumnNameStyle;
import dao.metadata.StuffTableInfo;
import org.junit.Test;
import util.load.PropertyLoader;

import java.util.Properties;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class StuffConstantTableInfoTest {
    private StuffTableInfo tableInfo;
    private Properties tableProperties;
    private final String RESOURCE_PATH = "dao/tables/stuff.properties";

    public StuffConstantTableInfoTest() {
        tableInfo = ConstantTableInfoFactory.getInstance().getStuffTableInfo();
        tableProperties = PropertyLoader.getInstance().getProperties(RESOURCE_PATH);
    }


    @Test
    public void getTableName() throws Exception {
        assertEquals(tableProperties.getProperty("table"), tableInfo.getTableName());
    }

    @Test
    public void getIdColumnShortStyle() throws Exception {
        assertEquals(tableProperties.getProperty("id"), tableInfo.getIdColumn(ColumnNameStyle.SHORT));
    }

    @Test
    public void getIdColumnFullStyle() throws Exception {
        assertEquals(tableProperties.getProperty("table") + "." + tableProperties.getProperty("id"),
                tableInfo.getIdColumn(ColumnNameStyle.FULL));
    }

    @Test
    public void getNameColumnFullStyle() throws Exception {
        assertEquals(tableProperties.getProperty("table") +
                        "." + tableProperties.getProperty("name"),
                tableInfo.getNameColumn(ColumnNameStyle.FULL));
    }

    @Test
    public void getSurnameColumnShortStyle() throws Exception {
        assertEquals(tableProperties.getProperty("surname"),
                tableInfo.getSurnameColumn(ColumnNameStyle.SHORT));
    }

    @Test
    public void getSurnameColumnFullStyle() throws Exception {
        assertEquals(tableProperties.getProperty("table") +
                        "." + tableProperties.getProperty("surname"),
                tableInfo.getSurnameColumn(ColumnNameStyle.FULL));
    }

    @Test
    public void getDepartmentIdColumnShortStyle() throws Exception {
        assertEquals(tableProperties.getProperty("department_id"),
                tableInfo.getDepartmentIdColumn(ColumnNameStyle.SHORT));
    }

    @Test
    public void getDepartmentIdColumnFullStyle() throws Exception {
        assertEquals(tableProperties.getProperty("table") +
                        "." + tableProperties.getProperty("department_id"),
                tableInfo.getDepartmentIdColumn(ColumnNameStyle.FULL));
    }

    @Test
    public void getNonGeneratingColumns() throws Exception {
        assertArrayEquals(new String[]{
                        tableProperties.getProperty("name"),
                        tableProperties.getProperty("surname"),
                        tableProperties.getProperty("department_id"),
                },
                tableInfo.getNonGeneratingColumns().toArray());
    }

}