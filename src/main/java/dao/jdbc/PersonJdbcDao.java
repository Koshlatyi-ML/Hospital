package dao.jdbc;

import dao.PersonDao;
import domain.model.Person;

import java.util.List;
import java.util.Optional;

public abstract class PersonJdbcDao<T extends Person>
        extends CrudJdbcDao<T> implements PersonDao<T> {

    @Override
    public Optional<List<T>> findBySurname(String surname) {
        return null;
    }

    @Override
    public Optional<List<T>> findByFullName(String name, String surname) {
        return null;
    }
}
