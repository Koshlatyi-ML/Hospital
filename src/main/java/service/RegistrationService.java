package service;

import service.dto.AbstractRegistrationDTO;

public interface RegistrationService<T extends AbstractRegistrationDTO> {
    void register(T registrationDTO);
}
