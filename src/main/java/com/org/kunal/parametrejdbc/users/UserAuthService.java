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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userDao.getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("Users '" + username + "' not found.");
        }
        List<Role> roles = userDao.getRoles(username);
        List<GrantedAuthority> grantedAuthorities = roles.stream().map(r -> {
            return new SimpleGrantedAuthority(r.getRole());
        }).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getUserpwd(),
                grantedAuthorities);
    }

    public UsersVo getUserByUsername(String username) {
        Users user = userDao.getUser(username);

        if (user != null) {
            List<Role> roles = userDao.getRoles(username);
            Set<String> rls = roles.stream().map(a -> a.getRole()).collect(Collectors.toSet());
            UsersVo userVo = new UsersVo();
            userVo.setUsername(user.getUsername());
            userVo.setUserpwd(user.getUserpwd());
            userVo.setRoles(rls);
            return userVo;
        }

        return null;
    }

    public void saveUser(UsersVo userVo) {
        UserRole user = new UserRole();
        user.setUsername(userVo.getUsername());
        user.setUserpwd(passwordEncoder.encode(userVo.getUserpwd()));
        user.setRoles(userVo.getRoles());
        userDao.saveUser(user);
    }
}
