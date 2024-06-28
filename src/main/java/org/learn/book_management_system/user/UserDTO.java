package org.learn.book_management_system.user;

import java.time.LocalDateTime;

public record UserDTO(
        int id,
        String fullName,
        String email,
        String password,
        String displayName,
        String thoughts,
        String role,
        LocalDateTime startDate
) {
}
