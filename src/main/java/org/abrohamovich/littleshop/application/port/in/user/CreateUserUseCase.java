package org.abrohamovich.littleshop.application.port.in.user;

import org.abrohamovich.littleshop.application.dto.user.UserCreateCommand;
import org.abrohamovich.littleshop.application.dto.user.UserResponse;

public interface CreateUserUseCase {
    UserResponse createUser(UserCreateCommand command);
}
