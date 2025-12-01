package com.learn.auth_app_backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.learn.auth_app_backend.entities.Provider;
import lombok.*;
import jakarta.validation.constraints.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private UUID id;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 300)
    private String email;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 300)
    private String name;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
            message = "Password is weak"
    )
    private String password;

    @Size(max = 500)
    private String image;

    private boolean enable = true;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
    private Instant createdAt = Instant.now();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
    private Instant updatedAt = Instant.now();

//    @NotNull(message = "Provider is required")
    private Provider provider = Provider.LOCAL;

    private Set<RoleDto> roles = new HashSet<>();
}
