package dao;

import domain.dto.AbstractDTO;

import java.util.Optional;

public interface ApplicationUserDAO<T extends AbstractDTO> {
    Optional<T> findByCredentialsId(long id);
}
