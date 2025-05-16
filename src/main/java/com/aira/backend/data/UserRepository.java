package com.aira.backend.data;

import com.aira.backend.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    /**
     * Finds a user by their email address
     * Since email is our main way of identifying users, this method is the star of the login flow.
     * Also used when issuing tokens or checking who’s trying to log in.
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if an email is already taken before registering a new user
     * Think of it as the little guardian that prevents duplicate accounts.
     * Useful for showing helpful messages like “this email is already in use”
     */
    boolean existsByEmail(String email);
}