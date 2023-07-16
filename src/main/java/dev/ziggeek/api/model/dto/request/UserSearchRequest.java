package dev.ziggeek.api.model.dto.request;

import dev.ziggeek.api.model.dto.email.Email;
import dev.ziggeek.api.model.dto.phone.Phone;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class UserSearchRequest {

//    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth = null;
    private Phone phone = null;
    private String name = null;
    private Email email = null;
    private int page = 0;
    private int pageSize = 10;
}