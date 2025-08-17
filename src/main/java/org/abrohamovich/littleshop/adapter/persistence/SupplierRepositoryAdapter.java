package org.abrohamovich.littleshop.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.SupplierJpaEntity;
import org.abrohamovich.littleshop.adapter.persistence.jpa.repository.SpringDataSupplierRepository;
import org.abrohamovich.littleshop.adapter.persistence.mapper.SupplierJpaMapper;
import org.abrohamovich.littleshop.application.port.out.persistence.SupplierRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DataPersistenceException;
import org.abrohamovich.littleshop.domain.model.Supplier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SupplierRepositoryAdapter implements SupplierRepositoryPort {
    private final SpringDataSupplierRepository springDataSupplierRepository;
    private final SupplierJpaMapper supplierJpaMapper;

    @Override
    @Transactional
    public Supplier save(Supplier supplier) {
        try {
            SupplierJpaEntity entity;
            if (supplier.getId() == null) {
                entity = supplierJpaMapper.toJpaEntity(supplier);
            } else {
                entity = springDataSupplierRepository.findById(supplier.getId())
                        .map(jpaEntity -> {
                            supplierJpaMapper.updateJpaEntityFromDomain(supplier, jpaEntity);
                            return jpaEntity;
                        })
                        .orElseThrow(() -> new DataPersistenceException("Supplier with ID '" + supplier.getId() + "' not found for update."));
            }
            SupplierJpaEntity savedEntity = springDataSupplierRepository.save(entity);
            return supplierJpaMapper.toDomainEntity(savedEntity);
        } catch (DataAccessException e) {
            throw new DataPersistenceException("Failed to save supplier due to data integrity violation. " + e.getMessage(), e);
        } catch (Exception e) {
            throw new DataPersistenceException("Failed to save supplier. " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Supplier> findById(Long id) {
        return springDataSupplierRepository.findById(id)
                .map(supplierJpaMapper::toDomainEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Supplier> findByName(String name) {
        return springDataSupplierRepository.findByName(name)
                .map(supplierJpaMapper::toDomainEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Supplier> findByEmail(String email) {
        return springDataSupplierRepository.findByEmail(email)
                .map(supplierJpaMapper::toDomainEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Supplier> findByPhone(String phone) {
        return springDataSupplierRepository.findByPhone(phone)
                .map(supplierJpaMapper::toDomainEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Supplier> findAll(Pageable pageable) {
        return springDataSupplierRepository.findAll(pageable)
                .map(supplierJpaMapper::toDomainEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Supplier> findByNameLike(String name, Pageable pageable) {
        return springDataSupplierRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(supplierJpaMapper::toDomainEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Supplier> findByEmailLike(String email, Pageable pageable) {
        return springDataSupplierRepository.findByEmailContainingIgnoreCase(email, pageable)
                .map(supplierJpaMapper::toDomainEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Supplier> findByPhoneLike(String phone, Pageable pageable) {
        return springDataSupplierRepository.findByPhoneContainingIgnoreCase(phone, pageable)
                .map(supplierJpaMapper::toDomainEntity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            springDataSupplierRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new DataPersistenceException("Failed to delete supplier with ID '" + id + "' due to data access error. " + e.getMessage(), e);
        } catch (Exception e) {
            throw new DataPersistenceException("Failed to delete supplier with ID '" + id + "'. " + e.getMessage(), e);
        }
    }
}
