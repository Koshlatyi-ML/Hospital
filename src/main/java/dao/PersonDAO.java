package dao;

import domain.dto.AbstractPersonDTO;

import java.util.List;

public interface PersonDAO<T extends AbstractPersonDTO> extends CrudDAO<T> {
    List<T> findByFullName(String fullName);
}
