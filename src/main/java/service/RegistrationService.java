package service;

import domain.AbstractPersonDTO;
import service.dto.AbstractRegistrationDTO;

public interface RegistrationService<T extends AbstractRegistrationDTO> {
    AbstractPersonDTO register(T registrationDTO);
}
