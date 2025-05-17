package com.aira.backend.infrastructure.security;

import com.aira.backend.data.UserRepository;
import com.aira.backend.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
/**
 * CustomUserDetailsService is our quiet verifier at the door
 * It reads the user ID from the token, looks them up gently,
 * and tells Spring Security: “Yes, I know this one — they’re good.”
 */
