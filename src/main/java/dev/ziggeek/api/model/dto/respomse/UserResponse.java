package dev.ziggeek.api.model.dto.respomse;

import java.time.LocalDate;
import java.util.Set;

import dev.ziggeek.api.model.dto.email.Email;
import dev.ziggeek.api.model.dto.phone.Phone;
import lombok.Value;


@Value
public class UserResponse {

    Long id;
    String name;
    LocalDate dateOfBirth;
    Set<Email> emails;
    Set<Phone> phones;
}
