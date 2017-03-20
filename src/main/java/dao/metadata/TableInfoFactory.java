package dao.metadata;

import dao.metadata.annotation.AnnotTableInfoFactory;
import util.load.Implementation;
import util.load.Instantiator;

@Implementation(AnnotTableInfoFactory.class)
public abstract class TableInfoFactory {
    private static final TableInfoFactory INSTANCE
            = Instantiator.getInstance().loadInstance(TableInfoFactory.class);

    public static TableInfoFactory getInstance() {
        return INSTANCE;
    }
}
