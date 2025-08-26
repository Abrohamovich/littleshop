package org.abrohamovich.littleshop.application.port.in.auth;

import org.abrohamovich.littleshop.application.dto.auth.AuthenticationResponse;
import org.abrohamovich.littleshop.application.dto.auth.LoginCommand;

public interface AuthenticateUseCase {
    AuthenticationResponse authenticate(LoginCommand command);
}
