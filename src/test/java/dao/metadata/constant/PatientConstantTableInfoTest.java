package dao.metadata.constant;

import dao.metadata.ColumnNameStyle;
import dao.metadata.MedicTableInfo;
import dao.metadata.PatientTableInfo;
import org.junit.Test;
import util.load.PropertyLoader;

import java.util.Properties;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class PatientConstantTableInfoTest {
    private PatientTableInfo tableInfo;
    private Properties tableProperties;
    private final String RESOURCE_PATH = "dao/tables/patients.properties";

    public PatientConstantTableInfoTest() {
        tableInfo = ConstantTableInfoFactory.getInstance().getPatientTableInfo();
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
        assertEquals(tableProperties.getProperty("name"),
                tableInfo.getNameColumn(ColumnNameStyle.SHORT));
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
    public void getDoctorIdColumnShortStyle() throws Exception {
        assertEquals(tableProperties.getProperty("doctor_id"),
                tableInfo.getDoctorIdColumn(ColumnNameStyle.SHORT));
    }

    @Test
    public void getDoctorIdColumnFullStyle() throws Exception {
        assertEquals(tableProperties.getProperty("table") +
                        "." + tableProperties.getProperty("doctor_id"),
                tableInfo.getDoctorIdColumn(ColumnNameStyle.FULL));
    }

    @Test
    public void getDiagnosisColumnShortStyle() throws Exception {
        assertEquals(tableProperties.getProperty("diagnosis"),
                tableInfo.getDiagnosisColumn(ColumnNameStyle.SHORT));
    }

    @Test
    public void getDiagnosisColumnFullStyle() throws Exception {
        assertEquals(tableProperties.getProperty("table") +
                        "." + tableProperties.getProperty("diagnosis"),
                tableInfo.getDiagnosisColumn(ColumnNameStyle.FULL));
    }

    @Test
    public void getComplaintsColumnShortStyle() throws Exception {
        assertEquals(tableProperties.getProperty("complaints"),
                tableInfo.getComplaintsColumn(ColumnNameStyle.SHORT));
    }

    @Test
    public void getComplaintsColumnFullStyle() throws Exception {
        assertEquals(tableProperties.getProperty("table") +
                        "." + tableProperties.getProperty("complaints"),
                tableInfo.getComplaintsColumn(ColumnNameStyle.FULL));
    }

    @Test
    public void getStateColumnShortStyle() throws Exception {
        assertEquals(tableProperties.getProperty("state"),
                tableInfo.getStateColumn(ColumnNameStyle.SHORT));
    }

    @Test
    public void getStateColumnFullStyle() throws Exception {
        assertEquals(tableProperties.getProperty("table") +
                        "." + tableProperties.getProperty("state"),
                tableInfo.getStateColumn(ColumnNameStyle.FULL));
    }

    @Test
    public void getNonGeneratingColumns() throws Exception {
        assertArrayEquals(new String[]{
                        tableProperties.getProperty("name"),
                        tableProperties.getProperty("surname"),
                        tableProperties.getProperty("doctor_id"),
                        tableProperties.getProperty("complaints"),
                        tableProperties.getProperty("diagnosis"),
                        tableProperties.getProperty("state")
                },
                tableInfo.getNonGeneratingColumns().toArray());
    }

}