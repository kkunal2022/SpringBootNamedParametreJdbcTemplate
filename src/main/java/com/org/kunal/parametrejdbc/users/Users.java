package com.org.kunal.parametrejdbc.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class Users {

    @NotBlank(message = "Email cannot be blank")
    @NotNull(message = "Email Cannot Be Null")
    @Email(message = "Email Should Be Valid")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 12, message = "Password must be between 8 and 12 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Password must be alphanumeric without whitespaces or tabs")
    private String userpwd;
}
