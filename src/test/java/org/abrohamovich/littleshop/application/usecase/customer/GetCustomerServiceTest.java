package org.abrohamovich.littleshop.application.usecase.customer;

import org.abrohamovich.littleshop.application.dto.customer.CustomerResponse;
import org.abrohamovich.littleshop.application.port.out.persistence.CustomerRepositoryPort;
import org.abrohamovich.littleshop.domain.exception.customer.CustomerNotFoundException;
import org.abrohamovich.littleshop.domain.model.Customer;
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
class GetCustomerServiceTest {
    private final Long customerId = 1L;
    @Mock
    private CustomerRepositoryPort customerRepositoryPort;
    @InjectMocks
    private GetCustomerService getCustomerService;
    private Customer testCustomer;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        testCustomer = Customer.withId(customerId, "John", "Doe", "john.doe@example.com", "1234567890", "123 Main St", LocalDateTime.now(), LocalDateTime.now());
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void findById_shouldReturnCustomerResponse_whenCustomerExists() {
        when(customerRepositoryPort.findById(customerId)).thenReturn(Optional.of(testCustomer));

        CustomerResponse response = getCustomerService.findById(customerId);

        assertNotNull(response);
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals("john.doe@example.com", response.getEmail());
        verify(customerRepositoryPort).findById(customerId);
    }

    @Test
    void findById_shouldThrowCustomerNotFoundException_whenCustomerDoesNotExist() {
        when(customerRepositoryPort.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> getCustomerService.findById(customerId));
        verify(customerRepositoryPort).findById(customerId);
    }


    @Test
    void findAll_shouldReturnPageOfCustomerResponses_whenCustomersExist() {
        Page<Customer> customerPage = new PageImpl<>(List.of(testCustomer), pageable, 1);
        when(customerRepositoryPort.findAll(pageable)).thenReturn(customerPage);

        Page<CustomerResponse> responsePage = getCustomerService.findAll(pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("John", responsePage.getContent().get(0).getFirstName());
        verify(customerRepositoryPort).findAll(pageable);
    }

    @Test
    void findAll_shouldReturnEmptyPage_whenNoCustomersExist() {
        Page<Customer> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(customerRepositoryPort.findAll(pageable)).thenReturn(emptyPage);

        Page<CustomerResponse> responsePage = getCustomerService.findAll(pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(customerRepositoryPort).findAll(pageable);
    }

    @Test
    void findByFirstNameLike_shouldReturnPageOfCustomerResponses_whenMatchesExist() {
        String searchTerm = "Jo";
        Page<Customer> customerPage = new PageImpl<>(List.of(testCustomer), pageable, 1);
        when(customerRepositoryPort.findByFirstNameLike(searchTerm, pageable)).thenReturn(customerPage);

        Page<CustomerResponse> responsePage = getCustomerService.findByFirstNameLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("John", responsePage.getContent().get(0).getFirstName());
        verify(customerRepositoryPort).findByFirstNameLike(searchTerm, pageable);
    }

    @Test
    void findByFirstNameLike_shouldReturnEmptyPage_whenNoMatchesFound() {
        String searchTerm = "NoMatch";
        Page<Customer> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(customerRepositoryPort.findByFirstNameLike(searchTerm, pageable)).thenReturn(emptyPage);

        Page<CustomerResponse> responsePage = getCustomerService.findByFirstNameLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(customerRepositoryPort).findByFirstNameLike(searchTerm, pageable);
    }

    @Test
    void findByLastNameLike_shouldReturnPageOfCustomerResponses_whenMatchesExist() {
        String searchTerm = "Do";
        Page<Customer> customerPage = new PageImpl<>(List.of(testCustomer), pageable, 1);
        when(customerRepositoryPort.findByLastNameLike(searchTerm, pageable)).thenReturn(customerPage);

        Page<CustomerResponse> responsePage = getCustomerService.findByLastNameLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("Doe", responsePage.getContent().get(0).getLastName());
        verify(customerRepositoryPort).findByLastNameLike(searchTerm, pageable);
    }

    @Test
    void findByLastNameLike_shouldReturnEmptyPage_whenNoMatchesFound() {
        String searchTerm = "NoMatch";
        Page<Customer> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(customerRepositoryPort.findByLastNameLike(searchTerm, pageable)).thenReturn(emptyPage);

        Page<CustomerResponse> responsePage = getCustomerService.findByLastNameLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(customerRepositoryPort).findByLastNameLike(searchTerm, pageable);
    }

    @Test
    void findByEmailLike_shouldReturnPageOfCustomerResponses_whenMatchesExist() {
        String searchTerm = "@example.com";
        Page<Customer> customerPage = new PageImpl<>(List.of(testCustomer), pageable, 1);
        when(customerRepositoryPort.findByEmailLike(searchTerm, pageable)).thenReturn(customerPage);

        Page<CustomerResponse> responsePage = getCustomerService.findByEmailLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("john.doe@example.com", responsePage.getContent().get(0).getEmail());
        verify(customerRepositoryPort).findByEmailLike(searchTerm, pageable);
    }

    @Test
    void findByEmailLike_shouldReturnEmptyPage_whenNoMatchesFound() {
        String searchTerm = "NoMatch";
        Page<Customer> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(customerRepositoryPort.findByEmailLike(searchTerm, pageable)).thenReturn(emptyPage);

        Page<CustomerResponse> responsePage = getCustomerService.findByEmailLike(searchTerm, pageable);

        assertNotNull(responsePage);
        assertEquals(0, responsePage.getTotalElements());
        assertTrue(responsePage.getContent().isEmpty());
        verify(customerRepositoryPort).findByEmailLike(searchTerm, pageable);
    }
}