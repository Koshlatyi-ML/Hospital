package dao;

import java.util.List;

public interface StuffDao<T> {
    List<T> findByDepartmentId(long id);
}
