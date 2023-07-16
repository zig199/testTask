package dev.ziggeek.api.model.dto.phone;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PhoneRequest {

    @NotNull
    private Long userId;

    @NotEmpty
    @Email
    private Phone phone;
}
