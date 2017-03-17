package dao;

import domain.Person;

import java.util.Optional;

public interface PersonDao<T extends Person> extends Dao<T> {
    Optional<T> findBySurname(String surname); //?
    Optional<T> findByFullName(String name, String surname); //?
}
