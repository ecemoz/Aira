package com.aira.backend.contracts;

import com.aira.backend.domain.model.Role;
import java.util.UUID;

public record UserDto(
        UUID id,
        String email,
        String name,
        Role role,
        String location,
        String phoneNumber
) {}

/**
 * A sweet little summary of who the user is
 * Just enough info to greet them and guide their experience
 */