package dev.ziggeek.api.model.dto.email;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmailRequest {

    @NotNull
    private Long userId;

    @NotBlank
    @Email
    private String email;
}
