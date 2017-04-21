package service;

import dao.CrudDAO;
import dao.DaoManager;
import domain.DepartmentDTO;

import java.util.Optional;

public class DepartmentService extends AbstractCrudService<DepartmentDTO> {

    private DaoManager daoManager;

    DepartmentService(DaoManager daoManager) {
        this.daoManager = daoManager;
    }

    public Optional<DepartmentDTO> getByName(String name) {
        return daoManager.getDepartmentDao().findByName(name);
    }

    @Override
    CrudDAO<DepartmentDTO> getDAO() {
        return daoManager.getDepartmentDao();
    }
}
