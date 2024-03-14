package com.org.kunal.parametrejdbc.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author kunal
 * @project SpringBootNamedParametreJdbcTemplate
 */

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userDao.getUser(email);
        if (user == null) {
            throw new UsernameNotFoundException("Users '" + email + "' not found.");
        }
        List<Role> roles = userDao.getRoles(email);
        List<GrantedAuthority> grantedAuthorities = roles.stream().map(k -> {
            return new SimpleGrantedAuthority(k.getRole());
        }).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getUserpwd(),
                grantedAuthorities);
    }

    public UsersVo getUserByUsername(String email) {
        Users user = userDao.getUser(email);

        if (user != null) {
            List<Role> roles = userDao.getRoles(email);
            Set<String> setRoles = roles.stream().map(a -> a.getRole()).collect(Collectors.toSet());
            UsersVo userVo = new UsersVo();
            userVo.setEmail(user.getEmail());
            userVo.setUserpwd(user.getUserpwd());
            userVo.setRoles(setRoles);
            return userVo;
        }

        return null;
    }

    public void saveUser(UsersVo userVo) {
        UserRole saveUserWithRole = new UserRole();
        saveUserWithRole.setEmail(userVo.getEmail());
        saveUserWithRole.setUserpwd(passwordEncoder.encode(userVo.getUserpwd()));
        saveUserWithRole.setRoles(userVo.getRoles());
        userDao.saveUser(saveUserWithRole);
    }
}
