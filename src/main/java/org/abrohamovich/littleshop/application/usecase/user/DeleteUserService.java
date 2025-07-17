package org.abrohamovich.littleshop.application.usecase.user;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.port.in.user.DeleteUserUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.UserRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.user.UserNotFoundException;

@RequiredArgsConstructor
public class DeleteUserService implements DeleteUserUseCase {
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void deleteById(Long id) {
        if (userRepositoryPort.findById(id).isEmpty()) {
            throw new UserNotFoundException("User with ID '" + id + "' not found for deletion.");
        }

        userRepositoryPort.deleteById(id);
    }
}
