package dev.ziggeek.api.model.entity;


import java.math.BigDecimal;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString()
@Builder
@Entity
@Table(name = "t_accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    private Long userId;

    private BigDecimal balance;
    private BigDecimal initialBalance;


    public void addBalance(@NonNull BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }


    public void subtractFromBalance(@NonNull BigDecimal amount) {
        var result = this.balance.subtract(amount);
        if (result.signum() < 0) {
            throw new RuntimeException("*** Недостаточно средств для проведения транзакции.");
        }

        this.balance = result;
    }
}
