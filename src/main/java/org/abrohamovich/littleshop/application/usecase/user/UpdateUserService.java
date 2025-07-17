package org.abrohamovich.littleshop.application.usecase.user;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.user.UserResponse;
import org.abrohamovich.littleshop.application.dto.user.UserUpdateCommand;
import org.abrohamovich.littleshop.application.port.in.user.UpdateUserUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.UserRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.exception.user.UserNotFoundException;
import org.abrohamovich.littleshop.domain.model.User;

@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public UserResponse update(Long id, UserUpdateCommand command) {
        User existingUser = userRepositoryPort.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found for update."));

        if (!existingUser.getEmail().equals(command.getEmail())) {
            if (userRepositoryPort.findByEmail(command.getEmail()).isPresent()) {
                throw new DuplicateEntryException("User with email '" + command.getEmail() + "' already exists.");
            }
        }
        if (!existingUser.getPhone().equals(command.getPhone())) {
            if (userRepositoryPort.findByPhone(command.getPhone()).isPresent()) {
                throw new DuplicateEntryException("User with phone '" + command.getEmail() + "' already exists.");
            }
        }

        existingUser.updateDetails(command.getFirstName(), command.getLastName(),
                command.getEmail(), command.getPassword(), command.getRole(), command.getPhone());

        User updatedUser = userRepositoryPort.save(existingUser);

        return UserResponse.toResponse(updatedUser);
    }
}
