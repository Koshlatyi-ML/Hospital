package dao.metadata.constant;

import dao.metadata.ColumnNameStyle;
import dao.metadata.TherapyTableInfo;
import org.junit.Test;
import util.load.PropertyLoader;

import java.util.Properties;

import static org.junit.Assert.*;

public class TherapyConstantTableInfoTest {
    private TherapyTableInfo tableInfo;
    private Properties tableProperties;
    private final String RESOURCE_PATH = "dao/tables/therapies.properties";

    public TherapyConstantTableInfoTest() {
        tableInfo = ConstantTableInfoFactory.getInstance().getTherapyTableInfo();
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
    public void getTitleColumnShortStyle() throws Exception {
        assertEquals(tableProperties.getProperty("title"),
                tableInfo.getTitleColumn(ColumnNameStyle.SHORT));
    }

    @Test
    public void getTitleColumnFullStyle() throws Exception {
        assertEquals(tableProperties.getProperty("table") +
                        "." + tableProperties.getProperty("title"),
                tableInfo.getTitleColumn(ColumnNameStyle.FULL));
    }

    @Test
    public void getTypeColumnShortStyle() throws Exception {
        assertEquals(tableProperties.getProperty("type"),
                tableInfo.getTypeColumn(ColumnNameStyle.SHORT));
    }

    @Test
    public void getTypeColumnFullStyle() throws Exception {
        assertEquals(tableProperties.getProperty("table") +
                        "." + tableProperties.getProperty("type"),
                tableInfo.getTypeColumn(ColumnNameStyle.FULL));
    }

    @Test
    public void getDescriptionColumnShortStyle() throws Exception {
        assertEquals(tableProperties.getProperty("description"),
                tableInfo.getDescriptionColumn(ColumnNameStyle.SHORT));
    }

    @Test
    public void getDescriptionColumnFullStyle() throws Exception {
        assertEquals(tableProperties.getProperty("table") +
                        "." + tableProperties.getProperty("description"),
                tableInfo.getDescriptionColumn(ColumnNameStyle.FULL));
    }

    @Test
    public void getAppointmentDateColumnShortStyle() throws Exception {
        assertEquals(tableProperties.getProperty("appointment_date"),
                tableInfo.getAppointmentDateColumn(ColumnNameStyle.SHORT));
    }

    @Test
    public void getAppointmentDateColumnFullStyle() throws Exception {
        assertEquals(tableProperties.getProperty("table") +
                        "." + tableProperties.getProperty("appointment_date"),
                tableInfo.getAppointmentDateColumn(ColumnNameStyle.FULL));
    }

    @Test
    public void getCompleteDateColumnShortStyle() throws Exception {
        assertEquals(tableProperties.getProperty("complete_date"),
                tableInfo.getCompleteDateColumn(ColumnNameStyle.SHORT));
    }

    @Test
    public void getCompleteDateColumnFullStyle() throws Exception {
        assertEquals(tableProperties.getProperty("table") +
                        "." + tableProperties.getProperty("complete_date"),
                tableInfo.getCompleteDateColumn(ColumnNameStyle.FULL));
    }

    @Test
    public void getPatientIdColumnShortStyle() throws Exception {
        assertEquals(tableProperties.getProperty("patient_id"),
                tableInfo.getPatientIdColumn(ColumnNameStyle.SHORT));
    }

    @Test
    public void getPatientIdColumnFullStyle() throws Exception {
        assertEquals(tableProperties.getProperty("table") +
                        "." + tableProperties.getProperty("patient_id"),
                tableInfo.getPatientIdColumn(ColumnNameStyle.FULL));
    }

    @Test
    public void getPerformerIdColumnShortStyle() throws Exception {
        assertEquals(tableProperties.getProperty("performer_id"),
                tableInfo.getPerformerIdColumn(ColumnNameStyle.SHORT));
    }

    @Test
    public void getPerformerIdColumnFullStyle() throws Exception {
        assertEquals(tableProperties.getProperty("table") +
                        "." + tableProperties.getProperty("performer_id"),
                tableInfo.getPerformerIdColumn(ColumnNameStyle.FULL));
    }

    @Test
    public void getNonGeneratingColumns() throws Exception {
        assertArrayEquals(new String[]{
                        tableProperties.getProperty("title"),
                        tableProperties.getProperty("type"),
                        tableProperties.getProperty("description"),
                        tableProperties.getProperty("appointment_date"),
                        tableProperties.getProperty("complete_date"),
                        tableProperties.getProperty("patient_id"),
                        tableProperties.getProperty("performer_id")
                },
                tableInfo.getNonGeneratingColumns().toArray());
    }
}