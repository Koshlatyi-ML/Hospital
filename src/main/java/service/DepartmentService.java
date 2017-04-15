package service;

import dao.CrudDAO;
import dao.DaoManager;
import domain.DepartmentDTO;

public class DepartmentService extends AbstractCrudService<DepartmentDTO> {

    private DaoManager daoManager;

    DepartmentService(DaoManager daoManager) {
        this.daoManager = daoManager;
    }

    @Override
    CrudDAO<DepartmentDTO> getDAO() {
        return null;
    }
}
