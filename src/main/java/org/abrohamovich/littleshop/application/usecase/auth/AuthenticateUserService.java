package org.abrohamovich.littleshop.application.usecase.auth;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.auth.AuthenticationResponse;
import org.abrohamovich.littleshop.application.dto.auth.LoginCommand;
import org.abrohamovich.littleshop.application.port.in.auth.AuthenticateUseCase;
import org.abrohamovich.littleshop.application.port.out.auth.TokenServicePort;
import org.abrohamovich.littleshop.application.port.out.persistence.UserRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.auth.AuthenticationException;
import org.abrohamovich.littleshop.domain.exception.user.UserNotFoundException;
import org.abrohamovich.littleshop.domain.model.AuthenticationToken;
import org.abrohamovich.littleshop.domain.model.User;

import java.time.Duration;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class AuthenticateUserService implements AuthenticateUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final TokenServicePort tokenServicePort;

    @Override
    public AuthenticationResponse authenticate(LoginCommand command) {
        User user = userRepositoryPort.findByEmail(command.getEmail())
                .orElseThrow(() -> new AuthenticationException("Invalid email"));

        if (!user.checkPassword(command.getPassword())) {
            throw new AuthenticationException("Invalid password");
        }

        AuthenticationToken token = tokenServicePort.generateToken(user);

        long expiresIn = Duration.between(LocalDateTime.now(), token.getExpiresAt()).toSeconds();

        return new AuthenticationResponse(
                token.getToken(),
                token.getTokenType(),
                expiresIn,
                user.getEmail(),
                user.getRole()
        );
    }
}
