package dev.ziggeek.api.model.dto.email;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmailUpdateRequest {

    @NotNull
    private Long userId;

    @NotEmpty
    @Email
    private String currentEmail;

    @NotEmpty
    @Email
    private String newEmail;
}
