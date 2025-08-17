package org.abrohamovich.littleshop.application.usecase.supplier;

import org.abrohamovich.littleshop.application.dto.supplier.SupplierResponse;
import org.abrohamovich.littleshop.application.port.out.persistence.SupplierRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.supplier.SupplierNotFoundException;
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
class GetSupplierServiceTest {
    private final Long supplierId = 1L;
    @Mock
    private SupplierRepositoryPort supplierRepositoryPort;
    @InjectMocks
    private GetSupplierService getSupplierService;
    private Supplier testSupplier;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        testSupplier = Supplier.withId(supplierId, "Test Supplier", "test@example.com", "1234567890", "123 Main St", "Test description", LocalDateTime.now(), LocalDateTime.now());
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void findById_shouldReturnSupplierResponse_whenSupplierExists() {
        when(supplierRepositoryPort.findById(supplierId)).thenReturn(Optional.of(testSupplier));

        SupplierResponse response = getSupplierService.findById(supplierId);

        assertNotNull(response);
        assertEquals("Test Supplier", response.getName());
        assertEquals("test@example.com", response.getEmail());
        verify(supplierRepositoryPort).findById(supplierId);
    }

    @Test
    void findById_shouldThrowSupplierNotFoundException_whenSupplierDoesNotExist() {
        when(supplierRepositoryPort.findById(supplierId)).thenReturn(Optional.empty());

        assertThrows(SupplierNotFoundException.class, () -> getSupplierService.findById(supplierId));
        verify(supplierRepositoryPort).findById(supplierId);
    }

    @Test
    void findAll_shouldReturnPageOfSupplierResponses_whenSuppliersExist() {
        Page<Supplier> supplierPage = new PageImpl<>(List.of(testSupplier), pageable, 1);
        when(supplierRepositoryPort.findAll(pageable)).thenReturn(supplierPage);

        Page<SupplierResponse> responsePage = getSupplierService.findAll(pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("Test Supplier", responsePage.getContent().get(0).getName());
        verify(supplierRepositoryPort).findAll(pageable);
    }

    @Test
    void findAll_shouldReturnEmptyPage_whenNoSuppliersExist() {
        Page<Supplier> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(supplierRepositoryPort.findAll(pageable)).thenReturn(emptyPage);

        Page<SupplierResponse> responsePage = getSupplierService.findAll(pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(supplierRepositoryPort).findAll(pageable);
    }

    @Test
    void findByNameLike_shouldReturnPageOfSupplierResponses_whenMatchesExist() {
        String searchTerm = "Test";
        Page<Supplier> supplierPage = new PageImpl<>(List.of(testSupplier), pageable, 1);
        when(supplierRepositoryPort.findByNameLike(searchTerm, pageable)).thenReturn(supplierPage);

        Page<SupplierResponse> responsePage = getSupplierService.findByNameLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("Test Supplier", responsePage.getContent().get(0).getName());
        verify(supplierRepositoryPort).findByNameLike(searchTerm, pageable);
    }

    @Test
    void findByNameLike_shouldReturnEmptyPage_whenNoMatchesFound() {
        String searchTerm = "NoMatch";
        Page<Supplier> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(supplierRepositoryPort.findByNameLike(searchTerm, pageable)).thenReturn(emptyPage);

        Page<SupplierResponse> responsePage = getSupplierService.findByNameLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(supplierRepositoryPort).findByNameLike(searchTerm, pageable);
    }

    @Test
    void findByEmailLike_shouldReturnPageOfSupplierResponses_whenMatchesExist() {
        String searchTerm = "@example.com";
        Page<Supplier> supplierPage = new PageImpl<>(List.of(testSupplier), pageable, 1);
        when(supplierRepositoryPort.findByEmailLike(searchTerm, pageable)).thenReturn(supplierPage);

        Page<SupplierResponse> responsePage = getSupplierService.findByEmailLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("test@example.com", responsePage.getContent().get(0).getEmail());
        verify(supplierRepositoryPort).findByEmailLike(searchTerm, pageable);
    }

    @Test
    void findByEmailLike_shouldReturnEmptyPage_whenNoMatchesFound() {
        String searchTerm = "NoMatch";
        Page<Supplier> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(supplierRepositoryPort.findByEmailLike(searchTerm, pageable)).thenReturn(emptyPage);

        Page<SupplierResponse> responsePage = getSupplierService.findByEmailLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(supplierRepositoryPort).findByEmailLike(searchTerm, pageable);
    }

    @Test
    void findByPhoneLike_shouldReturnPageOfSupplierResponses_whenMatchesExist() {
        String searchTerm = "1234";
        Page<Supplier> supplierPage = new PageImpl<>(List.of(testSupplier), pageable, 1);
        when(supplierRepositoryPort.findByPhoneLike(searchTerm, pageable)).thenReturn(supplierPage);

        Page<SupplierResponse> responsePage = getSupplierService.findByPhoneLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("1234567890", responsePage.getContent().get(0).getPhone());
        verify(supplierRepositoryPort).findByPhoneLike(searchTerm, pageable);
    }

    @Test
    void findByPhoneLike_shouldReturnEmptyPage_whenNoMatchesFound() {
        String searchTerm = "NoMatch";
        Page<Supplier> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(supplierRepositoryPort.findByPhoneLike(searchTerm, pageable)).thenReturn(emptyPage);

        Page<SupplierResponse> responsePage = getSupplierService.findByPhoneLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(supplierRepositoryPort).findByPhoneLike(searchTerm, pageable);
    }
}