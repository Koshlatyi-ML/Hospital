package service;

import dao.CrudDAO;
import dao.DaoManager;
import domain.CredentialsDTO;

public class CredentialsService extends AbstractCrudService<CredentialsDTO> {

    private DaoManager daoManager;

    CredentialsService(DaoManager daoManager) {
        this.daoManager = daoManager;
    }

    public boolean hasLogin(String login) {
        return daoManager.getCredentialsDao().hasLogin(login);
    }

    @Override
    CrudDAO<CredentialsDTO> getDAO() {
        return daoManager.getCredentialsDao();
    }
}
