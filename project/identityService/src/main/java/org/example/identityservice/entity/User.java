package org.example.identityservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.identityservice.enums.UserStatus;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends EntityWithTimestamps {
    @Id
    String uid;
    @NotNull
    String email;
    @NotNull
    String password;
    @NotNull
    UserStatus status_code;
    @ManyToOne
    Role role;
}
