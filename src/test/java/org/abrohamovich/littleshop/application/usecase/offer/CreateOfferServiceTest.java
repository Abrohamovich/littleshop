package org.abrohamovich.littleshop.application.usecase.offer;

import org.abrohamovich.littleshop.application.dto.offer.OfferCreateCommand;
import org.abrohamovich.littleshop.application.dto.offer.OfferResponse;
import org.abrohamovich.littleshop.application.port.out.persistence.CategoryRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.OfferRepositoryPort;
import org.abrohamovich.littleshop.application.port.out.persistence.SupplierRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.exception.category.CategoryNotFoundException;
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
public class CreateOfferServiceTest {
    @Mock
    private OfferRepositoryPort offerRepositoryPort;
    @Mock
    private CategoryRepositoryPort categoryRepositoryPort;
    @Mock
    private SupplierRepositoryPort supplierRepositoryPort;
    @InjectMocks
    private CreateOfferService createOfferService;

    private OfferCreateCommand command;
    private Category existingCategory;
    private Supplier existingSupplier;

    @BeforeEach
    void setUp() {
        command = new OfferCreateCommand("New Offer", 9.99, OfferType.PRODUCT, "Description", 1L, 1L);
        existingCategory = Category.withId(1L, "Category Name", "Description", LocalDateTime.now(), LocalDateTime.now());
        existingSupplier = Supplier.withId(1L, "Supplier Name", "supplier@example.com", "1234567890", "Address", "Description", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void save_shouldReturnOfferResponse_whenOfferIsUniqueAndDependenciesExist() {
        when(offerRepositoryPort.findByName(command.getName())).thenReturn(Optional.empty());
        when(categoryRepositoryPort.findById(command.getCategoryId())).thenReturn(Optional.of(existingCategory));
        when(supplierRepositoryPort.findById(command.getSupplierId())).thenReturn(Optional.of(existingSupplier));

        Offer savedOffer = Offer.withId(1L, command.getName(), command.getPrice(), command.getType(), command.getDescription(), existingCategory, existingSupplier, LocalDateTime.now(), LocalDateTime.now());
        when(offerRepositoryPort.save(any(Offer.class))).thenReturn(savedOffer);

        OfferResponse response = createOfferService.save(command);

        assertNotNull(response);
        assertEquals("New Offer", response.getName());
        assertEquals(9.99, response.getPrice());
        verify(offerRepositoryPort, times(1)).findByName(command.getName());
        verify(categoryRepositoryPort, times(1)).findById(command.getCategoryId());
        verify(supplierRepositoryPort, times(1)).findById(command.getSupplierId());
        verify(offerRepositoryPort, times(1)).save(any(Offer.class));
    }

    @Test
    void save_shouldThrowDuplicateEntryException_whenOfferNameAlreadyExists() {
        Offer existingOffer = Offer.withId(2L, "New Offer", 15.0, OfferType.PRODUCT, "Existing description", existingCategory, existingSupplier, LocalDateTime.now(), LocalDateTime.now());
        when(offerRepositoryPort.findByName(command.getName())).thenReturn(Optional.of(existingOffer));

        DuplicateEntryException exception = assertThrows(DuplicateEntryException.class, () ->
                createOfferService.save(command)
        );

        assertEquals("Offer with name " + command.getName() + " already exists.", exception.getMessage());
        verify(offerRepositoryPort, times(1)).findByName(command.getName());
        verify(categoryRepositoryPort, never()).findById(anyLong());
        verify(supplierRepositoryPort, never()).findById(anyLong());
        verify(offerRepositoryPort, never()).save(any(Offer.class));
    }

    @Test
    void save_shouldThrowCategoryNotFoundException_whenCategoryDoesNotExist() {
        when(offerRepositoryPort.findByName(command.getName())).thenReturn(Optional.empty());
        when(categoryRepositoryPort.findById(command.getCategoryId())).thenReturn(Optional.empty());

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () ->
                createOfferService.save(command)
        );

        assertEquals("Category with ID '" + command.getCategoryId() + "' does not exist.", exception.getMessage());
        verify(offerRepositoryPort, times(1)).findByName(command.getName());
        verify(categoryRepositoryPort, times(1)).findById(command.getCategoryId());
        verify(supplierRepositoryPort, never()).findById(anyLong());
        verify(offerRepositoryPort, never()).save(any(Offer.class));
    }

    @Test
    void save_shouldThrowSupplierNotFoundException_whenSupplierDoesNotExist() {
        when(offerRepositoryPort.findByName(command.getName())).thenReturn(Optional.empty());
        when(categoryRepositoryPort.findById(command.getCategoryId())).thenReturn(Optional.of(existingCategory));
        when(supplierRepositoryPort.findById(command.getSupplierId())).thenReturn(Optional.empty());

        SupplierNotFoundException exception = assertThrows(SupplierNotFoundException.class, () ->
                createOfferService.save(command)
        );

        assertEquals("Supplier with ID '" + command.getCategoryId() + "' does not exist.", exception.getMessage());
        verify(offerRepositoryPort, times(1)).findByName(command.getName());
        verify(categoryRepositoryPort, times(1)).findById(command.getCategoryId());
        verify(supplierRepositoryPort, times(1)).findById(command.getSupplierId());
        verify(offerRepositoryPort, never()).save(any(Offer.class));
    }
}
