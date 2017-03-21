package dao;

import domain.Medic;

public interface MedicDao extends PersonDao<Medic>,
        DepartmentMemberDao<Medic> {}
