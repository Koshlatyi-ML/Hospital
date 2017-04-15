package service;

import domain.AbstractPersonDTO;

import java.util.Optional;

public interface LoginService<T extends AbstractPersonDTO> {
    Optional<T> login(String login, String password);
}
