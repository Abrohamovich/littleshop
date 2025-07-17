package org.abrohamovich.littleshop.application.port.in.user;

import org.abrohamovich.littleshop.application.dto.user.UserResponse;
import org.abrohamovich.littleshop.application.dto.user.UserUpdateCommand;

public interface UpdateUserUseCase {
    UserResponse updateUser(UserUpdateCommand command);
}
