package org.abrohamovich.littleshop.application.usecase.auth;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.port.in.auth.ValidateTokenUseCase;
import org.abrohamovich.littleshop.application.port.out.auth.TokenServicePort;
import org.abrohamovich.littleshop.application.port.out.persistence.UserRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.auth.AuthenticationException;
import org.abrohamovich.littleshop.domain.model.User;

@RequiredArgsConstructor
public class ValidateTokenService implements ValidateTokenUseCase {
    private final TokenServicePort tokenService;
    private final UserRepositoryPort userRepository;

    @Override
    public User validateTokenAndGetUser(String token) {
        if (!tokenService.isTokenValid(token)) {
            throw new AuthenticationException("Invalid or expired token");
        }

        Long userId = tokenService.extractUserIdFromToken(token);
        return userRepository.findById(userId)
                .orElseThrow(() -> new AuthenticationException("User not found"));
    }
}
