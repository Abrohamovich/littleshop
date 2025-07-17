package org.abrohamovich.littleshop.application.dto.user;

import lombok.*;
import org.abrohamovich.littleshop.domain.model.UserRole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateCommand {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
    private String phone;
}
