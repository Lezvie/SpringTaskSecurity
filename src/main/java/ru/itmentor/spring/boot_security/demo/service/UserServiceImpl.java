package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public void create(User user) {
        if (user.getUsername().equals("") | user.getPassword().equals("")) {
            throw new UsernameNotFoundException("User не имеет пароля и логина!");
        } else {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);

    }

    @Override
    public List<User> getUserAndRoles() {
        return userRepository.findAll();
    }


    @Override
    public User setRoleByUser (User user, String[] roles) {
        user.setRoles(roleService.getRoleByName(roles));
        return user;
    }


    @Override
    public List<User> getUserByUsername(String name) {
        List<User> list = new ArrayList<>();
        list.add(userRepository.findByUsername(name));
        return list;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByUsername(username);
        if (username == null) {
            throw new UsernameNotFoundException("User с именем " + username + " не был найден!");
        }
        return userDetails;
    }
}