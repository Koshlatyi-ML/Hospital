package dao.jdbc;

import dao.PersonDao;
import dao.metadata.IdHolderTableInfo;
import dao.metadata.PersonTableInfo;
import domain.Person;

import java.util.List;
import java.util.Optional;

public abstract class PersonJdbcDao<P extends Person, T extends IdHolderTableInfo>
        extends CrudJdbcDao<P, T> implements PersonDao<P> {

    @Override
    public Optional<List<P>> findBySurname(String surname) {
        return null;
    }

    @Override
    public Optional<List<P>> findByFullName(String name, String surname) {
        return null;
    }
}
