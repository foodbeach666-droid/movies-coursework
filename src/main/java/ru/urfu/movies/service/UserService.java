package ru.urfu.movies.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.urfu.movies.entity.Role;
import ru.urfu.movies.entity.User;
import ru.urfu.movies.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует!");
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(Collections.singleton(Role.ROLE_READ_ONLY))
                .build();

        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
    }

    public void updateUserRoles(Long userId, Set<Role> roles) {
        User user = getUserById(userId);
        user.setRoles(roles);
        userRepository.save(user);
    }
}
