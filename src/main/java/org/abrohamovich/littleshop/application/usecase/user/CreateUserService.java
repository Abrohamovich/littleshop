package org.abrohamovich.littleshop.application.usecase.user;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.user.UserCreateCommand;
import org.abrohamovich.littleshop.application.dto.user.UserResponse;
import org.abrohamovich.littleshop.application.port.in.user.CreateUserUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.UserRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.model.User;

@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public UserResponse save(UserCreateCommand command) {
        if (userRepositoryPort.findByEmail(command.getEmail()).isPresent()) {
            throw new DuplicateEntryException("User with email " + command.getEmail() + " already exists");
        }
        if (userRepositoryPort.findByPhone(command.getPhone()).isPresent()) {
            throw new DuplicateEntryException("User with phone " + command.getPhone() + " already exists");
        }

        User user = User.createNewUser(command.getFirstName(), command.getLastName(), command.getEmail(),
                command.getPassword(), command.getRole(), command.getPhone());

        User savedUser = userRepositoryPort.save(user);

        return UserResponse.toResponse(savedUser);
    }
}
