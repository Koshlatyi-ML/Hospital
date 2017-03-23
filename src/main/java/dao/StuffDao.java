package dao;

import domain.Person;

import java.util.List;

public interface StuffDao<T extends Person> extends PersonDao<T> {
    List<T> findByDepartmentId(long id);
}
