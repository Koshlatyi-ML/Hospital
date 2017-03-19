package dao;

import domain.Medic;

public interface MedicDao extends CrudDao<Medic>,
        DepartmentMemberDao<Medic> {}
