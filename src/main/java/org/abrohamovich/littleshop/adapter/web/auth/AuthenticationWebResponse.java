package org.abrohamovich.littleshop.adapter.web.auth;

import lombok.Builder;
import lombok.Setter;
import org.abrohamovich.littleshop.domain.model.UserRole;

@Setter
@Builder
public record AuthenticationWebResponse(String token, String tokenType, Long expiresIn, String userEmail,
                                        UserRole userRole) {
}
