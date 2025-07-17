package org.abrohamovich.littleshop.application.port.out.persistence;

import org.abrohamovich.littleshop.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    Page<User> findAll(Pageable pageable);

    Page<User> findByFirstNameLike(String firstName, Pageable pageable);

    Page<User> findByLastNameLike(String lastName, Pageable pageable);

    Page<User> findByEmailLike(String email, Pageable pageable);

    void deleteById(Long id);
}
