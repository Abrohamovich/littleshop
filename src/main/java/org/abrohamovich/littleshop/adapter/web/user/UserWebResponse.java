package org.abrohamovich.littleshop.adapter.web.user;

import lombok.*;
import org.abrohamovich.littleshop.domain.model.UserRole;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWebResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
