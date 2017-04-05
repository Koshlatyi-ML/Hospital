package util.load.init;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.naming.Context;

import static org.junit.Assert.*;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class JndiInitializerTest {
    @Mock
    private Context localContextMock;

    private final String JNDI_PATH_1 = "env/resource1";
    private final String JNDI_PATH_2= "env/resource2";

    class Tested {
        @Jndi(JNDI_PATH_1)
        private String resorce1;

        @Jndi(JNDI_PATH_2)
        private String resorce2;
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void initializeNull() throws Exception {
        JndiInitializer jndiInitializer = new JndiInitializer(localContextMock);
        jndiInitializer.initialize(null);
    }

    @Test
    public void initializeWrongJndi() throws Exception {
        when(localContextMock.lookup(JNDI_PATH_1)).thenReturn(null);
        when(localContextMock.lookup(JNDI_PATH_2)).thenReturn(null);

        Tested object = new Tested();
        JndiInitializer jndiInitializer = new JndiInitializer(localContextMock);
        jndiInitializer.initialize(object);

        assertNull(object.resorce1);
        assertNull(object.resorce2);
    }

    @Test
    public void initialize() throws Exception {
        String resource1 = "res1";
        String resource2 = "res2";

        when(localContextMock.lookup(JNDI_PATH_1)).thenReturn(resource1);
        when(localContextMock.lookup(JNDI_PATH_2)).thenReturn(resource2);

        Tested object = new Tested();
        JndiInitializer jndiInitializer = new JndiInitializer(localContextMock);
        jndiInitializer.initialize(object);

        assertEquals(resource1, object.resorce1);
        assertEquals(resource2, object.resorce2);
    }
}