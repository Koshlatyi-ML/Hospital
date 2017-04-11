package util.init;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JndiInitializerFactory {

    private final JndiInitializer jndiInitializer;
    private static final Logger LOG = LogManager.getLogger(JndiInitializerFactory.class);
    private static final JndiInitializerFactory INSTANCE = new JndiInitializerFactory();

    private JndiInitializerFactory() {
        try {
            jndiInitializer = new JndiInitializer(new InitialContext());
        } catch (NamingException e) {
            LOG.log(Level.ERROR, "Can't create JndiInitializer object", e);
            throw new InitializerException(e);
        }
    }

    public static JndiInitializerFactory getInstance() {
        return INSTANCE;
    }

    public JndiInitializer getJndiInitializer() {
        return jndiInitializer;
    }
}
