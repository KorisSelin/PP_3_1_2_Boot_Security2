package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

//import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {


    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Transactional
    @Override
    public List<User> getAllUsers() {return userRepository.findAll();}

    @Transactional
    @Override
    public void saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUserById(long id) {
        if (userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
        } else throw new UsernameNotFoundException(String.format("User with id '%s' not founded!", id ));
    }


    @Transactional
    @Override
    public User getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return user.get();
        } else {
            throw new UsernameNotFoundException(String.format("User with id '%s' not found", id ));
        }
    }
    @Transactional
    @Override
    public User getUserByUsername(String username) {return userRepository.findUserByUsername(username);}

    @Override
    @Transactional
    public void updateUser(Long id, User updatedUser) {
            User userToUpdate = userRepository.getById(id);
            userToUpdate.setUsername(updatedUser.getUsername());
            userToUpdate.setPassword(updatedUser.getPassword());
            userToUpdate.setConfirmPassword(updatedUser.getConfirmPassword());
            userToUpdate.setFirstName(updatedUser.getFirstName());
            userToUpdate.setLastName(updatedUser.getLastName());
            userToUpdate.setAge(updatedUser.getAge());
            userToUpdate.setEmail(updatedUser.getEmail());
            userToUpdate.setRoles(updatedUser.getRoles());
            userRepository.save(userToUpdate);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByUsername(username));
        if (user.isEmpty()){
            throw new UsernameNotFoundException(String.format("User '%s' not found!", username ));
        }
        return new ru.kata.spring.boot_security.demo.security.UserDetails(user.get());
    }
}
