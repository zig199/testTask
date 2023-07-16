package dev.ziggeek.api.model.dto.email;

import lombok.*;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Access(AccessType.FIELD)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Email {

    private String email;
}
