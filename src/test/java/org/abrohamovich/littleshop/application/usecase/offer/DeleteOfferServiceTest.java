package org.abrohamovich.littleshop.application.usecase.offer;

import org.abrohamovich.littleshop.application.port.out.persistence.OfferRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.offer.OfferNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteOfferServiceTest {
    private final Long offerId = 1L;
    @Mock
    private OfferRepositoryPort offerRepositoryPort;
    @InjectMocks
    private DeleteOfferService deleteOfferService;
    private Offer testOffer;

    @BeforeEach
    void setUp() {
        Category category = Category.withId(1L, "Category", "desc", LocalDateTime.now(), LocalDateTime.now());
        Supplier supplier = Supplier.withId(1L, "Supplier", "mail", "phone", "address", "desc", LocalDateTime.now(), LocalDateTime.now());
        testOffer = Offer.withId(offerId, "Test Offer", 10.0, OfferType.PRODUCT, "Description", category, supplier, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void deleteById_shouldDeleteOffer_whenOfferExists() {
        when(offerRepositoryPort.findById(offerId)).thenReturn(Optional.of(testOffer));

        deleteOfferService.deleteById(offerId);

        verify(offerRepositoryPort, times(1)).findById(offerId);
        verify(offerRepositoryPort, times(1)).deleteById(offerId);
    }

    @Test
    void deleteById_shouldThrowOfferNotFoundException_whenOfferDoesNotExist() {
        Long nonExistentId = 99L;
        when(offerRepositoryPort.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(OfferNotFoundException.class, () ->
                deleteOfferService.deleteById(nonExistentId)
        );

        verify(offerRepositoryPort, times(1)).findById(nonExistentId);
        verify(offerRepositoryPort, never()).deleteById(anyLong());
    }
}
