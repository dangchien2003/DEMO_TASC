package org.example.productservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AuthorUpdateRequest {
    @NotNull(message = "NOTFOUND_ID")
    Long id;
    @NotNull(message = "INVALID_NAME")
    String name;
    String website;
}