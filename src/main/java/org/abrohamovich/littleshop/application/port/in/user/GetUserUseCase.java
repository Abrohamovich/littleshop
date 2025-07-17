package org.abrohamovich.littleshop.application.port.in.user;

import org.abrohamovich.littleshop.application.dto.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetUserUseCase {
    UserResponse getUserById(Long id);
    Page<UserResponse> getAllUsers(Pageable pageable);
    Page<UserResponse> getUsersByFirstNameLike(String firstName, Pageable pageable);
    Page<UserResponse> getUsersByLastNameLike(String lastName, Pageable pageable);
    Page<UserResponse> getUsersByEmailLike(String email, Pageable pageable);
}
