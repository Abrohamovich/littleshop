package org.abrohamovich.littleshop.application.usecase.user;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.user.UserResponse;
import org.abrohamovich.littleshop.application.port.in.user.GetUserUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.UserRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.user.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class GetUserService implements GetUserUseCase {
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public UserResponse findById(Long id) {
        return userRepositoryPort.findById(id)
                .map(UserResponse::toResponse)
                .orElseThrow(() -> new UserNotFoundException("User with ID '" + id + "' not found."));
    }

    @Override
    public Page<UserResponse> findAll(Pageable pageable) {
        return userRepositoryPort.findAll(pageable)
                .map(UserResponse::toResponse);
    }

    @Override
    public Page<UserResponse> findByFirstNameLike(String firstName, Pageable pageable) {
        return userRepositoryPort.findByFirstNameLike(firstName, pageable)
                .map(UserResponse::toResponse);
    }

    @Override
    public Page<UserResponse> findByLastNameLike(String lastName, Pageable pageable) {
        return userRepositoryPort.findByLastNameLike(lastName, pageable)
                .map(UserResponse::toResponse);
    }

    @Override
    public Page<UserResponse> findByEmailLike(String email, Pageable pageable) {
        return userRepositoryPort.findByEmailLike(email, pageable)
                .map(UserResponse::toResponse);
    }
}
