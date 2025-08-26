package org.abrohamovich.littleshop.application.port.in.auth;

import org.abrohamovich.littleshop.domain.model.User;

public interface ValidateTokenUseCase {
    User validateTokenAndGetUser(String token);
}
