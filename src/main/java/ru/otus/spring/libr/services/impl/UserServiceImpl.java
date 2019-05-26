package ru.otus.spring.libr.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.libr.entities.User;
import ru.otus.spring.libr.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.getUserByEmail(email);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User is not found");
        }
        User user = userOpt.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(email)
                .password(user.getPassword())
                .disabled(!user.isEnabled())
                .roles(user.getRole())
                .build();
    }
}
