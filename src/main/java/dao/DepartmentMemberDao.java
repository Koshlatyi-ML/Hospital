package dao;

import java.util.List;

public interface DepartmentMemberDao<T> {
    List<T> findByDepartmentId(long id);
}
