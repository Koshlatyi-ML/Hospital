package dao;

import domain.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDao<T extends Person> extends CrudDao<T> {
    Optional<List<T>> findBySurname(String surname); //?
    Optional<List<T>> findByFullName(String name, String surname); //?
}
