package org.abrohamovich.littleshop.application.port.out.persistence;

import org.abrohamovich.littleshop.domain.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerRepositoryPort {
    Customer save(Customer customer);
    Optional<Customer> findById(Long id);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhone(String phone);
    Page<Customer> findAll(Pageable pageable);
    Page<Customer> findByFirstNameLike(String firstName, Pageable pageable);
    Page<Customer> findByLastNameLike(String lastName, Pageable pageable);
    Page<Customer> findByEmailLike(String email, Pageable pageable);
    void deleteById(Long id);
}
