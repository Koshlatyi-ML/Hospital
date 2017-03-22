package dao;

import domain.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDao<T extends Person> extends CrudDao<T> {
    List<T> findByFullName(String name, String surname);
}
