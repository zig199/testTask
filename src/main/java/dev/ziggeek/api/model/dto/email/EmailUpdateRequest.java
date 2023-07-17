package dev.ziggeek.api.model.dto.email;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmailUpdateRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Email currentEmail;

    @NotNull
    private Email newEmail;
}
