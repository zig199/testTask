package dev.ziggeek.api.model.dto.request;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AccountTransferRequest {

    private Long fromUser;
    private Long toUser;
    private BigDecimal amount;
}
