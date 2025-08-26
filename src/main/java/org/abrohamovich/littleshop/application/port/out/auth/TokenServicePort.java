package org.abrohamovich.littleshop.application.port.out.auth;

import org.abrohamovich.littleshop.domain.model.AuthenticationToken;
import org.abrohamovich.littleshop.domain.model.User;

public interface TokenServicePort {
    AuthenticationToken generateToken(User user);

    Long extractUserIdFromToken(String token);

    boolean isTokenValid(String token);
}
