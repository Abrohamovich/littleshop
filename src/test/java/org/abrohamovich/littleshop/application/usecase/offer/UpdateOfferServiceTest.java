package org.abrohamovich.littleshop.application.usecase.offer;

import org.abrohamovich.littleshop.application.dto.offer.OfferResponse;
import org.abrohamovich.littleshop.application.dto.offer.OfferUpdateCommand;
import org.abrohamovich.littleshop.application.port.out.persistence.CategoryRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.OfferRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.SupplierRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.exception.category.CategoryNotFoundException;
import org.abrohamovich.littleshop.domain.exception.offer.OfferNotFoundException;
import org.abrohamovich.littleshop.domain.exception.supplier.SupplierNotFoundException;
import org.abrohamovich.littleshop.domain.model.Category;
import org.abrohamovich.littleshop.domain.model.Offer;
import org.abrohamovich.littleshop.domain.model.OfferType;
import org.abrohamovich.littleshop.domain.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateOfferServiceTest {
    private final Long offerId = 1L;
    @Mock
    private OfferRepositoryPort offerRepositoryPort;
    @Mock
    private CategoryRepositoryPort categoryRepositoryPort;
    @Mock
    private SupplierRepositoryPort supplierRepositoryPort;
    @InjectMocks
    private UpdateOfferService updateOfferService;
    private Offer existingOffer;
    private Category existingCategory;
    private Supplier existingSupplier;

    @BeforeEach
    void setUp() {
        existingCategory = Category.withId(1L, "Original Category", "desc", LocalDateTime.now(), LocalDateTime.now());
        existingSupplier = Supplier.withId(1L, "Original Supplier", "mail", "phone", "address", "desc", LocalDateTime.now(), LocalDateTime.now());
        existingOffer = Offer.withId(offerId, "Original Offer", 10.0, OfferType.PRODUCT, "Original desc", existingCategory, existingSupplier, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void update_shouldReturnOfferResponse_whenUpdateIsSuccessful() {
        OfferUpdateCommand command = new OfferUpdateCommand("Updated Offer", 15.0, OfferType.SERVICE, "Updated desc", 2L, 2L);
        Category newCategory = Category.withId(2L, "New Category", "desc", LocalDateTime.now(), LocalDateTime.now());
        Supplier newSupplier = Supplier.withId(2L, "New Supplier", "newmail", "newphone", "newaddress", "newdesc", LocalDateTime.now(), LocalDateTime.now());

        when(offerRepositoryPort.findById(offerId)).thenReturn(Optional.of(existingOffer));
        when(offerRepositoryPort.findByName(command.getName())).thenReturn(Optional.empty());
        when(categoryRepositoryPort.findById(command.getCategoryId())).thenReturn(Optional.of(newCategory));
        when(supplierRepositoryPort.findById(command.getSupplierId())).thenReturn(Optional.of(newSupplier));
        when(offerRepositoryPort.save(any(Offer.class))).thenReturn(
                Offer.withId(offerId, "Updated Offer", 15.0, OfferType.SERVICE, "Updated desc", newCategory, newSupplier, existingOffer.getCreatedAt(), LocalDateTime.now())
        );

        OfferResponse response = updateOfferService.update(offerId, command);

        assertNotNull(response);
        assertEquals("Updated Offer", response.getName());
        assertEquals(15.0, response.getPrice());
        verify(offerRepositoryPort).findById(offerId);
        verify(offerRepositoryPort).findByName(command.getName());
        verify(categoryRepositoryPort).findById(command.getCategoryId());
        verify(supplierRepositoryPort).findById(command.getSupplierId());
        verify(offerRepositoryPort).save(any(Offer.class));
    }

    @Test
    void update_shouldThrowOfferNotFoundException_whenOfferDoesNotExist() {
        Long nonExistentId = 99L;
        OfferUpdateCommand command = new OfferUpdateCommand("Updated Offer", 15.0, OfferType.SERVICE, "Updated desc", 2L, 2L);

        when(offerRepositoryPort.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(OfferNotFoundException.class, () -> updateOfferService.update(nonExistentId, command));

        verify(offerRepositoryPort).findById(nonExistentId);
        verify(offerRepositoryPort, never()).findByName(anyString());
        verify(categoryRepositoryPort, never()).findById(anyLong());
        verify(supplierRepositoryPort, never()).findById(anyLong());
        verify(offerRepositoryPort, never()).save(any(Offer.class));
    }

    @Test
    void update_shouldThrowDuplicateEntryException_whenNameAlreadyExists() {
        Offer existingDuplicateOffer = Offer.withId(2L, "Updated Offer", 20.0, OfferType.PRODUCT, "desc", existingCategory, existingSupplier, LocalDateTime.now(), LocalDateTime.now());
        OfferUpdateCommand command = new OfferUpdateCommand("Updated Offer", 15.0, OfferType.SERVICE, "Updated desc", 1L, 1L);

        when(offerRepositoryPort.findById(offerId)).thenReturn(Optional.of(existingOffer));
        when(offerRepositoryPort.findByName(command.getName())).thenReturn(Optional.of(existingDuplicateOffer));

        assertThrows(DuplicateEntryException.class, () -> updateOfferService.update(offerId, command));

        verify(offerRepositoryPort).findById(offerId);
        verify(offerRepositoryPort).findByName(command.getName());
        verify(categoryRepositoryPort, never()).findById(anyLong());
        verify(supplierRepositoryPort, never()).findById(anyLong());
        verify(offerRepositoryPort, never()).save(any(Offer.class));
    }

    @Test
    void update_shouldThrowCategoryNotFoundException_whenCategoryDoesNotExist() {
        OfferUpdateCommand command = new OfferUpdateCommand("Updated Offer", 15.0, OfferType.SERVICE, "Updated desc", 99L, 1L);

        when(offerRepositoryPort.findById(offerId)).thenReturn(Optional.of(existingOffer));
        when(offerRepositoryPort.findByName(command.getName())).thenReturn(Optional.empty());
        when(categoryRepositoryPort.findById(command.getCategoryId())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> updateOfferService.update(offerId, command));

        verify(offerRepositoryPort).findById(offerId);
        verify(offerRepositoryPort).findByName(command.getName());
        verify(categoryRepositoryPort).findById(command.getCategoryId());
        verify(supplierRepositoryPort, never()).findById(anyLong());
        verify(offerRepositoryPort, never()).save(any(Offer.class));
    }

    @Test
    void update_shouldThrowSupplierNotFoundException_whenSupplierDoesNotExist() {
        OfferUpdateCommand command = new OfferUpdateCommand("Updated Offer", 15.0, OfferType.SERVICE, "Updated desc", 1L, 99L);
        when(offerRepositoryPort.findById(offerId)).thenReturn(Optional.of(existingOffer));
        when(offerRepositoryPort.findByName(command.getName())).thenReturn(Optional.empty());
        when(categoryRepositoryPort.findById(command.getCategoryId())).thenReturn(Optional.of(existingCategory));
        when(supplierRepositoryPort.findById(command.getSupplierId())).thenReturn(Optional.empty());

        assertThrows(SupplierNotFoundException.class, () -> updateOfferService.update(offerId, command));

        verify(offerRepositoryPort).findById(offerId);
        verify(offerRepositoryPort).findByName(command.getName());
        verify(categoryRepositoryPort).findById(command.getCategoryId());
        verify(supplierRepositoryPort).findById(command.getSupplierId());
        verify(offerRepositoryPort, never()).save(any(Offer.class));
    }
}
