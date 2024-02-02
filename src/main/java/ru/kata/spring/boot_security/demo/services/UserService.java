package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    public List<User> getAllUsers();
    public void saveUser(User user);
    public void deleteUserById(long id);
    public User getUserById(long id);
    public void updateUser(Long id, User updateUser);
    User getUserByUsername(String username);

}
