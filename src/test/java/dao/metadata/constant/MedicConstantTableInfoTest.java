package dao.metadata.constant;

import dao.metadata.ColumnNameStyle;
import dao.metadata.DoctorTableInfo;
import dao.metadata.MedicTableInfo;
import org.junit.Test;
import util.load.PropertyLoader;

import java.util.Properties;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class MedicConstantTableInfoTest {
    private MedicTableInfo tableInfo;
    private Properties tableProperties;
    private final String RESOURCE_PATH = "dao/tables/medics.properties";

    public MedicConstantTableInfoTest() {
        tableInfo = ConstantTableInfoFactory.getInstance().getMedicTableInfo();
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
    public void getNonGeneratingColumns() throws Exception {
        assertArrayEquals(new String[]{tableProperties.getProperty("id"),
                tableProperties.getProperty("credentials_id")},
                tableInfo.getNonGeneratingColumns().toArray());
    }
}