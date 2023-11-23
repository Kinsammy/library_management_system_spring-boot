package com.sametech.library_management_system.data.dto.request;

import com.sametech.library_management_system.data.models.users.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.sametech.library_management_system.util.AppUtilities.EMAIL_REGEX_STRING;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull(message = "first name cannot be null")
    @NotEmpty(message = "first name cannot be empty")
    private String firstName;
    @NotNull(message = "last name cannot be null")
    @NotEmpty(message = "last name cannot be empty")
    private String lastName;
    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    @Email(message = "must be valid email address", regexp = EMAIL_REGEX_STRING)
    private String email;
    @Size(min = 8, max = 15 )
    @NotEmpty
    @NotNull
    private String password;
    private Role role;
}
