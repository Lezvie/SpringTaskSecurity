package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;
public interface UserService {
    void create (User user);
    User getUserById (Long id);
    void delete (Long id);
    List<User> getUserAndRoles();

    User setRoleByUser (User user, String[] roles);

    List<User> getUserByUsername (String name);



}
