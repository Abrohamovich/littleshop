package org.abrohamovich.littleshop.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.adapter.persistence.jpa.entity.OfferJpaEntity;
import org.abrohamovich.littleshop.adapter.persistence.jpa.repository.SpringDataOfferRepository;
import org.abrohamovich.littleshop.adapter.persistence.mapper.OfferJpaMapper;
import org.abrohamovich.littleshop.application.port.out.persistence.CategoryRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.OfferRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.SupplierRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DataPersistenceException;
import org.abrohamovich.littleshop.domain.exception.category.CategoryNotFoundException;
import org.abrohamovich.littleshop.domain.exception.supplier.SupplierNotFoundException;
import org.abrohamovich.littleshop.domain.model.Category;
import org.abrohamovich.littleshop.domain.model.Offer;
import org.abrohamovich.littleshop.domain.model.Supplier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OfferRepositoryAdapter implements OfferRepositoryPort {
    private final SpringDataOfferRepository springDataOfferRepository;
    private final CategoryRepositoryPort categoryRepositoryPort;
    private final SupplierRepositoryPort supplierRepositoryPort;
    private final OfferJpaMapper offerJpaMapper;

    @Override
    @Transactional
    public Offer save(Offer offer) {
        try {
            OfferJpaEntity entityToPersist;

            if (offer.getId() == null) {
                entityToPersist = offerJpaMapper.toJpaEntity(offer);
            } else {
                entityToPersist = springDataOfferRepository.findByIdWithDetails(offer.getId())
                        .orElseThrow(() -> new DataPersistenceException("Offer with ID '" + offer.getId() + "' not found for update."));

                offerJpaMapper.updateJpaEntityFromDomain(offer, entityToPersist);
            }
            OfferJpaEntity savedOrUpdatedJpaEntity = springDataOfferRepository.save(entityToPersist);
            Offer resultOffer = offerJpaMapper.toDomainEntity(savedOrUpdatedJpaEntity);
            Category category = categoryRepositoryPort.findById(savedOrUpdatedJpaEntity.getCategory().getId())
                    .orElseThrow(() -> new CategoryNotFoundException(
                            "Category not found for saved offer ID " + savedOrUpdatedJpaEntity.getId() +
                                    " (Category ID: " + savedOrUpdatedJpaEntity.getCategory().getId() + ")"
                    ));
            Supplier supplier = supplierRepositoryPort.findById(savedOrUpdatedJpaEntity.getSupplier().getId())
                    .orElseThrow(() -> new SupplierNotFoundException(
                            "Supplier not found for saved offer ID " + savedOrUpdatedJpaEntity.getId() +
                                    " (Supplier ID: " + savedOrUpdatedJpaEntity.getSupplier().getId() + ")"
                    ));
            return Offer.withId(
                    resultOffer.getId(),
                    resultOffer.getName(),
                    resultOffer.getPrice(),
                    resultOffer.getType(),
                    resultOffer.getDescription(),
                    category,
                    supplier,
                    resultOffer.getCreatedAt(),
                    resultOffer.getUpdatedAt()
            );

        } catch (DataAccessException e) {
            throw new DataPersistenceException("Failed to save offer due to data access error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new DataPersistenceException("Failed to save offer: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Offer> findById(Long id) {
        return springDataOfferRepository.findByIdWithDetails(id)
                .map(jpaEntity -> {
                    Offer offer = offerJpaMapper.toDomainEntity(jpaEntity);

                    Category category = categoryRepositoryPort.findById(jpaEntity.getCategory().getId())
                            .orElseThrow(() -> new CategoryNotFoundException("Category not found for offer ID " + id + " (Category ID: " + jpaEntity.getCategory().getId() + ")"));

                    Supplier supplier = supplierRepositoryPort.findById(jpaEntity.getSupplier().getId())
                            .orElseThrow(() -> new SupplierNotFoundException("Supplier not found for offer ID " + id + " (Supplier ID: " + jpaEntity.getSupplier().getId() + ")"));

                    return Offer.withId(
                            offer.getId(),
                            offer.getName(),
                            offer.getPrice(),
                            offer.getType(),
                            offer.getDescription(),
                            category,
                            supplier,
                            offer.getCreatedAt(),
                            offer.getUpdatedAt()
                    );
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Offer> findByName(String name) {
        return springDataOfferRepository.findByNameWithDetails(name)
                .map(jpaEntity -> {
                    Offer offer = offerJpaMapper.toDomainEntity(jpaEntity);

                    Category category = categoryRepositoryPort.findById(jpaEntity.getCategory().getId())
                            .orElseThrow(() -> new CategoryNotFoundException("Category not found for offer name " + name));

                    Supplier supplier = supplierRepositoryPort.findById(jpaEntity.getSupplier().getId())
                            .orElseThrow(() -> new SupplierNotFoundException("Supplier not found for offer name " + name));

                    return Offer.withId(
                            offer.getId(),
                            offer.getName(),
                            offer.getPrice(),
                            offer.getType(),
                            offer.getDescription(),
                            category,
                            supplier,
                            offer.getCreatedAt(),
                            offer.getUpdatedAt()
                    );
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Offer> findAll(Pageable pageable) {
        return springDataOfferRepository.findAll(pageable)
                .map(jpaEntity -> {
                    Offer offer = offerJpaMapper.toDomainEntity(jpaEntity);
                    Category category = categoryRepositoryPort.findById(jpaEntity.getCategory().getId())
                            .orElseThrow(() -> new CategoryNotFoundException("Category not found for offer ID " + jpaEntity.getId()));
                    Supplier supplier = supplierRepositoryPort.findById(jpaEntity.getSupplier().getId())
                            .orElseThrow(() -> new SupplierNotFoundException("Supplier not found for offer ID " + jpaEntity.getId()));
                    return Offer.withId(
                            offer.getId(), offer.getName(), offer.getPrice(), offer.getType(), offer.getDescription(),
                            category, supplier, offer.getCreatedAt(), offer.getUpdatedAt()
                    );
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Offer> findByNameLike(String name, Pageable pageable) {
        return springDataOfferRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(jpaEntity -> {
                    Offer offer = offerJpaMapper.toDomainEntity(jpaEntity);
                    Category category = categoryRepositoryPort.findById(jpaEntity.getCategory().getId())
                            .orElseThrow(() -> new CategoryNotFoundException("Category not found for offer name " + name));
                    Supplier supplier = supplierRepositoryPort.findById(jpaEntity.getSupplier().getId())
                            .orElseThrow(() -> new SupplierNotFoundException("Supplier not found for offer name " + name));
                    return Offer.withId(
                            offer.getId(), offer.getName(), offer.getPrice(), offer.getType(), offer.getDescription(),
                            category, supplier, offer.getCreatedAt(), offer.getUpdatedAt()
                    );
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Offer> findByCategoryId(Long categoryId, Pageable pageable) {
        return springDataOfferRepository.findByCategoryId(categoryId, pageable)
                .map(jpaEntity -> {
                    Offer offer = offerJpaMapper.toDomainEntity(jpaEntity);
                    Category category = categoryRepositoryPort.findById(jpaEntity.getCategory().getId())
                            .orElseThrow(() -> new CategoryNotFoundException("Category not found for offer ID " + jpaEntity.getId()));
                    Supplier supplier = supplierRepositoryPort.findById(jpaEntity.getSupplier().getId())
                            .orElseThrow(() -> new SupplierNotFoundException("Supplier not found for offer ID " + jpaEntity.getId()));
                    return Offer.withId(
                            offer.getId(), offer.getName(), offer.getPrice(), offer.getType(), offer.getDescription(),
                            category, supplier, offer.getCreatedAt(), offer.getUpdatedAt()
                    );
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Offer> findBySupplierId(Long supplierId, Pageable pageable) {
        return springDataOfferRepository.findBySupplierId(supplierId, pageable)
                .map(jpaEntity -> {
                    Offer offer = offerJpaMapper.toDomainEntity(jpaEntity);
                    Category category = categoryRepositoryPort.findById(jpaEntity.getCategory().getId())
                            .orElseThrow(() -> new CategoryNotFoundException("Category not found for offer ID " + jpaEntity.getId()));
                    Supplier supplier = supplierRepositoryPort.findById(jpaEntity.getSupplier().getId())
                            .orElseThrow(() -> new SupplierNotFoundException("Supplier not found for offer ID " + jpaEntity.getId()));
                    return Offer.withId(
                            offer.getId(), offer.getName(), offer.getPrice(), offer.getType(), offer.getDescription(),
                            category, supplier, offer.getCreatedAt(), offer.getUpdatedAt()
                    );
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Offer> findByPriceIsGreaterThanEqual(Double price, Pageable pageable) {
        return springDataOfferRepository.findByPriceIsGreaterThanEqual(price, pageable)
                .map(jpaEntity -> {
                    Offer offer = offerJpaMapper.toDomainEntity(jpaEntity);
                    Category category = categoryRepositoryPort.findById(jpaEntity.getCategory().getId())
                            .orElseThrow(() -> new CategoryNotFoundException("Category not found for offer ID " + jpaEntity.getId()));
                    Supplier supplier = supplierRepositoryPort.findById(jpaEntity.getSupplier().getId())
                            .orElseThrow(() -> new SupplierNotFoundException("Supplier not found for offer ID " + jpaEntity.getId()));
                    return Offer.withId(
                            offer.getId(), offer.getName(), offer.getPrice(), offer.getType(), offer.getDescription(),
                            category, supplier, offer.getCreatedAt(), offer.getUpdatedAt()
                    );
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Offer> findByPriceIsLessThanEqual(Double price, Pageable pageable) {
        return springDataOfferRepository.findByPriceIsLessThanEqual(price, pageable)
                .map(jpaEntity -> {
                    Offer offer = offerJpaMapper.toDomainEntity(jpaEntity);
                    Category category = categoryRepositoryPort.findById(jpaEntity.getCategory().getId())
                            .orElseThrow(() -> new CategoryNotFoundException("Category not found for offer ID " + jpaEntity.getId()));
                    Supplier supplier = supplierRepositoryPort.findById(jpaEntity.getSupplier().getId())
                            .orElseThrow(() -> new SupplierNotFoundException("Supplier not found for offer ID " + jpaEntity.getId()));
                    return Offer.withId(
                            offer.getId(), offer.getName(), offer.getPrice(), offer.getType(), offer.getDescription(),
                            category, supplier, offer.getCreatedAt(), offer.getUpdatedAt()
                    );
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Offer> findByPriceIsGreaterThanEqualAndPriceLessThanEqual(Double min, Double max, Pageable pageable) {
        return springDataOfferRepository.findByPriceIsGreaterThanEqualAndPriceLessThanEqual(min, max, pageable)
                .map(jpaEntity -> {
                    Offer offer = offerJpaMapper.toDomainEntity(jpaEntity);
                    Category category = categoryRepositoryPort.findById(jpaEntity.getCategory().getId())
                            .orElseThrow(() -> new CategoryNotFoundException("Category not found for offer ID " + jpaEntity.getId()));
                    Supplier supplier = supplierRepositoryPort.findById(jpaEntity.getSupplier().getId())
                            .orElseThrow(() -> new SupplierNotFoundException("Supplier not found for offer ID " + jpaEntity.getId()));
                    return Offer.withId(
                            offer.getId(), offer.getName(), offer.getPrice(), offer.getType(), offer.getDescription(),
                            category, supplier, offer.getCreatedAt(), offer.getUpdatedAt()
                    );
                });
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            springDataOfferRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataPersistenceException("Failed to delete offer with ID '" + id + "'. " + e.getMessage(), e);
        }
    }
}
