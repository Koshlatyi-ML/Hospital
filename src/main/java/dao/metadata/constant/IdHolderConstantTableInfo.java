package dao.metadata.constant;

import dao.metadata.PlainTableInfo;

import java.util.List;

public abstract class IdHolderConstantTableInfo implements PlainTableInfo {
    @Override
    public abstract String getTableName();

    @Override
    public abstract String getIdColumn();

    @Override
    public abstract List<String> getColumns();
}
