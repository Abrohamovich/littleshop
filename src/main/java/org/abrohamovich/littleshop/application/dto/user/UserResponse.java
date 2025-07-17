package org.abrohamovich.littleshop.application.dto.user;

import lombok.*;
import org.abrohamovich.littleshop.domain.model.User;
import org.abrohamovich.littleshop.domain.model.UserRole;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserResponse toResponse(User user) {
        if (user == null) {
            throw new  IllegalArgumentException("User cannot be null to continue the conversion");
        }
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .phone(user.getPhone())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
