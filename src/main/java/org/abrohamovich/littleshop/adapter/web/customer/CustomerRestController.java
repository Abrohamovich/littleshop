package org.abrohamovich.littleshop.adapter.web.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.PageResponse;
import org.abrohamovich.littleshop.application.dto.customer.CustomerCreateCommand;
import org.abrohamovich.littleshop.application.dto.customer.CustomerResponse;
import org.abrohamovich.littleshop.application.dto.customer.CustomerUpdateCommand;
import org.abrohamovich.littleshop.application.port.in.customer.CreateCustomerUseCase;
import org.abrohamovich.littleshop.application.port.in.customer.DeleteCustomerUseCase;
import org.abrohamovich.littleshop.application.port.in.customer.GetCustomerUseCase;
import org.abrohamovich.littleshop.application.port.in.customer.UpdateCustomerUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerRestController {
    private final CreateCustomerUseCase  createCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final CustomerWebMapper customerWebMapper;

    @PostMapping
    public ResponseEntity<CustomerWebResponse> create(@Valid @RequestBody CustomerCreateWebRequest customerCreateWebRequest) {
        CustomerCreateCommand customerCreateCommand = customerWebMapper.toCreateCommand(customerCreateWebRequest);
        CustomerResponse customerResponse = createCustomerUseCase.save(customerCreateCommand);
        return new ResponseEntity<>(customerWebMapper.toWebResponse(customerResponse), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerWebResponse> findById(@PathVariable Long id) {
        CustomerResponse customerResponse = getCustomerUseCase.findById(id);
        return new ResponseEntity<>(customerWebMapper.toWebResponse(customerResponse), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResponse<CustomerWebResponse>> get(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerResponse> customersPage;

        if (firstName != null && !firstName.isBlank()) {
            customersPage = getCustomerUseCase.findByFirstNameLike(firstName, pageable);
        } else if (lastName != null && !lastName.isBlank()) {
            customersPage = getCustomerUseCase.findByLastNameLike(lastName, pageable);
        } else if (email != null && !email.isBlank()) {
            customersPage = getCustomerUseCase.findByEmailLike(email, pageable);
        } else {
            customersPage = getCustomerUseCase.findAll(pageable);
        }

        Page<CustomerWebResponse> webResponsePage = customersPage.map(customerWebMapper::toWebResponse);
        return new ResponseEntity<>(PageResponse.fromSpringPage(webResponsePage), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerWebResponse> update(@PathVariable Long id, @Valid @RequestBody CustomerUpdateWebRequest customerUpdateWebRequest) {
        CustomerUpdateCommand customerUpdateCommand =  customerWebMapper.toUpdateCommand(customerUpdateWebRequest);
        CustomerResponse response = updateCustomerUseCase.update(id, customerUpdateCommand);
        return new ResponseEntity<>(customerWebMapper.toWebResponse(response), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        deleteCustomerUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
