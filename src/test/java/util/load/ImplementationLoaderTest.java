package util.load;

import org.junit.Test;
import util.init.Initializer;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@Implementation(Derived.class)
class LoneClass {}

@Implementation(Derived.class)
class BaseClass {}

class Derived extends BaseClass {
    private Derived() {}
}

public class ImplementationLoaderTest {
    private ImplementationLoader defaultLoader;

    public ImplementationLoaderTest() {
        defaultLoader = ImplementationLoaderFactory.getInstance().createImplementationLoader();
    }

    @Test(expected = NullPointerException.class)
    public void loadInstanceNull() throws Exception {
        defaultLoader.loadInstance(null);
    }

    @Test(expected = ClassCastException.class)
    public void loadInstanceNotInHierarchy() throws Exception {
        defaultLoader.loadInstance(LoneClass.class);
    }

    @Test
    public void loadInstance() throws Exception {
        BaseClass instance = defaultLoader.loadInstance(BaseClass.class);
        assertSame(Derived.class, instance.getClass());
    }

    @Test
    public void loadInstanceСheckInitialized() throws Exception {
        Initializer initializerMock1 = mock(Initializer.class);
        Initializer initializerMock2 = mock(Initializer.class);
        Initializer initializerMock3 = mock(Initializer.class);

        ImplementationLoader mockedLoader =
                new ImplementationLoader(Arrays.asList(initializerMock1,
                        initializerMock2, initializerMock3));

        BaseClass obj = mockedLoader.loadInstance(BaseClass.class);

        verify(initializerMock1).initialize(obj);
        verify(initializerMock2).initialize(obj);
        verify(initializerMock3).initialize(obj);
    }
}