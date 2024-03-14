package com.org.kunal.parametrejdbc.users;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Set;

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
public class UserRole {

    private String email;
    private String userpwd;
    private Set<String> roles;
}
