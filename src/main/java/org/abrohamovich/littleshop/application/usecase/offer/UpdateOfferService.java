package org.abrohamovich.littleshop.application.usecase.offer;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.offer.OfferResponse;
import org.abrohamovich.littleshop.application.dto.offer.OfferUpdateCommand;
import org.abrohamovich.littleshop.application.port.in.offer.UpdateOfferUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.CategoryRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.OfferRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.SupplierRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.exception.category.CategoryNotFoundException;
import org.abrohamovich.littleshop.domain.exception.offer.OfferNotFoundException;
import org.abrohamovich.littleshop.domain.exception.supplier.SupplierNotFoundException;
import org.abrohamovich.littleshop.domain.model.Category;
import org.abrohamovich.littleshop.domain.model.Offer;
import org.abrohamovich.littleshop.domain.model.Supplier;

@RequiredArgsConstructor
public class UpdateOfferService implements UpdateOfferUseCase {
    private final OfferRepositoryPort offerRepositoryPort;
    private final CategoryRepositoryPort categoryRepositoryPort;
    private final SupplierRepositoryPort supplierRepositoryPort;

    @Override
    public OfferResponse update(Long id, OfferUpdateCommand command) {
        Offer existingOffer = offerRepositoryPort.findById(id)
                .orElseThrow(() -> new OfferNotFoundException("Offer with ID " + id + " not found for update."));

        if (!existingOffer.getName().equals(command.getName())) {
            if (offerRepositoryPort.findByName(command.getName()).isPresent()) {
                throw new DuplicateEntryException("Offer with name '" + command.getName() + "' already exists.");
            }
        }

        Category category = categoryRepositoryPort.findById(command.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + command.getCategoryId() + " does not exist"));

        Supplier supplier = supplierRepositoryPort.findById(command.getSupplierId())
                .orElseThrow(() -> new SupplierNotFoundException("Supplier with ID " + command.getCategoryId() + " does not exist"));

        existingOffer.updateDetails(command.getName(), command.getPrice(), command.getType(),
                command.getDescription(), category, supplier);

        Offer updatedOffer = offerRepositoryPort.save(existingOffer);

        return OfferResponse.toResponse(updatedOffer);
    }
}
