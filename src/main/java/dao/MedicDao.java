package dao;

import domain.model.Medic;

public interface MedicDao extends CrudDao<Medic>,
        DepartmentMemberDao<Medic> {}
