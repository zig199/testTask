package dev.ziggeek.api.model.dto.phone;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PhoneUpdateRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Phone currentPhoneNumber;

    @NotNull
    private Phone newPhoneNumber;
}
