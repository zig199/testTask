package dev.ziggeek.api.model.dto.phone;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PhoneUpdateRequest {

    @NotNull
    private Long userId;

    @NotEmpty
    private Phone currentPhoneNumber;

    @NotEmpty
    private Phone newPhoneNumber;
}
