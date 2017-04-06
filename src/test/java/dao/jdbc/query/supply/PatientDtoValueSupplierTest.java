package dao.jdbc.query.supply;

import domain.dto.PatientDTO;
import org.junit.Test;

import java.sql.PreparedStatement;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PatientDtoValueSupplierTest {
    private PatientDtoValueSupplier dtoValueSupplier;

    public PatientDtoValueSupplierTest() {
        dtoValueSupplier =  new PatientDtoValueSupplier();
    }


    @Test(expected = NullPointerException.class)
    public void supplyValuesNullFirst() throws Exception {
        dtoValueSupplier.supplyValues(null, mock(PatientDTO.class));
    }

    @Test(expected = NullPointerException.class)
    public void supplyValuesNullSecond() throws Exception {
        dtoValueSupplier.supplyValues(mock(PreparedStatement.class), null);
    }

    @Test
    public void supplyValuesVerifySetting() throws Exception {
        PatientDTO dtoMock = mock(PatientDTO.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        dtoValueSupplier.supplyValues(preparedStatementMock, dtoMock);
        verify(preparedStatementMock).setString(1, dtoMock.getName());
        verify(preparedStatementMock).setString(2, dtoMock.getSurname());
        verify(preparedStatementMock).setLong(3, dtoMock.getDoctorId());
        verify(preparedStatementMock).setString(4, dtoMock.getComplaints());
        verify(preparedStatementMock).setString(5, dtoMock.getDiagnosis());
        verify(preparedStatementMock).setString(6, dtoMock.getState());
    }

    @Test
    public void supplyValuesCountSetting() throws Exception {
        PatientDTO dtoMock = mock(PatientDTO.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        assertEquals(6, dtoValueSupplier.supplyValues(preparedStatementMock, dtoMock));
    }
}