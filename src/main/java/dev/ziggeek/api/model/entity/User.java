package dev.ziggeek.api.model.entity;

import dev.ziggeek.api.model.dto.email.Email;
import dev.ziggeek.api.model.dto.phone.Phone;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Builder
@Table(name = "t_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    @ToString.Include
    @EqualsAndHashCode.Include
    private String name;

    @ToString.Include
    @EqualsAndHashCode.Include
    private LocalDate dateOfBirth;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "t_email_data", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Email> emails;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "t_phone_data", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Phone> phones;

    private String password;



    public void addEmail(@NonNull Email emailAddress) { this.emails.add(emailAddress); }
    public void addPhone(@NonNull Phone phoneNumber) { this.phones.add(phoneNumber); }

    public boolean removeEmail(@NonNull Email email) { return this.emails.remove(email);}
    public boolean removePhone(@NonNull Phone phone) { return this.phones.remove(phone);}

    public void updateEmail(@NonNull Email currentEmail, @NonNull Email newEmail) {
        if (removeEmail(currentEmail)) addEmail(newEmail);
    }
    public void updatePhone(@NonNull Phone currentPhone, @NonNull Phone newPhone) {
        if (removePhone(currentPhone)) addPhone(newPhone);
    }

}
