package com.org.kunal.parametrejdbc.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * @author kunal
 * @project SpringBootNamedParametreJdbcTemplate
 */
@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtRequest {

	@Email(message = "Email is not valid")
    @NotEmpty(message = "Email cannot be empty")
    @NotBlank(message = "Email cannot be blank")
    @NotNull(message = "Email Cannot Be Null")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 4, max = 12, message = "Password must be between 4 and 12 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,12}$", message = "Password must be alphanumeric with length from 4 to 12, include a capital letter, use at least one lowercase letter, consist of at least one digit, need to have one special symbol, and shouldn’t contain space, tab, etc.")
    private String userpwd;
}
