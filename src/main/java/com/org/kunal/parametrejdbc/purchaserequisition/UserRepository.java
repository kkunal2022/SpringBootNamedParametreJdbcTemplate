package com.org.kunal.parametrejdbc.purchaserequisition;
/*

import com.org.kunal.parametrejdbc.users.Users;

import java.util.Collection;
import java.util.List;

*/
/**
 * Kumar.Kunal
 *//*

public interface UserRepository<T extends Users> {
    */
/* Basic CRUD Operations *//*

    T create(T data);

    List<T> filterUsersByFirstNameOrLastName(String keyword);

    T get(Long id);

    void update(T t,Long id);

    Boolean delete(Long id);

    //MAKE FOR LIST
    List<T> findAll();

    Collection<T> list();

    Users getUserByEmail(String email);
    Users verifyCode(String email, String code);
    void resetPassword(String email);
    T verifyPasswordKey(String key);
    void renewPassword(String key, String password, String confirmPassword);
    T verifyAccountKey(String key);

    void updatePassword(Long id, String currentPassword, String newPassword, String confirmNewPassword);
    void updateAccountSettings(Long userId, Boolean enabled, Boolean notLocked);
    Users toggleMfa(String email);



}*/
