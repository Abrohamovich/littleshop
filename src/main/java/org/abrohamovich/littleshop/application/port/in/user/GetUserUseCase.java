package org.abrohamovich.littleshop.application.port.in.user;

import org.abrohamovich.littleshop.application.dto.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetUserUseCase {
    UserResponse findById(Long id);

    Page<UserResponse> findAll(Pageable pageable);

    Page<UserResponse> findByFirstNameLike(String firstName, Pageable pageable);

    Page<UserResponse> findByLastNameLike(String lastName, Pageable pageable);

    Page<UserResponse> findByEmailLike(String email, Pageable pageable);
}
