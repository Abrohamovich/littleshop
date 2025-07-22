package org.abrohamovich.littleshop.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.CategoryJpaEntity;
import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.UserJpaEntity;
import org.abrohamovich.littleshop.adapter.persistence.jpa.repository.SpringDataUserRepository;
import org.abrohamovich.littleshop.adapter.persistence.mapper.UserJpaMapper;
import org.abrohamovich.littleshop.application.port.out.persistence.UserRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DataPersistenceException;
import org.abrohamovich.littleshop.domain.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final SpringDataUserRepository springDataUserRepository;
    private final UserJpaMapper userJpaMapper;

    @Override
    public User save(User user) {
        try {
            UserJpaEntity entity = userJpaMapper.toJpaEntity(user);
            UserJpaEntity savedEntity = springDataUserRepository.save(entity);
            return userJpaMapper.toDomainEntity(savedEntity);
        } catch (DataAccessException e) {
            throw new DataPersistenceException("Failed to save user due to data integrity violation. " + e.getMessage(), e);
        } catch (Exception e) {
            throw new DataPersistenceException("Failed to save user. " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        return springDataUserRepository.findById(id)
                .map(userJpaMapper::toDomainEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springDataUserRepository.findByEmail(email)
                .map(userJpaMapper::toDomainEntity);
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        return springDataUserRepository.findByPhone(phone)
                .map(userJpaMapper::toDomainEntity);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return springDataUserRepository.findAll(pageable)
                .map(userJpaMapper::toDomainEntity);
    }

    @Override
    public Page<User> findByFirstNameLike(String firstName, Pageable pageable) {
        return springDataUserRepository.findByFirstNameContainingIgnoreCase(firstName, pageable)
                .map(userJpaMapper::toDomainEntity);
    }

    @Override
    public Page<User> findByLastNameLike(String lastName, Pageable pageable) {
        return springDataUserRepository.findByLastNameContainingIgnoreCase(lastName, pageable)
                .map(userJpaMapper::toDomainEntity);
    }

    @Override
    public Page<User> findByEmailLike(String email, Pageable pageable) {
        return springDataUserRepository.findByEmailContainingIgnoreCase(email, pageable)
                .map(userJpaMapper::toDomainEntity);
    }

    @Override
    public void deleteById(Long id) {
        try {
            springDataUserRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataPersistenceException("Failed to delete user with ID '" + id + "'. " + e.getMessage(), e);
        }
    }
}
