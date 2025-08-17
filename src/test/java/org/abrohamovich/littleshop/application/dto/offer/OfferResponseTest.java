package org.abrohamovich.littleshop.application.dto.offer;

import org.abrohamovich.littleshop.domain.model.Category;
import org.abrohamovich.littleshop.domain.model.Offer;
import org.abrohamovich.littleshop.domain.model.OfferType;
import org.abrohamovich.littleshop.domain.model.Supplier;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class OfferResponseTest {
    @Test
    void toResponse_WithValidData_ShouldReturnOfferResponse() {
        Long id = 1L;
        String name = "Raspberry Pi 5";
        double price = 5;
        OfferType type = OfferType.PRODUCT;
        String description = "Description";
        Category category = mock(Category.class);
        Supplier supplier = mock(Supplier.class);
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        Offer offer = Offer.withId(id, name, price, type, description,
                category, supplier, createdAt, updatedAt);

        OfferResponse offerResponse = OfferResponse.toResponse(offer);

        assertNotNull(offerResponse);
        assertEquals(offer.getId(), offerResponse.getId());
        assertEquals(offer.getName(), offerResponse.getName());
        assertEquals(offer.getPrice(), offerResponse.getPrice());
        assertEquals(offer.getType(), offerResponse.getType());
        assertEquals(offer.getDescription(), offerResponse.getDescription());
        assertNotNull(offerResponse.getCategory());
        assertNotNull(offerResponse.getSupplier());
        assertEquals(offer.getCreatedAt(), offerResponse.getCreatedAt());
        assertEquals(offer.getUpdatedAt(), offerResponse.getUpdatedAt());
    }

    @Test
    void toResponse_WithNullOffer_ShouldThrowException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> OfferResponse.toResponse(null));

        assertTrue(ex.getMessage().startsWith("Offer cannot be null to continue the conversion."));
    }
}
