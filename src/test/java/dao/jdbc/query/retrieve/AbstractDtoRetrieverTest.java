package dao.jdbc.query.retrieve;

import domain.dto.AbstractDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbstractDtoRetrieverTest {

    @Spy
    private AbstractDtoRetriever abstractDtoRetrieverMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void retrieveDtoEmpty() throws Exception {
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(false);
        Optional optional = abstractDtoRetrieverMock.retrieveDTO(resultSetMock);
        assertFalse(optional.isPresent());
    }

    @Test
    public void retrieveDtoNotEmpty() throws Exception {
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(true);
        AbstractDTO abstractDtoMock = mock(AbstractDTO.class);
        when(abstractDtoRetrieverMock.retrieve(resultSetMock)).thenReturn(abstractDtoMock);

        assertEquals(Optional.of(abstractDtoMock),
                abstractDtoRetrieverMock.retrieveDTO(resultSetMock));
    }


    @Test
    public void retrieveDtoListEmpty() throws Exception {
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(false);
        List list = abstractDtoRetrieverMock.retrieveDtoList(resultSetMock);
        assertTrue(list.isEmpty());
    }

    @Test
    public void retrieveDtoList() throws Exception {
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        AbstractDTO abstractDtoMock1 = mock(AbstractDTO.class);
        AbstractDTO abstractDtoMock2 = mock(AbstractDTO.class);

        when(abstractDtoRetrieverMock.retrieve(resultSetMock))
                .thenReturn(abstractDtoMock1)
                .thenReturn(abstractDtoMock2);


        List list = abstractDtoRetrieverMock.retrieveDtoList(resultSetMock);
        assertArrayEquals(list.toArray(), new AbstractDTO[] {
                abstractDtoMock1, abstractDtoMock2
        });
    }
}