package org.abrohamovich.littleshop.application.dto.auth;

import lombok.Builder;
import lombok.Setter;
import org.abrohamovich.littleshop.domain.model.UserRole;

@Setter
@Builder
public record AuthenticationResponse(String token, String tokenType, Long expiresIn, String userEmail,
                                     UserRole userRole) {
}
