package dao;

import domain.Medic;

import java.util.List;

public interface DepartmentMemberDao<T> {
    List<T> findByDepartmentId(long id);
}
