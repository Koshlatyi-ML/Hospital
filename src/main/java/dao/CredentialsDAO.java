package dao;

import domain.dto.CredentialsDTO;

import java.util.Optional;

public interface CredentialsDAO extends CrudDAO<CredentialsDTO> {
    Optional<CredentialsDTO> findByLoginAndPassword(String login, String password);
}
