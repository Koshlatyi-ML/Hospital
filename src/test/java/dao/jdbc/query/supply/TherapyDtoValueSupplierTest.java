package dao.jdbc.query.supply;

import domain.dto.TherapyDTO;
import org.junit.Test;

import java.sql.PreparedStatement;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TherapyDtoValueSupplierTest {
    private TherapyDtoValueSupplier dtoValueSupplier;

    public TherapyDtoValueSupplierTest() {
        dtoValueSupplier =  new TherapyDtoValueSupplier();
    }


    @Test(expected = NullPointerException.class)
    public void supplyValuesNullFirst() throws Exception {
        dtoValueSupplier.supplyValues(null, mock(TherapyDTO.class));
    }

    @Test(expected = NullPointerException.class)
    public void supplyValuesNullSecond() throws Exception {
        dtoValueSupplier.supplyValues(mock(PreparedStatement.class), null);
    }

    @Test
    public void supplyValuesVerifySetting() throws Exception {
        TherapyDTO dtoMock = mock(TherapyDTO.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        dtoValueSupplier.supplyValues(preparedStatementMock, dtoMock);
        verify(preparedStatementMock).setString(1, dtoMock.getTitle());
        verify(preparedStatementMock).setString(2, dtoMock.getType());
        verify(preparedStatementMock).setString(3, dtoMock.getDescription());
        verify(preparedStatementMock).setTimestamp(4, dtoMock.getAppointmentDateTime());
        verify(preparedStatementMock).setTimestamp(5, dtoMock.getCompleteDateTime());
        verify(preparedStatementMock).setLong(6, dtoMock.getPatientId());
        verify(preparedStatementMock).setLong(7, dtoMock.getPerformerId());
    }

    @Test
    public void supplyValuesCountSetting() throws Exception {
        TherapyDTO dtoMock = mock(TherapyDTO.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        assertEquals(7, dtoValueSupplier.supplyValues(preparedStatementMock, dtoMock));
    }
}