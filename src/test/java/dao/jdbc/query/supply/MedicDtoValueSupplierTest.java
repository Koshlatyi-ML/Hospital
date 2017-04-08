package dao.jdbc.query.supply;

import domain.dto.MedicDTO;
import domain.dto.MedicDTO;
import org.junit.Test;

import java.sql.PreparedStatement;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MedicDtoValueSupplierTest {
    private MedicDtoValueSupplier dtoValueSupplier;

    public MedicDtoValueSupplierTest() {
        dtoValueSupplier =  new MedicDtoValueSupplier();
    }


    @Test(expected = NullPointerException.class)
    public void supplyValuesNullFirst() throws Exception {
        dtoValueSupplier.supplyValues(null, mock(MedicDTO.class));
    }

    @Test(expected = NullPointerException.class)
    public void supplyValuesNullSecond() throws Exception {
        dtoValueSupplier.supplyValues(mock(PreparedStatement.class), null);
    }

    @Test
    public void supplyValuesVerifySetting() throws Exception {
        MedicDTO dtoMock = mock(MedicDTO.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        dtoValueSupplier.supplyValues(preparedStatementMock, dtoMock);
        verify(preparedStatementMock).setLong(1, dtoMock.getId());
    }

    @Test
    public void supplyValuesCountSetting() throws Exception {
        MedicDTO dtoMock = mock(MedicDTO.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        assertEquals(2, dtoValueSupplier.supplyValues(preparedStatementMock, dtoMock));
    }

    @Test
    public void supplyStuffValuesVerifySetting() throws Exception {
        MedicDTO dtoMock = mock(MedicDTO.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        dtoValueSupplier.supplyStuffValues(preparedStatementMock, dtoMock);
        verify(preparedStatementMock).setString(1, dtoMock.getName());
        verify(preparedStatementMock).setString(2, dtoMock.getSurname());
        verify(preparedStatementMock).setLong(3, dtoMock.getDepartmentId());
    }

    @Test
    public void supplyStuffValuesCountSetting() throws Exception {
        MedicDTO dtoMock = mock(MedicDTO.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        assertEquals(3, dtoValueSupplier.supplyStuffValues(preparedStatementMock, dtoMock));
    }
}