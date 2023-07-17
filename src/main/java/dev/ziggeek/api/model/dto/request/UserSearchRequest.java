package dev.ziggeek.api.model.dto.request;

import dev.ziggeek.api.model.dto.email.Email;
import dev.ziggeek.api.model.dto.phone.Phone;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class UserSearchRequest {

    private LocalDate dateOfBirth;
    private Phone phone;
    private String name;
    private Email email;
}