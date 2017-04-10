package util.init;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JndiInitializerFactory {
    private final JndiInitializer jndiInitializer;

    private JndiInitializerFactory() {
        try {
            jndiInitializer = new JndiInitializer(new InitialContext());
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    private static class Holder {
        static final JndiInitializerFactory INSTANCE = new JndiInitializerFactory();
    }

    public static JndiInitializerFactory getInstance() {
        return Holder.INSTANCE;
    }

    public JndiInitializer createJndiInitializer() {
        return jndiInitializer;
    }
}
