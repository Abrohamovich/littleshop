package org.abrohamovich.littleshop.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.CustomerJpaEntity;
import org.abrohamovich.littleshop.adapter.persistence.jpa.repository.SpringDataCustomerRepository;
import org.abrohamovich.littleshop.adapter.persistence.mapper.CustomerJpaMapper;
import org.abrohamovich.littleshop.application.port.out.persistence.CustomerRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DataPersistenceException;
import org.abrohamovich.littleshop.domain.model.Customer;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {
    private final SpringDataCustomerRepository springDataCustomerRepository;
    private final CustomerJpaMapper customerJpaMapper;

    @Override
    public Customer save(Customer customer) {
        try {
            CustomerJpaEntity customerJpaEntity = customerJpaMapper.toJpaEntity(customer);
            CustomerJpaEntity savedEntity = springDataCustomerRepository.save(customerJpaEntity);
            return customerJpaMapper.toDomainEntity(savedEntity);
        } catch (DataAccessException e) {
            throw new DataPersistenceException("Failed to save customer due to data integrity violation. " + e.getMessage(), e);
        } catch (Exception e) {
            throw new DataPersistenceException("Failed to save customer. " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return springDataCustomerRepository.findById(id)
                .map(customerJpaMapper::toDomainEntity);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return springDataCustomerRepository.findByEmail(email)
                .map(customerJpaMapper::toDomainEntity);
    }

    @Override
    public Optional<Customer> findByPhone(String phone) {
        return springDataCustomerRepository.findByPhone(phone)
                .map(customerJpaMapper::toDomainEntity);
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return springDataCustomerRepository.findAll(pageable)
                .map(customerJpaMapper::toDomainEntity);
    }

    @Override
    public Page<Customer> findByFirstNameLike(String firstName, Pageable pageable) {
        return springDataCustomerRepository.findByFirstNameContainingIgnoreCase(firstName, pageable)
                .map(customerJpaMapper::toDomainEntity);
    }

    @Override
    public Page<Customer> findByLastNameLike(String lastName, Pageable pageable) {
        return springDataCustomerRepository.findByLastNameContainingIgnoreCase(lastName, pageable)
                .map(customerJpaMapper::toDomainEntity);
    }

    @Override
    public Page<Customer> findByEmailLike(String email, Pageable pageable) {
        return springDataCustomerRepository.findByEmailContainingIgnoreCase(email, pageable)
                .map(customerJpaMapper::toDomainEntity);
    }

    @Override
    public void deleteById(Long id) {
        try {
            springDataCustomerRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataPersistenceException("Failed to delete customer with ID '" + id + "'. " + e.getMessage(), e);
        }
    }
}
