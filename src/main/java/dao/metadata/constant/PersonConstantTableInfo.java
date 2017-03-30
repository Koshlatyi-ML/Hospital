package dao.metadata.constant;

import dao.metadata.PersonTableInfo;

public abstract class PersonConstantTableInfo extends IdHolderConstantTableInfo
        implements PersonTableInfo {

    @Override
    public abstract String getNameColumn();

    @Override
    public abstract String getSurnameColumn();
}
