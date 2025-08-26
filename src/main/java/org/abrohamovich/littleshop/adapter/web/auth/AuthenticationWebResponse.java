package org.abrohamovich.littleshop.adapter.web.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.abrohamovich.littleshop.domain.model.UserRole;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class AuthenticationWebResponse {
    private final String token;
    private final String tokenType;
    private final Long expiresIn;
    private final String userEmail;
    private final UserRole userRole;
}
