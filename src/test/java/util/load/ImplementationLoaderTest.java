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

