package org.abrohamovich.littleshop.application.usecase.offer;

import org.abrohamovich.littleshop.application.dto.offer.OfferResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetOfferServiceTest {
    private final Long offerId = 1L;
    private final Long categoryId = 1L;
    private final Long supplierId = 1L;
    @Mock
    private OfferRepositoryPort offerRepositoryPort;
    @InjectMocks
    private GetOfferService getOfferService;
    private Offer testOffer;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        Category category = Category.withId(categoryId, "Category", "desc", LocalDateTime.now(), LocalDateTime.now());
        Supplier supplier = Supplier.withId(supplierId, "Supplier", "mail", "phone", "address", "desc", LocalDateTime.now(), LocalDateTime.now());
        testOffer = Offer.withId(offerId, "Test Offer", 10.0, OfferType.PRODUCT, "Description", category, supplier, LocalDateTime.now(), LocalDateTime.now());
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void findById_shouldReturnOfferResponse_whenOfferExists() {
        when(offerRepositoryPort.findById(offerId)).thenReturn(Optional.of(testOffer));

        OfferResponse response = getOfferService.findById(offerId);

        assertNotNull(response);
        assertEquals("Test Offer", response.getName());
        assertEquals(10.0, response.getPrice());
        verify(offerRepositoryPort).findById(offerId);
    }

    @Test
    void findById_shouldThrowOfferNotFoundException_whenOfferDoesNotExist() {
        when(offerRepositoryPort.findById(offerId)).thenReturn(Optional.empty());

        assertThrows(OfferNotFoundException.class, () -> getOfferService.findById(offerId));
        verify(offerRepositoryPort).findById(offerId);
    }

    @Test
    void findAll_shouldReturnPageOfOfferResponses_whenOffersExist() {
        Page<Offer> offerPage = new PageImpl<>(List.of(testOffer), pageable, 1);
        when(offerRepositoryPort.findAll(pageable)).thenReturn(offerPage);

        Page<OfferResponse> responsePage = getOfferService.findAll(pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("Test Offer", responsePage.getContent().get(0).getName());
        verify(offerRepositoryPort).findAll(pageable);
    }

    @Test
    void findAll_shouldReturnEmptyPage_whenNoOffersExist() {
        Page<Offer> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(offerRepositoryPort.findAll(pageable)).thenReturn(emptyPage);

        Page<OfferResponse> responsePage = getOfferService.findAll(pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(offerRepositoryPort).findAll(pageable);
    }

    @Test
    void findByNameLike_shouldReturnPageOfOfferResponses_whenMatchesExist() {
        String searchTerm = "Test";
        Page<Offer> offerPage = new PageImpl<>(List.of(testOffer), pageable, 1);
        when(offerRepositoryPort.findByNameLike(searchTerm, pageable)).thenReturn(offerPage);

        Page<OfferResponse> responsePage = getOfferService.findByNameLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("Test Offer", responsePage.getContent().get(0).getName());
        verify(offerRepositoryPort).findByNameLike(searchTerm, pageable);
    }

    @Test
    void findByNameLike_shouldReturnEmptyPage_whenNoMatchesFound() {
        String searchTerm = "NoMatch";
        Page<Offer> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(offerRepositoryPort.findByNameLike(searchTerm, pageable)).thenReturn(emptyPage);

        Page<OfferResponse> responsePage = getOfferService.findByNameLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(offerRepositoryPort).findByNameLike(searchTerm, pageable);
    }

    @Test
    void findByCategoryId_shouldReturnPageOfOfferResponses_whenMatchesExist() {
        Page<Offer> offerPage = new PageImpl<>(List.of(testOffer), pageable, 1);
        when(offerRepositoryPort.findByCategoryId(categoryId, pageable)).thenReturn(offerPage);

        Page<OfferResponse> responsePage = getOfferService.findByCategoryId(categoryId, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("Test Offer", responsePage.getContent().get(0).getName());
        verify(offerRepositoryPort).findByCategoryId(categoryId, pageable);
    }

    @Test
    void findByCategoryId_shouldReturnEmptyPage_whenNoMatchesFound() {
        Long nonExistentId = 99L;
        Page<Offer> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(offerRepositoryPort.findByCategoryId(nonExistentId, pageable)).thenReturn(emptyPage);

        Page<OfferResponse> responsePage = getOfferService.findByCategoryId(nonExistentId, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(offerRepositoryPort).findByCategoryId(nonExistentId, pageable);
    }

    @Test
    void findBySupplierId_shouldReturnPageOfOfferResponses_whenMatchesExist() {
        Page<Offer> offerPage = new PageImpl<>(List.of(testOffer), pageable, 1);
        when(offerRepositoryPort.findBySupplierId(supplierId, pageable)).thenReturn(offerPage);

        Page<OfferResponse> responsePage = getOfferService.findBySupplierId(supplierId, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("Test Offer", responsePage.getContent().get(0).getName());
        verify(offerRepositoryPort).findBySupplierId(supplierId, pageable);
    }

    @Test
    void findBySupplierId_shouldReturnEmptyPage_whenNoMatchesFound() {
        Long nonExistentId = 99L;
        Page<Offer> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(offerRepositoryPort.findBySupplierId(nonExistentId, pageable)).thenReturn(emptyPage);

        Page<OfferResponse> responsePage = getOfferService.findBySupplierId(nonExistentId, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(offerRepositoryPort).findBySupplierId(nonExistentId, pageable);
    }

    @Test
    void findByPriceIsGreaterThanEqual_shouldReturnPageOfOfferResponses_whenMatchesExist() {
        Double price = 5.0;
        Page<Offer> offerPage = new PageImpl<>(List.of(testOffer), pageable, 1);
        when(offerRepositoryPort.findByPriceIsGreaterThanEqual(price, pageable)).thenReturn(offerPage);

        Page<OfferResponse> responsePage = getOfferService.findByPriceIsGreaterThanEqual(price, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("Test Offer", responsePage.getContent().get(0).getName());
        verify(offerRepositoryPort).findByPriceIsGreaterThanEqual(price, pageable);
    }

    @Test
    void findByPriceIsGreaterThanEqual_shouldReturnEmptyPage_whenNoMatchesFound() {
        Double price = 20.0;
        Page<Offer> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(offerRepositoryPort.findByPriceIsGreaterThanEqual(price, pageable)).thenReturn(emptyPage);

        Page<OfferResponse> responsePage = getOfferService.findByPriceIsGreaterThanEqual(price, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(offerRepositoryPort).findByPriceIsGreaterThanEqual(price, pageable);
    }

    @Test
    void findByPriceIsLessThanEqual_shouldReturnPageOfOfferResponses_whenMatchesExist() {
        Double price = 15.0;
        Page<Offer> offerPage = new PageImpl<>(List.of(testOffer), pageable, 1);
        when(offerRepositoryPort.findByPriceIsLessThanEqual(price, pageable)).thenReturn(offerPage);

        Page<OfferResponse> responsePage = getOfferService.findByPriceIsLessThanEqual(price, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("Test Offer", responsePage.getContent().get(0).getName());
        verify(offerRepositoryPort).findByPriceIsLessThanEqual(price, pageable);
    }

    @Test
    void findByPriceIsLessThanEqual_shouldReturnEmptyPage_whenNoMatchesFound() {
        Double price = 5.0;
        Page<Offer> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(offerRepositoryPort.findByPriceIsLessThanEqual(price, pageable)).thenReturn(emptyPage);

        Page<OfferResponse> responsePage = getOfferService.findByPriceIsLessThanEqual(price, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(offerRepositoryPort).findByPriceIsLessThanEqual(price, pageable);
    }

    @Test
    void findByPriceIsGreaterThanEqualAndPriceLessThanEqual_shouldReturnPageOfOfferResponses_whenMatchesExist() {
        Double minPrice = 5.0;
        Double maxPrice = 15.0;
        Page<Offer> offerPage = new PageImpl<>(List.of(testOffer), pageable, 1);
        when(offerRepositoryPort.findByPriceIsGreaterThanEqualAndPriceLessThanEqual(minPrice, maxPrice, pageable)).thenReturn(offerPage);

        Page<OfferResponse> responsePage = getOfferService.findByPriceIsGreaterThanEqualAndPriceLessThanEqual(minPrice, maxPrice, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("Test Offer", responsePage.getContent().get(0).getName());
        verify(offerRepositoryPort).findByPriceIsGreaterThanEqualAndPriceLessThanEqual(minPrice, maxPrice, pageable);
    }

    @Test
    void findByPriceIsGreaterThanEqualAndPriceLessThanEqual_shouldReturnEmptyPage_whenNoMatchesFound() {
        Double minPrice = 20.0;
        Double maxPrice = 30.0;
        Page<Offer> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(offerRepositoryPort.findByPriceIsGreaterThanEqualAndPriceLessThanEqual(minPrice, maxPrice, pageable)).thenReturn(emptyPage);

        Page<OfferResponse> responsePage = getOfferService.findByPriceIsGreaterThanEqualAndPriceLessThanEqual(minPrice, maxPrice, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(offerRepositoryPort).findByPriceIsGreaterThanEqualAndPriceLessThanEqual(minPrice, maxPrice, pageable);
    }
}
