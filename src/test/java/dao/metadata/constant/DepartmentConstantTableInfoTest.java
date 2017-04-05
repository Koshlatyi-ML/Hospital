package dao.metadata.constant;

import dao.metadata.ColumnNameStyle;
import dao.metadata.DepartmentTableInfo;
import org.junit.Test;

import util.load.PropertyLoader;

import java.util.Properties;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class DepartmentConstantTableInfoTest {
    private DepartmentTableInfo tableInfo;
    private Properties tableProperties;
    private final String RESOURCE_PATH = "dao/tables/departments.properties";

    public DepartmentConstantTableInfoTest() {
        tableInfo = ConstantTableInfoFactory.getInstance().getDepartmentTableInfo();
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
    public void getNameColumnShortStyle() throws Exception {
        assertEquals(tableProperties.getProperty("name"), tableInfo.getNameColumn(ColumnNameStyle.SHORT));
    }

    @Test
    public void getNameColumnFullStyle() throws Exception {
        assertEquals(tableProperties.getProperty("table") + "." + tableProperties.getProperty("name"),
                tableInfo.getNameColumn(ColumnNameStyle.FULL));
    }


    @Test
    public void getNonGeneratingColumns() throws Exception {
        assertArrayEquals(new String[]{tableProperties.getProperty("name")},
                tableInfo.getNonGeneratingColumns().toArray());
    }

}