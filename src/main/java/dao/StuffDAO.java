package dao;

import domain.dto.AbstractStuffDTO;

import java.util.List;

public interface StuffDAO<T extends AbstractStuffDTO> extends PersonDAO<T> {
    List<T> findByDepartmentId(long id);
}
