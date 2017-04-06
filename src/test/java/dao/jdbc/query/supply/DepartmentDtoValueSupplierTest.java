package dao.jdbc.query.supply;

import domain.dto.DepartmentDTO;
import org.junit.Test;

import java.sql.PreparedStatement;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DepartmentDtoValueSupplierTest {
    private DepartmentDtoValueSupplier dtoValueSupplier;

    public DepartmentDtoValueSupplierTest() {
        dtoValueSupplier =  new DepartmentDtoValueSupplier();
    }


    @Test(expected = NullPointerException.class)
    public void supplyValuesNullFirst() throws Exception {
        dtoValueSupplier.supplyValues(null, mock(DepartmentDTO.class));
    }

    @Test(expected = NullPointerException.class)
    public void supplyValuesNullSecond() throws Exception {
        dtoValueSupplier.supplyValues(mock(PreparedStatement.class), null);
    }

    @Test
    public void supplyValuesVerifySetting() throws Exception {
        DepartmentDTO dtoMock = mock(DepartmentDTO.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        dtoValueSupplier.supplyValues(preparedStatementMock, dtoMock);
        verify(preparedStatementMock).setString(1, dtoMock.getName());
    }

    public void supplyValuesSettedAmount() throws Exception {
        DepartmentDTO dtoMock = mock(DepartmentDTO.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        assertEquals(1, dtoValueSupplier.supplyValues(preparedStatementMock, dtoMock));
    }
}